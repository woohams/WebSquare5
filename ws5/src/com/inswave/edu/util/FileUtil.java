package com.inswave.edu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;
import org.w3c.dom.Document;

import websquare.util.XMLUtil;

public class FileUtil extends AbstractView {

	public FileUtil() {
		setContentType("application/download; charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		String param = request.getParameter("xmlValue");
		
		Document fileDoc = XMLUtil.getDocument(param);
		String fileNm = XMLUtil.getText(fileDoc, "FILE_NM");	
		

		
		response.setContentType(getContentType());
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + fileNm + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = response.getOutputStream();
		FileInputStream fin = null;
		File file = null;

		try {
			file = new File (WqConfig.getFileDownPath()+File.separator+fileNm);
			fin = new FileInputStream(file);
			FileCopyUtils.copy(fin, out);
		} finally {
			try { fin.close(); } catch (IOException e) { e.printStackTrace();}
		}
		out.flush();
	}

}
