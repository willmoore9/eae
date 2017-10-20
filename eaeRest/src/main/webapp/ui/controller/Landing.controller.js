sap.ui.define([
     "sap/ui/core/mvc/Controller",
     "sap/m/Page"
], function(Controller){
	"use strict";
	return Controller.extend("org.eae.tools.controller.Landing", {
		init: function() {
		},
		
		navigateToTeamCalendar : function() {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("overviewPeriods");
		},
		
		navigateToPublisherManagement : function() {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("overviewPublishers");
		},
		
		navigateToPersonalCalendarManagement : function() {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("publisherCalendar", {
					periodId : "358462b5-651c-4e27-a6ca-1b8a28d21b5b",
					publisherId : "166e6f2c-256e-449e-8f56-d3275c778641"
			});
		}
	});
});