package it.gruppoinit.cnshandler.servlet;

import it.cefriel.utility.smartcard.CardUserData;
import it.gruppoinit.cnshandler.utils.CertUtils;
import it.people.sirac.smartcardprofile.ISmartCardProfileFactory;
import it.people.sirac.smartcardprofile.SmartCardProfile;
import it.people.sirac.smartcardprofile.SmartCardProfileFactory;
import it.people.sirac.smartcardprofile.SmartCardProfiles;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet implementation class ProcessServlet
 */
public class ProcessServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(ProcessServlet.class);
    private String smartCardProfilesXmlFile = null;
    private String certHeaderName = "HTTP_X_SSL_CERT";
    private SmartCardProfiles smartCardProfiles = null;
    private ServletContext ctx = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessServlet() {

	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	log.info("doGet");
	doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	log.info("doPost");
	try {
	    log.info("Extract certificate");
	    CardUserData cardUserData = null;
	    X509Certificate cert = extractClientCertificate(request);
	    if (cert == null) {
		log.info("Certificate not found in request, try to find issuerDN and subjectDN attribues");
		String issuerDN = request.getParameter("issuerDN");
		String subjectDN = request.getParameter("subjectDN");
		if (StringUtils.isBlank(issuerDN) && StringUtils.isBlank(subjectDN)) {
		    log.error("Certificate and other attributes not found in request!");
		    throw new RuntimeException("Certificato non presente");
		}
		log.info("Get certificate profile with issuerDN: " + issuerDN + ", subjectDN: " + subjectDN);
		SmartCardProfile smartCardProfile = CertUtils.getCertificateProfile(issuerDN, subjectDN, this.smartCardProfiles);
		log.info("Get user data with smarcard profile: " + smartCardProfile);
		cardUserData = CertUtils.getUserInfo(issuerDN, subjectDN, smartCardProfile);
	    } else {
		log.info("Get certificate profile");
		SmartCardProfile smartCardProfile = CertUtils.getCertificateProfile(cert, this.smartCardProfiles);
		log.info("Get user data");
		cardUserData = CertUtils.getUserInfo(cert, smartCardProfile);
	    }
	    String ticket = UUID.randomUUID().toString();
	    log.info("Save user data with id=" + ticket);
	    ctx.setAttribute(ticket, cardUserData);
	    String redirectUrl = getRedirectUrl(request, ticket);
	    log.info("Send redirect: " + redirectUrl);
	    response.sendRedirect(redirectUrl);
	    return;
	} catch (Exception e) {
	    log.error("doPost", e);
	    throw new ServletException(e);
	}
    }

    public void init(ServletConfig config) throws ServletException {

	if (log.isDebugEnabled()) {
	    log.debug("init...");
	}
	ctx = config.getServletContext();
	String smartCardProfilesXmlFileRelativePath = config.getInitParameter("smartCardProfilesXmlFile");
	this.smartCardProfilesXmlFile = ctx.getRealPath(smartCardProfilesXmlFileRelativePath);
	if (log.isDebugEnabled()) {
	    log.debug("init - smartCardProfilesXmlFile: " + this.smartCardProfilesXmlFile);
	}
	ISmartCardProfileFactory smartCardProfileFactory = SmartCardProfileFactory.getInstance();
	try {
	    this.smartCardProfiles = smartCardProfileFactory.loadSmartCardProfilesFromXml(new FileInputStream(this.smartCardProfilesXmlFile));
	    if (log.isDebugEnabled()) {
		log.debug("init - Smartcard profiles XML correctly found and loaded.");
	    }
	} catch (Exception e) {
	    log.error("init - error parsing SmartCardProfileXML", e);
	    throw new ServletException(e);
	}
	this.certHeaderName = config.getInitParameter("certHeaderName");
	if (log.isDebugEnabled()) {
	    log.debug("init - certHeaderName: " + this.certHeaderName);
	}
	if (log.isDebugEnabled()) {
	    log.debug("init...done!");
	}
    }

    private X509Certificate extractClientCertificate(HttpServletRequest request) {

	X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
	if (certs != null && certs.length > 0) {
	    log.info("Certificate: " + certs[0]);
	    return certs[0];
	}
	String certContent = null;
	if (StringUtils.isNotBlank(request.getHeader(this.certHeaderName))) {
	    log.info("Certificato non trovato come attributo. Lo cerco nella request");
	    certContent = request.getHeader(this.certHeaderName);
	} else {
	    log.info("Certificato non trovato come attributo. Lo cerco nella request come post");
	    certContent = request.getParameter(this.certHeaderName);
	}
	if (StringUtils.isNotBlank(certContent)) {
	    log.info("==> certContent: \n[" + certContent + "]\n");
	    X509Certificate cert = getCertificate(certContent);
	    if (cert != null) {
		return cert;
	    }
	}
	log.error("No certificate found in request.");
	return null;
    }

    private X509Certificate getCertificate(String certContent) {

	try {
	    certContent = certContent.replaceAll("-----BEGIN CERTIFICATE-----", "").replaceAll("-----END CERTIFICATE-----", "").replaceAll(" ", "");
	    byte[] decodedCertificate = Base64.decodeBase64(certContent.getBytes());
	    CertificateFactory cf = CertificateFactory.getInstance("X.509");
	    ByteArrayInputStream bis = new ByteArrayInputStream(decodedCertificate);
	    X509Certificate cert = (X509Certificate) cf.generateCertificate(bis);
	    bis.close();
	    return cert;
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return null;
    }

    private String getRedirectUrl(HttpServletRequest request, String ticket) throws Exception {

	String returnTo = request.getParameter("return_to");
	if (StringUtils.isBlank(returnTo)) {
	    throw new Exception("No return_to parameter found in request");
	}
	returnTo += "&ticket=" + ticket;
	return returnTo;
    }
}
