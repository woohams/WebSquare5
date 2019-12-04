package com.inswave.edu.util;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class WqConfig {
	private static final Logger LOGGER = Logger.getLogger("webApp");
	private static boolean isInitDone = false;
	private static Properties ws5prop;
	private static String uploadRootPath;
	public static String F01;
	
	
	@Resource(name="ws5prop")
	private void setWs5prop(Properties ws5prop) {
		WqConfig.ws5prop = ws5prop;
	}
	
	@PostConstruct
	public void postConstruct(){
		try {
			isInitDone = true;
			uploadRootPath = get("edu.file.rootPath");
		} catch (RuntimeException rex) {
			rex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isInitDone || ws5prop == null) {
				LOGGER.error("WqConfig initialization failed");
			} else {
				LOGGER.info("WqConfig is loaded");
				LOGGER.info(ws5prop.toString());
			}
		}
	}
	
	private static String get(String key) {
		String rs = null;
		if (isInitDone) {
			try {
				rs = ws5prop.getProperty(key);
			} catch (Exception ex) {
				LOGGER.error("check property key : " + key);
			}
		}else{
			LOGGER.error("WqConfig initialization failed.");
			throw new RuntimeException();
		}
		return rs;
	}

	
	public static String getDefFolder01() throws Exception {
		return uploadRootPath + get("edu.file.folder01");
	}

	public static String getFileDownPath() throws Exception {
		return get("edu.file.downPath");
	}	

}
