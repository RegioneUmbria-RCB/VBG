<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@page import="java.math.BigDecimal"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.SchedaContabileController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.SchedaContabileCommand"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
		<fmt:message key="form.schedacontabile.title.view" />
	</title>	
</head>
<body>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../schedacontabile/view" />
</jsp:include>

<span class="titoloPagina">
	<fmt:message key="form.schedacontabile.title.view" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form  commandName="schedaContabileCommand" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="schedaContabileCommand" />
    </jsp:include>

	
	<span class="parametri">
		<fmt:message key="form.registrazioni.anagrafe" />:
			<a class="vbg-btn btn-dettaglio" href="javascript:dettaglioAnagrafe(${schedaContabileCommand.entity.id.codice});"></a>
			<label><b>${schedaContabileCommand.entity.descrizioneRichiedente}</b></label>
	</span>	
			<span id="anagrafe_dettaglio" style="display: none; text-align: left;"></span>
			<script	type="text/javascript">
				var anagrafeVisibile=false;
				function dettaglioAnagrafe(codiceAnagrafe){
					if(anagrafeVisibile==false){
					new Ajax.Request('<%=request.getContextPath()%>/ajax/dettaglioAnagrafe.htm', {
						  method: 'post',
						  parameters: {codiceAnagrafe: codiceAnagrafe},
						  onSuccess: function(transport){
							  var response = transport.responseText;		
							  $("anagrafe_dettaglio").innerHTML = response;
							  $("anagrafe_dettaglio").appear();
							  applyStyle();			  
						    },
						  onFailure: function(transport){ 
							var response = transport.responseText;
						    alert(response); }						    		 
						  });
						anagrafeVisibile=true;
					}else{
						$("anagrafe_dettaglio").dropOut();
						anagrafeVisibile=false;
					}
					  
				}
			</script>
			

	<br class="clear" />

<%

String raggruppatoStyle = "display: none";
String dettaglioStyle = "";

String tabParameter=(String)request.getAttribute("tabParameter");
String showParameter=(String)request.getAttribute("showParameter");

String raggruppatoSchedaClass="";
String dettaglioSchedaClass="";


if(tabParameter.equalsIgnoreCase(SchedaContabileCommand.TAB_RAGGRUPPATO)){
    raggruppatoStyle = "";
    dettaglioStyle = "display: none";
    raggruppatoSchedaClass = "SchedaAttiva";
    dettaglioSchedaClass = "Scheda";
}else{
    raggruppatoStyle = "display: none";
    dettaglioStyle = "";
    raggruppatoSchedaClass = "Scheda";
    dettaglioSchedaClass = "SchedaAttiva";
}
String sceltaAllChecked = "";
String sceltaClosedChecked = "";

if(showParameter.equalsIgnoreCase(SchedaContabileCommand.SHOW_ALL)){
    sceltaAllChecked = " checked='checked' ";
    sceltaClosedChecked = "";	
}else{
    sceltaAllChecked = "  ";
    sceltaClosedChecked = " checked='checked'";	
}
%>


		<fieldset><legend><fmt:message key="form.schedacontabile.daticontabili"/></legend>
		
		<br class="clear" />
			<ul class="listaSchede">
				<li><a id="raggruppatoScheda" class="<%= raggruppatoSchedaClass%>" href="javascript:changeTab('<%= SchedaContabileCommand.TAB_RAGGRUPPATO%>')"><fmt:message key="form.schedacontabile.tab.raggruppata"/></a></li>
				<li><a id="dettaglioScheda" class="<%= dettaglioSchedaClass%>" href="javascript:changeTab('<%= SchedaContabileCommand.TAB_DETTAGLIO%>')"><fmt:message key="form.schedacontabile.tab.dettaglio"/></a></li>	
			</ul>

		
		<input type="radio" name="scelta" <%=sceltaAllChecked %> onclick="changeShowRecords('<%=SchedaContabileCommand.SHOW_ALL %>');" value="<%=SchedaContabileCommand.SHOW_ALL %>"/><fmt:message key="form.schedacontabile.show.all"/> 
		<input type="radio" name="scelta" <%=sceltaClosedChecked %> onclick="changeShowRecords('<%=SchedaContabileCommand.SHOW_CLOSED %>');" value="<%=SchedaContabileCommand.SHOW_CLOSED %>" /><fmt:message key="form.schedacontabile.show.open"/> 


		
