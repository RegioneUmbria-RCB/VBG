package it.gruppoinit.pal.gp.core.features.oggetti.metadati;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.MovimentiDAO;
import it.gruppoinit.pal.gp.core.dao.OggettiMetadatiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.MetadatiFunzioneEnum;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class OggettiMetadatiServiceImpl extends BaseServiceImpl<OggettiMetadati, OggettiMetadatiId> implements OggettiMetadatiService {

    private static Logger log = LoggerFactory.getLogger(OggettiMetadatiServiceImpl.class);
    private OggettiMetadatiDAO oggettiMetadatiDAO;
    private IstanzeDAO istanzeDAO;
    private MovimentiDAO movimentiDAO;
    private OggettiService oggettiService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setIstanzeDAO(IstanzeDAO istanzeDAO) {

	this.istanzeDAO = istanzeDAO;
    }

    @Autowired
    public void setMovimentiDAO(MovimentiDAO movimentiDAO) {

	this.movimentiDAO = movimentiDAO;
    }

    @Autowired
    public void setOggettiMetadatiDAO(OggettiMetadatiDAO oggettiMetadatiDAO) {

	this.oggettiMetadatiDAO = oggettiMetadatiDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    public void insert(OggettiMetadati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    oggettiMetadatiDAO.insert(entity);
	}
    }

    private void dataIntegration(OggettiMetadati entity) {

	if (StringUtils.isNotBlank(entity.getValore())) {
	    entity.setValore(StringUtils.right(entity.getValore(), 4000));
	}
    }

    @Override
    public void update(OggettiMetadati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    oggettiMetadatiDAO.update(entity);
	}
    }

    @Override
    public void delete(OggettiMetadati entity) {

	if (isDeleteAllowed(entity)) {
	    oggettiMetadatiDAO.delete(entity);
	}
    }

    @Override
    public List<OggettiMetadati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public OggettiMetadati findById(OggettiMetadatiId id) {

	return oggettiMetadatiDAO.findById(id);
    }

    @Override
    protected Class<OggettiMetadati> getEntityClass() {

	return OggettiMetadati.class;
    }

    @Override
    public void deleteByOggetto(Integer codiceOggetto) {

	oggettiMetadatiDAO.deleteByOggetto(codiceOggetto);
    }

    @Override
    public void insertMetadatiPerOggetto(Integer codiceOggetto, List<MetadatiBean> metadati) {

	log.debug("insertMetadatiPerOggetto# entro nel metodo {}", codiceOggetto);
	if (codiceOggetto == null) {
	    log.warn("insertMetadatiPerOggetto# parametro codice oggetto = null non faccio niente");
	    return;
	}
	for (ChiaveValoreBean<String, String> md : metadati) {
	    if (md != null && StringUtils.isNotBlank(md.getChiave()) && StringUtils.isNotBlank(md.getValore())) {
		oggettiMetadatiDAO.flush(); // FIX 2024042310000163 2024042410000036 Duplicate entry 'L424-5892835-INITMD_MOV_DATA' for key 'PRIMARY'
					    // l'errore sembra derivare dal fatto che il DB non vede l'inserimento del metadato e quando va a fare il flush sul DB la chiave
					    // idcomune, codiceoggetto, chiave risulta effettivamente duplicata. senza flush il controllo findById!=null probabilmente non viene processato 
		if (checkmdfunzione(md.getChiave())) {
		    insertMetadatiPerFunzione(codiceOggetto, md.getChiave(), md.getValore());
		} else {
		    if (log.isDebugEnabled()) {
			log.debug("insertMetadatiPerOggetto# codiceoggetto {}: inserisco aggiorno il metadato con chiave: {}, valore: {}",
				new Object[] { codiceOggetto, md.getChiave(), md.getValore() });
		    }
		    OggettiMetadatiId id = new OggettiMetadatiId(codiceOggetto, md.getChiave());
		    OggettiMetadati o = this.findById(id);
		    if (o != null) {
			if (!StringUtils.defaultString(o.getValore()).equals(StringUtils.defaultString(md.getValore()))) {
			    log.debug("insertMetadatiPerOggetto# aggiorno il valore da [{}] a [{}]", o.getValore(), md.getValore());
			    o.setValore(md.getValore());
			    this.update(o);
			}
		    } else {
			log.debug("insertMetadatiPerOggetto# inserisco il nuovo metadato {}, {}", codiceOggetto, md.getChiave());
			o = new OggettiMetadati();
			o.setId(new OggettiMetadatiId(codiceOggetto, md.getChiave()));
			o.setValore(md.getValore());
			this.insert(o);
		    }
		}
	    }
	}
	oggettiService.aggiornaMetadatiCMIS(codiceOggetto);
    }

    private void insertMetadatiPerFunzione(Integer codiceOggetto, String chiave, String valore) {

	MetadatiFunzioneEnum fx = MetadatiFunzioneEnum.fromValue(chiave);
	if (fx != null) {
	    switch (fx) {
	    case CREA_MD_ISTANZA:
		insertMetadatiPerIstanza(codiceOggetto, valore);
		break;
	    case CREA_MD_MOVIMENTO:
		insertMetadatiPerMovimento(codiceOggetto, valore);
		break;
	    case CREA_MD_ENDO:
		insertMetadatiPerEndo(codiceOggetto, valore);
		break;
	    case CREA_MD_ARCHIVI:
		break;
	    default:
		break;
	    }
	}
    }

    private void insertMetadatiPerEndo(Integer codiceOggetto, String codiceEndoString) {

	if (Utilities.isInteger(codiceEndoString)) {
	    log.debug("Metadati per endo non codificati {}", codiceOggetto);
	}
    }

    private void insertMetadatiPerMovimento(Integer codiceOggetto, String codiceMovimentoString) {

	if (Utilities.isInteger(codiceMovimentoString)) {
	    Integer codiceMovimento = Integer.parseInt(codiceMovimentoString);
	    insertListMetadato(codiceOggetto, movimentiDAO.findMetadatiMovimento(codiceMovimento));
	}
    }

    private void insertMetadatiPerIstanza(Integer codiceOggetto, String codiceIstanzaString) {

	if (Utilities.isInteger(codiceIstanzaString)) {
	    Integer codiceIstanza = Integer.parseInt(codiceIstanzaString);
	    // findmetadati istanza
	    insertListMetadato(codiceOggetto, istanzeDAO.findMetadatiIstanza(codiceIstanza));
	}
    }

    private static boolean checkmdfunzione(String chiave) {

	if (StringUtils.isBlank(chiave)) {
	    return false;
	}
	MetadatiFunzioneEnum exists = MetadatiFunzioneEnum.fromValue(chiave);
	return exists != null;
    }

    private void insertListMetadato(Integer codiceOggetto, List<CodiceDescrizioneBean> mds) {

	for (CodiceDescrizioneBean cdb : mds) {
	    String chiave = cdb.getCodice();
	    String valore = cdb.getDescrizione();
	    if (StringUtils.isNotBlank(chiave) && StringUtils.isNotBlank(valore)) {
		OggettiMetadatiId id = new OggettiMetadatiId(codiceOggetto, chiave);
		OggettiMetadati o = oggettiMetadatiDAO.findById(id);
		if (o == null) {
		    // inserisco solamente se non trovato e non aggiorno se presente mantengo il dato già presente
		    o = new OggettiMetadati();
		    o.setId(new OggettiMetadatiId(codiceOggetto, chiave));
		    o.setValore(valore);
		    this.insert(o);
		}
	    }
	}
    }

    @Override
    public List<OggettiMetadati> findByOggetto(Integer codiceOggetto) {

	if (codiceOggetto == null) {
	    return new ArrayList<OggettiMetadati>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceoggetto", codiceOggetto, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.chiave"));
	return oggettiMetadatiDAO.findByFilterTable(ft);
    }

    @Override
    public void insertInNewTransaction(Integer codiceOggetto, String chiave, String valore) {

	oggettiMetadatiDAO.insertInNewTransaction(codiceOggetto, chiave, valore);
    }

    @Override
    public void updateInNewTransaction(Integer codiceOggetto, String chiave, String valore) {

	oggettiMetadatiDAO.updateInNewTransaction(codiceOggetto, chiave, valore);
    }

    @Override
    public Integer findByChiaveEValore(String chiave, String valore) {

	if (chiave == null) {
	    throw new RuntimeException("codice chiave nulla");
	}
	if (valore == null) {
	    throw new RuntimeException(chiave + " nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("valore", valore, String.class));
	fr.addFilterField(FilterUtils.equals("id.chiave", chiave, String.class));
	ft.addRestriction(fr);
	List<OggettiMetadati> omds = oggettiMetadatiDAO.findByFilterTable(ft, 0, 3);
	if (omds.size() > 1) {
	    throw new RuntimeException("Sono stati trovati più oggetti per la chiave " + chiave + " e valore " + valore);
	} else {
	    if (omds.size() == 1) {
		return omds.get(0).getId().getCodiceoggetto();
	    }
	}
	return null;
    }

    @Override
    public List<OggettiMetadati> findByOggetto(Integer codiceOggetto, String chiave) {

	if (codiceOggetto == null) {
	    return new ArrayList<OggettiMetadati>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceoggetto", codiceOggetto, Integer.class));
	fr.addFilterField(FilterUtils.equals("id.chiave", chiave, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.chiave"));
	return oggettiMetadatiDAO.findByFilterTable(ft);
    }

    @Override
    public String calcolaMd5(Integer codiceOggetto) {

	String md5Val = "";
	Oggetti o = oggettiService.findById(new PkId(codiceOggetto));
	if (o != null) {
	    InputStream is = new ByteArrayInputStream(o.getOggetto());
	    if (log.isDebugEnabled()) {
		log.debug("calcolaMd5# InpuntStream recuperato {}, calcolo MD5", (is == null ? Boolean.FALSE : Boolean.TRUE));
	    }
	    if (is == null) {
		throw new RuntimeException("Non è stato possibile estrarre il contenuto del file");
	    }
	    try {
		md5Val = DigestUtils.md5Hex(is);
	    } catch (IOException e) {
		log.error("calcolaMd5# Errore nel calcolo del checksum MD5 per l'oggetto {}, {}", codiceOggetto, e);
	    }
	    if (log.isDebugEnabled()) {
		log.debug("calcolaMd5# md5 calcolato del file {}", md5Val);
	    }
	}
	return md5Val;
    }

    @Override
    public boolean isOggettoFirmatoDigitalmente(Integer codiceOggetto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceoggetto", codiceOggetto, Integer.class));
	fr.addFilterField(FilterUtils.equals("id.chiave", "FIRMA_DIGITALE_PRESENTE", String.class));
	ft.addRestriction(fr);
	List<OggettiMetadati> metadati = oggettiMetadatiDAO.findByFilterTable(ft);
	if (metadati.isEmpty()) {
	    return false;
	}
	return metadati.get(0).getValore().equals("S");
    }

    @Override
    public String getMessaggioModificaMetadato(Integer codiceoggetto, String metadato, String valore) {

	return "#MODIFICA_METADATO_OGGETTO# L'operatore " + //
		userSecurityService.getCurrentlyAuthenticatedUserDetails() + //
		" ha modificato il metadato " +
		metadato +
		" dell'oggetto con codice " + //
		"[" +
		ORMHelper.getIdcomune() +
		", " +
		codiceoggetto +
		"]" + //
		" assegnando il valore " +
		valore;
    }

    @Override
    public String getUIDFromCodiceOggetto(Integer codiceOggetto) {

	OggettiMetadatiId id = new OggettiMetadatiId(codiceOggetto, UID);
	OggettiMetadati metadato = this.findById(id);
	if (metadato == null) {
	    return null;
	}
	return metadato.getValore();
    }

    @Override
    public void rimuoviConservazioneSospesa(Integer codiceOggetto) {

	OggettiMetadatiId id = new OggettiMetadatiId();
	id.setChiave(WebConstants.METADATO_CONSERVAZIONE_DOC_SOSPESA);
	id.setCodiceoggetto(codiceOggetto);
	id.setIdcomune(ORMHelper.getIdcomune());
	OggettiMetadati om = this.findById(id);
	if (om != null) {
	    om.setValore(WebConstants.NO);
	    this.update(om);
	}
    }
}
