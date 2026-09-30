<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
	<fmt:message key="label.nuova_concessione" />
</c:if> <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
	<fmt:message key="label.dettaglio_concessione" />
</c:if></title>
</head>
<body>
<script type="text/javascript">
		var qstring = "codiceIstanza=${param.codiceIstanza}";
</script>
<span class="titoloPagina">
<fmt:message key="label.nuova_concessione" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>	
	<div id="subcontent">
		<spring-form:form commandName="concessioniCommand" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="concessioniCommand" />
		</jsp:include>
		<table width="100%">
			<tr class="titoloSezione">
				<td colspan="4"><fmt:message key="label.concessione_dati_della_concessione" /></td>
			</tr>
			<tr>
					<td>
						<fmt:message key="label.registro" />
						<a name="autorizzazione"></a>
					</td>
					<td colspan="3">
					
						<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">
					 	
							<script type="text/javascript">
								function updateRegistroConcessione(inputField,listItem){
									var a = listItem.id;
									document.getElementById('registro_concessione_id').value = inputField.value;
									document.getElementById('registro_concessione_hidden').value = a;
									$('registro_concessione_id_choices').fade();
									if(document.getElementById('registro_concessione_hidden').value!=''){
										//doSubmit('updateRegistroConecessione.htm?codice=${concessioniCommand.entity.id.codice}&'+qstring+'&codiceRegistro=' + document.getElementById('registro_concessione_hidden').value,'',document.inviodati);
										doSubmit('updateRegistroConecessione.htm?codice=${concessioniCommand.entity.id.codice}&'+qstring+'&codiceAnagrafe='+document.getElementById('titolare_hidden').value+'&codiceRegistro=' + document.getElementById('registro_concessione_hidden').value,'',document.inviodati);
									}
								}
							</script>
							<spring-form:input id="registro_concessione_id" path="autorizzazione.tipologiaregistro.trDescrizione" cssClass="searchbox" onchange="checkValue(this,'registro_concessione_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
							<init:autocompleter methodAjax="findTipologiaRegistri.htm" afterUpdateElement="updateRegistroConcessione"  idHidden="registro_concessione_hidden"  idInput="registro_concessione_id" inputTitleKey="label.ricerca_tipo_registro"/>
							<spring-form:errors	path="autorizzazione.tipologiaregistro.trDescrizione" cssClass="error"	/>
							<spring-form:hidden id="registro_concessione_hidden" path="autorizzazione.tipologiaregistro.id.codice"  />
						 </c:if>
						 <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
						 	<input id="_id_registro" name="_id_registro_concessioni" size="70" disabled="disabled" value="${concessioniCommand.autorizzazione.tipologiaregistro.trDescrizione}"/>	
						</c:if>
					</td>
				</tr>	
	
			
			<%-- QUESTO È IL CASO IN CUI VISUALIZZO UN'AUTORIZZAZIONE ATTIVA --%>
				<c:if test="${concessioniCommand.registroConcessioneProtocollo eq false}">
					<tr>
						<td width="10%"><fmt:message key="label.concessione_numero_concessione" /></td>
						<td>
							<spring-form:input id="entity_id" path="entity.autorizzazioniByFkAutconcAutatt.autoriznumero" size="10" /> 
							<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.autoriznumero" cssClass="error" />
						</td>
						<td>
							<fmt:message key="label.concessione_data_rilascio_concessione" />
						</td>
						<td>	
							<spring-form:input id="autorizdata_id" path="entity.autorizzazioniByFkAutconcAutatt.autorizdata" size="10" onblur="isValidDate(this,true);" />
						 	<init:calendar imagePath="/images/cal.gif" idImage="cal_autorizdata_id" idInput="autorizdata_id" textKey="label.calendar" /> 
						 	<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.autorizdata" cssClass="error"	/>
						</td>
					</tr>
				</c:if>
				<c:if test="${concessioniCommand.registroConcessioneProtocollo eq true}">
					<tr>
						<td colspan="4"><fmt:message key="label.concessione_estremi_da_protocollo" /></td>
					</tr>
					<tr>
						<td><fmt:message key="label.concessione_numero_concessione" /></td>
						<td>
							<input type="text" name="_entity.autorizzazioniByFkAutconcAutatt.autoriznumero" size="10" disabled="disabled" value="" /> 
							<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.autoriznumero" cssClass="error" />
						</td>
						<td><fmt:message key="label.concessione_data_rilascio_concessione" /></td>
						<td>
							<input type="text" name="_entity.autorizzazioniByFkAutconcAutatt.autorizdata" size="10" disabled="disabled" value=""/> 
							<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.autorizdata" cssClass="error" />
						</td>
					</tr>
				</c:if>
				<tr>
					<td><fmt:message key="label.concessione_titolare" /></td>
					<td colspan="3">
						<spring-form:input id="titolare_id" size="70" path="entity.autorizzazioniByFkAutconcAutatt.anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'titolare_hidden')" onkeydown="javascript:return searchAll(this,event)"/> 
						<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="titolare_hidden" idInput="titolare_id" inputTitleKey="label.ricerca_titolare"/>
						<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.anagrafe" cssClass="error" /> <spring-form:hidden id="titolare_hidden" path="entity.autorizzazioniByFkAutconcAutatt.anagrafe.id.codice" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_tipologia_concessione" /></td>
					<td colspan="3">
						<spring-form:select id="selectTipoConcessione" path="entity.concessionitipi.tipoconcessione" onchange="showTipologia(this);">
							<spring-form:option value=""></spring-form:option>
							<spring-form:options items="${concessionitipis}" itemLabel="descrizione" itemValue="tipoconcessione"></spring-form:options>
						</spring-form:select>
						<spring-form:errors path="entity.concessionitipi" cssClass="error" />
					</td>
				</tr>
				<tr id="tipologiaStagionaleDiv">
					<td><fmt:message key="label.concessione_tipologia_stagionale_da" /></td>
					<td>
						<spring-form:input id="stagionaleda_id" path="entity.stagionaledaTransient" size="6" maxlength="5" onblur="isValidPeriod(this,true);" /> 
						<spring-form:errors	path="entity.stagionaleda" cssClass="error"	/></td>
					<td>
						<fmt:message key="label.concessione_tipologia_stagionale_a" /> 
					</td>
					<td>
						<spring-form:input id="stagionalea_id" path="entity.stagionaleaTransient" size="6" maxlength="5" onblur="isValidPeriod(this,true);" />
						<spring-form:errors path="entity.stagionalea" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_scadenza" /></td>
					<td colspan="3">
						<spring-form:input id="scadenza_id" path="entity.autorizzazioniByFkAutconcAutatt.datascadenza" size="10" onblur="isValidDate(this,true);" /> 
						<init:calendar imagePath="/images/cal.gif" idImage="cal_scadenza_id" idInput="scadenza_id" textKey="label.calendar"/> 
						<spring-form:errors path="entity.datascadenza" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.concessione_causale_acquisizione" /></td>
					<td colspan="3">
						<spring-form:select id="selectCausaleAcquisizione" path="entity.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.id.codice">
							<spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
							<spring-form:options items="${concessionicausalisAcq}" itemLabel="descrizione" itemValue="id.codice"></spring-form:options>
						</spring-form:select>
						<spring-form:errors path="entity.autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.id.codice" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><input type="hidden" name="concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.flagAttiva" value="true" /></td>
				</tr>
				
				<tr class="titoloSezione">
					<td colspan="4"><fmt:message key="label.concessione_dati_della_manifestazione" /></td>
				</tr>
					
			    <tr id="mercati">
					<td><fmt:message key="label.manifestazione" /></td>
					<td colspan="3">
						<b>${concessioniCommand.entity.mercati.descrizione}</b>
						<spring-form:hidden id="mercati_hidden" path="entity.mercati.id.codice" />
					</td>
				</tr>
				<%-- 
				<tr id="mercatiUso">
					<td><fmt:message key="label.mercati_uso" /></td>
					<td colspan="3">
						<b>${concessioniCommand.entity.mercatiUso.descrizione}</b>
						<spring-form:hidden path="entity.mercatiUso.id.codice" id="mercatiUso_hidden"/>
					</td>
				</tr>
				--%>
				<c:if test="${concessioniCommand.entity.mercatiUso.id.codice != null}">
				<tr id="mercatiUso">
					<td><fmt:message key="label.mercati_uso" /></td>
					<td colspan="3">
						<b>${concessioniCommand.entity.mercatiUso.descrizione}</b>
						<spring-form:hidden path="entity.mercatiUso.id.codice" id="mercatiUso_hidden"/>
					</td>
				</tr>
				</c:if>
				<c:if test="${concessioniCommand.entity.mercatiUso.id.codice == null}">
				<tr id="mercatiUso">
					<td><fmt:message key="label.mercati_uso" /></td>
					<td colspan="3">
						<script type="text/javascript">
							function filtermercato(element, entry) { 
								return entry + "&codiceMercato=" + document.getElementById("mercati_hidden").value;
							}
						</script>
						<script type="text/javascript">
							function setHiddenFieldmercati(inputField,listItem){
								var a = listItem.id;
								document.getElementById('mercatiUso_id').value = inputField.value;
								document.getElementById('mercatiUso_hidden').value = a;
								assegnaPosteggio();
								}
						</script>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="mercatiUso" />		
							<jsp:param name="propertyPath" value="entity.mercatiUso" />				
							<jsp:param name="pathPropertyDescription" value="entity.mercatiUso.descrizione" />
							<jsp:param name="pathPropertyCode" value="entity.mercatiUso.id.codice" />
							<jsp:param name="autocompleterAjax" value="findMercatiUsoAndMercato.htm" />
							<jsp:param name="autocompleterInputSize" value="50"/>
							<jsp:param name="ajaxCallBack" value="filtermercato"/>
							<jsp:param name="afterUpdateElement" value="setHiddenFieldmercati"/>
							<jsp:param name="titleKey" value="label.giorno" />
						</jsp:include>		
					</td>
				</tr>
				</c:if>
				
				<tr id="mercatiPosteggio">
					<td><fmt:message key="label.posteggio" /></td>
					<td colspan="3">	
					<c:if test="${param.status_msg ne '01'}">
					<spring-form:select id="selectPosteggio" path="entity.mercatiD.id.codice" />	
					</c:if>
					<c:if test="${param.status_msg eq '01'}">
					<b>${concessioniCommand.entity.mercatiD.codiceposteggio}</b>
					</c:if>
					<spring-form:errors path="entity.mercatiD.id.codice" cssClass="error" />
					</td>
				</tr>
					
																	
		</table>
	</spring-form:form>
	</div>
		
	<script type="text/javascript">
	var posteggioSelezionato = '${concessioniCommand.entity.mercatiD.id.codice}';			
	var isEditmode = false;
	<c:if test="${param.status_msg eq '01'}">
	isEditmode = true;
	</c:if>
	function removeOptionSelected(opt){
		  var elSel = document.getElementById(opt);
		  var i;
		  for (i = elSel.length - 1; i>=0; i--) {
		    if (elSel.options[i]) {
		       elSel.remove(i);
		    }
		  }
		}
	
		
		function showTipologia(obj){
					var pos=document.getElementById('selectTipoConcessione').selectedIndex;
					var valore_nascosto = '';
					if(document.getElementById('hidden_tipoconcessione')){
						valore_nascosto = document.getElementById('hidden_tipoconcessione').value;
					}
					
					if (pos>-1 || valore_nascosto!='') {
						var itemSelected = null;
						if(valore_nascosto==''){
							itemSelected = document.getElementById('selectTipoConcessione').options[pos].value;
						}else{
							itemSelected = valore_nascosto;
						}
						if(itemSelected==''){
							hideDiv('tipologiaStagionaleDiv');
							return;
						}
						var call_msg = new Ajax.Request('<%=request.getContextPath()%>/concessionitipi/ajaxIsConcessioneStagionale.htm?tipoconcessione=' + itemSelected,
						{
							method : 'post',
							onSuccess : function(transport) {
								var risposta = transport.responseText;
								showHidestagionaleDiv(risposta);
							},
							onFailure : function(transport) {
								var responseTexts = transport.responseText;
								alert(responseTexts);
							}
						});
	
			}
		}
			
	
		function showHidestagionaleDiv(isStagionale) {
			if (isStagionale == "true") {
				document.getElementById("tipologiaStagionaleDiv").style.display = "";
			} else {
				document.getElementById("tipologiaStagionaleDiv").style.display = "none";
			}
		}
		
		if(!isEditmode){
			if (document.getElementById("mercati_hidden")!=null && document.getElementById("mercati_hidden").value != '') {
				assegnaPosteggio();
			}
		}
		
		showTipologia(document.getElementById('selectTipoConcessione'));
		
		function assegnaPosteggio(){
			removeOptionSelected("selectPosteggio");
			var idMercato=document.getElementById("mercati_hidden").value;
			var idGiorno=document.getElementById("mercatiUso_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findPosteggiNonAssegnati.htm', {
				  method: 'post',
				  parameters: {codiceMercato: idMercato, codiceMercatiUso: idGiorno ,limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("mercatiPosteggio").appear();
					  var opts=response.split("-SEP-");
					  var itemSelected = false;
 				      $('selectPosteggio').options[$('selectPosteggio').options.length] = new Option('<fmt:message key="label.select.default" />','');
					  for(var i=0;i<((opts.length)-1); i++ ){
						  if(posteggioSelezionato == opts[i]){
							  itemSelected = true; 
						  }else{
							  itemSelected = false;
						  }
	 				      $('selectPosteggio').options[$('selectPosteggio').options.length] = new Option(opts[i+1],opts[i],false,itemSelected);
						  i++;
					  }
				    },
				  onFailure: function(transport){  
				    printResult(transport, "Errore durante la ricerca dei posteggi");
				  }
			});
		}


		function inserisci(){
			if(convalida()){
				doSubmit('insertConcessione.htm?codiceIstanza=${param.codiceIstanza}&decorator=popup','',document.inviodati);
			}
		}
	

		function convalida(){
			if(document.getElementById('selectPosteggio')){
				if(document.getElementById('selectPosteggio').value==''){
					alert('Selezionare un posteggio');
					document.getElementById('selectPosteggio').focus();
					return false;
				}
			}
			if(document.getElementById('flag_attiva_id')){
				if(document.getElementById('flag_attiva_id').checked==false){
					if(document.getElementById('selectCausaleCessazione').value==''){
						alert('<fmt:message key="field.required" />');
						document.getElementById('selectCausaleCessazione').focus();
						return false;
					}
					if(document.getElementById('cessazione_id').value==''){
						alert('<fmt:message key="field.required" />');
						document.getElementById('cessazione_id').focus();
						return false;
					}
				}
			}			
			return true;
		}
		function aggiornaParent(){
			window.opener.jQuery("#conc_table${param.codiceIstanza}").append("<tr><td>${concessioniCommand.entity.mercatiD.codiceposteggio}</td><td align='right'><a href=\"javascript:historySet(urlback,'../autorizzazioni/viewConcessione.htm?codiceIstanza=${param.codiceIstanza}&codiceAutorizzazione=${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.id.codice }', '')\">${concessioniCommand.entity.autorizzazioniByFkAutconcAutatt.transientEstremiAut}, ${concessioniCommand.entity.mercatiUso.descrizione}</a></td></tr>");
			window.opener.jQuery("#nuova_conc${param.codiceIstanza}").html("");
			self.close();
		}
	</script>
<div id="functions">
<ul>	
	<c:if test="${param.status_msg ne '01'}">
	<li><a href="javascript:inserisci();"><fmt:message key="button.insert" /></a></li>
	<li><a href="javascript:self.close();"><fmt:message key="button.back" /></a></li>	
	</c:if>
	<c:if test="${param.status_msg eq '01'}">
	<li><a href="javascript:aggiornaParent();"><fmt:message key="button.back" /></a></li>
	</c:if>
</ul>
</div>
</body>
</html>