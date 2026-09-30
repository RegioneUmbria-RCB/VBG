package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryBollettazioneMercatiPresenzeEffettiveHelper extends AbstractQueryBollettazioneMercatiHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryBollettazioneMercatiPresenzeEffettiveHelper.class);
    private List<Integer> filtriMercati;
    private IntervalloDate intervalloDate;
    private String guid;

    public QueryBollettazioneMercatiPresenzeEffettiveHelper(String guid, SessionFactoryImplementor sessimpl, List<Integer> filtriMercati,
	    IntervalloDate intervalloDate, Boolean conguaglio) {

	super();
	this.guid = guid;
	this.filtriMercati = filtriMercati;
	this.intervalloDate = intervalloDate;
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryBollettazioneMercatiHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryBollettazioneMercatiHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryBollettazioneMercatiHelper: schemaName={}", schemaName);
    }

    @Override
    public String buildQuery() {

	int position = 0;
	String qm = StringUtils.repeat("?,", filtriMercati.size());
	qm = qm.substring(0, qm.length() - 1);
	String sql = "SELECT " + //
		" mercatipresenze_t.idcomune AS idComune, " + //
		" ? as guid, " + //
		" 'PRESENZE' AS provenienza, " + //
		" 0 AS subentro, " + //
		" mercatipresenze_t.id AS idGiornata, " +
		" mercatipresenze_t.dataregistrazione AS dataGiornata, " + //
		" mercatipresenze_d.fkidposteggio AS idPosteggio, " +
		" mercatipresenze_d.CODICEANAGRAFE AS idAnagrafe, " + //
		" autorizzazioni.id AS idRiferimento, " +
		" autorizzazioni_concessioni.id AS idAutorizzazioneConcessione, " +
		" COALESCE(mercatipresenze_d.flag_assenza_giust,0) AS assenzaGiustificata, " +
		" CASE COALESCE(mercatipresenze_d.spuntista,0) WHEN 1 THEN 0 ELSE mercatipresenze_d.NUMEROPRESENZE END AS concpresente, " +
		" COALESCE(mercatipresenze_d.spuntista,0) AS spuntpresente, " + //
		" mercatipresenze_d.fk_codiceistat AS catmerc, " + //
		" mercatipresenze_t.fkidmercatiuso AS idUso, " + //
		" NULL AS idAutorizzazioniSubentri " + //
		"FROM " + //
		" mercatipresenze_t " +
		"   INNER JOIN mercatipresenze_d ON mercatipresenze_d.idcomune = mercatipresenze_t.idcomune AND mercatipresenze_d.fkidtestata = mercatipresenze_t.id AND mercatipresenze_d.fkidposteggio IS NOT NULL " +
		"   INNER JOIN autorizzazioni ON autorizzazioni.idcomune = mercatipresenze_d.idcomune AND autorizzazioni.id = mercatipresenze_d.fk_autorizzazioni_id " +
		"   LEFT JOIN autorizzazioni_concessioni ON " + //
		" autorizzazioni_concessioni.idcomune = autorizzazioni.idcomune AND " +
		" autorizzazioni_concessioni.fk_idaut_attuale = autorizzazioni.id AND " +
		" autorizzazioni_concessioni.fk_codicemercato = mercatipresenze_t.fkcodicemercato AND " +
		" autorizzazioni_concessioni.fk_idmercatiuso = mercatipresenze_t.fkidmercatiuso AND " +
		" autorizzazioni_concessioni.fk_idposteggio = mercatipresenze_d.fkidposteggio " + //
		"WHERE " +
		" mercatipresenze_t.idcomune = ? AND " + //
		" mercatipresenze_t.fkcodicemercato IN (" +
		qm +
		") " +
		" AND mercatipresenze_t.dataregistrazione >= ? AND " + //
		" mercatipresenze_t.dataregistrazione <= ? AND " +
		" autorizzazioni.autorizdata <= mercatipresenze_t.dataregistrazione AND " +
		" (autorizzazioni.datascadenza IS NULL OR autorizzazioni.datascadenza > mercatipresenze_t.dataregistrazione) " +
		" AND NOT EXISTS ( " + //
		"  SELECT 1 " + //
		"  FROM autorizzazioni_subentri " +
		"  WHERE autorizzazioni_subentri.IDCOMUNE = autorizzazioni.IDCOMUNE AND " +
		"   autorizzazioni_subentri.fk_idaut_attuale = autorizzazioni.ID AND " +
		"   autorizzazioni_subentri.data_cessazione > mercatipresenze_t.dataregistrazione " + //
		" ) " + //
		"UNION " + //
		"SELECT " +
		" mercatipresenze_t.idcomune AS idComune, " + //
		" ? as guid, " + //
		" 'PRESENZE' AS provenienza, " + //
		" 1 AS subentro, " + //
		" mercatipresenze_t.id AS idGiornata, " +
		" mercatipresenze_t.dataregistrazione AS dataGiornata, " + //
		" mercatipresenze_d.fkidposteggio AS idPosteggio, " +
		" mercatipresenze_d.CODICEANAGRAFE AS idanagrafe, " + //
		" autorizzazioni_subentri.fk_idaut_attuale AS idRiferimento, " +
		" autorizzazioni_subentri_conc.id AS idAutorizzazioneConcessione, " +
		" COALESCE(mercatipresenze_d.flag_assenza_giust,0) AS assenzaGiustificata, " +
		" CASE COALESCE(mercatipresenze_d.spuntista,0) WHEN 1 THEN 0 ELSE mercatipresenze_d.NUMEROPRESENZE END AS concpresente, " +
		" COALESCE(mercatipresenze_d.spuntista, 0) AS spuntpresente, " + //
		" mercatipresenze_d.fk_codiceistat AS catmerc, " + //
		" mercatipresenze_t.fkidmercatiuso AS idUso, " + //
		" autorizzazioni_subentri.id AS idAutorizzazioniSubentri " + //
		"FROM " +
		" mercatipresenze_t " + //
		" INNER JOIN mercatipresenze_d ON mercatipresenze_d.idcomune = mercatipresenze_t.idcomune " +
		"   AND mercatipresenze_d.fkidtestata = mercatipresenze_t.id AND mercatipresenze_d.fkidposteggio IS NOT NULL " +
		" INNER JOIN autorizzazioni_subentri ON autorizzazioni_subentri.idcomune = mercatipresenze_d.idcomune " +
		"   AND autorizzazioni_subentri.fk_idaut_attuale = mercatipresenze_d.fk_autorizzazioni_id " +
		" LEFT JOIN autorizzazioni_subentri_conc ON autorizzazioni_subentri_conc.idcomune = autorizzazioni_subentri.idcomune " +
		"   AND autorizzazioni_subentri_conc.fk_autsub_id = autorizzazioni_subentri.id " +
		"   AND autorizzazioni_subentri_conc.fk_codicemercato = mercatipresenze_t.fkcodicemercato " +
		"   AND autorizzazioni_subentri_conc.fk_idmercatiuso = mercatipresenze_t.fkidmercatiuso " +
		"   AND autorizzazioni_subentri_conc.fk_idposteggio = mercatipresenze_d.fkidposteggio " + //
		"WHERE " +
		" mercatipresenze_t.idcomune = ? AND " + //
		" mercatipresenze_t.fkcodicemercato IN (" +
		qm +
		") AND " +
		" mercatipresenze_t.dataregistrazione >= ? AND " + //
		" mercatipresenze_t.dataregistrazione <= ? " +
		" AND autorizzazioni_subentri.autorizdata <= mercatipresenze_t.dataregistrazione " +
		" AND autorizzazioni_subentri.data_cessazione > mercatipresenze_t.dataregistrazione " +
		" AND (autorizzazioni_subentri.datascadenza IS NULL OR autorizzazioni_subentri.datascadenza > mercatipresenze_t.dataregistrazione)";
	parameters.add(new ParameterHelper(position++, this.guid, new StringType()));
	parameters.add(new ParameterHelper(position++, ORMHelper.getIdcomune(), new StringType()));
	for (Integer codiceMercato : filtriMercati) {
	    parameters.add(new ParameterHelper(position++, codiceMercato, new IntegerType()));
	}
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataInizio(), new TimestampType()));
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataFine(), new TimestampType()));
	parameters.add(new ParameterHelper(position++, this.guid, new StringType()));
	parameters.add(new ParameterHelper(position++, ORMHelper.getIdcomune(), new StringType()));
	for (Integer codiceMercato : filtriMercati) {
	    parameters.add(new ParameterHelper(position++, codiceMercato, new IntegerType()));
	}
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataInizio(), new TimestampType()));
	parameters.add(new ParameterHelper(position++, intervalloDate.getDataFine(), new TimestampType()));
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
