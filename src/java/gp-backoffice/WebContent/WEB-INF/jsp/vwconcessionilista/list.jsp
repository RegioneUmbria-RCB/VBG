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
		<title><fmt:message key="form.vwconcessionilista.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.vwconcessionilista.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
		    <jsp:param name="path" value="../vwconcessionilista/search" />
		</jsp:include>        
		<div id="subcontent">
			<div class="parametriDiv">
		        <fieldset><legend><fmt:message key="label.filtri_concessione"></fmt:message></legend>
		        <c:if test="${vwConcessionilista.concNumero != null}">
		       	<span class="parametri">
		       		<fmt:message key="label.concessione_numero_concessione" />: <label><c:out value="${vwConcessionilista.concNumero}"/></label>
		       	</span>
		        </c:if>
		        <c:if  test="${vwConcessionilista.titolareConcessione.id.codice != null}" >
		       	<span class="parametri">
		       		<fmt:message key="label.concessione_titolare" />: <label><c:out value="${vwConcessionilista.titolareConcessione.descrizioneRichiedente}"/></label>
		       	</span>
		        </c:if>
		        <c:if  test="${vwConcessionilista.dataInizioRilascio != null || vwConcessionilista.dataFineRilascio != null}" >
		       	<span class="parametri">
		       		<fmt:message key="form.vwConcessionilista.dataRilascio" />:
					<c:if  test="${vwConcessionilista.dataInizioRilascio != null }" >
						<fmt:message key="form.vwConcessionilista.data.inizio" /><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" value="${vwConcessionilista.dataInizioRilascio}"/></label>
					</c:if>
					<c:if  test="${vwConcessionilista.dataFineRilascio != null }" >
						<fmt:message key="form.vwConcessionilista.data.fine"/><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${vwConcessionilista.dataFineRilascio}"/></label>
					</c:if>
		       	</span>
		        </c:if>
		        <c:if  test="${vwConcessionilista.dataInizioScadenze != null || vwConcessionilista.dataFineScadenze!= null}" >
		       	<span class="parametri">
		       		<fmt:message key="form.vwConcessionilista.dataScadenza" />:
					<c:if  test="${vwConcessionilista.dataInizioScadenze != null }" >
						<fmt:message key="form.vwConcessionilista.data.inizio" /><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${vwConcessionilista.dataInizioScadenze}"/></label>
					</c:if>
					<c:if  test="${vwConcessionilista.dataFineScadenze != null }" >
						<fmt:message key="form.vwConcessionilista.data.fine"/><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${vwConcessionilista.dataFineScadenze}"/></label>
					</c:if>
		       	</span>
		        </c:if>
				<c:if test="${vwConcessionilista.concIdmercato!=0}">
		     		 <span class="parametri">
		             <fmt:message key="form.vwConcessionilista.mercati" />: <label><c:out value="${vwConcessionilista.concMercato}"/></label>
		             </span>
		        </c:if>
		        <c:if test="${vwConcessionilista.concIdmercatiuso!=0}">
		     		 <span class="parametri">
		             <fmt:message key="form.vwConcessionilista.mercatiUso" />: <label><c:out value="${vwConcessionilista.concDescrizioneuso}"/></label>
		             </span>
		        </c:if>
		        <c:if test="${not empty vwConcessionilista.concPosteggio}">
		     		 <span class="parametri">
		             <fmt:message key="form.vwconcessionilista.posteggio" />: <label><c:out value="${vwConcessionilista.concPosteggio}"/></label>
		             </span>
		        </c:if>
		        <c:if test="${vwConcessionilista.iconcCodicecausale!=0}">
		       		 <span class="parametri">
		             <fmt:message key="form.vwConcessionilista.concessionicausali" />:<label> <c:out value="${vwConcessionilista.iconcCausale}"/></label>
		        	</span>
		        </c:if>
		       	<c:if test="${vwConcessionilista.concAttiva eq true}">
		       	<span class="parametri">
		             <fmt:message key="form.vwConcessionilista.soloConcAttive" />:<label> <fmt:message key="label.si" /></label>
		        </span>
		        <c:if test="${vwConcessionilista.attiveAllaDataTransient !=null}">
		        <span class="parametri">
		             <fmt:message key="form.vwConcessionilista.data.fine" />:<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${vwConcessionilista.attiveAllaDataTransient}"/> </label>
		        </span>
		        </c:if>
		        
		        </c:if>
		        </fieldset>
		        
		    <fieldset><legend><fmt:message key="label.filtri_istanza" /></legend>
		    	<c:if test="${not empty vwConcessionilista.istanza.comune.codicecomune}">
					<span class="parametri">
		              	<fmt:message key="label.comune" /> : 
		              	<label><c:out value="${vwConcessionilista.istanza.comune.descrizioneEstesa}" /></label> 
		        	</span>
		       	</c:if>
		    	<c:if test="${not empty  vwConcessionilista.istanza.numeroistanza}">
					<span class="parametri">
		            	<fmt:message key="label.numero" /> : 
		            	<label><c:out value="${vwConcessionilista.istanza.numeroistanza}" /></label>
		        	</span>
	        	</c:if>
	        	<c:if test="${not empty  vwConcessionilista.istanzadataDa}">
					<span class="parametri">
		              	<fmt:message key="label.da" /> :
		              	<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${ vwConcessionilista.istanzadataDa}"/></label> 
		        	</span>
		       	</c:if>
	        	<c:if test="${not empty  vwConcessionilista.istanzadataA}">
					<span class="parametri">
		              	<fmt:message key="label.a" /> :
		              	<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${ vwConcessionilista.istanzadataA}"/></label> 
		        	</span>
		       	</c:if>
		    	<c:if test="${vwConcessionilista.istanza.richiedente.id.codice!=null}">
					<span class="parametri">
		              	<fmt:message key="label.anagrafe" /> : 
		              	<label><c:out value="${vwConcessionilista.istanza.richiedente.descrizioneRichiedente}" /></label> 
		        	</span>
		       	</c:if>
		       		<c:if test="${vwConcessionilista.istanza.alberoproc.id.codice!=null}">
					<span class="parametri">
		              	<fmt:message key="label.alberoproc" /> : 
		              	<label><c:out value="${vwConcessionilista.istanza.alberoproc.vwAlberoproc.scDescrizione}" /></label> 
		        	</span>
		       	</c:if>
		       	<c:if test="${vwConcessionilista.istanza.procedura.id.codice!=null}">
					<span class="parametri">
		              	<fmt:message key="label.procedura" /> : 
		              	<label><c:out value="${vwConcessionilista.istanza.procedura.procedura}" /></label> 
		        	</span>
		       	</c:if>		
		       	<c:if test="${vwConcessionilista.istanzestradario.stradario.id.codice!=null}">
					<span class="parametri">
		              	<fmt:message key="label.indirizzo" /> : 
		              	<label><c:out value="${vwConcessionilista.istanzestradario.stradario.descrizioneCompleta}" /></label> 
		        	</span>
		       	</c:if>	
		       	<c:if test="${not empty  vwConcessionilista.istanzestradario.cap}">
					<span class="parametri">
		              	<fmt:message key="label.cap" /> : 
		              	<label><c:out value="${vwConcessionilista.istanzestradario.cap}" /></label> 
		        	</span>
		       	</c:if>	
		       				
	    </fieldset>
		 <!-- DIV per mostrare la jsp caricata dal metodo  javacsript:visualizzaDettaglioAutorizSpuntista(--) -->
		<div id="spuntistiMercati"></div>	       
		
		    </div>
		    <br class="clear"/>			
			<form name="inviodati" action="search.htm">
				${htmltable}
			</form>
			<script type="text/javascript">
				var _jmesaUrl='search.htm?';
				var _captionTab='<fmt:message key="form.vwconcessionilista.title.list" />';
			</script>
		</div>
		<div class="form-button">
			<a class="btn btn-primary" href="javascript:void 0" onclick="stampa();"><fmt:message key="button.stampa" /></a>
			<a class="btn btn-primary" href="javascript:exportConcessioni(${numConcessioni});"><fmt:message key="button.esporta" /></a>
			<a class="btn btn-secondary" href="javascript:historyBack();"><fmt:message key="button.back" /></a>
		</div>
		
		 <%
			String urlStampe = BackofficeNETConstants.getUrlToPopupdecorator(request,BackofficeNETConstants.getURL_STAMPA_PROVVEDIMENTI_AUTORIZZATIVI()+"?SoloDocTipo=1&windowed=S","",(String)session.getAttribute(WebConstants.SOFTWARE));
			pageContext.setAttribute("url_stampe", urlStampe);
		%>	
		<script type="text/javascript">
		
		function exportConcessioni(numConcessioni){		
			if(numConcessioni>0)
			{
				if (${OBS_EXPORT})
				{
				// Vecchia chiamata export	
				// Imposto il metodo da ajax da chiamare
				
					var secondDlg = new dijit.Dialog({
				            title: "<fmt:message key="label.export_dati" />" ,
				            style: "overflow:auto; width: 650px;height: 240px;"
				    });
					disableFunctions(); 
					new Ajax.Request('<%=request.getContextPath()%>/ajax/exportConcessioni.htm?contestoExport=CON', {
						  method: 'post',
						  parameters: {},
						  onSuccess: function(transport){
							  enableFunctions();
							  var response = transport.responseText;		
							  result = parseAjaxResponse(response, true, false);
							  secondDlg.attr("content", result);
						      secondDlg.show();							  
						  },
						  onFailure: function(transport){ 
							  enableFunctions();
						  	  var response = transport.responseText;
							  secondDlg.attr("content", response);
							  secondDlg.show();	
						  }
					});	
				 } 
				 else{
					goToExportPentahoPanel('CON');
				}
	
			}else
			{
				alert('Attenzione, la ricerca non ha prodotto una lista di attività per effettuare l\'esportazione');	
			}
		}
		
		function goToExportPentahoPanel(contesto){
			
			var url  = URLDecode('${_urlback}');			
			ajaxHistorySet(url);

			setTimeout("doSubmit('../vwconcessionilista/createExportModalitaPentaho.htm?1=1','',document.inviodati_second)",10);
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
		
		function esporta(){
			var codice = getSelectLabelAndValue(document.getElementById("tipoEsportazione_id"));
			var email = document.getElementById("responsabile_email_id").value;
			var checkInvioMail = false;
			if(document.getElementById("invio_email_id").checked)
			{
				if(email == '' || email == null )
				{
					alert('Attenzione, si è deciso di inviare l\'esportazione per e-mail, ma non ne è stata configurata una');
					checkInvioMail=false;
				}else
				{
				checkInvioMail=true;
				}
			}
			if(codice){
				if(codice[0]!=''){
					document.location.href='ajaxExport.htm?codice='+ codice[0] + '&descrizione=' + escape(codice[1])+'&email='+email+'&isInviaMail='+checkInvioMail;
				}
			}
		}
		
		function getSelectLabelAndValue(selectObj){
			
			var pos=selectObj.selectedIndex;
			var valore='';
			var testo='';
			if (pos>-1) {
				valore=selectObj.options[pos].value;
				testo=selectObj.options[pos].label;
			} 
			var valori =	new Array(valore, testo);
			return valori;
		}
		

		function stampa(){
			var urlStampe = '<%=request.getContextPath()%>/vwconcessionilista/popupstampa.htm?';
			urlParams = jQuery('form').serialize();
			urlStampe = urlStampe + "&" + urlParams;
			console.info(urlStampe);
			var wii = window.open(urlStampe,66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');
		}	
		
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
					  }					  
				  }
			});
			enableFunctions();
		}
		
		</script>
		
		
	</body>
</html>