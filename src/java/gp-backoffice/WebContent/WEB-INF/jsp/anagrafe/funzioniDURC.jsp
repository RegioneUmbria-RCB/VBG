<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI --%>
<c:set var="uniquePageIdentifier" value="${param.uniquePageIdentifier }"/>
<c:set var="codiceAnagrafe" value="${param.codiceAnagrafe }"/>
<c:set var="codiceIstanza" value="${param.codiceIstanza }"/>
<c:set var="showAsButton" value="true"/>
<c:if test="${param.showAsButton eq 'false'}">
	<c:set var="showAsButton" value="false"/>
</c:if>
<c:set var="function" value="verifica"/>
<c:if test="${not empty param.function}">
	<c:set var="function" value="${param.function}"/>
</c:if>
<c:set var="returnToUrl" value=""/>
<c:if test="${not empty param.returnToUrl}">
	<c:set var="returnToUrl" value="${param.returnToUrl}"/>
</c:if>
<%-- 

			ES: 
				<jsp:include page="../anagrafe/funzioniDURC.jsp">
				   <jsp:param name="uniquePageIdentifier" value="${anagrafe.entity.id.codice}" />
				   <jsp:param name="codiceAnagrafe" value="${anagrafe.entity.id.codice}" />
				   <jsp:param name="codiceIstanza" value="" />
				   <jsp:param name="showAsButton" value="true" />
				   <jsp:param value="function" name="verifica"/>
				   <jsp:param value="returnToUrl" name="${_urlback}"/>
				</jsp:include>		

		parametri obbligatori:
			uniquePageIdentifier:	identificativo Univoco all'interno della pagina che sarà renderizzata, 
								  	garantisce l'univocità delle funzioni javascript e degli identificativi 
			codiceAnagrafe:			codice anagrafica
			showAsButton:			'true' o 'false', se true allora sarà renderizzato come link altrimenti come icona
			function:				Valori ammessi: 'verifica', 'nuovoDURC' 
			
		parametri opzionali	
			codiceIstanza:			se la chiamata alla funzionalità parte dall'istanza allora viene settato anche il codice istanza (che lega l'istanza a ANAGRAFEDOCUMENTI)
			returnToUrl:			la pagina dove ritornare sia in caso di successo che di errore
 --%>
<%-- END RECUPERO PARAMETRI--%>
<%-- BEGIN SEZIONE FUNZIONI --%>
<c:set var="link_js"></c:set>
<%--  --%>
<c:set var="link_js_set_or_href">doHref(</c:set>
<c:if test="${not empty returnToUrl}">
	<c:set var="link_js_set_or_href">historySet('${returnToUrl}',</c:set>
</c:if>
<%-- --%>
<c:if test="${function eq 'verifica'}">
	<c:set var="link_js">'../anagrafe/verificaDurc.htm?codiceAnagrafe=${codiceAnagrafe}&codiceIstanza=${codiceIstanza}'</c:set>
	<c:set var="label_link"><fmt:message key="label.verifica_durc" /></c:set>

</c:if>				
<c:if test="${function eq 'nuovoDURC'}">
	<c:set var="link_js">'../anagrafe/richiediDurc.htm?codiceAnagrafe=${codiceAnagrafe}&codiceIstanza=${codiceIstanza}'</c:set>
	<c:set var="label_link"><fmt:message key="label.richiedi_durc" /></c:set>
		
</c:if>

	<a href="javascript:void(0);" onclick="${link_js_set_or_href}${link_js}, '');" >${label_link}</a>

<%-- END SEZIONE FUNZIONI --%>
