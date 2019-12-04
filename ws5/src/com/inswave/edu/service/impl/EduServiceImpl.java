package com.inswave.edu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.inswave.edu.dao.EduDao;
import com.inswave.edu.service.EduService;
import com.inswave.edu.util.EduUtils;

@Service("eduService")
public class EduServiceImpl implements EduService {
	@Resource(name="eduDao")
	private EduDao eduDao;
	
	/**
	 * <pre>
	 * 신규 단건 저장 - insert
	 * @param param
	 * {
	 * 	"EMP_NM": "홍길동",
	 * 	"GENDER_CD": "F",
	 * 	"JOIN_DATE": "20060616",
	 * 	"POSITION_CD": "01",
	 * 	"DUTY_CD": "02",
	 * 	"ROLE_CD": "03",
	 * 	"ASSIGNED_TASK": null,
	 * 	"EMAIL": "test@inswave02.com",
	 * 	"ZIP_CD": "142813",
	 * 	"ADDRESS1": "서울 강북구 미아2동",
	 * 	"ADDRESS2": " 233",
	 * 	"IMAGE_PATH": null
	 * }
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	public int insertSpMember(Map param) throws Exception {
		return eduDao.insertSpMember(param); 
	}

	/**
	 * <pre>
	 * 단건 업데이트 - update
	 * @param param
	 * {
	 * 	"EMP_NM": "홍길동",
	 * 	"GENDER_CD": "F",
	 * 	"JOIN_DATE": "20060616",
	 * 	"POSITION_CD": "01",
	 * 	"DUTY_CD": "02",
	 * 	"ROLE_CD": "03",
	 * 	"ASSIGNED_TASK": null,
	 * 	"EMAIL": "test@inswave02.com",
	 * 	"ZIP_CD": "142813",
	 * 	"ADDRESS1": "서울 강북구 미아2동",
	 * 	"ADDRESS2": " 233",
	 * 	"IMAGE_PATH": null
	 * }
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	public int updateSpMember(Map param) throws Exception {
		return eduDao.updateSpMember(param);
	}
	/**
	 * <pre>
	 * 단건 삭제 - delete
	 * @param param
	 * {
	 * 	"EMP_CD": "99999999"
	 * }
	 * @return
	 * @throws Exception
	 * </pre>
	 */
	public int deleteSpMember(Map param) throws Exception{
		return eduDao.deleteSpMember(param);
	}
	
	/**
	 * <pre>
	 * insert, update, delete를 한꺼번에 처리.
	 * 다건 처리가 가능하며 mybatis의 foreach 사용.
	 * @param param
	 * {
	 * 	"insert" : [
	 * 		{
	 * 			"EMP_NM": "홍신규",
	 * 			"GENDER_CD": "M",
	 * 			"JOIN_DATE": "20060616",
	 * 			"POSITION_CD": "01",
	 * 			"DUTY_CD": "02",
	 * 			"ROLE_CD": "03",
	 * 			"ASSIGNED_TASK": null,
	 * 			"EMAIL": "test@inswave02.com",
	 * 			"ZIP_CD": "142813",
	 * 			"ADDRESS1": "서울 강북구 미아2동",
	 * 			"ADDRESS2": " 233",
	 * 			"IMAGE_PATH": null
	 * 		}
	 * 	],
	 * 	"update" : [
	 * 		{
	 * 			"EMP_CD": "99999998",
	 * 			"EMP_NM": "홍기존",
	 * 			"GENDER_CD": "M",
	 * 			"JOIN_DATE": "20060616",
	 * 			"POSITION_CD": "01",
	 * 			"DUTY_CD": "02",
	 * 			"ROLE_CD": "03",
	 * 			"ASSIGNED_TASK": null,
	 * 			"EMAIL": "test@inswave02.com",
	 * 			"ZIP_CD": "142813",
	 * 			"ADDRESS1": "서울 강북구 미아2동",
	 * 			"ADDRESS2": " 233",
	 * 			"IMAGE_PATH": null
	 * 		}
	 * 	],
	 * 	"delete" : [
	 * 		{
	 * 			"EMP_CD": "99999999"
	 * 		}
	 * 	]
	 * }
	 * @return resMap : DB 반영 결과
	 * {
	 * 	"I" : 1,
	 * 	"D" : 1,
	 * 	"U" : 2
	 * }
	 * @throws Exception
	 * </pre>
	 */
	public Map saveSpMember(Map param) throws Exception{
		Map resMap = new HashMap();
		List arr = null;
		int arrLen = 0;
		int rsNum = 0;

		if(((List)param.get("insert")).size() > 0){
			resMap.put("I",eduDao.insertSpMemberBatch(param));
		}
		
		if(((List)param.get("delete")).size() > 0){
			resMap.put("D",eduDao.deleteSpMemberBatch(param));
		}
		
		/*
		if(((List)param.get("update")).size() > 0){
			resMap.put("U",eduDao.updateSpMemberBatch(param));
		}
		*/

		arr = (List)param.get("update");
		arrLen = arr.size();
		
		if(arrLen > 0){
			for(int i=0;i<arrLen;i++){
				rsNum += eduDao.updateSpMember((Map)arr.get(i));
			}
			resMap.put("U",rsNum);
		}
		
		return resMap;
	}
	
