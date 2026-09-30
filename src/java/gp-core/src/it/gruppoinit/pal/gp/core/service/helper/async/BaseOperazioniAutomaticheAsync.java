package it.gruppoinit.pal.gp.core.service.helper.async;

import org.slf4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.infrastructure.security.SigeproSecurityUtilsService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;

public abstract class BaseOperazioniAutomaticheAsync implements Runnable {

    // BEGIN variabili ORMHelper // USATE SOLO NEL METODO RUN
    protected String idcomuneAlias;
    protected String idcomune;
    protected String software;
    protected String token;
    protected String hibernateSfKey;
    // END variabili ORMHelper // USATE SOLO NEL METODO RUN

    @Override
    public void run() {

	getLogger().debug("run# eseguo le notifiche automatiche per idcomune {}, alias {}, software {}, token {}",
		new Object[] { idcomune, idcomuneAlias, software, token });
	gestisciAutenticazione();
	//  eseguo la logica delle classi concrete
	runInternal();
	// end
    }

    private void gestisciAutenticazione() {

	ORMHelper.setIdcomuneAlias(idcomuneAlias);
	ORMHelper.setIdcomune(idcomune);
	ORMHelper.setHibernateSFKey(hibernateSfKey);
	ORMHelper.setSoftware(software);
	ORMHelper.setToken(token);
	// gestisco autenticazione
	// verifico se token applicativo
	getLogger().debug("run# prima di chiamare infotoken {}", token);
	SigeproSecurityUtilsService ssutil = (SigeproSecurityUtilsService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("sigeproSecurityUtilsServiceImpl", SigeproSecurityUtilsService.class);
	CheckTokenResponse infoToken = ssutil.infoToken(token);
	if (infoToken == null || !infoToken.isValid() || infoToken.getTokenInfo() == null) {
	    getLogger().debug("run# Token non valido in notifica automatica per idcomune {}, alias {}, software {}, token {}",
		    new Object[] { idcomune, idcomuneAlias, software, token });
	    throw new SecurityException("token non valido");
	}
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	getLogger().debug("run# authentication {}", authentication);
	UserSecurityService ss = (UserSecurityService) ContextLoader.getCurrentWebApplicationContext().getBean("userSecurityService",
		UserSecurityService.class);
	UserDetails ud = null;
	getLogger().debug("Il token {} è di tipo {}", token, infoToken.getTokenInfo().getContesto());
	if (infoToken.getTokenInfo().getContesto().equals(ContestoType.APP)) {
	    // carico utente amministratore e lo setto nella security
	    ud = ss.loadAdministratorUser();
	} else if (infoToken.getTokenInfo().getContesto().equals(ContestoType.OPE)) {
	    // recupero lo userid dal token e carico l'utente con quell'userid
	    ud = ss.loadUserByUsername(infoToken.getTokenInfo().getUserid());
	} else {
	    getLogger().debug("run# Token non valido in notifica automatica per idcomune {}, alias {}, software {}, token {}",
		    new Object[] { idcomune, idcomuneAlias, software, token });
	    throw new SecurityException("Token non valido");
	}
	SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ud, "", ud.getAuthorities()));	
    }

    public abstract void runInternal();

    public abstract Logger getLogger();
}
