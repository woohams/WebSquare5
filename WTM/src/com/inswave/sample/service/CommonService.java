package com.inswave.sample.service;

import java.util.List;
import java.util.Map;

public interface CommonService {

	// 공통코드관리 조회
	public List selectCommonGroup(Map param);

	public List selectCommonCodeAll();

	public List selectCommonCodeList(Map param);

	public List selectCommonSearchItem();

	// 공통코드 조회
	public List<Map> selectCodeList(Map param);	
}
