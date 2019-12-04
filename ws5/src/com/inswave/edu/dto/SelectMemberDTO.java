package com.inswave.edu.dto;

public class SelectMemberDTO {
	private String GENDER_CD;
	private String POSITION_CD;
	
	public String getGENDER_CD() {
		return GENDER_CD;
	}
	public void setGENDER_CD(String gENDER_CD) {
		GENDER_CD =  this.getNull(gENDER_CD);
	}
	public String getPOSITION_CD() {
		return POSITION_CD;
	}
	public void setPOSITION_CD(String pOSITION_CD) {
		POSITION_CD = this.getNull(pOSITION_CD);
	}
	private String getNull(String value){
		String obj = (String)value;
		if(obj == null || obj.trim().equals("")){
			return null;
		}
		return value.replaceAll(" ", "");
	};
}
