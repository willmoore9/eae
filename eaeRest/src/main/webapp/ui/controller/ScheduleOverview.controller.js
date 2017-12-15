sap.ui.define([
	"sap/ui/core/mvc/Controller",
	"org/eae/tools/utils/FormatUtils"
], function(Controller, FormatUtils){
	return Controller.extend("org.eae.tools.controller.ScheduleOverview", {
		formatUtils : FormatUtils,
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("overviewSchedule").attachPatternMatched(function(oEvent){
				var objectPage = this.getView().byId("schedulePage");
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
		    oModel.fetchData("rest/periods/" + periodId + "/weeks", "/Schedule/" + periodId + "/weeks", true, {}, true).
		    then(function(){
		    	oModel.read("rest/periods/read/" + periodId).then(function(data){
		    		oModel.setProperty("/Schedule/" + periodId + "/info", data)
		    	});
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
			var oAssignment = oContext.getModel().getObject(oPublisherPath);
			var aPathParts = oPublisherPath.split("/");

			aPathParts.splice(aPathParts.length-2, 2);
			
			var oShift = oContext.getObject(aPathParts.join("/"));
			
			return new sap.m.StandardListItem(sId, {
				title:"{publisher/name} {publisher/surname}",
				infoState: "Error",
				iconDensityAware:false,
				press: this.onShiftPublisherPress.bind(this),
				type:"Active",
				visible : (oAssignment != null && oAssignment.schedule != null && oAssignment.schedule.guid === this._sScheduleId)
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
		
		onBeforeAssignPublishersOpen : function(oEvent) {
			var oAssignedPublishers = sap.ui.getCore().byId("addPubToShift--assignedPublishers");
			oAssignedPublishers.removeSelections();
			var oAllPublishers = sap.ui.getCore().byId("addPubToShift--allPublishers");
			oAllPublishers.removeSelections();

			var oModel = this.getView().getModel();
			var oShiftBindingContext = oEvent.getSource().getBindingContext();
			var oShift = oShiftBindingContext.getModel().getObject(oShiftBindingContext.getPath());
			oModel.fetchData("rest/shifts/assignableToShift/" + oShift.guid, "/AssignedToShiftTemp", true, {}, true);
			oModel.fetchData("rest/publishers/", "/Publishers", true);
		},
		
		onAfterAssignPublishersClose : function(oEvent) {
			this.getView().getModel().setProperty("/AssignedToShiftTemp", []);
		},

		onAssignPublisher : function(oEvent) {
			var oModel = this.getView().getModel();
			var oPublisherItem = oEvent.getParameter("listItem");
			var oPublisherContext = oPublisherItem.getBindingContext();
			var oObj = oPublisherContext.getModel().getObject(oPublisherContext.getPath());
			var oCurrentShift = this._oCurrentShiftContext.getModel().getObject(this._oCurrentShiftContext.getPath());
			oModel.createObject("rest/shifts/assign/" + oCurrentShift.guid + "/schedule/" + this._sScheduleId + "/publisher/" + oObj.guid,
					{},
					"POST",
					this._oCurrentShiftContext.getPath(), false).then(function(){
						var oAssignedPublishers = sap.ui.getCore().byId("addPubToShift--assignedPublishers");
						oAssignedPublishers.removeSelections();
						
						this.getView().getModel().setProperty("/AssignedToShiftTemp", []);						
						oModel.fetchData("rest/shifts/assignableToShift/" + oCurrentShift.guid, "/AssignedToShiftTemp", true, {}, true);
					}.bind(this));
		},
		
		selectPublisherForAdd : function(oEvent) {
			var oShiftContext = oEvent.getSource().getBindingContext();
			this._oCurrentShiftContext = oShiftContext;
			if(!this._oAssignToShiftDialog) {
				this._oAssignToShiftDialog = sap.ui.xmlfragment("addPubToShift", "org.eae.tools.view.fragments.AddPublisherToShift", this);
				this.getView().addDependent(this._oAssignToShiftDialog);	
			}
			this._oAssignToShiftDialog.setBindingContext(oShiftContext);
			this._oAssignToShiftDialog.open();
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
			
			oModel.post("rest/shifts/unassign/" + oShiftObj.guid + "/schedule/" + this._sScheduleId + "/publisher/" + oPublisherObj.publisher.guid,
					"POST",
					"").then(function(aResults){
						var oUpdatedShift = aResults.objects[0]; 
						this.shiftBinding.getModel().setProperty(this.shiftBinding.getPath(), oUpdatedShift);
						
					}.bind(oShift));
		},
		
		onCloseAssignPublishersPress : function (oEvent) {
			this._oAssignToShiftDialog.close();
		}
		
	});
});