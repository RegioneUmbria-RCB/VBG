package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.pay.dao.PayConnectorConfigDAO;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;

@Repository
public class PayConnectorConfigDAOImpl extends BaseDAOImpl<PayConnectorConfig, String> implements PayConnectorConfigDAO {

    @Override
    public Class<PayConnectorConfig> getEntityClass() {

	return PayConnectorConfig.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PayConnectorConfig> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getEmptyCriteriaForClass();
	if (null != firstResult && null != maxResult) {
	    return (List<PayConnectorConfig>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<PayConnectorConfig>) getHibernateTemplate().findByCriteria(det);
	}
    }
}
