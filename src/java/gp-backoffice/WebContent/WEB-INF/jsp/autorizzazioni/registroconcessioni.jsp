<%@ include file="../includes/taglibs.jsp"%>
<div class="form-group">
	<label><fmt:message key="label.registro" /><a name="autorizzazione"></a></label>				
	<c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.NEW}">					 	
		<script type="text/javascript">
			function updateRegistroConcessione(inputField,listItem){
				let a = listItem.id;
				document.getElementById('registro_concessione_id').value = inputField.value;
				document.getElementById('registro_concessione_hidden').value = a;
				/* $('registro_concessione_id_choices').fade(); */
				if(document.getElementById('registro_concessione_hidden').value!=''){
					doSubmit('updateRegistroConcessione.htm?codice=${concessioniCommand.concessioneInsert.id.codice}&'+qstring+'&codiceAnagrafe='+document.getElementById('titolare_hidden').value+'&codiceRegistro=' + document.getElementById('registro_concessione_hidden').value,'',document.inviodati);
				}
			}
		</script>
		<spring-form:input id="registro_concessione_id" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.tipologiaregistro.trDescrizioneCompleta" cssClass="searchbox" onchange="checkValue(this,'registro_concessione_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
		<init:autocompleter methodAjax="findTipologiaRegistriPerManifestazioni.htm?codicecomune=${concessioniCommand.autorizzazione.istanza.comune.codicecomune}" afterUpdateElement="updateRegistroConcessione"  idHidden="registro_concessione_hidden"  idInput="registro_concessione_id" inputTitleKey="label.ricerca_tipo_registro"/>
		<spring-form:errors	path="concessioneInsert.autorizzazioniByFkAutconcAutatt.tipologiaregistro.trDescrizione" cssClass="error"/>
		<spring-form:hidden id="registro_concessione_hidden" path="concessioneInsert.autorizzazioniByFkAutconcAutatt.tipologiaregistro.id.codice" />	
	 </c:if>
	 <c:if test="${concessioniCommand.displayMode eq concessioniCommand.displayConstants.EDIT}">
	 	<input id="_id_registro" name="_id_registro_concessioni" size="70" disabled="disabled" value="${concessioniCommand.autorizzazione.tipologiaregistro.trDescrizioneCompleta}"/>	
	</c:if>				
</div>