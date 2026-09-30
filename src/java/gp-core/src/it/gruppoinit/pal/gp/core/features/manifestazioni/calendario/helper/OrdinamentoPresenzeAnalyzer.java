package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.helper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * TEST DA EFFETTUARE su modifiche alla classe
 * <ol>
 * <li>INTERFACCIA GESTIONE PRESENZE BOTTONE <b>"MOSTRA GRADUATORIA SPUNTISTI"</b></li>
 * <li>RICERCA ANAGRAFE <b>/backend/services/rest-private/app/mercati/ops/ricerca
 * {"idGiornata":8274,"testo":"LDLMMD83T26Z336R","numMaxRecords":10}</b></li>
 * <li>GRADUATORIE
 * <b>/areariservata2/services/rest/servizi-graduatorie/mercati/graduatoria?mercato=TEST+PAGAMENTI&giorno=lun</b></li>
 * <li>STATO GIORNATA <b>/backend/services/rest-private/app/mercati/65900</b></li>
 * </ol>
 */
public class OrdinamentoPresenzeAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(OrdinamentoPresenzeAnalyzer.class);
    private static final String DATAREGDITTE_SQL_REGEXP = "(?i)dataregditte";
    private static final String DATAANZIANITA_SQL_REGEXP = "(?i)dataanzianita";
    private static final String AUTORIZDATA_SQL_REGEXP = "(?i)autorizdata";
    private static final String NUMEROPRESENZE_SQL_REGEXP = "(?i)numpresenze";
    private static final String REST_NOMINATIVOGERENTE_SQL_REGEXP = "(?i)\\bnominativogerente\\b";
    private static final String REST_NOMINATIVO_SQL_REGEXP = "(?i)\\bnominativo\\b";
    private static final String REST_NOMEGERENTE_SQL_REGEXP = "(?i)\\bnomegerente\\b";
    private static final String REST_NOME_SQL_REGEXP = "(?i)\\bnome\\b"; // coalesce(gerente.nominativo, occupante.nominativo), coalesce(gerente.nome, occupante.nome)

    enum FUNCTION_KEY_WORDS {
	LEAST,
	COALESCE,
	DESC,
	ASC
    }

    /**
     * 
     * @param sqlCrit
     * @param isForGradSpuntisti
     * @return
     */
    public static String parseSQLCriterio(String sqlCrit, boolean isForGradSpuntisti) {

	log.debug("parseSQLCriterio isForGradSpuntisti {},  crit {}", isForGradSpuntisti, sqlCrit);
	if (StringUtils.isBlank(sqlCrit)) {
	    return sqlCrit;
	}
	if (isForGradSpuntisti) {
	    sqlCrit = sqlCrit.replaceAll(DATAREGDITTE_SQL_REGEXP, "anagrafe.dataregditte");
	    sqlCrit = sqlCrit.replaceAll(DATAANZIANITA_SQL_REGEXP, "autorizzazioni.data_anzianita");
	    sqlCrit = sqlCrit.replaceAll(AUTORIZDATA_SQL_REGEXP, "autorizzazioni.autorizdata");
	    sqlCrit = sqlCrit.replaceAll(NUMEROPRESENZE_SQL_REGEXP, "numpresenze");
	}
	return sqlCrit.replace("|", ",");
    }

    public static String parseSQLCriterioForRestHelper(String sqlCrit) {

	log.debug("parseSQLCriterioForRestHelper  crit {}", sqlCrit);
	if (StringUtils.isBlank(sqlCrit)) {
	    return sqlCrit;
	}
	sqlCrit = sqlCrit.replaceAll(DATAREGDITTE_SQL_REGEXP, "occupante.dataregditte");
	sqlCrit = sqlCrit.replaceAll(DATAANZIANITA_SQL_REGEXP, "autDataAnzianita");
	sqlCrit = sqlCrit.replaceAll(AUTORIZDATA_SQL_REGEXP, "autorizdata");
	sqlCrit = sqlCrit.replaceAll(NUMEROPRESENZE_SQL_REGEXP, "numeropresenze");
	//
	sqlCrit = sqlCrit.replaceAll(REST_NOMINATIVO_SQL_REGEXP, "occupante.nominativo");
	sqlCrit = sqlCrit.replaceAll(REST_NOMINATIVOGERENTE_SQL_REGEXP, "gerente.nominativo"); // coalesce(gerente.nominativo, occupante.nominativo), coalesce(gerente.nome, occupante.nome)
	sqlCrit = sqlCrit.replaceAll(REST_NOME_SQL_REGEXP, "occupante.nome");
	sqlCrit = sqlCrit.replaceAll(REST_NOMEGERENTE_SQL_REGEXP, "gerente.nome");
	log.debug("parseSQLCriterioForRestHelper ret crit {}", sqlCrit);
	return sqlCrit.replace("|", ",");
    }
}
