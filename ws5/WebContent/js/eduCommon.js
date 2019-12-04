/** 
 * @file 공통 처리용 유틸성 기능을 제공한다.
 * 공통 코드 호출 및 컴포넌트 연동, 통신 메세지 처리 등
 * @version 2.0
 * @author InswaveSystems
 * 
 */

/**
 * 공통 처리용 유틸성 기능을 제공한다.
 * @type com
 * @class com
 * @namespace com
 */
com = {
	CONTEXT_PATH : "/ws5", // Context Path 경로
	DEFAULT_OPTIONS_MODE : "asynchronous", // 기본 통신 모드 ( asynchronous / synchronous)
	DEFAULT_OPTIONS_MEDIATYPE : "application/json", // 기본 미디어 타입
	MESSAGE_CODE : {
		STATUS_ERROR : "E",
		STATUS_SUCCESS : "S",
		STATUS_WARNING : "W"
	},
	_commonCodeInfo : {
		idPrefix:"dlt_CmCode",
		label:"CODE_NM",
		value:"CODE_CD",
		fieldArr : ["GRP_CD","CODE_CD","CODE_NM"] 
	},
	/**
	 * config.xml에서 사용 visibleHelper로 엔진에서 사용. 단독 사용 금지.
	 * @private
	 * @date 2017. 3. 7.
	 * @memberOf com 
	 * @author InswaveSystems
	 */
	_setWebSquareContent : function(htmlStr){
		var contBox = document.getElementById("wsContent") || document.body; 
		contBox.innerHTML = htmlStr;
	},
	/**
	 * 총 행의 수와 화면에 보여질 행의 수를 가지고 페이지수를 반환한다.
	 * @date 2017. 3. 7.
	 * @param {Number} totalRowCount 총 행의 수
	 * @param {Number} rowCount 화면에 보여지는 행의 수
	 * @returns {Number} pageCount
	 * @memberOf com 
	 * @author InswaveSystems
	 * @example
	 * var rs = com.getPageSize(100,10);
	 * //return 예시) 10
	 * var rs2 = com.getPageSize(101,10);
	 * //return 예시) 11
	 */
	getPageSize : function(totalRowCount, rowCount){
		return Math.ceil(totalRowCount/rowCount);
	},
	/**
	 * 공통 코드를 데이터 객체로 생성할 때 사용되는 기본 포맷을 반환한다.
	 * @date 2017. 11. 09.
	 * @returns {JSON} commonCodeFormInfo
	 * @memberOf com 
	 * @author InswaveSystems
	 * @example
	 * var infoJSON = com.getCommonCodeInfo();
	 * //return 예시) {"idPrefix":"dlt_CmCode","label":"CODE_NM","value":"CODE_CD"}
	 */
	getCommonCodeInfo : function(){
		var info = this._commonCodeInfo;
		return {"idPrefix":info.idPrefix, "label":info.label, "value":info.value};
	},
	/**
	 * array형태의 코드 데이타를 key/value 형태의 json 형태로 반환한다.
	 * key는 code값이며 value는 code의 display값이 할당된다.
	 * 이 기능은 공통으로 가져온 code데이타를 기준으로 구현되어있으며 화면에 만들어진 코드를 가지고 생성한다.
	 * 즉, 화면에 코드 데이타가 없는 경우는 생성되지 않음.
	 * 사용법은 example 참조. 
	 * @date 2017. 11. 10.
	 * @param {String} codeIdArrStr 코드값을 ,로 구분한 문자.
	 * @returns {JSON} codeJSON	{"요청 코드값":{"value":"label","value":"label"}}
	 * @memberOf com 
	 * @author InswaveSystems
	 * @example
	 * //code key가 101이고 해당 코드값이 남성-M, 여성-F인 경우
	 * var codeJSON = com.getCodeJSON("101");
	 * //return 예시) {"101":{"M":"남성", "F":"여성"}}
	 * 
	 * //2건의 code를 가져오는 경우
	 * var codeJSON = com.getCodeJSON("101,102");
	 * //return 예시) {"101":{"M":"남성", "F":"여성"}, "102":{"01":"010", "02":"011", "03":"016"}}
	 *
	 */
	getCodeJSON : function(codeIdArrStr){
		if(!codeIdArrStr){
			alert("필수 파라메터가 누락되었습니다.");
			return;
		}
	
		var formJSON = com.getCommonCodeInfo(),
			prefix = formJSON.idPrefix, 
			i=0, codeAll, codeArr, codeArrLen, codeJSON,
			j, idArr = codeIdArrStr.split(","), idArrLen = idArr.length,
			tmpId, tmpJSON, rs={}, errId="";
			
		for(j=0;j<idArrLen;j++){
			idArr[j] = prefix+idArr[j];
		}
		
		codeAll = $p.data.get( "JSON" , idArr );
		
		for(j=0;j<idArrLen;j++){
			tmpId = idArr[j];
			codeArr = codeAll[tmpId];
			if(!codeArr){ 
				errId = errId+tmpId+",";
				continue; 
			}
			codeArrLen = codeArr.length;	
			tmpJSON={};
			for(i=0;i<codeArrLen;i++){
				codeJSON = codeArr[i];
				tmpJSON[codeJSON[formJSON.value]] = codeJSON[formJSON.label];
			}
			rs[tmpId.replace(prefix,"")] = tmpJSON;
		}
		
		if(errId){
			alert("요청하신 코드 중 처리되지 않은 코드가 있습니다.\n"+errId);
		}
		return rs;
	},
	/**
	 * com에 정의된 CONTEXT_PATH를 포함한 URL을 반환한다.
	 * @date 2017. 3. 20.
	 * @param {String} url URL경로
	 * @returns {String} CONTEXT가 포함된 URL경로
	 * @memberOf com 
	 * @author InswaveSystems
	 * @example
	 * //아래와 같이 CONTEXT가 정의된 경우
	 * com.CONTEXT_PATH = "ws5";
	 * var rs = com.getURL("/ws/sample.xml");
	 * //return 예시) "/ws5/ws/sample.xml"
	 * var rs2 = com.getURL("/edu/selectMember.do");
	 * //return 예시) "/ws5/edu/selectMember.do"
	 * var rs3 = com.getURL("/ws5/edu/selectMember.do");
	 * //return 예시) "/ws5/edu/selectMember.do"  
	 */
	getURL : function(url){
		var tmpPath = url.charAt(0) === "/" ? this.CONTEXT_PATH : this.CONTEXT_PATH +"/";
		
		if(url.startsWith( tmpPath+"/" ) || url.startsWith( "." )){
			return url;
		}else{
			return tmpPath+url;
		}
	},
	getI18NUrl : function(url) {

		var w2xPath = this.getURL( url);
		var URL = url.toUpperCase();

		if( URL.indexOf(".XML") > 0 ){
		    var locale = $p.local.getItem( "locale" );
		    if( locale == null || locale == '' ) {
		    	
		        return "/ws5/websquare/I18N.do?w2xPath="+w2xPath;
		    } else {
		    	
		        return "/ws5/websquare/I18N.do?locale="+unescape(locale)+"&w2xPath="+w2xPath;
		    }    
		} else {
			return w2xPath;
		}
	},	
	
	/**
	 * namespace에 정의된 객체를 반환한다.
	 * function명을 문자열로 받아 객체로 반환 받을 때 사용한다. 
	 * @date 2017. 9. 27.
	 * @param {String} objName 문자열 객체명
	 * @returns {Object} Object 
	 * @memberOf com 
	 * @author InswaveSystems
	 * @example
	 * //"com.getURL"과 같이 문자열로 받은 function을 객체화 시키고 싶을 때.
	 * var rsObj = com.getObjByNm("com.getURL");
	 * rsObj("/ws/sample.xml");	//반환 받은 function을 실행한다.
	 */
	getObjByNm : function(objName, _parentObj){
		if(typeof objName === "function"){
			return objName;
		}
		_parentObj = _parentObj || window;
		var idx = objName.indexOf("."), restStr;
		if(idx > 0){
			restStr = objName.slice(idx+1);
			if(restStr){
				return this.getObjByNm(restStr, _parentObj[objName.substr(0,idx)]);
			}
		}
		return _parentObj[objName];
	}
};

