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
			
			console.log("Component.init");
			UIComponent.prototype.init.apply(this, arguments);
			
			var oJsonModel = new EaeModel({
				Schedule : {},
				PublisherData : {},
				Publishers : [],
				Temp : {
					PublisherEdit : {}
				},
				ui: {
					createPeriod : {
						cartPoint : {
							guid:""
						},
						firstShiftStarts : "07:00"
						
					},
					createShift : {
						serviceDayId : ""
					},
					createPublisher : {
						serviceDayId : ""
					},
					createCartSchedule : {
						period : {
							guid : ""
						},
						cart : {
							guid : ""
						}
					},
					createCartLocation : {
						
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
			
		},
		
		onBeforeRendering : function() {
		},
		
		readCurrentUserInfo : function() {
			var oModel = this.getModel();
			oModel.read("rest/landing").then(function(oData){
				this.getModel().setProperty("/PublisherData/Publisher",oData.publisher);
				this.getModel().setProperty("/PublisherData/Period",oData.currentPeriod);
				this.getModel().setProperty("/PublisherData/SharedSchedules",oData.sharedSchedules);
				oModel.setProperty("/Temp/PublisherEdit/",oData.publisher);
				sap.ui.core.BusyIndicator.hide();
				if(oData.publisher.name != "eae" && (oData.publisher.consent == null || oData.publisher.consent.status == 'NO')) {
					//this.getRouter().navTo("myConsents");
				}
			}.bind(this)).catch(function(data){
				sap.ui.core.BusyIndicator.hide();
				if(data.status === 401) {
					this.getRouter().navTo("login");
				}
			}.bind(this));
		}
	});
});