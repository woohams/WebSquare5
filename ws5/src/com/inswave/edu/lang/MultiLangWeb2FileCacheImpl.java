package com.inswave.edu.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import websquare.WebSquareConfig;
import websquare.i18n.AbstractWeb2File;
import websquare.i18n.I18NUtil;
import websquare.i18n.LabelMessageLoader;
import websquare.logging.util.LogUtil;
import websquare.util.StreamUtil;
import websquare.util.XMLUtil;

public class MultiLangWeb2FileCacheImpl  extends AbstractWeb2File {

	private Hashtable w2xPathCache = new Hashtable();
	private String[] excludeList;
	private String GMLXMLBaseDir = "GMLXMLBaseDir";
	private String GMLXMLBaseUrl = "";
	private boolean useCache = false;
	private int storageType = 0;

	public MultiLangWeb2FileCacheImpl() {
		try {
			this.initialize();
		} catch (Exception arg1) {
			LogUtil.exception("[MultilLangWeb2FileCacheImpl] Exception occurs :", arg1);
		}

	}

	private void initialize() throws Exception {
		try {
			WebSquareConfig e = WebSquareConfig.getInstance();
			this.GMLXMLBaseDir = e.getStringValue("/websquare/i18n/xmlInfo/@baseDir", "");
			this.GMLXMLBaseUrl = e.getStringValue("/websquare/i18n/xmlInfo/@baseUrl", "url");
			String strCache = e.getStringValue("/websquare/i18n/xmlInfo/@cache", "false");
			if (strCache != null && strCache.toLowerCase().equals("true")) {
				this.useCache = true;
			}

			// storageType 0:properties, 1:DB
			String storageTypeStr = e.getStringValue("/websquare/i18n/xmlInfo/@storageType", "0");
			this.storageType = Integer.parseInt(storageTypeStr);
			String excludeStr = e.getStringValue("/websquare/i18n/xmlInfo/excludeList/@value", "");
			if (excludeStr.length() > 0) {
				this.excludeList = excludeStr.split(",");
			}

			LogUtil.severe(
					"[MultilLangWeb2FileCacheImpl.initialize] ###################initialize DefaultWeb2FileCacheImpl #####################");
			LogUtil.severe("[MultilLangWeb2FileCacheImpl.initialize] baseDir            : " + this.GMLXMLBaseDir);
			LogUtil.severe("[MultilLangWeb2FileCacheImpl.initialize] url  \t\t\t\t: " + this.GMLXMLBaseUrl);
			LogUtil.severe("[MultilLangWeb2FileCacheImpl.initialize] useCache        \t: " + this.useCache);
			LogUtil.severe("[MultilLangWeb2FileCacheImpl.initialize] storageType  \t\t: " + this.storageType);
		} catch (Exception arg4) {
			LogUtil.exception("[MultilLangWeb2FileCacheImpl.initialize] Exception occurs", arg4);
			throw arg4;
		}
	}

	public void cacheClear() {
		LogUtil.info("[MultilLangWeb2FileCacheImpl.cacheClear] start.");
		LogUtil.info("[MultilLangWeb2FileCacheImpl.cacheClear] size : " + this.w2xPathCache.size());
		this.w2xPathCache.clear();
		this.excludeList = null;
		LogUtil.info("[MultilLangWeb2FileCacheImpl.cacheClear] size : " + this.w2xPathCache.size());
		LogUtil.info("[MultilLangWeb2FileCacheImpl.cacheClear] end.");
	}

	public String getXML(HttpServletRequest request) throws Exception {
		String result = "";
		String contextPath  = request.getContextPath();

		try {
			String e = I18NUtil.getLocale();
			String fullFilePath = this.getW2xPathFullFilePath(request);
			LogUtil.fine("[MultilLangWeb2FileCacheImpl.getXML] fullFilePath :" + fullFilePath);
			if (fullFilePath != null && fullFilePath.length() != 0) {
				boolean isExclude = false;
				if (this.excludeList != null) {
					for (int localeHash = 0; localeHash < this.excludeList.length; ++localeHash) {
						if (fullFilePath.indexOf(this.excludeList[localeHash].trim()) > -1) {
							isExclude = true;
							break;
						}
					}

					if (isExclude) {
						LogUtil.info("[MultilLangWeb2FileCacheImpl.getXML] w2xPath 요청은 유효하지 않은 요청입니다.");
						return result;
					}
				}

				if (this.useCache) {
					Hashtable arg7 = (Hashtable) this.w2xPathCache.get("FILE_" + fullFilePath);
					if (arg7 == null) {
						arg7 = new Hashtable();
						this.w2xPathCache.put("FILE_" + fullFilePath, arg7);
					}

					result = (String) arg7.get(e);
					if (result == null) {
						result = this.getContents(fullFilePath, e, request);
						arg7.put(e, result);
					}
				} else {
					result = this.getContents(fullFilePath, e, request);
				}

				return result;
			} else {
				LogUtil.info("[MultilLangWeb2FileCacheImpl.getXML] w2xPath File 이름이 입력되지 않았습니다. 파일이름을 확인하세요");
				return result;
			}
		} catch (Exception arg6) {
			LogUtil.exception("[MultilLangWeb2FileCacheImpl.getW2xPath] Exception occurs.", arg6);
			throw arg6;
		}
	}

