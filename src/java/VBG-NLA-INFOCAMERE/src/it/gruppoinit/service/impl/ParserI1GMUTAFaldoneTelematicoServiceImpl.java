package it.gruppoinit.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.ws.rs.NotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.pratica.AdempimentoSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.AllegatoGenerico;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaPersona;
import it.gov.impresainungiorno.schema.suap.pratica.ImpiantoProduttivo.DatiCatastali;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.TipoIntervento;
import it.gruppoinit.domain.nla.Allegato;
import it.gruppoinit.faldonetelematico.model.CodiceAllegato;
import it.gruppoinit.faldonetelematico.model.CompilazioneModulo;
import it.gruppoinit.faldonetelematico.model.FaldoneTelematicoValoreCampoEnum;
import it.gruppoinit.faldonetelematico.model.RiepilogoPraticaFaldoneTelematico;
import it.gruppoinit.faldonetelematico.model.UrnAllegato;
import it.gruppoinit.faldonetelematico.model.ValoreCampo;
import it.gruppoinit.service.MappingElementICToBOService;
import it.gruppoinit.service.MappingFaldoneTelematicoToBOService;
import it.gruppoinit.service.ParserI1GMUTAFaldoneTelematicoService;
import it.gruppoinit.service.helper.TempFileHelper;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.AltriSoggettiType;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.CodiceDescrizioneType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DatiIscrizioneAlboType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.InterventoType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.MetaDatoType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;
import it.init.sigepro.rte.types.RuoloType;
import it.init.sigepro.rte.types.SchedaType;

@Service
public class ParserI1GMUTAFaldoneTelematicoServiceImpl implements ParserI1GMUTAFaldoneTelematicoService {

    private static final Logger log = LoggerFactory.getLogger(ParserI1GMUTAFaldoneTelematicoServiceImpl.class);
    @Autowired
    private MappingElementICToBOService mappingElementICToBOService;
    @Autowired
    private MappingFaldoneTelematicoToBOService mappingFaldoneTelematicoToBOService;

