<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="form.registrazioni.rateizzazioni.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.registrazioni.rateizzazioni.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
<span class="parametri">
  <fmt:message key="form.registrazioni.progressivo" /> : <label><c:out value="${registrazioniFilter.progressivo}"/></label>
</span>
<span class="parametri">
  <fmt:message key="form.registrazioni.importo" /> : <label><fmt:formatNumber minFractionDigits="2" value="${registrazioniFilter.importo}" /></label>
</span>
<br/>
<span class="parametri">
  <fmt:message key="form.registrazioni.conti" /> : 
</span>
<c:forEach items="${regImportiList}" var="importi_var">
<span class="parametri">
<c:out value="${importi_var.conti.descrizione}"></c:out>:<label><fmt:formatNumber minFractionDigits="2" value="${importi_var.importo}" />&nbsp;€</label>
</span>
</c:forEach>

<%
String  rateizzazione="display:none;";
String readonly="true";
String dataInzio="display:none;";
String tabInteressi="display:none;";
%>
	<spring-form:form commandName="registrazioniFilter" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="registrazioniFilter" />
    </jsp:include>
    
    <table >
	    <tr>
	    <td><fmt:message key="form.registrazioni.dataregistrazione" />   </td>
	    <td>
	    		<spring-form:input id="dataregistrazione_id" path="dataInizio" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
	    		<init:calendar imagePath="/images/cal.gif" idImage="caldataregistrazione" idInput="dataregistrazione_id" textKey="label.calendar"/>
				<spring-form:errors	path="dataInizio" cssClass="error" />
	    </td>
	    </tr>
		<tr>
			<td><fmt:message key="form.registrazioni.tiporateizzazione" /></td>
			<td>
				<spring-form:select id="selectRate" path="oneritipirateizzazione.id.codice" onchange="assegnaRateizzazioni();">
				    <spring-form:option value="0"><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${oneritipirateizzazioneList}" itemValue="id.codice"	itemLabel="descrizione" />
				</spring-form:select>
				<spring-form:errors	path="oneritipirateizzazione.id.codice" cssClass="error" />
			</td>
		</tr>
	</table>
	
	
	




<fieldset  id="tipiRateizzazione" style="<%=rateizzazione %>">
<legend><fmt:message key="form.registrazioni.tiporateizzazione" /></legend>
	<table>
	<tr>
		<td><fmt:message key="form.registrazioni.tiporateizzazione.edit" />:</td> 
		<td >
			<spring-form:checkbox id="checkEdit" path="oneritipirateizzazione.editOneriTransient"  onclick="changeValueEdit();"></spring-form:checkbox>
			<init:help idHelp="helpedit" textKey="form.registrazionimercato.rangeRateizzazioni.edit.help"/>
		</td>
	</tr>
	<tr>
		<td><fmt:message key="form.registrazioni.tiporateizzazione.numerorate" />:</td> 
		<td >
			<spring-form:input id="nrrate" onchange="calcolaRipartizione($('calc_acconto_id'))" path="oneritipirateizzazione.nrorate" size="3" maxlength="3" cssStyle="text-align: right;" disabled="<%=readonly %>" onblur="checkNrRate(this);updateValue();"/>  
		<init:help idHelp="helprate" textKey="form.registrazionimercato.rangeRateizzazioni.nrrate.help"/>
	</td>
</tr>
<tr>
	<td><fmt:message key="form.registrazioni.tiporateizzazione.ripartizionerate" />:</td> 
	<td ><spring-form:input id="ripRate" path="oneritipirateizzazione.ripartizionerate" size="60" disabled="<%=readonly %>" onblur="checkTipirateizzazioni(this);"/>
	<init:help idHelp="help3" textKey="form.registrazionimercato.rangeRateizzazioni.ripartizionerate.help"/>
	&nbsp;<input type="calc_acconto" style="text-align: right;" onchange="calcolaRipartizione(this)" id="calc_acconto_id"/>
	
	<script type="text/javascript">
	function calcolaRipartizione(obj){
		
		checkNumberInt($('nrrate'));
		var nrrate = $('nrrate').value;
		var acconto = obj.value;
		if(acconto == ""){
			acconto = 0;
		}
		checkNumberValue(obj);
		if(acconto!=0){
			var percentualePrimarata = (100 * acconto)/${registrazioniFilter.importo};
			var percentualeRimanente = 100-percentualePrimarata;
			var ripartSingola = percentualeRimanente/(nrrate-1);
			var ripartizioneFinale = Math.round(percentualePrimarata*100)/100;
			for(i=0;i<(nrrate-1);i++){
				ripartizioneFinale +=";"+Math.round(ripartSingola*100)/100 
			}
		}else{
			var ripartSingola = 100/(nrrate);
			ripartizioneFinale = Math.round(ripartSingola*100)/100;
			for(i=0;i<(nrrate-1);i++){
				ripartizioneFinale +=";"+Math.round(ripartSingola*100)/100
			}
		}
		$('ripRate').value=ripartizioneFinale;
	}

