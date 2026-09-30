package it.gruppoinit.pal.gp.core.dao.helper;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extends {@link org.hibernate.criterion.Order} to allow ordering by an SQL formula passed by the user. Is simply
 * appends the <code>sqlFormula</code> passed by the user to the resulting SQL query, without any verification.
 * 
 * @author Riccardo Bocci
 * @since Agosto 25, 2011
 */
public class OrderBySqlFormula extends Order {

    private static final Logger log = LoggerFactory.getLogger(OrderBySqlFormula.class);
    public static String NVL_CONVERT_STRING_TO_DATE = "NVL_CONVERT_STRING_TO_DATE";

    /**
     * <ul>
     * <li>{@link #LPAD_FUNCTION}</li>
     * <li>{@link #NVL_FUNCTION}</li>
     * <li>{@link #NONE_FUNCTION}</li>
     * <li>{@link #SUBSTRING_FUNCTION}</li>
     * <li>{@link #LENGTH_FUNCTION}</li>
     * </ul>
     */
    public static enum FunctionsEnum {
	/**
	 * Per usare questa formula non occorre specificare parametri
	 * 
	 */
	LENGTH_FUNCTION,
	/**
	 * Per usare questa formula è necessario specificare due parametri in questo ordine:
	 * <ol>
	 * <li>la lunghezza del pad</li>
	 * <li>il carattere da replicare</li>
	 * </ol>
	 * 
	 */
	LPAD_FUNCTION,
	/**
	 * Per usare questa formula è necessario specificare due parametri in questo ordine:
	 * <ol>
	 * <li>Il parametro da ritornare se nullo</li>
	 * </ol>
	 */
	NVL_FUNCTION,
	/**
	 * Nessuna funzione viene applicata
	 */
	NONE_FUNCTION,
	/**
	 * Per usare questa formula è necessario specificare due parametri in questo ordine:
	 * 
	 * <ol>
	 * <li>Valore intero che specifica l'inizio dei caratteri restituiti(0 primo carattere)</li>
	 * <li>Valore intero positivo che specifica quanti caratteri di expression verranno restituiti</li>
	 * </ol>
	 */
	SUBSTRING_FUNCTION
    };

    /**
     * 
     */
    private static final long serialVersionUID = -694908573552378658L;
    private boolean ascending;
    private String propertyName;
    private FunctionsEnum function;
    private String[] functionParams;

    protected OrderBySqlFormula(String propertyName, FunctionsEnum function, boolean ascending, String... functionParams) {

	super(propertyName, ascending);
	this.propertyName = propertyName;
	this.ascending = ascending;
	this.function = function;
	this.functionParams = functionParams;
    }

