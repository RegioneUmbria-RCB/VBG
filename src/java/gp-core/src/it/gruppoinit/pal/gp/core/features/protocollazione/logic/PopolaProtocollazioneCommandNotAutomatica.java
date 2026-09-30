package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.MovimentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.ProtocolloSoggettoCommand;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.TipisoggettopeopleService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.protocollo.schemas.messages.AllegatoType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloFascicolatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.EnumFascicolatoType;

public class PopolaProtocollazioneCommandNotAutomatica {

    private static final Logger log = LoggerFactory.getLogger(PopolaProtocollazioneCommandNotAutomatica.class);
    private IstanzeprocedimentiService istanzeprocedimentiService;
    private OggettiService oggettiService;
    private DocumentiistanzaService documentiistanzaService;
    private IstanzeallegatiService istanzeallegatiService;
    private ProtocollazioneService protocollazioneService;
    private AmministrazioniService amministrazioniService;
    private MailtipoService mailtipoService;
    private MovimentiallegatiService movimentiallegatiService;
    private Movimenti entity;
    private TipimovStcMapping mapping;
    private TipisoggettopeopleService tipisoggettopeopleService;
    private MovimentiDAO movimentiDAO;
    private IVerticalizzazioneProtocolloAttivoService iVerticalizzazioneProtocolloAttivoService;

    public PopolaProtocollazioneCommandNotAutomatica(IstanzeprocedimentiService istanzeprocedimentiService, OggettiService oggettiService,
	    DocumentiistanzaService documentiistanzaService, IstanzeallegatiService istanzeallegatiService,
	    ProtocollazioneService protocollazioneService, AmministrazioniService amministrazioniService, MailtipoService mailtipoService,
	    MovimentiallegatiService movimentiallegatiService, Movimenti entity, TipimovStcMapping mapping,
	    TipisoggettopeopleService tipisoggettopeopleService, MovimentiDAO movimentiDAO,
	    IVerticalizzazioneProtocolloAttivoService iVerticalizzazioneProtocolloAttivoService) {

	this.istanzeprocedimentiService = istanzeprocedimentiService;
	this.oggettiService = oggettiService;
	this.documentiistanzaService = documentiistanzaService;
	this.istanzeallegatiService = istanzeallegatiService;
	this.protocollazioneService = protocollazioneService;
	this.amministrazioniService = amministrazioniService;
	this.mailtipoService = mailtipoService;
	this.movimentiallegatiService = movimentiallegatiService;
	this.entity = entity;
	this.mapping = mapping;
	this.tipisoggettopeopleService = tipisoggettopeopleService;
	this.movimentiDAO = movimentiDAO;
	this.iVerticalizzazioneProtocolloAttivoService = iVerticalizzazioneProtocolloAttivoService;
    }

    public ProtocollazioneCommand popolaCommand() {

	log.debug("popolaCommand...");
	Istanze istanza = entity.getIstanza();
	movimentiDAO.refreshEntity(istanza);
	ProtocollazioneCommand pc = new ProtocollazioneCommand();
	pc.setProvenienza(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI);
	pc.setComune(istanza.getComune());
	pc.setProtSoftware(istanza.getSoftware());
	pc.setMovimento(entity);
	pc.setEntity(istanza);
	pc.setSmistamento(
		protocollazioneService.findProtocolloSmistamentoDefault(istanza.getComune().getCodicecomune(), istanza.getSoftware().getCodice()));
	pc.setToken(ORMHelper.getToken());
	leggiDatiFascicolo(pc, istanza);
	Mailtipo mt = mailtipoService.replaceOggettoCorpo(mailtipoService.findById(new PkId(mapping.getMailtipo().getId().getCodice())), istanza,
		entity);
	pc.setOggetto(mt.getOggetto());
	pc.setFlusso(mapping.getProtocolloFlusso().getCodice());
	ProtMittDestAutoResolverNotAutomatica mittDestResolver = new ProtMittDestAutoResolverNotAutomatica(iVerticalizzazioneProtocolloAttivoService,
		istanza, tipisoggettopeopleService);
	pc.setMittente(
		mittDestResolver.resolveMittenteAmministrazione(amministrazioniService.findById(mapping.getAmministrazioneMittente().getId())));
	pc.setClassifica(protocollazioneService.findClassifica(istanza, istanza.getComune().getCodicecomune(), istanza.getSoftware().getCodice()));
	List<ProtocolloSoggettoCommand> destinatari = new ArrayList<ProtocolloSoggettoCommand>();
	if (BooleanUtils.toBoolean(mapping.getFlgSovrascriviAmmDest())) { // il destinatario è l'amministrazione del movimento
	    destinatari
		    .add(mittDestResolver.resolveDestinatarioAmministrazione(amministrazioniService.findById(mapping.getAmministrazioni().getId())));
	} else {
	    destinatari.addAll(mittDestResolver.resolveMittDestAnagrafe());
	}
	pc.setDestinataris(destinatari);
	pc.setInserimentoAutomatico(true);
	pc.setFlusso(mapping.getProtocolloFlusso().getCodice());
	String tipodocumento = mapping.getProtocolloTipidocumentoCodice();
	pc.setTipoDocumento(tipodocumento);
	pc.setAllegatiGenerici(getAllegati());
	if ("P".equals(pc.getFlusso())) {
	    Mailtipo protocolloMailToReplaced = mailtipoService
		    .replaceOggettoCorpoProtocollo(mailtipoService.findById(new PkId(mapping.getMailtipo().getId().getCodice())), istanza, entity);
	    pc.setOggettoProtocolloMail(protocolloMailToReplaced.getProtocolloOggettoMail());
	    pc.setCorpoProtocolloMail(protocolloMailToReplaced.getProtocolloCorpoMail());
	}
	return pc;
    }

