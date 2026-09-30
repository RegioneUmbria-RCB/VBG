<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.MercatiD"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.net.URLEncoder"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="mercatid.label.lista_posteggi.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="mercatid.label.lista_posteggi.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatid/list" />
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
		<div class="etichetta">
	      	<div>
	        	<fmt:message key="label.manifestazione"/>:
	        </div>
	    </div>        
	    <div class="parametro">
	      	<div>
	             ${mercati.descrizione}
	        </div>
        </div>
	</div>
	<div class="clear" />	
	<spring-form:form commandName="filtro"name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="filtro" />
	</jsp:include>

	<%-- variabili per la visualizzazione dei campi bottoni e campi nascosti --%>
	<%-- START --%>
	<%
	    String displayMerceologie = "display:none;";
	    String displayAltreinfo = "display:none;";
	    String displayFiltri = "display:none;";
	    String displayDettaglioMerceologie = "display:none;";
	    String displayDettagliRiga = "display:none;";
	    String displayConcessioni = "display:none;";
	   
		String showChecked = "";
		String showChecked1 = "";
		String showChecked2 = "";
		
		boolean visualizzaMerceologie = false;
		boolean visualizzaAltreInformazioni = false;
		//gestisce la visualizzazione della tabella merceologie per ogni singolo posteggio
		if (((String) request.getAttribute(WebConstants.VISUALIZZA_MERCEOLOGIA_MERCATI)).equals("1")) {
		    showChecked = "checked='checked'";
		    displayMerceologie = "";
		    displayDettaglioMerceologie = "display:none;";
		    visualizzaMerceologie = true;
		} else {
		    displayMerceologie = "display:none;";
		    displayDettaglioMerceologie="";
		    showChecked = "";
		}
		//gestisce la visualizzazione per ogni singolo posteggio delle info aggiuntive (note,merceol,dimensioni posteggio,tipo posteggio...)
		if (((String) request.getAttribute(WebConstants.VISUALIZZA_ALTRE_INFORMAZIONI_MERCATI)).equals("1")) {
		    showChecked1 = "checked='checked'";
		    displayAltreinfo = "";
		    displayConcessioni = "";
		    visualizzaAltreInformazioni = true;
		} else {
		    displayAltreinfo = "display:none;";
		    displayConcessioni = "display:none;";
		    displayDettaglioMerceologie = "display:none;";
		    
		    showChecked1 = "";
		}
		//gestisce la visualizzazione del filtro dei posteggi
		if (((String) request.getAttribute(WebConstants.VISUALIZZA_FILTRO_POSTEGGI)).equals("1")) {
		    showChecked2 = "checked='checked'";
		    displayFiltri = "";
		} else {
		    displayFiltri = "display:none;";
		    showChecked2 = "";
		}
		List mercatidList = (List) request.getAttribute("mercatidList");
		pageContext.setAttribute("sizeListaPosteggi", mercatidList.size());
		pageContext.setAttribute("visualizzaAltreInformazioni", visualizzaAltreInformazioni);
		pageContext.setAttribute("visualizzaMerceologie", visualizzaMerceologie);
		boolean showPosteggiAsList = true;
		String showChecked4 = " checked='checked'";
		if (((String) request.getAttribute(WebConstants.CONF_UTENTE_MERCATI_VIS_POSTEGGILISTA)).equals("0")) {
		    showPosteggiAsList = false;
		    showChecked4 = " ";
		} 
		pageContext.setAttribute("showPosteggiAsListVal", Boolean.valueOf(showPosteggiAsList));
	%>
	<!-- END -->
	
	<!-- java script :
	     viewMerceologia(size) : gestisce la visualizzazione delle merceologie per ogni i posteggio 
	     se il  chekbox con  id=id_check1 è :
	     1- selezionato         : Mostra una tabella contenete le merceologie
	     2- se nopn selezionato : Mostra un icona attiva che al click mostra una tabella
	     
	     viewAltreinfo(size): gestisce la visulaizzazione delle info
	     se il checkbox con id=id_check2 è : 
	     1- selezionato         : Mostra info ulteriori (note,merceologie,concessionario,storico)
	     2- se nopn selezionato : Non mostra nessuna info
	     
	     selezionaAndDeselezionaTutti(size) : se:
	     1- Selezionato         : seleziona tutti i posteggi
	     2- Non selezionato		: deseleziona tutti i posteggi
	     
	     saveUserPreference() : salva le preferenze utente per la visualizzazione
	     -->
	<script type="text/javascript">
	var codP="";
	function viewMerceologia(size){
		var codicemercato=${mercati.id.codice};
		if ($('id_check1').checked) {
			doHref('salvaPreferenzaInConfigurazione.htm?nomeparametro=<%= WebConstants.VISUALIZZA_MERCEOLOGIA_MERCATI %>&codicemercato=${mercati.id.codice}&valore=1','');
		}else{
			doHref('salvaPreferenzaInConfigurazione.htm?nomeparametro=<%= WebConstants.VISUALIZZA_MERCEOLOGIA_MERCATI %>&codicemercato=${mercati.id.codice}&valore=0','');
		}
		
	}

	
	function viewAltreinfo(size){
		var codicemercato=${mercati.id.codice};
		if ($('id_check2').checked) {
			doHref('salvaPreferenzaInConfigurazione.htm?nomeparametro=<%= WebConstants.VISUALIZZA_ALTRE_INFORMAZIONI_MERCATI %>&codicemercato=${mercati.id.codice}&valore=1','');
		}else{
			doHref('salvaPreferenzaInConfigurazione.htm?nomeparametro=<%= WebConstants.VISUALIZZA_ALTRE_INFORMAZIONI_MERCATI %>&codicemercato=${mercati.id.codice}&valore=0','');
		}
		
		
	}

	function viewFiltroPosteggi(){
		if ($('id_check3').checked) {
			$('id_filtro_posteggi').appear();
		    valore=1;	
		}else{
			$('id_filtro_posteggi').fade();
			valore=0;
		}
		saveUserPreference('<%= WebConstants.VISUALIZZA_FILTRO_POSTEGGI %>',valore);
	}
	
	function selezionaAndDeselezionaTutti(size){
		
		if($('id_check_posteggi').checked){
			for(i=0;i<size;i++){
				$('posteggi_checkbox_id'+i).checked=true;
			}
		}else{
			for(i=0;i<size;i++){
				$('posteggi_checkbox_id'+i).checked=false;
			}
		}
	}
	function saveUserPreference(nomeparametro, valore,codicemercato){
		new Ajax.Request('<%=request.getContextPath()%>/mercatid/salvaPreferenza.htm',{
			  method: 'post',
			  parameters: {nomeparametro: nomeparametro,valore: valore},
			  onSuccess: function(transport){ },
			  onFailure: function(transport){ 
				var response = transport.responseText;
			    alert(response); }						    		 
		});			
	}
	
	
	function mostraComeLista(){
		var valore=0;
		if ($('id_check4').checked) {			
		    valore=1;	
		}
		doHref('salvaPreferenzaInConfigurazione.htm?nomeparametro=<%= WebConstants.CONF_UTENTE_MERCATI_VIS_POSTEGGILISTA %>&codicemercato=${mercati.id.codice}&valore='+valore,'');
	}
	
	</script>
	
	<%-- TABELLA DI OPZIONE VISUALIZZAZIONE E FILTRI --%>
	<%-- START --%>
	<table  width="60%">
	<tr>
			<td valign="top" colspan="3">	  
				 <input id="id_check4" type="checkbox" value="" <%=showChecked4%>
					name="" onclick="mostraComeLista();"></input><label for="id_check4"><fmt:message key="mercatid.label.visualizza_posteggi_come_lista"></fmt:message></label>	
			</td>
	</tr>
	<tr>
		 		<td valign="top">
    		<!-- Checkbox per le regole di visualizzazione -->
   				 <input id="id_check1" type="checkbox" value="" <%=showChecked%>
					name="" onclick="viewMerceologia(${sizeListaPosteggi});"></input><label><fmt:message key="mercatid.label.info_merceologia"></fmt:message></label>
			</td>
			<td valign="top">
				 <input id="id_check2" type="checkbox" value="" <%=showChecked1%>
					name="" onclick="viewAltreinfo(${sizeListaPosteggi});"></input><label><fmt:message key="mercatid.label.altre_info"></fmt:message></label>
			</td>
			<td valign="top">	  
				 <input id="id_check3" type="checkbox" value="" <%=showChecked2%>
					name="" onclick="viewFiltroPosteggi();"></input><label><fmt:message key="mercatid.label.filtro_posteggio"></fmt:message></label>	
			</td>

	</tr>
	</table>
    <table id=id_filtro_posteggi style="<%=displayFiltri%>;" width="100%">
        <thead class="jmesa">
        <tr>
        <td><div class="titoloSezione"><fmt:message key="mercatid.label.filtra_posteggi" />
        </div></td>
        </tr>
        </thead>
        <tbody>
    	<tr>
  			<td>
  			<table >
     			<tr>
					<td>
						<fmt:message key="label.indirizzo" />
					</td>
					<td>
						<spring-form:input id="stradario_id" path="stradario.descrizione" cssClass="searchbox" onchange="checkValue(this,'stradario_hidden')" onkeydown="javascript:return searchAll(this,event) " size="40"/>
						<init:autocompleter methodAjax="findStradario.htm" idHidden="stradario_hidden" idInput="stradario_id" inputTitleKey="label.ricerca_stradario"></init:autocompleter>
						<spring-form:hidden id="stradario_hidden" path="stradario.id.codice"  />
					</td>			
	    		</tr>
	     		<tr>
			 		<td><fmt:message key="label.note" /></td>
			 		<td><spring-form:textarea id="note_id" path="note" cols="33" rows="1" /></td>
				</tr>
				<c:if test="${fn:length(merceologieList)>0}">
				<tr>
            		<td><fmt:message key="label.filta_attivita_consentite" /></td>
            		<td><spring-form:select  id="consentita_id"  path="consentita"  >
               			<spring-form:option value=""><fmt:message key="label.tutte" /></spring-form:option>
                		<spring-form:option value="true" ><fmt:message key="label.consentite" /></spring-form:option>
                		<spring-form:option value="false"><fmt:message key="label.non_consentite" /></spring-form:option>
                		</spring-form:select>				
					</td>
        	   	</tr>
       			<tr>
            		<td><fmt:message key="label.attivita" /></td>
            		<td><spring-form:select  path="listaCodiciMerceologie" multiple="true" size="8">
						<spring-form:options id="listaCodiciMerceologie_id"  items="${merceologieList}" itemLabel="attivita.istat" itemValue="id.fkcodiceattivitaistat" />
						</spring-form:select>
					</td>
        		</tr>
        		</c:if>
     			<tr>
     				<td>
     				<div id="functions">
     				<ul>
     					<li><a href="javascript:doSubmit('list.htm?codicemercato=${mercati.id.codice}','',document.inviodati)"><fmt:message
							key="button.search" /></a>
	 					</li>
	 					<li><a href="javascript:doHref('list.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
							key="mercatid.button.search_total" /></a>
	 					</li>
	 				</ul>
    				</div>
    				</td>
     			</tr>
     			
     	</table>
        </td>
  	</tr>
  	</tbody>
  </table>
  <%-- END --%>
   
   <div class="titoloSezione">
   		<fmt:message key="mercatid.label.elenco_posteggi" />
		<input id="id_check_posteggi" type="checkbox" name="" onclick="selezionaAndDeselezionaTutti(${sizeListaPosteggi});isposteggiselezionati(${sizeListaPosteggi})" title="<fmt:message key="label.seleziona_tutto" />" ></input>
   </div>
	
