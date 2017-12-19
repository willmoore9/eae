sap.ui.define([
    "sap/ui/core/mvc/Controller",
], function(Controller){
	return Controller.extend("org.eae.tools.controller.ScheduleWorklist", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("scheduleWorklist").attachPatternMatched(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		refreshTable : function() {
			this.getView().getModel().fetchData("rest/cartSchedule", "/CartSchedules", true);
		},
		
		onAddSchedule : function(oEvent) {
			if(!this._oAddDialog) {
				this._oAddDialog = sap.ui.xmlfragment("createSchedule", "org.eae.tools.view.fragments.AddScheduleDialog", this);
				this.getView().addDependent(this._oAddDialog);	
			}

			this._oAddDialog.open();
		},
		
		onOkAddDialogPress:function(oEvent) {
			var oModel = this.getView().getModel();
			var oParams = oModel.getProperty("/ui/createCartSchedule");
			oModel.createObject("rest/cartSchedule/create/",
					JSON.stringify(oParams),
					"POST",
					"/CartSchedules", 
					true
			).then(function(){
				console.log("Created publishe. Do nothing");
				var oCreateData = this.getView().getModel().setProperty("/ui/createCartSchedule", {
					period : {
						guid : ""
					},
					cart : {
						guid : ""
					}
				});
			}.bind(this));
			
			this._oAddDialog.close();

		},
		
		onCancelAddDialogPress : function() {
			this._oAddDialog.close();
		},
		
		onBeforeAddScheduleOpen : function() {
			this.getView().getModel().fetchData("rest/periods", "/Periods", true);
			this.getView().getModel().fetchData("rest/cartLocations", "/CartLocations", true);
		},
		
		onDeleteSchedulePress : function() {
			if(!this._oSelected) {
				return;
			}
			
			var oModel = this.getView().getModel();
			oModel.removeById("rest/cartSchedule/delete/" + this._oSelected.getBindingContext().getProperty("guid")).then(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		
		onSelectedSchedule : function(oEvent) {
			var isSelected, oListItem;
			oListItem = oEvent.getParameter("listItem");
			isSelected = oEvent.getParameter("selected");
			
			if(isSelected) {
				this._oSelected = oListItem;
			} else {
				this._oSelected = undefined;
			}
		},
		
		onNavigateToSchedule : function(oEvent) {
			var oItem, oCtx;
			oItem = oEvent.getSource();
			oCtx = oItem.getBindingContext();
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("overviewSchedule",{
				scheduleId : oCtx.getProperty("guid"),
				periodId : oCtx.getProperty("period/guid"),
			});
		}

	});
});