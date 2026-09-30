package it.gruppoinit.pal.gp.pay.connector.entranext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.net.ssl.TrustManager;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.AbstractPayConnector;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.forehead.IntestazioneFO;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.AnnullaPosizioneDebitoriaRequest;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.AnnullaPosizioneDebitoriaResponse;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.CausaliImporti;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.Contribuente;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.InserisciPosizioneRequest;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.InserisciPosizioneResponse;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.InserisciPosizioniInAttesaRequest;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.InserisciPosizioniInAttesaResponse;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.LinkNextSoap;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.LoginRequest;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.LoginResponse;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.NaturaGiuridica;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.PagamentoPagoPA;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.PosizioneDebitoria;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.PosizioneDebitoriaDettaglio;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.PosizioneDebitoriaInAttesa;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.PosizioneDebitoriaInAttesaDettaglio;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.PosizioneDebitoriaInAttesaResult;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.PosizioneDebitoriaRata;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.PosizioneDebitoriaResult;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.RendicontazionePagamenti;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.RiceviEsitoTransazioneRequest;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.RiceviEsitoTransazioneResponse;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.SoggettoPagatore;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.SoggettoVersante;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.StatoPagamentoPagoPA;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.StatoPosizioneDebitoria;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.TipiDocumentiSDI;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.TipoChiaveApplicativa;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.Ubicazione;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.VerificaPosizioneRequest;
import it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione.VerificaPosizioneResponse;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayDocumenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroTipoDocumentoSdi;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;
import it.gruppoinit.pal.gp.pay.service.PayDettaglioImportiService;
import it.gruppoinit.pal.gp.pay.service.PayDocumentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.ParametriConnettoreHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PopolamentoDatiHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.EsitoOperazionePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPosizioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.TipoDocumentoType;
import it.gruppoinit.pal.gp.pay.ws.utils.TrustAllX509TrustManager;

public class EntraNextConnector extends AbstractPayConnector implements IPayConnector {

