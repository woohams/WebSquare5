package com.inswave.edu.service;

import java.util.List;
import java.util.Map;

import com.inswave.edu.dto.SelectMemberDTO;

public interface TrainingService {
	/**
	 * Request데이터는 없으며 1건(Map)의 사용자 정보를 반환한다.
	 * @param Map 사용자 코드 {"EMP_CD" : "10000004"}
	 * @return
	 * {
	 * 	 "ZIP_CD": "135502",
	 * 	 "ASSIGNED_TASK": null,
	 * 	 "GENDER_CD": "F",
	 * 	 "JOIN_DATE": "20160702",
	 * 	 "DUTY_CD": "04",
	 * 	 "UPDATED_DATE": "2016-12-07",
	 * 	 "ADDRESS2": " 995∼1002",
	 * 	 "CREATED_DATE": "2016-12-07",
	 * 	 "EMP_CD": 10001998,
	 * 	 "ADDRESS1": "서울 강남구 대치3동",
	 * 	 "ROLE_CD": "05",
	 * 	 "POSITION_CD": "04",
	 * 	 "EMP_NM": "연나빈",
	 * 	 "EMAIL": "koho567@inswave03.com"
	 * }
	 * @throws Exception
	 */
	public Map getUserInfo(Map param)throws Exception;
	
	/**
	 * <pre>
	 * 검색조건에 따른 사용자 리스트 반환.
	 * @param
	 *   - GENDER_CD : 성별 코드 [all, M, F] 값이 all이거나 null일 경우 전체를 가져오다.
	 *   - POSITION_CD : 직위 코드 [all, 02, 03, ...] 값이 all이거나 null일 경우 전체를 가져오다.
	 * 예시)
	 * {
	 * 	"GENDER_CD": "M",
	 * 	"POSITION_CD": "02"
	 * }
	 * @return
	 * [
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
	 * ]
	 * @throws Exception
	 * </pre>
	 */
	public List searchMember(SelectMemberDTO param) throws Exception;
	
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
	
}
