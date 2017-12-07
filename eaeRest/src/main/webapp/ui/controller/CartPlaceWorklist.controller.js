sap.ui.define([
    "sap/ui/core/mvc/Controller",
], function(Controller){
	return Controller.extend("org.eae.tools.controller.CartPlaceWorklist", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("cartPlaceWorklist").attachPatternMatched(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		refreshTable : function() {
			this.getView().getModel().fetchData("rest/cartLocations", "/CartLocations", true);
		},
		
		onAddCartPlace : function(oEvent) {
			if(!this._oDialog) {
				this._oDialog = sap.ui.xmlfragment("createCartLocation", "org.eae.tools.view.fragments.AddCartLocation", this);
				this.getView().addDependent(this._oDialog);	
			}
			

			this._oDialog.open();
		},
		
		handleDeleteCartPlace : function(oEvent) {
			var guidToDelete = oEvent.getParameter("listItem").getBindingContext().getProperty("guid");
			var oModel = this.getView().getModel();
			oModel.removeById("rest/cartLocations/delete/" + guidToDelete).then(function(){
				this.refreshTable();
			}.bind(this))
		},
		
		onCreateCartLocationPress:function(oEvent) {
			var oModel = this.getView().getModel();
			var oParams = oModel.getProperty("/ui/createCartLocation");
			oModel.createObject("rest/cartLocations/create/",
					JSON.stringify(oParams),
					"POST",
					"/CartLocations", 
					true
			).then(function(){
				console.log("Created publishe. Do nothing");
			}.bind(this));
			
			this._oDialog.close();
			var oCreateData = this.getView().getModel().setProperty("/ui/createCartLocation", {});
		},
		
		onCancelCartLocationPress : function() {
			this._oDialog.close();
		}

	});
});