<c:choose>
<c:when test="${showPosteggiAsListVal eq false}">	
	<%-- TABELLA A POSTEGGI AFFIANCATI --%>
	<%-- Tabella principale contenete ogni singolo posteggio, è fatta in modo che ogni riga contenga 4 colonne (4 posteggi) --%>	

	<table border="1"  width="100%">
		<%
		    int i = 0;
		%>
		<c:forEach items="${mercatidList}" var="posteggi" varStatus="varIndex">
		    <c:if test="${posteggi.disabilitato eq true}">
				<c:set scope="page" var="style_etichetta_posteggio" value="etichetta_posteggio_disable"></c:set>
			</c:if>
			<c:if test="${posteggi.disabilitato eq false || posteggi.disabilitato == null}">
				<c:set scope="page" var="style_etichetta_posteggio" value="etichetta_posteggio"></c:set>
			</c:if>
			<c:if test="<%=(i%4)==0%>">
			<tr>
			</c:if>
			<td  width="25%" valign="top" >
            
            <%-- Tabella secondaria (tabella che rappresenta ogni singolo posteggio) --%>
            <%-- START TABELLA SECONDARIA --%>
			<table width="100%" cellpadding="0">
				<%-- PRIMA RIGA START --%>
				<tr>
				
				<td class="codice_posteggio" style="padding: 0px;" valign="top"  ><input id="posteggi_checkbox_id${varIndex.index}" type="checkbox"
				    value="${posteggi.id.codice}" name="codiceposteggi" onclick="isposteggiselezionati(${sizeListaPosteggi});"/> 
				    <a href="javascript:void 0" onclick="doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid%2Fview.htm%3fcodice%3d${posteggi.id.codice}','')" title="<fmt:message key="label.dettaglio_posteggio" />">
				    <label class="${style_etichetta_posteggio}">${posteggi.codiceposteggio}</label> 
				    </a> 
                </td>
                <%-- Contiene le due icone delle informazioni aggiuntivi (merceologie ed altre info) --%>
                <%-- Sono due icone attive che cliccando sopra mostra le infomazioni    --%>
				
				    <%-- RIGA NOTE ICONA  --%>
				    <%-- START  --%>
				    <td width="16"  id="riga_dettagli_id${varIndex.index}" style="<%=displayAltreinfo%>;" align="right">
					<c:if test="${posteggi.note!=null}">
					<%--  class="noteColumn" (classe che mostra la S) --%>
					<label  style="font-size: 12px; font-weight:bold; cursor: pointer; " for="codiceposteggio${varIndex.index}"
							onclick="visualizzaMerceologieAndNote('posteggio_nota${varIndex.index}',event)"
							onmouseout="$('posteggio_nota${varIndex.index}').style.display='none'"
							title="<fmt:message key="label.note" />" >[N]
					</label>
				    <span id="posteggio_nota${varIndex.index}" class="posteggi_dettaglio" style="display: none; text-align: left;">
						<table class="jmesa "width="400">
							<tr class="header">
							<td><fmt:message key="label.note" /></td>
							</tr>
							<tr>
							   <td>${posteggi.note}</td>
							</tr>
					   </table>
					</span>
					</c:if> 
					<c:if test="${posteggi.note==null}">
					&nbsp;
					</c:if>
					</td>
					
					<%-- RIGA NOTE  --%>
				    <%-- END  --%>
				    
				    <%-- RIGA MERCEOLOGIE ICONA  --%>
				    <%-- START  --%>
				 
					<td width="16" id="dettagli_merceologie_id${varIndex.index}"style="<%=displayDettaglioMerceologie%>;" align="right"  >
						  <c:if test="${fn:length(posteggi.mercatiDattivitaistats)>0}">
						<%--  class="merceologiaColumn" (classe che mostra la S) --%>
						<label   style="font-size: 12px; font-weight:bold; cursor: pointer;"
								for="codiceposteggio${varIndex.index}"
								onclick="visualizzaMerceologieAndNote('posteggio_merceologia${varIndex.index}',event)"
								onmouseout="$('posteggio_merceologia${varIndex.index}').style.display='none'"
								title="<fmt:message key="label.attivita" />">[M]</label>
						<span id="posteggio_merceologia${varIndex.index}" class="posteggi_dettaglio"
							  style="display: none; text-align: left;">
				        <%-- tabella che viee mostrata quando si clicca sulla M --%>
						<%-- Mostra le merceologie configurate per quel posteggio --%>
						<%-- START --%>
						<table class="jmesa" width="400">
							<tr class="header">
								<td width="90%"><fmt:message key="label.attivita" /></td>
								<td width="10%"><fmt:message key="label.consentita" /></td>
							</tr>
							<c:forEach items="${posteggi.mercatiDattivitaistats}" var="merceologie">
							<tr>
								<td>${merceologie.attivita.istat}</td>
								<td><c:if test="${merceologie.flagConsentito eq true}">
									<fmt:message key="label.si" />
									</c:if> 
									<c:if test="${merceologie.flagConsentito eq false}">
									<fmt:message key="label.no" />
									</c:if>
							    </td>
							</tr>
			 				</c:forEach>
			 			</table>
			 			</span>
			   		</c:if>
			   		<c:if test="${fn:length(posteggi.mercatiDattivitaistats)==0}">
					&nbsp;
					</c:if>
					</td>
					
					
					<%-- RIGA MERCEOLOGIE ICONA  --%>
				    <%-- END  --%>
					</tr>
			        <%-- END PRIMA RIGA --%>
               
                <tr>
                <td >
                 	 <c:if test="${posteggi.tipoSpazio.tipospazio!=null}" >
				     	${posteggi.tipoSpazio.tipospazio}&nbsp;-&nbsp;
				     </c:if>
                     <c:if test="${posteggi.larghezza!=null && posteggi.lunghezza!=null && posteggi.superficie!=null}" >
                     	(<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.larghezza}"/><fmt:message key="label.metri" />
                     	X&nbsp;<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.lunghezza}"/><fmt:message key="label.metri" /> = 
                     	<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.superficie}"/><fmt:message key="label.metri_quadri" />)
                     </c:if>
                     <c:if test="${(posteggi.larghezza==null || posteggi.lunghezza==null) && posteggi.superficie!=null}" >
                     	(<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.superficie}"/><fmt:message key="label.metri_quadri" />)
                     </c:if>
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                </tr>
                
			
                    <%--Riga che rappresenta le concessioni attive e lo storico  --%>
                    <%-- START SECONDA RIGA --%>
				    <c:if test="${fn:length(posteggi.vwConcessioniattives)>0}">
				    <tr id="concessioni_id${varIndex.index}" style="<%=displayConcessioni%>">
				      <td colspan="3">
				        <div class="posteggi_concessionari">
	                    <div class="titoloSezionePosteggio" ><fmt:message key="label.concessionari" /></div>
	                  </td>
				    </tr>
				    <%-- RIGHE CHE RAPPRESENTANO LE CONCESSIONI ATTIVE, UNA PER OGNI USO --%>
				    <%-- START --%>
				   
				    <c:forEach items="${posteggi.vwConcessioniattives}" var="concessioni" varStatus="index_concessioni">				    
					    <c:if test="${concessioni.mercatiUso.id.codice eq param.codiceuso or param.codiceuso eq null}">
						    <tr id="concessioni_id${varIndex.index}${index_concessioni.index}" style="<%=displayConcessioni%>" >
						       <td colspan="2">
							        <b>${concessioni.mercatiUso.descrizione}:</b><br />
							        <b><fmt:message key="label.concessione" /></b>:&nbsp;${concessioni.autorizzazione.transientEstremiAut}<br />
							        <b><fmt:message key="label.concessione_titolare"/></b>: ${concessioni.titolare.descrizioneRichiedente}						  
							        <c:if test="${not empty concessioni.autorizzazione.istanza}">						
										<br />
								        <b><fmt:message key="mercatid.label.occupante"/></b>: ${concessioni.autorizzazione.istanza.titolareLegaleORichiedente.descrizioneRichiedente}
									</c:if> 							        
						  		</td>
								<!-- Link alla chiamata ajax per recuperare la lista dei subentri collegati alla concesione attiva -->
								<td align="right" valign="bottom">
									<label style="font-size: 12px;  font-weight:bold; cursor: pointer; "
										for="codiceposteggio${index_concessioni.index}${varIndex.index}"
										onclick="tabVisualizzaSubentri('dettaglioDialogDiv${concessioni.id.codice}',${concessioni.id.codice})"
										title="<fmt:message key="label.storico_subentri" />" >[S]</label>
			                    </td>
							</tr>
							<tr>
								<td colspan="3" class="titoloSezione" height="3px"></td>
							</tr>
							 <%-- Div che viene popolato alla chiamata ajax quando si ricercano i subentri sulla concesione attiva --%>
							 
						</c:if>
						<div dojoType="dijit.Dialog" id="dettaglioDialogDiv${concessioni.id.codice}" title="<fmt:message key="label.storico_subentri" />: ">
							 	<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 600px">
									<div id="dettaglio${concessioni.id.codice}"></div>
								</div>
	                     </div>
					</c:forEach>
				            
				           
				           
								
							
								 
							
						
			        </c:if>	 
				
					  
				<%-- END SECONDA RIGA --%>	
				
				<%-- Riga che rapprensenta le la lista delle merceologie di un posteggio --%>
				<%-- START TERZA RIGA --%>
				<tr id="tabellamerceologie_id${varIndex.index}"
					style="<%=displayMerceologie%>">
					<td colspan="3">
					<table class="jmesa" width="100%">
						<tr class="header">
							<td width="90%"><fmt:message key="label.attivita" /></td>
							<td width="10%"><fmt:message key="label.consentita" /></td>
						</tr>
						<c:forEach items="${posteggi.mercatiDattivitaistats}"
							var="merceologie">
							<tr>
								<td>${merceologie.attivita.istat}</td>
								<td><c:if
									test="${merceologie.flagConsentito eq true}">
									<fmt:message key="label.si" />
								</c:if> <c:if test="${merceologie.flagConsentito eq false}">
									<fmt:message key="label.no" />
								</c:if></td>
							</tr>
						</c:forEach>

					</table>
					</td>
				</tr>
				<%-- END TERZA RIGA --%>
			</table>
            <%-- END TABELLA SECONDARIA --%>
			<%
			    i++;
			%>
			</td>
			<c:if test="<%=(i%4)==0%>">
			</tr>
			<%
			    i = 0;
			%>
			</c:if>
		</c:forEach>
	</table>
	
	