	/**
	 * <pre>
	 * 사용자 리스트 조회
	 * - 페이징 사용
	 * - 검색 조건 : 사용자명, 이메일로 like 검색.
	 *  
	 * @param sParam 
	 *   - page : 시작 페이지 번호(1부터 시작)
	 *   - count : 한 페이지에 보여지는 row수
	 *   - searchType : 검색 컬럼명. null일 경우 전체 대상으로 조회.
	 *     - name : 사용자명
	 *     - email : 이메일
	 *   - searchParam : 검색어
	 * 
	 * 예시)
	 * {"page":"1", "count":"10", "searchType":"name", "searchParam":"은진"}
	 * 
	 * @return resObj<JSONObject>
	 * - dma_SearchResult : request로 넘어온 파라메터 + 페이징 정보.
	 *   - count : request값 그대로
	 *   - page : request값 그대로
	 *   - prePage : request값 그대로
	 *   - searchType :  request값 그대로
	 *   - pEMP_NM||pEMAIL :  searchType에 따라 컬럼명 변동. searchParam으로 넘어온 값이 담긴다.
	 *   - startNum : 쿼리 조건절 시작 row
	 *   - endNum : 쿼리 조건절 종료 row
	 *   - totalCount : 전체 검색건 수
	 *   
	 * - dlt_Member : 검색된 사용자 리스트
	 * 예시)
	 * {
	 * 	"dma_SearchResult": {
	 * 		"count": "10",
	 * 		"page": "1",
	 * 		"prePage": "",
	 * 		"searchType": "name",
	 * 		"pEMP_NM": "은진",
	 * 		"startNum": 0,
	 * 		"endNum": 6,
	 * 		"totalCount": 6
	 * 	},
	 * 	"dlt_Member": [
	 * 		{
	 * 			"EMP_CD": 10000070,
	 * 			"EMP_NM": "학은진",
	 * 			"GENDER_CD": "F",
	 * 			"JOIN_DATE": "20150323",
	 * 			"POSITION_CD": "02",
	 * 			"DUTY_CD": "03",
	 * 			"ROLE_CD": "02",
	 * 			"ASSIGNED_TASK": null,
	 * 			"EMAIL": "tiji631@inswave02.com",
	 * 			"ZIP_CD": "702834",
	 * 			"ADDRESS1": "대구 북구 산격2동",
	 * 			"ADDRESS2": " 1∼100",
	 * 			"IMAGE_PATH": null,
	 * 			"CREATED_DATE": "2016-12-07",
	 * 			"UPDATED_DATE": "2016-12-07"
	 * 		},
	 * 		생략
	 * 	]
	 * }
	 * </pre>
	 */
	public JSONObject selectMemberList(Map sParam) throws Exception {
		JSONObject resObj = new JSONObject();
		int sPage = 0;
		int sCount = 0;
		int startNum = 0;
		int endNum = 0;
		int totCount = 0;
			
		if(sParam == null){
			throw new Exception("필수 파라메터가 누락되었습니다.");
		}
		
		try{
			sPage = EduUtils.getInt(sParam.get("page"));
			sCount = EduUtils.getInt(sParam.get("count"));
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("필수 파라메터가 누락되었습니다.");
		}
		
		//"searchType": "",
	    //"searchParam": ""
		String searchType = (String)sParam.get("searchType");
		
		if(searchType != null && !searchType.equals("")){
			if(searchType.equals("name")){
				sParam.put("pEMP_NM", sParam.remove("searchParam"));
			}else if(searchType.equals("email")){
				sParam.put("pEMAIL", sParam.remove("searchParam"));
			}
		}
		
		//검색조건에 따른 총 row수
		totCount = selectSpMemberTotal(sParam);
		
		startNum = (sPage-1)*sCount;
		endNum = sPage * sCount;
		
		if(startNum > totCount){
			throw new Exception("최대 데이터를 초과하였습니다.");
		}
		
		sParam.put("startNum", startNum );
		
		if(endNum > totCount){
			sParam.put("endNum", totCount);
		}else{
			sParam.put("endNum", endNum);
		}
		
		sParam.put("totalCount", totCount );
		
		resObj.put("dma_SearchResult", sParam);
		resObj.put("dlt_Member", selectListSpMember(sParam));
		return resObj;
	}
	
