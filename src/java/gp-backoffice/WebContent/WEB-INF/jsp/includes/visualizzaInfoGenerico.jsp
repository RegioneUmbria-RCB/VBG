<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>

<!-- 
La pagina di inclusione crea una finestra di dialog che permette di
recuperare info del record selezionato attraverso una chiamata ajax. 
Il metodo deve essere creato all'interno dell'ajax controller e la jsp a cui rimanda  si deve trovare 
nella cartella jsp/ajax

Campi obligatori: 

1- ${param.label_dialog}
2- ${param.descrizione}

Campi opzionali

1-${param.indice} (se sono presenti più inclusioni sulla stessa jsp)
2-${param.sizeDialog}
-->


<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<c:set var="size" value="700" />
<c:if test="${not empty param.sizeDialog}">
	<c:set var="size" value="${param.sizeDialog}" />
</c:if>
<c:set var="indice" value="" />
<c:if test="${not empty param.indice}">
	<c:set var="indice" value="${param.indice}" />
</c:if>
<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>

<a class="infoColumn" href="javascript:tabInfo${indice}();"  
	title="<fmt:message key="label.info"></fmt:message>&nbsp;${param.descrizione}&#13<fmt:message key="label.modifica_effettuata" />:&nbsp;${param.descrizioneResponsabile}">
   	<label><fmt:message key="label.info" /></label>
</a>

<script type="text/javascript">
				function tabInfo${indice}(){
			        // create the dialog:
			        var secondDlg = new dijit.Dialog({
			            title: "<fmt:message key="${param.label_dialog}" />" ,
			            style: "width: ${size}px"
			        });
			    	new Ajax.Request(
							'${pageContext.request.contextPath}/ajax/${param.methodAjax}',
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
</script>