<%-- FINE TABELLA A POSTEGGI AFFIANCATI --%>	
</c:when>	
<c:otherwise>	
<%-- TABELLA A POSTEGGI LISTA --%>
	<%int colspanTableInLine=4; %>
	<table width="100%" cellspacing="2" cellpadding="0" >
		<tr class="titoloSezione">
			<th width="10%"><fmt:message key="label.codice"/></th>
			<th  width="30%"><fmt:message key="label.info" /></th>
			<c:if test="${visualizzaMerceologie eq true}">
				<%colspanTableInLine++;%>
				<th><fmt:message key="label.attivita" /></th>
			</c:if>
			<c:if test="${visualizzaAltreInformazioni eq true}">	
				<%colspanTableInLine++;%>			
				<th><fmt:message key="label.concessionari" /></th>
			</c:if>
			<th width="5%"><fmt:message key="form.gestionepresenze.posteggio.occupato" /></th>
			<th>&nbsp;</th>
		</tr>	
		<%int posteggiCount=0; %>
		<%int posteggiOccupati=0; %>
		<%int posteggiDisabilitati=0; %>
		<%int posteggiAbilitati=0; %>
		<c:forEach items="${mercatidList}" var="posteggi" varStatus="varIndex">			
		<c:if test="${posteggi.disabilitato eq true}">
			<%posteggiDisabilitati++;%>
			<c:set scope="page" var="style_etichetta_posteggio" value="etichetta_posteggio_disable"></c:set>
		</c:if>
		<c:if test="${posteggi.disabilitato eq false || posteggi.disabilitato == null}">
			<%posteggiAbilitati++;%>
			<c:set scope="page" var="style_etichetta_posteggio" value="etichetta_posteggio"></c:set>
		</c:if>
		<% posteggiCount++; %>
			<tr>			
				<td style="vertical-align:top;"  ><input id="posteggi_checkbox_id${varIndex.index}" type="checkbox"
				    value="${posteggi.id.codice}" name="codiceposteggi" onclick="isposteggiselezionati(${sizeListaPosteggi});"/> 			    
					    <a 
					    	href="javascript:void 0" onclick="doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid%2Fview.htm%3fcodice%3d${posteggi.id.codice}','')" 
					    	title="<fmt:message key="label.dettaglio_posteggio" />">
					    	<label class="${style_etichetta_posteggio}">
					    		${posteggi.codiceposteggio}
					    	</label>
					    </a>			    
                </td>
                <td  style="vertical-align:top;">
					<c:if test="${posteggi.tipoSpazio.tipospazio!=null}" >
				     	${posteggi.tipoSpazio.tipospazio}&nbsp;<br />
				     </c:if>
                     <c:if test="${posteggi.larghezza!=null && posteggi.lunghezza!=null && posteggi.superficie!=null}" >
                     	(<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.larghezza}"/><fmt:message key="label.metri" />
                     	X&nbsp;<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.lunghezza}"/><fmt:message key="label.metri" /> = 
                     	<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.superficie}"/><fmt:message key="label.metri_quadri" />)
                     </c:if>
                     <c:if test="${(posteggi.larghezza==null || posteggi.lunghezza==null) && posteggi.superficie!=null}" >
                     	(<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.superficie}"/><fmt:message key="label.metri_quadri" />)
                     </c:if>                 
                </td>
                <c:if test="${visualizzaMerceologie eq true}">
	                <td  style="vertical-align:top;">
	                	<c:if test="${fn:length(posteggi.mercatiDattivitaistats)>0}">	
							<table class="jmesa" width="100%" id="tabellamerceologie_id${varIndex.index}"
							style="<%=displayMerceologie%>">
								<tr>
									<td width="95%">&nbsp;</td>
									<td width="5%" style="font-weight: bold;"><fmt:message key="label.consentita" /></td>
								</tr>
								<c:forEach items="${posteggi.mercatiDattivitaistats}" var="merceologie">
									<tr>
										<td>${merceologie.attivita.istat}</td>
										<td><c:if
											test="${merceologie.flagConsentito eq true}">
											<fmt:message key="label.si" />
										</c:if> <c:if test="${merceologie.flagConsentito eq false}">
											<fmt:message key="label.no" />
										</c:if></td>
									</tr>
								</c:forEach>	
							</table>
						</c:if>
					</td>
				</c:if>
				
				<c:if test="${visualizzaAltreInformazioni eq true}">
				 	<%boolean isoccupato = false;%>
	                <td  style="vertical-align:top;">
	                
	                <c:if test="${fn:length(posteggi.vwConcessioniattives)>0}">
	                	<table width="100%">
							    <%-- RIGHE CHE RAPPRESENTANO LE CONCESSIONI ATTIVE, UNA PER OGNI USO --%>
							    <%-- START --%>
							    <c:forEach items="${posteggi.vwConcessioniattives}" var="concessioni" varStatus="index_concessioni">								    			   
								    <c:if test="${concessioni.mercatiUso.id.codice eq param.codiceuso or param.codiceuso eq null}">
								    <%	isoccupato=true; 
								     %>
									    <tr id="concessioni_id${varIndex.index}${index_concessioni.index}" style="<%=displayConcessioni%>" >
									       <td colspan="2" style="vertical-align:top;">
										        <b>${concessioni.mercatiUso.descrizione}</b>:<br />
										        <b><fmt:message key="label.concessione" /></b>:&nbsp;${concessioni.autorizzazione.transientEstremiAut}<br />
										        <b><fmt:message key="label.concessione_titolare"/></b>: &nbsp;${concessioni.titolare.descrizioneRichiedente}										        
										        <c:if test="${not empty concessioni.autorizzazione.istanza}">						
													<br />
											        <b><fmt:message key="mercatid.label.occupante"/></b>: ${concessioni.autorizzazione.istanza.titolareLegaleORichiedente.descrizioneRichiedente}
												</c:if>
									  		</td>
											<%-- Link alla chiamata ajax per recuperare la lista dei subentri collegati alla concesione attiva --%>
											<td align="right" style="vertical-align:top;">
												<label style="font-size: 12px;  font-weight:bold; cursor: pointer; "
													for="codiceposteggio${index_concessioni.index}${varIndex.index}"
													onclick="tabVisualizzaSubentri('dettaglioDialogDiv${concessioni.id.codice}',${concessioni.id.codice})"
													title="<fmt:message key="label.storico_subentri" />" >[S]</label>
													<div dojoType="dijit.Dialog" id="dettaglioDialogDiv${concessioni.id.codice}" title="<fmt:message key="label.storico_subentri" />: ">
														 	<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 600px">
																<div id="dettaglio${concessioni.id.codice}"></div>
															</div>
								                     </div>
						                    </td>
										</tr>
										<tr>
											<td colspan="3"><div style="height: 1px;border-bottom: 1px solid #000;"/></td>
										</tr>
										 <%-- Div che viene popolato alla chiamata ajax quando si ricercano i subentri sulla concesione attiva --%>									 
									</c:if>
									
								</c:forEach>
								<%if(isoccupato){posteggiOccupati++;} %>
							</table>		
				        </c:if>	
	                </td>	      
	                <td  style="vertical-align:top;text-transform: uppercase;">
						<%if(isoccupato){%>
						    <b style="color: #00CC00;"><fmt:message key="label.si" /></b>
						<%}else{ %>
							<b style="color: #FF0000;"><fmt:message key="label.no" /></b>
						<%}%>
					</td>	
	                          
                </c:if>
                <c:if test="${visualizzaAltreInformazioni eq false}">
				
							<%-- Conteggio dei concessionari nel caso che altre informazioni sia disabilitato --%>
							<%boolean isoccupato = false;%>
							    <c:forEach items="${posteggi.vwConcessioniattives}" var="concessioni" varStatus="index_concessioni">								    			   
								    <c:if test="${concessioni.mercatiUso.id.codice eq param.codiceuso or param.codiceuso eq null}">
								    <%	isoccupato=true; %>
								     </c:if>								       
								</c:forEach>
							
							<td  style="vertical-align:top;text-transform: uppercase;">
								<%if(isoccupato){
								    	posteggiOccupati++; %>
								    <b style="color: #00CC00;"><fmt:message key="label.si" /></b>
								<%}else{ %>
									<b style="color: #FF0000;"><fmt:message key="label.no" /></b>
								<%}%>
							</td>	
				</c:if>
                <td  style="vertical-align:top;"><%-- AZIONI --%>        
                
                	<%-- START MERCEOLOGIE [M] --%>
                	<c:if test="${visualizzaMerceologie eq false}">
	                	<c:if test="${fn:length(posteggi.mercatiDattivitaistats)>0}">	
							<label 
								id="mercPosteggio${posteggi.id.codice}"
								title="<fmt:message key="label.attivita" />"><b>[M]</b></label>
							<div data-dojo-type="dijit.Tooltip" data-dojo-props="connectId:'mercPosteggio${posteggi.id.codice}',position:['before']" style="display: none;">
								<c:forEach items="${posteggi.mercatiDattivitaistats}" var="merceologie">
									${merceologie.attivita.istat}
									<c:if test="${merceologie.flagConsentito eq true}">
										<fmt:message key="label.consentite" />
									</c:if> 
									<c:if test="${merceologie.flagConsentito eq false}">
										<fmt:message key="label.non_consentite" />
									</c:if>
									<br />
					 			</c:forEach>
							</div>			
			   			</c:if>
			   		</c:if>
			   		<%-- END MERCEOLOGIE [M] --%>
			   		<%-- START NOTE [N] --%>
			   		<c:if test="${posteggi.note!=null}">
						<%--  class="noteColumn" (classe che mostra la S) --%>
						<label  id="notePosteggio${posteggi.id.codice}"
								title="<fmt:message key="label.note" />" ><b>[N]</b>
						</label>
						<div data-dojo-type="dijit.Tooltip" data-dojo-props="connectId:'notePosteggio${posteggi.id.codice}',position:['before']"  style="display: none;">
						    <pre>${posteggi.note}</pre>
						</div>
					</c:if>
					<%-- END NOTE [N] --%> 		
                </td>
			</tr>
			<tr>
				<td colspan="<%= colspanTableInLine%>"><div style="height: 1px;border-bottom: 1px dotted #000;" /></td> 
			</tr>				
		</c:forEach>
	</table>
	
    <fieldset>
    <legend><b><fmt:message key="label.riepilogo"/></b></legend>
	<div class="parametriDiv">
		<div class="etichetta">
		     <div>
	        	<fmt:message key="label.totale_posteggi" />:
	        </div>
	        <div>
	        	<fmt:message key="label.totale_attivi" />:
	        </div>
		    <div>
	        	<fmt:message key="label.totale_disabilitati" />:
	        </div>
			
	      	<div>
		      	<fmt:message key="label.posteggi_occupati" />:
	        </div>
	        <div>
	        	<fmt:message key="label.posteggi_liberi" />:
	        </div>
	    </div>        
	    <div class="parametro">
	        <div>
	             <%= (posteggiCount) %>
	        </div>
	        <div>
	             <%= (posteggiAbilitati) %>
	        </div>
	        <div>
	             <%= (posteggiDisabilitati) %>
	        </div>
	      	<div>
	             <%= posteggiOccupati %>
	        </div>
	        <div>
	             <%= (posteggiCount-posteggiOccupati) %>
	        </div>
        </div>
	</div>	
	</fieldset>
	<br class="clear" />
	
	
