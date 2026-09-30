<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${vwregistrazionidebiti.id.codice==null}">
			<fmt:message key="form.vwregistrazionidebiti.title.create" />
		</c:if> 
		<c:if test="${vwregistrazionidebiti.id.codice!=null}">
			<fmt:message key="form.vwregistrazionidebiti.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${vwregistrazionidebiti.id.codice==null}">
	<fmt:message key="form.vwregistrazionidebiti.title.create" />
</c:if> 
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>

<%
String  mercatiUso="display:none;";
String  mercati="display:none;";
%>
<div id="subcontent">
	<%--
	la chiave di ricerca serve per la funzionalità 'salva ricerche', utilizzare il nome della pagina jsp come valore
	--%>
	<input type="hidden" id="chiave_ricerca" value="registrazioni_searchRateNonPagate" />
	<input type="hidden" id="nome_form_ricerca" value="ratenonpagatefilter" />
	
	<spring-form:form commandName="ratenonpagatefilter" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="ratenonpagatefilter" />
    </jsp:include>
     <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../registrazioni/searchRateNonPagate" />
    </jsp:include>
	<%-- form delle ricerche salvate --%>
	<jsp:include page="../includes/ricerche.jsp" />
    <fieldset><legend><fmt:message key="form.vwregistrazionidebiti.filtri"></fmt:message></legend>

	<!-- START Tabella principale --> 
    <div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td><fmt:message key="form.vwregistrazionidebiti.anno" /></td>
                <td width="5%" ><fmt:message key="form.vwregistrazionidebiti.ratenonpagate" /></td>
                <td width="5%" ><fmt:message key="form.vwregistrazionidebiti.importodaincassareda" /></td>
                <td width="5%" ><fmt:message key="form.vwregistrazionidebiti.importodaincassarea" /></td>
                <td width="5%"><fmt:message key="form.vwregistrazionidebiti.causale" /></td>
                <td width="350">
                <span id="mercato" style="<%=mercati%>"><fmt:message key="form.vwregistrazionidebiti.mercato" /></span></td>
                <td width="250"><span id="mercatoUso" style="<%=mercatiUso%>" ><fmt:message key="form.vwregistrazionidebiti.giorno" /></span></td>

			</tr>
		</thead>
		<tbody class="tbody">
				<tr class= "odd">
				   <td><spring-form:input tabindex="1" id="anno" path="anno" size="4" cssStyle="text-align:right;"/></td>
                 
                   <td><spring-form:input tabindex="2" path="rateNonPagate" size="2" cssStyle="text-align:right;"/></td>
                   <td><spring-form:input tabindex="3" path="importoDaIncassareInf" size="8" cssStyle="text-align:right;" onchange="checkCurrencyValue(this);"/></td>
                   <td><spring-form:input tabindex="4" path="importoDaIncassareSup" size="8" cssStyle="text-align:right;" onchange="checkCurrencyValue(this);"/></td>
					<td>
                       <spring-form:select tabindex="5" id="causale_id" path="registrazioniCausali.id.codice" onchange="displaymercato(this);">
				       <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					   <spring-form:options items="${registrazioniCausaliList}" itemValue="id.codice"	itemLabel="descrizione" />
				       </spring-form:select>
                   </td>     
					<td >
						<span id="mercati" style="<%=mercati%>">
						<script type="text/javascript">
						function setHiddenFieldmercati(inputField,listItem){
							var a = listItem.id;
							document.getElementById('mercati_id').value = inputField.value;
							document.getElementById('mercati_hidden').value = a;
							$('mercati_id_choices').fade();	 
							mercatiUsoDisplay();
						}
						</script>
	                       <spring-form:input tabindex="6" id="mercati_id" path="mercati.descrizione" cssClass="searchbox" size="40" onchange="checkMercatiValue(this,'mercati_hidden','selectMercatiUso')" onkeydown="javascript:return searchAll(this,event)" cssStyle="padding-left:20px;"/>
					       <init:autocompleter methodAjax="findMercati.htm" afterUpdateElement="setHiddenFieldmercati" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
					       <spring-form:hidden id="mercati_hidden" path="mercati.id.codice" />
						</span>
                   </td>
                      <td >
						<span id="mercatiUso" style="<%=mercatiUso%>">
	                       <spring-form:select id="selectMercatiUso" tabindex="7"  path="mercatiUso.id.codice">
					       <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
						   <spring-form:options items="${mercati.mercatiUsos}" itemValue="id.codice"	itemLabel="descrizione" />
					       </spring-form:select>
						</span>
                   </td> 

			   </tr>
				
		</tbody>
	</table>
	
</div>
	<script type='text/javascript'>
	$('anno').focus();
	</script>
<br class="clear"/>
<div id="functions">
<ul>
	<c:if test="${vwregistrazionidebiti.id.codice==null}">
		<li><a href="javascript:doSubmit('searchRateNonPagate.htm','',document.inviodati)"><fmt:message key="button.search" /></a></li>
	</c:if>
