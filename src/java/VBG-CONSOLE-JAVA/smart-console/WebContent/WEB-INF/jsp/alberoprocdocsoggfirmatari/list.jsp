<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.alberoprocdocsoggfirmatari.list.title" /></title>
    <jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../alberoproc/viewDocumenti" />
	</jsp:include>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.alberoprocdocsoggfirmatari.list.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
<div id="subcontent">
<form name="alberoprocdocsoggfirmatariForm" action="list.htm"><jmesa:springTableFacade
	id="alberoprocdocsoggfirmatari_id" items="${alberoprocdocsoggfirmatariList}" var="alberoprocdocsoggfirmatari_var"
	exportTypes="pdfp,excel,csv" stateAttr="restore">
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="tipisoggetto.tiposoggetto"	titleKey="label.tipo_soggetto" />
			<jmesa:htmlColumn property="" titleKey="label.azioni"		sortable="false" filterable="false" width="5%">
				<a class="eliminaRiga" href="deleteByCodice.htm?codice=${alberoprocdocsoggfirmatari_var.id.codice}"	title="<fmt:message key="label.elimina" />&nbsp;${alberoprocdocsoggfirmatari_var.tipisoggetto.tiposoggetto}">
					<label><fmt:message key="label.elimina.image" /></label>
				</a>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade></form>

<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="label.alberoprocdocsoggfirmatari.list.title" />';
			</script></div>
<div id="functions">
<ul>
	<li><a href="javascript:doHref('create.htm?codicedocumento=${alberoprocDocumenti.id.codice}','');"><fmt:message key="button.new" /></a></li>
	<li><a href="javascript:doHref('../alberoproc/viewDocumenti.htm?alberoprocDocumenti.id.codice=${alberoprocDocumenti.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>