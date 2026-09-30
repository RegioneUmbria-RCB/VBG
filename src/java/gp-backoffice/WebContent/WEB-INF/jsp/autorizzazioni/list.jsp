<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="java.net.URLEncoder" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.autorizzazioni.title" />
	</title>
	<script type="text/javascript">
			vbg.ready(() => {
				let buttonMostraInMappa = document.querySelector('.mostra-mappa');
				buttonMostraInMappa.addEventListener('click',async (e) => {
					e.preventDefault();
					window.vbg.mostraModalCaricamento();
					try
					{
						let urlMappa = await recuperaUrlMappa();
						location.replace(urlMappa);
					}
					catch(error) {
						console.log(error);
						alert('Si sono verificati errori durante l\'apertura della mappa: ' + error);
					}
					window.vbg.nascondiModalCaricamento();
				});
			});
			
			async function recuperaUrlMappa(){
							
				const response = await fetch('../istanzestradariocartografico/jsonMostraAutorizzazioniInMappa.htm', {
	        		method: 'POST',
	                headers: {
	                    'Accept': 'application/json',
	                    'Content-Type': 'application/json',
	                }
	        	});
				
				const jsResponse = await response.json();
				
				if(jsResponse.esito.esito == "KO"){
					throw new Error(jsResponse.esito.exceptions.join(' - '));
				}
				
				return jsResponse.url;
			}
		</script>
