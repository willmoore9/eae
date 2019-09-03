sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/core/format/DateFormat",
    "org/eae/tools/utils/FormatUtils"
], function(Controller, DateFormat, FormatUtils){
	return Controller.extend("org.eae.tools.controller.PlacementsReportWorklist", {
		formatUtils : FormatUtils,
		
		__fromDate: null,
		__toDate : null,
		
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("placementsReport").attachPatternMatched(function(){
				this.refreshTable();
			}.bind(this));
			
			var oDateRange = this.getView().byId("dateRange");
			var oMonthAgoDate = new Date();
			oMonthAgoDate.setMonth(oMonthAgoDate.getMonth() - 1);
			oDateRange.setDateValue(oMonthAgoDate);
			oDateRange.setSecondDateValue(new Date());
		},
		
		refreshTable : function() {
			var oDateRange = this.getView().byId("dateRange");
			var oDateRange = {
					"starts" : oDateRange.getDateValue().getTime(),
					"ends" : oDateRange.getSecondDateValue().getTime()
			}
			var oModel = this.getView().getModel();
			oModel.post("rest/shiftReport/", "GET", oDateRange).then(function(oData) {
				oModel.setProperty("/ShiftReports", oData.objects);
				sap.ui.core.BusyIndicator.hide();
			}.bind(this));
			


		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		handleDeleteShiftReport : function (oEvent) {
			var oModel = this.getView().getModel();
			var guid = oEvent.getParameter("listItem").getBindingContext().getObject().guid;
			oModel.removeById("rest/shiftReport/delete/"+guid).then(function(oEvent) {
				this.refreshTable();
			}.bind(this)); 
		},
		
		onFilter : function(oEvent) {
			
			this.refreshTable();
			
		},
		handleUpdateBinding : function(eEvent) {
			console.log("handleUpdateBinding");
		}
	});
});