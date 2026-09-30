package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.CalendariomercatoParametriService;
import it.gruppoinit.pal.gp.core.service.GiornisectimanaService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

/**
 * Crea i calendari per l'anno in corso per i mercati specificati dai filtri
 *
 * <pre>
 * 
 * 		&lt;java-task id="UPGR_CREA_CALENDARI_MERCATI" spring-bean-id="upgrUpdateCreaCalendariMercatiTask"
 * 			java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateCreaCalendariMercatiTask"
 * 			fail-on-error="true" autocommit="true"&gt;
 * 			&lt;param name="alias" value="E256"&gt;&lt;/param&gt;
 * 			&lt;param name="software" value="CO"&gt;&lt;/param&gt;
 * 			&lt;param name="codiciMercato" value="1,2"&gt;&lt;/param&gt;
 * 		&lt;/java-task&gt;
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpdateCreaCalendariMercatiTask")
public class UpdateCreaCalendariMercatiTask extends BaseJavaTask {

    private static final Logger log = LoggerFactory.getLogger(UpdateCreaCalendariMercatiTask.class);

    @Override
    public void initialize() throws SetupRunException {

	// TODO Auto-generated method stub
    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	log.info("UpdateCreaCalendariMercatiTask start.....");
	String id = String.valueOf(System.currentTimeMillis());
	String idcomunealias = getParameterValue("alias");
	String software = getParameterValue("software");
	String codiciMercato = StringUtils.trim(getParameterValue("codiciMercato"));
	List<String> codici = new ArrayList<String>(Arrays.asList(codiciMercato.split(",")));
	LoggerUpdaterecord.log("######################################################################");
	try {
	    LoggerUpdaterecord.log(id +
		    "==>UpdateCreaCalendariMercatiTask ALIAS:" +
		    idcomunealias +
		    ", Software:" +
		    software +
		    ", AT: " +
		    Utilities.formatDate(new Date(), true));
	    setORMHelper(idcomunealias, software);
	    MercatipresenzeTService mercatipresenzeTService = (MercatipresenzeTService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("mercatipresenzeTServiceImpl");
	    MercatiService mercatiService = (MercatiService) ContextLoader.getCurrentWebApplicationContext().getBean("mercatiServiceImpl");
	    MercatiUsoService mercatiUsoService = (MercatiUsoService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("mercatiUsoServiceImpl");
	    // Map beansOfType = ContextLoader.getCurrentWebApplicationContext().getBeansOfType(GiornisectimanaService.class);
	    CalendariomercatoParametriService calendariomercatoParametriService = (CalendariomercatoParametriService) ContextLoader
		    .getCurrentWebApplicationContext().getBean("calendariomercatiParametriServiceImpl");
	    GiornisectimanaService giornisectimanaService = (GiornisectimanaService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("giornisectimanaServiceImpl");
	    UserSecurityService userSecurityService = (UserSecurityService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("userSecurityService");
	    log.info("Inizio l'aggiornamento.....");
	    List<Mercati> mercati = mercatiService.findAllMercatiAttivi(null, null);
	    int anno = Calendar.getInstance().get(Calendar.YEAR);
	    Responsabili utente = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    for (Mercati _mercato : mercati) {
		String codiceM = _mercato.getId().getCodice().toString();
		if (codici.contains(codiceM)) {
		    List<MercatiUso> mercatoUsi = mercatiUsoService.findByMercato(_mercato);
		    for (MercatiUso _uso : mercatoUsi) {
			String descrizioneMercato = _mercato.getDescrizione() + " (" + _mercato.getId() + ")-USO:" + _uso.getDescrizione();
			Giornisettimana gsect = giornisectimanaService.findById(_uso.getGiornisettimana().getId());
			List<Giornisettimana> gs = new ArrayList<Giornisettimana>();
			if (gsect == null) {
			    LoggerUpdaterecord.log(id +
				    "==>UpdateCreaCalendariMercatiTask Il mercato " +
				    descrizioneMercato +
				    " non ha configurato la voce Giorno della Settimana giornate e lo considero come elaborato AT: " +
				    Utilities.formatDate(new Date(), true));
			} else {
			    gsect.setTransientSelected(true);
			    gs.add(gsect);
			    int giornateDellAnno = mercatipresenzeTService.countByMercatoUsoAnno(_mercato.getId().getCodice(),
				    _uso.getId().getCodice(), anno);
			    if (giornateDellAnno == 0) {
				LoggerUpdaterecord.log(id +
					"==>UpdateCreaCalendariMercatiTask Elaboro il mercato " +
					descrizioneMercato +
					" AT: " +
					Utilities.formatDate(new Date(), true));
				CalendariomercatoParametri calendariomercatoParametri = new CalendariomercatoParametri();
				calendariomercatoParametri.setAnno(anno);
				calendariomercatoParametri.setMercatiUso(_uso);
				calendariomercatoParametri.setStep(0);
				calendariomercatoParametri.setGiorniSettimana(gs);
				calendariomercatoParametri.setGiorniMercato(calendariomercatoParametriService
					.findGiorniMercatoInUnAnno(calendariomercatoParametri.getAnno(),
						calendariomercatoParametri.getMercatiUso(), calendariomercatoParametri.getGiorniSettimana())
					.getGiorniMercato());
				calendariomercatoParametri
					.setGiorniFestivi(calendariomercatoParametriService.findGiornifestivi(calendariomercatoParametri.getAnno()));
				try {
				    mercatipresenzeTService.inserisciCalendario(calendariomercatoParametri, _mercato, utente, false);
				} catch (Exception e) {
				    log.error("UpdateCreaCalendariMercatiTask errore nell'elaborazione del mercato " +
					    descrizioneMercato +
					    " id:" +
					    id +
					    "-->{}", e);
				    LoggerUpdaterecord.log(id +
					    "==>UpdateCreaCalendariMercatiTask errore nell'elaborazione del mercato " +
					    descrizioneMercato +
					    " AT: " +
					    Utilities.formatDate(new Date(), true) +
					    ": errore=" +
					    e.getMessage());
				}
			    } else {
				LoggerUpdaterecord.log(id +
					"==>UpdateCreaCalendariMercatiTask Il mercato " +
					descrizioneMercato +
					" ha già configurate N#" +
					giornateDellAnno +
					" giornate e lo considero come elaborato AT: " +
					Utilities.formatDate(new Date(), true));
			    }
			}
		    }
		}
	    }
	    log.info("Aggiornamento completato.....");
	} catch (Exception e) {
	    log.error("execute", e);
	    LoggerUpdaterecord.log(id + "==>UpdateCreaCalendariMercatiTask STOP WITH ERROR AT: " + Utilities.formatDate(new Date(), true));
	    LoggerUpdaterecord.log(id + "==>######################################################################");
	    throw new RuntimeException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	}
	LoggerUpdaterecord.log(id + "==>UpdateCreaCalendariMercatiTask STOP AT: " + Utilities.formatDate(new Date(), true));
	LoggerUpdaterecord.log(id + "==>##########################################################################");
	log.info("UpdateCreaCalendariMercatiTask end.....");
	return 0;
    }

    protected void setORMHelper(String alias, String software) {

	ExternalDBResolver externalDBResolver = (ExternalDBResolver) ContextLoader.getCurrentWebApplicationContext().getBean("externalDBResolver");
	Properties connProps = externalDBResolver.getConnectionProperties(alias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	if (StringUtils.isNotBlank(software)) {
	    ORMHelper.setSoftware(software);
	} else {
	    ORMHelper.setSoftware(WebConstants.SOFTWARE_TT);
	}
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setToken(connProps.getProperty(WebConstants.TOKEN));
	this.login(connProps.getProperty(WebConstants.USER_ID));
    }

    private void login(String userid) {

	try {
	    //se la verticalizzazione WS_LOGIN è attiva utilizzo il valore del parametro CODICE_OPERATORE come userid
	    Verticalizzazioniparametri vParam = verticalizzazioniService.getVerticalizzazioniparametri("WS_LOGIN", "USERID_OPERATORE");
	    if (vParam != null) {
		userid = vParam.getValore();
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
	} catch (Exception e) {
	    throw new RuntimeException("BACKOFFICE BASE ENV: Errore durante la login: " + e.getMessage());
	}
    }

    private UserSecurityService userSecurityService;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }
}
