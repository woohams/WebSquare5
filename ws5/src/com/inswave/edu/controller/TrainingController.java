package com.inswave.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inswave.edu.dto.SelectMemberDTO;
import com.inswave.edu.service.TrainingService;
import com.inswave.edu.util.EduUtils;

@RestController
public class TrainingController {
	private static final Logger LOGGER = Logger.getLogger("webApp");
	
	@Resource(name = "trainingService")
	private TrainingService trainingService;
	
	/**
	 * request, response 테스트용
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/training/test.do", method = RequestMethod.POST)
	public Map test(@RequestBody(required = false) Map param) throws Exception {
		Map resObj = new HashMap();
		Map sParam = null;
		Map resData1 = null;
		try {
			LOGGER.info("==== in /training/test.do request parameter ====");
			LOGGER.info(param);
			LOGGER.info("================================================");			
			sParam = (Map)param.get("dc_reqParam");
			
			LOGGER.info(sParam.get("name"));
			LOGGER.info("================================================");
			
			Map reqMap = new HashMap();
			reqMap.put("EMP_CD", "10000004");
			resObj.put("dc_userInfo", trainingService.getUserInfo(reqMap));
			
			resData1 = new HashMap();
			resData1.put("resName", "김고은");
			resData1.put("resBirthday", "19910702");
			resObj.put("resData", resData1);

			resObj.put("msg", "조회가 완료되었습니다.");
			resObj.put("msgCode", "S");
		} catch (Exception ex) {
			ex.printStackTrace();
			resObj = EduUtils.setErrorMessage(resObj);
		}
		return resObj;
	}
	
	/**
	 * 1일차 실습에 사용.
	 * Request데이터는 없으며 1건(Map)의 사용자 정보를 반환한다.
	 * @param NaN
	 * @return
	 * {
	 * 	"dc_userInfo": {
	 * 		"ZIP_CD": "135502",
	 * 		"ASSIGNED_TASK": null,
	 * 		"GENDER_CD": "F",
	 * 		"JOIN_DATE": "20160702",
	 * 		"DUTY_CD": "04",
	 * 		"UPDATED_DATE": "2016-12-07",
	 * 		"ADDRESS2": " 995∼1002",
	 * 		"CREATED_DATE": "2016-12-07",
	 * 		"EMP_CD": 10001998,
	 * 		"ADDRESS1": "서울 강남구 대치3동",
	 * 		"ROLE_CD": "05",
	 * 		"POSITION_CD": "04",
	 * 		"EMP_NM": "연나빈",
	 * 		"EMAIL": "koho567@inswave03.com"
	 * 	},
	 * 	"msgCode": "S",
	 * 	"msg": "조회가 완료되었습니다."
	 * }
	 * @throws Exception
	 */
	//, method = RequestMethod.POST
	@RequestMapping(value = "/training/getUserInfo.do")
	public Map getMember(@RequestBody(required = false) Map param) throws Exception {
		Map resObj = new HashMap();
		Map sParam = null;
		try {
			if(param == null){
				throw new RuntimeException("필수 파라메터가 누락되었습니다.");
			}
			sParam = (Map)param.get("dc_reqUserInfo");
			if(sParam == null || sParam.get("EMP_CD") == null){
				throw new RuntimeException("필수 파라메터가 누락되었습니다.");
			}
			resObj.put("dc_userInfo", trainingService.getUserInfo(sParam));
			resObj.put("msg", "조회가 완료되었습니다.");
			resObj.put("msgCode", "S");
		} catch (Exception ex) {
			ex.printStackTrace();
			resObj = EduUtils.setErrorMessage(resObj);
		}
		return resObj;
	}
	/**
	 * <pre>
	 * 2일차 실습에 사용.
	 * 검색조건에 따른 사용자 리스트 반환.
	 * @param
	 *   - dc_searchParam : 사용자 검색 조건
	 *    - GENDER_CD : 성별 코드 [all, M, F] 값이 all이거나 null일 경우 전체를 가져오다.
	 *    - POSITION_CD : 직위 코드 [02, 03, ...] 값이 all이거나 null일 경우 전체를 가져오다.
	 * 예시)
	 * {
	 * 	"dc_searchParam": {
	 * 		"GENDER_CD": "M",
	 * 		"POSITION_CD": "02"
	 * 	}
	 * }
	 * @return
	 * {
	 * 	"dc_userInfoList": [
	 * 		{
	 * 			"ADDRESS2": " 143∼148",
	 * 			"ZIP_CD": "135825",
	 * 			"CREATED_DATE": "2016-12-07",
	 * 			"GENDER_CD": "M",
	 * 			"EMP_CD": 10000032,
	 * 			"ADDRESS1": "서울 강남구 논현1동",
	 * 			"JOIN_DATE": "20020820",
	 * 			"POSITION_CD": "02",
	 * 			"EMAIL": "wovo968@inswave01.com",
	 * 			"EMP_NM": "설세윤",
	 * 			"UPDATED_DATE": "2016-12-07"
	 * 		},
	 * 		{
	 * 			"ADDRESS2": "푸른마을아파트 ",
	 * 			"ZIP_CD": "135942",
	 * 			"CREATED_DATE": "2016-12-07",
	 * 			"GENDER_CD": "M",
	 * 			"EMP_CD": 10001297,
	 * 			"ADDRESS1": "서울 강남구 일원본동",
	 * 			"JOIN_DATE": "20160927",
	 * 			"POSITION_CD": "02",
	 * 			"EMAIL": "ruga091@inswave01.com",
	 * 			"EMP_NM": "제갈정원",
	 * 			"UPDATED_DATE": "2016-12-07"
	 * 		}
	 * 	],
	 * 	"msgCode": "S",
	 * 	"msg": "조회가 완료되었습니다."
	 * }
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping(value = "/training/searchMember.do", method = RequestMethod.POST)
	public Map searchMember(@RequestBody(required = true) Map param) throws Exception {
		Map resObj = new HashMap();
		Map sParam = null;
		SelectMemberDTO sMember = null;
		try {
			sParam = (Map)param.get("dc_searchParam");
			
			if(sParam == null){
				throw new RuntimeException("필수 파라메터가 누락되었습니다."); 
			}
			
			sMember = new SelectMemberDTO();
			sMember.setGENDER_CD((String)sParam.get("GENDER_CD"));
			sMember.setPOSITION_CD((String)sParam.get("POSITION_CD"));
			
			resObj.put("dc_userInfoList", trainingService.searchMember(sMember));

			resObj.put("msg", "조회가 완료되었습니다.");
			resObj.put("msgCode", "S");
		} catch (Exception ex) {
			ex.printStackTrace();
			resObj = EduUtils.setErrorMessage(resObj);
		}
		return resObj;
	}
	
	/**
	 * DataList의 rowStatus의 값에 다른 값 확인용.
	 * 실제 저장은 하지 않고 WAS log만 출력한다. 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/training/saveMember.do", method = RequestMethod.POST)
	public Map saveMember(@RequestBody(required = true) Map param) throws Exception {
		Map resObj = new HashMap();		
		try {
			System.out.println("=====saveMember param====================");
			System.out.println(param);
			System.out.println("=========================================");
			
			
			System.out.println("=====saveMember param====================");
			List sList = (List) param.get("dc_userInfoList");
			int totalCnt = sList.size();
			
			System.out.println("넘겨받은 총 건수 : " + totalCnt + "건");
			
			for(int i=0; i<totalCnt; i++) {
				Map rowData = (Map) sList.get(i);
				String rowStatus = (String) rowData.get("rowStatus");
				
				if(rowStatus.equals("C")) {
					System.out.println("입력 : " + rowData.toString());
					
				}else if(rowStatus.equals("U")){
					System.out.println("수정 : " + rowData.toString());
					
				}else if(rowStatus.equals("D") || rowStatus.equals("E")){
					System.out.println("삭제 : " + rowData.toString());
				}
			}
			
			System.out.println("=========================================");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			resObj = EduUtils.setErrorMessage(resObj);
		}
		return resObj;
	}
	
	/**
	 * <pre>
	 * 코드값을 가져온다.
	 * 코드별 key를 분리하여 반환.(return 예시 참조)
	 * 
	 * @param param
	 *   - dc_reqCode : 코드 조회 파라메터
	 *     - GRP_CD : 조회할 코드값. ,로 구분.
	 * 예시 ) {"dc_reqCode":{ "GRP_CD":"101,02" }}
	 * @return 
	 * {
	 * 	"dc_code101": [
	 * 		{
	 * 			"GRP_CD": "101",
	 * 			"CODE_CD": "M",
	 * 			"CODE_NM": "남"
	 * 		},
	 * 		{
	 * 			"GRP_CD": "101",
	 * 			"CODE_CD": "F",
	 * 			"CODE_NM": "여"
	 * 		}
	 * 	],
	 * 	"dc_code02": [
	 * 		{
	 * 			"GRP_CD": "02",
	 * 			"CODE_CD": "02",
	 * 			"CODE_NM": "이사"
	 * 		},
	 * 		{
	 * 			"GRP_CD": "02",
	 * 			"CODE_CD": "07",
	 * 			"CODE_NM": "사원"
	 * 		}
	 * 	],
	 * 	"msgCode": "S",
	 * 	"msg": "공통코드 조회가 완료되었습니다."
	 * }
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping("/training/getCodeList.do")
	public Map getCodeList(@RequestBody Map param) throws Exception {
		Map resObj = new HashMap();
		Map commonCode;
		String GRP_CD;
		String dataIdPrefix = "dc_code";
		String[] selectCodeList;
		try {
			commonCode = (Map) param.get("dc_reqCode");
			GRP_CD = (String)commonCode.get("GRP_CD");
			
			if(GRP_CD != null){
				GRP_CD = GRP_CD.replaceAll(" ", "");
			}
			
			selectCodeList = GRP_CD.split(",");
			commonCode.put("CODE", selectCodeList);
			
			List codeList = trainingService.selectCodeList(commonCode);
			
			//여러건의 code를 가져올 때 code별로 객체 생성
			if(selectCodeList.length > 1){
				int size = codeList.size();
				String preCode = "";
				List codeGrpList = null;
				for (int i = 0; i < size; i++ ){
					Map codeMap = (Map)codeList.remove(0);
					String grp_cd = (String)codeMap.get("GRP_CD");
					if ( !preCode.equals(grp_cd)){
						if ( codeGrpList != null ){
							resObj.put(dataIdPrefix + preCode, codeGrpList);
						}
						preCode = grp_cd;
						codeGrpList = new ArrayList();
						codeGrpList.add(codeMap);
					} else {
						codeGrpList.add(codeMap);
					}
					
					if ( i ==  size-1 ){
						resObj.put(dataIdPrefix + preCode, codeGrpList);
					}
				}
			}else{
				resObj.put(dataIdPrefix + selectCodeList[0], codeList);
			}
			
			
			resObj.put("msg", "공통코드 조회가 완료되었습니다.");
			resObj.put("msgCode", "S");
		} catch (Exception ex) {
			ex.printStackTrace();
			resObj = EduUtils.setErrorMessage(resObj);
		}

		return resObj;
	}
	
	
}