    private static final Logger log = LoggerFactory.getLogger(EntraNextConnector.class);
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;
    @Autowired
    private PayDocumentiService payDocumentiService;
    @Autowired
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    @Autowired
    private PosizioniDebitorieCommandService posizioniDebitorieCommandService;
    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    @Autowired
    private PayDettaglioImportiService payDettaglioImportiService;

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	InserisciPosizioneResponse resp = null;
	IntestazioneFO header = login();
	String error = null;
	if (header == null || StringUtils.isEmpty(header.getTokenAuth())) {
	    error = "Errore nella chiamata al servizio di autenticazione";
	}
	TipiDocumentiSDI td = getTipoDocumento(datiRegistrazioniCommand);
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    //predispongo gli esiti per le posizioni di questa registrazione contabile
	    List<EsitoOperazionePosizioneDebitoriaType> esitiRate = new ArrayList<>();
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		esitoPos.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
		esitoPos.setIdRegistrazioneContabile(BigInteger.valueOf(payPos.getRegistrazioneContabile().getId().getCodice()));
		esitoPos.setStato(StatoPagamentoType.ACQUISITO);
		result.getEsitoPosizione().add(esitoPos);
		esitiRate.add(esitoPos);
		if (error == null) {
		    esitoPos.setEsito(false);
		    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
		    esitoPos.setMessaggio(error);
		}
	    }
	    //se autenticato
	    if (error == null) {
		//carico i dati a partire dalla registrazione contabile in modo che corrisponda  PayRegistrazioniContabili --> PosizioneDebitoria PayPosizioniDebitorie --> Rata
		PosizioneDebitoria posDeb = popolaPosizioneDebitoria(payReg, td);
		InserisciPosizioneRequest req = new InserisciPosizioneRequest();
		req.setPosizioniDebitoria(posDeb);
		//deve essere generato un evento per ogni chiamata effettuata al servizio EntraNext ossia uno per ogni registrazione contabile
		try {
		    resp = getPort().inserisciPosizione(header, req);
		    if (resp != null && resp.getEsito().equalsIgnoreCase("OK")) {
			if (log.isInfoEnabled()) {
			    log.info("registraPosizioniDebitorie - invocazione del servizio inserisciPosizione effettuata con esito OK");
			}
			List<PosizioneDebitoriaRata> rate = resp.getPosizioneDebitoria().getRate();
			if (rate.size() != esitiRate.size()) {
			    throw new PayException(
				    "Le rate restituite nell'esito del caricamento del debito non corrispondono alle posizioni debitorie da caricare");
			}
			List<PayPosizioniDebitorie> posizioniReg = new ArrayList<>(payReg.getPosizioniDebitorie());
			PayDocumenti payDoc = null;
			for (int i = 0; i < rate.size(); i++) {
			    EsitoOperazionePosizioneDebitoriaType esitoPos = esitiRate.get(i);
			    esitoPos.setEsito(true);
			    esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
			    esitoPos.setIUV(rate.get(i).getIUV());
			    esitoPos.setCodiceAvviso(rate.get(i).getIUV());
			    PayPosizioniDebitorie payPos = posizioniReg.get(i);
			    byte[] doc = resp.getDocumento();
			    StringBuilder sb = new StringBuilder(resp.getPosizioneDebitoria().getTipoDocumento().value());
			    sb.append("_").append(ORMHelper.getIdcomune()).append("_").append(payReg.getId().getCodice()).append(".pdf");
			    DataHandler dh = new DataHandler(new ByteArrayDataSource(doc, ContentType.APPLICATION_OCTET_STREAM.getMimeType()));
			    TipiDocumentiSDI tipoDocSDI = posDeb.getTipoDocumento();
			    if (tipoDocSDI != null) {
				TipoDocumentoType tipoDoc = null;
				switch (tipoDocSDI) {
				case AVVISO:
				    tipoDoc = TipoDocumentoType.AVVISO;
				    break;
				case FATTURA:
				    tipoDoc = TipoDocumentoType.FATTURA;
				    break;
				default:
				    break;
				}
				if (tipoDoc != null) {
				    if (payDoc == null) {
					payDoc = this.payDocumentiService.salvaDocumentoPerPosizioneDebitoriaTrans(payPos, tipoDoc, dh,
						sb.toString());
				    } else {
					this.payDocumentiService.associaDocumentoAPosizioneDebitoriaTrans(payPos, payDoc, tipoDoc);
				    }
				}
			    }
			}
		    } else {
			if (log.isInfoEnabled()) {
			    log.info("registraPosizioniDebitorie - invocazione del servizio inserisciPosizione effettuata con esito OK");
			}
			log.error("registraPosizioniDebitorie - Errore nel inserimento della posizione {}: {}", posDeb.getRiferimentoPraticaEsterna(),
				StringUtils.defaultString(resp.getDescrizione()));
			for (int i = 0; i < esitiRate.size(); i++) {
			    EsitoOperazionePosizioneDebitoriaType esitoPos = esitiRate.get(i);
			    esitoPos.setEsito(false);
			    esitoPos.setStato(StatoPagamentoType.CON_ERRORE);
			    if (resp != null) {
				esitoPos.setMessaggio(resp.getDescrizione());
			    }
			}
		    }
		} catch (Exception e) {
		    for (EsitoOperazionePosizioneDebitoriaType esitoPos : esitiRate) {
			this.handleException(e, esitoPos);
		    }
		    log.error("Errore nella chiamata al servizio inserisciPosizione:", e);
		} finally {
		}
	    }
	}
	return result;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	AnnullaPosizioneDebitoriaResponse resp = null;
	IntestazioneFO header = this.login();
	for (PayRegistrazioniContabili payReg : datiRegistrazioniCommand.getRegistrazioniPosizioni()) {
	    List<EsitoOperazionePosizioneDebitoriaType> esitiReg = new ArrayList<>();
	    String idPosizionePSP = null;
	    for (PayPosizioniDebitorie payPos : payReg.getPosizioniDebitorie()) {
		if (null == idPosizionePSP) {
		    if (StringUtils.isNotBlank(payPos.getIdPosizionePsp())) {
			idPosizionePSP = payPos.getIdPosizionePsp();
		    } else {
			idPosizionePSP = this.generaIdPosizioneDebitoria(payPos);
		    }
		}
		EsitoOperazionePosizioneDebitoriaType esito = new EsitoOperazionePosizioneDebitoriaType();
		esito.setIdPosizione(BigInteger.valueOf(payPos.getId().getCodice()));
		esito.setIdRegistrazioneContabile(BigInteger.valueOf(payReg.getId().getCodice()));
		PayStatoPagamenti payStato = payPos.recuperaStatoCorrente();
		if (payStato == null) {
		    payStato = this.payStatoPagamentiService.getStatoPosizioneDebitoria(payPos);
		}
		esito.setStato(StatoPagamentoType.fromValue(payStato.getStato()));
		esitiReg.add(esito);
	    }
	    AnnullaPosizioneDebitoriaRequest req = new AnnullaPosizioneDebitoriaRequest();
	    req.setTipoChiaveApplicativa(TipoChiaveApplicativa.GESTIONALE);
	    req.setChiaveApplicativa(idPosizionePSP);
	    try {
		resp = getPort().annullaPosizioneDebitoria(header, req);
		if (resp.getEsito().equals("OK")) {
		    for (EsitoOperazionePosizioneDebitoriaType esito : esitiReg) {
			esito.setStato(StatoPagamentoType.ANNULLATO);
			esito.setEsito(true);
		    }
		} else {
		    for (EsitoOperazionePosizioneDebitoriaType esito : esitiReg) {
			esito.setMessaggio(resp.getDescrizione());
		    }
		}
	    } catch (Exception e) {
		for (EsitoOperazionePosizioneDebitoriaType esito : esitiReg) {
		    this.handleException(e, esito);
		}
		log.error("annullaPosizioniDebitorie - Errore nell'annullamento della posizione", e);
	    } finally {
		result.getEsitoPosizione().addAll(esitiReg);
	    }
	}
	return result;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	List<PaySessioniPagamento> sex = new ArrayList<>();
	String[] idSessioneVals = reqParams.get("idTransaction");
	String[] esitoVal = reqParams.get("esito");
	if (log.isDebugEnabled()) {
	    log.debug("idSessioneVals {}", idSessioneVals);
	    log.debug("esitoVal {}", idSessioneVals);
	}
	String idSessione = null;
	String esito = null;
	if (idSessioneVals != null && idSessioneVals.length > 0) {
	    idSessione = idSessioneVals[0];
	}
	if (esitoVal != null && esitoVal.length > 0) {
	    esito = esitoVal[0];
	}
	if (StringUtils.isNotBlank(idSessione)) {
	    sex = this.paySessioniPagamentoService.findBySessionId(idSessione);
	}
	if (!sex.isEmpty()) {
	    for (PaySessioniPagamento paySessioniPagamento : sex) {
		paySessioniPagamento.setEsito("OK".equals(esito));
		this.paySessioniPagamentoService.update(paySessioniPagamento);
	    }
	    return sex.get(0); // ne ritorno una perché la redirect è sempre quella
	}
	return null;
    }

    @SuppressWarnings("unused")
    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	//validazione causale di registrazione
	// TODO RIATTIVARE CONTROLLO SU PARAMETRI OBBLIGATORI PER CAUSALE E CONNETTORE
	//	CausaleRegistrazioneType causReg = registrazioneContabile.getCausale();
	//	try {
	//	    PayRegistrazioniCausali payRegCaus = this.payRegistrazioniCausaliService.findCausaleRegistrazione(causReg,
	//		    this.configurazionePagamentiService.getConfigurazioneEnteCorrente());
	//	    if (StringUtils.isBlank(payRegCaus.getParametri())) {
	//		throw new ValidazionePosizioniDebitorieException(
	//			"Errore di configurazione del nodo pagamenti: la causale di registrazione utilizzata deve avere il campo PARAMETRI valorizzato con uno dei seguenti valori: 'Avviso', 'Fattura'");
	//	    } else {
	//		if (!(payRegCaus.getParametri().equals(TipiDocumentiSDI.AVVISO.value())
	//			|| payRegCaus.getParametri().equals(TipiDocumentiSDI.FATTURA.value()))) {
	//		    throw new ValidazionePosizioniDebitorieException(
	//			    "Errore di configurazione del nodo pagamenti: la causale di registrazione utilizzata deve avere il campo PARAMETRI valorizzato con uno dei seguenti valori: 'Avviso', 'Fattura'");
	//		}
	//	    }
	//	} catch (PayException e1) {
	//	    throw new ValidazionePosizioniDebitorieException("Errore di configurazione del nodo pagamenti: " + e1.getMessage());
	//	}
	List<PosizioneDebitoriaType> rate = registrazioneContabile.getRate().getRata();
	SoggettoDebitoreType sogg = registrazioneContabile.getSoggettoDebitore();
	//validazioni soggetto debitore
	//SE persona fisica (cfpi.length == 16) obbligatorio il cognome
	if (sogg.getCfpi().length() == 16 && StringUtils.isBlank(sogg.getCognome())) {
	    throw new ValidazionePosizioniDebitorieException("il cognome è obbligatorio per le persone fisiche");
	}
	//cap obbligatorio
	if (StringUtils.isBlank(sogg.getCap())) {
	    throw new ValidazionePosizioniDebitorieException("il cap è obbligatorio");
	}
	//indirizzo obbligatorio
	if (StringUtils.isBlank(sogg.getVia())) {
	    throw new ValidazionePosizioniDebitorieException("l'indirizzo di residenza è obbligatorio");
	}
	//località obbligatoria
	if (StringUtils.isBlank(sogg.getLocalita())) {
	    throw new ValidazionePosizioniDebitorieException("il comune di residenza è obbligatorio");
	}
	//provincia obbligatoria
	if (StringUtils.isBlank(sogg.getProvincia())) {
	    throw new ValidazionePosizioniDebitorieException("la provincia di residenza è obbligatorio");
	}
	//validazione delle rate
	for (int i = 0; i < rate.size(); i++) {
	    PosizioneDebitoriaType rata = rate.get(i);
	    String ref = PopolamentoDatiHelper.getRiferimentoPosizioneDebitoria(rata, registrazioneContabile,
		    rata.getNumeroRata() != null ? rata.getNumeroRata().intValue() : i + 1);
	    //data scadenza 
	    if (rata.getDataScadenza() == null) {
		throw new ValidazionePosizioniDebitorieException("la data di scadenza è obbligatoria. Riferimento rata: " + ref);
	    }
	    //dettagli importo
	    for (ImportoPagamentoType impRata : rata.getImporto().getComponenteImporto()) {
		//dati riscossione mancanti
		if (StringUtils.isBlank(impRata.getDatiRiscossione())) {
		    throw new ValidazionePosizioniDebitorieException("dati riscossione non specificati per la rata: " + ref);
		}
		//dati riscossione non validi
		else {
		    try {
			CausaliImporti causImp = CausaliImporti.fromValue(impRata.getDatiRiscossione());
		    } catch (Exception e) {
			String valori = IterableUtils.toString(Arrays.asList(CausaliImporti.values()), new Transformer<CausaliImporti, String>() {

			    public String transform(CausaliImporti input) {

				if (input == null) {
				    return "null";
				} else {
				    return input.value();
				}
			    };
			});
			throw new ValidazionePosizioniDebitorieException("i dati riscossione devono avere uno dei seguenti valori " + valori);
		    }
		}
	    }
	}
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	ElencoStatoPosizioniType result = new ElencoStatoPosizioniType();
	IntestazioneFO header = this.login();
	//trasformo la lista di posizioni in una lista di registrazioni contabili ciascuna contenente la lista delle sue posizioni/rate
	Map<PkId, List<PayPosizioniDebitorie>> payDebiti = new HashMap<>();
	for (PayPosizioniDebitorie payPos : cmd.getPosizioni()) {
	    List<PayPosizioniDebitorie> rateDebito = payDebiti.get(payPos.getRegistrazioneContabile().getId());
	    if (rateDebito == null) {
		rateDebito = new ArrayList<>();
		payDebiti.put(payPos.getRegistrazioneContabile().getId(), rateDebito);
	    }
	    rateDebito.add(payPos);
	}
	//ciclo la lista delle registrazioni e fare una chiamata a verifica stato per ciascuna registrazione
	for (List<PayPosizioniDebitorie> rateDebito : payDebiti.values()) {
	    String idPosizionePsp = null;
	    boolean verificaPerPiuPosizioni = rateDebito.size() > 1;
	    if (!rateDebito.isEmpty()) {
		idPosizionePsp = rateDebito.get(0).getIdPosizionePsp();
		//creo un record in PayIOEventi per ciascuna chiamata al WS di EntraNext 
		try {
		    VerificaPosizioneRequest req = new VerificaPosizioneRequest();
		    req.setTipoChiaveApplicativa(TipoChiaveApplicativa.GESTIONALE);
		    req.setChiaveApplicativa(idPosizionePsp);
		    VerificaPosizioneResponse resp = getPort().verificaPosizione(header, req);
		    if (resp.getEsito().equals("OK")) {
			//chiamata con esito OK
			for (PayPosizioniDebitorie payPos : rateDebito) {
			    StatoPosizioneType retStatus = new StatoPosizioneType();
			    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
				    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
			    retStatus.setEsito(true);
			    PosizioneDebitoriaResult respDebito = resp.getPosizioneDebitoria();
			    if (respDebito.isAnnullato()) {
				/*
				 * se la PosizioneDebitoria di EntraNext è annullata avrà il flag annullato==true, in
				 * questo caso tutte le PayPosizioniDebitorie della registrazione contabile vanno
				 * restituite con stato = ANNULLATO
				 */
				retStatus.setStato(StatoPagamentoType.ANNULLATO);
			    } else {
				/*
				 * se la posizione EntraNext non è annullata nella response di ciascuna chiamata ciclare
				 * la lista di RendicontazionePagamenti e matchare ciascun elemento con la posizione
				 * debitoria della registrazione corrente il match fra RendicontazionePagamenti e
				 * PayPosizioniDebitorie si fa tramite l'uguaglianza degli IUV. Si popola l'esito da
				 * restituire per ciascuna posizione debitoria per cui si è trovato il match
				 */
				retStatus.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
				gestisciRispostaPagamenti(resp.getPagamenti(), retStatus, payPos, respDebito, verificaPerPiuPosizioni);
			    }
			    result.getStatoPosizioni().add(retStatus);
			}
		    } else {
			//chiamata con esito KO
			for (PayPosizioniDebitorie payPos : rateDebito) {
			    StatoPosizioneType retStatus = new StatoPosizioneType();
			    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
				    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
			    String msg = "Errore nella verifica stato della posizione: " + StringUtils.defaultString(resp.getDescrizione());
			    retStatus.setMessaggio(msg);
			    result.getStatoPosizioni().add(retStatus);
			}
		    }
		} catch (Exception e) {
		    for (PayPosizioniDebitorie payPos : rateDebito) {
			StatoPosizioneType retStatus = new StatoPosizioneType();
			this.handleException(e, retStatus);
			PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(retStatus, payPos,
				payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice()));
			result.getStatoPosizioni().add(retStatus);
		    }
		} finally {
		    //aggiorno l'esito l'esito dell'evento 
		}
	    }
	}
	return result;
    }

    private void gestisciRispostaPagamenti(List<RendicontazionePagamenti> pagamenti, StatoPosizioneType retStatus, PayPosizioniDebitorie payPos,
	    PosizioneDebitoriaResult respDebito, boolean verificaPerPiuPosizioni) throws PayException {

	if (pagamenti.isEmpty() && BooleanUtils.isTrue(payPos.getFlagOTF()) && //
		respDebito.getStato().equals(StatoPosizioneDebitoria.PAGATO)) {
	    gestisciPagamentoOTF(payPos, retStatus, respDebito);
	    return;
	}
	if (pagamenti.isEmpty()) {
	    return;
	}
	// NOTA!! verificaPerPiuPosizioni se la richiesta di verificastato mi è arrivata per più posizioni es SOAPUI allora non devo usare il metodo per sistemare l'altra posizione
	// I client es. backoffice chiamano il verifica posizione per una posizione solamente allora in questo caso
	// devo gestire anche l'altra posizione
	for (RendicontazionePagamenti respPagamento : pagamenti) {
	    String idPosizionePsp = payPos.getIdPosizionePsp();
	    log.debug("Debito {} in stato {} ", idPosizionePsp, respDebito.getStato());
	    if (respDebito.getStato().equals(StatoPosizioneDebitoria.PAGATO) || //
		    respDebito.getStato().equals(StatoPosizioneDebitoria.RATEALE_IN_CORSO) || // 
		    respDebito.getStato().equals(StatoPosizioneDebitoria.RATEALE_NON_OTTEMPERATO)) {
		List<PayPosizioniDebitorie> posByIdPosizionePSP = payPosizioniDebitorieService.findAllByIdPosizionePSP(idPosizionePsp);
		boolean pagamentoRataUnica = isPagamentoRataUnica(respDebito, pagamenti);
		if (pagamentoRataUnica) {
		    log.debug("Debito {} in stato {} ==> pagamentoRataUnica = true", idPosizionePsp, respDebito.getStato());
		    // nella rata unica ho un solo pagamento che non ha i riferimenti IUV o QUINTO CAMPO o CODICE AVVISO <> da quello delle rate
		    for (PayPosizioniDebitorie pps : posByIdPosizionePSP) {
			if (pps.getId().getCodice().equals(payPos.getId().getCodice())) {
			    log.debug("Debito {} in stato pagato segno pagata la posizione {}", idPosizionePsp, payPos.getId().getCodice());
			    segnaPagataPosizioneDebitoria(retStatus, respPagamento, payPos);
			} else {
			    if (!verificaPerPiuPosizioni) {
				log.debug("Debito {} in stato pagato segno ASYNC pagata la posizione {}", idPosizionePsp, payPos.getId().getCodice());
				segnaPagataAltraPosizioneDebitoria(respPagamento, pps);
			    }
			}
		    }
		} else {
		    String codiceAvviso = StringUtils.defaultString(respPagamento.getCodiceAvviso(), "AVV_NON_FORNITO_ENTRANEXT");
		    String iuv = StringUtils.defaultString(respPagamento.getIuv(), "IUV_NON_FORNITO_ENTRANEXT");
		    String quintocampo = StringUtils.defaultString(respPagamento.getQuintoCampo(), "5C_NON_FORNITO_ENTRANEXT");
		    log.debug("verifico il pagamento per questi dati CodiceAvviso: {}, Iuv: {}, QuintoCampo:{}", codiceAvviso, iuv, quintocampo);
		    for (PayPosizioniDebitorie pps : posByIdPosizionePSP) {
			log.debug("Dati della posizione debitoria {} per questi dati CodiceAvviso: {}, Iuv: {}", pps.getId(), pps.getCodiceAvviso(),
				pps.getIuv());
			if (codiceAvviso.equalsIgnoreCase(pps.getCodiceAvviso()) //
				|| iuv.equalsIgnoreCase(pps.getIuv()) //
				|| quintocampo.equalsIgnoreCase(pps.getCodiceAvviso()) //
				|| quintocampo.equalsIgnoreCase(pps.getIuv())) {
			    if (pps.getId().getCodice().equals(payPos.getId().getCodice())) {
				log.debug("Debito {} in stato {} segno pagata la posizione {}", idPosizionePsp, respDebito.getStato(),
					payPos.getId().getCodice());
				segnaPagataPosizioneDebitoria(retStatus, respPagamento, payPos);
			    } else {
				if (!verificaPerPiuPosizioni) {
				    log.debug("Debito {} in stato {} segno ASYNC pagata la posizione {}", idPosizionePsp, respDebito.getStato(),
					    pps.getId().getCodice());
				    segnaPagataAltraPosizioneDebitoria(respPagamento, pps);
				}
			    }
			}
		    }
		}
	    }
	}
	/*
	 * Se il debito è stato saldato tutto in un unica soluzione dovrei trovare un
	 * dettaglio di RendicontazionePagamenti con un codice avviso che non corrisponde ai codici avviso
	 * delle singole rate registrate nel nodo Per evitare di perdere le informazioni di
	 * pagamento in questi casi registro i dati del pagamento in unica rata nei dati di
	 * pagamento delle singole rate.
	 * I confronti vengono fatti per codice avviso e non per IUV perché in realtà a noi durante il caricamento della posizione
	 *  nel campo IUV viene comunicato il codice avviso e non lo IUV mentre i dati di pagamento riportano lo IUV vero e quindi non coinciderebbero.
	 */
    }

    private boolean isPagamentoRataUnica(PosizioneDebitoriaResult respDebito, List<RendicontazionePagamenti> pagamenti) {

	if (pagamenti.size() > 1) {
	    return false;
	}
	String quintoCampoSoluzioneUnica = StringUtils.defaultString(respDebito.getQuintoCampoSoluzioneUnica(), "NON_FORNITO_");
	String iuvSoluzioneUnica = StringUtils.defaultString(respDebito.getIUVSoluzioneUnica(), "NON _FORNITO_");
	RendicontazionePagamenti respPagamento = pagamenti.get(0);
	log.debug(
		"isPagamentoRataUnica QuintoCampoSoluzioneUnica: {}, iuvSoluzioneUnica: {}, respPagamento.getIuv():{}, respPagamento.getQuintoCampo():{}", //
		quintoCampoSoluzioneUnica, iuvSoluzioneUnica, respPagamento.getIuv(), respPagamento.getQuintoCampo());
	return iuvSoluzioneUnica.equalsIgnoreCase(respPagamento.getIuv())
		|| quintoCampoSoluzioneUnica.equalsIgnoreCase(respPagamento.getQuintoCampo());
    }

    private void segnaPagataAltraPosizioneDebitoria(RendicontazionePagamenti respPagamento, PayPosizioniDebitorie pos) throws PayException {

	PayStatoPagamenti payStato = this.payStatoPagamentiService.getStatoPosizioneDebitoria(pos);
	StatoPagamentoType statoDB = StatoPagamentoType.fromValue(payStato.getStato());
	log.debug("segnaPagataAltraPosizioneDebitoria Stato della posizione debitoria {}={}", pos.getId(), statoDB);
	if (!(statoDB.equals(StatoPagamentoType.NOTIFICATO_DA_PSP) || statoDB.equals(StatoPagamentoType.RENDICONTATO_DA_IC))) {
	    // registro solo se non già in stato pagato
	    DatiPagamentoType datiPag = fromRendicontazionePagamenti(respPagamento, pos);
	    StatoPosizioneType esito = new StatoPosizioneType();
	    PopolamentoDatiHelper.completaDatiDaPosizioneDebitoria(esito, pos,
		    payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(pos.getId().getCodice()));
	    esito.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
	    esito.setEsito(true);
	    esito.setDatiPagamento(datiPag);
	    log.debug("notificaPagamento - prima di acquisire i dati di pagamento {}", pos.getId());
	    this.payStatoPagamentiService.registraStatoPosizioneDebitoria(esito, pos);
	    log.debug("notificaPagamento - acquisiti i dati di pagamento e aggiornato lo stato per la posizione debitoria {}", pos.getId());
	}
    }

    private void segnaPagataPosizioneDebitoria(StatoPosizioneType retStatus, //
	    RendicontazionePagamenti respPagamento, // 
	    PayPosizioniDebitorie payPos) {

	retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
	DatiPagamentoType pagamento = fromRendicontazionePagamenti(respPagamento, payPos);
	retStatus.setDatiPagamento(pagamento);
    }

    private DatiPagamentoType fromRendicontazionePagamenti(RendicontazionePagamenti respPagamento, PayPosizioniDebitorie payPos) {

	DatiPagamentoType pagamento = new DatiPagamentoType();
	pagamento.setIuv(respPagamento.getIuv());
	StringBuilder sb = new StringBuilder();
	if (respPagamento.getModalita() != null) {
	    sb.append(respPagamento.getModalita().value());
	    if (StringUtils.isNotBlank(respPagamento.getDescrizioneModalita())) {
		sb.append(": ").append(respPagamento.getDescrizioneModalita());
	    }
	}
	pagamento.setModalitaPagamento(StringUtils.substring(sb.toString(), 0, 50));
	if (respPagamento.getProvenienza() != null) {
	    sb.append(" ").append(respPagamento.getProvenienza().value());
	    if (StringUtils.isNotBlank(respPagamento.getDescrizioneProvenienza())) {
		sb.append(": ").append(respPagamento.getDescrizioneProvenienza());
	    }
	}
	pagamento.setNote(sb.toString());
	pagamento.setDataOraPagamento(respPagamento.getDataPagamento());
	pagamento.setDataOraAutorizzazione(respPagamento.getDataVersamento());
	pagamento.setDataOraInizioTransazione(respPagamento.getDataInserimento());
	pagamento.setImportoPagato(respPagamento.getImporto());
	pagamento.setRiferimentiPagamento(respPagamento.getIdentificativoPagamentoEntraNext());
	pagamento.setDescrizioneCausale(respPagamento.getPagatoAFronteDi());
	pagamento.setRagioneSocialePSP(respPagamento.getDescrizioneProvenienza());
	if (respPagamento.getCodiceFiscale() != null && !respPagamento.getCodiceFiscale().equalsIgnoreCase(payPos.getSoggettoDebitore().getCfPi())) {
	    SoggettoDebitoreType soggDeb = new SoggettoDebitoreType();
	    soggDeb.setCfpi(respPagamento.getCodiceFiscale());
	    soggDeb.setNome(respPagamento.getNominativo());
	    pagamento.setSoggettoPagatore(soggDeb);
	}
	return pagamento;
    }

    /*
     * Pagamento on the fly. Non sarà usato per ora perché esiste già una implementazione
     * funzionante .NET in uno specifico step dell'area riservata.
     */
    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	AttivaPagamentoOnTheFlyResponseType result = new AttivaPagamentoOnTheFlyResponseType();
	EsitoOperazionePosizioneDebitoriaType esitoKO = new EsitoOperazionePosizioneDebitoriaType();
	AttivaSessionePagamentoResponseType attivaSessioneOTF = new AttivaSessionePagamentoResponseType();
	List<PayRegistrazioniContabili> posizioneRegistrazioniContabili = cmd.getRegistrazioniPosizioni();
	List<PayPosizioniDebitorie> listPds = new ArrayList<>();
	for (PayRegistrazioniContabili prc : posizioneRegistrazioniContabili) {
	    listPds.addAll(prc.getPosizioniDebitorie());
	}
	InserisciPosizioniInAttesaResponse resp = null;
	try {
	    IntestazioneFO header = login();
	    PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	    result.setSessionePagamento(attivaSessioneOTF);
	    List<PosizioneDebitoriaInAttesa> posizioniInAttesa = new ArrayList<PosizioneDebitoriaInAttesa>();
	    Map<String, List<String>> posRifClient = new HashMap<>();
	    for (PayPosizioniDebitorie payPosizioniDebitorie : listPds) {
		PosizioneDebitoriaInAttesa posDeb = new PosizioneDebitoriaInAttesa();
		popolaPosizioneDebitoriaInAttesa(payPosizioniDebitorie, posDeb, posRifClient);
		posizioniInAttesa.add(posDeb);
	    }
	    InserisciPosizioniInAttesaRequest req = new InserisciPosizioniInAttesaRequest();
	    req.setUrlBack(profiloEnte.getUrlAnnullamentoPagamento());
	    req.setUrlReturn(profiloEnte.getUrlEsitoPagamento());
	    req.setPosizioniDebitorie(posizioniInAttesa);
	    resp = getPort().inserisciPosizioniInAttesa(header, req);
	    if (resp != null && resp.getEsito().equals("OK")) {
		attivaSessioneOTF.setPayUrl(resp.getUrl());
		attivaSessioneOTF.setIdSessione(resp.getIdentificativoTransazione()); // questo deve rimanere tale che serve per la verifica dello stato delle posizioni OTF
		attivaSessioneOTF.setEsito(true);
		List<PosizioneDebitoriaInAttesaResult> posizioniDebitorie = resp.getPosizioniDebitorie();
		for (PosizioneDebitoriaInAttesaResult res : posizioniDebitorie) {
		    EsitoOperazionePosizioneDebitoriaType esitoPos = new EsitoOperazionePosizioneDebitoriaType();
		    esitoPos.setEsito(true);
		    esitoPos.setStato(StatoPagamentoType.ATTIVATO_IN_PSP);
		    esitoPos.setMessaggio("Posizione OTF creata con successo");
		    esitoPos.setIUV(res.getIUV());
		    esitoPos.setCodiceAvviso(res.getIUV());
		    esitoPos.setIdPosizione(BigInteger.valueOf(res.getNumero()));
		    List<String> rifClient = posRifClient.get(res.getRiferimentoPraticaEsterna());
		    if (rifClient != null && !rifClient.isEmpty()) {
			esitoPos.getRiferimentoClient().addAll(rifClient); // settato in
		    }
		    // popolaPosizioneDebitoriaInAttesa
		    result.getPosizioneInserita().add(esitoPos);
		}
	    } else {
		attivaSessioneOTF.setEsito(false);
		attivaSessioneOTF.setDescEsito("Errore nel creazione della posizione OTF");
		esitoKO.setEsito(false);
		esitoKO.setStato(StatoPagamentoType.CON_ERRORE);
		if (resp != null) {
		    esitoKO.setMessaggio(resp.getDescrizione());
		    attivaSessioneOTF.setDescEsito(resp.getDescrizione());
		}
	    }
	} catch (Exception e) {
	    log.error("Errore attivazione pagamento on the fly", e);
	    attivaSessioneOTF.setEsito(false);
	    attivaSessioneOTF.setDescEsito("Errore nel creazione della posizione OTF: " + e.getMessage());
	    esitoKO.setEsito(false);
	    if (resp != null) {
		esitoKO.setMessaggio(resp.getDescrizione());
	    }
	    esitoKO.setStato(StatoPagamentoType.CON_ERRORE);
	    this.handleException(e, esitoKO);
	}
	return result;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	//L'OTF è supportato ma non è ancora stato implementato perché non necessario
	return true;
    }

    /**
     * Extranet usa i riferimenti all'intero debito (PayRegistrazioniContabili) che viene chiamato PosizioneDebitoria e
     * non usa i riferimenti alle singole rate (PayPosizioniDebitorie) quindi ricaviamo l'id della loro
     * PosizioneDebitoria dall'id della nostra PayRegistrazioniContabili
     */
    @Override
    public String generaIdPosizioneDebitoria(PayPosizioniDebitorie pos) {

	StringBuilder sb = new StringBuilder();
	PayRegistrazioniContabili reg = pos.getRegistrazioneContabile();
	if (reg != null && reg.getId() != null) {
	    if (StringUtils.isNotBlank(getIdInstallazione())) {
		sb.append(getIdInstallazione()).append(PkId.TO_STRING_ID_SEPARATOR);
	    }
	    sb.append(PkId.toStringId(reg.getId()));
	}
	return sb.toString();
    }

    private IntestazioneFO login() throws PayConfigurationException {

	PayProfiliEntiCreditori profiloEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	LinkNextSoap port = getPort();
	LoginRequest req = new LoginRequest();
	req.setVersione(payConnectorConfigValuesService.getValoreParametroConfigurazione(ConfigParamNames.VERSIONE));
	req.setPasswordMD5(this.getWsCaricamentoConfig().getPassword());
	req.setUsername(this.getWsCaricamentoConfig().getUtente());
	req.setIdentificativo(profiloEnte.getCfCodiceProfilo());
	IntestazioneFO intestatzione = new IntestazioneFO();
	intestatzione.setIdentificativoConnettore(profiloEnte.getIdAppPSP());
	intestatzione.setCodiceFiscaleEnte(profiloEnte.getCfCodiceProfiloPSP());
	LoginResponse resp = port.login(intestatzione, req);
	if (resp != null && resp.getEsito().equals("OK")) {
	    IntestazioneFO header = new IntestazioneFO();
	    header.setTokenAuth(resp.getTokenAuth());
	    header.setIdentificativoConnettore(profiloEnte.getIdAppPSP());
	    header.setCodiceFiscaleEnte(profiloEnte.getCfCodiceProfiloPSP());
	    return header;
	}
	return null;
    }

    private LinkNextSoap getPort() throws PayConfigurationException {

	PayConnectorWsEndpoint wsCfg = this.getWsSecurityConfig();
	if (wsCfg == null) {
	    throw new PayConfigurationException("Il PayConnectorWsEndpoint non è configurato per il connettorre EntraNext.");
	}
	LinkNextSoap ws = (LinkNextSoap) getWSPort(wsCfg, LinkNextSoap.class);
	this.prepareWsPort(ws, wsCfg);
	return ws;
    }

    private void prepareWsPort(Object wsPort, PayConnectorWsEndpoint wsCfg) {

	Client client = ClientProxy.getClient(wsPort);
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	if (wsCfg.getTimeout() != null) {
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	    httpClientPolicy.setConnectionTimeout(wsCfg.getTimeout());
	    httpClientPolicy.setReceiveTimeout(wsCfg.getTimeout());
	    conduit.setClient(httpClientPolicy);
	}
	TLSClientParameters params = conduit.getTlsClientParameters();
	if (params == null) {
	    params = new TLSClientParameters();
	    conduit.setTlsClientParameters(params);
	}
	params.setTrustManagers(new TrustManager[] { new TrustAllX509TrustManager() });
	params.setDisableCNCheck(true);
    }

    private PosizioneDebitoria popolaPosizioneDebitoria(PayRegistrazioniContabili payReg, TipiDocumentiSDI td) throws PayException {

	PosizioneDebitoria posDeb = new PosizioneDebitoria();
	posDeb.setAnnoImposta(payReg.getAnno());
	posDeb.setNumero(payReg.getId().getCodice());
	posDeb.setNumeroDocumentoEsterno("" + payReg.getId().getCodice());// IMPORTANTE è i lnostro aggancio con le posizioni debitorie
									  // il campo numero è un intero e sulla doc c'è scritto attribuito dal sistema e quindi potrebbe essere generato da ENTRANEXT
	posDeb.setDescrizione(payReg.getDescrizione());
	posDeb.setGestioneIva(Boolean.FALSE);
	//RECUPERO IL TIPO DI DOCUMENTO DAI PARAMETRI DELLA CAUSALE DI REGISTRAZIONE CHE DEVE ESSERE POPOLATA CON IL VALORE CORRISPONDENTE DELL'ENUMERATION
	posDeb.setTipoDocumento(td);
	Iterator<PayPosizioniDebitorie> posIter = payReg.getPosizioniDebitorie().iterator();
	PayPosizioniDebitorie payPos = null;
	BigDecimal importoTot = BigDecimal.ZERO;
	List<PosizioneDebitoriaDettaglio> dettagliImporti = new ArrayList<>();
	String idPosizionePsp = null;
	while (posIter.hasNext()) {
	    boolean primaRata = payPos == null;
	    payPos = posIter.next();
	    if (primaRata) {
		idPosizionePsp = this.generaIdPosizioneDebitoria(payPos);
		posDeb.setRiferimentoPraticaEsterna(idPosizionePsp);
		// data inizio e fine periodo impostate entrambe con la data emissione
		XMLGregorianCalendar dataInizioPeriodo = Utilities.getXMLGregorianCalendar(payPos.getDataRegistrazione());
		posDeb.setDataInizioPeriodo(dataInizioPeriodo);
		posDeb.setDataFinePeriodo(dataInizioPeriodo);
		posDeb.setDataEmissione(dataInizioPeriodo);
		Contribuente contrb = new Contribuente();
		contrb.setCodiceFiscale(payPos.getSoggettoDebitore().getCfPi());
		if (payPos.getSoggettoDebitore().getCfPi().length() == 16) {
		    contrb.setNaturaGiuridica(NaturaGiuridica.PERSONA_FISICA);
		    contrb.setCognome(payPos.getSoggettoDebitore().getCognome());
		    contrb.setNome(payPos.getSoggettoDebitore().getNome());
		} else {
		    contrb.setNaturaGiuridica(NaturaGiuridica.PERSONA_GIURIDICA);
		    contrb.setRagioneSociale(payPos.getSoggettoDebitore().getNome());
		    contrb.setPartitaIva(payPos.getSoggettoDebitore().getCfPi());
		}
		contrb.setEmail(payPos.getSoggettoDebitore().getEmail());
		posDeb.setContribuente(contrb);
		Ubicazione ubi = new Ubicazione();
		ubi.setCAP(payPos.getSoggettoDebitore().getCap());
		ubi.setIndirizzo(payPos.getSoggettoDebitore().getVia());
		//ubi.setLocalita(payPos.getSoggettoDebitore().getLocalita());
		ubi.setNumeroCivico(payPos.getSoggettoDebitore().getCivico());
		ubi.setProvincia(payPos.getSoggettoDebitore().getProvincia());
		ubi.setComune(payPos.getSoggettoDebitore().getLocalita());
		contrb.setResidenza(ubi);
	    }
	    payPos.setIdPosizionePsp(idPosizionePsp);
	    PosizioneDebitoriaRata rata = new PosizioneDebitoriaRata();
	    rata.setNumeroRata(payPos.getNumRata());
	    XMLGregorianCalendar dataScadenza = Utilities.getXMLGregorianCalendar(payPos.getDataScadenza());
	    rata.setScadenza(dataScadenza);
	    BigDecimal importoRata = BigDecimal.ZERO;
	    //il dettaglio importi non è specificato a livello di singola rata ma a livello dell'intero debito 
	    // --> ciclando le rate sommo le voci di importo delle singole che sono rate relative allo stasso tipo di dettaglio importo per ottenere i valori complessivi
	    for (PayDettaglioImporti payDetImporto : payPos.getDettagliImporto()) {
		importoRata = importoRata.add(payDetImporto.getImporto());
		PosizioneDebitoriaDettaglio posDebDett = new PosizioneDebitoriaDettaglio();
		if (StringUtils.isNotBlank(payDetImporto.getNumeroAccertamento())) {
		    posDebDett.setAnnoAccertamentoContabile(payDetImporto.getAnnoAccertamento());
		    posDebDett.setNumeroAccertamentoContabile(payDetImporto.getNumeroAccertamento());
		}
		posDebDett.setDescrizione(payDetImporto.getDescCausale());
		//voce di costo recuperata dal sottoaccertamento
		posDebDett.setNomeVoceDiCosto(payDetImporto.getNumeroSottoAccertamento());
		//recupero la causale importo dai dati riscossione
		CausaliImporti causImp = null;
		try {
		    causImp = CausaliImporti.fromValue(payDetImporto.getDatiRiscossione());
		    posDebDett.setCausaleImporto(causImp);
		} catch (Exception e) {
		    throw new PayException("Valore enumeration inesistente per CausaliImporti verificare i dati riscossione del dettaglio importo",
			    e);
		}
		//lookup nella lista per recuperare lo stesso dettaglio importo se già presente, altrimenti lo aggiungo alla lista
		//due dettagli importo li consideriamo uguali se sono uguali numeroAccertamento, annoAccertamento, numeroSottoAccertamento e i dati risossione che contengono la codifica della causale importo
		PosizioneDebitoriaDettaglio existingDett = IterableUtils.find(dettagliImporti, new DettaglioImportoEqualsPredicate(posDebDett));
		if (existingDett != null) {
		    existingDett.setImporto(existingDett.getImporto().add(payDetImporto.getImporto()));
		} else {
		    posDebDett.setImporto(payDetImporto.getImporto());
		    int annoCompetenza = payDetImporto.getAnnoAccertamento() != null ? payDetImporto.getAnnoAccertamento() : payReg.getAnno();
		    posDebDett.setAnnoCompetenza(annoCompetenza);
		    dettagliImporti.add(posDebDett);
		}
	    }
	    rata.setImporto(importoRata);
	    posDeb.getRate().add(rata);
	    importoTot = importoTot.add(importoRata);
	}
	posDeb.getDettagli().addAll(dettagliImporti);
	posDeb.setImportoDovuto(importoTot);
	return posDeb;
    }

    private TipiDocumentiSDI getTipoDocumento(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	String tipoDocumento = posizioniDebitorieCommandService.findValoreUnicoParametroFromCommand(datiRegistrazioniCommand,
		new ParametroTipoDocumentoSdi());
	TipiDocumentiSDI ret = TipiDocumentiSDI.AVVISO;
	if (StringUtils.isNotBlank(tipoDocumento)) {
	    try {
		ret = TipiDocumentiSDI.fromValue(tipoDocumento);
	    } catch (Exception e) {
		throw new PayException("Valore enumeration inesistente per TipiDocumentiSDI, verificare i parametri della causale di registrazione",
			e);
	    }
	}
	return ret;
    }

    private void popolaPosizioneDebitoriaInAttesa(PayPosizioniDebitorie payPos, PosizioneDebitoriaInAttesa posDeb, Map<String, List<String>> posRifClient)
	    throws PayException {

	posDeb.setAnnoImposta(payPos.getAnno());
	posDeb.setAnnullato(Boolean.FALSE);
	posDeb.setNumero(payPos.getId().getCodice());
	String idPosizionePsp = this.generaIdPosizioneDebitoria(payPos);
	payPos.setIdPosizionePsp(idPosizionePsp);
	posDeb.setRiferimentoPraticaEsterna(idPosizionePsp);
	posDeb.setNumeroDocumentoEsterno("" + payPos.getId().getCodice()); // IMPORTANTE è i lnostro aggancio con le posizioni debitorie
									   // il campo numero è un intero e sulla doc c'è scritto attribuito dal sistema e quindi potrebbe essere generato da ENTRANEXT 
	List<String> rifClients = posRifClient.get(idPosizionePsp);
	if (null == rifClients) {
	    rifClients = new ArrayList<>();
	}
	Set<String> rif = payPosizioniDebitorieService.findRiferimentiClientByPosizioneDebitoria(payPos.getId().getCodice());
	if (!rif.isEmpty()) {
	    rifClients.addAll(rif);
	}
	posRifClient.put(idPosizionePsp, rifClients);
	SoggettoVersante sv = new SoggettoVersante();
	sv.setCodiceFiscale(payPos.getSoggettoDebitore().getCfPi());
	sv.setCognome(payPos.getSoggettoDebitore().getCognome());
	sv.setNome(payPos.getSoggettoDebitore().getNome());
	if (payPos.getSoggettoDebitore().getCfPi().length() == 16) {
	    sv.setNaturaGiuridica(NaturaGiuridica.PERSONA_FISICA);
	} else {
	    sv.setNaturaGiuridica(NaturaGiuridica.PERSONA_GIURIDICA);
	    sv.setRagioneSociale(payPos.getSoggettoDebitore().getNome());
	    sv.setPartitaIva(payPos.getSoggettoDebitore().getCfPi());
	}
	sv.setEmail(payPos.getSoggettoDebitore().getEmail());
	Ubicazione ubi = new Ubicazione();
	ubi.setCAP(payPos.getSoggettoDebitore().getCap());
	ubi.setIndirizzo(payPos.getSoggettoDebitore().getVia());
	ubi.setLocalita(payPos.getSoggettoDebitore().getLocalita());
	ubi.setNumeroCivico(payPos.getSoggettoDebitore().getCivico());
	ubi.setProvincia(payPos.getSoggettoDebitore().getProvincia());
	ubi.setComune(payPos.getSoggettoDebitore().getLocalita());
	sv.setResidenza(ubi);
	posDeb.setSoggettoVersante(sv);
	SoggettoPagatore sp = new SoggettoPagatore();
	sp.setCodiceFiscale(payPos.getSoggettoDebitore().getCfPi());
	sp.setCognome(payPos.getSoggettoDebitore().getCognome());
	sp.setNome(payPos.getSoggettoDebitore().getNome());
	if (payPos.getSoggettoDebitore().getCfPi().length() == 16) {
	    sp.setNaturaGiuridica(NaturaGiuridica.PERSONA_FISICA);
	} else {
	    sp.setNaturaGiuridica(NaturaGiuridica.PERSONA_GIURIDICA);
	    sp.setRagioneSociale(payPos.getSoggettoDebitore().getNome());
	    sp.setPartitaIva(payPos.getSoggettoDebitore().getCfPi());
	}
	sp.setEmail(payPos.getSoggettoDebitore().getEmail());
	sp.setResidenza(ubi);
	posDeb.setSoggettoPagatore(sp);
	posDeb.setDescrizione(payPos.getDescrizioneCausale());
	posDeb.setGestioneIva(Boolean.FALSE);
	posDeb.setDettagli(new ArrayList<PosizioneDebitoriaInAttesaDettaglio>());
	BigDecimal importo = BigDecimal.ZERO;
	for (PayDettaglioImporti payDetImporto : payPos.getDettagliImporto()) {
	    importo = importo.add(payDetImporto.getImporto());
	    PosizioneDebitoriaInAttesaDettaglio posDebDett = new PosizioneDebitoriaInAttesaDettaglio();
	    posDebDett.setDescrizione(payDetImporto.getDescCausale());
	    posDebDett.setImporto(payDetImporto.getImporto());
	    if (StringUtils.isNotBlank(payDetImporto.getNumeroAccertamento())) {
		posDebDett.setNumeroAccertamentoContabile(payDetImporto.getNumeroAccertamento());
		JAXBElement<Integer> annoAccert = new JAXBElement<>(new QName(Integer.class.getSimpleName()), Integer.class,
			payDetImporto.getAnnoAccertamento());
		posDebDett.setAnnoAccertamentoContabile(annoAccert);
	    }
	    //voce di costo recuperata dal sottoaccertamento
	    posDebDett.setNomeVoceDiCosto(payDetImporto.getNumeroSottoAccertamento());
	    //recupero la causale importo dai dati riscossione
	    CausaliImporti causImp = null;
	    try {
		causImp = CausaliImporti.fromValue(payDetImporto.getDatiRiscossione());
		posDebDett.setCausaleImporto(causImp);
	    } catch (Exception e) {
		throw new PayException("Valore enumeration inesistente per CausaliImporti verificare i dati riscossione del dettaglio importo", e);
	    }
	    posDebDett.setImporto(payDetImporto.getImporto());
	    posDeb.getDettagli().add(posDebDett);
	}
	posDeb.setImportoDovuto(importo);
    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	return false;
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	return false;
    }

    @Override
    public boolean supportaRataUnica() {

	return true;
    }

    @Override
    public boolean supportaMolteCausaliRaggruppate() {

	return false;
    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	return ParametriConnettoreHelper.getMappaParametriConnettore(this.getClass().getName());
    }

    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie pos, String identificativoCausalePerCalcolo) {

	return null;
    }

    private void gestisciPagamentoOTF(PayPosizioniDebitorie payPos, StatoPosizioneType retStatus, PosizioneDebitoriaResult respDebito)
	    throws PayConfigurationException {

	// per le posizioni OTF devo chiamare il metodo RICEVI ESITO TRANSAZIONE
	String idPosizionePsp = payPos.getIdPosizionePsp();
	log.debug("gestisciPagamentoOTF {} in stato {} ", idPosizionePsp, respDebito.getStato());
	String idTransazione = getIdTransazioneFromPosizioneDebitoria(payPos);
	log.debug("gestisciPagamentoOTF {} idTransazione {} ", idPosizionePsp, idTransazione);
	if (StringUtils.isBlank(idTransazione)) {
	    log.warn("gestisciPagamentoOTF {} IdTransazione non trovato per la posizione {}", idPosizionePsp, payPos.getId());
	    return;
	}
	IntestazioneFO header = this.login();
	RiceviEsitoTransazioneRequest req = new RiceviEsitoTransazioneRequest();
	req.setIdentificativoTransazione(idTransazione);
	log.debug("gestisciPagamentoOTF {} prima di chiamare riceviEsitoTransazione", idPosizionePsp);
	RiceviEsitoTransazioneResponse resp = getPort().riceviEsitoTransazione(header, req);
	log.debug("gestisciPagamentoOTF {} riceviEsitoTransazione chiamato", idPosizionePsp);
	if (!resp.getEsito().equalsIgnoreCase("OK")) {
	    log.debug("gestisciPagamentoOTF {} in resp.getEsito() {} ", idPosizionePsp, resp.getEsito());
	    return;
	}
	PagamentoPagoPA esitoTransazione = resp.getEsitoTransazione();
	log.debug("gestisciPagamentoOTF {} in esitoTransazione.getStato() {} ", idPosizionePsp, esitoTransazione.getStato());
	if (esitoTransazione.getStato().equals(StatoPagamentoPagoPA.PAGAMENTO_ACCETTATO)) {
	    // arrivano solamente questi dati
	    //		<res:EsitoTransazione>
	    //		<res:Stato>PagamentoAccettato</res:Stato>
	    //		<res:IdentificativoTransazione>M16F05D96389B14A9FA2B2AB3418092B15</res:IdentificativoTransazione>
	    //		<res:IdentificativoPSP>BNLIITRR</res:IdentificativoPSP>
	    //		<res:DescrizionePSP>Worldline</res:DescrizionePSP>
	    //		<res:TipoVersamnetoPSP>Pagamento attivato da Connettore</res:TipoVersamnetoPSP>
	    //		</res:EsitoTransazione>
	    log.debug("gestisciPagamentoOTF {}  popolo i dati di pagamento", idPosizionePsp);
	    DatiPagamentoType pagamento = new DatiPagamentoType();
	    pagamento.setIdPSP(StringUtils.left(esitoTransazione.getIdentificativoPSP(), 50));
	    pagamento.setRagioneSocialePSP(StringUtils.left(esitoTransazione.getDescrizionePSP(), 200));
	    if (esitoTransazione.getImporto() != null) {
		pagamento.setImportoPagato(esitoTransazione.getImporto());
	    } else {
		BigDecimal importo = BigDecimal.ZERO;
		List<PayDettaglioImporti> dettImporti = payDettaglioImportiService.findByIdPosizioneDebitoria(payPos.getId().getCodice());
		for (PayDettaglioImporti payDetImporto : dettImporti) {
		    importo = importo.add(payDetImporto.getImporto());
		}
		pagamento.setImportoPagato(importo);
	    }
	    pagamento.setDataOraInizioTransazione(esitoTransazione.getDataRichiesta());
	    pagamento.setDataOraAutorizzazione(esitoTransazione.getDatarisposta());
	    pagamento.setRiferimentiPagamento(StringUtils.left(esitoTransazione.getIdentificativoTransazione(), 50));
	    pagamento.setIur(esitoTransazione.getIdentificativoTransazione());
	    pagamento.setModalitaPagamento(StringUtils.left(esitoTransazione.getTipoVersamnetoPSP(), 50));
	    retStatus.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP);
	    retStatus.setStatoPagamentoNativo(esitoTransazione.getStato().value());
	    if (resp.getRTXML() != null) {
		DataSource ds = new ByteArrayDataSource(resp.getRTXML(), ContentType.APPLICATION_OCTET_STREAM.getMimeType());
		pagamento.setRicevutaXml(new DataHandler(ds));
	    }
	    retStatus.setDatiPagamento(pagamento);
	    log.debug("gestisciPagamentoOTF {} dati di pagamento popolati", idPosizionePsp);
	}
    }

    private String getIdTransazioneFromPosizioneDebitoria(PayPosizioniDebitorie payPos) {

	// da sessioni pagamento recupero l'ultimo idsessione che sarebbe l'id transazione
	// l'ultima ordinata per data indipendentemente se attiva o meno
	List<PaySessioniPagamento> sessioni = paySessioniPagamentoService.findSessioniPerPosizioneDebitoria(payPos.getId().getCodice());
	if (!sessioni.isEmpty()) {
	    return sessioni.get(0).getIdSessionePagamento();
	}
	return null;
    }
}

