<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${command.mercatidLetture.id.codice==null}">
			<fmt:message key="form.mercatidLetture.title.create" />
		</c:if> 
		<c:if test="${command.mercatidLetture.id.codice!=null}">
			<fmt:message key="form.mercatidLetture.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.mercatidLetture.reg.title.create" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatidletture/createRegistrazioni" />
	</jsp:include>
<div id="subcontent">
	<span class="parametri"><fmt:message key="form.mercatidLetture.mercato" />:<label>${mercato.descrizione}</label></span>
    <span class="parametri"><fmt:message key="form.mercatidLetture.mercatoUso" />:<label>${uso.descrizione}</label></span>
    <spring-form:form commandName="command" name="inviodatiLetture" modelAttribute="command">
        <jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="command" />
	</jsp:include>
    <fieldset><legend><fmt:message key="form.mercatidLetture.letture"></fmt:message></legend>
	<!-- START Tabella principale --> 
    <div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="5%" ><fmt:message key="form.mercatidLetture.registrazioniconatbili" /></td>
				<td width="5%" ><fmt:message key="form.mercatidLetture.posteggio" /></td>
				<td width="10%"><fmt:message key="form.mercatidLetture.tipicontatore" /> </td>
				<td width="10%" ><fmt:message key="form.mercatidLetture.datalettura" /></td>
                <td width="10%" ><fmt:message key="form.mercatidLetture.dalladata" /></td>
                <td width="10%"><fmt:message key="form.mercatidLetture.alladata" /></td>
                <td width="10%" ><fmt:message key="form.mercatidLetture.presunta" /></td>
                <td width="10%" style="text-align: right;" ><fmt:message key="form.mercatidLetture.letturainiziale" /></td>
                <td width="10%" style="text-align: right;" ><fmt:message key="form.mercatidLetture.letturafinale" /></td>
                <td width="10%" style="text-align: right;" ><fmt:message key="form.mercatidLetture.consumo" /></td>
                <td width="10%" style="text-align: right;" ><fmt:message key="form.mercatidLetture.importo" /></td>
    		</tr>
		</thead>
		<tbody class="tbody">
		<%int i=1;%>
		
			<c:forEach items="${command.mercatidLettureList}" var="letture_var" varStatus="status" >
			
			<tr class= "<%=(i%2)==0?"odd":"even"%>">
				<spring:bind path="mercatidLettureList[${status.index}].creaRegistrazioni" >
                   <td><spring-form:checkbox path="${status.expression}" value="${letture_var.creaRegistrazioni}" /></td>
                </spring:bind>
				<td width="5%">${letture_var.posteggio.codiceposteggio}</td>
				<td width="5%">${letture_var.tipiContatore.descrizione}</td>
                <td>
                	<fmt:formatDate value="${letture_var.dataLettura}" pattern="dd/MM/yyyy"/>
             	</td>
            	<td><fmt:formatDate value="${letture_var.dataInizio}" pattern="dd/MM/yyyy"/></td>
                <td><fmt:formatDate value="${letture_var.dataFine}" pattern="dd/MM/yyyy"/></td>
               	<td>
               	<c:set var="presunta" value="<%=WebConstants.MERCATIDLETTURE_PRESUNTA_PRESUNTA%>"></c:set>
               	<c:set var="effetiva" value="<%=WebConstants.MERCATIDLETTURE_PRESUNTA_EFFETTIVA%>"></c:set>
               	<c:if test="${letture_var.presunta == presunta}">
               		<fmt:message key="form.mercatidLetture.label.presunta" />
               	</c:if>
               	<c:if test="${letture_var.presunta == effetiva}">
               		<fmt:message key="form.mercatidLetture.label.effettiva" />
               	</c:if>
               	</td>     
			    <td align="right"><fmt:formatNumber minFractionDigits="5" value="${letture_var.letturaIniziale}"/></td>
                <td align="right"><fmt:formatNumber minFractionDigits="5" value="${letture_var.letturaFinale}"/></td>
                <td align="right"><fmt:formatNumber minFractionDigits="5" value="${letture_var.consumo}"/></td>
                <td align="right"><fmt:formatNumber minFractionDigits="2" value="${letture_var.importo}"/></td>           
			   </tr>
			   <%i++; %>
			   </c:forEach>
		
	     </tbody>
	</table>
