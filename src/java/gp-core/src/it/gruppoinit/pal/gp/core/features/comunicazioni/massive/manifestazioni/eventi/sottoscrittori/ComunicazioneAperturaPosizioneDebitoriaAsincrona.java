package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.eventi.sottoscrittori;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.IComunicazioniManifestazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ICreazioneMassiveDettaglioManifestazioniService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.StrategiaInvioComunicazioniEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.IVerticalizzazioneAbbonamentoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.VerticalizzazioneAbbonamentoPosteggiServiceImpl;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Component
public class ComunicazioneAperturaPosizioneDebitoriaAsincrona extends BaseAsyncHelper implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ComunicazioneAperturaPosizioneDebitoriaAsincrona.class);
    @Autowired
    private TaskExecutor taskExecutor;
    private long sleepTimeBeforeExecute = 10000;
    // BEGIN variabili ORMHelper // USATE SOLO NEL METODO RUN
    private String idcomuneAlias;
    private String idcomune;
    private String software;
    private String token;
    private String hibernateSfKey;
    // END VARIABILI ORMHELPER
    private Integer idPresenza;
    private String riferimentoOperazione;

    private ComunicazioneAperturaPosizioneDebitoriaAsincrona(Integer idPresenza, String idcomuneAlias, String idcomune, String software, String token,
	    String hibernateSfKey) {

	this();
	this.idcomuneAlias = idcomuneAlias;
	this.idcomune = idcomune;
	this.software = software;
	this.token = token;
	this.hibernateSfKey = hibernateSfKey;
	this.idPresenza = idPresenza;
	this.riferimentoOperazione = "[" + idcomune + "-" + software + "-" + idPresenza + "-" + UUID.randomUUID().toString() + "]";
    }

    protected ComunicazioneAperturaPosizioneDebitoriaAsincrona() {

	super();
    }

    public synchronized void eseguiTask(Integer idPresenza, String idcomunealias, String idcomune, String software, String token,
	    String hibernateSfKey) {

	if (log.isDebugEnabled()) {
	    log.debug("eseguiTask# entro nel metodo");
	}
	try {
	    if (idPresenza != null) {
		taskExecutor.execute(
			new ComunicazioneAperturaPosizioneDebitoriaAsincrona(idPresenza, idcomunealias, idcomune, software, token, hibernateSfKey));
	    } else {
		log.debug("Non ci sono notifiche per la presenza {}", riferimentoOperazione);
	    }
	} catch (Exception e) {
	    log.error("eseguiTask# Errore nell' esecuzione della comunicazione " + riferimentoOperazione + ": " + e.getMessage(), e);
	}
    }

    @Override
    public void run() {

	ORMHelper.setIdcomuneAlias(idcomuneAlias);
	ORMHelper.setIdcomune(idcomune);
	ORMHelper.setHibernateSFKey(hibernateSfKey);
	ORMHelper.setSoftware(software);
	ORMHelper.setToken(token);
	log.debug("run# recupero i service {}", riferimentoOperazione);
	SecurityContext context = SecurityContextHolder.getContext();
	Authentication authentication = context.getAuthentication();
	if (authentication == null) {
	    UserSecurityService ss = (UserSecurityService) ContextLoader.getCurrentWebApplicationContext().getBean("userSecurityService",
		    UserSecurityService.class);
	    try {
		UserDetails ud = ss.loadAdministratorUser();
		if (log.isDebugEnabled()) {
		    log.debug("run# carico l'utente amministratore {}-{}", ud, riferimentoOperazione);
		}
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ud, "", ud.getAuthorities()));
	    } catch (Exception e) {
		log.error("Non è stato possibile recuperare l'utente autenticato a causa di: " + e.getMessage() + " per l'operazione " +
			  riferimentoOperazione,
			e);
		throw new SecurityException("Non è stato possibile recuperare l'utente autenticato a causa di: " + e.getMessage(), e);
	    }
	}
	long sleepTimeBeforeExecuteConfigurato = this.sleepTimeBeforeExecute;
	log.debug("run# {} lancio Thread.sleep({})", riferimentoOperazione, sleepTimeBeforeExecuteConfigurato);
	try {
	    Thread.sleep(sleepTimeBeforeExecuteConfigurato);
	} catch (InterruptedException e) {
	    ORMHelper.destroyORMHelper();
	    log.error("run#  " + riferimentoOperazione + " errore nella chiamata a Thread.sleep non mando in esecuzione il restante codice: " +
		      e.getMessage(),
		    e);
	    return;
	}
	//1. Verifico se vanno inviate comunicazioni
	try {
	    log.debug("Invia comunicazione per credito insufficiente {} instanzio i service", riferimentoOperazione);
	    MercatipresenzeDService mercatipresenzedService = getBeanOfType(MercatipresenzeDService.class.getName());
	    MercatipresenzeTService mercatipresenzeTService = getBeanOfType(MercatipresenzeTService.class.getName());
	    MercatiService mercatiService = getBeanOfType(MercatiService.class.getName());
	    MercatipresenzeD presenza = mercatipresenzedService.findById(new PkId(idPresenza));
	    MercatipresenzeT giornata = mercatipresenzeTService.findById(new PkId(presenza.getMercatiPresenzeT().getId().getCodice()));
	    Mercati mercato = mercatiService.findById(new PkId(giornata.getMercato().getId().getCodice()));
	    VerticalizzazioniService verticalizzazioniService = getBeanOfType(VerticalizzazioniService.class.getName());
	    ContiService contiService = getBeanOfType(ContiService.class.getName());
	    MailtipoService mailTipoService = getBeanOfType(MailtipoService.class.getName());
	    AmministrazioniService amministrazioniService = getBeanOfType(AmministrazioniService.class.getName());
	    IComunicazioniManifestazioniService comunicazioniService = getBeanOfType(IComunicazioniManifestazioniService.class.getName());
	    ICreazioneMassiveDettaglioManifestazioniService creazioneMassiveDettaglioService = getBeanOfType(
		    ICreazioneMassiveDettaglioManifestazioniService.class.getName());
	    IVerticalizzazioneAbbonamentoPosteggiService vert = new VerticalizzazioneAbbonamentoPosteggiServiceImpl(verticalizzazioniService,
		    contiService, mailTipoService, amministrazioniService, mercato.getComune().getCodicecomune());
	    if (!vert.isAttiva() || !vert.strategiaInvioComunicazioniSupportata(StrategiaInvioComunicazioniEnum.APERTURA_POS_CREDITO_INSUFFICIENTE)) {
		log.debug("Invia comunicazione per credito insufficiente non configurata {}", riferimentoOperazione);
		return;
	    }
	    //2. Verifico se presente una comunicazione per quella giornata
	    //   potrebbe essere stata chiusa, inviata, riaperta e richiusa
	    int idTestata = -1;
	    ConfigurazioneComunicazioniManifestazioni config = ConfigurazioneComunicazioniManifestazioni.fromCreditoInsufficientePresenza(vert,
		    giornata);
	    if (comunicazioniService.presentiComunicazioniPerTipologiaEGiornata(giornata.getId().getCodice(),
		    ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE.APERTURA_POS_CREDITO_INSUFFICIENTE)) {
		log.debug("Invia comunicazione per credito insufficiente la comunicazione esite la collego {}", riferimentoOperazione);
		// IN QUESTO CASO DEVO COLLEGARLA AD UNA PRESENTE E POI ELABORARE
		idTestata = comunicazioniService.recuperaPrimaComunicazionePerTipologiaEGiornata(giornata.getId().getCodice(),
			ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE.APERTURA_POS_CREDITO_INSUFFICIENTE);
		log.debug("Invia comunicazione per credito insufficiente la comunicazione esite la collego alla testata {} - {}", idTestata,
			riferimentoOperazione);
	    } else {
		//3. Creo la comunicazione
		log.debug("Invia comunicazione per credito insufficiente la comunicazione NON esite la creo {}", riferimentoOperazione);
		idTestata = comunicazioniService.creaNuovaComunicazione(config);
	    }
	    creazioneMassiveDettaglioService.collegaRigaAComunicazione(config, idPresenza, idTestata);
	    //	    log.debug("Invia comunicazione per credito insufficiente ELABORO la comunicazione {}", riferimentoOperazione);
	    //	    comunicazioniService.elabora(idTestata); NON DEVO ELABORARE lo farà lo schedulatore
	} catch (Exception e) {
	    log.error("Errore nel recupero dei bean " + e.getMessage(), e);
	} finally {
	    ORMHelper.destroyORMHelper();
	}
    }
}