class DettaglioImportoEqualsPredicate implements Predicate<PosizioneDebitoriaDettaglio> {

    private PosizioneDebitoriaDettaglio matchWith;

    DettaglioImportoEqualsPredicate(PosizioneDebitoriaDettaglio matchWith) {

	this.matchWith = matchWith;
    }

    @Override
    public boolean evaluate(PosizioneDebitoriaDettaglio object) {

	if (object == null) {
	    if (this.matchWith != null) {
		return false;
	    }
	} else {
	    if (this.matchWith == null) {
		return false;
	    } else {
		if (object.getAnnoAccertamentoContabile() == null) {
		    if (matchWith.getAnnoAccertamentoContabile() != null) {
			return false;
		    }
		} else {
		    if (matchWith.getAnnoAccertamentoContabile() == null) {
			return false;
		    } else {
			if (!object.getAnnoAccertamentoContabile().equals(matchWith.getAnnoAccertamentoContabile())) {
			    return false;
			}
		    }
		}
		//NumeroAccertamentoContabile
		if (object.getNumeroAccertamentoContabile() == null) {
		    if (matchWith.getNumeroAccertamentoContabile() != null) {
			return false;
		    }
		} else {
		    if (matchWith.getNumeroAccertamentoContabile() == null) {
			return false;
		    } else {
			if (!object.getNumeroAccertamentoContabile().equals(matchWith.getNumeroAccertamentoContabile())) {
			    return false;
			}
		    }
		}
		//CausaleImporto
		if (object.getCausaleImporto() == null) {
		    if (matchWith.getCausaleImporto() != null) {
			return false;
		    }
		} else {
		    if (matchWith.getCausaleImporto() == null) {
			return false;
		    } else {
			if (!object.getCausaleImporto().equals(matchWith.getCausaleImporto())) {
			    return false;
			}
		    }
		}
		//NomeVoceDiCosto
		if (object.getNomeVoceDiCosto() == null) {
		    if (matchWith.getNomeVoceDiCosto() != null) {
			return false;
		    }
		} else {
		    if (matchWith.getNomeVoceDiCosto() == null) {
			return false;
		    } else {
			if (!object.getNomeVoceDiCosto().equals(matchWith.getNomeVoceDiCosto())) {
			    return false;
			}
		    }
		}
	    }
	}
	return true;
    }
}
