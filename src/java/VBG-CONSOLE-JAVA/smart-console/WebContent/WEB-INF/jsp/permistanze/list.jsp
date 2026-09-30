<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.elenco_permessi_istanza" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.elenco_permessi_istanza" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="listprocedimentiistanze" />
</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../permistanze/list" />
	</jsp:include>
    <c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanze.id.codice}</c:param>
	</c:import>	
	<br class="clear" />
	<%--
	<jsp:include page="../includes/istanze_funzioni.jsp">
    	<jsp:param name="codiceIstanza" value="${istanze.id.codice}" />
    	<jsp:param name="hideFunction" value="btnPermessiIstanza" />
	</jsp:include>
	 --%>	
<div id="subcontent">
    <table width="100%">
		<%
			String displayAmministratori= "display:none;";
			String styleAmministratori = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ISTANZA_AMMINISTRATORI_ABILITATI)).equals("1")) {
			    displayAmministratori = "";
			    styleAmministratori="sezioneDatiMeno";
			} else {
			    displayAmministratori = "display:none;";
			    styleAmministratori="sezioneDatiPiu";
			}
		%>
		
		<%
			String displayAmministratorisoftware= "display:none;";
			String styleAmministratorisoftware = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ISTANZA_AMMINISTRATORI_SOFTWARE_ABILITATI)).equals("1")) {
			    displayAmministratorisoftware = "";
			    styleAmministratorisoftware="sezioneDatiMeno";
			} else {
			    displayAmministratorisoftware = "display:none;";
			    styleAmministratorisoftware="sezioneDatiPiu";
			}
		%>
		
		<%
			String displayRuoli= "display:none;";
			String styleRuoli = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ISTANZA_RUOLI_ACCESSO)).equals("1")) {
			    displayRuoli = "";
			    styleRuoli="sezioneDatiMeno";
			} else {
			    displayRuoli = "display:none;";
			    styleRuoli="sezioneDatiPiu";
			}
		%>
		
		<%
			String displayPermessioperatori= "display:none;";
			String stylePermessioperatori= "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ISTANZA_OPERATORI_ACCESSO)).equals("1")) {
			    displayPermessioperatori = "";
			    stylePermessioperatori="sezioneDatiMeno";
			} else {
			    displayPermessioperatori = "display:none;";
			    stylePermessioperatori="sezioneDatiPiu";
			}
		%>
			
		<tr class="titoloSezione">
			<td colspan="6">
				<a class="<%=styleAmministratori%>" id="id_link_amministatori_abilitatti" href="javascript:showHidePanel('id_amministatori_abilitatti_table', 'id_link_amministatori_abilitatti', '<%= WebConstants.CONF_UTENTE_ISTANZA_AMMINISTRATORI_ABILITATI %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /><fmt:message key="label.amministratori_sistema"/>">
					<label for="id_link_amministatori_abilitatti"><fmt:message key="label.amministratori_sistema"/></label>
				</a>
			</td>
		</tr>
		<c:forEach items="${amministratoriList}" var="amministrazioni">
		<tr id="id_amministatori_abilitatti_table" style="<%=displayAmministratori%>;">
			<td>
				<input type="checkbox" checked="checked" disabled="disabled">
				${amministrazioni.responsabile}
			</td>
		</tr>
		</c:forEach>
		
		<tr class="titoloSezione">
			<td colspan="6">
				<a class="<%=styleAmministratorisoftware%>" id="id_link_amministatori_software_abilitatti" href="javascript:showHidePanel('id_amministatori_software_abilitatti_table', 'id_link_amministatori_software_abilitatti', '<%= WebConstants.CONF_UTENTE_ISTANZA_AMMINISTRATORI_SOFTWARE_ABILITATI %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.amministratori"/>&nbsp;
				${istanze.software.descrizione}">
					<label for="id_link_amministatori_software_abilitatti"><fmt:message key="label.amministratori"/>&nbsp; ${istanze.software.descrizione}</label>
				</a>
			</td>
		</tr>
		<c:forEach items="${amministratoriSoftwareList}" var="amministrazionisoftware">
		<tr id="id_amministatori_software_abilitatti_table" style="<%=displayAmministratorisoftware%>;">
			<td>
				<input type="checkbox" checked="checked" disabled="disabled">
				${amministrazionisoftware.responsabile}
			</td>
		</tr>
		</c:forEach>
		
		<tr class="titoloSezione">
			<td colspan="6">
				<a class="<%=styleRuoli%>" id="id_link_ruoli_istanze_abilitatti" href="javascript:showHidePanel('id_amministatori_ruoli_istanze_table', 'id_link_ruoli_istanze_abilitatti', '<%= WebConstants.CONF_UTENTE_ISTANZA_RUOLI_ACCESSO %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.ruoli_con_accesso_istanza"/>">
					<label for="id_link_ruoli_istanze_abilitatti"><fmt:message key="label.ruoli_con_accesso_istanza"/></label>
				</a>
			</td>
		</tr>
		
		<c:forEach items="${ruoliList}" var="ruolo" varStatus="indice">
		<tr id="id_amministatori_ruoli_istanze_table" style="<%=displayRuoli%>;">
			<td>
				<input type="checkbox" id="check_box${indice.index}" ${ruolo.ruoloIstanzaTransient?'checked':''} value="${ruolo.id.codice}" name="id.codice" onclick="abilitaDisabilitaRuoli('check_box${indice.index}','codice_istanza','result_${ruolo.id.codice}')"/>
				${ruolo.ruolo}
				<span id="result_${ruolo.id.codice}" style="display: none"></span>
			</td>
		</tr>
		<input type="hidden" id="codice_istanza" value="${istanze.id.codice}" />
		</c:forEach>
		<tr class="titoloSezione">
			<td colspan="6">
				<a class="<%=stylePermessioperatori%>" id="id_link_operatori_abilitatti" href="javascript:showHidePanel('id_operatori_abilitatti_table', 'id_link_operatori_abilitatti', '<%= WebConstants.CONF_UTENTE_ISTANZA_OPERATORI_ACCESSO %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.operatori_accesso_istanza"/>">
					<label for="id_link_operatori_abilitatti"><fmt:message key="label.operatori_accesso_istanza"/></label>
				</a>
			</td>
		</tr>
		<c:forEach items="${responsabiliList}" var="responsabile" varStatus="indice1">
		<tr id="id_operatori_abilitatti_table" style="<%=displayPermessioperatori%>;">
			<td>
				<input type="checkbox" id="check_box_operatori${indice1.index}" ${responsabile.responsabilePermessiTransient?'checked':''} value="${responsabile.id.codice}" name="id.codice" onclick="abilitaDisabilitaOperatori('check_box_operatori${indice1.index}','codice_istanza','result1_${responsabile.id.codice}')"/>
				${responsabile.responsabile}
				<span id="result1_${responsabile.id.codice}" style="display: none"></span>
			</td>
		</tr>
		<input type="hidden" id="codice_istanza" value="${istanze.id.codice}" />
		</c:forEach>
		
	</table>