/**
 * 객체의 Type을 반환한다.
 * 객체의 typeof 값을 반환하며 typeof의 값이 object인 경우 array, json, xml, null로 체크하여 반환한다.
 * @date 2016. 12. 20.
 * @param {Object} obj type을 반환 받을 객체(string,boolean,number,object 등)
 * @author InswaveSystems
 * @return {String} 객체의 타입으로 typeof가 object인 경우 array, json, xml, null로 세분화하여 반환한다. 그외 object타입이 아닌경우 원래의 type(string,boolean,number 등)을 반환한다.
 * @example
 * com.getObjecType("WebSquare");
 * //return 예시)"string"
 * com.getObjecType({"name":"WebSquare"});
 * //return 예시)"json"
 * com.getObjecType(["1","2"]);
 * //return 예시)"array"
 */
com.getObjecType = function(obj){
    var objType = typeof obj;
    if (objType !== 'object') {
        return objType;
    } else if (obj.constructor === {}.constructor) {
        return 'json';
    } else if (obj.constructor === [].constructor) {
        return 'array';
    } else if (obj === null) {
    	return 'null';
    } else{
    	var tmpDoc = WebSquare.xml.parse("<data></data>");
    	if (obj.constructor === tmpDoc.constructor || obj.constructor === tmpDoc.firstElementChild.constructor) {
    		return 'xml';
    	}else {
            return objType;
        }
    }
};


