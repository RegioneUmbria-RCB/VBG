package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipigraduatorietDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipigraduatorietService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipigraduatorietServiceImpl extends BaseServiceImpl<Tipigraduatoriet, PkId> implements TipigraduatorietService {

    private TipigraduatorietDAO tipigraduatorietDAO;

    @Autowired
    public void setTipigraduatorietDAO(TipigraduatorietDAO tipigraduatorietDAO) {

	this.tipigraduatorietDAO = tipigraduatorietDAO;
    }

    @Override
    public void delete(Tipigraduatoriet entity) {

	// §§§BEGIN§§§
	tipigraduatorietDAO.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<Tipigraduatoriet> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return tipigraduatorietDAO.findAll(null, null);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Tipigraduatoriet findById(PkId id) {

	// §§§BEGIN§§§
	return tipigraduatorietDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Tipigraduatoriet entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipigraduatorietDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(Tipigraduatoriet entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipigraduatorietDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<Tipigraduatoriet> findByTipibando(Tipigraduatoriet tipigraduatoriet) {

	// §§§BEGIN§§§
	return tipigraduatorietDAO.findByTipibando(tipigraduatoriet);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void dataIntegration(Tipigraduatoriet entity) {

	if (entity == null) {
	    throw new RuntimeException("Non si può inserire/aggiornare tipo graduatoria t nulla");
	}
	if (entity.getFlagEsprArtTemp() == null) {
	    entity.setFlagEsprArtTemp(Boolean.FALSE);
	}
	if (entity.getFlagPianorotazione() == null) {
	    entity.setFlagPianorotazione(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected Class<Tipigraduatoriet> getEntityClass() {

	return Tipigraduatoriet.class;
    }

    @Override
    public List<Tipigraduatoriet> findByTipibando(Tipibando tipibando) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", tipibando.getId().getCodice(), "tipibando", Integer.class));
	ft.addRestriction(fr);
	return tipigraduatorietDAO.findByFilterTable(ft);
    }
}
