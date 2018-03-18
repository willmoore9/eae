sap.ui.define([
     "sap/ui/core/mvc/Controller",
     "sap/ui/core/format/DateFormat"
], function(Controller, DateFormat){
	"use strict";
	return Controller.extend("org.eae.tools.controller.PeriodsWorlkist", {
		onInit: function() {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("overviewPeriods").attachPatternMatched(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		onAddPeriodPress : function() {
			if(!this._oDialog) {
				this._oDialog = sap.ui.xmlfragment("createPeriod", "org.eae.tools.view.fragments.AddServicePeriod", this);
				this.getView().addDependent(this._oDialog);	
			}
			

			this._oDialog.open();
		},
		
		refreshTable : function() {
			this.getView().getModel().fetchData("rest/periods", "/Periods", true);
			sap.ui.core.BusyIndicator.hide();
		},
		
		_getDatefromTime : function(oDate, sTime) {
			var aTimeArray = sTime.split(":");
			var dateTime = new Date(oDate);
			dateTime.setHours(aTimeArray[0]);
			dateTime.setMinutes(aTimeArray[1]);
			return dateTime.toJSON();
		},	
		
		createNewPeriod : function() {
			var that = this;
			var oModel = this.getView().getModel();
			var oCreateData = that.getView().getModel().getProperty("/ui/createPeriod");
			oCreateData.starts = oCreateData.from.toJSON();
			oCreateData.ends = oCreateData.to.toJSON();
			oCreateData.zoneOffset = new Date().getTimezoneOffset();
			var oPromise = oModel.createObject("rest/periods/create",
					JSON.stringify(oCreateData),
					"POST",
					"", false);
			
//			var oPromise = new Promise(function(fnSuccess, fnReject){
//				var oCreateData = that.getView().getModel().getProperty("/ui/createPeriod");
//				oCreateData.starts = oCreateData.from.toJSON();
//				oCreateData.ends = oCreateData.to.toJSON();
//				oCreateData.timezone = {};
//				$.ajax({
//				   headers: { 
//				        'Accept': 'application/json',
//				        'Content-Type': 'application/json' 
//				    },
//					url : "rest/periods/create",
//					type:"POST",
//					dataType: 'json',
//					data :JSON.stringify(oCreateData),
//					success : function(oData) {
//						fnSuccess({
//							data : oData
//						})
//					}
//				});				
//				
//			});
			return oPromise;
		},
		
		_formatDate : function(iTimestamp) {
			var oDate = new Date(iTimestamp);
			var oDateFormat = DateFormat.getDateInstance({
			    pattern: "E, MMM d, yyyy"
			});
			return oDateFormat.format(oDate);
		},
		
		onCancelCreatePeriodPress : function(){
			this._oDialog.close();
			var oCreateData = this.getView().getModel().setProperty("/ui/createPeriod", {});
		},
		
		onCreatePeriodPress : function() {
			
			this.createNewPeriod().then(function(oData){
				this._oDialog.close();
				var oCreateData = this.getView().getModel().setProperty("/ui/createPeriod", {});
				this.refreshTable();
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		onNavigateToPeriod : function(oEvent) {
			var oItem, oCtx;
			oItem = oEvent.getSource();
			oCtx = oItem.getBindingContext();
			var oRouter = this.getOwnerComponent().getRouter();
			sap.ui.core.BusyIndicator.show(1000);
			oRouter.navTo("overviewPeriod",{
				periodId : oCtx.getProperty("guid")
			});
		},
		
		onSelectedPeriod : function(oEvent) {
			var isSelected, oListItem;
			oListItem = oEvent.getParameter("listItem");
			isSelected = oEvent.getParameter("selected");
			
			if(isSelected) {
				this._oSelected = oListItem;
			} else {
				this._oSelected = undefined;
			}
		},
		
		onDeletePeriodPress : function() {
			if(!this._oSelected) {
				return;
			}
			
			var oModel = this.getView().getModel();
			oModel.removeById("rest/periods/delete/" + this._oSelected.getBindingContext().getProperty("guid")).then(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		beforeOpenAddServicePeriod : function() {
			this.getView().getModel().fetchData("rest/cartLocations", "/CartLocations", true);
		},
		
		onDownloadPeriodPress : function(oEvent) {
			if(!this._oSelected) {
				return;
			}			
			var oModel = this.getView().getModel();
			sap.m.URLHelper.redirect("rest/periods/download/" + this._oSelected.getBindingContext().getProperty("guid"));		
		}
	});
});