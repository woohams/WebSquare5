package com.inswave.edu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inswave.edu.util.WqLargeResultHandler;

@Repository("eduDao")
public class EduDao {
	@Autowired
	private SqlSession sqlSession;

	private static final String NS = "com.inswave.edu.dao.EduDao.";

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.selectEmpCd
	 * @return
	 * @throws Exception
	 */
	public int selectEmpCd() throws Exception{
		return sqlSession.selectOne(NS + "selectEmpCd");
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.insertSpMember
	 * @return
	 * @throws Exception
	 */
	public int insertSpMember(Map param) throws Exception{
		return sqlSession.insert(NS + "insertSpMember", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.insertSpMemberBatch
	 * @return
	 * @throws Exception
	 */
	public int insertSpMemberBatch(Map param) throws Exception{
		return sqlSession.insert(NS + "insertSpMemberBatch", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.updateSpMember
	 * @return
	 * @throws Exception
	 */
	public int updateSpMember(Map param) throws Exception{
		return sqlSession.update(NS + "updateSpMember", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.updateSpMemberBatch
	 * @return
	 * @throws Exception
	 */
	public int updateSpMemberBatch(Map param) throws Exception{
		return sqlSession.update(NS + "updateSpMemberBatch", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.deleteSpMember
	 * @return
	 * @throws Exception
	 */
	public int deleteSpMember(Map param) throws Exception{
		return sqlSession.delete(NS + "deleteSpMember", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.deleteSpMemberBatch
	 * @return
	 * @throws Exception
	 */
	public int deleteSpMemberBatch(Map param) throws Exception{
		return sqlSession.delete(NS + "deleteSpMemberBatch", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.selectOneSpMember
	 * @return
	 * @throws Exception
	 */
	public Map selectOneSpMember(Map param) throws Exception{
		return sqlSession.selectOne(NS + "selectOneSpMember", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.selectListSpMember
	 * @return
	 * @throws Exception
	 */
	public List selectListSpMember(Map param) throws Exception{
		return sqlSession.selectList(NS + "selectListSpMember", param);
	}

	/**
	 * com.inswave.edu.dao.EduDao.selectSpMemberTotal
	 * @return
	 * @throws Exception
	 */
	public int selectSpMemberTotal(Map param) throws Exception{
		return sqlSession.selectOne(NS + "selectSpMemberTotal", param);
	}
	
	public int selectSpMemberTotalScroll(Map param) throws Exception{
		return sqlSession.selectOne(NS + "selectSpMemberTotalScroll", param);
	}
	
	/**
	 * ResultHandler 사용
	 * com.inswave.edu.dao.EduDao.selectListSpMember
	 * @return
	 * @throws Exception
	 */
	public Map selectListSpMemberByHandler(Map param) throws Exception{
		return callDefaultResultHandler(NS + "selectListSpMember", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.selectListZipCodeByTown
	 * @return
	 * @throws Exception
	 */
	public List selectListZipCodeByTown(Map param) throws Exception{
		return sqlSession.selectList(NS + "selectListZipCodeByTown", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.selectCodeList
	 * @return
	 * @throws Exception
	 */
	public List selectCodeList(Map param) throws Exception{
		return sqlSession.selectList(NS + "selectCodeList", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.selectZipCodeStreet
	 * @return
	 * @throws Exception
	 */
	public List selectZipCodeStreet(Map param) throws Exception{
		return sqlSession.selectList(NS + "selectZipCodeStreet", param);
	}
	
	/**
	 * com.inswave.edu.dao.EduDao.selectZipCodeStreetSplit
	 * @return
	 * @throws Exception
	 */
	public List selectZipCodeStreetSplit(Map param) throws Exception{
		return sqlSession.selectList(NS + "selectZipCodeStreetSplit", param);
	}
	
	/**
	 * ResultHandler 사용
	 * com.inswave.edu.dao.EduDao.selectZipCodeStreet
	 * @return
	 * @throws Exception
	 */
	public Map selectZipCodeStreetByDefaultResultHandler(Map param) {
		return callDefaultResultHandler(NS + "selectZipCodeStreet", param);
	}
	
	
	/**
	 * resultHandler를 사용하여 Array 타입의 데이터 반환.
	 * @param mapperID mapper의 id
	 * @param param parameter
	 * @return Map
	 * {
	 * 	"columnInfo": [
	 * 		"column1",
	 * 		"column2"
	 * 	],
	 * 	"data": [
	 * 		row1-column1 data,
	 * 		row1-column2 data,
	 * 		row2-column1 data,
	 * 		row2-column2 data,
	 * 		row3-column1 data,
	 * 		row3-column2 data
	 * 	],
	 * 	"rowCount": "3",
	 * 	"colCount": "2"
	 * }
	 */
	public Map callDefaultResultHandler(String mapperID, Map param) {
		WqLargeResultHandler ldh = new WqLargeResultHandler();
		sqlSession.select(mapperID, param, ldh);
		return (Map)ldh.getResult();
	}

	/**
	 * com.inswave.edu.dao.EduDao.selectMultiLangCodeList
	 * @return
	 * @throws Exception
	 */
	public List selectMultiLangCodeList() throws Exception{
		return sqlSession.selectList(NS + "selectMultiLangCodeList");
	}
	
	
}
