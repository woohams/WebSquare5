package com.inswave.edu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inswave.edu.dto.SelectMemberDTO;

@Repository("trainingDao")
public class TrainingDao {
	@Autowired
	private SqlSession sqlSession; 

	private static final String NS = "com.inswave.edu.dao.TrainingDao.";

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;  
	}
	
	/**
	 * com.inswave.edu.dao.trainingDao.getUserInfo
	 * @return
	 * @throws Exception
	 */
	public Map getUserInfo(Map param) throws Exception{
		return sqlSession.selectOne(NS + "getUserInfo", param); 
	}
	
	/**
	 * com.inswave.edu.dao.trainingDao.selectMember
	 * @return
	 * @throws Exception
	 */
	public List selectMember(SelectMemberDTO param) throws Exception{
		return sqlSession.selectList(NS + "selectMember",param); 
	}
	
	/**
	 * com.inswave.edu.dao.trainingDao.selectCodeList
	 * @return
	 * @throws Exception
	 */
	public List selectCodeList(Map param) throws Exception{
		return sqlSession.selectList(NS + "selectCodeList", param);
	}
	
}
