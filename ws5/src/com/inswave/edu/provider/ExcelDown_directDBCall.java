package com.inswave.edu.provider;

import java.io.InputStream;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;

import websquare.http.controller.grid.excel.write.IExternalGridDataProvider;
import websquare.util.XMLUtil;

public class ExcelDown_directDBCall implements IExternalGridDataProvider {

	public String[] getData(Document requestObj) throws Exception {
		
		
		//System.out.println(XMLUtil.indent(requestObj));
		JSONParser parse = new JSONParser();
		Map data = (Map)parse.parse(XMLUtil.getText(requestObj, "jsonData" ));
		String sqlId = XMLUtil.getText(requestObj, "sqlId");
		//System.out.println("==========================================");
		//System.out.println("provider sql : "+sqlId);
		//System.out.println("==========================================");
		String resource = "com/inswave/edu/provider/mybatis-mutifactor-config.xml";
		SqlSession session = null;
		
		WqExcelProviderHandler wph = new WqExcelProviderHandler();
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			session.select(sqlId, data,wph);
			
//			System.out.println("Provider totalCellCount : "+((String[])wph.getResult()).length);
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
		return (String[])wph.getResult();
	}


}
