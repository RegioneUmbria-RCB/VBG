package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.dao.PayConnectorConfigParamsDAO;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigParamsService;

@Service
public class PayConnectorConfigParamsServiceImpl extends BaseServiceImpl<PayConnectorConfigParams, String> implements PayConnectorConfigParamsService {
    
    private PayConnectorConfigParamsDAO payConnectorConfigParamsDAO;

    @Override
    public void insert(PayConnectorConfigParams entity) {

	this.payConnectorConfigParamsDAO.insert(entity);
    }

    @Override
    public void update(PayConnectorConfigParams entity) {

	this.payConnectorConfigParamsDAO.update(entity);
    }

    @Override
    public void delete(PayConnectorConfigParams entity) {

	if(isDeleteAllowed(entity)) {
	    this.payConnectorConfigParamsDAO.delete(entity);
	}
    }

    @Override
    public List<PayConnectorConfigParams> findAll(Integer firstResult, Integer maxResult) {

	return this.payConnectorConfigParamsDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PayConnectorConfigParams findById(String id) {

	return this.payConnectorConfigParamsDAO.findById(id);
    }

    @Override
    protected Class<PayConnectorConfigParams> getEntityClass() {

	return PayConnectorConfigParams.class;
    }
}
