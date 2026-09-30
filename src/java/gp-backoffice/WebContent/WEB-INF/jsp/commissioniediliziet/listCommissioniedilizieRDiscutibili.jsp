<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		
			<fmt:message key="label.istanze_discusse_commissione" />
		
	</title>
</head>
		

<body>
	<span class="titoloPagina">
	
			<fmt:message key="label.istanze_discusse_commissione" />
		
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../commissioniediliziet/searchIstanzeInCommissione" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="commissioniediliziet" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commissioniediliziet" />
		    </jsp:include>
			<div class="parametriDiv">
				<div class="etichetta">
					<div><fmt:message key="label.numero_commissione" />:</div>
					<div><fmt:message key="label.descrizione" />:</div>
					<div><fmt:message key="label.data_commissione" />:</div>
					<div><fmt:message key="label.ora_inizio" />:</div>
					<div><fmt:message key="label.ora_fine" />:</div>
				</div>		
				<div class="parametro">       		 	
					<div>${commissioniediliziet.entity.numprotocollo}</div>
					<div>${commissioniediliziet.entity.descrizione}</div>
					<div><fmt:formatDate value="${commissioniediliziet.entity.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></div>
				    <div>${commissioniediliziet.entity.orainizio}</div>
				    <div>${commissioniediliziet.entity.orafine}</div>
				</div>
			</div>
			<br class="clear" />
			<table width="100%">
			
				<tr class="titoloSezione">
					<td >
						<fmt:message key="label.istanze_possibilita_discussione" />
						
					</td>
					<td align="right">
						<input id="id_check" type="checkbox"  onclick="selezionaAndDeseleziona()" title="<fmt:message key="label.seleziona_tutto" />"></input> 
				 		<label for="id_check"><fmt:message key="label.seleziona_tutto" /></label>				 
				   </td>
				</tr>
				<!--  TABELLA JMESA CHE CONTIENE LE COMMISSIONI EDILIZIE R IN DISCUSSIONE -->
				<tr>
				<%int i=0;%>
					<td colspan="2">
						
						<form name="commissioniedilizietForm" action="searchIstanzeInCommissione.htm">
							  ${htmltable}
						</form>
						 
						
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				
			var _jmesaUrl='searchIstanzeInCommissione.htm?';
			var _captionTab='<fmt:message key="label.istanze_possibilita_discussione" />';	
	
 	   			
			function selezionaAndDeseleziona(){	
					
						if($('id_check').checked){
							jQuery("input[id^='checkbox_id']").click();
							jQuery("input[id^='checkbox_id']").attr('checked', true);						
						}else{
							jQuery("input[id^='checkbox_id']").click();
							jQuery("input[id^='checkbox_id']").attr('checked', false);					
						}	
				}
			</script>	
		</spring-form:form>
	</div>
	 	
	<div id="functions">
		<ul>
		    <c:if test="${numeroIstanzeDaDiscutere >0}">
				<li><a href="javascript:doSubmit('insertCommissioniedilizieR.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('listCommissioniedilizieR.htm?codiceCommissione=${commissioniediliziet.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
</body>
</html>