package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.pay.dao.PayPosdebRifClientDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPosdebRifClient;
import it.gruppoinit.pal.gp.pay.domain.PayPosdebRifClientId;

@Repository
public class PayPosdebRifClientDAOImpl extends BaseDAOImpl<PayPosdebRifClient, PayPosdebRifClientId> implements PayPosdebRifClientDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<PayPosdebRifClient> findByPosizioneDebitoria(Integer idPosizioneDebitoria) {

	if (null == idPosizioneDebitoria) {
	    return new ArrayList<>(0);
	}
	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("posizioneDebitoriaId", idPosizioneDebitoria));
	return (List<PayPosdebRifClient>) getHibernateTemplate().findByCriteria(crit);
    }

    @Override
    public Class<PayPosdebRifClient> getEntityClass() {

	return PayPosdebRifClient.class;
    }
}