</ul>
</div>
<br class="clear"/>
</fieldset>
</spring-form:form>
<br class="clear"/>
<fieldset><legend><fmt:message key="form.vwregistrazionidebiti.registrazionenonopagate"></fmt:message></legend>
	<form name="registrazioniFilterForm" action="searchRateNonPagate.htm">
	<jmesa:springTableFacade id="rateNonPagate_id" items="${listaRegistrazioniNonPagate}" 
		var="registrazioninonpagateList_var" exportTypes="pdfp,excel,csv" 
		stateAttr="restore" filterMatcherMap="org.jmesa.custom.DateRateNonPagateFilterMatcherMap">
			<jmesa:htmlTable>
				<jmesa:htmlRow>							
							<jmesa:htmlColumn property="registrazione.progressivo" titleKey="form.registrazioni.progressivo" />
							<jmesa:htmlColumn property="registrazione.anno" titleKey="form.registrazioni.anno" />
							<jmesa:htmlColumn property="dataRegistrazione" titleKey="form.registrazioni.dataregistrazione" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DateRateNonPagateCustomFilter" />
							<jmesa:htmlColumn property="registrazione.anagrafe.descrizioneRichiedente" titleKey="form.registrazioni.anagrafe" />
							<jmesa:htmlColumn property="registrazione.registrazioniCausali.descrizione" titleKey="form.registrazioni.registrazioniCausali" />
							<jmesa:htmlColumn property="registrazione.mercatiD.mercati.descrizione" titleKey="form.registrazioni.mercati" />
							<jmesa:htmlColumn property="registrazione.mercatiUso.descrizione" titleKey="form.registrazioni.mercatiUso" />
							<jmesa:htmlColumn property="registrazione.mercatiD.codiceposteggio" titleKey="form.registrazioni.mercati.posteggio" />
							<jmesa:htmlColumn property="rateNonPagate" titleKey="form.vwregistrazionidebiti.ratenonpagate" style="text-align:right;" headerStyle="text-align:left;"/>
							<jmesa:htmlColumn property="registrazione.importo" titleKey="form.registrazioni.importo" style="text-align:right;" headerStyle="text-align:right;">
								<fmt:formatNumber minFractionDigits="2">${registrazioninonpagateList_var.registrazione.importo}</fmt:formatNumber>								
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="registrazione.vwRegistrazionisaldo.saldo" titleKey="form.registrazioninonpagate.debito" style="text-align:right;" headerStyle="text-align:left;">
								<fmt:formatNumber minFractionDigits="2">${registrazioninonpagateList_var.registrazione.vwRegistrazionisaldo.saldo}</fmt:formatNumber>								
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="registrazione.id.codice" titleKey="label.edit.record" sortable="false" filterable="false" width="2%">
								<a class="dettaglioColumn" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../registrazioni/view.htm?codice=${registrazioninonpagateList_var.registrazione.id.codice}','');"  title="<fmt:message key="label.edit.record" /> ${registrazioninonpagateList_var.registrazione.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
								<c:if test="${registrazioninonpagateList_var.registrazione.vwRegistrazionisaldo.saldo gt 0}">									
									<a class="assegnaColumn" href="javascript:vaiAScadenze('${registrazioninonpagateList_var.registrazione.progressivo}','${registrazioninonpagateList_var.registrazione.anagrafe.id.codice}');" title="<fmt:message key="label.assegna" />" >
										<label><fmt:message key="label.assegna.image" /></label>
									</a>
								</c:if>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			<script type="text/javascript">
				var _jmesaUrl='searchRateNonPagate.htm?';
				var _captionTab='<fmt:message key="form.vwregistrazionidebiti.registrazionenonopagate"/>';

				//Sovrascrivo le due funzioni javascript presenti in gruppoinit.js
				//è neccessario sovrascriverle perchè nella pagina sono presenti due tabelle jmesa

				// funzione per determinare le proprietà del table facade (JMesa)
				function getProperties(id) {
					var properties = new Array();
					properties[0]='registrazione.progressivo';
					properties[1]='registrazione.anno';
					properties[2]='registrazione.dataRegistrazione';
					properties[3]='registrazione.anagrafe.descrizioneRichiedente';
					properties[4]='registrazione.registrazioniCausali.descrizione';
					properties[5]='registrazione.mercatiD.mercati.descrizione';
					properties[6]='registrazione.mercatiUso.descrizione';
					properties[7]='registrazione.mercatiD.codiceposteggio';
					properties[8]='rateNonPagate';
					properties[9]='registrazione.importo';
					properties[10]='registrazione.vwRegistrazionisaldo.saldo';
					var propertiesEncode=new Array();
					for(var i=0;i<properties.length;i++){
						propertiesEncode[i]=URLEncode(properties[i]);
					}
					return propertiesEncode;
				}

				// determina a partire dall'id del table facade della tabella di JMesa determina
				// il nome delle colonne
				function getColumnArray(id) {
					key = new Array();
					key[0]='<fmt:message key="form.registrazioni.progressivo" />';
					key[1]='<fmt:message key="form.registrazioni.anno" />';
					key[2]='<fmt:message key="form.registrazioni.dataregistrazione" />';
					key[3]='<fmt:message key="form.registrazioni.anagrafe" />';
					key[4]='<fmt:message key="form.registrazioni.registrazioniCausali" />';
					key[5]='<fmt:message key="form.registrazioni.mercati" />';
					key[6]='<fmt:message key="form.registrazioni.mercatiUso" />';
					key[7]='<fmt:message key="form.registrazioni.mercati.posteggio" />';
					key[8]='<fmt:message key="form.vwregistrazionidebiti.ratenonpagate.export" />';
					key[9]='<fmt:message key="form.registrazioni.importo.euro" />';
					key[10]='<fmt:message key="form.registrazioniimporti.debito.euro" />';
					var keyEncode=new Array();
					for(var i=0;i<key.length;i++){
						keyEncode[i]=URLEncode(key[i]);
					}
					return keyEncode;
				}
			</script>
		
	
	<br class="clear"/>
    <div id="functions">
			<ul>
                <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				 
			</ul>
		</div>
