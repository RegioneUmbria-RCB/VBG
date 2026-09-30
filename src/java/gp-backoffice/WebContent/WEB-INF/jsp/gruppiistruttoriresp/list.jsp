<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.gruppiistruttoriresp.list.title" /></title>
    
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.gruppiistruttoriresp.list.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../gruppiistruttoriresp/list"/>
			<jsp:param name="qs" value="codice%3D${codicegruppo}"/>
	    </jsp:include>
<div id="subcontent">
<div><b><fmt:message key="label.gruppiistruttoriresp.help.title" /></b></div>
<form name="gruppiistruttorirespForm" action="list.htm"><jmesa:springTableFacade
	id="gruppiistruttori_id" items="${gruppiistruttorirespList}" var="gruppiistruttoriresp_var"
	 stateAttr="restore"> <%-- exportTypes="pdfp,excel,csv" --%>
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
        			<a href ="javascript:historySet('${_urlback}','../gruppiistruttoriresp/view.htm?codice=${gruppiistruttoriresp_var.id.codice}');" >${gruppiistruttoriresp_var.id.codice}</a >		
            </jmesa:htmlColumn>
			<jmesa:htmlColumn width="30%" property="responsabili.responsabile"  titleKey="label.istruttore" >
			<c:if test="${gruppiistruttoriresp_var.isAttivoPerSoftwareCorrente}">
			     <span>${gruppiistruttoriresp_var.responsabili.responsabile}</span>
			</c:if>
			<c:if test="${!gruppiistruttoriresp_var.isAttivoPerSoftwareCorrente}">
				<span style="color: red">${gruppiistruttoriresp_var.responsabili.responsabile} (Responsabile non più configurato per il software corrente)</span>
			</c:if>
			
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="responsabili.isAssenteOra"  titleKey="label.assente" >
				<c:if test="${gruppiistruttoriresp_var.responsabili.isAssenteOra}">
						<fmt:message key="label.si" />
				</c:if>
				<c:if test="${!gruppiistruttoriresp_var.responsabili.isAssenteOra}">
					<fmt:message key="label.no" />
				</c:if>
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="" titleKey="label.edit.record"	sortable="false" filterable="false" width="5%">
				<a class="dettaglioColumn" href ="javascript:historySet('${_urlback}','../gruppiistruttoriresp/view.htm?codice=${gruppiistruttoriresp_var.id.codice}');"title="<fmt:message key="label.edit.record" />&nbsp;${gruppiistruttoriresp_var.id.codice}">
					<label><fmt:message key="label.edit.record.image" /></label>
				</a>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade></form>

<script type="text/javascript">
				var _jmesaUrl='list.htm?codice=${codicegruppo}&';
				var _captionTab='<fmt:message key="label.gruppiistruttoriresp.list.title" />';
			</script></div>
<div id="functions">
<ul>
	<li><a href ="javascript:historySet('${_urlback}','../gruppiistruttoriresp/create.htm?codice=${codicegruppo}');"><fmt:message key="button.new" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>