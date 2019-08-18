sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/core/format/DateFormat",
    "org/eae/tools/utils/FormatUtils"
], function(Controller, DateFormat, FormatUtils){
	return Controller.extend("org.eae.tools.controller.PlacementsReportWorklist", {
		formatUtils : FormatUtils,
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("placementsReport").attachPatternMatched(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		refreshTable : function() {
			var oModel = this.getView().getModel();
			oModel.post("rest/shiftReport/").then(function(oData) {
				oModel.setProperty("/ShiftReports", oData.objects);
				sap.ui.core.BusyIndicator.hide();
			});
			
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
		}
	});
});