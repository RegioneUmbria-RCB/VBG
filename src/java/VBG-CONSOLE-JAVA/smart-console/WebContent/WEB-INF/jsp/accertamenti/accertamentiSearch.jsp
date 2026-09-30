<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoEnum"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="form.registrazioniFilter.title.search" />
	</title>
</head>
<body>
<%
String  mercati="display:none;";
String alberoproc="display:none;";
String  mercatiUso="display:none;";
%>
<span class="titoloPagina">
<fmt:message key="form.registrazioniFilter.title.search" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="registrazioniFilter" name="inviodati">
	<table>
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.registrazioniCausali" />
			</td>
			<td>
				<spring-form:select tabindex="1" id="regCausali" path="registrazioniCausali.id.codice">
				    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${registrazioniCausaliList}" itemValue="id.codice"	itemLabel="descrizione" />
				</spring-form:select>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.conti" />
			</td>
			<td>
				<spring-form:input tabindex="2"  id="conti_id" path="conti.descrizione" cssClass="searchbox" onchange="checkValue(this,'conti_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
				<init:autocompleter methodAjax="findConti.htm" idHidden="conti_hidden" idInput="conti_id" inputTitleKey="label.ricerca_conto"/>
				<spring-form:errors path="conti" cssClass="error"/> 
				<spring-form:hidden id="conti_hidden" path="conti.id.codice"  />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniFilter.datadistinta" /></td>
			<td>
				<fmt:message key="form.registrazioniFilter.data.inizio" />
				<spring-form:input tabindex="3" id="dataInizio_id" path="dataInizio" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dataInizio_id" textKey="label.calendar"/>
				<fmt:message key="form.registrazioniFilter.data.fine" />
				<spring-form:input tabindex="5" id="dataFine_id" path="dataFine" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="dataFine_id" textKey="label.calendar"/>
				<spring-form:errors path="dataFine" cssClass="error" delimiter=" :"/>  
			</td>
		</tr>
				
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.anagrafe" />
			</td>
			<td>
				<spring-form:input tabindex="7" id="anagrafe_id" path="anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden')" onkeydown="javascript:return searchAll(this,event,3)" size="67"/><fmt:message key="ajax.search.minchars"/>
				<init:autocompleter methodAjax="findAnagrafeRegistrazioni.htm" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente"/>
				<spring-form:errors path="anagrafe" cssClass="error"/> 
				<spring-form:hidden id="anagrafe_hidden" path="anagrafe.id.codice"  />
			</td>
		</tr>
		<tr id="mercati" >
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
				<spring-form:input tabindex="8" id="mercati_id" path="mercati.descrizione" cssClass="searchbox" onchange="checkValue(this,'mercati_hidden');" onkeydown="javascript:return searchAll(this,event)"  size="67"/>
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
		<tr id="alberoproc">
			<td valign="top"><fmt:message key="form.registrazioniFilter.alberoproc" /></td>
			<td>
				<%-- 
				<spring-form:input tabindex="10" id="alberoproc_id" path="alberoproc.scDescrizione" cssClass="searchbox" onchange="checkValue(this,'alberoproc_hidden');alberoproc(this);" onkeydown="javascript:return searchAll(this,event)" size="67"/>
				<init:autocompleter methodAjax="findAlberoproc.htm" idHidden="alberoproc_hidden" idInput="alberoproc_id" inputTitleKey="label.ricerca_intervento"/>
				<spring-form:hidden id="alberoproc_hidden" path="alberoproc.id.codice" />
				--%>
				 <jsp:include page="../includes/searchAlberoProc.jsp">
						<jsp:param name="propertyPath" value="alberoproc" />								
						<jsp:param name="pathPropertyDescription" value="alberoproc.scDescrizione" />
						<jsp:param name="pathPropertyCode" value="alberoproc.id.codice" />
						<jsp:param name="isSelectLeafDisable" value="true" />
						<jsp:param name="isSelectNodoPadre" value="true" />
				 </jsp:include>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioniFilter.raggruppamentoEnum" />
			</td>
			<td>
				<spring-form:select tabindex="11" path="raggruppamentoEnum">
					<spring-form:option value="<%=RaggruppamentoEnum.CAUSALE%>"><fmt:message key="form.registrazioniFilter.raggruppamentoEnum.select.Causale" /></spring-form:option>
					<spring-form:option value="<%=RaggruppamentoEnum.CONTO%>"><fmt:message key="form.registrazioniFilter.raggruppamentoEnum.select.Conto" /></spring-form:option>
					<spring-form:option value="<%=RaggruppamentoEnum.ANAGRAFE%>"><fmt:message key="form.registrazioniFilter.raggruppamentoEnum.select.Anagrafe" /></spring-form:option>
                    <spring-form:option value="<%=RaggruppamentoEnum.MERCATO%>"><fmt:message key="form.registrazioniFilter.raggruppamentoEnum.select.mercato" /></spring-form:option>
				</spring-form:select>
			</td>
		</tr>	
        
	</table>	
	<script type='text/javascript'>
	//<![CDATA[ 					
		$('regCausali').focus();
		function mercato(){
			if($('mercati_id').value == ''){
				$('alberoproc').appear();
				
			}else{
			$('alberoproc').fade();
			checkValue('alberoproc_id','alberoproc_hidden');
			}
		}
		function alberoproc(){
			if($('alberoproc_id').value == ''){
			$('mercati').appear();
			}else{
				$('mercati').fade();
				checkValue('mercati_id','mercati_hidden');
			}
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
	//]]> 
	</script>		
</spring-form:form>
</div>
<div id="functions">
<ul>
<li><a tabindex="12" href="javascript:doSubmit('search.htm?1=1','',document.inviodati,'GET',false)"><fmt:message key="button.search" /></a></li>
<li><a tabindex="12" href="javascript:doSubmit('../registrazioniinout/createStampaIVA.htm','',document.inviodati)"><fmt:message key="button.riepilogo.incassi.search" /></a></li>
<li><a tabindex="13" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
