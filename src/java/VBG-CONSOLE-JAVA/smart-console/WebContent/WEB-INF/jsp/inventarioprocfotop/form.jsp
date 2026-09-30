<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		
			<fmt:message key="label.inventarioprocfotop" />
		
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.inventarioprocfotop" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="inventarioprocFoTop" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="inventarioprocFoTop" />
    </jsp:include>
	<table>
		<tr>
			<td  width="20%"><fmt:message key="label.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="120" />
			<spring-form:errors path="descrizione" cssClass="error"/></td>
		</tr>
		
		<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_famigliendo" />
					</td>
					<td>

<div id="id1_famigliaendo" style="display:none;">
		<input type="text" id="famigliaendo_id1" class="searchbox" size="67" onchange="checkValue(this,'famigliaendo_hidden')" onkeydown="return searchAll(this,event)"/>
		<init:autocompleter 
			methodAjax='findTipifamiglieendo.htm?codicesoftware=TT'  
			idHidden="famigliaendo_hidden" 
			idInput="famigliaendo_id1" 
			inputTitleKey="label.ricerca_tipo_famiglia_endo" 
			callBack="" 
			afterUpdateElement=""/>
		
	</div>
	<div id="id2_famigliaendo" style="display:inline;">
	
		<input type="text" id="famigliaendo_id2" class="searchbox" size="67" onchange="checkValue(this,'famigliaendo_hidden')" onkeydown="return searchAll(this,event)"/>		
		<init:autocompleter 
			methodAjax='findTipifamiglieendo.htm?codicesoftware='  
			idHidden="famigliaendo_hidden"  
			idInput="famigliaendo_id2" 
			inputTitleKey="label.ricerca_tipo_famiglia_endo" 
			callBack="" 
			afterUpdateElement=""/>
	</div>
	
	
	<spring-form:hidden id="famigliaendo_hidden"
		path="${param.pathPropertyCode}" />
	<input name="famigliaendo_hidden" type="hidden" id="famigliaendo_hidden"  />
	
	<input type="checkbox" id="id_flag_famigliaendo" onclick="switchAutocompleterfamigliaendo();"/>
	<input type="hidden" id="famigliaendo_software_hidden">
	<init:help idHelp="help_FAMIGLIEENDO" textKey="help.ricerca_per_software_TT"/>
	
	
	<script type="text/javascript">
		function switchAutocompleterfamigliaendo(){
			if($('id_flag_famigliaendo').checked){
			    $('id1_famigliaendo').style.display="inline";
			    $('id2_famigliaendo').style.display="none";
			    $('famigliaendo_id2').value='';
			    $('famigliaendo_hidden').value='';
			    $('famigliaendo_software_hidden').value='TT';
			    
			}else{
				$('id1_famigliaendo').style.display="none";
				$('id2_famigliaendo').style.display="inline";
				$('famigliaendo_id1').value='';
			    $('famigliaendo_hidden').value='';
			    $('famigliaendo_software_hidden').value='';
			}
		}
		
	</script>

					</td>
					

				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_tipiendo" />
					</td>
					<td>
						<script type="text/javascript">
							function filtertipiendo(element, entry) { 
								return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+"&codicesoftware="+document.getElementById("famigliaendo_software_hidden").value;
							}
						</script>
					
						
						<input id="tipiendo_id"  class="searchbox" onchange="checkValue(this,'tipiendo_hidden');" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter callBack="filtertipiendo" methodAjax="findTipiendo.htm" idHidden="tipiendo_hidden" idInput="tipiendo_id" inputTitleKey="label.ricerca_tipiendo"></init:autocompleter>
						<input name="inventarioprocedimenti.tipiendo.id.codice" type="hidden" id="tipiendo_hidden"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" />
					</td>
					<td>
						<script type="text/javascript">
							function inventarioCallBack(inputField,listItem){
								var a = listItem.id;
								document.getElementById('inventarioprocedimento_id').value = inputField.value;
								document.getElementById('inventarioprocedimento_id_hidden').value = a;								
								$('inventarioprocedimento_id_choices').fade();	
							}
							function filterinventario(element, entry) { 
								return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_hidden").value+"&codicesoftware="+document.getElementById("famigliaendo_software_hidden").value;
							}
						</script>
						<spring-form:input id="inventarioprocedimento_id" path="inventarioprocedimenti.procedimento" 
							cssClass="searchbox" 
							onchange="checkValue(this,'inventarioprocedimento_hidden')" 
							onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter methodAjax="findInventarioprocByFamigliaAndCategoriaEndoAndSoftware.htm" 
							idHidden="inventarioprocedimento_id_hidden" 
							idInput="inventarioprocedimento_id" callBack="filterinventario" afterUpdateElement="inventarioCallBack" 
							inputTitleKey="label.ricerca_inventarioprocedimento"/>
						<spring-form:errors path="inventarioprocedimenti" cssClass="error"/> 
						<spring-form:hidden id="inventarioprocedimento_id_hidden" path="inventarioprocedimenti.id.codice"  />						
					</td>
				</tr>
				
				
				
		<tr>
			<td><fmt:message key="label.ordine" /></td>
			<td><spring-form:input id="ordine_id" path="ordine" size="3" maxlength="3" cssStyle="text-align: right;" />
			<spring-form:errors path="ordine" cssClass="error"/></td>
		</tr>
	</table>

	<script type='text/javascript'>
		$('descrizione_id').focus();


		
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${inventarioprocFoTop.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${inventarioprocFoTop.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
