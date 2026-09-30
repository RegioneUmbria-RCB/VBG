package it.gruppoinit.pal.gp.core.features.sistema.upgr;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.sistema.upgr.model.IdComuneCodiceBean;
import it.gruppoinit.pal.gp.core.features.sistema.upgr.model.IdComuneTipoMovimentoBean;
import it.gruppoinit.pal.gp.core.utils.LoggerArchiviBackoffice;

@SuppressWarnings("rawtypes")
@Repository
public class MySQLTipimovimentoUtfBinDAOImpl extends BaseDAOImpl implements IMySQLTipimovimentoUtfBinDAO {

    private static final String COLLATE_TIPIMOVIMENTO = "ALTER TABLE TIPIMOVIMENTO MODIFY TIPOMOVIMENTO VARCHAR(8) CHARACTER SET utf8 COLLATE utf8_general_ci";
    private static final String COLLATE_TIPIMOVIMENTO_DIS = "ALTER TABLE tipimovimento_dis MODIFY TIPOMOVIMENTO VARCHAR(8) CHARACTER SET utf8 COLLATE utf8_general_ci";
    private static final String QUERY_FILTER_COLLATION_TIPIMOVIMENTO = "SELECT TABLE_NAME, COLUMN_NAME, COLLATION_NAME FROM information_schema.columns WHERE NOT COLLATION_NAME = 'utf8_general_ci' AND  table_name='TIPIMOVIMENTO' AND COLUMN_NAME='TIPOMOVIMENTO'";