<div class="jmesa">		
		<div id="raggruppato_div" style="<%= raggruppatoStyle %>">
			
			<table border="0" class="table">
				<thead>
					<tr class="header">
						<td width="3%"><fmt:message key="form.registrazioni.anno"/></td>
						<td><fmt:message key="form.registrazioni.registrazioniCausali"/></td>
						<td><fmt:message key="form.registrazioni.mercati"/></td>
						<td><fmt:message key="form.registrazioni.mercatiUso"/></td>
						<td><fmt:message key="form.registrazioni.mercati.posteggio"/></td>
						<td align="right"><fmt:message key="form.registrazioni.importo"/></td>
						<td align="right"><fmt:message key="form.registrazioni.incassato"/></td>
						<td></td>
					</tr>
				</thead>
				<tbody class="tbody">
				<%int j=1;%>
				
				<c:forEach var="registrazioni_var" items="${schedaContabileCommand.registrazioniList}" varStatus="registrazioneStatus">			
					<tr>
						<td class="<%=(j%2)==0?"odd":"even"%>">${registrazioni_var.anno}</td>
						<td class="<%=(j%2)==0?"odd":"even"%>">${registrazioni_var.registrazioniCausali.descrizione}</td>
						<td class="<%=(j%2)==0?"odd":"even"%>">${registrazioni_var.mercatiD.mercati.descrizione}</td>
						<td class="<%=(j%2)==0?"odd":"even"%>">${registrazioni_var.mercatiUso.descrizione}</td>
						<td class="<%=(j%2)==0?"odd":"even"%>">${registrazioni_var.mercatiD.codiceposteggio}</td>
						<td class="<%=(j%2)==0?"odd":"even"%>" align="right"><fmt:formatNumber minFractionDigits="2" value="${registrazioni_var.importo}" /></td>
						<td class="<%=(j%2)==0?"odd":"even"%>" align="right"><fmt:formatNumber minFractionDigits="2" value="${registrazioni_var.incassato}" /></td>
						<td class="<%=(j%2)==0?"odd":"even"%>">
							<a class="dettaglioColumn" 
							href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fregistrazioni%2Fview.htm?codice=${registrazioni_var.id.codice}"  title="<fmt:message key="label.edit.record" /> ${registrazioni_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</td>					
					</tr>
					<%j++; %>
				</c:forEach>
	
					<tr>
						<td colspan="6">
							<a href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fregistrazioni%2Fcreate.htm?entity.anagrafe.id.codice=${schedaContabileCommand.entity.id.codice}"
							title="&nbsp;<fmt:message key="form.schedacontabile.nuovaregistrazione"/>" class="vbg-btn btn-aggiungi">
							</a>
						</td>
					</tr>
			
				</tbody>
			</table>
		</div>	
		<br class="clear" />
		
		<div id="dettaglio_div" style="<%=dettaglioStyle %>">
				<table border="0" class="table">
					<thead>
						<tr class="header">
							<td width="3%"><fmt:message key="form.registrazioni.anno"/></td>
							<td width="15%"><fmt:message key="form.registrazioni.registrazioniCausali"/></td>
							<td width="10%"><fmt:message key="form.registrazioni.mercati"/></td>
							<td width="10%"><fmt:message key="form.registrazioni.mercatiUso"/></td>
							<td width="10%"><fmt:message key="form.registrazioni.mercati.posteggio"/></td>
							<td width="5%" align="right"><fmt:message key="form.registrazioniimporti.importo"/></td>
							<td width="5%" align="right"><fmt:message key="form.registrazioni.incassato"/></td>
							<td width="5%" align="right"><fmt:message key="form.registrazioniimporti.nrRata"/></td>
							<td width="5%"><fmt:message key="form.registrazioniimporti.scadenza"/></td>
							<td width="10%" ><fmt:message key="form.registrazioniimporti.conti"/></td>
							<td width="10%" ></td>
						</tr>
					</thead>
					<tbody class="tbody">
					<%int i=1;%>
					<c:forEach var="registrazioniImporti_var" items="${schedaContabileCommand.registrazioniImportiList}" varStatus="importoStatus">			
						<tr>
							<td class="<%=(i%2)==0?"odd":"even"%>">${registrazioniImporti_var.registrazioni.anno}</td>
							<td class="<%=(i%2)==0?"odd":"even"%>">${registrazioniImporti_var.registrazioni.registrazioniCausali.descrizione}</td>
							<td class="<%=(i%2)==0?"odd":"even"%>">${registrazioniImporti_var.registrazioni.mercatiD.mercati.descrizione}</td>
							<td class="<%=(i%2)==0?"odd":"even"%>">${registrazioniImporti_var.registrazioni.mercatiUso.descrizione}</td>
							<td class="<%=(i%2)==0?"odd":"even"%>">${registrazioniImporti_var.registrazioni.mercatiD.codiceposteggio}</td>
							
							<td class="<%=(i%2)==0?"odd":"even"%>" align="right">
								<c:if test="${registrazioniImporti_var.rimanenza <= 0}">
									<fmt:formatNumber minFractionDigits="2" value="${registrazioniImporti_var.importo}" />
								</c:if>
								<c:if test="${registrazioniImporti_var.rimanenza > 0}">
									<c:choose>
									<c:when test="${registrazioniImporti_var.incassato <= 0 and (not registrazioniImporti_var.nonPrevedeIncassi)}">
										<input type="text" id="registrazioneImporto_${registrazioniImporti_var.id.codice}" style="text-align: right" size="10" value="<fmt:formatNumber minFractionDigits="2" value="${registrazioniImporti_var.importo}" />"/>
									</c:when>	
									<c:otherwise>
										<fmt:formatNumber minFractionDigits="2" value="${registrazioniImporti_var.importo}" />
									</c:otherwise>
									</c:choose>
								</c:if>								
							</td>							
							<td class="<%=(i%2)==0?"odd":"even"%>" align="right"><fmt:formatNumber minFractionDigits="2" value="${registrazioniImporti_var.incassato}" /></td>
							<td class="<%=(i%2)==0?"odd":"even"%>" align="right">${registrazioniImporti_var.nrRata}</td>
							<td class="<%=(i%2)==0?"odd":"even"%>"><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioniImporti_var.scadenza}" /></td>
							<td class="<%=(i%2)==0?"odd":"even"%>">${registrazioniImporti_var.conti.descrizione}</td>
							<td class="<%=(i%2)==0?"odd":"even"%>">&nbsp;
								<c:if test="${registrazioniImporti_var.rimanenza <= 0}">
									&nbsp;
								</c:if>
								<c:if test="${registrazioniImporti_var.rimanenza > 0 }">
									<c:if test="${registrazioniImporti_var.incassato <= 0 and (not registrazioniImporti_var.nonPrevedeIncassi)}">
    									<a class="vbg-btn btn-salva" href="javascript:salvaRigaImporto(${registrazioniImporti_var.id.codice});" title="<fmt:message key="label.aggiorna.importo.schedacontabile" />">
											<label><fmt:message key="label.aggiorna.importo.schedacontabile.image" /></label>
										</a>
										<a class="eliminaRiga" href="javascript:doHref('deleteRegistrazioniImporti.htm?codiceImporto=${registrazioniImporti_var.id.codice}&entity.id.codice=${schedaContabileCommand.entity.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" title="<fmt:message key="label.elimina" /> ${registrazioniImporti_var.id.codice}">
											<label><fmt:message key="label.elimina.riga.schedacontabile.image" /></label>
										</a>
									</c:if>
								</c:if>								
							</td>
						</tr>
					<%i++; %>
					</c:forEach>
					</tbody>
				</table>
					
		</div>
		
