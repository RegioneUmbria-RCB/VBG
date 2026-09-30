/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloFlussoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * @author gianpaolot
 */
@Service
public class ProtocolloFlussoServiceImpl extends BaseServiceImpl<ProtocolloFlusso, String> implements ProtocolloFlussoService {

    private ProtocolloFlussoDAO protocolloFlussoDAO;

    @Autowired
    public void setProtocolloFlussoDAO(ProtocolloFlussoDAO protocolloFlussoDAO) {

	this.protocolloFlussoDAO = protocolloFlussoDAO;
    }

    @Override
    protected Class<ProtocolloFlusso> getEntityClass() {

	return ProtocolloFlusso.class;
    }

    @Override
    public void delete(ProtocolloFlusso entity) {

	protocolloFlussoDAO.delete(entity);
    }

    @Override
    public List<ProtocolloFlusso> findAll(Integer firstResult, Integer maxResult) {

	return protocolloFlussoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public ProtocolloFlusso findById(String id) {

	return protocolloFlussoDAO.findById(id);
    }

    @Override
    public void insert(ProtocolloFlusso entity) {

	if (validateEntity(entity)) {
	    protocolloFlussoDAO.insert(entity);
	}
    }

    @Override
    public void update(ProtocolloFlusso entity) {

	if (validateEntity(entity)) {
	    protocolloFlussoDAO.update(entity);
	}
    }

    @Override
    public List<ProtocolloFlusso> findByResponsabile(Responsabili responsabili, List<String> escludiFlussi) {

	return protocolloFlussoDAO.findByResponsabile(responsabili, escludiFlussi);
    }

    @Override
    public List<ProtocolloFlusso> findByTipiFlussi(String flussoPartenza, String flussoArrivo, String flussoInterno) {

	List<String> list = new ArrayList<String>();
	if (StringUtils.isNotBlank(flussoPartenza))
	    list.add(flussoPartenza);
	if (StringUtils.isNotBlank(flussoArrivo))
	    list.add(flussoArrivo);
	if (StringUtils.isNotBlank(flussoInterno))
	    list.add(flussoInterno);
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction filterRestriction = new FilterRestriction();
	filterRestriction.addFilterField(FilterUtils.in("codice", list.toArray(), String.class));
	filterTable.addRestriction(filterRestriction);
	return protocolloFlussoDAO.findByFilterTable(filterTable);
    }
}
