package it.gruppoinit.pal.gp.core.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.features.areariservata.VerticalizzazioneAreaRiservataServiceImpl;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiStoricoService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento;
import it.gruppoinit.pal.gp.core.service.ProtocollazioneManager;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoPratica;
import it.gruppoinit.pal.gp.core.service.rules.IstanzeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.types.ParametroType;

@Service
public class ProtocollazioneManagerImpl implements ProtocollazioneManager {

    public static final Logger log = LoggerFactory.getLogger(ProtocollazioneManagerImpl.class);
    private IstanzeService istanzeService;
    private DomandestcService domandestcService;
    private VerticalizzazioniService verticalizzazioniService;
    private IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService;
    private OggettiService oggettiService;
    private IstanzeDAO istanzeDAO;
    private DocumentiistanzaService documentiistanzaService;
    private OggettiStoricoService oggettiStoricoService;
    private OggettiMetadatiService oggettiMetadatiService;

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setDomandestcService(DomandestcService domandestcService) {

	this.domandestcService = domandestcService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setVerticalizzazioniService(IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService) {

	this.verticalizzazioneAreaRiservataService = verticalizzazioneAreaRiservataService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setIstanzeDAO(IstanzeDAO istanzeDAO) {

	this.istanzeDAO = istanzeDAO;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setOggettiStoricoService(OggettiStoricoService oggettiStoricoService) {

	this.oggettiStoricoService = oggettiStoricoService;
    }

    @Autowired
    public void setOggettiMetadatiService(OggettiMetadatiService oggettiMetadatiService) {

	this.oggettiMetadatiService = oggettiMetadatiService;
    }

    @Override
    public Map<String, String> insertRipetiProtocollazioneFallitaIstanza(Integer codiceIstanza) {

	Istanze i = istanzeService.findById(new PkId(codiceIstanza));
	String resultOK = "In data ''{0}'' la pratica {1}[{2}] è stata protocollata con tipo protocollazione ''{3}'', pratica da stc = {4}, pratica inserimento diretto = {5}. Numero = {6}, Data = {7}";
	String resultKO = "In data ''{0}'' la pratica {1}[{2}] NON è stata protocollata con tipo protocollazione ''{3}''. Più record Domandestc per il codice istanza {4}.";
	String resultERROR = "In data ''{0}'' la pratica {1}[{2}] NON è stata protocollata con tipo protocollazione ''{3}''(Vedi gp-backoffice.log). Errore = {4}";
	Domandestc domandestc = null;
	String message = "";
	TipoInserimento tipoInserimento = null;
	IstanzeBusinessRules istanzeBusinessRules = new IstanzeBusinessRules(true, true);
	boolean isPraticaInseritaDaStc = false;
	boolean isInserimentoDiretto = false;
	Map<String, String> res = null;
	try {
	    log.debug("ripetiProtocollazioneFallitaIstanza# recupero il tipo protocollazione codice = {}", i.getTipoProtFallita());
	    tipoInserimento = TipoInserimento.fromValue(Integer.parseInt(i.getTipoProtFallita()));
	    log.debug("ripetiProtocollazioneFallitaIstanza# Tipo protocollazione  = {}", tipoInserimento.name());
	    List<Domandestc> ldstc = domandestcService.findByIstanza(i.getId().getCodice());
	    if (!ldstc.isEmpty()) {
		if (ldstc.size() == 1) {
		    isPraticaInseritaDaStc = true;
		    log.debug("ripetiProtocollazioneFallitaIstanza# Istanza = {}[], Proviene da stc = {}, IdDomandaSTC = {}",
			    new Object[] { i.getNumeroistanza(), true, ldstc.get(0).getIdDomandamitt() });
		    domandestc = ldstc.get(0);
		    log.debug("ripetiProtocollazioneFallitaIstanza# Set IstanzeBusinessRules.....");
		    istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(), true);
		    log.debug("ripetiProtocollazioneFallitaIstanza# Rules: {} = {}", IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(),
			    true);
		    isInserimentoDiretto = isInserimentoDiretto(domandestc.getOggetti().getId().getCodice());
		    if (isInserimentoDiretto) {
			istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name(), true);
			log.debug("ripetiProtocollazioneFallitaIstanza# Rules: {} = {}",
				IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name(), false);
		    }
		    SigeproBusinessRules.setClassRules(IstanzeBusinessRules.class, istanzeBusinessRules);
		} else {
		    // FIXME - prendo l'ultima eventualmente ???
		    log.error(
			    "ripetiProtocollazioneFallitaIstanza# Istanza = {}[] ha più record su Domandestc impossibile procedere con la protocollazione",
			    new Object[] { i.getNumeroistanza(), true, ldstc.get(0).getIdDomandamitt() });
		    message = Utilities.formatMessage(resultKO, Utilities.getToday(true), i.getNumeroistanza(), i.getId().getCodice().toString(),
			    tipoInserimento.name(), i.getId().getCodice());
		    res = new HashMap<String, String>();
		    res.put("NULL", message);
		    return res;
		}
	    }
	    DatiProtocolloResponseType dp = istanzeService.insertProtocolloEfascicolo(i, tipoInserimento);
	    if (dp != null && dp.getErrore() != null && StringUtils.isNotBlank(dp.getErrore().getDescrizione())) {
		res = new HashMap<String, String>();
		message = Utilities.formatMessage(resultERROR, Utilities.getToday(true), i.getNumeroistanza(), i.getId().getCodice().toString(),
			tipoInserimento, dp.getErrore().getDescrizione());
		res.put("NULL", message);
	    } else {
		//i1 = istanzeService.findById(new PkId(i.getId().getCodice()));
		istanzeService.updateTipoProtFallita(i.getId().getCodice(), null);
		istanzeDAO.flush();
		istanzeDAO.clear();
		res = new HashMap<String, String>();
		message = Utilities.formatMessage(resultOK, Utilities.getToday(true), i.getNumeroistanza(), i.getId().getCodice().toString(),
			tipoInserimento.name(), isPraticaInseritaDaStc, isInserimentoDiretto, dp.getNumeroProtocollo(), dp.getDataProtocollo());
		res.put(dp.getNumeroProtocollo(), message);
	    }
	} catch (Exception e) {
	    log.error("ripetiProtocollazioneFallitaIstanza# {}", e);
	    String ti = "NON TROVATO";
	    if (tipoInserimento != null) {
		ti = tipoInserimento.name();
	    }
	    message = Utilities.formatMessage(resultERROR, Utilities.getToday(true), i.getNumeroistanza(), i.getId().getCodice().toString(), ti,
		    e.getMessage());
	    res = new HashMap<String, String>();
	    res.put("NULL", message);
	} finally {
	    log.debug("ripetiProtocollazioneFallitaIstanza# Reset Rules: {} = {}, {} = {}",
		    new Object[] { IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(), false,
			    IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name(), false });
	    istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(), false);
	    istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStcDiretto.name(), false);
	    SigeproBusinessRules.setClassRules(IstanzeBusinessRules.class, istanzeBusinessRules);
	}
	return res;
    }

    private boolean isInserimentoDiretto(Integer codiceOggetto) {

	boolean isInserimentoDiretto = false;
	try {
	    Oggetti oggettoDomanda = oggettiService.findById(new PkId(codiceOggetto));
	    // verifica se è attiva la verticalizzazione stc , altrimenti rilancia un eccezione.
	    isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	    // creo la request a partire dall'xml
	    String xmlString = new String(oggettoDomanda.getOggetto(), "UTF-8");
	    InputStream is = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
	    JAXBContext jc;
	    InserimentoPraticaNLARequest request = null;
	    jc = JAXBContext.newInstance(InserimentoPraticaNLARequest.class);
	    Unmarshaller u = jc.createUnmarshaller();
	    request = (InserimentoPraticaNLARequest) u.unmarshal(is);
	    if (request.getDettaglioPratica().getAltriDati() != null) {
		for (ParametroType param : request.getDettaglioPratica().getAltriDati()) {
		    if ("$INSERIMENTO_DIRETTO$".equals(param.getNome())) {
			isInserimentoDiretto = true;
			break;
		    }
		}
	    }
	} catch (Exception e) {
	    log.error(
		    "isInserimentoDiretto# Errore durante la lettura dell'xml della domanda per verifcare l'inserimento diretto. Codice oggetto domanda = {}",
		    codiceOggetto);
	    throw new RuntimeException(
		    "Errore durante la lettura dell'xml della domanda per verifcare l'inserimento diretto. Codice oggetto domanda = " +
			    codiceOggetto);
	}
	return isInserimentoDiretto;
    }

    @Override
    public String insertNuovaRicevutaPratica(Integer codiceIstanza) {

	StringBuffer m = new StringBuffer("Generazione nuova ricevuta per istaza = ").append(codiceIstanza);
	try {
	    byte[] b = createRicevuta(codiceIstanza);
	    String nomeFileRiepilogo = this.verticalizzazioneAreaRiservataService.isAttiva()
		    ? this.verticalizzazioneAreaRiservataService.getNomeFileRicevuta()
		    : "certificato-di-invio.pdf";
	    String descrFileRicevuta = this.verticalizzazioneAreaRiservataService.isAttiva()
		    ? this.verticalizzazioneAreaRiservataService.getDescrizioneFileRicevuta()
		    : null;
	    if (b != null) {
		log.debug("insertNuovaRicevutaPratica# Ricerco la ricevuta attraverso i metadati degli allegati dell'istanza");
		List<Documentiistanza> documentiistanzas = new ArrayList<Documentiistanza>();
		List<Documentiistanza> documentiistanzaTot = documentiistanzaService.findByIstanza(codiceIstanza);
		for (Documentiistanza documentiistanza : documentiistanzaTot) {
		    Integer codiceOggetto = documentiistanza.getOggetto().getId().getCodice();
		    List<OggettiMetadati> omd = oggettiMetadatiService.findByOggetto(codiceOggetto, WebConstants.OGGETTI_METADATI_TIPODOCUMENTO);
		    if (!omd.isEmpty() && "CertificatoInvio".equals(omd.get(0).getValore())) {
			documentiistanzas.add(documentiistanza);
		    }
		}
		if (documentiistanzas.isEmpty()) {
		    log.debug("insertNuovaRicevutaPratica# Non trovata la ricevuta attraverso i metadati degli allegati dell'istanza");
		    descrFileRicevuta = StringUtils.defaultIfEmpty(descrFileRicevuta, "Certificato di invio");
		    log.debug("insertNuovaRicevutaPratica# Ricerco la ricevuta negli allegati dell'istanza per nome file = {}", nomeFileRiepilogo);
		    documentiistanzas = documentiistanzaService.findByIstanzaNomeFile(nomeFileRiepilogo, codiceIstanza);
		}
		if (documentiistanzas.isEmpty()) {
		    log.debug(
			    "insertNuovaRicevutaPratica# Non trovata ricevuta sull'istanza. Inserisco nuova ricevuta generata. Nome file = {}, Descrizione file = {}",
			    nomeFileRiepilogo, descrFileRicevuta);
		    Documentiistanza documentiistanza = populateDocumentoIstanza(b, nomeFileRiepilogo, descrFileRicevuta, codiceIstanza);
		    documentiistanzaService.insert(documentiistanza);
		    m = m.append(" Ricevuta non trovata. Aggiungo nuova ricevuta. Descrizione file = ").append(descrFileRicevuta)
			    .append(", Nome file = ").append(nomeFileRiepilogo);
		} else if (documentiistanzas.size() == 1) {
		    log.debug("insertNuovaRicevutaPratica# Trovata ricevuta sull'istanza. Storicizzo inserendo la nuova ricevuta");
		    Oggetti o = populateAndInserOggetto(b, nomeFileRiepilogo);
		    Integer codiceOggettoVecchio = documentiistanzas.get(0).getOggetto().getId().getCodice();
		    Integer codiceOggettoNuovo = o.getId().getCodice();
		    String dscDoc = documentiistanzas.get(0).getDocumento();
		    Integer codiceDocIst = documentiistanzas.get(0).getId().getCodice();
		    log.debug(
			    "insertNuovaRicevutaPratica# Sostituisco il codice oggetto vecchio = {}, con il codice oggetto nuovo = {} per il documento istanza = {}[{}]",
			    new Object[] { codiceOggettoVecchio, codiceOggettoNuovo, dscDoc, codiceDocIst });
		    oggettiStoricoService.updateSostituisciOggetto(codiceIstanza, codiceOggettoVecchio, codiceOggettoNuovo,
			    TipoDocumentoPratica.DOC_ISTANZA);
		    m = m.append(" Ricevuta  trovata. Sostituisco filericevuta. Descrizione file = ").append(descrFileRicevuta)
			    .append(", Nome file = ").append(nomeFileRiepilogo).append(", Vecchio codice oggetto = ").append(codiceOggettoVecchio)
			    .append(" Nuovo codice oggetto = ").append(codiceOggettoNuovo);
		} else if (documentiistanzas.size() > 1) {
		    log.debug(
			    "insertNuovaRicevutaPratica# Trovati più file che corispondono alla ricevuta sull'istanza. Inserisco nuova ricevuta con suffisso data");
		    String data = Utilities.formatDate(new Date(), "yyyy-MM-dd");
		    String filed[] = nomeFileRiepilogo.split("\\.");
		    String _nomeFileRiepilogo = filed[0] + "_" + data + "." + filed[1];
		    String _descrFileRicevuta = descrFileRicevuta + "_" + data;
		    Documentiistanza documentiistanza = populateDocumentoIstanza(b, _nomeFileRiepilogo, _descrFileRicevuta, codiceIstanza);
		    log.debug("insertNuovaRicevutaPratica# Inserisco il documento istanza. Descrizione = {}, Nome file = {}, codice istanza = {}",
			    new Object[] { _descrFileRicevuta, _nomeFileRiepilogo, codiceIstanza });
		    documentiistanzaService.insert(documentiistanza);
		    m = m.append(" Trovate più ricevute con stesso nome file. Aggiungo nuova ricevuta. Descrizione file = ")
			    .append(_descrFileRicevuta).append(", Nome file = ").append(_nomeFileRiepilogo);
		}
	    } else {
		m = m.append(" Ricevuta non creata, array di byte null");
	    }
	} catch (IOException e) {
	    log.error("insertNuovaRicevutaPratica# {} ", e);
	    m = m.append(e.getMessage());
	}
	return m.toString();
    }

    private Oggetti populateAndInserOggetto(byte[] b, String nomeFile) {

	log.debug("populateAndInserOggetto# start...");
	Oggetti oggetto = new Oggetti();
	oggetto.setDimensioneFile(b.length);
	oggetto.setNomefile(nomeFile);
	oggetto.setOggetto(b);
	oggettiService.insert(oggetto);
	log.debug("populateAndInserOggetto# end...");
	return oggetto;
    }

    private Documentiistanza populateDocumentoIstanza(byte[] b, String nomeFile, String descrizioneFile, Integer codiceistanza) {

	log.debug("populateDocumentoIstanza# start...");
	Documentiistanza documentiistanza = new Documentiistanza();
	Istanze i = istanzeService.findById(new PkId(codiceistanza));
	documentiistanza.setData(new Date());
	documentiistanza.setIstanza(i);
	documentiistanza.setDocumento(descrizioneFile);
	documentiistanza.setNote("File generato dal processo di protocollazione automatica delle pratiche");
	documentiistanza.setPresente(true);
	Oggetti oggetto = populateAndInserOggetto(b, nomeFile);
	documentiistanza.setOggetto(oggetto);
	log.debug("populateDocumentoIstanzaS# end...");
	return documentiistanza;
    }

    private byte[] createRicevuta(Integer codiceIstanza) throws IOException {

	log.debug("createRicevuta# codiceIstanza={}", codiceIstanza);
	String codiceDomandeOnline = "";
	List<Domandestc> domandestcs = domandestcService.findByIstanza(codiceIstanza);
	if (domandestcs.size() > 0) {
	    for (Domandestc domandestc : domandestcs) {
		codiceDomandeOnline = domandestc.getIdDomandamitt();
		log.debug("createRicevuta# codice domanda online={}", codiceDomandeOnline);
		break;
	    }
	}
	Istanze i = istanzeService.findById(new PkId(codiceIstanza));
	URL url;
	byte[] bytes = null;
	try {
	    String urlRigenerazione = this.verticalizzazioneAreaRiservataService.getUrlRigenerazioneRicevutaPratica();
	    if (StringUtils.isBlank(urlRigenerazione)) {
		String err = "Attenzione! Parametro " +
			VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE +
			"." +
			VerticalizzazioneAreaRiservataServiceImpl.PAR_URL_GENERA_RICEVUTA_PRATICA +
			" non settato";
		log.error("createRicevuta#error:{} ", err);
		throw new RuntimeException(err);
	    }
	    urlRigenerazione = urlRigenerazione.replace("{codice-istanza}", i.getId().getCodice().toString());
	    urlRigenerazione = urlRigenerazione.replace("{valore-0-1}", "0");
	    urlRigenerazione = urlRigenerazione.replace("{codice-software}", i.getSoftware().getCodice());
	    urlRigenerazione = urlRigenerazione.replace("{codice-idcomune}", i.getId().getIdcomune());
	    urlRigenerazione = urlRigenerazione.replace("{codice-token}", ORMHelper.getToken());
	    log.debug("createRicevuta# donwload url={}", urlRigenerazione);
	    url = new URL(urlRigenerazione);
	    log.debug("createRicevuta# call url..");
	    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	    log.debug("createRicevuta# open connection ok....");
	    int responseCode = httpConn.getResponseCode();
	    log.debug("createRicevuta# response code={}", responseCode);
	    if (responseCode == HttpURLConnection.HTTP_OK) {
		// opens input stream from the HTTP connection
		InputStream inputStream = httpConn.getInputStream();
		bytes = IOUtils.toByteArray(inputStream);
	    } else {
		String err = "Errore nella generazione della ricevuta. Call = " + url + " , Response = " + responseCode;
		log.error("createRicevuta#error:{} ", err);
		throw new RuntimeException(err);
	    }
	} catch (MalformedURLException e) {
	    throw e;
	} catch (IOException e) {
	    throw e;
	}
	return bytes;
    }

    /**
     * se la vert non è attiva rilancia una RuntimeException
     * 
     * @param modulo
     * @return
     */
    private boolean isVerticalizzazioneAttiva(String modulo) {

	if (!verticalizzazioniService.isAttiva(modulo)) {
	    throw new RuntimeException("Attenzione: Non è configurata la verticalizzazione " + modulo);
	}
	return true;
    }
}
