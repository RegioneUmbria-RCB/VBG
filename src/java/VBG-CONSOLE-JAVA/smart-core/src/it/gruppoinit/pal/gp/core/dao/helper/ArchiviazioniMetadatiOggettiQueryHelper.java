package it.gruppoinit.pal.gp.core.dao.helper;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;

public class ArchiviazioniMetadatiOggettiQueryHelper extends BaseQueryHelper {

    private Integer codiceistanza;

    public ArchiviazioniMetadatiOggettiQueryHelper(SessionFactoryImplementor sfi, Integer codiceistanza) {

	String hibernateDialect = sfi.getDialect().toString();
	this._dialetto = fromString(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	this.codiceistanza = codiceistanza;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceistanza);
	q.setString(2, ORMHelper.getIdcomune());
	q.setInteger(3, codiceistanza);
	q.setString(4, ORMHelper.getIdcomune());
	q.setInteger(5, codiceistanza);
	q.setString(6, ORMHelper.getIdcomune());
	q.setInteger(7, codiceistanza);
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

	//TODO aggiungere schema
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT 'DOCUMENTIISTANZA' AS origine, DOCUMENTIISTANZA.CODICEISTANZA AS codiceIstanza, DOCUMENTIISTANZA.CODICEOGGETTO AS codiceOggetto, DOCUMENTIISTANZA.DOCUMENTO AS descrizioneDocumento, DOCUMENTIISTANZA.DATA AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE AS dimensioneFile FROM DOCUMENTIISTANZA INNER JOIN OGGETTI ON DOCUMENTIISTANZA.IDCOMUNE = OGGETTI.IDCOMUNE AND DOCUMENTIISTANZA.CODICEOGGETTO = OGGETTI.CODICEOGGETTO WHERE DOCUMENTIISTANZA.IDCOMUNE = ? AND DOCUMENTIISTANZA.CODICEISTANZA = ?");
	sql.append(" UNION ");
	sql.append("SELECT 'ISTANZEALLEGATI' AS origine, ISTANZEALLEGATI.CODICEISTANZA AS codiceIstanza, ISTANZEALLEGATI.CODICEOGGETTO AS codiceOggetto, ISTANZEALLEGATI.ALLEGATOEXTRA AS descrizioneDocumento, INVENTARIOPROCEDIMENTI.DATAAGGIORNAMENTO AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE AS dimensioneFile FROM ISTANZEALLEGATI INNER JOIN OGGETTI ON ISTANZEALLEGATI.IDCOMUNE = OGGETTI.IDCOMUNE AND ISTANZEALLEGATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO INNER JOIN INVENTARIOPROCEDIMENTI ON ISTANZEALLEGATI.IDCOMUNE = INVENTARIOPROCEDIMENTI.IDCOMUNE AND ISTANZEALLEGATI.CODICEINVENTARIO = INVENTARIOPROCEDIMENTI.CODICEINVENTARIO WHERE ISTANZEALLEGATI.IDCOMUNE = ? AND ISTANZEALLEGATI.CODICEISTANZA = ?");
	sql.append(" UNION ");
	sql.append("SELECT 'MOVIMENTIALLEGATI' AS origine, MOVIMENTI.CODICEISTANZA AS codiceIstanza, MOVIMENTIALLEGATI.CODICEOGGETTO AS codiceOggetto, MOVIMENTIALLEGATI.DESCRIZIONE AS descrizioneDocumento, MOVIMENTI.DATA AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE AS dimensioneFile FROM MOVIMENTIALLEGATI INNER JOIN OGGETTI ON MOVIMENTIALLEGATI.IDCOMUNE = OGGETTI.IDCOMUNE AND MOVIMENTIALLEGATI.CODICEOGGETTO = OGGETTI.CODICEOGGETTO INNER JOIN MOVIMENTI ON MOVIMENTIALLEGATI.IDCOMUNE = MOVIMENTI.IDCOMUNE AND MOVIMENTIALLEGATI.CODICEMOVIMENTO = MOVIMENTI.CODICEMOVIMENTO WHERE MOVIMENTI.IDCOMUNE = ? AND MOVIMENTI.CODICEISTANZA = ?");
	sql.append(" UNION ");
	sql.append("SELECT 'ISTANZEPROCURE' AS origine, ISTANZEPROCURE.CODICEISTANZA AS codiceIstanza, OGGETTI.CODICEOGGETTO AS codiceOggetto, OGGETTI.NOMEFILE AS descrizioneDocumento, ISTANZE.DATA AS dataDocumento, OGGETTI.NOMEFILE AS nomeFile, OGGETTI.DIMENSIONE_FILE AS dimensioneFile FROM ISTANZEPROCURE INNER JOIN OGGETTI ON ISTANZEPROCURE.IDCOMUNE = OGGETTI.IDCOMUNE AND ISTANZEPROCURE.CODICEOGGETTOPROCURA = OGGETTI.CODICEOGGETTO INNER JOIN ISTANZE ON ISTANZEPROCURE.IDCOMUNE = ISTANZE.IDCOMUNE AND ISTANZEPROCURE.CODICEISTANZA = ISTANZE.CODICEISTANZA WHERE ISTANZEPROCURE.IDCOMUNE = ? AND ISTANZEPROCURE.CODICEISTANZA = ?");
	return sql.toString();
    }
}
