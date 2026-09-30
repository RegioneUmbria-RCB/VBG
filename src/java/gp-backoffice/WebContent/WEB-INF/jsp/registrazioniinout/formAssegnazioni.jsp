<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message
	key="form.registrazioni.registrazioniImportis.title.list" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message
	key="form.registrazioni.registrazioniimporti" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>

<c:if test="${param.deleteAssegnazione=='ok'}">
	<span class="success" id="deleteAssegnazione"><fmt:message
		key="form.registrazioniInOut.deleteAssegnazione.success" /></span>

	<script type="text/javascript">
	$('deleteAssegnazione').pulsate( {
		pulses :2,
		duration :1.0
	});
</script>
</c:if>
<c:if test="${param.insertAssegnazioni=='ok'}">
	<span class="success" id="insertAssegnazioni"><fmt:message key="form.registrazioniInOut.insertAssegnazioni.success" /></span>
<script type="text/javascript">
	$('insertAssegnazioni').pulsate( {
		pulses :2,
		duration :1.0
	});
</script>
</c:if>
<c:if test="${param.insertAssegnazioni=='ko'}">
	<span class="error" id="insertAssegnazioni"><fmt:message key="form.registrazioniInOut.insertAssegnazioni.error" /></span>
<script type="text/javascript">
	$('insertAssegnazioni').pulsate( {
		pulses :2,
		duration :1.0
	});
</script>
</c:if>
<br />
<br />
<span class="parametri">
<fmt:message key="form.registrazioni.title.view" />
<br />
<fmt:message key="form.registrazioni.progressivo"></fmt:message>:
<label> ${registrazioni.progressivo}</label>
<br />
<fmt:message key="form.registrazioni.dataregistrazione"></fmt:message>:
<label><fmt:formatDate pattern="<%= WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioni.dataRegistrazione}"/></label>
<br />
<fmt:message key="form.registrazioni.anagrafe"></fmt:message>:
<label> ${registrazioni.anagrafe.descrizioneRichiedente}</label>

</span>

<br />
<div id="subcontent">

<script type="text/javascript">
var daAssegnare =  ${registrazioniInOut.rimanenza};

function mostradiv(id, importo){
	$(id).appear();	
	$('euro_'+id).fade();	
	setTimeout('inserisciImporto(\'importo'+id+'\',\''+importo+'\')',500);
		
}

function inserisciImporto(divName, rimanenza){
	if(daAssegnare >= rimanenza){
		$(divName).value = rimanenza;
	}else{
		$(divName).value = daAssegnare;
	}	
}

function nascondidiv(id){
	$(id).fade();
	$('euro_'+id).appear();
}

function changeValue(link,id){
	var idImporto='importo'+id;
	var importo=$(idImporto).value;
	if(isNaN(importo.replace(",","."))){
		alert('<fmt:message key="alert.field.numeric" />');
		return;
	}
	if(importo.indexOf(".",0)>0){
		importo = importo.replace(".",",");
	}		
	var linkHref=link+"&importo="+importo;
	location.href=linkHref;
}

</script>

<div class="jmesa" >
<span class="parametri">
	<fmt:message key="form.registrazioniInOut.rimanenza" />:
	<label>${registrazioniInOut.rimanenza} <fmt:message key="label.valuta" /></label>	
