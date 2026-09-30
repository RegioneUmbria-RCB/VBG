<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${nlaservizi.id.codice==null}">
			<fmt:message key="nlaservizi.label.nuovo_nlaservizi.title" />
		</c:if> 
		<c:if test="${nlaservizi.id.codice!=null}">
			<fmt:message key="nlaservizi.label.dettaglio_nlaservizi.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${nlaservizi.id.codice==null}">
			<fmt:message key="nlaservizi.label.nuovo_nlaservizi.title" />
		</c:if> 
		<c:if test="${nlaservizi.id.codice!=null}">
			<fmt:message key="nlaservizi.label.dettaglio_nlaservizi.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="nlaservizi" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="nlaservizi" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.stcIdnodo" />
					</td>
					<td>
						<spring-form:input id="idnodo_id" path="idnodo" size="10" maxlength="6" />
						<spring-form:errors path="idnodo" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.stcIdente" />
					</td>
					<td>
						<spring-form:input id="idente_id" path="idente" size="10" maxlength="10" />
						<spring-form:errors path="idente" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.stcIdsportello" />
					</td>
					<td>
						<spring-form:input id="idsportello_id" path="idsportello" size="10" maxlength="10" />
						
						<spring-form:errors path="idsportello" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.pec" />
					</td>
					<td>
						<spring-form:input id="pec_id" path="pec" size="70" maxlength="320" />				
						<spring-form:errors path="pec" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" maxlength="4000" />
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
			</table>
		<script type='text/javascript'>
				$('idnodo_id').focus();
		</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${nlaservizi.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${nlaservizi.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
			
	<c:if test="${nlaservizi.id.codice!=null}">
		<script type='text/javascript'>
		
		function viewAltriDati(){
			jQuery("#altriDatiErrorId").hide();
			var jhqrPr = jQuery.ajax({
				  url: 'ajaxViewAltriDati.htm',
				  context: document.body,
				  cache: false,
				  data: "codiceservizio=${nlaservizi.id.codice}",
				  dataType: "html",
				  success: function(data) {
					  jQuery("#altriDatiId").html(data);						
				  },
				  error: function(jqXHR, textStatus, errorThrown){
					  jQuery("#altriDatiErrorId").html(jqXHR.responseText);
					  jQuery("#altriDatiErrorId").show();
				}
			});					
		}
		
		jQuery(document).ready(function(){
		
			viewAltriDati();
						
		});			
		
		function aggiungiParametro(){
			jQuery("#nomeparametroID").val("");
			jQuery("#valoreID").val("");
			
			dijit.byId('aggiungiParametroDiv').show();	
		}
		
		
		
		
		function inserisciParametro(){
			
			var nomeparametro = jQuery("#nomeparametroID").val();
			var valore = jQuery("#valoreID").val();
			if(nomeparametro==''){
				alert('<fmt:message key="label.nome_parametro" /> <fmt:message key="alert.required" />');
				return;
			}
			
				var jhqrPr = jQuery.ajax({
					  type: "POST",
					  url: 'ajaxInsertAltriDati.htm',
					  context: document.body,
					  cache: false,
					  data: "codiceservizio=${nlaservizi.id.codice}&nomeparametro="+nomeparametro+"&valore="+valore,
					  dataType: "html",
					  success: function(data) {
						  dijit.byId('aggiungiParametroDiv').hide();
						  viewAltriDati();
						  showInfo('<fmt:message key="01" />');
					  },
					  error: function(jqXHR, textStatus, errorThrown){
						  dijit.byId('aggiungiParametroDiv').hide();
						  jQuery("#altriDatiErrorId").html(jqXHR.responseText);
						  jQuery("#altriDatiErrorId").show();
					}
				});					
					
		}
	function eliminaParametro(codice){
				if(confirm('<fmt:message key="javascript.confirm.delete" />')){
					var jhqrPr = jQuery.ajax({
						  type: "POST",
						  url: 'ajaxDeleteAltriDati.htm',
						  context: document.body,
						  cache: false,
						  data: "codice="+codice,
						  dataType: "html",
						  success: function(data) {
							  viewAltriDati();
							  showInfo('<fmt:message key="05" />');
						  },
						  error: function(jqXHR, textStatus, errorThrown){
							  jQuery("#altriDatiErrorId").html(jqXHR.responseText);
							  jQuery("#altriDatiErrorId").show();
						}
					});					
				}
		}
		
		function showInfo(msg){
			jQuery("#altriDatiInfoId").html(msg);
			jQuery("#altriDatiInfoId").show();
			setTimeout('jQuery("#altriDatiInfoId").hide()',3000);
		}
		</script>
		
		<br class="clear"/><br class="clear"/>
		<div id="altriDatiInfoId" class="success" style="display: none;">
		</div>
		<div id="altriDatiErrorId" class="error" style="display: none;">
		</div>		
		<div id="altriDatiId">
		
		
		</div>
		
		<div dojoType="dijit.Dialog" id="aggiungiParametroDiv" title="<fmt:message key="label.nuovo_parametro" />"  style="display: none;">
			
			<table style="width: 300px;">
			<tr>
				<td style="width: 20%;"><fmt:message key="label.nome_parametro" /></td>
				<td><input type="text" id="nomeparametroID" name="nomeparametro" maxlength="50"/></td>
			</tr>
			<tr>
				<td style="width: 20%;"><fmt:message key="label.valore" /></td>
				<td><input type="text" id="valoreID" name="valore" maxlength="200"/></td>
			</tr>
			</table>
			
			<div id="functions">
				<ul>
					<li><a href="javascript:inserisciParametro();"><fmt:message key="button.save" /></a></li>
					<li><a href="javascript:void 0" onclick="dijit.byId('aggiungiParametroDiv').hide();"><fmt:message key="button.annulla" /></a></li>
				</ul>
			</div>
			<br class="clear" />	
		</div>
		
	</c:if>
	
	</body>
</html>