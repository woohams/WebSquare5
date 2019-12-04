package com.inswave.edu.service;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

public interface EduService {
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
	public int insertSpMember(Map param) throws Exception;
	
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
	public int updateSpMember(Map param) throws Exception;
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
	public int deleteSpMember(Map param) throws Exception;
	
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
	public Map saveSpMember(Map param) throws Exception;
	
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
	public JSONObject selectMemberList(Map sParam) throws Exception;
	
	public JSONObject selectMemberListScroll(Map sParam) throws Exception;

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
	public Map selectOneSpMember(Map param) throws Exception;
	
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
	public List selectListSpMember(Map param) throws Exception;
	
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
	public int selectSpMemberTotal(Map param) throws Exception;
	
	public int selectSpMemberTotalScroll(Map param) throws Exception;
	
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
	public Map selectListSpMemberByHandler(Map param) throws Exception;
	
	/**
	 * <pre>
	 * 우편번호 다건 조회.
	 * ZIP_CD, ZIP_NM, BUILDING, FROM_NO 컬럼만 조회.
	 * 
	 * @param param
	 *   - town : like 검색.
	 * 예시)
	 * { "town" : "개포"}
	 * </pre>
	 */
	public List selectListZipCodeByTown(Map param) throws Exception;
	
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
	public List selectCodeList(Map param) throws Exception;
	
	/**
	 * param의 rowcount에 따라 select
	 * @date 2016. 8. 11.
	 * @param param : rpwcount에 select할 count명시. null일 경우 모든 데이터를 반환
	 * @returns <List> rpwcount에 따른 list
	 * @author InswaveSystems
	 * @example 샘플 코드
	 */
	public List selectZipCodeStreet(Map param) throws Exception;
	
	/**
	 * param의Between 조건에 따라 select - splitProvider용
	 * @date 2018. 9. 24.
	 * @param param : rpwcount에 select할 count명시. null일 경우 모든 데이터를 반환
	 * @returns <List> rpwcount에 따른 list
	 * @author InswaveSystems
	 * @example 샘플 코드
	 */
	public List selectZipCodeStreetSplit(Map param) throws Exception;
	
	/**
	 * returnType이 Map인 dao 호출.
	 * @date 2016. 8. 17.
	 * @param param SEQ_NO-조회할 row수
	 * @author InswaveSystems
	 * @example 샘플 코드
	 */
	public Map selectZipCodeStreetByDefaultResultHandler(Map param) throws Exception;
	
}

