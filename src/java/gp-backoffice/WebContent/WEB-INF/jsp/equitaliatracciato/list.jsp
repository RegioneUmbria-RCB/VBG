<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.lista_tracciati_450.title" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.lista_tracciati_450.title" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../equitaliatracciato/list" />
</jsp:include>
<div id="subcontent">
<form name="equitaliatracciatoForm" action="list.htm"><jmesa:springTableFacade
	id="equitaliatracciato_id" items="${equitaliatracciatoList}" var="equitaliatracciato_var"
	exportTypes="pdfp,excel,csv" stateAttr="restore">
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                 <a href="javascript:historySet('${_urlback}','../equitaliatracciato/view.htm?codice=${equitaliatracciato_var.id.codice}','')">${equitaliatracciato_var.id.codice}</a>
            </jmesa:htmlColumn>
			<jmesa:htmlColumn property="dataCreazione"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" cellEditor="org.jmesa.view.editor.DateCellEditor"	titleKey="label.data_creazione" filterable="false" sortable="false"/>
			<jmesa:htmlColumn property="anno" titleKey="label.anno" />
			<jmesa:htmlColumn property="progressivoAnno" titleKey="label.progressivo_anno" />
			<jmesa:htmlColumn property="responsabili.responsabile" titleKey="label.creato_da" />
			<jmesa:htmlColumn property="" titleKey="label.visualizza" sortable="false" filterable="false" width="5%">
				<a class="visualizzaDocColumn" href="../file/ajaxDownload.htm?fileId=${equitaliatracciato_var.oggetti.id.codice}"  title="<fmt:message key="label.visualizza" /> ">
					<label><fmt:message key="label.visualizza.image" /></label>
				</a>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade></form>

			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="label.lista_tracciati_450.title" />';
			</script></div>
<div id="functions">
<ul>
	<li><a href="javascript:historySet('${_urlback}','../equitaliatracciato/searchIstanze.htm','')"><fmt:message key="button.new"/></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>