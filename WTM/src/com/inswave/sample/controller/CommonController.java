package com.inswave.sample.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inswave.sample.service.CommonService;
import com.inswave.util.Result;

@Controller
public class CommonController {

	@Autowired
	private CommonService commonService;

	/**
	 * selectCommonSearchItem - 공통코드 아이템 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} 없음
	 * @returns mv dlt_commonSearchItem ( 공통코드 아이템 리스트 )
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/selectCommonSearchItem")
	public @ResponseBody Map<String, Object> selectCommonSearchItem() {
		Result result = new Result();

		try {
			result.setData("dlt_commonSearchItem", commonService.selectCommonSearchItem());
			result.setMsg(result.STATUS_SUCESS, "공통코드 아이템 리스트가 조회 되었습니다.");
		} catch (Exception ex) {
			result.setMsg(result.STATUS_ERROR, "공통코드 아이템 리스트를 가져오는 도중 오류가 발생하였습니다,", ex);
		}
		return result.getResult();
	}

	/**
	 * CommonCode - 모든 공통코드를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} 없음
	 * @returns mv List : 공통코드 전체 리스트
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/selectCommonCode")
	public @ResponseBody Map<String, Object> selectCommonCode() {
		Result result = new Result();
		try {
			result.setData("dlt_commonCode", commonService.selectCommonCodeAll());
			result.setMsg(result.STATUS_SUCESS, "공통코드 전체가 조회되었습니다.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setMsg(result.STATUS_ERROR, "공통코드 정보를 가져오는 도중 오류가 발생하였습니다,", ex);
		}
		return result.getResult();
	}

	/**
	 * CommonCodeList - 조회조건에 따른 공통코드 리스트를 조회한다.
	 * 
	 * @date 2017.12.22
	 * @param {} dma_commonGrp { GRP_CD : "공통그룹 코드" }
	 * @returns mv dlt_commonCode ( 공통코드 리스트 );
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/selectCommonCodeList")
	public @ResponseBody Map<String, Object> selectCommonCodeList(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		try {
			result.setData("dlt_commonCode", commonService.selectCommonCodeList((Map) param.get("dma_commonGrp")));
			result.setMsg(result.STATUS_SUCESS, "공통코드(" + ((Map) param.get("dma_commonGrp")).get("GRP_CD") + ") 리스트가 조회되었습니다.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setMsg(result.STATUS_ERROR, "공통코드 정보(" + ((Map) param.get("dma_commonGrp")).get("GRP_CD") + ")를 가져오는 도중 오류가 발생하였습니다,", ex);
		}
		return result.getResult();
	}

	@RequestMapping("/common/selectCommonGroup")
	public @ResponseBody Map<String, Object> selectCommonGroup(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		try {
			result.setData("dlt_commonGrp", commonService.selectCommonGroup((Map) param.get("dma_search")));
			result.setMsg(result.STATUS_SUCESS, "공통코드 그룹 리스트가 조회되었습니다.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setMsg(result.STATUS_ERROR, "공통코드 그룹 리스트를 가져오는 도중 오류가 발생하였습니다.", ex);
		}
		return result.getResult();
	}

	/**
	 * GetCodeList - 공통코드 조회 dma_commonCode : {GRP_CD:"02,01", DATA_PREFIX:"dlt_code"} <String> GRP_CD : 코드값,코드값 <String> DATA_PREFIX :
	 * "Data객체의 ID prefix 없을 경우 dlt_commonCode_"
	 * 
	 * @date 2017.12.22
	 * @param param {dma_commonCode : {GRP_CD:"02,01", DATA_PREFIX:"dlt_code"}}
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/common/selectCodeList")
	public @ResponseBody Map<String, Object> selectCodeList(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		Map commonCode;
		String GRP_CD;
		String dataIdPrefix;
		String[] selectCodeList;
		try {
			commonCode = (Map) param.get("dma_commonCode");
			GRP_CD = (String) commonCode.get("GRP_CD");
			dataIdPrefix = (String) commonCode.get("DATA_PREFIX");

			if (dataIdPrefix == null) {
				dataIdPrefix = "dlt_commonCode_";
			}
			selectCodeList = GRP_CD.split(",");
			commonCode.put("CODE", selectCodeList);

			List codeList = commonService.selectCodeList(commonCode);

			int size = codeList.size();
			String preCode = "";
			List codeGrpList = null;
			for (int i = 0; i < size; i++) {
				Map codeMap = (Map) codeList.remove(0);
				String grp_cd = (String) codeMap.get("GRP_CD");
				if (!preCode.equals(grp_cd)) {
					if (codeGrpList != null) {
						result.setData(dataIdPrefix + preCode, codeGrpList);
					}
					preCode = grp_cd;
					codeGrpList = new ArrayList();
					codeGrpList.add(codeMap);
				} else {
					codeGrpList.add(codeMap);
				}

				if (i == size - 1) {
					result.setData(dataIdPrefix + preCode, codeGrpList);
				}
			}

			result.setMsg(result.STATUS_SUCESS, "공통코드 조회가 완료되었습니다.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setMsg(result.STATUS_ERROR, "공통코드 조회중 오류가 발생하였습니다.", ex);
		}
		return result.getResult();
	}
}