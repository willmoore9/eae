sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"sap/ui/core/format/DateFormat"
], function(Controller, DateFormat){
	return Controller.extend("org.eae.tools.controller.PublisherCalendar", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("publisherCalendar").attachPatternMatched(function(oEvent){
				var objectPage = this.getView().byId("pubPeriodPage");
				var periodId = oEvent.getParameter("arguments").periodId;
				var publisherId = oEvent.getParameter("arguments").publisherId;
				this.loadSericeDays(periodId);
				objectPage.bindElement("/Publisher/" + periodId);
			}.bind(this));
		},
		
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		
		loadSericeDays : function(periodId) {
			var oModel = this.getView().getModel();
			oModel.setProperty("/Publisher/" + periodId, {});
			oModel.fetchData("rest/periods/" + periodId + "/weeks", "/Publisher/" + periodId + "/weeks", true);
		},
		
		formatDayTitle : function(iDate){
			var oDate = new Date(iDate);
			var oDateFormat = DateFormat.getDateInstance({
			    pattern: "E, MMM d"
			});

			return oDateFormat.format(oDate);
		},
		
		formatShiftTitle : function(starts, ends) {
			var oStarts = new Date(starts);
			var oEnds = new Date(ends);
			var oDateFormat = DateFormat.getDateInstance({
			    pattern: "HH:mm"
			});

			return oDateFormat.format(oStarts)  + " - " + oDateFormat.format(oEnds);
		},

		
	});
});