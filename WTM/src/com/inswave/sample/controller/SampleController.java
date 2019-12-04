package com.inswave.sample.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inswave.sample.service.SampleService;
import com.inswave.util.Result;

@Controller
public class SampleController {

	@Autowired
	private SampleService sampleService;

	/**
	 * 조회 조건에 따른 샘플 목록을 조회한다.
	 * 
	 * @date 2018.11.20
	 * @param {} dma_search { KEYWORD :"검색어", IS_USE:"사용여부" }
	 * @returns mv dlt_sample (샘플 목록) 
	 * @author InswaveSystems
	 * @example
	 */
	@RequestMapping("/sample/searchSample")
	public @ResponseBody Map<String, Object> searchMenu(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		try {
			result.setData("dlt_sample", sampleService.selectSample((Map) param.get("dma_search")));
			result.setMsg(result.STATUS_SUCESS, "샘플 리스트가 조회되었습니다.");
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setMsg(result.STATUS_ERROR, "샘플 리스트를 가져오는 도중 오류가 발생하였습니다.", ex);
		}
		return result.getResult();
	}

	/**
	 * 모든 타입의 컬럼 정보를 담은 table(SP_CUSTOMER_ORDER) 조회
	 * 
	 * @date 2017.12.22
	 * @param argument 파라미터 정보
	 * @returns <ModelAndView> 반환 변수 및 객체
	 * @author InswaveSystems
	 * @example 샘플 코드
	 */
	@RequestMapping("/sample/tempSelect")
	public @ResponseBody Map<String, Object> sampleTempSelect(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		try {
			Map searchMap = (Map) param.get("dma_search");
			List tempList = sampleService.select(searchMap);

			result.setData("dlt_temp", tempList);
			result.setMsg(result.STATUS_SUCESS, "리스트가 조회 되었습니다.");
		} catch (Exception ex) {
			result.setMsg(result.STATUS_ERROR, "처리 도중 오류가 발생하였습니다.", ex);
			ex.printStackTrace();
		}
		return result.getResult();
	}


	/**
	 * 모든 타입의 컬럼 정보를 담은 table(SP_CUSTOMER_ORDER) 조회 - Map방식
	 * 
	 * @date 2017.12.22
	 * @param argument 파라미터 정보
	 * @returns <ModelAndView> 반환 변수 및 객체
	 * @author InswaveSystems
	 * @example 샘플 코드
	 */
	@RequestMapping("/sample/map/tempMapSelect")
	public @ResponseBody Map<String, Object> sampleTempMapSelect(@RequestBody Map<String, Object> param) {
		Result result = new Result();
		try {
			Map searchMap = (Map) param.get("dma_search");
			List tempList = sampleService.select(searchMap);

			result.setData("dlt_temp", tempList);
			result.setMsg(result.STATUS_SUCESS, "리스트가 조회 되었습니다.");
		} catch (Exception ex) {
			result.setMsg(result.STATUS_ERROR, "처리 도중 오류가 발생하였습니다.", ex);
			ex.printStackTrace();
		}
		return result.getResult();
	}

}