<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@page import="java.util.Set"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.FACCTConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.ModuloRendering"%>
<%@page	import="it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
String  vJS = "3.11_2017-10-24_17.48";%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="-1" />
<link type="text/css" href="${pageContext.request.contextPath}/css/facct.css" rel="stylesheet"></link>
<link type="text/css" href="${pageContext.request.contextPath}/css/schededinamiche.css" rel="stylesheet" ></link>
<link type="text/css" href="${pageContext.request.contextPath}/css/jquery.fileupload-ui.css" rel="stylesheet" ></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/autoNumeric-1.7.5.js?<%=vJS %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js?<%=vJS %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload.js?<%=vJS %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-fp.js?<%=vJS %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-ui.js?<%=vJS %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-jui.js?<%=vJS %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-init.js?<%=vJS %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/init-facct.js?<%=vJS %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/init-schededinamiche.js?<%=vJS %>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.uploadDatiDinamici.js?<%=vJS %>"></script>
<title></title>
</head>
<%
List<ModuloRendering> moduli =(List<ModuloRendering>) request.getAttribute("modulistica");

PresentazioneDomandaCartCommand cmd = ((PresentazioneDomandaCartCommand)request.getAttribute("presentazioneDomandaCommand"));

Set<String> endoAttivi = (Set<String>) ((PresentazioneDomandaCartCommand)request.getAttribute("presentazioneDomandaCommand")).getEndoAttivi();
Set<String> endoNoCartAttivi = (Set<String>) ((PresentazioneDomandaCartCommand)request.getAttribute("presentazioneDomandaCommand")).getEndoNoCartAttivi();

