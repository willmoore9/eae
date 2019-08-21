sap.ui.define([
     "sap/ui/core/mvc/Controller",
     "sap/m/Page",
     "sap/ui/core/routing/History"
], function(Controller, Page, History){
	"use strict";
	return Controller.extend("org.eae.tools.controller.App", {
		onInit: function() {
		},
		
		onNavBack : function(oEvent) {
			var oHistory = History.getInstance();
			var sPreviousHash = oHistory.getPreviousHash();

			if (sPreviousHash !== undefined) {
				window.history.go(-1);
			} else {
				var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
				oRouter.navTo("landingPage", true);
			}	
		},
		
		handleUserItemPressed : function(oEvent){
			var oButton = oEvent.getSource();
			// create action sheet only once
			if (!this._actionSheet) {//sap.ui.xmlfragment("createShift", "org.eae.tools.view.fragments.AddShift", this);
				this._actionSheet = sap.ui.xmlfragment(
					"org.eae.tools.view.fragments.UserIconActionSheet",
					this
				);
				this.getView().addDependent(this._actionSheet);
			}

			this._actionSheet.openBy(oButton);
		},
		
		onLogoutPress: function() {
			sap.m.URLHelper.redirect("logout");
		},
		onLoginPress: function() {
			sap.m.URLHelper.redirect("#login");
		},
		onEditProfile : function() {
			var oRouter = this.getOwnerComponent().getRouter();
			var oModel = this.getView().getModel();
			oRouter.navTo("publisherProfile", {
				publisherId : oModel.getProperty("/PublisherData/Publisher/guid"),
				isMyAccount : true
		});
		}
		
	});
});