	private String getW2xPathFullFilePath(HttpServletRequest request) throws Exception {
		String fullFilePath = null;
		String contextPath = request.getContextPath();
		try {
			String e = request.getParameter("w2xPath");
			
			int iPosition = e.indexOf("?");
			if ( iPosition > -1){
				e = e.substring(0, e.indexOf("?"));
			}			
			
			if (e == null) {
				LogUtil.info(
						"[MultilLangWeb2FileCacheImpl.getW2xPathFullFilePath] w2xPath File 이름이 입력되지 않았습니다. 파일이름을 확인하세요");
				return null;
			} else {
				int idx = e.lastIndexOf(".");
				if (idx == -1) {
					LogUtil.info(
							"[MultilLangWeb2FileCacheImpl.getW2xPathFullFilePath] w2xPath File 이름이 올바르지 않습니다. 파일이름을 확인하세요");
					return null;
				} else {
					
					if ( contextPath.length() > 0 && e.startsWith( contextPath+"/")){
						e = e.substring(contextPath.length());
					}

					if (this.storageType == 3) {
						fullFilePath = this.GMLXMLBaseUrl + e;
					} else {
						fullFilePath = this.GMLXMLBaseDir + e;
					}

					if (this.storageType == 0  || this.storageType == 1) {
						File f = new File(fullFilePath);
						String canoPath = f.getCanonicalPath();
						if (canoPath.indexOf(this.GMLXMLBaseDir) != 0) {
							LogUtil.info(
									"[MultilLangWeb2FileCacheImpl.getW2xPathFullFilePath] The file name is incorrect. Check the file name. \nfullFilePath : "
											+ fullFilePath + "\nCanonicalPath : " + canoPath);
							fullFilePath = null;
						}
					}

					return fullFilePath;
				}
			}
		} catch (Exception arg6) {
			LogUtil.exception("[MultilLangWeb2FileCacheImpl.getW2xPathFullFilePath] Exception occurs.", arg6);
			throw arg6;
		}
	}

	public String getContents(String fullFilePath, String locale, HttpServletRequest request) throws Exception {
		String result = "";
		Object is = null;

		try {
			LogUtil.fine("[MultilLangWeb2FileCacheImpl.getContents] storageType:" + this.storageType);
			LogUtil.fine("[MultilLangWeb2FileCacheImpl.getContents] fullFilePath:" + fullFilePath);

			is = new FileInputStream(fullFilePath);

			String e = StreamUtil.getString((InputStream) is, "UTF-8");
			LogUtil.fine("[MultilLangWeb2FileCacheImpl.getContents] fileStr:" + e);
			String regex = "[!][~]";
			String[] strArray = e.split(regex);
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < strArray.length; ++i) {
				String token = strArray[i];
				int lastIndex = token.indexOf("~!");
				if (lastIndex > -1) {
					String key = token.substring(0, lastIndex);
					String value = "";
					value = LabelMessageLoader.getInstance().getMessage(request, key, locale);
					value = XMLUtil.XMLEncoder(value);
					String rest = token.substring(lastIndex + 2);
					sb.append(value).append(rest);
				} else {
					sb.append(token);
				}
			}

			result = sb.toString();
			return result;

		} catch (Exception arg24) {
			LogUtil.exception("[MultilLangWeb2FileCacheImpl.getContents] Exception occurs.", arg24);
			throw arg24;
		} finally {
			try {
				((InputStream) is).close();
			} catch (Exception arg23) {
				;
			}

		}
	}

	public int getStorageType() {
		return this.storageType;
	}

	public void setStorageType(int storageType) {
		this.storageType = storageType;
	}	
	
}
