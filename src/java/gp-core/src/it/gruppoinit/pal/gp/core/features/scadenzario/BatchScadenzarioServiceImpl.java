package it.gruppoinit.pal.gp.core.features.scadenzario;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.BatchScadenzario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ScadenzarioListHelper;
import it.gruppoinit.pal.gp.core.features.scadenzario.dao.BatchScadenzarioDAO;
import it.gruppoinit.pal.gp.core.features.scadenzario.service.BatchScadenzarioService;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.helper.BatchScadenzarioFilterHelper;
import it.gruppoinit.pal.gp.core.service.helper.BatchScadenzarioFilterHelper.QUERY_PER;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchScadenzarioServiceImpl extends BaseServiceImpl<BatchScadenzario, PkId> implements BatchScadenzarioService {

    private BatchScadenzarioDAO batchScadenzarioDAO;
    private AlberoprocService alberoprocService;
    private ComuniassociatiService comuniassociatiService;
    private SoftwareService softwareService;
    private ResponsabiliService responsabiliService;
    private StatiistanzaService statiistanzaService;

    @Autowired
    public void setBatchScadenzarioDAO(BatchScadenzarioDAO batchScadenzarioDAO) {

	this.batchScadenzarioDAO = batchScadenzarioDAO;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Override
    protected Class<BatchScadenzario> getEntityClass() {

	return BatchScadenzario.class;
    }

    @Override
    public void delete(BatchScadenzario entity) {

	batchScadenzarioDAO.delete(entity);
    }

    @Override
    public List<BatchScadenzario> findAll(Integer firstResult, Integer maxResult) {

	return batchScadenzarioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public BatchScadenzario findById(PkId id) {

	return batchScadenzarioDAO.findById(id);
    }

    @Override
    public void insert(BatchScadenzario entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public void update(BatchScadenzario entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public List<BatchScadenzario> findByFilter(BatchScadenzarioFilter filter, Integer firstResult, Integer maxResult) {

	BatchScadenzarioFilterHelper helper = new BatchScadenzarioFilterHelper(responsabiliService, softwareService, alberoprocService,
		statiistanzaService, comuniassociatiService);
	FilterTable filterTable = helper.getFilterTable(filter, true, QUERY_PER.BATCH_SCADENZARIO);
	return batchScadenzarioDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public int countByFilter(BatchScadenzarioFilter batchScadenzarioFilter) {

	BatchScadenzarioFilterHelper helper = new BatchScadenzarioFilterHelper(responsabiliService, softwareService, alberoprocService,
		statiistanzaService, comuniassociatiService);
	FilterTable filterTable = helper.getFilterTable(batchScadenzarioFilter, true, QUERY_PER.BATCH_SCADENZARIO);
	int count = batchScadenzarioDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public void clear() {

	batchScadenzarioDAO.clear();
    }

    @Override
    public int countByHelperFilter(BatchScadenzarioFilter batchScadenzarioFilter) {

	return batchScadenzarioDAO.countByHelperFilter(batchScadenzarioFilter);
    }

    @Override
    public List<ScadenzarioListHelper> findByHelperFilter(BatchScadenzarioFilter batchScadenzarioFilter, Integer startRowPage, Integer endRowPage) {

	return batchScadenzarioDAO.findByHelperFilter(batchScadenzarioFilter, startRowPage, endRowPage);
    }
}
