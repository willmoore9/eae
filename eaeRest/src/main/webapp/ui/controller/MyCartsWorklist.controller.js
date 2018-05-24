sap.ui.define([
    "sap/ui/core/mvc/Controller",
], function(Controller){
	return Controller.extend("org.eae.tools.controller.MyCartsWorklist", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("myCartPlaceWorklist").attachPatternMatched(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		refreshTable : function() {
			this.getView().getModel().fetchData("rest/cartLocations", "/CartLocations", true);
			sap.ui.core.BusyIndicator.hide();
		},
		

	});
});