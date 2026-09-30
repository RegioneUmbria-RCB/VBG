<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>		
 	<form action="${inite:geturltopost(pageContext.request, urlStampe, URL_BACK, null, true)}" name=innerForm method="post" > 
	<%--  <spring-form:form commandName="stampaAutorizzazioniHelper" name="innerForm" id="innerForm" action="${inite:geturltopost(pageContext.request, urlStampe, URL_BACK, null, true)}">--%> 
	 	<table>
		<tr>			
			<td><input type="hidden" name="nAutorizzazione" value="${VwConcessionilista.concNumero}" /></td>
		</tr>
		<tr>
			<td><input type="hidden" name="dataAutorizzazione" value="<fmt:formatDate value="${VwConcessionilista.dataInizioRilascio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>"  /></td>
		</tr>
		<tr>		
			<td><input type="hidden" name="dataAutorizzazioneFine" value="<fmt:formatDate value="${VwConcessionilista.dataFineRilascio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>"  /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="codiceRegistro" value="${VwConcessionilista.contipologiaregistri.id.codice}" /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="codiceIstanza" value="${VwConcessionilista.istanza.numeroistanza}" /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="nominativo" value="${VwConcessionilista.istanza.richiedente.descrizioneRichiedente}" /></td>
		</tr>
		<tr>
			<td><input type="hidden" name="dataPresentazione" value="<fmt:formatDate value="${VwConcessionilista.istanzadataDa}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>"  /></td>
			
		</tr>
		<tr>
			<td><input type="hidden" name="dataPresentazioneFine" value="<fmt:formatDate value="${VwConcessionilista.istanzadataA}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>"  /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="codiceStradario" value="${VwConcessionilista.istanzestradario.stradario.id.codice}" /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="stradario" value="${VwConcessionilista.istanzestradario.stradario.descrizioneCompleta}" /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="cap" value="${VwConcessionilista.istanzestradario.cap}" /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="codiceInterventoProc" value="${VwConcessionilista.istanza.alberoproc.id.codice}" /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="interventoProc" value="${VwConcessionilista.istanza.alberoproc.vwAlberoproc.scDescrizione}" /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="codiceProcedura" value="${VwConcessionilista.istanza.procedura.id.codice}" /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="codiceComune" value="${VwConcessionilista.codiceComuni}" /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="ordinamento" value="${VwConcessionilista.ordinamento}" /></td>
		</tr>
		<tr>			
			<td><input type="hidden" name="ordinamentoASCDESC" value="${VwConcessionilista.ordinamentoASCDESC}" /></td>
		</tr>
		
	</table>	
	 
	 <%-- </spring-form:form>--%>
	</form>

	<script type="text/javascript">
		 document.forms["innerForm"].submit();	
	</script>



