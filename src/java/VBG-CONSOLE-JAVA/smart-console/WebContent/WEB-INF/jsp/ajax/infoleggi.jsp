<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<spring-form:form commandName="leggi" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="leggi" />
	</jsp:include>
	<div id="subcontent">
	<table>
	
		<tr>
			<td class="parametro"><fmt:message key="label.descrizione" />:</td>
			<td>${leggi.leDescrizione}</td>
		</tr>
		<c:if test="${leggi.normative.normativa!=null}">
		<tr>
			<td class="parametro"><fmt:message key="label.normativa" />:</td>
			<td>${leggi.normative.normativa}</td>
		</tr>
		</c:if>
		<c:if test="${leggi.leggitipi.ltDescrizione!=null}">
		<tr>
			<td class="parametro"><fmt:message key="leggi.label.tipolegge" />:</td>
			<td>${leggi.leggitipi.ltDescrizione}</td>
		</tr>
		</c:if>
		
		<c:if test="${leggi.leLink!=null}">
		<tr>
		<td class="parametro"><fmt:message key="leggi.label.indirizzoweb" />:</td>
			<td>
				${leggi.leLink}
			</td>
		</tr>
		</c:if>
		<c:if test="${leggi.oggetto.id.codice!=null}">
		<tr>
		<td class="parametro"><fmt:message key="label.allegato" />:</td>
			<td>
				<jsp:include page="../includes/visualizzaOggetto.jsp" >
  					<jsp:param name="idElemento" value="allegato${leggi.oggetto.id.codice}" />
  					<jsp:param name="fileId" value="${leggi.oggetto.id.codice}" />
					<jsp:param name="mostralabel" value="true" />					
	   			</jsp:include>
			</td>
		</tr>
		</c:if>
	</table>
	</div>
</spring-form:form>