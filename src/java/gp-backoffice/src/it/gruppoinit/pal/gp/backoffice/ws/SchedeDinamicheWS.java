package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.definitions.schededinamiche.SchedeDinamiche;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.Dyn2BasecontestiType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.Dyn2BasetipitestoType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.Dyn2CampiScriptType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.Dyn2CampiType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.Dyn2CampiproprietaType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.Dyn2ModelliTScriptType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.Dyn2ModellidType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.Dyn2ModellidtestiType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.Dyn2ModellitType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.ModellitFindRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.ModellitFindResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.ModellitInsertRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.ModellitInsertResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.StrutturaModelloTFindRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche.StrutturaModelloTFindResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basecontesti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Basetipitesto;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiproprietaId;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScript;
import it.gruppoinit.pal.gp.core.domain.Dyn2ModelliScriptId;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellidtesti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2BasecontestiService;
import it.gruppoinit.pal.gp.core.service.Dyn2BasetipitestoService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiproprietaService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModelliScriptService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidtestiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebService;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 * PER I WEB SERVICE SONO STATI CREATI OGGETTI AD-HOC CHE REPLICANO GLI OGGETTI DEL DB (SI è DECISO COME PER GLI ALTRI
 * WEB DI NON COLLEGARLI AGLI OGGETTI DI DOMAIN).
 * 
 * 	1. METODI DI MAPPING PER CONVERTIRE DOMAIN (DYN2MODELLIT,DYN2MODELLID,DYN2CAMPI, ETC..) IN OGGETTI DEL WS 
 * 	2. METODI DI POPULATE PER CONVERTIRE OGGETTI DEL WS (DYN2MODELLIT,DYN2MODELLID,DYN2CAMPI, ETC..) IN DOMAIN
 * 
 * ATTENZIONE I METODI MAPPING E POPULATE NON FANNO NESSUNA INSERT, MA TRAVASONO SOLO LE PROPRITA' DA UN OGGETTO ALL'ALTRO
 * SI è RESO NECESSARIO RICONVERTIRE I GLI OGGETTI DEL WS NEGLI OGGETTI DI DOMAIN IN FASE DI INSERT PERCHE' SI E' DOVUTO
 * SPOSATRE TUTTE LE INSERT ALL'INTERNO DI UN SERVICE PER MATENERE LA TRANSAZIONALITA'. 
 * 
 * NEI SERVICE (SONO SUL CORE) NON AVEVO ACCESSO AGLI OGGETTI DEL WS (SONO SUL BACKOFFICE) ECCO PERCHE' SI E' RESO NECESSARIO
 * RICONVERTIRLI IN OGGETTI DOMAIN 
 * 
 * TUTTE LE INSERT SONO DEMANDATE AL METODO "dyn2ModellitService.insertDaWSSchede(dyn2Modellit, dyn2Modellids)"
 * 
 * @author gianpaolot
 * </pre>
 *
 */
