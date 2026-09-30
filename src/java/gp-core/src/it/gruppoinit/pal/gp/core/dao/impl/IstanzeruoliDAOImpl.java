/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.IstanzeruoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeruoli;
import it.gruppoinit.pal.gp.core.domain.IstanzeruoliId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeruoliDTO;

/**
 * @author francescop
 * 
 */
@Repository
public class IstanzeruoliDAOImpl extends BaseDAOImpl<Istanzeruoli, IstanzeruoliId> implements IstanzeruoliDAO {

    @Override
    public Class<Istanzeruoli> getEntityClass() {

	return Istanzeruoli.class;
    }

    @Autowired
    private AlberoprocDAO alberoprocDAO;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<Istanzeruoli> findByIstanzaAndResponsabile(Istanze istanza, Responsabili responsabile) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("id.codiceistanza", istanza.getId().getCodice()));
	crit.createAlias("ruolo", "_ruoli");
	crit.createAlias("_ruoli.responsabiliruolis", "_responsabiliruoli");
	crit.add(Restrictions.eq("_responsabiliruoli.id.codiceresponsabile", responsabile.getId().getCodice()));
	crit.addOrder(OrderBySqlFormula.asc("_ruoli.readonly", FunctionsEnum.NVL_FUNCTION, "0"));
	crit.addOrder(OrderBySqlFormula.desc("_ruoli.flagGestmovimenti", FunctionsEnum.NVL_FUNCTION, "0"));
	crit.addOrder(OrderBySqlFormula.asc("_ruoli.flagDisgestmovamm", FunctionsEnum.NVL_FUNCTION, "0"));
	List result = getHibernateTemplate().findByCriteria(crit);
	return result;
    }

    @Override
    public List<Istanzeruoli> findByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResult) {

	Assert.notNull(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocDAO.findById(new PkId(codiceAlberoproc));
	Assert.notNull(alberoproc);
	String software = alberoproc.getSoftware().getCodice();
	String scCodice = alberoproc.getScCodice();
	String hqlQuery = "SELECT ir FROM Istanze i inner join i.alberoproc ap inner join i.istanzeruolis ir WHERE " +
		" i.id.idcomune = ? AND  i.software.codice = ? and ap.scCodice like ? order by ir.id.codiceistanza";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hqlQuery);
	q.setFirstResult(firstResult);
	q.setMaxResults(maxResult);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, software);
	q.setString(2, scCodice + "%");
	List<Istanzeruoli> dynList = q.list();
	return dynList;
    }

    @Override
    public List<IstanzeruoliDTO> findDTOByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResult) {

	Assert.notNull(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocDAO.findById(new PkId(codiceAlberoproc));
	Assert.notNull(alberoproc);
	String software = alberoproc.getSoftware().getCodice();
	String scCodice = alberoproc.getScCodice();
	String hqlQuery = "SELECT ir.id.idcomune as idcomune, ir.id.codiceistanza as codiceistanza, ir.id.idruolo as idruolo FROM Istanze i inner join i.alberoproc ap inner join i.istanzeruolis ir WHERE " +
		" i.id.idcomune = ? AND  i.software.codice = ? and ap.scCodice like ? order by ir.id.codiceistanza";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hqlQuery);
	q.setFirstResult(firstResult);
	q.setMaxResults(maxResult);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, software);
	q.setString(2, scCodice + "%");
	q.setResultTransformer(Transformers.aliasToBean(IstanzeruoliDTO.class));
	List<IstanzeruoliDTO> dynList = q.list();
	return dynList;
    }

    @Override
    public int countByAlberoproc(Integer codiceAlberoproc) {

	Assert.notNull(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocDAO.findById(new PkId(codiceAlberoproc));
	Assert.notNull(alberoproc);
	String software = alberoproc.getSoftware().getCodice();
	String scCodice = alberoproc.getScCodice();
	String hqlQuery = "SELECT count(ir.id.codiceistanza) FROM Istanze i inner join i.alberoproc ap inner join i.istanzeruolis ir WHERE " +
		" i.id.idcomune = ? AND  i.software.codice = ? and ap.scCodice like ?";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), software, scCodice + "%" };
	int ris = ((Long) getHibernateTemplate().find(hqlQuery, values).get(0)).intValue();
	return ris;
    }

    @Override
    public List<Istanzeruoli> findByAlberoprocAndIdRuolo(Integer codiceAlberoproc, Integer idRuolo, Integer firstResult, Integer maxResult) {

	Assert.notNull(codiceAlberoproc);
	Assert.notNull(idRuolo);
	Alberoproc alberoproc = alberoprocDAO.findById(new PkId(codiceAlberoproc));
	Assert.notNull(alberoproc);
	String software = alberoproc.getSoftware().getCodice();
	String scCodice = alberoproc.getScCodice();
	String hqlQuery = "SELECT ir FROM Istanze i inner join i.alberoproc ap inner join i.istanzeruolis ir WHERE " +
		" i.id.idcomune = ? AND  i.software.codice = ? and ap.scCodice like ? and ir.id.idruolo=? order by ir.id.codiceistanza";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hqlQuery);
	q.setFirstResult(firstResult);
	q.setMaxResults(maxResult);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, software);
	q.setString(2, scCodice + "%");
	q.setInteger(3, idRuolo);
	List<Istanzeruoli> dynList = q.list();
	return dynList;
    }

    @Override
    public List<IstanzeruoliDTO> findDTOByAlberoprocAndIdRuolo(Integer codiceAlberoproc, Integer idRuolo, Integer firstResult, Integer maxResult) {

	Assert.notNull(codiceAlberoproc);
	Assert.notNull(idRuolo);
	Alberoproc alberoproc = alberoprocDAO.findById(new PkId(codiceAlberoproc));
	Assert.notNull(alberoproc);
	String software = alberoproc.getSoftware().getCodice();
	String scCodice = alberoproc.getScCodice();
	String hqlQuery = "SELECT ir.id.idcomune as idcomune, ir.id.codiceistanza as codiceistanza, ir.id.idruolo as idruolo FROM Istanze i inner join i.alberoproc ap inner join i.istanzeruolis ir WHERE " +
		" i.id.idcomune = ? AND  i.software.codice = ? and ap.scCodice like ? and ir.id.idruolo=? order by ir.id.codiceistanza";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hqlQuery);
	q.setFirstResult(firstResult);
	q.setMaxResults(maxResult);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, software);
	q.setString(2, scCodice + "%");
	q.setInteger(3, idRuolo);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeruoliDTO.class));
	List<IstanzeruoliDTO> dynList = q.list();
	return dynList;
    }

    @Override
    public int countByAlberoprocAndIdRuolo(Integer codiceAlberoproc, Integer idRuolo) {

	Assert.notNull(codiceAlberoproc);
	Assert.notNull(idRuolo);
	Alberoproc alberoproc = alberoprocDAO.findById(new PkId(codiceAlberoproc));
	Assert.notNull(alberoproc);
	String software = alberoproc.getSoftware().getCodice();
	String scCodice = alberoproc.getScCodice();
	String hqlQuery = "SELECT count(ir.id.codiceistanza) FROM Istanze i inner join i.alberoproc ap inner join i.istanzeruolis ir WHERE " +
		" i.id.idcomune = ? AND  i.software.codice = ? and ap.scCodice like ? and ir.id.idruolo=?";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), software, scCodice + "%", idRuolo };
	int ris = ((Long) getHibernateTemplate().find(hqlQuery, values).get(0)).intValue();
	return ris;
    }

    @Override
    public IstanzeruoliDTO findDTOById(String idcomune, Integer codiceIstanza, Integer idRuolo) {

	Assert.notNull(idcomune);
	Assert.notNull(codiceIstanza);
	Assert.notNull(idRuolo);
	String hqlQuery = "SELECT ir.id.idcomune as idcomune, ir.id.codiceistanza as codiceistanza, ir.id.idruolo as idruolo FROM Istanzeruoli ir WHERE " +
		" ir.id.idcomune = ? AND  ir.id.codiceistanza = ? and ir.id.idruolo=?";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hqlQuery);
	q.setString(0, idcomune);
	q.setInteger(1, codiceIstanza);
	q.setInteger(2, idRuolo);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeruoliDTO.class));
	IstanzeruoliDTO result = (IstanzeruoliDTO) q.uniqueResult();
	return result;
    }

    @Override
    public void insertDTO(String idcomune, Integer codiceIstanza, Integer idRuolo) {

	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	SQLQuery q = session.createSQLQuery("insert into " + schema + ".istanzeruoli (idcomune,codiceistanza,idruolo) values (?,?,?)");
	q.setString(0, idcomune);
	q.setInteger(1, codiceIstanza);
	q.setInteger(2, idRuolo);
	q.executeUpdate();
    }

    @Override
    public void deleteDTO(String idcomune, Integer codiceIstanza, Integer idRuolo) {

	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	SQLQuery q = session.createSQLQuery("delete from " + schema + ".istanzeruoli where idcomune=? and codiceistanza=? and idruolo=?");
	q.setString(0, idcomune);
	q.setInteger(1, codiceIstanza);
	q.setInteger(2, idRuolo);
	q.executeUpdate();
    }

    @Override
    public void deleteAllByAlberoproc(Integer codiceAlberoproc) {

	Alberoproc ap = alberoprocDAO.findById(new PkId(codiceAlberoproc));
	String software = ap.getSoftware().getCodice();
	String scCodice = ap.getScCodice();
	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	SQLQuery q = session.createSQLQuery("delete from " +
		schema +
		".istanzeruoli where idcomune=? and codiceistanza in" +
		" (select codiceistanza from " +
		schema +
		".istanze i inner join " +
		schema +
		".alberoproc ap on "
		// 
		+
		"i.idcomune=ap.idcomune and "
		//
		+
		"i.codiceinterventoproc=ap.sc_id "
		//
		+
		"where ap.idcomune=? and ap.sc_codice like ? and ap.software=?) ");
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getIdcomune());
	q.setString(2, scCodice + "%");
	q.setString(3, software);
	q.executeUpdate();
    }

    @Override
    public void delete(String idComune, Integer codiceIstanza, Set<Integer> idRuoliDaCancellare) {

	if (StringUtils.isBlank(idComune)) {
	    throw new IllegalArgumentException("Impossibile richiamare IstanzeRuoliDAO.delete senza passare idComune");
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Impossibile richiamare IstanzeRuoliDAO.delete senza passare un codice istanza");
	}
	if (idRuoliDaCancellare == null || idRuoliDaCancellare.isEmpty()) {
	    throw new IllegalArgumentException("Impossibile richiamare IstanzeRuoliDAO.delete senza passare la lista dei ruoli da cancellare");
	}
	StringBuilder sql = new StringBuilder("delete from istanzeruoli where " + "idcomune = ? and " + "codiceistanza = ? and " + "idruolo in (");
	for (int i = 0; i < idRuoliDaCancellare.size(); i++) {
	    sql.append("?,");
	}
	sql.deleteCharAt(sql.length() - 1);
	sql.append(")");
	SQLQuery query = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(Istanzeruoli.class);
	int index = 0;
	query.setString(index, idComune);
	index++;
	query.setInteger(index, codiceIstanza);
	index++;
	for (Integer idRuolo : idRuoliDaCancellare) {
	    query.setInteger(index, idRuolo);
	    index++;
	}
	query.executeUpdate();
    }

    @Override
    public void insert(String idComune, Integer codiceIstanza, Set<Integer> idRuoliDaAggiungere) {

	if (StringUtils.isBlank(idComune)) {
	    throw new IllegalArgumentException("Impossibile richiamare IstanzeRuoliDAO.insert senza passare idComune");
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Impossibile richiamare IstanzeRuoliDAO.insert senza passare un codice istanza");
	}
	if (idRuoliDaAggiungere == null || idRuoliDaAggiungere.isEmpty()) {
	    throw new IllegalArgumentException("Impossibile richiamare IstanzeRuoliDAO.insert senza passare la lista dei ruoli da aggiungere");
	}
	String sql = "insert into istanzeruoli(idcomune,codiceistanza,idruolo) values (?,?,?)";
	for (Integer idRuolo : idRuoliDaAggiungere) {
	    SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzeruoli.class);
	    query.setString(0, ORMHelper.getIdcomune());
	    query.setInteger(1, codiceIstanza);
	    query.setInteger(2, idRuolo);
	    query.executeUpdate();
	}
    }
}
