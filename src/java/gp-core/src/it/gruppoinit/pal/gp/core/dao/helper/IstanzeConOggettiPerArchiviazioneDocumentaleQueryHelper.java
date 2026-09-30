package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;

import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.service.helper.IstanzePerArchiviazioneFilter;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoPratica;

public class IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper extends BaseQueryHelper {

    private TipoDocumentoPratica tipoDocumentoPratica;
    private IstanzePerArchiviazioneFilter filter;
    private List<String> mimeTypeFileAmmessi;

    public IstanzeConOggettiPerArchiviazioneDocumentaleQueryHelper(SessionFactoryImplementor sfi, IstanzePerArchiviazioneFilter filter,
	    List<String> mimeTypeFileAmmessi, TipoDocumentoPratica tipoDocumentoPratica) {

	String hibernateDialect = sfi.getDialect().toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	this.tipoDocumentoPratica = tipoDocumentoPratica;
	this.mimeTypeFileAmmessi = mimeTypeFileAmmessi;
	this.filter = filter;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int i = 0;
	q.setString(i, ORMHelper.getIdcomune());
	i++;
	q.setString(i, ORMHelper.getSoftware());
	i++;
	if (filter.getDallaData() != null) {
	    Calendar t = Calendar.getInstance();
	    t.setTime(filter.getDallaData());
	    t.set(Calendar.HOUR, 0);
	    t.set(Calendar.MINUTE, 0);
	    t.set(Calendar.SECOND, 0);
	    q.setTimestamp(i, t.getTime());
	    i++;
	}
	if (filter.getAllaData() != null) {
	    Calendar t = Calendar.getInstance();
	    t.setTime(filter.getAllaData());
	    t.set(Calendar.HOUR, 23);
	    t.set(Calendar.MINUTE, 59);
	    t.set(Calendar.SECOND, 59);
	    q.setTimestamp(i, t.getTime());
	    i++;
	}
	if (!mimeTypeFileAmmessi.isEmpty()) {
	    for (String mime : mimeTypeFileAmmessi) {
		q.setString(i, mime);
		i++;
	    }
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("numeroIstanza", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	switch (tipoDocumentoPratica) {
	    case DOC_ISTANZA:
		return builsQueryDocumentiIstanza();
	    case DOC_ENDO:
		return builsQueryDocumentiEndo();
	    case DOC_MOVIMENTI:
		return builsQueryDocumentiMovimento();
	    case DOC_PROCURE:
		return builsQueryDocumentiProcure();
	    default:
		break;
	}
	return null;
    }

    private String builsQueryDocumentiProcure() {

	StringBuffer sb = new StringBuffer("SELECT ISTANZEPROCURE.CODICEISTANZA AS codiceistanza, ISTANZE.NUMEROISTANZA AS numeroIstanza ");
	sb = sb.append(" ").append(
		" FROM ISTANZEPROCURE INNER JOIN OGGETTI ON ISTANZEPROCURE.IDCOMUNE = OGGETTI.IDCOMUNE AND ISTANZEPROCURE.CODICEOGGETTOPROCURA = OGGETTI.CODICEOGGETTO ")
		.append(" ");
	sb = sb.append(" ")
		.append("LEFT JOIN ISTANZE ON ISTANZEPROCURE.IDCOMUNE = ISTANZE.IDCOMUNE AND ISTANZEPROCURE.CODICEISTANZA = ISTANZE.CODICEISTANZA")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_OGGETTI ON ARCHIVIAZIONI_OGGETTI.IDCOMUNE = OGGETTI.IDCOMUNE AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_ISTANZE ON ARCHIVIAZIONI_ISTANZE.IDCOMUNE = ISTANZE.IDCOMUNE AND ARCHIVIAZIONI_ISTANZE.CODICEISTANZA = ISTANZE.CODICEISTANZA")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN OGGETTI_METADATI ON OGGETTI_METADATI.IDCOMUNE = OGGETTI.IDCOMUNE AND OGGETTI_METADATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sb = sb.append(" ").append("WHERE ISTANZEPROCURE.IDCOMUNE = ? AND ISTANZE.SOFTWARE = ? ").append(" ");
	sb = sb.append(" ").append("AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO  IS NULL").append(" ");
	sb = sb.append(" ").append("AND").append(" ");
	sb = sb.append(" ").append("(").append(" ");
	sb = sb.append(" ").append("ARCHIVIAZIONI_ISTANZE.ID IS NULL").append(" ");
	sb = sb.append(" ").append("OR").append(" ");
	sb = sb.append(" ").append("ARCHIVIAZIONI_ISTANZE.ID IS NOT NULL AND ARCHIVIAZIONI_ISTANZE.ESCLUSA =  0").append(" ");
	sb = sb.append(" ").append(")").append(" ");
	if (filter.getDallaData() != null) {
	    sb = sb.append(" ").append("AND ISTANZEPROCURE.DATA >= ?").append(" ");
	}
	if (filter.getAllaData() != null) {
	    sb = sb.append(" ").append("AND ISTANZEPROCURE.DATA <= ?").append(" ");
	}
	if (!mimeTypeFileAmmessi.isEmpty()) {
	    String p1 = StringUtils.repeat("?,", mimeTypeFileAmmessi.size());
	    p1 = StringUtils.removeEnd(p1, ",");
	    sb = sb.append(" ").append("AND OGGETTI_METADATI.CHIAVE = 'FILE_CONTENT_TYPE'").append(" ");
	    sb = sb.append(" ").append("AND OGGETTI_METADATI.VALORE IN (").append(p1).append(")").append(" ");
	}
	// Elimina i file pdf in conservazione sospesa, non firmati in caso sia attiva l'opzione invia solo firmati
	sb = sb.append(" AND NOT EXISTS ( SELECT oggetti_metadati.chiave  FROM oggetti_metadati  ");
	sb = sb.append(" WHERE oggetti_metadati.idcomune = oggetti.idcomune AND oggetti_metadati.codiceoggetto = oggetti.codiceoggetto ");
	sb = sb.append(" AND oggetti_metadati.chiave='CONSERVAZIONE_DOC_SOSPESA' AND oggetti_metadati.valore='SI' )");
	sb = sb.append(" ").append("GROUP BY ISTANZEPROCURE.CODICEISTANZA,ISTANZE.NUMEROISTANZA ");
	sb = sb.append(" ").append("ORDER BY codiceIstanza").append(" ");
	return sb.toString();
    }

    private String builsQueryDocumentiMovimento() {

	StringBuffer sb = new StringBuffer("SELECT MOVIMENTI.CODICEISTANZA AS codiceistanza, ISTANZE.NUMEROISTANZA AS numeroIstanza ");
	sb = sb.append(" ").append(
		" FROM MOVIMENTIALLEGATI INNER JOIN OGGETTI ON MOVIMENTIALLEGATI.IDCOMUNE = OGGETTI.IDCOMUNE AND MOVIMENTIALLEGATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO ")
		.append(" ");
	sb = sb.append(" ").append(
		"INNER JOIN MOVIMENTI ON MOVIMENTIALLEGATI.IDCOMUNE = MOVIMENTI.IDCOMUNE AND MOVIMENTIALLEGATI.CODICEMOVIMENTO = MOVIMENTI.CODICEMOVIMENTO")
		.append(" ");
	sb = sb.append(" ").append("LEFT JOIN ISTANZE ON MOVIMENTI.IDCOMUNE = ISTANZE.IDCOMUNE AND MOVIMENTI.CODICEISTANZA = ISTANZE.CODICEISTANZA")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_OGGETTI ON ARCHIVIAZIONI_OGGETTI.IDCOMUNE = OGGETTI.IDCOMUNE AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_ISTANZE ON ARCHIVIAZIONI_ISTANZE.IDCOMUNE = ISTANZE.IDCOMUNE AND ARCHIVIAZIONI_ISTANZE.CODICEISTANZA = ISTANZE.CODICEISTANZA")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN OGGETTI_METADATI ON OGGETTI_METADATI.IDCOMUNE = OGGETTI.IDCOMUNE AND OGGETTI_METADATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sb = sb.append(" ").append("WHERE MOVIMENTIALLEGATI.IDCOMUNE = ? AND ISTANZE.SOFTWARE = ? ").append(" ");
	sb = sb.append(" ").append("AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO  IS NULL").append(" ");
	sb = sb.append(" ").append("AND").append(" ");
	sb = sb.append(" ").append("(").append(" ");
	sb = sb.append(" ").append("ARCHIVIAZIONI_ISTANZE.ID IS NULL").append(" ");
	sb = sb.append(" ").append("OR").append(" ");
	sb = sb.append(" ").append("ARCHIVIAZIONI_ISTANZE.ID IS NOT NULL AND ARCHIVIAZIONI_ISTANZE.ESCLUSA =  0").append(" ");
	sb = sb.append(" ").append(")").append(" ");
	if (filter.getDallaData() != null) {
	    sb = sb.append(" ").append("AND MOVIMENTIALLEGATI.DATAREGISTRAZIONE >= ?").append(" ");
	}
	if (filter.getAllaData() != null) {
	    sb = sb.append(" ").append("AND MOVIMENTIALLEGATI.DATAREGISTRAZIONE <= ?").append(" ");
	}
	if (!mimeTypeFileAmmessi.isEmpty()) {
	    String p1 = StringUtils.repeat("?,", mimeTypeFileAmmessi.size());
	    p1 = StringUtils.removeEnd(p1, ",");
	    sb = sb.append(" ").append("AND OGGETTI_METADATI.CHIAVE = 'FILE_CONTENT_TYPE'").append(" ");
	    sb = sb.append(" ").append("AND OGGETTI_METADATI.VALORE IN (").append(p1).append(")").append(" ");
	}
	// Elimina i file pdf in conservazione sospesa, non firmati in caso sia attiva l'opzione invia solo firmati
	sb = sb.append(" AND NOT EXISTS ( SELECT oggetti_metadati.chiave  FROM oggetti_metadati  ");
	sb = sb.append(" WHERE oggetti_metadati.idcomune = oggetti.idcomune AND oggetti_metadati.codiceoggetto = oggetti.codiceoggetto ");
	sb = sb.append(" AND oggetti_metadati.chiave='CONSERVAZIONE_DOC_SOSPESA' AND oggetti_metadati.valore='SI' )");
	sb = sb.append(" ").append("GROUP BY MOVIMENTI.CODICEISTANZA,ISTANZE.NUMEROISTANZA ");
	sb = sb.append(" ").append("ORDER BY codiceIstanza").append(" ");
	return sb.toString();
    }

    private String builsQueryDocumentiEndo() {

	StringBuffer sb = new StringBuffer("SELECT ISTANZEALLEGATI.CODICEISTANZA AS codiceistanza, ISTANZE.NUMEROISTANZA AS numeroIstanza ");
	sb = sb.append(" ").append(
		" FROM ISTANZEALLEGATI INNER JOIN OGGETTI ON ISTANZEALLEGATI.IDCOMUNE = OGGETTI.IDCOMUNE AND ISTANZEALLEGATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO ")
		.append(" ");
	sb = sb.append(" ").append(
		" INNER JOIN ISTANZEPROCEDIMENTI ON ISTANZEALLEGATI.IDCOMUNE = ISTANZEPROCEDIMENTI.IDCOMUNE AND ISTANZEALLEGATI.CODICEINVENTARIO = ISTANZEPROCEDIMENTI.CODICEINVENTARIO AND ISTANZEALLEGATI.CODICEISTANZA  = ISTANZEPROCEDIMENTI.CODICEISTANZA ")
		.append(" ");
	sb = sb.append(" ")
		.append("LEFT JOIN ISTANZE ON ISTANZEALLEGATI.IDCOMUNE = ISTANZE.IDCOMUNE AND ISTANZEALLEGATI.CODICEISTANZA = ISTANZE.CODICEISTANZA")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_OGGETTI ON ARCHIVIAZIONI_OGGETTI.IDCOMUNE = OGGETTI.IDCOMUNE AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_ISTANZE ON ARCHIVIAZIONI_ISTANZE.IDCOMUNE = ISTANZE.IDCOMUNE AND ARCHIVIAZIONI_ISTANZE.CODICEISTANZA = ISTANZE.CODICEISTANZA")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN OGGETTI_METADATI ON OGGETTI_METADATI.IDCOMUNE = OGGETTI.IDCOMUNE AND OGGETTI_METADATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sb = sb.append(" ").append("WHERE ISTANZEALLEGATI.IDCOMUNE = ? AND ISTANZE.SOFTWARE = ? ").append(" ");
	sb = sb.append(" ").append("AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO  IS NULL").append(" ");
	sb = sb.append(" ").append("AND").append(" ");
	sb = sb.append(" ").append("(").append(" ");
	sb = sb.append(" ").append("ARCHIVIAZIONI_ISTANZE.ID IS NULL").append(" ");
	sb = sb.append(" ").append("OR").append(" ");
	sb = sb.append(" ").append("ARCHIVIAZIONI_ISTANZE.ID IS NOT NULL AND ARCHIVIAZIONI_ISTANZE.ESCLUSA =  0").append(" ");
	sb = sb.append(" ").append(")").append(" ");
	if (filter.getDallaData() != null) {
	    sb = sb.append(" ").append("AND ISTANZEALLEGATI.DATA >= ?").append(" ");
	}
	if (filter.getAllaData() != null) {
	    sb = sb.append(" ").append("AND ISTANZEALLEGATI.DATA <= ?").append(" ");
	}
	if (!mimeTypeFileAmmessi.isEmpty()) {
	    String p1 = StringUtils.repeat("?,", mimeTypeFileAmmessi.size());
	    p1 = StringUtils.removeEnd(p1, ",");
	    sb = sb.append(" ").append("AND OGGETTI_METADATI.CHIAVE = 'FILE_CONTENT_TYPE'").append(" ");
	    sb = sb.append(" ").append("AND OGGETTI_METADATI.VALORE IN (").append(p1).append(")").append(" ");
	}
	// Elimina i file pdf in conservazione sospesa, non firmati in caso sia attiva l'opzione invia solo firmati
	sb = sb.append(" AND NOT EXISTS ( SELECT oggetti_metadati.chiave  FROM oggetti_metadati  ");
	sb = sb.append(" WHERE oggetti_metadati.idcomune = oggetti.idcomune AND oggetti_metadati.codiceoggetto = oggetti.codiceoggetto ");
	sb = sb.append(" AND oggetti_metadati.chiave='CONSERVAZIONE_DOC_SOSPESA' AND oggetti_metadati.valore='SI' )");
	sb = sb.append(" ").append("GROUP BY ISTANZEALLEGATI.CODICEISTANZA,ISTANZE.NUMEROISTANZA ");
	sb = sb.append(" ").append("ORDER BY codiceIstanza").append(" ");
	return sb.toString();
    }

    private String builsQueryDocumentiIstanza() {

	//	String[] myArray = new String[mimeTypeFileAmmessi.size()];
	//	mimeTypeFileAmmessi.toArray(myArray);
	//	System.out.println(StringUtils.join(myArray, ","));
	StringBuffer sb = new StringBuffer("SELECT DOCUMENTIISTANZA.CODICEISTANZA AS codiceistanza, ISTANZE.NUMEROISTANZA AS numeroIstanza ");
	sb = sb.append(" ").append(
		" FROM DOCUMENTIISTANZA INNER JOIN OGGETTI ON DOCUMENTIISTANZA.IDCOMUNE = OGGETTI.IDCOMUNE AND DOCUMENTIISTANZA.CODICEOGGETTO = OGGETTI.CODICEOGGETTO ")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN ISTANZE ON DOCUMENTIISTANZA.IDCOMUNE = ISTANZE.IDCOMUNE AND DOCUMENTIISTANZA.CODICEISTANZA = ISTANZE.CODICEISTANZA")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_OGGETTI ON ARCHIVIAZIONI_OGGETTI.IDCOMUNE = OGGETTI.IDCOMUNE AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN ARCHIVIAZIONI_ISTANZE ON ARCHIVIAZIONI_ISTANZE.IDCOMUNE = ISTANZE.IDCOMUNE AND ARCHIVIAZIONI_ISTANZE.CODICEISTANZA = ISTANZE.CODICEISTANZA")
		.append(" ");
	sb = sb.append(" ").append(
		"LEFT JOIN OGGETTI_METADATI ON OGGETTI_METADATI.IDCOMUNE = OGGETTI.IDCOMUNE AND OGGETTI_METADATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO")
		.append(" ");
	sb = sb.append(" ").append("WHERE DOCUMENTIISTANZA.IDCOMUNE = ? AND ISTANZE.SOFTWARE = ? ").append(" ");
	sb = sb.append(" ").append("AND ARCHIVIAZIONI_OGGETTI.CODICEOGGETTO  IS NULL").append(" ");
	sb = sb.append(" ").append("AND").append(" ");
	sb = sb.append(" ").append("(").append(" ");
	sb = sb.append(" ").append("ARCHIVIAZIONI_ISTANZE.ID IS NULL").append(" ");
	sb = sb.append(" ").append("OR").append(" ");
	sb = sb.append(" ").append("ARCHIVIAZIONI_ISTANZE.ID IS NOT NULL AND ARCHIVIAZIONI_ISTANZE.ESCLUSA =  0").append(" ");
	sb = sb.append(" ").append(")").append(" ");
	if (filter.getDallaData() != null) {
	    sb = sb.append(" ").append("AND DOCUMENTIISTANZA.DATA >= ?").append(" ");
	}
	if (filter.getAllaData() != null) {
	    sb = sb.append(" ").append("AND DOCUMENTIISTANZA.DATA <= ?").append(" ");
	}
	if (!mimeTypeFileAmmessi.isEmpty()) {
	    String p1 = StringUtils.repeat("?,", mimeTypeFileAmmessi.size());
	    p1 = StringUtils.removeEnd(p1, ",");
	    sb = sb.append(" ").append("AND OGGETTI_METADATI.CHIAVE = 'FILE_CONTENT_TYPE'").append(" ");
	    sb = sb.append(" ").append("AND OGGETTI_METADATI.VALORE IN (").append(p1).append(")").append(" ");
	}
	// Elimina i file pdf in conservazione sospesa, non firmati in caso sia attiva l'opzione invia solo firmati
	sb = sb.append(" AND NOT EXISTS ( SELECT oggetti_metadati.chiave  FROM oggetti_metadati  ");
	sb = sb.append(" WHERE oggetti_metadati.idcomune = oggetti.idcomune AND oggetti_metadati.codiceoggetto = oggetti.codiceoggetto ");
	sb = sb.append(" AND oggetti_metadati.chiave='CONSERVAZIONE_DOC_SOSPESA' AND oggetti_metadati.valore='SI' )");
	sb = sb.append(" ").append("GROUP BY DOCUMENTIISTANZA.CODICEISTANZA,ISTANZE.NUMEROISTANZA ");
	sb = sb.append(" ").append("ORDER BY codiceIstanza").append(" ");
	return sb.toString();
    }
}
