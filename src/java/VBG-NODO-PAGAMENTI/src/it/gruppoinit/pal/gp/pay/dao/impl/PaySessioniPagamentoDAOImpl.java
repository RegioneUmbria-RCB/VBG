/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PaySessioniPagamentoDAO;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;

/**
 * @author francol
 *
 */
@Repository
public class PaySessioniPagamentoDAOImpl extends BaseDAOImpl<PaySessioniPagamento, PkId> implements PaySessioniPagamentoDAO {

    @Override
    public Class<PaySessioniPagamento> getEntityClass() {

	return PaySessioniPagamento.class;
    }

    @Override
    public List<PaySessioniPagamento> findByIdSessione(String idSes) {

	if (StringUtils.isNotBlank(idSes)) {
	    DetachedCriteria crit = getIdcomuneCriteria();
	    crit.add(Restrictions.eq("idSessionePagamento", idSes));
	    return (List<PaySessioniPagamento>) getHibernateTemplate().findByCriteria(crit);
	}
	return new ArrayList<>();
    }

    @Override
    public List<PaySessioniPagamento> findByDigest(String digest) {

	if (StringUtils.isNotBlank(digest)) {
	    DetachedCriteria crit = getIdcomuneCriteria();
	    crit.add(Restrictions.eq("digestSicurezza", digest));
	    return (List<PaySessioniPagamento>) getHibernateTemplate().findByCriteria(crit);
	}
	return new ArrayList<>();
    }

    @Override
    public List<PaySessioniPagamento> findSessioniAttivePerPosizioneDebitoria(Integer idPos) {

	List<PaySessioniPagamento> results = new ArrayList<PaySessioniPagamento>();
	if (idPos != null) {
	    DetachedCriteria crit = getIdcomuneCriteria();
	    crit.add(Restrictions.eq("posizioneDebitoria.id.codice", idPos));
	    crit.add(Restrictions.isNull("esito"));
	    crit.addOrder(Order.desc("dataInizio"));
	    results = (List<PaySessioniPagamento>) getHibernateTemplate().findByCriteria(crit);
	}
	return results;
    }

    @Override
    public List<PaySessioniPagamento> findSessioniPerPosizioneDebitoria(Integer idPos) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("posizioneDebitoria.id.codice", idPos));
	crit.addOrder(Order.desc("dataInizio"));
	return (List<PaySessioniPagamento>) getHibernateTemplate().findByCriteria(crit);
    }
}