    @SuppressWarnings("unchecked")
    @Override
    public List<String> upgrTipiMovimentiDoppi() {

	LoggerArchiviBackoffice.log("Inizio UPGR per tipimovimento doppi");
	List<String> errori = new ArrayList<String>();
	if (!checkEseguiProcedura()) {
	    LoggerArchiviBackoffice.log("UPGR per utf8bin movimenti non applicabile");
	    return errori;
	}
	String sql = "select tipimovimento.idcomune as idcomune,upper(tipomovimento) as tipomovimento  " + //
		" from tipimovimento  " + //
		" group by tipimovimento.idcomune,upper(tipomovimento)  " + //
		" having count(*)>1 " + //
		" order by tipimovimento.idcomune";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Tipimovimento.class);
	query.addScalar("idcomune", Hibernate.STRING);
	query.addScalar("tipomovimento", Hibernate.STRING);
	query.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IdComuneTipoMovimentoBean.class));
	List<IdComuneTipoMovimentoBean> list = query.list();
	LoggerArchiviBackoffice.log("Trovati {} movimenti duplicati " + list.size());
	LoggerArchiviBackoffice.log("\n===================================================\n");
	LoggerArchiviBackoffice.log("\n==== INIZIO PROCEDURA MODIFICA MOVIMENTI DOPPI ====\n");
	disabilitaChiavi();
	sistemaTipiMovimentoDis();
	if (list.size() > 0) {
	    for (IdComuneTipoMovimentoBean idMov : list) {
		LoggerArchiviBackoffice.log("Verifico idcomune " + idMov.getIdcomune() + ", tipomovimento " + idMov.getTipomovimento());
		SQLQuery queryFind = getSession().createSQLQuery(getSQLFind()).addSynchronizedEntityClass(Tipimovimento.class);
		queryFind.addScalar("idcomune", Hibernate.STRING);
		queryFind.addScalar("tipomovimento", Hibernate.STRING);
		queryFind.addScalar("tipomovsubstr", Hibernate.STRING);
		//
		queryFind.setString("idcomune", idMov.getIdcomune());
		queryFind.setString("tipomovimento", idMov.getTipomovimento());
		queryFind.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IdComuneTipoMovimentoBean.class));
		List<IdComuneTipoMovimentoBean> listMovs = queryFind.list();
		int i = 1;
		for (IdComuneTipoMovimentoBean idb : listMovs) {
		    String nuovoCodice = ("E" + (i++) + idb.getTipomovsubstr()).toUpperCase();
		    LoggerArchiviBackoffice.log("Modifico idcomune " +
			    idMov.getIdcomune() +
			    ", tipomovimento " +
			    idMov.getTipomovimento() +
			    " con il nuovo codice " +
			    nuovoCodice);
		    SQLQuery queryMetadati = getSession().createSQLQuery(getQueryMetadati(idb, nuovoCodice))
			    .addSynchronizedEntityClass(Tipimovimento.class);
		    queryMetadati.addScalar("querydaeseguire", Hibernate.STRING);
		    List<String> daEseguire = queryMetadati.list();
		    for (String queryDaEseguire : daEseguire) {
			LoggerArchiviBackoffice.log("QUERY == {}" + queryDaEseguire);
			getSession().createSQLQuery(queryDaEseguire).executeUpdate();
		    }
		    aggiornaTipiMovimento(idb, nuovoCodice);
		}
	    }
	}
	abilitaChiavi();
	LoggerArchiviBackoffice.log("\n==== FINE PROCEDURA MODIFICA MOVIMENTI DOPPI ======\n");
	LoggerArchiviBackoffice.log("\n===================================================\n");
	LoggerArchiviBackoffice.log("\n===================================================\n");
	return errori;
    }

    @Override
    public List<String> upgrCollateUtf8() {

	LoggerArchiviBackoffice.log("Inizio UPGR per utf8bin movimenti");
	List<String> errori = new ArrayList<String>();
	if (!checkEseguiProcedura()) {
	    LoggerArchiviBackoffice.log("UPGR per utf8bin movimenti non applicabile");
	    return errori;
	}
	LoggerArchiviBackoffice.log("\n===================================================\n");
	LoggerArchiviBackoffice.log("\n======= INIZIO PROCEDURA MODIFICA COLLATE =========\n");
	SQLQuery query = getSession().createSQLQuery(QUERY_FILTER_COLLATION_TIPIMOVIMENTO).addSynchronizedEntityClass(Tipimovimento.class);
	if (query.list().size() > 0) {
	    disabilitaChiavi();
	    modificaCollateTabelle();
	    abilitaChiavi();
	}
	LoggerArchiviBackoffice.log("\n========= FINE PROCEDURA MODIFICA COLLATE =========\n");
	LoggerArchiviBackoffice.log("\n===================================================\n");
	LoggerArchiviBackoffice.log("\n===================================================\n");
	return errori;
    }

    @SuppressWarnings("unchecked")
    private void sistemaTipiMovimentoDis() {

	LoggerArchiviBackoffice.log("procedo ad eliminare i movimenti non esistenti di TIPIMOVIMENTO_DIS");
	String sql = "SELECT " + //
		" tipimovimento_dis.idcomune as idcomune, " + //
		" tipimovimento_dis.id as codice" + //
		" FROM " + //
		" tipimovimento_dis left " + //
		" JOIN tipimovimento ON tipimovimento.idcomune = tipimovimento_dis.idcomune " + //
		" AND tipimovimento.tipomovimento = tipimovimento_dis.tipomovimento " + //
		" WHERE " + //
		" tipimovimento.tipomovimento IS NULL";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.addScalar("idcomune", Hibernate.STRING);
	query.addScalar("codice", Hibernate.INTEGER);
	query.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IdComuneCodiceBean.class));
	List<IdComuneCodiceBean> daEseguire = query.list();
	LoggerArchiviBackoffice.log("trovati {}  movimenti non esistenti in TIPIMOVIMENTO_DIS" + daEseguire.size());
	for (IdComuneCodiceBean pkId : daEseguire) {
	    String delete = "delete from tipimovimento_dis where idcomune=:idcomune and id=:codice";
	    SQLQuery queryDelete = getSession().createSQLQuery(delete);
	    queryDelete.setString("idcomune", pkId.getIdcomune());
	    queryDelete.setInteger("codice", pkId.getCodice());
	    queryDelete.executeUpdate();
	}
    }

    @SuppressWarnings("unchecked")
    private void modificaCollateTabelle() {

	// modifico il collate
	SQLQuery queryMetadati = getSession().createSQLQuery(getQueryMetadatiTabelleCollateUTF8());
	queryMetadati.addScalar("querydaeseguire", Hibernate.STRING);
	List<String> daEseguire = queryMetadati.list();
	for (String queryDaEseguire : daEseguire) {
	    getSession().createSQLQuery(queryDaEseguire).executeUpdate();
	}
	LoggerArchiviBackoffice.log("QUERY == {}" + COLLATE_TIPIMOVIMENTO_DIS);
	getSession().createSQLQuery(COLLATE_TIPIMOVIMENTO_DIS).executeUpdate();
	LoggerArchiviBackoffice.log("QUERY == {}" + COLLATE_TIPIMOVIMENTO);
	getSession().createSQLQuery(COLLATE_TIPIMOVIMENTO).executeUpdate();
	LoggerArchiviBackoffice.log("modificaCollateTabelle done ");
    }

    private void aggiornaTipiMovimento(IdComuneTipoMovimentoBean idb, String nuovoCodice) {

	String sql = "update tipimovimento set tipomovimento=:nuovocodice where idcomune=:idcomune and tipomovimento=:tipomovimento";
	LoggerArchiviBackoffice.log("QUERY == {}" +
		sql.replace(":nuovocodice", nuovoCodice).replace(":idcomune", idb.getIdcomune()).replace(":tipomovimento", idb.getTipomovimento()));
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Tipimovimento.class);
	query.setString("nuovocodice", nuovoCodice);
	query.setString("idcomune", idb.getIdcomune());
	query.setString("tipomovimento", idb.getTipomovimento());
	query.executeUpdate();
    }

    private boolean checkEseguiProcedura() {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	DialettoEnum db = DialettoEnum.fromHibernateDialect(sfi.getDialect().toString());
	// verificare se Mysql
	LoggerArchiviBackoffice.log("checkEseguiProcedura " + db);
	return db.compareTo(DialettoEnum.MYSQL) == 0;
    }

    private void abilitaChiavi() {

	LoggerArchiviBackoffice.log("\n===============================\n");
	String sql = "SET FOREIGN_KEY_CHECKS = 1";
	LoggerArchiviBackoffice.log(sql);
	LoggerArchiviBackoffice.log("\n===============================\n");
	SQLQuery query = getSession().createSQLQuery(sql);
	query.executeUpdate();
    }

    private void disabilitaChiavi() {

	LoggerArchiviBackoffice.log("\n===============================\n");
	String sql = "SET FOREIGN_KEY_CHECKS = 0";
	LoggerArchiviBackoffice.log(sql);
	LoggerArchiviBackoffice.log("\n===============================\n");
	SQLQuery query = getSession().createSQLQuery(sql);
	query.executeUpdate();
    }

    private String getQueryMetadati(IdComuneTipoMovimentoBean idb, String nuovoTipoMov) {

	String sql = "SELECT CONCAT_WS(' ' ,'UPDATE',TABLE_NAME,'SET ',COLUMN_NAME,'=''" +
		nuovoTipoMov +
		"'' WHERE IDCOMUNE=''" +
		idb.getIdcomune() +
		"'' AND ',COLUMN_NAME,'=''" +
		idb.getTipomovimento() +
		"''') as querydaeseguire" + //
		" FROM " + //
		"  INFORMATION_SCHEMA.KEY_COLUMN_USAGE " + //
		" WHERE " + //
		"  REFERENCED_TABLE_NAME = 'TIPIMOVIMENTO' AND " + //
		"  REFERENCED_COLUMN_NAME = 'TIPOMOVIMENTO' " + //
		"  ORDER BY TABLE_NAME";
	return sql;
    }

    private String getQueryMetadatiTabelleCollateUTF8() {

	return "SELECT CONCAT_WS(' ' ,'ALTER TABLE',CU.TABLE_NAME,'MODIFY',CU.COLUMN_NAME,'VARCHAR(8) CHARACTER SET utf8 COLLATE utf8_general_ci') AS querydaeseguire " + //
		" FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE CU JOIN information_schema.columns CO ON CU.TABLE_NAME=CO.TABLE_NAME AND CU.COLUMN_NAME=CO.COLUMN_NAME " + //
		" WHERE CU.REFERENCED_TABLE_NAME = 'TIPIMOVIMENTO' AND CU.REFERENCED_COLUMN_NAME = 'TIPOMOVIMENTO' " + //
		" AND NOT COLLATION_NAME = 'utf8_general_ci' ORDER BY CU.TABLE_NAME";
    }

    private String getSQLFind() {

	return "select idcomune as idcomune,tipomovimento as tipomovimento, substr(tipomovimento,3) as tipomovsubstr " + //
		" from tipimovimento " + //
		" where idcomune = :idcomune " + //
		"and upper(tipomovimento) = :tipomovimento";
    }

    @Override
    public Class getEntityClass() {

	return null;
    }
}
