sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"org/eae/tools/utils/FormatUtils"
], function(Controller, FormatUtils){
	return Controller.extend("org.eae.tools.controller.ScheduleOverview", {
		formatUtils : FormatUtils,
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("currentWeekSchedule").attachPatternMatched(function(oEvent){
				console.log("currentWeekSchedule");
				
			}.bind(this));
		},
		
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
	});
});