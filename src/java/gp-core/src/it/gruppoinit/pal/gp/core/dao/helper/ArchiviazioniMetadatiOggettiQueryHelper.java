package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;

import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.service.helper.IstanzePerArchiviazioneFilter;

public class ArchiviazioniMetadatiOggettiQueryHelper extends BaseQueryHelper {

    private Integer codiceistanza;
    private IstanzePerArchiviazioneFilter filter;

    public ArchiviazioniMetadatiOggettiQueryHelper(SessionFactoryImplementor sfi, Integer codiceistanza, IstanzePerArchiviazioneFilter filter) {

	String hibernateDialect = sfi.getDialect().toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	this.codiceistanza = codiceistanza;
	this.filter = filter;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	//	q.setString(0, ORMHelper.getIdcomune());
	//	q.setInteger(1, codiceistanza);
	//	q.setString(2, ORMHelper.getIdcomune());
	//	q.setInteger(3, codiceistanza);
	//	q.setString(4, ORMHelper.getIdcomune());
	//	q.setInteger(5, codiceistanza);
	//	q.setString(6, ORMHelper.getIdcomune());
	//	q.setInteger(7, codiceistanza);
	Calendar tDA = null;
	Calendar tA = null;
	if (filter != null) {
	    if (filter.getDallaData() != null) {
		tDA = Calendar.getInstance();
		tDA.setTime(filter.getDallaData());
		tDA.set(Calendar.HOUR, 0);
		tDA.set(Calendar.MINUTE, 0);
		tDA.set(Calendar.SECOND, 0);
		//q.setTimestamp(i, t.getTime());
	    }
	    if (filter.getAllaData() != null) {
		tA = Calendar.getInstance();
		tA.setTime(filter.getAllaData());
		tA.set(Calendar.HOUR, 23);
		tA.set(Calendar.MINUTE, 59);
		tA.set(Calendar.SECOND, 59);
		//q.setTimestamp(i, t.getTime());
	    }
	}
	int i = 0;
	// DOC ISTANZA
	q.setString(i, ORMHelper.getIdcomune());
	i++;
	q.setInteger(i, codiceistanza);
	i++;
	if (tDA != null) {
	    q.setTimestamp(i, tDA.getTime());
	    i++;
	}
	if (tA != null) {
	    q.setTimestamp(i, tA.getTime());
	    i++;
	}
	if (filter != null && !filter.getMimeTypeFileAmmessi().isEmpty()) {
	    for (String mime : filter.getMimeTypeFileAmmessi()) {
		q.setString(i, mime);
		i++;
	    }
	}
	// DOC ENDO
	q.setString(i, ORMHelper.getIdcomune());
	i++;
	q.setInteger(i, codiceistanza);
	i++;
	if (tDA != null) {
	    q.setTimestamp(i, tDA.getTime());
	    i++;
	}
	if (tA != null) {
	    q.setTimestamp(i, tA.getTime());
	    i++;
	}
	if (filter != null && !filter.getMimeTypeFileAmmessi().isEmpty()) {
	    for (String mime : filter.getMimeTypeFileAmmessi()) {
		q.setString(i, mime);
		i++;
	    }
	}
	// DOC MOV
	q.setString(i, ORMHelper.getIdcomune());
	i++;
	q.setInteger(i, codiceistanza);
	i++;
	if (tDA != null) {
	    q.setTimestamp(i, tDA.getTime());
	    i++;
	}
	if (tA != null) {
	    q.setTimestamp(i, tA.getTime());
	    i++;
	}
	if (filter != null && !filter.getMimeTypeFileAmmessi().isEmpty()) {
	    for (String mime : filter.getMimeTypeFileAmmessi()) {
		q.setString(i, mime);
		i++;
	    }
	}
	// DOC PROCURE
	q.setString(i, ORMHelper.getIdcomune());
	i++;
	q.setInteger(i, codiceistanza);
	i++;
	if (tDA != null) {
	    q.setTimestamp(i, tDA.getTime());
	    i++;
	}
	if (tA != null) {
	    q.setTimestamp(i, tA.getTime());
	    i++;
	}
	if (filter != null && !filter.getMimeTypeFileAmmessi().isEmpty()) {
	    for (String mime : filter.getMimeTypeFileAmmessi()) {
		q.setString(i, mime);
		i++;
	    }
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	//q.addScalar("IDCOMUNE", Hibernate.STRING);
	q.addScalar("codiceIstanza", Hibernate.INTEGER);
	q.addScalar("codiceOggetto", Hibernate.INTEGER);
	q.addScalar("origine", Hibernate.STRING);
	q.addScalar("descrizioneDocumento", Hibernate.STRING);
	q.addScalar("dataDocumento", Hibernate.DATE);
	q.addScalar("nomeFile", Hibernate.STRING);
	q.addScalar("dimensioneFile", Hibernate.INTEGER);
    }

    @Override
    public String buildQuery() {

	/*
		//TODO aggiungere schema
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT 'DOCUMENTIISTANZA' AS origine, DOCUMENTIISTANZA.CODICEISTANZA AS codiceIstanza, DOCUMENTIISTANZA.CODICEOGGETTO AS codiceOggetto, DOCUMENTIISTANZA.DOCUMENTO AS descrizioneDocumento, DOCUMENTIISTANZA.DATA AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE AS dimensioneFile FROM DOCUMENTIISTANZA INNER JOIN OGGETTI ON DOCUMENTIISTANZA.IDCOMUNE = OGGETTI.IDCOMUNE AND DOCUMENTIISTANZA.CODICEOGGETTO = OGGETTI.CODICEOGGETTO WHERE DOCUMENTIISTANZA.IDCOMUNE = ? AND DOCUMENTIISTANZA.CODICEISTANZA = ?");
		sql.append(" UNION ");
		sql.append("SELECT 'ISTANZEALLEGATI' AS origine, ISTANZEALLEGATI.CODICEISTANZA AS codiceIstanza, ISTANZEALLEGATI.CODICEOGGETTO AS codiceOggetto, ISTANZEALLEGATI.ALLEGATOEXTRA AS descrizioneDocumento, ISTANZEPROCEDIMENTI.DATAATTIVAZIONE AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE AS dimensioneFile FROM ISTANZEALLEGATI INNER JOIN OGGETTI ON ISTANZEALLEGATI.IDCOMUNE = OGGETTI.IDCOMUNE AND ISTANZEALLEGATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO INNER JOIN ISTANZEPROCEDIMENTI ON ISTANZEALLEGATI.IDCOMUNE = ISTANZEPROCEDIMENTI.IDCOMUNE AND ISTANZEALLEGATI.CODICEINVENTARIO = ISTANZEPROCEDIMENTI.CODICEINVENTARIO AND ISTANZEALLEGATI.CODICEISTANZA = ISTANZEPROCEDIMENTI.CODICEISTANZA WHERE ISTANZEALLEGATI.IDCOMUNE = ? AND ISTANZEALLEGATI.CODICEISTANZA = ?");
		sql.append(" UNION ");
		sql.append("SELECT 'MOVIMENTIALLEGATI' AS origine, MOVIMENTI.CODICEISTANZA AS codiceIstanza, MOVIMENTIALLEGATI.CODICEOGGETTO AS codiceOggetto, MOVIMENTIALLEGATI.DESCRIZIONE AS descrizioneDocumento, MOVIMENTI.DATA AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE AS dimensioneFile FROM MOVIMENTIALLEGATI INNER JOIN OGGETTI ON MOVIMENTIALLEGATI.IDCOMUNE = OGGETTI.IDCOMUNE AND MOVIMENTIALLEGATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO INNER JOIN MOVIMENTI ON MOVIMENTIALLEGATI.IDCOMUNE = MOVIMENTI.IDCOMUNE AND MOVIMENTIALLEGATI.CODICEMOVIMENTO = MOVIMENTI.CODICEMOVIMENTO WHERE MOVIMENTI.IDCOMUNE = ? AND MOVIMENTI.CODICEISTANZA = ?");
		sql.append(" UNION ");
		sql.append("SELECT 'ISTANZEPROCURE' AS origine, ISTANZEPROCURE.CODICEISTANZA AS codiceIstanza, OGGETTI.CODICEOGGETTO AS codiceOggetto, OGGETTI.NOMEFILE AS descrizioneDocumento, ISTANZE.DATA AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE AS dimensioneFile FROM ISTANZEPROCURE INNER JOIN OGGETTI ON ISTANZEPROCURE.IDCOMUNE = OGGETTI.IDCOMUNE AND ISTANZEPROCURE.CODICEOGGETTOPROCURA = OGGETTI.CODICEOGGETTO INNER JOIN ISTANZE ON ISTANZEPROCURE.IDCOMUNE = ISTANZE.IDCOMUNE AND ISTANZEPROCURE.CODICEISTANZA = ISTANZE.CODICEISTANZA WHERE ISTANZEPROCURE.IDCOMUNE = ? AND ISTANZEPROCURE.CODICEISTANZA = ?");
		*/
	StringBuffer sql = new StringBuffer();
	sql = sql.append(builsQueryDocumentiIstanza()).append(" ");
	sql = sql.append(" ").append("UNION").append(" ");
	sql = sql.append(" ").append(builsQueryDocumentiEndo()).append(" ");
	sql = sql.append(" ").append("UNION").append(" ");
	sql = sql.append(" ").append(builsQueryDocumentiMovimenti()).append(" ");
	sql = sql.append(" ").append("UNION").append(" ");
	sql = sql.append(" ").append(builsQueryDocumentiProcure()).append(" ");
	return sql.toString();
    }

    private String builsQueryDocumentiIstanza() {

	StringBuffer sql = new StringBuffer(
		"SELECT 'DOCUMENTIISTANZA' AS origine, DOCUMENTIISTANZA.CODICEISTANZA AS codiceIstanza, DOCUMENTIISTANZA.CODICEOGGETTO AS codiceOggetto,  ");
	sql = sql.append(" ").append(
		"DOCUMENTIISTANZA.DOCUMENTO AS descrizioneDocumento, DOCUMENTIISTANZA.DATA AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE  AS dimensioneFile")
		.append(" ");
	sql = sql.append(" ").append(
		"FROM DOCUMENTIISTANZA INNER JOIN OGGETTI ON DOCUMENTIISTANZA.IDCOMUNE = OGGETTI.IDCOMUNE AND DOCUMENTIISTANZA.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	//	sql = sql
	//		.append(" ")
	//		.append("LEFT JOIN ISTANZE ON DOCUMENTIISTANZA.IDCOMUNE = ISTANZE.IDCOMUNE AND DOCUMENTIISTANZA.CODICEISTANZA = ISTANZE.CODICEISTANZA")
	//		.append(" ");
	sql = sql.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_OGGETTI ON ARCHIVIAZIONI_OGGETTI.IDCOMUNE = OGGETTI.IDCOMUNE AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sql = sql.append(" ").append(
		"INNER JOIN OGGETTI_METADATI ON OGGETTI_METADATI.IDCOMUNE = OGGETTI.IDCOMUNE AND OGGETTI_METADATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sql = sql.append(" ").append("WHERE DOCUMENTIISTANZA.IDCOMUNE  = ?").append(" ");
	sql = sql.append(" ").append("AND DOCUMENTIISTANZA.CODICEISTANZA = ?").append(" ");
	sql = sql.append(" ").append("AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO  IS NULL").append(" ");
	if (filter != null && filter.getDallaData() != null) {
	    sql = sql.append(" ").append("AND DOCUMENTIISTANZA.DATA >= ?").append(" ");
	}
	if (filter != null && filter.getAllaData() != null) {
	    sql = sql.append(" ").append("AND DOCUMENTIISTANZA.DATA <= ?").append(" ");
	}
	if (filter != null && !filter.getMimeTypeFileAmmessi().isEmpty()) {
	    String p1 = StringUtils.repeat("?,", filter.getMimeTypeFileAmmessi().size());
	    p1 = StringUtils.removeEnd(p1, ",");
	    sql = sql.append(" ").append("AND OGGETTI_METADATI.CHIAVE = 'FILE_CONTENT_TYPE'").append(" ");
	    sql = sql.append(" ").append("AND OGGETTI_METADATI.VALORE IN (").append(p1).append(")").append(" ");
	}
	sql = sql.append(" ").append("").append(" ");
	sql = sql.append(" ").append("").append(" ");
	// Elimina i file pdf in conservazione sospesa, non firmati
	sql = sql.append(" AND NOT EXISTS ( SELECT oggetti_metadati.chiave  FROM oggetti_metadati  ");
	sql = sql.append(" WHERE oggetti_metadati.idcomune = oggetti.idcomune AND oggetti_metadati.codiceoggetto = oggetti.codiceoggetto ");
	sql = sql.append(" AND oggetti_metadati.chiave='CONSERVAZIONE_DOC_SOSPESA' AND oggetti_metadati.valore='SI' )");
	return sql.toString();
    }

    private String builsQueryDocumentiEndo() {

	StringBuffer sql = new StringBuffer();
	sql = sql.append(" ").append(
		"SELECT 'ISTANZEALLEGATI' AS origine,ISTANZEALLEGATI.CODICEISTANZA AS codiceIstanza,ISTANZEALLEGATI.CODICEOGGETTO AS codiceOggetto,")
		.append(" ");
	sql = sql.append(" ").append(
		"ISTANZEALLEGATI.ALLEGATOEXTRA AS descrizioneDocumento, ISTANZEALLEGATI.DATA AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE AS dimensioneFile")
		.append(" ");
	sql = sql.append(" ").append(
		"FROM ISTANZEALLEGATI INNER JOIN OGGETTI ON ISTANZEALLEGATI.IDCOMUNE = OGGETTI.IDCOMUNE AND ISTANZEALLEGATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sql = sql.append(" ").append(
		"INNER JOIN ISTANZEPROCEDIMENTI ON ISTANZEALLEGATI.IDCOMUNE  = ISTANZEPROCEDIMENTI.IDCOMUNE AND ISTANZEALLEGATI.CODICEINVENTARIO = ISTANZEPROCEDIMENTI.CODICEINVENTARIO AND ISTANZEALLEGATI.CODICEISTANZA = ISTANZEPROCEDIMENTI.CODICEISTANZA")
		.append(" ");
	//	sb = sb.append(" ")
	//		.append("LEFT JOIN ISTANZE ON ISTANZEALLEGATI.IDCOMUNE = ISTANZE.IDCOMUNE AND ISTANZEALLEGATI.CODICEISTANZA = ISTANZE.CODICEISTANZA")
	//		.append(" ");
	sql = sql.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_OGGETTI ON ARCHIVIAZIONI_OGGETTI.IDCOMUNE = OGGETTI.IDCOMUNE AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sql = sql.append(" ").append(
		"LEFT JOIN OGGETTI_METADATI ON OGGETTI_METADATI.IDCOMUNE = OGGETTI.IDCOMUNE AND OGGETTI_METADATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sql = sql.append(" ").append("WHERE ISTANZEALLEGATI.IDCOMUNE = ?").append(" ");
	sql = sql.append(" ").append("AND ISTANZEALLEGATI.CODICEISTANZA = ?").append(" ");
	sql = sql.append(" ").append("AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO  IS NULL").append(" ");
	//sql = sql.append(" ").append("AND ARCHIVIAZIONI_ISTANZE.ID IS NULL").append(" ");
	if (filter != null && filter.getDallaData() != null) {
	    sql = sql.append(" ").append("AND ISTANZEALLEGATI.DATA >= ?").append(" ");
	}
	if (filter != null && filter.getAllaData() != null) {
	    sql = sql.append(" ").append("AND ISTANZEALLEGATI.DATA <= ?").append(" ");
	}
	if (filter != null && !filter.getMimeTypeFileAmmessi().isEmpty()) {
	    String p1 = StringUtils.repeat("?,", filter.getMimeTypeFileAmmessi().size());
	    p1 = StringUtils.removeEnd(p1, ",");
	    sql = sql.append(" ").append("AND OGGETTI_METADATI.CHIAVE = 'FILE_CONTENT_TYPE'").append(" ");
	    sql = sql.append(" ").append("AND OGGETTI_METADATI.VALORE IN (").append(p1).append(")").append(" ");
	}
	// Elimina i file pdf in conservazione sospesa, non firmati
	sql = sql.append(" AND NOT EXISTS ( SELECT oggetti_metadati.chiave  FROM oggetti_metadati  ");
	sql = sql.append(" WHERE oggetti_metadati.idcomune = oggetti.idcomune AND oggetti_metadati.codiceoggetto = oggetti.codiceoggetto ");
	sql = sql.append(" AND oggetti_metadati.chiave='CONSERVAZIONE_DOC_SOSPESA' AND oggetti_metadati.valore='SI' )");
	return sql.toString();
    }

    private String builsQueryDocumentiMovimenti() {

	StringBuffer sql = new StringBuffer();
	sql = sql.append(" ").append(
		"SELECT 'MOVIMENTIALLEGATI' AS origine, MOVIMENTI.CODICEISTANZA AS codiceIstanza, MOVIMENTIALLEGATI.CODICEOGGETTO AS codiceOggetto,")
		.append(" ");
	sql = sql.append(" ").append(
		"MOVIMENTIALLEGATI.DESCRIZIONE AS descrizioneDocumento, MOVIMENTIALLEGATI.DATAREGISTRAZIONE AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE AS dimensioneFile")
		.append(" ");
	sql = sql.append(" ").append(
		"FROM MOVIMENTIALLEGATI INNER JOIN OGGETTI ON MOVIMENTIALLEGATI.IDCOMUNE  = OGGETTI.IDCOMUNE AND MOVIMENTIALLEGATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO ")
		.append(" ");
	sql = sql.append(" ").append(
		"INNER JOIN MOVIMENTI ON MOVIMENTIALLEGATI.IDCOMUNE = MOVIMENTI.IDCOMUNE AND MOVIMENTIALLEGATI.CODICEMOVIMENTO = MOVIMENTI.CODICEMOVIMENTO")
		.append(" ");
	sql = sql.append(" ").append("LEFT JOIN ISTANZE ON MOVIMENTI.IDCOMUNE = ISTANZE.IDCOMUNE AND MOVIMENTI.CODICEISTANZA = ISTANZE.CODICEISTANZA")
		.append(" ");
	sql = sql.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_OGGETTI ON ARCHIVIAZIONI_OGGETTI.IDCOMUNE = OGGETTI.IDCOMUNE AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sql = sql.append(" ").append(
		"INNER JOIN OGGETTI_METADATI ON OGGETTI_METADATI.IDCOMUNE = OGGETTI.IDCOMUNE AND OGGETTI_METADATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sql = sql.append(" ").append("WHERE MOVIMENTI.IDCOMUNE = ? AND MOVIMENTI.CODICEISTANZA = ?").append(" ");
	sql = sql.append(" ").append("AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO  IS NULL").append(" ");
	if (filter != null && filter.getDallaData() != null) {
	    sql = sql.append(" ").append("AND MOVIMENTIALLEGATI.DATAREGISTRAZIONE >= ?").append(" ");
	}
	if (filter != null && filter.getAllaData() != null) {
	    sql = sql.append(" ").append("AND MOVIMENTIALLEGATI.DATAREGISTRAZIONE <= ?").append(" ");
	}
	if (filter != null && !filter.getMimeTypeFileAmmessi().isEmpty()) {
	    String p1 = StringUtils.repeat("?,", filter.getMimeTypeFileAmmessi().size());
	    p1 = StringUtils.removeEnd(p1, ",");
	    sql = sql.append(" ").append("AND OGGETTI_METADATI.CHIAVE = 'FILE_CONTENT_TYPE'").append(" ");
	    sql = sql.append(" ").append("AND OGGETTI_METADATI.VALORE IN (").append(p1).append(")").append(" ");
	}
	// Elimina i file pdf in conservazione sospesa, non firmati
	sql = sql.append(" AND NOT EXISTS ( SELECT oggetti_metadati.chiave  FROM oggetti_metadati  ");
	sql = sql.append(" WHERE oggetti_metadati.idcomune = oggetti.idcomune AND oggetti_metadati.codiceoggetto = oggetti.codiceoggetto ");
	sql = sql.append(" AND oggetti_metadati.chiave='CONSERVAZIONE_DOC_SOSPESA' AND oggetti_metadati.valore='SI' )");
	return sql.toString();
    }

    private String builsQueryDocumentiProcure() {

	StringBuffer sql = new StringBuffer();
	sql = sql.append(" ").append(
		"SELECT 'ISTANZEPROCURE' AS origine,ISTANZEPROCURE.CODICEISTANZA AS codiceIstanza, OGGETTI.CODICEOGGETTO AS codiceOggetto,OGGETTI.NOMEFILE AS descrizioneDocumento,")
		.append(" ");
	sql = sql.append(" ").append("ISTANZEPROCURE.DATA AS dataDocumento,OGGETTI.NOMEFILE AS nomeFile,OGGETTI.DIMENSIONE_FILE AS dimensioneFile")
		.append(" ");
	sql = sql.append(" ").append(
		"FROM ISTANZEPROCURE INNER JOIN OGGETTI ON ISTANZEPROCURE.IDCOMUNE = OGGETTI.IDCOMUNE AND ISTANZEPROCURE.CODICEOGGETTOPROCURA = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sql = sql.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_OGGETTI ON ARCHIVIAZIONI_OGGETTI.IDCOMUNE = OGGETTI.IDCOMUNE AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sql = sql.append(" ").append(
		"INNER JOIN OGGETTI_METADATI ON OGGETTI_METADATI.IDCOMUNE = OGGETTI.IDCOMUNE AND OGGETTI_METADATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sql = sql.append(" ").append("WHERE ISTANZEPROCURE.IDCOMUNE = ? AND ISTANZEPROCURE.CODICEISTANZA = ?").append(" ");
	sql = sql.append(" ").append("AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO  IS NULL").append(" ");
	if (filter != null && filter.getDallaData() != null) {
	    sql = sql.append(" ").append("AND ISTANZEPROCURE.DATA >= ?").append(" ");
	}
	if (filter != null && filter.getAllaData() != null) {
	    sql = sql.append(" ").append("AND ISTANZEPROCURE.DATA <= ?").append(" ");
	}
	if (filter != null && !filter.getMimeTypeFileAmmessi().isEmpty()) {
	    String p1 = StringUtils.repeat("?,", filter.getMimeTypeFileAmmessi().size());
	    p1 = StringUtils.removeEnd(p1, ",");
	    sql = sql.append(" ").append("AND OGGETTI_METADATI.CHIAVE = 'FILE_CONTENT_TYPE'").append(" ");
	    sql = sql.append(" ").append("AND OGGETTI_METADATI.VALORE IN (").append(p1).append(")").append(" ");
	}
	// Elimina i file pdf in conservazione sospesa, non firmati
	sql = sql.append(" AND NOT EXISTS ( SELECT oggetti_metadati.chiave  FROM oggetti_metadati  ");
	sql = sql.append(" WHERE oggetti_metadati.idcomune = oggetti.idcomune AND oggetti_metadati.codiceoggetto = oggetti.codiceoggetto ");
	sql = sql.append(" AND oggetti_metadati.chiave='CONSERVAZIONE_DOC_SOSPESA' AND oggetti_metadati.valore='SI' )");
	return sql.toString();
    }
}
