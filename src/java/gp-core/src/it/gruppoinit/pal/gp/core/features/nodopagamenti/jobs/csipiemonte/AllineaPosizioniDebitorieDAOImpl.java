package it.gruppoinit.pal.gp.core.features.nodopagamenti.jobs.csipiemonte;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.DettPosizioneDebitoriaBean;

@Repository
public class AllineaPosizioniDebitorieDAOImpl extends BaseDAOImpl implements AllineaPosizioniDebitorieDAO {

    @Override
    public List<DettPosizioneDebitoriaBean> getPosizioniDaAllineare() {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryPosizioniDebitorieDaAllineareHelper queryHelper = new QueryPosizioniDebitorieDaAllineareHelper(sessimpl);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(DettPosizioneDebitoriaBean.class));
	return (List<DettPosizioneDebitoriaBean>) q.list();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class getEntityClass() {

	return AllineaPosizioniDebitorieDAOImpl.class;
    }
}