    @Override
    public DettaglioPraticaType getRipielogoPraticaFaldoneTelematico(List<DocumentiType> documenti, boolean popolaProcedimenti) throws Exception {

	String uuidPratica = UUID.randomUUID().toString();
	Map<String, TempFileHelper> allegatiScaricati = new HashMap<String, TempFileHelper>();
	log.debug("processo la pratica nella cartella {}", uuidPratica);
	TempFileHelper faldoneXML = getFaldone(documenti, uuidPratica, allegatiScaricati);
	JAXBContext jaxbContext = JAXBContext.newInstance(RiepilogoPraticaFaldoneTelematico.class);
	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	RiepilogoPraticaFaldoneTelematico riepilogoPraticaFaldoneTelematico = (RiepilogoPraticaFaldoneTelematico) unmarshaller
		.unmarshal(faldoneXML.getDataHandler().getInputStream());
	List<CompilazioneModulo> compilazioneModuli = riepilogoPraticaFaldoneTelematico.getCompilazioneModulo();
	String codPratica = riepilogoPraticaFaldoneTelematico.getIdCompilazione();
	DettaglioPraticaType dettaglioPraticaType = new DettaglioPraticaType();
	CompilazioneModulo compilazioneModulo = compilazioneModuli.get(0);
	dettaglioPraticaType.setCodicePraticaTelematica(codPratica);
	dettaglioPraticaType.setIdPratica(codPratica);
	dettaglioPraticaType.setNumeroPratica(codPratica);
	dettaglioPraticaType.setOggetto(StringUtils.defaultIfEmpty(compilazioneModulo.getOggetto(), ""));
	// for su valori campi
	List<ValoreCampo> valoreCampo = compilazioneModulo.getValoriCampo().getValoreCampo();
	for (ValoreCampo valoreCampo2 : valoreCampo) {
	    // ///////////////////////////////// SEZIONE  RICHIENDENTE ///////////////////////////////////////    
	    if (StringUtils.equalsIgnoreCase(valoreCampo2.getNome(), "Titolare")) {
		log.debug("getRipielogoPraticaFaldoneTelematico# populate RICHIEDENTE... {} ", uuidPratica);
		PersonaFisicaType personaFisicaType = new PersonaFisicaType();
		mappingFaldoneTelematicoToBOService.populatePersonaFisicaType(valoreCampo2, personaFisicaType);
		PersonaGiuridicaType personaGiuridicaType = new PersonaGiuridicaType();
		mappingFaldoneTelematicoToBOService.populatePersonaGiuridicaType(valoreCampo2, personaGiuridicaType);
		dettaglioPraticaType.setAziendaRichiedente(personaGiuridicaType);
		RichiedenteType richiedenteType = new RichiedenteType();
		mappingFaldoneTelematicoToBOService.populateRichiedenteType(valoreCampo2, richiedenteType);
		richiedenteType.setAnagrafica(personaFisicaType);
		dettaglioPraticaType.setRichiedente(richiedenteType);
	    }
	    /////////////////////////////////// SEZIONE LOCALIZZAZIONE  E COMUNE ///////////////////////////////////////////////////////
	    else if (StringUtils.equalsIgnoreCase(valoreCampo2.getNome(), "Immobile")) {
		log.debug("getRipielogoPraticaFaldoneTelematico# populate LOCALIZZAZIONE...{} ", uuidPratica);
		LocalizzazioneNelComuneType localizzazioneType = new LocalizzazioneNelComuneType();
		String cap = "";
		String civico = "";
		String via = "";
		for (ValoreCampo valoreCampo3 : valoreCampo2.getSubValoreCampo()) {
		    if (StringUtils.equalsIgnoreCase(valoreCampo3.getNome(), "CatNumero")) {
			cap = valoreCampo3.getValore();
			localizzazioneType.setCap(cap);
		    }
		    if (StringUtils.equalsIgnoreCase(valoreCampo3.getNome(), "Civico")) {
			civico = valoreCampo3.getValore();
			localizzazioneType.setCivico(civico);
		    }
		    if (StringUtils.equalsIgnoreCase(valoreCampo3.getNome(), "Via")) {
			via = valoreCampo3.getValore();
		    }
		}
		localizzazioneType.setDenominazione(via);
		localizzazioneType.setCivico(civico);
		localizzazioneType.setCap(cap);
		dettaglioPraticaType.getLocalizzazione().add(localizzazioneType);
	    }
	    //////////////////////////////////////SEZIONE COMUNE ////////////////////////////////////////////////////////
	    else if (StringUtils.equalsIgnoreCase(valoreCampo2.getNome(), "Modulo")) {
		log.debug("getRipielogoPraticaFaldoneTelematico# populate COMUNE...{} ", uuidPratica);
		ComuneType comuneType = new ComuneType();
		for (ValoreCampo valoreCampo4 : valoreCampo2.getSubValoreCampo()) {
		    if (StringUtils.equalsIgnoreCase(valoreCampo4.getNome(), "domiciliodigitale")) {
			dettaglioPraticaType.setDomicilioElettronico(StringUtils.defaultIfEmpty(valoreCampo4.getValore(), ""));
		    } else if (StringUtils.equalsIgnoreCase(valoreCampo4.getNome(), "BelFiore")) {
			String belfiore = StringUtils.upperCase(valoreCampo4.getValore().split("_")[1]);
			comuneType.setCodiceCatastale(belfiore);
		    } else if (StringUtils.equalsIgnoreCase(valoreCampo4.getNome(), "Luogo")) {
			comuneType.setComune(valoreCampo4.getValore());
		    }
		}
		dettaglioPraticaType.setCodiceComune(comuneType);
		// ///////////////////////////////// SEZIONE  INTERMEDIARIO ///////////////////////////////////////  
	    } else if (StringUtils.equalsIgnoreCase(valoreCampo2.getNome(), "Referente")) {
		log.debug("getRipielogoPraticaFaldoneTelematico# populate INTERMEDIARIO... {} ", uuidPratica);
		AnagrafeType anagrafeType = new AnagrafeType();
		PersonaFisicaType perFis = new PersonaFisicaType();
		mappingFaldoneTelematicoToBOService.populatePersonaFisicaType(valoreCampo2, perFis);
		if (perFis != null && StringUtils.isNotBlank(perFis.getCognome()) && StringUtils.isNotBlank(perFis.getNome())) {
		    DatiIscrizioneAlboType datiIscrizioneAlboType = new DatiIscrizioneAlboType();
		    LocalizzazioneType lo = new LocalizzazioneType();
		    for (ValoreCampo campoReferente : valoreCampo2.getSubValoreCampo()) {
			switch (FaldoneTelematicoValoreCampoEnum.fromValue(campoReferente.getNome())) {
			case ATTRIBUTI_ALBO:
			    CodiceDescrizioneType codiceDescrizioneType = new CodiceDescrizioneType();
			    codiceDescrizioneType.setDescrizione(campoReferente.getValore());
			    break;
			case Attributi_AlNumero:
			    datiIscrizioneAlboType.setNumeroIscrizione(campoReferente.getValore());
			    break;
			case ATTRIBUTI_DELLAPROVINCIA:
			    datiIscrizioneAlboType.setSiglaProvincia(campoReferente.getValore());
			    break;
			case PROVINCIASTUDIO:
			    lo.setProvincia(campoReferente.getValore());
			    break;
			case CAPSTUDIO:
			    lo.setCap(campoReferente.getValore());
			    break;
			case CIVICOSTUDIO:
			    lo.setCivico(campoReferente.getValore());
			    break;
			case COMUNESTUDIO:
			    ComuneType comuneType = new ComuneType();
			    comuneType.setComune(campoReferente.getValore());
			    lo.setComune(comuneType);
			    break;
			case VIASTUDIO:
			    lo.setIndirizzo(campoReferente.getValore());
			    break;
			case TELEFONOSTUDIO:
			    perFis.setTelefono(campoReferente.getValore());
			    break;
			default:
			    break;
			}
		    }
		    perFis.setDatiIscrizioneAlbo(datiIscrizioneAlboType);
		    perFis.setCorrispondenza(lo);
		    anagrafeType.setPersonaFisica(perFis);
		    AnagrafeType intermediario = new AnagrafeType();
		    intermediario.setPersonaFisica(perFis);
		    dettaglioPraticaType.setIntermediario(intermediario);
		}
	    }
	}
	// ///////////////////////////////// SEZIONE  ALTRI SOGGETTI COLLEGATI ///////////////////////////////////////  
	log.debug("getRipielogoPraticaFaldoneTelematico# populate ALTRI SOGGETTI COLLEGATI... {} ", uuidPratica);
	for (int i = 0; i < compilazioneModuli.size(); i++) {
	    CompilazioneModulo compMod = compilazioneModuli.get(i);
	    List<ValoreCampo> valCamp = compMod.getValoriCampo().getValoreCampo();
	    // se il campo ruolo non esiste oppure è vuoto usiamo il titolo se c'è
	    String titolo = "";
	    String ruolo = "";
	    for (ValoreCampo valoreCampo2 : valCamp) {
		titolo = "";
		ruolo = "";
		if (StringUtils.equalsIgnoreCase(valoreCampo2.getNome(), "Referente")) {
		    AltriSoggettiType altriSoggettiType = new AltriSoggettiType();
		    AnagrafeType anagrafeType = new AnagrafeType();
		    PersonaFisicaType perFis = new PersonaFisicaType();
		    mappingFaldoneTelematicoToBOService.populatePersonaFisicaType(valoreCampo2, perFis);
		    altriSoggettiType.setTipoRapporto(new RuoloType());
		    if (perFis != null && StringUtils.isNotBlank(perFis.getCognome()) && StringUtils.isNotBlank(perFis.getNome())) {
			DatiIscrizioneAlboType datiIscrizioneAlboType = new DatiIscrizioneAlboType();
			LocalizzazioneType lo = new LocalizzazioneType();
			for (ValoreCampo campoReferente : valoreCampo2.getSubValoreCampo()) {
			    switch (FaldoneTelematicoValoreCampoEnum.fromValue(campoReferente.getNome())) {
			    case RUOLO:
				if (StringUtils.isNotBlank(campoReferente.getValore())) {
				    ruolo = campoReferente.getValore();
				    altriSoggettiType.getTipoRapporto().setRuolo(campoReferente.getValore());
				} else {
				    altriSoggettiType.getTipoRapporto().setRuolo(titolo);
				}
				altriSoggettiType.getTipoRapporto().setIdRuolo("");
				break;
			    case TITOLO:
				if (StringUtils.isNotBlank(campoReferente.getValore())) {
				    titolo = campoReferente.getValore();
				    altriSoggettiType.getTipoRapporto().setRuolo(campoReferente.getValore());
				}
				break;
			    case ATTRIBUTI_ALBO:
				CodiceDescrizioneType codiceDescrizioneType = new CodiceDescrizioneType();
				codiceDescrizioneType.setDescrizione(campoReferente.getValore());
				break;
			    case Attributi_AlNumero:
				datiIscrizioneAlboType.setNumeroIscrizione(campoReferente.getValore());
				break;
			    case ATTRIBUTI_DELLAPROVINCIA:
				datiIscrizioneAlboType.setSiglaProvincia(campoReferente.getValore());
				break;
			    case PROVINCIASTUDIO:
				lo.setProvincia(campoReferente.getValore());
				break;
			    case CAPSTUDIO:
				lo.setCap(campoReferente.getValore());
				break;
			    case CIVICOSTUDIO:
				lo.setCivico(campoReferente.getValore());
				break;
			    case COMUNESTUDIO:
				ComuneType comuneType = new ComuneType();
				comuneType.setComune(campoReferente.getValore());
				lo.setComune(comuneType);
				break;
			    case VIASTUDIO:
				lo.setIndirizzo(campoReferente.getValore());
				break;
			    case TELEFONOSTUDIO:
				perFis.setTelefono(campoReferente.getValore());
				break;
			    default:
				break;
			    }
			}
			perFis.setDatiIscrizioneAlbo(datiIscrizioneAlboType);
			perFis.setCorrispondenza(lo);
			anagrafeType.setPersonaFisica(perFis);
			altriSoggettiType.setSoggetto(anagrafeType);
			altriSoggettiType.setAnagraficaCollegata(anagrafeType);
			dettaglioPraticaType.getAltriSoggetti().add(altriSoggettiType);
		    }
		}
	    }
	}
	///////////////////////////////// SEZIONE DOCUMENTI ///////////////////////////////////////////////////////
	for (CodiceAllegato allegato : riepilogoPraticaFaldoneTelematico.getAllegatiFaldoneTelematico().getCodiceAllegati()) {
	    for (DocumentiType documentiType : documenti) {
		String filenameTelematico = allegato.getFilename();
		if (StringUtils.equals(filenameTelematico, documentiType.getAllegati().getFile().getFileName())) {
		    documentiType.setDocumento(allegato.getDescrizione());
		}
	    }
	}
	for (UrnAllegato urnAllegato : riepilogoPraticaFaldoneTelematico.getAllegatiFaldoneTelematico().getUrnAllegati()) {
	    for (DocumentiType documentiType : documenti) {
		String filenameTelematico = urnAllegato.getFilename();
		String fileNameDoc = documentiType.getDocumento();
		if (StringUtils.equals(filenameTelematico, fileNameDoc)) {
		    documentiType.setDocumento(urnAllegato.getDescrizione());
		}
	    }
	}
	log.debug("Azzero i documenti della pratica ==> {}", uuidPratica);
	dettaglioPraticaType.getDocumenti().clear();
	// ripristino i documenti istanza che hanno perso il datahandler
	for (DocumentiType documentiType : documenti) {
	    log.debug("Verifico DH Documento {}-{} ==> {}", documentiType.getId(), documentiType.getDocumento(), uuidPratica);
	    if (documentiType.getAllegati() != null && documentiType.getAllegati().getFile() != null) {
		TempFileHelper tempFileHelper = allegatiScaricati.get(TempFileHelper.getKeyFromDoc(documentiType));
		log.debug("Verifico DH Documento {} ==> {}", tempFileHelper, uuidPratica);
		if (tempFileHelper != null) {
		    log.debug("DH Sovrascritto per il Documento {} ==> {}", tempFileHelper, uuidPratica);
		    documentiType.getAllegati().getFile().setBinaryData(tempFileHelper.getDataHandler());
		}
	    }
	    dettaglioPraticaType.getDocumenti().add(documentiType);
	}
	log.debug("Sono presenti {} documenti per la pratica ==> {}", dettaglioPraticaType.getDocumenti().size(), uuidPratica);
	return dettaglioPraticaType;
    }

