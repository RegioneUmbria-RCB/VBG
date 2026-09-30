package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.DocumentiContabilitaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.DocumentiContabilita;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class DocumentiContabilitaDAOImpl extends BaseDAOImpl<DocumentiContabilita, PkId> implements DocumentiContabilitaDAO {

    @Override
    public Class<DocumentiContabilita> getEntityClass() {

	return DocumentiContabilita.class;
    }

    @Override
    public List<DocumentiContabilita> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "nomedocumento", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<DocumentiContabilita> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	if (StringUtils.isNotBlank(textToSearch)) {
	    try {
		criteria.add(Restrictions.eq("id.codice", Integer.parseInt(textToSearch.replaceAll("%", ""))));
	    } catch (Exception e) {
		criteria.add(Restrictions.ilike("nomedocumento", textToSearch, MatchMode.ANYWHERE));
	    }
	}
	criteria.addOrder(Order.asc("nomedocumento"));
	if (null != firstResult && null != maxResult) {
	    return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	} else {
	    return getHibernateTemplate().findByCriteria(criteria);
	}
	//criteria.add(Restrictions.ilike("descrizione", filter.getDescrizione(), MatchMode.ANYWHERE));
    }
}
