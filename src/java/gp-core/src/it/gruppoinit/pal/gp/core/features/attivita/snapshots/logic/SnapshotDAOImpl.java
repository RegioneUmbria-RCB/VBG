package it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IAttivitaSnapshotDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshot;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.SnapshotIdData;

@SuppressWarnings("rawtypes")
@Repository
public class SnapshotDAOImpl extends BaseDAOImpl implements ISnapshotDAO {

    @Autowired
    private IAttivitaSnapshotDAO attivitaSnapshotDAO;

    @Override
    public Class getEntityClass() {

	return null;
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public boolean findIfIsAttivitaOperanteFromMovimenti(Integer idAttivita, Date dataSnapshot) {

	Calendar localDataSnapshot = Calendar.getInstance();
	localDataSnapshot.setTime(dataSnapshot);
	localDataSnapshot.set(Calendar.HOUR, 23);
	localDataSnapshot.set(Calendar.MINUTE, 59);
	localDataSnapshot.set(Calendar.SECOND, 59);
	Object[] values = null;
	values = new Object[5];
	values[0] = ORMHelper.getIdcomune();
	values[1] = idAttivita;
	values[2] = Boolean.TRUE;
	values[3] = Boolean.TRUE;
	values[4] = localDataSnapshot.getTime();
	String hql = "select _tipomovimento.flagOperante , _tipomovimento.flagNonoperante from Movimenti this_ inner join this_.istanza _istanza " +
		" inner join this_.tipomovimento _tipomovimento where this_.id.idcomune = ? and _istanza.attivitaId = ? " +
		" and (_tipomovimento.flagOperante=? or _tipomovimento.flagNonoperante=?) and this_.data <= ?";
	List<Object> result = getHibernateTemplate().find(hql, values);
	int operante = 0;
	int nonOperante = 0;
	for (Object object2 : result) {
	    Object[] riga = (Object[]) object2;
	    Boolean operanteBool = BooleanUtils.toBoolean((Boolean) riga[0]);
	    Boolean nonOperanteBool = BooleanUtils.toBoolean((Boolean) riga[1]);
	    if (Boolean.TRUE.equals(operanteBool)) {
		operante++;
	    } else if (Boolean.TRUE.equals(nonOperanteBool)) {
		nonOperante++;
	    }
	}
	return operante - nonOperante >= 0;
    }

    @Override
    public Integer findIdSnapshot(Integer idAttivita, Date dataSnapshot) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findIdSnapshot senza passare il parametro idAttivita");
	}
	if (dataSnapshot == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findIdSnapshot senza passare il parametro dataSnapshot");
	}
	String sql = "select id  from i_attivita_snapshot where idcomune = ? and fk_ia_id = ? and data = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.setDate(2, dataSnapshot);
	query.addScalar("id", Hibernate.INTEGER);
	return Integer.parseInt(query.uniqueResult().toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Date, Integer> findSnapshots(Integer idAttivita) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findSnapshots senza passare il parametro idAttivita");
	}
	String sql = "select id, data from i_attivita_snapshot where idcomune = ? and fk_ia_id = ? order by data asc";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("data", Hibernate.DATE);
	query.setResultTransformer(Transformers.aliasToBean(SnapshotIdData.class));
	List<SnapshotIdData> elenco = query.list();
	Map<Date, Integer> retVal = new HashMap<Date, Integer>();
	for (SnapshotIdData snapshotIdData : elenco) {
	    retVal.put(snapshotIdData.getData(), snapshotIdData.getId());
	}
	return retVal;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Date, Integer> findSnapshots(Integer idAttivita, Date dataRicalcolo) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findSnapshots senza passare il parametro idAttivita");
	}
	if (dataRicalcolo == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findSnapshots senza passare il parametro dataRicalcolo");
	}
	String sql = "select id, data from i_attivita_snapshot where idcomune = ? and fk_ia_id = ? and data >= ? order by data asc";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.setDate(2, dataRicalcolo);
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("data", Hibernate.DATE);
	query.setResultTransformer(Transformers.aliasToBean(SnapshotIdData.class));
	List<SnapshotIdData> elenco = query.list();
	Map<Date, Integer> retVal = new HashMap<Date, Integer>();
	for (SnapshotIdData snapshotIdData : elenco) {
	    retVal.put(snapshotIdData.getData(), snapshotIdData.getId());
	}
	return retVal;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Date, Integer> findSnapshotsSuccessivi(Integer idAttivita, Date dataDiRiferimento) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findSnapshotsSuccessivi senza passare il parametro idAttivita");
	}
	if (dataDiRiferimento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findSnapshotsSuccessivi senza passare il parametro dataDiRiferimento");
	}
	String sql = "select id, data from i_attivita_snapshot where idcomune = ? and fk_ia_id = ? and data > ? order by data asc";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.setDate(2, dataDiRiferimento);
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("data", Hibernate.DATE);
	query.setResultTransformer(Transformers.aliasToBean(SnapshotIdData.class));
	List<SnapshotIdData> elenco = query.list();
	Map<Date, Integer> retVal = new HashMap<Date, Integer>();
	for (SnapshotIdData snapshotIdData : elenco) {
	    retVal.put(snapshotIdData.getData(), snapshotIdData.getId());
	}
	return retVal;
    }

    @Override
    public Date findMinDataValidita(Integer idAttivita) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findMinDataValidita senza passare il parametro idAttivita");
	}
	String sql = "select min(datavalidita) as data from istanze where idcomune = ? and fk_idi_attivita = ? and datavalidita is not null";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanze.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.addScalar("data", Hibernate.DATE);
	return (Date) query.uniqueResult();
    }

    @Override
    public Date findMinDataSnapshot(Integer idAttivita) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findMinDataSnapshot senza passare il parametro idAttivita");
	}
	String sql = "select min(data) as data from i_attivita_snapshot where idcomune = ? and fk_ia_id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.addScalar("data", Hibernate.DATE);
	return (Date) query.uniqueResult();
    }

    @Override
    public IAttivitaSnapshot findSnapshotPiuRecente(Integer idAttivita) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findSnapshotPiuRecente senza passare il parametro idAttivita");
	}
	String sql = "select " +
		" i_attivita_snapshot.id " +
		"from " +
		"  i_attivita_snapshot " +
		"    inner join (select idcomune, fk_ia_id, max(data) as data from i_attivita_snapshot where idcomune = ? and fk_ia_id = ? group by idcomune, fk_ia_id) max_data on " +
		"      i_attivita_snapshot.idcomune = max_data.idcomune and  " +
		"      i_attivita_snapshot.fk_ia_id = max_data.fk_ia_id and  " +
		"      i_attivita_snapshot.data = max_data.data";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.addScalar("id", Hibernate.INTEGER);
	Object idObject = query.uniqueResult();
	if (idObject == null) {
	    return null;
	}
	Integer id = Integer.parseInt(query.uniqueResult().toString());
	return this.attivitaSnapshotDAO.findById(new PkId(id));
    }

    @Override
    public void delete(Integer idSnapshot) {

	if (idSnapshot == null) {
	    throw new IllegalArgumentException("Impossibile cancellare lo snapshot senza passare il riferimento dello snapshot da cancellare");
	}
	//1. cancello i modelli dinamici dello snapshot
	String sql = "delete from i_attivitadyn2mod_t_snapshot where idcomune = ? and fk_ias_id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2modTSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idSnapshot);
	query.executeUpdate();
	//2. cancello i dati dinamici dello snapshot
	sql = "delete from i_attivitadyn2dati_snapshot where idcomune = ? and fk_ias_id = ?";
	query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2datiSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idSnapshot);
	query.executeUpdate();
	//3. cancello lo snapshot
	sql = "delete from i_attivita_snapshot where idcomune = ? and id = ?";
	query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idSnapshot);
	query.executeUpdate();
    }
}
