jQuery.sap.registerPreloadedModules({
	"name": "org.eae.tools.Component-preload",
	"version": "2.0",
	"modules": {
		"org/eae/tools/Component-preload.js": "jQuery.sap.registerPreloadedModules({name:\"org.eae.tools.Component-preload\",version:\"2.0\",modules:{\"org/eae/tools/Component-preload.js\":'jQuery.sap.registerPreloadedModules({name:\"org.eae.tools.Component-preload\",version:\"2.0\",modules:{\"org/eae/tools/Component-preload.js\":\\'jQuery.sap.registerPreloadedModules({name:\"org.eae.tools.Component-preload\",version:\"2.0\",modules:{\"org/eae/tools/Component-preload.js\":\\\\\\'jQuery.sap.registerPreloadedModules({name:\"org.eae.tools.Component-preload\",version:\"2.0\",modules:{\"org/eae/tools/Component-preload.js\":\\\\\\\\\\\\\\'jQuery.sap.registerPreloadedModules({name:\"org.eae.tools.Component-preload\",version:\"2.0\",modules:{\"org/eae/tools/Component-preload.js\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\'jQuery.sap.registerPreloadedModules({name:\"org.eae.tools.Component-preload\",version:\"2.0\",modules:{\"org/eae/tools/Component-preload.js\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\'jQuery.sap.registerPreloadedModules({name:\"org.eae.tools.Component-preload\",version:\"2.0\",modules:{\"org/eae/tools/Component.js\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\'sap.ui.define([\"sap/ui/core/UIComponent\",\"sap/ui/model/resource/ResourceModel\",\"sap/ui/model/json/JSONModel\",\"org/eae/tools/common/EaeModel\"],function(e,t,o,i){return e.extend(\"org.eae.tools.Component\",{metadata:{manifest:\"json\"},init:function(){e.prototype.init.apply(this,arguments);var t=new i({Schedule:{},PublisherData:{},Publishers:[],ui:{createPeriod:{cartPoint:{guid:\"\"}},createShift:{serviceDayId:\"\"},createPublisher:{serviceDayId:\"\"},createCartSchedule:{period:{guid:\"\"},cart:{guid:\"\"}},createCartLocation:{}}});this.setModel(t),this.getRouter().initialize(),t.attachRequestFailed(function(e){console.log(e),401===e.getParameter(\"statusCode\")?this.getRouter().navTo(\"login\"):403===e.getParameter(\"statusCode\")&&sap.m.MessageToast.show(e.getParameter(\"message\"))}.bind(this))},onBeforeRendering:function(){},readCurrentUserInfo:function(){var e=this.getModel();e.read(\"rest/landing\").then(function(e){this.getModel().setProperty(\"/PublisherData/Publisher\",e.publisher),this.getModel().setProperty(\"/PublisherData/Period\",e.currentPeriod),this.getModel().setProperty(\"/PublisherData/SharedSchedules\",e.sharedSchedules)}.bind(this))[\"catch\"](function(e){401===e.status&&this.getRouter().navTo(\"login\")}.bind(this))}})});\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\'}});\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\',\"org/eae/tools/Component.js\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\'sap.ui.define([\"sap/ui/core/UIComponent\",\"sap/ui/model/resource/ResourceModel\",\"sap/ui/model/json/JSONModel\",\"org/eae/tools/common/EaeModel\"],function(e,t,i,o){return e.extend(\"org.eae.tools.Component\",{metadata:{manifest:\"json\"},init:function(){e.prototype.init.apply(this,arguments);var t=new o({Schedule:{},PublisherData:{},Publishers:[],ui:{createPeriod:{cartPoint:{guid:\"\"},firstShiftStarts:\"07:00\"},createShift:{serviceDayId:\"\"},createPublisher:{serviceDayId:\"\"},createCartSchedule:{period:{guid:\"\"},cart:{guid:\"\"}},createCartLocation:{}}});this.setModel(t),this.getRouter().initialize(),t.attachRequestFailed(function(e){console.log(e),401===e.getParameter(\"statusCode\")?this.getRouter().navTo(\"login\"):403===e.getParameter(\"statusCode\")&&sap.m.MessageToast.show(e.getParameter(\"message\"))}.bind(this))},onBeforeRendering:function(){},readCurrentUserInfo:function(){var e=this.getModel();e.read(\"rest/landing\").then(function(e){this.getModel().setProperty(\"/PublisherData/Publisher\",e.publisher),this.getModel().setProperty(\"/PublisherData/Period\",e.currentPeriod),this.getModel().setProperty(\"/PublisherData/SharedSchedules\",e.sharedSchedules)}.bind(this))[\"catch\"](function(e){401===e.status&&this.getRouter().navTo(\"login\")}.bind(this))}})});\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\'}});\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\',\"org/eae/tools/Component.js\":\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\'sap.ui.define([\"sap/ui/core/UIComponent\",\"sap/ui/model/resource/ResourceModel\",\"sap/ui/model/json/JSONModel\",\"org/eae/tools/common/EaeModel\"],function(e,t,i,o){return e.extend(\"org.eae.tools.Component\",{metadata:{manifest:\"json\"},init:function(){e.prototype.init.apply(this,arguments);var t=new o({Schedule:{},PublisherData:{},Publishers:[],ui:{createPeriod:{cartPoint:{guid:\"\"},firstShiftStarts:\"07:00\"},createShift:{serviceDayId:\"\"},createPublisher:{serviceDayId:\"\"},createCartSchedule:{period:{guid:\"\"},cart:{guid:\"\"}},createCartLocation:{}}});this.setModel(t),this.getRouter().initialize(),t.attachRequestFailed(function(e){console.log(e),401===e.getParameter(\"statusCode\")?this.getRouter().navTo(\"login\"):403===e.getParameter(\"statusCode\")&&sap.m.MessageToast.show(e.getParameter(\"message\"))}.bind(this))},onBeforeRendering:function(){},readCurrentUserInfo:function(){var e=this.getModel();e.read(\"rest/landing\").then(function(e){this.getModel().setProperty(\"/PublisherData/Publisher\",e.publisher),this.getModel().setProperty(\"/PublisherData/Period\",e.currentPeriod),this.getModel().setProperty(\"/PublisherData/SharedSchedules\",e.sharedSchedules)}.bind(this))[\"catch\"](function(e){401===e.status&&this.getRouter().navTo(\"login\")}.bind(this))}})});\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\'}});\\\\\\\\\\\\\\',\"org/eae/tools/Component.js\":\\\\\\\\\\\\\\'sap.ui.define([\"sap/ui/core/UIComponent\",\"sap/ui/model/resource/ResourceModel\",\"sap/ui/model/json/JSONModel\",\"org/eae/tools/common/EaeModel\"],function(e,t,i,o){return e.extend(\"org.eae.tools.Component\",{metadata:{manifest:\"json\"},init:function(){e.prototype.init.apply(this,arguments);var t=new o({Schedule:{},PublisherData:{},Publishers:[],ui:{createPeriod:{cartPoint:{guid:\"\"},firstShiftStarts:\"07:00\"},createShift:{serviceDayId:\"\"},createPublisher:{serviceDayId:\"\"},createCartSchedule:{period:{guid:\"\"},cart:{guid:\"\"}},createCartLocation:{}}});this.setModel(t),this.getRouter().initialize(),t.attachRequestFailed(function(e){console.log(e),401===e.getParameter(\"statusCode\")?this.getRouter().navTo(\"login\"):403===e.getParameter(\"statusCode\")&&sap.m.MessageToast.show(e.getParameter(\"message\"))}.bind(this))},onBeforeRendering:function(){},readCurrentUserInfo:function(){var e=this.getModel();e.read(\"rest/landing\").then(function(e){this.getModel().setProperty(\"/PublisherData/Publisher\",e.publisher),this.getModel().setProperty(\"/PublisherData/Period\",e.currentPeriod),this.getModel().setProperty(\"/PublisherData/SharedSchedules\",e.sharedSchedules)}.bind(this))[\"catch\"](function(e){401===e.status&&this.getRouter().navTo(\"login\")}.bind(this))}})});\\\\\\\\\\\\\\'}});\\\\\\',\"org/eae/tools/Component.js\":\\\\\\'sap.ui.define([\"sap/ui/core/UIComponent\",\"sap/ui/model/resource/ResourceModel\",\"sap/ui/model/json/JSONModel\",\"org/eae/tools/common/EaeModel\"],function(e,t,i,o){return e.extend(\"org.eae.tools.Component\",{metadata:{manifest:\"json\"},init:function(){e.prototype.init.apply(this,arguments);var t=new o({Schedule:{},PublisherData:{},Publishers:[],ui:{createPeriod:{cartPoint:{guid:\"\"},firstShiftStarts:\"07:00\"},createShift:{serviceDayId:\"\"},createPublisher:{serviceDayId:\"\"},createCartSchedule:{period:{guid:\"\"},cart:{guid:\"\"}},createCartLocation:{}}});this.setModel(t),this.getRouter().initialize(),t.attachRequestFailed(function(e){console.log(e),401===e.getParameter(\"statusCode\")?this.getRouter().navTo(\"login\"):403===e.getParameter(\"statusCode\")&&sap.m.MessageToast.show(e.getParameter(\"message\"))}.bind(this))},onBeforeRendering:function(){},readCurrentUserInfo:function(){var e=this.getModel();e.read(\"rest/landing\").then(function(e){this.getModel().setProperty(\"/PublisherData/Publisher\",e.publisher),this.getModel().setProperty(\"/PublisherData/Period\",e.currentPeriod),this.getModel().setProperty(\"/PublisherData/SharedSchedules\",e.sharedSchedules)}.bind(this))[\"catch\"](function(e){401===e.status&&this.getRouter().navTo(\"login\")}.bind(this))}})});\\\\\\'}});\\',\"org/eae/tools/Component.js\":\\'sap.ui.define([\"sap/ui/core/UIComponent\",\"sap/ui/model/resource/ResourceModel\",\"sap/ui/model/json/JSONModel\",\"org/eae/tools/common/EaeModel\"],function(e,t,i,o){return e.extend(\"org.eae.tools.Component\",{metadata:{manifest:\"json\"},init:function(){e.prototype.init.apply(this,arguments);var t=new o({Schedule:{},PublisherData:{},Publishers:[],ui:{createPeriod:{cartPoint:{guid:\"\"},firstShiftStarts:\"07:00\"},createShift:{serviceDayId:\"\"},createPublisher:{serviceDayId:\"\"},createCartSchedule:{period:{guid:\"\"},cart:{guid:\"\"}},createCartLocation:{}}});this.setModel(t),this.getRouter().initialize(),t.attachRequestFailed(function(e){console.log(e),401===e.getParameter(\"statusCode\")?this.getRouter().navTo(\"login\"):403===e.getParameter(\"statusCode\")&&sap.m.MessageToast.show(e.getParameter(\"message\"))}.bind(this))},onBeforeRendering:function(){},readCurrentUserInfo:function(){var e=this.getModel();e.read(\"rest/landing\").then(function(e){this.getModel().setProperty(\"/PublisherData/Publisher\",e.publisher),this.getModel().setProperty(\"/PublisherData/Period\",e.currentPeriod),this.getModel().setProperty(\"/PublisherData/SharedSchedules\",e.sharedSchedules)}.bind(this))[\"catch\"](function(e){401===e.status&&this.getRouter().navTo(\"login\")}.bind(this))}})});\\'}});',\"org/eae/tools/Component.js\":'sap.ui.define([\"sap/ui/core/UIComponent\",\"sap/ui/model/resource/ResourceModel\",\"sap/ui/model/json/JSONModel\",\"org/eae/tools/common/EaeModel\"],function(e,t,i,o){return e.extend(\"org.eae.tools.Component\",{metadata:{manifest:\"json\"},init:function(){e.prototype.init.apply(this,arguments);var t=new o({Schedule:{},PublisherData:{},Publishers:[],ui:{createPeriod:{cartPoint:{guid:\"\"},firstShiftStarts:\"07:00\"},createShift:{serviceDayId:\"\"},createPublisher:{serviceDayId:\"\"},createCartSchedule:{period:{guid:\"\"},cart:{guid:\"\"}},createCartLocation:{}}});this.setModel(t),this.getRouter().initialize(),t.attachRequestFailed(function(e){console.log(e),401===e.getParameter(\"statusCode\")?this.getRouter().navTo(\"login\"):403===e.getParameter(\"statusCode\")&&sap.m.MessageToast.show(e.getParameter(\"message\"))}.bind(this))},onBeforeRendering:function(){},readCurrentUserInfo:function(){var e=this.getModel();e.read(\"rest/landing\").then(function(e){this.getModel().setProperty(\"/PublisherData/Publisher\",e.publisher),this.getModel().setProperty(\"/PublisherData/Period\",e.currentPeriod),this.getModel().setProperty(\"/PublisherData/SharedSchedules\",e.sharedSchedules)}.bind(this))[\"catch\"](function(e){401===e.status&&this.getRouter().navTo(\"login\")}.bind(this))}})});'}});",
		"org/eae/tools/Component.js": "sap.ui.define([\"sap/ui/core/UIComponent\",\"sap/ui/model/resource/ResourceModel\",\"sap/ui/model/json/JSONModel\",\"org/eae/tools/common/EaeModel\"],function(e,t,i,r){return e.extend(\"org.eae.tools.Component\",{metadata:{manifest:\"json\"},init:function(){e.prototype.init.apply(this,arguments);var t=new r({Schedule:{},PublisherData:{},Publishers:[],Temp:{PublisherEdit:{}},ui:{createPeriod:{cartPoint:{guid:\"\"},firstShiftStarts:\"07:00\"},createShift:{serviceDayId:\"\"},createPublisher:{serviceDayId:\"\"},createCartSchedule:{period:{guid:\"\"},cart:{guid:\"\"}},createCartLocation:{}}});this.setModel(t),this.getRouter().initialize(),t.attachRequestFailed(function(e){console.log(e),401===e.getParameter(\"statusCode\")?this.getRouter().navTo(\"login\"):403===e.getParameter(\"statusCode\")&&sap.m.MessageToast.show(e.getParameter(\"message\"))}.bind(this))},onBeforeRendering:function(){},readCurrentUserInfo:function(){var e=this.getModel();e.read(\"rest/landing\").then(function(e){this.getModel().setProperty(\"/PublisherData/Publisher\",e.publisher),this.getModel().setProperty(\"/PublisherData/Period\",e.currentPeriod),this.getModel().setProperty(\"/PublisherData/SharedSchedules\",e.sharedSchedules)}.bind(this))[\"catch\"](function(e){401===e.status&&this.getRouter().navTo(\"login\")}.bind(this))}})});"
	}
});