    @Override
    public DettaglioPraticaType getRiepilogoPraticaSUAP(List<DocumentiType> documenti, boolean popolaProcedimenti) throws Exception {

	String uuidPratica = UUID.randomUUID().toString();
	Map<String, TempFileHelper> allegatiScaricati = new HashMap<String, TempFileHelper>();
	log.debug("processo la pratica nella cartella {}", uuidPratica);
	TempFileHelper suapXML = getSuapXML(documenti, uuidPratica, allegatiScaricati);
	log.debug("File SUAP.XML {} ==> {} ", suapXML, uuidPratica);
	JAXBContext jaxbContext = JAXBContext.newInstance(RiepilogoPraticaSUAP.class);
	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	RiepilogoPraticaSUAP riepilogoPraticaSUAP = (RiepilogoPraticaSUAP) unmarshaller.unmarshal(suapXML.getDataHandler().getInputStream());
	DettaglioPraticaType dettaglioPraticaType = new DettaglioPraticaType();
	String oggettoComunicazione = null;
	if (riepilogoPraticaSUAP.getIntestazione() != null && riepilogoPraticaSUAP.getIntestazione().getOggettoComunicazione() != null) {
	    oggettoComunicazione = riepilogoPraticaSUAP.getIntestazione().getOggettoComunicazione().getValue();
	}
	dettaglioPraticaType.setOggetto(StringUtils.defaultString(oggettoComunicazione, "Nuova pratica"));
	log.debug("File SUAP.XML {} ==> {} processato", suapXML, uuidPratica);
	if (riepilogoPraticaSUAP.getIntestazione().getImpresa() != null) {
	    log.debug("getDettaglioPraticaType# populate AZIENDA RICHIEDENTE...{} ", uuidPratica);
	    PersonaGiuridicaType personaGiuridicaType = new PersonaGiuridicaType();
	    mappingElementICToBOService.populatePersonaGiuridicaType(riepilogoPraticaSUAP.getIntestazione().getImpresa(), personaGiuridicaType);
	    dettaglioPraticaType.setAziendaRichiedente(personaGiuridicaType);
	    RichiedenteType richiedenteType = new RichiedenteType();
	    mappingElementICToBOService.populateRichiedenteType(riepilogoPraticaSUAP.getIntestazione().getImpresa().getLegaleRappresentante(),
		    riepilogoPraticaSUAP.getIntestazione().getImpresa().getLegaleRappresentante().getCarica(), richiedenteType);
	    RuoloType ruoloType = new RuoloType();
	    ruoloType.setRuolo("Legale rappresentante");
	    richiedenteType.setRuolo(ruoloType);
	    dettaglioPraticaType.setRichiedente(richiedenteType);
	} else if (riepilogoPraticaSUAP.getIntestazione().getRichiedente() != null) {
	    AnagraficaPersona anagraficaPersona = riepilogoPraticaSUAP.getIntestazione().getRichiedente();
	    // ///////////////////////////////// SEZIONE  RICHIENDENTE ///////////////////////////////////////    
	    log.debug("getDettaglioPraticaType# populate RICHIEDENTE... {} ", uuidPratica); // DA FARE
	    RichiedenteType richiedenteType = new RichiedenteType();
	    mappingElementICToBOService.popolateRichiedenteType(anagraficaPersona, richiedenteType);
	    dettaglioPraticaType.setRichiedente(richiedenteType);
	}
	// ///////////////////////////////// SEZIONE  INTERMEDIARIO ///////////////////////////////////////    
	if (riepilogoPraticaSUAP.getIntestazione().getDichiarante() != null) {
	    log.debug("getDettaglioPraticaType# populate INTERMEDIARIO... {} ", uuidPratica);
	    AnagrafeType intermediario = new AnagrafeType();
	    PersonaFisicaType personaFisicaTypeIntermediario = new PersonaFisicaType();
	    mappingElementICToBOService.populatePersonaFisicaTypeByEstrimiDichiarante(riepilogoPraticaSUAP.getIntestazione().getDichiarante(),
		    personaFisicaTypeIntermediario);
	    mappingElementICToBOService.populateAnagrafeTypeFisica(personaFisicaTypeIntermediario, intermediario);
	    dettaglioPraticaType.setIntermediario(intermediario);
	}
	/////////////////////////////////// SEZIONE PROCEDIMENTI ///////////////////////////////////////////////////////
	if (popolaProcedimenti) {
	    ProcedimentoType procedimentoType = null;
	    List<AdempimentoSUAP> anAdempimentoSUAPs = riepilogoPraticaSUAP.getStruttura().getModulo();
	    for (AdempimentoSUAP adempimentoSUAP : anAdempimentoSUAPs) {
		procedimentoType = new ProcedimentoType();
		procedimentoType.setCodice(adempimentoSUAP.getCod());
		procedimentoType.setDescrizione(adempimentoSUAP.getNome());
		dettaglioPraticaType.getProcedimenti().add(procedimentoType);
	    }
	}
	////////////////////////////////////// SEZIONE COMUNE ////////////////////////////////////////////////////////
	/////////////////////////////////// SEZIONE LOCALIZZAZIONE  E COMUNE ///////////////////////////////////////////////////////
	log.debug("getDettaglioPraticaType# SEZIONI LOCALIZZAZIONE...................{} ", uuidPratica);
	if (riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo() != null) {
	    log.debug("getDettaglioPraticaType# populate LOCALIZZAZIONE...{} ", uuidPratica);
	    LocalizzazioneNelComuneType indirizzoPratica = new LocalizzazioneNelComuneType();
	    if (riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo() != null) {
		mappingElementICToBOService.populateLocalizzazioneNelComuneType(
			riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo(), indirizzoPratica);
		if (riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getDatiCatastali() != null
			&& !riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getDatiCatastali().isEmpty()) {
		    log.debug("getDettaglioPraticaType# Popolo dati catastali dell'indirizzo = {} ==> {}",
			    riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo().getDenominazioneStradale(), uuidPratica);
		    List<DatiCatastali> dataiCatastali = riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getDatiCatastali();
		    for (DatiCatastali datocatastale : dataiCatastali) {
			// popolo tutti i dati catasti, la modellazione prevede che per ogno oggetto ci sia una lista di particelle e una di sub,
			//la nostra struttura è piatta e quindi dobbiamo normalizzare e duplicare gli oggetti per tutti le particelle e sub
			List<String> mappalis = datocatastale.getMappale();
			for (String mappale : mappalis) {
			    List<String> subs = datocatastale.getSubalterno();
			    for (String sub : subs) {
				RiferimentoCatastaleType riferimentoCatastaleType = new RiferimentoCatastaleType();
				String sezione = "";
				if (datocatastale.getSezione() != null) {
				    sezione = StringUtils.defaultIfEmpty(datocatastale.getSezione().getValue(), "");
				}
				mappingElementICToBOService.populateRiferimentoCatastaleType(datocatastale.getTipo(), sezione,
					datocatastale.getFoglio(), mappale, sub, riferimentoCatastaleType);
				indirizzoPratica.getRiferimentoCatastale().add(riferimentoCatastaleType);
			    }
			}
		    }
		}
		if (riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo().getComune() != null) {
		    log.debug("getDettaglioPraticaType# populate COMUNE {} ", uuidPratica);
		    ComuneType comuneType = new ComuneType();
		    mappingElementICToBOService.populateComuneType(
			    riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo().getComune(), comuneType);
		    dettaglioPraticaType.setCodiceComune(comuneType);
		}
	    }
	    dettaglioPraticaType.getLocalizzazione().add(indirizzoPratica);
	}
	/////////////////////////////////// SEZIONE SCHEDE DINAMICHE ///////////////////////////////////////////////
	log.debug("getDettaglioPraticaType# SEZIONI SCHEDE DINAMICHE ........................{} ", uuidPratica);
	List<SchedaType> schedaTypes = new ArrayList<SchedaType>();
	List<Allegato> listAllegati = new ArrayList<Allegato>();
	for (DocumentiType documentiType : documenti) {
	    String key = TempFileHelper.getKeyFromDoc(documentiType);
	    TempFileHelper all = allegatiScaricati.get(key);
	    if (all == null) {
		log.debug("Allegato non scaricato {}, {}", key, uuidPratica);
		all = TempFileHelper.fromDoc(documentiType, uuidPratica);
		allegatiScaricati.put(all.getKey(), all);
	    }
	    log.debug("Allegato schede dinamiche {}, {}", all, uuidPratica);
	    Allegato a = new Allegato();
	    a.setEmbeddedFileRef(all.getDataHandler());
	    a.setMime(all.getMimeType());
	    a.setNomeFile(all.getNomeFile());
	    listAllegati.add(a);
	}
	log.debug("Prima di popolare le schede {}", uuidPratica);
	mappingElementICToBOService.populateSchedeType(listAllegati, schedaTypes);
	log.debug("Prima di popolare le schede popolate {}", uuidPratica);
	dettaglioPraticaType.getSchede().addAll(schedaTypes);
	String codicePratica = riepilogoPraticaSUAP.getIntestazione().getCodicePratica();
	if (StringUtils.isNotEmpty(codicePratica)) {
	    dettaglioPraticaType.setCodicePraticaTelematica(codicePratica);
	    dettaglioPraticaType.setIdPratica(codicePratica);
	    dettaglioPraticaType.setNumeroPratica(codicePratica);
	} else {
	    String date = Utilities.getToday("yyyyMMdd-HHmm");
	    String codPrat = riepilogoPraticaSUAP.getIntestazione().getImpresa().getPartitaIva() + "-" + date;
	    dettaglioPraticaType.setCodicePraticaTelematica(codPrat);
	    dettaglioPraticaType.setIdPratica(codPrat);
	    dettaglioPraticaType.setNumeroPratica(codPrat);
	}
	InterventoType interventoType = new InterventoType();
	TipoIntervento tipoIntervento = riepilogoPraticaSUAP.getIntestazione().getOggettoComunicazione().getTipoIntervento();
	interventoType.setDescrizione(tipoIntervento.value());
	dettaglioPraticaType.setIntervento(interventoType);
	dettaglioPraticaType.setDataPratica(null);
	dettaglioPraticaType.setDomicilioElettronico(riepilogoPraticaSUAP.getIntestazione().getDomicilioElettronico());
	jaxbContext = JAXBContext.newInstance(CooperazioneSUAPEnte.class);
	/////////////////////////////////// SEZIONE DOCUMENTI ///////////////////////////////////////////////////////
	for (AdempimentoSUAP adempimentoSUAP2 : riepilogoPraticaSUAP.getStruttura().getModulo()) {
	    for (AllegatoGenerico allegato : adempimentoSUAP2.getDocumentoAllegato()) {
		for (DocumentiType documentiType : documenti) {
		    if (StringUtils.equals(documentiType.getAllegati().getFile().getFileName(), allegato.getNomeFile())) {
			documentiType.setDocumento(allegato.getDescrizione());
		    }
		}
	    }
	}
	Unmarshaller unmarshaller2 = jaxbContext.createUnmarshaller();
	TempFileHelper suapEnte = findSuapEnte(documenti, uuidPratica, allegatiScaricati);
	if (suapEnte != null) {
	    log.debug("SuapEnte Scaricato {}, {}", suapEnte, uuidPratica);
	    CooperazioneSUAPEnte cooperazioneSUAPEnte = (CooperazioneSUAPEnte) unmarshaller2.unmarshal(suapEnte.getDataHandler().getInputStream());
	    if (riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo() == null
		    && cooperazioneSUAPEnte.getIntestazione().getSuapCompetente() != null) {
		String codiceCatastale = cooperazioneSUAPEnte.getIntestazione().getSuapCompetente().getCodiceCatastale();
		codiceCatastale = StringUtils.upperCase(codiceCatastale.split("_")[1]);
		ComuneType comuneType = new ComuneType();
		comuneType.setCodiceCatastale(codiceCatastale);
	    } else {
		throw new Exception("Referimento comune non trovato: (Errore: COMUNE NON TROVATO)");
	    }
	    String oggettoIstanza = cooperazioneSUAPEnte.getIntestazione().getTestoComunicazione();
	    dettaglioPraticaType.setAnnotazioni(oggettoIstanza);
	    for (AllegatoCooperazione allegatoCooperazione : cooperazioneSUAPEnte.getAllegato()) {
		for (DocumentiType documentiType : documenti) {
		    if (StringUtils.equals(documentiType.getAllegati().getFile().getFileName(), allegatoCooperazione.getNomeFile())) {
			documentiType.setDocumento(allegatoCooperazione.getDescrizione());
		    }
		}
	    }
	} else {
	    if (riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo() == null) {
		throw new Exception("Referimento comune non trovato: (Errore: COMUNE NON TROVATO)");
	    }
	}
	log.debug("Azzero i documenti della pratica ==> {}", uuidPratica);
	dettaglioPraticaType.getDocumenti().clear();
	// ripristino i documenti istanza che hanno perso il datahandler
	for (DocumentiType documentiType : documenti) {
	    log.debug("Verifico DH Documento {}-{} ==> {}", documentiType.getId(), documentiType.getDocumento(), uuidPratica);
	    if (documentiType.getAllegati() != null && documentiType.getAllegati().getFile() != null) {
		TempFileHelper tempFileHelper = allegatiScaricati.get(TempFileHelper.getKeyFromDoc(documentiType));
		log.debug("Verifico DH Documento {} ==> {}", tempFileHelper, uuidPratica);
		if (tempFileHelper != null) {
		    log.debug("DH Sovrascritto per il Documento {} ==> {}", tempFileHelper, uuidPratica);
		    documentiType.getAllegati().getFile().setBinaryData(tempFileHelper.getDataHandler());
		}
	    }
	    dettaglioPraticaType.getDocumenti().add(documentiType);
	}
	log.debug("Sono presenti {} documenti per la pratica ==> {}", dettaglioPraticaType.getDocumenti().size(), uuidPratica);
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	return dettaglioPraticaType;
    }

