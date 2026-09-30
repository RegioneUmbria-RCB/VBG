package it.gruppoinit.pal.gp.core.features.attivita.istanze;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.model.IstanzeAttivitaHelper;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.query.OrderByParams;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.query.Param;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.query.QueryIstanzeAttivita;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.query.QueryRecuperaAttivaAllaData;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

@SuppressWarnings("rawtypes")
@Repository
public class AttivitaIstanzeDAOImpl extends BaseDAOImpl implements IAttivitaIstanzeDAO {

    private static final String SQL_UPDATE = "UPDATE ISTANZE SET FK_IDI_ATTIVITA = ?, I_ATTIVITAORDINE = ? WHERE IDCOMUNE = ? AND CODICEISTANZA = ?";
    private static final String QUERY = "SELECT" + //
	    " ISTANZE.CODICEISTANZA AS CODICEISTANZA, ISTANZE.DATAVALIDITA " + //
	    " FROM " + //
	    " ISTANZECOLLEGATE" + //
	    "   INNER JOIN ISTANZECOLLEGATE ALTREISTANZE ON ISTANZECOLLEGATE.IDCOMUNE = ALTREISTANZE.IDCOMUNE AND ISTANZECOLLEGATE.PROGRESSIVO = ALTREISTANZE.PROGRESSIVO " + //
	    "   INNER JOIN ISTANZE ON ALTREISTANZE.IDCOMUNE = ISTANZE.IDCOMUNE AND ALTREISTANZE.CODICEISTANZA = ISTANZE.CODICEISTANZA AND ISTANZE.FK_IDI_ATTIVITA IS NULL " + //
	    " WHERE " + //
	    " ISTANZECOLLEGATE.IDCOMUNE = ? AND " + //
	    " ( ISTANZECOLLEGATE.CODICEISTANZA = ? OR ISTANZECOLLEGATE.CODICEISTANZACOLLEGATA = ? ) " + //
	    " GROUP BY ISTANZE.CODICEISTANZA, ISTANZE.DATAVALIDITA";

    @Override
    public Class getEntityClass() {

	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findCatenaIstanzeDaCollegare(final Integer codiceIstanza) {

	// §§§BEGIN§§§
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Il parametro codiceIstanzaDaEscludere non può essere nullo");
	}
	return (List<Integer>) this.getHibernateTemplate().execute(new HibernateCallback() {

	    @Override
	    public List<Integer> doInHibernate(Session session) throws HibernateException, SQLException {

		SQLQuery query = session.createSQLQuery(AttivitaIstanzeDAOImpl.QUERY);
		query.setString(0, ORMHelper.getIdcomune());
		query.setInteger(1, codiceIstanza);
		query.setInteger(2, codiceIstanza);
		query.addScalar("codiceistanza", Hibernate.INTEGER);
		return query.list();
	    }
	});
    }

    @Override
    public void collegaIstanzeAdAttivita(final Integer codiceAttivita, final Integer codiceIstanza, final Integer ordine) {

	if (codiceAttivita == null) {
	    throw new IllegalArgumentException("Il parametro codiceAttivita non può essere nullo per collegare un'istanza ad un'attività");
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Il parametro codiceIstanza non può essere nullo per collegare un'istanza ad un'attività");
	}
	this.getHibernateTemplate().execute(new HibernateCallback() {

	    @Override
	    public Integer doInHibernate(Session session) throws HibernateException, SQLException {

		SQLQuery query = session.createSQLQuery(AttivitaIstanzeDAOImpl.SQL_UPDATE);
		query.setInteger(0, codiceAttivita);
		query.setInteger(1, ordine);
		query.setString(2, ORMHelper.getIdcomune());
		query.setInteger(3, codiceIstanza);
		return query.executeUpdate();
	    }
	});
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeAttivitaHelper> cercaIstanzeDaDataValidita(Integer idAttivita, Date dataValiditaRiferimento) {

	DialettoEnum d = getDialetto();
	List<Param> params = new ArrayList<Param>();
	params.add(0, new Param("istanze.idcomune=?", ORMHelper.getIdcomune(), new StringType()));
	params.add(1, new Param("istanze.fk_idi_attivita=?", ORMHelper.getIdcomune(), new IntegerType()));
	List<OrderByParams> p = new ArrayList<OrderByParams>();
	QueryIstanzeAttivita sql = new QueryIstanzeAttivita(d, params, p);
	SQLQuery q = getSession().createSQLQuery(sql.buildQuery());
	sql.setFilterValues(q);
	sql.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(IstanzeAttivitaHelper.class));
	return q.list();
    }