</head>
<body>	
	<span class="titoloPagina">
		<fmt:message key="label.autorizzazioni.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../autorizzazioni/list" />
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
		<fieldset><legend><fmt:message key="label.filtri_autorizzazioni" /></legend>
			<c:if test="${autorizzazioniFilter.cercaTraSubentrate}">
				<span class="parametri">
	            	<fmt:message key="label.subentrate" /> : 
	            	<label><fmt:message key="label.si" /> </label>
	        	</span>
			</c:if>
			<c:if test="${not empty autorizzazioniFilter.autoriznumero}">
				<span class="parametri">
	            	<fmt:message key="label.numero" /> : 
	            	<label><c:out value="${autorizzazioniFilter.autoriznumero}" /></label>
	        	</span>
        	</c:if>
        	<c:if test="${not empty autorizzazioniFilter.dallaData}">
				<span class="parametri">
	              	<fmt:message key="label.da" /> :
	              	<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${autorizzazioniFilter.dallaData}"/></label> 
	        	</span>
	       	</c:if>
        	<c:if test="${not empty autorizzazioniFilter.allaData}">
				<span class="parametri">
	              	<fmt:message key="label.a" /> :
	              	<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${autorizzazioniFilter.allaData}"/></label> 
	        	</span>
	       	</c:if>
	       	<c:if test="${not empty autorizzazioniFilter.autorizcomune.codicecomune}">
				<span class="parametri">
	              	<fmt:message key="label.comune" /> : 
	              	<label><c:out value="${autorizzazioniFilter.autorizcomune.descrizioneEstesa }" /></label> 
	        	</span>
	       	</c:if>
	       	<c:if test="${not empty autorizzazioniFilter.tipologiaregistro.id.codice}">
				<span class="parametri">
	              	<fmt:message key="label.registro" /> : 
	              	<label><c:out value="${autorizzazioniFilter.tipologiaregistro.trDescrizione}" /></label> 
	        	</span>
	       	</c:if>
	       	<c:if test="${autorizzazioniFilter.anagrafe.id.codice!=null}">
				<span class="parametri">
	              	<fmt:message key="label.concessione_titolare" /> o <fmt:message key="mercatid.label.occupante" />  : 
	              	<label><c:out value="${autorizzazioniFilter.anagrafe.descrizioneRichiedente}" /></label> 
	        	</span>
	       	</c:if>
		</fieldset>
		
	    <fieldset><legend><fmt:message key="label.filtri_istanza" /></legend>
	    	<c:if test="${not empty autorizzazioniFilter.istanzeFilter.comune.codicecomune}">
				<span class="parametri">
	              	<fmt:message key="label.comune" /> : 
	              	<label><c:out value="${autorizzazioniFilter.istanzeFilter.comune.descrizioneEstesa}" /></label> 
	        	</span>
	       	</c:if>
	    	<c:if test="${not empty autorizzazioniFilter.istanzeFilter.numeroistanza}">
				<span class="parametri">
	            	<fmt:message key="label.numero" /> : 
	            	<label><c:out value="${autorizzazioniFilter.istanzeFilter.numeroistanza}" /></label>
	        	</span>
        	</c:if>
        	<c:if test="${not empty autorizzazioniFilter.istanzeFilter.dallaData}">
				<span class="parametri">
	              	<fmt:message key="label.da" /> :
	              	<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${autorizzazioniFilter.istanzeFilter.dallaData}"/></label> 
	        	</span>
	       	</c:if>
        	<c:if test="${not empty autorizzazioniFilter.istanzeFilter.allaData}">
				<span class="parametri">
	              	<fmt:message key="label.a" /> :
	              	<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${autorizzazioniFilter.istanzeFilter.allaData}"/></label> 
	        	</span>
	       	</c:if>
	    	<c:if test="${autorizzazioniFilter.istanzeFilter.richiedente.id.codice!=null}">
				<span class="parametri">
	              	<fmt:message key="label.anagrafe" /> : 
	              	<label><c:out value="${autorizzazioniFilter.istanzeFilter.richiedente.descrizioneRichiedente}" /></label> 
	        	</span>
	       	</c:if>
	       		<c:if test="${autorizzazioniFilter.istanzeFilter.alberoproc.id.codice!=null}">
				<span class="parametri">
	              	<fmt:message key="label.alberoproc" /> : 
	              	<label><c:out value="${autorizzazioniFilter.istanzeFilter.alberoproc.vwAlberoproc.scDescrizione}" /></label> 
	        	</span>
	       	</c:if>
	       	<c:if test="${autorizzazioniFilter.istanzeFilter.procedura.id.codice!=null}">
				<span class="parametri">
	              	<fmt:message key="label.procedura" /> : 
	              	<label><c:out value="${autorizzazioniFilter.istanzeFilter.procedura.procedura}" /></label> 
	        	</span>
	       	</c:if>		
	       	<c:if test="${autorizzazioniFilter.istanzeFilter.istanzestradario.stradario.id.codice!=null}">
				<span class="parametri">
	              	<fmt:message key="label.indirizzo" /> : 
	              	<label><c:out value="${autorizzazioniFilter.istanzeFilter.istanzestradario.stradario.descrizioneCompleta}" /></label> 
	        	</span>
	       	</c:if>	
	       	<c:if test="${not empty  autorizzazioniFilter.istanzeFilter.istanzestradario.cap}">
				<span class="parametri">
	              	<fmt:message key="label.cap" /> : 
	              	<label><c:out value="${autorizzazioniFilter.istanzeFilter.istanzestradario.cap}" /></label> 
	        	</span>
	       	</c:if>	
	       				
	    </fieldset>
	</div>
	<br class="clear"/>
	
	
   <%
		pageContext.setAttribute("SEARCH_AUT_DEFAULT", WebConstants.SEARCH_AUT_DEFAULT);
	%>
	 
	<c:if test="${richiediRicercaSuSubentrate}">
	    <input type="checkbox" id="cerca_tra_subentrate_id"  onchange="searchAutorizzazioniSub()" />
		<fmt:message key="label.ricerca_tra_subentrate" />
		
		<form name="RICERCA_TRA_SUBENTRATE" method="post" action="${pageContext.request.contextPath}/autorizzazioni/list.htm?modalita_ricerca=${SEARCH_AUT_DEFAULT}">					
			<input type="hidden" name="autorizzazioniFilter.cercaTraSubentrate" value="true" id="cercaTraSubentrate_id"/>
		</form>
	</c:if>
	
	<form name="inviodati" action="list.htm">
		${htmltable}
	</form>
	
	<!-- DIV per mostrare la jsp caricata dal metodo  javacsript:visualizzaDettaglioAutorizSpuntista(--) -->
	<div id="spuntistiMercati"></div>	
	
	<%
		String urlStampe = BackofficeNETConstants.getUrlToPopupdecorator(request,BackofficeNETConstants.getURL_STAMPA_PROVVEDIMENTI_AUTORIZZATIVI()+"?SoloDocTipo=1&windowed=S","",(String)session.getAttribute(WebConstants.SOFTWARE));
		pageContext.setAttribute("url_stampe", urlStampe);
	%>	
	<script type="text/javascript">
		var _jmesaUrl='list.htm?';
		var _captionTab='<fmt:message key="label.autorizzazioni" />';
		
		function searchAutorizzazioniSub(){
			document.forms['RICERCA_TRA_SUBENTRATE'].submit();	
		}
		
		function stampa(){
			var urlStampe = '<%=request.getContextPath()%>/autorizzazioni/popupstampa.htm';
			//urlParams = jQuery('form').serialize();
			//urlStampe = urlStampe + escape("&" + urlParams+"&tipoStampa="+tipoStampa);
			//console.info(urlStampe);
			var wii = window.open(urlStampe,66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
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
		
		function openExport(){		
			var url  = URLDecode('${_urlback}');			
			ajaxHistorySet(url);			
			setTimeout("doHref('../autorizzazioni/createExportModalitaPentaho.htm','',document.inviodati)",10);
		}
		
		function ajaxHistorySet(url){
			
			var jhqr = jQuery.ajax({
				  url: '../history/ajaxSet.htm?ReturnTo='+url,
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  success: function(data) { 				   
					} 
				});	
		}
		
		
	</script>
	
	</div>
	<%
		pageContext.setAttribute("SEARCH_AUT_DEFAULT", WebConstants.SEARCH_AUT_DEFAULT);
		pageContext.setAttribute("SEARCH_AUT_PER_GESTIONE_SPUNTA", WebConstants.SEARCH_AUT_PER_GESTIONE_SPUNTA);
	%>
	<div class="form-button">
		<c:if test="${TIPO_SEARCH_AUT eq  SEARCH_AUT_DEFAULT && !autorizzazioniFilter.cercaTraSubentrate}">
			<a class="btn btn-primary" href="javascript:void 0" onclick="stampa();"><fmt:message key="button.stampa" /></a>
			<a class="btn btn-primary" href="javascript:openExport()"><fmt:message key="button.esporta" /></a>
		</c:if>
		<c:if test="${cartograficoAttivo eq true }">
			<a class="btn btn-primary mostra-mappa" href="javascript: void 0;"><fmt:message key="button.mostra_in_mappa" /></a>
		</c:if>
		<a class="btn btn-secondary" href="javascript:historyBack();"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>