/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipibandooutputDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipibandooutputService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class TipibandooutputServiceImpl extends BaseServiceImpl<Tipibandooutput, PkId> implements TipibandooutputService {

    private TipibandooutputDAO tipibandooutputDAO;

    @Autowired
    public void setTipibandooutputDAO(TipibandooutputDAO tipibandooutputDAO) {

	this.tipibandooutputDAO = tipibandooutputDAO;
    }

    @Override
    protected Class<Tipibandooutput> getEntityClass() {

	return Tipibandooutput.class;
    }

    @Override
    public void delete(Tipibandooutput entity) {

	// §§§BEGIN§§§
	tipibandooutputDAO.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<Tipibandooutput> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return tipibandooutputDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Tipibandooutput findById(PkId id) {

	// §§§BEGIN§§§
	return tipibandooutputDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Tipibandooutput entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    tipibandooutputDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(Tipibandooutput entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    tipibandooutputDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<Tipibandooutput> findByTipibando(Tipibandooutput tipibandooutput) {

	// §§§BEGIN§§§
	return tipibandooutputDAO.findByGraduatoriat(tipibandooutput);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Tipibandooutput> findByTipigraduatoriet(Integer codiceGraduatoriat) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceGraduatoriat, "tipigraduatoriet", Integer.class));
	ft.addRestriction(fr);
	return tipibandooutputDAO.findByFilterTable(ft);
    }
}