/**
 * 브라우저의 console객체가 있는 경우 console.log로 출력하며 console객체가 없는 경우 $p.log로 출력한다. 
 * @date 2017. 12. 18.
 * @memberOf com
 * @param {String}	message	출력할 문자열.
 * @param {String}	type	[defulat:null, E]로그 종류. 아무런 값을 넣지 않으면 message만 출력되며 "E"인 경우 message 앞뒤에 "===== error =====" 와 같은 구분자가 포함되어 출력된다.
 * @author InswaveSystems
 * @example
 * com.log("출력할 문자열을 입력하세요.");
 * @since
 */
com.log = function(message, type){
	if(type && type.toUpperCase() === "E"){
		message = "=================== error start ===============\n" + message + "\n=================== error end ================="; 
	}
	if(console){
		console.log(message);
	}else{
		$p.log(message);
	}
};


/**
 * 코드성 데이터와 컴포넌트의 nodeSet(아이템 리스트)연동 기능을 제공한다.
 * code별로 JSON객체를 생성하여 array에 담아 첫번째 파라메터로 넘겨준다. 
 * 그리드뷰의 경우 compID는 [컴포넌트아이디]:[컬럼명]으로 작성한다.
 * @date 2016. 07. 27.
 * @param {JSON} codeJsonArr [필수]{"code" : "코드넘버", "compID" : "적용할 컴포넌트명"}
 * @param {String} callbackFuncStr [선택]콜백 함수명
 * @param {Array} callbackFuncParamArr [선택]콜백 함수에 넘겨줄 파라메터로 array안에 담는다. 콜백 함수를 호출 할 때 첫번째는 응답 문자열을 넣고 두번째부터 이 값을 할당한다. 
 * @memberOf com
 * @author InswaveSystems
 * @example
 * //콜백 함수가 없는 경우
 * com.setCommonCode([
 * 		{"code":"04","compID":sbx_CommCodeReligion.getID()},
 * 		{"code":"24","compID":grd_CommCodeSample.getID()+":JOB_CD"}
 * 		]);
 * 
 * //콜백 함수만 있는 경우
 * com.setCommonCode([
 * 		{"code":"04","compID":sbx_CommCodeReligion.getID()},
 * 		{"code":"24","compID":grd_CommCodeSample.getID()+":JOB_CD"}
 * 		], "setCommonCodeCallback");
 * 
 * //콜백함수에 파라메터를 넘겨주는 경우
 * com.setCommonCode([
 * 		{"code":"04","compID":sbx_CommCodeReligion.getID()},
 * 		{"code":"24","compID":grd_CommCodeSample.getID()+":JOB_CD"}
 * 		], "com.setCommonCodeCallback", [true,"init"]);
 * //공통 코드 통신 이후 com.setCommonCodeCallback이 실행되며 호출되는 모양은 다음과 같다.
 * //첫번째 값은 서버 통신의 응답값이며 두번째 부터 callbackFuncParamArr에 할당된 값을 할당한다.
 * com.setCommonCodeCallback(responseText, true, "init");
 */
