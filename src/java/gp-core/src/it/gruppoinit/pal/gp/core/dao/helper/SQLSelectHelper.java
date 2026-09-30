package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public class SQLSelectHelper {

    private static final Logger log = LoggerFactory.getLogger(SQLSelectHelper.class);
    private String orderBy;
    private String groupBy;
    private String where;
    private String from;
    private String select;
    private String sql;

    class SelectField {

	String nameS;
	String alias;

	public String getNameS() {

	    return nameS;
	}

	public void setNameS(String nameS) {

	    this.nameS = nameS;
	}

	public String getAlias() {

	    return alias;
	}

	public void setAlias(String alias) {

	    this.alias = alias;
	}
    }

    class OrderByField {

	String nameO;
	String order;

	public String getNameO() {

	    return nameO;
	}

	public void setNameO(String nameO) {

	    this.nameO = nameO;
	}

	public String getOrder() {

	    return order;
	}

	public void setOrder(String order) {

	    this.order = order;
	}
    }

    class GroupByField {

	String nameG;

	public void setName(String name) {

	    this.nameG = name;
	}

	public String getName() {

	    return nameG;
	}
    }

    class WhereField {

	String name;
	String value;
	String compareOperator;
	String joinOperator;

	public String getName() {

	    return name;
	}

	public void setName(String name) {

	    this.name = name;
	}

	public String getValue() {

	    return value;
	}

	public void setValue(String value) {

	    this.value = value;
	}

	public String getCompareOperator() {

	    return compareOperator;
	}

	public void setCompareOperator(String compareOperator) {

	    this.compareOperator = compareOperator;
	}

	public String getJoinOperator() {

	    return joinOperator;
	}

	public void setJoinOperator(String joinOperator) {

	    this.joinOperator = joinOperator;
	}
    }

    class SqlTableDef {

	String tableName;
	String tableAlias;

	public void setTableName(String tableName) {

	    this.tableName = tableName;
	}

	public void setTableAlias(String tableAlias) {

	    this.tableAlias = tableAlias;
	}

	public String getTableName() {

	    return tableName;
	}

	public String getTableAlias() {

	    return tableAlias;
	}
    }

    public void setOrderBy(String orderBy) {

	this.orderBy = orderBy;
    }

    public void setGroupBy(String groupBy) {

	this.groupBy = groupBy;
    }

    public void setWhere(String where) {

	this.where = where;
    }

    public void setFrom(String from) {

	this.from = from;
    }

    public void setSelect(String select) {

	this.select = select;
    }

    public void setSql(String sql) {

	this.sql = sql;
	String retValue;
	String selectQuery;
	String fromQuery = "";
	String whereQuery = "";
	String groupByQuery = "";
	String orderByQuery = "";
	String partQuery[];
	String tmpRightQuery;
	String queryType;
	int spcPos;
	retValue = sql;
	retValue = VerificaQueryHelper.capitalizeKeyWords(retValue);
	spcPos = retValue.indexOf(" ", 0);
	queryType = retValue.substring(0, spcPos + 1).toUpperCase().trim();
	if (!queryType.equals("SELECT"))
	    throw new BusinessValidationException("VerificaQuery: la query passata non contiene SELECT: " + sql);
	retValue = retValue.replace("SELECT ", "");
	log.debug("revalue ==> {}", retValue);
	partQuery = VerificaQueryHelper.split2(retValue, " FROM ", true, -1);
	selectQuery = partQuery[0];
	tmpRightQuery = partQuery[1];
	partQuery = VerificaQueryHelper.split2(tmpRightQuery, " WHERE ", true, -1);
	if (partQuery.length == 1) {
	    partQuery = VerificaQueryHelper.split2(tmpRightQuery, " GROUP BY ", true, -1);
	    if (partQuery.length == 1) {
		partQuery = VerificaQueryHelper.split2(tmpRightQuery, " ORDER BY ", true, -1);
		if (partQuery.length == 1)
		    fromQuery = tmpRightQuery;
		else {
		    fromQuery = partQuery[0];
		    tmpRightQuery = partQuery[1];
		    partQuery = VerificaQueryHelper.split2(tmpRightQuery, "ORDER BY", true, -1);
		    if (partQuery.length == 1)
			groupByQuery = tmpRightQuery;
		    else {
			groupByQuery = partQuery[0];
			orderByQuery = partQuery[1];
		    }
		}
	    }
	} else {
	    fromQuery = partQuery[0];
	    tmpRightQuery = partQuery[1];
	    partQuery = VerificaQueryHelper.split2(tmpRightQuery, " GROUP BY ", true, -1);
	    if (partQuery.length == 1) {
		partQuery = VerificaQueryHelper.split2(tmpRightQuery, " ORDER BY ", true, -1);
		if (partQuery.length == 1)
		    whereQuery = tmpRightQuery;
		else {
		    whereQuery = partQuery[0];
		    orderByQuery = partQuery[1];
		}
	    } else {
		whereQuery = partQuery[0];
		tmpRightQuery = partQuery[1];
		partQuery = VerificaQueryHelper.split2(tmpRightQuery, " ORDER BY ", true, -1);
		if (partQuery.length == 1)
		    groupByQuery = tmpRightQuery;
		else {
		    groupByQuery = partQuery[0];
		    orderByQuery = partQuery[1];
		}
	    }
	}
	this.from = fromQuery;
	this.select = selectQuery;
	this.where = whereQuery;
	this.groupBy = groupByQuery;
	this.orderBy = orderByQuery;
    }

    public String getSql() {

	sql = "SELECT " + getSelect() + " FROM " + getFrom();
	if (!where.isEmpty()) {
	    sql = sql + " WHERE " + getWhere();
	}
	if (!groupBy.isEmpty())
	    sql = sql + " GROUP BY " + getGroupBy();
	if (!orderBy.isEmpty())
	    sql = sql + " ORDER BY " + getOrderBy();
	log.debug("FINAL SQL:\n" + sql);
	return sql;
    }

    public String getOrderBy() {

	List<OrderByField> orderByFields = new ArrayList<SQLSelectHelper.OrderByField>();
	String[] fields = VerificaQueryHelper.split2(this.orderBy, ",", true, -1);
	String[] orderByFieldsDetails;
	OrderByField odf;
	String content;
	String orderByLocale = "";
	for (int i = 0; i < fields.length; i++) {
	    orderByFieldsDetails = VerificaQueryHelper.split2(fields[i], " ", true, 1);
	    odf = new OrderByField();
	    odf.setNameO(orderByFieldsDetails[0]);
	    orderByFields.add(odf);
	    if (orderByFieldsDetails.length > 1)
		orderByFields.get(i).setOrder(orderByFieldsDetails[1]);
	    content = VerificaQueryHelper.replaceFieldContentToMySql(orderByFields.get(i).getNameO());
	    orderByFields.get(i).setNameO(content);
	}
	for (int i = 0; i < orderByFields.size(); i++) {
	    orderByLocale = orderByLocale.concat(" " + orderByFields.get(i).getNameO());
	    if (orderByFields.get(i).getOrder() != null)
		orderByLocale.concat(" " + orderByFields.get(i).getOrder());
	    orderByLocale = orderByLocale.concat(",");
	}
	if (orderByLocale.endsWith(","))
	    orderByLocale = orderByLocale.substring(0, orderByLocale.length() - 1);
	return orderByLocale;
    }

    public String getGroupBy() {

	String fields[] = null;
	String groupByFiledDetails[] = null;
	String groupByLocale = "";
	List<GroupByField> groupByFields = new ArrayList<GroupByField>();
	fields = VerificaQueryHelper.split2(this.groupBy, ",", true, -1);
	for (int i = 0; i < fields.length; i++) {
	    groupByFiledDetails = VerificaQueryHelper.split2(fields[i], ",", true, 1);
	    GroupByField gp = new GroupByField();
	    gp.setName(groupByFiledDetails[0].trim());
	    groupByFields.add(gp);
	    String content = VerificaQueryHelper.replaceFieldContentToMySql(groupByFields.get(i).getName());
	    groupByFields.get(i).setName(content);
	}
	for (int i = 0; i < groupByFields.size(); i++) {
	    groupByLocale = groupByLocale.concat(groupByFields.get(i).getName());
	    groupByLocale = groupByLocale.concat(",");
	}
	if (groupByLocale.endsWith(","))
	    groupByLocale = groupByLocale.substring(0, groupByLocale.lastIndexOf(","));
	return groupByLocale;
    }

    public String getWhere() {

	final String OPBOOL = " AND , OR ";
	final String OPCOMP = "<=,=<,>=,=>,<>,=,<,>, BETWEEN , NOT LIKE , LIKE , NOT IN(, NOT IN , IN(, IN , NOT IS , IS NOT , IS ";
	String fieldsTxt = this.where;
	String[] opBoolVet = OPBOOL.split(",");
	String[] fields;
	String[] opcVet;
	WhereField wfd;
	String[] opCompVet;
	String[] whereFieldDetails = null;
	String content;
	List<WhereField> whereFields = new ArrayList<SQLSelectHelper.WhereField>();
	for (int i = 0; i < opBoolVet.length; i++) {
	    fields = VerificaQueryHelper.split2(fieldsTxt, opBoolVet[i], false, -1);
	    fieldsTxt = org.apache.commons.lang.StringUtils.join(fields, "$opc$" + opBoolVet[i] + "$$,$$");
	}
	fields = fieldsTxt.split("\\$\\$,\\$\\$");
	for (int i = 0; i < fields.length; i++) {
	    opcVet = fields[i].split("\\$opc\\$");
	    wfd = new WhereField();
	    whereFields.add(wfd);
	    if (opcVet.length > 1)
		whereFields.get(i).setJoinOperator(opcVet[1]);
	    fields[i] = opcVet[0];
	    opCompVet = OPCOMP.split(",");
	    for (int j = 0; j < opCompVet.length; j++) {
		whereFieldDetails = VerificaQueryHelper.split2(fields[i], opCompVet[j], false, 1);
		if (whereFieldDetails.length > 1) {
		    whereFields.get(i).setName(whereFieldDetails[0]);
		    whereFields.get(i).setValue(whereFieldDetails[1]);
		    whereFields.get(i).setCompareOperator(opCompVet[j]);
		    content = VerificaQueryHelper.replaceFieldContentToMySql(whereFields.get(i).getValue());
		    whereFields.get(i).setValue(content);
		    break;
		}
	    }
	    if (whereFields.get(i).getName() == null) {
		content = VerificaQueryHelper.replaceFieldContentToMySql(whereFieldDetails[0]);
		whereFields.get(i).setName(whereFieldDetails[0]);
	    }
	}
	String whereLocal = "";
	for (int i = 0; i < whereFields.size(); i++) {
	    whereLocal = whereLocal.concat(" " + whereFields.get(i).getName());
	    whereLocal = whereLocal.concat(" " + whereFields.get(i).getCompareOperator() + " " + whereFields.get(i).getValue());
	    if (whereFields.get(i).getJoinOperator() != null)
		whereLocal = whereLocal.concat(" " + whereFields.get(i).getJoinOperator() + " ");
	    whereLocal = whereLocal.trim();
	}
	return whereLocal;
    }

    public String getFrom() {

	String fromLocal = this.from;
	String[] fromVet = null;
	String[] fromVet2 = null;
	SqlTableDef selectable;
	fromVet = VerificaQueryHelper.split2(this.from, ",", true, -1);
	for (int i = 0; i < fromVet.length; i++) {
	    selectable = new SqlTableDef();
	    fromVet2 = VerificaQueryHelper.split2(fromVet[i], " ", true, -1);
	    if (fromVet2.length > 1) {
		selectable.setTableName(fromVet2[0]);
		selectable.setTableAlias(fromVet2[1]);
	    } else {
		fromVet2 = VerificaQueryHelper.split2(fromVet[i], " AS ", true, -1);
		if (fromVet2.length > 1) {
		    selectable.setTableName(fromVet[0]);
		    selectable.setTableAlias(fromVet2[1]);
		} else {
		    selectable.setTableName(fromVet2[0]);
		    selectable.setTableAlias("");
		}
	    }
	}
	return fromLocal;
    }

    public String getSelect() {

	String content = null;
	String[] fields = null;
	String[] selectFieldDetails = null;
	String selectLocale = "";
	List<SelectField> selectFields = new ArrayList<SQLSelectHelper.SelectField>();
	this.select = VerificaQueryHelper.capitalizeKeyWords(this.select);
	fields = VerificaQueryHelper.split2(this.select, ",", true, -1);
	for (int i = 0; i < fields.length; i++) {
	    selectFieldDetails = VerificaQueryHelper.split2(fields[i], " AS ", true, 1);
	    if (selectFieldDetails.length == 1) {
		selectFieldDetails = VerificaQueryHelper.split2(fields[i], " ", true, 1);
	    }
	    SelectField sf = new SelectField();
	    sf.setNameS(selectFieldDetails[0].trim());
	    selectFields.add(sf);
	    if (selectFieldDetails.length > 1) {
		selectFields.get(i).setAlias(selectFieldDetails[1].trim());
	    }
	    content = VerificaQueryHelper.replaceFieldContentToMySql(selectFields.get(i).getNameS());
	    selectFields.get(i).setNameS(content);
	}
	for (int i = 0; i < selectFields.size(); i++) {
	    selectLocale = selectLocale + selectFields.get(i).getNameS();
	    if (selectFields.get(i).getAlias() != null)
		selectLocale = selectLocale.concat(" AS " + selectFields.get(i).getAlias());
	    selectLocale = selectLocale.concat(",");
	}
	if (selectLocale.endsWith(","))
	    selectLocale = selectLocale.substring(0, selectLocale.lastIndexOf(","));
	return selectLocale;
    }
}
