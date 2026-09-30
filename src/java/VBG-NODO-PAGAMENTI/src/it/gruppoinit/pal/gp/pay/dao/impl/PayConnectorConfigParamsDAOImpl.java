package it.gruppoinit.pal.gp.pay.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.pay.dao.PayConnectorConfigParamsDAO;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams;

@Repository
public class PayConnectorConfigParamsDAOImpl extends BaseDAOImpl<PayConnectorConfigParams, String> implements PayConnectorConfigParamsDAO {

    @Override
    public Class<PayConnectorConfigParams> getEntityClass() {

	return PayConnectorConfigParams.class;
    }
}
