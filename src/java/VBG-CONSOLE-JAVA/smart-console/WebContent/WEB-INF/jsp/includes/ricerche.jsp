<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="bsh.StringUtil"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 
	sezione per la visualizzazione delle ricerche salvate 
	deve essere inclusa nel form di ricerca utilizzando il codide sotto
	<jsp:include page="../includes/ricerche.jsp" />
	inoltre fuori dal form di ricerca devono essere presenti due campi hidden.
	il primo con attributo value formato dal
	path della cartella che contiene la jsp più il nome della jsp senza il suffisso .jsp
	il secondo con attributo value pari all'attributo id del form di ricerca (per form spring pari al commandName
	<input type="hidden" id="chiave_ricerca" value="cartella_nomejsp" />
	<input type="hidden" id="nome_form_ricerca" value="filter" />
--%>

<c:set var="_ricerca_attivita" value="0" />
<c:if test="${not empty param.ricerca_attivita}">
	<c:set var="_ricerca_attivita"
		value="${param.ricerca_attivita}" />
</c:if>

<c:set var="_ricerca_selezionata" value="-1" />
<c:if test="${not empty param.ricerca_selezionata}">
	<c:set var="_ricerca_selezionata"
		value="${param.ricerca_selezionata}" />
</c:if>



<script type="text/javascript">
//<!--

	
    
	var nome_form_ricerca = $('nome_form_ricerca').value;
	function recuperaRicerche(index){
	 	new Ajax.Request('${pageContext.request.contextPath}/jsonricerche/list.htm', { 
		 	method:'post',
		 	parameters:{chiave_ricerca : $('chiave_ricerca').value}, 
  			onSuccess: function(transport){
  			 	var json = transport.responseText.evalJSON();		 	
      			var lista = json.ricerche;
      			var combo = clearSelect($('ricerche'));
      			for(var i = 0;i<lista.length;i++){
      				var option = document.createElement("option");
      				option.text = lista[i].descrizione;
      				option.value = lista[i].id.codice;
      				try {
      					combo.add(option, null); // Standard
      				}catch(error) {
      					combo.add(option); // IE only
      				}   				
      			}
      			// Questa parte di codice è utilizzata solo nel caso di ricerche di attività.
      			// Gestisce le regole di visualizzazione in quanto, nelle ricerche delle
      			// attività la scelta di una ricerca salvata fa il reload della pagina
      			if(index !="-1")
      			{
      			    // setta sulla combo box la ricerca selezionata
      				combo[index].selected=true;
      			    // Imposta il codice della ricerca selezionata per eventuali aggiornamenti
      				$('id_ricerca_selezionata').value=combo[index].value;
      				// Imposta la descrizione della ricerca
      			    $('desc_ricerca').value=combo[index].text;
      				// Imposta il flag globale della ricerca
      				if(lista[index-1].flagGlobale == 'true')
      				{
      					$('globale_ricerca').checked=true;
      				}
      				// Mostra o nasconde la sezione function secondo la regola;
      				// 1- ricerca creata dall'operatore loggato 	: visibile
      				// 2- ricerca non creata dall'operatore loggato : non visibile
      				if(lista[index-1].codiceresponsabile == <spring-security:authentication property="principal.codiceResponsabile" />)
      				{$('functions').style.display='';}
      				else
      				{$('functions').style.display='none';}
      				
      			}
    		},
    		onFailure: function(transport){
  				printResult(transport, "Errore durante il recupero delle ricerche salvate.");
    		}
		});
	
	}
	function salvaRicerca(){
		var desc = $('desc_ricerca').value;
		if(desc == ''){
			alert("Inserire una descrizione per la ricerca");
			$('desc_ricerca').focus();
			return;
		}
		var id = $('id_ricerca_selezionata').value;
		if(id != ''){
			alert("Questa ricerca è già salvata. Clicca su Aggiorna se hai modificato i parametri di ricerca.");
			return;
		}
		// Verifico se il checkbox è selezionato e seto true se si, altimenti false
		$('globale_ricerca').value=false;
		if($('globale_ricerca').checked)
		{
			$('globale_ricerca').value=true;	
		}
		new Ajax.Request('${pageContext.request.contextPath}/jsonricerche/save.htm', { 
	 		method:'post',
	 		parameters: {

	 			filtro_ricerca : $(nome_form_ricerca).serialize(), 
	 			globale_ricerca : $('globale_ricerca').value, 
	 			chiave_ricerca : $('chiave_ricerca').value, 
	 			desc_ricerca : $('desc_ricerca').value
	 			///////
	 			
	 			
	 		},
  			onSuccess: function(transport){
  				var json = transport.responseText.evalJSON();
  				$('desc_ricerca').value = '';
  				printResult(transport);
  				recuperaRicerche();
    		},
    		onFailure: function(transport){
  				printResult(transport, "Errore durante il salvataggio della ricerca.");
    		}   		
		});
	}
	function aggiornaRicerca(){
		var id = $('id_ricerca_selezionata').value;
		if(id == ''){
			alert("Selezionare una ricerca");
			return;
		}
		// Verifico se il checkbox è selezionato e seto true se si, altimenti false
		$('globale_ricerca').value=false;
		if($('globale_ricerca').checked)
		{
			$('globale_ricerca').value=true;	
		}
		new Ajax.Request('${pageContext.request.contextPath}/jsonricerche/update.htm', { 
	 		method:'post',
	 		parameters: {
	 			filtro_ricerca : $(nome_form_ricerca).serialize(),
	 			globale_ricerca : $('globale_ricerca').value,
	 			id_ricerca : $('id_ricerca_selezionata').value, 
	 			desc_ricerca :  $('desc_ricerca').value
	 		},
  			onSuccess: function(transport){
  				printResult(transport);
  				recuperaRicerche();
    		},
    		onFailure: function(transport){
  				printResult(transport, "Errore durante l'aggiornamento della ricerca salvata.");
    		}   		
		});	
	}
	function eliminaRicerca(){
		var id = $('id_ricerca_selezionata').value;
		if(id == ''){
			alert("Selezionare una ricerca");
			return;
		}
		if(id != ''){
			if(!confirm("Vuoi eliminare la ricerca selezionata?"))
			return;
		}
		new Ajax.Request('${pageContext.request.contextPath}/jsonricerche/delete.htm', { 
	 		method:'post',
	 		parameters: {
	 			id_ricerca : id
	 		},
  			onSuccess: function(transport){
	 			$('id_ricerca_selezionata').value = '';
	 			$('desc_ricerca').value = '';
	 			recuperaRicerche();
	 			resetFormRicerca();
    		},
    		onFailure: function(transport){
  				printResult(transport, "Errore durante l'eliminazione della ricerca salvata.");
    		}
		});	
	}
	function popolaFormRicerca(option_idx){
		resetFormRicerca();
		var combo = $('ricerche');
		var id = combo[option_idx].value;
		$('id_ricerca_selezionata').value = id;
		if(id == ''){
			$('desc_ricerca').value = '';
			return;
		}
		new Ajax.Request('<%=request.getContextPath()%>/jsonricerche/find.htm', { 
		 	method:'post',
		 	parameters:{id_ricerca : id}, 
  			onSuccess: function(transport){
  			 	var json = transport.responseText.evalJSON();		 	
      			var ricerca = json.ricerca;
      			var filtro = ricerca.transientStringaFiltro;
      			$('desc_ricerca').value = ricerca.descrizione;
      			// popolo il chechbox globale
      			$('globale_ricerca').checked=false;
      			if(ricerca.flagGlobale=='true')
      			{
      				$('globale_ricerca').checked=true;
      			}
      			// Verifico se la ricerca è stata effettuata dall'operatore loggato 
      			// gestisce la possibilità i modifica dei parametri di ricerca.
      			if(ricerca.codiceresponsabile==<spring-security:authentication property="principal.codiceResponsabile" />)
      			{
      				$('functions').style.display='';
      			}else
      			{
      				$('functions').style.display='none';
      			}
      			var paramArray = filtro.split('&');
      			for(var i=0;i<paramArray.length;i++){
      				var param = paramArray[i].split('=');
      				var paramName = param[0];
      				var paramValue = unescape(param[1]);		
      				var eles = document.getElementsByName(paramName);
      				if(eles && eles.length > 0 ){
      					for(var j=0;j<eles.length;j++){
							// alert("paramName: "+paramName+"\n"+"paramValue: "+paramValue+"\n"+"type: "+eles[j].type);
							if(eles[j].type == "checkbox" || eles[j].type =="radio"){
								if(paramValue){
									// alert(eles[j].value + "=" + paramValue + " checked="+eles[j].checked);
									eles[j].checked = true;
									// eles[j].value = paramValue;
									
								}
							}else
							if(eles[j].type == "select-multiple"){
								var opts = eles[j].options;
								 //alert("inside select-multiple");
								for (var k = 0; k < opts.length; k++) { 
									 //alert("current opt is:" +opts[k].value);
							        if (opts[k].value == paramValue) { 
							            opts[k].selected = true;
							            //alert("set selected opt:" +opts[k].value);  
							        } 
							    } 

							}else{
								eles[j].value = paramValue;
							}
      					}
      				}
      			}
    		},
    		onFailure: function(transport){
  				printResult(transport, "Errore durante il recupero del dettaglio della ricerca salvata.");
    		}
		});
	}
	
	/**
	Il metodo è utilizzato in caso di pagina di ricerca delle attività, la pagina viene popolata 
	facendo una chiamata al controller di Iattivita e non tramite una chimata JSON. 
	**/
	function popolaFormRicercaAttivita(option_idx){
		
		var combo = $('ricerche');
		var id = combo[option_idx].value;
		$('id_ricerca_selezionata').value = id;
		if(id == ''){
			$('desc_ricerca').value = '';
			return;
		}
		javascript:doHref('search.htm?id_ricerca='+id+'&indice_combo='+option_idx,'');

	}
	
	function resetFormRicerca(){
		// reset dei checkbox, radio e le select-multiple
		var eles = document.inviodati.elements;
		for (var i=0;i<eles.length;i++){	
			if (eles[i]){
				if(eles[i].type == "checkbox" || eles[i].type =="radio"){
					eles[i].checked = false;
				}else
				if(eles[i].type == "select-multiple"){
					var opts = eles[i].options;
					for (var k = 0; k < opts.length; k++) {
				    	opts[k].selected = false;  
				    }
				}else{
					eles[i].value='';
				}
			}
		}
	}
	
	function clearSelect(elSel){
		var i;
		for (i = elSel.length - 1; i>=0; i--) {	 
			elSel.remove(i);  
		}
		var option = document.createElement("option");
		option.text = "Seleziona...";
		option.value = "";
		try {
			elSel.add(option, null); // Standard
		}catch(error) {
			elSel.add(option); // IE only
		}
		return elSel;
	}
	
	jQuery(document).ready(function(){
		recuperaRicerche(${_ricerca_selezionata});	
	});
