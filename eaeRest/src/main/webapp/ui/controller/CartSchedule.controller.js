sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"org/eae/tools/utils/FormatUtils"
], function(Controller, FormatUtils){
	return Controller.extend("org.eae.tools.controller.CartSchedule", {
		formatUtils : FormatUtils,
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("cartSchedule").attachPatternMatched(function(oEvent){
				var objectPage = this.getView().byId("cartSchedulePage");
				var periodId = oEvent.getParameter("arguments").periodId;
				var scheduleId = oEvent.getParameter("arguments").scheduleId;
				this._sScheduleId = scheduleId;
				this.loadSericeDays(periodId);
				objectPage.bindElement("/Schedule/" + periodId);
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		loadSericeDays : function(periodId) {
			if(periodId === undefined) {
				periodId = this._sPeriod;
			} else {
				this._sPeriod = periodId;	
			}
			
			var oModel = this.getView().getModel();
			oModel.setProperty("/Schedule/" + periodId, {});
		    //oModel.fetchData("rest/periods/" + periodId + "/weeks", "/Schedule/" + periodId + "/weeks", true, {}, true).
			oModel.fetchData("rest/periods/period/" + periodId + "/schedule/" +this._sScheduleId + "/weeks" , "/Schedule/" + periodId + "/weeks", true, {}, true).
		    then(function(){
		    	oModel.read("rest/periods/read/" + periodId).then(function(data){
		    		oModel.setProperty("/Schedule/" + periodId + "/info", data);
		    	});
		    	
		    	oModel.read("rest/cartSchedule/read/" + this._sScheduleId).then(function(data){
		    		oModel.setProperty("/Schedule/" + periodId + "/cartSchedule", data);
		    	})
		    }.bind(this));
		},
		
		formatHeaderTitle : function(obj) {
			if(obj) {
				return FormatUtils.formatPeriodDates(obj.starts, obj.ends);	
			}
			return "";
		},

		formatShiftAssignmentVisibility : function(oSchedule) {
			if(!oSchedule) {
				return false;
			} 
			
			if(oSchedule.guid === this._sScheduleId) {
				return true;
			}
			
			return false;
		},


	});
});