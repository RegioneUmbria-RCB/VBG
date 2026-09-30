package it.gruppoinit.pal.gp.areariservata.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/compilazione/")
@Controller
public class DomandeResolverController extends BaseResolverController {

    private final String uriMapping = "/compilazione/";

    @RequestMapping
    public String resolveServizio(HttpServletRequest request) {

	String ctxPath = request.getContextPath();
	StringBuffer redirect = new StringBuffer();
	String uriServizio = request.getRequestURI().replaceFirst(ctxPath + uriMapping, "");
	log.debug("resolveServizio: urlServizio=[{}]", uriServizio);
	//1.carico idcomunealias, software da DeployProperties
	String idcomunealias = deployProperties.getServiziIdcomunealias();
	String software = deployProperties.getServiziSoftware();
	//2.inizializzo ORMHelper
	this.setORMHelper(idcomunealias, software);
	if (StringUtils.isNotBlank(uriServizio)) {
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
	    redirect.append("redirect:");
	    redirect.append("../domande/listPerServizio.htm");
	    redirect.append("?idcomunealias=");
	    redirect.append(idcomunealias);
	    redirect.append("&software=");
	    redirect.append(software);
	    redirect.append("&idServizio=");
	    redirect.append(idServizio);
	} else {
	    redirect.append("redirect:");
	    redirect.append("../domande/list.htm");
	    redirect.append("?idcomunealias=");
	    redirect.append(idcomunealias);
	    redirect.append("&software=");
	    redirect.append(software);
	}
	//5.2 aggiungo eventuale returnTo
	this.appendReturnTo(redirect);
	//
	ORMHelper.destroyORMHelper();
	log.debug("resolveServizio: redirect=[{}]", redirect.toString());
	return redirect.toString();
    }
}
