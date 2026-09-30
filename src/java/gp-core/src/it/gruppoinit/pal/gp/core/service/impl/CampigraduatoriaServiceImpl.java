/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CampigraduatoriaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Campigraduatoria;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CampigraduatoriaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class CampigraduatoriaServiceImpl extends BaseServiceImpl<Campigraduatoria, PkId> implements CampigraduatoriaService {

    private CampigraduatoriaDAO campigraduatoriaDAO;

    @Autowired
    public void setCampigraduatoriaDAO(CampigraduatoriaDAO campigraduatoriaDAO) {

	this.campigraduatoriaDAO = campigraduatoriaDAO;
    }

    @Override
    protected Class<Campigraduatoria> getEntityClass() {

	return Campigraduatoria.class;
    }

    @Override
    public void delete(Campigraduatoria entity) {

	campigraduatoriaDAO.delete(entity);
    }

    @Override
    public List<Campigraduatoria> findAll(Integer firstResult, Integer maxResult) {

	return campigraduatoriaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Campigraduatoria findById(PkId id) {

	return campigraduatoriaDAO.findById(id);
    }

    @Override
    public void insert(Campigraduatoria entity) {

	if (validateEntity(entity)) {
	    campigraduatoriaDAO.insert(entity);
	}
    }

    @Override
    public void update(Campigraduatoria entity) {

	if (validateEntity(entity)) {
	    campigraduatoriaDAO.update(entity);
	}
    }

    @Override
    public List<Campigraduatoria> findByGraduatorieDAndOrderByOrdine(Integer codiceGraduadoriaD) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceGraduadoriaD, "graduatoried", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	return campigraduatoriaDAO.findByFilterTable(ft);
    }
}