<br class="clear"/>
</fieldset>
<!-- END FINE TABELLA PRINCIPALE --> 


	
	<script type='text/javascript'>

	displaymercato($('causale_id'));
	
	
	function checkMercatiValue(inputField, hiddenFieldId, field2Id) {
		if (inputField.value == ''){
			removeOptionSelected("selectMercatiUso");
			$(hiddenFieldId).value = '';
		}		
	}
	
	function vaiAScadenze(progressivo, codiceAnagrafe){
		var goToUrl = "../registrazioniinout/listScadenze.htm?filter.progressivo="+progressivo+
					"&filter.anagrafe.id.codice="+codiceAnagrafe;
		goToUrl = escape(goToUrl);
		doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'');
	}
	function mercatiUsoDisplay(){
		var usoSelected = '${ratenonpagatefilter.mercatiUso.id.codice}';
		var selected = false;
		
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
						  if(opts[i]==usoSelected){
								selected = true;
						  }else{
							  selected = false;
						  }
						  try{
						  	$('selectMercatiUso').add(new Option( opts[j], opts[i], selected, selected),  $('selectMercatiUso').options[i]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectMercatiUso').add(new Option( opts[j], opts[i], selected, selected));
						  }
						  
						  i++;
					  }
				    },
				  onFailure: function(){  }
								  
				  });
		
		
	}
	
	function removeOptionSelected(opt){		
		
	  var elSel = document.getElementById(opt);
	  var i;
	  for (i = elSel.length - 1; i>=0; i--) {
	    if (elSel.options[i]) {
	       elSel.remove(i);
	    }
	  }
  	try{
	  	$(opt).add(new Option( "<fmt:message key="label.select.default"/>", ""),  $('selectMercatiUso').options[0]);
	  }
	  catch(e){ //in IE, try the below version instead of add()
		  $(opt).add(new Option( "<fmt:message key="label.select.default"/>", ""));
	  }

	}

	function searchAll(inputField,evt){
		 var charCode = (evt.which) ? evt.which : event.keyCode;
		 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
		   inputField.value='%';
		 }
	}

	function displaymercato(elem){		
		var code=elem[elem.selectedIndex].value;
		
		new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiEndo.htm', {
			  method: 'get',
			  parameters: {code: code, limit: 12},
			  onSuccess: function(transport){
				  var response = transport.responseText;
				  if(response == '0' ){ //ne endo ne posteggio
					   $("mercati_hidden").value='';
					   $("mercati_id").value='';
			    	   $("selectMercatiUso").value='';
			    	   $("mercati").fade();
			    	   $("mercatiUso").fade();
			    	   $("mercato").fade();
			    	   $("mercatoUso").fade();
				}
			      if(response == '1' ){ //richiede posteggio
				       $("mercati").appear();
			    	   $("mercatiUso").appear();
			    	   $("mercato").appear();
			    	   $("mercatoUso").appear();
			    	   mercatiUsoDisplay();			    	   					  
			      }
				  if(response == '2'){ //richiede endo
					   $("mercati_hidden").value='';
					   $("mercati_id").value='';
			    	   $("selectMercatiUso").value='';
			    	   $("mercati").fade();
			    	   $("mercatiUso").fade();
			    	   $("mercato").fade();
			    	   $("mercatoUso").fade();
				  }
			    },
			  onFailure: function(){ 
					   $("mercati_hidden").value='';
			    	   $("selectMercatiUso").value='';
			    	   $("mercati").fade();
			    	   $("mercatiUso").fade();
			    	   $("mercato").fade();
			    	   $("mercatoUso").fade();
			   }
							  
			  });		
	}

	</script>	

</div>

</body>
</html>
