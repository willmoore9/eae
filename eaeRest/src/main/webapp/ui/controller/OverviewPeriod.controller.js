sap.ui.define([
     "sap/ui/core/mvc/Controller",
     "org/eae/tools/utils/FormatUtils"
], function(Controller, FormatUtils){
	"use strict";
	return Controller.extend("org.eae.tools.controller.OverviewPeriod", {
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
		
		selectPublisherForAdd : function(oEvent) {
			var oShiftContext = oEvent.getSource().getBindingContext();
			this._oCurrentShiftContext = oShiftContext;
			if(!this._oAssignToShiftDialog) {
				this._oAssignToShiftDialog = sap.ui.xmlfragment("createShift", "org.eae.tools.view.fragments.AddPublisherToShift", this);
				this.getView().addDependent(this._oAssignToShiftDialog);	
			}
			this._oAssignToShiftDialog.setBindingContext(oShiftContext);
			this._oAssignToShiftDialog.open();
		},
		
		onBeforeAssignPublishersOpen : function(oEvent) {
			var oAssignedPublishers = sap.ui.getCore().byId("createShift--assignedPublishers");
			oAssignedPublishers.removeSelections();
			var oAllPublishers = sap.ui.getCore().byId("createShift--allPublishers");
			oAllPublishers.removeSelections();

			var oModel = this.getView().getModel();
			var oShiftBindingContext = oEvent.getSource().getBindingContext();
			var oShift = oShiftBindingContext.getModel().getObject(oShiftBindingContext.getPath());
			oModel.fetchData("rest/shifts/assignableToShift/" + oShift.guid, oShiftBindingContext.getPath() + "/assignable", true, {}, true);
			oModel.fetchData("rest/publishers/", "/Publishers", true);
		},
		
		onCloseAssignPublishersPress : function (oEvent) {
			this._oAssignToShiftDialog.close();
		},
		
		onPublisherSearch : function(oEvent) {
		},
		
		onAssignPublisher : function(oEvent) {
			var oModel = this.getView().getModel();
			var oPublisherItem = oEvent.getParameter("listItem");
			var oPublisherContext = oPublisherItem.getBindingContext();
			var oObj = oPublisherContext.getModel().getObject(oPublisherContext.getPath());
			var oCurrentShift = this._oCurrentShiftContext.getModel().getObject(this._oCurrentShiftContext.getPath());
			oModel.createObject("rest/shifts/assign/" + oCurrentShift.guid + "/" + oObj.guid,
					{},
					"POST",
					this._oCurrentShiftContext.getPath() + "/assigned", true).then(function(){
					});
	
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
		
		onShiftPublisherPress : function(oEvent) {
			if(!this._oPublisherActions) {
				this._oPublisherActions = sap.ui.xmlfragment("publisherAdminActions", "org.eae.tools.view.fragments.PublisherAdminActions", this);
				this.getView().addDependent(this._oPublisherActions);	
			}
			
			var oPubBc = oEvent.getSource().getBindingContext();
			var oShiftBc = oEvent.getSource().getParent().getBindingContext();
			
			this._AdminActins_Publisher = oPubBc.getModel().getObject(oPubBc.getPath()).guid;
			this._AdminActins_Shift = oShiftBc.getModel().getObject(oShiftBc.getPath()).guid;
			this._AdminAction_LineItemId = oEvent.getSource().getId();
			this._oPublisherActions.openBy(oEvent.getSource());
		},
		
		onDeletePublisherFromShiftDelete : function(oEvent) {
			console.log(oEvent.getParameters());
			var oModel = this.getView().getModel();
			var oRemovedListItem = oEvent.getParameter("listItem");
			var oBindingContextP = oRemovedListItem.getBindingContext();
			var oBindingContextS = oEvent.getSource().getBindingContext();
			var oPublisherObj = oBindingContextP.getModel().getObject(oBindingContextP.getPath());
			var oShiftObj = oBindingContextS.getModel().getObject(oBindingContextS.getPath());
			var oShift = {
				shiftBinding : oBindingContextS,
			};
			
			oModel.post("rest/shifts/unassign/" + oShiftObj.guid + "/" + oPublisherObj.guid,
					"POST",
					"").then(function(aResults){
						var oUpdatedShift = aResults.objects[0]; 
						this.shiftBinding.getModel().setProperty(this.shiftBinding.getPath(), oUpdatedShift);
						
					}.bind(oShift));
		},
		
		formatHeaderTitle : function(obj) {
			if(obj) {
				return FormatUtils.formatPeriodDates(obj.starts, obj.ends);	
			}
			return "";
		},
		
		onAssignAsLeader : function(oEvent) {
			var oModel = this.getView().getModel();
			debugger;
			oModel.post("rest/shifts/assignShiftLeader/" + this._AdminActins_Shift + "/" + this._AdminActins_Publisher,
					"POST"
					).then(function(){
						sap.ui.getCore().byId(this._AdminAction_LineItemId).setHighlight(sap.ui.core.MessageType.Success);
					}.bind(this));
	
		},
		
		
		onUnassignAsLeader : function(oEvent) {
			var oModel = this.getView().getModel();
			debugger;
			oModel.post("rest/shifts/unassignShiftLeader/" + this._AdminActins_Shift + "/" + this._AdminActins_Publisher,
					"POST"
					).then(function(){
						sap.ui.getCore().byId(this._AdminAction_LineItemId).setHighlight(sap.ui.core.MessageType.None);
					}.bind(this));
	
		},
		
		onAssignCarrier : function(oEvent) {
			var oModel = this.getView().getModel();
			debugger;
			oModel.post("rest/shifts/assignTrolleyCarrier/" + this._AdminActins_Shift + "/" + this._AdminActins_Publisher,
					"POST"
					).then(function(){
						sap.ui.getCore().byId(this._AdminAction_LineItemId).setInfo("#");
					}.bind(this));
	
		},
		
		onUnassignCarrier : function(oEvent) {
			var oModel = this.getView().getModel();
			debugger;
			oModel.post("rest/shifts/unassignTrolleyCarrier/" + this._AdminActins_Shift + "/" + this._AdminActins_Publisher,
					"POST"
					).then(function(){
						sap.ui.getCore().byId(this._AdminAction_LineItemId).setInfo("");
					}.bind(this));
	
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
		}
	});
});