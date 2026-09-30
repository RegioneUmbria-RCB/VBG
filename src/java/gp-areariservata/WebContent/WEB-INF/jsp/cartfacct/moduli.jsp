<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.FACCTConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand"%>
<%@page	import="it.gruppoinit.pal.gp.core.domain.cart.FileUpdateInfo.FileUpdateOperation"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.ModuloRendering"%>
<%@page	import="it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="-1" />
<link type="text/css" href="${pageContext.request.contextPath}/css/facct.css" rel="stylesheet"></link>
<link type="text/css" href="${pageContext.request.contextPath}/css/schededinamiche.css" rel="stylesheet" ></link>
<link type="text/css" href="${pageContext.request.contextPath}/css/jquery.fileupload-ui.css" rel="stylesheet" ></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/autoNumeric-1.7.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-fp.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-jui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-init.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/init-facct.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/init-schededinamiche.js"></script>
<title></title>
</head>
<%
List<ModuloRendering> moduli =(List<ModuloRendering>) request.getAttribute("modulistica");
List<String> endoAttivi = (List<String>) ((PresentazioneDomandaCartCommand)request.getAttribute("presentazioneDomandaCommand")).getEndoAttivi();
List<String> endoNoCartAttivi = (List<String>) ((PresentazioneDomandaCartCommand)request.getAttribute("presentazioneDomandaCommand")).getEndoNoCartAttivi();

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

	$(document).ready(inizializzaFACCT);
	
	
	</script>


	<div class="dialog" id="dialog" title="" style="display: none;">
		<p id="dialog_content">Messaggio:</p>
	</div>

	<div style="text-align: center; width: 100%; padding-bottom: 10px;">
		<a class="linkEsterno" target="_new"
			href="${_baseUrl}/Contenuti/Step3.aspx?alias=${_idcomunealias}&Software=${_codicesoftware}&Id=${idalberoproc}&SoloPagina=true"
			title="Visualizza la scheda di spiegazione dell'intervento scelto">SCHEDA
			DI SPIEGAZIONE</a>
	</div>
	
	<!-- The template to display files available for upload -->
	<script id="cart-template-upload" type="text/x-jquery-tmpl">
	{{each( i, file ) files}}
    <tr class="template-upload fade">
        <td class="name">
			<span>{{= file.name}}</span>
			<input type="hidden" name="" id="" value="{{= file.codiceoggetto}}"/>
		</td>
		{{if file.error}}
        	<td class="cancel" colspan="2">
			{{if !i}}
            	<button class="btn btn-warning">
                	<i class="icon-ban-circle icon-white"></i>
                	<span>Annulla</span>
            	</button>
        	{{/if}}
			</td>
			<td class="error" colspan="2"><span class="label label-important">Error</span> {{= file.error}}</td>
		{{else $data.files.valid && !i}}
            <td class="start">
			{{if !$data.options.autoUpload}}
                <button class="btn btn-primary">
                    <i class="icon-upload icon-white"></i>
                    <span>Carica</span>
                </button>
            {{/if}}
			</td>
        	<td class="cancel" colspan="2">
            	<button class="btn btn-warning">
                	<i class="icon-ban-circle icon-white"></i>
                	<span>Annulla</span>
            	</button>
			</td>
			<td class="size"><span>{{= $data.formatFileSize(file.size)}}</span></td>
            <td>
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
					<div class="bar" style="width:0%;"></div>
				</div>
            </td>
        {{else}}
            <td colspan="4"></td>
        {{/if}}
    </tr>
	{{/each}}
	</script>
	<!-- The template to display files available for download -->
	<script id="cart-template-download" type="text/x-jquery-tmpl">
	{{each( i, file ) files}}
    <tr class="template-download">
        {{if (file.error)}}
        	<td class="name">
				<span>{{= file.name}}</span>
			</td>
	        <td class="delete">
    	        <button class="btn btn-danger" data-type="{{= file.deleteType}}" data-url="{{= file.deleteUrl}}">
        	        <i class="icon-trash icon-white"></i>
            	    <span>Elimina</span>
     	       </button>
        	</td>
            <td class="size"><span>{{= $data.formatFileSize(file.size)}}</span></td>
            <td class="error" colspan="2"><span class="label label-important">{{= file.error}}</span></td>
        {{else}}
            <td class="name">
                <a href="{{= file.url}}" title="{{= file.name}}" download="{{= file.name}}">{{= file.name}}</a>
				<input type="hidden" name="{{= $data.options.inputName}}" id="{{= $data.options.idSemantico}}" value="{{= file.codiceOggetto}}"/>
				<input type="hidden" name="{{= $data.options.inputName}}_filename" id="{{= $data.options.idSemantico}}_filename" value="{{= file.name}}"/>
            </td>
	        <td class="delete">
    	        <button class="btn btn-danger" data-type="{{= file.deleteType}}" data-url="{{= file.deleteUrl}}">
        	        <i class="icon-trash icon-white"></i>
            	    <span>Elimina</span>
     	       </button>
        	</td>
            <td class="size"><span>{{= $data.formatFileSize(file.size)}}</span></td>
            <td class="warning" colspan="2">
			{{if (file.dserror)}}
				<span class="label label-important ds-warning-{{= $data.options.inputName}}-{{= file.codiceOggetto}}">{{html file.dserror}}</span>
			{{/if}}
			</td>
		{{/if}}
    </tr>
	{{/each}}
	</script>
	

	<%@ include file="../includes/alert.jsp"%>
	<div id="contenutoDaBloccare">
		<%
		if(moduli != null && moduli.size() > 0){
		    ModuloRendering modulo = moduli.get(0);
	    %>

		<form action="<%=request.getContextPath() %>/cart/preparaDomanda.htm" id="mainForm" name="presentazioneDomandaCommand" method="post">

			<input type="hidden" name="codiceAttivitaBdr" value="${presentazioneDomandaCommand.codiceAttivitaBdr }" /> 
			<input type="hidden" name="idAlberoProc" value="${presentazioneDomandaCommand.idAlberoProc }" /> 
			<input type="hidden" name="returnTo" value="${presentazioneDomandaCommand.returnTo }" /> 
			<input type="hidden" name="idDomandaFo" value="${presentazioneDomandaCommand.idDomandaFo }" /> 
			<input type="hidden" name="tipoAzione" value="${presentazioneDomandaCommand.tipoAzione }" /> 
			<input type="hidden" name="token" value="${presentazioneDomandaCommand.token }" /> 
			<input type="hidden" name="codicecomune" value="${presentazioneDomandaCommand.codicecomune }" />
			<input type="hidden" name="lavoriSuFabbricati" value="${presentazioneDomandaCommand.lavoriSuFabbricati }"/>
			<input type="hidden" name="endoCount" value="${presentazioneDomandaCommand.endoCount}"/>
			<%for(int i = 0; i < endoAttivi.size(); i++){ %>
			<input type="hidden" name="endoAttivi" value="<%=endoAttivi.get(i) %>" />
			<% }%>
			<%for(int i = 0; i < endoNoCartAttivi.size(); i++){ %>
			<input type="hidden" name="endoNoCartAttivi" value="<%=endoNoCartAttivi.get(i) %>" />
			<% }%>
			<input type="hidden" id="titolo_modulo_attivo" name="titolo_modulo_attivo" value="<%= modulo.getName() %>" /> 
			<input type="hidden" id="id_modulo_attivo" name="id_modulo_attivo" value="<%= modulo.getId() %>" /> 
			<input type="hidden" id="riferimento_modulo_attivo" name="riferimento_modulo_attivo" value="<%= modulo.getRiferimento() %>" /> 
			<input type="hidden" id="quadro_attivo" name="quadro_attivo" value="" />
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
				<div>
					<input type="button" value="Avanti" name="avanti" class="bottone-cart" />
				</div>
			</div>
			<%
			    }
		    %>
		</div>
		<div>
			<input type="button" value="Presenta Domanda" name="submit" id="presenta_domanda" class="bottone-cart" />
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
	<div id="descrizioneEndo">
</body>
</html>