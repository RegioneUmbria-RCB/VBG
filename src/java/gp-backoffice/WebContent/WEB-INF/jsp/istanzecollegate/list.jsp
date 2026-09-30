<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_istanze_collegate" /></title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.lista_istanze_collegate" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanzecollegate/list" />
	</jsp:include>
	<div id="subcontent">
	
		<%
		 	
			String displayIstanzecollegate= "";
			String styledisplayIstanzecollegate = "sezioneDatiMeno";
			String displaypannelloRiassuntivo = "";
			String stylepannelloRiassuntivo = "sezioneDatiMeno";
		%>
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzecollegate" />
		</jsp:include>    
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${istanza.id.codice}</c:param>
		</c:import>
 		<br class="clear" /><br class="clear" />
 		
 		
 		<c:if test="${not empty listaIstanzecollegateSuccessive  || not empty listaIstanzecollegatePrecedenti}">
 		<table width="100%">
 		<tr align="center">
			<td>
			<fieldset>
		 	<legend>
			<a
				class="<%=stylepannelloRiassuntivo%>"
				id="id_link_pannelloriassuntivo"
				href="javascript:showHidePanelBase('id_pannelloriassuntivo_table', 'id_link_pannelloriassuntivo', ' ', '${pageContext.request.contextPath}/images/','table',false);"
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.pannello_riassuntivo_collegamenti"/>">
				<label for="id_link_pannelloriassuntivo"><fmt:message key="label.pannello_riassuntivo_collegamenti" /></label> 
			</a> </legend>
		
 	        <div class="jmesa" >
	 		<table border="0"  cellpadding="0"  cellspacing="0"  id="id_pannelloriassuntivo_table" class="jmesa" width="60%" align="center">
	 		<thead>
	 		<tr class="header">
	 				<td >
	 					<c:if test="${empty listaIstanzecollegatePrecedenti}">
							<a class="addColumn" href="javascript:historySet('${_urlback}','..%2Fistanze/searchIstanze.htm?tipoRicerca=<%=WebConstants.SEARCH_ISTANZE_COLLEGATE%>&codiceIstanzaDaconfigurare=${istanza.id.codice}&isCollegamentoPrecedente=true','')" title="<fmt:message key="label.aggiungi" />">
	 				        	<label><fmt:message key="label.collega_istanza_precedente" /></label>
	 				        </a>
	 				    </c:if>
	 					<fmt:message key="label.precedente" />
	 				</td>
	 				<td ><fmt:message key="label.istanza_corrente" /></td>
	 				<td ><fmt:message key="label.successivo" /></td>
	 		</tr>
	 		</thead>
	 		<%
			    int i=0;
		    %>
	 		<tbody class="tbody">
	 		<tr class="odd">
	 				<td  valign="bottom" style="text-align:left"> 
	 					<c:forEach items="${listaIstanzecollegatePrecedenti}" var="istanzecollegatePrecedenti">
		 					<a href="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${istanzecollegatePrecedenti.id.codice}&software=${istanzecollegatePrecedenti.software.codice}','')" title="<fmt:message key="label.edit.record" />">
									${istanzecollegatePrecedenti.numeroistanza}
							</a><br/>
	 					</c:forEach>
	 				</td>
	 				<td valign="bottom" style="text-align:center">${istanza.numeroistanza}</td>
	 				<td valign="bottom" style="text-align: right;">
						<c:forEach items="${listaIstanzecollegateSuccessive}" var="istanzecollegateSuccessive">
						<a  href="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${istanzecollegateSuccessive.id.codice}&software=${istanzecollegateSuccessive.software.codice}','')" title="<fmt:message key="label.edit.record" />">
								${istanzecollegateSuccessive.numeroistanza}
						</a><br/>
	 				    </c:forEach>
	 				</td>
	 		<%i++;%>
	 		</tr>
	 		
	 		</tbody>
	 		</table>
	 		</div>
	 		</fieldset>
 		</td>
 		</tr>
 		
 		</table>
 		
 		<br class="clear" />
 		</c:if>
 	
 	<c:if test="${VIS_CAMBIO_STATO eq true }">
 	
		<div id="functions">
			<ul>
				<li><a href="javascript:void 0" onclick="attivaModificaStato()"><fmt:message key="button.modifica_stato_pratiche" /></a></li>
			</ul>
		</div>
		<br />	
 		<div class="modifica_stato_istanze_id" style="display: none; width:100%;"><fmt:message key="label.assegna_stato_pratiche" />
 			<select name="nuovostato" id="nuovostato_id">
 				<c:forEach items="${statiistanzas }" var="si">
 					<option value="${si.id.codicestato}">${ si.stato }</option>
 				</c:forEach>
 			</select>
 			<label for="seleziona_tutte" ><b>Seleziona tutte</b></label>
 			<input type="checkbox" id="seleziona_tutte" onclick="selezionatutte()"/>
 			
 			<div id="functions">
				<ul>
					<li><a href="javascript:void 0" onclick="modificaStato()"><fmt:message key="button.aggiorna_stato_pratiche" /></a></li>
				</ul>
			</div>
 			
 			
 			
 			
 		</div>
 		
 	</c:if>	
 		
 		
 		<!-- TABELLA PRINCIPALE CHE RACCOGLIE TUTTE LE VARIE TABELLE DELLE ISTANZE COLLEGATE -->
 		<!-- START -->
 		<table width="100%">
 		
 						
 		<c:forEach items="${istanzecollegateHelperList}" var="istanzecollegateHelper" varStatus="indice_istanzecollegateHelper">
 		
 		
	 		<tr>
		 		<td>
				<fieldset>
		 		<legend> <a
				class="<%=styledisplayIstanzecollegate%>"
				id="id_link_istanzecollegate${indice_istanzecollegateHelper.index}"
				href="javascript:showHidePanelBase('id_istanzecollegate_table${indice_istanzecollegateHelper.index}', 'id_link_istanzecollegate${indice_istanzecollegateHelper.index}', ' ', '${pageContext.request.contextPath}/images/','form',false);"
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.istanze_collegate"/>">
				<label for="id_link_istanzecollegate${indice_istanzecollegateHelper.index}"><fmt:message key="label.istanze_collegate" /></label> </a> </legend>
					  <!-- TABELLA DI OGNI LISTA DI ISTANZE COLLEGATE -->
		 			  <!-- START -->
		 			
		 			<form id="id_istanzecollegate_table${indice_istanzecollegateHelper.index}" name="istanzecollegateForm${indice_istanzecollegateHelper.index}" action="list.htm">
		 			<jmesa:springTableFacade id="istanzecollegate_id${indice_istanzecollegateHelper.index}" items="${istanzecollegateHelper.istanzecollegates}" var="istanzecollegate_var" stateAttr="restore"
		 			filterMatcherMap="org.jmesa.custom.IstanzecollegateFilterMatcherMap">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
						<jmesa:htmlColumn property="istanza.numeroistanza" titleKey="label.codice_istanza" width="2%">
						     <c:if test="${istanzecollegate_var.istanza.id.codice ne istanza.id.codice}">
			                	 <a href="javascript:historySet('${_urlback}','../istanze/view.htm?codice=${istanzecollegate_var.istanza.id.codice}&software=${istanzecollegate_var.istanza.software.codice}','')">${istanzecollegate_var.istanza.numeroistanza}</a>
			                 </c:if>
			                 <c:if test="${istanzecollegate_var.istanza.id.codice eq istanza.id.codice}">
			                	${istanzecollegate_var.istanza.numeroistanza}
			                 </c:if>
			                 <c:if test="${istanzecollegate_var.istanza.chiusura.staticomportamento.codcomportamento eq 0}">
			                 	<div class="modifica_stato_istanze_id" style="display: none;"><input type="checkbox" class="istanzemodstato" value="${istanzecollegate_var.istanza.id.codice}" /></div>
			                 </c:if>
			            </jmesa:htmlColumn>
						<jmesa:htmlColumn width="10%" property="istanza.data" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataIstanzacollegataCustomFilter"	titleKey="label.data"  />
						<jmesa:htmlColumn width="10%" property="istanza.datavalidita"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataValiditaIstanzacollegataCustomFilter" titleKey="label.data_validita" />
						<jmesa:htmlColumn property="istanza.transientRichiedenteQualitaAzienda"	titleKey="label.richiedente" />
						<jmesa:htmlColumn property="istanza.transientRichiedenteQualitaAziendaStorico" titleKey="label.richiedente_storico" />
						<jmesa:htmlColumn property="istanza.transientLocalizzazionePrimario" titleKey="label.indirizzo" />
						<jmesa:htmlColumn property="istanza.alberoproc.vwAlberoproc.scDescrizione" titleKey="label.intervento" />
						<jmesa:htmlColumn property="istanza.procedura.procedura" titleKey="label.procedura" />
						<jmesa:htmlColumn property="istanza.chiusura.stato" titleKey="label.stato" />
						<jmesa:htmlColumn property="istanza.azione" titleKey="label.azione" />
						<jmesa:htmlColumn property="id.ordine" titleKey="label.ordine" width="7%" >
							${istanzecollegate_var.id.ordine}
							<c:if test="${istanzecollegate_var.id.ordine!=fn:length(istanzecollegateHelper.istanzecollegates)}">
							<a class="downColumn" href="downColumn.htm?codiceIstanzaCollegata=${istanzecollegate_var.id.codiceistanza}&progressivo=${istanzecollegate_var.id.progressivo}&ordine=${istanzecollegate_var.id.ordine}&codiceIstanza=${istanza.id.codice}" title="<fmt:message key="label.down" /> ">
								<label><fmt:message key="label.azioni" /></label>
							</a>
							</c:if>
							<c:if test="${istanzecollegate_var.id.ordine!=1}">
							<a class="upColumn" href="upColumn.htm?codiceIstanzaCollegata=${istanzecollegate_var.id.codiceistanza}&progressivo=${istanzecollegate_var.id.progressivo}&ordine=${istanzecollegate_var.id.ordine}&codiceIstanza=${istanza.id.codice}" title="<fmt:message key="label.up" /> ">
								<label><fmt:message key="label.azioni" /></label>
							</a>
							</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="" titleKey="label.azioni"	sortable="false" filterable="false" width="5%">
							
							<a class="eliminaRiga" href="javascript:doHref('deleteCollegamento.htm?codiceIstanzaDaScollegare=${istanzecollegate_var.id.codiceistanza}&progressivo=${istanzecollegate_var.id.progressivo}&ordine=${istanzecollegate_var.id.ordine}&codiceIstanza=${istanza.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />">
								<label><fmt:message key="label.azioni" /></label>
							</a>
						</jmesa:htmlColumn>
						
				</jmesa:htmlRow>
				</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${istanza.id.codice}" name="codiceIstanza"/>
				</form>
	 			</fieldset>
		  	</td>
			</tr>
		</c:forEach>
  		</table>		    	
		
		
		<!-- TABELLA PRINCIPALE CHE RACCOGLIE TUTTE LE VARIE TABELLE DELLE ISTANZE COLLEGATE -->
 		<!-- END -->
	   
	</div>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:historySet('${_urlback}','..%2Fistanze/searchIstanze.htm?tipoRicerca=<%=WebConstants.SEARCH_ISTANZE_COLLEGATE%>&codiceIstanzaDaconfigurare=${istanza.id.codice}','')"><fmt:message key="button.crea_collegamento" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
	<script type="text/javascript">

	function attivaModificaStato(){
		jQuery('.modifica_stato_istanze_id').each(function () {
			jQuery(this).toggle();
		});
	}
	
	
	function selezionatutte(){
		
		var checked = jQuery('#seleziona_tutte').is(':checked');
		
		jQuery('.istanzemodstato').each(function () {
			
	            jQuery(this).prop('checked',checked);                
	        
		});
		
	}
	
	function modificaStato(){
		
		var stato = jQuery('#nuovostato_id').val();
		var istanzeSelezionate="";
		
		jQuery('.istanzemodstato').each(function () {

			if(jQuery(this).attr('checked')=="checked"){
				istanzeSelezionate += jQuery(this).val() + "|";
			}			
		});
		
		if(istanzeSelezionate!=''){
			if(confirm('Attenzione! Si intende modificare gli stati delle pratiche selezionate. Proseguire?')){
				location.href = '${pageContext.request.contextPath}/istanzecollegate/updateModificaStatoPratiche.htm?codicestato='+stato+'&istanze='+istanzeSelezionate+'&tmpStato=${tmpStatoIstanza}&codiceIstanza=${istanza.id.codice}'; 
			}
		}else{
			alert('Selezionare le pratiche per le quali impostare il nuovo stato');
			
		}
	}
	function ajaxHistorySet(url){
		new Ajax.Request('<%=request.getContextPath()%>/history/ajaxSet.htm', {
			  method: 'get',
			  parameters: {ReturnTo: url, limit: 12},
			  onSuccess: function(transport){},
			  onFailure: function(){}			  
		});
	}
	
	function searchIstanze(){
		ajaxHistorySet(URLDecode('${_urlback}'));
		doSubmit('../istanze/listIstanze.htm','',document.inviodati);
		
	}
	
	function upOrdine(codiceIstanza,progressivo){
		new Ajax.Request(
					'${pageContext.request.contextPath}/istanzecollegate/ajaxUpColumn.htm?codiceIstanza='+codiceIstanza+'&progressivo='+ progressivo,
					{
						onSuccess : function(transport) {
							alert('modificaUp');
							mostralistaaggiornata();
						},
						onFailure : function(transport) {
							$(result+codice).innerHTML = transport.responseText;
							$(result+codice).className = 'error_checkbox';
							$(result+codice).style.display = '';
							$(result+codice).pulsate;
							({
								pulses : 2,
								duration : 1.0
							});
						}
					});
		
	}
	function downOrdine(codiceIstanza,progressivo){
		new Ajax.Request(
					'${pageContext.request.contextPath}/istanzecollegate/ajaxDownColumn.htm?codiceIstanza='+codiceIstanza+'&progressivo='+ progressivo,
					{
						onSuccess : function(transport) {
							//$(result+codice).innerHTML = transport.responseText;
							//$(result+codice).className = 'succes_checkbox';
							//$(result+codice).style.display = '';
							//$(result+codice).pulsate;
							//$(result+codice).fade
							//({
							//	pulses : 2,
							//	duration : 3.0
							//});
							alert('modificaDown');
							mostralistaaggiornata();
						},
						onFailure : function(transport) {
							$(result+codice).innerHTML = transport.responseText;
							$(result+codice).className = 'error_checkbox';
							$(result+codice).style.display = '';
							$(result+codice).pulsate;
							({
								pulses : 2,
								duration : 1.0
							});
						}
					});
		
	}
	
	
	
	
	</script>
</body>
</html>