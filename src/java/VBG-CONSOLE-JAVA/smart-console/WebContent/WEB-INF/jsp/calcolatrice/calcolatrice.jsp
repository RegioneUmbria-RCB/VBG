<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="form.calcolatrice.title.view" />
	</title>
</head>
<body>
<span class="titoloPagina">
<fmt:message key="form.calcolatrice.title.view" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
<fieldset>
<legend><fmt:message key="form.calcolatrice.title"></fmt:message></legend>
<spring-form:form commandName="calcolatrice" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="calcolatrice" />
    </jsp:include>
 
	<table>
		<tr>
			<td><fmt:message key="form.calcolatrice.importo" /></td>
			<td colspan="3"><spring-form:input id="importo_id" path="importo" cssStyle="text-align:right;" size="10" maxlength="10" onblur="checkNumberValue(this);"/>
			<init:help idHelp="helplegali" textKey="form.calcolatrice.importo.help"/>
			<spring-form:errors path="importo" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.calcolatrice.dataInizio" />:</td>
			<td>
	    		<spring-form:input id="dataInizio_id" path="dataInizio" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
	    		<init:calendar idImage="caldataInizio" idInput="dataInizio_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
				&nbsp;&nbsp;
	    	</td>
			<td><fmt:message key="form.calcolatrice.dataFine" />:</td>
			<td>
	    		<spring-form:input id="dataFine_id" path="dataFine" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
	    		<init:calendar idImage="caldataFine" idInput="dataFine_id" imagePath="/images/cal.gif" textKey="label.calendar"/> 
				<spring-form:errors	path="dataInizio" cssClass="error" delimiter=";"/>
	    	</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.tiporateizzazione.tabinteressilegali" />:
			<img src="<%=request.getContextPath() %>/images/search.gif" onclick="dettaglioTabInteressi(this);" />	
			</td>
		</tr>
		<tr>
		<td colspan="4">		
			<span id="interessi_dettaglio" style="display: none; text-align: left;"></span>
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
</spring-form:form>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('calculate.htm','',document.inviodati)"><fmt:message key="button.calculate" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>		
</ul>
</div>
</fieldset>
<c:if test="${risultato == true}">
<fieldset>
<c:set var="totInteresse" value="0.00"></c:set>
<legend><fmt:message key="form.calcolatrice.risultato"></fmt:message></legend>
<div class="jmesa" >
		<table border="0" cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td><fmt:message key="form.calcolatrice.dataInizio" /></td>
					<td><fmt:message key="form.calcolatrice.dataFine" /></td>
					<td align="right"><fmt:message key="form.calcolatrice.tassoPercentuale" /></td>
					<td align="right"><fmt:message key="form.calcolatrice.giorni" /></td>
					<td align="right"><fmt:message key="form.calcolatrice.importo" /></td>					
					<td align="right"><fmt:message key="form.calcolatrice.interessi" /></td>
				</tr>
			</thead>
			<tbody class="tbody" >
			<%int i=1;%>
			<c:forEach var="interessi_var" items="${calcoloInteressiLegaliList}" >
			<tr class="<%=(i%2)==0?"odd":"even"%>"  >
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${interessi_var.dataInizio}"/></td>
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${interessi_var.dataFine}"/></td>
				<td align="right">${interessi_var.tassoPercentuale}%</td>
				<td align="right">${interessi_var.giorni}</td>
				<td align="right"><fmt:formatNumber pattern="###,##0.00" maxFractionDigits="2" value="${interessi_var.importo}"></fmt:formatNumber>€</td>
				<td align="right"><fmt:formatNumber pattern="###,##0.00" maxFractionDigits="2" value="${interessi_var.interessi}"></fmt:formatNumber>€</td>
			<c:set var="totInteresse" value="${totInteresse + interessi_var.interessi}"></c:set>
			</tr>
			<%i++;%>
			</c:forEach>
			<tr class="header">
			<td colspan="5" align="right">TOTALI</td><td style="text-align: right;"><fmt:formatNumber pattern="###,##0.00" minFractionDigits="2" value="${totInteresse}"></fmt:formatNumber>€</td>
			</tr>
			</tbody>
		</table>
</div>
<br/>
<table>
		<tr>
			<td><span class="parametri"><fmt:message key="form.calcolatrice.totaleinteresse"></fmt:message> </span></td>
			<td align="right"><span class="parametri"><fmt:formatNumber pattern="###,##0.00" minFractionDigits="2" value="${totInteresse}"></fmt:formatNumber>€</span></td>
		</tr>
		<tr>
			<td><span class="parametri"><fmt:message key="form.calcolatrice.totaleinteressecapitale"></fmt:message></span></td>
			<td align="right"><span class="parametri"><fmt:formatNumber pattern="###,##0.00" minFractionDigits="2" value="${calcolatrice.importo + totInteresse}"></fmt:formatNumber>€</span></td>
		</tr>
</table>
</fieldset>
</c:if>
</div>
</body>
</html>