	public JSONObject selectMemberListScroll(Map sParam) throws Exception {
		JSONObject resObj = new JSONObject();
			
		if(sParam == null){
			throw new Exception("필수 파라메터가 누락되었습니다.");
		}
		
		String searchType = (String)sParam.get("searchType");
		
		if(searchType != null && !searchType.equals("")){
			if(searchType.equals("name")){
				sParam.put("pEMP_NM", sParam.remove("searchParam"));
			}else if(searchType.equals("email")){
				sParam.put("pEMAIL", sParam.remove("searchParam"));
			}
		}
		
		resObj.put("dlt_Member", selectListSpMember(sParam));
		return resObj;
	}
	
	/**
	 * <pre>
	 * EMP_CD로 사용자 정보 단건 조회
	 * @param param
	 * {
	 * 	"EMP_CD": "99999999"
	 * }
	 * @return Map 사용자 정보 단건
	 * {
	 * 	"EMP_NM": "홍길동",
	 * 	"GENDER_CD": "F",
	 * 	"JOIN_DATE": "20060616",
	 * 	"POSITION_CD": "01",
	 * 	"DUTY_CD": "02",
	 * 	"ROLE_CD": "03",
	 * 	"ASSIGNED_TASK": null,
	 * 	"EMAIL": "test@inswave02.com",
	 * 	"ZIP_CD": "142813",
	 * 	"ADDRESS1": "서울 강북구 미아2동",
	 * 	"ADDRESS2": " 233",
	 * 	"IMAGE_PATH": null
	 * }
	 * @throws Exception
	 * </pre>
	 */
	public Map selectOneSpMember(Map param) throws Exception {
		return eduDao.selectOneSpMember(param);
	}
	
	/**
	 * <pre>
	 * 사용자 리스트 조회
	 * - 페이징 사용
	 * - 검색 조건 : 사용자명, 이메일로 like 검색.
	 *  
	 * @param sParam
	 *   - pEMP_NM : 검색할 사용자명. like 검색.
	 *   - pEMAIL : 검색할 이메일. like 검색.
	 *   - startNum : 시작 row 번호. limit 검색 조건.
	 *   - count : row 수. limit 검색 조건.
	 * 
	 * 예시)
	 * {"pEMP_NM":"은진", "startNum":20, "count":10}
	 * 
	 * @return List 검색된 사용자 리스트
	 * 예시)
	 * 	[
	 * 		{
	 * 			"EMP_CD": 10000070,
	 * 			"EMP_NM": "학은진",
	 * 			"GENDER_CD": "F",
	 * 			"JOIN_DATE": "20150323",
	 * 			"POSITION_CD": "02",
	 * 			"DUTY_CD": "03",
	 * 			"ROLE_CD": "02",
	 * 			"ASSIGNED_TASK": null,
	 * 			"EMAIL": "tiji631@inswave02.com",
	 * 			"ZIP_CD": "702834",
	 * 			"ADDRESS1": "대구 북구 산격2동",
	 * 			"ADDRESS2": " 1∼100",
	 * 			"IMAGE_PATH": null,
	 * 			"CREATED_DATE": "2016-12-07",
	 * 			"UPDATED_DATE": "2016-12-07"
	 * 		},
	 * 		생략
	 * 	]
	 * </pre>
	 */
	public List selectListSpMember(Map param) throws Exception {
		return eduDao.selectListSpMember(param);
	}
	
