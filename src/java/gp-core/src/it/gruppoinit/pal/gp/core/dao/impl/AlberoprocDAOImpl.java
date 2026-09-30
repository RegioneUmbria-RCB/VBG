package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;

@Repository
public class AlberoprocDAOImpl extends BaseDAOImpl<Alberoproc, PkId> implements AlberoprocDAO {

    @Override
    public Class<Alberoproc> getEntityClass() {

	return Alberoproc.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Alberoproc> findByCriteria(DetachedCriteria criteria) {

	return (List<Alberoproc>) getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Alberoproc findByScCodice(String sccodice) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.add(Restrictions.eq("scCodice", sccodice));
	List<Alberoproc> listTemp = (List<Alberoproc>) getHibernateTemplate().findByCriteria(criteria);
	if (!listTemp.isEmpty()) {
	    return listTemp.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Alberoproc findBySoftwareAndScCodice(String software, String sccodice) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("software.codice", software));
	criteria.add(Restrictions.eq("scCodice", sccodice));
	List<Alberoproc> listTemp = (List<Alberoproc>) getHibernateTemplate().findByCriteria(criteria);
	if (!listTemp.isEmpty()) {
	    return listTemp.get(0);
	}
	return null;
    }

    @Override
    public List<Alberoproc> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "scCodice", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Alberoproc> findAlberoprocFigli(String scCodiceIniziale, boolean soloPrimoLivello, DAOOrderTypeEnum tipoOrdinamento,
	    boolean isPerCalcoloprogressivo) {

	String hql = "Select a from Alberoproc a where a.id.idcomune=? and a.software.codice=? ";
	hql += " and a.scCodice like ? ";
	Object[] values = null;
	if (soloPrimoLivello) {
	    values = new Object[] { ORMHelper.getIdcomune(), ORMHelper.getSoftware(), scCodiceIniziale + "%",
		Long.valueOf(scCodiceIniziale.length() + 2) };
	    hql += " and length(a.scCodice)=? ";
	} else {
	    values = new Object[] { ORMHelper.getIdcomune(), ORMHelper.getSoftware(), scCodiceIniziale + "%" };
	}
	String orderBy = "";
	if (tipoOrdinamento == null) {
	    orderBy += " asc";
	} else {
	    orderBy += tipoOrdinamento.name();
	}
	// non modificare questo ordinamento perchè utilizzato nel calcolo del prossimo codice
	if (isPerCalcoloprogressivo) {
	    hql += " order by a.scCodice " + orderBy;
	} else {
	    hql += " order by a.scOrdine " + orderBy + ", a.scDescrizione " + orderBy + ",a.scCodice " + orderBy;
	}
	return getHibernateTemplate().find(hql, values);
    }

    @Override
    public int countAlberoprocFigli(String scCodicePadre, boolean soloPrimoLivello) {

	String hql = "Select count(*) from Alberoproc a where a.id.idcomune=? and a.software.codice=? ";
	hql += " and a.scCodice like ? ";
	Object[] values = null;
	if (soloPrimoLivello) {
	    values = new Object[] { ORMHelper.getIdcomune(), ORMHelper.getSoftware(), scCodicePadre + "%", Long.valueOf(scCodicePadre.length() + 2) };
	    hql += " and length(a.scCodice)=? ";
	} else {
	    values = new Object[] { ORMHelper.getIdcomune(), ORMHelper.getSoftware(), scCodicePadre + "%" };
	}
	Long ris = ((Long) getHibernateTemplate().find(hql, values).get(0)).longValue();
	return ris.intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public String findDescrizionePrimaVoceAlberoproc(String scCodice) {

	String hql = "Select scDescrizione from Alberoproc a  where a.id.idcomune=? and a.software.codice=? and a.scCodice=? ";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	int paramPos = 0;
	q.setString(paramPos, ORMHelper.getIdcomune());
	paramPos++;
	q.setString(paramPos, ORMHelper.getSoftware());
	paramPos++;
	q.setString(paramPos, scCodice.substring(0, 2));
	List<String> list = (List<String>) q.list();
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlberoprocCommand> findAlberoprocCommand(Integer rootCodiceAlbero) {

	String scCodice = "";
	if (rootCodiceAlbero != null) {
	    Alberoproc ap = this.findById(new PkId(rootCodiceAlbero));
	    if (ap == null) {
		throw new RuntimeException("Nessun intervento con codice " + rootCodiceAlbero.intValue());
	    }
	    scCodice = ap.getScCodice();
	}
	// Setto condizioni base di where
	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	if (StringUtils.isNotBlank(scCodice)) {
	    criteria.add(Restrictions.like("scCodice", scCodice, MatchMode.START));
	}
	// Setto condizioni di join
	criteria.createAlias("vwAlberoproc", "_vwAlberoproc");
	//Setto i campi per cui vogliamo fare la projection
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID");
	plist.add(Projections.property("scDescrizione"), "NAME");
	plist.add(Projections.property("_vwAlberoproc.scDescrizione"), "DESCRIZIONEESTESA");
	plist.add(Projections.property("scCodice"), "CODICE");
	plist.add(Projections.property("scAttivo"), "SCATTIVO");
	plist.add(Projections.property("scPadre"), "PADRE");
	plist.add(Projections.property("scPubblica"), "SCPUBBLICA");
	// Setto condizioni di ordinamento
	criteria.addOrder(OrderBySqlFormula.asc("scCodice", OrderBySqlFormula.FunctionsEnum.LENGTH_FUNCTION));
	criteria.addOrder(Order.asc("scOrdine"));
	criteria.addOrder(Order.asc("scDescrizione"));
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AlberoprocCommand.class));
	List<AlberoprocCommand> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public void updateScCodice(Integer codiceAlberoproc, String scCodice) {

	if (codiceAlberoproc == null) {
	    throw new RuntimeException("updateScCodice: il parametro codiceAlberoproc passato è nullo");
	}
	if (StringUtils.isBlank(scCodice)) {
	    throw new RuntimeException("updateScCodice: il parametro updateScCodice passato è nullo ");
	}
	String hql = "update Alberoproc set scCodice = ? where id.idcomune = ? and id.codice=?";
	getHibernateTemplate().bulkUpdate(hql, new Object[] { scCodice, ORMHelper.getIdcomune(), codiceAlberoproc });
    }
}
