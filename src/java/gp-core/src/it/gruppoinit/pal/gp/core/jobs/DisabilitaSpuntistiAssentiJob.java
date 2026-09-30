package it.gruppoinit.pal.gp.core.jobs;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.SoftwareattiviDTO;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.pal.gp.core.service.SpuntistiMercatiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolverWS;
import it.gruppoinit.pal.gp.core.utils.LoggerDisabilitaSpuntisti;

public class DisabilitaSpuntistiAssentiJob extends BaseEnvironment implements StatefulJob {

    private static final Logger log = LoggerFactory.getLogger(DisabilitaSpuntistiAssentiJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("DisabilitaSpuntistiAssentiJob# execute....");
	String _SERVIZIO_DISABILITA_SPUNTISTA_MERCATI = "In data ''{0}'' start procedura automatica per disabilitare spuntisti per assenti";
	LoggerDisabilitaSpuntisti.logDisabilitaSpuntistiMercatiAndFiereJob(_SERVIZIO_DISABILITA_SPUNTISTA_MERCATI);
	SoftwareattiviService serviceSwAttivi = (SoftwareattiviService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("softwareattiviServiceImpl");
	MercatiConfigurazioneService serviceMercatiCfg = (MercatiConfigurazioneService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("mercatiConfigurazioneServiceImpl");
	SpuntistiMercatiService spuntistimercatiService = (SpuntistiMercatiService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("spuntistiMercatiServiceImpl");
	MercatiService mercatiService = (MercatiService) ContextLoader.getCurrentWebApplicationContext().getBean("mercatiServiceImpl");
	MercatiUsoService mercatiUsoService = (MercatiUsoService) ContextLoader.getCurrentWebApplicationContext().getBean("mercatiUsoServiceImpl");
	try {
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    ExternalDBResolverWS dbResolverWS = (ExternalDBResolverWS) ContextLoader.getCurrentWebApplicationContext().getBean("externalDBResolver");
	    Properties properties = dbResolverWS.getConnectionProperties(idcomunealias);
	    setORMHelperSoftware(properties, idcomunealias, software);
	    String _SERVIZIO_DISABILITA_SPUNTISTA_MERCATI_SW_CONFIG = "Software configurato per procedura " + software.toUpperCase();
	    LoggerDisabilitaSpuntisti.logDisabilitaSpuntistiMercatiAndFiereJob(_SERVIZIO_DISABILITA_SPUNTISTA_MERCATI_SW_CONFIG);
	    if (software.equals(WebConstants.SOFTWARE_TT)) {
		// procedura configurata per tutti i software, deveo verificare quelli attivi ed effettuare la procedura
		List<SoftwareattiviDTO> swAttivi = serviceSwAttivi.findAllSoftwareattiviDTO();
		for (SoftwareattiviDTO softwareattiviDTO : swAttivi) {
		    if (!softwareattiviDTO.equals(WebConstants.SOFTWARE_TT)) {
			MercatiConfigurazione mercatiConfigurazione = findConfigurazioneMercatoPerGestioneSpuntisti(softwareattiviDTO.getCodice(),
				serviceMercatiCfg);
			if (mercatiConfigurazione != null) {
			    Integer ggAssenza = mercatiConfigurazione.getGgAssenza();
			    setORMHelperSoftware(properties, idcomunealias, softwareattiviDTO.getCodice());
			    List<Mercati> mercatis = mercatiService.findAllMercatiAttivi(null, null);
			    for (Mercati mercati : mercatis) {
				Integer codiceMercato = mercati.getId().getCodice();
				List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(mercati);
				for (MercatiUso mercatiUso : mercatiUsos) {
				    LoggerDisabilitaSpuntisti.logDisabilitaSpuntistiMercatiAndFiereJob(
					    LoggerDisabilitaSpuntisti._DISABILITA_SPUNTISTA_MERCATI, codiceMercato.toString(),
					    mercatiUso.getId().getCodice().toString());
				    spuntistimercatiService.disabilitaPerAssenza(codiceMercato, mercatiUso.getId().getCodice(), ggAssenza);
				}
			    }
			}
		    }
		}
	    } else {
		MercatiConfigurazione mercatiConfigurazione = findConfigurazioneMercatoPerGestioneSpuntisti(software, serviceMercatiCfg);
		if (mercatiConfigurazione != null) {
		    Integer ggAssenza = mercatiConfigurazione.getGgAssenza();
		    setORMHelperSoftware(properties, idcomunealias, software);
		    List<Mercati> mercatis = mercatiService.findAllMercatiAttivi(null, null);
		    for (Mercati mercati : mercatis) {
			Integer codiceMercato = mercati.getId().getCodice();
			List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(mercati);
			for (MercatiUso mercatiUso : mercatiUsos) {
			    LoggerDisabilitaSpuntisti.logDisabilitaSpuntistiMercatiAndFiereJob(
				    LoggerDisabilitaSpuntisti._DISABILITA_SPUNTISTA_MERCATI, codiceMercato.toString(),
				    mercatiUso.getId().getCodice().toString());
			    spuntistimercatiService.disabilitaPerAssenza(codiceMercato, mercatiUso.getId().getCodice(), ggAssenza);
			}
		    }
		}
	    }
	    log.info("DisabilitaSpuntistiAssentiJob# execute end....");
	} catch (Exception e) {
	    log.error("DisabilitaSpuntistiAssentiJob# execute", e);
	    throw new JobExecutionException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	}
    }

    private MercatiConfigurazione findConfigurazioneMercatoPerGestioneSpuntisti(String codiceSoftware,
	    MercatiConfigurazioneService serviceMercatiCfg) {

	MercatiConfigurazioneId id = new MercatiConfigurazioneId();
	id.setIdcomune(ORMHelper.getIdcomuneAlias());
	id.setSoftware(codiceSoftware);
	MercatiConfigurazione mercatiCfg = serviceMercatiCfg.findById(id);
	if (mercatiCfg != null && MercatiConfigurazione.checkConfigurazioneGradSpuntistiPerManifestazioni(mercatiCfg)
		&& mercatiCfg.getGgAssenza() != null && mercatiCfg.getGgAssenza() != 0) {
	    return mercatiCfg;
	}
	return null;
    }

    protected void setORMHelperSoftware(Properties connProps, String idcomunealias, String software) {

	//Properties connProps = externalDBResolver.getConnectionProperties(alias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(idcomunealias);
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setToken(connProps.getProperty(WebConstants.TOKEN));
	this.login(connProps.getProperty(WebConstants.USER_ID));
    }

    private void login(String userid) {

	try {
	    //se la verticalizzazione WS_LOGIN è attiva utilizzo il valore del parametro CODICE_OPERATORE come userid
	    VerticalizzazioniService verticalizzazioniService = (VerticalizzazioniService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("verticalizzazioniServiceImpl");
	    UserSecurityService userSecurityService = (UserSecurityService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("userSecurityService");
	    Verticalizzazioniparametri vParam = verticalizzazioniService.getVerticalizzazioniparametri("WS_LOGIN", "USERID_OPERATORE");
	    if (vParam != null) {
		userid = vParam.getValore();
		log.debug("login(userid:{}) recuperato dalla verticalizzazione WS_LOGIN", userid);
	    }
	    UserDetails user;
	    try {
		user = userSecurityService.loadUserByUsername(userid);
	    } catch (UsernameNotFoundException e) {
		user = userSecurityService.loadAdministratorUser();
	    }
	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	    // update the current context to the target user
	    SecurityContextHolder.getContext().setAuthentication(authRequest);
	    log.info("login(userid:{})", userid);
	} catch (Exception e) {
	    log.error("login(userid:{}) idcomunealias:{}", new Object[] { userid, ORMHelper.getIdcomuneAlias(), e });
	    throw new RuntimeException("BACKOFFICE BASE ENV: Errore durante la login: " + e.getMessage());
	}
    }
}
