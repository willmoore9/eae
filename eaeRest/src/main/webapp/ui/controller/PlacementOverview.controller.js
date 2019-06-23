sap.ui.define([
	"sap/ui/core/mvc/Controller",
], function(Controller){
	return Controller.extend("org.eae.tools.controller.PlacementOverview", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("placementOverview").attachPatternMatched(function(oEvent){
//				var objectPage = this.getView().byId("pubProfilePage");
				var placementId = oEvent.getParameter("arguments").placementId;
				this._placementId = placementId;
				this.getView().getModel().fetchData("rest/publicationLangs", "/PublicationLanguages", true);
				var model = this.getView().getModel();
				var promise = model.read("rest/placements/read/" + this._placementId).then(function(data) {
					model.setProperty("/Placements/Placement(" + this._placementId + ")", data.objects[0]);
					console.log(data);
					this.getView().byId("mainInfoSection").bindElement("/Placements/Placement(" + this._placementId + ")");	
				}.bind(this)).catch(function(error) {
					console.log("Error during placement loadubg" + this._placementId);
				});
			}.bind(this));
		},

		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("placements");
			
		},
		onAddPlacementTitle : function(oEvent) {
			var model = this.getView().getModel();
			if(!model.getProperty("/PublicationLanguages")) {
				model.fetchData("rest/publicationLangs", "/PublicationLanguages", true);	
			}
			
			this._initEmptyPlacementTitle();
			if(!this._oNewPlacementTitle) {
				this._oNewPlacementTitle = sap.ui.xmlfragment("createPlacementTitle", "org.eae.tools.view.fragments.AddPlacementTitle", this);
				this.getView().addDependent(this._oNewPlacementTitle);	
			}
			this._oNewPlacementTitle.open();
		},
		
		_initEmptyPlacementTitle : function() {
			this.getView().getModel().setProperty("/ui/createPlacementTitle", {});
			this.getView().getModel().setProperty("/ui/createPlacementTitle/language", {});
			this.getView().getModel().setProperty("/ui/createPlacementTitle/language/guid", {});
		},
		
		onCancelCreatePlacemenntTitleRecord : function (oEvent) {
			this._initEmptyPlacementTitle();
			this._oNewPlacementTitle.close();
		},
		
		onCreatePlacementTitleRecord : function (oEvent) {
			var oModel = this.getView().getModel();
			var oParams = oModel.getProperty("/ui/createPlacementTitle");
			oModel.post("rest/placements/update/"+this._placementId+"/title" ,"POST", JSON.stringify(oParams)).then(function(data) {
				oModel.setProperty("/Placements/Placement(" + data.object.guid + ")", data.object);
				this._oNewPlacementTitle.close();
			}.bind(this)).catch(function(error) {
				console.log(error.staustText + " " +error.stutus);
			});
		},
		
		onDeletePlacementTitle : function(oEvent)  {
			var oModel = this.getView().getModel();
			var titleTables = this.getView().byId("titlesTable");
			var oContext = titleTables.getSelectedContexts()[0];
			var titleId = oContext.getObject().guid;
			oModel.post("rest/placements/delete/"+this._placementId+"/title/" + titleId ,"DELETE", "").then(function(data) {
				oModel.setProperty("/Placements/Placement(" + data.object.guid + ")", data.object);
				this._oNewPlacementTitle.close();
			}.bind(this)).catch(function(error) {
				console.log(error.staustText + " " +error.stutus);
			});			
		},
		
		

	});
});