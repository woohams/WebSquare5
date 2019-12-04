package com.inswave.edu.lang;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import websquare.i18n.AbstractMessage;
import websquare.logging.util.LogUtil;

public class MultiLangMessageImpl extends AbstractMessage {
	
	public Hashtable initializeProperty(int storageType, String path) throws Exception {
		Hashtable languageHash = null;

		try {
			languageHash = this.loadDBResource();
			
		} catch (Exception arg4) {
			LogUtil.exception("[MulTilLangMessageImpl.loadProperty] exception occured.", arg4);
		}

		return languageHash;
	}

	public Hashtable loadProperty(int storageType, HttpServletRequest request, String path) throws Exception {
		Hashtable languageHash = new Hashtable();

		try {
			languageHash = this.loadDBResource();
		} catch (Exception arg5) {
			LogUtil.exception("[MulTilLangMessageImpl.loadProperty] exception occured.", arg5);
		}

		return languageHash;
	}

	private Hashtable loadDBResource() throws Exception {
		Hashtable languageHash = new Hashtable();

		try {
			
			String resource = "com/inswave/edu/provider/mybatis-mutifactor-config.xml";
			SqlSession session = null;
			
			try {
				InputStream inputStream = Resources.getResourceAsStream(resource);
				SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
				session = sqlSessionFactory.openSession();
				List result = session.selectList("com.inswave.edu.dao.EduDao.selectMultiLangCodeList");
				
				int resultSize = result.size();
				for ( int i = 0; i < resultSize; i++ ){
					Map data = (Map)result.get(i);
					String locale = (String)data.get("LOCALE");
					String key = (String)data.get("KEY");
					String label = (String)data.get("LABEL");
					Hashtable codeList = (Hashtable)languageHash.get(locale);
					if ( codeList == null ){
						codeList = new Hashtable();
					}
					codeList.put(key, label);
					languageHash.put(locale, codeList);
					
				}
				
			}catch(Exception ex){
				System.out.println(ex);
				ex.printStackTrace();
			}finally {
				try {
					session.close();
				}catch(Exception ex){
					
				}finally {
					session = null;
				}
			}			

			LogUtil.info("[MulTilLangMessageImpl.loadFileResource] loadFileResource() success.");
		} catch (Exception arg6) {
			LogUtil.exception("[MulTilLangMessageImpl.loadFileResource] exception occured.", arg6);
		}

		return languageHash;
	}
}