    @Override
    public DettaglioPraticaType getDettaglioPraticaType(List<DocumentiType> documenti, boolean popolaProcedimenti) throws Exception {

	boolean isSuap = false;
	boolean isFaldoneTelematico = false;
	boolean iseml = false;
	for (DocumentiType documentiType : documenti) {
	    String nomeDoc = documentiType.getDocumento().toLowerCase();
	    if (nomeDoc.endsWith("suap.xml")) {
		isSuap = true;
	    } else if (nomeDoc.contains("faldone telematico.xml") || nomeDoc.contains("faldone_telematico.xml")) {
		isFaldoneTelematico = true;
	    } else if (isZesDocument(documentiType)) {
		iseml = true;
	    }
	}
	if (isSuap) { // la parte SUAP ha Precedenza su Faldone telematico
	    return getRiepilogoPraticaSUAP(documenti, popolaProcedimenti);
	}
	if (isFaldoneTelematico) {
	    return getRipielogoPraticaFaldoneTelematico(documenti, popolaProcedimenti);
	}
	if (iseml) {
	    return getRiepilogoPraticaSUAPEml(documenti, popolaProcedimenti);
	}
	return null;
    }
    
    

    private TempFileHelper getSuapXML(List<DocumentiType> documenti, String folderPratica, Map<String, TempFileHelper> allegatiScaricati) {

	for (DocumentiType documentiType : documenti) {
	    String nomeDoc = documentiType.getDocumento().toLowerCase();
	    if (nomeDoc.contains("suap.xml")) {
		try {
		    String key = TempFileHelper.getKeyFromDoc(documentiType);
		    TempFileHelper suapXML = allegatiScaricati.get(key);
		    if (suapXML == null) {
			suapXML = TempFileHelper.fromDoc(documentiType, folderPratica);
			allegatiScaricati.put(key, suapXML);
		    }
		    return suapXML;
		} catch (IOException e) {
		    log.error("Errore nel recupero del file " + nomeDoc, e);
		    throw new NotFoundException("Errore nel recupero del file " + nomeDoc, e);
		}
	    }
	}
	throw new NotFoundException("Errore: file SUAP.xml non trovato");
    }

