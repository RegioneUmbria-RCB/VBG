<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${istanzeoneri.id.codice==null}">
			<fmt:message key="label.nuovo_onere.title" />
		</c:if> 
		<c:if test="${istanzeoneri.id.codice!=null}">
			<fmt:message key="label.dettaglio_onere.title" />
		</c:if>
	</title>
    <script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-dettaglio-posizione-debitoria/vbg-dettaglio-posizione-debitoria.js?<%=vJS %>" defer></script>
</head>
	
	<%
	    String dispayInfoPagamenti="";
		String dispayEndo_and_Amminitrazione="";
		String dispayFlagEntra_Uscita="";
		String dispayPercentuale_ribasso="";
		String dispayImportoIstruttoria="display:none";
		
	    if(request.getAttribute("pagato")==null)
	    {
			 dispayInfoPagamenti="display:none";
	    }
	    
	    if((Boolean)request.getAttribute("viewEndoAndAmministrazione").equals(false))
	    {
			dispayEndo_and_Amminitrazione ="display:none";
	    }else
	    {
			dispayEndo_and_Amminitrazione ="";
	    }
	    if((Boolean)request.getAttribute("entrata_uscita").equals(false))
	    {
		      dispayFlagEntra_Uscita="";
		      dispayPercentuale_ribasso="";
	    }else
	    {
		     dispayFlagEntra_Uscita="display:none";
		     dispayPercentuale_ribasso="display:none";
	    }
	    if(request.getAttribute("isMostraImportoIstruttoria").equals(true))
	    {
			dispayImportoIstruttoria="";
	    }
		pageContext.setAttribute("_dispayImportoIstruttoria", request.getAttribute("isMostraImportoIstruttoria"));
	    

	%>
