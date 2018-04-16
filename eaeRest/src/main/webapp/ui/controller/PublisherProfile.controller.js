sap.ui.define([
	"sap/ui/core/mvc/Controller",
], function(Controller){
	return Controller.extend("org.eae.tools.controller.PublisherProfile", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("publisherProfile").attachPatternMatched(function(oEvent){
//				var objectPage = this.getView().byId("pubProfilePage");
				this._publisherId = oEvent.getParameter("arguments").publisherId;
				this._isEditMyProfile = oEvent.getParameter("arguments").isMyAccount;
				if(this._isEditMyProfile) {
					this.getOwnerComponent().readCurrentUserInfo();	
				} else {
					this._readUserInfo(this._publisherId);
				}				
				
			}.bind(this));
		},
		
		_readUserInfo : function(sUserId) {
			var oModel = this.getView().getModel();
			oModel.read("rest/publishers/read/" + sUserId).then(function(oData){
				oModel.setProperty("/Temp/PublisherEdit/",oData.objects[0]);
			}.bind(this)).catch(function(data){
				if(data.status === 401) {
					this.getRouter().navTo("login");
				}
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			if(this._isEditMyProfile) {
				oRouter.navTo("landingPage");	
			} else {
				oRouter.navTo("overviewPublishers");
			}
			
		},
		
		onCreatePublisherPress : function() {
			var oParams = this._getEditPublisher();
			var oModel = this.getView().getModel();
			sap.ui.core.BusyIndicator.show();
			var sUpdatePath = this._isEditMyProfile ?  "rest/publishers/updateMyProfile" : "rest/publishers/update/";
			oModel.createObject(sUpdatePath,
					JSON.stringify(oParams),
					"POST",
					"/Publishers", 
					true
			).then(function(){
				sap.ui.core.BusyIndicator.hide();
				this.onSuccess();
			}.bind(this));
			
		},
		
		onSavePublisherData : function(oEvent) {
			this.onCreatePublisherPress();
		},
		
		onSuccess : function() {
			var sSavedText = this.getView().getModel("i18n").getProperty("savedConfirmaton");
			sap.m.MessageToast.show(sSavedText);
		},
		
		onLivePasswordChange : function(oEvent) {
			var sPincode = oEvent.getParameter("value");
			if(sPincode.length == 1 && parseInt(sPincode) == 0) {
				var sErrorMessage = this.getView().getModel("i18n").getProperty("pinCodeShouldNotStartFrom0");
				sap.m.MessageToast.show(sErrorMessage);
				oEvent.getSource().setValue("");
			}
		},
		
		_getEditPublisher : function() {
			var oModel = this.getView().getModel();
			return oModel.getProperty("/Temp/PublisherEdit");
		}
	});
});