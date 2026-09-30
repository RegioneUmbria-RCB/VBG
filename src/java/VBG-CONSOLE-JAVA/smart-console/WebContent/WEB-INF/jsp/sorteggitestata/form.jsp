<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.NEW}">
			<fmt:message key="sorteggitestata.label.nuovo_sorteggitestata.title" />
		</c:if> 
		<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.VIEW}">
			<fmt:message key="sorteggitestata.label.dettaglio_sorteggitestata.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
	<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.NEW}">
		<fmt:message key="sorteggitestata.label.nuovo_sorteggitestata.title" />
	</c:if> 
	<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.VIEW}">
		<fmt:message key="sorteggitestata.label.dettaglio_sorteggitestata.title" />
	</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<%
		String escludeestrazioni="display:none;";
		String salva="display:none;";
	%>
	<div id="subcontent">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
	       <jsp:param name="commandName" value="sorteggitestata" />
	    </jsp:include>
		<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.NEW}">
		<spring-form:form commandName="sorteggitestata" name="inviodati">			
			<table>
				<tr id="elementIdBeforeCombo">
					<td width="15%"></td>
					<td colspan="3"></td>
				</tr>
				<jsp:include page="../includes/comboComuni.jsp">					
					<jsp:param name="mostraTutti" value="false" />
					<jsp:param name="readOnly" value="false" />
					<jsp:param name="commandPropertyPath" value="sorteggitestataFilter.comune" />
					<jsp:param name="colspan" value="4" />
					<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
				</jsp:include>
				<tr>
					<td>
						<fmt:message key="label.data_sorteggio" />
					</td>
					<td>
						<spring-form:input  tabindex="2" id="dataSorteggio_id" path="sorteggitestataFilter.dataSorteggio" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
		   				<init:calendar idImage="caldata1" idInput="dataSorteggio_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
		   				<spring-form:errors	path="sorteggitestataFilter.dataSorteggio" cssClass="error" />
					</td>
				</tr>	
			</table>
			<br class="clear"/>	
			<fieldset><legend><fmt:message key="label.filtri_istanza" /></legend>
			<table>
				<tr>
					<td width="200px"><fmt:message key="label.archivio_pratiche" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="archiviopratiche_id" />		
							<jsp:param name="propertyPath" value="sorteggitestataFilter.tipiarchivioistanza" />				
							<jsp:param name="pathPropertyDescription" value="sorteggitestataFilter.tipiarchivioistanza.archivio" />
							<jsp:param name="pathPropertyCode" value="sorteggitestataFilter.tipiarchivioistanza.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiarchivioistanze.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipiarchivioistanze" />
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td valign="top"><fmt:message key="label.tipologia_intervento" /></td>
					<td colspan="3">
						<%-- 
						<spring-form:textarea id="alberoproc_id" path="sorteggitestataFilter.alberoproc.vwAlberoproc.scDescrizione" cssClass="searchbox" onchange="checkValue(this,'alberoproc_hidden')" onkeydown="return searchAll(this,event)" cols="70" rows="2" />
						<init:autocompleter methodAjax="findAlberoproc.htm" idHidden="alberoproc_hidden" idInput="alberoproc_id" inputTitleKey="label.ricerca_intervento"/>
						<spring-form:errors path="sorteggitestataFilter.alberoproc.scDescrizione" cssClass="error"/> 
						<spring-form:hidden	id="alberoproc_hidden" path="sorteggitestataFilter.alberoproc.id.codice"/>
						--%>
						<%-- 
							Campo nascosto per evitare errore di validazione: siccome visualizziamo alberoproc.vwAlberoproc.scDescrizione
							e l'oggetto di dominio ha il controllo di validazione @Valid allora trovando alberoproc.scDescrizione vuoto o nullo
							dà errore nella validazione (vedi it.gruppoinit.pal.gp.core.domain.Alberoproc.getVwAlberoproc())
						--%>
						<%-- 
						<spring-form:hidden	id="alberoproc_hidden_descrizione" path="sorteggitestataFilter.alberoproc.scDescrizione"/>
						--%>
						<jsp:include page="../includes/searchAlberoProc.jsp">
								<jsp:param name="propertyPath" value="sorteggitestataFilter.alberoproc.vwAlberoproc" />								
								<jsp:param name="pathPropertyDescription" value="sorteggitestataFilter.alberoproc.vwAlberoproc.scDescrizione" />
								<jsp:param name="pathPropertyCode" value="sorteggitestataFilter.alberoproc.id.codice" />
								<jsp:param name="isSelectLeafDisable" value="true" />
								<jsp:param name="isSelectNodoPadre" value="true" />
				 		</jsp:include>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.tipo_procedura" /></td>
					<td colspan="3">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="tipiprocedure_id" />				
							<jsp:param name="propertyPath" value="sorteggitestataFilter.procedura" />			
							<jsp:param name="pathPropertyDescription" value="sorteggitestataFilter.procedura.procedura" />
							<jsp:param name="pathPropertyCode" value="sorteggitestataFilter.procedura.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiprocedure.htm?includiDisabilitate=true" />
							<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
						</jsp:include>
					</td>
				</tr>	
				<tr>
					<td><fmt:message key="label.stato" /></td>
					<td colspan="3">
						<spring-form:select id="chiusura_id" path="sorteggitestataFilter.chiusura.id.codicestato" >
							<spring-form:option value=""><fmt:message key='label.tutte' /></spring-form:option>
							<c:forEach items="${sorteggitestata.statiistanzaList}" var="statiistanza">
								<spring-form:option value="${statiistanza.id.codicestato}" label="${statiistanza.stato}"></spring-form:option>
							</c:forEach>
						</spring-form:select>
					</td>
				</tr>
			</table>
			</fieldset>
			<br class="clear"/>	
			<fieldset><legend><fmt:message key="label.filtri_movimento" /></legend>
			<table>
				<tr>
					<td width="200px">
						<fmt:message key="label.data_movimento_dal" />
					</td>
					<td>
						<spring-form:input id="dataDal_id" path="sorteggitestataFilter.dataDal" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
		   				<init:calendar idImage="caldata2" idInput="dataDal_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
		   				<init:help idHelp="help2" textKey="sorteggitestata.help.dataDal"/>
		   				<spring-form:errors	path="sorteggitestataFilter.dataDal" cssClass="error" />
					</td>				
					<td>
						<fmt:message key="label.data_movimento_al" />
					</td>
					<td>
						<spring-form:input id="dataAl_id" path="sorteggitestataFilter.dataAl" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
		   				<init:calendar idImage="caldata3" idInput="dataAl_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
		   				<init:help idHelp="help3" textKey="sorteggitestata.help.dataAl"/>
		   				<spring-form:errors	path="sorteggitestataFilter.dataAl" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.tipomovimento" /></td>
					<td colspan="3">							
						<jsp:include page="../includes/tipimovimentosearch.jsp">
							<jsp:param name="idElemento" value="tipoMovimentoInputId" />
							<jsp:param name="pathTipomovimento"	value="sorteggitestataFilter.tipoMovimento" />
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.tipo_ricerca_mov" /></td>
					<td colspan="3">
						<spring-form:select path="sorteggitestataFilter.tipoRicercaMovimento" > 
							<spring-form:option value="0" ><fmt:message key='sorteggitestata.label.ricerca_mov_effettuati' /></spring-form:option>
							<spring-form:option value="1"><fmt:message key='sorteggitestata.label.ricerca_mov_da_effettuare' /></spring-form:option>
							<spring-form:option value="2" ><fmt:message key='sorteggitestata.label.ricerca_tutti' /></spring-form:option>
						</spring-form:select>
						<spring-form:errors path="sorteggitestataFilter.tipoRicercaMovimento" cssClass="error"/> 
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.tipologia_esito" /></td>
					<td colspan="3">
						<spring-form:select path="sorteggitestataFilter.esito" > 
							<spring-form:option value="0" ><fmt:message key='label.qualsiasi' /></spring-form:option>
							<spring-form:option value="1"><fmt:message key='label.negativo' /></spring-form:option>
							<spring-form:option value="2" ><fmt:message key='label.positivo' /></spring-form:option>
						</spring-form:select>
						<spring-form:errors path="sorteggitestataFilter.esito" cssClass="error"/> 
					</td>
				</tr>
			</table>
			</fieldset>
			<br class="clear"/>	
			<fieldset><legend><fmt:message key="label.filtri_estrazione" /></legend>
			<table>
				<tr>
					<td  width="200px">
						<fmt:message key="label.percentuale" />
					</td>
					<td>
						<spring-form:input cssStyle="text-align:right;" id="percentuale_id" path="sorteggitestataFilter.percentuale" size="3" maxlength="3" onchange="javascript:checkNumberInt(this)" />
						<fmt:message key="label.percento" />
						<spring-form:errors path="sorteggitestataFilter.percentuale" cssClass="error"/>						
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="label.estrazione_gruppi" />
					</td>
					<td>
						<spring-form:input cssStyle="text-align:right;" id="gruppiIstanze_id" path="sorteggitestataFilter.gruppiIstanze" size="3" maxlength="3" onchange="javascript:checkNumberInt(this)" />
						<spring-form:errors path="sorteggitestataFilter.gruppiIstanze" cssClass="error"/>
						<fmt:message key="label.istanze" />
					</td>
				</tr>	
				<tr>
					<td><fmt:message key="label.arrotondamento" /></td>
					<td colspan="3">
						<spring-form:select path="sorteggitestataFilter.arrotondamento" > 
							<spring-form:option value="0" ><fmt:message key='label.approssimazione_difetto' /></spring-form:option>
							<spring-form:option value="1"><fmt:message key='label.approssimazione_eccesso' /></spring-form:option>
							<spring-form:option value="2" ><fmt:message key='label.arrotondamento_intero' /></spring-form:option>
						</spring-form:select>
						<spring-form:errors path="sorteggitestataFilter.arrotondamento" cssClass="error"/> 
					</td>
				</tr>	
			</table>
			</fieldset>
			
			<c:if test="${not empty sorteggitestata.sorteggitestataList or not empty sorteggitestata.sorteggiCategorieList}">
			<br class="clear"/>	
			<fieldset><legend><fmt:message key="label.filtri_esclusione_istanze" /></legend>
			<table>
				<tr>
					<td  width="200px"><fmt:message key="label.esclude" /></td>
					<td>
						<spring-form:checkbox id="escludeIstanze_id" path="sorteggitestataFilter.escludeIstanze" onclick="viewEscludeEstrazioni();"/>
						<init:help idHelp="help4" textKey="sorteggitestata.help.escludeIstanze"/>
						<spring-form:errors path="sorteggitestataFilter.escludeIstanze" cssClass="error" />
					</td>
				</tr>
				<c:if test="${not empty sorteggitestata.sorteggitestataList}">
				<tr id="listaEstrazioni_id" style="<%=escludeestrazioni %>">
					<td>
						<fmt:message key="label.lista_estrazioni" />
					</td>
					<td colspan="3">
						<spring-form:select id="listaEstrazioni_id" path="sorteggitestataFilter.listaEstrazioni" multiple="true" size="${fn:length(sorteggitestata.sorteggitestataList)}">
							<spring-form:options items="${sorteggitestata.sorteggitestataList}" itemValue="id.codice" itemLabel="stDescrizione"></spring-form:options>				
						</spring-form:select>
					</td>
				</tr>				
				</c:if>				
				<c:if test="${not empty sorteggitestata.sorteggiCategorieList}">
				<tr id="listaCategorieEstrazioni_id" style="<%=escludeestrazioni %>">
					<td>
						<fmt:message key="label.lista_categorie" />
					</td>
					<td colspan="3">
						<spring-form:select id="listaCategorieEstrazioni_id" path="sorteggitestataFilter.listaCategorieEstrazioni" multiple="true" size="${fn:length(sorteggitestata.sorteggiCategorieList)}">
							<spring-form:options items="${sorteggitestata.sorteggiCategorieList}" itemValue="id.codice" itemLabel="descrizione"></spring-form:options>				
						</spring-form:select>
					</td>		
				</tr>		 
				</c:if>				 		
			</table>
			</fieldset>
			</c:if>
			<br class="clear"/>	
			<fieldset><legend><fmt:message key="label.dati_salvataggio_sorteggio" /></legend>
			<table>
				<tr>
					<td  width="200px"><fmt:message key="label.salva" /></td>
					<td>
						<spring-form:checkbox id="salva_id" path="sorteggitestataFilter.salva" onclick="viewSalva();"/>
						<init:help idHelp="help5" textKey="sorteggitestata.help.salva"/>
						<spring-form:errors path="sorteggitestataFilter.salva" cssClass="error" />
					</td>
				</tr>
				<tr  id="descrizione_id" style="<%=salva %>">
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="sorteggitestataFilter.descrizione" size="70" />						
						<spring-form:errors path="sorteggitestataFilter.descrizione" cssClass="error"/>
					</td>
				</tr>				
				<tr  id="categoria_id" style="<%=salva %>">
					<td><fmt:message key="label.categoria" /></td>
					<td colspan="3">						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="categoria_id" />		
							<jsp:param name="propertyPath" value="sorteggitestataFilter.categoria" />				
							<jsp:param name="pathPropertyDescription" value="sorteggitestataFilter.categoria.descrizione" />
							<jsp:param name="pathPropertyCode" value="sorteggitestataFilter.categoria.id.codice" />
							<jsp:param name="autocompleterAjax" value="findSorteggiCategorie.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_categoria" />
						</jsp:include>
					</td>
				</tr>				 
			</table>
			</fieldset>			
			<script type='text/javascript'>
			jQuery(document).ready(function(){
				$('archiviopratiche_id_id').focus();
				viewEscludeEstrazioni();
				viewSalva();				
			});
				function viewEscludeEstrazioni(){
					if($('escludeIstanze_id')){
						if($('escludeIstanze_id').checked){
							if($('listaCategorieEstrazioni_id')){
								$('listaCategorieEstrazioni_id').appear();
							}
							if($('listaEstrazioni_id')){
								$('listaEstrazioni_id').appear();
							}
						}else{
							if($('listaCategorieEstrazioni_id')){
								$('listaCategorieEstrazioni_id').fade();
							}
							if($('listaEstrazioni_id')){
								$('listaEstrazioni_id').fade();
							}
						}
					}
				}				
				function viewSalva(){
					if($('salva_id').checked){
						$('descrizione_id').appear();
						$('categoria_id').appear();
					}else{
						$('descrizione_id').fade();
						$('categoria_id').fade();
					}
				}
			</script>
		</spring-form:form>
		</c:if>
		</div>			
		<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.VIEW}">
			<%-- Testata --%>			
			<div class="parametriDiv">
				<div class="etichetta">    		 
					<div><fmt:message key="label.descrizione" />:</div>
					<div><fmt:message key="label.data_sorteggio" />:</div>
		        </div>
		        <div class="parametro">
		      		 <div>&nbsp;${sorteggitestata.entity.stDescrizione}</div>
		      		 <div>&nbsp;<fmt:formatDate value="${sorteggitestata.entity.stDatasorteggio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></div>		      		 
	        	</div>
			 </div>
	 		<br class="clear"/>
	 		<spring-form:form commandName="sorteggitestata" name="inviodati">
				<table>
					<tr>
						<td>
							<fmt:message key="label.documento_da_allegare" />
						</td>
						<td>
						<jsp:include page="../includes/oggetti.jsp">
		       				<jsp:param name="idElemento" value="oggettoIdCodice" />
		   					<jsp:param name="codiceOggetto" value="${sorteggitestata.entity.oggetto.id.codice}" />
		   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
		   					<jsp:param name="nomefileId" value="oggetto_nomefile" />
		   				</jsp:include>
	    				<spring-form:hidden path="entity.oggetto.id.codice" id="oggetto_id_codice"/>
	    				<spring-form:hidden path="entity.oggetto.nomefile" id="oggetto_nomefile"/>
	    				<spring-form:errors path="entity.oggetto" cssClass="error"/>
						</td>
					</tr>
				</table>
			</spring-form:form>		
		</c:if>
			<div id="functions">
				<ul>
					<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.NEW}">
						<li><a href="javascript:doSubmit('sorteggia.htm?sorteggia=true&sorteggidettaglio_id_f_sorteggiata=Si','',document.inviodati)"><fmt:message key="button.sorteggia" /></a></li>
					</c:if>
					<c:if test="${sorteggitestata.displayMode==sorteggitestata.displayConstants.VIEW}">	
						<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>	
						
						<%-- STAMPA DOCUMENTO TIPO --%>
						<%pageContext.setAttribute("URL_STAMPA",BackofficeNETConstants.getURL_STAMPA_DOC_TIPO_SORTEGGIO());%>
						<c:set var="_URL_STAMPA" value="${URL_STAMPA}?ST_ID=${sorteggitestata.entity.id.codice}" /><c:set var="_URL_STAMPA" value="${inite:geturlto(pageContext.request, _URL_STAMPA, _urlback, null, true)}" />
						<li><a href="javascript:void 0"	onclick="window.open('${_URL_STAMPA}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message key="button.stampa" /></a></li>
						<li><a	href="javascript:doSubmit('createMovimento.htm?codice=${sorteggitestata.entity.id.codice}','',document.inviodati)"><fmt:message key="button.inserisci_movimento" /></a></li>		
						<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
					</c:if>
					<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
				</ul>
			</div>
			<br class="clear"/>
			<br class="clear"/>
			<%-- Info --%>	
			<c:if test="${sorteggitestata.entity.sorteggitestatainfos!=null && not empty sorteggitestata.entity.sorteggitestatainfos}">	
	 		<%
				String displayFiltri = "display:none;";
				String styleFiltri = "";		
				if (((String) request.getAttribute(WebConstants.CONF_UTENTE_SORT_TEST_VISFILTRI_DIV)).equals("1")) {
				    displayFiltri = "";
				    styleFiltri ="sezioneDatiMeno";
				} else {
				    displayFiltri = "display:none;";
				    styleFiltri ="sezioneDatiPiu";
				}
			%>		 
			<a class="<%= styleFiltri %>" 
				id="id_link_filtri" 
				href="javascript:showHidePanel('filtriId', 'id_link_filtri', '<%= WebConstants.CONF_UTENTE_SORT_TEST_VISFILTRI_DIV %>', '${pageContext.request.contextPath}/images/','div');"	
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="sorteggitestata.label.list_info_sorteggio.title" />">
				<label for="id_link_filtri" ><fmt:message key="sorteggitestata.label.list_info_sorteggio.title" /></label>
			</a>			
			<div class="jmesa" id="filtriId" style="<%= displayFiltri%>">
				<fieldset>
				<table cellpadding="2" cellspacing="0" class="table"> <%-- border="0" width="70%" cellpadding="2" cellspacing="0" class="table"  --%> 
					<thead>
						<tr class="header">
							<td><fmt:message key="label.filtro" /></td>
							<td><fmt:message key="label.valore" /></td>	
						</tr>
					</thead>
					<tbody class="tbody" >
					<%int i=1;%>
					<c:forEach var="testatainfo_var" items="${sorteggitestata.entity.sorteggitestatainfos}" varStatus="testatainfoStatus">
					<tr class="<%=(i%2)==0?"odd":"even"%>"  >
						<td>${testatainfo_var.etichetta}</td>
						<td>${testatainfo_var.valore}</td>
					</tr>
					</c:forEach>
					</tbody>
				</table>
				</fieldset>
			</div>			
			</c:if>			
		<%-- End View --%>
	<c:if test="${sorteggitestata.entity.sorteggidettaglios!=null && not empty sorteggitestata.entity.sorteggidettaglios}">
		<br class="clear"/>		
		<%
			String displayIstSort = "display:none;";
			String styleIstSort = "";		
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_SORT_TEST_VISSORTEGGIATE_DIV)).equals("1")) {
			    displayIstSort = "";
			    styleIstSort="sezioneDatiMeno";
			} else {
			    displayIstSort = "display:none;";
			    styleIstSort ="sezioneDatiPiu";
			}
		%>		 
		<a class="<%= styleIstSort %>" 
			id="id_link_istSort" 
			href="javascript:showHidePanel('istSortId', 'id_link_istSort', '<%= WebConstants.CONF_UTENTE_SORT_TEST_VISSORTEGGIATE_DIV %>', '${pageContext.request.contextPath}/images/','div');"	
			title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="sorteggitestata.label.list_istanze_sorteggiate.title" />">
			<label for="id_link_istSort" ><fmt:message key="sorteggitestata.label.list_istanze_sorteggiate.title" /></label>
		</a>
		<div id="istSortId" style="<%= displayIstSort%>">	
		<fieldset>
		<spring-form:form commandName="sorteggitestata" name="jmesa" action="sorteggia.htm?sorteggia=false">
			<jmesa:springTableFacade
				id="sorteggidettaglio_id" 
				items="${sorteggitestata.sorteggidettaglioList}" 
				var="sorteggidettaglio_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.SorteggidettaglioFilterMatcherMap">
				<jmesa:htmlTable>				
					<jmesa:htmlRow>												
						<jmesa:htmlColumn property="istanza.numeroistanza" titleKey="label.numeroistanza"/>
						<jmesa:htmlColumn property="istanza.data" titleKey="label.data_presentazione"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataIstanzaSorteggioCustomFilter" width="8%"/>
						<jmesa:htmlColumn property="istanza.numeroprotocollo" titleKey="label.numero_protocollo"/>
						<jmesa:htmlColumn property="istanza.dataprotocollo" titleKey="label.data_protocollo"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataProtSorteggioCustomFilter" width="8%"/>
						<jmesa:htmlColumn property="istanza.transientRichiedenteQualitaAzienda" titleKey="label.richiedente"/>
						<jmesa:htmlColumn property="istanza.transientLocalizzazionePrimario" titleKey="label.indirizzo"/>
						<jmesa:htmlColumn property="istanza.alberoproc.vwAlberoproc.scDescrizione" titleKey="label.intervento"/>
						<jmesa:htmlColumn property="istanza.tipiarchivioistanza.archivio" titleKey="label.posizione_archivio"/>											
						<jmesa:htmlColumn property="istanza.lavori" titleKey="label.lavori"/>
						<jmesa:htmlColumn property="sorteggiata" titleKey="label.sorteggiata" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist" />	
					</jmesa:htmlRow>				
				</jmesa:htmlTable>
			</jmesa:springTableFacade>	
			<input type="hidden" value="${sorteggitestata.entity.id.codice}" name="codice" />					
		</spring-form:form>	
		<script type="text/javascript">			
			var _jmesaUrl='../sorteggitestata/sorteggia.htm?sorteggia=false&';
			var _captionTab='<fmt:message key="sorteggitestata.label.list_istanze_sorteggiate" />';			
		</script>		
		</fieldset>
		</div>
	</c:if>	
</body>
</html>