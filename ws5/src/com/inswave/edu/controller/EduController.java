package com.inswave.edu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inswave.edu.service.EduService;
import com.inswave.edu.util.EduUtils;
import com.inswave.edu.util.FileHandler;

@RestController
public class EduController {
	private static final Logger LOGGER = Logger.getLogger("webApp");

	@Resource(name = "eduService")
	private EduService eduService;
	
	/**
	 * <pre>
	 * 사용자 리스트 조회
	 * 페이징 + 검색(사용자명, 이메일) 기능
	 * @param param
	 * 예시) {"dma_SearchParam":{"count":"10","page":"1","prePage":"","searchType":"","searchParam":""}}
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping(value = "/edu/selectMemberList.do", method = RequestMethod.POST)
	public Map selectMemberList(@RequestBody Map param) throws Exception {
		Map resObj = new HashMap();
		Map sParam = null;
		try {
			sParam = (Map) param.get("dma_SearchParam");
			
			if (sParam == null) {
				throw new RuntimeException("필수 파라메터가 누락되었습니다.");
			}
			resObj = eduService.selectMemberList(sParam);
			
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
	 * 전체 건수 조회
	 * @param param
	 * 예시) {"dma_SearchParam":{"count":"10","page":"1","prePage":"","searchType":"","searchParam":""}}
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping(value = "/edu/selectSpMemberTotal.do", method = RequestMethod.POST)
	public Map selectSpMemberTotal(@RequestBody Map param) throws Exception {
		Map resObj = new HashMap();
		Map sParam = null;
		int totalCnt = 0;
		try {
			
			totalCnt = eduService.selectSpMemberTotalScroll(sParam);
			resObj.put("TOTAL_CNT", totalCnt);
			
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
	 * 사용자 리스트 scrollEnd 방식
	 * @param param
	 * 예시) {"dma_SearchParam":{"count":"10","page":"1","prePage":"","searchType":"","searchParam":""}}
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping(value = "/edu/selectMemberListScroll.do", method = RequestMethod.POST)
	public Map selectMemberListScroll(@RequestBody Map param) throws Exception {
		Map resObj = new HashMap();
		Map sParam = null;
		int totalCnt = 0;
		try {
			sParam = (Map) param.get("dma_Search");
			
			if (sParam == null) {
				throw new RuntimeException("필수 파라메터가 누락되었습니다.");
			}
			
			String totalSearch_Yn = (String) sParam.get("TOTAL_YN"); // 총건수 조회 여부
						
			resObj = eduService.selectMemberListScroll(sParam);
			
			if (totalSearch_Yn.equals("Y")) {
				totalCnt = eduService.selectSpMemberTotalScroll(sParam);
				resObj.put("TOTAL_CNT", totalCnt);
			}
			
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
	 * 사용자 정보 저장 후 조회.
	 * 다건 insert, delete, update 처리.
	 * @param param
	 *   - dlt_Member : 사용자 정보 List. DataList의 rowStatus컬럼으로 CUD처리.
	 *   - dma_SearchParam : 사용자 정보 조회 파라메터
	 *     예시 ) {"dma_SearchParam":{"count":"10","page":"1","prePage":"","searchType":"","searchParam":""}}
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping(value = "/edu/saveAndSelectMember.do", method = RequestMethod.POST)
	public Map saveAndSelectMember(@RequestBody Map param)
			throws Exception {
		Map resObj = new HashMap();
		List modList = null;
		Map saveResult = null;
		int modListLen;
		Map modParam = new HashMap<String, List>();
		List insert = new ArrayList<Map>();
		List update = new ArrayList<Map>();
		List delete = new ArrayList<Map>();
		Map rowData;
		String rowStatus;

		try {
			modList = (List) param.get("dlt_Member");
			modListLen = modList.size();

			for (int i = 0; i < modListLen; i++) {
				rowData = (Map) modList.get(i);
				rowStatus = (String) rowData.get("rowStatus");

				if (rowStatus.equals("C")) {
					insert.add(rowData);
				} else if (rowStatus.equals("U")) {
					update.add(rowData);
				} else if (rowStatus.equals("D") || rowStatus.equals("E")) {
					delete.add(rowData);
				}
			}

			modParam.put("insert", insert);
			modParam.put("update", update);
			modParam.put("delete", delete);

			saveResult = eduService.saveSpMember(modParam);

			try {
				resObj = eduService.selectMemberList((Map) param.get("dma_SearchParam"));
			} catch (Exception ex) {
				throw new RuntimeException(
						"저장은 완료되었으나 조회도중 오류가 발생하였습니다. 다시 조회 해주시기 바랍니다.");
			}

			resObj.put("rsObj", saveResult);

			resObj.put("msg", "저장이 완료되었습니다.");
			resObj.put("msgCode", "S");
		} catch (Exception ex) {
			ex.printStackTrace();
			resObj = EduUtils.setErrorMessage(resObj);
		}
		return resObj;
	}
	
	/**
	 * <pre>
	 * 사용자 정보 단건 저장
	 * @param param
	 *   - dma_Member : 단건  
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping(value = "/edu/saveMember.do", method = RequestMethod.POST)
	public Map saveMember(@RequestBody Map param) throws Exception {
		Map resObj = new HashMap();
		try {
			if (eduService.insertSpMember((Map) param.get("dma_Member")) != 1) {
				throw new RuntimeException("저장 도중 오류가 발생하였습니다.");
			}

			resObj.put("msg", "저장이 완료되었습니다.");
			resObj.put("msgCode", "S");
		} catch (Exception ex) {
			ex.printStackTrace();
			resObj = EduUtils.setErrorMessage(resObj);
		}
		return resObj;
	}
	
	
	/**
	 * 코드 조회.
	 * 다건 코드 조회, 코드별 List 분리.
	 * @param param
	 *   - dma_Common_Code : 코드 조회 파라메터
	 *     - GRP_CD : 조회할 코드값. ,로 구분.
	 *     - DATA_PREFIX : 데이타 객체의 KEY값을 생성하기 위한 참조값.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/edu/getCodeList.do")
	public Map getCodeList(@RequestBody Map param) throws Exception {
		Map resObj = new HashMap();
		Map commonCode;
		String GRP_CD;
		String dataIdPrefix;
		String[] selectCodeList;
		try {
			commonCode = (Map) param.get("dma_Common_Code");
			GRP_CD = (String)commonCode.get("GRP_CD");
			dataIdPrefix = (String)commonCode.get("DATA_PREFIX");
			if(dataIdPrefix == null){
				dataIdPrefix = "dlt_CommonCode_";
			}
			selectCodeList = GRP_CD.split(",");
			commonCode.put("CODE", selectCodeList);
			
			List codeList = eduService.selectCodeList(commonCode);
			
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
			resObj = EduUtils.setErrorMessage(resObj, "공통코드 조회도중 오류가 발생하였습니다.");
		}

		return resObj;
	}
	
	/**
	 * 대량 데이터와 GirdView 연계를 위한 샘플.
	 * dma_SearchParam의  daoType값에 따라 기본 List타입과 대량 데이터용 Map타입이 반환된다.
	 * @param param
	 *   - dma_SearchParam : 데이터 반환 타입용 파라메터
	 *     - daoType : [json, array] 값에 따라 반환 데이터의 타입이 List와 Map 타입으로 바뀐다.
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/edu/selectAllMemberList.do", method = RequestMethod.POST)
	public Map e001SelectListAll(@RequestBody(required=false) Map param) throws Exception {
		Map resObj = new HashMap();
		Map sParam = null;
		String daoType = null;
		boolean isArrayType = false;
		try{
			sParam = (Map)param.get("dma_SearchParam");
			if(sParam != null){
				daoType = (String)sParam.get("daoType");
				if(daoType != null && daoType.equals("array")){
					isArrayType = true;
				}
			}
			
			if(isArrayType){
				LOGGER.info("/edu/selectAllMemberList.do ::::: in array");
				resObj.put("dlt_Member", eduService.selectListSpMemberByHandler(null));
			}else{
				LOGGER.info("/edu/selectAllMemberList.do ::::: in default");
				resObj.put("dlt_Member", eduService.selectListSpMember(null));
			}
			resObj.put("msg", "조회가 완료되었습니다.");
			resObj.put("msgCode", "S");			
		}catch(Exception ex){
			ex.printStackTrace();
			resObj = EduUtils.setErrorMessage(resObj);
		}
		return resObj;
	}
	
	/**
	 * <pre>
	 * 대용량 데이터 타입에 따른 속도 비교으로 JSON 타입 반환.
	 * 10만건 이상의 데이터를 조회한다.
	 * @param param
	 *   - dma_Search : 조회를 위한 파라메터
	 *     - SEQ_NO : Row 건수 조절용도. null일 경우 1000 Row 반환.
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	@RequestMapping(value = "/edu/selectLargeDataDefault.do", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map selectLargeDataDefault( @RequestBody Map param ) throws Exception {
		Map resObj = new HashMap();
		try {
			resObj.put("dlt_ZipCodeStreet", eduService.selectZipCodeStreet((Map) param.get("dma_Search")));
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
	 * 대용량 데이터 타입에 따른 속도 비교으로 Map타입 반환.
	 * 10만건 이상의 데이터를 조회한다.
	 * ResultHandler사용.
	 * 
	 * @param param
	 *   - dma_Search : 조회를 위한 파라메터
	 *     - SEQ_NO : Row 건수 조절용도. null일 경우 1000 Row 반환.
	 * </pre>
	 */
	@RequestMapping(value = "/edu/selectLargeDataArray.do", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map selectLargeDataArray( @RequestBody Map param ) throws Exception {
		Map resObj = new HashMap();
		try {
			resObj.put("dlt_ZipCodeStreet", eduService.selectZipCodeStreetByDefaultResultHandler((Map) param.get("dma_Search")));
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
	 * 우편번호 검색, 다건 조회.
	 * @param param
	 *   - dma_SearchParam
	 *     - town : like 검색.
	 * </pre>
	 */	
	@RequestMapping(value= "/edu/searchZipcode.do", method = RequestMethod.POST)
	public Map searchZipcode(@RequestBody Map param) throws Exception {
		Map resObj = new HashMap();
		Map sParam = null;
		String sTown = null;
		boolean isArrayType = false;
		try{
			sParam = (Map)param.get("dma_SearchParam");
			if(sParam != null && sParam.get("town") != null){
				resObj.put("dlt_ZipCd", eduService.selectListZipCodeByTown(sParam));
			}else{
				throw new RuntimeException("검색 조건을 확인하세요.");
			}
			
			resObj.put("msg", "조회가 완료되었습니다.");
			resObj.put("msgCode", "S");
		}catch(Exception ex){
			ex.printStackTrace();
			resObj = EduUtils.setErrorMessage(resObj);
		}
		return resObj;
	}
	
	@RequestMapping(value="/edu/img/saveBLOB.do", method=RequestMethod.POST)
	public Map saveBLOBImage(@RequestBody Map param) throws Exception{
		Map resObj = new HashMap();
		Map sParam = null;
		try{
			sParam = (Map)param.get("dma_Param");
			if(sParam != null && sParam.get("image") != null){
				System.out.println("inin!!");
				
				sParam.put("data", sParam.remove("image"));
				
				resObj = FileHandler.uploadBLOBImage(sParam); 
			}else{
				throw new RuntimeException("업로드할 파일이 존재하지 않습니다.");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			resObj = EduUtils.setErrorMessage(resObj);
		}
		return resObj;
	};

}
