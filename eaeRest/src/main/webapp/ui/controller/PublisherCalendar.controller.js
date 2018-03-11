sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"org/eae/tools/utils/FormatUtils"
], function(Controller, FormatUtils){
	return Controller.extend("org.eae.tools.controller.PublisherCalendar", {
		formatUtils : FormatUtils,
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("publisherCalendar").attachPatternMatched(function(oEvent){
				var objectPage = this.getView().byId("pubPeriodPage");
				var periodId = oEvent.getParameter("arguments").periodId;
				var publisherId = oEvent.getParameter("arguments").publisherId;
				this.loadSericeDays(periodId);
				objectPage.bindElement("/PublisherData/Publisher/" + periodId);
				this.periodUUID = periodId;
				this.publisherUUID = publisherId;
			}.bind(this));
		},
		
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		
		loadSericeDays : function(periodId) {
			var oModel = this.getView().getModel();
			oModel.setProperty("/PublisherData/Publisher/" + periodId, {});
			oModel.fetchData("rest/periods/" + periodId + "/weeks", "/PublisherData/Publisher/" + periodId + "/weeks", true);
			sap.ui.core.BusyIndicator.hide();
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
		
		assignToShift : function(oEvent) {
			var oModel = this.getView().getModel();
			var bSelected = oEvent.getParameter("selected");
			var oSource = oEvent.getSource().getBindingContext();
			var oShift = oSource.getObject(oSource.getPath());
			
			var isSelected = oEvent.getParameter("selected");
			
			if(isSelected) {
				oModel.createObject("rest/shifts/addAssignRequest/" + oShift.guid + "/" + this.publisherUUID,
						{},
						"POST",
						oSource.getPath(), false);
			} else {
				oModel.createObject("rest/shifts/removeAssignRequest/" + oShift.guid + "/" + this.publisherUUID,
						{},
						"POST",
						oSource.getPath(), false);
			}
		},
		
		isUserAssignedFormatter : function(aAssigned) {
			for(var i = 0; i < aAssigned.length; i++) {
				if(aAssigned[i].publisher.guid == this.publisherUUID){
					return true;					
				}
			}
			return false;
		},
		
		formatHeaderTitle : function(obj) {
			if(obj) {
				return FormatUtils.formatPeriodDates(obj.starts, obj.ends);	
			}
			return "";
		}
		
	});
});