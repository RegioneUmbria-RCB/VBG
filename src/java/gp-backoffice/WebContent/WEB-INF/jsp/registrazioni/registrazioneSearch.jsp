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
			<td><fmt:message key="form.registrazioniFilter.progressivo" /></td>
			<td colspan="4">
				<spring-form:input tabindex="1" id="progressivo_id" path="progressivo" size="70" />
			    <spring-form:errors path="progressivo" cssClass="error"/>
			</td>
		</tr>
        <tr>
			<td>
                <fmt:message key="form.registrazioniFilter.descrizione" />
            </td>
			<td colspan="4">
				<spring-form:input tabindex="2" id="descrizione_id" path="descrizione" size="70" />
		    	<spring-form:errors path="descrizione" cssClass="error"/>
		    </td>
		</tr>
 		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.registrazioniCausali" />
			</td>
			<td colspan="4">
				<spring-form:select tabindex="3" id="regCausali" path="registrazioniCausali.id.codice">
				    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${registrazioniCausaliList}" itemValue="id.codice"	itemLabel="descrizione" />
				</spring-form:select>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.conti" />
			</td>
			<td colspan="4">
				<spring-form:input tabindex="4" id="conti_id" path="conti.descrizione" cssClass="searchbox" onchange="checkValue(this,'conti_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="67"/>
				<init:autocompleter methodAjax="findConti.htm" idHidden="conti_hidden" idInput="conti_id" inputTitleKey="label.ricerca_conto"/>
				<spring-form:errors path="conti" cssClass="error"/> 
				<spring-form:hidden id="conti_hidden" path="conti.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.data" />
			</td>
			<td class="inline-ui-cell" style="min-width: 30px;">
				<fmt:message key="form.registrazioniFilter.data.inizio" />
			</td>
			<td class="inline-ui-cell" >
				<spring-form:input tabindex="5" id="dataInizio_id" path="dataInizio" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dataInizio_id" textKey="label.calendar"/>
			</td>
			<td class="inline-ui-cell" style="min-width: 30px;">
				<fmt:message key="form.registrazioniFilter.data.fine" />
			</td>
			<td class="inline-ui-cell">
				<spring-form:input tabindex="6" id="dataFine_id" path="dataFine" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="dataFine_id" textKey="label.calendar"/>
				<spring-form:errors path="dataFine" cssClass="error" delimiter=" :"/>  
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.anagrafe" />
			</td>
			<td colspan="4">
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
		<tr id="mercati">
			<td><fmt:message key="form.registrazioniFilter.mercati" /></td>
			<td colspan="4">
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
				<init:autocompleter methodAjax="findMercatiForAnagrafe.htm" afterUpdateElement="setHiddenFieldmercati" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
				<spring-form:errors path="mercati" cssClass="error"/> 
				<spring-form:hidden id="mercati_hidden" path="mercati.id.codice" />
			</td>
		</tr>
		<tr id="mercatiUso" style="<%=mercatiUso%>">
			<td><fmt:message key="form.registrazioni.mercatiUso" /></td>
			<td colspan="4">
				<spring-form:select tabindex="9" id="selectMercatiUso" path="mercatiUso.id.codice">
				<spring-form:option value="${registrazioniFilter.mercatiUso.id.codice}" label="${registrazioniFilter.mercatiUso.descrizione}"></spring-form:option>
				</spring-form:select><spring-form:errors path="mercatiUso.id.codice" cssClass="error"/> 
			</td>
		</tr>
		<tr id="mercatiPosteggio" style="<%=mercatiPosteggio%>">
			<td><fmt:message key="form.registrazioni.mercati.posteggio" /></td>
			<td colspan="4">
				<spring-form:select id="selectPosteggio" path="posteggio.id.codice">
				</spring-form:select><spring-form:errors path="posteggio.id.codice" cssClass="error"/> 
			</td>
		</tr>
       
		<tr id="alberoproc">
			<td valign="top"><fmt:message key="form.registrazioniFilter.alberoproc" /></td>
			<td colspan="4" class="inline-ui-cell">
			    <%-- 
				<spring-form:input tabindex="10" id="alberoproc_id" path="alberoproc.scDescrizione" cssClass="searchbox" onchange="checkValue(this,'alberoproc_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67"/>
				<init:autocompleter methodAjax="findAlberoproc.htm" idHidden="alberoproc_hidden" idInput="alberoproc_id" inputTitleKey="label.ricerca_intervento"/>
				<spring-form:hidden id="alberoproc_hidden" path="alberoproc.id.codice" />
				--%>
				<jsp:include page="../includes/searchAlberoProc.jsp">
					<jsp:param name="propertyPath" value="alberoproc" />								
					<jsp:param name="pathPropertyDescription" value="alberoproc.scDescrizione" />
					<jsp:param name="pathPropertyCode" value="alberoproc.id.codice" />
					<jsp:param name="isSelectLeafDisable" value="tru" />
					<jsp:param name="isSelectNodoPadre" value="true" />
				</jsp:include>
			</td>
		</tr>
		<tr>
			<td> 
                <fmt:message key="form.registrazioniFilter.importo" />
            </td>
			<td colspan="4">
				<spring-form:input tabindex="11" id="importo_id" path="importo" size="10" cssStyle="text-align:right;" onchange="checkCurrencyValue(this);"/>
			    <spring-form:errors path="importo" cssClass="error"/>
			</td>
		</tr>        		
		<tr>
			<td><fmt:message key="form.registrazioniFilter.daincassare.maggiore" /></td>
			<td colspan="4">
				<spring-form:input tabindex="12" id="saldo_id" path="saldo" size="10" cssStyle="text-align:right;"/>
				<spring-form:errors path="saldo" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniFilter.vai_direttamente_a_scheda" /></td>
			<td colspan="4" class="inline-ui-cell">
			<c:set var="flgVisualizzaScheda_checked_" value=""/>
			<c:if test="${requestScope.CONTABILITA_VISUALIZZA_SCHEDE eq '1' }">
				<c:set var="flgVisualizzaScheda_checked_" value=" checked='checked' "/>
			</c:if>
				<input ${flgVisualizzaScheda_checked_} type="checkbox" tabindex="13" id="flg_visualizza_scheda_id" name="flgVisualizzaScheda" onclick="salvaImpostazioneUtente(this)" value="1" />
				<label for="flg_visualizza_scheda_id"><fmt:message key="form.registrazioniFilter.vai_direttamente_a_scheda.help" /></label>
			</td>
		</tr>
	</table>
	</spring-form:form>

