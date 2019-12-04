package com.inswave.edu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebSquareViewControllor {
	private static final Logger LOGGER = Logger.getLogger("webApp");
	
	/**
	 * WebSquare 페이지 로딩 컨트롤
	 * websquare.jsp, popup.html, i18n.jsp 접근 시 컨트롤.
	 * 화면파일의 경로(w2xPath)가 없을 경우 index.xml파일을 default로 셋팅. 
	 * @param model
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = { "/", "/ws5.do", "websquare/popup.html", "websquare/*.do" })
	public String init(Model model, HttpServletRequest req, HttpServletResponse res){
		String contextPath = req.getContextPath();
		String url = null;
		String page = null;
		try {
			url = getPageUrl(req.getRequestURI());
			page = req.getParameter("w2xPath");
		} catch (Exception e) {
			
		} finally {
			if (page == null) {
				page = contextPath + "/index.xml";
			}
			model.addAttribute("page", page);
		}
		return url;
	}
	
	/**
	 * WebSquare 관련 URL인지 확인 후 경로 return.
	 * 기본 값은 websquare.jsp
	 * @param urlPath
	 * @return
	 */
	private static String getPageUrl(String urlPath) {
		String view = "websquare";
		String[] urlArr = {"I18N","popup", "screenView"};
		String url = urlPath.replaceAll("(.*websquare/)(.*)(.do|.html)", "$2");
		for(int i=0;i<urlArr.length;i++){
			if(urlArr[i].equals(url)){
				view = url;
				break;
			}
		}
		return "/websquare/"+view;
	}
	
	/**
	 * <pre>
	 * 파일 다운로드 
	 * 서블릿을 이용하여 지정된 파일을 다운로드 받을 수 있다.
	 * 
	 * @param param
	 *   - dma_fileInfo : 다운로드를 위한 파일 정보
	 *     - FILE_NM : 다운로드 받을 파일 이름
	 * </pre>	
	 */
	@RequestMapping(value="/edu/fileDownLoad.do", method=RequestMethod.POST)
	public String fileDownLoad(HttpServletRequest req, HttpServletResponse res) throws Exception{
		return "websquare/download";
	};	
	
}
