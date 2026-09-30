package it.gruppoinit.pal.gp.core.features.sorteggi.categorie;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SorteggiCategorie;

/**
 * 
 * @author fabrizioc
 * 
 */
@Repository
public class SorteggiCategorieDAOImpl extends BaseDAOImpl<SorteggiCategorie, PkId> implements SorteggiCategorieDAO {

    @Override
    public Class<SorteggiCategorie> getEntityClass() {

	return SorteggiCategorie.class;
    }

    @Override
    public List<SorteggiCategorie> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SorteggiCategorie> findBySoftware(String software) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("software.codice", software));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
