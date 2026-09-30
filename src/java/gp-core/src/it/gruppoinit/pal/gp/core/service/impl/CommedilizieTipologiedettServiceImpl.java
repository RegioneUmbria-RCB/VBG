package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CommedilizieTipologiedettDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedettId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologiedettService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Riccardo Bocci
 */
@Service
public class CommedilizieTipologiedettServiceImpl extends BaseServiceImpl<CommedilizieTipologiedett, CommedilizieTipologiedettId> implements
	CommedilizieTipologiedettService {

    private CommedilizieTipologiedettDAO commedilizietipologiedettDAO;

    @Autowired
    public void setCommedilizieTipologiedettDAO(CommedilizieTipologiedettDAO commedilizietipologiedettDAO) {

	this.commedilizietipologiedettDAO = commedilizietipologiedettDAO;
    }

    @Override
    protected Class<CommedilizieTipologiedett> getEntityClass() {

	return CommedilizieTipologiedett.class;
    }

    @Override
    public List<CommedilizieTipologiedett> findAll(Integer firstResult, Integer maxResult) {

	return commedilizietipologiedettDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CommedilizieTipologiedett entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    commedilizietipologiedettDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public CommedilizieTipologiedett findById(CommedilizieTipologiedettId id) {

	// §§§BEGIN§§§
	return commedilizietipologiedettDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(CommedilizieTipologiedett entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    commedilizietipologiedettDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(CommedilizieTipologiedett entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    commedilizietipologiedettDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(CommedilizieTipologiedett entity) {

	boolean delete = true;
	return delete;
    }

    @Override
    public List<CommedilizieTipologiedett> findByTipologia(CommedilizieTipologie tipologia) {

	// §§§BEGIN§§§
	return commedilizietipologiedettDAO.findByTipologia(tipologia);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<CommedilizieTipologiedett> findTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipomovimento, String.class));
	ft.addRestriction(fr);
	// BEGIN BOCCI la seguente riga serve per gli import per la procedura di distribuzione
	new ArrayList<CommedilizieTipologiedett>(0);
	// END
	return commedilizietipologiedettDAO.findByFilterTable(ft, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new ArrayList<CommedilizieTipologiedett>();@@@ENDALTERNATIVEEXIT@@@
    }
}
