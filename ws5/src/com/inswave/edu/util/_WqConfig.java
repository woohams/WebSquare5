package com.inswave.edu.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class _WqConfig {
	private static final Logger LOGGER = Logger.getLogger("webApp");
	private static final String FILE_PATH = "config/ws5_properties.xml";
	private static String uploadRootPath;
	private static String classPath;
	private static Properties ws5prop;
	private static boolean isInitDone = false;

	private static void setInstance() {
		String filePath = null;
		ws5prop = new Properties();
		try {
			classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			filePath = classPath + FILE_PATH;
			
			ws5prop.loadFromXML(FileUtils.openInputStream(new File(filePath)));
			
			

			setInit();
			isInitDone = true;

		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (RuntimeException rex) {
			rex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isInitDone) {
				LOGGER.error("check property file : " + filePath);
			} else {
				LOGGER.info("WqConfig is loaded : " + filePath);
			}
		}
	}

	private static void setInit() {
		uploadRootPath = get("edu.file.rootPath");
	};

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
	
/*	@PostConstruct
	public void postConstruct(){
		String filePath = null;
		ws5prop = new Properties();
		try {
			classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			filePath = classPath + FILE_PATH;
			LOGGER.info(filePath);
			
			ws5prop.loadFromXML(FileUtils.openInputStream(new File(filePath)));
			LOGGER.info(ws5prop);
			isInitDone = true;
			setInit();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (RuntimeException rex) {
			rex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isInitDone) {
				LOGGER.error("check property file : " + filePath);
			} else {
				LOGGER.info("WqConfig is loaded : " + filePath);
			}
		}
	}*/

}