</div>
</fieldset>	
<%
String  rateizzazione="display:none;";
%>
<c:if test="${not empty command.mercatidLettureList}">
<fieldset><legend><fmt:message key="form.mercatidLetture.registrazioni"></fmt:message></legend>
<div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="5%" ><fmt:message key="form.mercatidLetture.registrazionicausale" /></td>
				<td width="5%" ><fmt:message key="form.mercatidLetture.dataregistrazione" /></td>
				<td width="10%"><fmt:message key="form.mercatidLetture.tiporateizzazione" /> </td>
				<td width="10%"><fmt:message key="form.mercatidLetture.conto" /> </td>
			</tr>
		</thead>
		<tbody class="tbody">
			<tr class= "even">
					<td>
						<spring-form:select id="regCausali" path="registrazioni.registrazioniCausali.id.codice" >
							<spring-form:options items="${regCausaliList}" itemLabel="descrizione" itemValue="id.codice"/>
					    </spring-form:select>
					</td>
	                <td>
		                <spring-form:input tabindex="4" id="dataregistrazione_id" path="registrazioni.dataRegistrazione" size="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="calDataReg" idInput="dataregistrazione_id" textKey="label.calendar"/>
						<br />
						<spring-form:errors path="registrazioni.dataRegistrazione" cssClass="error"/> 
	                </td>
	                <td>
	                	<spring-form:select tabindex="2" id="selectRate" path="oneritipirateizzazione.id.codice" onchange="assegnaRateizzazioni();">
	                		<spring-form:option value=""><fmt:message key="form.mercatidLetture.tiporateizzazione.nessunarate" /></spring-form:option>
							<spring-form:options items="${rateList}" itemLabel="descrizione" itemValue="id.codice"/>
					    </spring-form:select>
					    	<fieldset  id="tipiRateizzazione" style="<%=rateizzazione %>">
							<legend><fmt:message key="form.registrazioni.tiporateizzazione" /></legend>
								<table>
								<tr>
									<td><fmt:message key="form.registrazioni.tiporateizzazione.numerorate" />:</td> 
									<td >
									<label id="nrrate"></label>
									</td>
								</tr>
								<tr>
									<td><fmt:message key="form.registrazioni.tiporateizzazione.ripartizionerate" />:</td> 
									<td> <label id="ripRate"></label>
									<init:help idHelp="help3" textKey="form.registrazionimercato.rangeRateizzazioni.ripartizionerate.help"/>
									</td>
								</tr>
								<tr>
									<td><fmt:message key="form.registrazioni.tiporateizzazione.frequenzarate" />:</td> 
									<td >
									<label id="freqRate"></label>
									<init:help idHelp="help4" textKey="form.registrazionimercato.rangeRateizzazioni.frequenzarate.help"/>
									</td>
								</tr>
								<tr>
									<td><fmt:message key="form.registrazioni.tiporateizzazione.scadenzarate" />:</td> 
									<td >
									<label id="scadenzaRate"></label>
									</td>
								</tr>
								<tr>
									<td><fmt:message key="form.registrazioni.tiporateizzazione.interessi" />:</td> 
									<td>
									<label  id="interessi"></label>
									</td>
								</tr>
							    </table>
							</fieldset>
	                </td>
	               <td>
	                	<spring-form:input id="conti_id" path="conti.descrizioneConto" cssStyle="padding-left:15px;" cssClass="searchbox" onchange="checkValue(this,'conti_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/><br />
						<init:autocompleter methodAjax="findConti.htm" idHidden="conti_hidden" idInput="conti_id" inputTitleKey="label.ricerca_conto"/>
						<spring-form:errors path="conti" cssClass="error"/> 
						<spring-form:hidden id="conti_hidden" path="conti.id.codice"  />
	                </td>
			</tr>
		</tbody>
	</table>
</div>
</fieldset>
</c:if>
</spring-form:form>
<c:if test="${not empty command.mercatidLettureList}">
	<script type='text/javascript'>
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
				  document.getElementById("nrrate").innerHTML=opts[0];
				}
			  if(opts[1]!= 'null'){
				  document.getElementById("ripRate").innerHTML=opts[1];
				}
			  if(opts[2]!= 'null'){
				  document.getElementById("freqRate").innerHTML=opts[2];
				}
			  if(opts[3]!= 'null'){
				  document.getElementById("scadenzaRate").innerHTML=opts[3];
				}
			  if(opts[4]!= 'null'){
				  document.getElementById("interessi").innerHTML=opts[4];
				}
		    },
		  onFailure: function(){  }
		  });
	}
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
					  document.getElementById("nrrate").innerHTML=opts[0];
					}
				  if(opts[1]!= 'null'){
					  document.getElementById("ripRate").innerHTML=opts[1];
					}
				  if(opts[2]!= 'null'){
					  document.getElementById("freqRate").innerHTML=opts[2];
					}
				  if(opts[3]!= 'null'){
					  document.getElementById("scadenzaRate").innerHTML=opts[3];
					}
				  if(opts[4]!= 'null'){
					  document.getElementById("interessi").innerHTML=opts[4];
					}
			    },
			  onFailure: function(){  }
			  });
		}
	}
</script>
</c:if>
</div>
<div id="functions">
	<ul>
	<c:if test="${not empty command.mercatidLettureList}">
		<li><a href="javascript:doSubmit('insertRegistrazioni.htm?mercati.id.codice=${mercato.id.codice}&mercatoUso=${uso.id.codice}','',document.inviodatiLetture)"><fmt:message key="button.update" /></a></li>		
	</c:if>
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>		
	</ul>
</div>
</body>
</html>