com.setCommonCode = function(codeJsonArr, callbackFuncStr, callbackFuncParamArr){
	var codeJsonArrLen;
	if(codeJsonArr){
		 codeJsonArrLen = codeJsonArr.length;
	}else{
		$p.log("=== com.setCommonCode Parameter Type Error ===\nex) com.setCommonCode([{\"code:\":\"04\",\"compID\":\"sbx_Gender\"}],\"com.callbackFunction\")\n===================================");
		return;
	}
	
	var i, j, codeObj, dltId, dltIdArr=[], paramCode = "", compArr, compArrLen, tmpIdArr;
	var commCodeInfo = com._commonCodeInfo;
	var dataPrefix = commCodeInfo.idPrefix;
	
	var dcFormObj = com.getCodeDCForm(commCodeInfo.fieldArr);
	for(i=0;i<codeJsonArrLen;i++){
		codeObj = codeJsonArr[i];
		try{
			if(i>0){ 
				paramCode += ",";
			}
			dltId = dataPrefix+codeObj.code;
			dltIdArr.push(dltId);
			paramCode += codeObj.code;
			
			dcFormObj.id = dltId;
			
			try{
				$p.data.remove(dltId);
			}catch(ex){}
			
			$p.data.create(dcFormObj);
			
			if(codeObj.compID){	//컴포넌트의 ID가 없는 경우도 있음.
				compArr = (codeObj.compID).replaceAll(" ","").split(",");
				compArrLen = compArr.length;
				for(j=0;j<compArrLen;j++){
					tmpIdArr = compArr[j].split(":");
					try{
						if(tmpIdArr.length === 1){	//기본 컴포넌트
							window[tmpIdArr[0]].setNodeSet("data:"+dltId, commCodeInfo.label, commCodeInfo.value);
						}else{	//gridView 컴포넌트
							window[tmpIdArr[0]].setColumnNodeSet(tmpIdArr[1], "data:"+dltId, commCodeInfo.label, commCodeInfo.value);
						}
					}catch(ex){
						$p.log(tmpIdArr[0] + "이 존재하지 않습니다.");
					}
				}
			}
			
		}catch(ex){
			$p.log("com.setCommonCode Error");
			$p.log(JSON.stringify(codeObj));
			$p.log(ex);
			continue;
		}
	}

	// submission 생성 
    var searchCodeGrpOption = {
    	id : "sbm_SearchCode",
    	action : "/edu/getCodeList.do",
    	target : "data:json," + JSON.stringify(dltIdArr),
    	isShowMeg : false };
    
    if(callbackFuncStr){
    	searchCodeGrpOption.submitDoneHandler = callbackFuncStr;
    	searchCodeGrpOption.submitDoneHandlerParams = callbackFuncParamArr;
    }

    com.executeSubmission_dynamic(searchCodeGrpOption,{"dma_Common_Code":{"GRP_CD" : paramCode, "DATA_PREFIX" : dataPrefix}});
};

/**
 * 공통 코드용 dataList의 골격을 생성하여 반환한다.
 * setCommonCode에서 사용.
 * @private
 * @date 2016. 11. 15.
 * @param {JSON} idStr 생성할 DataList ID
 * @memberOf com
 * @author InswaveSystems
 */
com.getCodeDCForm = function(infoArr){
	var option = {
		"type" : "dataList",
		"option":{ "baseNode": "list", "repeatNode": "map"},
		"columnInfo":[]
    };
	//{"id":"GRP_CD", "name":"코드그룹", "dataType":"text"}
	var i, infoArrLen;
	infoArrLen = infoArr.length; 
	for(i=0;i<infoArrLen;i++){
		//option.columnInfo.push({"id":"GRP_CD", "name":"코드그룹", "dataType":"text"});
		option.columnInfo.push({"id":infoArr[i]});
	}

	return option;
};

/**
 * submission를 실행한다.
 * submission의 id로 객체가 존재하지 않으면 생성후 실행한다.
 *
 * @date 2014. 12. 9.
 * @private
 * @param {JSON} options createSubmission의 options 참고
 * @param {JSON} requestData 요청 데이터
 * @param {JSON} obj 전송중 disable시킬 컴퍼넌트
 * @memberOf com
 * @author InswaveSystems
 * @example
 * var searchCodeGrpOption = {
 * 		id : "sbm_SearchCodeGrp"
 * 		,action : "serviceId=CD0001&action=R"
 * 		,target : 'data:json,{"id":"dlt_CodeGrp","key":"data"}'
 * 		,submitDoneHandler : "searchCodeGrpCallback", isShowMeg : false };
 * executeSubmission_dynamic(searchCodeGrpOption);
 */