    private DialettoEnum getDialetto() {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	return DialettoEnum.fromHibernateDialect(sessimpl.getDialect().toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isAttivaAllaData(Integer idAttivita, Date dataValidita) {

	SQLQuery query = getSession().createSQLQuery(new QueryRecuperaAttivaAllaData().getSql());
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.setDate(2, dataValidita);
	query.setString(3, "=");
	query.setInteger(4, -1);
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("descrizione", Hibernate.STRING);
	query.setResultTransformer(new AliasToBeanResultTransformer(IdentificativoDescrizioneBean.class));
	List<IdentificativoDescrizioneBean> result = query.list();
	int valoriAttiva = 0;
	int valoriNegativa = 0;
	for (IdentificativoDescrizioneBean idb : result) {
	    if (idb.getDescrizione().equals("+")) {
		valoriAttiva = idb.getId();
	    } else {
		valoriNegativa = idb.getId();
	    }
	}
	return valoriAttiva > valoriNegativa;
    }

    @Override
    public boolean isPresenteUnaSolaIstanza(Integer idAttivita) {

	SQLQuery query = getSession().createSQLQuery("select count(*) as conteggio from istanze where idcomune = ? and fk_idi_attivita = ?");
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.addScalar("conteggio", Hibernate.INTEGER);
	return Integer.parseInt(query.uniqueResult().toString()) == 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Date> findSnapshotMancanti(Integer idAttivita) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile recuperare la lista delle schede senza passare il riferimento dell'attività");
	}
	String sql = "select " +
		" istanze.datavalidita " +
		"from " +
		" istanze " +
		"  left join i_attivita_snapshot on " +
		"   istanze.idcomune = i_attivita_snapshot.idcomune and " +
		"   istanze.fk_idi_attivita = i_attivita_snapshot.fk_ia_id and " +
		"   istanze.datavalidita = i_attivita_snapshot.data " +
		"where " +
		" istanze.idcomune = ? and " +
		" istanze.fk_idi_attivita = ? and " +
		" istanze.datavalidita is not null and " +
		" i_attivita_snapshot.data is null " +
		"group by " +
		" istanze.datavalidita " +
		"order by " +
		" istanze.datavalidita asc";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.addScalar("datavalidita", Hibernate.DATE);
	return query.list();
    }

    @Override
    public Date scambiaOrdine(Integer codiceIstanzaPrec, Integer codiceIstanzaSuc) {

	if (codiceIstanzaPrec == null || codiceIstanzaSuc == null) {
	    throw new IllegalArgumentException(
		    "Impossibile scambiare l'ordine tra due istanze in un'attività senza passare i riferimenti di entrambe le istanze");
	}
	String sql = "select i_attivitaordine, datavalidita from istanze where idcomune = ? and codiceistanza = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanze.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceIstanzaPrec);
	query.addScalar("i_attivitaordine", Hibernate.INTEGER);
	Integer ordinePrecedente = Integer.parseInt(query.uniqueResult().toString());
	sql = "select i_attivitaordine from istanze where idcomune = ? and codiceistanza = ?";
	query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanze.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceIstanzaSuc);
	query.addScalar("i_attivitaordine", Hibernate.INTEGER);
	Integer ordineSuccessivo = Integer.parseInt(query.uniqueResult().toString());
	if (ordinePrecedente.equals(ordineSuccessivo)) {
	    ordineSuccessivo = ordinePrecedente + 1;
	}
	sql = "update istanze set i_attivitaordine = ? where idcomune = ? and codiceistanza = ?";
	query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanze.class);
	query.setInteger(0, ordinePrecedente);
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, codiceIstanzaSuc);
	query.executeUpdate();
	sql = "update istanze set i_attivitaordine = ? where idcomune = ? and codiceistanza = ?";
	query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanze.class);
	query.setInteger(0, ordineSuccessivo);
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, codiceIstanzaPrec);
	query.executeUpdate();
	sql = "select datavalidita from istanze where idcomune = ? and codiceistanza = ?";
	query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanze.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceIstanzaPrec);
	query.addScalar("datavalidita", Hibernate.DATE);
	return (Date) query.uniqueResult();
    }

    @Override
    public void updateOrdine(Integer idIstanza, Integer ordine) {

	if (idIstanza == null) {
	    throw new IllegalArgumentException("Impossibile cambiare l'ordine di una istanza senza passare il riferimento dell'istanza");
	}
	String sql = "update istanze set i_attivitaordine = ? where idcomune = ? and codiceistanza = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanze.class);
	query.setInteger(0, ordine);
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, idIstanza);
	query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer findIstanzaSenzaDataValiditaPiuRecente(Integer idAttivita) {

	String sql = "select codiceistanza from istanze where idcomune = ? and fk_idi_attivita = ? and datavalidita is null order by data desc, codiceistanza desc";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanze.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.addScalar("codiceistanza", Hibernate.INTEGER);
	List<Integer> ids = query.list();
	return ids.get(0);
    }
}