    private TempFileHelper getFaldone(List<DocumentiType> documenti, String folderPratica, Map<String, TempFileHelper> allegatiScaricati) {

	for (DocumentiType documentiType : documenti) {
	    String nomeDoc = documentiType.getDocumento().toLowerCase();
	    if (nomeDoc.contains("faldone telematico.xml") || nomeDoc.contains("faldone_telematico.xml")) {
		try {
		    String key = TempFileHelper.getKeyFromDoc(documentiType);
		    TempFileHelper faldoneXML = allegatiScaricati.get(key);
		    if (faldoneXML == null) {
			faldoneXML = TempFileHelper.fromDoc(documentiType, folderPratica);
			allegatiScaricati.put(key, faldoneXML);
		    }
		    return faldoneXML;
		} catch (IOException e) {
		    log.error("Errore nel recupero del file " + nomeDoc, e);
		    throw new NotFoundException("Errore nel recupero del file " + nomeDoc, e);
		}
		//return documentiType.getAllegati().getFile().getBinaryData();
	    }
	}
	throw new NotFoundException("Errore: file xml Faldone Telematico o ImpresaInUnGiorno non trovato");
    }

    private TempFileHelper findSuapEnte(List<DocumentiType> documenti, String folderPratica, Map<String, TempFileHelper> allegatiScaricati) {

	for (DocumentiType documentiType : documenti) {
	    String nomeDoc = documentiType.getDocumento().toLowerCase();
	    if (nomeDoc.contains("SUAPENTE.XML") || nomeDoc.contains("SUAPENTE.xml")) {
		try {
		    String key = TempFileHelper.getKeyFromDoc(documentiType);
		    TempFileHelper suapEnte = allegatiScaricati.get(key);
		    if (suapEnte == null) {
			suapEnte = TempFileHelper.fromDoc(documentiType, folderPratica);
			allegatiScaricati.put(suapEnte.getKey(), suapEnte);
		    }
		    return suapEnte;
		} catch (IOException e) {
		    log.error("Errore nel recupero del file " + nomeDoc, e);
		    throw new NotFoundException("Errore nel recupero del file " + nomeDoc, e);
		}
	    }
	}
	return null;
    }

