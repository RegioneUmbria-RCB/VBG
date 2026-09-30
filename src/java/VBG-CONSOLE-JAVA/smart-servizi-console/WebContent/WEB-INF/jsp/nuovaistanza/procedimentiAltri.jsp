<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione" ><c:out value="${CURRENT_STEP.descrizione }" escapeXml="false"></c:out></div>
	<%@ include file="../includes/alert.jsp" %>
	<spring-form:form action="saveAltri.htm" method="post" commandName="nuovaIstanzaCommand" name="formProcedimentiAltri">	
		<div class="titolo_sezione toggle"><fmt:message key='label.endo-altri' /></div>
		<div id="sez_procedimenti_altri" class="sezione lista-endo">
			<c:choose>
			<c:when test="${helper != null}">
			<ul>
				<c:forEach items="${helper.procedimentiAltri }" var="procAltriFamiglia" varStatus="idxAltriFam">
					<li>${procAltriFamiglia.key.descrizione }<c:if test="${empty procAltriFamiglia.key.descrizione }"><fmt:message key='label.famiglia-endo-non-specificata' /></c:if>
					<ul>
					<c:forEach items="${procAltriFamiglia.value }" var="procAltriCategoria" varStatus="idxAltriCat">
						<li>${procAltriCategoria.key.descrizione }<c:if test="${empty procAltriCategoria.key.descrizione }"><fmt:message key='label.famiglia-endo-non-specificata' /></c:if>
						<ul>
						<c:forEach items="${procAltriCategoria.value }" var="procAltri" varStatus="idxAltri">
							<li>
								<c:choose>
								<c:when test="${procAltri.value.selezionato eq true }">
								<input type="checkbox" name="codice" value="${procAltri.value.procedimento.codice}" checked="checked" />
								</c:when>
								<c:otherwise>
								<input type="checkbox" name="codice" value="${procAltri.value.procedimento.codice}" />
								</c:otherwise>
								</c:choose>
								${procAltri.key.descrizione }
								</li>
						</c:forEach>
						</ul>
						</li>
					</c:forEach>
					</ul>
					</li>
				</c:forEach>
				</ul>
			</c:when>
			<c:otherwise><fmt:message key='label.nessun-endo' /></c:otherwise>
			</c:choose>				
		</div>
		<div class="sezione_table_buttons">
			<input type="button" value="<fmt:message key='button.annulla' />" onclick="indietro()" />
			<input type="button" value="<fmt:message key='button.salva' />" onclick="salva()" />
		</div>		
	</spring-form:form>
	<script type="text/javascript">
	function indietro(){
		window.location.replace("view.htm");
	}
	function salva(){
		document.formProcedimentiAltri.submit();
	}
	</script>
</body>
</html>