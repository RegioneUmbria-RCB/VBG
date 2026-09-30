package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BlacklistMotivi;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class BlacklistMotiviDAOImpl extends BaseDAOImpl<BlacklistMotivi, PkId> implements BlacklistMotiviDAO {

    private static final Logger log = LoggerFactory.getLogger(BlacklistMotiviDAOImpl.class);

    @Override
    public Class<BlacklistMotivi> getEntityClass() {

	return BlacklistMotivi.class;
    }

    @Override
    public List<PosizioneDaAggiungereABlackList> findElencoPosizioniDaAggiungereABlackList(List<String> statiPagabili, BlackListContestoEnum contesto) {

	log.debug("findElencoPosizioniDaAggiungereABlackList: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	
	QueryPosizioniDaAggiungereABlackListHelper queryHelper;
	if(BlackListContestoEnum.PRESENZE == contesto){
	    queryHelper = new QueryPosizioniDaAggiungereABLPresenzeHelper(sessimpl, statiPagabili);
	}else if(BlackListContestoEnum.BOLLETTAZIONE == contesto){
	    queryHelper = new QueryPosizioniDaAggiungereABLBollHelper(sessimpl, statiPagabili);
	}else{
	    throw new RuntimeException("Nessun contesto valido");
	}
	
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(PosizioneDaAggiungereABlackList.class));
	return (List<PosizioneDaAggiungereABlackList>) q.list();
    }

    @Override
    public List<ElementoBlackListDaChiudereBean> findBlackListAperte(BlackListContestoEnum contesto) {

	log.debug("findBlackListAperte: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryBlackListDaChiudereHelper queryHelper;
	
	if(BlackListContestoEnum.PRESENZE == contesto){
	    queryHelper = new QueryBlackListDaChiuderePresenzeHelper(sessimpl);
	}else if(BlackListContestoEnum.BOLLETTAZIONE == contesto){
	    queryHelper = new QueryBlackListDaChiudereBollettazioneHelper(sessimpl);
	}else{
	    throw new RuntimeException("Nessun contesto valido");
	}
	
	
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(ElementoBlackListDaChiudereBean.class));
	return (List<ElementoBlackListDaChiudereBean>) q.list();
    }
    
    @Override
    public List<Date> findAllDataAccertamentoByContesto(BlackListContestoEnum contesto){
	SQLQuery q = getSession().createSQLQuery("SELECT DISTINCT DATA_ACCERTAMENTO FROM BLACKLIST_MOTIVI WHERE IDCOMUNE = :idcomune AND CONTESTO = :contesto AND DATA_FINE_BL IS NULL AND DATA_ACCERTAMENTO IS NOT NULL ORDER BY DATA_ACCERTAMENTO DESC");
	q.setParameter("idcomune", ORMHelper.getIdcomune());
	q.setParameter("contesto", contesto.name().toLowerCase());
	q.addScalar("DATA_ACCERTAMENTO", Hibernate.DATE);
	return (List<Date>) q.list();
    }
    
    @Override
    public List<BlackListResultBean> getBlackListResultFe(BlackListContestoEnum contesto,
	    String dataaccertamento,
	    Date dalladatablacklist,
	    Date alladatablacklist,
	    Date dalladataiuv,
	    Date alladataiuv,
	    String iuv,
	    String titolare,
	    String cf,
	    Integer firstresult,
	    Integer maxresult){
	
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryBlackListFeHelper queryHelper = new QueryBlackListFeHelper(sessimpl, contesto);
	String query = queryHelper.buildQuery();
	query += queryHelper.concatAndConditions(dataaccertamento, dalladatablacklist, alladatablacklist, dalladataiuv, alladataiuv, iuv, titolare, cf);
	query += " ORDER BY ANAGRAFE.CODICEFISCALE DESC, BLACKLIST_MOTIVI.DATA_ACCERTAMENTO DESC ";
	SQLQuery q = getSession().createSQLQuery(query);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	
	q.setFirstResult( firstresult != null ? firstresult : 0 );
	q.setMaxResults( maxresult != null ? maxresult : 1000 );	

	q.setResultTransformer(Transformers.aliasToBean(BlackListResultBean.class));
	return (List<BlackListResultBean>) q.list();
    }
    
    @Override
    public List<BlackListResultBean> getBlackListChiuseExport(BlackListContestoEnum contesto){
	
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryBlackListBLChiuseExportHelper queryHelper = new QueryBlackListBLChiuseExportHelper(sessimpl, contesto);
	String query = queryHelper.buildQuery();	
	SQLQuery q = getSession().createSQLQuery(query);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);	
	q.setResultTransformer(Transformers.aliasToBean(BlackListResultBean.class));
	return (List<BlackListResultBean>) q.list();
    }

}