//-->
</script>
		<%
			String displayDiv = "display:none;";
			String styleDiv = "";
			//gestisce la visualizzazione della tabella altri dati
			if ((StringUtils.isNotBlank((String)request.getAttribute(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE))))
			{
				if (((String) request.getAttribute(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE)).equals("1")) {
				    displayDiv = "";
				    styleDiv="sezioneDatiMeno";
				} else {
				    displayDiv = "display:none;";
				    styleDiv="sezioneDatiPiu";
				}
			}
		%>
		<!--<c:set value="<spring-security:authentication property='principal.codiceResponsabile' />" var="codiceOperatore" scope="page"></c:set>-->
		<fieldset>
				<legend>
				<a class="<%=styleDiv%>" 
					id="id_link_preferenze" 
					href="javascript:showHidePanelBase('preferenzeVisualizzazione_id', 'id_link_preferenze', '<%= WebConstants.CONF_UTENTE_GESTIONE_RICERCHE %>', '${pageContext.request.contextPath}/images/','div',true);"	
					title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.gestione_ricerca"/>">
					<label for="id_link_preferenze"><fmt:message key="label.gestione_ricerca"/></label>
				</a>
				</legend>
				<div id="preferenzeVisualizzazione_id" style="<%=displayDiv%>">	
				<%-- 
				<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
				--%>
					<label>Descrizione ricerca&nbsp;</label><input type="text" id="desc_ricerca" size="80" maxlength="4000">	
				<%--
				</spring-security:authorize>
				--%>
				<%--
				<spring-security:authorize ifNotGranted="ROLE_ADMINISTRATOR">
				--%>
					<input type="hidden" id="desc_ricerca">
				<%--
				</spring-security:authorize> codiceRicerca
				--%>
				<input type="hidden" id="id_ricerca_selezionata">
				<c:if test="${_ricerca_attivita eq '0' }">
					<label>Seleziona una ricerca&nbsp;</label><select id="ricerche" onchange="popolaFormRicerca(this.selectedIndex);"></select>
				</c:if>
				<c:if test="${_ricerca_attivita eq '1' }">
					<label>Seleziona una ricerca&nbsp;</label><select id="ricerche" onchange="popolaFormRicercaAttivita(this.selectedIndex);" ></select>
				</c:if>
				
				
				<c:set var="amministratore" scope="page">
                          <spring-security:authentication property="principal.amministratore" />
                </c:set>
                <c:set var="amministratoreSoftware" scope="page">
                          <spring-security:authentication property="principal.amministratoreSoftware" />
                </c:set>
				<%--
				<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
				--%>
				<c:set scope="page" value="true" var="hidden_field"></c:set>
                <c:if test="${amministratore || amministratoreSoftware}">
                <c:set scope="page" value="false" var="hidden_field"></c:set>
				    <label>Ricerca globale&nbsp;</label><input type="checkbox" id="globale_ricerca" name="flagGlobale" >
				    <init:help idHelp="global_help_id" textKey="help.label.ricerca_globale"/>
				</c:if>
				<%-- 
				</spring-security:authorize>
				<spring-security:authorize ifNotGranted="ROLE_ADMINISTRATOR">
				--%>
				<c:if test="${hidden_field}">
					<input type="hidden" id="globale_ricerca">
				</c:if>
				<br />
				<%-- 
				</spring-security:authorize>
				<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR">
				--%>
				<div  id="functions">
					<ul>
						<li><a href="javascript: void(0);" onclick="salvaRicerca();">Salva</a></li>
						<li><a href="javascript: void(0);" onclick="aggiornaRicerca();">Aggiorna</a></li>
						<li><a href="javascript: void(0);" onclick="eliminaRicerca();">Elimina</a></li>
					</ul>
				</div>
				<br />
				<%-- 
				</spring-security:authorize>
				--%>
				</div>	
		</fieldset>