package it.gruppoinit.pal.gp.areariservata.web;

import it.gruppoinit.pal.gp.areariservata.web.util.DeployProperties;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.service.FoArjServiziService;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseResolverController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected ExternalDBResolver externalDBResolver;
    @Autowired
    protected FoArjServiziService foArjServiziService;
    @Autowired
    protected VerticalizzazioniService verticalizzazioniService;
    @Autowired
    protected DeployProperties deployProperties;
    @Autowired
    protected SdeproxyService sdeproxyService;

    protected String resolveServizio(HttpServletRequest request, String uriMapping, String uriRedirect) {

	String ctxPath = request.getContextPath();
	StringBuffer redirect = new StringBuffer();
	String uriServizio = request.getRequestURI().replaceFirst(ctxPath + uriMapping, "");
	log.debug("resolveServizio: urlServizio=[{}]", uriServizio);
	//1.carico idcomunealias,software,guestUserId da deployProperties
	String idcomunealias = deployProperties.getServiziIdcomunealias();
	String software = deployProperties.getServiziSoftware();
	String guestUserId = deployProperties.getServiziUserid();
	//2.inizializzo ORMHelper
	this.setORMHelper(idcomunealias, software);
	//3.foArjNlaServizi.findByURIServizio(uri);
	List<FoArjServizi> servizi = foArjServiziService.findByUrlServizio(uriServizio);
	if (servizi.size() != 1) {
	    log.error("resolveServizio: il numero di servizi mappati per l'url=[{}] è di [{}] invece di 1", uriServizio, servizi.size());
	    throw new RuntimeException("Errore durante la risoluzione del servizio");
	}
	FoArjServizi servizio = servizi.get(0);
	Integer idServizio = servizio.getId().getCodice();
	log.debug("resolveServizio: urlServizio=[{}], idServizio=[{}]", uriServizio, idServizio);
	//creazione redirect
	//
	int count = StringUtils.countMatches(uriServizio, "/");
	for (int i = 0; i < count; i++) {
	    uriRedirect = "../" + uriRedirect;
	}
	//
	redirect.append("redirect:");
	redirect.append(uriRedirect);
	redirect.append("?idcomunealias=");
	redirect.append(idcomunealias);
	redirect.append("&software=");
	redirect.append(software);
	redirect.append("&idServizio=");
	redirect.append(idServizio);
	//4.gestione servizio anonimo
	if (BooleanUtils.isTrue(servizio.getAnonimo())) {
	    //login utente guest
	    log.debug("resolveServizio: urlServizio=[{}] servizio anonimo, login utente guest", uriServizio);
	    String token = this.getGuestSecurityToken(idcomunealias, guestUserId, request.getRemoteAddr());
	    redirect.append("&").append(WebConstants.TOKEN).append("=");
	    redirect.append(token);
	}
	//5.2 aggiungo eventuale returnTo
	this.appendReturnTo(redirect);
	//
	ORMHelper.destroyORMHelper();
	log.debug("resolveServizio: redirect=[{}]", redirect.toString());
	return redirect.toString();
    }

    protected Sdeproxy setORMHelper(String idente, String software) {

	// Quello che mi è stato passato è l'idente, dall'id ente recupero Alias ente dalla tabelle sdeproy
	// per fare questa chiamata uso l'idcomune alias defautl
	Sdeproxy sdeproxy = sdeproxyService.findByIdEnte(idente);
	Properties connProps = externalDBResolver.getConnectionProperties(sdeproxy.getAliasEnte());
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(software);
	ORMHelper.setIdente(idente);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setIdcomunebase(sdeproxy.getIdcomunebase());
	return sdeproxy;
    }

    protected String getGuestSecurityToken(String idcomuneAlias, String username, String ipAddress) {

	log.debug("getGuestSecurityToken: idcomuneAlias=[{}], username=[{}]", idcomuneAlias, username);
	return externalDBResolver.getUserUTEToken(idcomuneAlias, username, ipAddress);
    }

    protected void appendReturnTo(StringBuffer redirect) {

	Verticalizzazioniparametri vertParam = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA,
		"RETURN_TO_URL_PER_SERVIZI");
	if (vertParam != null && StringUtils.isNotEmpty(vertParam.getValore())) {
	    try {
		String returnTo = URLEncoder.encode(vertParam.getValore(), "UTF-8");
		redirect.append("&");
		redirect.append(WebConstants.RETURNTO);
		redirect.append("=");
		redirect.append(returnTo);
	    } catch (UnsupportedEncodingException e) {
		log.error("appendReturnTo", e);
	    }
	}
    }
}
