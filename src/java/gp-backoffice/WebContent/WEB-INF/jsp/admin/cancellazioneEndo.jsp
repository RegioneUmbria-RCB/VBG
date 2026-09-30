<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.pannello_di_amministrazione" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.pannello_di_amministrazione" />	
	</span>
<!-- §§§BEGIN§§§ -->

	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../admin/view" />
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="administration" />
	</jsp:include>
	<div id="subcontent">
		<div style="margin-bottom: 10px;">
		<b>Cancellazione Massiva Endoprocedimenti</b>
		</div>
		<table border="1" cellpadding="5px">
			<thead><tr><th colspan="3">Lista Endo per software</th></tr></thead>
			<tbody>
			<c:forEach items="${mapEndoCount }" var="endoCount">
				<tr><td>${endoCount.key.descrizione }</td><td>${endoCount.value}</td><td><a href="javascript:cancella('${endoCount.key.codice}');">Cancella</a></td></tr>
			</c:forEach>
			</tbody>
		</table>
		<c:if test="${not empty mapEndoNonCancellati}">
		<table border="1">
			<thead><tr><th colspan="3">Lista Endo non cancellati</th></tr></thead>
			<tbody>
			<c:forEach items="${mapEndoNonCancellati }" var="endoNoDeleted">
				<tr><td>${endoNoDeleted.key.id.codice }</td><td>${endoNoDeleted.key.procedimento }</td><td>${endoNoDeleted.value}</td></tr>
			</c:forEach>
			</tbody>
		</table>
		</c:if>
	</div>
	<script type="text/javascript">
		function cancella(codiceSoftware){
			doHref('../admin/cancellazioneEndo.htm?codiceSoftware='+codiceSoftware,'Attenzione! Stai per cancellare tutti gli endo del software '+codiceSoftware+'. Continuare?');	
		}
	</script>

<!-- §§§END§§§ -->
</body>
</html>