	/**
	 * <pre>
	 * 검색 조건에 의한 총 row수. selectListSpMember와 함께 사용함.
	 * - 검색 조건 : 사용자명, 이메일로 like 검색.
	 *  
	 * @param sParam
	 *   - pEMP_NM : 검색할 사용자명. like 검색.
	 *   - pEMAIL : 검색할 이메일. like 검색.
	 * 
	 * 예시)
	 * {"pEMP_NM":"은진"}
	 * 
	 * @return int 검색 조건에 의한 총 row수
	 * </pre>
	 */
	public int selectSpMemberTotal(Map param) throws Exception {
		return eduDao.selectSpMemberTotal(param);
	}
	
	public int selectSpMemberTotalScroll(Map param) throws Exception {
		return eduDao.selectSpMemberTotalScroll(param);
	}
	
	/**
	 * <pre>
	 * selectListSpMember와 동일한 조건.
	 * 다만 DAO에서 MyBatis의 ResultHandler를 이용하여 JSON Array형태로 데이터를 변환 처리.
	 *  
	 * @param sParam
	 *   - pEMP_NM : 검색할 사용자명. like 검색.
	 *   - pEMAIL : 검색할 이메일. like 검색.
	 * 
	 * 예시)
	 * {"pEMP_NM":"은진"}
	 * 
	 * @return Map columnInfo와 data로 나뉜 데이터
	 * {
	 * 	"columnInfo": [
	 * 		"EMP_CD",
	 * 		"EMP_NM",
	 * 		"GENDER_CD",
	 * 		"JOIN_DATE",
	 * 		"POSITION_CD",
	 * 		"DUTY_CD",
	 * 		"ROLE_CD",
	 * 		"ASSIGNED_TASK",
	 * 		"EMAIL",
	 * 		"ZIP_CD",
	 * 		"ADDRESS1",
	 * 		"ADDRESS2",
	 * 		"IMAGE_PATH",
	 * 		"CREATED_DATE",
	 * 		"UPDATED_DATE"
	 * 	],
	 * 	"data": [
	 * 		10000001,
	 * 		"순은진",
	 * 		"F",
	 * 		"20151106",
	 * 		"06",
	 * 		"02",
	 * 		"04",
	 * 		null,
	 * 		"gevo206@inswave02.com",
	 * 		"601802",
	 * 		"부산 동구 범일1동",
	 * 		" 54∼61",
	 * 		null,
	 * 		"2016-12-07",
	 * 		"2016-12-07"
	 * 	],
	 * 	"rowCount": "1995",
	 * 	"colCount": "15"
	 * }
	 * </pre>
	 */
	public Map selectListSpMemberByHandler(Map param) throws Exception{
		return eduDao.selectListSpMemberByHandler(param);
	}
	
	/**
	 * <pre>
	 * 우편번호 다건 조회.
	 * ZIP_CD, ZIP_NM, BUILDING, FROM_NO 조회.
	 * 
	 * @param param
	 *   - town : like 검색.
	 * 예시)
	 * { "town" : "개포"}
	 * </pre>
	 */
	public List selectListZipCodeByTown(Map param) throws Exception{
		return eduDao.selectListZipCodeByTown(param); 
	}
	
	/**
	 * <pre>
	 * 코드 다건 조회
	 * 
	 * @param param
	 *   - CODE : GRP_CD값으로 Array에 담아서 넘김. MyBatis에서 foreach 사용.
	 * 예시)
	 * { "CODE" : [02,101,06] }
	 * 
	 * @return 
	 * <pre> 
	 */
	public List selectCodeList(Map param) throws Exception{
		return eduDao.selectCodeList(param);
	}
	