</span>
<c:forEach var="rata_var" items="${rateList}" varStatus="rataStatus">
		<table border="0" width="40%" cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td><fmt:message key="form.registrazioniimporti.nrRata" /> ${rata_var.numeroRata}</td>
					<td align="right">
						<%boolean scritto = false; %>
						<c:forEach var="registrazioniImporti_var"
								items="${rata_var.registrazioniImportiList}" varStatus="vsi">
								<%if (scritto==false){ %>
									<fmt:message key="form.registrazioniimporti.scadenza" />: 
									<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioniImporti_var.scadenza}"/>
								<%scritto = true;
								} %> 
						</c:forEach>
					</td>
				</tr>
			</thead>		
			<tbody class="tbody" >
			<tr>
				<td colspan="2">
					<table border="0" width="40%" cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td><fmt:message key="form.conti.descrizione" /></td>
								<td></td>
								<td align="right" width="5%"><fmt:message key="form.registrazioniimporti.importo" /> <fmt:message key="label.valuta" /></td>
								<td width="10%" align="right"><fmt:message key="form.registrazioni.rimanenza" /> <fmt:message key="label.valuta" /></td>
								<td></td>
							</tr>
						</thead>
						<tbody class="tbody" >
						<%
						int i=0;
					
						%>
						<c:forEach var="registrazioniImporti_var"
							items="${rata_var.registrazioniImportiList}" varStatus="vs">
							<tr class="<%=(i%2)==0?"odd":"even"%>"  onmouseover="this.className='highlight'"  onmouseout="this.className='<%=(i%2)==0?"odd":"even"%>'">
								<td>${registrazioniImporti_var.conti.descrizione}</td>
								<td></td>
								<td align="right">${registrazioniImporti_var.importo}</td>
								<td align="right">${registrazioniImporti_var.rimanenza}</td>
								<td align="right">
									<c:if test="${registrazioniImporti_var.rimanenza > 0 }">
										<c:if test="${registrazioniInOut.rimanenza > 0 }">
											<span id="euro_${registrazioniImporti_var.id.codice}">
												<a class="vbg-btn btn-euro" href="javascript:mostradiv('${registrazioniImporti_var.id.codice}','${registrazioniImporti_var.rimanenza}');" title="<fmt:message key="label.assegna" /> ${registrazioni_var.id.codice}">
												<!-- <img src="${pageContext.request.contextPath}/images/euro.png" alt="<fmt:message key="label.assegna" /> ${registrazioni_var.id.codice}"/> -->
												</a>
											</span>
											<span id="${registrazioniImporti_var.id.codice}" style="display: none">
												<input style="text-align:right" id="importo${registrazioniImporti_var.id.codice}" size="10" name="regValue" title="<fmt:message key="form.registrazioniimporti.importo" /> ${registrazioniImporti_var.id.codice}"/>
												<a class="vbg-btn btn-euro btn-euro-aggiungi" href="javascript:changeValue('../registrazioniinout/insertAssegnazioniRegistrazioniImporti.htm?idRegistrazioneInOut=${registrazioniInOut.id.codice}&idRigaImporto=${registrazioniImporti_var.id.codice}','${registrazioniImporti_var.id.codice}')" 
															title="<fmt:message key="label.assegna" /> ${registrazioniImporti_var.id.codice}">
												<!-- <img src="${pageContext.request.contextPath}/images/money_add.png" alt="<fmt:message key="label.assegna" /> ${registrazioni_var.id.codice}"/>  -->
												</a>
												<a class="vbg-btn btn-elimina" href="javascript:nascondidiv('${registrazioniImporti_var.id.codice}');" title="<fmt:message key="label.annulla" /> ${registrazioniImporti_var.id.codice}">
												<!-- <img src="${pageContext.request.contextPath}/images/cross.gif" alt="<fmt:message key="label.annulla" /> ${registrazioniImporti_var.id.codice}"/> -->
												</a>
											</span>
										</c:if>	
									</c:if>
									<%
										int assegnazioni = 0;
									%>
									<c:forEach var="assegnazione_var"	items="${registrazioniImporti_var.regIoAssegnazionis}" varStatus="rios">
											<c:if test="${assegnazione_var.registrazioniInOut.id.codice eq registrazioniInOut.id.codice}">
												<%
												assegnazioni += 1;												
												%>
											</c:if>																										
									</c:forEach>										
									<% if(assegnazioni>0){%>
										<a class="vbg-btn btn-euro btn-euro-elimina" href="javascript:doHref('../registrazioniinout/deleteAssegnazioniRegistrazioniImporti.htm?idRegistrazioneInOut=${registrazioniInOut.id.codice}&idRigaImporto=${registrazioniImporti_var.id.codice}','<fmt:message key="javascript.confirm.delete" />');" title="<fmt:message key="button.regioassegnazioni.reset" /> ${registrazioniImporti_var.id.codice}">
										<!-- <img src="${pageContext.request.contextPath}/images/money_delete.png" alt="<fmt:message key="button.regioassegnazioni.reset" /> ${registrazioniImporti_var.id.codice}"/> -->
										</a>
									<% }%>
								</td>
							</tr>							
							<%i++;%>
						</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
			</tbody>
		</table>
	<br />
</c:forEach>	
</div>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doHref('view.htm?codice=${registrazioniInOut.id.codice}','')"><fmt:message key="button.back" /></a></li>
	<li><a href="javascript:doHref('deleteAssegnazioniRegistrazione.htm?idRegistrazioneInOut=${registrazioniInOut.id.codice}&idRegistrazione=${registrazioni.id.codice}','<fmt:message key="javascript.confirm.delete" />')"><fmt:message key="button.regioassegnazioni.reset" /></a></li>
</ul>
</div>
</body>
</html>