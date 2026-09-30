package it.gruppoinit.pal.gp.core.dao.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryAlberoprocPerInterventiHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryIstanzeeventiHelper.class);
    private String selectMySQLQuery = "";
    private String selectORACLEQuery = "";
    private String idcomune;
    private String testoDaCercare;
    private String tipoRicerca;
    private String campiRicerca;
    private boolean filtraSoloComunica;
    private boolean soloModulisticaNazionale;

    public QueryAlberoprocPerInterventiHelper(SessionFactoryImplementor sessimpl, String idcomune, String testoDaCercare, String tipoRicerca,
	    String campiRicerca, boolean filtraSoloComunica, boolean isCountQuery, boolean soloModulisticaNazionale) {

	log.debug("QueryIstanzeeventiHelper: recupero il dialetto della SessionFactoryImplementor");
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryIstanzeeventiHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = fromString(hibernateDialect);
	log.debug("QueryIstanzeeventiHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryIstanzeeventiHelper: schemaName={}", schemaName);
	this.isCountQuery = isCountQuery;
	this.idcomune = idcomune;
	this.testoDaCercare = testoDaCercare;
	this.tipoRicerca = tipoRicerca;
	this.campiRicerca = campiRicerca;
	this.filtraSoloComunica = filtraSoloComunica;
	this.soloModulisticaNazionale = soloModulisticaNazionale;
	// inizializzata qui per applyNVLFunction
	this.selectMySQLQuery = "select ap.sc_id as id, sc_codice as sccodice, sc_descrizione as text, if((select count(*) from "
		+ SCHEMA_NAME
		+ "alberoproc ap2 where ap2.idcomune=ap.idcomune and ap2.software=ap.software and ap2.sc_codice like "
		+ applyConcatFunction("", new String[] { "ap.sc_codice", "'%'" })
		+ " and length(ap2.sc_codice)>length(ap.sc_codice))>0,1,0) as haschilds, sc_ordine as scordine from "
		+ SCHEMA_NAME
		+ "alberoproc ap  where ap.idcomune=? and ap.software=? and ( not ap.sc_attivo=?)  and ( ap.sc_pubblica in (?,?)  or ap.sc_pubblica    is null) ";
	this.selectORACLEQuery = "select ap.sc_id as id, sc_codice as sccodice, sc_descrizione as text, decode((select count(*) from "
		+ SCHEMA_NAME
		+ "alberoproc ap2 where ap2.idcomune=ap.idcomune and ap2.software=ap.software and ap2.sc_codice like "
		+ applyConcatFunction("", new String[] { "ap.sc_codice", "'%'" })
		+ " and length(ap2.sc_codice)>length(ap.sc_codice)),null,0,0,0,1) as haschilds, sc_ordine as scordine from "
		+ SCHEMA_NAME
		+ "alberoproc ap  where ap.idcomune=? and ap.software=? and ( not ap.sc_attivo=?)  and ( ap.sc_pubblica in (?,?)  or ap.sc_pubblica    is null) ";
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int _position = 0;
	log.debug("param {}={}", _position, ORMHelper.getIdcomune());
	q.setString(_position, this.idcomune); // IDCOMUNE
	_position++;
	log.debug("param {}={}", _position, ORMHelper.getSoftware());
	q.setString(_position, ORMHelper.getSoftware()); // SOFTWARE
	_position++;
	log.debug("param {}={}", _position, 1);
	q.setInteger(_position, 1); // SC_ATTIVO
	_position++;
	log.debug("param {}={}", _position, 1);
	q.setInteger(_position, 1); // SC_PUBBLICA = 1
	_position++;
	log.debug("param {}={}", _position, 1);
	q.setInteger(_position, 3); // SC_PUBBLICA = 3
	_position++;
	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("scCodice", Hibernate.STRING);
	q.addScalar("text", Hibernate.STRING);
	q.addScalar("hasChilds", Hibernate.BOOLEAN);
	q.addScalar("scOrdine", Hibernate.INTEGER);
    }

    @Override
    public String buildQuery() {

	String selectQuery = selectORACLEQuery;
	switch (_dialetto) {
	case MYSQL:
	    selectQuery = selectMySQLQuery;
	    break;
	}
	String result = isCountQuery == true ? countQuery : selectQuery;
	// result += fromQuery + whereQuery;
	int position = 5;
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	//______  _____  _    _____ ______  _____   _____  _   _  _____  ______ _____  _____ 
	//|  ___||_   _|| |  |_   _|| ___ \|_   _| |_   _|| \ | ||_   _||___  /|_   _||  _  |
	//| |_     | |  | |    | |  | |_/ /  | |     | |  |  \| |  | |     / /   | |  | | | |
	//|  _|    | |  | |    | |  |    /   | |     | |  | . ` |  | |    / /    | |  | | | |
	//| |     _| |_ | |____| |  | |\ \  _| |_   _| |_ | |\  | _| |_ ./ /___ _| |_ \ \_/ /
	//\_|     \___/ \_____/\_/  \_| \_| \___/   \___/ \_| \_/ \___/ \_____/ \___/  \___/ 
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	tipoRicerca = StringUtils.defaultIfEmpty(tipoRicerca, "tutteParole");
	campiRicerca = StringUtils.defaultIfEmpty(campiRicerca, "titoli");
	if (StringUtils.isBlank(testoDaCercare)) {
	    result += " and 1=2 "; // nessun risultato
	}
	try {
	    testoDaCercare = URLDecoder.decode(testoDaCercare, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	}
	testoDaCercare = testoDaCercare.replaceAll("%", "");
	if (StringUtils.isBlank(testoDaCercare)) {
	    result += " and 1=2 "; // nessun risultato
	}
	if (tipoRicerca.equals("tutteParole")) {
	    String[] valori = testoDaCercare.split(" ");
	    result += " and (1=1 ";
	    for (String v : valori) {
		result += "and (upper(ap.sc_descrizione) like ?  ";
		parameters.add(new ParameterHelper(position, "%" + v.toUpperCase() + "%", new StringType()));
		position++;
		if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		    result += " or upper(sc_note) like ? ";
		    parameters.add(new ParameterHelper(position, "%" + v.toUpperCase() + "%", new StringType()));
		    position++;
		}
		result += " or exists (select 1 from " + SCHEMA_NAME
			+ "alberoproc_ateco apateco inner join ateco on ateco.id=apateco.fk_idateco where ap.idcomune=apateco.idcomune and "
			+ "ap.sc_id=apateco.fk_scid and (upper(ateco.titolo) like ? ";
		parameters.add(new ParameterHelper(position, "%" + v.toUpperCase() + "%", new StringType()));
		position++;
		if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		    result += " or upper(ateco.descrizione) like ?";
		    parameters.add(new ParameterHelper(position, "%" + v.toUpperCase() + "%", new StringType()));
		    position++;
		}
		result += " ) ) )";
	    }
	    result += ")";
	} else if (tipoRicerca.equals("interaFrase")) {
	    result += " and (";
	    result += " (upper(ap.sc_descrizione) like ?  ";
	    parameters.add(new ParameterHelper(position, "%" + testoDaCercare.toUpperCase() + "%", new StringType()));
	    position++;
	    if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		result += " or upper(sc_note) like ? ";
		parameters.add(new ParameterHelper(position, "%" + testoDaCercare.toUpperCase() + "%", new StringType()));
		position++;
	    }
	    result += " or exists (select 1 from " + SCHEMA_NAME
		    + "alberoproc_ateco apateco inner join ateco on ateco.id=apateco.fk_idateco where ap.idcomune=apateco.idcomune and "
		    + "ap.sc_id=apateco.fk_scid and (upper(ateco.titolo) like ? ";
	    parameters.add(new ParameterHelper(position, "%" + testoDaCercare.toUpperCase() + "%", new StringType()));
	    position++;
	    if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		result += " or upper(ateco.descrizione) like ?";
		parameters.add(new ParameterHelper(position, "%" + testoDaCercare.toUpperCase() + "%", new StringType()));
		position++;
	    }
	    result += " ) ) ) )";
	} else { // almenoUnaParola
	    String[] valori = testoDaCercare.split(" ");
	    result += " and ( 1=2 ";
	    for (String v : valori) {
		result += "or (upper(ap.sc_descrizione) like ?  ";
		parameters.add(new ParameterHelper(position, "%" + v.toUpperCase() + "%", new StringType()));
		position++;
		if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		    result += " or upper(sc_note) like ? ";
		    parameters.add(new ParameterHelper(position, "%" + v.toUpperCase() + "%", new StringType()));
		    position++;
		}
		result += " or exists (select 1 from " + SCHEMA_NAME
			+ "alberoproc_ateco apateco inner join ateco on ateco.id=apateco.fk_idateco where ap.idcomune=apateco.idcomune and "
			+ "ap.sc_id=apateco.fk_scid and (upper(ateco.titolo) like ? ";
		parameters.add(new ParameterHelper(position, "%" + v.toUpperCase() + "%", new StringType()));
		position++;
		if (campiRicerca.equalsIgnoreCase("titoliDescrizioni")) {
		    result += " or upper(ateco.descrizione) like ?";
		    parameters.add(new ParameterHelper(position, "%" + v.toUpperCase() + "%", new StringType()));
		    position++;
		}
		result += " ) ) )";
	    }
	    result += ")";
	}
	if (filtraSoloComunica) {
	    result += " and exists (select 1 from " + SCHEMA_NAME
		    + "alberoproc ap3 where ap3.idcomune=ap.idcomune and ap3.software=ap.software and ap3.sc_codice like "
		    + applyConcatFunction("", new String[] { "ap.sc_codice", "'%'" })
		    + " and length(ap3.sc_codice)>=length(ap.sc_codice) and ap3.flag_comunica=? )";
	    parameters.add(new ParameterHelper(position, 1, new IntegerType()));
	    position++;
	}
	if (soloModulisticaNazionale) {
	    result += " and exists (select 1 from " + SCHEMA_NAME
		    + "alberoproc ap4 where ap4.idcomune=ap.idcomune and ap4.software=ap.software and ap4.sc_codice like "
		    + applyConcatFunction("", new String[] { "ap.sc_codice", "'%'" })
		    + " and length(ap4.sc_codice)>=length(ap.sc_codice) and ap4.flag_modulisticanazionale=? )";
	    parameters.add(new ParameterHelper(position, 1, new IntegerType()));
	    position++;
	}
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	//______  _____  _    _____ ______  _____  ______  _____  _   _  _____ 
	//|  ___||_   _|| |  |_   _|| ___ \|_   _| |  ___||_   _|| \ | ||  ___|
	//| |_     | |  | |    | |  | |_/ /  | |   | |_     | |  |  \| || |__  
	//|  _|    | |  | |    | |  |    /   | |   |  _|    | |  | . ` ||  __| 
	//| |     _| |_ | |____| |  | |\ \  _| |_  | |     _| |_ | |\  || |___ 
	//\_|     \___/ \_____/\_/  \_| \_| \___/  \_|     \___/ \_| \_/\____/ 
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	//	  _____ ______ ______  _____  _   _   ___  ___  ___ _____  _   _  _____  _____ 
	//	 |  _  || ___ \|  _  \|_   _|| \ | | / _ \ |  \/  ||  ___|| \ | ||_   _||_   _|
	//	 | | | || |_/ /| | | |  | |  |  \| |/ /_\ \| .  . || |__  |  \| |  | |    | |  
	//	 | | | ||    / | | | |  | |  | . ` ||  _  || |\/| ||  __| | . ` |  | |    | |  
	//	 \ \_/ /| |\ \ | |/ /  _| |_ | |\  || | | || |  | || |___ | |\  |  | |   _| |_ 
	//	  \___/ \_| \_||___/   \___/ \_| \_/\_| |_/\_|  |_/\____/ \_| \_/  \_/   \___/ 
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	if (!isCountQuery) {
	    result += " order by ";
	    result += " sc_codice asc ";
	}
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	if (StringUtils.isNotBlank(schemaName)) {
	    result = result.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), result);
	return result;
    }

    private String countQuery = "select count(*) as conteggio ";
}
