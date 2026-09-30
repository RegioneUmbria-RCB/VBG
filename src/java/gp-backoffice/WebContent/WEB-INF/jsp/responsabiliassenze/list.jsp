<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.responsabiliassenze.list.title" /></title>
    
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.responsabiliassenze.list.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../responsabiliassenze/list" />
	    	<jsp:param name="qs" value="codice%3D${codiceResponsabile}"/>
	    </jsp:include>
		
<div id="subcontent">
<div><b><fmt:message key="help.resposabiliassenze.descrizione"/></b></div>
<form name="responsabiliassenzeForm" action="list.htm"><jmesa:springTableFacade
	id="responsabiliassenze_id" items="${responsabiliassenzeList}" var="responsabiliassenze_var"
	exportTypes="pdfp,excel,csv" stateAttr="restore">
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
        			<a href ="javascript:historySet('${_urlback}','../responsabiliassenze/view.htm?codice=${responsabiliassenze_var.id.codice}');" >${responsabiliassenze_var.id.codice}</a >		
            </jmesa:htmlColumn>
			<jmesa:htmlColumn property="dal" titleKey="label.data_inizio_assenza" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterable="false" sortable="false" width="8%"/>
			<jmesa:htmlColumn property="al" titleKey="label.data_fine_assenza" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterable="false" sortable="false" width="8%"/>
			<jmesa:htmlColumn property="note" titleKey="label.note"/>
			<jmesa:htmlColumn property="" titleKey="label.edit.record"	sortable="false" filterable="false" width="5%">
				<a class="dettaglioColumn" href ="javascript:historySet('${_urlback}','../responsabiliassenze/view.htm?codice=${responsabiliassenze_var.id.codice}');"title="<fmt:message key="label.edit.record" />&nbsp;${responsabiliassenze_var.id.codice}">
					<label><fmt:message key="label.edit.record.image" /></label>
				</a>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade></form>

<script type="text/javascript">
				var _jmesaUrl='list.htm?codice=${codiceResponsabile}&';
				var _captionTab='<fmt:message key="label.responsabiliassenze.list.title" />';
			</script></div>
<div id="functions">
<ul>
	<li><a href ="javascript:historySet('${_urlback}','../responsabiliassenze/create.htm?codice=${codiceResponsabile}');"><fmt:message key="button.new" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>