    public String toString() {

	return this.propertyName + ' ' + ((this.ascending) ? "asc" : "desc");
    }

    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {

	if (this.propertyName == null) {
	    return "";
	}
	String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, this.propertyName);
	Type type = criteriaQuery.getTypeUsingProjection(criteria, this.propertyName);
	StringBuffer fragment = new StringBuffer();
	for (int i = 0; i < columns.length; ++i) {
	    SessionFactoryImplementor factory = criteriaQuery.getFactory();
	    boolean lower = (type.sqlTypes(factory)[i] == 12);
	    if (lower) {
		fragment.append(factory.getDialect().getLowercaseFunction()).append('(');
	    }
	    fragment.append(columns[i]);
	    if (lower) {
		fragment.append(')');
	    }
	    fragment = applyCustomFunction(fragment, factory);
	    fragment.append((this.ascending) ? " asc" : " desc");
	    if (i >= columns.length - 1) {
		continue;
	    }
	    fragment.append(", ");
	}
	return fragment.toString();
    }

    private StringBuffer applyCustomFunction(StringBuffer fragment, SessionFactoryImplementor factory) {

	if (this.function != null) {
	    if (this.function.equals(FunctionsEnum.NONE_FUNCTION)) {
		return fragment;
	    }
	    Dialect dialect = factory.getDialect();
	    if (dialect != null) {
		DIALETTO dialetto = null;
		String dialString = dialect.toString();
		//	hibernate.dialect.Oracle=org.hibernate.dialect.Oracle8iDialect
		//	hibernate.dialect.Oracle9i=org.hibernate.dialect.Oracle9iDialect
		//	hibernate.dialect.Oracle10g=org.hibernate.dialect.Oracle10gDialect
		//	hibernate.dialect.MySql=org.hibernate.dialect.MySQLDialect
		//	hibernate.dialect.MySqlInnoDB=org.hibernate.dialect.MySQLInnoDBDialect
		//	hibernate.dialect.MySqlMyISAM=org.hibernate.dialect.MySQLMyISAMDialect
		//	hibernate.dialect.PostgreSQL=org.hibernate.dialect.PostgreSQLDialect
		//	hibernate.dialect.SQLServer=org.hibernate.dialect.SQLServerDialect
		if (dialString.indexOf("Oracle") > 0) {
		    dialetto = DIALETTO.ORACLE;
		} else if (dialString.indexOf("MySQL") > 0) {
		    dialetto = DIALETTO.MYSQL;
		} else if (dialString.indexOf("PostgreSQL") > 0) {
		    dialetto = DIALETTO.POSTGRES;
		} else if (dialString.indexOf("SQLServer") > 0) {
		    dialetto = DIALETTO.SQLSERVER;
		} else {
		    log.error("applyCustomFunction: Attenzione!! Il dialetto [{}] non è supportato", dialString);
		    throw new RuntimeException("Attenzione!! Il dialetto [" + dialString + "] non è supportato da questa funzione");
		}
		switch (this.function) {
		case LENGTH_FUNCTION:
		    return applyLengthFunction(fragment, dialetto);
		case LPAD_FUNCTION:
		    return applyLpadFunction(fragment, dialetto);
		case NVL_FUNCTION:
		    return applyNVLFunction(fragment, dialetto);
		case SUBSTRING_FUNCTION:
		    return applySubstringFunction(fragment, dialetto);
		default:
		    break;
		}
	    }
	}
	return fragment;
    }

    private StringBuffer applyLengthFunction(StringBuffer fragment, DIALETTO dialetto) {

	StringBuffer result = null;
	switch (dialetto) {
	case ORACLE:
	case MYSQL:
	case POSTGRES:
	    // LENGTH(this_.DESCRIZIONE)
	    result = new StringBuffer(" LENGTH(").append(fragment).append(")");
	    log.debug("applyLengthFunction: frammento di query {}", result);
	    break;
	case SQLSERVER:
	    // LEN(this_.DESCRIZIONE)
	    result = new StringBuffer(" LEN(").append(fragment).append(")");
	    log.debug("applyLengthFunction: frammento di query {}", result);
	    break;
	default:
	    result = new StringBuffer(fragment);
	    break;
	}
	return result;
    }

    private StringBuffer applySubstringFunction(StringBuffer fragment, DIALETTO dialetto) {

	StringBuffer result = null;
	switch (dialetto) {
	case ORACLE:
	case MYSQL:
	case POSTGRES:
	    // substr(this_.DESCRIZIONE,0,500)
	    result = new StringBuffer(" SUBSTR(").append(fragment).append(", ");
	    if (this.functionParams != null && this.functionParams.length > 0) {
		result.append(this.functionParams[0]).append(",").append(functionParams[1]);
	    } else {
		result.append("0,500");
	    }
	    result.append(")");
	    log.debug("applySubstringFunction: frammento di query {}", result);
	    break;
	case SQLSERVER:
	    // substring(this_.DESCRIZIONE,0,500)
	    result = new StringBuffer(" SUBSTRING(").append(fragment).append(", ");
	    if (this.functionParams != null && this.functionParams.length > 0) {
		result.append(this.functionParams[0]).append(",").append(functionParams[1]);
	    } else {
		result.append("0,500");
	    }
	    result.append(")");
	    log.debug("applySubstringFunction: frammento di query {}", result);
	    break;
	default:
	    result = new StringBuffer(fragment);
	    break;
	}
	return result;
    }

    private StringBuffer applyNVLFunction(StringBuffer fragment, DIALETTO dialetto) {

	StringBuffer result = null;
	String conversionType = "";
	if (this.functionParams != null && this.functionParams.length > 1) {
	    if (functionParams[1].equalsIgnoreCase(NVL_CONVERT_STRING_TO_DATE)) {
		conversionType = NVL_CONVERT_STRING_TO_DATE;
	    }
	}
	switch (dialetto) {
	case ORACLE:
	    result = new StringBuffer(" NVL(").append(fragment).append(", ");
	    if (this.functionParams != null && this.functionParams.length > 0) {
		result.append(convertValue(this.functionParams[0], conversionType, dialetto));
	    } else {
		// 
	    }
	    result.append(")");
	    log.debug("applyLpadFunction: frammento di query {}", result);
	    break;
	case MYSQL:
	case POSTGRES:
	case SQLSERVER:
	    result = new StringBuffer(" COALESCE(").append(fragment).append(", ");
	    if (this.functionParams != null && this.functionParams.length > 0) {
		result.append(convertValue(this.functionParams[0], conversionType, dialetto));
	    } else {
		// 
	    }
	    result.append(")");
	    break;
	default:
	    result = new StringBuffer(fragment);
	    break;
	}
	return result;
    }

    private Object convertValue(String param, String conversionType, DIALETTO dialetto) {

	if (StringUtils.defaultIfEmpty(conversionType, "").equalsIgnoreCase(NVL_CONVERT_STRING_TO_DATE)) {
	    switch (dialetto) {
	    case MYSQL:
		param = "STR_TO_DATE(" + param + ",'%d/%m/%Y')";
		break;
	    case POSTGRES:
	    case ORACLE:
		param = "TO_DATE(" + param + ",'dd/MM/yyyy')";
		break;
	    case SQLSERVER:
		param = "CONVERT(DATETIME," + param + ",103)";
		break;
	    default:
		break;
	    }
	}
	return param;
    }

    private StringBuffer applyLpadFunction(StringBuffer fragment, DIALETTO dialetto) {

	StringBuffer result = null;
	switch (dialetto) {
	case MYSQL:
	case POSTGRES:
	case ORACLE:
	    result = new StringBuffer(" lpad(").append(fragment).append(", ");
	    if (this.functionParams != null && this.functionParams.length > 0) {
		for (int i = 0; i < this.functionParams.length; i++) {
		    result.append(this.functionParams[i]);
		    if (i >= this.functionParams.length - 1) {
			continue;
		    }
		    result.append(", ");
		}
	    }
	    result.append(")");
	    log.debug("applyLpadFunction: frammento di query {}", result);
	    break;
	case SQLSERVER:
	    result = new StringBuffer(" replicate(");
	    // replicate([carattere da ripetere], ([lunghezza] - LEN([campo]))
	    if (this.functionParams != null) {
		if (this.functionParams.length > 1) {
		    result.append(this.functionParams[1]).append(", (").append(this.functionParams[0]).append(" - len(").append(fragment).append(")")
			    .append(")");
		}
	    }
	    result.append(") + ").append(fragment);
	    log.debug("applyLpadFunction: frammento di query {}", result);
	    break;
	default:
	    result = new StringBuffer(fragment);
	    break;
	}
	return result;
    }

    public static Order asc(String propertyName, FunctionsEnum function, String... functionParams) {

	return new OrderBySqlFormula(propertyName, function, true, functionParams);
    }

    public static Order desc(String propertyName, FunctionsEnum function, String... functionParams) {

	return new OrderBySqlFormula(propertyName, function, false, functionParams);
    }

    private enum DIALETTO {
	ORACLE,
	SQLSERVER,
	POSTGRES,
	MYSQL
    };
}
