<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>		
 	<%-- <form action="${inite:geturltopost(pageContext.request, urlStampe, URL_BACK, null, true)}" name=innerForm method="post" > --%>
	 <spring-form:form commandName="stampaAutorizzazioniHelper" name="innerForm" id="innerForm" action="${inite:geturltopost(pageContext.request, urlStampe, URL_BACK, null, true)}"> 
	 	<table>
		<tr>			
			<td><spring-form:hidden path="nAutorizzazione" id="numaut_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="dataAutorizzazione" id="dataaut_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="dataAutorizzazioneFine" id="dataautfine_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codiceRegistro" id="codReg_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codiceIstanza" id="codIst_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="nominativo" id="nominativo_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="dataPresentazione" id="dataPres_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="dataPresentazioneFine" id="dataPresFine_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codiceStradario" id="codStrad_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="stradario" id="stradario_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="cap" id="cap_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codiceInterventoProc" id="codInvProc_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="interventoProc" id="interventoproc_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codiceProcedura" id="codiceProc_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="codiceComune" id="codiceComune_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="ordinamento" id="ord_id"/></td>
		</tr>
		<tr>			
			<td><spring-form:hidden path="ordinamentoASCDESC" id="tipoOrd_id"/></td>
		</tr>
	</table>	
	 
	 </spring-form:form>
	

	<script type="text/javascript">
		 document.forms["innerForm"].submit();	
	</script>



