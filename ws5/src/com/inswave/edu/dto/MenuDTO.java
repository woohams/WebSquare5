/**
 * 
 */
package com.inswave.edu.dto;

import java.util.Map;

/**
 * @author jys
 *
 */
public class MenuDTO {
	private Map result = null;
	//private boolean 
	
	public MenuDTO(Map dto) {
		String tmpStr = null;
		
		if(dto == null){
			this.setStatus(false);
			return;
		}
		
		result = dto;
		
		String[] keyArr = {"TYPE", "TITLE", "PATH", "IS_USE"};
		int keyArrLen = keyArr.length;
		boolean isError = false;
		
		for(int i = 0; i<keyArrLen; i++){
			if(this.isNull(keyArr[i])){
				isError = true;
				break;
			}
		}
		
		if(isError){
			this.setStatus(false);
			return;
		}
		
		if(this.isNull("P_ID")){
			result.put("P_ID", null);
		}
		
		if(this.isNull("ID")){
			result.put("ID", null);
		}
		
		
		//tmpStr = (String)result.get("ID");
		//tmpStr = (String)result.get("P_ID");
		//tmpStr = (String)result.get("DEPTH");
		//tmpStr = (String)result.get("SORT_ORDER");
		//tmpStr = (String)result.get("REMARK");
	}
	
	private boolean isNull(String key){
		Object tmp = result.get(key);
		if(tmp instanceof String){
			tmp = ((String) tmp).trim();
			if(!tmp.equals("")){
				result.put(key, tmp);
				return false;	
			}
		}
		return true;
	};
	
	private void setDefaultValue(){
		//result.put("", "");
	};
	
	
	private void setStatus(boolean isValid){
		this.result.put("isValid", isValid);
	};
	
	public Map getResult(){
		return result;
	}; 
	
}
