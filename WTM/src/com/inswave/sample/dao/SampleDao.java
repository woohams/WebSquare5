package com.inswave.sample.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("sampleDao")
public interface SampleDao {

	// 샘플 목록 조회
	public List<Map> selectSample(Map param);

	// 리스트 조회
	public List<Map> select(Map param);

}
