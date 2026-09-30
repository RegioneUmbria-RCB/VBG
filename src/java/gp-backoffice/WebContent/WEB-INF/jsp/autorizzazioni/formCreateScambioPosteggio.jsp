<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<fmt:message key="label.scambio_posteggi" />
</title>
</head>
<body>
<%--
<script type="text/javascript">
		var qstring = "codiceIstanza=${param.codiceIstanza}";
</script>
--%>

<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
	<jsp:include page="../includes/history.jsp">
   		<jsp:param name="path" value="../autorizzazioni/viewConcessione" />
   	</jsp:include>	
</c:if>
<span class="titoloPagina"> 
	<fmt:message key="label.scambio_posteggi" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<c:import url="/ajax/dettaglioIstanza.htm">
	<c:param name="codIstanza">${param.codiceIstanza}</c:param>
</c:import>
<br class="clear" />	


<div id="subcontent"><spring-form:form commandName="concessioniCommand" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="concessioniCommand" />
	</jsp:include>
	
	<table>
		<tr>
			<td><b><fmt:message key="label.causale_cessazione" /></b></td>
			<td>
				<select id="codice_cessazione_id">
					<option value=""><fmt:message key="label.seleziona" /></option>
			   		<c:forEach items="${concessionicausalisCess}" var="causaliCessazione">
			   			<option value="${causaliCessazione.id.codice}">${causaliCessazione.descrizione}</option>
			   		</c:forEach>
				</select>
			</td>
		</tr>
		
		<tr>
			<td><b><fmt:message key="label.causale_acquisizione" /></b></td>
			<td>
			
			<select id="codice_acquisizione_id">
				<option value=""><fmt:message key="label.seleziona" /></option>
		   		<c:forEach items="${concessionicausalisAcq}" var="causaliAcquisizione">
		   			<option value="${causaliAcquisizione.id.codice}">${causaliAcquisizione.descrizione}</option>
		   		</c:forEach>
			</select>
			
			</td>
	
		</tr>
	</table>
	
	
	<table width="100%">
		<tr>
			<td width="50%">
			<!--  POSTEGGIO PARTENZA  -->
				<table width="100%">
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.dati_posteggio_partenza" /></td>
				</tr>
				<tr>
					<td><fmt:message key="label.posteggio" /></td>
					<td colspan="3">
						<spring-form:input disabled="true" id="posteggio_id" path="concessioneInsert.mercatiD.codiceposteggio" size="70"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.manifestazione" /></td>
					<td colspan="3" class="inline-ui-cell">
						<spring-form:input disabled="true" id="mercati_id" path="concessioneInsert.mercati.descrizione" size="70"	/> 
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.mercati_uso" /></td>
					<td colspan="3">
						<spring-form:input disabled="true" id="mercatiuso_id" path="concessioneInsert.mercatiUso.descrizione" size="70"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.registro" /><a name="autorizzazione"></a></td>
					<td colspan="3"><input id="_id_registro" name="_id_registro_concessioni" size="70" disabled="disabled" value="${concessioniCommand.autorizzazione.tipologiaregistro.trDescrizioneCompleta}"/>	</td>
				</tr>
				<tr>
					<td colspan="4">
					 	<span id="messaggio_no_conc_id1" >&nbsp;</span>
					</td>
				</tr>
				<tr>
					<td width="10%"><fmt:message key="label.concessione_numero_concessione" /></td>
					<td class="inline-ui-cell" style="min-width: 250px;"><spring-form:input id="entity_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autoriznumero" size="10" disabled="true"/></td>
					<td class="inline-ui-cell" ><fmt:message key="label.concessione_data_rilascio_concessione" /></td>
					<td class="inline-ui-cell" > <spring-form:input id="autorizdata_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.autorizdata" size="10" disabled="true" /></td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_titolare" /></td>
					<td colspan="3"><spring-form:input id="titolare_id" size="70" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe.descrizioneRichiedente" disabled="true"/></td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_tipologia_concessione" /></td>
					<td colspan="3"> <spring-form:input id="selectTipoConcessione" size="70" path="concessioneInsert.concessionitipi.descrizione" disabled="true"/></td>
				</tr>
				<tr id="tipologiaStagionaleDiv">
					<td><fmt:message key="label.concessione_tipologia_stagionale_da" /></td>
					<td>
						<spring-form:input id="stagionaleda_id" path="concessioneInsert.stagionaledaTransient" size="6" maxlength="5" disabled="true" />
						<fmt:message key="label.concessione_tipologia_stagionale_a" /> 
						<spring-form:input id="stagionalea_id" path="concessioneInsert.stagionaleaTransient" size="6" maxlength="5" disabled="true" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_scadenza" /></td>
					<td colspan="3"><spring-form:input id="scadenza_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.datascadenza" size="10" disabled="true"/></td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_causale_acquisizione" /></td>
					<td colspan="3">
						<spring-form:input id="selectCausaleAcquisizione" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.descrizione" size="70" disabled="true" />
					</td>
				</tr>
				
				</table>
			</td>
			
			<!--  POSTEGGIO DESTINAZIONE  -->
			<td>
				<table width="100%">
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.dati_posteggio_destinazione" /></td>
				</tr>
				<tr>
					<td><fmt:message key="label.posteggio" /></td>
					<td colspan="3">
					<script type="text/javascript">
					
					function setHiddenFieldmercatiD(inputField,listItem)
						 {
						    clearCampi();
							var a = listItem.id;
						    document.getElementById('posteggioDestinazione_id').value = inputField.value;
						    document.getElementById('posteggioDestinazione_hidden').value = a;
							var call_msg = new Ajax.Request('<%=request.getContextPath()%>/json/getConcessioneByPosteggio.htm?_ts='+new Date().getTime()+'&codicePosteggio='+a+'&codiceUso=${concessioniCommand.concessioneInsert.mercatiUso.id.codice}&codiceMercato=${concessioniCommand.concessioneInsert.mercati.id.codice}', {
								  method: 'post',	
								  onSuccess: function(transport){ 										
									var json = transport.responseText.evalJSON();		
									var scambioPH = json.scambioPosteggioHelper;
									if(scambioPH.autoriznumero!='-1')
									//..
									{
									jQuery("#scadenza_dest_id").val(scambioPH.datascadenza);
									jQuery("#stagionalea_dest_id").val(scambioPH.stagionalea);
									jQuery("#stagionaleda_dest_id").val(scambioPH.stagionaleda);
									jQuery("#tipo_concessione_dest_id").val(scambioPH.descrizioneConcessionitipi);
									//..
									jQuery("#autoriz_data_dest_id").val(scambioPH.autorizdata);
									jQuery("#autoriz_numero_dest_id").val(scambioPH.autoriznumero);
									jQuery("#tipologia_registro_dest_id").val(scambioPH.decsrizioneTipologiaregistro);
									jQuery("#titolare_dest_id").val(scambioPH.titolare);
									//..
									jQuery("#causale_acquisizione_dest_id").val(scambioPH.concessionicausaliAcq);
									
									jQuery("#codice_aut_dest_id").val(scambioPH.codiceAutorizzazioneConcessione);	
									
									}else
									{
										jQuery("#messaggio_no_conc_id").show();
										jQuery("#messaggio_no_conc_id1").show();
										
									}
						  		  },
								  onFailure: function(transport){ 
						  			var responseTexts = transport.responseText;
						  			alert(responseTexts);
							  	  }						    		 
							});
						 }
					
					function clearCampi()
					{
						jQuery("#messaggio_no_conc_id").hide();
						jQuery("#messaggio_no_conc_id1").hide();
						jQuery("#codice_aut_dest_id").val('');
						jQuery("#scadenza_dest_id").val('');
						jQuery("#stagionalea_dest_id").val('');
						jQuery("#stagionaleda_dest_id").val('');
						jQuery("#tipo_concessione_dest_id").val('');
						//..
						jQuery("#autoriz_data_dest_id").val('');
						jQuery("#autoriz_numero_dest_id").val('');
						jQuery("#tipologia_registro_dest_id").val('');
						jQuery("#titolare_dest_id").val('');
						//..
						jQuery("#causale_acquisizione_dest_id").val('');
					}
					
					</script>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="posteggioDestinazione" />		
							<jsp:param name="propertyPath" value="posteggioDestinazione" />				
							<jsp:param name="pathPropertyDescription" value="posteggioDestinazione.codiceposteggio" />
							<jsp:param name="pathPropertyCode" value="posteggioDestinazione.id.codice" />
							<jsp:param name="autocompleterAjax" value="findPosteggioAndMercato.htm?codiceMercato=${concessioniCommand.concessioneInsert.mercati.id.codice}" />
							<jsp:param name="afterUpdateElement" value="setHiddenFieldmercatiD"/>
							<jsp:param name="titleKey" value="label.giorno" />
						</jsp:include>		
					</td>
				</tr>			
				<tr>
					<td><fmt:message key="label.manifestazione" /></td>
					<td colspan="3" class="inline-ui-cell">
						<spring-form:input disabled="true" id="mercati_id" path="concessioneInsert.mercati.descrizione" size="70"	/> 
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.mercati_uso" /></td>
					<td colspan="3">
						<spring-form:input disabled="true" id="mercatiuso_id" path="concessioneInsert.mercatiUso.descrizione" size="70"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
					 	<span id="messaggio_no_conc_id" ><b>Per il posteggio non è presente una concessione</b></span>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.registro" /><a name="autorizzazione"></a></td>
					 <td colspan="3">
						<%--<input id="_id_registro" name="_id_registro_concessioni" size="70" disabled="disabled" value="${concessioniCommand.autorizzazione.tipologiaregistro.trDescrizioneCompleta}"/>	
						--%>
						 <input id="tipologia_registro_dest_id" disabled="disabled"  size="70" />
					</td>
				</tr>
				<tr>
					<td width="10%"><fmt:message key="label.concessione_numero_concessione" /></td>
					<td class="inline-ui-cell" style="min-width: 250px;">
					<%-- <spring-form:input id="entity_id" path="entity.autorizzazioniByFkAutconcAutatt.autoriznumero" size="10" disabled="true"/> --%>
						<input id="autoriz_numero_dest_id" disabled="disabled"  size="10" />
					</td>
					<td class="inline-ui-cell" ><fmt:message key="label.concessione_data_rilascio_concessione" /></td>
					<td class="inline-ui-cell" > 
					<%--<spring-form:input id="autorizdata_id" path="entity.autorizzazioniByFkAutconcAutatt.autorizdata" size="10" disabled="true" /> --%>
						 <input id="autoriz_data_dest_id" disabled="disabled"  size="10" />
				
					<input type="hidden" id="codice_aut_dest_id" />
					</td>
					
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_titolare" /></td>
					<td colspan="3">
						<%-- <spring-form:input id="titolare_id" size="70" path="entity.autorizzazioniByFkAutconcAutatt.anagrafe.descrizioneRichiedente" disabled="true"/> --%>
						<input id="titolare_dest_id" disabled="disabled"  size="70" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_tipologia_concessione" /></td>
					<%-- <td colspan="3"> <spring-form:input id="selectTipoConcessione" size="70" path="entity.concessionitipi.descrizione" disabled="true"/></td> --%>
					<td colspan="3"><input id="tipo_concessione_dest_id" disabled="disabled"  size="70" /></td>
					
				</tr>
				<tr id="tipologiaStagionaleDiv">
					<td><fmt:message key="label.concessione_tipologia_stagionale_da" /></td>
					<td>
						<%-- <spring-form:input id="stagionaleda_id" path="entity.stagionaledaTransient" size="6" maxlength="5" disabled="true" /> --%>
						<input id="stagionaleda_dest_id" disabled="disabled" />
						<fmt:message key="label.concessione_tipologia_stagionale_a" /> 
						<%-- <spring-form:input id="stagionalea_id" path="entity.stagionaleaTransient" size="6" maxlength="5" disabled="true" /> --%>
						<input id="stagionalea_dest_id" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_scadenza" /></td>
					<td colspan="3">
					<%-- <spring-form:input id="scadenza_id" path="entity.autorizzazioniByFkAutconcAutatt.datascadenza" size="10" disabled="true"/> --%>
					<input id="scadenza_dest_id" disabled="disabled" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_causale_acquisizione" /></td>
					<td colspan="3">
						<%--<spring-form:input id="selectCausaleAcquisizione" path="entity.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.descrizione" size="70" disabled="true" /> --%>
					    <input id="causale_acquisizione_dest_id" disabled="disabled"  size="70" />
					</td>
				</tr>
			</table>
			</td>
		</table>
	</spring-form:form>
	
