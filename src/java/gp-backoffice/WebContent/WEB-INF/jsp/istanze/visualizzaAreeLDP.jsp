<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.istanzestradario" />
	</title>
</head>
<body>
   
	<span class="titoloPagina">
			Gestione aree occupazione
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${param.codice}</c:param>
	</c:import>
	
			
	
	
	<br class="clear" />
	<div id="subcontent">
<c:choose>
<c:when test="${not empty dettaglio }">
	<fieldset><legend><span class="titoloPagina">Dati occupazione</span></legend>
	
<div class="header_dati">
	<div class="header_dato">
		<span class="header_dato_etichetta">Tipologia:</span>
		<span class="header_dato_valore">${dettaglio.tipologia} </span>
	</div>		
	<div class="header_dato">
		<span class="header_dato_etichetta">Giorni:</span>
		<span class="header_dato_valore">${dettaglio.giorniSettimana}</span>
	</div>	
	<div class="header_dato">
		<span class="header_dato_etichetta">Ripetizione:</span>
		<span class="header_dato_valore">${dettaglio.ripetizione}</span>
	</div>	
</div>

	    <div class="clear" ></div>
		<div class="jmesa" >
			<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">
			<thead>
					<tr class="header">
						<td>
							Inizio
						</td>						
						<td>
							Fine
						</td>
						<td>Aree</td>					
					</tr>
				</thead>	
				<tbody class="tbody" >
				<%int i = 0; %>
				<c:forEach var="periodi_var" items="${periodis}" varStatus="status">

					<tr class="<%=(i%2)==0?"odd":"even"%>">
						<td>${periodi_var.inizio}</td>
						<td>${periodi_var.fine}</td>
						<td>
							<c:if test="${not empty periodi_var.AAree.complexTypeArea}">
								<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">								
									<c:forEach items="${ periodi_var.AAree.complexTypeArea }" var="ca" varStatus="areestatus">
									<tr>
										<td id="ldparee_descrizione_${ areestatus.index}">${ca.descrizione}</td>
										<td id="ldparee_identificativo_${ areestatus.index}">${ca.identificativo}</td>
										<td style="width:1%">${ca.metriQuadrati}</td>
										<td style="width:5%">
										<a class="eliminaRiga" style="float: none;" href="javascript:eliminaAreaLdp('ldparee_identificativo_${ areestatus.index}','${periodi_var.inizio}','${periodi_var.fine}')" title="<fmt:message key="label.elimina" />">
				              	 			<label><fmt:message key="label.elimina.image" /></label>
					            		</a>							            
					            		</td>										
									</tr>		
									</c:forEach>												
								</table>
							</c:if>	
						</td>
					</tr>
					<%i++; %>
				</c:forEach>
				</tbody>
			</table>			
		</div>
	</fieldset>
</c:when>
<c:otherwise>
	Non ci sono dati di occupazione.
</c:otherwise>
</c:choose>
	
<form action="${pageContext.request.contextPath}/istanze/eliminaAreaLDP.htm" name="invio" method="post">
	<input id="codiceIstanza" type="hidden" name="codiceIstanza" value="${param.codice}" />
	<input id="identificativo_ldp" type="hidden" name="identificativo" />
	<input id="inizio_ldp" type="hidden" name="inizio" />
	<input id="fine_ldp" type="hidden" name="fine" />

</form>
<div id="functions">
	<ul>				
		<li><a href="javascript:void(0);" onclick="historyBack()"><fmt:message key="button.back" /></a></li>
	</ul>
</div>

<script type="text/javascript">

function eliminaAreaLdp(identificativo_id,inizio,fine){
	jQuery('#identificativo_ldp').val(jQuery('#'+identificativo_id).text());
	jQuery('#inizio_ldp').val(inizio);
	jQuery('#fine_ldp').val(fine);
	document.forms['invio'].submit();
}

</script>
</div>	
	
</body>
</html>