</script>
</td>
</tr>
<tr>
	<td><fmt:message key="form.registrazioni.tiporateizzazione.frequenzarate" />:</td> 
	<td > 
		<spring-form:input id="freqRate" path="oneritipirateizzazione.frequenzarate" size="20" disabled="<%=readonly %>" onblur="checkTipirateizzazioni(this);"/>
	<init:help idHelp="help4" textKey="form.registrazionimercato.rangeRateizzazioni.frequenzarate.help"/>
	</td>
</tr>
<tr>
	<td><fmt:message key="form.registrazioni.tiporateizzazione.scadenzarate" />:</td> 
	<td >
		<spring-form:select id="scadenzaRate" path="oneritipirateizzazione.scadenzarate.id" disabled="<%=readonly %>" >
		    <spring-form:options items="${tipiscadenzaList}" itemValue="id"	itemLabel="descrizione" />
		</spring-form:select>
	</td>
</tr>
<tr>
	<td><fmt:message key="form.registrazioni.tiporateizzazione.interessi" />:</td> 
	<td>
		<spring-form:input id="interessi" path="oneritipirateizzazione.interessirate" size="20" disabled="<%=readonly %>" onblur="checkTipirateizzazioni(this);"/>
	</td>
</tr>
<tr>
	<td><fmt:message key="form.registrazioni.tiporateizzazione.interessilegali" />:</td> 
	<td >
		<spring-form:checkbox id="interessilegali" path="oneritipirateizzazione.flagInteressiLegali"  onclick="changeInteressiLegali();" disabled="<%=readonly %>" value="false"></spring-form:checkbox>
		<init:help idHelp="helplegali" textKey="form.registrazionimercato.rangeRateizzazioni.flagInteressiLegali.help"/>

	</td>

</tr>
<tr id="dataInizioTR" style="<%=dataInzio %>">
<td colspan="4">
<fieldset>
<legend><fmt:message key="form.registrazioni.conti" /></legend>
<table cellpadding="5" cellspacing="5">

<c:forEach items="${registrazioniFilter.contoInteressiLegaliList}" var="current" varStatus="a">

		<tr >
			<td><label>${current.conti.descrizione}</label></td>
			<td >
			<spring:bind path="contoInteressiLegaliList[${a.index}].dataInizio">
				<input type="text" id="dataInizioTransient${a.index}" name="${status.expression}" onblur="isValidDate(this,true);" value="${status.value}"  size="10"  maxlength="10"/>
			<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio${a.index}" idInput="dataInizioTransient${a.index}" textKey="label.calendar"/>	
			<init:help idHelp="helpData${a.index}" textKey="form.registrazionimercato.rangeRateizzazioni.datainizio.help"/>
			</spring:bind>
			<spring-form:errors path="contoInteressiLegaliList[${a.index}].dataInizio" cssClass="error" />
			</td>
		</tr>


	</c:forEach>

</table>
</fieldset>
	</td>
</tr>
<tr id="labelInteressiLegali"  style="<%=tabInteressi %>">
	<td>
		<fmt:message key="form.registrazioni.tiporateizzazione.tabinteressilegali" />:
		<a class="vbg-btn btn-dettaglio" onclick="dettaglioTabInteressi(this);" />	
	</td>
			
