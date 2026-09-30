<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.web.MailConfigCommand"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.configurazione_parametri_mail" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.configurazione_parametri_mail" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="mailconfig" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mailconfig" />
		    </jsp:include>
		    
		 <c:set value="false" scope="page" var="onlyRead"></c:set>
		 <c:if test="${mailconfig.entity.flagDisabilitato eq true }">
		 	<c:set value="true" scope="page" var="onlyRead"></c:set> 
		 </c:if>
		 
		 <div class="parametriDiv">
			<div class="etichetta">
			   	<c:if test="${isComuniAssociati eq true}"><div><fmt:message key="label.comune" />:</div> </c:if>
				<div><fmt:message key="label.modulo" />:</div>
			</div>		
			<div class="parametro ">
				<div class="inline-ui-cell">
					<c:if test="${isComuniAssociati eq true}"><c:if test="${mailconfig.entity.comuni.codicecomune eq null}">Tutti</c:if> </c:if>
					<c:if test="${mailconfig.entity.comuni.codicecomune ne ''}">${mailconfig.entity.comuni.comune}</c:if>
				</div>       		 	
				<div >
					${mailconfig.entity.software.descrizione}
				</div>
			</div>
		</div>
		<br/><br/>
		<table>
		<%-- 
		<tr>
			<td>
			<jsp:include page="../includes/comboComuni.jsp">
				<jsp:param name="mostraTutti" value="true" />
				<jsp:param name="readOnly" value="${onlyRead}" />
				<jsp:param name="commandPropertyPath" value="entity.comuni" />
				<jsp:param name="colspan" value="4" />
				<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
				
			</jsp:include>
			</td>
		</tr>
		--%>
		<tr>
			<td>
				<fmt:message key="label.nome" />
			</td>
			<td>
				<spring-form:input id="loginname_id" path="entity.descrizione" size="70" readonly="${onlyRead}" />
				<spring-form:errors path="entity.descrizione" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.principale" />
			</td>
			<td>
				<spring-form:checkbox id="flagPrincipale_id" path="entity.flagPrincipale"  disabled="${onlyRead}"/>
				<spring-form:errors path="entity.flagPrincipale" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.disabilitato" />
			</td>
			<td>
				<spring-form:checkbox id="flagDisabilitato_id" path="entity.flagDisabilitato" />
				<spring-form:errors path="entity.flagDisabilitato" cssClass="error"/>
			</td>
		</tr>
		</table>
		<c:if test="${dettaglioMailConfig eq true }">
			<div id="listaComuni">
			</div>	 
		</c:if>
		  
		<table border="0" width="100%"><tr><td valign="top">
			<table width="100%">
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.dati_mail_out"/></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.utente" />
					</td>
					<td>
						<spring-form:input id="loginname_id" path="entity.loginname" size="30" readonly="${onlyRead}"/>
						<spring-form:errors path="entity.loginname" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.password" />
					</td>
					<td>						
						<% MailConfigCommand cmd = (MailConfigCommand)request.getAttribute("mailconfig");
							String pass = StringUtils.defaultString(cmd.getEntity().getLoginpass());
							
							if(StringUtils.isNotBlank(pass)){
							    pass = StringUtils.repeat("*", pass.length());
							}
							out.print("<b>"+pass+"</b>");
						%>						
						<br />
						<spring-form:password id="loginpass_new_id" path="newLoginpass" showPassword="false" size="32" />
						<spring-form:errors path="entity.loginpass" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.mailserver" />
					</td>
					<td>
						<spring-form:input id="mailserver_id" path="entity.mailserver" size="30" readonly="${onlyRead}"/>
						<spring-form:errors path="entity.mailserver" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.port" />
					</td>
					<td>
						<spring-form:input id="loginname_id" path="entity.port" size="6" cssStyle="text-align:right;" readonly="${onlyRead}"/>
						<spring-form:errors path="entity.port" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.usa_autenticazione" />
					</td>
					<td>
						<spring-form:checkbox id="useauthentication_id" path="entity.useauthentication" value="true" disabled="${onlyRead}"/>
						<spring-form:errors path="entity.useauthentication" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.protocollo_invio_mail" />
					</td>
					<td>
						<spring-form:select path="entity.usessl" disabled="${onlyRead}">
							<spring-form:option value="0"><fmt:message key="label.smtp" /></spring-form:option>
							<spring-form:option value="1"><fmt:message key="label.legacy_smtps" /></spring-form:option>
							<spring-form:option value="2"><fmt:message key="label.smtp_with_ssl" /></spring-form:option>
						</spring-form:select>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.email_mittente" />
					</td>
					<td>
						<spring-form:input id="senderaddress_id" path="entity.senderaddress" size="30" readonly="${onlyRead}"/>
						<spring-form:errors path="entity.senderaddress" cssClass="error"/>
					</td>
				</tr>
			</table>
			</td><td valign="top">
			<table width="100%">
				<tr class="titoloSezione">
					<td colspan="2"><fmt:message key="label.dati_mail_in"/></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.utente" />
					</td>
					<td>
						<spring-form:input id="loginnamein_id" path="entity.inLoginname" size="30" readonly="${onlyRead}"/>
						<spring-form:errors path="entity.inLoginname" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.password" />
					</td>
					<td>
					<%
					String inLoginPass = StringUtils.defaultString(cmd.getEntity().getInLoginpass());
					if(StringUtils.isNotBlank(inLoginPass)){
					    inLoginPass = StringUtils.repeat("*", inLoginPass.length());
					}
					out.print("<b>"+inLoginPass+"</b>");
					%>
					
						<spring-form:password id="inloginpass_new_id" path="newInLoginpass" showPassword="false" size="32" />
						<spring-form:errors path="entity.inLoginpass" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.mailserver" />
					</td>
					<td>
						<spring-form:input id="mailserverin_id" path="entity.inMailserver" size="30" readonly="${onlyRead}"/>
						<spring-form:errors path="entity.inMailserver" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.port" />
					</td>
					<td>
						<spring-form:input id="loginnamein_id" path="entity.inPort" size="6" cssStyle="text-align:right;" readonly="${onlyRead}"/>
						<spring-form:errors path="entity.inPort" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.usa_autenticazione" />
					</td>
					<td>
						<spring-form:checkbox id="useauthenticationin_id" path="entity.inUseauthentication" value="true" disabled="${onlyRead}"/>
						<spring-form:errors path="entity.inUseauthentication" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.protocollo_lettura_mail" />
					</td>
					<td>
						<spring-form:select path="entity.inUsessl" disabled="${onlyRead}">
							<spring-form:option value="0"><fmt:message key="label.pop3" /></spring-form:option>
							<spring-form:option value="1"><fmt:message key="label.imap" /></spring-form:option>
							<spring-form:option value="2"><fmt:message key="label.ssl_pop3" /></spring-form:option>
							<spring-form:option value="3"><fmt:message key="label.ssl_imap" /></spring-form:option>
						</spring-form:select>
					</td>
				</tr>
			</table>
			</td></tr></table>
			<script type='text/javascript'>
				$('loginname_id').focus();
											
				 function visualizzaComuni(){		
						disableFunctions();					
						var jhqrPr = jQuery.ajax({
							url: '${pageContext.request.contextPath}/mailconfig/ajaxDettaglioComuni.htm?codice=${mailconfig.entity.id.codice}',
									context: document.body,
									cache: false,
									dataType: "html",
									success: function(data, textStatus, jqXHR){						
										  if(data){
											  jQuery('#listaComuni').html(data);
											  enableFunctions();
										  }	
									},
									error: gestisciErrore
						});
					}
				
				
				
				function nuovoComune(codiceComune){
					// elimino l'eventuale messaggio di errore se presente
					jQuery('#messaggioErrore').hide();
					
					if(codiceComune!=''){
						disableFunctions();
						var jhqrPr = jQuery.ajax({
							url: "${pageContext.request.contextPath}/mailconfig/ajaxAssegnaComune.htm?codice=${mailconfig.entity.id.codice}&codicecomune="+codiceComune,
							context: document.body,
							cache: false,
							dataType: "html",
							success: function(data, textStatus, jqXHR){
								verificaErroreoSuccessoComune(data);
							},
							error: gestisciErrore
						});
					}
				}
				function verificaErroreoSuccessoComune(data){
					 // verifica errori o altro e aggiorna					 
					 if(data.toLowerCase() == 'inserito' || data.toLowerCase() =='eliminato'){
						 visualizzaComuni();
					 }else{
						 jQuery('#messaggioErrore').html(data);
						 jQuery('#messaggioErrore').show();				 
						 jQuery('#comune_id').val('');
						 jQuery('#comune_id_hidden').val('');
						 enableFunctions();
					 }
				}
				function setHiddenFieldComune(inputField,listItem){
					var a = listItem.id;
					nuovoComune(a);
				}
				function eliminaComune(id){
					if(confirm('<fmt:message key="javascript.confirm.delete" />')){
						// elimino l'eventuale messaggio di errore se presente
						jQuery('#messaggioErrore').hide();
						disableFunctions();
						var jhqrPr = jQuery.ajax({
							  url: '${pageContext.request.contextPath}/mailconfig/ajaxEliminaComune.htm?codice='+id,
							  context: document.body,
							  cache: false,					  
							  dataType: "html",
							  success: function(data, textStatus, jqXHR){
								  verificaErroreoSuccessoComune(data,'');
							  },
							  error: gestisciErrore
							});						
					}
				}
				function gestisciErrore(jqXHR, textStatus, errorThrown){
			  		 console.error([jqXHR, textStatus, errorThrown ]);
			  		 jQuery('#listaComuni').html("<div class='error_header'>Si è verificato un errore di sistema. Riprovare in un secondo momento</div>");
					 jQuery('#listaComuni').show();		
					 enableFunctions();
			  	}
				
				jQuery(document).ready(function(){
					visualizzaComuni();
				});
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
		    <c:if test="${mailconfig.entity.id.codice==null }">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mailconfig.entity.id.codice!=null }">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>