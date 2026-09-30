<%@page import="it.gruppoinit.pal.gp.core.domain.web.MovimentiCommand"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>	
	<fmt:message key="label.movimenti_istanza" />
</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.movimenti_istanza" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<c:import url="/ajax/dettaglioIstanza.htm">
	<c:param name="codIstanza">${movimentiCommand.entity.istanza.id.codice}</c:param>
</c:import>	
<br class="clear" />
<div id="subcontent">
	<spring-form:form commandName="movimentiCommand" name="inviodati">
	<c:if test="${movimentiCommand.displayMode eq movimentiCommand.displayConstants.VIEW}">		
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.stato_attuale_dell_istanza" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					<spring-form:select id="statoistanza_id" path="statiistanza.id.codicestato">
						<spring-form:options items="${statiistanzaList}" itemValue="id.codicestato" itemLabel="stato"/>
					</spring-form:select>											
				</div>
			</div>
		</div>
		<br class="clear"/>								
	</c:if>	
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="movimentiCommand" />
    </jsp:include>		
</spring-form:form>
</div>
<div id="functions">
<ul>	
	<li><a href="javascript:doSubmit('chiudiIstanzaUpdate.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>