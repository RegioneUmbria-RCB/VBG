<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
			<fmt:message key="label.dettaglio_autorizzazione" />		
	</title>
</head>
<body>
	<span class="titoloPagina">		
		<fmt:message key="label.dettaglio_autorizzazione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../autorizzazioni/viewOperazione" />			
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="helper" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="domain" />
		    </jsp:include>
		    <div class="table" id="table_id">	
		    <c:set var="autorizdata"><fmt:formatDate value="${helper.autorizzazione.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></c:set>
		    <!-- Setto il messaggio di intestazione -->
		   		<table width="70%" border="0" cellpadding="5">
		   			<tr class="head">		   			
		   				<td colspan="2" style="font-size: large;">${helper.autorizzazione.tipologiaregistro.trDescrizione} n. 
		   					<b>${helper.autorizzazione.autoriznumero}</b> del <b>${autorizdata}</b></td>	
		   				<td rowspan="4" align="center" style="font-size:medium;border-style: solid;" ><fmt:message key="label.autorizzazioni.rimanenti" /><br />
		   					<span style="font-size:x-large;">${helper.numeroAccessiRimanenti}</span>
		   					</td>
		   			</tr>		   			
		   			<tr>
		   				<td>&nbsp;</td>
		   				<td>&nbsp;</td>	  				
		   			</tr>
		   			<tr>		   			
		   				<td style="font-size: large; font-weight: bold;">		
		   					<span class="header_dato_etichetta"><fmt:message key="label.autorizzazioni.validaDal"/></span>
		   				</td>
		   				<td style="font-size: large; font-weight: bold;">
		   					<span class="header_dato_etichetta"><fmt:message key="label.autorizzazioni.validaAl"/></span>
		   				</td>		   				
		   			</tr>
		   			<tr>
		   				<td style="font-size: large; font-weight: bold;"><fmt:formatDate value="${helper.autorizzazione.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
		   				<td style="font-size: large; font-weight: bold;"><fmt:formatDate value="${helper.autorizzazione.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />
			   				<c:if test="${isProrogata eq true}">
			   					<span style="color: red; font-weight: bold">* prorogata</span>	
			   				</c:if>
		   				</td>
		   			</tr>		   			
		   		</table>		   		
		    </div>		    
		    <div class="clear"></div>
		    <div class="header_dato" style="font-size:1.4em;  padding: 2px; margin-top: 40px;">
		    	<span class="header_dato_etichetta"><fmt:message key="label.tab_operazioni"/></span>
		    </div>
		    <div class="jmesa">	    
		    
				<table border="0" cellpadding="2" cellspacing="0" class="table" width="100%">
					<thead>
						<tr class="header">
							<td><fmt:message key="autorizzazioni.operazioni.tipo"/></td>
							<td><fmt:message key="autorizzazioni.operazioni.data"/></td>
							<td><fmt:message key="autorizzazioni.operazioni.numero"/></td>
							<td><fmt:message key="autorizzazioni.operazioni.protocollo"/></td>
							<td><fmt:message key="autorizzazioni.operazioni.stato"/></td>
							<td><fmt:message key="autorizzazioni.operazioni.dataautop"/></td>
							<td><fmt:message key="autorizzazioni.operazioni.istanza"/></td>							
						</tr>
					</thead>
					<tbody >
					<%
					    int j=1;
					%>					
						<c:forEach items="${helper.autorizzazioniAccessiOperazioniHelper}" var="autaccop">
							<tr class="<%=(j%2)==0?"odd":"even"%>">
								<td>${autaccop.tipo }</td>
								<td><fmt:formatDate value="${autaccop.istanza.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />  </td>
								<td>${autaccop.istanza.numeroistanza}</td>
								<td><c:out value="${autaccop.istanza.transientProtocolloNProtocollo}" default="-" /></td>
								<td>${autaccop.stato }</td>
								<td><fmt:formatDate value="${autaccop.istanza.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />  </td>  
								<td><a class="dettaglioColumn"
									href="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${autaccop.istanza.id.codice}','')"></a>
								</td>						
							</tr>
						<%
						    j++;
						%>
						</c:forEach>				
					</tbody>			
				</table>
			</div>
			
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>