    public static void main(String[] args) {

	String date = Utilities.getToday("yyyyMMdd-HHmm");
	System.out.println("DATE: " + date);
	//
	//	try {
	//	    DataSource fds = new FileDataSource("C:\\Users\\leroy.mbabu\\Desktop\\faldone_test.xml");
	//	    DataHandler dataHandler = new DataHandler(fds);
	//	    JAXBContext jaxbContext;
	//	    jaxbContext = JAXBContext.newInstance(RiepilogoPraticaFaldoneTelematico.class);
	//	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	//	    RiepilogoPraticaFaldoneTelematico riepilogoPraticaFaldoneTelematico = (RiepilogoPraticaFaldoneTelematico) unmarshaller
	//		    .unmarshal(dataHandler.getInputStream());
	//	    System.out.println("done---");
	//	} catch (JAXBException e) {
	//	    // TODO Auto-generated catch block
	//	    e.printStackTrace();
	//	} catch (IOException e) {
	//	    // TODO Auto-generated catch block
	//	    e.printStackTrace();
	//	}
    }

    @Override
    public DettaglioPraticaType getRiepilogoPraticaSUAPEml(List<DocumentiType> documenti, boolean popolaProcedimenti) throws Exception {

	List<DocumentiType> documentifinali = new ArrayList<DocumentiType>();
	for (DocumentiType documentiType : documenti) {
	    if (!isZesDocument(documentiType)) {
		documentifinali.add(documentiType);
		continue;
	    }
	    documentifinali.addAll(getDocumentiFromEml(documentiType));
	}
	return getRiepilogoPraticaSUAP(documentifinali, popolaProcedimenti);
    }

