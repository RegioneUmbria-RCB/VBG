<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="batchscadenzario.label.scadenzario.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="batchscadenzario.label.scadenzario.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<div id="subcontent">
<c:if test="${empty msgUtente }">	
		<div class="parametriDiv">
		<fieldset><legend><fmt:message key="label.filtri" /></legend>
			<c:if test="${not empty batchScadenzarioFilter.dallaData}">
				<span class="parametri">
		            <fmt:message key="batchscadenzario.label.dalla_data" /> : 
		            <label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${batchScadenzarioFilter.dallaData}"/></label>
		        </span>
	       	</c:if>
	       	<c:if test="${not empty batchScadenzarioFilter.allaData}">
				<span class="parametri">
		             <fmt:message key="batchscadenzario.label.alla_data" /> : 
		             <label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${batchScadenzarioFilter.allaData}"/></label>
		        </span>
	       	</c:if>
	      	<c:if test="${not empty batchScadenzarioFilter.numeroIstanza}">
				<span class="parametri">
	              <fmt:message key="batchscadenzario.label.numero_istanza" /> : 
	              <label><c:out value="${batchScadenzarioFilter.numeroIstanza}"/></label>
	        	</span>
	      	</c:if>
	      	<c:if test="${not empty batchScadenzarioFilter.responsabile.id.codice}">
				<span class="parametri">
	              <fmt:message key="batchscadenzario.label.operatore_responsabile_istruttore" /> : 
	              <label><c:out value="${batchScadenzarioFilter.responsabile.responsabile}"/></label>
	        	</span>
	      	</c:if>
	      	<c:if test="${not empty batchScadenzarioFilter.intervento.id.codice}">
				<span class="parametri">
	              <fmt:message key="batchscadenzario.label.tipologia_intervento" /> : 
	              <label><c:out value="${batchScadenzarioFilter.intervento.vwAlberoproc.scDescrizione}"/></label>
	        	</span>
	      	</c:if>
	      	<c:if test="${not empty batchScadenzarioFilter.tipoMovimentoFatto.id.tipomovimento}">
				<span class="parametri">
	              <fmt:message key="batchscadenzario.label.movimento_fatto" /> : 
	              <label><c:out value="${batchScadenzarioFilter.tipoMovimentoFatto.movimento}"/></label>
	        	</span>
	      	</c:if>
	      	<c:if test="${not empty batchScadenzarioFilter.tipoMovimentoDaFare.id.tipomovimento}">
				<span class="parametri">
	              <fmt:message key="batchscadenzario.label.tipo_movimento_da_fare" /> : 
	              <label><c:out value="${batchScadenzarioFilter.tipoMovimentoDaFare.movimento}"/></label>
	        	</span>
	      	</c:if>
	      	<c:if test="${batchScadenzarioFilter.scadComportamento==null}">
				<span class="parametri">
		            <fmt:message key="batchscadenzario.label.stato_istanza" /> : 
		            <label>
		            <c:if test="${not empty batchScadenzarioFilter.statiIstanza}">
			        	<c:forEach items="${batchScadenzarioFilter.statiIstanza}" var="stato" varStatus="statoIdx">
			            	<c:out value="${stato.stato}" /><c:if test="${not statoIdx.last}">,&nbsp;</c:if>
			        	</c:forEach>
		       		</c:if>
		       		<c:if test="${empty batchScadenzarioFilter.statiIstanza}">
		       			<fmt:message key="batchscadenzario.label.tutti_gli_stati_istanza" />
		       		</c:if>
		       		</label>
		        </span>
	        </c:if>
	        <c:if test="${batchScadenzarioFilter.scadComportamento != null}">
				<span class="parametri">
		            <fmt:message key="batchscadenzario.label.stato_istanza" /> : 
		            <label>
		            <c:if test="${batchScadenzarioFilter.scadComportamento eq 0}">
						<fmt:message key="label.stato_pratiche_attive"/>
					</c:if>
		            <c:if test="${batchScadenzarioFilter.scadComportamento eq 1}">
						<fmt:message key="label.stato_pratiche_chiuse"/>
					</c:if>					
		       		</label>
		        </span>
	        </c:if>	        
	        <span class="parametri">	        
	        	<fmt:message key="batchscadenzario.label.solo_scadenze_importanti" />:  
	            <label>
	            <%
	            String scadImportantiChecked = "";	
	            if ("1".equals((String) request.getAttribute(WebConstants.CONF_UTENTE_SCADENZARIO_SOLO_SCADENZE_IMPORTANTI))) {
	        		scadImportantiChecked = " checked "; 
				} 
	            %>
	            	<input type="checkbox" id="solo_scadenze_importanti_id" <%= scadImportantiChecked%> onclick="javascript:saveUserPreference('<%=WebConstants.CONF_UTENTE_SCADENZARIO_SOLO_SCADENZE_IMPORTANTI%>',(this.checked==true) ? 1 : 0); document.location.reload();"/>		            	       			
	       		</label>
	       		<init:help idHelp="help_zz" textKey="batchscadenzario.label.solo_scadenze_importanti.help"/>
	        </span>
		</fieldset>
		</div>
		<br class="clear"/>		