<body>
	<span class="titoloPagina">
		<c:if test="${istanzeoneri.id.codice==null}">
			<fmt:message key="label.nuovo_onere.title" />
		</c:if> 
		<c:if test="${istanzeoneri.id.codice!=null}">
			<fmt:message key="label.dettaglio_onere.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="istanzeoneri" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzeoneri" />
		    </jsp:include>
		    <c:import url="/ajax/dettaglioIstanza.htm">
				<c:param name="codIstanza">${istanza.id.codice}</c:param>
	 		</c:import>
			<table width="100%" >			
			<c:if test="${istanzeoneri.id.codice!=null}">
				<tr>				
					<td>
						<fmt:message key="label.numero_rata"/>						
					</td>			
					 <c:choose>
				 	 	<c:when test="${empty istanzeoneri.guidRateizzazione && empty istanzeoneri.datapagamento && mostra_salva_elimina}">
				 	 		<td>
				 	 			<spring-form:input path="numerorata" size="10"  />
				 	 		</td>
				 	 	</c:when>
				 	 	<c:otherwise>
				 	 		<td><input id="nrata_id" type="text" maxlength="3" value="${istanzeoneri.numerorata}" size="10" readonly="true"/></td>
				 	 	</c:otherwise>
				 	 </c:choose>				
				</tr>
				</c:if>
				<tr>
					<td>
						<fmt:message key="label.causale" />
					</td>
					<c:if test="${istanzeoneri.id.codice==null}">
						<td>
						 	<jsp:include page="../includes/causaleonereSearch.jsp" >
								<jsp:param name="idElemento" value="tipoCausaleInputId" />
								<jsp:param name="pathCausaleonere" value="tipicausalioneri" />
								<jsp:param name="causaleonereInputSize" value="67"/>							
							</jsp:include>
						</td>
					</c:if>
					<c:if test="${istanzeoneri.id.codice!=null}">
						<td>
							<spring-form:input   path="tipicausalioneri.coDescrizione" size="55" readonly="true" />
							<c:if test="${istanzeoneri.nrDocumento ne null}">
								(<fmt:message key="label.numero_documento"/>:&nbsp<b>${istanzeoneri.nrDocumento}</b>)
							</c:if>
						</td>
					</c:if>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.entrata_uscita" />
					</td>
					<c:if test="${istanzeoneri.id.codice==null}">
					
					<td id="id_flag_entrata">
					 <spring-form:checkbox id="_id_flagEntrataUscita" path="flentratauscita" value="1" disabled="true"/>
					 	<fmt:message key="label.entrata" />
					</td>
					
					<td id="id_flagentrata_uscita" style="<%=dispayFlagEntra_Uscita%>">
						<spring-form:checkbox id="id_flagEntrataUscita" path="flentratauscita" value="1" onchange="changeEntrataUscita()"/>
						<fmt:message key="label.entrata"/>
					</td>
					</c:if>
					<c:if test="${istanzeoneri.id.codice!=null}">
						<td><spring-form:checkbox id="id_flagEntrataUscita" path="flentratauscita" disabled="true"/>
							<c:if test="${istanzeoneri.flentratauscita eq true }">
								<fmt:message key="label.entrata"/>
							</c:if>
							<c:if test="${istanzeoneri.flentratauscita eq false }">
								<fmt:message key="label.uscita"/>
							</c:if>		
						</td>
					</c:if>
				</tr>
								
				<c:if test="${istanzeoneri.flagNondovuto }">
					<tr>
						<td></td>
						<td>
							<!-- <input id="id_flagNondovuto" name="flagNondovuto" disabled="disabled" type="checkbox" value="true" checked="checked"> -->
							<b>NON DOVUTO</b> &nbsp;<i>(Dichiarazione effettuata durante la presentazione della domanda online)</i>
						</td>
					</tr>
				</c:if>
				
				<c:if test="${istanzeoneri.id.codice==null}">
				<tr  id="id_tr_endo" style="<%=dispayEndo_and_Amminitrazione%>" >
					<td>
						<fmt:message key="label.endoprocedimento" />
					</td>
					<td >
						<script type="text/javascript">
					    	
					    	function inizializzaAmministrazione(inputField,listItem){
					    		var a = listItem.id;
					    		document.getElementById('inventarioprocedimenti_hidden').value = a;
								new Ajax.Request(
								'${pageContext.request.contextPath}/istanzeoneri/ajaxAmministrazioneByEndoprocedimento.htm?codiceEndo='+ a,
								{
									method : 'post',
									onSuccess : function(transport) {
								    var response = transport.responseText;
								    var valori=response.split(",");
								    document.getElementById('amministrazioni_hidden').value = valori[1];
									document.getElementById("amministrazioni_id").value=valori[0];	
									},
									onFailure : function(transport) {
										var response = transport.responseText;
										alert(response);
									}
									});
								$('id_tr_importo_istruttoria').style.display = '';
							}							
						</script>	
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="inventarioprocedimenti" />		
							<jsp:param name="propertyPath" value="inventarioprocedimenti" />				
							<jsp:param name="pathPropertyDescription" value="inventarioprocedimenti.procedimento" />
							<jsp:param name="pathPropertyCode" value="inventarioprocedimenti.id.codice" />
							<jsp:param name="autocompleterAjax" value="findIstanzeprocedimentiEndo.htm?codiceIstanza=${istanzeoneri.istanza.id.codice }" />	
							<jsp:param name="afterUpdateElement" value="inizializzaAmministrazione" />
							<jsp:param name="titleKey" value="label.ricerca_endoprocedimento" />
							<jsp:param value="autocompleterInputSize" name="55"/>
						</jsp:include>	
					</td>	
				</tr>
				<tr id="id_tr_amministrazione" style="<%=dispayEndo_and_Amminitrazione%>">
					<td>
						<fmt:message key="label.amministrazione" />
					</td>
					<td>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioni" />		
							<jsp:param name="propertyPath" value="amministrazioni" />				
							<jsp:param name="pathPropertyDescription" value="amministrazioni.amministrazione" />
							<jsp:param name="pathPropertyCode" value="amministrazioni.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=true" />	
							<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
							<jsp:param value="autocompleterInputSize" name="55"/>
						</jsp:include>	
					</td>	
				</tr>
				</c:if>
				<c:if test="${istanzeoneri.id.codice!=null && istanzeoneri.tipicausalioneri != null && istanzeoneri.tipicausalioneri.coSerichiedeendo==true}">
					<tr>
						<td><fmt:message key="label.endoprocedimento" /></td>	
						<td><spring-form:input path="inventarioprocedimenti.procedimento" size="55" readonly="true" /></td>
					</tr>
					<tr>
						<td><fmt:message key="label.amministrazione" /></td>
						<td><spring-form:input path="amministrazioni.amministrazione" size="55" readonly="true" /></td>
					</tr>
				</c:if>
				<tr>
					<td>
						<fmt:message key="label.data_registrazione" />
					</td>
					<td>
						<spring-form:input  id="dataRegistrazione_id" path="data" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldataregistrazione" idInput="dataRegistrazione_id" textKey="label.calendar"/>
						<spring-form:errors path="data" cssClass="error"/>  
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.movimento_scadenza" />
					</td>
					<td>
					 	<jsp:include page="../includes/tipimovimentosearch.jsp" >
							<jsp:param name="idElemento" value="tipoMovimentoInputId" />
							<jsp:param name="pathTipomovimento" value="tipomovimento" />
							<jsp:param name="tipimovimentoInputSize" value="55"/>							
						</jsp:include>		
					</td>
				</tr>

				<tr>
					<td>
						<fmt:message key="label.data_scadenza" />
					</td>
					<td>
						<spring-form:input  id="dataScadenza_id" path="datascadenza" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatascadenza" idInput="dataScadenza_id" textKey="label.calendar"/>
						<spring-form:errors path="data" cssClass="error"/>  
					</td>
				</tr>

				<tr>
					<td>
						<fmt:message key="label.importo_causale" />
					</td>
					<td>
						<spring-form:input id="prezzo_id" path="prezzo" cssStyle="text-align:right;" size="10" maxlength="10" onblur="checkNumberValue(this);" onchange="changeImportopagato();"/>
						<spring-form:errors path="prezzo" cssClass="error"/>
						<fmt:message key="label.valore_interesse_onere" />
						<spring-form:input readonly="true" path="importoInteresse" size="10" maxlength="10" />
						<init:help idHelp="help_importoInteresse_id" textKey="help.dettaglio_importo_interesse"/>
					<span id="id_td_perc_ribasso" style="<%=dispayPercentuale_ribasso%>">
						<fmt:message key="label.ribasso" />
						<spring-form:input id="percribasso_id" path="percribasso" cssStyle="text-align:right;" size="10" maxlength="10" onblur="checkNumberInt(this);"/>
						<spring-form:errors path="percribasso" cssClass="error"/>
						<spring-form:checkbox id="id_flribasso" path="flribasso" value="1" onclick="changeImportopagato();"/>
						<fmt:message key="label.descrizione_ribasso" />  
					</span>
					</td>
				</tr>

				<tr id="id_tr_importo_istruttoria" style="<%=dispayImportoIstruttoria%>">
					<td>
						<fmt:message key="label.importo_istruttoria" />
					</td>
					<td>
						<spring-form:input id="prezzoistruttoria_id" path="prezzoistruttoria" cssStyle="text-align:right;" size="10" maxlength="10" onblur="checkNumberValue(this);" onchange="changeImportopagato()"/>
						<spring-form:errors path="prezzoistruttoria" cssClass="error"/>  
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.data_pagamento" />
					</td>
					
					<%-- <td>
						<spring-form:input  id="dataPagamento_id" path="datapagamento" size="10" maxlength="10" onblur="isValidDate(this,true);"  onchange="viewDettaglioPagamento(this);changeImportopagato();" />
						<init:calendar imagePath="/images/cal.gif" idImage="caldatapagamento" idInput="dataPagamento_id" textKey="label.calendar"/>
						<spring-form:errors path="datapagamento" cssClass="error"/>  
					</td> --%>
					
					  
					 
					
					 <td>

						<c:choose>
					 	<c:when test="${not empty istanzeoneri.istoneriDettPosizioni || not empty istanzeoneri.bollGestIstanzeoneris }">
					 		<spring-form:input  id="dataPagamento_id" path="datapagamento" size="10" maxlength="10" onclick="viewDettaglioPagamento(this);" onblur="isValidDate(this,true);"  onchange="viewDettaglioPagamento(this);changeImportopagato();"  readonly="readonly"/>					 		
					 	</c:when>
					 	<c:otherwise>
					 		<spring-form:input  id="dataPagamento_id" path="datapagamento" size="10" maxlength="10" onclick="viewDettaglioPagamento(this);" onblur="isValidDate(this,true);"  onchange="viewDettaglioPagamento(this);changeImportopagato();" />					 	
						 	<a id="caldatapagamento" href="javascript:void(0)" title="Calendario"> <img src="/backend/images/cal.gif" alt="Calendario"/></a>					 	
					 	</c:otherwise>					 
					 </c:choose>
						 
					</td> 
					<script type="text/javascript"> 
						RANGE_CAL_1 = new Calendar({
							inputField: "dataPagamento_id",
							dateFormat: "%d/%m/%Y",
							trigger: "caldatapagamento",
							bottomBar: false,
							onSelect: function() {
						var date = Calendar.intToDate(this.selection.get());
						this.hide();
						changeImportopagato();
						viewDettaglioPagamento('dataPagamento_id');
					
					}
					})
					</script>
				</tr>	
				
				<tr id="info_pagamamento_importo_id" style="<%=dispayInfoPagamenti%>">
					<td>
						<fmt:message key="label.importo_pagato" />
					</td>
					<td>
					<c:choose>
						<c:when test="${not empty istanzeoneri.istoneriDettPosizioni || not empty istanzeoneri.bollGestIstanzeoneris }">
							<spring-form:input id="importopagato_id" path="importopagato" cssStyle="text-align:right;" size="10" maxlength="10" onblur="checkNumberValue(this);" readonly="true"/>
						</c:when>
						<c:otherwise>
							<spring-form:input id="importopagato_id" path="importopagato" cssStyle="text-align:right;" size="10" maxlength="10" onblur="checkNumberValue(this);"/>
						</c:otherwise>
					
					</c:choose>
						
						<spring-form:errors path="importopagato" cssClass="error"/>  
					</td>
				</tr>
				<tr id="info_pagamamento_modalita_id" style="<%=dispayInfoPagamenti%>">
					<td>
						<fmt:message key="label.modalita_pagamento" />
					</td>
					<td>
					<c:choose>
						<c:when test="${not empty istanzeoneri.istoneriDettPosizioni || not empty istanzeoneri.bollGestIstanzeoneris }">
							<spring-form:select id="modalitapagamento_id" path="tipimodalitapagamento.id.codice" disabled="true"> 
							<spring-form:option value="" ><fmt:message key='label.seleziona'/></spring-form:option>
							<spring-form:options items="${tipimodalitapagamentos}" itemLabel="mpDescrestesa" itemValue="id.codice" />
						</spring-form:select>
						</c:when>
						<c:otherwise>
						 	<spring-form:select id="modalitapagamento_id" path="tipimodalitapagamento.id.codice"> 
							<spring-form:option value="" ><fmt:message key='label.seleziona'/></spring-form:option>
							<spring-form:options items="${tipimodalitapagamentos}" itemLabel="mpDescrestesa" itemValue="id.codice" />
						</spring-form:select>
						</c:otherwise>
					
					</c:choose>
						
					</td>
				</tr>
				<tr id="info_pagamamento_doc_rif_id" style="<%=dispayInfoPagamenti%>">
					<td>
						<fmt:message key="label.riferimento_documento" />
					</td>
					<td>
					<c:choose>
						<c:when test="${not empty istanzeoneri.istoneriDettPosizioni || not empty istanzeoneri.bollGestIstanzeoneris }">
							<spring-form:input id="docriferimento_id" path="docriferimento"  size="60" readonly="true" />
						</c:when>
						<c:otherwise>
							<spring-form:input id="docriferimento_id" path="docriferimento"  size="60" />
						</c:otherwise>
					</c:choose>	
						<spring-form:errors path="docriferimento" cssClass="error"/>  
					</td>
				</tr>

				<vbg-fetch-ref id='data-by-id' method='get' response-format='json' request-format='form'
                    url='../dettposizionedebitoria/ajaxDettaglioPosizione.htm'>
                </vbg-fetch-ref>

				<c:if test="${not empty istanzeoneri.istoneriDettPosizioni}">
					<tr id="stato_posizione_debitorio">
						<td><fmt:message key="label.istanzeoneri.stato_posizione_debitoria" /></td>
						<td>
						<c:forEach items="${ istanzeoneri.istoneriDettPosizioni}" var="istoneredettaglio">
								<vbg-dettaglio-posizione-debitoria
									id-posizione="${istoneredettaglio.dettPosizioneDebitoria.id.codice}"
									fetch-ref='data-by-id'></vbg-dettaglio-posizione-debitoria>
						</c:forEach> 
						</td>
					
					</tr>
				</c:if>

				<c:if test="${not empty istanzeoneri.bollGestIstanzeoneris }">
					<c:forEach items="${istanzeoneri.bollGestIstanzeoneris}" var="posBollettazione">	
						<c:if test="${not empty posBollettazione.bollGestDettaglio.dettPosizioneDebitoria}">
							<tr id="stato_posizione_debitorio">
								<td><fmt:message key="label.istanzeoneri.stato_posizione_debitoria_in_bollettazione" /></td>
								<td>
									<vbg-dettaglio-posizione-debitoria id-posizione="${posBollettazione.bollGestDettaglio.dettPosizioneDebitoria.id.codice}" fetch-ref='data-by-id'>
	                    			</vbg-dettaglio-posizione-debitoria>	
								</td>							
							</tr>
						</c:if>
						<c:if test="${not empty posBollettazione.bollGestDettaglio.bollGestDettRate}">
							<tr id="stato_posizione_debitorio">
								<td><fmt:message key="label.istanzeoneri.stato_posizione_debitoria_in_bollettazione" /></td>
								<td>
									<c:forEach items="${posBollettazione.bollGestDettaglio.bollGestDettRate}" var="rata">
										<c:if test="${not empty rata.dettPosizioneDebitoria}">
											<vbg-dettaglio-posizione-debitoria mostra-testo="false" id-posizione="${rata.dettPosizioneDebitoria.id.codice}" fetch-ref='data-by-id'></vbg-dettaglio-posizione-debitoria>
										</c:if>
									</c:forEach>
								</td>
						</c:if>
					</c:forEach>
				</c:if>
				
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea  id="note_id" path="note" rows="10" cols="69" />
						<spring-form:errors path="note" cssClass="error"/>  
					</td>
				</tr>
				
				
				
			</table>
			
			<script type='text/javascript'>
			
			(function($) {

		    	
    			$(document).ready(function(){
    		    	
    				changeEntrataUscita();
					$('#form-invia-nodopagamento').hide();
					
    				if (${istanzeoneri.id.codice == null || not empty istanzeoneri.istoneriDettPosizioni}) {
    	    			$('#cmd-elimina').hide();
    	    			
    	    		}						
    				else {
    					$('#cmd-elimina').show();

    				}
    				
    				var cmdInviaNodopagamenti = $('#cmd-invia-nodopagamenti');
		    		cmdInviaNodopagamenti.on('click',function(e){
		    			popup();
		    			let includeObbligatoInSolido = document.getElementById('scelta_obbligato_in_solido');
		    			includeObbligatoInSolido.addEventListener('change',(event)=>{
		    				let x = event.target.value;
		    				let y = document.getElementById('lista_obbligati_in_in_solido');
		    				if (x === "Si") {
		    					y.style.display = "block";
								
							}else{
								let z = document.getElementsByClassName("soggetti_collegati");
								
								
								for (var i = 0; i < z.length; i++) {
									if (z.item(i).checked) {
										z.item(i).checked = false;		
									}
									
									
								}
								y.style.display = "none";
								
							}
		    				console.log(x);
		    			});
		    			e.preventDefault();
		    			
		    		});

		    		var cmdAnnullaPosizioneDebitoria = $('#cmd-annulla-posizione-debitoria');
		    		
			    	cmdAnnullaPosizioneDebitoria.on('click',function(e) 
					{
			    		e.preventDefault();
						if( confirm("Stai per annullare la posizione. Procedi?") )
						{
				    		callAsync(
							{
				    			url:"annullaPosizioneDebitoria.htm",
				    			data:{
				    				idIstanzaOnere:'${istanzeoneri.id.codice}'
				    			},
				    			success: function(data)
				    			{
				    				if(data.toUpperCase() == "OK")
				    				{
				    					$('#cmd-annulla').hide();
				    					$('#cmd-elimina').show();
				    					location.reload();
			    					}
			    				 }
							});
						}
					});
			    	
			    	var datapagamento = $('#dataPagamento_id');
			    	if (datapagamento.val() != "") {
			    		viewDettaglioPagamento2();	
					}
    			});

		    	
		    	function popup(){
		    		var codiceIstanza = ${istanzeoneri.istanza.id.codice};
		    		var codiceTipiCausaliOnere = '${istanzeoneri.tipicausalioneri.id.codice}';
		    		var isOnereRateizzato = ${istanzeoneri.flagOnereRateizzato};
		    		
		    		var popupInviaNodoPagamenti = new PopupInviaNodoPagamenti($('#form-invia-nodopagamento'));
		    		
		    		popupInviaNodoPagamenti.mostra(codiceIstanza,codiceTipiCausaliOnere,isOnereRateizzato).then(function onSuccess(e) {  
					    console.log('success, ', e);
						
					  },
						  
					  function onFail(){
						  console.log('fail');
					  });    		
		    	}
		    	
		    	
		    	function callAsync(options) {
		    		
		    		disableFunctions();
		    		
		    		return jQuery.ajax({	
		    			url : options.url,
		    			data : options.data,
		    			method : 'POST',
		    			type : 'POST', // For jQuery < 1.9
		    			context : document.body,
		    			cache : false,
		    			dataType : options.dataType || "html",
		    			success : options.success,
		    			error : options.error || gestisciErrore
		    		}).always(enableFunctions);
		    	}
		    	function gestisciErrore(jqXHR, textStatus, errorThrown) {
					console.error([ jqXHR, textStatus, errorThrown ]);
					// TODO Mostrare a video
					alert("Si è verificato un errore durante l'escuzione.");

				}
		    	
	
		    	
		    })(jQuery);
			
			function viewDettaglioPagamento2()
			{
					$('info_pagamamento_importo_id').style.display = '';
					$('info_pagamamento_modalita_id').style.display = '';
					$('info_pagamamento_doc_rif_id').style.display = '';			
			}
				
				
			    function viewDettaglioPagamento(obj)
				{
					if(obj.value!="")
					{
						$('info_pagamamento_importo_id').style.display = '';
						$('info_pagamamento_modalita_id').style.display = '';
						$('info_pagamamento_doc_rif_id').style.display = '';			
					}else
					{
						$('info_pagamamento_importo_id').style.display = 'none';
						$('info_pagamamento_modalita_id').style.display = 'none';
						$('info_pagamamento_doc_rif_id').style.display = 'none';
						
					}
				}
			    
			    function changeEntrataUscita()
			    {
			    	if(document.getElementById("id_flagEntrataUscita").checked==false)
			    	{
			    		$('id_td_perc_ribasso').style.display = '';
			    		$('id_flribasso').style.display = '';
			    		$('id_tr_importo_istruttoria').style.display = 'none';
			    		$('prezzoistruttoria_id').value='';
			    	}else
			    	{
			    		$('id_td_perc_ribasso').style.display = 'none';
			    		$('percribasso_id').value='';
			    		if(${_dispayImportoIstruttoria} == false)
			    		{
				    		if(document.getElementById('inventarioprocedimenti_hidden') && document.getElementById('inventarioprocedimenti_hidden').value!='')
				    		{
				    			$('id_tr_importo_istruttoria').style.display = '';
				    		}
			    		}else // Forzo il tr "id_tr_importo_istruttoria" ad essere visibile
			    		{
			    			$('id_tr_importo_istruttoria').style.display = '';	
			    		}
			    		$('id_flribasso').checked=false;
			    	}
			    }
			    
			   
			    function changeImportopagato()
			    {
			    	
			    	var onereCausale=0;
			    	var onereIstruttoria=0;
			    	if(document.getElementById('prezzo_id').value!='')
			    	{
			    		onereCausale=document.getElementById('prezzo_id').value;
			    		onereCausale=parseFloat(onereCausale.replace(',','.'))
			    	}
			    	if(document.getElementById('prezzoistruttoria_id').value!=''){
			    		onereIstruttoria=document.getElementById('prezzoistruttoria_id').value;
			    		onereIstruttoria=parseFloat(onereIstruttoria.replace(',','.'));
			    	}
			    	
			    	var onereDaPagare=onereCausale+onereIstruttoria;
			    	if(document.getElementById('dataPagamento_id').value!='')
			    	{
			    		if(document.getElementById("id_flribasso").checked==true)
			    		{
			    			var percRib=100;
			    			if(document.getElementById("percribasso_id").value!='')
			    			{
			    				percRib=document.getElementById("percribasso_id").value;
			    				var percRib_value=(onereDaPagare/100)*percRib;
			    				onereDaPagare=onereDaPagare - percRib_value;
			    			}
			    		}
			    		var totPagato = Math.round(onereDaPagare*100)/100;
			    		totPagato = (totPagato + "").replace('.',',');
			    		$('importopagato_id').value = totPagato;
			    	}
			    	
			    }
			    vbg.ready(function(){
					function aggiungiEventoReload(){
						let det = document.querySelectorAll('vbg-dettaglio-posizione-debitoria');
						det.forEach((vbgDettaglioPosizione)=>{
							vbgDettaglioPosizione.addEventListener('stato-modificato',()=>{
								window.vbg.mostraModalCaricamento();
								document.location.reload();
							});
							
						});
						
					}
					
					aggiungiEventoReload();
					
				});
			    
			</script>
			<c:if test="${istanzeoneri.id.codice!=null}">
				<jsp:include page="./popupinvianodopagamento.jsp" >
					<jsp:param name="dispayImportoIstruttoria" value="<%=dispayImportoIstruttoria%>"/>
				</jsp:include>
			</c:if>
			
		</spring-form:form>
	</div>
	
	
	<div id="functions">
		<ul>		
			<c:if test="${istanzeoneri.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mostra_salva_elimina eq true  }">				
				<c:if test="${istanzeoneri.id.codice!=null}">
					<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				</c:if>
			        <li id="cmd-elimina" ><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceIstanza=${istanzeoneri.istanza.id.codice}','')"><fmt:message key="button.back" /></a></li>
			
			<c:if test="${istanzeoneri.flagNondovuto eq false}"> 
				<c:if test="${visualizzaInviaNodoPagamenti}">
						<li><a href="javascript:;"id="cmd-invia-nodopagamenti"><fmt:message key="button.inviaSistemaPagamenti"/></a></li>
				</c:if>
			</c:if>
			
		</ul>
	</div>
</body>
</html>