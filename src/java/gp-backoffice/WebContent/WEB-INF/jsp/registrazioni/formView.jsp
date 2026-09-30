<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@page import="java.math.BigDecimal"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${registrazioni.entity.id.codice==null}">
			<fmt:message key="form.registrazioni.title.create" />
		</c:if> 
		<c:if test="${registrazioni.entity.id.codice!=null}">
			<fmt:message key="form.registrazioni.title.view" />
		</c:if>
	</title>
	
</head>
<body>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../registrazioni/view" />
</jsp:include>
<%
String  mercati="display:none;";
String  mercatiUso="display:none;";
String  mercatiPosteggio="display:none;";
String inventProc="display:none;";
%>
<c:if test="${registrazioni.entity.mercatiD.id.codice!=null}">
<%
mercati="";
mercatiPosteggio="";
mercatiUso="";
%>
</c:if>
<c:if test="${registrazioni.entity.mercatiD.id.codice==null}">
<%
mercati="display:none;";
mercatiPosteggio="display:none;";
mercatiUso="display:none;";
%>
</c:if>
<c:if test="${registrazioni.entity.inventarioprocedimenti.id.codice!=null}">
<%
inventProc="";
%>
</c:if>
<c:if test="${registrazioni.entity.inventarioprocedimenti.id.codice==null}">
<%
inventProc="display:none;";
%>
</c:if>
<span class="titoloPagina">
<c:if test="${registrazioni.entity.id.codice==null}">
	<fmt:message key="form.registrazioni.title.create" />
</c:if> 
<c:if test="${registrazioni.entity.id.codice!=null}">
	<fmt:message key="form.registrazioni.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form  commandName="registrazioni" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="registrazioni" />
    </jsp:include>

<c:if test="${registrazioni.displayMode==registrazioni.displayConstants.VIEW}"><%-- VIEW --%>
		
	<fieldset><legend><fmt:message key="form.registrazioni.dettaglioregistrazione"/></legend>
	<div id="pannello_risultati_id" style="float: right;"></div>
	<table border="0">
		<tr>
			<td><fmt:message key="form.registrazioni.progressivo" />:</td>
			<td colspan="3"><b>${registrazioni.entity.progressivo}</b></td>
			<td rowspan="5" style="vertical-align: top"></td>
		</tr>
		
		<tr>
			<td><fmt:message key="form.registrazioni.dataregistrazione" />:</td>
			<td><b><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioni.entity.dataRegistrazione}"/></b></td>
			<td><fmt:message key="form.registrazioni.anno" />:</td>
			<td><b>${registrazioni.entity.anno}</b></td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.registrazioniCausali" />:</td> 
			<td colspan="3"><b>${registrazioni.entity.registrazioniCausali.descrizione}</b></td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.anagrafe" />:</td>
			<td colspan="3">
			<a class="vbg-btn btn-cerca" href="javascript:dettaglioAnagrafe(${registrazioni.entity.anagrafe.id.codice});"></a>
			<b>${registrazioni.entity.anagrafe.descrizioneRichiedente}</b>
			<span id="anagrafe_dettaglio" style="display: none; text-align: left;"></span>
			<script	type="text/javascript">
				var anagrafeVisibile=false;
				function dettaglioAnagrafe(codiceAnagrafe){
					if(anagrafeVisibile==false){
					new Ajax.Request('<%=request.getContextPath()%>/ajax/dettaglioAnagrafe.htm', {
						  method: 'post',
						  parameters: {codiceAnagrafe: codiceAnagrafe},
						  onSuccess: function(transport){
							  var response = transport.responseText;		
							  $("anagrafe_dettaglio").innerHTML = response;
							  $("anagrafe_dettaglio").appear();
							  applyStyle();			  
						    },
						  onFailure: function(transport){ 
							var response = transport.responseText;
						    alert(response); }						    		 
						  });
						anagrafeVisibile=true;
					}else{
						$("anagrafe_dettaglio").fade();
						anagrafeVisibile=false;
					}
					  
				}
			</script>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.importo" />:</td>
			<td colspan="3"><b><fmt:formatNumber minFractionDigits="2" value="${registrazioni.entity.importo}"/> <fmt:message key="label.valuta" /></b></td>
		</tr>
		
			<tr>
				<td><fmt:message key="form.registrazioni.descrizione" />:</td>
				<td colspan="3"><b>${registrazioni.entity.descrizione}</b></td>
			</tr>
		
		
			<tr>
				<td><fmt:message key="form.registrazioni.note" />:</td>
				<td colspan="3"><b>${registrazioni.entity.note}</b></td>
			</tr>
		
	</table>
	</fieldset>
	<c:if test="${not (empty registrazioni.entity.mercatiD.id.codice)}">
	<% 	
		String urlBack = "/registrazioni/view.htm?codice=" + (Integer)request.getAttribute("codice");
		pageContext.setAttribute("URL_BACK", urlBack);
		// FIXME inserire path java
		pageContext.setAttribute("URL_DETT_CONC", "TODO");
	%>
	<fieldset>
	<legend><fmt:message key="form.registrazioni.informazioni"/></legend>
	<table>
		<tr>
			<td><fmt:message key="form.registrazioni.mercati"/>:</td>
			<td>&nbsp;<strong>${registrazioni.entity.mercatiD.mercati.descrizione}</strong></td>
			<td><fmt:message key="form.registrazioni.mercatiUso"/>:</td>
			<td>&nbsp;<strong>${registrazioni.entity.mercatiUso.descrizione}</strong></td>
	
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.mercati.posteggio"/>:</td>
			<td colspan="3">&nbsp;<strong>${registrazioni.entity.mercatiD.codiceposteggio}</strong></td>
		</tr>
	</table>
	
	
	<%
	
	String showHideChecked = ""; 
	String defaultStyleInfo = "display:none;";
	if(((String)request.getAttribute(WebConstants.CONF_UTENTE_REGISTRAZIONI_SHOW_INFO)).equals("1")){
	    showHideChecked = " checked ";
	    defaultStyleInfo = "";
	}
	
	%>

 
	
	<input type="checkbox" <%=showHideChecked %> name="scelta" onclick="showInfo(this);" /><label for="scelta"><fmt:message key="form.registrazioni.mostra_nascondi.info" /></label>
    <div class="jmesa" style="<%= defaultStyleInfo %>" id="concessioni">
	<table border="0" width="70%" cellpadding="2" cellspacing="0">
		<caption ><fmt:message key="form.registrazioni.concessioni"/></caption>
		<thead>
			<tr class="header">
				<td><fmt:message key="form.registrazioni.concessioni.numero"/></td>
				<td><fmt:message key="form.registrazioni.concessioni.datarilascio"/></td>
				<td><fmt:message key="form.registrazioni.concessioni.istanza"/></td>
				<td><fmt:message key="form.registrazioni.concessioni.titolare"/></td>
				<td><fmt:message key="form.registrazioni.concessioni.causaleconcessione"/></td>
				<td><fmt:message key="form.registrazioni.concessioni.scadenza"/></td>
			</tr>
		</thead>
		<tbody class="tbody">
		<%int j=1;%>
			<c:forEach items="${concessioniList}" var="concessioni_var">
				<tr>
					<td class="<%=(j%2)==0?"odd":"even"%>">						
						<a href="javascript:historySet('${_urlback}','../autorizzazioni/viewConcessione.htm?codiceIstanza=${concessioni_var.istCodiceistanza}&codiceAutorizzazione=${concessioni_var.concId}&software=${ concessioni_var.software.codice }');" title="<fmt:message key="label.edit.record" /> ${concessioni_var.concNumero}">
							${concessioni_var.concNumero }
						</a>					
					</td>			
					<td class="<%=(j%2)==0?"odd":"even"%>"><fmt:formatDate value="${concessioni_var.concDatarilascio }" pattern="dd/MM/yyyy"/></td>
					<td class="<%=(j%2)==0?"odd":"even"%>">
						<a href ="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${concessioni_var.istCodiceistanza }&software=${ concessioni_var.software.codice }');" >${ concessioni_var.istNumeroistanza }</a >
					</td>
					<td class="<%=(j%2)==0?"odd":"even"%>">${concessioni_var.descrizioneOccupante }</td>
					<td class="<%=(j%2)==0?"odd":"even"%>">${concessioni_var.iconcCausale }</td>
					<td class="<%=(j%2)==0?"odd":"even"%>"><fmt:formatDate value="${concessioni_var.concDatascadenza }" pattern="dd/MM/yyyy"/></td>
				</tr>
				<%j++; %>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</fieldset>
	</c:if>
