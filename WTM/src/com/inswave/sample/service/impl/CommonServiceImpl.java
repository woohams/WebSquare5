package com.inswave.sample.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inswave.sample.dao.CommonDao;
import com.inswave.sample.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

	@Resource(name = "commonDao")
	private CommonDao commonDao;

	/**
	 * 공통그룹 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	@Override
	public List selectCommonGroup(Map param) {
		return commonDao.selectCommonGroup(param);
	}

	/**
	 * 모든 공통코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	@Override
	public List selectCommonCodeAll() {
		return commonDao.selectCommonCode();
	}

	/**
	 * 공통코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	@Override
	public List selectCommonCodeList(Map param) {
		return commonDao.selectCommonCodeList(param);
	}

	/**
	 * 공통코드 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	@Override
	public List<Map> selectCodeList(Map param) {
		return commonDao.selectCodeList(param);
	}

	/**
	 * 공통관리 조회(검색어)
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	@Override
	public List selectCommonSearchItem() {
		return commonDao.selectCommonSearchItem();
	}	
}
