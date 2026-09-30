<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
			<fmt:message key="label.gruppi_endoprocedimenti_t" />		
	</title>
</head>
<body>
<span class="titoloPagina">
	
		<fmt:message key="label.gruppi_endoprocedimenti_t" />

</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
        <jsp:param name="commandName" value="gruppiEndoprocedimentiTCommand" />
    </jsp:include>
	<spring-form:form commandName="gruppiEndoprocedimentiTCommand" name="inviodati">
	<table>
		<tr>
			<td><fmt:message key="label.descrizione" /></td>
			<td class="inline-ui-cell"> 
			<spring-form:input id="descrizione_id" path="entity.descrizione" size="70" />
			<spring-form:errors path="entity.descrizione" cssClass="error"/>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="label.num_endo_warning" />
			</td>
			<td class="inline-ui-cell">
				<spring-form:input id="numEndoWarning_id" path="entity.numEndoWarning" cssStyle="text-align:right;" size="5" />
				<spring-form:errors path="entity.numEndoWarning" cssClass="error"/>
				<init:help idHelp="hlpwarning" textKey="label.num_endo_warning.help"/>
			</td>
		</tr>
		<tr>
		   	<td>
				<fmt:message key="label.tipimovimento" />
			</td>
			<td class="inline-ui-cell">
			<jsp:include page="../includes/tipimovimentosearch.jsp" >
				<jsp:param name="idElemento" value="tipoMovimentoInputId" />
				<jsp:param name="pathTipomovimento" value="entity.tipimovimento" />
				<jsp:param name="includiDisabilitate" value="false" />
			</jsp:include>
			</td>
		</tr>
        
	</table>
	

</spring-form:form>

<c:if test="${gruppiEndoprocedimentiTCommand.displayMode == gruppiEndoprocedimentiTCommand.displayConstants.VIEW}">

	<div id="dettaglioGruppi" style="padding:20px;">&nbsp;<img src='${pageContext.request.contextPath}/images/spinner.gif' /></div>

	
	<div>
		<jsp:include page="../includes/autocompletergenerico.jsp">
			<jsp:param name="idElemento" value="inventarioprocedimento" />					
			<jsp:param name="propertyPath" value="gruppiEndoprocedimentiTCommand.inventarioprocedimenti" />	
			<jsp:param name="pathPropertyDescription" value="gruppiEndoprocedimentiTCommand.inventarioprocedimenti.procedimento" />
			<jsp:param name="pathPropertyCode" value="gruppiEndoprocedimentiTCommand.inventarioprocedimenti.id.codice" />
			<jsp:param name="autocompleterAjax" value="findInventarioprocedimentoGruppiEndo.htm" />
			<jsp:param name="titleKey" value="label.ricerca_inventarioprocedimento" />
			<jsp:param name="ajaxCallBack" value="filterinventario" />
			<jsp:param name="afterUpdateElement" value="inventarioCallBack" />
		</jsp:include>
	
		<a class="generaallegato vbg-btn btn-aggiungi" title="aggiungi" href="#" onclick="assegnaEndo()">
														<!-- img src="${pageContext.request.contextPath}/images/add.png"/>  -->
		</a>
		
		<div id="messaggioErrore" class="error_header" style="padding:20px;display:none;"></div>
		<p>&nbsp;</p>
	</div>
</c:if>

</div>
<%--
<hr />
<br />
1 = ${gruppiEndoprocedimentiTCommand.displayMode}
<br />
2 = ${gruppiEndoprocedimentiTCommand.displayConstants.VIEW}
 --%>
 
<div id="functions">
	<ul>
		<c:if test="${gruppiEndoprocedimentiTCommand.displayMode != gruppiEndoprocedimentiTCommand.displayConstants.VIEW}">
			<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
		</c:if>
		<c:if test="${gruppiEndoprocedimentiTCommand.displayMode == gruppiEndoprocedimentiTCommand.displayConstants.VIEW}">
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
	       <li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
    </ul>
</div>

<script type="text/javascript">

var visualizzaDettaglioInfo = function(){
	
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/gruppiendoprocedimentit/ajaxDettaglioGruppi.htm?codice=${gruppiEndoprocedimentiTCommand.entity.id.codice}',
			  context: document.body,
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
				  if(data){					  
					  	jQuery('#dettaglioGruppi').html(data);
					  	jQuery('#dettaglioGruppi').show();
				  }					  
			  }
		});

}



function assegnaEndo(){	
	var codiceInventario = jQuery('#inventarioprocedimento_hidden').val();
	if(codiceInventario!=''){
	var jhqrPr = jQuery.ajax({
		  url: '${pageContext.request.contextPath}/gruppiendoprocedimentit/assegnaEndo.htm?codiceEndo='+codiceInventario+"&codiceGruppo=${gruppiEndoprocedimentiTCommand.entity.id.codice}",
		  context: document.body,
		  cache: false,					  
		  dataType: "html",
		  success: function(data, textStatus, jqXHR){
				  verificaErroreoSuccesso(data);
				  azzeraAutoCompleter();
		  }
		});
	}
	
	}
	function azzeraAutoCompleter(){
		jQuery('#inventarioprocedimento_id').val('');
		jQuery('#inventarioprocedimento_hidden').val('');
	}
function eliminaEndo(idRiga){		
	if(confirm('<fmt:message key="javascript.confirm.delete" />')){
		
		var jhqrPr = jQuery.ajax({
			  url: '${pageContext.request.contextPath}/gruppiendoprocedimentit/ajaxEliminaEndo.htm?idRiga='+idRiga,
			  context: document.body,
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
					  verificaErroreoSuccesso(data);
			  }
			});
		
	}
}	

function cancellaErrore(){
	jQuery('#messaggioErrore').hide();
	jQuery('#messaggioErrore').html('');
}

function verificaErroreoSuccesso(data){
	 // verifica errori o altro e aggiorna
	 if(data=='OK'){
	 	visualizzaDettaglioInfo();
	 }else{
		 jQuery('#messaggioErrore').html(data);
		 jQuery('#messaggioErrore').show();
	 }
}

function inventarioCallBack(inputField,listItem){
	var a = listItem.id;
	document.getElementById('inventarioprocedimento_id').value = inputField.value;
	document.getElementById('inventarioprocedimento_hidden').value = a;
	$('inventarioprocedimento_id_choices').fade();	
}
function filterinventario(element, entry) { 
	cancellaErrore();
	return entry + "&escludiDisabilitati=true";
	/*
		if(document.getElementById("famiglia_id_hidden")){
			return entry + "&escludiDisabilitati=false&codiceFamiglia=" + document.getElementById("famiglia_id_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_id_hidden").value;
		}else{
			return entry + "&escludiDisabilitati=false&codiceTipologia=" + document.getElementById("tipiendo_id_hidden").value;
		}
	*/
}

<c:if test="${gruppiEndoprocedimentiTCommand.displayMode == gruppiEndoprocedimentiTCommand.displayConstants.VIEW}">
	jQuery(document).ready(function(){
		visualizzaDettaglioInfo(); 
	});
</c:if>

</script>

</body>
</html>