</c:if>











<c:if test="${registrazioni.displayMode==registrazioni.displayConstants.NEW || registrazioni.displayMode==registrazioni.displayConstants.EDIT}">
<fieldset><legend><fmt:message key="form.registrazioni.dettaglioregistrazione"/></legend>
	
   
    
    <table>
	    <c:if test="${registrazioni.entity.id.codice!=null}">
		<tr>
			<td><fmt:message key="form.registrazioni.progressivo" /></td>
			<td><spring-form:input id="progressivo_id" path="entity.progressivo" size="12" maxlength="7" readonly="true"/>
			<spring-form:errors path="entity.progressivo" cssClass="error"/></td>
		</tr>
		</c:if>
		<tr>
			<td><fmt:message key="form.registrazioni.dataregistrazione" /></td>
			<td>
				<spring-form:input id="dataregistrazione_id" path="entity.dataRegistrazione" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
				<init:calendar imagePath="/images/cal.gif" idImage="caldataregistrazione" idInput="dataregistrazione_id" textKey="label.calendar"/>
				<spring-form:errors	path="entity.dataRegistrazione" cssClass="error" />
		   		<spring-form:hidden id="datasistema_id" path="entity.dataSistema"/> 
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.registrazioniCausali" /></td>
			<td>
				<spring-form:select id="regCausali" path="entity.registrazioniCausali.id.codice"  onchange="assegna(this);">
				    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${registrazioniCausaliList}" itemValue="id.codice"	itemLabel="descrizione" />
				</spring-form:select> 
				<spring-form:errors path="entity.registrazioniCausali" cssClass="error" />
				
			</td>
		</tr>		
		<tr id="mercati" style="<%=mercati%>">
			<td><fmt:message key="form.registrazioni.mercati" /></td>
			<td>
				<script type="text/javascript">
					function setHiddenFieldmercati(inputField,listItem){
						var a = listItem.id;
						document.getElementById('mercati_id').value = inputField.value;
						document.getElementById('mercati_hidden').value = a;
						$('mercati_id_choices').fade();	 
						assegnaPosteggio();
						mercatiUsoDisplay();
					}
				</script>
				<spring-form:input id="mercati_id" path="entity.mercatiD.mercati.descrizione" cssClass="searchbox" onchange="checkValue(this,'mercati_hidden')"  onkeydown="javascript:return searchAll(this,event)" size="67" />
				<init:autocompleter methodAjax="findMercati.htm" afterUpdateElement="setHiddenFieldmercati" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
				<spring-form:errors path="entity.mercatiD" cssClass="error"/> 
				<spring-form:hidden id="mercati_hidden" path="entity.mercatiD.mercati.id.codice" />
			</td>
		</tr>
		
		<tr id="mercatiPosteggio" style="<%=mercatiPosteggio%>">
			<td><fmt:message key="form.registrazioni.mercati.posteggio" /></td>
			<td>
				<spring-form:select id="selectPosteggio" path="entity.mercatiD.id.codice">
					<spring-form:option value="${registrazioni.entity.mercatiD.id.codice}" label="${registrazioni.entity.mercatiD.codiceposteggio}"></spring-form:option>
				</spring-form:select>
				<spring-form:errors path="entity.mercatiD" cssClass="error"/> 
			</td>
		</tr>
		<tr id="mercatiUso" style="<%=mercatiUso%>">
			<td><fmt:message key="form.registrazioni.mercatiUso" /></td>
			<td>
				<spring-form:select id="selectMercatiUso" path="entity.mercatiUso.id.codice">
				<spring-form:option value="${registrazioni.entity.mercatiUso.id.codice}" label="${registrazioni.entity.mercatiUso.descrizione}"></spring-form:option>
				</spring-form:select>
				<spring-form:errors path="entity.mercatiUso" cssClass="error"/> 
			</td>
		</tr>
		<tr id="endo" style="<%=inventProc%>">
			<td><fmt:message key="form.registrazioni.istanza" /></td>
			<td>
				<script type="text/javascript">
				function setHiddenFieldistanze(inputField,listItem){
					var a = listItem.id;
					document.getElementById('istanze_id').value = inputField.value;
					document.getElementById('istanze_hidden').value = a;
					$('istanze_id_choices').fade();	 
					removeOptionSelected("selectProc");
					assegnaEndo();
				}
				</script>
				<spring-form:input id="istanze_id" path="entity.istanze.numeroistanza" cssClass="searchbox" onchange="checkValue(this,'istanze_hidden')"  onkeydown="javascript:return searchAll(this,event)" size="67" />
				<init:autocompleter methodAjax="findIstanze.htm" afterUpdateElement="setHiddenFieldistanze" idHidden="istanze_hidden" idInput="istanze_id" inputTitleKey="label.ricerca_istanza"/>
				<spring-form:errors path="entity.istanze" cssClass="error"/> 
				<spring-form:hidden id="istanze_hidden" path="entity.istanze.id.codice" />
			</td>
		</tr>
		
		<tr id="inventProc" style="<%=inventProc%>">
			<td><fmt:message key="form.registrazioni.inventarioproc" /></td>
			<td> 
				<spring-form:select id="selectProc" path="entity.inventarioprocedimenti.id.codice">
					<spring-form:option value="${registrazioni.entity.inventarioprocedimenti.id.codice}" label="${registrazioni.entity.inventarioprocedimenti.procedimento}"></spring-form:option>
				</spring-form:select>
		 		<spring-form:errors path="entity.inventarioprocedimenti" cssClass="error"/> 	
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioni.anagrafe" />
			</td>
			<td>
				<spring-form:input id="anagrafe_id" path="entity.anagrafe.descrizioneRichiedente" cssClass="searchbox" onkeydown="javascript:return searchAll(this,event,3)" onchange="checkValue(this,'anagrafe_hidden')" size="67"/>
				<init:autocompleter methodAjax="findAnagrafe.htm" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente" />
				<spring-form:errors path="entity.anagrafe" cssClass="error"/> 
				<spring-form:hidden id="anagrafe_hidden" path="entity.anagrafe.id.codice"  />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioni.operatore" />
			</td>
			<td>
				<spring-form:input id="responsabili_id" path="entity.responsabili.responsabile" cssClass="searchbox" onkeydown="javascript:return searchAll(this,event)"onchange="checkValue(this,'responsabili_hidden')" size="67"/>
				<init:autocompleter methodAjax="findResponsabili.htm" idHidden="responsabili_hidden" idInput="responsabili_id" inputTitleKey="label.ricerca_responsabile"/>
				<spring-form:errors path="entity.responsabili.responsabile" cssClass="error"/> 
				<spring-form:hidden id="responsabili_hidden" path="entity.responsabili.id.codice"  />
				<spring-form:hidden id="responsabiliSistema_hidden" path="entity.responsabiliSistema.id.codice"  />
			</td>
		</tr>		
		<tr>
			<td><fmt:message key="form.registrazioni.descrizione" /></td>
			<td class="inline-ui-cell">
				<spring-form:textarea id="descrizione_id" path="entity.descrizione" cols="70" rows="2" />
				<init:help idHelp="help_descrizione_id" textKey="form.registrazioni.descrizione.help"/>
				<spring-form:errors path="entity.descrizione" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioni.note" />
			</td>
			<td>
				<spring-form:textarea id="note_id" path="entity.note" cols="70" rows="4" />
				<spring-form:errors path="entity.note" cssClass="error"/> 
			</td>
		</tr>
	</table>
	</fieldset>
	
	<script type='text/javascript'>
		if($('descrizione_id')){
			$('descrizione_id').focus();
		}
			window.onload=function() {
			  var elSel = document.getElementById("regCausali");
			  if (elSel.options[1]) {
				  new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiEndo.htm', {
					  method: 'get',
					  parameters: {code: elSel.options[elSel.selectedIndex].value, limit: 12},
					  onSuccess: function(transport){
						  var response = transport.responseText;
						  if(response == '0' ){
							$('mercati').fade();
							$('mercatiUso').fade();
							$('endo').fade();
							$('inventProc').fade();							
							$('mercatiPosteggio').fade();
							$('mercati').value='';
							$('mercatiUso').value='';
							$('endo').value='';
							$('inventProc').value='';
							$('mercatiPosteggio').value='';
						}
					      if(response == '1' ){
					    	  $('mercati').appear();
					    	  $('mercatiUso').appear();
					    	  $('mercatiPosteggio').appear();
					    	  $('endo').fade();
							  $('endo').value='';
							  $('inventProc').fade();
							  $('inventProc').value='';
							  $('selectProc')[$('selectProc').selectedIndex].value='';
					      }
						  if(response == '2'){
							  $('mercatiPosteggio').fade();
							  $('mercati').fade();
							  $('mercatiUso').fade();
							  $('endo').appear();
							  $('inventProc').appear();
							}
					    },
					  onFailure: function(){ 
					    	$('mercati').fade();
					    	$('endo').fade();
							$('inventProc').fade();
							$('mercatiPosteggio').fade();
							$('mercati').value='';
							$('mercatiPosteggio').value='';
							$('mercatiUso').value='';
					   }
									  
					  });
			  }
			};
			
		function assegna(elem){
			var code=elem[elem.selectedIndex].value;
			
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findMercatiEndo.htm', {
				  method: 'get',
				  parameters: {code: code, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  if(response == '0' ){
						$('mercati').fade();
						$('endo').fade();
						$('inventProc').fade();
						$('mercatiUso').fade();
						$('mercatiPosteggio').fade();
					}
				      if(response == '1' ){
				    	  $('mercati').appear();
				    	  $('mercatiPosteggio').appear();
				    	  $('endo').fade();
						  $('inventProc').fade();
						  if(document.getElementById("mercati_hidden").value != ''){
						  $('mercatiUso').appear();
						  }
						  
				      }
					  if(response == '2'){
						  $('mercatiPosteggio').fade();
						  $('mercati').fade();
						  $('mercatiUso').fade();
						  $('endo').appear();
						  $('inventProc').appear();
		  
					  }
				    },
				  onFailure: function(){ 
				    	$('mercati').fade();
						$('inventProc').fade();
						$('endo').fade();
						$('mercatiPosteggio').fade();
				   }
								  
				  });		
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
			var idMercato=document.getElementById("mercati_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findPosteggioMercato.htm', {
				  method: 'post',
				  parameters: {code: idMercato, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("mercatiPosteggio").appear();
					  var opts=response.split(",");
					  for(var i=0;i<((opts.length)-1); i++ ){
						  var j=i+1;
						  try{
						  	$('selectPosteggio').add(new Option( opts[j], opts[i]),  $('selectPosteggio').options[i]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectPosteggio').add(new Option( opts[j], opts[i]));
						  }
						  i++;
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

        function assegnaEndo(){
        	removeOptionSelected("selectProc");
			var code=$("istanze_hidden").value;
			new Ajax.Request('<%=request.getContextPath()%>/ajax/findInventarioEndo.htm', {
				  method: 'post',
				  parameters: {code: code, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  $("inventProc").appear();
					  var opts=response.split(",");
					  for(var i=0;i<((opts.length)-1); i++ ){
						  var j=i+1;
						  try{
						  	$('selectProc').add(new Option( opts[j], opts[i]),  $('selectProc').options[i]);
						  }
						  catch(e){ //in IE, try the below version instead of add()
							  $('selectProc').add(new Option( opts[j], opts[i]));
						  }
						  i++;
					  }
				    },
				  onFailure: function(){ alert('Something went wrong...'); }
								  
				  });



		}
	</script>
</c:if>	
<c:if test="${not (registrazioni.displayMode == registrazioni.displayConstants.NEW || registrazioni.displayMode == registrazioni.displayConstants.EDIT)}">
		<fieldset><legend><fmt:message key="form.registrazioni.dettaglioimporti"/></legend>
	<%
	
	String showChecked = ""; 
	String defaultInfo = "display:none;";
	if(((String)request.getAttribute(WebConstants.CONF_UTENTE_REGISTRAZIONI_RATE_GROUPED)).equals("1")){
	    showChecked = " checked ";
	    defaultInfo = "";
	}
	
	%>	
    

    <input type="checkbox" <%=showChecked%>  onclick="changeVisualizzazione(this);" /><label for="scelta"><fmt:message key="form.registrazioni.informazioniraggruppate" /></label>
    <div class="jmesa">
    
    <!-- TABELLA NON RAGGRUPPATA START -->
    <c:if test="${configurazionerate eq 0}">
				<table border="0" width="50%" cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td width="5%"><fmt:message key="form.registrazioniimporti.nrRata"/></td>
							<td width="10%"><fmt:message key="form.registrazioniimporti.scadenza"/></td>
							<td width="30%"><fmt:message key="form.registrazioniimporti.conti"/></td>
							<td  width="5%" align="right"><fmt:message key="form.registrazioniimporti.importo"/></td>
						<%--	<td  width="5%" align="right"><fmt:message key="form.registrazioniimporti.interessi"/></td>--%>
							<c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false}">
							<td align="right" width="5%"><fmt:message key="form.registrazioniimporti.versato"/></td>
							</c:if>
							<td width="15%"><fmt:message key="form.registrazioniimporti.riferimenti"/></td>
							<c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false}">
							<td align="right" width="5%"><fmt:message key="form.registrazioniimporti.debito"/></td>
							</c:if>
							<td align="right" width="10%"></td>
						</tr>
					</thead>
					<tbody class="tbody" >
								
					<c:set var="incassato" value="0.0"/>
					<c:set var="ridotto" value="0.0"/>
					<%--<c:set var="interessi" value="0.0"/>--%>
					<%int j=1;%>
					<c:forEach var="rata_var" items="${registrazioni.entity.listaRate}" varStatus="rataStatus">
							<c:set var="rataScritta" value="false"/>
							<c:set var="rowspan" value="${fn:length(rata_var.registrazioniImportiList)}" />
							<%int i=1;%>
							<c:forEach var="registrazioniImporti_var"
										items="${rata_var.registrazioniImportiList}" varStatus="vsi">
								<tr>
									<c:if test="${rataScritta == false}">
										<c:if test="${rowspan > 1}">
											<c:set var="valign" value="valign='top'" />
										</c:if>
										<c:if test="${rowspan < 2}">
											<c:set var="valign" value="valign='top'" />
										</c:if>
										<td class="<%=(j%2)==0?"odd":"even"%>" rowspan="${rowspan}"  valign="top">
											${rata_var.numeroRata}
										</td>
										<td class="<%=(j%2)==0?"odd":"even"%>" rowspan="${rowspan}" valign="top" >
											<fmt:formatDate pattern="dd/MM/yyyy" value="${registrazioniImporti_var.scadenza}" />
										</td>																
									<c:set var="rataScritta" value="true"/>
									</c:if>		
											
										<td class="<%=(j%2)==0?"odd":"even"%>" valign="top">${registrazioniImporti_var.conti.descrizione}</td> 
										<td align="right"  class="<%=(j%2)==0?"odd":"even"%>" ${valign}><fmt:formatNumber minFractionDigits="2" value="${registrazioniImporti_var.importo}" /></td>
										<%--<td align="right"  class="<%=(j%2)==0?"odd":"even"%>" ${valign}><fmt:formatNumber minFractionDigits="2" value="${registrazioniImporti_var.interessi}" /></td>--%>
										<c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false}">
										<td align="right" class="<%=(j%2)==0?"odd":"even"%>" ${valign}><fmt:formatNumber minFractionDigits="2" value="${registrazioniImporti_var.importo - registrazioniImporti_var.rimanenza}" /></td>
										<td class="<%=(j%2)==0?"odd":"even"%>" id="riferimento_dettaglio${registrazioniImporti_var.id.codice}"  ${valign}>
										<c:if test="${(registrazioniImporti_var.importo - registrazioniImporti_var.rimanenza) > 0}">
										<script	type="text/javascript">
										var riferimentoVisibile=false;
										dettaglioRiferimento${registrazioniImporti_var.id.codice}(${registrazioniImporti_var.id.codice});

										function dettaglioRiferimento${registrazioniImporti_var.id.codice}(codiceImporto){
												if(riferimentoVisibile==false){
												new Ajax.Request('<%=request.getContextPath()%>/ajax/dettaglioRiferimento.htm', {
													  method: 'post',
													  parameters: {codiceImporto: codiceImporto},
													  onSuccess: function(transport){
														  var response = transport.responseText;		
														  $("riferimento_dettaglio"+${registrazioniImporti_var.id.codice}).innerHTML = response;
														  $("riferimento_dettaglio"+${registrazioniImporti_var.id.codice}).appear();
														  applyStyle();							  
													    },
													  onFailure: function(transport){ 
														var response = transport.responseText;
													    alert(response); }						    		 
													  });
												riferimentoVisibile=true;
												}else{
													$("riferimento_dettaglio"+${registrazioniImporti_var.id.codice}).dropOut();
													riferimentoVisibile=false;
												}
												  
											}
										</script>
										</c:if>
										</td>
										</c:if>
										<td align="right"  class="<%=(j%2)==0?"odd":"even"%>" ${valign}>								
											<c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false}">
											<c:if test="${registrazioniImporti_var.nonPrevedeIncassi eq true}">
											<label style="text-decoration: line-through;">
											<fmt:formatNumber minFractionDigits="2" value="${registrazioniImporti_var.rimanenza}" />
											</label> (Ridotto)
											</c:if>
											<c:if test="${registrazioniImporti_var.nonPrevedeIncassi eq false}">
											<fmt:formatNumber minFractionDigits="2" value="${registrazioniImporti_var.rimanenza}" />
											</c:if>
											</c:if>
										</td>
										<td align="left"  class="<%=(j%2)==0?"odd":"even"%>" ${valign} >
										<c:if test="${registrazioniImporti_var.nonPrevedeIncassi eq false}">
										<a class="dettaglioColumn" href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fregistrazioni%2FviewRegistrazioneimporti.htm?codice=${registrazioniImporti_var.id.codice}"  title="<fmt:message key="label.edit.record" /> ${registrazioniImporti_var.id.codice}">
											<label><fmt:message key="label.edit.record.image" /></label>
										</a>										
										<a class="eliminaRiga" href="javascript:doSubmit('deleteRegistrazioniImporti.htm?codiceImporto=${registrazioniImporti_var.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" title="<fmt:message key="label.elimina" /> ${registrazioniImporti_var.id.codice}">
											<label><fmt:message key="label.elimina.image" /></label>
										</a>
										</c:if>
										</td>
									
									<c:set var="incassato" value="${incassato+(registrazioniImporti_var.importo - registrazioniImporti_var.rimanenza)}"/>
									<%--<c:set var="interessi" value="${interessi + registrazioniImporti_var.interessi}" />--%>
									<c:if test="${registrazioniImporti_var.nonPrevedeIncassi eq true}">
									<c:set var="ridotto" value="${ridotto+(registrazioniImporti_var.rimanenza)}"/>
									</c:if>
								</tr>
								<%i++;%>
							</c:forEach>
							<%j++;%>
					</c:forEach>
						
						<tr>
							<td colspan="7" align="left">
								<a class="vbg-btn btn-aggiungi" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fregistrazioni%2FcreateImporto.htm?registrazionecodice=${registrazioni.entity.id.codice}','')" title="<fmt:message key="label.nuovo" />">
								</a>
							</td>
						</tr>
						
					</tbody>
					<tfoot>
						<tr class="header">
							
							<td colspan="3" align="right"><b><fmt:message key="label.totalColumn" /></b></td>
							<td align="right"><fmt:formatNumber minFractionDigits="2" value="${registrazioni.entity.importo}"/></td>
							<%--<td align="right"><fmt:formatNumber minFractionDigits="2" value="${interessi}"/></td>--%>
							<c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false}">
							<td align="right">
							<fmt:formatNumber minFractionDigits="2" value="${incassato}"/>
							</td>
							<td>&nbsp;</td>
							<td align="right">							
							<c:set var="rimanenza" value="${registrazioni.entity.importo-incassato-ridotto}"></c:set>
							<c:if test="${rimanenza gt 0}">
                            &nbsp;<a class="assegnaColumn" style="float: none;" href="javascript:vaiAScadenze('${registrazioni.entity.progressivo}','${registrazioni.entity.anagrafe.id.codice}');"><label>ass</label></a>
                            </c:if>														
							<fmt:formatNumber minFractionDigits="2" value="${rimanenza}"/>
							</td>
							<td>&nbsp;</td>
							</c:if>
							<c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq true}">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							</c:if>
						</tr>
					</tfoot>
				</table>
	     </c:if>
         <!-- TABELLA NON RAGGRUPPATA END -->

        <!-- TABELLA  RAGGRUPPATA START -->     
        <c:if test="${configurazionerate eq 1}">
        <table border="0" width="50%" cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td width="5%"><fmt:message key="form.registrazioniimporti.nrRata"/></td>
							<td width="10%"><fmt:message key="form.registrazioniimporti.scadenza"/></td>
						    <td  width="5%" align="right"><fmt:message key="form.registrazioniimporti.importo"/></td> 
						    <c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false}">
							<td align="right" width="5%"><fmt:message key="form.registrazioniimporti.versato"/></td>
							</c:if>
							<td width="15%"><fmt:message key="form.registrazioniimporti.riferimenti"/></td>
							<c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false}">
							<td align="right" width="5%"><fmt:message key="form.registrazioniimporti.debito"/></td>
							</c:if>
						</tr>
					</thead>
					<tbody class="tbody" >
								
					<c:set var="incassato" value="0.0"/>
					<c:set var="ridotto" value="0.0"/>
					<%--<c:set var="interessi" value="0.0"/>--%>
					<%int j=1;%>
					<c:forEach var="rata_var" items="${registrazioni.entity.listaRate}" varStatus="rataStatus">
							<c:set var="rataScritta" value="false"/>
							<c:set var="rowspan" value="${fn:length(rata_var.registrazioniImportiList)}" />
							<%int i=1;%>
							<c:forEach var="registrazioniImporti_var"
										items="${rata_var.registrazioniImportiList}" varStatus="vsi">
								<tr>
									<c:if test="${rataScritta == false}">
										<c:if test="${rowspan > 1}">
											<c:set var="valign" value="valign='top'" />
										</c:if>
										<c:if test="${rowspan < 2}">
											<c:set var="valign" value="valign='top'" />
										</c:if>
										<td class="<%=(j%2)==0?"odd":"even"%>" rowspan="${rowspan}"  valign="top">
											${rata_var.numeroRata}
										</td>
										<td class="<%=(j%2)==0?"odd":"even"%>" rowspan="${rowspan}" valign="top" >
											<fmt:formatDate pattern="dd/MM/yyyy" value="${registrazioniImporti_var.scadenza}" />
										</td>
                                        <td align="right" class="<%=(j%2)==0?"odd":"even"%>" rowspan="${rowspan}" valign="top" >
											<fmt:formatNumber minFractionDigits="2" value="${rata_var.importoRata}" />
										</td>	
 
                                        <c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false}">
										<td align="right" class="<%=(j%2)==0?"odd":"even"%>" rowspan="${rowspan}" valign="top" >
										        <fmt:formatNumber minFractionDigits="2" value="${rata_var.incassato}" />
                                        </td>
                                        
                                        <td class="<%=(j%2)==0?"odd":"even"%>" valign="top">
                                        <span id="riferimento_dettaglio${registrazioniImporti_var.id.codice}" style="vertical-align: text-top;"></span>
										<%--
										<c:if test="${(registrazioniImporti_var.importo - registrazioniImporti_var.rimanenza) > 0}">
										 --%>
											<script	type="text/javascript">
											var riferimentoVisibile=false;
											function dettaglioRiferimento${registrazioniImporti_var.id.codice}(codiceImporto){
													var ts_ = new Date().getTime();
													if(riferimentoVisibile==false){
													new Ajax.Request('<%=request.getContextPath()%>/ajax/dettaglioRiferimentoRata.htm', {
														  method: 'post',
														  parameters: {codiceImporto: codiceImporto, numeroRata: ${rata_var.numeroRata}, ts: ts_},
														  onSuccess: function(transport){
															  var response = transport.responseText;		
															  $("riferimento_dettaglio"+${registrazioniImporti_var.id.codice}).innerHTML = response;
															  $("riferimento_dettaglio"+${registrazioniImporti_var.id.codice}).appear();
															  applyStyle();							  
														    },
														  onFailure: function(transport){ 
															var response = transport.responseText;
															jQuery('#_debugInfoId').addClass('error_header');
														    jQuery('#_debugInfoId').text(response);
														    jQuery('#_debugInfoId').show();
														  }						    		 
														  });
													riferimentoVisibile=true;
													}else{
														$("riferimento_dettaglio"+${registrazioniImporti_var.id.codice}).dropOut();
														riferimentoVisibile=false;
													}
													  
												}
												dettaglioRiferimento${registrazioniImporti_var.id.codice}(${registrazioniImporti_var.id.codice});
											
											</script>
											<%--
										</c:if>
										--%>
										</td>
                                        
                                        </c:if>
                                        
                                        						
									    												
									
									</c:if>	
									 	<c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false}">
									 
									    
											<c:if test="${rataScritta == false}">	
                                        <td align="right" class="<%=(j%2)==0?"odd":"even"%>" rowspan="${rowspan}" valign="top" >
                                                 <fmt:formatNumber minFractionDigits="2" value="${rata_var.rimanenza}" />	
					                            <c:if test="${rata_var.rimanenza gt 0}">
					                            &nbsp;<a class="assegnaColumn" style="float: none;" href="javascript:pannelloAssegna('${registrazioni.entity.id.codice}','${rata_var.numeroRata}');"><label>ass</label></a>
					                            </c:if>							                                                 
                                        </td>
                                        <c:set var="rataScritta" value="true"/>
										</c:if>	
                                        </c:if>
                                        <c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq true}">
                                        <td class="<%=(j%2)==0?"odd":"even"%>"></td>
                                        </c:if>
	                           
								
                                    <c:if test="${registrazioniImporti_var.nonPrevedeIncassi eq true}">
									<c:set var="ridotto" value="${ridotto+(registrazioniImporti_var.rimanenza)}"/>
									</c:if>
								<c:set var="incassato" value="${incassato+(registrazioniImporti_var.importo - registrazioniImporti_var.rimanenza)}"/>
								</tr>
								<%i++;%>
							</c:forEach>
                            <%j++;%>
					</c:forEach>
						
						<tr>
							<td colspan="7" align="left">
								<a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fregistrazioni%2FcreateImporto.htm?registrazionecodice=${registrazioni.entity.id.codice}','')" 
								class="vbg-btn btn-aggiungi" title="<fmt:message key="label.nuovo" />"></a>
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr class="header">
							
							<td colspan="2" align="right"><b><fmt:message key="label.totalColumn" /></b></td>
							<td align="right"><fmt:formatNumber minFractionDigits="2" value="${registrazioni.entity.importo}"/></td>
							<%--<td align="right"><fmt:formatNumber minFractionDigits="2" value="${interessi}"/></td>--%>
							<c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false}">
							<td align="right">
							<fmt:formatNumber minFractionDigits="2" value="${incassato}"/>
							</td>
							<td>&nbsp;</td>
							<td align="right">							
							<c:set var="rimanenza" value="${registrazioni.entity.importo-incassato-ridotto}"></c:set>
							<c:if test="${rimanenza gt 0}">
                            &nbsp;<a class="assegnaColumn" style="float: none;" href="javascript:vaiAScadenze('${registrazioni.entity.progressivo}','${registrazioni.entity.anagrafe.id.codice}');"><label>ass</label></a>
                            </c:if>							
							<fmt:formatNumber minFractionDigits="2" value="${rimanenza}"/>
							</td>
							
							</c:if>
							<c:if test="${registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq true}">
							<td>&nbsp;</td>
							</c:if>
						</tr>
					</tfoot>
				</table>

        
        </c:if>
         <!-- TABELLA  RAGGRUPPATA START -->  
		</div>
	</fieldset>