	/**
	 * <pre>
	 * 대용량 데이터 조회 용도. JSON 타입 반환.
	 * 
	 * @param param
	 *   - SEQ_NO : Row 건수 조절용도. null일 경우 1000 Row 반환.
	 * 예시)
	 * { "SEQ_NO" : 10000 }
	 * </pre>
	 * 
	 * @return List
	 * [
	 * 	{
	 * 		"SEQ_NO": 1,
	 * 		"SIGUNGUCODE": "11110",
	 * 		"STREETNUM": "2005001",
	 * 		"STREET": "세종대로",
	 * 		"STREETENG": "Sejong-daero",
	 * 		"DONGSEQ": "00",
	 * 		"SIDO": "서울특별시",
	 * 		"SIGUNGU": "종로구",
	 * 		"DONGTYPE": "2",
	 * 		"DONGCODE": "NULL",
	 * 		"DONG": "NULL",
	 * 		"PARENTSTREETNUM": "NULL",
	 * 		"PARENTSTREET": "NULL",
	 * 		"ISUSE": "0",
	 * 		"CHANGEREASON": "NULL",
	 * 		"CHANGEHISTORY": "NULL",
	 * 		"SIDOENG": "Seoul",
	 * 		"SIGUNGUENG": "Jongno-gu",
	 * 		"DONGENG": "NULL",
	 * 		"OPENDATE": "20100520",
	 * 		"CLOSEDATE": "NULL"
	 * 	},
	 * 	{
	 * 		"SEQ_NO": 2,
	 * 		"SIGUNGUCODE": "11110",
	 * 		"STREETNUM": "2005001",
	 * 		"STREET": "세종대로",
	 * 		"STREETENG": "Sejong-daero",
	 * 		"DONGSEQ": "01",
	 * 		"SIDO": "서울특별시",
	 * 		"SIGUNGU": "종로구",
	 * 		"DONGTYPE": "1",
	 * 		"DONGCODE": "119",
	 * 		"DONG": "세종로",
	 * 		"PARENTSTREETNUM": "NULL",
	 * 		"PARENTSTREET": "NULL",
	 * 		"ISUSE": "0",
	 * 		"CHANGEREASON": "NULL",
	 * 		"CHANGEHISTORY": "NULL",
	 * 		"SIDOENG": "Seoul",
	 * 		"SIGUNGUENG": "Jongno-gu",
	 * 		"DONGENG": "Sejongno",
	 * 		"OPENDATE": "20100520",
	 * 		"CLOSEDATE": "NULL"
	 * 	}
	 * ]
	 */
	public List selectZipCodeStreet(Map param) throws Exception{
		return eduDao.selectZipCodeStreet(param);
	}
	
	/**
	 * <pre>
	 * 대용량 데이터 조회 용도. JSON 타입 반환. SpitProver 사용시 분할 조회
	 * 
	 * @param param
	 *   - START_NUM : 시작 ROW
     *   - END_NUM : 종료 ROW
	 * 예시)
	 * { "START_NUM" : 0, "END_NUM":1000 }
	 * </pre>
	 * 
	 * @return List
	 * [
	 * 	{
	 * 		"SEQ_NO": 1,
	 * 		"SIGUNGUCODE": "11110",
	 * 		"STREETNUM": "2005001",
	 * 		"STREET": "세종대로",
	 * 		"STREETENG": "Sejong-daero",
	 * 		"DONGSEQ": "00",
	 * 		"SIDO": "서울특별시",
	 * 		"SIGUNGU": "종로구",
	 * 		"DONGTYPE": "2",
	 * 		"DONGCODE": "NULL",
	 * 		"DONG": "NULL",
	 * 		"PARENTSTREETNUM": "NULL",
	 * 		"PARENTSTREET": "NULL",
	 * 		"ISUSE": "0",
	 * 		"CHANGEREASON": "NULL",
	 * 		"CHANGEHISTORY": "NULL",
	 * 		"SIDOENG": "Seoul",
	 * 		"SIGUNGUENG": "Jongno-gu",
	 * 		"DONGENG": "NULL",
	 * 		"OPENDATE": "20100520",
	 * 		"CLOSEDATE": "NULL"
	 * 	},
	 * 	{
	 * 		"SEQ_NO": 2,
	 * 		"SIGUNGUCODE": "11110",
	 * 		"STREETNUM": "2005001",
	 * 		"STREET": "세종대로",
	 * 		"STREETENG": "Sejong-daero",
	 * 		"DONGSEQ": "01",
	 * 		"SIDO": "서울특별시",
	 * 		"SIGUNGU": "종로구",
	 * 		"DONGTYPE": "1",
	 * 		"DONGCODE": "119",
	 * 		"DONG": "세종로",
	 * 		"PARENTSTREETNUM": "NULL",
	 * 		"PARENTSTREET": "NULL",
	 * 		"ISUSE": "0",
	 * 		"CHANGEREASON": "NULL",
	 * 		"CHANGEHISTORY": "NULL",
	 * 		"SIDOENG": "Seoul",
	 * 		"SIGUNGUENG": "Jongno-gu",
	 * 		"DONGENG": "Sejongno",
	 * 		"OPENDATE": "20100520",
	 * 		"CLOSEDATE": "NULL"
	 * 	}
	 * ]
	 */
	public List selectZipCodeStreetSplit(Map param) throws Exception{
		return eduDao.selectZipCodeStreetSplit(param);
	}
	
