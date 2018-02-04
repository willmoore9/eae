sap.ui.define([
	"sap/ui/core/mvc/Controller",
], function(Controller){
	return Controller.extend("org.eae.tools.controller.PublisherProfile", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("publisherProfile").attachPatternMatched(function(oEvent){
//				var objectPage = this.getView().byId("pubProfilePage");
				this.getOwnerComponent().readCurrentUserInfo();
			}.bind(this));
		},
		
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
			
			this.onCreatePublisherPress();
		},
		
		onCreatePublisherPress : function() {
			var oModel = this.getView().getModel();
			var oParams = oModel.getProperty("/PublisherData/Publisher");
			oModel.createObject("rest/publishers/update/",
					JSON.stringify(oParams),
					"POST",
					"/Publishers", 
					true
			).then(function(){
				console.log("Created publishe. Do nothing");
			}.bind(this));
			
		},
		
	});
});