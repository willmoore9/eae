sap.ui.define([
     "sap/ui/core/mvc/Controller",
     "sap/ui/core/format/DateFormat"
], function(Controller,DateFormat){
	"use strict";
	return Controller.extend("org.eae.tools.controller.Landing", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("landingPage").attachPatternMatched(function(oEvent){
				this.getOwnerComponent().readCurrentUserInfo();
			}.bind(this));
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
			var oModel = this.getView().getModel();
			oRouter.navTo("publisherCalendar", {
					periodId : oModel.getProperty("/PublisherData/Period/guid"),
					publisherId : oModel.getProperty("/PublisherData/Publisher/guid")
			});
		},
		
		navigateToCuurentSchedule : function() {
			var oRouter = this.getOwnerComponent().getRouter();
			var oModel = this.getView().getModel();
			oRouter.navTo("currentWeekSchedule");			
		},
		
		formatPeriodDates : function(starts, ends) {
			console.log(starts, ends);
			var oDateFormat = DateFormat.getDateInstance({
			    pattern: "MMM d"
			});


			return oDateFormat.format(new Date(starts)) + " - " + oDateFormat.format(new Date(ends));
		},
		
		manageCartPlaces : function() {
			var oRouter = this.getOwnerComponent().getRouter();
			var oModel = this.getView().getModel();
			oRouter.navTo("cartPlaceWorklist");	
		},
		
		navigateToScheduleManage : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			var oModel = this.getView().getModel();
			oRouter.navTo("scheduleWorklist");	
		}
	});
});