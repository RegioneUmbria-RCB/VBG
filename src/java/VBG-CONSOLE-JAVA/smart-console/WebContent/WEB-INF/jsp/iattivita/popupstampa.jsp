<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>		
<spring-form:form commandName="helper" name="innerForm" id="innerForm" action="${inite:geturltopost(pageContext.request, helper.url_stampa, URL_BACK, null, true)}">
	<table>
		<tr>			
			<td><spring-form:hidden path="posizionearchivio" id="posizionearchivio_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codicestradario" id="codicestradario_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="stradario" id="stradario_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="fkidzona" id="fkidzona_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="fkidzonadesc" id="fkidzonadesc_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="denominazioneattivita" id="denominazioneattivita_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="numerocivico" id="numerocivico_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="attiva" id="attiva_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="descrizionetipomovimento" id="descrizionetipomovimento_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="colore" id="colore_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="operante" id="operante_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codiceinterventoproc" id="codiceinterventoproc_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="interventoproc" id="interventoproc_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codiceintervento" id="codiceintervento_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codicerichiedente" id="codicerichiedente_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="chkcercatutte" id="chkcercatutte_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codicetipomovimento" id="codicetipomovimento_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codiceattivita" id="codiceattivita_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codiceattivitadesc" id="codiceattivitadesc_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codicetipoarchivio" id="codicetipoarchivio_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codicetipoarchiviodesc" id="codicetipoarchiviodesc_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="lavoriestesa" id="lavoriestesa_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="richiedente" id="richiedente_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codicesettore2" id="codicesettore2_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codicesettore2desc" id="codicesettore2desc_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="lavori" id="lavori_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="fkidtipologiaistanza" id="fkidtipologiaistanza_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="fkidtipologiaistanzadesc" id="fkidtipologiaistanzadesc_id"/></td>
		</tr>		
		<tr>			
			<td><spring-form:hidden path="codicecomune" id="codicecomune_id"/></td>
		</tr>		
		<tr>			
			<td><spring-form:hidden path="parametroSoftware" id="software_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="area" id="area_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codicearea" id="codicearea_id"/></td>
		</tr>
	</table>	
</spring-form:form>
<script type="text/javascript">	
	   document.forms["innerForm"].submit();	
</script>

	