<script type="text/javascript">
			
			function abilitaDisabilitaRuoli(idCheckbox,idHiddenCodiceistanza,result){			
			
				var istanza=document.getElementById(idHiddenCodiceistanza);
				var ruolo=document.getElementById(idCheckbox);
				new Ajax.Request('${pageContext.request.contextPath}/permistanze/ajaxAbilitaDisabilitaRuolo.htm?codiceistanza='+istanza.value+'&codiceruolo='+ruolo.value, {
					  method: 'post',	
					  onSuccess: function(transport){
						$(result).innerHTML = transport.responseText;
						$(result).className='success_header'
						$(result).style.display='';
						$(result).pulsate({ pulses: 2, duration: 1.0 });						
				      },
					  onFailure: function(transport){ 
						$(result).innerHTML= transport.responseText;
						$(result).className='error_header'
						$(result).style.display='';
						$(result).pulsate({ pulses: 2, duration: 1.0 });
					  }						    		 
				});
				}
			
			function abilitaDisabilitaOperatori(idCheckbox,idHiddenCodiceistanza,result){			
				
				var istanza=document.getElementById(idHiddenCodiceistanza);
				var ruolo=document.getElementById(idCheckbox);
				new Ajax.Request('${pageContext.request.contextPath}/permistanze/ajaxAbilitaDisabilitaOperatore.htm?codiceistanza='+istanza.value+'&codiceoperatore='+ruolo.value, {
					  method: 'post',	
					  onSuccess: function(transport){
						$(result).innerHTML = transport.responseText;
						$(result).className='success_header'
						$(result).style.display='';
						$(result).pulsate({ pulses: 2, duration: 1.0 });						
				      },
					  onFailure: function(transport){ 
						$(result).innerHTML= transport.responseText;
						$(result).className='error_header'
						$(result).style.display='';
						$(result).pulsate({ pulses: 2, duration: 1.0 });
					  }						    		 
				});
				}
			
			
		</script></div>
<div id="functions">
<ul>
	<li>
		<a href="javascript:historyBack();"><fmt:message key="button.back" /></a>
	</li>
</ul>
</div>
</body>
</html>