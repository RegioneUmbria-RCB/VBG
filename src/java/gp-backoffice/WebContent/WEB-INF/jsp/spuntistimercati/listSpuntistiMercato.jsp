<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="manifestazioni.label.lista_spuntisti.title" />
	</title>
</head>
<body>
    
	
	<span class="titoloPagina">
		<fmt:message key="manifestazioni.label.lista_spuntisti.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../mercati/view" />	    	
		</jsp:include>	
		
		
		<div class="header_dato">
			<span class="header_dato_etichetta"><fmt:message key="label.mercato" />:</span>
			<span class="header_dato_valore">
			<div>${mercati.descrizione}</div>
			</span>
		</div>
		
		<br class="clear" />
		
		
		<div class="jmesa">
		<table border="1" cellpadding="2" cellspacing="0" class="table">
     	    <%int i=1;%>
			<tr class="header" >
				<td><fmt:message key="label.giorno" /></td>
				<td colspan="4" ><fmt:message key="label.azioni" /></td>
			</tr>	
			<c:forEach items="${mercatiUsos}" var="var" varStatus="b">	
			<tr class= "<%=(i%2)==0?"odd":"even"%>">
				<td width="50%">${var.descrizione}</td>
				<td>
					<a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../mercati/viewSpuntistiMercato.htm?codiceMercato=${var.mercati.id.codice}&codiceuso=${var.id.codice}', '')" title="<fmt:message key="label.edit.record" /> ${var.id.codice}">
						<label><fmt:message key="label.edit.record.image" /></label>
					</a>
				</td>
			</tr>
			<%i++;%>								
			</c:forEach>
		</table>
		</div>
		
		
		
		
		<script>
		  
			/* jQuery( function() {
			  jQuery( "#tabs" ).tabs();
		  	} ); */
		
  		</script>
		<%-- 
		<div id="tabs">
		  <ul>
		  <c:forEach items="${listSpuntistiMercati}" var="current" varStatus="a1">
		    <li><a href="#tabs-${a1.index}"><b>${current.chiave}</b></a></li>
		  </c:forEach>
		  </ul>
		  <c:forEach items="${listSpuntistiMercati}" var="current" varStatus="a2">
		  <div id="tabs-${a2.index}">
		  	<div class="jmesa">
			<table border="1" cellpadding="2" cellspacing="0" class="table">
		  	<%int j=1;%>
		  	
				<tr class="header" >
				    <td width="20%"><fmt:message key="label.spuntista"/></td>
				    <td width="8%"><fmt:message key="label.autorizzazione"/></td>
				    <td width="8%"><fmt:message key="label.data"/></td>
				    <td width="10%"><fmt:message key="label.registro"/></td>
				    <td width="10%"><fmt:message key="label.comune"/></td>
				    <td width="10%"><fmt:message key="label.data_registrazione" /></td>
					<td width="10%"><fmt:message key="label.attivo" /></td>
					<td width="10%"><fmt:message key="label.data_disattivazione" /></td>
					
            	</tr>
				<c:forEach items="${current.valore}" var="var" varStatus="b1">	
				    <c:set value="" var="styleDisattivati"></c:set>
				    <c:if test="${!var.flgAttivo}">
				    	<c:set value="background-color: red; color: white;" var="styleDisattivati"></c:set>
				    </c:if>
				    <tr style="${styleDisattivati}" class= "<%=(j%2)==0?"odd":"even"%>">
				    <td>${var.autorizzazioni.anagrafe.descrizioneRichiedente}</td>
					<td>${var.autorizzazioni.autoriznumero}</td>
					<td><fmt:formatDate value="${var.autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td>${var.autorizzazioni.tipologiaregistro.trDescrizione}</td>
					<td>${var.autorizzazioni.autorizcomune.descrizioneEstesa}</td>
					<td><fmt:formatDate value="${var.dataRegistrazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					<td>
						<c:if test="${var.flgAttivo}"><fmt:message key="label.si" /></c:if> 
						<c:if test="${!var.flgAttivo}"><fmt:message key="label.no" /></c:if> 
					</td>
					<td>
						<c:if test="${var.dataDisattivazione!=null}"><fmt:formatDate value="${var.dataDisattivazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if> 
						<c:if test="${var.dataDisattivazione==null}"> - </c:if> 
					</td>
					
				</tr>
				<%j++;%>								
				</c:forEach>
			</table>
			</div>
		  </div>
		  </c:forEach>
		</div>
	</div>
	--%>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>