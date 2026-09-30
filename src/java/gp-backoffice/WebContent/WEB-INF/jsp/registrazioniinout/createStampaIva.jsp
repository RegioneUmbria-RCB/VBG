<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@page import="it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoRiepiloghiIncassi"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="form.registrazioniFilter.title.riepilogoincassi" />
	</title>
</head>
<body>
<%
String  mercati="display:none;";
String  mercatiUso="display:none;";
%>
<span class="titoloPagina">
<fmt:message key="form.registrazioniFilter.title.riepilogoincassi" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="registrazioniFilter" name="inviodati">
	<table>
		 
		<tr>
			<td><fmt:message key="form.registrazioniFilter.distinta" /></td>
			<td>
				<fmt:message key="form.registrazioniFilter.data.inizio" />
				<spring-form:input tabindex="1" id="dataInizio_id" path="dataInizio" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dataInizio_id" textKey="label.calendar"/>
				<fmt:message key="form.registrazioniFilter.data.fine" />
				<spring-form:input tabindex="3" id="dataFine_id" path="dataFine" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="dataFine_id" textKey="label.calendar"/>
				<spring-form:errors path="dataFine" cssClass="error" delimiter=" :"/>  
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
				
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.nominativo" />
			</td>
			<td>
				<spring-form:input tabindex="5" id="anagrafe_id" path="anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden')" onkeydown="javascript:return searchAll(this,event,3)" size="67"/><fmt:message key="ajax.search.minchars"/>
				<init:autocompleter methodAjax="findAnagrafeRegistrazioni.htm" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente"/>
				<spring-form:errors path="anagrafe" cssClass="error"/> 
				<spring-form:hidden id="anagrafe_hidden" path="anagrafe.id.codice"  />
			</td>
		</tr>
		<tr id="mercati">
			<td><fmt:message key="form.registrazioniFilter.mercati" /></td>
			<td>
				<script type="text/javascript">
				function setHiddenFieldmercati(inputField,listItem){
					var a = listItem.id;
					document.getElementById('mercati_id').value = inputField.value;
					document.getElementById('mercati_hidden').value = a;
					$('mercati_id_choices').fade();	 
					mercatiUsoDisplay();
				}
				</script>
				<spring-form:input tabindex="6" id="mercati_id" path="mercati.descrizione" cssClass="searchbox" onchange="checkValue(this,'mercati_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67"/>
				<init:autocompleter methodAjax="findMercati.htm" afterUpdateElement="setHiddenFieldmercati" idHidden="mercati_hidden" idInput="mercati_id" inputTitleKey="label.ricerca_manifestazione"/>
				<spring-form:errors path="mercati" cssClass="error"/> 
				<spring-form:hidden id="mercati_hidden" path="mercati.id.codice" />
			</td>
		</tr>
       <tr id="mercatiUso" style="<%=mercatiUso%>">
			<td><fmt:message key="form.registrazioniFilter.mercatiUso" /></td>
			<td>
				<spring-form:select tabindex="7" id="selectMercatiUso" path="mercatiUso.id.codice">
				<spring-form:option value="${registrazioniFilter.mercatiUso.id.codice}" label="${registrazioniFilter.mercatiUso.descrizione}"></spring-form:option>
				</spring-form:select><spring-form:errors path="mercatiUso.id.codice" cssClass="error"/> 
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.raggruppamentoEnum" />
			</td>
			<td>
				<spring-form:select tabindex="8" path="raggruppamentoRiepiloghiIncassi">
					<spring-form:option value="<%=RaggruppamentoRiepiloghiIncassi.ANAGRAFE_MERCATO_POSTEGGIO%>"><fmt:message key="form.registrazioniFilter.raggrRiepIncassi.anagrMercPost" /></spring-form:option>
					<spring-form:option value="<%=RaggruppamentoRiepiloghiIncassi.CONTI%>"><fmt:message key="form.registrazioniFilter.raggrRiepIncassi.conto" /></spring-form:option>
					<spring-form:option value="<%=RaggruppamentoRiepiloghiIncassi.DATADISTINTA_CONTO%>"><fmt:message key="form.registrazioniFilter.raggrRiepIncassi.datadistintaconto" /></spring-form:option>
                <spring-form:option value="<%=RaggruppamentoRiepiloghiIncassi.NESSUN_RAGGRUPPAMENTO%>"><fmt:message key="form.registrazioniFilter.raggrRiepIncassi.nessuno" /></spring-form:option>
                </spring-form:select>
			</td>
		</tr>	
        
	</table>
	
	 	<%
			String urlStampe = BackofficeNETConstants.getUrlTo(request,BackofficeNETConstants.getURL_STAMPA_CONTABILITA_RAGIONERIA(),"",(String)session.getAttribute(WebConstants.SOFTWARE),true);
			pageContext.setAttribute("dyn_url_stampe", urlStampe);
		%>	
	<script type='text/javascript'>
	//<![CDATA[ 
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
		function searchAll(inputField,evt){
			 var charCode = (evt.which) ? evt.which : event.keyCode;
			 if (charCode == '<fmt:message key="ajax.searchall.key" />'){
			   inputField.value='%';
			 }
			}
			
		function stampaRtf(tipoStampa){
			var urlStampe = '${dyn_url_stampe}';
			urlParams = jQuery('form').serialize();
			urlStampe = urlStampe + escape("&" + urlParams+"&tipoStampa="+tipoStampa);
			console.info(urlStampe);
			var wii = window.open(urlStampe,66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
		}		
			
	//]]> 
	</script>		
</spring-form:form>
</div>

<div id="functions">
<ul>
<li><a tabindex="9" href="javascript:doSubmit('stampaiva.htm','',document.inviodati,'',false)"><fmt:message key="button.search" /></a></li>
<!-- §§§BEGIN§§§ -->

	<li><a href="javascript:void 0" onclick="stampaRtf('');"><fmt:message	key="button.stampa" /> 1</a></li>
	<li><a href="javascript:void 0" onclick="stampaRtf('2');"><fmt:message	key="button.stampa" /> 2</a></li>

<!-- §§§END§§§ -->	
<li><a tabindex="10" href="javascript:doHref('../accertamenti/create.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>