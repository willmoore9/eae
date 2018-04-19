sap.ui.define([
     "sap/ui/core/mvc/Controller",
     "sap/m/Page"
], function(Controller){
	"use strict";
	return Controller.extend("org.eae.tools.controller.Login", {
		init: function() {
		},
		
		loginPress : function() {
			var model = this.getView().getModel();
			var username = this.getView().byId("usernameInput").getValue();
			var pass = this.getView().byId("passwordInput").getValue();
			sap.ui.core.BusyIndicator.show(500);
			model.login(username, pass).then(function(data){
				var oRouter = this.getOwnerComponent().getRouter();
				oRouter.navTo("");
//				var oComp = this.getOwnerComponent()
//				oComp.readCurrentUserInfo();
				sap.ui.core.BusyIndicator.hide();
			}.bind(this)).catch(function(){
				var sErrorMessage = this.getView().getModel("i18n").getProperty("loginFailed");
				sap.ui.core.BusyIndicator.hide();
				sap.m.MessageToast.show(sErrorMessage);
			}.bind(this));
		},
		
		userIsAdmin : function(oEvent) {
			var isSelected = oEvent.getParameter("selected");
			var oPassElem = this.getView().byId("passwordFElement");
			if(isSelected) {
				oPassElem.setVisible(true);
			} else {
				oPassElem.setVisible(false);
			}
		}
	});
});