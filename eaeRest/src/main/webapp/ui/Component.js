sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/model/resource/ResourceModel",
	"sap/ui/model/json/JSONModel",
	"org/eae/tools/common/EaeModel"
], function(UIComponent,ResourceModel, JSONModel, EaeModel) {
	return UIComponent.extend("org.eae.tools.Component", {
        metadata : {
        	manifest: "json",
    	},
		
		init : function(){
			UIComponent.prototype.init.apply(this, arguments);
			
			var oJsonModel = new EaeModel({
				Schedule : {},
				Publisher : {},
				Publishers : [],
				ui: {
					createPeriod : {
						name:"",
						
					},
					createShift : {
						serviceDayId : ""
					},
					createPublisher : {
						serviceDayId : ""
					}
					
				}

			});
			
			this.setModel(oJsonModel);
			this.getRouter().initialize();
			oJsonModel.attachRequestFailed(function(oError){
				console.log(oError);
				if(oError.getParameter("statusCode") === 401) {
					this.getRouter().navTo("login");	
				} else if(oError.getParameter("statusCode") === 403) {
					sap.m.MessageToast.show(oError.getParameter("message"));
				}
				
			}.bind(this));
			
		}
	});
});