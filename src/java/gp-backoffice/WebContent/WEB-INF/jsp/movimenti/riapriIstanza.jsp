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
	<c:param name="codIstanza">${param.codiceIstanza}</c:param>
</c:import>	
<br class="clear" />
<div id="subcontent">
	<div style="width: 650px;min-height: 50px; border: thin dotted; padding: 5px;">
		<b><fmt:message key="label.help_messaggio_riapertura_istanza_dopo_cancellazione_chiusura" /></b>
	</div>
	<form name="inviodati" action="" method="post">			
	<input type="hidden" name="codiceIstanza" value="${param.codiceIstanza}"/>
	<input type="hidden" name="refMovId" value="${param.refMovId}"/>
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.stato_istanza" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					<select id="statoistanza_id" name="codiceStato">
						<c:forEach items="${statiistanzaList}" var="s">
							<option value="${s.id.codicestato}" >${s.stato}</option>
						</c:forEach>
					</select>											
				</div>
			</div>
		</div>
		<br class="clear"/>								
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	        <jsp:param name="commandName" value="movimentiCommand" />
	    </jsp:include>		
	</form>
</div>
<div id="functions">
<ul>	
	<li><a href="javascript:doSubmit('riapriIstanzaUpdate.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
</ul>
</div>
</body>
</html>