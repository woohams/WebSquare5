package com.inswave.sample.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.inswave.sample.dao.SampleDao;
import com.inswave.sample.service.SampleService;

@Service
public class SampleServiceImpl implements SampleService {

	
	@Resource(name = "sampleDao")
	private SampleDao sampleDao;

	/**
	 * 샘플목록 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	@Override
	public List<Map> selectSample(Map param) {
		return sampleDao.selectSample(param);
	}

	/**
	 * 조회
	 * 
	 * @param param Client 전달한 데이터 맵 객체
	 */
	@Override
	public List select(Map param) {
		return sampleDao.select(param);
	}
}