@WebService(serviceName = "SchedeDinamicheService", portName = "SchedeDinamicheSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/schedeDinamiche", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.schededinamiche.SchedeDinamiche")
//@WebService(serviceName = "OggettiService", portName = "OggettiSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/oggetti", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.oggetti.Oggetti")
public class SchedeDinamicheWS extends BaseWS implements SchedeDinamiche {

    private static final Logger log = LoggerFactory.getLogger(SchedeDinamicheWS.class);
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private Dyn2ModellidService dyn2ModellidService;
    @Autowired
    private Dyn2CampiproprietaService dyn2CampiproprietaService;
    @Autowired
    private Dyn2BasecontestiService dyn2BasecontestiService;
    @Autowired
    private Dyn2ModellidtestiService dyn2ModellidtestiService;
    @Autowired
    private Dyn2BasetipitestoService dyn2BasetipitestoService;
    @Autowired
    private Dyn2ModelliScriptService dyn2ModelliScriptService;
    @Autowired
    private SoftwareService softwareService;

    @Override
    public ModellitFindResponse getModelliT(ModellitFindRequest modellitFindRequest) {

	log.debug("getModelliT# call getModelliT.... Parametri {}/{}", modellitFindRequest.getCodiceScheda(), modellitFindRequest.getSoftware());
	setORMHelper(modellitFindRequest.getSoftware(), modellitFindRequest.getToken());
	ModellitFindResponse modellitFindResponse = null;
	Dyn2Modellit dyn2Modellit = dyn2ModellitService.findByCodiceScheda(modellitFindRequest.getCodiceScheda());
	if (EntityUtils.getNestedProperty(dyn2Modellit, "id.codice") != null) {
	    log.debug("getModelliT# Popolo sezione informazioni modelli T....");
	    modellitFindResponse = new ModellitFindResponse();
	    modellitFindResponse.setCodiceScheda(dyn2Modellit.getCodiceScheda());
	    modellitFindResponse.setDescrizioneScheda(dyn2Modellit.getDescrizione());
	    modellitFindResponse.setIdScheda(dyn2Modellit.getId().getCodice().toString());
	    modellitFindResponse.setSoftware(dyn2Modellit.getSoftware().getCodice());
	    log.debug("getModelliT# Popolo sezione informazioni dyn2 campi....");
	    List<Dyn2Campi> campi = dyn2CampiService.findByIdModello(dyn2Modellit.getId().getCodice());
	    Dyn2CampiType campiType = null;
	    for (Dyn2Campi dyn2Campi : campi) {
		campiType = new Dyn2CampiType();
		campiType.setEtichetta(dyn2Campi.getEtichetta());
		campiType.setNomeCampo(dyn2Campi.getNomecampo());
		campiType.setObbligatorio(BooleanUtils.toBoolean(dyn2Campi.getObbligatorio()));
		campiType.setSoftware(dyn2Campi.getSoftware().getCodice());
		campiType.setIdCampo(dyn2Campi.getId().getCodice().toString());
		modellitFindResponse.getDyn2CampiType().add(campiType);
	    }
	}
	log.debug("getModelliT# end call getModelliT.... ");
	return modellitFindResponse;
    }

    @Override
    public ModellitInsertResponse createModelliT(ModellitInsertRequest modellitInsertRequest) {

	ModellitInsertResponse modellitInsertResponse = new ModellitInsertResponse();
	String sw = modellitInsertRequest.getSoftware();
	log.info("getModelliT# call createModelliT.... parametri modello t del MASTER {}/{}", modellitInsertRequest.getDyn2ModellitType()
		.getCodiceScheda(), sw);
	/////////////////////////////////////////////////////////////////////////////
	modellitInsertRequest.getDyn2ModellitType().setSoftware(sw);
	////////////////////////////////////////////////////////////////////////////
	setORMHelper(sw, modellitInsertRequest.getToken());
	modellitInsertResponse.setCodiceScheda(modellitInsertRequest.getDyn2ModellitType().getCodiceScheda());
	log.debug("createModelliT# Prepare Dyn2Modellit e List dyn2Modellid per inserimento nuova scheda");
	log.debug("createModelliT# populateDyn2Modellit....");
	Dyn2Modellit dyn2Modellit = populateDyn2Modellit(modellitInsertRequest.getDyn2ModellitType(), sw);
	//	log.debug("createModelliT# insert Dyn2Modellit.....");
	//dyn2ModellitService.insert(dyn2Modellit);
	//modellitInsertResponse.setIdModelloT(dyn2Modellit.getId().getCodice().toString());
	log.debug("createModelliT# populate lista Dyn2Modellid....");
	List<Dyn2ModellidType> modellidTypes = modellitInsertRequest.getDyn2ModellitType().getDyn2ModellidType();
	List<Dyn2Modellid> dyn2Modellids = new ArrayList<Dyn2Modellid>();
	for (Dyn2ModellidType dyn2ModellidType : modellidTypes) {
	    log.debug("createModelliT# populateDyn2Modellit. Codice Master: {}", dyn2ModellidType.getIdModelloD());
	    Dyn2Modellid dyn2Modellid = populateDyn2Modellid(dyn2ModellidType);
	    dyn2Modellid.setDyn2Modellit(dyn2Modellit);
	    //dyn2ModellidService.insert(dyn2Modellid);
	    log.debug("createModelliT# populate dyn2Campi o dyn2Modellidtesti");
	    if (dyn2ModellidType.getDyn2CampiType() != null) {
		log.debug("createModelliT# populate dyn2Campi, nome campo {}", dyn2ModellidType.getDyn2CampiType().getNomeCampo());
		Dyn2Campi dyn2Campi = dyn2CampiService.findByNomeCampo(dyn2ModellidType.getDyn2CampiType().getNomeCampo());
		if (EntityUtils.getNestedProperty(dyn2Campi, "id.codice") != null) {
		    log.info(
			    "createModelliT# Durante la creazione del modello si sta utilizzando un campo già presente(Nome campo {}).Verranno utilizzate "
				    + "tutte le proprietà del campo trovato. Eventuali differenze con quello della consolle verranno perse.",
			    dyn2Campi.getNomecampo());
		    // deve essere messo perchè viene usato in fase di inserimento (già presente per la gestione java delle schede)
		    dyn2Modellid.setTipocampoTransient(WebConstants.CAMPO_DINAMICO);
		    dyn2Modellid.setDyn2Campi(dyn2Campi);
		} else {
		    Dyn2Campi dyn2Campinew = populateDyn2Campi(dyn2ModellidType.getDyn2CampiType(), sw);
		    // deve essere messo perchè viene usato in fase di inserimento (già presente per la gestione java delle schede)
		    dyn2Modellid.setTipocampoTransient(WebConstants.CAMPO_DINAMICO);
		    // dyn2CampiService.insert(dyn2Campinew);
		    dyn2Modellid.setDyn2Campi(dyn2Campinew);
		}
	    } else if (dyn2ModellidType.getDyn2ModellidtestiType() != null) {
		log.debug("createModelliT# populate and insert dyn2Modellidtesti. Codice Master Dyn2Modellidtesti", dyn2ModellidType
			.getDyn2ModellidtestiType().getIdModellidtesti());
		Dyn2Modellidtesti dyn2Modellidtesti = populateDyn2Modellidtesti(dyn2ModellidType.getDyn2ModellidtestiType());
		// deve essere messo perchè viene usato in fase di inserimento (già presente per la gestione java delle schede)
		dyn2Modellid.setTipocampoTransient(WebConstants.CAMPO_TESTO);
		//dyn2ModellidtestiService.insert(dyn2Modellidtesti);
		dyn2Modellid.setDyn2Modellidtesti(dyn2Modellidtesti);
	    }
	    log.debug("createModelliT# Aggiorno dyn2Modellid con il nuovo dyn2Campi o dyn2Modellidtesti");
	    dyn2Modellids.add(dyn2Modellid);
	    //dyn2ModellidService.update(dyn2Modellid);
	}
	List<Dyn2ModelliTScriptType> dyn2ModelliTScriptTypes = modellitInsertRequest.getDyn2ModellitType().getDyn2ModelliTScriptType();
	for (Dyn2ModelliTScriptType dyn2ModelliTScriptType : dyn2ModelliTScriptTypes) {
	    //TODO da implementare il passaggio dei dati, ad oggi non necessario
	    log.warn("createModelliT# passaggio script (formule) non previsto");
	}
	log.debug("createModelliT# Start Call dyn2ModellitService.insertDaWSSchede.......");
	// Necessario per avere tutti gli insert in un solo service per mantenere la transazionalità in caso di errore durante i vari inserimenti
	dyn2ModellitService.insertDaWSSchede(dyn2Modellit, dyn2Modellids);
	log.debug("createModelliT# End Call dyn2ModellitService.insertDaWSSchede.......");
	modellitInsertResponse.setIdModelloT(dyn2Modellit.getId().getCodice().toString());
	log.info("getModelliT# end call createModelliT.... parametri modello t del MASTER {}/{}", modellitInsertRequest.getDyn2ModellitType()
		.getCodiceScheda(), modellitInsertRequest.getDyn2ModellitType().getSoftware());
	return modellitInsertResponse;
    }

    @Override
    public StrutturaModelloTFindResponse getStrutturaModelloT(StrutturaModelloTFindRequest strutturaModelloTFindRequest) {

	log.debug("getStrutturaModelloT# call getStrutturaModelloT.... Parametri {}/{}", strutturaModelloTFindRequest.getCodiceScheda(),
		strutturaModelloTFindRequest.getSoftware());
	StrutturaModelloTFindResponse strutturaModelloTFindResponse = null;
	setORMHelper(strutturaModelloTFindRequest.getSoftware(), strutturaModelloTFindRequest.getToken());
	Dyn2Modellit dyn2Modellit = dyn2ModellitService.findByCodiceScheda(strutturaModelloTFindRequest.getCodiceScheda());
	if (EntityUtils.getNestedProperty(dyn2Modellit, "id.codice") != null) {
	    log.debug("getStrutturaModelloT# Popolo sezione informazioni modelli T....");
	    strutturaModelloTFindResponse = new StrutturaModelloTFindResponse();
	    strutturaModelloTFindResponse.setCodiceScheda(strutturaModelloTFindRequest.getCodiceScheda());
	    strutturaModelloTFindResponse.setSoftware(strutturaModelloTFindRequest.getSoftware());
	    log.debug("getStrutturaModelloT# applico le logiche di conversione da Dyn2Modellit a Dyn2ModellitType");
	    Dyn2ModellitType dyn2ModellitType = mappingDyn2ModellitT(dyn2Modellit);
	    strutturaModelloTFindResponse.setDyn2ModellitType(dyn2ModellitType);
	}
	return strutturaModelloTFindResponse;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////// METODI PRIVATI/////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////// METODI DI POPULATE PER CONVERTIRE OGGETTI DEL WS (DYN2MODELLIT,DYN2MODELLID,DYN2CAMPI, ETC..) IN DOMAIN  ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Dyn2Campi populateDyn2Campi(Dyn2CampiType dyn2CampiType, String codSoftware) {

	log.debug("populateDyn2Campi# start....");
	Dyn2Campi dyn2Campi = new Dyn2Campi();
	dyn2Campi.setNomecampo(dyn2CampiType.getNomeCampo());
	dyn2Campi.setEtichetta(StringUtils.defaultIfEmpty(dyn2CampiType.getEtichetta(), ""));
	dyn2Campi.setDescrizione(StringUtils.defaultIfEmpty(dyn2CampiType.getDescrizione(), ""));
	dyn2Campi.setTipodato(StringUtils.defaultIfEmpty(dyn2CampiType.getTipodato(), ""));
	dyn2Campi.setObbligatorio(BooleanUtils.toBoolean(dyn2CampiType.isObbligatorio()));
	Software sw = softwareService.findById(codSoftware);
	dyn2Campi.setSoftware(sw);
	if (dyn2CampiType.getDyn2BasecontestiType() != null
		&& StringUtils.isNotBlank(dyn2CampiType.getDyn2BasecontestiType().getIdDyn2Basecontesti())) {
	    log.debug("populateDyn2Campi# Gestione del BasecontestiType...");
	    Dyn2Basecontesti dyn2Basecontesti = dyn2BasecontestiService.findById(dyn2CampiType.getDyn2BasecontestiType().getIdDyn2Basecontesti());
	    if (dyn2Basecontesti != null && StringUtils.isNotBlank(dyn2Basecontesti.getId())) {
		log.info(
			"populateDyn2Campi# Durante la creazione del campo dinamico si sta utilizzando un Dyn2Basecontesti(id {}).Verranno utilizzate "
				+ "tutte le informazioni del record trovato. Eventuali differenze con quello della consolle verranno perse.",
			dyn2Basecontesti.getId());
		dyn2Campi.setDyn2Basecontesti(dyn2Basecontesti);
	    } else {
		log.debug(
			"populateDyn2Campi# populate Dyn2Basecontesti (valori non presneti sul db backoffice, durante la procedura di inserimento modello verranno inseriti. "
				+ "Codice Master Dyn2Basecontesti", dyn2CampiType.getDyn2BasecontestiType().getIdDyn2Basecontesti());
		Dyn2Basecontesti dyn2BasecontestiNew = new Dyn2Basecontesti();
		dyn2BasecontestiNew.setId(dyn2CampiType.getDyn2BasecontestiType().getIdDyn2Basecontesti());
		dyn2BasecontestiNew.setContesto(StringUtils.defaultIfEmpty(dyn2CampiType.getDyn2BasecontestiType().getContesto(), ""));
		//dyn2BasecontestiService.insert(dyn2BasecontestiNew);
		dyn2Campi.setDyn2Basecontesti(dyn2BasecontestiNew);
	    }
	}
	log.debug("populateDyn2Campi# Insert dyn2Campi.....");
	//dyn2CampiService.insert(dyn2Campi);
	log.debug("populateDyn2Campi# Insert dyn2Campi avvenuto.....");
	log.debug("populateDyn2Campi# Gestione delle Dyn2CampiproprietaType di Dyn2Campi...");
	List<Dyn2CampiproprietaType> dyn2CampiproprietaTypes = dyn2CampiType.getDyn2Campiproprieta();
	//Integer idcampoInserito = dyn2Campi.getId().getCodice();
	for (Dyn2CampiproprietaType dyn2CampiproprietaType : dyn2CampiproprietaTypes) {
	    Dyn2Campiproprieta dyn2CampiproprietaNew = new Dyn2Campiproprieta();
	    Dyn2CampiproprietaId idNew = new Dyn2CampiproprietaId();
	    idNew.setProprieta(dyn2CampiproprietaType.getProprieta());
	    //idNew.setFkD2cId(idcampoInserito);
	    dyn2CampiproprietaNew.setId(idNew);
	    if (StringUtils.isNotBlank(dyn2CampiproprietaType.getValore())) {
		dyn2CampiproprietaNew.setValore(dyn2CampiproprietaType.getValore());
	    }
	    //dyn2CampiproprietaService.insert(dyn2CampiproprietaNew);
	    //dyn2CampiproprietaNew.setDyn2Campi(dyn2Campi);
	    dyn2Campi.getDyn2Campiproprietas().add(dyn2CampiproprietaNew);
	}
	//log.debug("populateDyn2Campi# Gestione delle formule del campo...");
	List<Dyn2CampiScriptType> dyn2CampiScriptTypes = dyn2CampiType.getDyn2CampiScriptType();
	for (Dyn2CampiScriptType dyn2CampiScriptType : dyn2CampiScriptTypes) {
	    log.warn("populateDyn2Campi# Gestione degli script (formule) per i dyn2Campi non gestita ");
	}
	log.debug("populateDyn2Campi# end....");
	return dyn2Campi;
    }

    private Dyn2Modellit populateDyn2Modellit(Dyn2ModellitType dyn2ModellitType, String codSoftware) {

	log.debug("populateDyn2Modellit# start....");
	Dyn2Modellit dyn2Modellit = new Dyn2Modellit();
	dyn2Modellit.setCodiceScheda(dyn2ModellitType.getCodiceScheda());
	dyn2Modellit.setDescrizione(dyn2ModellitType.getDescrizione());
	dyn2Modellit.setFlgReadonlyWeb(BooleanUtils.toBoolean(dyn2ModellitType.isFlgReadonlyWeb()));
	dyn2Modellit.setFlgStoricizza(BooleanUtils.toBoolean(dyn2ModellitType.isFlgStoricizza()));
	dyn2Modellit.setModelloFrontoffice(BooleanUtils.toBoolean(dyn2ModellitType.isModelloFrontoffice()));
	dyn2Modellit.setModellomultiplo(BooleanUtils.toBoolean(dyn2ModellitType.isModellomultiplo()));
	Software sw = softwareService.findById(codSoftware);
	dyn2Modellit.setSoftware(sw);
	if (dyn2ModellitType.getDyn2BasecontestiType() != null
		&& StringUtils.isNotBlank(dyn2ModellitType.getDyn2BasecontestiType().getIdDyn2Basecontesti())) {
	    log.debug("populateDyn2Modellit# Gestione del BasecontestiType...");
	    Dyn2Basecontesti dyn2Basecontesti = dyn2BasecontestiService.findById(dyn2ModellitType.getDyn2BasecontestiType().getIdDyn2Basecontesti());
	    if (dyn2Basecontesti != null && StringUtils.isNotBlank(dyn2Basecontesti.getId())) {
		log.info(
			"populateDyn2Modellit# Durante la creazione di Dyn2Modellit si sta utilizzando un Dyn2Basecontesti(id {}) trovato nel DB. Verranno utilizzate "
				+ "tutte le informazioni del record trovato. Eventuali differenze con quello della consolle verranno perse.",
			dyn2Basecontesti.getId());
		dyn2Modellit.setBasecontesti(dyn2Basecontesti);
	    } else {
		log.debug(
			"populateDyn2Modellit# populate Dyn2Basecontesti (valori non presneti sul db del backoffice, durante la procedura di inserimento modello verranno inseriti. "
				+ "Codice Master Dyn2Basecontesti", dyn2ModellitType.getDyn2BasecontestiType().getIdDyn2Basecontesti());
		Dyn2Basecontesti dyn2BasecontestiNew = new Dyn2Basecontesti();
		dyn2BasecontestiNew.setId(dyn2ModellitType.getDyn2BasecontestiType().getIdDyn2Basecontesti());
		dyn2BasecontestiNew.setContesto(StringUtils.defaultIfEmpty(dyn2ModellitType.getDyn2BasecontestiType().getContesto(), ""));
		//dyn2BasecontestiService.insert(dyn2BasecontestiNew);
		dyn2Modellit.setBasecontesti(dyn2BasecontestiNew);
	    }
	    List<Dyn2ModelliTScriptType> modelliScripts = dyn2ModellitType.getDyn2ModelliTScriptType();
	    Dyn2ModelliScript dyn2ModelliScript = null;
	    for (Dyn2ModelliTScriptType dyn2ModelliTScriptType : modelliScripts) {
		dyn2ModelliScript = new Dyn2ModelliScript();
		Dyn2ModelliScriptId id = new Dyn2ModelliScriptId();
		id.setEvento(dyn2ModelliTScriptType.getEvento());
		dyn2ModelliScript.setId(id);
		if (dyn2ModelliTScriptType.getBinaryData() != null) {
		    byte[] b = Utilities.dataHandlerToBytes(dyn2ModelliTScriptType.getBinaryData());
		    dyn2ModelliScript.setScript(b);
		}
		dyn2Modellit.get_transietDyn2ModelliScripts().add(dyn2ModelliScript);
	    }
	}
	log.debug("populateDyn2Modellit# end....");
	return dyn2Modellit;
    }

    private Dyn2Modellid populateDyn2Modellid(Dyn2ModellidType dyn2ModellidType) {

	log.debug("populateDyn2Modellit# start....");
	Dyn2Modellid dyn2Modellid = new Dyn2Modellid();
	if (dyn2ModellidType.getPosverticale() != null) {
	    dyn2Modellid.setPosverticale(dyn2ModellidType.getPosverticale().intValue());
	}
	if (dyn2ModellidType.getPosorizzontale() != null) {
	    dyn2Modellid.setPosorizzontale(dyn2ModellidType.getPosorizzontale().intValue());
	}
	dyn2Modellid.setFlgMultiplo(BooleanUtils.toBoolean(dyn2ModellidType.isFlgMultiplo()));
	dyn2Modellid.setFlgObbligatorio(BooleanUtils.toBoolean(dyn2ModellidType.isFlgObbligatorio()));
	log.debug("populateDyn2Modellit# end....");
	return dyn2Modellid;
    }

    /**
     * Popola Dyn2Modellidtesti, per popolare Dyn2Modellidtesti.Dyn2Basetipitesto prima fa una ricerca per id
     * dyn2ModellidtestiType.Dyn2BasetipitestoType.IdDyn2Basetipitesto 1. Lo trova, usa quello trovato 2. Non lo trova,
     * ne inserisce uno e lo setta a Dyn2Modellidtesti
     * 
     * @param dyn2ModellidtestiType
     * @return
     */
    private Dyn2Modellidtesti populateDyn2Modellidtesti(Dyn2ModellidtestiType dyn2ModellidtestiType) {

	Dyn2Modellidtesti dyn2Modellidtesti = new Dyn2Modellidtesti();
	dyn2Modellidtesti.setTesto(StringUtils.defaultIfEmpty(dyn2ModellidtestiType.getTesto(), ""));
	if (dyn2ModellidtestiType.getDyn2BasetipitestoType() != null
		&& StringUtils.isNotBlank(dyn2ModellidtestiType.getDyn2BasetipitestoType().getIdDyn2Basetipitesto())) {
	    Dyn2Basetipitesto dyn2Basetipitesto = dyn2BasetipitestoService.findById(dyn2ModellidtestiType.getDyn2BasetipitestoType()
		    .getIdDyn2Basetipitesto());
	    if (dyn2Basetipitesto != null && StringUtils.isNotBlank(dyn2Basetipitesto.getId())) {
		dyn2Modellidtesti.setDyn2Basetipitesto(dyn2Basetipitesto);
	    } else {
		log.warn("populateDyn2Modellidtesti#Non trovato nell'installazione il Dyn2Basetipitesto con codice {}.", dyn2ModellidtestiType
			.getDyn2BasetipitestoType().getIdDyn2Basetipitesto());
		log.warn("populateDyn2Modellidtesti# Aggiungo una nuova riga in Dyn2Basetipitesto con id ", dyn2ModellidtestiType
			.getDyn2BasetipitestoType().getIdDyn2Basetipitesto());
		Dyn2Basetipitesto dyn2BasetipitestoNew = new Dyn2Basetipitesto();
		dyn2BasetipitestoNew.setId(dyn2ModellidtestiType.getDyn2BasetipitestoType().getIdDyn2Basetipitesto());
		dyn2BasetipitestoNew.setTipotesto(StringUtils.defaultIfEmpty(dyn2ModellidtestiType.getDyn2BasetipitestoType().getTipotesto(), ""));
		dyn2BasetipitestoService.insert(dyn2BasetipitestoNew);
		dyn2Modellidtesti.setDyn2Basetipitesto(dyn2Basetipitesto);
	    }
	}
	return dyn2Modellidtesti;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////// METODI PRIVATI/////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////// METODI DI MAPPING PER CONVERTIRE DOMAIN (DYN2MODELLIT,DYN2MODELLID,DYN2CAMPI, ETC..) IN OGGETTI DEL WS /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Dyn2ModellitType mappingDyn2ModellitT(Dyn2Modellit dyn2Modellit) {

	log.debug("mappingDyn2ModellitT# mapping informazioni di Dyn2Modellit in Dyn2ModellitType");
	Dyn2ModellitType dyn2ModellitType = new Dyn2ModellitType();
	// Proprità singole di Dyn2Modellit
	dyn2ModellitType.setCodiceScheda(dyn2Modellit.getCodiceScheda());
	dyn2ModellitType.setDescrizione(dyn2Modellit.getDescrizione());
	dyn2ModellitType.setFlgReadonlyWeb(BooleanUtils.toBoolean(dyn2Modellit.getFlgReadonlyWeb()));
	dyn2ModellitType.setFlgStoricizza(BooleanUtils.toBoolean(dyn2Modellit.getFlgStoricizza()));
	dyn2ModellitType.setIdModelloT(dyn2Modellit.getId().getCodice().toString());
	dyn2ModellitType.setModelloFrontoffice(BooleanUtils.toBoolean(dyn2Modellit.getModelloFrontoffice()));
	dyn2ModellitType.setModellomultiplo(BooleanUtils.toBoolean(dyn2Modellit.getModellomultiplo()));
	dyn2ModellitType.setSoftware(dyn2Modellit.getSoftware().getCodice());
	Dyn2BasecontestiType dyn2BasecontestiType = null;
	if (dyn2Modellit.getBasecontesti() != null && StringUtils.isNotBlank(dyn2Modellit.getBasecontesti().getId())) {
	    Dyn2Basecontesti basecontesti = dyn2BasecontestiService.findById(dyn2Modellit.getBasecontesti().getId());
	    if (basecontesti != null && StringUtils.isNotBlank(basecontesti.getId())) {
		dyn2BasecontestiType = new Dyn2BasecontestiType();
		dyn2BasecontestiType.setContesto(basecontesti.getContesto());
		dyn2BasecontestiType.setIdDyn2Basecontesti(basecontesti.getId());
		dyn2ModellitType.setDyn2BasecontestiType(dyn2BasecontestiType);
	    }
	}
	log.debug("mappingDyn2ModellitT# populate Dyn2ModelliScript (formule)");
	List<Dyn2ModelliScript> dyn2ModelliScripts = dyn2ModelliScriptService.findByModello(dyn2Modellit.getId().getCodice());
	for (Dyn2ModelliScript dyn2ModelliScript : dyn2ModelliScripts) {
	    Dyn2ModelliTScriptType dyn2ModelliTScriptType = mappingDyn2ModelliTScript(dyn2ModelliScript);
	    if (dyn2ModelliTScriptType != null) {
		dyn2ModellitType.getDyn2ModelliTScriptType().add(dyn2ModelliTScriptType);
	    }
	}
	Dyn2ModellidType dyn2ModellidType = null;
	log.debug("mappingDyn2ModellitT# popolo sezione modelli d");
	List<Dyn2Modellid> dyn2Modellids = dyn2ModellidService.findByModelloT(dyn2Modellit);
	for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
	    dyn2ModellidType = mappingDyn2ModellitD(dyn2Modellid);
	    if (dyn2ModellidType != null) {
		dyn2ModellitType.getDyn2ModellidType().add(dyn2ModellidType);
	    }
	}
	// 
	return dyn2ModellitType;
    }

    private Dyn2ModelliTScriptType mappingDyn2ModelliTScript(Dyn2ModelliScript dyn2ModelliScript) {

	Dyn2ModelliTScriptType dyn2ModelliTScriptType = null;
	if (dyn2ModelliScript != null && dyn2ModelliScript.getId() != null && dyn2ModelliScript.getId().getFkD2mtId() != null
		&& StringUtils.isNotBlank(dyn2ModelliScript.getId().getEvento())) {
	    dyn2ModelliTScriptType = new Dyn2ModelliTScriptType();
	    if (dyn2ModelliScript.getScript() != null) {
		DataHandler dataHandler = Utilities.bytesToDataHandler(dyn2ModelliScript.getScript());
		dyn2ModelliTScriptType.setBinaryData(dataHandler);
	    }
	    dyn2ModelliTScriptType.setEvento(dyn2ModelliScript.getId().getEvento());
	}
	return dyn2ModelliTScriptType;
    }

    private Dyn2ModellidType mappingDyn2ModellitD(Dyn2Modellid dyn2Modellid) {

	log.debug("mappingDyn2ModellitD# mapping informazioni di Dyn2Modellid in Dyn2ModellidType. parametri:  codice master {}", dyn2Modellid
		.getId().getCodice());
	Dyn2ModellidType dyn2ModellidType = new Dyn2ModellidType();
	dyn2ModellidType.setIdModelloD(dyn2Modellid.getId().getCodice().toString());
	if (dyn2Modellid.getPosorizzontale() != null) {
	    dyn2ModellidType.setPosorizzontale(new BigInteger(String.valueOf(dyn2Modellid.getPosorizzontale())));
	}
	if (dyn2Modellid.getPosverticale() != null) {
	    dyn2ModellidType.setPosverticale(new BigInteger(String.valueOf(dyn2Modellid.getPosverticale())));
	}
	dyn2ModellidType.setFlgMultiplo(BooleanUtils.toBoolean(dyn2Modellid.getFlgMultiplo()));
	dyn2ModellidType.setFlgObbligatorio(BooleanUtils.toBoolean(dyn2Modellid.getFlgObbligatorio()));
	if (EntityUtils.getNestedProperty(dyn2Modellid.getDyn2Campi(), "id.codice") != null) {
	    Dyn2Campi _dyn2Campi = dyn2CampiService.findById(new PkId(dyn2Modellid.getDyn2Campi().getId().getCodice()));
	    log.debug("mappingDyn2ModellitD# popolo Dyn2Campi del modello D. Descrizione {}", _dyn2Campi.getDescrizione());
	    Dyn2CampiType dyn2CampiType = mappingDyn2Campi(_dyn2Campi);
	    dyn2ModellidType.setDyn2CampiType(dyn2CampiType);
	}
	if (EntityUtils.getNestedProperty(dyn2Modellid.getDyn2Modellidtesti(), "id.codice") != null) {
	    Dyn2Modellidtesti _dyn2Modellidtesti = dyn2ModellidtestiService
		    .findById(new PkId(dyn2Modellid.getDyn2Modellidtesti().getId().getCodice()));
	    log.debug("mappingDyn2ModellitD# popolo Dyn2Modellidtesti del modello D. Codice master {}", _dyn2Modellidtesti.getId().getCodice());
	    Dyn2ModellidtestiType dyn2ModellidtestiType = mappingDyn2ModellidtestiType(_dyn2Modellidtesti);
	    dyn2ModellidType.setDyn2ModellidtestiType(dyn2ModellidtestiType);
	}
	return dyn2ModellidType;
    }

    private Dyn2CampiType mappingDyn2Campi(Dyn2Campi dyn2Campi) {

	log.debug("mappingDyn2ModellitD# mapping informazioni di Dyn2Campi in Dyn2CampiType. parametri:  codice master {}", dyn2Campi.getId()
		.getCodice());
	Dyn2CampiType dyn2CampiType = new Dyn2CampiType();
	dyn2CampiType.setEtichetta(StringUtils.defaultIfEmpty(dyn2Campi.getEtichetta(), ""));
	dyn2CampiType.setIdCampo(dyn2Campi.getId().getCodice().toString());
	dyn2CampiType.setNomeCampo(StringUtils.defaultIfEmpty(dyn2Campi.getNomecampo(), ""));
	dyn2CampiType.setObbligatorio(BooleanUtils.toBoolean(dyn2Campi.getObbligatorio()));
	dyn2CampiType.setSoftware(dyn2Campi.getSoftware().getCodice());
	dyn2CampiType.setTipodato(StringUtils.defaultIfEmpty(dyn2Campi.getTipodato(), ""));
	dyn2CampiType.setDescrizione(StringUtils.defaultIfEmpty(dyn2Campi.getDescrizione(), ""));
	////
	if (dyn2Campi.getDyn2Basecontesti() != null && StringUtils.isNotBlank(dyn2Campi.getDyn2Basecontesti().getId())) {
	    log.debug("mappingDyn2Campi# popolo sezione Dyn2Basecontesti");
	    Dyn2BasecontestiType dyn2BasecontestiType = new Dyn2BasecontestiType();
	    Dyn2Basecontesti _dyn2Basecontesti = dyn2BasecontestiService.findById(dyn2Campi.getDyn2Basecontesti().getId());
	    dyn2BasecontestiType.setIdDyn2Basecontesti(_dyn2Basecontesti.getId());
	    dyn2BasecontestiType.setContesto(_dyn2Basecontesti.getContesto());
	    dyn2CampiType.setDyn2BasecontestiType(dyn2BasecontestiType);
	}
	///////////////////////////////////////
	/// non gestito ////////////////////////
	//dyn2CampiType.getDyn2CampiScriptType();
	///////////////////////////////////////////
	Dyn2CampiproprietaType dyn2CampiproprietaType = null;
	List<Dyn2Campiproprieta> dyn2Campiproprietas = dyn2CampiproprietaService.findByDyn2Campi(dyn2Campi.getId().getCodice());
	//Set<Dyn2Campiproprieta> dyn2Campiproprietas = dyn2Campi.getDyn2Campiproprietas();---
	log.debug("mappingDyn2Campi# popolo sezione Dyn2Campiproprieta");
	for (Dyn2Campiproprieta dyn2Campiproprieta : dyn2Campiproprietas) {
	    dyn2CampiproprietaType = new Dyn2CampiproprietaType();
	    dyn2CampiproprietaType.setProprieta(dyn2Campiproprieta.getId().getProprieta());
	    dyn2CampiproprietaType.setValore(StringUtils.defaultIfEmpty(dyn2Campiproprieta.getValore(), ""));
	    dyn2CampiType.getDyn2Campiproprieta().add(dyn2CampiproprietaType);
	}
	return dyn2CampiType;
    }

    private Dyn2ModellidtestiType mappingDyn2ModellidtestiType(Dyn2Modellidtesti dyn2Modellidtesti) {

	log.debug("mappingDyn2ModellitD# mapping Dyn2Modellidtesti in Dyn2ModellidtestiType. Codice master {}", dyn2Modellidtesti.getId().getCodice());
	Dyn2ModellidtestiType dyn2ModellidtestiType = new Dyn2ModellidtestiType();
	dyn2ModellidtestiType.setTesto(StringUtils.defaultIfEmpty(dyn2Modellidtesti.getTesto(), ""));
	dyn2ModellidtestiType.setIdModellidtesti(dyn2Modellidtesti.getId().getCodice().toString());
	Dyn2Basetipitesto _dyn2Basetipitesto = dyn2BasetipitestoService.findById(dyn2Modellidtesti.getDyn2Basetipitesto().getId());
	Dyn2BasetipitestoType dyn2BasetipitestoType = new Dyn2BasetipitestoType();
	dyn2BasetipitestoType.setIdDyn2Basetipitesto(_dyn2Basetipitesto.getId());
	dyn2BasetipitestoType.setTipotesto(_dyn2Basetipitesto.getTipotesto());
	dyn2ModellidtestiType.setDyn2BasetipitestoType(dyn2BasetipitestoType);
	return dyn2ModellidtestiType;
    }
}
