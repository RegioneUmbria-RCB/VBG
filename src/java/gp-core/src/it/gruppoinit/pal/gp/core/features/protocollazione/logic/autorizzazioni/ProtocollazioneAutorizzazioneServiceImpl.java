package it.gruppoinit.pal.gp.core.features.protocollazione.logic.autorizzazioni;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.helper.ProtocolloRegistriHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocolloAmministrazioniBuilder;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocolloAnagrafeBuilder;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocolloSourceEnum;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.ProtocollazioneFallitaException;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrProtocolloService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.ProtocolloRegistriService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.ws.client.ProtocolloWSClient;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfAllegatoType;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfProtocolloAmministrazioni;
import it.gruppoinit.protocollo.schemas.messages.ArrayOfProtocolloAnagrafe;
import it.gruppoinit.protocollo.schemas.messages.DatiDestinatariXmlType;
import it.gruppoinit.protocollo.schemas.messages.DatiMittentiXmlType;
import it.gruppoinit.protocollo.schemas.messages.DatiRequestType;
import it.gruppoinit.protocollo.schemas.messages.IProtocollazioneService;
import it.gruppoinit.protocollo.schemas.messages.ProtocollazioneMovimentoXmlRequestType;
import it.gruppoinit.protocollo.schemas.messages.ProtocolloAmministrazioni;
import it.gruppoinit.protocollo.schemas.messages.ProtocolloAnagrafe;

@Service
public class ProtocollazioneAutorizzazioneServiceImpl implements IProtocollazioneAutorizzazioneService {

    private ProtocolloRegistriService protocolloRegistriService;
    private IstanzeService istanzeService;
    private MovimentiService movimentiService;
    private ResponsabiliService responsabiliService;
    private MailtipoService mailtipoService;
    private VerticalizzazioniService verticalizzazioniService;
    private ComuniassociatiService comuniAssociatiService;
    private AmministrazioniService amministrazioniService;
    private AmministrProtocolloService amministrazioniProtocolloService;
    private AnagrafeService anagrafeService;

