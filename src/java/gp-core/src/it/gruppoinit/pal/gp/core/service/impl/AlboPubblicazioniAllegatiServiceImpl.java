package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlboPubblicazioniAllegatiDAO;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioniAllegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniAllegatiService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlboPubblicazioniAllegatiServiceImpl extends BaseServiceImpl<AlboPubblicazioniAllegati, PkId> implements
	AlboPubblicazioniAllegatiService {

    private AlboPubblicazioniAllegatiDAO alboPubblicazioniAllegatiDAO;
    private OggettiService oggettiService;
    private AlboPubblicazioniService alboPubblicazioniService;

    @Autowired
    public void setAlboPubblicazioniService(AlboPubblicazioniService alboPubblicazioniService) {

	this.alboPubblicazioniService = alboPubblicazioniService;
    }

    @Autowired
    public void setAlboPubblicazioniAllegatiDAO(AlboPubblicazioniAllegatiDAO alboPubblicazioniAllegatiDAO) {

	this.alboPubblicazioniAllegatiDAO = alboPubblicazioniAllegatiDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<AlboPubblicazioniAllegati> getEntityClass() {

	return AlboPubblicazioniAllegati.class;
    }

    @Override
    public void delete(AlboPubblicazioniAllegati entity) {

	// §§§BEGIN§§§
	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	alboPubblicazioniAllegatiDAO.delete(entity);
	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
	// §§§END§§§
    }

    @Override
    public List<AlboPubblicazioniAllegati> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return alboPubblicazioniAllegatiDAO.findAll(null, null);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public AlboPubblicazioniAllegati findById(PkId id) {

	// §§§BEGIN§§§
	return alboPubblicazioniAllegatiDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(AlboPubblicazioniAllegati entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    alboPubblicazioniAllegatiDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
	// §§§END§§§
    }

    @Override
    public void update(AlboPubblicazioniAllegati entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    alboPubblicazioniAllegatiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
	// §§§END§§§
    }

    //    private Integer controllaCancellaOggetti(AlboPubblicazioniAllegati entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (EntityUtils.getNestedProperty(entity.getOggetti(), "id.codice") != null) {
    //	    codiceOggetto = entity.getOggetti().getId().getCodice();
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("ALBO_PUBBLICAZIONI_ALLEGATI", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    AlboPubblicazioniAllegati entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// recupero il vecchio id
    //		if (EntityUtils.getNestedProperty(entityCopy.getOggetti(), "id.codice") != null) {
    //		    codiceOggettoOld = entityCopy.getOggetti().getId().getCodice();
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("ALBO_PUBBLICAZIONI_ALLEGATI", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    @Override
    public Oggetti findByPubblicazioneEOggetti(int codiceoggetto, int codicepubblicazione) {

	// §§§BEGIN§§§
	AlboPubblicazioni alboPubblicazioni = new AlboPubblicazioni();
	if (codicepubblicazione != 0) {
	    alboPubblicazioni = alboPubblicazioniService.findById(new PkId(codicepubblicazione));
	}
	Oggetti oggetti = new Oggetti();
	if (codiceoggetto != 0) {
	    oggetti = oggettiService.findById(new PkId(codiceoggetto));
	}
	return alboPubblicazioniAllegatiDAO.findByPubblicazioneEOggetti(oggetti, alboPubblicazioni);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<AlboPubblicazioniAllegati> findOrderByOrdine(AlboPubblicazioni alboPubblicazioni) {

	// §§§BEGIN§§§
	return alboPubblicazioniAllegatiDAO.findOrderByOrdine(alboPubblicazioni);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