com.executeSubmission_dynamic = function(options, requestData , obj) {
    var submissionObj;
    try{
    	submissionObj = $p.getSubmission(options.id);
    }catch(ex){
    	console.log("in catch");
    	submissionObj = null;
    }
    if (submissionObj === null) {
    	
    	com.createSubmission(options);
    } else {
    	com.setSubmission(submissionObj, options);
    }
    $p.executeSubmission( options.id, requestData, obj);
};


/**
 * Submission 객체를 동적으로 생성한다.
 *
 * @date 2014. 12. 9.
 * @private
 * @param {JSON} options ※options JSON형태 객체
 *  ----------------------------------------------------options arguments----------------------------------------------------
 * {String} options.id : submission 객체의 ID. 통신 모듈 실행 시 필요.
 * {String} options.ref : 서버로 보낼(request) DataCollection의 조건 표현식.(조건에 때라 표현식이 복잡하다) 또는 Instance Data의 XPath.
 * {String} options.target : 서버로 응답(response) 받은 데이터가 위치 할 DataCollection의 조건 표현식. 또는 Instance Data의 XPath.
 * {String} options.action : 통신 할 서버 측 URI.(브라우저 보안 정책으로 crossDomain은 지원되지 않는다.)
 * {String} options.method : [default: post, get, urlencoded-post] get:파라메타를 url에 붙이는 방식 (HTML과 동일).
 * post:파라메타를 body 구간에 담는 방식 (HTML과 동일) urlencoded-post:urlencoded-post.
 * {String} options.mediatype : [default: application/xml, text/xml, application/json, application/x-www-form-urlencoded]
 * application/x-www-form-urlencoded : 웹 form 방식(HTML방식). application/json : json 방식. application/xml : XML 방식. text/xml : xml방식
 * (두 개 차이는 http://stackoverflow.com/questions/4832357 참조)
 * {String} options.mode : [default: synchronous, synchronous] 서버와의 통신 방식.  asynchronous:비동기식.  synchronous:동기식
 * {String} options.encoding : [default: utf-8, euc-kr, utf-16] 서버 측 encoding 타입 설정 (euc-kr/utf-16/utf-8)
 * {String} options.replace : [default: none, all, instance] action으로부터 받은 response data를 적용 구분 값.
 * all : 문서 전체를 서버로부터 온 응답데이터로 교체.  Instance : 해당되는 데이터 구간.  None : 교체안함.
 * {String} options.processMsg : submission 통신 중 보여줄 메세지.
 * {String} options.errorHandler : submission오류 발생 시 실행 할 함수명.
 * {String} options.customHandler : submssion호출 시 실행 할 함수명.
 * {Function} options.submitHandler : {script type="javascript" ev:event="xforms-submit"} 에 대응하는 함수.
 * {Function} options.submitDoneHandler : {script type="javascript" ev:event="xforms-submit-done"} 에 대응하는 함수
 * {Function} options.submitErrorHandler : {script type="javascript" ev:event="xforms-submit-error"} 에 대응하는 함수
 * @memberOf com
 * @author InswaveSystems
 * @example
 * com.createSubmission(options);
 */
