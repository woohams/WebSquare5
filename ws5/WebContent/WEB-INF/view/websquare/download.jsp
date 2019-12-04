<%@page import="org.apache.commons.io.FileUtils"%>
<%@ page language="java" contentType="application/download; charset=utf-8"  pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8");%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.security.*" %>
<%@ page import="org.w3c.dom.*" %>
<%@ page import="websquare.util.*" %>
<%@ page import="com.inswave.edu.util.*" %>
<%@ page import="org.springframework.util.FileCopyUtils" %>
<%
		String param = request.getParameter("xmlValue");
		
		Document fileDoc = XMLUtil.getDocument(param);
		String fileNm = XMLUtil.getText(fileDoc, "FILE_NM");	
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + new String (fileNm.getBytes("euc-kr"), "8859_1") + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		out.clear(); // getOutputStream() has already been called for this response -> 발생시 필요함
		
		File file = null;
        BufferedInputStream fin = null;
        BufferedOutputStream fout = null;
		try {
			file = new File (WqConfig.getFileDownPath()+File.separator+fileNm);
			
	        fin = new BufferedInputStream(new FileInputStream(file) );
	        fout = new BufferedOutputStream(response.getOutputStream());
	        
			FileCopyUtils.copy(fin, fout);
			
		} finally {
			try { fin.close(); } catch (IOException e) { e.printStackTrace();}
			try { fout.close(); } catch (IOException e) { e.printStackTrace();}
			
		}
		
%>