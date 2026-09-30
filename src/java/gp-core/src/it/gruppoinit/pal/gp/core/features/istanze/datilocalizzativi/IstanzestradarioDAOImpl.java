package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.StatiistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.TipoQueryHelperEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzestradarioDTO;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

/**
 * 
 * @author francescop
 */
@Repository
public class IstanzestradarioDAOImpl extends BaseDAOImpl<Istanzestradario, PkId> implements IstanzestradarioDAO {

    private AlberoprocDAO alberoprocDAO;
    private StatiistanzaDAO statiistanzaDAO;
    private Dyn2CampiDAO dyn2CampiDAO;
    private ComuniassociatiService comuniassociatiService;

    @Autowired
    public void setAlberoprocDAO(AlberoprocDAO alberoprocDAO) {

	this.alberoprocDAO = alberoprocDAO;
    }

    @Autowired
    public void setStatiistanzaDAO(StatiistanzaDAO statiistanzaDAO) {

	this.statiistanzaDAO = statiistanzaDAO;
    }

    @Autowired
    public void setDyn2CampiDAO(Dyn2CampiDAO dyn2CampiDAO) {

	this.dyn2CampiDAO = dyn2CampiDAO;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Override
    public Class<Istanzestradario> getEntityClass() {

	return Istanzestradario.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Istanzestradario findPrimarioByCodiceIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new RuntimeException("Impossibile risalire alla localizzazione primaria dell'istanza senza passare il codice istanza");
	}
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("istanza.id.codice", codiceIstanza));
	det.add(Restrictions.eq("primario", true));
	List<Istanzestradario> istanzestradarios = getHibernateTemplate().findByCriteria(det);
	if (!istanzestradarios.isEmpty()) {
	    return istanzestradarios.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzestradario> findByIstanza(Integer codiceIstanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("istanzaId", codiceIstanza));
	det.addOrder(Order.desc("primario"));
	det.addOrder(Order.asc("id.codice"));
	List<Istanzestradario> istanzestradarios = getHibernateTemplate().findByCriteria(det);
	return istanzestradarios;
    }

    @Override
    public void updateFieldValido(Integer codiceIstanzaStradario, Boolean value) {

	String hql = "update Istanzestradario set valido = :newFlagValido where id.idcomune = :idcomune and id.codice=:codiceIstanzaStradario";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setBoolean("newFlagValido", value);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("codiceIstanzaStradario", codiceIstanzaStradario);
	SQLQuery qCommint = s.createSQLQuery("COMMIT");
	q.executeUpdate();
	qCommint.executeUpdate();
    }

    @Override
    public void updateFieldCodicecivico(Integer codiceIstanzaStradario, String codiceCivico) {

	String hql = "update Istanzestradario set CODICECIVICO = :codiceCivico where id.idcomune = :idcomune and id.codice=:codiceIstanzaStradario";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString("codiceCivico", codiceCivico);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("codiceIstanzaStradario", codiceIstanzaStradario);
	SQLQuery qCommint = s.createSQLQuery("COMMIT");
	q.executeUpdate();
	qCommint.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public IstanzestradarioDTO findIstanzeStradarioDTOByIstanza(Integer codiceistanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("istanza", "_istanza", DetachedCriteria.LEFT_JOIN);
	det.createAlias("stradario", "_stradario", DetachedCriteria.LEFT_JOIN);
	det.createAlias("stradariocolore", "_stradariocolore", DetachedCriteria.LEFT_JOIN);
	det.add(Restrictions.eq("_istanza.id.codice", codiceistanza));
	det.add(Restrictions.eq("primario", true));
	ProjectionList plist = Projections.projectionList();
	//stradario.prefisso
	plist.add(Projections.property("_stradario.prefisso"), "prefisso");
	//stradario.descrizione
	plist.add(Projections.property("_stradario.descrizione"), "descrizione");
	//stradario.cap
	plist.add(Projections.property("_stradario.cap"), "cap");
	//stradario.locfraz
	plist.add(Projections.property("_stradario.locfraz"), "locfraz");
	//stradario.codviario
	plist.add(Projections.property("_stradario.codviario"), "codviario");
	//	plist.add(Projections.property("stradariocolore"), "stradariocolore");
	//plist.add(Projections.property("_stradariocolore.colore"), "colore");
	plist.add(Projections.property("civico"), "civico");
	//plist.add(Projections.property("primario"), "primario");
	plist.add(Projections.property("esponente"), "esponente");
	det.setProjection(plist);
	det.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzestradarioDTO.class));
	List<IstanzestradarioDTO> list = (List<IstanzestradarioDTO>) getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public void updateSettaANullNonValidi() {

	String hql = "update Istanzestradario i set i.valido = null where i.id.idcomune = :idcomune and i.valido=:valido";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setBoolean(":valido", Boolean.FALSE);
	q.executeUpdate();
    }