com.createSubmission = function(options) {
    
	var submissionObj = {
        "id" : options.id, //submission 객체의 ID. 통신 모듈 실행 시 필요.
        "ref" : options.ref, //서버로 보낼(request) DataCollection의 조건 표현식.(조건에 때라 표현식이 복잡하다) 또는 Instance Data의 XPath.
        "target" : options.target, //서버로 응답(response) 받은 데이터가 위치 할 DataCollection의 조건 표현식. 또는 Instance Data의 XPath.
        "action" : options.action, //통신 할 서버 측 URI.(브라우저 보안 정책으로 crossDomain은 지원되지 않는다.)
        "method" : options.method, //[default: post, get, urlencoded-post] get:파라메타를 url에 붙이는 방식 (HTML과 동일).  
                           // post:파라메타를 body 구간에 담는 방식 (HTML과 동일).  urlencoded-post:urlencoded-post.
        "mediatype" : options.mediatype || com.DEFAULT_OPTIONS_MEDIATYPE, //application/json  
        "encoding" : "UTF-8", //[default: utf-8, euc-kr, utf-16] 서버 측 encoding 타입 설정 (euc-kr/utf-16/utf-8)
        "mode" : options.mode || com.DEFAULT_OPTIONS_MODE, //[default: synchronous, synchronous] 서버와의 통신 방식.  asynchronous:비동기식.  synchronous:동기식
        "submitHandler" : options.submitHandler, //<script type="javascript" ev:event="xforms-submit"> 에 대응하는 함수.
        "submitErrorHandler" : options.submitErrorHandler //<script type="javascript" ev:event="xforms-submit-error">에 대응하는 함수
    };
    
    if ((options.isProcessMsg === true) && (options.processMsg === "")) {
    	submissionObj.processMsg = "해당 작업을 처리중입니다";
    }
    
    if(options.submitDoneHandler){
    	submissionObj.submitDoneHandler = function(e){
    		try{
    			com.getObjByNm(options.submitDoneHandler).apply(this, [e.responseText].concat(options.submitDoneHandlerParams));
    		}catch(ex){
    			$p.log(ex);console.log(ex);
    		}finally{
    			options = null;
    			if(submissionObj){
    				submissionObj.submitDoneHandler = null;
        			submissionObj.submitHandler = null;
        			submissionObj.submitErrorHandler = null;
        			submissionObj = null;
    			}
    			
    		}
        };
    }
    
    $p.createSubmission(submissionObj);
};

/**
 * Submission 정보를 세팅한다.
 *
 * @date 2014. 12. 9.
 * @private
 * @param {Object} submissionObj 서브미션 ID
 * @param {Object} options 서브미션 옵션 (com.createSubmission의 옵션 참고)
 * @memberOf com
 * @author InswaveSystems
 * @example
 * com.setSubmission(submissionObj, options);
 */
com.setSubmission = function(submissionObj, options) {
    var ref = options.ref || "";
    var target = options.target || "";
    var action = options.action;// 요청주소
    var mode = options.mode || com.DEFAULT_OPTIONS_MODE;
    var mediatype = options.mediatype || com.DEFAULT_OPTIONS_MEDIATYPE;
    var method = (options.method || "post");
    var processMsg = options.processMsg || "";

    if ((options.isProcessMsg === true) && (processMsg === "")) {
        processMsg = "해당 작업을 처리중입니다";
    }

    var instance = options.instance || "";
    var replace = options.replace || "instance";

    submissionObj.id = options.id;
    submissionObj.ref = ref;
    submissionObj.target = target;
    submissionObj.action = action;
    submissionObj.method = method;
    submissionObj.mediatype = mediatype;
    submissionObj.encoding = "UTF-8";
    submissionObj.instance = instance;
    submissionObj.replace = replace;
    submissionObj.mode = mode;
    submissionObj.processMsg = processMsg;
};

/**
 * Submission을 수행하기 전에 실행된다.
 * 공통 설정에 적용되며 모든 submission이 영향을 받는다.
 * 현재는 submission의 action에 context가 누락된 경우의 오류를 방지하고자 url을 체크하는 로직이 구성되어있다.
 * @private
 * @date 2017. 3. 20.
 * @memberOf com
 * @author InswaveSystems
 */
com._sbm_preSubmitFunction = function(sbmObj){
	sbmObj.action = this.getURL( sbmObj.action );
};

/**
 * Submission이후 서버에서 정상 응답(200)이 왔을 때 실행된다.
 * return을 통해 error 상태로 변경이 가능하다.
 * 서버에서 내려온 상태코드를 가지고 오류가 있을 경우 log을 출력하는 기능이 구현되어있다.
 * @private
 * @date 2017. 5. 12.
 * @memberOf com
 * @author InswaveSystems
 */
com._sbm_errorHandler = function(resObj, sbmId){
	var msgObj, msgCode;
	if(resObj){
		msgCode = resObj.msgCode;
		if(msgCode && msgCode !== "S"){
			msgObj = resObj.msgDetail;
			if(msgObj){
				if(confirm(resObj.msg+"\n--------------------------\n상세 로그를 출력하시겠습니까?")){
					$p.log("\n--------------------------------\n message :: "+msgObj + "\n--------------------------------");
				}
			}else{
				alet(resObj.msg);
			}
			return true;
		}
	}
	return false;
};

