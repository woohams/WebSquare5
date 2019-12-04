package com.inswave.edu.provider;

import org.w3c.dom.Document;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.parser.JSONParser;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import websquare.http.controller.grid.excel.write.IExternalGridDataProvider;
import websquare.util.XMLUtil;
import websquare.http.WebSquareContext;

import com.inswave.edu.service.impl.EduServiceImpl;

public class ExcelDown implements IExternalGridDataProvider {
	
	public String[] getData(Document requestObj) throws Exception {
		JSONParser parse = new JSONParser();
		Map data = (Map)parse.parse(XMLUtil.getText(requestObj, "jsonData" ));
		String seqNo = XMLUtil.getText(requestObj, "seqNo");
		
		WebSquareContext context = WebSquareContext.getContext();
		// HttpServletRequest 객체 가져오기
		HttpServletRequest request = context.getRequest();
		// HttpSession 객체 가져오기
		HttpSession session = request.getSession();
		// ServletContext 객체 가져오기
		ServletContext sc = session.getServletContext();
		// Spring Context 가져오기
		WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(sc);
		// Service Call 방식
		EduServiceImpl eduService = (EduServiceImpl)wContext.getBean("eduService");
		
		ArrayList<Object> dataArr = new ArrayList();
		String[] returnData = null;
		
		try {
			Map req = new HashMap();
			Map res = new HashMap();
			req.put("SEQ_NO", seqNo);
			// data 조회 
			List dataList = eduService.selectZipCodeStreet(req);
			int _size = dataList.size();
			
			for(int i=0; i<_size; i++) {
				res = (Map) dataList.get(i);
				
				Set keySet = res.keySet();
				Iterator<String> keys = keySet.iterator();
				
				while (keys.hasNext()) {
					String key = (String)keys.next();
					if ( res.get(key) == null ){
						dataArr.add("");
					} else {
						dataArr.add(String.valueOf(res.get(key)));
					}
				}
			}
			returnData = new String[dataArr.size()];
			dataArr.toArray(returnData);
		}catch(Exception ex){
			System.out.println(ex);
			ex.printStackTrace();
		}finally {
			try {
				
			}catch(Exception ex){
				
			}finally {
				session = null;
			}
		}
		return returnData;
	}
}