</c:if>
</spring-form:form>
<div id="pannelloAssegna_id" dojoType="dijit.Dialog" title="<fmt:message key="label.registra_pagamento"/>" style="display: none;">
	<div id="pannelloAssegna_content_id"></div>
</div>

</div>
<script type="text/javascript">
	function searchAll(inputField,evt){
	 var charCode = (evt.which) ? evt.which : event.keyCode;
	 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
	   inputField.value='%';
	 }
	}
	// onkeydown="javascript:return searchAll(this,event)"

	
	function saveUserPreference(nomeparametro, valore){
		new Ajax.Request('<%=request.getContextPath()%>/registrazioni/salvaPreferenza.htm', {
			  method: 'post',
			  parameters: {nomeparametro: nomeparametro,valore: valore},
			  onSuccess: function(transport){ },
			  onFailure: function(transport){ 
				var response = transport.responseText;
			    alert(response); }						    		 
			  });			
	}


	function showInfo(obj){
		var valore = 0;
		if(obj){
			if(obj.checked==true){
				valore = 1;
				$('concessioni').style.display="block";
			}else{
				$('concessioni').style.display="none";
			}
		}
		saveUserPreference('<%= WebConstants.CONF_UTENTE_REGISTRAZIONI_SHOW_INFO %>',valore);
	}

	function changeVisualizzazione(obj){
		var valore = 0;
		if(obj){
			if(obj.checked==true){
				valore = 1;
			}
		}
		saveUserPreference('<%= WebConstants.CONF_UTENTE_REGISTRAZIONI_RATE_GROUPED %>',valore);
		setTimeout("refreshThisView();", 500);
	}
	function refreshThisView(){		
		doHref('view.htm?codice=${registrazioni.entity.id.codice}','');
	}
	
	
	function vaiAScadenze(progressivo, codiceAnagrafe){
		var goToUrl = "../registrazioniinout/listScadenze.htm?filter.progressivo="+progressivo+
					"&filter.anagrafe.id.codice="+codiceAnagrafe;
		goToUrl = escape(goToUrl);
		doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'');
	}
	
	jQuery(document).ready(function(){
		var jqxhr = jQuery.ajax({
			  url: "ajaxPannelloRisultati.htm",
			  context: document.body,
			  cache: false,				
			  dataType: "html",
			  data: "codice=${registrazioni.entity.id.codice}",
			  success: function(dataResult) { 
					var cntnr = jQuery('#pannello_risultati_id');
					cntnr.empty();	  
				   cntnr.append(dataResult);
				   applyStyle();
				},
			  error: function(dataError){
				  alert(dataError.innerText);
			  }	
			});
	});
	
	
	function pannelloAssegna(idRegistrazione,numeroRata){
		
		var jqxhr = jQuery.ajax({
			  url: "ajaxPannelloPagamento.htm",
			  context: document.body,
			  cache: false,				
			  dataType: "html",
			  data: "codice="+idRegistrazione+"&nrRata="+numeroRata,
			  success: function(dataResult) { 
				   $('pannelloAssegna_content_id').innerHTML = dataResult;
				   dijit.byId("pannelloAssegna_id").show();
				   applyStyle();
				},
			  error: function(dataError){
				  alert(dataError.innerText);
			  }	
			});
	}
	
	
	
