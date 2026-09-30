package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomandeOggettiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggettiId;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoDomandeOggettiService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class FoDomandeOggettiServiceImpl extends BaseServiceImpl<FoDomandeOggetti, FoDomandeOggettiId> implements FoDomandeOggettiService {

    private static final Logger log = LoggerFactory.getLogger(FoDomandeOggettiServiceImpl.class);
    private FoDomandeOggettiDAO fodomandeoggettiDAO;
    private OggettiService oggettiService;
    private FoDomandeService foDomandeService;

    @Autowired
    public void setFoDomandeService(FoDomandeService foDomandeService) {

	this.foDomandeService = foDomandeService;
    }

    @Autowired
    public void setFoDomandeOggettiDAO(FoDomandeOggettiDAO fodomandeoggettiDAO) {

	this.fodomandeoggettiDAO = fodomandeoggettiDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<FoDomandeOggetti> getEntityClass() {

	return FoDomandeOggetti.class;
    }

    @Override
    public List<FoDomandeOggetti> findAll(Integer firstResult, Integer maxResult) {

	return fodomandeoggettiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoDomandeOggetti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    fodomandeoggettiDAO.insert(entity);
	}
    }

    private void dataIntegration(FoDomandeOggetti entity) {

	if (entity != null) {
	    if (StringUtils.isBlank(entity.getTipoFile())) {
		entity.setTipoFile(TIPO_FILE.ALLEGATO.toString());
	    }
	}
    }

    @Override
    public FoDomandeOggetti findById(FoDomandeOggettiId id) {

	return fodomandeoggettiDAO.findById(id);
    }

    @Override
    public void update(FoDomandeOggetti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    fodomandeoggettiDAO.update(entity);
	}
    }

    @Override
    public void delete(FoDomandeOggetti entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    fodomandeoggettiDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		oggettiService.segnaDaCancellare(codiceOggettoDaCancellare);	
		//		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		//		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(FoDomandeOggetti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<FoDomandeOggetti> findByIdDomandaFo(String idComuneDomanda, Integer idDomandaFo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idComuneDomanda, String.class));
	fr.addFilterField(FilterUtils.equals("id.iddomanda", idDomandaFo, Integer.class));
	ft.addRestriction(fr);
	return fodomandeoggettiDAO.findByFilterTable(ft);
    }

    @Override
    public List<FoDomandeOggetti> findByOggetto(String idComuneOgg, Integer codiceOgg){
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idComuneOgg, String.class));
	fr.addFilterField(FilterUtils.equals("id.codiceoggetto", codiceOgg, Integer.class));
	ft.addRestriction(fr);
	return fodomandeoggettiDAO.findByFilterTable(ft);
    }
    
    @Override
    public FoDomandeOggetti findAllegatoZip(String idcomune, Integer idDomandaFo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("id.iddomanda", idDomandaFo, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipoFile", TIPO_FILE.ZIP_DOMANDA.toString(), String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codiceoggetto"));// l'ultimo salvato
	List<FoDomandeOggetti> os = fodomandeoggettiDAO.findByFilterTable(ft, 0, 2);
	if (os != null) {
	    if (os.size() > 0) {
		return os.get(0);
	    }
	}
	return null;
    }

    @Override
    public FoDomandeOggetti findAllegatoRicevuta(String idcomune, Integer idDomandaFo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("id.iddomanda", idDomandaFo, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipoFile", TIPO_FILE.RICEVUTA.toString(), String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("id.codiceoggetto"));// l'ultimo salvato
	List<FoDomandeOggetti> os = fodomandeoggettiDAO.findByFilterTable(ft, 0, 2);
	if (os != null) {
	    if (os.size() > 0) {
		return os.get(0);
	    }
	}
	return null;
    }

    @Override
    public FoDomandeOggetti insertOrUpdateRicevuta(String idcomune, Integer idDomandaFo, File ricevuta) throws IOException {

	return insertUpdateTipoFile(ricevuta, idDomandaFo, TIPO_FILE.RICEVUTA, idcomune);
    }

    private FoDomandeOggetti insertUpdateTipoFile(File f, Integer idDomandaFo, TIPO_FILE tipoFile, String idcomune) throws IOException {

	FoDomandeOggetti fileObj = null;
	if (tipoFile == null) {
	    return fileObj;
	}
	String nomeFile = "";
	switch (tipoFile) {
	case RICEVUTA:
	    nomeFile = "ricevuta_domanda.pdf";
	    fileObj = this.findAllegatoRicevuta(idcomune, idDomandaFo);
	    break;
	case ZIP_DOMANDA:
	    nomeFile = "allegati_domanda.zip";
	    fileObj = this.findAllegatoZip(idcomune, idDomandaFo);
	    break;
	default:
	    return fileObj;
	}
	if (fileObj == null) {
	    FoDomande fo = foDomandeService.findById(new PkId(idcomune, idDomandaFo));
	    // inserisco un nuovo oggetto
	    Oggetti o = new Oggetti();
	    o.getId().setIdcomune(idcomune);
	    InputStream is = new FileInputStream(f);
	    byte[] file = AttachmentsUtils.readBytesFromStream(is);
	    o.setOggetto(file);
	    o.setNomefile(nomeFile);
	    oggettiService.insert(o);
	    fileObj = new FoDomandeOggetti();
	    FoDomandeOggettiId id = new FoDomandeOggettiId();
	    id.setIdcomune(idcomune);
	    id.setIddomanda(idDomandaFo);
	    id.setCodiceoggetto(o.getId().getCodice());
	    fileObj.setId(id);
	    fileObj.setOggetti(o);
	    fileObj.setTipoFile(tipoFile.toString());
	    fileObj.setFoDomande(fo);
	    this.insert(fileObj);
	} else {
	    // aggiorno l'oggetto già presente
	    Integer codiceoggetto = fileObj.getId().getCodiceoggetto();
	    if (codiceoggetto != null) {
		Oggetti entity = oggettiService.findById(new PkId(idcomune, codiceoggetto));
		InputStream is = new FileInputStream(f);
		byte[] file = AttachmentsUtils.readBytesFromStream(is);
		entity.setOggetto(file);
		oggettiService.update(entity);
	    }
	}
	return fileObj;
    }

    @Override
    public void insertOrUpdateZipfile(String idcomune, Integer idDomandaFo, File zipFile) throws IOException {

	insertUpdateTipoFile(zipFile, idDomandaFo, TIPO_FILE.ZIP_DOMANDA, idcomune);
    }
}
