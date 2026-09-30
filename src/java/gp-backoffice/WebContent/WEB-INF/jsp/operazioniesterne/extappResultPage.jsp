<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.web.AnagrafeCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.operatori_esterni.carica_pratica.title" />
	</title>
	
</head>
<body>
	<span class="titoloPagina">		
		
		<init:editLabel key="label.operatori_esterni.carica_pratica.title" role="ROLE_EDITLABEL" />
	</span>
	
	<div id="subcontent">
		
			<init:editLabel key="label.operatori_esterni.carica_pratica.descrizione_esito_creazione_pratica" role="ROLE_EDITLABEL" />
			
		    <table>
				<tr>
					<td valign="top" style="width: 50%;">		
						<div class="header_dati">
						<div class="header_dato">
							<span class="header_dato_etichetta"><fmt:message key="label.numero_istanza" />:</span>
							<span class="header_dato_valore">${operazioniEsterneCommand.entity.numero_pratica}</span>
						</div>
						<div class="header_dato">
							<span class="header_dato_etichetta"><fmt:message key="label.data_presentazione_domanda" />:</span>
							<span class="header_dato_valore">${operazioniEsterneCommand.entity.data_pratica} </span>
							
						</div>
						<div class="header_dato">
							<span class="header_dato_etichetta"><fmt:message key="label.protocollo" />:</span>
							<span class="header_dato_valore">${operazioniEsterneCommand.entity.numero_protocollo_generale}</span>
						</div>
						<div class="header_dato">
							<span class="header_dato_etichetta"><fmt:message key="label.data_protocollo" />:</span>
							<span class="header_dato_valore">${operazioniEsterneCommand.entity.data_protocollo_generale} </span>
						</div>
						
						
						</div>
					</td>
				</tr>
			</table>
		 
	</div>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:chiudi();"><fmt:message key="button.operatori_esterni.indietro" /></a></li>
		</ul>
	</div>
	
	<script type="text/javascript">
		
		function chiudi(){			
			historySet('${_urlback}','../operazioniesterne/caricaPraticaView.htm','');
		}
		
		
	
	</script>
</body>
</html>