</tr>
<tr id="tabInteressiLegali" valign="middle">
<td colspan="2" >		
	<span id="interessi_dettaglio" style="display: none; text-align: left;z-index: 1000;"></span>
	<script	type="text/javascript">
		var tabInteressi=false;
		function dettaglioTabInteressi(obj){
			if(tabInteressi==false){
				new Ajax.Request('<%=request.getContextPath()%>/ajax/dettaglioInteressiLegali.htm', {
					  method: 'post',
					  onSuccess: function(transport){
						  var response = transport.responseText;		
						  $("interessi_dettaglio").innerHTML = response;
						  $("interessi_dettaglio").appear();	
						  applyStyle();						  
					    },
					  onFailure: function(transport){ 
						var response = transport.responseText;
					    alert(response); }						    		 
					  });
				tabInteressi=true;
			}else{
				$("interessi_dettaglio").dropOut();
				tabInteressi=false;
			}
			  
		}
	</script>
	</td>
</tr>

		
	</table>
	</fieldset>
	<script type="text/javascript">
	//<![CDATA[ 					
		    Calendar.setup({
		    	inputField     :    "dataregistrazione_id",     // id of the input field
		    	button         :    "caldataregistrazione"  // trigger for the calendar (button ID)
		    	
			});
	//]]> 
	</script>
	
	
	
	<fieldset  id="impostazioniRateizzazione">
		<legend><fmt:message key="form.registrazioni.impostazioni_rateizzazione" /></legend>
		<table>
		<tr>
			<td><fmt:message key="form.registrazioni.impostazioni_rateizzazione.conto_interessi" />:</td> 
			<td>
				<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="contoInteressiRat_id" />		
					<jsp:param name="propertyPath" value="contoInteressiRat" />				
					<jsp:param name="pathPropertyDescription" value="contoInteressiRat.descrizione" />
					<jsp:param name="pathPropertyCode" value="contoInteressiRat.id.codice" />
					<jsp:param name="autocompleterAjax" value="findConti.htm" />							
					<jsp:param name="titleKey" value="label.ricerca_conto" />
				</jsp:include>
			</td>
		</tr>
		
		<tr>
			<td style="vertical-align: text-top;"><fmt:message key="form.registrazioni.impostazioni_rateizzazione.tipo_ripartizione" />:</td> 
			<td style="vertical-align: text-top;">
			<ul>
			<c:set var="IMPOSTAZIONE_UTENTE_CONF_UTENTE_CONTAB_RAT_RIPARTIZIONE" scope="page"><%= WebConstants.CONF_UTENTE_CONTAB_RAT_RIPARTIZIONE %></c:set>
				<li style="list-style: none;">
					<spring-form:radiobutton id="tipo_ripartizione1" path="tipologiaRipartizioneRat"  value="1" onclick="saveUserPreference('${IMPOSTAZIONE_UTENTE_CONF_UTENTE_CONTAB_RAT_RIPARTIZIONE}','1')"/> 
					<label for="tipo_ripartizione1"><fmt:message key="form.registrazioni.impostazioni_rateizzazione.tipo_ripartizione.ordinata" /></label>
					<br />
					<ul>

					<c:forEach items="${registrazioniFilter.ordinamentoContiRat}" var="ci" varStatus="a">
						<li style="list-style: none;">
							<spring:bind path="ordinamentoContiRat[${a.index}].valore">
								<input type="text" id="dataInizioTransient${a.index}" name="${status.expression}" value="${status.value}" size="2" /> 
								 <b>${ci.chiave.descrizione}</b>							
							</spring:bind>	
						</li>							
					</c:forEach>

					</ul>
				</li>
				<li style="list-style: none;">
					<spring-form:radiobutton id="tipo_ripartizione2" path="tipologiaRipartizioneRat"  value="2" onclick="saveUserPreference('${IMPOSTAZIONE_UTENTE_CONF_UTENTE_CONTAB_RAT_RIPARTIZIONE}','1')"/> 
					<label for="tipo_ripartizione2"><fmt:message key="form.registrazioni.impostazioni_rateizzazione.tipo_ripartizione.omogenea" /></label>
				</li>
			</ul>
			</td>
		</tr>	
	</table>

</fieldset>
	
	
	<div id="functions">
<ul>
		<li><a href="javascript:simula();"><fmt:message key="button.simula" /></a></li>
		<li><a href="javascript:doHref('view.htm?codice=${codiceRegistrazione}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