    private void leggiDatiFascicolo(ProtocollazioneCommand command, Istanze istanza) {

	log.debug("leggiDatiFascicolo");
	if (!iVerticalizzazioneProtocolloAttivoService.isForzaFascicolazioneNotificaAutomatica()) {
	    log.debug("leggiDatiFascicolo il movimento NON deve fascicolare");
	    command.setForzaNonFascicolareInProtocollazioneXML(true);
	    return;
	}
	command.setForzaNonFascicolareInProtocollazioneXML(true);
	log.debug("leggiDatiFascicolo: Risulta attivo il parametro di verticalizzazione {}. Verifico se il movimento deve fascicolare",
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FORZA_FASCICOL_NOT_AUTOMATICA);
	DatiProtocolloFascicolatoResponseType dpf = protocollazioneService.isFascicolato(ORMHelper.getToken(), istanza.getFkidprotocollo(),
		istanza.getNumeroprotocollo(), istanza.getDataprotocollo(), istanza.getSoftware().getCodice(), istanza.getComune().getCodicecomune());
	if (dpf != null && dpf.getFascicolato() != null && dpf.getFascicolato().equals(EnumFascicolatoType.SI)) {
	    log.debug(
		    "leggiDatiFascicolo: Risulta attivo il parametro di verticalizzazione {}. L'istanza risulta fascicolata il movimento DEVE fascicolare",
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FORZA_FASCICOL_NOT_AUTOMATICA);
	    command.setForzaNonFascicolareInProtocollazioneXML(false);
	    command.setClassificaFascicolo(dpf.getClassifica());
	    command.setOggettoFascicolo(dpf.getOggetto());
	    command.setNumeroFascicolo(dpf.getNumeroFascicolo());
	    if (StringUtils.isNotBlank(dpf.getAnnoFascicolo()) && Utilities.isInteger(dpf.getAnnoFascicolo())) {
		command.setAnnoFascicolo(Integer.parseInt(dpf.getAnnoFascicolo()));
	    }
	    if (StringUtils.isNotBlank(dpf.getDataFascicolo())) {
		Date dataFascicolo = Utilities.parseDateString(dpf.getDataFascicolo(), false);
		command.setDataFascicolo(dataFascicolo);
	    } else {
		command.setDataFascicolo(null);
	    }
	}
	log.debug(
		"leggiDatiFascicolo: Risulta attivo il parametro di verticalizzazione {}. L'istanza risulta fascicolata il movimento fascicolerà? {}",
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FORZA_FASCICOL_NOT_AUTOMATICA,
		!command.isForzaNonFascicolareInProtocollazioneXML());
    }

    private List<AllegatoType> getAllegati() {

	List<AllegatoType> allegati = new ArrayList<AllegatoType>();
	if (BooleanUtils.toBoolean(mapping.getFlgProtocollaDocumenti())) {
	    if (mapping.getFlagAllegaDocumentiEndo()) {
		List<Istanzeprocedimenti> ips = istanzeprocedimentiService.findByIstanze(entity.getIstanza());
		for (Istanzeprocedimenti procedimento : ips) {
		    List<Istanzeallegati> ialls = istanzeallegatiService.findByIstanzaAndEndo(entity.getIstanza().getId().getCodice(),
			    procedimento.getId().getCodiceinventario());
		    if (!ialls.isEmpty()) {
			for (Istanzeallegati istanzeallegati : ialls) {
			    if (EntityUtils.getNestedProperty(istanzeallegati.getOggetto(), "id.codice") != null) {
				AllegatoType at = new AllegatoType();
				Oggetti ogg = oggettiService.findByIdLazy(new PkId(istanzeallegati.getOggetto().getId().getCodice()));
				at.setCod(String.valueOf(ogg.getId().getCodice()));
				at.setDescrizione(ogg.getNomefile());
				at.setInviaTramitePec(Boolean.TRUE); // di default mettiamo a true BOCCI/MENDICHI 2022-08-10
				allegati.add(at);
			    }
			}
		    }
		}
	    }
	    if (mapping.getFlagAllegaDocumentiIstanza()) {
		List<Documentiistanza> docs = documentiistanzaService.findByIstanzaOggetto(entity.getIstanza().getId().getCodice());
		for (Documentiistanza documentiistanza : docs) {
		    if (EntityUtils.getNestedProperty(documentiistanza.getOggetto(), "id.codice") != null) {
			AllegatoType at = new AllegatoType();
			Oggetti ogg = oggettiService.findByIdLazy(new PkId(documentiistanza.getOggetto().getId().getCodice()));
			at.setCod(String.valueOf(ogg.getId().getCodice()));
			at.setDescrizione(ogg.getNomefile());
			at.setInviaTramitePec(Boolean.TRUE); // di default mettiamo a true BOCCI/MENDICHI 2022-08-10
			allegati.add(at);
		    }
		}
	    }
	}
	List<MovimentiallegatiDTO> movalls = movimentiallegatiService.findMovimentiallegatiDTOByMovimenti(entity.getId().getCodice());
	for (MovimentiallegatiDTO movall : movalls) {
	    if (movall.getCodiceOggetto() != null) {
		AllegatoType at = new AllegatoType();
		Oggetti ogg = oggettiService.findByIdLazy(new PkId(movall.getCodiceOggetto()));
		at.setCod(String.valueOf(ogg.getId().getCodice()));
		at.setDescrizione(ogg.getNomefile());
		at.setInviaTramitePec(Boolean.TRUE); // di default mettiamo a true BOCCI/MENDICHI 2022-08-10
		allegati.add(at);
	    }
	}
	///	
	return allegati;
    }
}
