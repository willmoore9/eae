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
					periodId : "1203f9d8-81a9-4da9-9a43-91fa450c7b10",
					publisherId : "b719aedd-fc80-402e-848b-671c98405209"
			});
		}
	});
});