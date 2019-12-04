package com.inswave.sample.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CommonDao {

	public List selectCommonSearchItem();

	// 공통코드 및 코드 그룹 조회
	public List selectCommonGroup(Map param);

	public List selectCommonCode();

	public List selectCommonCodeList(Map param);

	// 공통코드
	public List<Map> selectCodeList(Map param);

}