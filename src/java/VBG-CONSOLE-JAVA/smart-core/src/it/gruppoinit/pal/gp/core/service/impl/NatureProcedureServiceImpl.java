package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.NatureProcedureDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.NatureProcedure;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.NatureProcedureService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NatureProcedureServiceImpl extends BaseServiceImpl<NatureProcedure, PkId> implements NatureProcedureService {

    @Autowired
    private NatureProcedureDAO natureProcedureDAO;
    @Autowired
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private SoftwareService softwareService;

    @Override
    public void insert(NatureProcedure entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    natureProcedureDAO.insert(entity);
	}
    }

    private void dataIntegration(NatureProcedure entity) {

	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(NatureProcedure entity) {

	Tipiprocedure tp = tipiprocedureService.bindDomainObject(entity.getTipiprocedure(), PkId.class, "id.codice");
	entity.setTipiprocedure(tp);
    }

    @Override
    public void update(NatureProcedure entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    natureProcedureDAO.update(entity);
	}
    }

    @Override
    public void delete(NatureProcedure entity) {

	if (isDeleteAllowed(entity)) {
	    natureProcedureDAO.insert(entity);
	}
    }

    @Override
    public List<NatureProcedure> findAll(Integer firstResult, Integer maxResult) {

	return natureProcedureDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "codicenaturabase", DAOOrderTypeEnum.ASC);
    }

    @Override
    public NatureProcedure findById(PkId id) {

	return natureProcedureDAO.findById(id);
    }

    @Override
    protected Class<NatureProcedure> getEntityClass() {

	return NatureProcedure.class;
    }

    @Override
    public List<NatureProcedure> findByTipiprocedure(Integer codiceprocedura, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipiprocedureId", codiceprocedura, Integer.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("codicenaturabase"));
	return natureProcedureDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public Tipiprocedure findByNatura(String codicenaturabase) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("codicenaturabase", codicenaturabase));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("codicenaturabase"));
	List<NatureProcedure> result = natureProcedureDAO.findByFilterTable(filterTable, 0, 2);
	if (result.size() > 0) {
	    if (result.get(0).getTipiprocedure() != null) {
		return result.get(0).getTipiprocedure();
	    }
	}
	return null;
    }
}
