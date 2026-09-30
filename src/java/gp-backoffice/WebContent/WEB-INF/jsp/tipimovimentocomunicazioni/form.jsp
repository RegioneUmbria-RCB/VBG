<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipimovimentocomunicazioni.entity.id.codice==null}">
			<fmt:message key="label.nuovo_tipimovimentocomunicazioni.title" />
		</c:if> 
		<c:if test="${tipimovimentocomunicazioni.entity.id.codice!=null}">
			<fmt:message key="label.dettaglio_tipimovimentocomunicazioni.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${tipimovimentocomunicazioni.entity.id.codice==null}">
			<fmt:message key="label.nuovo_tipimovimentocomunicazioni.title" />
		</c:if> 
	 <c:if test="${tipimovimentocomunicazioni.entity.id.codice!=null}">
			<fmt:message key="label.dettaglio_tipimovimentocomunicazioni.title" />
		</c:if>
	</span>

	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="tipimovimentocomunicazioni" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="tipimovimentocomunicazioni" />
			</jsp:include>

			<table>
				<tr>
					<td><fmt:message key="label.mailtipo" /></td>
					<td><jsp:include page="../includes/autocompletergenericoTT.jsp">
						<jsp:param name="idElemento" value="mailtipo" />
						<jsp:param name="propertyPath" value="entity.mailtipo" />
						<jsp:param name="pathPropertyDescription" value="entity.mailtipo.descrizione" />
						<jsp:param name="pathPropertyCode" value="entity.mailtipo.id.codice" />
						<jsp:param name="autocompleterAjax" value="findMailtipo.htm?amibito=M&software=" />
						<jsp:param name="titleKey" value="label.ricerca_mailtipo" />
						<jsp:param name="id_help" value="help_mailtipo" />
					</jsp:include>
					<spring-form:errors path="entity.mailtipo.descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td class="">
						<fmt:message key="tipimovimentocomunicazioni.label.accountmail" />
					</td>
					<td>
						<spring-form:input id="accountmail_descrizione" path="entity.mailConfig.descrizioneLunga2" size="100" readonly="true"/>
						<spring-form:errors path="entity.mailConfig" cssClass="error"/>
						<img src="${pageContext.request.contextPath}/images/book_open.png" style="cursor: pointer;" title=" <fmt:message key="movimentimail.label.rubrica_help" />" alt="" onclick="javascript:openSearch('ricerca_accountmail');" />
						<br />
						<span id="ricerca_accountmail" style="display: none;">
						    <fmt:message key="movimentimail.label.seleziona_account_email" />
						    <br />
						    <spring-form:select id="select_accountmail_id" path="entity.mailConfig.id.codice">
								<%-- <spring-form:options items="${listMailConfig}" itemLabel="descrizioneLunga" itemValue="id.codice" />--%>
								<c:forEach items="${listMailConfig}" var="current" >
									<spring-form:option value="${current.id.codice}" onclick="javascript:setMittente(this)" >${current.descrizioneLunga}</spring-form:option>
								</c:forEach>							
							</spring-form:select>
						</span>
					</td>
				
				</tr>
				<tr>
					<td><fmt:message key="label.funzione" /></td>
					<td>
						<spring-form:select id="select_funzione_id"  path="entity.funzione" onchange="gestiscicampi()">
							<spring-form:option value="<%=WebConstants.COMUNICAZIONI_TIPO_MOV_DOPO_FIRMA_DOC %>">Invio email dopo firma documento</spring-form:option>
							<spring-form:option value="<%=WebConstants.COMUNICAZIONI_TIPO_MOV_DOPO_INSERIMENTO_MOV %>">Invio email dopo inserimento movimento</spring-form:option>
						<%-- <spring-form:options items="${concessionicausalis}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options> --%>
						</spring-form:select>
					</td>
				</tr>
				
				<tr id="destinatari_id">
					<td>
						<fmt:message key="label.destinatari" />
					</td>
					<td> 
					<select multiple="multiple" name="entity.destinatariMail" size="7">
					  <c:forEach items="${destinatariHelpers}" var="destinatariHelper">
					  
					    <c:if test="${destinatariHelper.selezionato}">
					    	<option value="${destinatariHelper.codice}"  selected="selected" >${destinatariHelper.descrizione}</option>
					    </c:if>
					 	<c:if test="${!destinatariHelper.selezionato}">
					 		<option value="${destinatariHelper.codice}" >${destinatariHelper.descrizione}</option>
					 	</c:if>
					  </c:forEach>
					</select>
					<init:help idHelp="help_destinatariMail" textKey="tipimovimentocomunicazioni.help.destinatari_mail"/>
					<fmt:message key="label.select_multiplo" />
					</td>
				</tr>
				
				
				<tr id="fase_inserimento_id">
					<td><fmt:message key="label.invia_fase_inserimento" /></td>
					<td>
						<spring-form:checkbox path="entity.flagInserimentoMov" />
						<init:help idHelp="help_invia_fase_inserimento" textKey="tipimovimentocomunicazioni.help.invia_fase_inserimento"/>
					</td>
				</tr>
				
			</table>
			
			<table>
				<tr id="note_id">
					<td><fmt:message key="help.destinatari" /></td>
				</tr>
			</table>
		</spring-form:form>
	</div>
	
	<script type='text/javascript'>
	
			jQuery(document).ready(function(){
				   gestiscicampi();
				}
			);
	      
			
			function gestiscicampi()
			{
				var test=jQuery('#select_funzione_id').val();
				if(test!=null){
					if( test == '<%=WebConstants.COMUNICAZIONI_TIPO_MOV_DOPO_FIRMA_DOC %>')
					{
						jQuery("#destinatari_id").hide();
						jQuery("#fase_inserimento_id").hide();
						jQuery("#note_id").hide();
						
						
						
					}
					if(test == '<%=WebConstants.COMUNICAZIONI_TIPO_MOV_DOPO_INSERIMENTO_MOV %>')
					{
						jQuery("#destinatari_id").show();
						jQuery("#fase_inserimento_id").show();
						jQuery("#note_id").show();
						
					}
				
				}else
				{
					// Di default supponiamo che che come nuovo la prima voce sia WebConstants.COMUNICAZIONI_TIPO_MOV_DOPO_FIRMA_DOC
					jQuery("#destinatari_id").hide();
					jQuery("#fase_inserimento_id").hide();
					jQuery("#note_id").hide();
				}
			}
			
			function openSearch(id){
				jQuery( "#"+id ).toggle();
				
			}
			function setMittente(e){
				jQuery("#accountmail_descrizione").val(e.text);
				jQuery("#accountmail_id_hidden").val(e.value);
				jQuery("#ricerca_accountmail").hide();
				
				
			}
	</script>		
	
	<div id="functions">
		<ul>
			
	<c:if test="${tipimovimentocomunicazioni.entity.id.codice==null}">
	<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipimovimentocomunicazioni.entity.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
    <li><a href="javascript:doHref('list.htm?codiceTipomov=${tipimovimentocomunicazioni.entity.tipimovimento.id.tipomovimento}','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>