    @Override
    public List<DocumentiType> getDocumentiFromEml(DocumentiType eml) throws Exception {

	InputStream is1 = null;
	InputStream is2 = null;
	DocumentiType storedEml = memorizzaDocumentiType(eml);
	try {
	    javax.mail.Session session = javax.mail.Session.getDefaultInstance(new Properties(), null);
	    is1 = storedEml.getAllegati().getFile().getBinaryData().getInputStream();
	    MimeMessage msg1 = new MimeMessage(session, is1);
	    Part emlTrovato = cercaEml(msg1);
	    if (emlTrovato == null) {
		log.debug("Non risulta presente nessun file eml dentro il file eml");
		List<DocumentiType> retList = new ArrayList<DocumentiType>();
		retList.add(storedEml);
		return retList;
	    }
	    is2 = emlTrovato.getInputStream();
	    MimeMessage msg = new MimeMessage(javax.mail.Session.getDefaultInstance(new Properties(), null), is2);
	    Multipart mp = (Multipart) msg.getContent();
	    BodyPart part = mp.getBodyPart(0);
	    String html = (String) part.getContent();
	    int start = html.indexOf("ELENCO DOCUMENTI ALLEGATI"); //potrebbe essere un buon punto di inizio se la struttura rimane quella
	    if (start >= 0) {
		html = html.substring(start);
	    }
	    List<DocumentiType> documentiFromEml = new ArrayList<DocumentiType>();
	    Pattern p = Pattern.compile("([^:<]+):\\s*<a[^>]*href=\"([^\"]+)\"[^>]*>([^<]+)</a>", Pattern.CASE_INSENSITIVE);
	    Matcher m = p.matcher(html);
	    while (m.find()) {
		String descrizione = m.group(1).replace("br>", "").replace("\u2022", "").trim();
		String urlFile = m.group(2).trim();
		String nomeFile = m.group(3).trim();
		if ("null".equals(nomeFile)) {
		    nomeFile = "suap.xml";
		}
		log.debug("DESCRIZIONE = {}", descrizione);
		log.debug("FILE        = {}", nomeFile);
		log.debug("URL         = {}", urlFile);
		log.debug("--------------------------------");
		//DocumentiType buildDocumento
		URL url = new URL(urlFile);
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int len;
		while ((len = is.read(buffer)) != -1) {
		    baos.write(buffer, 0, len);
		}
		is.close();
		byte[] contenuto = baos.toByteArray();
		//			        String mimeType = conn.getContentType();
		//			         if (mimeType == null || mimeType.trim().length() == 0) {
		//			            mimeType = "application/octet-stream";
		//			         }
		String mimeType = "application/octet-stream";
		ByteArrayDataSource ds = new ByteArrayDataSource(contenuto, mimeType);
		DataHandler dataHandler = new DataHandler(ds);
		AllegatiType allegato = new AllegatiType();
		allegato.setId("ALL_" + Thread.currentThread().getId() + System.currentTimeMillis());
		allegato.setAllegato(nomeFile);
		AllegatoBinarioType binario = new AllegatoBinarioType();
		binario.setFileName(nomeFile);
		binario.setMimeType(mimeType);
		binario.setBinaryData(dataHandler);
		allegato.setFile(binario);
		MetaDatoType meta = new MetaDatoType();
		meta.setCodice("URL_ORIGINE");
		meta.setValore(urlFile);
		allegato.getMetaDati().add(meta);
		DocumentiType documento = new DocumentiType();
		documento.setId("DOC_" + Thread.currentThread().getId() + System.currentTimeMillis());
		documento.setDocumento(nomeFile);
		documento.setTipoDocumento("ALLEGATO");
		documento.setAllegati(allegato);
		documentiFromEml.add(documento);
	    }
	    if (documentiFromEml.isEmpty()) {
		log.debug("Non sono stati trovati documenti processando il file eml, dunque restituiamo il file eml");
		documentiFromEml.add(storedEml);
	    }
	    return documentiFromEml;
	} finally {
	    closeResource(is2);
	    closeResource(is1);
	}
    }
    
