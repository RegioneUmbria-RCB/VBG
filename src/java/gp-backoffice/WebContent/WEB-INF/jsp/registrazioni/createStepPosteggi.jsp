<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="form.registrazioni.title.search" />
	</title>
</head>
<body>
<span class="titoloPagina">
<fmt:message key="form.registrazioni.title.search" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>

<%
String  mercatiUso="display:none;";
String  mercatiPosteggio="display:none;";
%>


<div id="subcontent">
	<spring-form:form commandName="registrazioniFilter" name="inviodati">
    <jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="registrazioniFilter" />
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../registrazioni/createSearch" />
    </jsp:include>
	<table>
 		<tr>
			<td>
				<fmt:message key="label.anno" />
			</td>
			<td>
				<spring-form:input id="anno_id" path="anno" size="5" maxlength="4"/>

			</td>
		</tr>
 		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.registrazioniCausali" />
			</td>
			<td>
				<spring-form:select tabindex="3" id="regCausali" path="registrazioniCausali.id.codice">
				    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${registrazioniCausaliList}" itemValue="id.codice"	itemLabel="descrizione" />
				</spring-form:select>
			</td>
		</tr>
		<tr id="mercati">
			<td><fmt:message key="form.registrazioniFilter.mercati" /></td>
			<td>
				<script type='text/javascript'>
				function setHiddenFieldmercati(inputField,listItem){
					var a = listItem.id;
					//array che contiene l'id dei campi separati da '#'
					//i primi due valori sono l'id e la descrizione dell'anagrafe
					var arrayMercati=a.split('#');
					var arrayAnagrafe= inputField.value.split('-');
					if(arrayMercati.length>1 || arrayAnagrafe.length>1){
						document.getElementById('mercati_id').value = arrayMercati[1];
						document.getElementById('mercati_hidden').value = arrayMercati[0];
						var replace=new String();
						replace=arrayAnagrafe[0].substring(6,arrayAnagrafe[0].length);
						document.getElementById('anagrafe_id').value = replace;
						document.getElementById('anagrafe_hidden').value = arrayMercati[2];
						removeOptionSelected("selectMercatiUso");
						removeOptionSelected("selectPosteggio");
						$("mercatiPosteggio").appear();
						 $("mercatiUso").appear();
						try{
							$('selectPosteggio').add(new Option( arrayMercati[6], arrayMercati[5]),  $('selectPosteggio').options[0]);
						}catch(e){ //in IE, try the below version instead of add()
							
						   $('selectPosteggio').add(new Option( arrayMercati[6], arrayMercati[5]));
					  	}
						try{
						  	$('selectMercatiUso').add(new Option( arrayMercati[4], arrayMercati[3]),  $('selectMercatiUso').options[0]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectMercatiUso').add(new Option(arrayMercati[4], arrayMercati[3]));
						  }
					}else{
						document.getElementById('mercati_id').value = arrayAnagrafe[0];
						document.getElementById('mercati_hidden').value = arrayMercati[0];
						mercatiUsoDisplay();	 
						assegnaPosteggio();
					}
				}
				</script>
				<spring-form:input tabindex="8" id="mercati_id" path="mercati.descrizione" cssClass="searchbox" onchange="checkValue(this,'mercati_hidden');eliminaMercati();" onkeydown="javascript:return searchAll(this,event)"  size="67"/>
				<init:autocompleter methodAjax="findMercati.htm" afterUpdateElement="setHiddenFieldmercati" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
				<spring-form:errors path="mercati" cssClass="error"/> 
				<spring-form:hidden id="mercati_hidden" path="mercati.id.codice" />
			</td>
		</tr>
		<tr id="mercatiUso" style="<%=mercatiUso%>">
			<td><fmt:message key="form.registrazioni.mercatiUso" /></td>
			<td>
				<spring-form:select tabindex="9" id="selectMercatiUso" path="mercatiUso.id.codice">
				<spring-form:option value="${registrazioniFilter.mercatiUso.id.codice}" label="${registrazioniFilter.mercatiUso.descrizione}"></spring-form:option>
				</spring-form:select><spring-form:errors path="mercatiUso.id.codice" cssClass="error"/> 
			</td>
		</tr>
		<tr id="mercatiPosteggio" style="<%=mercatiPosteggio%>">
			<td><fmt:message key="form.registrazioni.mercati.posteggio" /></td>
			<td>
				<spring-form:select id="selectPosteggio" path="posteggio.id.codice" onchange="recuperaAnagrafePosteggio()">
				</spring-form:select><spring-form:errors path="posteggio.id.codice" cssClass="error"/> 
			</td>
		</tr>
       <tr>
			<td>
				<fmt:message key="form.registrazioniFilter.anagrafe" />
			</td>
			<td>
				<script type='text/javascript'>
				function setHiddenFieldanagrafe(inputField,listItem){
					var a = listItem.id;
					//array che contiene l'id dei campi separati da '#'
					//i primi due valori sono l'id e la descrizione dell'anagrafe
					var arrayMercati=a.split('#');
					var arrayAnagrafe= inputField.value.split('-');
					if(arrayMercati.length>1 || arrayAnagrafe.length>1){
						document.getElementById('anagrafe_id').value = arrayMercati[1];
						document.getElementById('anagrafe_hidden').value = arrayMercati[0];
						var replace=new String();
						replace=arrayAnagrafe[0].substring(6,arrayAnagrafe[0].length);
						document.getElementById('mercati_id').value = replace;
						document.getElementById('mercati_hidden').value = arrayMercati[2];
						removeOptionSelected("selectMercatiUso");
						removeOptionSelected("selectPosteggio");
						$("mercatiPosteggio").appear();
						$("mercatiUso").appear();
						try{
							
							$('selectPosteggio').add(new Option( arrayAnagrafe[2], arrayMercati[4]),  $('selectPosteggio').options[0]);
						}catch(e){ //in IE, try the below version instead of add()
							
						   $('selectPosteggio').add(new Option( arrayAnagrafe[2], arrayMercati[4]));
					  	}
						try{
						  	$('selectMercatiUso').add(new Option( arrayAnagrafe[1], arrayMercati[3]),  $('selectMercatiUso').options[0]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectMercatiUso').add(new Option(arrayAnagrafe[1], arrayMercati[3]));
						  }
					}else{
						document.getElementById('anagrafe_id').value = arrayAnagrafe[0];
						document.getElementById('anagrafe_hidden').value = arrayMercati[0];
					}
					$('anagrafe_id_choices').fade();	 	 
				}
				</script>
				<spring-form:input tabindex="7" id="anagrafe_id" path="anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden')" onkeydown="javascript:return searchAll(this,event,3)" size="67"/>
				<init:autocompleter methodAjax="findAnagrafeRegistrazioniForMercati.htm" afterUpdateElement="setHiddenFieldanagrafe" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente"/>
				<spring-form:errors path="anagrafe" cssClass="error"/> 
				<spring-form:hidden id="anagrafe_hidden" path="anagrafe.id.codice"  />
			</td>
		</tr>

		

		<tr>
			<td colspan="2">
			<fieldset><legend>Lista pagamenti</legend>
		<table border="1">
	 		<tr>
				<td>
					<fmt:message key="label.gennaio" /></td><td> <input size="5" type="text" value="" name="gennaio" id="gennaio_id"/>
				</td>
				<td>
					<fmt:message key="label.febbraio" /></td><td> <input size="5" type="text" value="" name="febbraio" id="febbraio_id"/>
				</td>
				<td>
					<fmt:message key="label.marzo" /></td><td> <input size="5" type="text" value="" name="marzo" id="marzo_id"/>
				</td>
			</tr>
			<tr>		
				<td>
					<fmt:message key="label.aprile" /></td><td> <input size="5" type="text" value="" name="aprile" id="aprile_id"/>
				</td>
				<td>
					<fmt:message key="label.maggio" /></td><td> <input size="5" type="text" value="" name="maggio" id="maggio_id"/>
				</td>
				<td>
					<fmt:message key="label.giugno" /></td><td> <input size="5" type="text" value="" name="giugno" id="giugno_id"/>
				</td>
			</tr>
			<tr>		
				<td>
					<fmt:message key="label.luglio" /></td><td> <input size="5" type="text" value="" name="luglio" id="luglio_id"/>
				</td>
				<td>
					<fmt:message key="label.agosto" /></td><td> <input size="5" type="text" value="" name="agosto" id="agosto_id"/>
				</td>
				<td>
					<fmt:message key="label.settembre" /></td><td> <input size="5" type="text" value="" name="settembre" id="settembre_id"/>
				</td>
			</tr>
			<tr>		
				<td>
					<fmt:message key="label.ottobre" /></td><td> <input size="5" type="text" value="" name="ottobre" id="ottobre_id"  />
				</td>
				<td>
					<fmt:message key="label.novembre" /></td><td> <input size="5" type="text" value="" name="novembre" id="novembre_id"/>
				</td>
				<td>
					<fmt:message key="label.dicembre" /></td><td> <input size="5" type="text" value="" name="dicembre" id="dicembre_id"/>
				</td>
				
			</tr>	
		</table>
		<br class="clear"/>
		<div id="spiegazioneCostoPosteggio_id"></div>
		
	</fieldset>
			</td> 
		</tr>
		
		<tr>
			<td><fmt:message key="form.registrazioni.tiporateizzazione.scadenzarate" />:</td> 
			<td >
				<input type="hidden" name="codiceConto" id="codiceConto_id"/>
				<select id="scadenzaRate_id" name="scadenzarate" >
					<c:forEach items="${tipiscadenzaList}" var="scadenza">
				    	<option value="${scadenza.id}">${scadenza.descrizione}</option>
				    </c:forEach>
				</select>
			</td>
		</tr>
		
		
	</table>
	
	
	
	
	</spring-form:form>

</div>
<div id="functions">
<ul>
  <li><a href="javascript:inserisci()"><fmt:message key="button.insert" /></a></li>
  <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>

	<script type='text/javascript'>
	//<![CDATA[
		
		function eliminaMercati(){
			if(document.getElementById('mercati_id').value == ''){
				document.getElementById('mercati_id').value = '';
				document.getElementById('mercati_hidden').value = '';
	
				removeOptionSelected("selectMercatiUso");
				removeOptionSelected("selectPosteggio");
				try{
					$('selectPosteggio').add(new Option('<fmt:message key="label.select.default" />', ''),  $('selectPosteggio').options[0]);
				}catch(e){ //in IE, try the below version instead of add()
					
				   $('selectPosteggio').add(new Option( '<fmt:message key="label.select.default" />',''));
			  	}
			}

			}
		function removeOptionSelected(opt)
		{
		  var elSel = document.getElementById(opt);
		  var i;
		  for (i = elSel.length - 1; i>=0; i--) {
		    if (elSel.options[i]) {
		       elSel.remove(i);
		    }
		  }
		}
		function assegnaPosteggio(){
			removeOptionSelected("selectPosteggio");
			try{
				$('selectPosteggio').add(new Option('<fmt:message key="label.select.default" />', ''),  $('selectPosteggio').options[0]);
			}catch(e){ //in IE, try the below version instead of add()
				
			   $('selectPosteggio').add(new Option( '<fmt:message key="label.select.default" />',''));
		  	}
			var idMercato=document.getElementById("mercati_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findPosteggioMercato.htm', {
				  method: 'post',
				  parameters: {code: idMercato, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("mercatiPosteggio").appear();
					  var opts=response.split(",");
					  var pos = 1;
					  for(var i=0;i<((opts.length)-1); i+=2 ){
						  var j=i+1;
						  try{
						  	$('selectPosteggio').add(new Option( opts[j], opts[i]),  $('selectPosteggio').options[pos]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectPosteggio').add(new Option( opts[j], opts[i]));
						  }						  
						  pos++;
					  }
				    },
				  onFailure: function(){  }
								  
				  });
												
			}
		function mercatiUsoDisplay(){
			removeOptionSelected("selectMercatiUso");
			var idMercato=document.getElementById("mercati_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiUso.htm', {
				  method: 'post',
				  parameters: {code: idMercato, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("mercatiUso").appear();
					  var opts=response.split(",");
					  for(var i=0;i<((opts.length)-1); i++ ){
						  var j=i+1;
						  try{
						  	$('selectMercatiUso').add(new Option( opts[j], opts[i]),  $('selectMercatiUso').options[i]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectMercatiUso').add(new Option( opts[j], opts[i]));
						  }
						  i++;
					  }
				    },
				  onFailure: function(){  }
								  
				  });
		}
    			
		
		function recuperaAnagrafePosteggio(){
			var mercato = document.getElementById("mercati_hidden").value;
			var uso = $('selectMercatiUso').value;
			if(uso==''){
				alert('E\' necessario indicare un giorno');
				return;
			}
			var posteggio = $('selectPosteggio').value;
			if(posteggio==''){
				alert('E\' necessario indicare un posteggio');
				return;
			}
			var jhqr = jQuery.ajax({
				  url: '../registrazioni/ajaxRecuperaAnagrafePosteggio.htm?mercato='+mercato+'&uso='+uso+'&posteggio='+posteggio,
				  context: document.body,
				  cache: false,				
				  dataType: "text",
				  success: function(data) { 
					  if(data.indexOf("|")>0){
						  var risposta = data.split("|");					  
						  document.getElementById('anagrafe_id').value = risposta[1];
						  document.getElementById('anagrafe_hidden').value = risposta[0];
						  dettagliPosteggio();
					  }else{
						  alert(data);
					  }
					}, 
				  error: function(xhr){
					  var message = 'Errore nell\'invio della richiesta: ';
					  var status = xhr.status;
					  var text = xhr.statusText;
  					  message += status + ': ' + text;
  					  alert(message);
					}
			});
			
		}
		function dettagliPosteggio(){
			
			var anno = 	document.getElementById("anno_id").value;
			var mercato = document.getElementById("mercati_hidden").value;
			var uso = $('selectMercatiUso').value;
			if(uso==''){
				alert('E\' necessario indicare un giorno');
				return;
			}
			var posteggio = $('selectPosteggio').value;
			if(posteggio==''){
				alert('E\' necessario indicare un posteggio');
				return;
			}
			var jhqr = jQuery.ajax({
				  url: '../registrazioni/ajaxRecuperaCostoPosteggio.htm?anno='+anno+'&mercato='+mercato+'&uso='+uso+'&posteggio='+posteggio,
				  context: document.body,
				  cache: false,				
				  dataType: "text",
				  success: function(data) {
					  console.debug(data);
					  if(data.indexOf("|")>0){						  						 
						 aggiornaCostoPosteggio(data);
					  }else{
						  alert(data);
					  }
					}, 
				  error: function(xhr){
					  var message = 'Errore nell\'invio della richiesta: ';
					  var status = xhr.status;
					  var text = xhr.statusText;
					  message += status + ': ' + text;
					  alert(message);
					}
			});			
			
		}
		
		
		function aggiornaCostoPosteggio(risposta){
			var righe = risposta.split('#');
			var costo = 0;
			var spiegazione = "L\'importo di ogni mensilità è dato dai seguenti valori:<ul>";
			for (i = 0;i<righe.length;i++){
				if(righe[i].indexOf("|")>0){
					riga = righe[i].split("|");	
					spiegazione +="<li>Conto: "+riga[0]+"<br />Importo: "+riga[1]+"€<br />Iva: "+riga[2]+"%</li>";
					$('codiceConto_id').value = riga[3];
					var costoRiga = 0;
					if(!isNaN(parseFloat(riga[1]))){
						costoRiga = parseFloat(riga[1]);
					}
					costo+=costoRiga;
				}
			}						
			spiegazione += "</ul>";
			
			
			$('gennaio_id').value=costo;
			$('febbraio_id').value=costo;
			$('marzo_id').value=costo;
			$('aprile_id').value=costo;
			$('maggio_id').value=costo;
			$('giugno_id').value=costo;
			$('luglio_id').value=costo;
			$('agosto_id').value=costo;
			$('settembre_id').value=costo;
			$('ottobre_id').value=costo;
			$('novembre_id').value=costo;
			$('dicembre_id').value=costo;
			
			$('spiegazioneCostoPosteggio_id').innerHTML = spiegazione;
			
			
		}
		
	    function searchAll(inputField,evt){
		 var charCode = (evt.which) ? evt.which : event.keyCode;
		 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
		   inputField.value='%';
		 }
		}
	    
	    
	    
	    function inserisci(){
	    	
	    	
	    	var anno = 	document.getElementById("anno_id").value;
	    	if(anno==''){
				alert('E\' necessario indicare un anno');
				return;
	    	}else{
	    		if(!checkNumberInt(document.getElementById("anno_id"))){
	    			return;
	    		}
	    	}			
			var uso = $('selectMercatiUso').value;
			if(uso==''){
				alert('E\' necessario indicare un giorno');
				return;
			}
			var posteggio = $('selectPosteggio').value;
			if(posteggio==''){
				alert('E\' necessario indicare un posteggio');
				return;
			}
	    	
	    	var causale = document.getElementById("regCausali").value;
	    	
	    	if(causale==''){
				alert('E\' necessario indicare una causale');
				return;
	    	}else{
	    		if(!checkNumberInt(document.getElementById("regCausali"))){
	    			return;
	    		}
	    	}		
	    	
	    	var anagrafe = document.getElementById("anagrafe_hidden").value;
	    	
	    	if(anagrafe==''){
				alert('E\' necessario indicare un\' anagrafe');
				return;
	    	}else{
	    		if(!checkNumberInt(document.getElementById("anagrafe_hidden"))){
	    			return;
	    		}
	    	}		
	    	var scadenza = document.getElementById("scadenzaRate_id").value;
	    	
	    	if(scadenza==''){
				alert('E\' necessario indicare una tipologia di scadenza');
				return;
	    	}else{
	    		if(!checkNumberInt(document.getElementById("scadenzaRate_id"))){
	    			return;
	    		}
	    	}		
	    	doSubmit('insertStepPosteggi.htm','');
	    	
	    }
	    
	    
	//]]> 
	</script>		

</body>
</html>
