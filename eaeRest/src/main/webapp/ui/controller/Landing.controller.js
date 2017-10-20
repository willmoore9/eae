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
		},
		
		navigateToPersonalCalendarManagement : function() {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("publisherCalendar", {
					periodId : "e43caeed-edf4-4b41-a00b-6629a0b3c5b1",
					publisherId : "01897d1d-e49e-4b69-8bfc-bfa6362b6760"
			});
			
		}
	});
});