<br/><br/>
<c:if test="${simulazione eq true}">
<%
String rateDisplay="display:none;";
%>
<div id="rate" class="jmesa" style="<%=rateDisplay %>">

	<table border="0" width="50%" cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
							<td width="2%"><fmt:message key="form.registrazioniimporti.nrRata"/></td>
							<td width="2%"><fmt:message key="form.registrazioniimporti.scadenza"/></td>
							<td width="2%" align="right"><fmt:message key="form.registrazioniimporti.totalerata"/></td>
							<td width="15%"><fmt:message key="form.registrazioniimporti.conti"/></td>
							<td width="5%" align="right"><fmt:message key="form.registrazioniimporti.importo"/></td>
							
							
				</tr>
			</thead>
			<tbody class="tbody" >
			<%int j=1;%>
			<c:set var="importoTot" value="0"/>
			
			<c:forEach var="rata_var" items="${simulaReg.listaRate}" varStatus="rataStatus">
					<c:set var="rataScritta" value="false"/>
							<c:set var="rowspan" value="${fn:length(rata_var.registrazioniImportiList)}" />
							
							<%int i=1;%>
							<c:forEach var="registrazioniImporti_var" items="${rata_var.registrazioniImportiList}" varStatus="vsi">
								<tr >
									<c:if test="${rataScritta == false}">
										<c:if test="${rowspan > 1}">
											<c:set var="valign" value="valign='middle'" />
										</c:if>
										<c:if test="${rowspan < 2}">
											<c:set var="valign" value="" />
										</c:if>
										<td class="<%=(j%2)==0?"odd":"even"%>" rowspan="${rowspan}"  valign="top">
											${rata_var.numeroRata}
										</td>
										<td class="<%=(j%2)==0?"odd":"even"%>" rowspan="${rowspan}" valign="top" >
											<fmt:formatDate pattern="dd/MM/yyyy" value="${registrazioniImporti_var.scadenza}" />
										</td>
										<td class="<%=(j%2)==0?"odd":"even"%>" rowspan="${rowspan}"  valign="top" align="right">
										<c:if test="${rataScritta == false}">										
											<fmt:formatNumber minFractionDigits="2" value="${rata_var.importoRata}" />
										</c:if>
										</td>																
									</c:if>		
										<td class="<%=(j%2)==0?"odd":"even"%>" ${valign} >${registrazioniImporti_var.conti.descrizione}</td>
										<td align="right"  class="<%=(j%2)==0?"odd":"even"%>" ${valign}><fmt:formatNumber minFractionDigits="2" value="${registrazioniImporti_var.importo}" /></td>

								</tr>
								<c:set var="rataScritta" value="true"/>
								<c:set var="importoTot" value="${importoTot + registrazioniImporti_var.importo}"/>
								
								<%i++;%>
							</c:forEach>
					<%j++;%>			
			</c:forEach>
			</tbody>
			<tfoot>
				<tr class="header">
					<td colspan="4" align="right"><b><fmt:message key="label.totalColumn" /></b></td>
					<td align="right"><fmt:formatNumber minFractionDigits="2" value="${importoTot}"/></td>
					
				</tr>
			</tfoot>
</table>