</div>			
		
		<br class="clear" />
</fieldset>
		<br class="clear" />
		
		<c:set var="totEmesso" value="0.0"/>
		<c:set var="totIncassato" value="0.0"/>
		<c:set var="totSaldo" value="0.0"/>
		
		<c:forEach var="tutteLeRegistrazioni_var" items="${tutteLeRegistrazioni}" varStatus="tutteLeregistrazioneStatus">	
			<c:set var="totEmesso" value="${totEmesso+tutteLeRegistrazioni_var.importo}"/>
			<c:set var="totIncassato" value="${totIncassato+tutteLeRegistrazioni_var.incassato}"/>
		</c:forEach>
		
		
		<div id="riepiloghi" align="center">
			<table border="0" class="table">
				<tr>
					<td align="right"><fmt:message key="form.schedacontabile.emesso"/></td>
					<td><input style="text-align:right" value="<fmt:formatNumber minFractionDigits="2" value="${totEmesso}" />" readonly="readonly" />&nbsp;<fmt:message key="label.valuta"/></td>
				</tr>
				<tr>
					<td align="right"><fmt:message key="form.schedacontabile.incassato"/></td>
					<td><input style="text-align:right"  value="<fmt:formatNumber minFractionDigits="2" value="${totIncassato}" />" readonly="readonly" />&nbsp;<fmt:message key="label.valuta"/></td>
				</tr>
				<tr>
					<c:set var="totSaldo" value="${totEmesso - totIncassato}"/>
					<td  align="right"><fmt:message key="form.schedacontabile.saldo"/></td>
					<td><input style="text-align:right" value="<fmt:formatNumber minFractionDigits="2" value="${totSaldo}" />" readonly="readonly" />&nbsp;<fmt:message key="label.valuta"/></td>
				</tr>

			</table>
		</div>

