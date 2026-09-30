<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="inventarioprocedimenti.label.lista_endo_base.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="inventarioprocedimenti.label.lista_endo_base.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../inventarioprocedimenti/listEndobase" />
	</jsp:include>
	<div id="subcontent">
		<script type="text/javascript">
		<!--
		function viewContent(idEl, codice){
			$('spinner-'+idEl).style.display='';
			var jhqr = jQuery.ajax({
				  url: 'ajaxShowContent.htm?codice='+codice,
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  success: function(data) {
					  $('spinner-'+idEl).style.display='none';		  
					  $(idEl).innerHTML = data;		
					  applyStyle();			  
					}, 
				  error: function(jqXHR, textStatus, errorThrown){
					   console.error("Errore nella chiamata a [jaxShowContent.htm?idElemento="+idEl+"&codice="+codice+"]: " + jqXHR.responseText);
					  $('spinner-'+idEl).style.display='none';
					  $(idEl).innerHTML = "<b color=red>"+jqXHR.responseText+"</b>";
				  } 						
				});			
		}
		//-->
		</script>
			<form name="inviodati" action="listEndobase.htm">				
				${htmltable}				
			</form>			
			<div dojoType="dijit.Dialog" id="modificaMovDiv" style="overflow: inherit;" title="<fmt:message key="label.dettaglio_movimento" />: ">
			<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 600px; height: 150px;">
				<div id="modificaMov">	
				</div>
			</div>
		</div>			
			<script type="text/javascript">
				var _jmesaUrl='listEndobase.htm?';
				var _captionTab='<fmt:message key="inventarioprocedimenti.label.lista_endo_base.title" />';				
				
				function tabmodificaMov(divId, codice){					
					jQuery("#modificaMov").empty();
					dijit.byId(divId).show();
					modificaMovTab(codice);
				}
				
				function modificaMovTab(codice) {					
					jQuery.ajax({
						  type: "GET",
						  url: '${pageContext.request.contextPath}/inventarioprocedimenti/ajaxCreateRicercaMovimenti.htm?codice='+ codice,
						  dataType: "html",
						  cache: false,
						  success: function(data) {
							  	jQuery("#modificaMov").html(data);							    
							  }
						  ,
						  error: function(dataError){
							  console.error(dataError.innerText);
						  }	
						});					
					}
				
				function eliminaMov(codice, confirmMessage) {		
					if (checkConfirmMessage(confirmMessage)) {
					  	var idEl = "ips_<%=ORMHelper.getIdcomune()%>_" + codice;				  	
						jQuery.ajax({
							  type: "GET",
							  url: '${pageContext.request.contextPath}/inventarioprocedimenti/ajaxDeleteInventarioprocedimentisoftwareMov.htm?codice='+ codice,
							  dataType: "html",
							  cache: false,
							  success: function(data) {							  
								    viewContent(idEl, codice);							  								    
								  }
							  ,
							  error: function(dataError){							  
								  console.error(dataError.innerText);
							  }	
							});		
					}
				}
				
				function updateMov(codice,codiceTipomovimento) {	
				  	var idEl = "ips_<%=ORMHelper.getIdcomune()%>_" + codice;				  	
					jQuery.ajax({
						  type: "GET",
						  url: '${pageContext.request.contextPath}/inventarioprocedimenti/ajaxUpdateInventarioprocedimentisoftwareMov.htm?codice='+ codice +'&codiceTipomovimento=' + codiceTipomovimento,
						  dataType: "html",
						  cache: false,
						  success: function(data) {		
							    dijit.byId('modificaMovDiv').hide();
							    viewContent(idEl, codice);							  								    
							  }
						  ,
						  error: function(dataError){							  
							  console.error(dataError.innerText);
						  }	
						});					
					}			
				
				function setHiddenFieldMovimento(inputField,listItem){
					var a = listItem.id;
					document.getElementById('tipoMovimentoInputId_id1').value = inputField.value;
					document.getElementById('tipoMovimentoInputId_hidden').value = a;					
				}
	
			</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>