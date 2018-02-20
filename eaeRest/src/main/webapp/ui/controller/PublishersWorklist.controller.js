sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/core/format/DateFormat"
], function(Controller, DateFormat){
	return Controller.extend("org.eae.tools.controller.PublishersWorklist", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("overviewPublishers").attachPatternMatched(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		onAddPublisherPress : function(oEvent) {
			if(!this._oNewPublisher) {
				this._oNewPublisher = sap.ui.xmlfragment("createPublisher", "org.eae.tools.view.fragments.AddPublisher", this);
				this.getView().addDependent(this._oNewPublisher);	
			}
			

			this._oNewPublisher.open();
		},
		
		
		onCreatePublisherPress : function() {
			var oModel = this.getView().getModel();
			var oParams = oModel.getProperty("/ui/createPublisher");
			oModel.createObject("rest/publishers/create/",
					JSON.stringify(oParams),
					"POST",
					"/Publishers", 
					true
			).then(function(){
				console.log("Created publishe. Do nothing");
			}.bind(this));
			
			this._oNewPublisher.close();
			var oCreateData = this.getView().getModel().setProperty("/ui/createPublisher", {});
		},
		
		onCancelCreatePublisherPress : function() {
			this._oNewPublisher.close();
		},
		
		handleDeletePublisher : function(oEvent) {
			var guidToDelete = oEvent.getParameter("listItem").getBindingContext().getProperty("guid");
			var oModel = this.getView().getModel();
			oModel.removeById("rest/publishers/delete/" + guidToDelete).then(function(){
				this.refreshTable();
			}.bind(this))
		},
		
		refreshTable : function() {
			this.getView().getModel().fetchData("rest/publishers", "/Publishers", true);
		},
		
		onDownloadPublishersPress : function () {
			var oModel = this.getView().getModel();
			sap.m.URLHelper.redirect("rest/publishers/download");
			
		},
		
		onImportPublishersSuccess : function() {
			this.refreshTable();
		},
		
		onSetDefaultPin : function(oEvent) {
			
			var sGuid = oEvent.getSource().getParent().getBindingContext().getProperty("guid");
			var oModel = this.getView().getModel();
			oModel.post("rest/publishers/setDefaultPin/"+ sGuid, "GET");
		} 
	});
});