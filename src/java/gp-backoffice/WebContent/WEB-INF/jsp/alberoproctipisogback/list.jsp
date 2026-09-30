<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="form.title.alberoproctipisogback.list" /></title>
</head>
<body>
<span class="titoloPagina"> 
	<fmt:message key="form.title.alberoproctipisogback.list" />
</span>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../alberoproc/view" />
	</jsp:include>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>

<div id="subcontent">

<div class="parametriDiv">
  	<div class="etichetta">
		<div><fmt:message key="label.procedimento" />:</div>
	</div>
	<div class="parametro">
		<div><c:out value="${alberoproc.scDescrizione}" /></div>
 	</div>
</div>

<form name="alberoproctipisogbackForm" action="list.htm"><jmesa:springTableFacade
	id="alberoproctipisogback_id" items="${alberoproctipisogbackList}" var="alberoproctipisogback_var"
	exportTypes="pdfp,excel,csv" stateAttr="restore" >
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="tipisoggetto.tiposoggetto"	titleKey="label.tipo_soggetto" />
			<jmesa:htmlColumn property="" titleKey="label.azioni"		sortable="false" filterable="false" width="5%">
				 <a class="eliminaRiga" href="javascript:doHref('deleteRecord.htm?codice=${alberoproctipisogback_var.id.codice}','<fmt:message key="javascript.confirm.delete" />')"  title="<fmt:message key="label.elimina" />${alberoproctipisogback_var.tipisoggetto.tiposoggetto}">
						<label><fmt:message key="label.azioni" /></label>
				 </a>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade></form>

<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="form.title.alberoproctipisogback.list" />';
			</script></div>
<div id="functions">
<ul>

	<%-- <li><a href="javascript:historySet('${_urlback}','../alberoproctipisogback/create.htm?codiceAlberoproc=${alberoproc.id.codice}','')"><fmt:message key="button.new"/></a></li> --%>
	<li><a href="javascript:doHref('../alberoproctipisogback/create.htm?codiceAlberoproc=${alberoproc.id.codice}','')"><fmt:message key="button.new"/></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>