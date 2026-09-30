<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_mercati_spuntista.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_mercati_spuntista.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../spuntistimercati/list" />
	</jsp:include>
	<br />
	<div id="subcontent">
	    <c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${istanza.id.codice}</c:param>
		</c:import>
		<%-- 
		<div class="header_dato">
		<span class="header_dato_etichetta"><fmt:message key="label.numero_istanza" />:</span>
		<span class="header_dato_valore">
			${istanza.numeroistanza}
		</span>
		--%>
		<br />
		<jsp:include page="../spuntistimercati/includeListaSpunte.jsp">
	       <jsp:param  name="isCreate" value="false"/>
    	</jsp:include>
	</div>
	<br /><br />
	<fieldset id="table_aut_istanze_id" style="display: none;">
	<legend><b><fmt:message key="label.selezione_aut_istanza" /></b></legend>
	<table border="0" cellpadding="1" cellspacing="1">
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<td width="3%"><fmt:message key="label.seleziona" /></td> 
			<td width="2%">
				<select>
					<c:forEach items="${autorizzazionis}" var="aut">
						<option id="option_aut_id" value="${aut.id.codice}">${aut.autoriznumero}</option>
					</c:forEach>
				</select>
			</td>
			<td>	
				<div id="functions">
				<ul>
					<li><a href="javascript:visualizzaDettaglioAutorizSpuntista(jQuery('#option_aut_id').val(),${istanza.id.codice},)"><fmt:message key="button.seleziona" /></a></li>
				</ul>
				</div>
			</td>
		</tr>
	<!-- DIV per mostrare la jsp caricata dal metodo  javacsript:visualizzaDettaglioAutorizSpuntista(--) -->
	
	<div id="spuntistiMercati"></div>	
	</table>	
	</fieldset>
	</div>
	<%
		//pageContext.setAttribute("SEARCH_AUT_DEFAULT", WebConstants.SEARCH_AUT_DEFAULT);
		pageContext.setAttribute("SEARCH_AUT_PER_GESTIONE_SPUNTA", WebConstants.SEARCH_AUT_PER_GESTIONE_SPUNTA);
		pageContext.setAttribute("SEARCH_CONC_PER_GESTIONE_SPUNTA", WebConstants.SEARCH_CONC_PER_GESTIONE_SPUNTA);
	%>
	<script type="text/javascript">
		function usaAutorizzazioneIstanza()
		{
			jQuery("#table_aut_istanze_id" ).show();
			
		}
		
		// funzione per la funzionalità gestione spuntisti
		//  url: '${pageContext.request.contextPath}/spuntistimercati/ajaxMercatiSpuntisti.htm?idautorizzazione='+codiceAut+'&codiceIstanza='+codiceIstanza+'&codiceAnagrafe='+codiceAnagrafe,
		var visualizzaDettaglioAutorizSpuntista = function(codiceAut,codiceIstanza,codiceAnagrafe){
			disableFunctions();
			var jhqrPr = jQuery.ajax({
				  url: '${pageContext.request.contextPath}/spuntistimercati/ajaxMercatiSpuntisti.htm?idautorizzazione='+codiceAut+'&codiceIstanza='+codiceIstanza,
				  context: document.body,
				  cache: false,					  
				  dataType: "html",
				  success: function(data, textStatus, jqXHR){
					  if(data){
						  jQuery('#spuntistiMercati').html(data);
						  jQuery("#spuntistiMercati").dialog({
	  							 resizable: false,
	  							 modal: true,
	  							 width:'90%',
	  							 title: 'Mercati spuntisti'
	  							}
	  						);
						  //jQuery('#spuntistiMercati').show();
					  }					  
				  }
			});
			enableFunctions();
		}
		
		
	</script>
	<div id="functions">
		<ul>
			<%--  <li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li> --%>
			<c:if test="${empty mercatiInCuiESpuntista}">
				<li><a href="javascript:historySet('${_urlback}','../autorizzazioni/createSearch.htm?codiceIstanza=${istanza.id.codice}&modalita_ricerca=${SEARCH_AUT_PER_GESTIONE_SPUNTA}','')"><fmt:message key="button.cerca_autorizzazione" /></a></li>
				<li><a href="javascript:historySet('${_urlback}','../vwconcessionilista/create.htm?codiceIstanza=${istanza.id.codice}&modalita_ricerca=${SEARCH_CONC_PER_GESTIONE_SPUNTA}','')"><fmt:message key="button.cerca_concessione" /></a></li>  
				<c:if test="${not empty autorizzazionis}">
				<li><a href="javascript:usaAutorizzazioneIstanza();"><fmt:message key="button.usa_autorizzazione_istanza" /></a></li> 
				</c:if>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>