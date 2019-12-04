package com.inswave.edu.provider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class WqExcelProviderHandler implements ResultHandler {

	private ArrayList<Object> dataArr = null;
	private String[] returnData = null;

	/**
	 * Data객체는 getResult()를 호출하여 반환받는다.
	 */
	public WqExcelProviderHandler() {
		dataArr = new ArrayList();
	}
	
	
	public void handleResult(ResultContext context) {

		LinkedHashMap data = (LinkedHashMap)context.getResultObject();
		
		Set keySet = data.keySet();
		Iterator<String> keys = keySet.iterator();

		while (keys.hasNext()) {
			
			String key = (String)keys.next();
			if ( data.get(key) == null ){
				dataArr.add("");
			} else {
				
				dataArr.add(String.valueOf(data.get(key)));
			}
		}

	}
	
	
	/**
	 * String[] 데이타를 반환한다.
	 * @date 2016. 8. 17.
	 * @returns <Object> String[]
	 * @author InswaveSystems
	 * @example 
	 * @todo 추가해야 할 작업
	 */
	public Object getResult() {
		returnData = new String[dataArr.size()];
		dataArr.toArray(returnData);
		return returnData;
	}	

}
