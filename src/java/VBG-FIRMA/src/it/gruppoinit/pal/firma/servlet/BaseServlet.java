package it.gruppoinit.pal.firma.servlet;

import it.gruppoinit.pal.firma.FileInfo;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = 2530657960653029396L;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private String overrideScheme = "";

    public BaseServlet() {

	InputStream is = null;
	try {
	    Properties prop = new Properties();
	    is = this.getClass().getClassLoader().getResourceAsStream("deploy.properties");
	    prop.load(is);
	    this.overrideScheme = prop.getProperty("baseurl.override.scheme.https");
	} catch (Exception e) {
	    log.error("Errore durante la creazione del FileManager", e);
	} finally {
	    if (is != null) {
		try {
		    is.close();
		} catch (Exception e) {
		}
	    }
	}
    }

    protected String getBaseUrl(HttpServletRequest request) {

	StringBuffer reqURL = request.getRequestURL();
	if (StringUtils.isNotBlank(overrideScheme)) {
	    String urlMod = reqURL.toString();
	    urlMod = urlMod.replaceFirst("http://", "https://");
	    reqURL = new StringBuffer(urlMod);
	}
	int start = reqURL.indexOf(request.getContextPath());
	int end = reqURL.length();
	String baseUrl = reqURL.replace(start, end, request.getContextPath()).toString();
	log.debug("getBaseUrl: {}", baseUrl);
	return baseUrl;
    }

    /**
     * {"files":[{"sessionId":"djkfj-khsfjkh-sdfjksh","fileId":"djkfjkhsfjkhsdfjksh","fileName":"file.txt"}]}
     * 
     * @param list
     * @return
     */
    protected String getJSON(List<FileInfo> list) {

	StringBuffer b = new StringBuffer("{\"files\":[");
	if (!list.isEmpty()) {
	    for (FileInfo fileInfo : list) {
		b.append("{\"sessionId\":\"").append(fileInfo.getSessionId());
		b.append("\",\"fileId\":\"").append(fileInfo.getFileId());
		b.append("\",\"fileName\":\"").append(fileInfo.getFileName());
		b.append("\"},");
	    }
	    b.deleteCharAt(b.length() - 1);
	}
	b.append("]}");
	String json = b.toString();
	log.debug(json);
	return json;
    }
}