</spring-form:form>
</div>
<script type="text/javascript">


	function changeTab(tabId){
		if(tabId == '<%=SchedaContabileCommand.TAB_RAGGRUPPATO%>'){
			$('raggruppatoScheda').className = "SchedaAttiva";
			$('dettaglioScheda').className = "Scheda";
			$('dettaglio_div').style.display = "none";
			$('raggruppato_div').style.display = "block";					
		}else{
			$('raggruppatoScheda').className = "Scheda";
			$('dettaglioScheda').className = "SchedaAttiva";
			$('raggruppato_div').style.display = "none";
			$('dettaglio_div').style.display = "block";			
		}		
		saveUserPreference('<%= WebConstants.CONF_UTENTE_SCHEDACONTAB_TAB %>',tabId);
	}

	function saveUserPreference(nomeparametro, valore){
		new Ajax.Request('<%=request.getContextPath()%>/schedacontabile/salvaPreferenza.htm', {
			  method: 'post',
			  parameters: {nomeparametro: nomeparametro,valore: valore},
			  onSuccess: function(transport){ },
			  onFailure: function(transport){ 
				var response = transport.responseText;
			    alert(response); }						    		 
			  });			
	}


	function changeShowRecords(showId){
		saveUserPreference('<%= WebConstants.CONF_UTENTE_SCHEDACONTAB_CHECK %>',showId);
		setTimeout("refreshThisView();", 500);
	}

	function refreshThisView(){
		doHref('view.htm?entity.id.codice=${schedaContabileCommand.entity.id.codice}','');
	}
	
	function salvaRigaImporto(codiceRiga){
		
			var idImporto='registrazioneImporto_'+codiceRiga;
			var importo=$(idImporto).value;
			
			if(isNaN(importo.replace(",","."))){
				alert('<fmt:message key="alert.field.numeric" />');
				return;
			}
			if(importo.indexOf(".",0)>0){
				importo = importo.replace(".",",");
			}		

			
			var linkHref="salvaRigaImporto.htm?codiceImporto=" + codiceRiga + "&importo="+importo+ "&entity.id.codice=${schedaContabileCommand.entity.id.codice}";

			doHref(linkHref,'<fmt:message key="javascript.confirm.update" />');

	}

	
	function searchAll(inputField,evt){
	 var charCode = (evt.which) ? evt.which : event.keyCode;
	 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
	   inputField.value='%';
	 }
	}
</script>
<br class="clear" />
<div id="functions">
<ul>
		<li><a href="javascript:doHref('search.htm','')"><fmt:message key="button.back" /></a></li>	
</ul>
</div>
</body>
</html>
