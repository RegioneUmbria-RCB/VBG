<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<fmt:message key="ruoli.label.configurazioniprotocollo" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="ruoli.label.configurazioniprotocollo" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	
<h3>${ruolo.ruolo }</h3>
	
<form action="configurazioniUpdate.htm" name="inviodati">
	<input type="hidden" name="codice" value="${ruolo.id.codice}"/>
	<c:forEach items="${helpers}" var="helper">
		<br class="clear"/>
		<fieldset>
			
			<legend style="font-weight: bolder; font-size: 1.2em;">
			<c:choose>			
			<c:when test="${not empty helper.comune.comune }">			
				${helper.comune.comune}
			</c:when>
			<c:otherwise>
				Tutti
			</c:otherwise>
			</c:choose>	
			</legend>
			<table style="margin: 10px;">
				<c:forEach items="${helper.cvb}" var="cvb" varStatus="a">
					<tr>
						<td>
							${cvb.chiave.descrizione}
						</td>
						<td>
							<input type="hidden" name="rif" value="${helper.comune.codicecomune}_${cvb.chiave.codice}" />
							<input  type="text" value="${cvb.valore}" name="${helper.comune.codicecomune}_${cvb.chiave.codice}"  />
						</td>
					</tr>
				</c:forEach>
			</table>
		</fieldset>
	</c:forEach>
</form>	
</div>

<br class="clear"/>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('saveConfigruazioniProtocollo.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	<li><a href="javascript:doHref('view.htm?codice=${ruolo.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
