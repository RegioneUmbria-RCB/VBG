/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipiendoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author Riccardo Bocci
 * 
 */
@Repository
public class TipiendoDAOImpl extends BaseDAOImpl<Tipiendo, PkId> implements TipiendoDAO {

    @Override
    public Class<Tipiendo> getEntityClass() {

	return Tipiendo.class;
    }

    @Override
    public List<Tipiendo> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "ordine", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipiendo> findByDescSWeTT(String textToSearch, Integer codiceFamiglia) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (codiceFamiglia != null) {
	    det.add(Restrictions.eq("tipifamiglieendo.id.codice", codiceFamiglia));
	}
	if (StringUtils.isNotBlank(textToSearch)) {
	    try {
		det.add(Restrictions.eq("id.codice", Integer.parseInt(textToSearch.replaceAll("%", ""))));
	    } catch (Exception e) {
		det.add(Restrictions.ilike("tipo", textToSearch, MatchMode.ANYWHERE));
	    }
	}
	det.add(Restrictions.in("software.codice", new Object[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }));
	det.addOrder(Order.asc("ordine"));
	det.addOrder(Order.asc("tipo"));
	return (List<Tipiendo>) getHibernateTemplate().findByCriteria(det);
    }
}
