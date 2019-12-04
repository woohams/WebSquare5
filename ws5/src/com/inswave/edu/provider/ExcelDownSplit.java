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

import websquare.http.controller.grid.excel.write.IExternalSplitProvider;
import websquare.util.XMLUtil;
import websquare.http.WebSquareContext;

import com.inswave.edu.service.impl.EduServiceImpl;

public class ExcelDownSplit implements IExternalSplitProvider {
	
	int cnt = 0;
    int limtcnt = 0;
    int startNum = 0;
    int endNum = 1000;
    boolean isEnd = false;
	
	public String[] getData(Document requestObj) throws Exception {
		JSONParser parse = new JSONParser();
		Map data = (Map)parse.parse(XMLUtil.getText(requestObj, "jsonData" ));
		String seqNo = XMLUtil.getText(requestObj, "seqNo");
		
		limtcnt = Integer.parseInt(seqNo)/1000;
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
			// 1000 row씩 가져오기 셋팅
			req.put("START_NUM", startNum);
			req.put("END_NUM", endNum);
			// data 조회 
			List dataList = eduService.selectZipCodeStreetSplit(req);
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

		this.cnt++;
		if(this.startNum == 0) {
			this.startNum = 1001;
		}else {
			this.startNum = this.startNum + 1000;
		}
		this.endNum = this.endNum + 1000;
        if( this.cnt >= this.limtcnt ){
            this.isEnd = true;
        }
		
		return returnData;
	}
	
	// sendCompleted 가 true를 리턴하면 getData() 를 더 호출하지 않고 종료된다
    public boolean sendCompleted() throws Exception {
        //System.out.println(Integer.toString(this.cnt*100) + "row 생성 ");
        return isEnd;
    }
}
