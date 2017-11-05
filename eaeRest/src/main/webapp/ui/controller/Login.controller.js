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
			model.login(username, pass).then(function(data){
				var oRouter = this.getOwnerComponent().getRouter();
				oRouter.navTo("");
				var oComp = this.getOwnerComponent()
				oComp.readCurrentUserInfo();
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