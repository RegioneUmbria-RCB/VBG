package it.gruppoinit.pal.gp.cartfacct.domain;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class HttpMultipartRequestParameterMap {

    Map<String, HttpMultipartRequestParameterValue> parametersMap = new HashMap<String, HttpMultipartRequestParameterValue>();

    public void addRequestParameterValue(String paramName, String paramValue) {

	HttpMultipartRequestParameterValue valueObj = this.parametersMap.get(paramName);
	if (null == valueObj) {
	    valueObj = new HttpMultipartRequestParameterValue(paramValue);
	    this.parametersMap.put(paramName, valueObj);
	} else {
	    valueObj.addStringValue(paramValue);
	}
    }

    public void addRequestParameterValue(String paramName, FileItem paramValue) {

	HttpMultipartRequestParameterValue valueObj = this.parametersMap.get(paramName);
	if (null == valueObj) {
	    valueObj = new HttpMultipartRequestParameterValue(paramValue);
	    this.parametersMap.put(paramName, valueObj);
	} else {
	    valueObj.addFileValue(paramValue);
	}
    }

    public HttpMultipartRequestParameterValue getParameterValue(String paramName) {

	HttpMultipartRequestParameterValue retVal = this.parametersMap.get(paramName);
	return retVal;
    }

    public String[] getStringParameterValues(String paramName) {

	HttpMultipartRequestParameterValue val = this.parametersMap.get(paramName);
	String[] retVals = null;
	if (null != val) {
	    retVals = val.getStringValues();
	}
	return retVals;
    }

    public String getStringParameter(String paramName) {

	HttpMultipartRequestParameterValue val = this.parametersMap.get(paramName);
	String retVal = null;
	String[] vals = null;
	if (null != val) {
	    vals = val.getStringValues();
	    if (vals.length > 0) {
		retVal = vals[0];
	    }
	}
	return retVal;
    }

    public FileItem[] getFileParameterValues(String paramName) {

	HttpMultipartRequestParameterValue val = this.parametersMap.get(paramName);
	FileItem[] retVals = null;
	if (null != val) {
	    retVals = val.getFileValues();
	}
	return retVals;
    }

    public FileItem getFileParameter(String paramName) {

	HttpMultipartRequestParameterValue val = this.parametersMap.get(paramName);
	FileItem[] vals = null;
	FileItem retVal = null;
	if (null != val) {
	    vals = val.getFileValues();
	    if(vals.length > 0){
		retVal = vals[0];
	    }
	}
	return retVal;
    }

    public boolean hasParameter(String parameterName) {

	return this.parametersMap.containsKey(parameterName);
    }

    public static HttpMultipartRequestParameterMap extractParametersFromMultipartRequest(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException {

	HttpMultipartRequestParameterMap parametersMap = new HttpMultipartRequestParameterMap();
	if (ServletFileUpload.isMultipartContent(request)) {
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    //TODO verificare se è opportuno settare i parametri del DiskFileItemFactory (SizeThreshold, Repository)
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    List<FileItem> items = upload.parseRequest(request);
	    for (FileItem fileItem : items) {
		if (fileItem.isFormField()) {
		    parametersMap.addRequestParameterValue(fileItem.getFieldName(), fileItem.getString(request.getCharacterEncoding()));
		} else {
		    parametersMap.addRequestParameterValue(fileItem.getFieldName(), fileItem);
		}
	    }
	} else {
	    Enumeration<String> paramNamesEnum = request.getParameterNames();
	    while (paramNamesEnum.hasMoreElements()) {
		String paramName = (String) paramNamesEnum.nextElement();
		String[] paramValues = request.getParameterValues(paramName);
		if (paramValues != null) {
		    for (int i = 0; i < paramValues.length; i++) {
			parametersMap.addRequestParameterValue(paramName, paramValues[i]);
		    }
		}
	    }
	}
	return parametersMap;
    }
}
