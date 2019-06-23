sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/core/format/DateFormat"
], function(Controller, DateFormat){
	return Controller.extend("org.eae.tools.controller.PlacementsWorklist", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("placements").attachPatternMatched(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		refreshTable : function() {
			this.getView().getModel().fetchData("rest/placements", "/Placements", true);
			sap.ui.core.BusyIndicator.hide();
		},		
		
		onAddPlacement : function(oEvent) {
			this.getView().getModel().fetchData("rest/publicationLangs", "/PublicationLanguages", true);
			this._initEmptyPlacement();
			if(!this._oNewPlacement) {
				this._oNewPlacement = sap.ui.xmlfragment("createPublisher", "org.eae.tools.view.fragments.AddPlacement", this);
				this.getView().addDependent(this._oNewPlacement);	
			}
			this._oNewPlacement.open();
		},
		
		handleUpdateBinding : function(oEvent) {
			var oTableHeader = this.getView().byId("tableHeader");
			var oPublishersText = this.getView().getModel("i18n").getProperty("placement_plural");
			var iPlacementsCount = oEvent.getSource().getBinding("items").getLength();
			
			oTableHeader.setText(oPublishersText + "(" + iPlacementsCount + ")");
		},

		onCreatePlacementRecord : function(oEvent) {
			var oModel = this.getView().getModel();
			var oParams = oModel.getProperty("/ui/createPlacement");
			oModel.createObject("rest/placements/create/",
					JSON.stringify(oParams),
					"POST",
					"/Placements", 
					true
			).then(function(){
				console.log("Created Language. Do nothing");
				this._oNewPlacement.close();
			}.bind(this));
			
			this._initEmptyPlacement();

		},
		
		onCancelCreatePlacementRecord : function(oEvent) {
			this._oNewPlacement.close();
		},
		
		handleDeletePlacement : function(oEvent) {
			var guidToDelete = oEvent.getParameter("listItem").getBindingContext().getProperty("guid");
			var oModel = this.getView().getModel();
			oModel.removeById("rest/placements/delete/" + guidToDelete).then(function(){
				this.refreshTable();
			}.bind(this))	
		},
		
		_initEmptyPlacement : function() {
			this.getView().getModel().setProperty("/ui/createPlacement", {});
			this.getView().getModel().setProperty("/ui/createPlacement/language", {});
		},
		
		onNavigateToPlacement : function (oEvent) {
			var oPlacement = oEvent.getParameter("srcControl").getBindingContext().getObject();
			oPlacement.guid
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("placementOverview", {
				placementId : oPlacement.guid
			});
		}
	});
});