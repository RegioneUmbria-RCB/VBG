<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_normative" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_normative" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <div class="parametriDiv">
   		<div class="etichetta">
			<div><fmt:message key="label.endo_procedimento" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${inventarioprocedimenti.procedimento}" /></div>
	 	</div>
	</div>
	<br class="clear"/>
    <div id="subcontent">
	<table width="100%">
	<tr >
		<td >
			<span id="lista_normativeconfigurate" style="display: none;"></span>
		</td>
	</tr>
	
	
		<%
		    
			String displayNormative_inserite= "";
			String styledisplayNormative_inserite = "";
			styledisplayNormative_inserite="sezioneDatiMeno";
			
		%>
		<tr>
			<td></td>
		</tr>
		<tr  >
			<td style="padding-top: 50px;">
			   <fieldset><legend> <a
				class="<%=styledisplayNormative_inserite%>"
				id="id_link_normative_configurate"
				href="javascript:showHidePanelBase('id_link_normative_configurate_table', 'id_link_normative_configurate', ' ', '${pageContext.request.contextPath}/images/','div',false);"
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.elenco_normative_da_attivare"/>">
				<label for="id_link_normative_configurate"><fmt:message key="label.elenco_normative_da_attivare" /></label> </a> </legend>
				<div id="id_link_normative_configurate_table" style="<%=displayNormative_inserite%>;" >
				<form name="leggiForm" action="listnormative.htm">
					<jmesa:springTableFacade
						id="leggi_id" 
						items="${leggiList}" 
						var="leggi_var"
						stateAttr="restore">
						<jmesa:htmlTable>
							<jmesa:htmlRow>
								<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%"/>						
								<jmesa:htmlColumn property="leDescrizione" titleKey="label.descrizione" />
								<jmesa:htmlColumn property="normative.normativa" titleKey="label.normativa" filterEditor="org.jmesa.custom.NormativaDroplist"/>
								<jmesa:htmlColumn property="leggitipi.ltDescrizione" titleKey="label.categoria" filterEditor="org.jmesa.custom.LeggitipoDroplist" />
								<jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="7%">
									<jsp:include page="../includes/visualizzaInfoGenerico.jsp" >
										<jsp:param name="label_dialog" value="label.info_normativa" />
	       								<jsp:param name="methodAjax" value="infoLeggi.htm?codice=${leggi_var.id.codice}"/>
	   									<jsp:param name="descrizione" value="${leggi_var.leDescrizione}" />
	   									<jsp:param name="indice" value="${leggi_var.id.codice}" />
	   									<jsp:param name="sizeDialog" value="700"/>
	   								</jsp:include>
									<a class="addColumn" href="javascript:addNormativa(${leggi_var.id.codice},${inventarioprocedimenti.id.codice})" title="<fmt:message key="label.aggiungi" />&nbsp;${leggi_var.leDescrizione}">
										<label><fmt:message key="label.azioni" /></label>
									</a>
								</jmesa:htmlColumn>
							</jmesa:htmlRow>
						</jmesa:htmlTable>
					 </jmesa:springTableFacade>
					 <input type="hidden" value="${inventarioprocedimenti.id.codice}" name="codiceendo"/>
				</form>
				</div>
				</fieldset>
			</td>
		</tr>
	</table>	
		
		
	<script type="text/javascript">
			mostraListainventarioprocleggi('${inventarioprocedimenti.id.codice}'); 
			
			function mostraListainventarioprocleggi(codiceendo){
					new Ajax.Request('${pageContext.request.contextPath}/ajax/listainventarioprocleggi.htm?codiceendo=${inventarioprocedimenti.id.codice}', {
						method: 'post',	
						onSuccess: function(transport){						
						  $("lista_normativeconfigurate").innerHTML = transport.responseText;
						  $("lista_normativeconfigurate").style.display='';
						  applyStyle();				  
		 				},
		 				onFailure: function(transport){ 
		 				  $("lista_normativeconfigurate").innerHTML= transport.responseText;
		 				  $("lista_normativeconfigurate").style.display='';     				      				  
		 				 }						    		 
		 			});
					}   		
	
				function addNormativa(codlegge,codendo){
				new Ajax.Request(
				'${pageContext.request.contextPath}/inventarioprocedimenti/ajaxAddNormativa.htm?codicelegge='+ codlegge +'&codiceendo='+codendo,
				{
					method : 'post',
					onSuccess : function(transport) {
						var response = transport.responseText;
						
						//$("listaInventarioprocedimenti"+codtipiendo).innerHTML =response;
						//$("listaInventarioprocedimenti"+codtipiendo).appear();
						mostraListainventarioprocleggi('${inventarioprocedimenti.id.codice}');
					},
					onFailure : function(transport) {
						var response = transport.responseText;
						alert(response);
					}
					});
				}
				
				function deleteNormativa(codice,confirmMessage){			
					if (checkConfirmMessage(confirmMessage)) {
						new Ajax.Request('${pageContext.request.contextPath}/inventarioprocedimenti/ajaxDeleteNormativa.htm?codice='+codice, {
							method: 'post',	
							onSuccess: function(transport){						
			 				  mostraListainventarioprocleggi('${inventarioprocedimenti.id.codice}');
			 				},
			 				onFailure: function(transport){ 
				 				  alert(transport.responseText);
			 				 }						    		 
			 			});
					}
		 		}
				
				
				
	</script>
	<script type="text/javascript">
				function tabRiferimenti(divId, codice){
										
			        // create the dialog:
			        var secondDlg = new dijit.Dialog({
			            title: "<fmt:message key="label.normativa" />" ,
			            style: "width: 700px"
			        });
			    	
			    	new Ajax.Request(
							'${pageContext.request.contextPath}/ajax/ajaxDettaglioInvetarioprocLeggi.htm?codice='+ codice,
							{
								method : 'post',
								onSuccess : function(transport) {							
									var response = transport.responseText;							
									// $("dettaglio" + inventario.value).innerHTML = parseScript(response);
									result = parseAjaxResponse(response, true, false);
									secondDlg.attr("content", result);
							        secondDlg.show();
									//$("dettaglio" + inventario.value).appear();							
								},
								onFailure : function(transport) {
									var response = transport.responseText;
									alert(response);
									secondDlg.attr("content", response);
							        secondDlg.show();
								}
							});					    	
				}
				
	</script >

	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>