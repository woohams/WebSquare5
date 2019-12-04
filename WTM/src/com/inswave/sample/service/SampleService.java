package com.inswave.sample.service;

import java.util.List;
import java.util.Map;

public interface SampleService {

	// 샘플목록 조회
	public List<Map> selectSample(Map param);
	
	// 리스트 조회
	public List select(Map param);

}
