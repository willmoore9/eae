sap.ui.define([
     "sap/ui/core/mvc/Controller",
     "sap/m/Page"
], function(Controller){
	"use strict";
	return Controller.extend("org.eae.tools.controller.App", {
		init: function() {
			console.log("Landing init");
		},
		
		navigateToTeamCalendar : function() {
			console.log("navigateToTeamCalendar");
			
			var oPlannerView = sap.ui.xmlview("org.eae.tools.view.PlannerView");
			
			var oNav = this.getView().byId("eaeNav");
			oNav.addPage(oPlannerView);
			oNav.to(oPlannerView.getId());
		}
	});
});