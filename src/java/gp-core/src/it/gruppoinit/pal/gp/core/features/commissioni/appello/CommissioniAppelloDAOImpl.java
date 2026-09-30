package it.gruppoinit.pal.gp.core.features.commissioni.appello;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppelloPratiche;
import it.gruppoinit.pal.gp.core.domain.CommedilizieCarica;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotazioni;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;

@SuppressWarnings("rawtypes")
@Repository
public class CommissioniAppelloDAOImpl extends BaseDAOImpl implements ICommissioniAppelloDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public List<CommedilizieAppelloPratiche> findCommEdilizieAppelloPraticheByAppello(Integer idAppello) {

	String hql = "from CommedilizieAppelloPratiche cap where cap.id.idcomune=? and cap.commedilizieAppelloId=? order by cap.commissioniedilizieR.ordine";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	int paramPos = 0;
	q.setString(paramPos++, ORMHelper.getIdcomune());
	q.setInteger(paramPos++, idAppello);
	return q.list();
    }

    @Override
    public void deleteAppelloPraticheByAppello(Integer idCommedilizieAppello) {

	String sql = "delete from commedilizie_appello_pratiche where idcomune=? and fk_appello=?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieAppelloPratiche.class)
		.addSynchronizedEntityClass(CommedilizieAppello.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idCommedilizieAppello);
	query.executeUpdate();
    }

    @Override
    public void deleteAppelloPraticheByIdRiga(Integer idRigaDettaglio) {

	String sql = "delete from commedilizie_appello_pratiche where idcomune=? and fk_commedilizier=?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieAppelloPratiche.class)
		.addSynchronizedEntityClass(CommedilizieAppello.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idRigaDettaglio);
	query.executeUpdate();
    }

    @Override
    public Integer soggettoPresenteInAppello(int codiceCommissione, int codiceAnagrafe) {

	String sql = "select  id from  commedilizie_appello where   idcomune = ? and  codicecommissione = ? and codiceanagrafe = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieAppello.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceCommissione);
	query.setInteger(2, codiceAnagrafe);
	query.addScalar("id", Hibernate.INTEGER);
	if (query.uniqueResult() == null) {
	    return null;
	}
	return new Integer(query.uniqueResult().toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer convocaSoggetto(Integer idCommissione, Integer codiceAnagrafe, Integer codiceCarica) {

	CommedilizieAppello appello = new CommedilizieAppello();
	Anagrafe soggetto = new Anagrafe();
	soggetto.setId(new PkId(codiceAnagrafe));
	appello.setAnagrafe(soggetto);
	CommissioniedilizieT commissione = new CommissioniedilizieT();
	commissione.setId(new PkId(idCommissione));
	appello.setCommissioniedilizieT(commissione);
	if (codiceCarica != null) {
	    CommedilizieCarica carica = new CommedilizieCarica();
	    carica.setId(new PkId(codiceCarica));
	    appello.setCommedilizieCarica(carica);
	}
	appello.setPresente(false);
	this.insert(appello);
	return appello.getId().getCodice();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer collegaAppelloAPratica(Integer idAppello, Integer idRiga) {

	CommedilizieAppelloPratiche appelloPratiche = new CommedilizieAppelloPratiche();
	CommedilizieAppello appello = new CommedilizieAppello();
	appello.setId(new PkId(idAppello));
	appelloPratiche.setCommedilizieAppello(appello);
	CommissioniedilizieR riga = new CommissioniedilizieR();
	riga.setId(new PkId(idRiga));
	appelloPratiche.setCommissioniedilizieR(riga);
	this.insert(appelloPratiche);
	return appelloPratiche.getId().getCodice();
    }

    @Override
    public Integer collegaCaricaByAppello(Integer codiceAnagrafe, int idCommissione) {

	String sql = "SELECT codicecarica  FROM commedilizie_appello WHERE idcomune = ? AND   codiceanagrafe = ? AND codicecommissione = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieAppello.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceAnagrafe);
	query.setInteger(2, idCommissione);
	query.addScalar("codicecarica", Hibernate.INTEGER);
	if (query.uniqueResult() == null) {
	    return null;
	}
	return new Integer(query.uniqueResult().toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findSoggettiGiaPresenti(Integer idRiga) {

	String sql = "select codiceanagrafe" +
		" from commedilizie_appello" +
		" where idcomune = ?" +
		" and id in (" +
		" select fk_appello" +
		" from commedilizie_appello_pratiche" +
		" where idcomune = ? and   fk_commedilizier = ?)";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieAppelloPratiche.class)
		.addSynchronizedEntityClass(CommedilizieAppello.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, idRiga);
	query.addScalar("codiceanagrafe", Hibernate.INTEGER);
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public CommedilizieAppello findBySoggettoAndCommissione(Integer codAnagraf, Integer idCommissione) {

	String hql = "from CommedilizieAppello c where c.id.idcomune = ? and c.commissioniedilizieTId = ? and c.anagrafeId = ?";
	Query query = getSession().createQuery(hql);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idCommissione);
	query.setInteger(2, codAnagraf);
	List<CommedilizieAppello> list = (List<CommedilizieAppello>) query.list();
	if (list.size() == 1) {
	    return list.get(0);
	}
	if (list.isEmpty()) {
	    return null;
	}
	throw new RuntimeException("Nell'appello sono presenti più record per la stessa anagrafe");
    }

    @Override
    public void deleteAppelloByIdAppello(Integer idAppello) {

	String sql = "delete from commedilizie_appello where idcomune=? and id=?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieAppello.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAppello);
	query.executeUpdate();
    }

    @Override
    public void deleteAppelloPraticheByIdRigaAndAppello(Integer idRigaDettaglio, Integer idAppello) {

	String sql = "delete from commedilizie_appello_pratiche where idcomune=? and fk_commedilizier=? and fk_appello=?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieAppelloPratiche.class)
		.addSynchronizedEntityClass(CommedilizieAppello.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idRigaDettaglio);
	query.setInteger(2, idAppello);
	query.executeUpdate();
    }

    @Override
    public List<CommedilizieAppelloPratiche> findCommEdilizieAppelloPraticheByAppelloAndRiga(Integer idAppello, Integer idRigaCommissioniEdilizieR) {

	String hql = "from CommedilizieAppelloPratiche cap where cap.id.idcomune=? and " +
		"cap.commedilizieAppelloId=? and cap.commissioniedilizieRId=? order by cap.commissioniedilizieR.ordine";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	int paramPos = 0;
	q.setString(paramPos++, ORMHelper.getIdcomune());
	q.setInteger(paramPos++, idAppello);
	q.setInteger(paramPos++, idRigaCommissioniEdilizieR);
	return q.list();
    }

    @Override
    public int countCommEdilizieAppelloPraticheByAppelloAndRiga(Integer idAppello, Integer idRigaCommissioniEdilizieR) {

	SQLQuery query = getSession()
		.createSQLQuery(
			"select count(*) as conteggio from commedilizie_appello_pratiche where idcomune = ? and fk_commedilizier=? and fk_appello=?")
		.addSynchronizedEntityClass(CommedilizieAppelloPratiche.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idRigaCommissioniEdilizieR);
	query.setInteger(2, idAppello);
	query.addScalar("conteggio", Hibernate.INTEGER);
	return Integer.parseInt(query.uniqueResult().toString());
    }

    @Override
    public boolean esisteVotazione(Integer idAppello) {

	SQLQuery query = getSession().createSQLQuery("select count(*) as conteggio from commedilizie_votazioni where idcomune = ? and idappello=?")
		.addSynchronizedEntityClass(CommedilizieVotazioni.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAppello);
	query.addScalar("conteggio", Hibernate.INTEGER);
	return Integer.parseInt(query.uniqueResult().toString()) > 0;
    }
}
