package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.SpuntistiMercatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.QuerySpuntistiMercatiHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SpuntistiMercati;
import it.gruppoinit.pal.gp.core.domain.helper.SpuntistiMercatiDTO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class SpuntistiMercatiDAOImpl extends BaseDAOImpl<SpuntistiMercati, PkId> implements SpuntistiMercatiDAO {

    @Override
    public Class<SpuntistiMercati> getEntityClass() {

	return SpuntistiMercati.class;
    }

    @Override
    public List<SpuntistiMercati> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "", DAOOrderTypeEnum.ASC);
    }

    @Override
    public boolean existsByMercatoAndUso(Integer codiceMercato, Integer codiceUso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	// fr.addFilterField(FilterUtils.equals("flgAttivo", Boolean.TRUE, Boolean.class));
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiUsoId", codiceUso, Integer.class));
	ft.addRestriction(fr);
	return super.existsRecords(ft);
    }

    @Override
    public List<SpuntistiMercatiDTO> findSpuntistaAssenteDa(Integer codiceMercato, Integer codiceUso, Integer giorniDiAssenza) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QuerySpuntistiMercatiHelper qih = new QuerySpuntistiMercatiHelper(codiceMercato, codiceUso, giorniDiAssenza, sessimpl);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	//	if (firstResult != null) {
	//	    q.setFirstResult(firstResult);
	//	}
	//	if (maxResults != null) {
	//	    q.setMaxResults(maxResults);
	//	}
	qih.setFilterValues(q);
	qih.setScalarProperties(q);
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(SpuntistiMercatiDTO.class));
	List<SpuntistiMercatiDTO> result = (List<SpuntistiMercatiDTO>) q.list();
	return result;
    }
}