<div id="functions">
<ul>
	<li><a href="javascript:scambio(${concessioniCommand.concessioneInsert.id.codice })"><fmt:message key="button.scambia" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>

<script type="text/javascript">

	 	function scambio(idAutPartenza)
		{
			
			var idconcDest=jQuery("#codice_aut_dest_id").val();
			var codPosteggioD=jQuery("#posteggioDestinazione_hidden").val();
			var codiceCess=jQuery("#codice_cessazione_id").val();
			var codiceAcqu=jQuery("#codice_acquisizione_id").val();
			var errorecausale='';
			var errorePosteggio='';
			if(codPosteggioD=='')
			{errorePosteggio="Attenzione è necessario selezionare un posteggio di destinazione"}
			if(codiceCess=='')
			{errorecausale="Attenzione è necessario selezionare una causale di cessazione"}
			if(codiceAcqu=='')
			{errorecausale="Attenzione è necessario selezionare una causale di acquisizione"}
			if(errorePosteggio!='' || errorecausale!='' )
		    {
				alert(errorecausale+'\n'+errorePosteggio);
				return false;
		    }else
		    {
		     doHref('../autorizzazioni/scambioPosteggio.htm?codiceConcPartenza='+idAutPartenza+'&codiceConcDestinazione='+idconcDest+'&codicePosteggioDestinazione='+codPosteggioD+'&codiceCausaleCessazione='+codiceCess+'&codiceCausaleAcquisizione='+codiceAcqu,'');
		    }
		  }
		
		
</script>

</div>

</body>
</html>