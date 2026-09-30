package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.wf;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.AppIoCodaMassiveD;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfig;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MercatiMassiveD;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaMassiveDDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoServiziConfigService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IWorkFlowStep;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.SoftwareComuneDataBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.EventoComunicazioneInviata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniMassiveGenDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.IstanzeGroupEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.eventi.EventoAppIoSchedulata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp.ana.SegnapostoAnaBuilder;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp.aut.SegnapostoAutBuilder;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettagliMailComunicazione;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

@Service
public class GenInviaAppioServiceImpl implements IWorkFlowStep<ConfigurazioniComunicazioneGen> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GenInviaAppioServiceImpl.class);
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    private MailtipoService mailtipoService;
    protected IComunicazioniMassiveDettaglioDAO massiveDao;
    private IEventPublisher publisher;
    private IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO;
    private IAppIoServiziConfigService appIoServiziConfigServiceImpl;
    private IAppIoCodaService appIoCodaService;
    private IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO;

    @Autowired
    public GenInviaAppioServiceImpl(IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO, MailtipoService mailtipoService,
	    MailServiceWSClient mailServiceWSClient, MailConfigService mailConfigService, IEventPublisher publisher,
	    IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO, IAppIoServiziConfigService appIoServiziConfigServiceImpl,
	    IAppIoCodaService appIoCodaService, IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO) {

	this.comunicazioniMassiveDettaglioDAO = comunicazioniMassiveDettaglioDAO;
	this.mailtipoService = mailtipoService;
	this.publisher = publisher;
	this.comunicazioniMassiveGenDAO = comunicazioniMassiveGenDAO;
	this.appIoServiziConfigServiceImpl = appIoServiziConfigServiceImpl;
	this.appIoCodaService = appIoCodaService;
	this.appIoCodaMassiveDDAO = appIoCodaMassiveDDAO;
    }

    @Autowired
    public void setMassiveDao(IComunicazioniMassiveDettaglioDAO massiveDao) {

	this.massiveDao = massiveDao;
    }

    @Override
    public void elabora(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione) {

	try {
	    MassiveDettaglio dettaglio = comunicazioniMassiveDettaglioDAO.getById(idDettaglioComunicazione);
	    List<AppIoCodaMassiveD> appIoCodaMassiveD = this.appIoCodaMassiveDDAO.findByIdDettaglioMassiveD(idDettaglioComunicazione);
	    if (appIoCodaMassiveD != null && !appIoCodaMassiveD.isEmpty()) {
		if (configurazione.getWarnings() != null && !configurazione.getWarnings().isEmpty()) {
		    EventoAppIoSchedulata eventoAppIoSchedulata = new EventoAppIoSchedulata(configurazione.getContesto(),
			    dettaglio.getId().getCodice());
		    eventoAppIoSchedulata.setWarnings(configurazione.getWarnings());
		    publisher.publishThrowOnFailure(eventoAppIoSchedulata); //In questo caso finirà in Warning
		    return;
		} else {
		    EventoAppIoSchedulata eventoAppIoSchedulata = new EventoAppIoSchedulata(configurazione.getContesto(),
			    dettaglio.getId().getCodice());
		    eventoAppIoSchedulata.setWarnings(configurazione.getWarnings());
		    publisher.publishThrowOnFailure(eventoAppIoSchedulata);
		    return;
		}
	    }
	    Mailtipo oggettoAndBodyReplaced = null;
	    boolean isInvioMail = dettaglio.getMassiveTestata().getParametro("GESTIONE_SCELTA_MAIL_ANAGRAFE") != null;
	    List<DettagliMailComunicazione> mailIviate = null;
	    if (isInvioMail) {
		mailIviate = comunicazioniMassiveDettaglioDAO.findDettagliMailInviate(idDettaglioComunicazione);
	    }
	    if (mailIviate != null && mailIviate.size() == 1 && mailIviate.get(0) != null) {
		DettagliMailComunicazione dettagliMailComunicazione = mailIviate.get(0);
		if (dettagliMailComunicazione != null) {
		    oggettoAndBodyReplaced = new Mailtipo();
		    oggettoAndBodyReplaced.setOggetto(dettagliMailComunicazione.getOggetto());
		    oggettoAndBodyReplaced.setCorpo(dettagliMailComunicazione.getCorpo());
		}
	    } else {
		Mailtipo oggettoAndBody;
		if (dettaglio.getMassiveTestata().getFkidMailtipo() != null) {
		    oggettoAndBody = dettaglio.getMassiveTestata().getFkidMailtipo();
		} else {
		    oggettoAndBody = new Mailtipo();
		    oggettoAndBody.setOggetto(dettaglio.getMassiveTestata().getParametro("OGGETTOMAIL_NAME"));
		    oggettoAndBody.setCorpo(dettaglio.getMassiveTestata().getParametro("BODYMAIL_NAME"));
		}
		SegnapostoAnaBuilder anaBuilder = new SegnapostoAnaBuilder();
		oggettoAndBody.setOggetto(anaBuilder.sostituisci(oggettoAndBody.getOggetto(), dettaglio.getDestinatari().getAnagrafe()));
		oggettoAndBody.setCorpo(anaBuilder.sostituisci(oggettoAndBody.getCorpo(), dettaglio.getDestinatari().getAnagrafe()));
		if (configurazione.getContesto() == ContestoComunicazioneEnum.ISTANZE && IstanzeGroupEnum.ISTANZE.name()
			.equals(comunicazioniMassiveGenDAO.getGroupTypeForIstanze(dettaglio.getMassiveTestata().getId().getCodice()))) {
		    Istanze istanza = comunicazioniMassiveGenDAO.getIstanzaFromDettaglio(idDettaglioComunicazione);
		    if (istanza != null) {
			oggettoAndBodyReplaced = mailtipoService.replaceOggettoCorpo(oggettoAndBody, istanza, null);
		    } else {
			oggettoAndBodyReplaced = oggettoAndBody;
		    }
		} else if (configurazione.getContesto() == ContestoComunicazioneEnum.MERCATI && dettaglio.getMercatiMassiveDs() != null
			&& dettaglio.getMercatiMassiveDs().size() == 1) {
		    oggettoAndBodyReplaced = oggettoAndBody;
		    for (MercatiMassiveD massiveD : dettaglio.getMercatiMassiveDs()) {
			Autorizzazioni autorizzazione = massiveD.getAutorizzazione();
			if (autorizzazione.getIstanza() != null) {
			    SegnapostoAutBuilder segnapostoAutBuilder = new SegnapostoAutBuilder();
			    oggettoAndBodyReplaced.setOggetto(segnapostoAutBuilder.sostituisci(oggettoAndBodyReplaced.getOggetto(), autorizzazione));
			    oggettoAndBodyReplaced.setCorpo(segnapostoAutBuilder.sostituisci(oggettoAndBodyReplaced.getCorpo(), autorizzazione));
			}
			break;
		    }
		} else {
		    oggettoAndBodyReplaced = oggettoAndBody;
		}
	    }
	    try {
		inviaAppIo(idDettaglioComunicazione, configurazione, dettaglio, oggettoAndBodyReplaced);
	    } catch (Exception e) {
		if (configurazione.getWarnings() == null) {
		    configurazione.setWarnings(new ArrayList<String>());
		}
		Throwable causa = e;
		while (causa.getCause() != null) {
		    causa = causa.getCause();
		}
		String messaggio = causa.getMessage();
		configurazione.getWarnings().add("Errore durante invio appio: " + messaggio);
		EventoComunicazioneInviata eventoComunicazioneInviata = new EventoComunicazioneInviata(configurazione.getContesto(),
			dettaglio.getId().getCodice());
		eventoComunicazioneInviata.setWarnings(configurazione.getWarnings());
		publisher.publishThrowOnFailure(eventoComunicazioneInviata); //In questo caso finirà in Warning
		return;
	    }
	    EventoAppIoSchedulata eventoAppIoSchedulata = new EventoAppIoSchedulata(configurazione.getContesto(), dettaglio.getId().getCodice());
	    eventoAppIoSchedulata.setWarnings(configurazione.getWarnings());
	    publisher.publishThrowOnFailure(eventoAppIoSchedulata);
	} catch (Exception e) {
	    log.error("Errore durante invio comunicazione", e);
	    comunicazioniMassiveDettaglioDAO.salvaErrore(idDettaglioComunicazione, e + "");
	}
    }

    private void inviaAppIo(int idDettaglioComunicazione, ConfigurazioniComunicazioneGen configurazione, MassiveDettaglio dettaglio,
	    Mailtipo oggettoAndBodyReplaced) {

	AppIoServiziConfig ioServiziConfig = null;
	if (configurazione.getContesto() == ContestoComunicazioneEnum.MERCATI) {
	    List<SoftwareComuneDataBean> softwareAndComuneMercatilist = comunicazioniMassiveGenDAO
		    .getMercatoDettaglioDByIdDett(idDettaglioComunicazione);
	    if (softwareAndComuneMercatilist == null || softwareAndComuneMercatilist.isEmpty()) {
		throw new RuntimeException("Appio empty software and comune found");
	    }
	    for (SoftwareComuneDataBean bean : softwareAndComuneMercatilist) {
		Istanze istanza = new Istanze();
		Software software = new Software();
		software.setCodice(bean.getSoftware());
		Comuni comune = new Comuni();
		comune.setCodicecomune(bean.getCodiceComune());
		istanza.setSoftware(software);
		istanza.setComune(comune);
		AppIoServiziConfig ioServiziConfigTemp = appIoServiziConfigServiceImpl
			.findByIdServizioEIstanza(dettaglio.getMassiveTestata().getParametro("APPIO_SERVIZIO"), istanza);
		if (ioServiziConfigTemp == null || !ioServiziConfigTemp.isAttivo()) {
		    continue;
		}
		ioServiziConfig = ioServiziConfigTemp;
		break;
	    }
	    if (ioServiziConfig == null) {
		throw new RuntimeException("No authorization for Appio send");
	    }
	} else if (configurazione.getContesto() == ContestoComunicazioneEnum.ISTANZE) {
	    List<Istanze> istanze = comunicazioniMassiveGenDAO.getIstanzeDettaglioDByIdDett(idDettaglioComunicazione);
	    if (istanze == null || istanze.isEmpty()) {
		throw new RuntimeException("Appio empty software and comune found");
	    }
	    for (Istanze istanzaF : istanze) {
		AppIoServiziConfig ioServiziConfigTemp = appIoServiziConfigServiceImpl
			.findByIdServizioEIstanza(dettaglio.getMassiveTestata().getParametro("APPIO_SERVIZIO"), istanzaF);
		if (ioServiziConfigTemp == null || !ioServiziConfigTemp.isAttivo()) {
		    continue;
		}
		ioServiziConfig = ioServiziConfigTemp;
		break;
	    }
	    if (ioServiziConfig == null) {
		throw new RuntimeException("No authorization for Appio send");
	    }
	} else {
	    throw new RuntimeException("No contesto found");
	}
	String oggetto = oggettoAndBodyReplaced.getOggetto();
	String corpo = oggettoAndBodyReplaced.getCorpo();
	//FACCIO VALIDAZIONE QUI ALTRIMENTI L'ECCEZIONE NON VIENE BEN GESTITA PER VIA DI HIBERNATE, IN ALTERNATIVA CI VORREBBERO STRADE PIU' COMPLESSE
	try {
	    if (oggetto.getBytes("UTF-8").length < 10) {
		throw new RuntimeException("L'oggetto non può avere lunghezza inferiore a 10");
	    }
	    if (oggetto.getBytes("UTF-8").length > 120) {
		throw new RuntimeException("L'oggetto non può avere lunghezza superiore a 120");
	    }
	    if (corpo.getBytes("UTF-8").length < 80) {
		throw new RuntimeException("Il body non può avere lunghezza inferiore a 80");
	    }
	    //		if(corpo.getBytes("UTF-8").length > 10000){
	    //		    throw new RuntimeException("L'oggetto non può avere lunghezza superiore a 10000");
	    //		}
	} catch (UnsupportedEncodingException e) {
	    throw new RuntimeException(e);
	}
	//PER NOI E' SUFFICIENTE SALVARE IL RECORD NELLA CODA
	appIoCodaService.inviaAppIo(ioServiziConfig, dettaglio.getMassiveTestata().getParametro("APPIO_SERVIZIO"), oggetto, corpo,
		dettaglio.getDestinatari().getAnagrafe(), idDettaglioComunicazione);
    }
}
