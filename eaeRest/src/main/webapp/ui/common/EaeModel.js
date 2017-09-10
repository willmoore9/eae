sap.ui.define([
"sap/ui/model/json/JSONModel",	
], function(JSONModel){
	return JSONModel.extend("org.eae.tools.common.EaeModel", {
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
				var oError = { message : oParams.textStatus, statusCode : oParams.request.status, statusText : oParams.request.statusText, responseText : oParams.request.responseText};
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
					headers: mHeaders,
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

				this.pSequentialImportCompleted = this.pSequentialImportCompleted.then(function() {
					//must always resolve
					return pImportCompleted.then(fnSuccess, fnError).catch(function() {});
				});
			} else {
				_loadData(fnSuccess, fnError);
			}
		},
		
		removeById : function(sUrl, id) {
			var oPromise = new Promise(function(resolve, reject){
				this._ajax({
				   headers: { 
				        'Accept': 'application/json',
				        'Content-Type': 'application/json' 
				    },
					url : sUrl,
					type:"DELETE",
					dataType: 'json',
					success: resolve,
					error: reject
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
					this.setProperty(sPathToMergeResult, oData.objects);	
				}
				resolve();
//				this.fireRequestCompleted({url : sURL, type : sType, async : bAsync, headers: mHeaders,
//					info : "cache=" + bCache + ";bMerge=" + bMerge, infoObject: {cache : bCache, merge : bMerge}, success: true});
			}.bind(this);

			var fnError = function(oParams){
				var oError = { message : oParams.textStatus, statusCode : oParams.request.status, statusText : oParams.request.statusText, responseText : oParams.request.responseText};
				jQuery.sap.log.fatal("The following problem occurred: " + oParams.textStatus, oParams.request.responseText + ","
							+ oParams.request.status + "," + oParams.request.statusText);

//				this.fireRequestCompleted({url : sURL, type : sType, async : bAsync, headers: mHeaders,
//					info : "cache=" + bCache + ";bMerge=" + bMerge, infoObject: {cache : bCache, merge : bMerge}, success: false, errorobject: oError});
//				this.fireRequestFailed(oError);
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
		}
	})
}, true);