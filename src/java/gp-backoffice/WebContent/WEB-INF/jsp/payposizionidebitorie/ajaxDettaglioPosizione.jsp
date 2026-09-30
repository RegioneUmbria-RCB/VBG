<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

	<table>
		<tr>
			<td><fmt:message key="label.codice" /></td>		
			<td>${pd.id.codice}
			</td>
	   </tr>
		<tr>
			<td><fmt:message key="label.descrizione" /></td>		
			<td>${pd.descrizioneCausale}
			</td>
	   </tr>
	   		<tr>
			<td><fmt:message key="label.data" /></td>		
			<td>${pd.dataRegistrazione}
			</td>
	   </tr>
	   </tr>
	   		<tr>
			<td>Stato</td>		
			<td>${stato.statoAttuale.descrizione}
				(${stato.statoAttuale.dataEvento})
			</td>
	   </tr>

	</table>
	
	<div id="functions">
	<ul>
		<li><a href="javascript:annullaPosizioneDebitoria(${pd.id.codice})">annulla posizione debitoria</a></li>
		<li><a href="javascript:segnaPagatoPosizioneDebitoria(${pd.id.codice})">Contrassegna come pagata offline</a></li>
	</ul>
	</div>
	
	<br class="clear" />