</div>
<script type='text/javascript'>
$("rate").appear();
</script>
</c:if>
	<script type='text/javascript'>
	var idTipiRate=document.getElementById("selectRate").value;
	if(idTipiRate==0){
		$("tipiRateizzazione").fade();
	}else{
		var checkEdit=document.getElementById("checkEdit");
		new Ajax.Request('<%=request.getContextPath()%>/ajax/findTipiRateizzazioni.htm', {
				  method: 'post',
				  parameters: {code: idTipiRate, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("tipiRateizzazione").appear();
					  var opts=response.split(",");
					  if(checkEdit.checked){
					if(document.getElementById("interessilegali").checked){
							$('dataInizioTR').appear();
							$('labelInteressiLegali').appear();
						}else{
							$('dataInizioTR').fade();
							  $('labelInteressiLegali').fade();
							  document.getElementById("dataInizioTransient").value='';
						}
					  }else{
						  if( opts[0]!= 'null'){
							  document.getElementById("nrrate").value=opts[0];
							}else{
								document.getElementById("nrrate").value='';
							}
						  if(opts[1]!= 'null'){
							  document.getElementById("ripRate").value=opts[1];
							}else{
								document.getElementById("ripRate").value='';
							}
						  if(opts[2]!= 'null'){
							  document.getElementById("freqRate").value=opts[2];
							}else{
								document.getElementById("freqRate").value='';
							}
						  if(opts[3]!= 'null'){
							  document.getElementById("scadenzaRate").selectedIndex=opts[3];
							}
						  if(opts[4]!= 'null'){
							  document.getElementById("interessi").value=opts[4];
							}else{
								document.getElementById("interessi").value='';
					    	}
					    	if(opts[5]== 'true'){
								  document.getElementById("interessilegali").checked=true;
								  $('dataInizioTR').appear();
								  
								  $('labelInteressiLegali').appear();
							}
							if(opts[5]== 'false'){
								  document.getElementById("interessilegali").checked=false;
								  $('dataInizioTR').fade();
								  $('labelInteressiLegali').fade();
								  document.getElementById("dataInizioTransient").value='';
							}
					  }
				    },
				  onFailure: function(){  }
				  });
		}
	changeValueEdit();
	function assegnaRateizzazioni(){
		var idTipiRate=document.getElementById("selectRate").value;
		if(idTipiRate==0){
			$("tipiRateizzazione").fade();
		}else{
		new Ajax.Request('<%=request.getContextPath()%>/ajax/findTipiRateizzazioni.htm', {
			  method: 'post',
			  parameters: {code: idTipiRate, limit: 12},
			  onSuccess: function(transport){
				  var response = transport.responseText;
				  $("tipiRateizzazione").appear();
				  var opts=response.split(",");
				  if( opts[0]!= 'null'){
					  document.getElementById("nrrate").value=opts[0];
					}else{
						document.getElementById("nrrate").value='';
					}
				  if(opts[1]!= 'null'){
					  document.getElementById("ripRate").value=opts[1];
					}else{
						document.getElementById("ripRate").value='';
					}
				  if(opts[2]!= 'null'){
					  document.getElementById("freqRate").value=opts[2];
					}else{
						document.getElementById("freqRate").value='';
					}
				  if(opts[3]!= 'null'){
					  document.getElementById("scadenzaRate").selectedIndex=opts[3];
					}
				  if(opts[4]!= 'null'){
					  document.getElementById("interessi").value=opts[4];
					}else{
						document.getElementById("interessi").value='';
					}
					
					if(opts[5]== 'true'){
					
					  document.getElementById("interessilegali").checked=true;
					  $('dataInizioTR').appear();
						 
					  $('labelInteressiLegali').appear();
					}
					if(opts[5]== 'false'){
						  document.getElementById("interessilegali").checked=false;
						  $('dataInizioTR').fade();
						  $('labelInteressiLegali').fade();
						  document.getElementById("dataInizioTransient").value='';
					}
				
			    },
			  onFailure: function(){  }
			  });
		}
	}

	function changeValueEdit(){
		var checkEdit=document.getElementById("checkEdit");
		if(checkEdit.checked){
			document.getElementById("nrrate").disabled=false;
			document.getElementById("ripRate").disabled=false;
			document.getElementById("freqRate").disabled=false;
			document.getElementById("scadenzaRate").disabled=false;
			document.getElementById("interessi").disabled=false;
			document.getElementById("interessilegali").disabled=false;
		}else{
			document.getElementById("nrrate").disabled=true;
			document.getElementById("ripRate").disabled=true;
			document.getElementById("freqRate").disabled=true;
			document.getElementById("scadenzaRate").disabled=true;
			document.getElementById("interessi").disabled=true;
			document.getElementById("interessilegali").disabled=true;
			assegnaRateizzazioni();
			}
	}

	function changeInteressiLegali(){
			if(document.getElementById("interessilegali").checked){
				$('dataInizioTR').appear();
                $('labelInteressiLegali').appear();
                $('interessi').value='';
                $('interessi').disabled=true;
			}else{
				$('dataInizioTR').fade();
				$('labelInteressiLegali').fade();
				$('interessi').disabled=false;
			}
		}

	function checkTipirateizzazioni(obj){
		var result=true;
		var value=leftTrim(obj.value);
		if(value.indexOf(";")>0){
			var stringa=new String();
			stringa=value.toString();
			var arrayValue=stringa.split(";");
			for(var i=0;i<arrayValue.length;i++){
				var valore=arrayValue[i];
				if(isNaN(valore)){
					alert("Il valore inserito non è un numero:\nInserito il valore '"+arrayValue[i]+"' nella stringa '"+value+"'");
					result=false;
				}
				if(valore.indexOf(" ")>=0){
					alert("Il valore inserito contiene degli spazi:\nInserito il valore '"+arrayValue[i]+"' nella stringa '"+value+"'");
					result=false;
				}
			}
		}else{
			if(isNaN(value)){
				alert("Il valore inserito non è un numero:\nIl valore errato è presente nella stringa '"+value+"'");
				result=false;
			}
		}
		return result;
	}
	function leftTrim(sString){
		while (sString.substring(0,1) == ' '){
			sString = sString.substring(1, sString.length);
		}
		return sString;
	}

	function simula(){
		var checkEdit=document.getElementById("checkEdit");
		if(checkEdit.checked){
			var nrRateCheck=checkNrRate(document.getElementById("nrrate"));
			if(document.getElementById("nrrate").value==""){
				document.getElementById("nrrate").value="1";
				}
			var rateCheck=checkTipirateizzazioni(document.getElementById("ripRate"));
			var fraqRateCheck=checkTipirateizzazioni(document.getElementById("freqRate"));
			var interessiCheck= checkTipirateizzazioni(document.getElementById("interessi"));
			if(rateCheck && fraqRateCheck && interessiCheck && nrRateCheck){
				if( document.getElementById("interessilegali").checked){
					 document.getElementById("interessilegali").checked=true;
				}else{
					 document.getElementById("interessilegali").checked=false;
				}
				doSubmit('simulaRateizzazioni.htm?codice=${codiceRegistrazione}&flagInteressi='+document.getElementById("interessilegali").checked,'',document.inviodati);
			}
		}else{
			if( document.getElementById("interessilegali").checked){
				 document.getElementById("interessilegali").checked=true;
			}else{
				 document.getElementById("interessilegali").checked=false;
			}
			doSubmit('simulaRateizzazioni.htm?codice=${codiceRegistrazione}&flagInteressi='+document.getElementById("interessilegali").checked,'',document.inviodati);
		}
	}
	function salva(){
		var checkEdit=document.getElementById("checkEdit");
		if(checkEdit.checked){
			var nrRateCheck=checkNrRate(document.getElementById("nrrate"));
			if(document.getElementById("nrrate").value==""){
				document.getElementById("nrrate").value="1";
				}
			var rateCheck=checkTipirateizzazioni(document.getElementById("ripRate"));
			var fraqRateCheck=checkTipirateizzazioni(document.getElementById("freqRate"));
			var interessiCheck= checkTipirateizzazioni(document.getElementById("interessi"));
			if(rateCheck && fraqRateCheck && interessiCheck && nrRateCheck){
				
				if( document.getElementById("interessilegali").checked){
					 document.getElementById("interessilegali").checked=true;
				}else{
					 document.getElementById("interessilegali").checked=false;
				}
				doSubmit('updateRateizzazioni.htm?codice=${codiceRegistrazione}&flagInteressi='+document.getElementById("interessilegali").checked,'',document.inviodati);
			}
		}else{
			if( document.getElementById("interessilegali").checked){
				 document.getElementById("interessilegali").checked=true;
			}else{
				 document.getElementById("interessilegali").checked=false;
			}
				doSubmit('updateRateizzazioni.htm?codice=${codiceRegistrazione}&flagInteressi='+document.getElementById("interessilegali").checked,'',document.inviodati);
			}
		}
	function checkNrRate(obj){
		var rateCheck=true;
		if(obj.value.indexOf(".")>=0){
			alert("Il valore inserito non è un numero intero");
			obj.value="";
			rateCheck=false;
		}
		if(obj.value.indexOf(",")>=0){
			alert("Il valore inserito non è un numero intero");
			obj.value="";
			rateCheck=false;
		}
		rateCheck= checkNumberValue(obj);
		return rateCheck;
	}

	function updateValue(){
		if(document.getElementById("nrrate").value==1){
			document.getElementById("ripRate").value=100;
			document.getElementById("freqRate").value=0;
		}
	}
	</script>	
	
</spring-form:form>
</div>
<script type="text/javascript">
var rateId=document.getElementById("selectRate").value;
</script>
<div id="functions">
<ul>
		<c:if test="${simulazione eq true}">
			<li><a href="javascript:salva()"><fmt:message key="button.update" /></a></li>
		</c:if>	
</ul>
</div>
</body>
</html>