</script>
<br class="clear" />
<div id="functions">
<ul>
	<c:if test="${registrazioni.displayMode eq registrazioni.displayConstants.NEW}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${registrazioni.displayMode!=registrazioni.displayConstants.NEW}">
		<c:if test="${registrazioni.displayMode==registrazioni.displayConstants.VIEW}">
			<li><a href="javascript:doSubmit('view.htm?codice=${registrazioni.entity.id.codice}&displayMode=${registrazioni.displayConstants.EDIT}','',document.inviodati)"><fmt:message key="button.modify" /></a></li>
			
		</c:if>
		<c:if test="${registrazioni.displayMode==registrazioni.displayConstants.VIEW && incassato le 0.00}">
			<c:if test="${(registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false) and (rimanenza gt 0)}">
				<li><a href="javascript:doHref('createRateizzazioni.htm?codice=${registrazioni.entity.id.codice}','')"><fmt:message key="button.rateizzazioni" /></a></li>
			</c:if>
		</c:if>
		<c:if test="${registrazioni.displayMode==registrazioni.displayConstants.EDIT}">
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		</c:if>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		<c:if test="${(registrazioni.entity.registrazioniCausali.nonPrevedeIncassi eq false) and (rimanenza gt 0)}">
		<li><a href="javascript:doSubmit('createRiduzioniAccertamento.htm','',document.inviodati)"><fmt:message key="button.riduzioni" /></a></li>
		</c:if>
	</c:if>
	<c:if test="${registrazioni.displayMode eq registrazioni.displayConstants.VIEW or registrazioni.displayMode eq registrazioni.displayConstants.NEW}">
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>	
	</c:if>
	<c:if test="${registrazioni.displayMode eq registrazioni.displayConstants.EDIT }">
		<li><a href="javascript:doHref('view.htm?codice=${registrazioni.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
	</c:if>
	
</ul>
</div>
</body>
</html>
