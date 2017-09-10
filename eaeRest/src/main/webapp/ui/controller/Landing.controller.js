sap.ui.define([
     "sap/ui/core/mvc/Controller",
     "sap/m/Page"
], function(Controller){
	"use strict";
	return Controller.extend("org.eae.tools.controller.Landing", {
		init: function() {
			console.log("Landing init");
		},
		
		navigateToTeamCalendar : function() {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("overviewPeriods");
		},
		
		navigateToPublisherManagement : function() {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("overviewPublishers");
		}
	});
});