    @Override
    public Istanzestradario findByUuid(Integer codiceIstanza, String uuid) {

	if (codiceIstanza == null) {
	    throw new RuntimeException("Impossibile cercare una localizzazione dell'istanza per uuid senza passare il codice istanza");
	}
	if (StringUtils.isBlank(uuid)) {
	    throw new RuntimeException("Impossibile cercare una localizzazione dell'istanza per uuid senza passare l'uuid");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.TYPE));
	fr.addFilterField(FilterUtils.equals("uuid", uuid, String.class));
	ft.addRestriction(fr);
	List<Istanzestradario> stradari = findByFilterTable(ft);
	if (stradari.size() == 0) {
	    return new Istanzestradario();
	}
	if (stradari.size() > 1) {
	    throw new RuntimeException("Ci sono più righe di istanzestradario con uuid " + uuid);
	}
	return stradari.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeStradarioExtendedDTO> findByIstanzeFilter(IstanzeFilter filter, Integer firstResult, Integer maxResults) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryIstanzeStradarioHelper qih = new QueryIstanzeStradarioHelper(filter, sessimpl, alberoprocDAO, statiistanzaDAO, dyn2CampiDAO,
		comuniassociatiService, TipoQueryHelperEnum.SELECT, firstResult, maxResults);
	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	Dialect dialetto = sessimpl.getDialect();
	String hibernateDialect = dialetto.toString();
	if (!DialettoEnum.ORACLE.equals(DialettoEnum.fromHibernateDialect(hibernateDialect))) {
	    if (firstResult != null) {
		q.setFirstResult(firstResult);
	    }
	    if (maxResults != null) {
		q.setMaxResults(maxResults);
	    }
	}
	qih.setFilterValues(q);
	qih.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeStradarioExtendedDTO.class));
	List<IstanzeStradarioExtendedDTO> retVal = q.list();
	return retVal;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeStradarioExtendedDTO> findByTmp(String token) {

	QueryIstanzeStradarioDaTmpHelper helper = new QueryIstanzeStradarioDaTmpHelper(token);
	String sql = helper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	helper.setFilterValues(q);
	helper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeStradarioExtendedDTO.class));
	List<IstanzeStradarioExtendedDTO> retVal = q.list();
	return retVal;
    }

    @SuppressWarnings("unchecked")
    @Override
    public IstanzeStradarioExtendedDTO findById(Integer idIstanzeStradario) {

	QueryIstanzeStradarioDaIdHelper helper = new QueryIstanzeStradarioDaIdHelper(idIstanzeStradario);
	String sql = helper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	helper.setFilterValues(q);
	helper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeStradarioExtendedDTO.class));
	List<IstanzeStradarioExtendedDTO> elenco = q.list();
	if (elenco == null || elenco.isEmpty()) {
	    return new IstanzeStradarioExtendedDTO();
	}
	return elenco.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeStradarioExtendedDTO> findByAutorizzazioniFilter(DetachedCriteria autorizzazioniFilter, Integer firstResult,
	    Integer maxResult) {

	//1. Aggiungo gli alias che mi servono per estrapolare i dati
	autorizzazioniFilter.createCriteria("_istanza.istanzestradarios", "_iststra");
	autorizzazioniFilter.createCriteria("_iststra.stradario", "_stra");
	autorizzazioniFilter.createCriteria("_istanza.comune", "_com");
	//2. Preparlo la ProjectionList
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("_iststra.id.idcomune"), "idComune");
	plist.add(Projections.property("_istanza.software.codice"), "software");
	plist.add(Projections.property("_com.codicecomune"), "codiceComune");
	plist.add(Projections.property("_com.comune"), "comune");
	plist.add(Projections.property("_com.codiceistat"), "codiceIstat");
	plist.add(Projections.property("_istanza.id.codice"), "codiceIstanza");
	plist.add(Projections.property("_istanza.numeroistanza"), "numeroIstanza");
	plist.add(Projections.property("_iststra.uuid"), "uuidIstanzeStradario");
	plist.add(Projections.property("_stra.codviario"), "codiceViario");
	plist.add(Projections.property("_iststra.km"), "km");
	plist.add(Projections.property("_iststra.latitudine"), "latitudine");
	plist.add(Projections.property("_iststra.longitudine"), "longitudine");
	autorizzazioniFilter.setProjection(plist);
	//3. Estrapolo i dati
	autorizzazioniFilter.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzeStradarioExtendedDTO.class));
	List<IstanzeStradarioExtendedDTO> list = getHibernateTemplate().findByCriteria(autorizzazioniFilter);
	return list;
    }
}
