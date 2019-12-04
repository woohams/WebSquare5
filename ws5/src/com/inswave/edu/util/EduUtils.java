package com.inswave.edu.util;

import java.util.Map;

public class EduUtils {
	
	/**
	 * 데이터의 타입이 String인지 int인지 확인하여 형 변환 후 반환 
	 * @param intData String|int
	 * @return int형 data
	 */
	public static int getInt(Object intData) throws Exception{
		int rs = 0;
		if(intData == null){
			
		}else if(intData instanceof String){
			rs = Integer.parseInt((String)intData, 10);
		}else{
			rs = (Integer)intData;
		}		
		return rs;
	}
	
	public static Map setErrorMessage(Map result) throws Exception{
		return setErrorMessage(result, "처리 도중 오류가 발생하였습니다.");
	}
	
	public static Map setErrorMessage(Map result, String message) throws Exception{
		result.put("msg", message);
		result.put("msgCode", "E");
		result.put("msgDetail", "WAS LOG를 확인하시기 바랍니다." );
		return result;
	}
	
}
