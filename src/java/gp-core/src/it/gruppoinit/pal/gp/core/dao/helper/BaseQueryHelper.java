package it.gruppoinit.pal.gp.core.dao.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaRigheFilter;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;

public abstract class BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(BaseQueryHelper.class);
    protected final String SCHEMA_NAME = "#SCHEMA_NAME#";
    protected DialettoEnum _dialetto;
    protected String schemaName = "";
    protected List<ParameterHelper> parameters = new ArrayList<ParameterHelper>();
    protected boolean isCountQuery = false;
    protected Dyn2CampiDAO dyn2CampiDAO;

    protected String applyLpadFunction(String fragment, String padNum, String padChar) {

	StringBuffer result = null;
	switch (_dialetto) {
	    case MYSQL:
	    case POSTGRES:
	    case ORACLE:
		// lpad( string1, padded_length, [ pad_string ] )
		result = new StringBuffer(" lpad(").append(fragment).append(", ");
		result.append(padNum).append(",").append(padChar);
		result.append(")");
		break;
	    case SQLSERVER:
		result = new StringBuffer(" replicate(");
		// replicate([carattere da ripetere], ([lunghezza] - LEN([campo]))
		result.append(padChar).append(", (").append(padNum).append(" - len(").append(fragment).append(")").append(")");
		result.append(") + ").append(fragment);
		break;
	    default:
		result = new StringBuffer(fragment);
		break;
	}
	return result.toString();
    }

    /**
     * 
     * @param separator
     *            la stringa usata per la separazione dei parametri (può essere nulla)
     * @param paramsToConcat
     *            la lista dei parametri da concatenare
     * @return
     */
    protected String applyConcatFunction(String separator, String... paramsToConcat) {

	String result = new String();
	switch (_dialetto) {
	    case MYSQL:
		// concat(ifnull(param[0],''),ifnull(param[1],''),....)
		result = result.concat("concat(");
		for (String param : paramsToConcat) {
		    result = result.concat("ifnull(").concat(param).concat(",''),");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(",");
		    }
		}
		result = result.substring(0, (result.length() - 1));
		result = result.concat(")");
		break;
	    case POSTGRES:
		// coalesce(param[0],'') || ' ' ||  coalesce(param[0],'') 
		for (String param : paramsToConcat) {
		    result = result.concat("coalesce(").concat(param).concat(",'') || ");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(" || ");
		    }
		}
		result = result.substring(0, (result.length() - 3));
		break;
	    case ORACLE:
		// param[0] || param[1] || ...
		for (String param : paramsToConcat) {
		    result = result.concat(param).concat(" || ");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(" || ");
		    }
		}
		result = result.substring(0, (result.length() - 3));
		break;
	    case SQLSERVER:
		// param[0] + param[1] + ...
		for (String param : paramsToConcat) {
		    result = result.concat(param).concat(" + ");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(" + ");
		    }
		}
		result = result.substring(0, (result.length() - 2));
		break;
	    default:
		break;
	}
	return result;
    }

    protected String applyDateToString_DDMMYYYY(String campoData) {

	return dateToString_DDMMYYYY(campoData, this._dialetto).toString();
    }

    public static StringBuffer stringToDate_DDMMYYYY(String dateString, DialettoEnum _dialetto) {

	StringBuffer result = null;
	switch (_dialetto) {
	    case ORACLE:
		result = new StringBuffer("TO_DATE('").append(dateString).append("', ");
		result.append("'DD/MM/YYYY'").append(")");
		break;
	    case MYSQL:
		result = new StringBuffer("STR_TO_DATE('").append(dateString).append("', ");
		result.append("'%d/%m/%Y'").append(")");
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

    public static StringBuffer dateToString_DDMMYYYY(String campoData, DialettoEnum _dialetto) {

	StringBuffer result = null;
	switch (_dialetto) {
	    case ORACLE:
		result = new StringBuffer("TO_CHAR(").append(campoData).append(", ");
		result.append("'DD/MM/YYYY'").append(")");
		break;
	    case MYSQL:
		result = new StringBuffer("DATE_FORMAT(").append(campoData).append(", ");
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

    public static StringBuffer applySimpleNVLFunction(String nullableValue, String valueIfNull, DialettoEnum _dialetto) {

	StringBuffer result = null;
	switch (_dialetto) {
	    case ORACLE:
		result = new StringBuffer(" NVL(").append(nullableValue).append(", ");
		result.append(valueIfNull).append(")");
		log.debug("applyLpadFunction: frammento di query {}", result);
		break;
	    case MYSQL:
	    case POSTGRES:
	    case SQLSERVER:
		result = new StringBuffer(" COALESCE(").append(nullableValue).append(", ");
		result.append(valueIfNull).append(")");
		break;
	    default:
		result = new StringBuffer(" NVL(").append(nullableValue).append(", ");
		result.append(valueIfNull).append(")");
		log.debug("applyLpadFunction: frammento di query {}", result);
		break;
	}
	return result;
    }

    protected StringBuffer applySimpleNVLFunction(String nullableValue, String valueIfNull) {

	StringBuffer result = null;
	switch (_dialetto) {
	    case ORACLE:
		result = new StringBuffer(" NVL(").append(nullableValue).append(", ");
		result.append(valueIfNull).append(")");
		log.debug("applyLpadFunction: frammento di query {}", result);
		break;
	    case MYSQL:
	    case POSTGRES:
	    case SQLSERVER:
		result = new StringBuffer(" COALESCE(").append(nullableValue).append(", ");
		result.append(valueIfNull).append(")");
		break;
	    default:
		result = new StringBuffer(" NVL(").append(nullableValue).append(", ");
		result.append(valueIfNull).append(")");
		log.debug("applyLpadFunction: frammento di query {}", result);
		break;
	}
	return result;
    }

    /**
     * La funzione torna true se la stringa non è vuota e nel caso diversa da % (wildCard).
     * 
     * @param stringToCheck
     * @return
     */
    protected boolean isStringNotEmptyOrWildCard(String stringToCheck) {

	if (StringUtils.isBlank(stringToCheck)) {
	    return false;
	}
	if (stringToCheck.equals("%")) {
	    return false;
	}
	return true;
    }

    public abstract void setFilterValues(SQLQuery q);

    public abstract void setScalarProperties(SQLQuery q);

    public abstract String buildQuery();

    protected String createSQLFragment(String aliasTabellaMaster, String colonnaIdMaster, String colonnaFkIdMaster, String tabellaDyn2Dati,
	    String dbSchema, SchedaDinamicaFilter filter, MutableInt posRef) {

	if (dyn2CampiDAO == null) {
	    throw new RuntimeException(
		    "BaseQueryHelper#createSQLFragment: non configurato correttamente " + getClass() + " non sovrascrive la proprietà Dyn2CampiDAO");
	}
	if (filter == null) {
	    return "";
	}
	if (filter.getRighe() == null) {
	    return "";
	}
	if (filter.getRighe().size() == 0) {
	    return "";
	}
	String result = " and (";
	int processedFiltersCount = 0;
	for (SchedaDinamicaRigheFilter riga : filter.getRighe()) {
	    Dyn2Campi d2c = dyn2CampiDAO.findById(new PkId(riga.getCampo().getId().getCodice()));
	    /*
	     * LION BUGFIX nel caso in cui l'utente non avesse selezionato il campo nella UI la findByID restituisce null 
	     * e --> processaRiga da NPE. Se d2c == null salto questo filtro e passo al successivo
	     */
	    if (d2c == null) {
		continue;
	    }
	    riga.setCampo(d2c);
	    //LION BUGBIX se l'utente ha una riga vuota all'inizio e la riga successiva ha ancora AND prepopolato dal sistema si genera una query SQL non corretta
	    //per evitare questi errori il campo AND/OR viene considerato solo a partire dalla seconda riga effettivamente elaborata
	    if (processedFiltersCount == 0 && riga.getAndOr() != null) {
		riga.setAndOr(null);
	    }
	    //END LION BUGFIX
	    result += processaRiga(riga, posRef);
	    processedFiltersCount++;
	}
	result += ")";
	result = result.replaceAll("SCHEMA_OWNER", dbSchema).replaceAll("ALIAS_TABELLA_MASTER", aliasTabellaMaster);
	result = result.replaceAll("ALIAS_TABELLA_DYN2DATI", tabellaDyn2Dati).replaceAll("ALIAS_COLONNA_ID_MASTER", colonnaIdMaster);
	result = result.replaceAll("ALIAS_COLONNA_FK_IDMASTER", colonnaFkIdMaster);
	return result;
    }

    private String processaRiga(SchedaDinamicaRigheFilter riga, MutableInt posRef) {

	String andOr = riga.getAndOr() == null ? "" : riga.getAndOr().name().toLowerCase();
	String fragment = "";
	switch (riga.getTipoConfronto()) {
	    case ISEMPTY:
		fragment = andOr +
			" " +
			StringUtils.defaultString(riga.getParentesiSx(), "") +
			" (not exists (select 1 from SCHEMA_OWNER.ALIAS_TABELLA_DYN2DATI istd2d where " +
			"istd2d.idcomune=ALIAS_TABELLA_MASTERidcomune and istd2d.ALIAS_COLONNA_FK_IDMASTER=ALIAS_TABELLA_MASTERALIAS_COLONNA_ID_MASTER and istd2d.fk_d2c_id = ? ";
		break;
	    default:
		fragment = andOr +
			" " +
			StringUtils.defaultString(riga.getParentesiSx(), "") +
			" (exists (select 1 from SCHEMA_OWNER.ALIAS_TABELLA_DYN2DATI istd2d where " +
			"istd2d.idcomune=ALIAS_TABELLA_MASTERidcomune and istd2d.ALIAS_COLONNA_FK_IDMASTER=ALIAS_TABELLA_MASTERALIAS_COLONNA_ID_MASTER and istd2d.fk_d2c_id=? ";
	}
	parameters.add(new ParameterHelper(posRef.intValue(), riga.getCampo().getId().getCodice(), new IntegerType()));
	posRef.add(1);
	fragment += processaConfronto(riga, posRef) + ") )" + StringUtils.defaultString(riga.getParentesiDx(), "");
	return fragment;
    }

    private String processaConfronto(SchedaDinamicaRigheFilter riga, MutableInt posRef) {

	String result = " ";
	String columnName = decodificaNomeColonna(riga);
	TipoControlloEnum tipodato = TipoControlloEnum.valueOf(riga.getCampo().getTipodato());
	switch (riga.getTipoConfronto()) {
	    case CONTAINS:
		result = " and lower(" + convertClob(columnName, riga, false) + ") like ?";
		parameters.add(new ParameterHelper(posRef.intValue(), "%" + decodificaValore(riga, false).toLowerCase() + "%", new StringType()));
		posRef.add(1);
		return result;
	    case ENDSWITH:
		result = " and lower(" + convertClob(columnName, riga, false) + ") like ?";
		parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga, false).toLowerCase() + "%", new StringType()));
		posRef.add(1);
		return result;
	    case STARTSWITH:
		result = " and lower(" + convertClob(columnName, riga, false) + ") like ?";
		parameters.add(new ParameterHelper(posRef.intValue(), "%" + decodificaValore(riga, false).toLowerCase(), new StringType()));
		posRef.add(1);
		return result;
	    case EQ:
		result = " and lower(" + convertClob(columnName, riga, false) + ") = ?";
		parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga, false).toLowerCase(), new StringType()));
		posRef.add(1);
		return result;
	    case ISEMPTY:
		return " and " + convertClob(columnName, riga, false) + " is not null ";
	    case ISNOTEMPTY:
		return " and " + convertClob(columnName, riga, false) + " is not null ";
	    case LE:
		switch (tipodato) {
		    case NumericoDouble:
			result = " and (" + convertClob(columnName, riga, false) + ") <= ?";
			parameters.add(
				new ParameterHelper(posRef.intValue(), Double.valueOf(decodificaValore(riga, true).toLowerCase()), Hibernate.DOUBLE));
			break;
		    default:
			result = " and lower(" + convertClob(columnName, riga, false) + ") <= ?";
			parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga, false).toLowerCase(), new StringType()));
			break;
		}
		// parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga).toLowerCase(), new StringType()));
		posRef.add(1);
		return result;
	    case LT:
		switch (tipodato) {
		    case NumericoDouble:
			result = " and (" + convertClob(columnName, riga, true) + ") < ?";
			parameters.add(
				new ParameterHelper(posRef.intValue(), Double.valueOf(decodificaValore(riga, true).toLowerCase()), Hibernate.DOUBLE));
			break;
		    default:
			result = " and lower(" + convertClob(columnName, riga, false) + ") < ?";
			parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga, false).toLowerCase(), new StringType()));
			break;
		}
		// parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga).toLowerCase(), new StringType()));
		posRef.add(1);
		return result;
	    case GE:
		switch (tipodato) {
		    case NumericoDouble:
			result = " and (" + convertClob(columnName, riga, true) + ") >= ?";
			parameters.add(
				new ParameterHelper(posRef.intValue(), Double.valueOf(decodificaValore(riga, true).toLowerCase()), Hibernate.DOUBLE));
			break;
		    default:
			result = " and lower(" + convertClob(columnName, riga, false) + ") >= ?";
			parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga, false).toLowerCase(), new StringType()));
			break;
		}
		// parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga).toLowerCase(), new StringType()));
		posRef.add(1);
		return result;
	    case GT:
		switch (tipodato) {
		    case NumericoDouble:
			result = " and (" + convertClob(columnName, riga, true) + ") > ?";
			parameters.add(
				new ParameterHelper(posRef.intValue(), Double.valueOf(decodificaValore(riga, true).toLowerCase()), Hibernate.DOUBLE));
			break;
		    default:
			result = " and lower(" + convertClob(columnName, riga, false) + ") > ?";
			parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga, false).toLowerCase(), new StringType()));
			break;
		}
		// parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga).toLowerCase(), new StringType()));
		posRef.add(1);
		return result;
	    case NE:
		result = " and lower(" + convertClob(columnName, riga, false) + ") <> ?";
		parameters.add(new ParameterHelper(posRef.intValue(), decodificaValore(riga, false).toLowerCase(), new StringType()));
		posRef.add(1);
		return result;
	    default:
		break;
	}
	return "";
    }

    private String convertClob(String columnName, SchedaDinamicaRigheFilter riga, boolean consideraDecimal) {

	String result = columnName;
	switch (_dialetto) {
	    case ORACLE:
		result = "dbms_lob.substr( " + columnName + ", 50, 1)";
		break;
	    default:
		break;
	}
	if (StringUtils.isBlank(riga.getCampo().getTipodato())) {
	    return result;
	}
	TipoControlloEnum tipodato = TipoControlloEnum.valueOf(riga.getCampo().getTipodato());
	switch (tipodato) {
	    case NumericoIntero:
		result = " lpad(" + result + "," + padReplication + ",'" + padChar + "')";
		break;
	    case NumericoDouble:
		if (consideraDecimal) {
		    switch (_dialetto) {
			case ORACLE:
			    result = " to_number(" + result + ") ";
			    break;
			case MYSQL:
			case POSTGRES:
			case SQLSERVER:
			    result = " cast(replace(" + result + ",',','.') as DECIMAL(15,4)) ";
			    break;
		    }
		} else {
		    result = " lpad(" + result + "," + padReplication + ",'" + padChar + "')";
		    break;
		}
	    default:
		break;
	}
	return result;
    }

    private String decodificaValore(SchedaDinamicaRigheFilter riga, boolean consideraDecimal) {

	String valore = StringUtils.defaultString(riga.getValore());
	if (StringUtils.isBlank(riga.getCampo().getTipodato())) {
	    return valore;
	}
	TipoControlloEnum tipodato = TipoControlloEnum.valueOf(riga.getCampo().getTipodato());
	switch (tipodato) {
	    case Data:
		if (StringUtils.isNotBlank(valore.trim())) {
		    try {
			Date d = sdfIn.parse(valore.trim());
			String result = sdfOut.format(d);
			return result;
		    } catch (Exception e) {
			log.error("errore nella conversione in data del valore {}", valore);
		    }
		}
	    case NumericoIntero:
		if (StringUtils.isNotBlank(valore)) {
		    valore = StringUtils.leftPad(valore, padReplication, padChar);
		}
		break;
	    case NumericoDouble:
		if (consideraDecimal) {
		    if (StringUtils.isNotBlank(valore)) {
			valore = valore.replace(",", ".");
		    }
		} else {
		    valore = StringUtils.leftPad(valore, padReplication, padChar);
		}
		break;
	    default:
		break;
	}
	return valore;
    }

    private String decodificaNomeColonna(SchedaDinamicaRigheFilter riga) {

	if (StringUtils.isBlank(riga.getCampo().getTipodato())) {
	    return "valoredecodificato";
	}
	TipoControlloEnum tipodato = TipoControlloEnum.valueOf(riga.getCampo().getTipodato());
	switch (tipodato) {
	    case Data:
		return "valore";
	    default:
		break;
	}
	return "valoredecodificato";
    }

    private int padReplication = 15;
    private String padChar = "0";
    private SimpleDateFormat sdfIn = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdfOut = new SimpleDateFormat("yyyyMMdd");
}
