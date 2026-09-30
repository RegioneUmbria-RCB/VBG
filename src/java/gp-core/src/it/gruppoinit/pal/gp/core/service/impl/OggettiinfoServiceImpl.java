/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OggettiinfoDAO;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.Oggettiinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.OggettiinfoService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class OggettiinfoServiceImpl extends BaseServiceImpl<Oggettiinfo, PkId> implements OggettiinfoService {

    @Autowired
    private OggettiinfoDAO oggettiinfoDAO;
    @Autowired
    private OggettiService oggettiService;

    public void setOggettiinfoDAO(OggettiinfoDAO oggettiinfoDAO) {

	this.oggettiinfoDAO = oggettiinfoDAO;
    }

    @Override
    protected Class<Oggettiinfo> getEntityClass() {

	return Oggettiinfo.class;
    }

    @Override
    public void delete(Oggettiinfo entity) {

	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, true);
	oggettiinfoDAO.delete(entity);
	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }

    @Override
    public List<Oggettiinfo> findAll(Integer firstResult, Integer maxResult) {

	return oggettiinfoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Oggettiinfo findById(PkId id) {

	return oggettiinfoDAO.findById(id);
    }

    @Override
    public void insert(Oggettiinfo entity) {

	if (validateEntity(entity)) {
	    //Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, false);
	    oggettiinfoDAO.insert(entity);
	    //	    if (codiceOggettoDaCancellare != null) {
	    //		Oggetti oggettoDaCancellare = oggettiDAO.findById(new PkId(codiceOggettoDaCancellare));
	    //		oggettiDAO.delete(oggettoDaCancellare);
	    //	    }
	}
    }

    @Override
    public void update(Oggettiinfo entity) {

	if (validateEntity(entity)) {
	    oggettiinfoDAO.update(entity);
	}
    }

    @Override
    public List<Oggettiinfo> findByDescrizioneAndTipologia(String descrizione, Integer tipologia) {

	return oggettiinfoDAO.findByDescrizioneAndTipologia(descrizione, tipologia);
    }

    @Override
    public void insertOggettiLibreria(Oggettiinfo oggettiinfo, byte[] fileContent, String fileName) {

	if (isInsertUpdateAllowed(oggettiinfo, fileContent, fileName)) {
	    if (validateEntity(oggettiinfo)) {
		Oggetti oggetto = new Oggetti();
		oggetto.setNomefile(fileName);
		oggetto.setOggetto(fileContent);
		oggettiService.insert(oggetto);
		oggettiinfo.getId().setCodice(oggetto.getId().getCodice());
		oggettiinfo.setOggetto(oggetto);
		oggettiinfo.setNomefile(oggetto.getNomefile());
		this.insert(oggettiinfo);
	    }
	}
    }

    @Override
    public void updateOggettiLibreria(Oggettiinfo oggettiinfo, byte[] fileContent, String fileName) {

	if (validateEntity(oggettiinfo)) {
	    if (fileContent != null) {
		if (fileContent.length > 0) {
		    if (StringUtils.isNotBlank(fileName)) {
			Oggetti oggetto = oggettiService.findById(new PkId(oggettiinfo.getId().getCodice()));
			oggetto.setNomefile(fileName);
			oggetto.setOggetto(fileContent);
			oggettiService.update(oggetto);
		    }
		}
	    }
	    this.update(oggettiinfo);
	}
    }

    protected Integer controllaCancellaOggetti(Oggettiinfo entity, boolean isDelete) {

	Integer codiceOggetto = null;
	Integer codiceOggettoOld = null;
	if (!(null == entity)) {
	    if (!(null == entity.getId())) {
		if (!(null == entity.getId().getCodice())) {
		    codiceOggetto = entity.getId().getCodice();
		}
	    }
	}
	if (isDelete) {
	    // sono in cancellazione
	    if (!(null == codiceOggetto)) {
		// if (oggettiDAO.controllaCancellaOggetto("OGGETTIINFO", "CODICEOGGETTO", codiceOggetto)) {
		return codiceOggetto;
		// }
	    }
	} else {
	    // sono in modifica / insert
	    // vedo se posso cancellare il vecchio oggetto
	    Oggettiinfo entityCopy = this.findById(entity.getId());
	    if (null != entityCopy) {
		// se non è nullo allora sono in modifica
		// in inserimento non devo fare il controllo
		// recupero il vecchio id
		if (!(null == entityCopy)) {
		    if (!(null == entityCopy.getId())) {
			if (!(null == entityCopy.getId().getCodice())) {
			    codiceOggettoOld = entityCopy.getId().getCodice();
			}
		    }
		}
		if (null != codiceOggettoOld) {
		    if (!codiceOggettoOld.equals(codiceOggetto)) {
			// cancello solo se sono diversi altrimenti no
			//if (oggettiDAO.controllaCancellaOggetto("OGGETTIINFO", "CODICEOGGETTO", codiceOggettoOld)) {
			return codiceOggettoOld;
			//}
		    }
		}
	    }
	}
	return null;
    }

    protected boolean isInsertUpdateAllowed(Oggettiinfo entity, byte[] content, String fName) {

	boolean isAllowed = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// controlla che il range di mq iniziale non sia maggiore di quello finale
	if (content == null || StringUtils.isBlank(fName)) {
	    _ivs.add(new InvalidValue("service_error.oggetto_non_presente", entity.getClass(), "oggetto.id.codice", null, entity));
	    this.throwValidationMessages(_ivs);
	}
	return isAllowed;
    }
}
