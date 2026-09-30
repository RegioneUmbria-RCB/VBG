package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public class VerificaQueryHelper {

    private String owner;
    public DialettoEnum dialetto;

    public VerificaQueryHelper(String owner, DialettoEnum dialetto) {

	this.owner = owner;
	this.dialetto = dialetto;
    }

    public String verificaQuery(String query, DialettoEnum dialetto) {

	query = bonificaQuery(query);
	String queryCompleto;
	String vetQuery[];
	String mquery = null;
	final String vetQuerySeparator[] = { "UNION", "UNION ALL", "INTERSECT", "MINUS" };
	mquery = query;
	for (int i = 0; i < vetQuerySeparator.length; i++) {
	    vetQuery = split2(mquery, vetQuerySeparator[i], true, -1);
	    mquery = org.apache.commons.lang.StringUtils.join(vetQuery, vetQuerySeparator[i] + "$#!#$");
	}
	vetQuery = mquery.split("\\$#\\!#\\$");
	mquery = "";
	for (int i = 0; i < vetQuery.length; i++) {
	    queryCompleto = vetQuery[i];
	    queryCompleto = qualifica(queryCompleto);
	    queryCompleto = ricercaQueryAnnidate(queryCompleto, 0);
	    SQLSelectHelper sqlSelectHelper = new SQLSelectHelper();
	    if (dialetto.equals(DialettoEnum.SQLSERVER) || dialetto.equals(DialettoEnum.MYSQL)) {
		sqlSelectHelper.setSql(queryCompleto);
		queryCompleto = sqlSelectHelper.getSql();
		if (queryCompleto.contains("(+)")) {
		    queryCompleto = sqlOut(queryCompleto);
		}
	    } else if (dialetto.equals(DialettoEnum.ORACLE)) {
	    } else {
		throw new BusinessValidationException("VerificaQuery: Provider " + dialetto + " non gestito ");
	    }
	    mquery = mquery + queryCompleto;
	}
	return mquery;
    }

    private String bonificaQuery(String query) {

	if (StringUtils.isBlank(query)) {
	    return query;
	}
	query = query.trim();
	return query.replace("\n", "") //
		.replace("\r", "") //
		.replaceAll("(\\\\lquote\\s|\\\\rquote\\s|\\\\lquote|\\\\rquote|\\\\'91|\\\\'92)", "'") //
		.trim();
    }

    public static String capitalizeKeyWords(String query) {

	String res = query.replaceAll("(?i)\\bselect\\b", "SELECT");
	res = res.replaceAll("(?i)\\bwhere\\b", "WHERE");
	res = res.replaceAll("(?i)\\bfrom\\b", "FROM");
	res = res.replaceAll("(?i)\\bgroup by\\b", "GROUP BY");
	res = res.replaceAll("(?i)\\border by\\b", "ORDER BY");
	res = res.replaceAll("(?i)\\band\\b", "AND");
	res = res.replaceAll("(?i)\\bdecode\\b", "DECODE");
	res = res.replaceAll("(?i)\\blpad\\b", "LPAD");
	res = res.replaceAll("(?i)\\bto_char\\b", "TO_CHAR");
	return res;
    }

    private String sqlOut(String queryCompleto) {

	String tmpArr[];
	String tmpArr1[];
	String tmpArr2[];
	String sqlTblArr[];
	String sqlANDArr[];
	String sqlSelect;
	String sqlFrom;
	String sqlWhere;
	String sqlOrdBy = "";
	String sqlGroupBy = "";
	String sqlOut;
	int tmpVal;
	String sqlJoin;
	int posTableAlias;
	final String FROM = " FROM ";
	final String WHERE = " WHERE ";
	final String GROUP_BY = " GROUP BY ";
	final String ORDER_BY = " ORDER BY ";
	List<String> arrJOIN = new ArrayList<String>();
	String cjRis;
	List<String> sqlANDList = new ArrayList<String>();
	queryCompleto = capitalizeKeyWords(queryCompleto);
	if (!queryCompleto.substring(0, "SELECT".length()).toUpperCase().equals("SELECT"))
	    return null;
	tmpArr = split2(queryCompleto, FROM, true, -1);
	sqlSelect = tmpArr[0];
	tmpArr1 = split2(tmpArr[1], WHERE, true, -1);
	sqlFrom = tmpArr1[0];
	sqlWhere = tmpArr1[1];
	if (sqlWhere.indexOf(GROUP_BY) >= 0) {
	    tmpArr2 = split2(sqlWhere, GROUP_BY, true, -1);
	    sqlWhere = tmpArr2[0];
	    sqlGroupBy = GROUP_BY + tmpArr2[1];
	}
	if (sqlGroupBy.indexOf(ORDER_BY) >= 0) {
	    tmpArr2 = split2(sqlGroupBy, ORDER_BY, true, -1);
	    sqlGroupBy = tmpArr2[0];
	    sqlOrdBy = ORDER_BY + tmpArr2[1];
	} else if (sqlWhere.indexOf(ORDER_BY) >= 0) {
	    tmpArr2 = split2(sqlWhere, ORDER_BY, true, -1);
	    sqlWhere = tmpArr2[0];
	    sqlOrdBy = ORDER_BY + tmpArr2[1];
	}
	sqlTblArr = split2(sqlFrom.toUpperCase(), ",", true, -1);
	sqlANDArr = split2(sqlWhere, " AND ", true, -1);
	sqlANDList.addAll(Arrays.asList(sqlANDArr));
	for (int i = 0; i < sqlANDArr.length; i++) {
	    tmpVal = sqlANDArr[i].indexOf("(+)");
	    if (tmpVal >= 0) {
		sqlJoin = "";
		if (sqlANDArr[i].indexOf("=") > tmpVal) {
		    sqlJoin = "RIGHT";
		} else {
		    sqlJoin = "LEFT";
		}
		cjRis = creaJoin(sqlJoin, sqlANDList, i, sqlTblArr);
		arrJOIN.add(cjRis);
		sqlANDArr[i] = "";
	    }
	}
	final String dblJoinDelimiter = " ON ";
	String joinOnBlock = "";
	/* *********************************************************************************
	     RISOLUZIONE DELLE JOIN DOPPIE
	     	-PER OGNI JOIN ESTERNA
		-CHE NON SIA NULLA
	     	-CONTROLLA NELLA LISTA
	      	-(TRANNE IN QUELLA IN CUI SI STA LAVORANDO)
	     	-(E CHE NON SIANO NULLE)
	   ********************************************************************************* */
	for (int i = 0; i < arrJOIN.size(); i++) {
	    if (!arrJOIN.get(i).isEmpty()) {
		joinOnBlock = arrJOIN.get(i).trim().substring(0, arrJOIN.get(i).indexOf(dblJoinDelimiter) + dblJoinDelimiter.length());
		for (int j = 0; j < arrJOIN.size(); j++) {
		    if (i != j) {
			if (!arrJOIN.get(j).isEmpty()) {
			    if (arrJOIN.get(j).substring(0, joinOnBlock.length()).toUpperCase().equalsIgnoreCase(joinOnBlock)) {
				arrJOIN.set(i, arrJOIN.get(i) + arrJOIN.get(j).replace(joinOnBlock, " AND "));
				arrJOIN.set(j, "");
			    }
			}
		    }
		}
	    }
	}
	String leftTableTwice = "";
	for (int i = 0; i < arrJOIN.size(); i++) {
	    if (!arrJOIN.get(i).isEmpty()) {
		leftTableTwice = arrJOIN.get(i).trim().substring(0, arrJOIN.get(i).indexOf("OUTER"));
		for (int j = 0; j < arrJOIN.size(); j++) {
		    if (i != j) {
			if (!arrJOIN.get(j).isEmpty()) {
			    if (arrJOIN.get(j).substring(0, leftTableTwice.length()).toUpperCase().equalsIgnoreCase(leftTableTwice)) {
				arrJOIN.set(i, arrJOIN.get(i) + arrJOIN.get(j).replace(leftTableTwice, " LEFT "));
				arrJOIN.set(j, "");
			    }
			}
		    }
		}
	    }
	}
	//VERIFICA DI JOIN MULTIPLE
	String leftTableOnRightSide = "";
	for (int i = 0; i < arrJOIN.size(); i++) {
	    if (!arrJOIN.get(i).isEmpty()) {
		leftTableOnRightSide = arrJOIN.get(i).trim().substring(0, arrJOIN.get(i).indexOf("LEFT OUTER"));
		for (int j = 0; j < arrJOIN.size(); j++) {
		    if (i != j) {
			if (!arrJOIN.get(j).isEmpty() && !arrJOIN.get(i).isEmpty()) {
			    if (arrJOIN.get(j).indexOf("JOIN " + leftTableOnRightSide + " ON") >= 0) {
				arrJOIN.set(j,
					arrJOIN.get(j).replace("JOIN " + leftTableOnRightSide + " ON", "JOIN " + arrJOIN.get(i).trim() + " ON"));
				arrJOIN.set(i, "");
			    }
			}
		    }
		}
	    }
	}
	String allJoins = "";
	for (int i = 0; i < arrJOIN.size(); i++) {
	    if (!arrJOIN.get(i).isEmpty())
		allJoins = allJoins + arrJOIN.get(i) + ",";
	}
	String tableAliasName = "";
	for (int i = 0; i < sqlTblArr.length; i++) {
	    if (!sqlTblArr[i].isEmpty()) {
		sqlTblArr[i] = sqlTblArr[i].trim();
		posTableAlias = sqlTblArr[i].indexOf(" ");
		if (posTableAlias >= 0) {
		    tableAliasName = owner + "." + sqlTblArr[i].substring(posTableAlias + 1);
		    if (allJoins.indexOf("JOIN " + tableAliasName) >= 0) {
			allJoins = allJoins.replace("JOIN " + tableAliasName, "JOIN " + sqlTblArr[i]);
			sqlTblArr[i] = "";
		    }
		    if (allJoins.indexOf("," + tableAliasName) >= 0) {
			allJoins = allJoins.replace("," + tableAliasName, "," + sqlTblArr[i]);
			sqlTblArr[i] = "";
		    }
		}
	    }
	    if (!sqlTblArr[i].isEmpty()) {
		if (allJoins.toUpperCase().indexOf((sqlTblArr[i] + " LEFT ").toUpperCase()) == -1
			&& (allJoins.toUpperCase().indexOf((sqlTblArr[i] + " ON ").toUpperCase()) == -1))
		    allJoins = allJoins + sqlTblArr[i] + ",";
	    }
	}
	allJoins = allJoins.substring(0, allJoins.length() - 1);
	sqlOut = sqlSelect + " FROM " + allJoins + " WHERE ";
	String ands = "";
	for (int i = 0; i < sqlANDArr.length; i++) {
	    if (!sqlANDArr[i].isEmpty())
		ands = ands + sqlANDArr[i] + " AND ";
	}
	if (!ands.isEmpty())
	    ands = ands.substring(0, ands.length() - 4);
	else
	    sqlOut = sqlOut.substring(0, sqlOut.length() - " WHERE ".length());
	return sqlOut + ands + sqlGroupBy + sqlOrdBy;
    }

    private String creaJoin(String sqlJoin, List<String> sqlANDList, int i, String[] sqlTblArr) {

	String tmpArr[];
	String tmpVal;
	String outStr;
	String sqlCondition;
	int dotPos1;
	int dotPos2;
	String fixVaLString;
	String tabs[] = new String[2];
	sqlCondition = sqlANDList.get(i);
	sqlCondition = sqlCondition.replace("(+)", "");
	tmpArr = split2(sqlCondition, "=", true, -1);
	dotPos1 = tmpArr[0].indexOf(".");
	dotPos2 = tmpArr[1].indexOf(".");
	if (dotPos1 == -1 || dotPos2 == -1) {
	    if (dotPos1 == -1)
		fixVaLString = "(" + tmpArr[1] + "=" + tmpArr[0] + " OR " + tmpArr[1] + " IS NULL)";
	    else
		fixVaLString = "(" + tmpArr[0] + "=" + tmpArr[1] + " OR " + tmpArr[0] + " IS NULL)";
	    sqlANDList.add(fixVaLString);
	    return "";
	}
	tmpArr[0] = extractTableName(tmpArr[0]);
	tmpArr[1] = extractTableName(tmpArr[1]);
	if (sqlJoin.toUpperCase().equals("RIGHT")) {
	    tmpVal = tmpArr[0].toUpperCase();
	    tmpArr[0] = tmpArr[1].trim().toUpperCase();
	    tmpArr[1] = tmpVal;
	    sqlCondition = revSQL(sqlCondition);
	}
	tabs[0] = find(sqlTblArr, owner + tmpArr[0], "." + tmpArr[0]);
	if (tabs[0] == null)
	    tabs[0] = owner + "." + tmpArr[0];
	if (tabs[1] == null)
	    tabs[1] = owner + "." + tmpArr[1];
	outStr = tabs[0] + " LEFT OUTER JOIN " + tabs[1] + " ON " + sqlCondition;
	return outStr;
    }

    private String find(String[] sqlTblArr, String textToFind, String alternativeTextToFind) {

	for (int i = 0; i < sqlTblArr.length; i++) {
	    if (sqlTblArr[i].toUpperCase().trim().equalsIgnoreCase(alternativeTextToFind.trim()))
		return sqlTblArr[i].trim();
	}
	return null;
    }

    private String revSQL(String sqlCondition) {

	String tmpArr[];
	tmpArr = split2(sqlCondition, "=", true, -1);
	return tmpArr[1].trim() + "=" + tmpArr[0].trim() + " ";
    }

    private String extractTableName(String sqlA) {

	int tPos;
	String retTableName = "";
	tPos = sqlA.indexOf(".", 0);
	if (tPos >= 0) {
	    retTableName = sqlA.substring(0, tPos);
	    tPos = retTableName.lastIndexOf("(");
	    if (tPos >= 0)
		retTableName = retTableName.substring(tPos + 1);
	    tPos = retTableName.lastIndexOf("'");
	    if (tPos >= 0)
		retTableName = retTableName.substring(tPos + 1);
	    tPos = retTableName.lastIndexOf("\"\"");
	    if (tPos >= 0)
		retTableName = retTableName.substring(tPos + 1);
	}
	return retTableName.trim();
    }

    private String ricercaQueryAnnidate(String queryCompleto, int startPos) {

	int wherePosition;
	String tmpStr = "";
	char tChar;
	int conteggioParentesi;
	int queryAnniDataStart;
	int queryAnniDataEnd;
	boolean esci;
	int posIterator;
	String queryAnniDateQualificate;
	queryAnniDateQualificate = queryCompleto;
	//se instr non trova nessun'occorrenza a partire dalla posizione data ricomincia dall'inizio!!!
	wherePosition = queryCompleto.indexOf("(", startPos);
	//where non trovato, impossibile che ci sia una query annidata
	if (wherePosition == -1 || wherePosition < startPos)
	    return queryAnniDateQualificate;
	//se trovo una parentesi aperta è possibile che ci sia una query annidata
	posIterator = wherePosition;
	conteggioParentesi = 0;
	queryAnniDataStart = -1;
	queryAnniDataEnd = -1;
	esci = false;
	while (posIterator < queryCompleto.length() && !esci) {
	    tChar = queryCompleto.charAt(posIterator);
	    if (String.valueOf(tChar).equals("("))
		conteggioParentesi++;
	    else if (String.valueOf(tChar).equals(")")) {
		if (conteggioParentesi > 0) {
		    conteggioParentesi--;
		    if (queryAnniDataStart > -1 && conteggioParentesi == 0) {
			queryAnniDataEnd = posIterator;
			esci = true;
		    }
		}
		tmpStr = "";
	    } else if (String.valueOf(tChar).equals(" ")) {
		if (queryAnniDataStart == -1 && conteggioParentesi > 0) {
		    if (tmpStr.compareToIgnoreCase("SELECT") == 0)
			queryAnniDataStart = posIterator - "SELECT".length();
		    tmpStr = "";
		}
	    } else {
		if (queryAnniDataStart == -1 && conteggioParentesi > 0)
		    tmpStr = tmpStr + String.valueOf(tChar);
	    }
	    posIterator++;
	}
	if (esci) {
	    queryAnniDateQualificate = queryCompleto.substring(0, queryAnniDataStart) +
		    " " +
		    qualifica(queryCompleto.substring(queryAnniDataStart, queryAnniDataEnd)) +
		    " " +
		    queryCompleto.substring(queryAnniDataEnd);
	    return ricercaQueryAnnidate(queryAnniDateQualificate, queryAnniDataStart);
	} else {
	    return queryCompleto;
	}
    }

    private String qualifica(String queryCompleto) {

	String op;
	String preString = "";
	String postString = "";
	String queryQualificata = null;
	if (queryCompleto.isEmpty())
	    return queryCompleto;
	queryCompleto = queryCompleto.trim();
	op = queryCompleto.substring(0, queryCompleto.indexOf(" "));
	if (op.equalsIgnoreCase("SELECT")) {
	    preString = "FROM";
	    postString = "WHERE,ORDER BY,GROUP BY";
	}
	if (!preString.isEmpty()) {
	    queryQualificata = qualificaTabelle(queryCompleto, preString, postString);
	    return queryQualificata;
	} else {
	    return queryCompleto;
	}
    }

    private String qualificaTabelle(String queryCompleto, String preString, String postString) {

	int sqlPre = -1;
	int sqlPost = -1;
	String arrTabelle[];
	String tabelleQualificate = "";
	String queryConTabelleQualificate = "";
	String preDelim[];
	String postDelim[];
	if (owner.isEmpty() || queryCompleto.isEmpty())
	    return queryCompleto;
	preDelim = preString.split(",");
	postDelim = postString.split(",");
	for (int i = 0; i < preDelim.length; i++) {
	    queryCompleto = queryCompleto.replaceAll("(?i)" + preDelim[i], preDelim[i]);
	    sqlPre = queryCompleto.indexOf(preDelim[i], 0) + preDelim[i].length() + 1;
	    if (sqlPre >= 0)
		break;
	}
	for (int i = 0; i < postDelim.length; i++) {
	    queryCompleto = queryCompleto.replaceAll("(?i)" + postDelim[i], postDelim[i]);
	    sqlPost = queryCompleto.indexOf(postDelim[i], sqlPre);
	    if (sqlPost >= 0)
		break;
	}
	if (sqlPost < 0)
	    sqlPost = queryCompleto.length();
	arrTabelle = queryCompleto.substring(sqlPre, sqlPost).split(",");
	if (arrTabelle[0].trim().indexOf(".") >= 0)
	    tabelleQualificate = " " + arrTabelle[0].trim();
	else
	    tabelleQualificate = " " + owner + "." + arrTabelle[0].trim();
	for (int h = 1; h < arrTabelle.length; h++) {
	    if (arrTabelle[h].trim().indexOf(".") >= 0)
		tabelleQualificate = " " + arrTabelle[h].trim();
	    else
		tabelleQualificate = tabelleQualificate + ", " + owner + "." + arrTabelle[h].trim();
	}
	queryConTabelleQualificate = queryCompleto.substring(0, sqlPre) + tabelleQualificate;
	if (sqlPost < queryCompleto.length())
	    queryConTabelleQualificate = queryConTabelleQualificate + queryCompleto.substring(sqlPost - 1);
	return queryConTabelleQualificate;
    }

    public static String[] split2(String query, String separator, boolean useParenthesisAsDelimiter, int limit) {

	String ristext = "";
	boolean isInString = false;
	int isInParenthesis = 0;
	char tChar = 0;
	int occurences = 0;
	String risTextSubSeq = "";
	while (!query.isEmpty()) {
	    tChar = query.substring(0, 1).charAt(0);
	    if (String.valueOf(tChar).equals("'"))
		isInString = !isInString;
	    else if (String.valueOf(tChar).equals("(")) {
		if (useParenthesisAsDelimiter) {
		    if (!isInString)
			isInParenthesis++;
		}
	    }
	    ristext = ristext + String.valueOf(tChar);
	    if (!isInString && isInParenthesis == 0) {
		if (ristext.length() > separator.length())
		    risTextSubSeq = ristext.substring(ristext.length() - separator.length()).toUpperCase();
		if (risTextSubSeq.equals(separator.toUpperCase())) {
		    occurences++;
		    if (limit > 0 && limit < occurences) {
			//E' stato specificato un limite ed è stato superato, quindi il testo rimanente deve far parte dell'ultima posizione del vettore
		    } else {
			ristext = ristext.substring(0, ristext.length() - separator.length()) + "$@#@$";
		    }
		}
	    }
	    if (String.valueOf(tChar).equals(")")) {
		if (!isInString) {
		    if (useParenthesisAsDelimiter && isInParenthesis > 0)
			isInParenthesis--;
		}
	    }
	    query = query.substring(1);
	}
	return ristext.split("\\$\\@#\\@\\$");
    }

    private static String createMySqlStatement(String parameter[]) {

	int lastParameter;
	String elseParameter;
	String ris = "";
	lastParameter = parameter.length;
	if (lastParameter < 3)
	    throw new BusinessValidationException("Errore createMysqlCaseStatment:  La Fuzione ORACLE DECODE(" +
		    org.apache.commons.lang.StringUtils.join(parameter, ",") +
		    " deve avere almeno 3 parametri per essere convertita in MySql. ");
	ris = "CASE ";
	ris = ris.concat(parameter[0]);
	ris = ris.concat(" WHEN " + parameter[1]);
	ris = ris.concat(" THEN " + parameter[2]);
	elseParameter = "";
	if (lastParameter % 2 == 0) {
	    elseParameter = " ELSE " + parameter[lastParameter - 1];
	    lastParameter--;
	}
	for (int i = 3; i < lastParameter; i++) {
	    if (i % 2 == 0)
		ris = ris.concat(" THEN " + parameter[i]);
	    else
		ris = ris.concat(" WHEN " + parameter[i]);
	}
	ris = ris.concat(elseParameter + " END");
	return ris;
    }

    public static String replaceFieldContentToMySql(String content) {

	String retContent;
	retContent = content;
	retContent = retContent.replace("TO_DATE('',", "TO_DATE(null,");
	retContent = retContent.replace(",'DD-MM-YYYY')", ",'DD/MM/YYYY')");
	retContent = retContent.replace(", 'DD-MM-YYYY')", ",'DD/MM/YYYY')");
	retContent = retContent.replace(", 'DD/MM/YYYY')", ",'DD/MM/YYYY')");
	retContent = retContent.replace("\\", "\\\\");
	retContent = replacePipeToMySql(retContent);
	retContent = replaceDecodeToMySql(retContent);
	retContent = replaceLPadToMySql(retContent);
	retContent = replaceDateFucntion(retContent);
	retContent = retContent.replace("NVL(", "ifnull(");
	return retContent;
    }

    private static String replacePipeToMySql(String contentParam) {

	String params[];
	String params2[];
	String params3[];
	String paramsTxt;
	String retVal;
	retVal = contentParam;
	params = VerificaQueryHelper.split2(retVal, "||", true, -1);
	if (params.length > 1) {
	    retVal = "CONCAT_WS(''," + org.apache.commons.lang.StringUtils.join(params, ",") + ")";
	}
	/*
	 * Cerco se è presente un'altro testo contenente || all'interno di una parentesi
	 * per ogni contenuto all'interno delle parentesi cerco ||
	 */
	paramsTxt = retVal;
	retVal = "";
	params = VerificaQueryHelper.split2(paramsTxt, "(", false, -1);
	paramsTxt = org.apache.commons.lang.StringUtils.join(params, "($,$");
	params = VerificaQueryHelper.split2(paramsTxt, ")", false, -1);
	if (params.length < 2) {
	    paramsTxt.concat(")$,$");
	} else {
	    paramsTxt = org.apache.commons.lang.StringUtils.join(params, ")$,$");
	    if (contentParam.charAt(contentParam.length() - 1) == ')') {
		paramsTxt += ")$,$";
	    }
	}
	/*
	 * In questa fase la funzione ReplaceDecodeToMySql è già passata quindi potrei trovare un caso del tipo:
	 * case alberoproc_l4.sc_descrizione when null then '' ELSE alberoproc_l4.sc_descrizione || ' - ' end
	 * quindi devo considerare il "case", "when", "else" e "end" come fossero parentesi
	 */
	params = VerificaQueryHelper.split2(paramsTxt, " CASE ", false, -1);
	paramsTxt = org.apache.commons.lang.StringUtils.join(params, "$,$ CASE $,$");
	params = VerificaQueryHelper.split2(paramsTxt, " WHEN ", false, -1);
	paramsTxt = org.apache.commons.lang.StringUtils.join(params, "$,$ WHEN $,$");
	params = VerificaQueryHelper.split2(paramsTxt, " ELSE ", false, -1);
	paramsTxt = org.apache.commons.lang.StringUtils.join(params, "$,$ ELSE $,$");
	params = VerificaQueryHelper.split2(paramsTxt, " END ", false, -1);
	paramsTxt = org.apache.commons.lang.StringUtils.join(params, "$,$ END $,$");
	/*
	 * All'interno di una parentesi potrebbero stare parametri di una funzione separati da virgola, quindi
	 * per ogni parametro vedo se c'è il ||
	 */
	params = paramsTxt.split("\\$,\\$");
	for (int i = 0; i < params.length; i++) {
	    params3 = VerificaQueryHelper.split2(params[i], ",", true, -1);
	    for (int j = 0; j < params3.length; j++) {
		if (j > 0)
		    retVal = retVal + ",";
		params2 = VerificaQueryHelper.split2(params3[j], "||", true, -1);
		if (params2.length > 1) {
		    if (params2[0].trim().isEmpty() || params2[1].trim().isEmpty()) {
			retVal = retVal + params3[j];
		    } else {
			retVal = retVal + " CONCAT_WS(''," + org.apache.commons.lang.StringUtils.join(params2, ",") + ") ";
		    }
		} else {
		    retVal = retVal + params2[0];
		}
	    }
	}
	return retVal;
    }

    private static String replaceDecodeToMySql(String content) {

	String retVal = content;
	String DECODETEXT = "DECODE(";
	String txt;
	String findTxt;
	String[] decodeParameter;
	String replaceTxt;
	int pos = retVal.indexOf(DECODETEXT, 0);
	while (pos >= 0) {
	    txt = retVal.substring(pos + DECODETEXT.length());
	    txt = VerificaQueryHelper.split2(txt, ")", true, 1)[0];
	    findTxt = DECODETEXT + txt + ")";
	    decodeParameter = VerificaQueryHelper.split2(txt, ",", true, -1);
	    replaceTxt = createMySqlStatement(decodeParameter);
	    retVal = retVal.replace(findTxt, replaceTxt);
	    pos = retVal.indexOf(DECODETEXT, pos + DECODETEXT.length());
	}
	return retVal;
    }

    private static String replaceLPadToMySql(String content) {

	final String LPADTXT = "LPAD(";
	String lPadParams = "";
	String retVal = content;
	String[] lPadParamsVet;
	String txtToPad;
	String padLen;
	if (content.startsWith(LPADTXT)) {
	    content = content.trim();
	    lPadParams = content.substring(LPADTXT.lastIndexOf("("));
	    lPadParams = lPadParams.substring(1, lPadParams.lastIndexOf(")") - 1);
	    lPadParamsVet = lPadParams.split(",");
	    txtToPad = lPadParamsVet[0];
	    padLen = lPadParamsVet[1];
	    String padFill;
	    if (lPadParamsVet.length == 2)
		padFill = "' '";
	    else
		padFill = lPadParamsVet[2];
	    retVal = LPADTXT + txtToPad + "," + padLen + "," + padFill + ")";
	}
	return retVal;
    }

    private static String replaceDateFucntion(String content) {

	String oldDateFunction;
	String newDateFunction;
	String oldDateFormat;
	String newDateFormat;
	//dialetto.equals(DIALETTO.MYSQL)
	if (true) {
	    oldDateFunction = "TO_CHAR(";
	    newDateFunction = "DATE_FORMAT(";
	    oldDateFormat = ",'DD/MM/YYYY HH24:MI:SS')";
	    newDateFormat = ",'%d/%m/%Y %H:%i:%s')";
	    content = replaceDate(content, oldDateFunction, oldDateFormat, newDateFunction, newDateFormat);
	    //---------------------------
	    oldDateFunction = "TO_CHAR(";
	    newDateFunction = "DATE_FORMAT(";
	    oldDateFormat = ",'HH24:MI:SS')";
	    newDateFormat = ",'%H:%i:%s')";
	    content = replaceDate(content, oldDateFunction, oldDateFormat, newDateFunction, newDateFormat);
	    //---------------------------
	    oldDateFunction = "TO_CHAR(";
	    newDateFunction = "DATE_FORMAT(";
	    oldDateFormat = ",'DD/MM/YYYY')";
	    newDateFormat = ",'%d/%m/%Y')";
	    content = replaceDate(content, oldDateFunction, oldDateFormat, newDateFunction, newDateFormat);
	    //---------------------------
	    oldDateFunction = "TO_CHAR(";
	    newDateFunction = "DATE_FORMAT(";
	    oldDateFormat = ",'YYYY')";
	    newDateFormat = ",'%Y')";
	    content = replaceDate(content, oldDateFunction, oldDateFormat, newDateFunction, newDateFormat);
	    //---------------------------
	    oldDateFunction = "TO_CHAR(";
	    newDateFunction = "DATE_FORMAT(";
	    oldDateFormat = ",'MM')";
	    newDateFormat = ",'%m')";
	    content = replaceDate(content, oldDateFunction, oldDateFormat, newDateFunction, newDateFormat);
	    //---------------------------
	    oldDateFunction = "TO_CHAR(";
	    newDateFunction = "DATE_FORMAT(";
	    oldDateFormat = ",'DD')";
	    newDateFormat = ",'%D')";
	    content = replaceDate(content, oldDateFunction, oldDateFormat, newDateFunction, newDateFormat);
	    //-------------------------------------------------------------------
	    //---------------------------------------------------
	    oldDateFunction = "TO_DATE(";
	    newDateFunction = "STR_TO_DATE(";
	    oldDateFormat = ",'DD/MM/YYYY HH24:MI:SS')";
	    newDateFormat = ",'%d/%m/%Y %H:%i:%s')";
	    content = replaceDate(content, oldDateFunction, oldDateFormat, newDateFunction, newDateFormat);
	    //---------------------------
	    oldDateFunction = "TO_DATE(";
	    newDateFunction = "STR_TO_DATE(";
	    oldDateFormat = ",'HH24:MI:SS')";
	    newDateFormat = ",'%H:%i:%s')";
	    content = replaceDate(content, oldDateFunction, oldDateFormat, newDateFunction, newDateFormat);
	    //---------------------------
	    oldDateFunction = "TO_DATE(";
	    newDateFunction = "STR_TO_DATE(";
	    oldDateFormat = ",'DD/MM/YYYY')";
	    newDateFormat = ",'%d/%m/%Y')";
	    content = replaceDate(content, oldDateFunction, oldDateFormat, newDateFunction, newDateFormat);
	    //---------------------------
	    oldDateFunction = "TO_DATE(";
	    newDateFunction = "STR_TO_DATE(";
	    oldDateFormat = ",'YYYYMMDD')";
	    newDateFormat = ",'%Y%m%d')";
	    content = replaceDate(content, oldDateFunction, oldDateFormat, newDateFunction, newDateFormat);
	}
	return content;
    }

    private static String replaceDate(String content, String oldDateFunction, String oldDateFormat, String newDateFunction, String newDateFormat) {

	int posStart = content.indexOf(oldDateFunction, 0);
	int pos = content.indexOf(oldDateFormat, posStart + 1);
	if (pos < 0)
	    posStart = -1;
	while (posStart >= 0) {
	    content = content.substring(0, posStart) + content.substring(posStart).replace(oldDateFunction, newDateFunction);
	    content = content.substring(0, posStart) + content.substring(posStart).replace(oldDateFormat, newDateFormat);
	    posStart = content.indexOf(oldDateFunction, posStart + newDateFunction.length());
	}
	return content;
    }
}
