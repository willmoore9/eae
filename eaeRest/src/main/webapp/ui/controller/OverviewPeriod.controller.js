sap.ui.define([
     "sap/ui/core/mvc/Controller",
     "sap/ui/core/format/DateFormat"
], function(Controller){
	"use strict";
	return Controller.extend("org.eae.tools.controller.OverviewPeriod", {
		onInit: function() {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("overviewPeriod").attachPatternMatched(function(oEvent){
				var objectPage = this.getView().byId("periodPage");
				var periodId = oEvent.getParameter("arguments").periodId;
				this.loadSericeDays(periodId);
				objectPage.bindElement("/Schedule/" + periodId);
			}.bind(this));

		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("overviewPeriods");
		},
		
		loadSericeDays : function(periodId) {
			var oModel = this.getView().getModel();
			oModel.setProperty("/Schedule/" + periodId, {});
			oModel.fetchData("rest/periods/" + periodId + "/weeks", "/Schedule/" + periodId + "/weeks", true);
		},
		
		formatDayTitle : function(iDate){
			var oDate = new Date(iDate);
			var oDateFormat = sap.ui.core.format.DateFormat.getDateInstance({
			    pattern: "E, MMM d"
			});

			return oDateFormat.format(oDate);
		},
		
		onAddShiftPress : function(oEvent) {
			if(!this._oShiftDialog) {
				this._oShiftDialog = sap.ui.xmlfragment("createShift", "org.eae.tools.view.fragments.AddShift", this);
				this.getView().addDependent(this._oShiftDialog);	
			}
			this._oShiftDialog.open();
			
			var oDayBindingContext = oEvent.getSource().getBindingContext();
			var serviceDayId = oDayBindingContext.getProperty("guid");
			var date = oDayBindingContext.getProperty("date");
			this.getView().getModel().setProperty("/ui/createShift/serviceDayId", serviceDayId);
			this.getView().getModel().setProperty("/ui/createShift/pathToMerge", oDayBindingContext.getPath() + "/shifts");
			this.getView().getModel().setProperty("/ui/createShift/date", date);
		},
		
		onCancelShiftPress : function(){
			this._oShiftDialog.close();
			this.getView().getModel().setProperty("/ui/createShift", {});
		},
		
		onCreateShiftPress : function(oEvent) {
			this.createShift();
		},
		
		createShift : function() {
				var oModel = this.getView().getModel();
				var oData = oModel.getProperty("/ui/createShift");
				
				var oParams = {
						starts : this._getDatefromTime(oData.date, oData.from),
						ends :  this._getDatefromTime(oData.date, oData.to),
				};
				oModel.createObject("rest/shifts/create/" + 
						oModel.getProperty("/ui/createShift/serviceDayId"),
						JSON.stringify(oParams),
						"POST",
						oModel.getProperty("/ui/createShift/pathToMerge"), true);
		
		},
		
		_getDatefromTime : function(oDate, sTime) {
			var aTimeArray = sTime.split(":");
			var dateTime = new Date(oDate);
			dateTime.setHours(aTimeArray[0]);
			dateTime.setMinutes(aTimeArray[1]);
			return dateTime.toJSON();
		},
		
		formatShiftTitle : function(starts, ends) {
			var oStarts = new Date(starts);
			var oEnds = new Date(ends);
			var oDateFormat = sap.ui.core.format.DateFormat.getDateInstance({
			    pattern: "HH:mm"
			});

			return oDateFormat.format(oStarts)  + " - " + oDateFormat.format(oEnds);
		},
		
		selectPublisherForAdd : function(oEvent) {
			
		},
		
		onPublisherSearch : function(oEvent) {
			console.log("onPublisherSearch" + oEvent);
		}
	});
});