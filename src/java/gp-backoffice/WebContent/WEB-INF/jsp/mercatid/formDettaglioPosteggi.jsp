<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="mercatid.label.dettaglio_posteggio.title" />
	</title>
</head>
<body>
	
	<span class="titoloPagina">
		<fmt:message key="mercatid.label.dettaglio_posteggio.title" />
    </span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>

	<div id="subcontent">
	
		 <jsp:include page="../includes/linkmercato.jsp">
			<jsp:param name="codiceMercato" value="${mercati.id.codice}" />
			<jsp:param name="descrizioneMercato" value="${mercati.descrizione}" />											
		</jsp:include>
	    <br class="clear" /> 
	    <jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercatid" />
	     </jsp:include>	
		<spring-form:form commandName="mercatid" name="inviodati">
			
	   
		<table>
			<tr>
				<td>
					<fmt:message key="label.larghezza" />
				</td>
				<td>
					<spring-form:input id="larghezza_id" path="larghezza" size="8" cssStyle="text-align:right;" onchange="checkNumberValue(this);calcolaSuperficie();" />
					<spring-form:errors path="larghezza" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.lunghezza" />
				</td>
				<td>
					<spring-form:input id="lunghezza_id" path="lunghezza" size="8" cssStyle="text-align:right;" onchange="checkNumberValue(this);calcolaSuperficie();" />
					<spring-form:errors path="lunghezza" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.superficie" />
				</td>
				<td>
					<spring-form:input id="superficie_id" path="superficie" cssStyle="text-align:right;" size="8" onchange="checkNumberValue(this);"/>
					<spring-form:errors path="superficie" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.tipo_spazio" />
				</td>
				<td>
					<spring-form:input id="tipoSpazio_id" path="tipoSpazio.tipospazio" cssClass="searchbox" onchange="checkValue(this,'tipoSpazio_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
					<init:autocompleter methodAjax="findtipospazio.htm" idHidden="tipoSpazio_hidden" idInput="tipoSpazio_id" inputTitleKey="label.ricerca_tipo_spazio"></init:autocompleter>
					<spring-form:errors path="tipoSpazio.tipospazio" cssClass="error"/> 
					<spring-form:hidden id="tipoSpazio_hidden" path="tipoSpazio.id.codice"  />
				</td>
			</tr>
			
			<tr>
				<td>
					<fmt:message key="label.indirizzo" />
				</td>
				<td>
					<spring-form:input id="stradario_id" path="stradario.descrizioneCompleta" cssClass="searchbox" onchange="checkValue(this,'descrizione_hidden')" onkeydown="javascript:return searchAll(this,event) " size="60"/>
					<init:autocompleter methodAjax="findStradarioMercato.htm?codicemercato=${mercati.id.codice}" idHidden="stradario_hidden" idInput="stradario_id" inputTitleKey="label.ricerca_stradario"></init:autocompleter>
					<spring-form:errors path="stradario" cssClass="error"/> 
					<spring-form:hidden id="stradario_hidden" path="stradario.id.codice"  />
				</td>			
			</tr>
	        <tr>
		       <td><fmt:message key="label.disabilitato" /></td>
		       <td >
		       <spring-form:select  id="disabilitato_id"  path="disabilitato" >
			         <spring-form:option value="null" ><fmt:message key="label.select.default" /></spring-form:option>
			         <spring-form:option value="0" ><fmt:message key="label.abilita" /></spring-form:option>
			         <spring-form:option value="1"><fmt:message key="label.disalbilita" /></spring-form:option>
			    </spring-form:select>				
		       <init:help idHelp="help1" textKey="mercatid.help.select_disabilitato"/>
		       <spring-form:errors path="disabilitato" cssClass="error"/></td>
	        </tr>
		</table>
			
	<script type='text/javascript'>
		$('larghezza_id').focus();
		function calcolaSuperficie()
		{
			var lunghezza= $("lunghezza_id").value;
			var larghezza=$("larghezza_id").value;
			if(larghezza.indexOf(",",0)>0){
			larghezza=larghezza.replace(",",".");
			
			}
			if(lunghezza.indexOf(",",0)>0){
			lunghezza=lunghezza.replace(",",".");
			
			}
			var superficie=0;
			
			if(lunghezza>0 && larghezza>0)
			{
				superficie=lunghezza*larghezza;
				superficie=superficie.toString();
				if(superficie.indexOf(".",0)>0){
				superficie=superficie.replace(".",",");
				}
			    $("superficie_id").value=superficie;
			}else
			{
				$("superficie_id").value='';
			}
				
		}
	</script>	
	
	</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li id="inserisci_id"><a href="javascript:doSubmit('insertDettaglioPosteggi.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
		    <%-- <li><a href="javascript:doHref('list.htm?codicemercato=${mercati.id.codice}','')"><fmt:message key="button.back" /></a></li> --%>
		    <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
	<%-- 
	
    <br/><br/>	
	<spring-form:form commandName="mercatid" name="inviodati_1">
	<div class="titoloSezione"><fmt:message key="label.livelli_servizio_posteggio" /></div>
	
	<table>
		<tr>
			<td><fmt:message key="label.giorno" /></td>
			<td></td>
		</tr>
		<tr>	
			<td><fmt:message key="label.livello_servizio" /></td>
			<td></td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.fattore_moltiplicativo" />
			</td>
			<td>
				<spring-form:input id="tariffa_id" path="entity.tariffa" size="6" onblur="checkNumberValue(this);" />
				<spring-form:errors path="entity.tariffa" cssClass="error"/>
			</td>
			<td>
				<fmt:message key="label.attivo" />
			</td>
			<td>
				<spring-form:checkbox id="attivo_id" path="entity.attivo" />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.data_inizio_validita" />
			</td>
			<td>
				<spring-form:input id="data_id" path="entity.dataInizioValidita" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
				<spring-form:errors path="entity.dataInizioValidita" cssClass="error"/>
			</td>
			<td>
				<fmt:message key="label.data_fine_validita" />
			</td>
			<td>
				<spring-form:input id="datafine_id" path="entity.dataFineValidita" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="datafine_id" textKey="label.calendar"/>
				<spring-form:errors path="entity.dataFineValidita" cssClass="error"/>
			</td>
		</tr>
	
	</table>
	</spring-form:form>
	
	<div class="jmesa" style="padding:0;">
		<table  border="0"  cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td width="15%" ><fmt:message key="label.segnaposto_formula" /></td>
					<td width="40%"><fmt:message key="label.descrizione" /> </td>
	            </tr>
			</thead>
			<tbody class="tbody">
			<%int l=1;%>
			<%-- 
			<c:if test="${not empty livelloServizios}">
			<c:forEach items="${livelloServizios}" var="liv_servizio_var">
			<tr class="<%=(l%2)==0?"odd":"even"%>">
			      <td><b><a  href="javascript:dettagliotariffe('${liv_servizio_var.id.codice}')"  />${liv_servizio_var.segnaposto}</a></b></td>
			      <td>${liv_servizio_var.descrizione}</td>
             </tr>
			<%l++; %>
			</c:forEach>
			</c:if>
			<c:if test="${ empty livelloServizios}">
				<tr class="even">
					<td colspan="6"><fmt:message key="html.statusbar.noResultsFound" /></td>
				</tr>
			</c:if>
			</tbody>
			--%>
			</table>
	 </div>
	
	
	
	
</body>
</html>