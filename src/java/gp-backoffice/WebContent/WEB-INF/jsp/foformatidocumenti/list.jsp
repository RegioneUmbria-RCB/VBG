<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.foformatidocumenti.title" /></title>
</head>
<body>
		<span class="titoloPagina"><fmt:message key="label.foformatidocumenti.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../foformatidocumenti/list" />
		</jsp:include>
		<div id="subcontent">
		
		<form name="foformatidocumentiForm" action="list.htm">
		<jmesa:springTableFacade id="foformatidocumenti_id" items="${foformatidocumentiList}" var="foformatidocumenti_var" stateAttr="restore">
			<jmesa:htmlTable>
				<jmesa:htmlRow>
					<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%" sortable="false" filterable="false">
		                 <a href="javascript:historySet('${_urlback}','../foformatidocumenti/view.htm?codice=${foformatidocumenti_var.id.codice}','');">${foformatidocumenti_var.formato}</a>
		            </jmesa:htmlColumn>
					<jmesa:htmlColumn property="formato" titleKey="label.formato" sortable="false" filterable="false"/>
					<jmesa:htmlColumn property="dimensioneMaxPagina" titleKey="label.dimensione_massima_pagina" sortable="false" filterable="false">
						${foformatidocumenti_var.dimensioneMaxPagina} Kb
					</jmesa:htmlColumn>
					<jmesa:htmlColumn property="" titleKey="label.edit.record"sortable="false" filterable="false" width="5%">
						<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../foformatidocumenti/view.htm?codice=${foformatidocumenti_var.id.codice}','');"	title="<fmt:message key="label.edit.record" />&nbsp;${foformatidocumenti_var.formato}">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>
					</jmesa:htmlColumn>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableFacade></form>

		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="label.foformatidocumenti.title" />';
		</script></div>
		<div id="functions">
		<ul>
			<li><a href="javascript:historySet('${_urlback}','../foformatidocumenti/create.htm','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
		</div>
		</body>
</html>