    private DocumentiType memorizzaDocumentiType(DocumentiType doc) throws IOException {

	byte[] contenuto = IOUtils.toByteArray(doc.getAllegati().getFile().getBinaryData().getInputStream());
	ByteArrayDataSource ds = new ByteArrayDataSource(contenuto, "application/octet-stream");
	DataHandler dataHandler = new DataHandler(ds);
	AllegatiType allegato = new AllegatiType();
	allegato.setId(doc.getAllegati().getId());
	allegato.setAllegato(doc.getAllegati().getAllegato());
	AllegatoBinarioType binario = new AllegatoBinarioType();
	binario.setFileName(doc.getAllegati().getFile().getFileName());
	binario.setMimeType("application/octet-stream");
	binario.setBinaryData(dataHandler);
	allegato.setFile(binario);
	DocumentiType documento = new DocumentiType();
	documento.setId(doc.getId());
	documento.setDocumento(doc.getDocumento());
	documento.setTipoDocumento(doc.getTipoDocumento());
	documento.setAllegati(allegato);
	return documento;
    }

    private Part cercaEml(Part part) throws Exception {

	if (part.isMimeType("message/rfc822")) {
	    return part;
	}
	Object content = part.getContent();
	if (content instanceof Multipart) {
	    Multipart mp = (Multipart) content;
	    for (int i = 0; i < mp.getCount(); i++) {
		Part trovato = cercaEml(mp.getBodyPart(i));
		if (trovato != null) {
		    return trovato;
		}
	    }
	}
	return null;
    }

    private void closeResource(InputStream is) {

	if (is == null) {
	    return;
	}
	try {
	    is.close();
	} catch (Exception e) {
	    log.error("Errore durante la chiusura is");
	}
    }

    public static boolean isZesDocument(DocumentiType documento) {

	return documento.getDocumento().toLowerCase().endsWith(".eml");
    }
}
