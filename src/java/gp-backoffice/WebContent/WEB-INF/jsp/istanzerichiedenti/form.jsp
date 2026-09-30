<%@ include file="../includes/taglibs.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
<title>	
	<fmt:message key="label.soggetti_collegati_all_istanza" />
</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.soggetti_collegati_all_istanza" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../istanzerichiedenti/view" />
		</jsp:include>
<c:import url="/ajax/dettaglioIstanza.htm">
	<c:param name="codIstanza">${istanzerichiedenti.istanza.id.codice}</c:param>
</c:import>
<br class="clear" />
<div id="subcontent">
	<spring-form:form commandName="istanzerichiedenti" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="istanzerichiedenti" />
    </jsp:include>
	<table>
		<tr>
			<td style="vertical-align: top;"><fmt:message key="label.soggetto_anagrafica" /></td>
			<td colspan="5">				
				<jsp:include page="../includes/anagraficasearch.jsp" >
					<jsp:param name="idElemento" value="richiedenteIdCodice" />						
					<jsp:param name="pathAnagrafica" value="richiedente" />					
					<jsp:param name="codAnagrafeStorico" value="${istanzerichiedenti.richiedentestorico.id.codice}" />
					<jsp:param name="descrizioneAnagrafeStorico" value="${istanzerichiedenti.richiedentestorico.descrizioneRichiedente}" />
					<jsp:param name="dataAnagrafeStorico" value="${istanzerichiedenti.richiedentestorico.datafinevalidita}" />
				    <jsp:param name="codiceIstanza" value="${istanzerichiedenti.istanza.id.codice}" />
				    <jsp:param name="isInUpdate" value="${istanzerichiedenti.id.codice}" />
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.tipo_soggetto" /></td>
			<td colspan="5">
			<script type="text/javascript">
					function setFieldTipisoggetto(inputField, listItem){
							var a = listItem.id;
							//array che contiene l'id dei campi separati da '#'
							//il primo valore è l'id della tabella tipisogegtto e il secondo se deve mostrare o meno la descrizione del soggetto
							var arrayValori=a.split('#');
							document.getElementById('tiposoggetto_id_id').value = inputField.value;
							document.getElementById('tiposoggetto_id_hidden').value = arrayValori[0];
							if(arrayValori[1]=='true'){
								$('TR_DESCRSOGGETTO').appear();
							}else{
								$('TR_DESCRSOGGETTO').fade();
							}
							if(arrayValori[2]=='true'){								
								$('TR_ANAGRAFECOLLEGATA').appear();
							}else{
								$('TR_ANAGRAFECOLLEGATA').fade();								
							}
					}
				</script>
				<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="tiposoggetto_id" />
					<jsp:param name="propertyPath" value="tiposoggetto" />
					<jsp:param name="pathPropertyDescription" value="tiposoggetto.tiposoggetto" />
					<jsp:param name="pathPropertyCode" value="tiposoggetto.id.codice" />
					<jsp:param name="autocompleterAjax" value="findTipisoggettoAndSpecificadescrizioneAndRichiedianagrafecoll.htm" />							
					<jsp:param name="titleKey" value="label.ricerca_tipisoggetto" />
					<jsp:param name="afterUpdateElement" value="setFieldTipisoggetto" />
				</jsp:include>
				
			</td>
		</tr>
		<c:set var="displayDescrSoggetto" value="display: none;" />
		<c:if test="${istanzerichiedenti.tiposoggetto.flgSpecificadescrizione eq true}">
			<c:set var="displayDescrSoggetto" value="" />
		</c:if>
		<tr id="TR_DESCRSOGGETTO" style="${displayDescrSoggetto}">
			<td>&nbsp;</td>
			<td colspan="5">
				<spring-form:input path="descrsoggetto" size="70" />
				<spring-form:errors path="descrsoggetto" cssClass="error"/>
				&nbsp;<fmt:message key="label.specificare_la_tipologia_di_soggetto" />
			</td>
		</tr>				
		<c:set var="displayAnagrafeCollegata" value="display: none;" />
		
			<c:if test="${istanzerichiedenti.tiposoggetto.richiedianagrafecoll eq true}">
				<c:set var="displayAnagrafeCollegata" value="" />
			</c:if>
				
		<tr id="TR_ANAGRAFECOLLEGATA" style="${displayAnagrafeCollegata}">
			<td style="vertical-align: top;"><fmt:message key="label.ragione_sociale" /></td>
			<td colspan="5">

					<jsp:include page="../includes/anagraficasearch.jsp">
						<jsp:param name="idElemento" value="anagrafeCollegataIdCodice" />
						<jsp:param name="pathAnagrafica" value="anagrafeCollegata" />
						<jsp:param name="anagrafeAutocompleterAjax" value="findAnagrafe.htm?tipoAnagrafe=G" />
						<jsp:param name="codAnagrafeStorico" value="${istanzerichiedenti.anagrafeCollegatastorico.id.codice}" />
						<jsp:param name="descrizioneAnagrafeStorico" value="${istanzerichiedenti.anagrafeCollegatastorico.descrizioneRichiedente}" />
						<jsp:param name="dataAnagrafeStorico" value="${istanzerichiedenti.anagrafeCollegatastorico.datafinevalidita}" />					
					   <jsp:param name="codiceIstanza" value="${istanzerichiedenti.istanza.id.codice}" />
				       <jsp:param name="isInUpdate" value="${istanzerichiedenti.id.codice}" />
					</jsp:include>
			</td>
		</tr>
		<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'fldIstRichProcuratore')}">
			<tr>
				<td style="vertical-align: top;"><fmt:message key="label.procuratore" /></td>
				<td colspan="5">
						<jsp:include page="../includes/anagraficasearch.jsp" >
							<jsp:param name="idElemento" value="procuratoreIdCodice" />						
							<jsp:param name="pathAnagrafica" value="procuratore" />
							<jsp:param name="codAnagrafeStorico" value="${istanzerichiedenti.procuratorestorico.id.codice}" />
							<jsp:param name="descrizioneAnagrafeStorico" value="${istanzerichiedenti.procuratorestorico.descrizioneRichiedente}" />
							<jsp:param name="dataAnagrafeStorico" value="${istanzerichiedenti.procuratorestorico.datafinevalidita}" />
						    <jsp:param name="codiceIstanza" value="${istanzerichiedenti.istanza.id.codice}" />
						    <jsp:param name="isInUpdate" value="${istanzerichiedenti.id.codice}" />
						</jsp:include>
					</td>
			</tr>
		
			<c:if test="${istanzerichiedenti.procuratore.id.codice!=null}">
			<tr>
				<td><fmt:message key="label.oggetto" /></td>
				<td colspan="5">				
					<jsp:include page="../includes/oggetti.jsp" >
		       				<jsp:param name="idElemento" value="oggettoIdCodice" />
		   					<jsp:param name="codiceOggetto" value="${istanzerichiedenti.oggettoProcuratore.id.codice}" />
		   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
		   					<jsp:param name="nomefileId" value="oggetto_nomefile" />
		   					<jsp:param name="codiceIstanza" value="${istanzerichiedenti.istanza.id.codice}" />
		   					<jsp:param name="parametroLogOperazione" value="1" />
		   			</jsp:include>
					<spring-form:hidden path="oggettoProcuratore.id.codice" id="oggetto_id_codice"/>
	   				<spring-form:hidden path="oggettoProcuratore.nomefile" id="oggetto_nomefile"/>
	   				<spring-form:errors path="oggettoProcuratore" cssClass="error"/>	
				</td>
			</tr>
			</c:if>
		</c:if>
	</table>
	<script type='text/javascript'>
	
	
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${istanzerichiedenti.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${istanzerichiedenti.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>