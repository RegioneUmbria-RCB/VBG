<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_comunicazionitmercato.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_comunicazionitmercato.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../comunicazionitmercato/list" />
	</jsp:include>
	<div id="subcontent">
		<form name="comunicazionitmercatoForm" action="list.htm">
			<jmesa:springTableFacade
				id="comunicazionitmercato_id" 
				items="${comunicazionitmercatoList}" 
				var="comunicazionitmercato_var"
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%" sortable="false" filterable="false">
                           	<a href="javascript:historySet('${_urlback}','../comunicazionitmercato/view.htm?codice=${comunicazionitmercato_var.id.codice}','');">${comunicazionitmercato_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="comunicazioniT.descrizione" titleKey="label.descrizione" />
						
						<jmesa:htmlColumn property="comunicazioniT.comunicazioniT.statoElaborazione" titleKey="label.stato" sortable="false" filterable="false" >
						    <c:if test="${comunicazionitmercato_var.comunicazioniT.statoElaborazione == 0}">
						    		-
						    </c:if>
						    <c:if test="${comunicazionitmercato_var.comunicazioniT.statoElaborazione == 1}">
						    	<img src="../images/warning.gif" title="Da elaborare"/>
						    </c:if>
						    <c:if test="${comunicazionitmercato_var.comunicazioniT.statoElaborazione == 2}">
						    	<img src="../images/accept.png" title="Elaborazione terminata correttamente"/>
						    </c:if>
						    <c:if test="${comunicazionitmercato_var.comunicazioniT.statoElaborazione == -1}">
						    	<img src="../images/error.png" title="Elaborazione terminata con errori"/>
						    </c:if>
							
						</jmesa:htmlColumn>
						
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../comunicazionitmercato/view.htm?codice=${comunicazionitmercato_var.id.codice}','');" title="<fmt:message key="label.edit.record" />${comunicazionitmercato_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${codmercato}" name="codicemercato" />
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codicemercato=${codmercato}&';
			var _captionTab='<fmt:message key="comunicazionitmercato.label.lista_comunicazionitmercato.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<%-- <li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li> --%>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>