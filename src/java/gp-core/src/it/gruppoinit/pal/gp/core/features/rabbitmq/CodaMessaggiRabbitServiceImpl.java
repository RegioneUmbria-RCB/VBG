package it.gruppoinit.pal.gp.core.features.rabbitmq;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.CodaMessaggiRabbitDAO;
import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CodaMessaggiRabbit;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbit;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoRabbitId;
import it.gruppoinit.pal.gp.core.features.istanze.eventi.NotificaSoggettiIstanzaAggiornatiRequest;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit.ITipimovimentoRabbitDAO;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.rabbit.ITipimovimentoRabbitService;
import it.gruppoinit.pal.gp.core.features.movimenti.metadati.IMovimentiMetadatiDAO;
import it.gruppoinit.pal.gp.core.features.movimenti.scadenze.rabbitmq.NotificaScadenzeRequest;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.MovimentiRabbitTestoBean;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitTopicEnum;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.messaggi.BodyMessaggioScadenza;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.messaggi.ComunicazioniUtenteBodyMessaggi;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.messaggi.MessaggiRabbitMQBroker;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.messaggi.PraticheBodyMessaggi;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class CodaMessaggiRabbitServiceImpl implements CodaMessaggiRabbitService {

    private static final Logger logger = LoggerFactory.getLogger(CodaMessaggiRabbitServiceImpl.class);
    private static final String PROVENIENZA_MESSAGGI_RABBIT = "backend";
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IstanzeDAO istanzeDAO;
    @Autowired
    private CodaMessaggiRabbitDAO codaMessaggiRabbitDAO;
    @Autowired
    private MovimentiNoSecurityService movimentiNoSecurityService;
    @Autowired
    private IMovimentiMetadatiDAO movimentiMetadatiDAO;
    @Autowired
    private ITipimovimentoRabbitService tmRS;
    @Autowired
    private ITipimovimentoRabbitDAO iTipimovimentoRabbitDAO;
    @Autowired
    private IVerticalizzazioneRabbitMQService iVerticalizzazioneRabbitMQService;

    private void insert(CodaMessaggiRabbit codaMessaggiRabbit) {

	if (!iVerticalizzazioneRabbitMQService.isAttiva()) {
	    return;
	}
	codaMessaggiRabbitDAO.insert(codaMessaggiRabbit);
	codaMessaggiRabbitDAO.flush();
    }

    private int countByUidIstanza(String idcomune, String uuidPratica) {

	if (!iVerticalizzazioneRabbitMQService.isAttiva()) {
	    return 0;
	}
	return codaMessaggiRabbitDAO.countByUidIstanza(idcomune, uuidPratica);
    }

    @Override
    public void insertScadenza(NotificaScadenzeRequest request) {

	if (!iVerticalizzazioneRabbitMQService.isAttiva()) {
	    return;
	}
	logger.debug("insertScadenza: {}", request);
	if (tmRS.checkTipimovRabbitByTipomovAndTopic(request.getTipoMovimento(), request.getTopic())
		&& (this.countByUidIstanza(ORMHelper.getIdcomune(), request.getUuidPratica()) > 0)) {
	    try {
		insert(populateCodaScadenze(request));
	    } catch (ClassNotFoundException e) {
		throw new RuntimeException(e);
	    } catch (JAXBException e) {
		throw new RuntimeException(e);
	    }
	}
    }

    @Override
    public void insertSoggettiAggiornati(NotificaSoggettiIstanzaAggiornatiRequest request) {

	if (!iVerticalizzazioneRabbitMQService.isAttiva()) {
	    return;
	}
	logger.debug("insertSoggettiAggiornati: {}", request);
	if (this.countByUidIstanza(ORMHelper.getIdcomune(), request.getUuidPratica()) > 0) {
	    try {
		insert(populateCodaSoggettiAggiornati(request));
	    } catch (ClassNotFoundException e) {
		throw new RuntimeException(e);
	    } catch (JAXBException e) {
		throw new RuntimeException(e);
	    }
	}
    }

    @Override
    public void insertCambioStato(Integer codiceIstanza) {

	if (!iVerticalizzazioneRabbitMQService.isAttiva()) {
	    return;
	}
	logger.debug("insertCambioStato: {}", codiceIstanza);
	Istanze istanza = istanzeDAO.findById(new PkId(codiceIstanza));
	if (this.countByUidIstanza(ORMHelper.getIdcomune(), istanza.getUuid()) > 0) {
	    try {
		insert(populateCodaCambioStato(istanza));
	    } catch (ClassNotFoundException e) {
		throw new RuntimeException(e);
	    } catch (JAXBException e) {
		throw new RuntimeException(e);
	    }
	}
    }

    @Override
    public void insertNotificaPraticaNuova(Integer codiceIstanza) {

	if (!iVerticalizzazioneRabbitMQService.isAttiva()) {
	    return;
	}
	logger.debug("insertNotificaPraticaNuova: {}", codiceIstanza);
	try {
	    insert(populateCodaNuovaPratica(codiceIstanza));
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException(e);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void insertNuovaComunicazioneUtente(Integer codiceIstanza) {

	if (!iVerticalizzazioneRabbitMQService.isAttiva()) {
	    return;
	}
	logger.debug("insertNuovaComunicazioneUtente: {}", codiceIstanza);
	try {
	    Istanze istanza = istanzeDAO.findById(new PkId(codiceIstanza));
	    Movimenti movimentoAvvioIstanza = movimentiNoSecurityService.findMovimentoAvvioIstanza(istanza);
	    if (tmRS.checkTipimovRabbitByTipomovAndTopic(movimentoAvvioIstanza.getTipomovimento().getId().getTipomovimento(),
		    RabbitTopicEnum.BACKEND_COMUNICAZIONI_UTENTE_NUOVA)) {
		Integer codiceMailTipo = iTipimovimentoRabbitDAO.recuperaTestoTipodaMovimentoETopic(
			movimentoAvvioIstanza.getTipomovimento().getId().getTipomovimento(),
			RabbitTopicEnum.BACKEND_COMUNICAZIONI_UTENTE_NUOVA.getValue());
		if (codiceMailTipo != null) {
		    insert(populateNuovaComunicazione(istanza, movimentoAvvioIstanza));
		}
	    }
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException(e);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void insertMessaggioPraticaCancellata(String uuidIstanza) {

	if (!iVerticalizzazioneRabbitMQService.isAttiva()) {
	    return;
	}
	logger.debug("insertMessaggioPraticaCancellata: {}", uuidIstanza);
	try {
	    if (this.countByUidIstanza(ORMHelper.getIdcomune(), uuidIstanza) > 0) {
		insert(populatePraticaEliminata(uuidIstanza));
	    }
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException(e);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }

    private CodaMessaggiRabbit populateCodaScadenze(NotificaScadenzeRequest request) throws ClassNotFoundException, JAXBException {

	CodaMessaggiRabbit ret = fromDati(request.getUuidPratica(), request.getTopic(), request.getUuIdMovimento());
	Set<String> cfRichiedentiPrincipaliIstanzaAut = istanzeService.getCfRichiedentiPrincipaliIstanzaAut(request.getCodiceIstanza());
	MessaggiRabbitMQBroker<BodyMessaggioScadenza> broker = new MessaggiRabbitMQBroker<BodyMessaggioScadenza>(ORMHelper.getIdcomuneAlias(),
		ORMHelper.getSoftware());
	BodyMessaggioScadenza bdms = new BodyMessaggioScadenza();
	bdms.setProvenienza(PROVENIENZA_MESSAGGI_RABBIT);
	List<String> mainList = new ArrayList<String>();
	mainList.addAll(cfRichiedentiPrincipaliIstanzaAut);
	bdms.setCodiciFiscaliDestinatari(mainList);
	bdms.setUuid(request.getUuIdMovimento());
	bdms.setUuidPratica(request.getUuidPratica());
	broker.setBody(bdms);
	String messaggio = Utilities.marshalJsonObject(broker, MessaggiRabbitMQBroker.class, false, Utilities.JAXB_ENCODING_UTF_8);
	ret.setMessaggio(messaggio);
	return ret;
    }

    private CodaMessaggiRabbit populateCodaCambioStato(Istanze istanza) throws ClassNotFoundException, JAXBException {

	Set<String> cfRichiedentiPrincipaliIstanzaAut = istanzeService.getCfRichiedentiPrincipaliIstanzaAut(istanza.getId().getCodice());
	List<String> mainList = new ArrayList<String>();
	mainList.addAll(cfRichiedentiPrincipaliIstanzaAut);
	CodaMessaggiRabbit ret = fromDati(istanza.getUuid(), RabbitTopicEnum.BACKEND_PRATICHE_CAMBIO_STATO, null);
	MessaggiRabbitMQBroker<PraticheBodyMessaggi> broker = new MessaggiRabbitMQBroker<PraticheBodyMessaggi>(ORMHelper.getIdcomuneAlias(),
		ORMHelper.getSoftware());
	PraticheBodyMessaggi bdms = new PraticheBodyMessaggi();
	bdms.setCodiciFiscaliDestinatari(mainList);
	bdms.setProvenienza(PROVENIENZA_MESSAGGI_RABBIT);
	bdms.setUuid(istanza.getUuid());
	broker.setBody(bdms);
	String messaggio = Utilities.marshalJsonObject(broker, MessaggiRabbitMQBroker.class, false, Utilities.JAXB_ENCODING_UTF_8);
	ret.setMessaggio(messaggio);
	return ret;
    }

    private CodaMessaggiRabbit populatePraticaEliminata(String uuidPratica) throws ClassNotFoundException, JAXBException {

	CodaMessaggiRabbit ret = fromDati(uuidPratica, RabbitTopicEnum.BACKEND_PRATICHE_ELIMINATA, null);
	MessaggiRabbitMQBroker<PraticheBodyMessaggi> broker = new MessaggiRabbitMQBroker<PraticheBodyMessaggi>(ORMHelper.getIdcomuneAlias(),
		ORMHelper.getSoftware());
	PraticheBodyMessaggi bdms = new PraticheBodyMessaggi();
	bdms.setProvenienza(PROVENIENZA_MESSAGGI_RABBIT);
	bdms.setUuid(uuidPratica);
	broker.setBody(bdms);
	String messaggio = Utilities.marshalJsonObject(broker, MessaggiRabbitMQBroker.class, false, Utilities.JAXB_ENCODING_UTF_8);
	ret.setMessaggio(messaggio);
	return ret;
    }

    private CodaMessaggiRabbit populateNuovaComunicazione(Istanze istanza, Movimenti movimentoAvvioIstanza)
	    throws ClassNotFoundException, JAXBException {

	Set<String> cfRichiedentiPrincipaliIstanzaAut = istanzeService.getCfRichiedentiPrincipaliIstanzaAut(istanza.getId().getCodice());
	List<String> mainList = new ArrayList<String>();
	mainList.addAll(cfRichiedentiPrincipaliIstanzaAut);
	TipimovimentoRabbitId tipimovimentoRabbitId = new TipimovimentoRabbitId(ORMHelper.getIdcomune(),
		movimentoAvvioIstanza.getTipomovimento().getId().getTipomovimento(), RabbitTopicEnum.BACKEND_COMUNICAZIONI_UTENTE_NUOVA.getValue());
	TipimovimentoRabbit tipimovimentoRabbit = tmRS.findById(tipimovimentoRabbitId);
	String uuidMovimento = movimentiMetadatiDAO.getUuid(movimentoAvvioIstanza.getId().getCodice());
	CodaMessaggiRabbit ret = fromDati(istanza.getUuid(), RabbitTopicEnum.BACKEND_COMUNICAZIONI_UTENTE_NUOVA, uuidMovimento);
	MessaggiRabbitMQBroker<ComunicazioniUtenteBodyMessaggi> broker = new MessaggiRabbitMQBroker<ComunicazioniUtenteBodyMessaggi>(
		ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware());
	ComunicazioniUtenteBodyMessaggi bdms = new ComunicazioniUtenteBodyMessaggi();
	bdms.setCategoria(tipimovimentoRabbit.getCategoria());
	bdms.setCodiciFiscaliDestinatari(mainList);
	bdms.setDataCreazione(Calendar.getInstance().getTime());
	MovimentiRabbitTestoBean testo = movimentiNoSecurityService.replaceTestoPerMovimentoeTopic(movimentoAvvioIstanza.getId().getCodice(),
		RabbitTopicEnum.BACKEND_COMUNICAZIONI_UTENTE_NUOVA.getValue());
	bdms.setSottotitolo(testo.getSottoTitolo());
	bdms.setTesto(testo.getMessaggio());
	bdms.setTitolo(testo.getTitolo());
	bdms.setUuid(UUID.randomUUID().toString());
	broker.setBody(bdms);
	String messaggio = Utilities.marshalJsonObject(broker, MessaggiRabbitMQBroker.class, false, Utilities.JAXB_ENCODING_UTF_8);
	ret.setMessaggio(messaggio);
	return ret;
    }

    private CodaMessaggiRabbit populateCodaNuovaPratica(Integer codiceIstanza) throws ClassNotFoundException, JAXBException {

	Set<String> cfRichiedentiPrincipaliIstanzaAut = istanzeService.getCfRichiedentiPrincipaliIstanzaAut(codiceIstanza);
	List<String> mainList = new ArrayList<String>();
	mainList.addAll(cfRichiedentiPrincipaliIstanzaAut);
	Istanze istanza = istanzeDAO.findById(new PkId(codiceIstanza));
	CodaMessaggiRabbit ret = fromDati(istanza.getUuid(), RabbitTopicEnum.BACKEND_PRATICHE_NUOVA, null);
	MessaggiRabbitMQBroker<PraticheBodyMessaggi> broker = new MessaggiRabbitMQBroker<PraticheBodyMessaggi>(ORMHelper.getIdcomuneAlias(),
		ORMHelper.getSoftware());
	PraticheBodyMessaggi bdms = new PraticheBodyMessaggi();
	bdms.setCodiciFiscaliDestinatari(mainList);
	bdms.setProvenienza(PROVENIENZA_MESSAGGI_RABBIT);
	bdms.setUuid(istanza.getUuid());
	broker.setBody(bdms);
	String messaggio = Utilities.marshalJsonObject(broker, MessaggiRabbitMQBroker.class, false, Utilities.JAXB_ENCODING_UTF_8);
	ret.setMessaggio(messaggio);
	return ret;
    }

    private CodaMessaggiRabbit populateCodaSoggettiAggiornati(NotificaSoggettiIstanzaAggiornatiRequest request)
	    throws ClassNotFoundException, JAXBException {

	Set<String> cfRichiedentiPrincipaliIstanzaAut = istanzeService.getCfRichiedentiPrincipaliIstanzaAut(request.getCodiceIstanza());
	List<String> mainList = new ArrayList<String>();
	mainList.addAll(cfRichiedentiPrincipaliIstanzaAut);
	CodaMessaggiRabbit ret = fromDati(request.getUuidPratica(), RabbitTopicEnum.BACKEND_PRATICHE_DESTINATARI_AGGIORNATI, null);
	MessaggiRabbitMQBroker<PraticheBodyMessaggi> broker = new MessaggiRabbitMQBroker<PraticheBodyMessaggi>(ORMHelper.getIdcomuneAlias(),
		ORMHelper.getSoftware());
	PraticheBodyMessaggi bdms = new PraticheBodyMessaggi();
	bdms.setCodiciFiscaliDestinatari(mainList);
	bdms.setProvenienza(PROVENIENZA_MESSAGGI_RABBIT);
	bdms.setUuid(request.getUuidPratica());
	broker.setBody(bdms);
	String messaggio = Utilities.marshalJsonObject(broker, MessaggiRabbitMQBroker.class, false, Utilities.JAXB_ENCODING_UTF_8);
	ret.setMessaggio(messaggio);
	return ret;
    }

    private CodaMessaggiRabbit fromDati(String uuidIstanza, RabbitTopicEnum topic, String uuidMovimento) throws ClassNotFoundException {

	CodaMessaggiRabbit ret = CodaMessaggiRabbit.defaultVal(ORMHelper.getIdcomune());
	ret.setTopic(topic.nomeTopicInMessaggi);
	ret.setUuidIstanza(uuidIstanza);
	ret.setUuidMovimento(uuidMovimento);
	return ret;
    }
}
