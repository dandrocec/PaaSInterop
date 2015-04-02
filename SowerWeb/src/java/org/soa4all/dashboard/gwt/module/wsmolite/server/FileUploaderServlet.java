package org.soa4all.dashboard.gwt.module.wsmolite.server;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PutMethod;


@SuppressWarnings("serial")
public class FileUploaderServlet extends HttpServlet {
	    
		   
	    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	        
	        res.setContentType("text/plain");
	        String url = null, contentType = "text/plain";
	        byte[] content = null;
	        ServletFileUpload parser = new ServletFileUpload(
	        		new DiskFileItemFactory(2 * 1024 * 1024, new File(".")));
	        try {
	        	for(Object fileItemO : parser.parseRequest(req)) {
	        		FileItem fileItem = (FileItem)fileItemO;
	        		if ("url".equals(fileItem.getFieldName())) {
	        			url = new String(fileItem.get());
	        			continue;
	        		}
	        		if ("file".equals(fileItem.getFieldName())) {
	        		    content = fileItem.get();
	        		    contentType = fileItem.getContentType();
	        		}
	        	}
	        	
	        	if (url == null) {
	        		throw new Exception("No remote url provided");
	        	}
	        	if (content == null) {
	        		throw new Exception("No file content provided");
	        	}
	        	putFile(url, content, contentType);
	        	res.setStatus(200);
	        }
	        catch(Exception ex) {
	        	ex.printStackTrace();
	        	res.sendError(500, ex.getMessage());
	        }
	    } // doPost
	    
	    public void doGet(HttpServletRequest req, HttpServletResponse res) 
	    throws IOException, ServletException {
	    	doPost(req, res);
	    }
	    
	    public void putFile(String requestURL, byte[] data, String contentType) throws Exception {
	        
	    	System.out.println("Saving file: " + requestURL + " \n " + data.length + " bytes");
	    	
	        PutMethod putMtd = new PutMethod(requestURL);
	        HttpClient httpclient = new HttpClient();
	        if (contentType == null) {
	        	contentType = "text/plain";
	        }
	        try {
	            putMtd.setRequestEntity(new ByteArrayRequestEntity(data, contentType));
	            
	            int result = httpclient.executeMethod(putMtd);
	            if (result != 200) {
	                throw new Exception("Error "+ result + " : " + putMtd.getResponseHeader("Error"));
	            }
	        } finally {
	            putMtd.releaseConnection();
	        }
	    }
	} 