</c:if>		
		<c:if test="${not empty msgUtente }">
			<div id="messaggio_utente_readonly" class="error_header" >
	    		${msgUtente}
	    	</div>
		</c:if>		
	    <br class="clear"/>
	    
	    
		<%
	    String uriBack = "../batchscadenzario/list.htm?tab=" + WebConstants.TAB_SCADENZARIO;
	    if(request.getAttribute("listPerOperatore") != null){
			
			uriBack = "/";
	    }
	    pageContext.setAttribute("URL_BACK", uriBack);
	    %>
	    
		<ul class="listaSchede">
			<c:if test="${numeroDocumentiDafirmare > 0}">
				<%if("1".equalsIgnoreCase((String)request.getAttribute(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_DOCUMENTI_DA_FIRMARE))){ %>
					<li><a id="<%=WebConstants.TAB_DOC_DA_FIRMARE %>" class="Scheda" href="javascript:void 0" onclick="viewTab(this)"><fmt:message key="label.documenti_da_firmare" /> &nbsp;(${numeroDocumentiDafirmare})<init:help idHelp="help_DDF" textKey="batchscadenzario.help.date_non_usate_per_filtro"/></a></li>
				<%} %>
			</c:if>
			<%if("1".equalsIgnoreCase((String)request.getAttribute(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_EFFETTUARE))){ %>
			<li>
				<a id="<%=WebConstants.TAB_SCADENZARIO %>" class="Scheda" href="javascript:void 0" onclick="viewTab(this)"><fmt:message key="label.movimenti_da_effettuare" />&nbsp;(${numeroMovimentiDaEffettuare})</a>				
			</li>
			<% }%>
			<%if("1".equalsIgnoreCase((String)request.getAttribute(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_VISIONARE))){ %>
				<li>
					<a id="<%=WebConstants.TAB_MOV_NON_LETTI %>" class="Scheda" href="javascript:void 0" onclick="viewTab(this)"><fmt:message key="label.movimenti_da_visionare" />&nbsp;(${numeroMovimentiDaLeggere})<init:help idHelp="help_MNL" textKey="batchscadenzario.help.date_non_usate_per_filtro"/></a>
				</li>
			<%} %>
			<%if("1".equalsIgnoreCase((String)request.getAttribute(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_NON_NOTIFICATI))){ %>
			<li><a id="<%=WebConstants.TAB_MOV_STC_NON_NOTIFICATI %>" class="Scheda" href="javascript:void 0" onclick="viewTab(this)"><fmt:message key="label.movimenti_non_notificati" />&nbsp;(${numeroMovimentiSTCNonNotificati})<init:help idHelp="help_MNN" textKey="batchscadenzario.help.date_non_usate_per_filtro"/></a>
			</li>
			<%} %>
			<!-- Tab di istanze perveute da stc -->
			<%if("1".equalsIgnoreCase((String)request.getAttribute(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC))){ %>
			<li><a id="<%=WebConstants.TAB_ISTANZE_STC %>" class="Scheda" href="javascript:void 0" onclick="viewTab(this)"><fmt:message key="label.nuove_istanze_stc" />&nbsp;(${numeroIstanzeStc})<init:help idHelp="help_IOL" textKey="batchscadenzario.help.istanze_da_stc"/></a>
			</li>
			<%} %>
			<!-- Tab di istanze stc non importate -->
			<%if("1".equalsIgnoreCase((String)request.getAttribute(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC_NON_IMPORTATE))){ %>
			<li><a id="<%=WebConstants.TAB_ISTANZE_STC_NON_IMPORTATE %>" class="Scheda" href="javascript:void 0" onclick="viewTab(this)"><fmt:message key="label.istanze_stc_non_importate" />&nbsp;(${numeroIstanzeNonImportateStc})<init:help idHelp="help_STC_non_importate" textKey="batchscadenzario.help.istanze_non_importate_da_stc"/></a>
			</li>
			<%} %>
			<!-- Tab richieste perveute da front-end -->
			<%if("1".equalsIgnoreCase((String)request.getAttribute(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_RICHIESTE_FO))){ %>
			<li><a id="<%=WebConstants.TAB_RICHIESTE_FO_NON_NON_LETTE %>" class="Scheda" href="javascript:void 0" onclick="viewTab(this)"><fmt:message key="label.richieste_frontoffice" />&nbsp;(${numeroRichiesteNonLette})<init:help idHelp="help_RFO" textKey="batchscadenzario.help.date_non_usate_per_filtro"/></a></li>
			<%} %>
			<%if("1".equalsIgnoreCase((String)request.getAttribute(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_NON_LETTI))){ %>
			<li><a id="<%=WebConstants.TAB_EVENTI_NON_LETTI %>" class="Scheda" href="javascript:void 0" onclick="viewTab(this)"><fmt:message key="label.eventi_non_letti" /> &nbsp;(${numeroEventiNonLetti})<init:help idHelp="help_IEV" textKey="batchscadenzario.help.date_non_usate_per_filtro"/></a></li>
			<%} %>	
			<%if("1".equalsIgnoreCase((String)request.getAttribute(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_SISTEMA))){ %>
			<li><a id="<%=WebConstants.TAB_EVENTI_SISTEMA %>" class="Scheda" href="javascript:void 0" onclick="viewTab(this)"><fmt:message key="label.eventi_sistema" /> &nbsp;(${numeroEventiSistema})<init:help idHelp="help_IES" textKey="batchscadenzario.help.date_non_usate_per_filtro"/></a></li>
			<%} %>
			
		
			<li>
				<a id="IMPOSTAZIONI_ID" class="SchedaAttiva" href="javascript:historySet('${URL_BACK}', '../batchscadenzario/viewParametriscadenzario.htm', '')"><img src="${pageContext.request.contextPath}/images/impostazioni.png" title="<fmt:message key="label.configura_parametri_scadenzario_avvio" />"/></a>
			</li>
		</ul>
		<c:if test="${numeroDocumentiDafirmare > 0}">
			<div id="divDocumentiDaFirmare" style="display: none;">
			
					<form name="batchDocumentiDaFirmare" action="${formAction}">					
						${documentiDaFirmareHtmlTable}					
						<input type="hidden" name="tab" value="tabDocumentiDaFirmare"/>
					</form>
					<c:if test="${ numeroDocumentiDafirmare  > 0}">
						<div id="metti_alla_firma_fun" style="display: none;">
							<div id="functions">
								<ul>
									<li><a href="javascript:firma();"><fmt:message key="label.firma_i_documenti" /></a></li>
								</ul>
							</div>
						</div>
					</c:if>
					
					<form name="METTI_ALLA_FIRMA_FRM" method="post" action="${pageContext.request.contextPath}/documentidafirmare/mettiallafirmamultipla.htm">					
							<input type="hidden" name="lista_doc_da_firmare" id="lista_doc_da_firmare_id"/>
					</form>
					
			</div>
		</c:if>
	    <div id="divScadenzario" style="display: none;">
			<form name="batchScadenzarioFilter" action="${formAction}">
				${batchScadenzarioHtmlTable }
				<input type="hidden" name="tab" value="tabScadenzario"/>
			</form>		
			<script type="text/javascript">
				var _jmesaUrl='${formAction}?tab=tabScadenzario&';
				var _captionTab='<fmt:message key="batchscadenzario.label.scadenzario.title" />';
			</script>
		</div>
		<div id="divMovNonLetti" style="display: none;">
				<form name="batchdaLeggere" action="${formAction}">
					${movimentidaVisionareHtmlTable}					
					<input type="hidden" name="tab" value="tabMovNonLetti"/>
				</form>				
		</div>
		<div id="divMovSTCNonNotificati" style="display: none;">
			<c:if test="${isVertSTCAttiva eq true}">
				<br class="clear"/>
				<label class="error">*<fmt:message key="batch_scadenzario.help.movimenti_non_notificati_flag_letto" /></label>
				<form name="batchStc" action="${formAction}">
					${movimentiSTCnonNotificatiHtmlTable}					
					<input type="hidden" name="tab" value="tabMovSTCNonNotificati"/>
				</form>				
			</c:if>
		</div>
		<div id="divIstanzeStc" style="display: none;">
				<br class="clear"/>
				<form name="batchStc" action="${formAction}">
					${istanzeStcHtmlTable}
					<input type="hidden" name="tab" value="tabIstanzeStc"/>
				</form>
				
		</div>
		<div id="divIstanzeStcNonImportate" style="display: none;">
				<br class="clear"/>
				<form name="batchStc" action="${formAction}">
					${istanzeNonImportateStcHtmlTable}
					<input type="hidden" name="tab" value="tabIstanzeStcNonImportate"/>
				</form>
				
		</div>
		
		<div id="divRichiesteFoNonLette" style="display: none;">
				<form name="foRichiesta" action="${formAction}">					
					${foRichiesteHtmlTable}					
					<input type="hidden" name="tab" value="tabRichiesteFoNonLette"/>
				</form>
		</div>
		<div id="divEventiNonLetti" style="display: none;">
		
				<form name="batchEventi" action="${formAction}">					
					${eventiNonLettiTableHtmlTable}					
					<input type="hidden" name="tab" value="tabEventiNonLetti"/>
				</form>
	   
		</div>
		<div id="divEventiSistema" style="display: none;">
		
				<form name="batchEventiSistema" action="${formAction}">					
					${eventiSistemaHtmlTable}					
					<input type="hidden" name="tab" value="tabEventiSistema"/>
				</form>
		</div>
		
	</div>
	
	
	<input type="hidden" id="hiddenTabjJs" value="${requestScope.tab}" />
	<script type="text/javascript">
		function selezionaTuttiDDF(){
			var checked = jQuery('#ddf_seleziona_tutti').is(':checked');
			jQuery(".documenti_da_firmare_cls").each(function() {
			    this.checked=checked;			    
			});
			addToDocDaFirmare();
		}
	
		function firma(){
			document.forms['METTI_ALLA_FIRMA_FRM'].submit();		
		}
		function addToDocDaFirmare(){
			var docDaFirmare = "";
			var almenoUno = false;
			jQuery(".documenti_da_firmare_cls").each(function() {
			    if(this.checked){
			    	docDaFirmare += this.value+",";
			    	almenoUno=true;
			    }
			});
			docDaFirmare = docDaFirmare.replace(/,$/,"");
			if(almenoUno){
				jQuery('#metti_alla_firma_fun').show();
			}else{
				jQuery('#metti_alla_firma_fun').hide();
			}
			jQuery('#lista_doc_da_firmare_id').val(docDaFirmare);
		}
	
		var schedeSuffix = new Array("DocumentiDaFirmare","Scadenzario","MovNonLetti","MovSTCNonNotificati","IstanzeStc","IstanzeStcNonImportate","RichiesteFoNonLette","EventiNonLetti","EventiSistema");		
		function viewTab(obj){
			for (i=0;i<schedeSuffix.length;i++){
				if($('tab'+schedeSuffix[i])){
					$('tab'+schedeSuffix[i]).className='Scheda';
				}
				if($('div'+schedeSuffix[i])){
					$('div'+schedeSuffix[i]).style.display='none';
				}
			}
			obj.className='SchedaAttiva';
			
			var sn = obj.id.substring(3);			
			$('hiddenTabjJs').value=obj.id;
			$('div'+sn).style.display='block';
		}
		var tab = '${param.tab}';
		if(tab == '')tab = '${requestScope.tab}';
		if(tab != ''){
			viewTab($(tab));
		}		
		
		function ricaricaPagina(){
			var docref = document.location;
			if (String(docref).endsWith("list.htm")){				
				if(docref.search.indexOf("tab=")>0){
					docref = docref.search.replace(/\?tab=tab[\w]*/g,'');
				}
				document.location.href = docref + "?tab=" + $('hiddenTabjJs').value;
			}else{
				if(docref.search.indexOf("&tab=")>0){
					docref = docref.search.replace(/&tab=tab[\w]*/g,'');
					document.location.href = docref + "&tab=" + $('hiddenTabjJs').value;
				}else{
					if(docref.search.indexOf("tab=")>0){
						docref = docref.search.replace(/tab=tab[\w]*/g,'');
						document.location.href = docref + "tab=" + $('hiddenTabjJs').value;
					}else{
						document.location.href = docref + "&tab=" + $('hiddenTabjJs').value;
					}					
				}					
			}			
		}		
	</script>
	<br class="clear" />
	<div id="functions">
		<ul>
			<li>
				<c:choose>
				<c:when test="${empty requestScope.listPerOperatore}">
					<a href="javascript:doHref('createSearch.htm','')"><fmt:message key="button.back" /></a>
				</c:when>
				<c:otherwise>
					<a href="javascript:historyBack()"><fmt:message key="button.back" /></a>
				</c:otherwise>
				</c:choose>
			</li>
			<li>
				<a href="javascript:ricaricaPagina();"><fmt:message key="button.aggiorna" /></a>
			</li>		
		</ul>
	</div>
</body>
</html>