<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html>
	<head>
		<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
		<title><fmt:message key="label.movimenti_istanza" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.movimenti_istanza" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../movimentiallegati/list" />	    	
		</jsp:include>				
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${movimentiCommand.entity.istanza.id.codice}</c:param>
		</c:import>
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.movimento" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					${movimentiCommand.entity.movimento} - [${movimentiCommand.entity.tipomovimento.id.tipomovimento}]							
				</div>
			</div>
		</div>
		<br class="clear" />	
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
		       <jsp:param name="commandName" value="movimentiCommand" />
		</jsp:include>		
		
		<div id="subcontent">							
			<div id="status_msg_warning" class="error_header"><b>
				<fmt:message key="service_errors.movimenti.esegui_elaborazione" /></b>
				<br />(${causa})
			</div>
			<br class="clear" />
		</div>
		
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('../movimenti/elabora.htm?codiceIstanza=${movimentiCommand.entity.istanza.id.codice}','');"><fmt:message key="button.elabora" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>