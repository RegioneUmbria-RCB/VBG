<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.archiviazioni.lista_istanze_escluse" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.archiviazioni.lista_istanze_escluse" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
<div id="subcontent">
<form name="escluseForm" action="listEscluse.htm?archId=${archId}">
	<jmesa:springTableFacade id="escluse_id" items="${list}" var="escluse_var" stateAttr="restore">
		<jmesa:htmlTable>
			<jmesa:htmlRow>
				<jmesa:htmlColumn property="istanze.numeroistanza" titleKey="label.codice" width="2%">			
					<a href="javascript:historySet('../archiviazioni/listEscluse.htm?archId=${escluse_var.archiviazioni.id.codice}', '../istanze/view.htm?codice=${escluse_var.istanze.id.codice}', '')" />
						${escluse_var.istanze.numeroistanza}
					</a>					
				</jmesa:htmlColumn>
				<jmesa:htmlColumn property="istanze.lavori" titleKey="label.lavori" width="40%"/>		
				<jmesa:htmlColumn property="errore"	titleKey="label.errore">
					<c:out value="${escluse_var.errore}" escapeXml="false" />
				</jmesa:htmlColumn>
				<jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="5%">
					<a class="eliminaRiga" href="deleteEsclusa.htm?archId=${escluse_var.archiviazioni.id.codice}&archIstId=${escluse_var.id.codice}" title="<fmt:message key="label.elimina" />&nbsp;${escluse_var.id.codice}">
						<label><fmt:message key="label.elimina" /></label>
					</a>
				</jmesa:htmlColumn>
			</jmesa:htmlRow>
		</jmesa:htmlTable>
	</jmesa:springTableFacade>
	<input type="hidden" name="archId" value="${archId}"/>
</form>
<script type="text/javascript">
	var _jmesaUrl='listEscluse.htm?archId=${archId}&';
	var _captionTab='<fmt:message key="label.archiviazioni.lista_istanze_escluse" />';
</script>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>