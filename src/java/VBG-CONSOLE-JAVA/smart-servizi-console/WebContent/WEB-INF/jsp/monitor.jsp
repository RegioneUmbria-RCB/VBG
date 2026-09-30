<%@page import="java.util.jar.Manifest"%>
<%@page import="java.io.InputStream"%>
<%@page import="it.gruppoinit.sigeprosecurity.schema.LoginResponse"%>
<%@page import="it.gruppoinit.sigeprosecurity.schema.ContestoType"%>
<%@page import="it.gruppoinit.sigeprosecurity.schema.LoginRequest"%>
<%@page import="it.gruppoinit.sigeprosecurity.schema.LoginSSORequest"%>
<%@page import="it.gruppoinit.sigeprosecurity.ws.SigeproSecurity"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.sigepro.definitions.anagrafe.Anagrafe"%>
<%@page import="it.gruppoinit.sigepro.definitions.anagrafe.AnagrafeWSClient"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.definitions.responsabili.Responsabili"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.definitions.responsabili.ResponsabiliWSClient"%>
<%@page import="it.gruppoinit.auth.service.impl.TokenLoginService"%>
<%@page import="it.gruppoinit.arpaag.listener.ConfigurationManager"%>
<%@page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>
BEGIN
<%
ConfigurationManager c = null;
boolean esitoPositivo = true;
try {
	ServletContext servletCtx = getServletConfig().getServletContext();
	InputStream inputStream = servletCtx.getResourceAsStream("/META-INF/MANIFEST.MF");
	Manifest manifest = new Manifest(inputStream);
	out.println("VERSIONE : " + manifest.getMainAttributes().getValue("Implementation-Version"));
} catch (Exception e) {
    out.println("LETTURA MANIFEST : ERROR " + e);
}

try {
	c = ConfigurationManager.getInstance();
	c.loadConfiguration();
	out.println("CARICAMENTO PARAMETRI DI CONFIGURAZIONE ("+c.getProperties().getProperty("listaEntiConDelega")+") : OK");
} catch (Exception e) {
    out.println("CARICAMENTO PARAMETRI DI CONFIGURAZIONE : ERROR " + e);
    esitoPositivo = false;
}

try {
	TokenLoginService tokenService = new TokenLoginService(c.getProperties());
	SigeproSecurity port = tokenService.getPort();
	LoginRequest loginRequest = new LoginRequest();
	loginRequest.setAlias("TEST");
	loginRequest.setContesto(ContestoType.APP);
	loginRequest.setUsername(c.getProperties().getProperty("ws.token.user"));
	loginRequest.setPassword(c.getProperties().getProperty("ws.token.pwd"));
	loginRequest.setIpAddress(request.getLocalAddr());
	LoginResponse resp = port.login(loginRequest);
	out.println("CHIAMATA AL WEB SERVICE SUAP-SECURITY : OK");
} catch (Exception e) {
    out.println("CHIAMATA AL WEB SERVICE SUAP-SECURITY : ERROR " + e);
    esitoPositivo = false;
}
if(esitoPositivo){
    out.println("STATUS : OK");
}else{
    out.println("STATUS : ERROR");
}
%>
END