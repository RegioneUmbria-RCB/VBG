<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_parametri_protocollo" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_parametri_protocollo" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    
     <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../amministrazioni/listparametriprotocollo" />
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
       		<div class="parametro"> 
	        	<div>${amministrazioni.amministrazione}</div>
	        </div>
	    </div>
	<br /><br /><br />
	<c:forEach items="${amministrProtocolloHelpers}" var="amministrProtocolloHelper">
	
		<% 
			String displayParametriProt = "";
			String styleParametriProt = "sezioneDatiMeno";
		%>
	<fieldset><legend>
	<a
			class="<%=styleParametriProt%>"
			id="id_link_parametri_prot${amministrProtocolloHelper.comune}"
			href="javascript:showHidePanelBase('id_parametri_prot_table${amministrProtocolloHelper.comune}', 'id_link_parametri_prot${amministrProtocolloHelper.comune}', ' ', '${pageContext.request.contextPath}/images/','div',false);"
			title="<fmt:message key="label.mostra_nasconde_sezione" /> ${amministrProtocolloHelper.comune}">
			<label for="id_link_parametri_prot${amministrProtocolloHelper.comune}">${amministrProtocolloHelper.comune}</label> 
	</a>
	</legend>
<div class="jmesa" style="<%=displayParametriProt%>" id="id_parametri_prot_table${amministrProtocolloHelper.comune}">
		<table border="0" cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="25%"><fmt:message key="label.descrizione_software" /></td>
				<td width="25%"><fmt:message key="label.protUo" /></td>
				<td width="25%"><fmt:message key="amministrazioni.label.protRuolo" /></td>
				<td width="60px;"><fmt:message key="label.azioni" /></td>
            </tr>
		</thead>
		<tbody class="tbody">
		<%int j=1;%>
		<c:forEach items="${amministrProtocolloHelper.amministrProtocollos}" var="amministrProtocollo">
			<tr class="<%=(j%2)==0?"odd":"even"%>">
			    <td>${amministrProtocollo.software.descrizione}</td>
				<td>${amministrProtocollo.protUo}</td>
				<td>${amministrProtocollo.protRuolo}</td>
				<td>					
					<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../amministrazioni/viewParametriProtocollo.htm?codice=${amministrProtocollo.id.codice}','')" title="<fmt:message key="label.azioni" /> ">
							<label><fmt:message key="label.azioni" /></label>
					</a>
					<%-- 	
					<a class="eliminaRiga" href="javascript:doHref('deleteParametriProtocollo.htm?codice=${amministrProtocollo.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.azioni" /> ">
							<label><fmt:message key="label.elimina.image" /></label>
					</a> 				
					 --%>
				</td>
			</tr>
			<%j++; %>
		</c:forEach>		
		</tbody>
	</table>
</div>
</fieldset>
<br />
</c:forEach>

	<div id="functions">
		<ul>
			<li><a href="javascript:historySet('${_urlback}','../amministrazioni/createParametriProtocollo.htm?codice=${amministrazioni.id.codice}','')"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</div>
</body>
</html>



