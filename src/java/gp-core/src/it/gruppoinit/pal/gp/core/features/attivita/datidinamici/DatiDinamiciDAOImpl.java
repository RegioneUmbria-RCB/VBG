package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtatt;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2dati;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;

@SuppressWarnings("rawtypes")
@Repository
public class DatiDinamiciDAOImpl extends BaseDAOImpl implements IDatiDinamiciDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public List<Integer> findIdSchedeDinamicheDaAlberoProc(Integer codiceInterventoProc) {

	if (codiceInterventoProc == null) {
	    throw new IllegalArgumentException("Impossibile cercare le schede da collegare senza passare il codice dell'albero degli interventi");
	}
	String sql = "select sc_codice from alberoproc where idcomune = ? and sc_id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Alberoproc.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceInterventoProc);
	query.addScalar("SC_CODICE", Hibernate.STRING);
	String scCodice = query.uniqueResult().toString();
	return findIdSchedeDinamicheDaAlberoProc(ORMHelper.getIdcomune(), ORMHelper.getSoftware(), scCodice);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIdSchedeDinamicheAttivita(Integer idAttivita) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile recuperare la lista delle schede senza passare il riferimento dell'attività");
	}
	String sql = "select fk_d2mt_id from i_attivitadyn2modellit where idcomune = ? and fk_ia_id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2modellit.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.addScalar("fk_d2mt_id", Hibernate.INTEGER);
	return query.list();
    }

    @Override
    public void aggiungiSchedeDinamicheAdAttivita(Integer idAttivita, Integer idScheda, List<Integer> idSnapshots) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo agganciaSchedeDinamicheAdAttivita senza passare il riferimento dell'attività");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo agganciaSchedeDinamicheAdAttivita senza passare il riferimento della scheda da aggiungere");
	}
	String sql = "insert into i_attivitadyn2modellit(idcomune,fk_ia_id,fk_d2mt_id) values (?,?,?)";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivita.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.setInteger(2, idScheda);
	query.executeUpdate();
	if (idSnapshots != null && !idSnapshots.isEmpty()) {
	    this.aggiungiSchedeDinamicheASnapshots(idAttivita, idScheda, idSnapshots);
	}
    }

    @Override
    public void aggiungiSchedeDinamicheASnapshots(Integer idAttivita, List<Integer> idSchede, Integer idSnapshot) {

	List<Integer> idSnapshots = new ArrayList<Integer>();
	idSnapshots.add(idSnapshot);
	this.aggiungiSchedeDinamicheASnapshots(idAttivita, idSchede, idSnapshots);
    }

    @Override
    public void aggiungiSchedeDinamicheASnapshots(Integer idAttivita, Integer idScheda, List<Integer> idSnapshots) {

	List<Integer> idSchede = new ArrayList<Integer>();
	idSchede.add(idScheda);
	this.aggiungiSchedeDinamicheASnapshots(idAttivita, idSchede, idSnapshots);
    }

    @Override
    public void aggiungiSchedeDinamicheASnapshots(Integer idAttivita, List<Integer> idSchede, List<Integer> idSnapshots) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo agganciaSchedeDinamicheASnapshots senza passare il riferimento dell'attività");
	}
	if (idSchede == null || idSchede.isEmpty()) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo agganciaSchedeDinamicheASnapshots senza passare la lista delle schede da aggiungere");
	}
	if (idSnapshots == null || idSnapshots.isEmpty()) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo agganciaSchedeDinamicheASnapshots senza passare la lista degli snapshot interessati");
	}
	String sql = "insert into i_attivitadyn2mod_t_snapshot(idcomune,fk_ia_id,fk_ias_id,fk_d2mt_id) values (?,?,?,?)";
	for (Integer idSnapshot : idSnapshots) {
	    for (Integer idScheda : idSchede) {
		SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
		query.setString(0, ORMHelper.getIdcomune());
		query.setInteger(1, idAttivita);
		query.setInteger(2, idSnapshot);
		query.setInteger(3, idScheda);
		query.executeUpdate();
	    }
	}
    }

    @SuppressWarnings("unchecked")
    private List<Integer> findIdSchedeDinamicheDaAlberoProc(String idComune, String software, String scCodice) {

	if (StringUtils.isBlank(scCodice)) {
	    throw new IllegalArgumentException("Impossibile cercare le schede da collegare senza passare il codice dell'albero degli interventi");
	}
	List<String> codici = this.scCodiceToArray(scCodice);
	StringBuilder sql = new StringBuilder("select" + " alberoproc_d2modtatt.fk_d2mt_id " + "from " + " alberoproc"
		+ "   inner join alberoproc_d2modtatt on alberoproc.idcomune = alberoproc_d2modtatt.idcomune and alberoproc.sc_id = alberoproc_d2modtatt.fk_sc_id "
		+ "where" + " alberoproc.idcomune = ? and " + " alberoproc.software = ? and " + " alberoproc.sc_codice in (");
	for (int i = 0; i < codici.size(); i++) {
	    sql.append("?,");
	}
	sql.deleteCharAt(sql.length() - 1).append(") group by alberoproc_d2modtatt.fk_d2mt_id");
	SQLQuery query = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(AlberoprocD2modtatt.class);
	int index = 0;
	query.setString(index, idComune);
	index++;
	query.setString(index, software);
	index++;
	for (String codice : codici) {
	    query.setString(index, codice);
	    index++;
	}
	query.addScalar("fk_d2mt_id", Hibernate.INTEGER);
	return query.list();
    }

    private List<String> scCodiceToArray(String scCodice) {

	if (StringUtils.isBlank(scCodice)) {
	    throw new IllegalArgumentException("Impossibile creare un'array di sc_codice senza passare il parametro sc_codice di partenza");
	}
	List<String> retVal = new ArrayList<String>();
	while (scCodice.length() >= 2) {
	    retVal.add(scCodice);
	    if (scCodice.length() <= 2) {
		break;
	    }
	    scCodice = scCodice.substring(0, scCodice.length() - 2);
	}
	return retVal;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> findCampiPresentiIstanze(Integer idAttivita, Integer idScheda) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findCampiPresentiIstanze senza passare il riferimento dell'attivita");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findCampiPresentiIstanze senza passare il riferimento della scheda dinamica");
	}
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryCampiIstanzeValorizzati queryHelper = new QueryCampiIstanzeValorizzati(sessimpl, idAttivita, idScheda);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzedyn2dati.class);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(CampoDinamicoSnapshot.class));
	List<CampoDinamicoSnapshot> elenco = q.list();
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappaOrdinata = new TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>(
		new ChiaveCampoDinamicoPerDataComparator(false));
	for (CampoDinamicoSnapshot campo : elenco) {
	    ChiaveCampoDinamicoPerData chiave = new ChiaveCampoDinamicoPerData(campo.getDataValidita(), campo.getId(), campo.getIndice(),
		    campo.getIndiceMolteplicita());
	    ValoreCampoPresente valore = new ValoreCampoPresente(campo.getValore(), campo.getValoreDecodificato());
	    mappaOrdinata.put(chiave, valore);
	}
	return mappaOrdinata;
    }

    @SuppressWarnings("unchecked")
    @Override
    public TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> findCampiPresentiIstanze(Integer idAttivita, List<Integer> idCampi) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findCampiPresentiIstanze senza passare il riferimento dell'attivita");
	}
	if (idCampi == null || idCampi.isEmpty()) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findCampiPresentiIstanze senza passare l'elenco dei campi dinamici");
	}
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryCampiIstanzeValorizzati queryHelper = new QueryCampiIstanzeValorizzati(sessimpl, idAttivita, idCampi);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzedyn2dati.class);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(CampoDinamicoSnapshot.class));
	List<CampoDinamicoSnapshot> elenco = q.list();
	TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappaOrdinata = new TreeMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>(
		new ChiaveCampoDinamicoPerDataComparator(false));
	for (CampoDinamicoSnapshot campo : elenco) {
	    ChiaveCampoDinamicoPerData chiave = new ChiaveCampoDinamicoPerData(campo.getDataValidita(), campo.getId(), campo.getIndice(),
		    campo.getIndiceMolteplicita());
	    ValoreCampoPresente valore = new ValoreCampoPresente(campo.getValore(), campo.getValoreDecodificato());
	    mappaOrdinata.put(chiave, valore);
	}
	return mappaOrdinata;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> findCampiPresentiSnapshot(Integer idAttivita, Integer idScheda) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findCampiPresentiSnapshot senza passare il riferimento dell'attivita");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findCampiPresentiSnapshot senza passare il riferimento della scheda dinamica");
	}
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryCampiSnapshotValorizzati queryHelper = new QueryCampiSnapshotValorizzati(sessimpl, idAttivita, idScheda);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2datiSnapshot.class);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(ChiaveCampoDinamicoPerDataDB.class));
	List<ChiaveCampoDinamicoPerDataDB> elenco = q.list();
	Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> mappa = new HashMap<ChiaveCampoDinamicoPerData, ValoreCampoPresente>();
	for (ChiaveCampoDinamicoPerDataDB campo : elenco) {
	    mappa.put(new ChiaveCampoDinamicoPerData(campo.getDataSnapshot(), campo.getIdCampo(), campo.getIndice(), campo.getIndiceMolteplicita()),
		    new ValoreCampoPresente(campo.getValore(), campo.getValoreDecodificato()));
	}
	return mappa;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CampoDinamicoAttivita> findCampiPresentiAttivita(Integer idAttivita, Integer idScheda) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findCampiPresentiAttivita senza passare il riferimento dell'attivita");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findCampiPresentiAttivita senza passare il riferimento della scheda dinamica");
	}
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryCampiAttivitaValorizzati queryHelper = new QueryCampiAttivitaValorizzati(sessimpl, idAttivita, idScheda);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2dati.class);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(CampoDinamicoAttivita.class));
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIdCampiScheda(Integer idScheda) {

	if (idScheda == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findIdCampiScheda senza passare il riferimento della scheda dinamica");
	}
	String sql = "select fk_d2c_id from dyn2_modellid where idcomune = ? and fk_d2mt_id = ? and fk_d2c_id is not null";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idScheda, new IntegerType());
	q.addScalar("fk_d2c_id", Hibernate.INTEGER);
	return q.list();
    }

    @Override
    public List<Integer> findIdCampiAttivitaGestibiliDaIstanze(Integer idAttivita) {

	return this.findIdCampiAttivitaGestibiliDaIstanze(idAttivita, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIdCampiAttivitaGestibiliDaIstanze(Integer idAttivita, Integer idScheda) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findIdCampiAttivita senza passare il riferimento dell'attività");
	}
	String sql = "select " + "    dyn2_modellid.fk_d2c_id " + "from " + "    dyn2_modellid "
		+ "      inner join i_attivitadyn2modellit on dyn2_modellid.idcomune = i_attivitadyn2modellit.idcomune and dyn2_modellid.fk_d2mt_id = i_attivitadyn2modellit.fk_d2mt_id "
		+ "      inner join i_attivita on i_attivitadyn2modellit.idcomune = i_attivita.idcomune and i_attivitadyn2modellit.fk_ia_id = i_attivita.id "
		+ "      inner join istanze on i_attivita.idcomune = istanze.idcomune and i_attivita.id = istanze.fk_idi_attivita and istanze.datavalidita is not null "
		+ "      inner join istanzedyn2modellit on istanze.idcomune = istanzedyn2modellit.idcomune and istanze.codiceistanza = istanzedyn2modellit.codiceistanza ";
	if (idScheda != null) {
	    sql += " and istanzedyn2modellit.fk_d2mt_id = ? ";
	}
	sql += "      inner join dyn2_modellid modelli_istanza on " + "        istanzedyn2modellit.idcomune = modelli_istanza.idcomune and "
		+ "        istanzedyn2modellit.fk_d2mt_id = modelli_istanza.fk_d2mt_id and "
		+ "        dyn2_modellid.idcomune = modelli_istanza.idcomune and " + "        dyn2_modellid.fk_d2c_id = modelli_istanza.fk_d2c_id "
		+ "where " + "    i_attivitadyn2modellit.idcomune =? and " + "    i_attivitadyn2modellit.fk_ia_id = ? and ";
	sql += " dyn2_modellid.fk_d2c_id is not null group by dyn2_modellid.fk_d2c_id order by dyn2_modellid.fk_d2c_id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2modellit.class);
	Integer idx = 0;
	if (idScheda != null) {
	    q.setParameter(idx, idScheda, new IntegerType());
	    idx++;
	}
	q.setParameter(idx, ORMHelper.getIdcomune(), new StringType());
	idx++;
	q.setParameter(idx, idAttivita, new IntegerType());
	q.addScalar("fk_d2c_id", Hibernate.INTEGER);
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findCampiPresentiIstanzaEAttivita(Integer idAttivita, Integer idScheda) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findCampiPresentiIstanzaEAttivita senza passare l'attività di riferimento");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findCampiPresentiIstanzaEAttivita senza passare il riferimento della scheda dinamica");
	}
	String sql = "select " + " dyn2_modellid.fk_d2c_id  " + "from " + " i_attivitadyn2modellit "
		+ "   inner join dyn2_modellid on i_attivitadyn2modellit.idcomune = dyn2_modellid.idcomune and i_attivitadyn2modellit.fk_d2mt_id = dyn2_modellid.fk_d2mt_id "
		+ "   inner join dyn2_modellid modello_istanza on dyn2_modellid.idcomune = modello_istanza.idcomune and dyn2_modellid.fk_d2c_id = modello_istanza.fk_d2c_id and modello_istanza.fk_d2mt_id = ? "
		+ "where " + " i_attivitadyn2modellit.idcomune = ? and " + " i_attivitadyn2modellit.fk_ia_id = ?  " + "group by "
		+ " dyn2_modellid.fk_d2c_id " + "order by " + " dyn2_modellid.fk_d2c_id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2modellit.class);
	q.setParameter(0, idScheda, new IntegerType());
	q.setParameter(1, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(2, idScheda, new IntegerType());
	q.addScalar("fk_d2c_id", Hibernate.INTEGER);
	return q.list();
    }

    @Override
    public void deleteCampiDinamici(Integer idAttivita, List<Integer> idCampi) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteAll senza passare il riferimento dell'attivita");
	}
	if (idCampi == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteAll senza l'array di campi da eliminare");
	}
	String sql = "delete from i_attivitadyn2dati_snapshot where idcomune = ? and fk_ia_id = ? and fk_d2c_id in (";
	sql += StringUtils.repeat("?,", idCampi.size());
	sql = sql.substring(0, sql.length() - 1);
	sql += ")";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2datiSnapshot.class);
	int index = 0;
	q.setParameter(index, ORMHelper.getIdcomune(), new StringType());
	index++;
	q.setParameter(index, idAttivita, new IntegerType());
	index++;
	for (Integer id : idCampi) {
	    q.setParameter(index, id, new IntegerType());
	    index++;
	}
	q.executeUpdate();
	//cancello dall'attività
	sql = sql.replace("i_attivitadyn2dati_snapshot", "i_attivitadyn2dati");
	q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2dati.class);
	index = 0;
	q.setParameter(index, ORMHelper.getIdcomune(), new StringType());
	index++;
	q.setParameter(index, idAttivita, new IntegerType());
	index++;
	for (Integer id : idCampi) {
	    q.setParameter(index, id, new IntegerType());
	    index++;
	}
	q.executeUpdate();
    }

    @Override
    public void deleteCampiDinamici(Integer idAttivita, List<Integer> idSnapshots, List<Integer> idCampi) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteAll senza passare il riferimento dell'attivita");
	}
	if (idSnapshots == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteAll senza l'array degli snapshot da ricalcolare");
	}
	if (idCampi == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteAll senza l'array di campi da eliminare");
	}
	String sql;
	SQLQuery q;
	int index = 0;
	if (!idSnapshots.isEmpty()) {
	    sql = "delete from i_attivitadyn2dati_snapshot where idcomune = ? ";
	    sql += " and fk_ias_id in (";
	    sql += StringUtils.repeat("?,", idSnapshots.size());
	    sql = sql.substring(0, sql.length() - 1);
	    sql += ")";
	    sql += " and fk_d2c_id in (";
	    sql += StringUtils.repeat("?,", idCampi.size());
	    sql = sql.substring(0, sql.length() - 1);
	    sql += ")";
	    q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2datiSnapshot.class);
	    q.setParameter(index, ORMHelper.getIdcomune(), new StringType());
	    index++;
	    for (Integer id : idSnapshots) {
		q.setParameter(index, id, new IntegerType());
		index++;
	    }
	    for (Integer id : idCampi) {
		q.setParameter(index, id, new IntegerType());
		index++;
	    }
	    q.executeUpdate();
	}
	//cancello dall'attività
	sql = "delete from i_attivitadyn2dati where idcomune = ? and fk_ia_id = ? and fk_d2c_id in (";
	sql += StringUtils.repeat("?,", idCampi.size());
	sql = sql.substring(0, sql.length() - 1);
	sql += ")";
	index = 0;
	q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2dati.class);
	q.setParameter(index, ORMHelper.getIdcomune(), new StringType());
	index++;
	q.setParameter(index, idAttivita, new IntegerType());
	index++;
	for (Integer id : idCampi) {
	    q.setParameter(index, id, new IntegerType());
	    index++;
	}
	q.executeUpdate();
    }

    @Override
    public void deleteCampiDinamiciDaSnapshot(Integer idSnapshot, Integer idScheda) {

	if (idSnapshot == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteAll senza passare il riferimento dello snapshot");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteAll senza passare il riferimento della scheda");
	}
	String sql = "delete  " + // 
		     "from " + // 
		     " i_attivitadyn2dati_snapshot " + // 
		     "where  " + // 
		     " idcomune = ? and " + // 
		     " fk_ias_id = ? and " + //
		     " fk_d2c_id in  " + // 
		     " ( select  dyn2_campi.id   from  dyn2_modellid " + //
		     "   inner join dyn2_campi on dyn2_modellid.idcomune = dyn2_campi.idcomune and dyn2_modellid.fk_d2c_id = dyn2_campi.id  " + //
		     "where " + // 
		     " dyn2_modellid.idcomune = ? and " + // 
		     " dyn2_modellid.fk_d2mt_id = ? )";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2datiSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idSnapshot);
	query.setString(2, ORMHelper.getIdcomune());
	query.setInteger(3, idScheda);
	query.executeUpdate();
    }

    @Override
    public void deleteSchedeDaSnapshots(Integer idAttivita, Integer idScheda, List<Integer> idCampi) {

	//1. Cancello la scheda dinamica dagli snapshot
	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteAll senza passare il riferimento dell'attivita");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteAll senza passare il riferimento della scheda");
	}
	if (idCampi == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteAll senza l'array di campi da eliminare");
	}
	String sql = "delete  from i_attivitadyn2mod_t_snapshot where  idcomune = ? and fk_ia_id = ? and fk_d2mt_id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2modTSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.setInteger(2, idScheda);
	query.executeUpdate();
	//2. Cancello i campi dinamici
	this.deleteCampiDinamici(idAttivita, idCampi);
    }

    @Override
    public void insertAutoIns(Integer idAttivita, Integer idSnapshot, Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiSnapshot) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insert senza passare il riferimento dell'attivita");
	}
	if (idSnapshot == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insert senza passare il riferimento allo snapshot");
	}
	if (campiSnapshot == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insert senza passare il set di dati da inserire");
	}
	String sql = "INSERT INTO I_ATTIVITADYN2DATI_SNAPSHOT(IDCOMUNE,FK_IA_ID,FK_D2C_ID,FK_IAS_ID,VALORE,INDICE,VALOREDECODIFICATO,INDICE_MOLTEPLICITA,AUTOINS) VALUES (?,?,?,?,?,?,?,?,?)";
	for (ChiaveCampoDinamicoPerData chiave : campiSnapshot.keySet()) {
	    ValoreCampoPresente valori = campiSnapshot.get(chiave);
	    SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2datiSnapshot.class);
	    query.setString(0, ORMHelper.getIdcomune());
	    query.setInteger(1, idAttivita);
	    query.setInteger(2, chiave.getIdCampo());
	    query.setInteger(3, idSnapshot);
	    query.setString(4, valori.getValore());
	    query.setInteger(5, chiave.getIndice());
	    query.setString(6, valori.getValoreDecodificato());
	    query.setInteger(7, chiave.getIndiceMolteplicita());
	    query.setInteger(8, 1);
	    query.executeUpdate();
	}
    }

    @Override
    public void insertAutoIns(Integer idAttivita, Map<ChiaveCampoDinamicoPerData, ValoreCampoPresente> campiSnapshot) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insert senza passare il riferimento dell'attivita");
	}
	if (campiSnapshot == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insert senza passare il set di dati da inserire");
	}
	String sql = "INSERT INTO I_ATTIVITADYN2DATI(IDCOMUNE,FK_IA_ID,FK_D2C_ID,VALORE,INDICE,VALOREDECODIFICATO,INDICE_MOLTEPLICITA,AUTOINS) VALUES (?,?,?,?,?,?,?,?)";
	for (ChiaveCampoDinamicoPerData chiave : campiSnapshot.keySet()) {
	    ValoreCampoPresente valori = campiSnapshot.get(chiave);
	    SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2dati.class);
	    query.setString(0, ORMHelper.getIdcomune());
	    query.setInteger(1, idAttivita);
	    query.setInteger(2, chiave.getIdCampo());
	    query.setString(3, valori.getValore());
	    query.setInteger(4, chiave.getIndice());
	    query.setString(5, valori.getValoreDecodificato());
	    query.setInteger(6, chiave.getIndiceMolteplicita());
	    query.setInteger(7, 1);
	    query.executeUpdate();
	}
    }

    private void insert(Integer idAttivita, Integer idSnapshot, List<CampoDinamicoAttivita> elenco) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insert senza passare il riferimento dell'attivita");
	}
	if (idSnapshot == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insert senza passare il riferimento allo snapshot");
	}
	if (elenco == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insert senza passare il set di dati da inserire");
	}
	String sql = "INSERT INTO I_ATTIVITADYN2DATI_SNAPSHOT(IDCOMUNE,FK_IA_ID,FK_D2C_ID,FK_IAS_ID,VALORE,INDICE,VALOREDECODIFICATO,INDICE_MOLTEPLICITA,AUTOINS) VALUES (?,?,?,?,?,?,?,?,?)";
	for (Iterator iterator = elenco.iterator(); iterator.hasNext();) {
	    CampoDinamicoAttivita campoDinamicoAttivita = (CampoDinamicoAttivita) iterator.next();
	    SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitadyn2datiSnapshot.class);
	    query.setString(0, ORMHelper.getIdcomune());
	    query.setInteger(1, idAttivita);
	    query.setInteger(2, campoDinamicoAttivita.getId());
	    query.setInteger(3, idSnapshot);
	    query.setString(4, campoDinamicoAttivita.getValore());
	    query.setInteger(5, campoDinamicoAttivita.getIndice());
	    query.setString(6, campoDinamicoAttivita.getValoreDecodificato());
	    query.setInteger(7, campoDinamicoAttivita.getIndiceMolteplicita());
	    query.setInteger(8, 1);
	    query.executeUpdate();
	}
    }

    @Override
    public Integer findIdSnapshotRappresentativo(Integer idAttivita) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException("Impossibile recuperare l'id dello snapshot rappresentativo senza passare l'attività");
	}
	String sql = "select " + " id " + "from " + " i_attivita_snapshot " + "where " + " i_attivita_snapshot.idcomune = ? and "
		+ " i_attivita_snapshot.fk_ia_id = ? and  " + " i_attivita_snapshot.data =  " + "   ( " + "    select "
		+ "     max(i_attivita_snapshot.data) " + "    from " + "     i_attivita_snapshot " + "    where "
		+ "     i_attivita_snapshot.idcomune = ? and " + "     i_attivita_snapshot.fk_ia_id = ? " + "   )";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(IAttivitaSnapshot.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAttivita);
	query.setString(2, ORMHelper.getIdcomune());
	query.setInteger(3, idAttivita);
	query.addScalar("id", Hibernate.INTEGER);
	return new Integer(query.uniqueResult().toString());
    }

    @Override
    public void sovrascriviSnapshot(Integer idAttivita, Integer idSnapshot, Integer idScheda, List<CampoDinamicoAttivita> elenco) {

	if (idSnapshot == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo sovrascriviSnapshot senza passare il riferimento dell'attivita");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo sovrascriviSnapshot senza passare il riferimento della scheda");
	}
	if (elenco == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo sovrascriviSnapshot senza passare la lista dei valori da inserire nei campi dinamici");
	}
	this.deleteCampiDinamiciDaSnapshot(idSnapshot, idScheda);
	this.insert(idAttivita, idSnapshot, elenco);
    }
}
