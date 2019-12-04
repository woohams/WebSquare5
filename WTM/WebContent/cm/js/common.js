requires("uiplugin.popup");

/**
 * popup을 호출 후 parameter를 주고 받는다.  
 * 
 * @version 1.0
 * @author InswaveSystems
 * @type com
 * @class com
 * @namespace com
 */
com.openPopup = function(dataObj, option, url){
    
    // 넘겨줄 Parameter 형태 작성 
    // ??????
    
    // top, left, width, height 보정 처리 예시 
    var width = option.width || 500;
    var height = option.height || 500;
    try {
        var deviceWidth = parseFloat($("body").css("width"));
        var deviceHeight = parseFloat($("body").css("height"));
       
        if (deviceWidth > 0 && width > deviceWidth) {
            width = deviceWidth - 4; // 팝업 border 고려
        }

        if (deviceHeight > 0 && height > deviceHeight) {
            height = deviceHeight - 4; // 팝업 border 고려
        }
        
    } catch (e) {

    }
    
    var top = ((document.body.offsetHeight / 2) - (parseInt(height) / 2) + $(document).scrollTop()) + "px";
    var left = ((document.body.offsetWidth / 2) - (parseInt(width) / 2) + $(document).scrollLeft()) + "px";
    
    // 팝업 option 셋팅 및 팝업 호출 
    // ?????
};


/**
 * code data를 가져오는 로직을 동적으로 구현  
 * 
 * @version 1.0
 * @author InswaveSystems
 * @type com
 * @class com
 * @namespace com
 */
com.excuteCommonCode = function(jsonObj){
    
    // 1. dataCollection 생성 
    
    // 1-1. request data 생성 
    //???
    
    // data 객체 중복 생성 여부 
    //???
    
    // 구해올 코드 정보의 갯수 
    var cnt = jsonObj.length;
    
    var _initCode = "";
    var _targetDataObj = "";
    
    for(var i=0; i<cnt; i++){
        var _dataObjId = "dlt_commonCode_" + jsonObj[i].cd;
        
        if(i > 0){
            _initCode = _initCode + "," + jsonObj[i].cd;
            _targetDataObj = _targetDataObj + "," + '{"id":"' + _dataObjId + '"}';
        }else{
            _initCode = jsonObj[i].cd;
            _targetDataObj = '{"id":"' + _dataObjId + '"}';
        }
        
        // 1-2. response data 생성 
        // ???
        
        // data 객체 중복 생성 여부 
        var _dataRes = $p.getComponentById(_dataObjId);
        if(_dataRes){
        }else{
            // 생성하지 않았을 경우 만들 것 
            $p.data.create( optionRes );
        }
        // 1-3. component & data binding
        var comObj = $p.getComponentById(jsonObj[i].id);
        // ???? => component data binding
    }
    
    _targetDataObj = "data:json,[" + _targetDataObj + "]"; 
    
    // 2. submission 생성 및 실행
    
    // 2-1. submission 생성
    // ????
    
    // 2-2. submission 실행하기 
    // ????

};