<%-- FINE TABELLA A POSTEGGI LISTA --%>	
	
</c:otherwise>

</c:choose>	
	
	
	
    </spring-form:form></div>
    <div id="functions">


	<script type="text/javascript">
		<%
		String displayButtonModifica = "display:none;";
		String displayButtonElimina= "display:none;";
		String displayButtonAddMerceologie="display:none;";
		%>
	
		function isposteggiselezionati(size){
			var count=0
			for(i=0;i<size;i++){
				if($('posteggi_checkbox_id'+i).checked)
				{
					if(codP.indexOf($('posteggi_checkbox_id'+i).value)<0){
					 codP=codP+$('posteggi_checkbox_id'+i).value+",";
				    }
					count++;
				}else{
					codP=codP.replace($('posteggi_checkbox_id'+i).value+",","");
				}
			}
			if(count>0)
			{
				
				$('modifica_button').appear();
			    $('elimina_button').appear();
			    $('addMerceologie_button').appear();
			}else
			{
				$('modifica_button').fade();
			    $('elimina_button').fade();
			    $('addMerceologie_button').fade();
			}
	    }
	    <%--
	    function visualizzaSubentri1(id,e)
	     {
	    	 var mouseX = e.clientX + getViewportScrollX();
	  		 var screenW = screen.width; 
	         $(id).appear();
	         if(mouseX+400>screenW)
	  		 {
	  	  		 var tableW=$(id).style.width;
	  			 $(id).style.marginLeft = "-520px";
	  		 }
	         $(id).style.marginTop = "20px";
	         
	     }
	     --%>
	     
	     
	    function tabVisualizzaSubentri(divId, id){
				dijit.byId(divId).show();
				visualizzaSubentri(id);		
		}
		
		
		function visualizzaSubentri(id) {	
				new Ajax.Request(
						'${pageContext.request.contextPath}/mercatid/ajaxStoricoSubentri.htm?codiceconcessione='+ id,
						{
							method : 'post',
							onSuccess : function(transport) {							
								var response = transport.responseText;							
								$("dettaglio" + id).innerHTML = response;
								//$("dettaglio" + inventario.value).appear();							
							},
							onFailure : function(transport) {
								var response = transport.responseText;
								alert(response);
							}
						});
			}
	    function visualizzaMerceologieAndNote(id,e)
	     {
	    	 var mouseX = e.clientX + getViewportScrollX();
	  		 var screenW = screen.width; 
	         $(id).appear();
	         
	        
	         if(mouseX+570>screenW)
	  		 {
	  	  		 var tableW=$(id).style.width;
	  			 $(id).style.marginLeft = "-415px";
	  		 }
	         $(id).style.marginTop = "20px";
	         
	     }
	
	    
	 	
	 	function getViewportScrollX() {
	 		var scrollX = 0;
	 		if( document.documentElement && document.documentElement.scrollLeft ) { //IE standards compliant and W3C 
	 		scrollX = document.documentElement.scrollLeft;
	 		}
	 		else if( document.body && document.body.scrollLeft ) { // IE 6 not standards compliant
	 		scrollX = document.body.scrollLeft;
	 		}
	 		else if( window.pageXOffset ) { // older browsers
	 		scrollX = window.pageXOffset;
	 		}
	 		else if( window.scrollX ) { // Gecko and KHTML/Webkit browsers I think...
	 		scrollX = window.scrollX;
	 		}
	 		return scrollX;
	 	}
	
	</script>

    <script type="text/javascript">
      var goToUrlCreateMerceologia= "../mercatid/createMerceologia.htm?codiceposteggio=${null}&codiceposteggi=";
      goToUrlCreateMerceologia = escape(goToUrlCreateMerceologia);
   </script>



<ul>
	<li><a href="javascript:doHref('create.htm?codicemercato=${mercati.id.codice}','');"><fmt:message key="button.new" /></a></li>
	<li id="modifica_button" style="<%=displayButtonModifica%>"><a href="javascript:doSubmit('dettaglioPosteggi.htm?codicemercato=${mercati.id.codice}','',document.inviodati)"><fmt:message key="mercatid.button.changeInfo" /></a></li>
	<%-- 
	<li id="addMerceologie_button" style="<%=displayButtonAddMerceologie%>"><a href="javascript:doSubmit('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlCreateMerceologia+codP,'',document.inviodati);"><fmt:message key="mercatid.button.addMerceologia" /></a></li>
	--%>
	<li id="elimina_button" style="<%=displayButtonElimina%>" ><a href="javascript:doSubmit('deletePosteggi.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
		key="button.delete" /></a></li>
    <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>