    @Autowired
    public void setProtocolloRegistriService(ProtocolloRegistriService protocolloRegistriService) {

	this.protocolloRegistriService = protocolloRegistriService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setComuniAssociatiService(ComuniassociatiService comuniAssociatiService) {

	this.comuniAssociatiService = comuniAssociatiService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setAmministrazioniProtocolloService(AmministrProtocolloService amministrazioniProtocolloService) {

	this.amministrazioniProtocolloService = amministrazioniProtocolloService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Override
    public ProtocollaAutorizzazioneResponse protocolla(Tipologiaregistri registro, String codiceComune, String software, Calendar dataAutorizzazione,
	    Autorizzazioni autorizzazione, int codiceResponsabile, String token) throws ProtocollazioneFallitaException {

	try {
	    if (null == registro) {
		throw new ProtocollazioneFallitaException("Il registro è nullo");
	    }
	    ProtocollaAutorizzazioneResponse response = new ProtocollaAutorizzazioneResponse();
	    String nomeRegistro = registro.getTrDescrizione();
	    //1. Verifica che il comune sia valido e presente nell'installazione
	    this.verificaComuneNellInstallazione(codiceComune);
	    //2. Recupera l'helper con alcune configurazioni della tabella PROTOCOLLO_REGISTRI
	    ProtocolloRegistriHelper protocolloRegistriHelper = protocolloRegistriService.findByRegistroSoftwareComune(registro.getId().getCodice(),
		    codiceComune);
	    //3. Verifica gli altri dati necessari alla protocollazione
	    this.verificaDatiProtocollo(protocolloRegistriHelper, registro, nomeRegistro);
	    Istanze istanza = null;
	    Movimenti movimento = null;
	    int codiceMovimento = -1;
	    if (EntityUtils.getNestedProperty(autorizzazione.getIstanza(), "id.codice") != null) {
		istanza = istanzeService.findById(new PkId(autorizzazione.getIstanza().getId().getCodice().intValue()));
		if (protocolloRegistriHelper.getTipimovimento() != null
			&& StringUtils.isNotBlank(protocolloRegistriHelper.getTipimovimento().getId().getTipomovimento())) {
		    movimento = new Movimenti();
		    movimento.setIstanza(istanza);
		    movimento.setTipomovimento(protocolloRegistriHelper.getTipimovimento());
		    movimento.setData(dataAutorizzazione.getTime());
		    movimento.setDatainserimento(dataAutorizzazione.getTime());
		    Responsabili responsabile = this.responsabiliService.findById(new PkId(codiceResponsabile));
		    movimento.setResponsabile(responsabile);
		    movimento.setEsito(Boolean.FALSE);
		    movimento.setPubblica(Boolean.FALSE);
		    movimento.setFlagDaLeggere(Boolean.TRUE);
		    movimentiService.insert(movimento);
		    codiceMovimento = movimento.getId().getCodice();
		    response.setMovimento(movimento);
		}
	    }
	    //5. Recupera l'oggetto del protocollo
	    Mailtipo mTipo = this.mailtipoService.replaceOggettoCorpo(protocolloRegistriHelper.getMailtipo(), istanza, movimento);
	    String oggetto = mTipo.getOggetto();
	    //6. Prepara la request per il protocollo
	    DatiRequestType bustaProtocollo = new DatiRequestType();
	    bustaProtocollo.setTipoDocumento(protocolloRegistriHelper.getIdtipodocumento());
	    bustaProtocollo.setOggetto(oggetto);
	    bustaProtocollo.setFlusso(protocolloRegistriHelper.getProtocolloFlusso().getCodice());
	    bustaProtocollo.setClassifica(protocolloRegistriHelper.getClassifica());
	    //7. Setta il mittente
	    this.impostaMittente(protocolloRegistriHelper, bustaProtocollo, codiceComune, software);
	    //8. Setta il destinatario
	    IVerticalizzazioneProtocolloAttivoService verticalizzazioneProtocolloAttivo = new VerticalizzazioneProtocolloAttivoServiceImpl(
		    verticalizzazioniService, codiceComune);
	    this.impostaDestinatario(verticalizzazioneProtocolloAttivo, protocolloRegistriHelper, bustaProtocollo, istanza, autorizzazione,
		    codiceComune, software);
	    //9. Setta gli allegati
	    this.impostaAllegati(bustaProtocollo);
	    IProtocollazioneService port = new ProtocolloWSClient(verticalizzazioniService).getWsPort();
	    if (codiceMovimento > 0) {
		ProtocollazioneMovimentoXmlRequestType request = new ProtocollazioneMovimentoXmlRequestType();
		request.setToken(token);
		request.setCodiceMovimento(String.valueOf(codiceMovimento));
		request.setDati(bustaProtocollo);
		request.setSource(ProtocolloSourceEnum.INSERIMENTO_NORMALE.getValue());
		response.setDatiProtocollo(port.protocollazioneMovimentoXml(request));
	    } else {
		response.setDatiProtocollo(port.protocollazioneXml(token, ORMHelper.getSoftware(), bustaProtocollo, codiceComune));
	    }
	    return response;
	} catch (Exception e) {
	    throw new ProtocollazioneFallitaException(e);
	}
    }

    private void verificaComuneNellInstallazione(String codiceComune) {

	if (StringUtils.isNotBlank(codiceComune)) {
	    ComuniassociatiId idCa = new ComuniassociatiId(codiceComune);
	    Comuniassociati ca = this.comuniAssociatiService.findById(idCa);
	    if (ca == null) {
		throw new ProtocollazioneFallitaException("Impossibile richiedere un protocollo per un comune non associato all'installazione.");
	    }
	}
    }

    private void verificaDatiProtocollo(ProtocolloRegistriHelper pProtocollo, Tipologiaregistri registro, String nomeRegistro) {

	if (pProtocollo == null) {
	    throw new ProtocollazioneFallitaException("Impossibile richiedere un protocollo. Non sono stati impostati i parametri per il registro " +
						      nomeRegistro + "(" + registro.getId().getCodice() + ")");
	}
	if (StringUtils.isBlank(pProtocollo.getClassifica())) {
	    throw new ProtocollazioneFallitaException(
		    "Impossibile richiedere un protocollo. Il parametro Classifica non è stato impostato per il registro " + nomeRegistro + "(" +
						      registro.getId().getCodice() + ")");
	}
	if (StringUtils.isBlank(pProtocollo.getIdtipodocumento())) {
	    throw new ProtocollazioneFallitaException(
		    "Impossibile richiedere un protocollo. Il parametro Tipo Documento non è stato impostato per il registro " + nomeRegistro + "(" +
						      registro.getId().getCodice() + ")");
	}
	if (pProtocollo.getMailtipo().getId().getCodice() == null) {
	    throw new ProtocollazioneFallitaException(
		    "Impossibile richiedere un protocollo. Il parametro Testo Tipo non è stato impostato per il registro " + nomeRegistro + "(" +
						      registro.getId().getCodice() + ")");
	}
	if (pProtocollo.getProtocolloFlusso() == null) {
	    throw new ProtocollazioneFallitaException(
		    "Impossibile richiedere un protocollo. Il parametro Flusso non è stato impostato per il registro " + nomeRegistro + "(" +
						      registro.getId().getCodice() + ")");
	}
	if (pProtocollo.getProtocolloFlusso().getCodice().equalsIgnoreCase("P")
		&& (pProtocollo.getMittente() == null || pProtocollo.getMittente().getId().getCodice() == null)) {
	    //Deve avere lo stesso comportamento del protocollo di tipo INTERNO
	    throw new ProtocollazioneFallitaException(
		    "Impossibile richiedere un protocollo. Il parametro Mittente non è stato impostato per il registro " + nomeRegistro + "(" +
						      registro.getId().getCodice() + ")");
	}
	if (pProtocollo.getProtocolloFlusso().getCodice().equalsIgnoreCase("I")) {
	    if (pProtocollo.getMittente() == null || pProtocollo.getMittente().getId().getCodice() == null) {
		throw new ProtocollazioneFallitaException(
			"Impossibile richiedere un protocollo. Il parametro Mittente non è stato impostato per il registro " + nomeRegistro + "(" +
							  registro.getId().getCodice() + ")");
	    }
	    if (pProtocollo.getDestinatario() == null || pProtocollo.getDestinatario().getId().getCodice() == null) {
		throw new ProtocollazioneFallitaException(
			"Impossibile richiedere un protocollo. Il parametro Destinatario non è stato impostato per il registro " + nomeRegistro +
							  "(" + registro.getId().getCodice() + ")");
	    }
	}
    }

    private void impostaMittente(ProtocolloRegistriHelper pProtocollo, DatiRequestType request, String codiceComune, String software) {

	Amministrazioni amm = this.amministrazioniService.findById(new PkId(pProtocollo.getMittente().getId().getCodice().intValue()));
	ProtocolloAmministrazioniBuilder builder = new ProtocolloAmministrazioniBuilder(this.amministrazioniProtocolloService);
	ProtocolloAmministrazioni amministrazione = builder.build(amm, codiceComune, software);
	ArrayOfProtocolloAmministrazioni amministrazioneMittente = new ArrayOfProtocolloAmministrazioni();
	amministrazioneMittente.getProtocolloAmministrazioni().add(amministrazione);
	DatiMittentiXmlType mittente = new DatiMittentiXmlType();
	mittente.setAmministrazione(amministrazioneMittente);
	request.setMittenti(mittente);
    }

    private void impostaDestinatario(IVerticalizzazioneProtocolloAttivoService verticalizzazioneProtocolloAttivo,
	    ProtocolloRegistriHelper protocolloRegHelper, DatiRequestType request, Istanze istanza, Autorizzazioni autorizzazione,
	    String codiceComune, String software) {

	////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////// GESTIONE DEL DESTINATARIO /////////////////////////////////////////////
	// IL destinatario può essere :
	//1. Un' AMMINISTRAZIONE nel caso di protocollo INTERNO 
	//2. Un ' ANAGRAFICA nel caso di protocollo in PARTENZA
	DatiDestinatariXmlType destinatari = new DatiDestinatariXmlType();
	// CASO 1: protocollo INTERNO, l'AMMINISTRAZIONE sarà presa dalla configurazione ProtocolloRegistri.getDestinatario()
	if (protocolloRegHelper.getProtocolloFlusso().getCodice().equalsIgnoreCase("I")) {
	    Amministrazioni amm = this.amministrazioniService
		    .findById(new PkId(protocolloRegHelper.getDestinatario().getId().getCodice().intValue()));
	    ProtocolloAmministrazioniBuilder builder = new ProtocolloAmministrazioniBuilder(this.amministrazioniProtocolloService);
	    ProtocolloAmministrazioni amministrazione = builder.build(amm, codiceComune, software);
	    ArrayOfProtocolloAmministrazioni amministrazioneDestinataria = new ArrayOfProtocolloAmministrazioni();
	    amministrazioneDestinataria.getProtocolloAmministrazioni().add(amministrazione);
	    destinatari.setAmministrazione(amministrazioneDestinataria);
	} else {
	    // CASO 2: protocollo PARTENZA, l'ANAGRAFICA sarà il richiedente dell'istanza AUTORIZZAZIONI.FK_CODICEANAGRAFE
	    Anagrafe anagrafe = this.anagrafeService.findById(new PkId(autorizzazione.getAnagrafe().getId().getCodice()));
	    ProtocolloAnagrafeBuilder builder = new ProtocolloAnagrafeBuilder();
	    Integer tipoGestionePec = verticalizzazioneProtocolloAttivo.getGestionePEC();
	    ProtocolloAnagrafe soggetto = builder.build(anagrafe, istanza, verticalizzazioneProtocolloAttivo.getMezzoDefault(),
		    verticalizzazioneProtocolloAttivo.getModalitaTrasmissioneDefault(), tipoGestionePec);
	    ArrayOfProtocolloAnagrafe anagrafeDestinatario = new ArrayOfProtocolloAnagrafe();
	    anagrafeDestinatario.getProtocolloAnagrafe().add(soggetto);
	    destinatari.setAnagrafe(anagrafeDestinatario);
	}
	request.setDestinatari(destinatari);
    }

    private void impostaAllegati(DatiRequestType request) {

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	// Questo blocco di codice è stato aggiunto perchè se al web service non arriva la struttura "Allegati",
	// va a prenderli automaticamente dall'istanza o dal movimento
	ArrayOfAllegatoType allegati = new ArrayOfAllegatoType();
	request.setAllegati(allegati);
    }
}
