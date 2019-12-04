package com.inswave.edu.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Base64Utils;

public class FileHandler{
	public static Map uploadBLOBImage(Map param) throws Exception {
		Map<String, Object> resObj = null;
		byte[] image;
		String folderPath = null;
		String fileName = null;
		String blobData = (String) param.get("data");
		boolean isFailed = false;
		String[] arrData;
		try{
			if(blobData != null){
				arrData = blobData.split(",");
				System.out.println(arrData[0]);
				
				
				image = Base64Utils.decodeFromString(arrData[1]);
				
				folderPath = WqConfig.getDefFolder01();
				fileName = getRendomFileName();
				
				FileUtils.writeByteArrayToFile(new File(folderPath+fileName+".png"), image);
				
			}
		}catch(Exception e){
			isFailed = true;
			e.printStackTrace();
		}finally {
			resObj = new HashMap<String,Object>();
			resObj.put("folderPath", folderPath);
			resObj.put("fileName", fileName);
			resObj.put("isFailed", isFailed);
		}
		
		return resObj;
	}
	
	private static String getRendomFileName(){
		String prefix = "WEBSQUARE5";
		Integer.toHexString(0xFF & prefix.charAt(new Random().nextInt(10)));
		return  System.nanoTime()+""+Integer.toHexString(0xFF & prefix.charAt(new Random().nextInt(10)));
	}
}
