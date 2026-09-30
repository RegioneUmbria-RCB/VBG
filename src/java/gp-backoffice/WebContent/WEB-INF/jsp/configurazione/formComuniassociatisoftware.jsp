<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>	
			<fmt:message key="label.intestazioni_stampe_comuni" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.intestazioni_stampe_comuni" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	
	<script type='text/javascript'>
		function changeComune(id)
		{
			var a=document.getElementById(id);
			doHref('../configurazione/createComuniassociatisoftware.htm?software=${configurazione.comuniassociatisoftware.configurazione.id.software}&idcomuneassociato='+a.value,'');
		}
	</script>	
	
	<div id="subcontent">
		<spring-form:form commandName="configurazione" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="configurazione" />
		    </jsp:include>
			<table width="100%">
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.informazioni_comune"/></td>
				</tr>	
				<c:if test="${fn:length(configurazione.responsabilicomunis)>1}">
			    <tr>
			        <td><fmt:message key="label.comune"/></td>
			    	<td>
			    	<spring-form:select id="comune_id" path="comuniassociatisoftware.comuni.codicecomune" onchange="changeComune('comune_id')">
			    		<spring-form:options  items="${configurazione.responsabilicomunis}" itemLabel="comune.comune" itemValue="comune.codicecomune"/>
			    	</spring-form:select>
			    	</td>
			    </tr>
			    <tr>
					<td>
						<fmt:message key="label.codice_accreditamento" />
					</td>
					<td>
						<spring-form:input id="codiceaccreditamento_id" path="comuniassociatisoftware.codiceAccreditamento" size="30" />
						<spring-form:errors path="comuniassociatisoftware.codiceAccreditamento" cssClass="error"/>
					</td>
				</tr>
				<c:choose>
					<c:when test="${configurazione.configurazione.id.software eq 'TT'}">
						<tr>
							<td>
								<fmt:message key="label.codiceamministrazione_ipa" />
							</td>
							<td>
								<spring-form:input id="codiceamministrazioneIpa_id" path="comuniassociati.codiceamministrazioneIpa" size="20" />
								<spring-form:errors path="comuniassociati.codiceamministrazioneIpa" cssClass="error"/>
								<init:help idHelp="help_codiceamministrazioneIpa_id" textKey="help.configurazione.codiceamministrazione_ipa"/>
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message key="label.cf_pi_ente" />
							</td>
							<td>
								<spring-form:input id="codicefiscale_id" path="comuniassociati.codicefiscale" size="20" />
								<spring-form:errors path="comuniassociati.codicefiscale" cssClass="error"/>
							</td>
						</tr>
						
						<c:if test="${isPayerAdapter eq true}">
						<tr>
							<td>
								<fmt:message key="label.codice_ente_payer" />
							</td>
							<td>
								<spring-form:input id="codiceEntePayer_id" path="comuniassociati.codiceEntePayer" size="5" maxlength="5" />
								<spring-form:errors path="comuniassociati.codiceEntePayer" cssClass="error"/>
								<init:help idHelp="help_codiceEntePayer_id" textKey="help.configurazione.codice_ente_payer"/>
							</td>
						</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr>
							<td>
								<fmt:message key="label.codice_aoo" />
							</td>
							<td>
								<spring-form:input id="codiceaoo_id" path="comuniassociatisoftware.codiceAoo" size="20" />
								<spring-form:errors path="comuniassociatisoftware.codiceAoo" cssClass="error"/>
								<init:help idHelp="help_codiceaoo_id" textKey="help.configurazione.codiceaoo"/>
							</td>
						</tr>		
					</c:otherwise>
				</c:choose>
			    </c:if>
				</tr>
			    <tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.intestazioni"/></td>
				</tr>
				<tr>
					<td><fmt:message key="label.stemma" /></td>
					<td>
						<jsp:include page="../includes/oggetti.jsp" >
			       			<jsp:param name="idElemento" value="oggettoIdCodice2" />
			   				<jsp:param name="codiceOggetto" value="${configurazione.comuniassociatisoftware.oggetti.id.codice}" />
			   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice2" />
			   				<jsp:param name="nomefileId" value="oggetto_nomefile2" />
		    			    <jsp:param name="overrideExtensionsAllowed" value="gif|jpeg|png"/>
	    				</jsp:include>
    				<spring-form:hidden path="comuniassociatisoftware.oggetti.id.codice" id="oggetto_id_codice2"/>
    				<spring-form:hidden path="comuniassociatisoftware.oggetti.nomefile" id="oggetto_nomefile2"/>
    				<spring-form:errors path="comuniassociatisoftware.oggetti.id.codice" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.intestazione_1" />
					</td>
					<td>
						<spring-form:input id="intestazione_1_id" path="comuniassociatisoftware.siIntestazione1" size="70" />
						<spring-form:errors path="comuniassociatisoftware.siIntestazione1" cssClass="error"/>
					</td>
				</tr>
				<tr >
					<td>
						<fmt:message key="label.intestazione_2" />
					</td>
					<td>
						<spring-form:input id="intestazione_ì2_id" path="comuniassociatisoftware.siIntestazione2" size="70" />
						<spring-form:errors path="comuniassociatisoftware.siIntestazione2" cssClass="error"/>
					</td>
				</tr>
				<tr >
					<td>
						<fmt:message key="label.intestazione_3" />
					</td>
					<td>
						<spring-form:input id="intestazione_3_id" path="comuniassociatisoftware.siIntestazione3" size="70" />
						<spring-form:errors path="comuniassociatisoftware.siIntestazione3" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.pie_pagina_1" />
					</td>
					<td>
						<spring-form:input id="pie_pagina_1_id" path="comuniassociatisoftware.siPdp1" size="70" />
						<spring-form:errors path="comuniassociatisoftware.siPdp1" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.pie_pagina_2" />
					</td>
					<td>
						<spring-form:input id="pie_pagina_2_id" path="comuniassociatisoftware.siPdp2" size="70" />
						<spring-form:errors path="comuniassociatisoftware.siPdp2" cssClass="error"/>
					</td>
				</tr>
				<c:if test="${fn:length(configurazione.responsabilicomunis)>1}">
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.email"/></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.email_entrata" />
					</td>
					<td>
						<spring-form:input id="mail_id" path="comuniassociatisoftware.mail" size="70" />
						<spring-form:errors path="comuniassociatisoftware.mail" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.email_pec" />
					</td>
					<td>
						<spring-form:input id="mailpec_id" path="comuniassociatisoftware.mailpec" size="70" />
						<spring-form:errors path="comuniassociatisoftware.mailpec" cssClass="error"/>
					</td>
				</tr>
				</c:if>
			</table>
			<script type='text/javascript'>
				$('comune_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertOrUpdateComuniassociatisoftware.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doHref('create.htm?software=${configurazione.configurazione.id.software}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>