String baseUrl=(String)request.getSession().getAttribute("baseAreaRiservataMsUrl");
String idcomunealias=(String)ORMHelper.getIdcomuneAlias();
String codicesoftware=(String)ORMHelper.getSoftware();
String idalberoproc=(String)request.getSession().getAttribute("idAlberoProc");
pageContext.setAttribute("_baseUrl", baseUrl);
pageContext.setAttribute("_codicesoftware", codicesoftware);
pageContext.setAttribute("_idcomunealias", idcomunealias);
pageContext.setAttribute("_idalberoproc", idalberoproc);
%>
<body>
	<script type="text/javascript">
	
	var AR_MS_URL = "<%= baseUrl%>";
	var contextPath = "<%=request.getContextPath()%>";
	var TABS_QUADRI_MODULO_PREFIX = "<%=FACCTConstants.JS_VAR_TABS_QUADRI_MODULO_PREFIX%>";
	var INFO_QUADRI_MODULO_PREFIX = "<%=FACCTConstants.JS_VAR_INFO_QUADRI_MODULO_PREFIX%>";
	var INDEXING_SUFFIX = "<%= FACCTConstants.PRESENTAZIONE_DOMANDA_INDEXED_INPUT_NAME_SEPARATOR %>";

	//oggetto jquery che viene popolato dagli script dinamici con gli oggetti su cui deve essere scatenato l'evento change al caricamneto della pagina
	var changeAfterLoad = jQuery();
	var callAfterLoad = [];
	var waitingDocumentReady = true;
	var intervalId;
	$(document).ready(function(){
		inizializzaFACCT();
		intervalId = setInterval(executeLazy, 400);
	});

	function executeLazy(){
		if(waitingDocumentReady){
			return;
		}
		else{
			runScriptsAfterLoad();
			clearInterval(intervalId);
		}
	};

	function runScriptsAfterLoad(){
		changeAfterLoad.change();
		for(var i = 0; i < callAfterLoad.length; i++){
			callAfterLoad[i]();
		}
		changeAfterLoad = jQuery();
		waitingDocumentReady = [];
	};
	
	</script>


	<div class="dialog" id="dialog" title="" style="display: none;">
		<p id="dialog_content">Messaggio:</p>
	</div>
	<jsp:include page="../includes/messaggio_aggiornamento.jsp" >
			<jsp:param name="settimeout" value="true"></jsp:param>
	</jsp:include>
	<jsp:include page="../includes/fileupload_templates.jsp" />
	<div style="text-align: center; width: 100%; padding-bottom: 10px;">
		<a class="linkEsterno" target="_new" href="../dizionario/popupSchedaSpiegazioneEndo2.htm?id=${idalberoproc}"
			title="Visualizza la scheda di spiegazione dell'intervento scelto">SCHEDA
			DI SPIEGAZIONE</a>
	</div>
	
	<%@ include file="../includes/alert.jsp"%>
	<div id="contenutoDaBloccare">
		<%
		if(moduli != null && moduli.size() > 0){
		    ModuloRendering modulo = moduli.get(0);
	    %>

		<form action="<%=request.getContextPath() %>/cart/stepOneri.htm" id="mainForm" name="presentazioneDomandaCommand" method="post">

			<input type="hidden" name="codiceAttivitaBdr" value="${presentazioneDomandaCommand.codiceAttivitaBdr }" /> 
			<input type="hidden" name="idAlberoProc" value="${presentazioneDomandaCommand.idAlberoProc }" /> 
			<input type="hidden" name="returnTo" value="${presentazioneDomandaCommand.returnTo }" /> 
			<input type="hidden" name="idDomandaFo" value="${presentazioneDomandaCommand.idDomandaFo }" /> 
			<input type="hidden" name="tipoAzione" value="${presentazioneDomandaCommand.tipoAzione }" /> 
			<input type="hidden" name="token" value="${presentazioneDomandaCommand.token }" /> 
			<input type="hidden" name="codicecomune" value="${presentazioneDomandaCommand.codicecomune }" />
			<input type="hidden" name="lavoriSuFabbricati" value="${presentazioneDomandaCommand.lavoriSuFabbricati }"/>
			<input type="hidden" name="endoCount" value="${presentazioneDomandaCommand.endoCount}"/>
			<%for(String cod : endoAttivi){ %>
			<input type="hidden" name="endoAttivi" value="<%=cod %>" />
			<% }%>
			<%for(String cod : endoNoCartAttivi ){ %>
			<input type="hidden" name="endoNoCartAttivi" value="<%=cod %>" />
			<% }%>
			<input type="hidden" id="titolo_modulo_attivo" name="titolo_modulo_attivo" value="<%= modulo.getName() %>" /> 
			<input type="hidden" id="id_modulo_attivo" name="id_modulo_attivo" value="<%= modulo.getId() %>" /> 
			<input type="hidden" id="riferimento_modulo_attivo" name="riferimento_modulo_attivo" value="<%= modulo.getRiferimento() %>" /> 
			<input type="hidden" id="quadro_attivo" name="quadro_attivo" value="" />
			
			<c:choose>
		    	<c:when test="${not empty presentazioneDomandaCommand.interventiLocali}">		    		
		    		<c:forEach items="${presentazioneDomandaCommand.interventiLocali}" var="intLoc">
		    			<input type="hidden" name="interventiLocali" value="${intLoc}" />
		    		</c:forEach>		    		
		    	</c:when>
		    </c:choose>
			
			
		</form>
		<div id="moduli_tabs" class="tabs-moduli-container">
			<ul>
				<%
			    for(int i = 0; i < moduli.size(); i++){
					modulo = moduli.get(i);
			%>
				<li  id="tab_<%=modulo.getId()%>">
					<a class="modulo-cart-title help"
						href="#tab_modulo_<%=modulo.getId()%>"
						title="<%=CartModuloHelper.escapeValueForHtmlAttribute(modulo.getHelp())%>">
							<%= modulo.getName() %> <%--span per contenere eventuali icone che indicano la presenza di errori di validazione dell'input di questo tab --%>
						<span class="tab_error_image"
							id="modulo-<%=modulo.getId()%>-errors" style="display: none;"
							title=""> <label>(0)</label>
						</span> <%--span per contenere eventuali icone che indicano la presenza di warning di validazione dell'input di questo tab --%>
						<span class="tab_warning_image"
							id="modulo-<%=modulo.getId()%>-warnings" style="display: none;"
							title=""> <label>(0)</label>
						</span>
					</a>
				</li>
				<%
			    }
		    %>
			</ul>
			<%
			    for(int i = 0; i < moduli.size(); i++){
					modulo = moduli.get(i);
			%>
			<div id="tab_modulo_<%=modulo.getId()%>" class="tab-cart">
				<%= modulo.getHtml() %>
				<div style="padding: 6px;">
					<input type="button" value="Avanti" name="avanti" class="bottone-cart" />
				</div>
			</div>
			<%
			    }
		    %>
		</div>
		<br />
		
		<div style="padding: 6px;">
			<input type="button" value="<spring:message code="cartfacct.button.presentadomanda"></spring:message>" name="submit" id="presenta_domanda" class="bottone-cart" />
			<c:choose>
				<c:when
					test="${valoreAzioneAvvio eq presentazioneDomandaCommand.tipoAzione or presentazioneDomandaCommand.endoCount gt 0}">
					<%
	    			/*
	    			il bottone 'torna a selezione endo' è visualizzato se tipoAzione = 'Avvio' o se domanda tipo STD_0 per cui sono attivabili endo non CART.
	    			ATTENZIONE!!! la logica potrebbe cambiare quando saranno implementati 
	    			anche altri tipi di azioni oltre a 'Avvio' e 'AvvioST0'
	    			*/
	    		%>
					<input type="button" value="Torna a selezione endoprocedimenti" name="indietro" id="indietro" class="bottone-cart" />
					<script>
	    				$(document).ready(function(){	
			    			$("#indietro").click(returnToEndo);
	    				});
	    			</script>
				</c:when>
			</c:choose>
			<input type="button" class="bottone-cart" onclick="anteprimadomanda()" value="Anteprima" />
			<input type="button" class="bottone-cart" onclick="document.location.href='<%= request.getSession().getAttribute(WebConstants.RETURNTO)%>'" value="Chiudi" />
		</div>

		<%-- 
	    </form>
	    --%>
		<%
		} 
		else{
		%>
		<span>Nessuna modulistica disponibile per
			l&apos;attivit&agrave; selezionata</span>
		<%
		}
		%>

	</div>
	<div id="descrizioneEndo" />	
	<script type="text/javascript">
	function anteprimadomanda(){
		var url = '<%=request.getContextPath() %>/cart/anteprimaDomanda.htm?<%= Utilities.getLinkForFile(("codice="+cmd.getIdDomandaFo()+"&ts_="+System.currentTimeMillis()))%>';
		var w = window.open(url);
	
	}
	$(document).ready(function(){
		<c:if test="${ isDomandaComunica eq true }">
			$('.init-autocompiler-button-user').closest('a').hide();
		</c:if>
		
		//l'ultimo handler dell'evento ready imposta a false la variabile waitingDocumentReady facendo partire il secondo blocco di script
		waitingDocumentReady = false;
	});
	</script>
</body>
</html>