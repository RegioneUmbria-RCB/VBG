<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.configurazione_parametri_mail" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.configurazione_parametri_mail" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../mailconfig/list" />
		</jsp:include>
		<div id="subcontent">
		
		<%-- 
		<div class="titoloSezione">Ereditati da TT </div>
		<div class="jmesa">
		<c:forEach items="${mailEreditatiTTConfigHelpers}" var="mailConfigHelper" varStatus="a">  
		<c:if test="${not empty mailConfigHelper.mailConfigs}">
		<table border="0" cellpadding="0" cellspacing="0" class="table">
			<tr class="header" >
				<td colspan="5">
				    <fmt:message key="label.comune" />:
					
					<c:if test="${mailConfigHelper.codiceComune eq null}">Tutti</c:if> 
					<c:if test="${mailConfigHelper.codiceComune ne ''}">${mailConfigHelper.comune}</c:if> 
				</td>
			</tr>
					
			<tr>
				<td width="100%" colspan="4">
				<table  border="0" cellpadding="0" cellspacing="0" class="table" width="100%">
					<tr class="header" >
					    <td width="35%"><fmt:message key="label.nome" /></td>
					    <td width="35%"><fmt:message key="label.email_mittente" /></td>
					    <td width="10%"><fmt:message key="label.mailserver" /></td>
						<td width="10%"><fmt:message key="label.principale" /></td>
						<td width="10%"><fmt:message key="label.disabilitato" /></td>
						
	            	</tr>
	            	<%int i=1;%>
					<c:forEach items="${mailConfigHelper.mailConfigs}" var="current" varStatus="b">
					<tr class= "<%=(i%2)==0?"odd":"even"%>">
						<c:if test="${current.flagPrincipale eq true}"><td><b>${current.descrizione} (Principale)</b></td></c:if>
						<c:if test="${current.flagPrincipale eq false}"><td>${current.descrizione}</td></c:if>
						<c:if test="${current.flagPrincipale eq true}"><td><b>${current.senderaddress}</b></td></c:if>
						<c:if test="${current.flagPrincipale eq false}"><td>${current.senderaddress}</td></c:if>
						<c:if test="${current.flagPrincipale eq true}"><td><b>${current.mailserver}</b></td></c:if>
						<c:if test="${current.flagPrincipale eq false}"><td>${current.mailserver}</td></c:if>
						<td>
							<c:if test="${current.flagPrincipale eq true}"><fmt:message key="label.si" /></c:if>
							<c:if test="${current.flagPrincipale eq false}"><fmt:message key="label.no" /></c:if>
						</td>
						<td>
							<c:if test="${current.flagDisabilitato eq true}"><fmt:message key="label.si" /></c:if>
							<c:if test="${current.flagDisabilitato eq false}"><fmt:message key="label.no" /></c:if>
						</td>
						
						</td>
					</tr>
					<%i++;%>								
				    </c:forEach>	
				</table>
				</td>
			</tr>
  		</table>
  		</c:if>
  		</c:forEach>	
		</div>
		
		--%>
		
		<div class="titoloSezione">${sw.descrizione}</div>
		<div class="jmesa">
		<c:forEach items="${mailConfigHelpers}" var="mailConfigHelper" varStatus="a">  
		<c:if test="${not empty mailConfigHelper.mailConfigs}">
		<table border="0" cellpadding="2" cellspacing="0" class="table">
			<tr class="header" >
				<td colspan="5">
				    <fmt:message key="label.comune" />:
					<%-- ${mailConfigHelper.software} - --%>
					<c:if test="${mailConfigHelper.codiceComune eq null}">Tutti</c:if> 
					<c:if test="${mailConfigHelper.codiceComune ne ''}">${mailConfigHelper.comune}</c:if> 
				</td>
			</tr>
					
			<tr>
				<td width="100%" colspan="4">
				<table  border="0" cellpadding="0" cellspacing="0" class="table" width="100%">
					<tr class="header" >
					    <td width="35%"><fmt:message key="label.nome" /></td>
					    <td width="35"><fmt:message key="label.email_mittente" /></td>
					    <td width="35"><fmt:message key="label.mailserver" /></td>
						<td width="10%"><fmt:message key="label.principale" /></td>
						<td width="10%"><fmt:message key="label.disabilitato" /></td>
						<td width="10%"><fmt:message key="label.edit.record" /></td>
	            	</tr>
	            	<%int i=1;%>
					<c:forEach items="${mailConfigHelper.mailConfigs}" var="current" varStatus="b">
					<tr class= "<%=(i%2)==0?"odd":"even"%>">
						<c:if test="${current.flagPrincipale eq true}"><td><b>${current.descrizione} (Principale)</b></td></c:if>
						<c:if test="${current.flagPrincipale eq false}"><td>${current.descrizione}</td></c:if>
						<c:if test="${current.flagPrincipale eq true}"><td><b>${current.senderaddress}</b></td></c:if>
						<c:if test="${current.flagPrincipale eq false}"><td>${current.senderaddress}</td></c:if>
						<c:if test="${current.flagPrincipale eq true}"><td><b>${current.mailserver}</b></td></c:if>
						<c:if test="${current.flagPrincipale eq false}"><td>${current.mailserver}</td></c:if>
						<td>
							<c:if test="${current.flagPrincipale eq true}"><fmt:message key="label.si" /></c:if>
							<c:if test="${current.flagPrincipale eq false}"><fmt:message key="label.no" /></c:if>
						</td>
						<td>
							<c:if test="${current.flagDisabilitato eq true}"><fmt:message key="label.si" /></c:if>
							<c:if test="${current.flagDisabilitato eq false}"><fmt:message key="label.no" /></c:if>
						</td>
						<td>
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../mailconfig/view.htm?codice=${current.id.codice}','')"	title="<fmt:message key="label.edit.record" />">
							<label><fmt:message key="label.edit.record" /></label>
						</a>
						</td>	
					</tr>
					<%i++;%>								
				    </c:forEach>	
					
				</table>
				</td>
			</tr>
  		</table>
  		</c:if>
  		</c:forEach>	
		</div>



</div>

<div id="functions">
<ul> 
     
	<li><a href="javascript:historySet('${_urlback}','../mailconfig/create.htm','');"><fmt:message key="button.new" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>