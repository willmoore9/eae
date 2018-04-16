sap.ui.define([
     "sap/ui/core/mvc/Controller"
], function(Controller){
	"use strict";
	return Controller.extend("org.eae.tools.controller.ConcentOverview", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("myConsents").attachPatternMatched(function(oEvent){
				this.getOwnerComponent().readCurrentUserInfo();
			}.bind(this));
		},
		
		onAgree : function(oEvent) {
			var oModel = this.getView().getModel();
			var sPublisherGuid = oModel.getObject("/PublisherData/Publisher").guid;
			sap.ui.core.BusyIndicator.show(1000);
			var obj = {
					
			};
			obj.concentStatusCode = oEvent.getParameter("selected") ? "YES" : "NO";
			
			oModel.post("rest/publishers/concent/publisher/" + sPublisherGuid, "POST",JSON.stringify( obj )).then(function(oData){
				sap.ui.core.BusyIndicator.hide();
			});
			
		},
		
		onSave : function(oEvent) {
			var oModel = this.getView().getModel();
			var sPhoneNumer = this.getView().byId("telMaskedInput").getValue().split(" ").join("");
			
			oModel.setProperty("/PublisherData/Publisher/telephone", sPhoneNumer);
			
			var oPub = oModel.getObject("/PublisherData/Publisher");
			
			sap.ui.core.BusyIndicator.show();
			var sUpdatePath =  "rest/publishers/updateMyProfile"
			oModel.createObject(sUpdatePath,
					JSON.stringify(oPub),
					"POST",
					"/Publishers", 
					true
			).then(function(){
				sap.ui.core.BusyIndicator.hide();
				var oRouter = this.getOwnerComponent().getRouter();
				sap.ui.core.BusyIndicator.show(1000);
				oRouter.navTo("");
			}.bind(this));
			
		}
	});
});