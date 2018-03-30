sap.ui.define([
	"sap/ui/core/mvc/Controller",
], function(Controller){
	return Controller.extend("org.eae.tools.controller.PublisherProfile", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("publisherProfile").attachPatternMatched(function(oEvent){
//				var objectPage = this.getView().byId("pubProfilePage");
				this._periodId = oEvent.getParameter("arguments").publisherId;
//				this.readUserInfo(this._periodId);
				this.getOwnerComponent().readCurrentUserInfo();
				debugger;
			}.bind(this));
		},
		
		readUserInfo : function(sUserId) {
			var oModel = this.getModel();
			oModel.read("rest/landing").then(function(oData){
				this.getModel().setProperty("/PublisherData/Publisher",oData.publisher);
				this.getModel().setProperty("/PublisherData/Period",oData.currentPeriod);
				this.getModel().setProperty("/PublisherData/SharedSchedules",oData.sharedSchedules);
			}.bind(this)).catch(function(data){
				if(data.status === 401) {
					this.getRouter().navTo("login");
				}
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
			
//			this.onCreatePublisherPress();
		},
		
		onCreatePublisherPress : function() {
			var oModel = this.getView().getModel();
			var oParams = oModel.getProperty("/PublisherData/Publisher");
			sap.ui.core.BusyIndicator.show();
			oModel.createObject("rest/publishers/update/",
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
		}
	});
});