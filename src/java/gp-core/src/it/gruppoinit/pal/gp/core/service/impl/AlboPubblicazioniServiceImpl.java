package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlboPubblicazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlboPretorioFilter;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioniAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniAllegatiService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlboPubblicazioniServiceImpl extends BaseServiceImpl<AlboPubblicazioni, PkId> implements AlboPubblicazioniService {

    private AlboPubblicazioniDAO alboPubblicazioniDAO;
    private AlboPubblicazioniAllegatiService alboPubblicazioniAllegatiService;

    @Autowired
    public void setAlboPubblicazioniAllegatiService(AlboPubblicazioniAllegatiService alboPubblicazioniAllegatiService) {

	this.alboPubblicazioniAllegatiService = alboPubblicazioniAllegatiService;
    }

    @Autowired
    public void setAlboPubblicazioniDAO(AlboPubblicazioniDAO alboPubblicazioniDAO) {

	this.alboPubblicazioniDAO = alboPubblicazioniDAO;
    }

    @Override
    protected Class<AlboPubblicazioni> getEntityClass() {

	return AlboPubblicazioni.class;
    }

    @Override
    public void delete(AlboPubblicazioni entity) {

	// §§§BEGIN§§§
	// controlla se ci sono allegati collegati ed eventualmente li cancella uno ad uno
	if (!entity.getAlboPubblicazioniAllegatis().isEmpty()) {
	    Iterator<AlboPubblicazioniAllegati> it = entity.getAlboPubblicazioniAllegatis().iterator();
	    while (it.hasNext()) {
		AlboPubblicazioniAllegati alboPubblicazioniAllegati = (AlboPubblicazioniAllegati) it.next();
		alboPubblicazioniAllegatiService.delete(alboPubblicazioniAllegati);
	    }
	}
	alboPubblicazioniDAO.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<AlboPubblicazioni> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return alboPubblicazioniDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public AlboPubblicazioni findById(PkId id) {

	// §§§BEGIN§§§
	return alboPubblicazioniDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(AlboPubblicazioni entity) {

	// §§§BEGIN§§§
	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getValidaDal() != null && entity.getValidaDal().after(entity.getValidaAl())) {
	    _ivs.add(new InvalidValue("errors.date.sequenza", entity.getClass(), "validaAl", "", entity));
	    insert = false;
	}
	if (!insert) {
	    this.throwValidationMessages(_ivs);
	}
	if (validateEntity(entity)) {
	    alboPubblicazioniDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(AlboPubblicazioni entity) {

	// §§§BEGIN§§§
	boolean update = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getValidaDal() != null && entity.getValidaDal().after(entity.getValidaAl())) {
	    _ivs.add(new InvalidValue("errors.date.sequenza", entity.getClass(), "validaAl", "", entity));
	    update = false;
	}
	if (!update) {
	    this.throwValidationMessages(_ivs);
	}
	if (validateEntity(entity)) {
	    alboPubblicazioniDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<AlboPubblicazioni> findAllMaxResult(AlboPretorioFilter alboPretorioFilter) {

	// §§§BEGIN§§§
	List<AlboPubblicazioni> alboPubblicazionis = alboPubblicazioniDAO.findAll(alboPretorioFilter.getDa(), alboPretorioFilter.getMaxresult());
	List<AlboPubblicazioni> listResult = new ArrayList<AlboPubblicazioni>();
	if (alboPretorioFilter.getDa() < alboPretorioFilter.getA()) {
	    int length = alboPretorioFilter.getA() - alboPretorioFilter.getDa();
	    for (int i = 0; i < length; i++) {
		listResult.add(alboPubblicazionis.get(i));
	    }
	}
	return listResult;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<AlboPubblicazioni> findAllFilter(AlboPretorioFilter alboPretorioFilter) {

	// §§§BEGIN§§§
	return alboPubblicazioniDAO.findAllFilter(alboPretorioFilter);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<AlboPubblicazioni> findPublicazioniValideAL(AlboPretorioFilter alboPretorioFilter) {

	// §§§BEGIN§§§
	return alboPubblicazioniDAO.findPublicazioniValideAL(alboPretorioFilter);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<AlboPubblicazioni> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return alboPubblicazioniDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }
}