	/**
	 * <pre>
	 * 대용량 데이터 조회 용도. Array 타입 반환.
	 * selectZipCodeStreet와 동일한 쿼리 사용하며 MyBatis의 ResultHandler를 사용하여 return 데이터 변환.
	 * 
	 * @param param
	 *   - SEQ_NO : Row 건수 조절용도. null일 경우 1000 Row 반환.
	 * 예시)
	 * { "SEQ_NO" : 10000 }
	 * 
	 * @return Map columnInfo와 data로 나뉜 데이터
	 * {
	 * 	"columnInfo": [
	 * 		"SEQ_NO",
	 * 		"SIGUNGUCODE",
	 * 		"STREETNUM",
	 * 		"STREET",
	 * 		"STREETENG",
	 * 		"DONGSEQ",
	 * 		"SIDO",
	 * 		"SIGUNGU",
	 * 		"DONGTYPE",
	 * 		"DONGCODE",
	 * 		"DONG",
	 * 		"PARENTSTREETNUM",
	 * 		"PARENTSTREET",
	 * 		"ISUSE",
	 * 		"CHANGEREASON",
	 * 		"CHANGEHISTORY",
	 * 		"SIDOENG",
	 * 		"SIGUNGUENG",
	 * 		"DONGENG",
	 * 		"OPENDATE",
	 * 		"CLOSEDATE"
	 * 	],
	 * 	"data": [
	 * 		1,
	 * 		"11110",
	 * 		"2005001",
	 * 		"세종대로",
	 * 		"Sejong-daero",
	 * 		"00",
	 * 		"서울특별시",
	 * 		"종로구",
	 * 		"2",
	 * 		"NULL",
	 * 		"NULL",
	 * 		"NULL",
	 * 		"NULL",
	 * 		"0",
	 * 		"NULL",
	 * 		"NULL",
	 * 		"Seoul",
	 * 		"Jongno-gu",
	 * 		"NULL",
	 * 		"20100520",
	 * 		"NULL",
	 * 		2,
	 * 		"11110",
	 * 		"2005001",
	 * 		"세종대로",
	 * 		"Sejong-daero",
	 * 		"01",
	 * 		"서울특별시",
	 * 		"종로구",
	 * 		"1",
	 * 		"119",
	 * 		"세종로",
	 * 		"NULL",
	 * 		"NULL",
	 * 		"0",
	 * 		"NULL",
	 * 		"NULL",
	 * 		"Seoul",
	 * 		"Jongno-gu",
	 * 		"Sejongno",
	 * 		"20100520",
	 * 		"NULL"
	 * 	],
	 * 	"rowCount": "2",
	 * 	"colCount": "21"
	 * }
	 * </pre>
	 */
	public Map selectZipCodeStreetByDefaultResultHandler(Map param) throws Exception{
		return eduDao.selectZipCodeStreetByDefaultResultHandler(param);
	}
	
	
}
