package it.gruppoinit.pal.gp.core.dao.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.FetchGmt;
import it.gruppoinit.pal.gp.core.dao.helper.GmtDictionary;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.RecordSetArray;
import it.gruppoinit.pal.gp.core.dao.helper.SQLSelectHelper;
import it.gruppoinit.pal.gp.core.dao.helper.VerificaQueryHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public class SostituzioniValoriConQueryWork implements Work {

    private static final Logger log = LoggerFactory.getLogger(SostituzioniValoriConQueryWork.class);
    private String fileInRTF = null;
    private String tipoFile;
    private Map<String, String> params;
    public String output;
    private String stestocompletooriginale;
    private String testocompleto;
    private String intestazione;
    private String coda;
    List<RecordSetArray> pResultSetArray;
    private String mvarsqlDefault;
    //TODO how does this get passed
    public String mvarsqlBase = null;
    private static String constParoleChiave = "DELETE ;INSERT ;UPDATE ;TABLE ;DROP ;ALTER ;CREATE ;TRUNCATE ";
    private String stringatempbase;
    private String nomeCampo;
    private String valore;
    FetchGmt pFetch;
    private List<GmtDictionary> pDictionaryArray;
    private GmtDictionary tempDict;
    private DialettoEnum dialetto;
    private String owner;
    private VerificaQueryHelper vQueryHelper;
    private final String SUFFIX_CLOSE = "#";
    final String PREFIX_NOMEQUERY = "NOME=";
    final String PREFIX_SQLQUERY = "SQL=";
    final String PREFIX_QUERY = "#QUERY";
    final String PREFIX_DICT = "#DICT";

    public SostituzioniValoriConQueryWork(String fileIn, String tipoFile, Map<String, String> params, DialettoEnum dialetto, String owner) {

	this.owner = owner;
	this.fileInRTF = fileIn;
	this.tipoFile = tipoFile;
	this.params = params;
	pResultSetArray = new ArrayList<RecordSetArray>();
	pFetch = new FetchGmt();
	sistemaMappaParametri();
	pDictionaryArray = new ArrayList<GmtDictionary>();
	this.dialetto = dialetto;
	this.vQueryHelper = new VerificaQueryHelper(this.owner, dialetto);
    }

    public SostituzioniValoriConQueryWork(String tipoFile, Map<String, String> params, DialettoEnum dialetto, String owner) {

	this.owner = owner;
	this.tipoFile = tipoFile;
	this.params = params;
	sistemaMappaParametri();
	this.dialetto = dialetto;
    }

    private void sistemaMappaParametri() {

	if (params != null) {
	    if (!params.containsKey("SOFTWARE")) {
		params.put("SOFTWARE", ORMHelper.getSoftware());
	    }
	    if (!params.containsKey("IDCOMUNE")) {
		params.put("IDCOMUNE", ORMHelper.getIdcomune());
	    }
	}
    }

    private String sostituisciParametriRTF(String testocompleto) {

	String input = new String(testocompleto);
	StringBuffer sb = new StringBuffer(input.length());
	String nomeParam = "";
	if (this.tipoFile.toUpperCase().equals("RTF")) {
	    String paramReg = "@PARAM\\.(.+?)@";
	    Pattern pattern = Pattern.compile(paramReg, Pattern.DOTALL);
	    Matcher matcher = pattern.matcher(input);
	    while (matcher.find()) {
		nomeParam = matcher.group(1).replaceAll("\\n", "").replaceAll("\\r", "");
		if (this.params.containsKey(nomeParam)) {
		    matcher.appendReplacement(sb, Matcher.quoteReplacement((String) this.params.get(nomeParam)));
		} else {
		    throw new BusinessValidationException("PARAMETRO NON TROVATO/PASSATO:\n " + nomeParam);
		}
	    }
	    matcher.appendTail(sb);
	}
	return sb.toString();
    }

    private String getBloccoBase(String testoOriginale) {

	/*String regex = "(#INIZIOBASE#)(.*?)(#FINEBASE#)";
	Pattern p = Pattern.compile(regex, Pattern.DOTALL);
	StringBuffer sb = new StringBuffer(testoOriginale);
	Matcher m = p.matcher(sb.toString());
	String testocompleto = "";
	boolean trovato = false;
	while (m.find()) {
	    trovato = true;
	    int start = m.start();
	    int end = m.end();
	    this.intestazione = sb.substring(0, start);
	    this.coda = sb.substring(end);
	    testocompleto = testocompleto.concat(m.group(1)).concat(m.group(2)).concat(m.group(3));
	    this.stestocompletooriginale = testocompleto;
	}
	if (trovato)
	    return this.stestocompletooriginale;
	else {
	    //Ci possono essere i TAGS allora restituisce la stringa originale
	    return testoOriginale;
	    //throw new BusinessValidationException("#INIZIOBASE# o #FINEBASE# non presente nel documento di origine");
	}*/
	int posInizioBase = testoOriginale.indexOf("#INIZIOBASE#");
	if (posInizioBase < 0) {
	    throw new BusinessValidationException("#INIZIOBASE# NON TROVATO CONTROLLA LA CORRETTEZZA DEL FILE");
	}
	int posFineBase = testoOriginale.indexOf("#FINEBASE#");
	if (posFineBase < 0)
	    throw new BusinessValidationException("#FINEBASE# NON TROVATO CONTROLLA LA CORRETTEZZA DEL FILE");
	this.intestazione = testoOriginale.substring(0, posInizioBase);
	this.coda = testoOriginale.substring(posFineBase + "#FINEBASE#".length());
	this.testocompleto = testoOriginale.substring(posInizioBase, posFineBase);
	return this.testocompleto;
    }

    private String trovaQuery(String nomeQuery, String testocompleto, String otherWhereClause) {

	boolean trovato = false;
	boolean ultimo = false;
	String sRiga = "";
	String sSQLRTF = "";
	String sQuery = "";
	String sNome = "";
	String testoIniziale = "";
	int posStartQuery;
	int posStartDict = -1;
	int posEnd;
	int posStart = -1;
	pResultSetArray.clear();
	if (nomeQuery.equals("QUERYDEFAULT")) {
	    RecordSetArray rd = new RecordSetArray();
	    pResultSetArray.add(rd);
	    pResultSetArray.get(0).setName(nomeQuery);
	    pResultSetArray.get(0).setSQLRtf(this.mvarsqlDefault);
	    pResultSetArray.get(0).setSQL(this.mvarsqlDefault);
	    trovato = true;
	    ultimo = true;
	} else {
	    ultimo = false;
	    while (ultimo == false) {
		sRiga = "";
		sQuery = "";
		sNome = "";
		testoIniziale = PREFIX_QUERY;
		posStartQuery = testocompleto.indexOf(testoIniziale, posStart + 1);
		if (!nomeQuery.equalsIgnoreCase("QUERYBASE")) {
		    testoIniziale = PREFIX_DICT;
		    posStartDict = testocompleto.indexOf(testoIniziale, posStart + 1);
		}
		testoIniziale = PREFIX_QUERY;
		posStart = posStartQuery;
		if (posStartDict >= 0) {
		    if (posStartDict > posStartQuery || posStartQuery < 0) {
			testoIniziale = PREFIX_DICT;
			posStart = posStartDict;
		    }
		}
		if (posStart >= 0) {
		    posEnd = testocompleto.indexOf(SUFFIX_CLOSE, posStart + 1);
		    sRiga = testocompleto.substring(posStart, posEnd + 1);
		    sSQLRTF = sRiga;
		    sRiga = sRiga.replace(testoIniziale, "");
		    sRiga = StringUtils.trimLeadingWhitespace(sRiga);
		    if (!sRiga.substring(0, PREFIX_NOMEQUERY.length()).equalsIgnoreCase(PREFIX_NOMEQUERY)) {
			throw new BusinessValidationException("TrovaQuery : Il parametro [NOME] non è scritto correttamente" + "[" + sRiga + "]");
		    }
		    sRiga = sRiga.substring(PREFIX_NOMEQUERY.length());
		    sRiga = StringUtils.trimLeadingWhitespace(sRiga);
		    sNome = sRiga.substring(0, sRiga.indexOf(" "));
		    sRiga = sRiga.substring(sNome.length() + 1);
		    sNome = sNome.trim();
		    sRiga = StringUtils.trimLeadingWhitespace(sRiga);
		    if (!sRiga.substring(0, PREFIX_SQLQUERY.length()).equalsIgnoreCase(PREFIX_SQLQUERY)) {
			throw new BusinessValidationException("TrovaQuery: Query nel testo non corretta [SQL], documento di origine non valido");
		    }
		    sRiga = sRiga.substring(PREFIX_SQLQUERY.length());
		    sRiga = StringUtils.trimLeadingWhitespace(sRiga);
		    sQuery = sRiga.substring(0, sRiga.length() - 1);
		    if (sNome.equalsIgnoreCase(nomeQuery)) {
			RecordSetArray r = new RecordSetArray();
			r.setName(sNome);
			r.setSQLRtf(sSQLRTF);
			r.setSQL(sQuery);
			pResultSetArray.add(r);
			ultimo = true;
			trovato = true;
		    }
		} else {
		    ultimo = true;
		}
	    }
	}
	if (trovato && !otherWhereClause.isEmpty()) {
	    SQLSelectHelper sqlHlpr = new SQLSelectHelper();
	    sqlHlpr.setSql(pResultSetArray.get(0).getSQL());
	    pResultSetArray.get(0).setSQL(sqlHlpr.getSql());
	    pFetch.setOtherWhereClause("");
	}
	if (trovato) {
	    return pResultSetArray.get(0).getSQL();
	}
	return "";
    }

    public static boolean controllaSicurezzaQuery(String query) {

	String gParole[] = constParoleChiave.split(";");
	boolean trovato = true;
	for (String s : gParole) {
	    if (query.toUpperCase().contains(s)) {
		trovato = false;
		break;
	    }
	}
	return trovato;
    }

    private String EliminaNull(String s, Map<String, Boolean> opts) {

	if (s == null) {
	    if (opts.get("seNumerico")) {
		return "0";
	    } else {
		return "";
	    }
	} else {
	    if (opts.get("seTrim")) {
		return FormatUtils.stringFormat(s.trim()).replace("\\\\u", "\\u");
	    } else if (opts.get("seNumerico")) {
		return s.replaceAll(",", ".");
	    } else {
		return FormatUtils.stringFormat(s).replace("\\\\u", "\\u");
	    }
	}
    }

    private void sostituisciFetch(Connection c) {

	String stringaorigine;
	String nuovastringa;
	String stringatemp = "";
	String nomecampo;
	String lastquery;
	trovaTestoFetch();
	if (!pFetch.getName().equalsIgnoreCase("QUERYBASE")) {
	    if (!pFetch.getName().isEmpty()) {
		trovaQuery(this.pFetch.getName(), this.testocompleto, this.pFetch.getOtherWhereClause());
		stringaorigine = this.pFetch.getTesto();
		nuovastringa = "";
		for (int i = 0; i < pResultSetArray.size(); i++) {
		    if (this.pResultSetArray.get(i).getName().equalsIgnoreCase(this.pFetch.getName())) {
			try {
			    pResultSetArray.get(i).setSQL(vQueryHelper.verificaQuery(pResultSetArray.get(i).getSQL(), this.dialetto));
			    lastquery = pResultSetArray.get(i).getSQL();
			    if (!controllaSicurezzaQuery(pResultSetArray.get(i).getSQL())) {
				throw new BusinessValidationException("Sicurezza Violata in query, Last Query = " + lastquery);
			    }
			    PreparedStatement ps = c.prepareStatement(pResultSetArray.get(i).getSQL());
			    ResultSet rs = ps.executeQuery();
			    ResultSetMetaData rsmdt = rs.getMetaData();
			    pResultSetArray.get(i).setResultSet(rs);
			    Map<String, Boolean> op = new HashMap<String, Boolean>();
			    op.put("seNumerico", false);
			    op.put("seTrim", true);
			    while (rs.next()) {
				stringatemp = stringaorigine;
				for (int j = 1; j <= rsmdt.getColumnCount(); j++) {
				    String colName = getColumnName(rsmdt, j);
				    nomecampo = "@" + pResultSetArray.get(i).getName() + "." + colName + "@";
				    stringatemp = stringatemp.replace(nomecampo.toUpperCase(), EliminaNull(rs.getString(colName), op));
				}
				nuovastringa = nuovastringa + stringatemp;
			    }
			} catch (SQLException e) {
			    e.printStackTrace();
			    log.error("sostituisciFetch {}", e);
			    throw new BusinessValidationException(
				    "QUERY/ISTRUZIONE NON VALIDO: \n" + pResultSetArray.get(i).getSQL() + "\n " + e.getMessage());
			}
		    }
		}
		this.testocompleto = this.testocompleto.substring(0, pFetch.getPosStart())
			+ nuovastringa
			+ this.testocompleto.substring(pFetch.getPosEnd() + "#ENDFETCH#".length());
		if (pResultSetArray.size() >= 1) {
		    this.testocompleto = this.testocompleto.replace(pResultSetArray.get(0).getSQLRtf(), "");
		}
	    }
	} else {
	    throw new BusinessValidationException("QUERYBASE TROVATO IN UN FETCHQUERY CONTROLLA LA SINTASSI");
	}
    }

    private void trovaTestoFetch() {

	int posStart;
	int posEnd;
	String prefixFetchQuery;
	String sNome;
	int fatti;
	int nextStartFetch;
	int nextEndFetch;
	final String STARTFETCH = "#FETCHQUERY";
	final String ENDFETCH = "#ENDFETCH#";
	String otherconditions;
	posStart = this.testocompleto.indexOf(STARTFETCH);
	if (posStart >= 0) {
	    posEnd = this.testocompleto.indexOf(SUFFIX_CLOSE, posStart + 1);
	    prefixFetchQuery = this.testocompleto.substring(posStart, posEnd + 1);
	    sNome = estrapolaNomeFetch(prefixFetchQuery);
	    otherconditions = estrapolaCondizioniFetch(prefixFetchQuery);
	    fatti = 1;
	    nextEndFetch = this.testocompleto.indexOf(ENDFETCH, posStart + 1);
	    nextStartFetch = this.testocompleto.indexOf(STARTFETCH, posStart + 1);
	    if (nextEndFetch < 0 && nextStartFetch < 0) {
		return;
	    }
	    while (fatti >= 1) {
		posEnd = nextEndFetch;
		if (nextStartFetch < 0 && nextEndFetch >= 0) {
		    fatti--;
		    nextEndFetch = this.testocompleto.indexOf(ENDFETCH, nextEndFetch + 1);
		    nextStartFetch = this.testocompleto.indexOf(STARTFETCH, nextEndFetch + 1);
		} else if (nextEndFetch < nextStartFetch) {
		    fatti--;
		    nextEndFetch = this.testocompleto.indexOf(ENDFETCH, nextEndFetch + 1);
		    nextStartFetch = this.testocompleto.indexOf(STARTFETCH, nextEndFetch + 1);
		} else if (nextEndFetch > nextStartFetch) {
		    //fatti++;
		    nextStartFetch = this.testocompleto.indexOf(STARTFETCH, nextEndFetch + 1);
		    nextEndFetch = this.testocompleto.indexOf(ENDFETCH, nextEndFetch + 1);
		} else if (nextStartFetch < 0 && nextEndFetch < 0) {
		    throw new BusinessValidationException("Non è stato trovato il tag #ENDFETCH# per la query " + sNome);
		}
	    }
	    this.pFetch.setName(sNome);
	    this.pFetch.setTesto(this.testocompleto.substring(posStart, posEnd).replace(prefixFetchQuery, ""));
	    this.pFetch.setTestoconintestazioni(this.testocompleto.substring(posStart, posEnd + ENDFETCH.length()));
	    this.pFetch.setPosStart(posStart);
	    this.pFetch.setPosEnd(posEnd);
	    this.pFetch.setRigaTestata(prefixFetchQuery);
	    this.pFetch.setOtherWhereClause(otherconditions);
	}
    }

    private String estrapolaCondizioniFetch(String rigaFecth) {

	String tabellaNome = "";
	final String CONDIZIONEFETCHQUERY = " AND ";
	int posStart = rigaFecth.indexOf(CONDIZIONEFETCHQUERY, 0);
	if (posStart >= 0) {
	    posStart = posStart + CONDIZIONEFETCHQUERY.length();
	    int posEnd = rigaFecth.indexOf(SUFFIX_CLOSE, posStart);
	    tabellaNome = rigaFecth.substring(posStart, posEnd);
	}
	return tabellaNome;
    }

    private String estrapolaNomeFetch(String rigaFetch) {

	int posStart = -1;
	int posEnd = -1;
	final String INIZIOFETCH = "#FETCHQUERY ";
	String nomeTabella = "";
	posStart = rigaFetch.indexOf(INIZIOFETCH, 0);
	if (posStart >= 0) {
	    posStart = posStart + INIZIOFETCH.length();
	    posEnd = rigaFetch.length() - 1;
	    if (posEnd < 0) {
		posEnd = rigaFetch.length();
	    }
	    nomeTabella = rigaFetch.substring(posStart, posEnd);
	}
	return nomeTabella;
    }

    public GmtDictionary trovaTestoDictionary(Connection c) {

	String tSql;
	ResultSet tRs;
	Map<String, Map<String, String>> tItem;
	Map<String, String> mapColVal;
	GmtDictionary pDictionary = new GmtDictionary();
	String testoDictionary = null;
	int posStart;
	int posEnd;
	String chiave;
	String dChiave, dValore;
	posStart = this.testocompleto.indexOf(PREFIX_DICT, 0);
	if (posStart >= 0) {
	    posEnd = this.testocompleto.indexOf(SUFFIX_CLOSE, posStart + 1);
	    testoDictionary = this.testocompleto.substring(posStart, posEnd + 1);
	}
	pDictionary.setNome(estrapolaNomeDictionary(testoDictionary));
	for (int pp = 0; pp < this.pDictionaryArray.size(); pp++) {
	    if (this.pDictionaryArray.get(pp).getNome().equals(pDictionary.getNome())) {
		throw new BusinessValidationException(" TrovaTestoDictionary E' già presente un dictionary con il nome " + pDictionary.getNome());
	    }
	}
	pDictionary.setSQL(trovaQuery(pDictionary.getNome(), testoDictionary, ""));
	tSql = vQueryHelper.verificaQuery(pDictionary.getSQL(), this.dialetto);
	try {
	    PreparedStatement pst = c.prepareStatement(tSql);
	    tRs = pst.executeQuery();
	    ResultSetMetaData rSMetaD = tRs.getMetaData();
	    tItem = new HashMap<String, Map<String, String>>();
	    while (tRs.next()) {
		chiave = tRs.getString(1);
		mapColVal = new HashMap<String, String>();
		for (int i = 1; i <= rSMetaD.getColumnCount(); i++) {
		    dChiave = getColumnName(rSMetaD, i);
		    Map<String, Boolean> opts = new HashMap<String, Boolean>();
		    opts.put("seNumerico", false);
		    opts.put("seTrim", true);
		    dValore = EliminaNull(tRs.getString(dChiave), opts);
		    mapColVal.put(dChiave, dValore);
		}
		tItem.put(chiave, mapColVal);
	    }
	    pDictionary.setItems(tItem);
	    this.pDictionaryArray.add(pDictionary);
	    this.testocompleto = this.testocompleto.replace(testoDictionary, "");
	} catch (SQLException e) {
	    throw new BusinessValidationException(e.getMessage() + "\nERROE QUERY " + tSql);
	}
	return pDictionary;
    }

    private String estrapolaNomeDictionary(String testoDictionary) {

	int posStart;
	int posEnd;
	final String INIZIODICT;
	String tNome;
	tNome = testoDictionary;
	INIZIODICT = "#DICT NOME=";
	posStart = tNome.indexOf(INIZIODICT);
	if (posStart >= 0) {
	    posStart = posStart + INIZIODICT.length();
	    posEnd = tNome.indexOf(" ", posStart);
	    if (posEnd < 0) {
		posEnd = tNome.length();
	    }
	    tNome = tNome.substring(posStart, posEnd);
	}
	return tNome;
    }

    private void sostituisciDictionary() {

	int posStart;
	String iniziodict;
	String testodasostituire;
	Map<String, String> p;
	for (Entry<?, ?> e : this.tempDict.getItems().entrySet()) {
	    iniziodict = "@" + tempDict.getNome() + "(" + e.getKey() + ")(\"";
	    posStart = this.testocompleto.indexOf(iniziodict, 0);
	    if (posStart >= 0) {
		posStart = posStart + iniziodict.length();
		p = this.tempDict.getItems().get(e.getKey());
		for (Entry<String, String> f : p.entrySet()) {
		    testodasostituire = iniziodict + f.getKey() + "\")@";
		    this.testocompleto = this.testocompleto.replace(testodasostituire, f.getValue());
		}
	    }
	}
    }

    private String sostituisciFunzioni(String testo) {

	String sRiga = "";
	String[] aRiga;
	String sFunzione = "";
	String restituisce = "";
	int posStart = 0;
	int posEnd;
	String valore1;
	String valore2;
	String operatoreConfronto;
	posStart = testo.indexOf("#SELECTCASE(", posStart);
	if (posStart >= 0) {
	    posEnd = testo.indexOf(SUFFIX_CLOSE, posStart + 1);
	    sRiga = testo.substring(posStart, posEnd + 1);
	    sFunzione = sRiga;
	    sRiga = sRiga.substring("#SELECTCASE(".length() + 1);
	    sRiga = sRiga.substring(0, sRiga.length() - 2);
	    aRiga = sRiga.split(";");
	    Map<String, Boolean> opts = new HashMap<String, Boolean>();
	    opts.put("seNumerico", false);
	    opts.put("seTrim", true);
	    valore1 = EliminaNull(aRiga[0], opts);
	    if (valore1.isEmpty()) {
		restituisce = "";
	    } else {
		for (int i = 2; i < aRiga.length; i += 2) {
		    valore2 = EliminaNull(aRiga[i].substring(1), opts);
		    operatoreConfronto = aRiga[i].substring(0, 1);
		    if (aRiga[1].equalsIgnoreCase("N")) {
			if (valore.isEmpty())
			    valore1 = "0";
			if (valore2.isEmpty())
			    valore2 = "0";
			if (operatoreConfronto.equals("=")) {
			    if (Double.parseDouble(valore1) == Double.parseDouble(valore2))
				restituisce = aRiga[i + 1];
			} else if (operatoreConfronto.equals("<")) {
			    if (Double.parseDouble(valore1) < Double.parseDouble(valore2))
				restituisce = aRiga[i + 1];
			} else if (operatoreConfronto.equals(">")) {
			    if (Double.parseDouble(valore1) > Double.parseDouble(valore2))
				restituisce = aRiga[i + 1];
			} else {
			    restituisce = "??????????????????";
			}
		    } else if (aRiga[1].equalsIgnoreCase("A")) {
			if (valore1.equalsIgnoreCase(valore2))
			    restituisce = aRiga[i + 1];
			else if (valore1.compareTo(valore2) > 0)
			    restituisce = aRiga[i + 1];
			else if (valore1.compareTo(valore2) < 0)
			    restituisce = aRiga[i + 1];
			else if (!valore1.equalsIgnoreCase(valore2))
			    restituisce = aRiga[i + 1];
		    } else {
			restituisce = "??????????????????";
		    }
		}
	    }
	    testo = testo.replace(sFunzione, restituisce);
	} else {
	    posStart = testo.indexOf("#CHARTODATA(", posStart);
	    if (posStart >= 0) {
		posEnd = testo.indexOf(SUFFIX_CLOSE, posStart + 1);
		sRiga = testo.substring(posStart, posEnd + 1);
		sFunzione = sRiga;
		sRiga = sRiga.substring("#CAHRTODATA(".length() + 1);
		sRiga = sRiga.substring(0, sRiga.length() - 2);
		aRiga = sRiga.split(";");
		if (aRiga.length == 8) {
		    if (aRiga[1].equalsIgnoreCase("ITA"))
			restituisce = aRiga[0].substring(6, 3) + "/" + aRiga[0].substring(4, 3) + "/" + aRiga[0].substring(0, 5);
		    else if (aRiga[1].equalsIgnoreCase("ENG"))
			restituisce = aRiga[0].substring(4, 3) + "/" + aRiga[0].substring(6, 3) + "/" + aRiga[0].substring(0, 5);
		    else {
			restituisce = aRiga[0].substring(4, 3) + "/" + aRiga[0].substring(6, 3) + "/" + aRiga[0].substring(0, 5);
		    }
		    testo = testo.replace(sFunzione, restituisce);
		} else {
		    testo = testo.replace(sFunzione, "");
		}
	    } else {
		posStart = testo.indexOf("#FORMAT(", posStart);
		if (posStart >= 0) {
		    posEnd = testo.indexOf(SUFFIX_CLOSE, posStart + 1);
		    sRiga = testo.substring(posStart, posEnd + 1);
		    sFunzione = sRiga;
		    sRiga = sRiga.substring("#FORMAT(".length() + 1);
		    sRiga = sRiga.substring(0, sRiga.length() - 2);
		    aRiga = sRiga.split(";");
		    restituisce = String.format(aRiga[0], aRiga[1]);
		    testo = testo.replace(sFunzione, restituisce);
		} else {
		}
	    }
	}
	return testo;
    }

    private void ricontrollaDictionary() {

	int posStart = -1;
	int posEnd = -1;
	String stringa;
	String iniziodict;
	for (int i = 0; i < this.pDictionaryArray.size(); i++) {
	    if (!pDictionaryArray.get(i).getNome().isEmpty()) {
		sostituisciDictionary();
		iniziodict = "@" + pDictionaryArray.get(i).getNome() + "(";
		posStart = this.testocompleto.indexOf(iniziodict, posStart);
		while (posStart >= 0) {
		    posEnd = testocompleto.indexOf("\")@", posStart);
		    if (posEnd < 0) {
			posEnd = testocompleto.length();
		    } else {
			posEnd = posEnd + 3;
		    }
		    stringa = testocompleto.substring(posStart, posEnd);
		    testocompleto = testocompleto.replace(stringa, "");
		    posStart = testocompleto.indexOf(iniziodict, 0);
		}
	    }
	}
    }

    private static boolean controllaPresenzaStringa(String stringaCampi, String valRicercare) {

	if (stringaCampi.indexOf(valRicercare) >= 0) {
	    return true;
	} else {
	    return false;
	}
    }

    private static String convertExpToECMA(String exp) {

	Pattern pattern = Pattern.compile("(\\=|\\<\\>)");
	Matcher matcher = pattern.matcher(exp);
	StringBuffer sb = new StringBuffer(exp.length());
	String captrGroup;
	boolean trovato = false;
	while (matcher.find()) {
	    trovato = true;
	    captrGroup = matcher.group();
	    if (captrGroup.equals("<>"))
		matcher.appendReplacement(sb, "!=");
	    else if (captrGroup.equals("="))
		matcher.appendReplacement(sb, "==");
	}
	matcher.appendTail(sb);
	if (trovato)
	    return sb.toString();
	else
	    return exp;
    }

    private static String splitAndReplaceCondizione(String c) {

	String regex = "(\\=|\\<\\>|\\>|\\<|\\>=|\\<=)";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(c);
	String cptrGroup = "";
	String[] cndtParams = null;
	String cnd = "";
	while (matcher.find()) {
	    cptrGroup = matcher.group();
	    cndtParams = c.split(cptrGroup);
	}
	for (int i = 0; i < cndtParams.length; i++) {
	    cndtParams[i] = cndtParams[i].replace("\"", "");
	}
	if (cndtParams.length == 2)
	    cnd = cnd.concat("\"").concat(cndtParams[0]).concat("\"").concat(cptrGroup).concat("\"").concat(cndtParams[1]).concat("\"");
	cnd = cnd.replaceAll("(\\n|\\r)", "");
	cnd = cnd.replace(System.getProperty("line.separator"), "");
	return cnd;
    }

    public static String sostituisciVariabiliGlobali(String fileContent) {

	String stampaSeInizio = "[-STAMPASE(";
	String stampaSeFinale = ")$$";
	String testoFinale = "$$-]";
	int tCounter = 0;
	int posInizio = 0;
	int posFine = -1;
	String lReplace;
	String lCondizione;
	String lTesto;
	String variabiliCompleta = "";
	String lCondizioneNuova;
	Boolean isCondizione;
	ScriptEngineManager scrptMngr = new ScriptEngineManager();
	ScriptEngine scrptEng = scrptMngr.getEngineByName("javascript");
	while (controllaPresenzaStringa(fileContent, stampaSeInizio)) {
	    lReplace = "";
	    if (tCounter >= 50) {
		throw new BusinessValidationException("Loop nella stampa delle VariabiliGlobali.SostituisciVariabiliGlobali");
	    }
	    posInizio = fileContent.indexOf(stampaSeInizio, 0) + stampaSeInizio.length();
	    posFine = fileContent.indexOf(stampaSeFinale, posInizio);
	    lCondizione = fileContent.substring(posInizio, posFine);
	    posInizio = posFine + stampaSeFinale.length();
	    posFine = fileContent.indexOf(testoFinale, posInizio);
	    variabiliCompleta = "";
	    if (posFine - posInizio >= 0) {
		lTesto = fileContent.substring(posInizio, posFine);
		variabiliCompleta = variabiliCompleta.concat(stampaSeInizio).concat(lCondizione).concat(stampaSeFinale).concat(lTesto)
			.concat(testoFinale);
		log.debug("sostituisciVariabiliGlobali variabiliCompleta {}", variabiliCompleta);
		lCondizioneNuova = "\"";
		lCondizioneNuova = splitAndReplaceCondizione(lCondizione);
		log.debug("sostituisciVariabiliGlobali splitAndReplaceCondizione(lCondizione): {}", lCondizioneNuova);
		try {
		    lCondizioneNuova = convertExpToECMA(lCondizioneNuova);
		    lCondizioneNuova = lCondizioneNuova.replace("\\u", ""); // FIX: UTF8 RTF CON ? ES: SOCIET\\u224? SEMPLICE AGRICOLA
		    log.debug("sostituisciVariabiliGlobali convertExpToECMA(lCondizioneNuova): {}", lCondizioneNuova);
		    isCondizione = (Boolean) scrptEng.eval(lCondizioneNuova);
		    log.debug("sostituisciVariabiliGlobali isCondizione: {}", isCondizione);
		    if (isCondizione) {
			lReplace = lTesto;
		    }
		} catch (Exception e) {
		    log.error("sostituisciVariabiliGlobali Errore ", e);
		    throw new RuntimeException(e.getMessage() + "\nERRORE SINTASSI: " + lCondizione, e);
		}
		fileContent = fileContent.replace(variabiliCompleta, lReplace);
	    }
	    tCounter++;
	}
	return fileContent;
    }

    public String decodeText(String value, String charset) {

	try {
	    return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(value.getBytes()), Charset.forName(charset))).readLine();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return value;
    }

    @Override
    public void execute(Connection c) throws SQLException {

	boolean isReadOnly = c.isReadOnly();
	try {
	    c.setReadOnly(true);
	    String testo = sostituisciParametriRTF(fileInRTF);
	    testo = getBloccoBase(testo);
	    String val = null;
	    int posStartFetch;
	    int posStartDict;
	    int posStartSelect;
	    int posStartCharToData;
	    int posStartFormat;
	    int elementi[] = new int[5];
	    this.stestocompletooriginale = testo;
	    this.testocompleto = "";
	    trovaQuery("QUERYBASE", testo, "");
	    if (pResultSetArray.size() == 0) {
		throw new BusinessValidationException("Run #QUERYBASE non presente nel documento di origine e non impostata");
	    }
	    if (pResultSetArray.get(0).getSQL().isEmpty()) {
		if (mvarsqlBase == null) {
		    throw new BusinessValidationException("Run #QUERYBASE non presente nel documento di origine e non impostata");
		} else {
		    mvarsqlBase = vQueryHelper.verificaQuery(mvarsqlBase, this.dialetto);
		    mvarsqlBase = sostituisciParametriRTF(mvarsqlBase);
		}
	    } else {
		pResultSetArray.get(0).setSQL(vQueryHelper.verificaQuery(pResultSetArray.get(0).getSQL(), this.dialetto));
		this.mvarsqlDefault = this.mvarsqlBase;
		this.mvarsqlBase = pResultSetArray.get(0).getSQL();
		mvarsqlBase = sostituisciParametriRTF(mvarsqlBase);
		this.stestocompletooriginale = this.stestocompletooriginale.replace(pResultSetArray.get(0).getSQLRtf(), "");
	    }
	    java.sql.PreparedStatement p = c.prepareStatement(mvarsqlBase);
	    log.debug("mvarsqlBase ==> {}", mvarsqlBase);
	    ResultSet rs = p.executeQuery();
	    ResultSetMetaData rsmdt = rs.getMetaData();
	    while (rs.next()) {
		this.stringatempbase = this.stestocompletooriginale;
		for (int k = 1; k <= rsmdt.getColumnCount(); k++) {
		    String colName = getColumnName(rsmdt, k);
		    this.nomeCampo = "@QUERYBASE\\." + colName + "@";
		    log.debug("this.nomeCampo ==> {}", this.nomeCampo);
		    Map<String, Boolean> opts = new HashMap<String, Boolean>();
		    opts.put("seTrim", true);
		    opts.put("seNumerico", false);
		    val = rs.getString(colName);
		    this.intestazione = this.intestazione.replaceAll(nomeCampo.toUpperCase(), EliminaNull(val, opts));
		    this.valore = EliminaNull(val, opts).replaceAll("(\\r|\\n)", "\\\\par");
		    this.stringatempbase = this.stringatempbase.replaceAll(this.nomeCampo.toUpperCase(), this.valore);
		    this.coda = this.coda.replaceAll(this.nomeCampo.toUpperCase(), this.valore);
		}
		this.testocompleto = this.testocompleto + this.stringatempbase;
		while (true) {
		    posStartFetch = this.testocompleto.indexOf("#FETCHQUERY");
		    posStartDict = this.testocompleto.indexOf(PREFIX_DICT);
		    posStartSelect = this.testocompleto.indexOf("#SELECTCASE");
		    posStartCharToData = this.testocompleto.indexOf("#CHARTODATA");
		    posStartFormat = this.testocompleto.indexOf("#FORMAT");
		    elementi[0] = posStartFetch;
		    elementi[1] = posStartDict;
		    elementi[2] = posStartSelect;
		    elementi[3] = posStartCharToData;
		    elementi[4] = posStartFormat;
		    Arrays.sort(elementi);
		    int count = 0;
		    for (int i = 0; i < elementi.length; i++) {
			if (elementi[i] < 0) {
			    count++;
			}
		    }
		    if (count == elementi.length)
			break;
		    for (int x = 0; x < elementi.length; x++) {
			if (elementi[x] >= 0) {
			    if (elementi[x] == posStartFetch) {
				sostituisciFetch(c);
			    } else if (elementi[x] == posStartDict) {
				tempDict = new GmtDictionary();
				tempDict = trovaTestoDictionary(c);
				sostituisciDictionary();
			    } else if (elementi[x] == posStartSelect || elementi[x] == posStartCharToData || elementi[x] == posStartFormat) {
				this.intestazione = sostituisciFunzioni(this.intestazione);
				this.testocompleto = sostituisciFunzioni(this.testocompleto);
			    }
			    break;
			}
		    }
		}
	    }
	    ricontrollaDictionary();
	    this.testocompleto = this.testocompleto.replace("#INIZIOBASE#", "");
	    this.testocompleto = this.testocompleto.replace("#FINEBASE#", "");
	    this.output = this.intestazione + this.testocompleto + this.coda;
	} catch (Exception e) {
	    log.error("execute() {}", e);
	    throw new RuntimeException(e);
	} finally {
	    c.setReadOnly(isReadOnly);
	}
    }

    private String getColumnName(ResultSetMetaData rsmdt, int k) throws SQLException {

	String colName = rsmdt.getColumnLabel(k);
	if (org.apache.commons.lang.StringUtils.isBlank(colName)) {
	    colName = rsmdt.getColumnName(k);
	}
	return colName;
    }
}
