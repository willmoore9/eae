sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/core/format/DateFormat"
], function(Controller, DateFormat){
	return Controller.extend("org.eae.tools.controller.PublicationLanguagesWorklist", {
		onInit : function(){
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.getRoute("publicationLangs").attachPatternMatched(function(){
				this.refreshTable();
			}.bind(this));
		},
		
		onNavBack : function(oEvent) {
			var oRouter = this.getOwnerComponent().getRouter();
			oRouter.navTo("landingPage");
		},
		
		refreshTable : function() {
			this.getView().getModel().fetchData("rest/publicationLangs", "/PublicationLanguages", true);
			sap.ui.core.BusyIndicator.hide();
		},		
		
		onAddLanguage : function(oEvent) {
			var oCreateData = this.getView().getModel().setProperty("/ui/createLanguage", {});
			if(!this._oNewLanguage) {
				this._oNewLanguage = sap.ui.xmlfragment("createPublisher", "org.eae.tools.view.fragments.AddLanguage", this);
				this.getView().addDependent(this._oNewLanguage);	
			}
			this._oNewLanguage.open();
		},
		
		handleUpdateBinding : function(oEvent) {
			var oTableHeader = this.getView().byId("tableHeader");
			var oPublishersText = this.getView().getModel("i18n").getProperty("language_plural");
			var iLangCount = oEvent.getSource().getBinding("items").getLength();
			
			oTableHeader.setText(oPublishersText + "(" + iLangCount + ")");
		},

		onCreateLanguageRecord : function(oEvent) {
			var oModel = this.getView().getModel();
			var oParams = oModel.getProperty("/ui/createLanguage");
			oModel.createObject("rest/publicationLangs/create/",
					JSON.stringify(oParams),
					"POST",
					"/PublicationLanguages", 
					true
			).then(function(){
				console.log("Created Language. Do nothing");
				this._oNewLanguage.close();
			}.bind(this));
			
			var oCreateData = this.getView().getModel().setProperty("/ui/createLanguage", {});

		},
		
		onCancelCreateLanguageRecord : function(oEvent) {
			this._oNewLanguage.close();
		}
	});
});