sap.ui.define([
"sap/ui/model/json/JSONModel",	
], function(JSONModel){
	return JSONModel.extend("org.eae.tools.common.EaeModel", {

		login : function(user, pass){
			var oPromise = new Promise(function(resolve, reject){
				var fnSuccess = function(oData) {
					resolve(oData);
				}.bind(this);

				var fnError = function(oParams){
					var oError = { message : oParams.statusText, statusCode : oParams.status};
					jQuery.sap.log.fatal("The following problem occurred: " + oParams.statusText + " - " + oParams.status);
					this.fireRequestFailed(oError);
					reject();
				}.bind(this);
				
				var _loadData = function(fnSuccess, fnError) {
					this._ajax({
						url: 'rest/login',
						async: true,
						cache: false,
						data: {
							username:user,
							password:pass,	
						},

					    type: "POST",
						success: fnSuccess,
						error: fnError
					});
				}.bind(this);
					_loadData(fnSuccess, fnError);
				}.bind(this));
				
				return oPromise;
			
			
		},
		fetchData : function(sURL, sPathToMergeResult, bMerge, oParameters, bAsync, sType, bCache, mHeaders){
			var pImportCompleted;

			bAsync = (bAsync !== false);
			sType = sType || "GET";
			bCache = bCache === undefined ? this.bCache : bCache;

			this.fireRequestSent({url : sURL, type : sType, async : bAsync, headers: mHeaders,
				info : "cache=" + bCache + ";bMerge=" + bMerge, infoObject: {cache : bCache, merge : bMerge}});

			var fnSuccess = function(oData) {
				if (!oData) {
					jQuery.sap.log.fatal("The following problem occurred: No data was retrieved by service: " + sURL);
				}
				var object = {};
//				object[sPathToMergeResult] = oData.objects;
//				this.setData(object, bMerge);
				this.setProperty(sPathToMergeResult, oData.objects);
				this.fireRequestCompleted({url : sURL, type : sType, async : bAsync, headers: mHeaders,
					info : "cache=" + bCache + ";bMerge=" + bMerge, infoObject: {cache : bCache, merge : bMerge}, success: true});
			}.bind(this);

			var fnError = function(oParams){
				var oError = { message : oParams.request.statusText, statusCode : oParams.request.status};
				jQuery.sap.log.fatal("The following problem occurred: " + oParams.textStatus, oParams.request.responseText + ","
							+ oParams.request.status + "," + oParams.request.statusText);
				
				this.fireRequestCompleted({url : sURL, type : sType, async : bAsync, headers: mHeaders,
					info : "cache=" + bCache + ";bMerge=" + bMerge, infoObject: {cache : bCache, merge : bMerge}, success: false, errorobject: oError});
				this.fireRequestFailed(oError);
			}.bind(this);

			var _loadData = function(fnSuccess, fnError) {
				this._ajax({
					url: sURL,
					async: bAsync,
					dataType: 'json',
					cache: bCache,
					data: oParameters,
					type: sType,
					success: fnSuccess,
					error: fnError
				});
			}.bind(this);

			if (bAsync) {
				pImportCompleted = new Promise(function(resolve, reject) {
					var fnReject =  function(oXMLHttpRequest, sTextStatus, oError) {
						reject({request: oXMLHttpRequest, textStatus: sTextStatus, error: oError});
					};
					_loadData(resolve, fnReject);
				});

//				this.pSequentialImportCompleted = this.pSequentialImportCompleted.then(function() {
					//must always resolve
					return pImportCompleted.then(fnSuccess, fnError).catch(function() {});
//				});
			} else {
				_loadData(fnSuccess, fnError);
			}
		},
		
		removeById : function(sUrl, id) {
			var oPromise = new Promise(function(resolve, reject){
				var fnSuccess = function(oData) {
					if (!oData) {
						jQuery.sap.log.fatal("The following problem occurred: No data was retrieved by service: " + sURL);
					}
					resolve(oData);
				}.bind(this);

				var fnError = function(oParams){
					var oError = { message : oParams.statusText, statusCode : oParams.status};
					jQuery.sap.log.fatal("The following problem occurred: " + oParams.statusText + " - " + oParams.status, oParams.responseText);
					this.fireRequestFailed(oError);
					reject();
				}.bind(this);
				
				this._ajax({
				   headers: { 
				        'Accept': 'application/json',
				        'Content-Type': 'application/json',
				    },
					url : sUrl,
					type:"DELETE",
					dataType: 'json',
					success: fnSuccess,
					error: fnError
				});

			}.bind(this));
			
			return oPromise;
		},
		
		createObject : function(sURL, oParameters, sType,  sPathToMergeResult, isArray) {
			var oPromise = new Promise(function(resolve, reject){
			var fnSuccess = function(oData) {
				if (!oData) {
					jQuery.sap.log.fatal("The following problem occurred: No data was retrieved by service: " + sURL);
				}
				var object = {};
//				object[sPathToMergeResult] = oData.objects;
//				this.setData(object, bMerge);
				if(isArray) {
					var oArray = this.getProperty(sPathToMergeResult);
					oArray.push(oData.objects[0]);
					this.setProperty(sPathToMergeResult, oArray);
				} else {
					this.setProperty(sPathToMergeResult, oData.objects.length == 1 ?oData.objects[0] : oData.objects);	
				}
				resolve();
			}.bind(this);

			var fnError = function(oParams){
				var oError = { message : oParams.statusText, statusCode : oParams.status};
				if(oError.statusCode === 401) {
//					fireUnauthorized();
				}
				jQuery.sap.log.fatal("The following problem occurred: " + oParams.statusText, oParams.status + ","
							+ oParams.responseText);
				this.fireRequestFailed(oError);
				reject();
			}.bind(this);
			var _loadData = function(fnSuccess, fnError) {
				this._ajax({
					url: sURL,
					async: true,
					dataType: 'json',
					cache: false,
					data: oParameters,
					headers: { 
				        'Accept': 'application/json',
				        'Content-Type': 'application/json'
				    },
					type: sType,
					success: fnSuccess,
					error: fnError
				});
			}.bind(this);
			
			_loadData(fnSuccess, fnError);
			}.bind(this));
			
			return oPromise;
		},
		
		read : function(sURL) {
			var oPromise = new Promise(function(fnSuccess, fnError) {
				this._ajax({
					url: sURL,
					async: true,
					dataType: 'json',
					cache: false,
					headers: { 
				        'Accept': 'application/json',
				        'Content-Type': 'application/json'
				    },
					type: "GET",
					success: fnSuccess,
					error: fnError
				});				
			}.bind(this));
			return oPromise;
		},
		
		post : function(sURL, sType, oParams) {
			var oPromise = new Promise(function(fnSuccess, fnError) {
				this._ajax({
					url: sURL,
					async: true,
					dataType: 'json',
					cache: false,
					headers: { 
				        'Accept': 'application/json',
				        'Content-Type': 'application/json'
				    },
					type: sType,
					data: oParams,
					success: fnSuccess,
					error: fnError
				});				
			}.bind(this));
			return oPromise;
		}
	})
}, true);