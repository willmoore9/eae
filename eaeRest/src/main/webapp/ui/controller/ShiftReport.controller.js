sap.ui.define([
    "sap/ui/core/mvc/Controller",
], function(Controller){
	return Controller.extend("org.eae.tools.controller.ShiftReport", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("shiftReport").attachPatternMatched(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		
		refreshTable : function() {
			this.getView().getModel().fetchData("rest/placements", "/Placements", true);
			var shiftGuid = "";
			var cartGuid = "";
			this.getView().getModel().fetchData("rest/reportPlacements/shift/" + shiftGuid + "/cart/" + cartGuid, 
					"/reportPlacements/" + shiftGuid + "/cart/" + cartGuid, true);
			sap.ui.core.BusyIndicator.hide();
		},
		

	});
});