package it.gruppoinit.pal.gp.backoffice.ws;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.backoffice.definitions.istanzeoneri.Istanzeoneri;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.EliminaOnereRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.EliminaOnereResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.EliminazioneOnereFallita;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.EliminazioneOnereRiuscita;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.InsertOnereFallito;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.InsertOnereRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.InsertOnereResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.InsertOnereRiuscito;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.ModalitaPagamentoType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.RegistraPagamentoRataRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri.RegistraPagamentoRequest;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

@WebService(serviceName = "IstanzeoneriWsService", portName = "IstanzeoneriSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/istanzeoneri", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.istanzeoneri.Istanzeoneri")
public class IstanzeoneriWS extends BaseWS implements Istanzeoneri {

    private static final Logger log = LoggerFactory.getLogger(IstanzeoneriWS.class);
    private AmministrazioniService amministrazioniService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private IstanzeoneriService istanzeoneriService;
    private IstanzeService istanzeService;
    private ResponsabiliService responsabiliService;
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    private TipicausalioneriService tipicausalioneriService;

    @Override
    @WebResult(name = "RegistraPagamentoResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/istanzeoneri", partName = "RegistraPagamentoResponse")
    @WebMethod(operationName = "RegistraPagamento", action = "RegistraPagamento")
    public EsitoOperazioneType registraPagamento(
	    @WebParam(partName = "RegistraPagamentoRequest", name = "RegistraPagamentoRequest", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/istanzeoneri") RegistraPagamentoRequest registraPagamentoRequest) {

	if (log.isDebugEnabled()) {
	    log.debug("registraPagamento# entro nel metodo");
	}
	EsitoOperazioneType result = new EsitoOperazioneType();
	result.setEsito(0);
	setORMHelper(WebConstants.SOFTWARE_TT, registraPagamentoRequest.getToken());
	try {
	    BigInteger id = registraPagamentoRequest.getRiferimentoOnere();
	    if (id == null) {
		String err = String.format("Non è stato possibile recuperare l'onere. Il riferimento è nullo.: %s",
			dumpRequest(registraPagamentoRequest));
		log.error(String.format("registraPagamento# %s", err));
		return esciConErrore(506, err);
	    }
	    Integer codiceIstanzeOneri = id.intValue();
	    if (log.isDebugEnabled()) {
		log.debug("registraPagamento# recupero il record di istanzeoneri con id {}", id.intValue());
	    }
	    it.gruppoinit.pal.gp.core.domain.Istanzeoneri io = istanzeoneriService.findById(new PkId(codiceIstanzeOneri));
	    if (io == null) {
		String errore = String.format("registraPagamento# Non è stato trovato l'onere con riferimento [%s]: %s", codiceIstanzeOneri,
			dumpRequest(registraPagamentoRequest));
		log.error(errore);
		errore = String.format("Non è stato trovato l'onere con riferimento [%s]: %s", codiceIstanzeOneri,
			dumpRequest(registraPagamentoRequest));
		return esciConErrore(505, errore);
	    }
	    BigDecimal oldImporto = io.getImportopagato();
	    if (oldImporto == null) {
		oldImporto = BigDecimal.ZERO;
	    }
	    String note = "";
	    if (oldImporto.compareTo(BigDecimal.ZERO) > 0) {
		BigDecimal newImporto = oldImporto.add(registraPagamentoRequest.getImporto());
		io.setImportopagato(newImporto);
		note = "E' presente già l'importo " +
			oldImporto.toPlainString() +
			" ed il pagamento e' stato sommato a " +
			registraPagamentoRequest.getImporto().toPlainString() +
			".\n";
		if (StringUtils.isNotBlank(registraPagamentoRequest.getNote())) {
		    note += registraPagamentoRequest.getNote();
		}
	    } else {
		io.setImportopagato(registraPagamentoRequest.getImporto());
		if (StringUtils.isNotBlank(registraPagamentoRequest.getNote())) {
		    note = StringUtils.defaultString(registraPagamentoRequest.getNote());
		}
	    }
	    if (StringUtils.isNotBlank(io.getNote())) {
		note += "\n " + StringUtils.defaultString(io.getNote());
	    }
	    io.setNote(StringUtils.left(note, 4000));
	    io.setDatapagamento(registraPagamentoRequest.getDataPagamento().toGregorianCalendar().getTime());
	    ModalitaPagamentoType mp = registraPagamentoRequest.getModalitaPagamento();
	    if (mp != null) {
		Tipimodalitapagamento tm = bindModalitaPagamento(mp);
		if (tm != null) {
		    io.setTipimodalitapagamento(tm);
		}
	    }
	    if (StringUtils.isNotBlank(registraPagamentoRequest.getRiferimentiPagamento())) {
		io.setDocriferimento(registraPagamentoRequest.getRiferimentiPagamento());
	    }
	    if (log.isDebugEnabled()) {
		log.debug("registraPagamento# aggiorno istanzeoneri con id {}", id.intValue());
	    }
	    auditAggiornamentoOnere(codiceIstanzeOneri, oldImporto, registraPagamentoRequest.getImporto());
	    istanzeoneriService.update(io);
	} catch (Exception e) {
	    log.error("registraPagamento# errore nella registrazione del pagamento {}, {}", dumpRequest(registraPagamentoRequest), e);
	    result.setEsito(500);
	    ErroreBackofficeType ex = new ErroreBackofficeType();
	    ex.setCodice("501");
	    ex.setDescrizione("Errore nella registrazione del pagamento: " + e.getMessage() + "\n" + dumpRequest(registraPagamentoRequest));
	    result.getListaErrori().add(ex);
	}
	return result;
    }

    private void auditAggiornamentoOnere(Integer codiceIstanzeoneri, BigDecimal oldImporto, BigDecimal newImporto) {

	Responsabili resp = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	String responsabile = "";
	if (resp != null) {
	    responsabile = resp.toString();
	}
	String messaggio = "id: " + codiceIstanzeoneri + ",  vecchio importopagato: " + oldImporto + ", nuovo importo: " + newImporto;
	LoggerCancellazioni.logAggiornaOnereistanza(responsabile, messaggio);
    }

    @Override
    @WebResult(name = "RegistraPagamentoResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/istanzeoneri", partName = "RegistraPagamentoResponse")
    @WebMethod(operationName = "RegistraPagamentoRata", action = "RegistraPagamentoRata")
    public EsitoOperazioneType registraPagamentoRata(
	    @WebParam(partName = "RegistraPagamentoRataRequest", name = "RegistraPagamentoRataRequest", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/istanzeoneri") RegistraPagamentoRataRequest registraPagamentoRataRequest) {

	if (log.isDebugEnabled()) {
	    log.debug("registraPagamentoRata# entro nel metodo");
	}
	setORMHelper(WebConstants.SOFTWARE_TT, registraPagamentoRataRequest.getToken());
	try {
	    if (log.isDebugEnabled()) {
		log.debug("registraPagamentoRata# effettuo la vaidazione della request");
	    }
	    if (registraPagamentoRataRequest.getRiferimentoCausale() == null) {
		return esciConErrore(501, "Non è stata specificata la causale e non è possibile estrarre informazioni sull'onere. " +
			dumpRequest(registraPagamentoRataRequest));
	    }
	    if (registraPagamentoRataRequest.getRiferimentoIstanza() == null) {
		return esciConErrore(501, "Non è stata specificato il riferimento istanza e non è possibile estrarre informazioni sull'onere. " +
			dumpRequest(registraPagamentoRataRequest));
	    }
	    if (registraPagamentoRataRequest.getNumeroRata() == null) {
		return esciConErrore(501, "Non è stata specificato numerorata e non è possibile estrarre informazioni sull'onere. " +
			dumpRequest(registraPagamentoRataRequest));
	    }
	    BigInteger id = registraPagamentoRataRequest.getRiferimentoIstanza();
	    if (id == null) {
		return esciConErrore(506,
			"Non è stato possibile recuperare l'onere. Il riferimento è nullo. " + dumpRequest(registraPagamentoRataRequest));
	    }
	    if (log.isDebugEnabled()) {
		log.debug("registraPagamentoRata# cerco l'istanza con id: {},{}", ORMHelper.getIdcomune(), id.intValue());
	    }
	    Integer codiceIstanza = id.intValue();
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    if (istanza == null) {
		return esciConErrore(501,
			"L'istanza con codice " + codiceIstanza.intValue() + " non è stata trovata. " + dumpRequest(registraPagamentoRataRequest));
	    }
	    int codiceCausale = registraPagamentoRataRequest.getRiferimentoCausale().intValue();
	    int numeroRata = registraPagamentoRataRequest.getNumeroRata().intValue();
	    if (log.isDebugEnabled()) {
		log.debug("registraPagamentoRata# ciclo gli oneri dell'istanza");
	    }
	    List<it.gruppoinit.pal.gp.core.domain.Istanzeoneri> oneri = istanzeoneriService.findByIstanzaCausaleRata(codiceIstanza, codiceCausale,
		    numeroRata);
	    if (oneri.isEmpty()) {
		return esciConErrore(506, "Non è stato possibile recuperare l'onere. Il riferimento passato non è stato trovato nella base dati. " +
			dumpRequest(registraPagamentoRataRequest));
	    }
	    // Aggiorna solo i dati del primo onere (prima nel ciclo for c'era un break dopo il primo elemento)
	    it.gruppoinit.pal.gp.core.domain.Istanzeoneri io = oneri.get(0);
	    if (log.isDebugEnabled()) {
		log.debug("registraPagamentoRata# trovato l'onere con id: {}", io.getId());
	    }
	    BigDecimal oldImporto = io.getImportopagato() == null ? BigDecimal.ZERO : io.getImportopagato();
	    BigDecimal newImporto = registraPagamentoRataRequest.getImporto();
	    Integer codiceIstanzeoneri = io.getId().getCodice();
	    String note = StringUtils.defaultString(registraPagamentoRataRequest.getNote());
	    if (oldImporto.compareTo(BigDecimal.ZERO) > 0) {
		note = "E' presente già l'importo " +
			oldImporto.toPlainString() +
			" ed il pagamento e' stato sommato a " +
			registraPagamentoRataRequest.getImporto().toPlainString() +
			".\n" +
			note;
		newImporto = oldImporto.add(newImporto);
	    }
	    io.inserisciNotaPrimaDiNoteEsistenti(note);
	    io.setImportopagato(newImporto);
	    io.setDatapagamento(registraPagamentoRataRequest.getDataPagamento().toGregorianCalendar().getTime());
	    ModalitaPagamentoType mp = registraPagamentoRataRequest.getModalitaPagamento();
	    if (mp != null) {
		Tipimodalitapagamento tm = bindModalitaPagamento(mp);
		if (tm != null) {
		    io.setTipimodalitapagamento(tm);
		}
	    }
	    if (StringUtils.isNotBlank(registraPagamentoRataRequest.getRiferimentiPagamento())) {
		io.setDocriferimento(registraPagamentoRataRequest.getRiferimentiPagamento());
	    }
	    if (log.isDebugEnabled()) {
		log.debug("registraPagamentoRata# aggiorno l'onere");
	    }
	    auditAggiornamentoOnere(codiceIstanzeoneri, oldImporto, registraPagamentoRataRequest.getImporto());
	    istanzeoneriService.update(io);
	    return new EsitoOperazioneType(0);
	} catch (Exception e) {
	    log.error("registraPagamentoRata# errore nella registrazione del pagamento  {}, {}", dumpRequest(registraPagamentoRataRequest), e);
	    String err = String.format("Errore nella registrazione del pagamento: %s%n%s", e.getMessage(), dumpRequest(registraPagamentoRataRequest));
	    return new EsitoOperazioneType(500, new ErroreBackofficeType("501", err));
	}
    }

    @Override
    @WebResult(name = "InsertOnereResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/istanzeoneri", partName = "InsertOnereResponse")
    @WebMethod(operationName = "InsertOnere", action = "InsertOnere")
    public InsertOnereResponse insertOnere(InsertOnereRequest parameters) {

	setORMHelper(WebConstants.SOFTWARE_TT, parameters.getToken());
	// Effettuo la validazione
	List<ErroreBackofficeType> erroriValidazione = validateRequest(parameters);
	if (!erroriValidazione.isEmpty()) {
	    InsertOnereFallito esito = new InsertOnereFallito(erroriValidazione);
	    log.error("Validazione non superata. Errori: {} ", esito.getTestoErrore());
	    return esito;
	}
	try {
	    PopulateIstanzeOneriResult oneri = populateIstanzeOneri(parameters);
	    if (oneri.getIstanzeoneri() == null) {
		InsertOnereFallito esito = new InsertOnereFallito(oneri.getErrore());
		log.error("Operazione di popolamento istanze oneri si è conclusa con errori. Errori {}", esito.getTestoErrore());
		return esito;
	    }
	    log.debug("insertOnere# Inserisco l'oggetto istanze oneri popolato");
	    istanzeoneriService.insert(oneri.getIstanzeoneri());
	    return new InsertOnereRiuscito(BigInteger.valueOf(oneri.getIstanzeoneri().getId().getCodice()));
	} catch (RuntimeException e) {
	    log.error("Errore imprevisto: {}", e.getMessage());
	    return new InsertOnereFallito(new ErroreBackofficeType("2", e.getMessage() + " - " + e.toString()));
	}
    }

    @WebMethod(operationName = "EliminaOnere", action = "EliminaOnere")
    @WebResult(name = "EliminaOnereResponse", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/istanzeoneri", partName = "EliminaOnereResponse")
    public EliminaOnereResponse eliminaOnere(
	    @WebParam(partName = "EliminaOnereRequest", name = "EliminaOnereRequest", targetNamespace = "http://gruppoinit.it/sigepro/schemas/messages/istanzeoneri") EliminaOnereRequest eliminaOnereRequest) {

	setORMHelper(WebConstants.SOFTWARE_TT, eliminaOnereRequest.getToken());
	if (eliminaOnereRequest.getCodiceonere() == null) {
	    return new EliminazioneOnereFallita(0, "Codice onere da eliminare non specificato");
	}
	try {
	    this.istanzeoneriService.delete(eliminaOnereRequest.getCodiceonere().intValue());
	    return new EliminazioneOnereRiuscita();
	} catch (Exception e) {
	    log.error("Errore imprevisto: {}", e.getMessage());
	    return new EliminazioneOnereFallita(eliminaOnereRequest.getCodiceonere().intValue(), e.getMessage());
	}
    }

    public class PopulateIstanzeOneriResult {

	private it.gruppoinit.pal.gp.core.domain.Istanzeoneri istanzeoneri;
	private ErroreBackofficeType errore;

	public PopulateIstanzeOneriResult(it.gruppoinit.pal.gp.core.domain.Istanzeoneri istanzeoneri) {

	    this.istanzeoneri = istanzeoneri;
	    this.errore = null;
	}

	public PopulateIstanzeOneriResult(String errore) {

	    this.istanzeoneri = null;
	    this.errore = new ErroreBackofficeType("1", errore);
	}

	public ErroreBackofficeType getErrore() {

	    return errore;
	}

	public it.gruppoinit.pal.gp.core.domain.Istanzeoneri getIstanzeoneri() {

	    return istanzeoneri;
	}
    }

    private PopulateIstanzeOneriResult populateIstanzeOneri(InsertOnereRequest parameters) {

	it.gruppoinit.pal.gp.core.domain.Istanzeoneri istanzeoneri = new it.gruppoinit.pal.gp.core.domain.Istanzeoneri();
	// Popolo le parti obbligatorie
	log.debug("populateIstanzeOneri# Recupero l'istanza con codice {}", parameters.getRiferimentoIstanza().intValue());
	Istanze istanza = istanzeService.findById(new PkId(parameters.getRiferimentoIstanza().intValue()));
	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    return new PopulateIstanzeOneriResult("Istanza non trovata con codice " + parameters.getRiferimentoIstanza());
	}
	istanzeoneri.setIstanza(istanza);
	log.debug("populateIstanzeOneri# Recupero la causale con codice {}", parameters.getRiferimentoCausale().intValue());
	Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(new PkId(parameters.getRiferimentoCausale().intValue()));
	if (EntityUtils.getNestedProperty(tipicausalioneri, "id.codice") == null) {
	    return new PopulateIstanzeOneriResult("Tipo causale onere non trovata con codice " + parameters.getRiferimentoCausale());
	}
	istanzeoneri.setTipicausalioneri(tipicausalioneri);
	// Non faccio nessun controllo perchè già so che è !=null, ha passato la validaizone
	log.debug("populateIstanzeOneri# Imposto l'importo {}", parameters.getImporto());
	istanzeoneri.setPrezzo(parameters.getImporto());
	// DATI  NON OBBLIGATORI
	log.debug("populateIstanzeOneri# Recupero l'amministrazione con codice {}", parameters.getCodiceamministrazioni());
	if (parameters.getCodiceamministrazioni() != null) {
	    Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(parameters.getCodiceamministrazioni().intValue()));
	    istanzeoneri.setAmministrazioni(amministrazioni);
	}
	log.debug("populateIstanzeOneri# Recupero il responsabile con codice {}", parameters.getCodiceresponsabile());
	if (parameters.getCodiceresponsabile() != null) {
	    Responsabili responsabili = responsabiliService.findById(new PkId(parameters.getCodiceresponsabile().intValue()));
	    istanzeoneri.setResponsabile(responsabili);
	}
	if (parameters.getDataPagamento() != null) {
	    log.debug("populateIstanzeOneri# Imposto la data di pagamento {}",
		    Utilities.formatDate(parameters.getDataPagamento().toGregorianCalendar().getTime(), true));
	    istanzeoneri.setDatapagamento(parameters.getDataPagamento().toGregorianCalendar().getTime());
	}
	if (parameters.getDatascadenza() != null) {
	    log.debug("populateIstanzeOneri# Imposto la data di pagamento {}",
		    Utilities.formatDate(parameters.getDatascadenza().toGregorianCalendar().getTime(), true));
	    istanzeoneri.setDatascadenza(parameters.getDatascadenza().toGregorianCalendar().getTime());
	}
	log.debug("populateIstanzeOneri# Imposto importo pagato");
	if (parameters.getImportopagato() != null) {
	    istanzeoneri.setImportopagato(parameters.getImportopagato());
	}
	if (parameters.getModalitaPagamento() != null) {
	    log.debug("populateIstanzeOneri# Recupero la moddalita di pagamento {} [{}]",
		    new Object[] { parameters.getModalitaPagamento().getDescrizione(), parameters.getModalitaPagamento().getCodice() });
	    Tipimodalitapagamento tipimodalitapagamento = tipimodalitapagamentoService
		    .findById(new PkId(Integer.parseInt(parameters.getModalitaPagamento().getCodice())));
	    istanzeoneri.setTipimodalitapagamento(tipimodalitapagamento);
	}
	log.debug("populateIstanzeOneri# Imposto le note");
	if (StringUtils.isNotBlank(parameters.getNote())) {
	    istanzeoneri.setNote(parameters.getNote());
	}
	log.debug("populateIstanzeOneri# Imposto il numero rata {}", parameters.getNumerorata());
	if (parameters.getNumerorata() != null) {
	    istanzeoneri.setNumerorata(parameters.getNumerorata().intValue());
	}
	log.debug("populateIstanzeOneri# Imposto i riferimenti pagamento {}", parameters.getRiferimentiPagamento());
	if (StringUtils.isNotBlank(parameters.getRiferimentiPagamento())) {
	    istanzeoneri.setDocriferimento(parameters.getRiferimentiPagamento());
	}
	log.debug("populateIstanzeOneri# Recupero l'inventarioprocedimento con codice {}", parameters.getRiferimentoEndoprocedimento());
	if (parameters.getRiferimentoEndoprocedimento() != null) {
	    Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService
		    .findById(new PkId(parameters.getRiferimentoEndoprocedimento().intValue()));
	    istanzeoneri.setInventarioprocedimenti(inventarioprocedimenti);
	}
	log.debug("populateIstanzeOneri# Recupero valore interesse se presente");
	if (parameters.getImportoInteresse() != null) {
	    istanzeoneri.setImportoInteresse(parameters.getImportoInteresse());
	}
	log.debug("populateIstanzeOneri# Recupero valore is rata, se presente");
	istanzeoneri.setFlagOnereRateizzato(false);
	if (parameters.isOnereRateizzato() != null) {
	    istanzeoneri.setFlagOnereRateizzato(parameters.isOnereRateizzato());
	}
	return new PopulateIstanzeOneriResult(istanzeoneri);
    }

    private List<ErroreBackofficeType> validateRequest(InsertOnereRequest parameters) {

	log.debug("validateRequest# Validazione request....");
	List<ErroreBackofficeType> errori = new ArrayList<ErroreBackofficeType>();
	if (StringUtils.isBlank(parameters.getToken())) {
	    errori.add(new ErroreBackofficeType("1", "token : obbligatorio"));
	}
	if (parameters.getImporto() == null) {
	    errori.add(new ErroreBackofficeType("1", "importo : obbligatorio"));
	}
	if (parameters.getRiferimentoCausale() == null) {
	    errori.add(new ErroreBackofficeType("1", "Rif. causale : obbligatorio"));
	}
	if (parameters.getRiferimentoIstanza() == null) {
	    errori.add(new ErroreBackofficeType("1", "Rif. istanza : obbligatorio"));
	}
	return errori;
    }

    private String dumpRequest(RegistraPagamentoRequest registraPagamentoRequest) {

	if (registraPagamentoRequest == null) {
	    registraPagamentoRequest = new RegistraPagamentoRequest();
	}
	return ReflectionToStringBuilder.toString(registraPagamentoRequest, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    private EsitoOperazioneType esciConErrore(int codice, String messaggio) {

	log.error("Errore nella registrazione del pagamento [{}] - {}", codice, messaggio);
	String codiceStr = codice + "";
	return new EsitoOperazioneType(codice, new ErroreBackofficeType(codiceStr, messaggio));
    }

    private Tipimodalitapagamento bindModalitaPagamento(ModalitaPagamentoType mp) {

	if (mp == null) {
	    return null;
	}
	if (StringUtils.isBlank(StringUtils.defaultString(mp.getCodice()).trim())
		&& StringUtils.isBlank(StringUtils.defaultString(mp.getDescrizione()).trim())) {
	    return null;
	}
	String codice = StringUtils.defaultString(mp.getCodice()).trim();
	String descrizione = StringUtils.defaultString(mp.getCodice()).trim();
	if (StringUtils.isBlank(descrizione)) {
	    return null;
	}
	List<Tipimodalitapagamento> tipimodalitapagamentos = tipimodalitapagamentoService.findByDescrizioneEsatta(descrizione);
	if (tipimodalitapagamentos.isEmpty()) {
	    log.warn("Non è stato possibile recuperare una modalita' pagamento dalle informazioni: codice: {}, descrizione: {}", codice, descrizione);
	    return null;
	}
	return tipimodalitapagamentos.get(0);
    }

    private String dumpRequest(RegistraPagamentoRataRequest registraPagamentoRataRequest) {

	if (registraPagamentoRataRequest == null) {
	    registraPagamentoRataRequest = new RegistraPagamentoRataRequest();
	}
	return ReflectionToStringBuilder.toString(registraPagamentoRataRequest, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Autowired
    public void setTipimodalitapagamentoService(TipimodalitapagamentoService tipimodalitapagamentoService) {

	this.tipimodalitapagamentoService = tipimodalitapagamentoService;
    }

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }
}
