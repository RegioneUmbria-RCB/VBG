<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.situazione_dehors" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.situazione_dehors" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	 	<br class="clear"/>
		<spring-form:form commandName="autorizzazioniSubentriCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
	        	<jsp:param name="commandName" value="autorizzazioniSubentriCommand" />
	    	</jsp:include>
			<table width="100%">
			
			<tr>
			   <td colspan="2"><b><fmt:message key="label.avvertenza_dehors_subentro" /></b></td>
			</tr>
			
			<tr class="titoloSezione">
			   <td colspan="2"><fmt:message key="label.precedente" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.mq_richiesti_dehors"/></td>
				<td><spring-form:input path="dehorsMqIstanzePrecendete.mqrichiesti" size="25"  readonly="true" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.mq_assegnati_dehors"/></td>
				<td><spring-form:input path="dehorsMqIstanzePrecendete.mqassegnati" size="25"  readonly="true" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.area" /></td>
				<td><spring-form:input size="25"  path="dehorsMqIstanzePrecendete.aree.denominazione"  /></td>		
			</tr>
			<tr class="titoloSezione">
			   <td colspan="2"><fmt:message key="label.nuova" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.mq_richiesti_dehors"/></td>
				<td >
					<spring-form:input id="mq_richiesti_id" path="dehorsMqIstanzeNuova.mqrichiesti" size="25" onblur="checkNumberValue(this);" onchange="calcolaSeDisponibile(0)" readonly="${_readonly}" />
					<spring-form:errors path="dehorsMqIstanzeNuova.mqrichiesti" cssClass="error" />
				</td>
			</tr>
			<tr>
				<td><fmt:message key="label.area" /></td>
				<%-- 
				<td>
					<spring-form:input size="28" id="aree_id" path="dehorsMqIstanzeNuova.aree.denominazione" cssClass="searchbox" onchange="checkValue(this,'tipiaree_hidden')" onkeydown="javascript:return searchAll(this,event)" /> <init:autocompleter
					methodAjax="findAreeDehors.htm" afterUpdateElement="calcolaMqDisponibiliCallBack" idHidden="aree_hidden" idInput="aree_id" inputTitleKey="label.ricerca_area"/> 
					<spring-form:errors path="dehorsMqIstanzeNuova.aree" cssClass="error" /> 
					<spring-form:hidden id="aree_hidden" path="dehorsMqIstanzeNuova.aree.id.codice" />
				</td>
				--%>	
				<td>
					<spring-form:input size="25"  path="dehorsMqIstanzeNuova.aree.denominazione"  />
					<spring-form:hidden id="aree_hidden" path="dehorsMqIstanzeNuova.aree.id.codice" />
				</td>		
			</tr>
			<tr>
				<td colspan="2">
			  		<div id="pannelloMqDisponibili">
			  		<!-- TABELLA  PER MOSTRARE E GESTIRE LA POSSIBILITà DI INSERIRE I MQ RICHIESTI
			  		     CREATA TRAMITE UNA CHIAMATA AJAX-->
					</div>
					<!-- FINE -->
				</td>
			</tr>
			<tr>
				<td><fmt:message key="label.mq_assegnati_dehors"/></td>
				<td>
					<spring-form:input id="mq_assegnati_id" path="dehorsMqIstanzeNuova.mqassegnati" size="25" onblur="checkNumberValue(this);" />
					<spring-form:errors path="dehorsMqIstanzeNuova.mqassegnati" cssClass="error" />
				</td>
			</tr>
			</table>
		</spring-form:form>
	</div>
	
	<script type="text/javascript">
		
		
		
        	calcolaSeDisponibile(0);
		
        
		function calcolaMqDisponibiliCallBack(inputField,listItem){
			 var a = listItem.id;
			 document.getElementById('aree_id').value = inputField.value;
			 document.getElementById('aree_hidden').value = a;
			 calcolaSeDisponibile(document.getElementById('aree_hidden').value);
		}
		
		function calcolaSeDisponibile(codiceArea){
			if(codiceArea==0)
			{
			 codiceArea=document.getElementById('aree_hidden').value
			}
			var mqrichiesti=document.getElementById('mq_richiesti_id').value
			if(codiceArea!='')
			{
			new Ajax.Request(
			'${pageContext.request.contextPath}/autorizzazioni/ajaxCalcolaAreaDehorsDisonibile.htm?codicearea='+ codiceArea+'&mqRichiesti='+mqrichiesti+'&codAutPrecNonCessata='+${codiceAut},
			{
				method : 'post',
				onSuccess : function(transport) {
					var response = transport.responseText;
					$("pannelloMqDisponibili").innerHTML = parseAjaxResponse(response, true, false);
					$("pannelloMqDisponibili").style.color = "red";
					$("pannelloMqDisponibili").style.font = "italic bold 15px arial,serif";
					var diffDisponibiliRichiesti=document.getElementById('disponibili_id').value
					if(diffDisponibiliRichiesti>0)
					{
						$("pannelloMqDisponibili").style.color = "green";
						var mqRichiesti=document.getElementById('mqRichiesti_id').value
						if(mqRichiesti.indexOf(".",0)>0){
							mqRichiesti=mqRichiesti.replace(".",",");
						}
						jQuery("#mq_assegnati_id").val(mqRichiesti);
					}
					$("pannelloMqDisponibili").appear();
					applyStyle();
				},
				onFailure : function(transport) {
					var response = transport.responseText;
					alert(response);
				}
			});
			}
		}
		
	</script>		

	<br />
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertSubentri.htm?forzaInserimento=true','Attenzione! Stai per eseguire il subentro delle Autorizzazioni/Concessioni selezionate. Continuare?',document.inviodati);"><fmt:message key="button.subentro" /></a></li>	
			<li><a href="javascript:doHref('createSearch.htm?codiceIstanza=${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice }','');"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>