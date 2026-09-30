package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CfgMetadatiCmisDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CfgMetadatiCmis;
import it.gruppoinit.pal.gp.core.domain.CfgMetadatiCmisId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CfgMetadatiCmisDAOImpl extends BaseDAOImpl<CfgMetadatiCmis, CfgMetadatiCmisId> implements CfgMetadatiCmisDAO {

    @Override
    public Class<CfgMetadatiCmis> getEntityClass() {

	return CfgMetadatiCmis.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CfgMetadatiCmis> findByIdComune(String idcomune) {

	DetachedCriteria criteria = getEmptyCriteriaForClass();
	criteria.add(Restrictions.eq("id.idcomune", idcomune));
	return (List<CfgMetadatiCmis>) getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public boolean existConfigurazioneByIdComune(String idcomune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	ft.addRestriction(fr);
	return existsRecords(ft);
    }
}
