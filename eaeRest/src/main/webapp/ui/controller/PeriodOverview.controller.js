sap.ui.define([
     "sap/ui/core/mvc/Controller",
     "org/eae/tools/utils/FormatUtils"
], function(Controller, FormatUtils){
	"use strict";
	return Controller.extend("org.eae.tools.controller.PeriodOverview", {
		formatUtils : FormatUtils,
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
			if(periodId === undefined) {
				periodId = this._sPeriod;
			} else {
				this._sPeriod = periodId;	
			}
			
			var oModel = this.getView().getModel();
			oModel.setProperty("/Schedule/" + periodId, {});
		    oModel.fetchData("rest/periods/" + periodId + "/weeks", "/Schedule/" + periodId + "/weeks", true, {}, true).
		    then(function(){
		    	oModel.read("rest/periods/read/" + periodId).then(function(data){
		    		oModel.setProperty("/Schedule/" + periodId + "/info", data)
		    		sap.ui.core.BusyIndicator.hide();
		    	});
		    });
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
		
//		formatShiftTitle : function(starts, ends) {
//			var oStarts = new Date(starts);
//			var oEnds = new Date(ends);
//			var oDateFormat = sap.ui.core.format.DateFormat.getDateInstance({
//			    pattern: "HH:mm"
//			});
//
//			return oDateFormat.format(oStarts)  + " - " + oDateFormat.format(oEnds);
//		},
		

		


		onPublisherSearch : function(oEvent) {
		},
		

		onSharePeriod : function(oEvent) {
			var oModel = this.getView().getModel();
			var oPage = oEvent.getSource();
			var oBC = oPage.getBindingContext();
			var oPeriodGuid = oBC.getModel().getProperty(oBC.getPath());
			var periodGuid = oPeriodGuid.info.guid;
			oModel.read("rest/periods/share/" + periodGuid).then(function(data){
				oModel.setProperty("/Schedule/" + periodGuid + "/info", data)
			}).catch(function(){
				console.log("error share");
			});;
		},
		
		onUnsharePeriod : function(oEvent) {
			var oModel = this.getView().getModel();
			var oPage = oEvent.getSource();
			var oBC = oPage.getBindingContext();
			var oPeriodGuid = oBC.getModel().getProperty(oBC.getPath());
			var periodGuid = oPeriodGuid.info.guid;
			oModel.read("rest/periods/unshare/" + periodGuid).then(function(data){
				oModel.setProperty("/Schedule/" + periodGuid + "/info", data)
			}).catch(function(){
				console.log("error unshare");
			});
		},
		

		

		formatHeaderTitle : function(obj) {
			if(obj) {
				return FormatUtils.formatPeriodDates(obj.starts, obj.ends);	
			}
			return "";
		},
		
		
		
		buildPublishersInShift : function(sId, oContext) {
			var oPublisherPath = oContext.getPath();
			var oPublisher = oContext.getModel().getObject(oPublisherPath);
			var aPathParts = oPublisherPath.split("/");

			aPathParts.splice(aPathParts.length-2, 2);
			
			var oShift = oContext.getObject(aPathParts.join("/"));
			return new sap.m.StandardListItem(sId, {
				title:"{name} {surname}",
				infoState: "Error",
				iconDensityAware:false,
				press: this.onShiftPublisherPress.bind(this),
				type:"Active",
				highlight: (oShift.shiftLeader != null && oShift.shiftLeader.guid === oPublisher.guid) ? "Success": "None",
				info: (oShift.trolleyCarrier != null && oShift.trolleyCarrier.guid === oPublisher.guid) ? "#": ""
			});
		},
		
		onRemoveShift : function(oEvent) {
			var oBc = oEvent.getSource().getParent().getBindingContext();
			var oShiftObj = oBc.getModel().getObject(oBc.getPath());
			var oModel = this.getView().getModel();
			oModel.removeById("rest/shifts/delete/" + oShiftObj.guid).then(function(){
				this.loadSericeDays();
			}.bind(this));
		},
		
		onLocationTodeliverChange : function(oEvent) {
			var sValue = oEvent.getParameter("value");
			var oModel = this.getView().getModel();
			var oBC = oEvent.getSource().getBindingContext();
			var oDay = oBC.getModel().getObject(oBC.getPath());
			oModel.post("rest/shifts/deliverAfterDay/" + oDay.guid + "/location/"  + sValue);
		}
	});
});