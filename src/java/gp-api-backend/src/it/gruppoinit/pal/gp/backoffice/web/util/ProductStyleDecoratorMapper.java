/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web.util;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.ConfigDecoratorMapper;
import com.opensymphony.module.sitemesh.mapper.ConfigLoader;

/**
 * Implementazione personalizzata del DecoratorMapper di symphony che carica la configurazione dei decorators da file
 * differenti in base al valore della proprietà product.name delle proprietà di connessione. Se la proprietà non è
 * impostata o se il suo valore non consente di identificare il file di configurazione da caricare allora viene caricato
 * il decorator in base al comportamento solito di com.opensymphony.module.sitemesh.mapper.ConfigDecoratorMapper, ovvero
 * viene utilizzato il file di configurazione specificato in web.xml
 * 
 * @author francol
 *
 */
public class ProductStyleDecoratorMapper extends ConfigDecoratorMapper {

    private static final Logger log = LoggerFactory.getLogger(ProductStyleDecoratorMapper.class);
    private Map<String, ConfigLoader> styleConfigCache = new HashMap<String, ConfigLoader>();
    public static String OVVERIDE_DECORATOR_IN_SESSION = "_OVVERIDE_DECORATOR_IN_SESSION_";

    /* (non-Javadoc)
     * @see com.opensymphony.module.sitemesh.mapper.ConfigDecoratorMapper#getDecorator(javax.servlet.http.HttpServletRequest, com.opensymphony.module.sitemesh.Page)
     */
    @Override
    public Decorator getDecorator(HttpServletRequest request, Page page) {

	Decorator decor = null;
	ConfigLoader cl = getStyleConfig(request);
	if (cl != null) {
	    String thisPath = request.getServletPath();
	    // getServletPath() returns null unless the mapping corresponds to a servlet
	    if (thisPath == null) {
		String requestURI = request.getRequestURI();
		if (request.getPathInfo() != null) {
		    // strip the pathInfo from the requestURI
		    thisPath = requestURI.substring(0, requestURI.indexOf(request.getPathInfo()));
		} else {
		    thisPath = requestURI;
		}
	    } else if ("".equals(thisPath)) {
		// in servlet 2.4, if a request is mapped to '/*', getServletPath returns null (SIM-130)
		thisPath = request.getPathInfo();
	    }
	    String name = null;
	    try {
		name = cl.getMappedName(thisPath);
		if (name != null) {
		    decor = cl.getDecoratorByName(name);
		} else {
		    decor = super.getDecorator(request, page);
		}
	    } catch (ServletException e) {
		log.error("getDecorator - errore nella creazione del decorator per il path: " + thisPath);
	    }
	}
	if (decor == null) {
	    decor = super.getDecorator(request, page);
	}
	return decor;
    }

    private ConfigLoader getStyleConfig(HttpServletRequest request) {

	String style = ORMHelper.getProductStyle();
	ConfigLoader cl = null;
	String overrideDecorator = request.getParameter(OVVERIDE_DECORATOR_IN_SESSION);
	if (StringUtils.isNotEmpty(overrideDecorator)) {
	    request.getSession().setAttribute(OVVERIDE_DECORATOR_IN_SESSION, overrideDecorator);
	}
	String paramInSession = (String) request.getSession().getAttribute(OVVERIDE_DECORATOR_IN_SESSION);
	if (StringUtils.isNotBlank(paramInSession)) {
	    style = paramInSession;
	}
	if (StringUtils.isNotBlank(style)) {
	    //	    style = "netbuk";
	    //	    style = "sporvic3";
	    //	    style = WebConstants.PRODUCT_STYLE_LEGACY;
	    cl = this.styleConfigCache.get(style);
	    String fileName = "/WEB-INF/decorators_" + style.toLowerCase() + ".xml";
	    String fileRealPath = request.getSession().getServletContext().getRealPath(fileName);
	    if (fileRealPath != null) {
		try {
		    cl = new ConfigLoader(new File(fileRealPath));
		    this.styleConfigCache.put(style, cl);
		} catch (ServletException e) {
		    log.error("getStyleConfig - errore nel caricamento della configurazione dei decorators dal file " + fileRealPath);
		}
	    }
	}
	return cl;
    }
}
