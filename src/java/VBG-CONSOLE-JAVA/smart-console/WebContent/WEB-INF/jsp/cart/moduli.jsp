<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.FileInfo"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.FACCTConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand"%>
<%@page
	import="it.gruppoinit.pal.gp.core.domain.cart.FileUpdateInfo.FileUpdateOperation"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<%@page import="it.gruppoinit.pal.gp.core.domain.cart.ModuloRendering"%>
<%@page
	import="it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Expires" content="-1" />

<title></title>
</head>
<%
List<ModuloRendering> moduli =(List<ModuloRendering>) request.getAttribute("modulistica");
List<String> endoAttivi = (List<String>) ((PresentazioneDomandaCartCommand)request.getAttribute("presentazioneDomandaCommand")).getEndoAttivi();
List<String> endoNoCartAttivi = (List<String>) ((PresentazioneDomandaCartCommand)request.getAttribute("presentazioneDomandaCommand")).getEndoNoCartAttivi();
List<FileInfo> elencoAllegati = (List<FileInfo>)request.getAttribute("allegatiIstanza");

String baseUrl=(String)request.getSession().getAttribute("baseAreaRiservataMsUrl");
%>
<body>
	<script type="text/javascript">
	
	var AR_MS_URL = "<%= baseUrl%>";
	var contextPath = "<%=request.getContextPath()%>";
	var TABS_QUADRI_MODULO_PREFIX = "<%=FACCTConstants.JS_VAR_TABS_QUADRI_MODULO_PREFIX%>";
	var INFO_QUADRI_MODULO_PREFIX = "<%=FACCTConstants.JS_VAR_INFO_QUADRI_MODULO_PREFIX%>";
	var INDEXING_SUFFIX = "<%= FACCTConstants.PRESENTAZIONE_DOMANDA_INDEXED_INPUT_NAME_SEPARATOR %>";

	$(document).ready(inizializzaFACCT);
	
		
	synchronizeRowDeletions = function(idQuadro, fileOperations){
		/*
		* per tutte le righe di tabella che sono state cancellate dall'utente imposto a -1 il valore del campo hidden 
		* che associa al progressivo della riga l'indice id semantico
		*/
		for(var i = 0; i < hiddenFieldsForDeletedRows.length; i++){
			hiddenFieldsForDeletedRows[i].val("-1");
		}
		hiddenFieldsForDeletedRows = [];
	};
		
	/*
	array che contiene l'elenco degli allegati dell'istanza, utilizzato per caricare 
	il contenuto di tutti i campi per la selezione dei files allegati.
	Ciascun campo visualizzerà l'elenco degli allegati istanza 
	esclusi quelli già selezionati in altri campi della modulistica
	*/
	var <%= FACCTConstants.JS_VAR_ALLEGATI_ISTANZA%> = [];
	<%for(FileInfo allegato : elencoAllegati){ %>
	<%= FACCTConstants.JS_VAR_ALLEGATI_ISTANZA%>[<%= FACCTConstants.JS_VAR_ALLEGATI_ISTANZA%>.length] = {codiceOggetto: <%= allegato.getIdOggetto()%>, nomeFile: "<%= allegato.getNomeFile()%>"};
	<%}%>
	
	/*
	* inizializzazione di un controllo che consente di selezionare uno degli allegati dell'istanza o dei suoi endoprocedimenti
	* 
	*/
	initializeSelectFile = function(inputElementId, maxUploadNum){
		//TODO se maxUploadNum > 0 deve essere possibile selezionare fino ad un massimo di maxUploadNum allegati
		$("#" + inputElementId).selectsharedmenu({sharedItems: <%= FACCTConstants.JS_VAR_ALLEGATI_ISTANZA%>}).selectsharedmenu("widget").addClass('campo-file-cart');
	};
	
	
	
	</script>


	<div class="dialog" id="dialog" title="" style="display: none;">
		<p id="dialog_content">Messaggio:</p>
	</div>

	<div id="contenutoDaBloccare">
		<%
		if(moduli != null && moduli.size() > 0){
		    ModuloRendering modulo = moduli.get(0);
	    %>

		<form action="<%=request.getContextPath() %>/cart/generaAllegatiNotifica.htm" id="mainForm" name="presentazioneDomandaCommand" method="post">

			<input type="hidden" name="codiceAttivitaBdr" value="${presentazioneDomandaCommand.codiceAttivitaBdr }" /> 
			<input type="hidden" name="idAlberoProc" value="${presentazioneDomandaCommand.idAlberoProc }" /> 
			<input type="hidden" name="returnTo" value="${presentazioneDomandaCommand.returnTo }" /> 
			<input type="hidden" name="tipoAzione" value="${presentazioneDomandaCommand.tipoAzione }" /> 
			<input type="hidden" name="token" value="${presentazioneDomandaCommand.token }" /> 
			<input type="hidden" name="codicecomune" value="${presentazioneDomandaCommand.codicecomune }" />

			<input type="hidden" id="titolo_modulo_attivo" name="titolo_modulo_attivo" value="<%= modulo.getName() %>" /> 
			<input type="hidden" id="id_modulo_attivo" name="id_modulo_attivo" value="<%= modulo.getId() %>" /> 
			<input type="hidden" id="riferimento_modulo_attivo" name="riferimento_modulo_attivo" value="<%= modulo.getRiferimento() %>" /> 
			<input type="hidden" id="quadro_attivo" name="quadro_attivo" value="" />
			
			<input type="hidden" name="codiceIstanza" value="${presentazioneDomandaCommand.codiceIstanza }" />
			<input type="hidden" name="codiceMovimento" value="${presentazioneDomandaCommand.codiceMovimento }" />
		</form>
		<div id="moduli_tabs" class="tabs-moduli-container">
			<ul>
				<%
			    for(int i = 0; i < moduli.size(); i++){
					modulo = moduli.get(i);
			%>
				<li id="tab_<%=modulo.getId()%>">
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
			<input type="button" value="Genera allegati per la notifica" name="submit" id="presenta_domanda" class="bottone-cart" />
			<input type="button" class="bottone-cart" onclick="document.location.href='../cart/generaAllegatiNotifica.htm?codiceIstanza=${presentazioneDomandaCommand.codiceIstanza}&codiceMovimento=${presentazioneDomandaCommand.codiceMovimento}'" value="Chiudi" />
		</div>

		<%-- 
	    </form>
	    --%>
		<%
		} 
		else{
		%>
		<span>Nessuna modulistica disponibile per l&apos;attivit&agrave; selezionata</span>
		<%
		}
		%>

	</div>
</body>
</html>