<div id="PannelloAdeguaIVA_id" dojoType="dijit.Dialog" title="<fmt:message key="label.adeguamento_iva.title"/>" style="display: none;">
	<div id="PannelloAdeguaIVA_content_id"></div>
</div>
	
	 


</div>
<div id="functions">
<ul>
  <li><a tabindex="14" href="javascript:goToRegistrazioniList();"><fmt:message key="button.search" /></a></li>
  <li><a tabindex="15" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../registrazioni/createStep1.htm','');"><fmt:message key="button.new" /></a></li>
  <li><a tabindex="16" href="javascript:goToTransazioniList();"><fmt:message key="button.transazioni.inizia" /></a></li>
  <li><a tabindex="17" href="javascript:popUpAdeguaIVA();"><fmt:message key="button.adeguamento.cerca" /></a></li>
  <li><a tabindex="18" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../calcolatrice/create.htm','')"><fmt:message key="button.calcolainteressilegali" /></a></li>
  <li><a tabindex="18_" href="javascript:historySet('${_urlback}','../documenticontabilita/searchDocumenti.htm','')"><fmt:message key="button.documenti" /></a></li>
  <li><a tabindex="19" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>

	<div class="notifichePopup" id="notificaPanel" style="display:none">
		<c:if test="${concessioni_cessate > 0}">
					<div class="notifichePopupInner">
						<a href="javascript:visualizzaConcessioniCessateUltimoMese()">
							<fmt:message key="label.concessioni_cessate_ultimo_mese">
								<fmt:param value="${concessioni_cessate}"/>
							</fmt:message>
						</a>
					</div>
		</c:if>
		<c:if test="${concessioni_subentrate > 0}">						 				  
				<div class="notifichePopupInfoInner">
					<a href="javascript:visualizzaConcessioniSubentrateUltimoMese()">
						<fmt:message key="label.concessioni_subentrate_ultimo_mese">
							<fmt:param value="${concessioni_subentrate}"/>
						</fmt:message>
					</a>
				</div>
		</c:if>		
		<c:if test="${concessioni_rilasciate > 0}">						 				  
				<div class="notifichePopupInfoInner">
					<a href="javascript:visualizzaConcessioniRilasciateUltimoMese()">
						<fmt:message key="label.concessioni_rilasciate_ultimo_mese">
							<fmt:param value="${concessioni_rilasciate}"/>
						</fmt:message>
					</a>
				</div>
		</c:if>			
	</div>
	<script type='text/javascript'>
	//<![CDATA[
		
		$('progressivo_id').focus();
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
    	function goToRegistrazioniList(){
			var goToUrl = "../registrazioni/search.htm?1=1";
			goToUrl = escape(goToUrl);
			doSubmit('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'',document.inviodati,'GET',true);
						
		}

		function goToTransazioniList(){
			var goToUrl = "../registrazioni/searchTransazioni.htm?";
			goToUrl = escape(goToUrl);
			doSubmit('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'',document.inviodati,'GET',true);
		}
		
	    function searchAll(inputField,evt){
		 var charCode = (evt.which) ? evt.which : event.keyCode;
		 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
		   inputField.value='%';
		 }
		}
	    
	    function salvaImpostazioneUtente(obj){
	    	if(obj.checked==true){
	    		saveUserPreference('CONTABILITA_VISUALIZZA_SCHEDE', '1');
	    	}else{
	    		saveUserPreference('CONTABILITA_VISUALIZZA_SCHEDE', '0');
	    	}	    	
	    }
	    
	    function popUpAdeguaIVA(){
			
			var jqxhr = jQuery.ajax({
				  url: "ajaxPannelloAdeguaIVA.htm",
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  data: "",
				  success: function(dataResult) { 
					   $("PannelloAdeguaIVA_content_id").innerHTML = dataResult;
					   dijit.byId("PannelloAdeguaIVA_id").show();
					   applyStyle();
					},
				  error: function(dataError){
					  alert(dataError.innerText);
				  }	
				});
		}
		
	    var START_OPACITY = 0.8;
		jQuery(function(){
			if(jQuery('#notificaPanel').html()=="")
			return;
			jQuery('#notificaPanel').fadeTo('slow',START_OPACITY,function(){ jQuery(this).css('display','block'); })
			   .hover(
					function() {
						jQuery(this).stop().animate({"opacity": "1"}, "slow");
					},
					function() {
						jQuery(this).stop().animate({"opacity": START_OPACITY}, "slow");
			   });
		});
	    
		function visualizzaConcessioniCessateUltimoMese(){
			chiamataAjax('cessazioniUltimoMese.htm');
		}
		function visualizzaConcessioniSubentrateUltimoMese(){
			chiamataAjax('subentriUltimoMese.htm');
		}
		
		function visualizzaConcessioniRilasciateUltimoMese(){
			chiamataAjax('rilasciUltimoMese.htm');
		}
		
		function chiamataAjax(urlAggiuntiva){
			var url  = URLDecode('${_urlback}');
			var jhqr = jQuery.ajax({
				  url: '../history/ajaxSet.htm?ReturnTo='+url,
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  success: function(data) { 		
					  document.location.href= "<%=request.getContextPath()%>/vwconcessionilista/" + urlAggiuntiva;
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
		
	//]]> 
	</script>		

</body>
</html>
