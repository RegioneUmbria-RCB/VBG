<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.impostazioni_utete" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.impostazioni_utete" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="configurazioneutenteCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="configurazioneutenteCommand" />
		    </jsp:include>
			<table width="100%">
			    <tr>
					<td width="20%">
						<fmt:message key="label.stile_css_utilizzato" />
					</td>
					<td width="15%">
					    <spring-form:select path="styleCss" >
					    	<spring-form:options items="${stilis}" itemValue="stNomefile" itemLabel="stDescrizione" />
					    </spring-form:select>
						<spring-form:errors path="styleCss" cssClass="error"/>
					</td>
					<td><fmt:message key="label.descrizione_impostazione_stile_css" /></td>
				</tr>
				<tr>
					<td >
						<fmt:message key="label.limite_record_liste" />
					</td>
					<td>
					    <spring-form:select  path="limitePaginazioneListe">
					        <c:if test="${configurazioneutenteCommand.limitePaginazioneListe==10}">
					    		<option value="10" selected="selected">10</option>
					    	</c:if>
					    	<c:if test="${configurazioneutenteCommand.limitePaginazioneListe!=10}">
					   		<option value="10">10</option>
					    	</c:if>
					    	<c:if test="${configurazioneutenteCommand.limitePaginazioneListe==50}">
					    		<option value="50"  selected="selected">50</option>
					    	</c:if>
					    	<c:if test="${configurazioneutenteCommand.limitePaginazioneListe!=50}">
					    		<option value="50" >50</option>
					    	</c:if>
					    	<c:if test="${configurazioneutenteCommand.limitePaginazioneListe==100}">
					    		<option value="100" selected="selected" >100</option>
					    	</c:if>
					    	<c:if test="${configurazioneutenteCommand.limitePaginazioneListe!=100}">
					    		<option value="100" >100</option>
					    	</c:if>
					    	
					    	
					    </spring-form:select>
						<spring-form:errors path="limitePaginazioneListe" cssClass="error"/>
					</td>
					<td><fmt:message key="label.descrizione_impostazione_limite_record_liste" /></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.salva_ed_esci" />
					</td>
					<td>
					    <spring-form:checkbox path="salvaEchiudiInMovimenti" />
						<spring-form:errors path="salvaEchiudiInMovimenti" cssClass="error"/>
					</td>
					<td><fmt:message key="label.descrizione_impostazione_salva_chiudi" /></td>
				</tr>
				
			</table>
			<script type='text/javascript'>
				$('limitePaginazioneListe').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertOrUpdate.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<br />
	<div id="subcontent">
		<div class="titoloSezione">
		   <fmt:message key="label.lista_link_preferiti" />
		</div>
	    <form name="linkForm" action="list.htm">
        <jmesa:springTableFacade id="link_id" items="${linkPreferitiUtente}" var="link_var" stateAttr="restore">
			<jmesa:htmlTable>
				<jmesa:htmlRow>
					<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
		                 <a href="javascript:tabAggiornaPreferito('preferitiDialogDiv',${link_var.id.codice})">${link_var.id.codice}</a>
		            </jmesa:htmlColumn>
					<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione" width="40%" />
					<jmesa:htmlColumn property="url" titleKey="label.url" width="40%"/>
					<jmesa:htmlColumn property="target" titleKey="label.target" width="5%">
					<c:if test="${empty link_var.target}">
						<fmt:message key="label.interno" />
					</c:if>
					<c:if test="${not empty link_var.target}">
						<fmt:message key="label.esterno" />
					</c:if>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn property="ordine" titleKey="label.ordine" filterable="false"  sortable="false" width="5%"/>
					<jmesa:htmlColumn property="" titleKey="label.edit.record"	sortable="false" filterable="false" width="5%">
						<a class="dettaglioColumn" href="javascript:tabAggiornaPreferito('preferitiDialogDiv',${link_var.id.codice})"	title="<fmt:message key="label.edit.record" />&nbsp;${link_var.descrizione}">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>
						<a class="eliminaRiga" href="deleteLinkPreferiti.htm?codice=${link_var.id.codice}"	title="<fmt:message key="label.elimina" />&nbsp;${link_var.descrizione}">
							<label><fmt:message key="label.elimina" /></label>
						</a>
					</jmesa:htmlColumn>
					</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableFacade>
		</form>
 
        
		<script type="text/javascript">
				var _jmesaUrl='view.htm?';
				var _captionTab='<fmt:message key="label.lista_link_preferiti" />';
		</script>
		
		<div >
			<a class="addColumn" style="cursor: pointer;" onclick="javascript:tabAggiungiPreferito('preferitiDialogDiv');"> 
				<label><fmt:message key="label.aggiungi" /></label> 
			</a>
			<div dojoType="dijit.Dialog" id="preferitiDialogDiv" title="<fmt:message key="label.aggiungi_preferito" />: ">
				<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 510px; height: 300px;">
					<div id="dettaglio"></div>
				</div>
			</div>
		</div>
		<script type="text/javascript">

		function tabAggiungiPreferito(divId,id){
			
				dijit.byId(divId).show();
				aggiungiPreferitoTab(id);		
		}
		
		
		function aggiungiPreferitoTab(id) {	
				new Ajax.Request(
						'${pageContext.request.contextPath}/configurazioneutente/ajaxAddPreferito.htm',
						{
							method : 'post',
							onSuccess : function(transport) {							
								var response = transport.responseText;							
								$("dettaglio").innerHTML = response;					
							},
							onFailure : function(transport) {
								var response = transport.responseText;
								alert(response);
							}
						});
		}
		
		
		function tabAggiornaPreferito(divId,codice){
			
			dijit.byId(divId).show();
			modificaPreferitoTab(codice);		
		}
	
	
		function modificaPreferitoTab(codice) {	
				new Ajax.Request(
						'${pageContext.request.contextPath}/configurazioneutente/ajaxUpdatePreferito.htm?codice='+codice,
						{
							method : 'post',
							onSuccess : function(transport) {							
								var response = transport.responseText;							
								$("dettaglio").innerHTML = response;					
							},
							onFailure : function(transport) {
								var response = transport.responseText;
								alert(response);
							}
						});
		}
		
		
		
		function aggiungiLinkPreferito(codice)
		{
			var go=true;
			var validationMessage='Attenzione: \n'
			if(document.getElementById('descrizione_id').value=='')
			{
				validationMessage=validationMessage + 'Il campo descrizione è obbligatorio \n';
				go=false;
			}
			if(document.getElementById('url_id').value=='')
			{
				validationMessage=validationMessage + 'Il campo url è obbligatorio \n';
				go=false;
			}
			if(go==true)
			{
				if(codice=='')
				{
					javascript:doSubmit('aggiungiLinkPreferito.htm','',document.innerForm);
				}else
				{
					javascript:doSubmit('updateLinkPreferito.htm','',document.innerForm);
				}
			}else
			{
				alert(validationMessage);
			}
			
		}
		
		
		aggiungiLinkPreferito
	

		</script>
	</div>
	
	
	</div>
</body>
</html>