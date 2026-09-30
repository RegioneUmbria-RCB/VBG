package it.gruppoinit.pal.gp.pay.dao.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public abstract class BaseQueryHelper {

    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final Logger log = LoggerFactory.getLogger(BaseQueryHelper.class);
    protected static final String SCHEMA_NAME = "#SCHEMA_NAME#";
    protected DIALETTO dialetto;
    protected String schemaName = "";
    protected List<IParameterHelper> parameters = new ArrayList<>();
    protected boolean isCountQuery = false;

    enum DIALETTO {
	ORACLE,
	SQLSERVER,
	POSTGRES,
	MYSQL
    }

    public static DIALETTO fromString(String hibernateDialect) {

	DIALETTO dialetto = null;
	if (hibernateDialect.contains("Oracle")) {
	    dialetto = DIALETTO.ORACLE;
	} else if (hibernateDialect.contains("MySQL")) {
	    dialetto = DIALETTO.MYSQL;
	} else if (hibernateDialect.contains("PostgreSQL")) {
	    dialetto = DIALETTO.POSTGRES;
	} else if (hibernateDialect.contains("SQLServer")) {
	    dialetto = DIALETTO.SQLSERVER;
	} else {
	    log.error("QueryIstanzeHelper#fromString: Attenzione!! Il dialetto [{}] non è supportato", hibernateDialect);
	    throw new RuntimeException("Attenzione!! Il dialetto [" + hibernateDialect + "] non è supportato da questa funzione");
	}
	return dialetto;
    }

    protected String applyLpadFunction(String fragment, String padNum, String padChar) {

	StringBuilder result = new StringBuilder(fragment);
	switch (dialetto) {
	case MYSQL:
	case POSTGRES:
	case ORACLE:
	    // lpad( string1, padded_length, [ pad_string ] )
	    result = new StringBuilder(" lpad(").append(fragment).append(", ");
	    result.append(padNum).append(",").append(padChar);
	    result.append(")");
	    break;
	case SQLSERVER:
	    result = new StringBuilder(" replicate(");
	    // replicate([carattere da ripetere], ([lunghezza] - LEN([campo]))
	    result.append(padChar).append(", (").append(padNum).append(" - len(").append(fragment).append(")").append(")");
	    result.append(") + ").append(fragment);
	    break;
	default:
	    break;
	}
	return result.toString();
    }

    private String applyConcatFunctionMySql(String separator, String... paramsToConcat) {

	String result = "";
	result = result.concat("concat(");
	for (String param : paramsToConcat) {
	    result = result.concat("ifnull(").concat(param).concat(",''),");
	    if (StringUtils.isNotBlank(separator)) {
		result = result.concat(separator).concat(",");
	    }
	}
	result = result.substring(0, (result.length() - 1));
	result = result.concat(")");
	return result;
    }

    private String applyConcatFunctionPostGreSql(String separator, String... paramsToConcat) {

	String result = "";
	for (String param : paramsToConcat) {
	    result = result.concat("coalesce(").concat(param).concat(",'') || ");
	    if (StringUtils.isNotBlank(separator)) {
		result = result.concat(separator).concat(" || ");
	    }
	}
	result = result.substring(0, (result.length() - 3));
	return result;
    }

    private String applyConcatFunctionOracle(String separator, String... paramsToConcat) {

	String result = "";
	for (String param : paramsToConcat) {
	    result = result.concat(param).concat(" || ");
	    if (StringUtils.isNotBlank(separator)) {
		result = result.concat(separator).concat(" || ");
	    }
	}
	result = result.substring(0, (result.length() - 3));
	return result;
    }

    private String applyConcatFunctionSQLServer(String separator, String... paramsToConcat) {

	String result = "";
	for (String param : paramsToConcat) {
	    result = result.concat(param).concat(" + ");
	    if (StringUtils.isNotBlank(separator)) {
		result = result.concat(separator).concat(" + ");
	    }
	}
	result = result.substring(0, (result.length() - 2));
	return result;
    }

    protected String applyConcatFunction(String separator, String... paramsToConcat) {

	String result = "";
	switch (dialetto) {
	case MYSQL:
	    result = applyConcatFunctionMySql(separator, paramsToConcat);
	    break;
	case POSTGRES:
	    result = applyConcatFunctionPostGreSql(separator, paramsToConcat);
	    break;
	case ORACLE:
	    result = applyConcatFunctionOracle(separator, paramsToConcat);
	    break;
	case SQLSERVER:
	    result = applyConcatFunctionSQLServer(separator, paramsToConcat);
	    break;
	default:
	    break;
	}
	return result;
    }

    protected String applyDateToStringDDMMYYYY(String campoData) {

	return dateToStringDDMMYYYY(campoData, this.dialetto).toString();
    }

    public static StringBuilder stringToDateDDMMYYYY(String dateString, DIALETTO dialettoUtilizzato) {

	StringBuilder result = new StringBuilder();
	switch (dialettoUtilizzato) {
	case ORACLE:
	    result = new StringBuilder("TO_DATE('").append(dateString).append("', ");
	    result.append("'DD/MM/YYYY'").append(")");
	    break;
	case MYSQL:
	    result = new StringBuilder("STR_TO_DATE('").append(dateString).append("', ");
	    result.append("'%D/%M/%Y'").append(")");
	    break;
	case POSTGRES:
	case SQLSERVER:
	    break;
	default:
	    log.debug("stringToDate_DDMMYYYY: frammento di query {}", result);
	    break;
	}
	return result;
    }

    public static StringBuilder dateToStringDDMMYYYY(String campoData, DIALETTO dialettoUtilizzato) {

	StringBuilder result = new StringBuilder();
	switch (dialettoUtilizzato) {
	case ORACLE:
	    result = new StringBuilder("TO_CHAR(").append(campoData).append(", ");
	    result.append("'DD/MM/YYYY'").append(")");
	    break;
	case MYSQL:
	    result = new StringBuilder("DATE_FORMAT(").append(campoData).append(", ");
	    result.append("'%d/%m/%Y'").append(")");
	    break;
	case POSTGRES:
	case SQLSERVER:
	    break;
	default:
	    log.debug("dateToString_DDMMYYYY: frammento di query {}", result);
	    break;
	}
	return result;
    }

    public static StringBuilder applySimpleNVLFunction(String nullableValue, String valueIfNull, DIALETTO dialettoUtilizzato) {

	StringBuilder result = null;
	switch (dialettoUtilizzato) {
	case MYSQL:
	case POSTGRES:
	case SQLSERVER:
	    result = new StringBuilder(" COALESCE(").append(nullableValue).append(", ");
	    result.append(valueIfNull).append(")");
	    break;
	default:
	    result = new StringBuilder(" NVL(").append(nullableValue).append(", ");
	    result.append(valueIfNull).append(")");
	    log.debug("applyLpadFunction: frammento di query {}", result);
	    break;
	}
	return result;
    }

    protected boolean isStringNotEmptyOrWildCard(String stringToCheck) {

	return (!StringUtils.isBlank(stringToCheck) && !stringToCheck.equals("%"));
    }

    public abstract void setFilterValues(SQLQuery q);

    public abstract void setScalarProperties(SQLQuery q);

    public abstract String buildQuery();

    protected Date getData(String dataInizioRicerca) {

	Date d = Utilities.getDate(dataInizioRicerca, YYYY_MM_DD);
	if (d == null) {
	    log.error("Formato data non valido {},{}", dataInizioRicerca, YYYY_MM_DD);
	    d = Calendar.getInstance().getTime();
	}
	return Utilities.dateWithoutTime(d);
    }
}
