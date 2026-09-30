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
	
		
	
	
	
	
	<div class="clear"></div>	
	<spring-form:form commandName="filtro" name="inviodati">
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
		List mercatidList = (List) request.getAttribute("_mercatidList");
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

			<div id="form" class="vbg-form">
				<fieldset class="collassabile">
					<legend>

						<a href="/backend/mercati/view.htm?codice=${mercati.id.codice}">${mercati.descrizione}</a><a
							href="/backend/mercatid/list.htm?codicemercato=${mercati.id.codice}">(
							Visualizza posteggi )</a>
					</legend>

					<div class="form-group">
						<input id="id_check2" type="checkbox" value="" <%=showChecked1%>
							name="" onclick="viewAltreinfo(${sizeListaPosteggi});"></input> <span
							style="position: relative; top: -4px;">Mostra informazioni
							aggiuntive sui posteggi</span>
					</div>
					<div class="form-group">
						<input id="id_check3" type="checkbox" value="" <%=showChecked2%>
							name="" onclick="viewFiltroPosteggi();"></input> <span
							style="position: relative; top: -4px;">Mostra filtri per
							posteggi</span>
					</div>
				</fieldset>
			</div>
			
			<div id="id_filtro_posteggi" class="vbg-form">
			<fieldset class="collassabile">
			<legend>Filtra i posteggi</legend>
			<div class="form-group">
			<label><fmt:message key="label.mercati_uso" /></label>
			<jsp:include page="../includes/autocompletergenerico.jsp" >
						<jsp:param name="idElemento" value="mercatiUsoTransient" />		
						<jsp:param name="propertyPath" value="mercatiUsoTransient" />				
						<jsp:param name="pathPropertyDescription" value="mercatiUsoTransient.descrizione" />
						<jsp:param name="pathPropertyCode" value="mercatiUsoTransient.id.codice" />
						<jsp:param name="autocompleterAjax" value="findMercatiUsoAndMercato.htm?codiceMercato=${mercati.id.codice}" />
						<jsp:param name="titleKey" value="label.giorno" />
					</jsp:include>	
			</div>
			<div class="form-group">
			<label><fmt:message key="label.attivita" /></label>
			<jsp:include page="../includes/autocompletergenerico.jsp" >
						<jsp:param name="idElemento" value="attivitaTransient" />		
						<jsp:param name="propertyPath" value="attivitaTransient" />				
						<jsp:param name="pathPropertyDescription" value="attivitaTransient.istat" />
						<jsp:param name="pathPropertyCode" value="attivitaTransient.id.codiceistat" />
						<jsp:param name="autocompleterAjax" value="findAttivita.htm?codicesettore=" />
						<jsp:param name="titleKey" value="label.attivita" />
					</jsp:include>
					<label style="margin-left: 20px;"><fmt:message key="label.ammessa" /></label>
					<spring-form:select path="attivataAmmessaNonAmmessaTransient">
  			    			<spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
  			    			<spring-form:option value="1"><fmt:message key="label.si" /></spring-form:option>
  			    			<spring-form:option value="0"><fmt:message key="label.no" /></spring-form:option>
  			    		</spring-form:select>		
			</div>
			<div class="form-group">
			<label><fmt:message key="label.indirizzo" /></label>
			<spring-form:input id="stradario_id" path="stradario.descrizione" cssClass="searchbox" onchange="checkValue(this,'stradario_hidden')" onkeydown="javascript:return searchAll(this,event) " size="40"/>
						<init:autocompleter methodAjax="findStradarioMercato.htm?codicemercato=${mercati.id.codice}&searchDisabilitati=false" idHidden="stradario_hidden" idInput="stradario_id" inputTitleKey="label.ricerca_stradario"></init:autocompleter>
						<spring-form:hidden id="stradario_hidden" path="stradario.id.codice"  />	
			</div>
			
			<div class="form-group">
			<label><fmt:message key="label.note" /></label>
			<spring-form:textarea id="note_id" path="note" cols="33" rows="1" />
			</div>
			
			<spring-form:hidden path="mercati.id.codice" />
			<input type="hidden" value="${mercati.id.codice}" name="filtro.mercati.id.codice"></input>
			
			<div class="form-group" id="functions">
     				<ul>
     					<li><a href="javascript:doSubmit('list.htm?codicemercato=${mercati.id.codice}','',document.inviodati)"><fmt:message
							key="button.search" /></a>
	 					</li>
	 					<li><a href="javascript:exportPosteggi();"><fmt:message key="button.esporta" /></a></li>
	 					</li>
	 					<li><a href="javascript:doHref('list.htm?codicemercato=${mercati.id.codice}','')"><fmt:message
							key="mercatid.button.search_total" /></a>
	 					</li>
	 				</ul>
    				</div>
			
			
			
		</fieldset>	
        </div>
  
  
  <%-- END --%>
   
	
<%-- TABELLA A POSTEGGI LISTA --%>
	<%int colspanTableInLine=4; %>
	<div id="id_lista_tabella_posteggi" class="vbg-form">
	<fieldset>
	<legend><fmt:message key="mercatid.label.elenco_posteggi" />
		<input id="id_check_posteggi" type="checkbox" name="" onclick="selezionaAndDeselezionaTutti(${sizeListaPosteggi});isposteggiselezionati(${sizeListaPosteggi})" title="<fmt:message key="label.seleziona_tutto" />" ></input></legend>
	<div class="icon-container" style="display: flex;gap: 15px;justify-content: center;">
		<div class="icona-testo" style="display: flex;flex-direction: column;align-items: center;gap: 5px;">
			<i class="${CONF_LISTPOSTEGGI_VISDETTAGLI eq '1' ? 'fas fa-eye' : 'fas fa-eye-slash' }" onclick="visualizzaByClasse(this, '.divdettagli', 'LISTPOSTEGGI_VISDETTAGLI')"></i><span>Dettagli</span>
		</div>
		<c:if test="${visualizzaAltreInformazioni eq true}">
		<div class="icona-testo" style="display: flex;flex-direction: column;align-items: center;gap: 5px;">
			<i class="${CONF_LISTPOSTEGGI_VISCONCESSIONARI eq '1' ? 'fas fa-eye' : 'fas fa-eye-slash' }" onclick="visualizzaByClasse(this, '.divconcessionari', 'LISTPOSTEGGI_VISCONCESSIONARI')"></i>
			<span>Concessionario</span>
		</div>
		</c:if>
		<div class="icona-testo" style="display: flex;flex-direction: column;align-items: center;gap: 5px;">
			<i class="${CONF_LISTPOSTEGGI_VISMERCEOLOGIE eq '1' ? 'fas fa-eye' : 'fas fa-eye-slash' }" onclick="visualizzaByClasse(this, '.merceologia-posteggio', 'LISTPOSTEGGI_VISMERCEOLOGIE')"></i>
			<span>Merceologie</span>
		</div>
		<div class="icona-testo" style="display: flex;flex-direction: column;align-items: center;gap: 5px;">
			<i class="${CONF_LISTPOSTEGGI_VISLVS eq '1' ? 'fas fa-eye' : 'fas fa-eye-slash' }" onclick="visualizzaByClasse(this,'.livelliserviziodettagli', 'LISTPOSTEGGI_VISLVS')"></i>
			<span>Livelli di servizio</span>
		</div>
	</div>
	<table width="100%" class="vbg-table">
	    <thead>
		<tr >
			<th width="10%"><fmt:message key="label.codice"/></th>
			<th  width="10%"><fmt:message key="label.info" /></th>
			<c:if test="${visualizzaAltreInformazioni eq true}">	
				<%colspanTableInLine++;%>			
				<th><fmt:message key="label.concessionari" /></th>
			</c:if>
			<th width="5%"><fmt:message key="form.gestionepresenze.posteggio.occupato" /></th>
			<th>&nbsp;</th>
		</tr>
		</thead>	
		<%int posteggiCount=0; %>
		<%int posteggiOccupati=0; %>
		<%int posteggiDisabilitati=0; %>
		<%int posteggiAbilitati=0; %>
		<tbody>
		<c:forEach items="${_mercatidList}" var="posteggi" varStatus="varIndex">			
		<c:if test="${posteggi.posteggioInfoHelper.disabilitato eq true}">
			<%posteggiDisabilitati++;%>
			<c:set scope="page" var="style_etichetta_posteggio" value="etichetta_posteggio_disable"></c:set>
		</c:if>
		<c:if test="${posteggi.posteggioInfoHelper.disabilitato eq false || posteggi.posteggioInfoHelper.disabilitato == null}">
			<%posteggiAbilitati++;%>
			<c:set scope="page" var="style_etichetta_posteggio" value="etichetta_posteggio"></c:set>
		</c:if>
		<% posteggiCount++; %>
			<tr>			
				<td style="vertical-align:top;"  ><input id="posteggi_checkbox_id${varIndex.index}" type="checkbox"
				    value="${posteggi.posteggioInfoHelper.id.codice}" name="codiceposteggi" onclick="isposteggiselezionati(${sizeListaPosteggi});"/> 			    
					    <a 
					    	href="javascript:void 0" onclick="doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid%2Fview.htm%3fcodice%3d${posteggi.posteggioInfoHelper.id.codice}','')" 
					    	title="<fmt:message key="label.dettaglio_posteggio" />">
					    	<label class="${style_etichetta_posteggio}">
					    		${posteggi.posteggioInfoHelper.codiceposteggio}
					    	</label>
					    </a>			    
                </td>
                <td  style="vertical-align:top;">
                    <div class="divdettagli" style="${CONF_LISTPOSTEGGI_VISDETTAGLI eq '1' ? '' : 'display:none;' }">
					<c:if test="${posteggi.posteggioInfoHelper.tipospazio ne ''}" >
				     	${posteggi.posteggioInfoHelper.tipospazio}&nbsp;<br />
				     </c:if>
                     <c:if test="${posteggi.posteggioInfoHelper.larghezza!=null && posteggi.posteggioInfoHelper.lunghezza!=null && posteggi.posteggioInfoHelper.superficie!=null}" >
                     	(<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.posteggioInfoHelper.lunghezza}"/><fmt:message key="label.metri" />
                     	X&nbsp;<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.posteggioInfoHelper.larghezza}"/><fmt:message key="label.metri" /> = 
                     	<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.posteggioInfoHelper.superficie}"/><fmt:message key="label.metri_quadri" />)
                     </c:if>
                     <c:if test="${(posteggi.posteggioInfoHelper.larghezza==null || posteggi.posteggioInfoHelper.lunghezza==null) && posteggi.posteggioInfoHelper.superficie!=null}" >
                     	(<fmt:formatNumber pattern="<%= WebConstants.NUMBER_FORMAT_PATTERN %>" value="${posteggi.posteggioInfoHelper.superficie}"/><fmt:message key="label.metri_quadri" />)
                     </c:if>  
                     <c:if test="${not empty posteggi.posteggioInfoHelper.settoreposteggio}" >
				     	<fmt:message key="label.posteggio_settore" />: ${posteggi.posteggioInfoHelper.settoreposteggio}&nbsp;<br />
				     </c:if>
				     </div>    
                     <div style="${CONF_LISTPOSTEGGI_VISMERCEOLOGIE eq '1' ? '' : 'display:none;' }" class="merceologia-posteggio" data-idposteggio="${posteggi.posteggioInfoHelper.id.codice}"></div>
                     <c:if test="${not empty livelliServizioMap[posteggi.posteggioInfoHelper.id.codice]}">
                          
                          
                          
                          <table class="vbg-table livelliserviziodettagli" style="width:500px;${CONF_LISTPOSTEGGI_VISLVS eq '1' ? '' : 'display:none;' }">
                             
                             <tbody>
                             <tr><th style="border: 1px solid;">Livelli di servizio</th></tr>
                             <c:forEach items="${livelliServizioMap[posteggi.posteggioInfoHelper.id.codice]}" var="livelliservizio" varStatus="lvsIndex">
                             <tr>
                             <td style="border: 1px solid;">
                             <ul style="list-style-type:none; margin-left: 0;padding-left: 0;margin-top: 0;margin-bottom: 0;">
                             <li>
                             
                             ${livelliservizio.descrizione}
                             
                             <c:choose>
                                 <c:when test="${not empty livelliservizio.dataInizio and not empty livelliservizio.dataFine}">
                                    (dal <fmt:formatDate value="${livelliservizio.dataInizio}" pattern="dd/MM/yyyy" /> al <fmt:formatDate value="${livelliservizio.dataFine}" pattern="dd/MM/yyyy" />)
                                 </c:when>
    
                                 <c:when test="${not empty livelliservizio.dataInizio}">
                                   (dal <fmt:formatDate value="${livelliservizio.dataInizio}" pattern="dd/MM/yyyy" />)
                                 </c:when>
                                 
                                 <c:when test="${not empty livelliservizio.dataFine}">
                                   (fino al <fmt:formatDate value="${livelliservizio.dataFine}" pattern="dd/MM/yyyy" />)
                                 </c:when>
                                 <c:otherwise>
                                 </c:otherwise>
                             </c:choose>
                             
                             </li>
                             <c:if test="${not empty livelliservizio.usaMqPosteggio and livelliservizio.usaMqPosteggio}">
                              <li style="margin-top:2px;">Usa mq del posteggio</li>
                             </c:if>
                             <li style="margin-top:2px;">Fattore moltiplicativo: <fmt:formatNumber value="${livelliservizio.fattoreMoltiplicativo}" type="number" minFractionDigits="2" maxFractionDigits="2" /></li>
                             <li style="margin-top:2px;">Tariffa: <fmt:formatNumber value="${livelliservizio.tariffa}" type="number" minFractionDigits="2" maxFractionDigits="2" /></li> 
                             <c:if test="${not empty livelliservizio.mercatiUso}">
                              <li style="margin-top:2px;">Giornata: ${livelliservizio.mercatiUso.descrizione}</li>
                             </c:if>
                             </ul>
                             </td>
                             </tr>
                             </c:forEach>
                             </tbody>
                           
                          </table>

                          
                     </c:if>           
                </td>
               
				<c:if test="${visualizzaAltreInformazioni eq true}">
				 	<%boolean isoccupato = false;%>
	                <td style="vertical-align:top;">
	                
	                <c:if test="${fn:length(posteggi.istanzeConcessioniMercatoHelpers)>0}">
	                	<table class="divconcessionari" style="${CONF_LISTPOSTEGGI_VISCONCESSIONARI eq '1' ? '' : 'display:none;' }"  width="100%">
							    <%-- RIGHE CHE RAPPRESENTANO LE CONCESSIONI ATTIVE, UNA PER OGNI USO --%>
							    <%-- START --%>
							     	<%
			    						int i=0;
			    					%>
			    					<%
			    						int j=i+1;
			    					%>
							    <c:forEach items="${posteggi.istanzeConcessioniMercatoHelpers}" var="concessioni" varStatus="index_concessioni">								    			   
								   
								    <c:if test="${concessioni.codiceuso eq param.codiceuso or param.codiceuso eq null}">
								    
								    <%	isoccupato=true; 
								     %>
								        
									    <tr id="concessioni_id${varIndex.index}${index_concessioni.index}" style="<%=displayConcessioni%>" >
									       <td colspan="2" style="vertical-align:top;">
										        <b>${concessioni.usoConcessione}</b>:<br />
										        <b><fmt:message key="label.concessione" /></b>:&nbsp;
										        <a href="javascript:historySet('${_urlback}','..%2Fautorizzazioni/viewConcessione.htm?codiceIstanza=${concessioni.codiceistanza}&codiceAutorizzazione=${concessioni.codiceconcessione}','')"><b>${concessioni.numeroConcessione}</b></a>
										        <c:if test="${concessioni.comuneConcessione ne ''}">, ${concessioni.comuneConcessione}</c:if>
							                    <c:if test="${concessioni.registroConcessione ne ''}">, ${concessioni.registroConcessione}</c:if><br />
							                  	<c:forEach items="${autorizzazioniConcessioni }" var="autConc" varStatus="index_autconcessioni">
										        	 <c:if test="${concessioni.codiceistanza eq autConc.autorizzazioniByFkAutconcAutatt.istanza.id.codice and posteggi.posteggioInfoHelper.id.codice eq autConc.mercatiD.id.codice}">
										        	 	<b><i class="fa fa-lg fa-link"></i>&nbsp;<fmt:message key="label.autorizzazione_collegata" />:&nbsp;</b>
										        	 	<b><a href="javascript:historySet('${_urlback}','..%2Fautorizzazioni/viewAutorizzazione.htm?codice=${ autConc.autorizzazioniByFkAutconcAutcoll.id.codice}','')">${ autConc.autorizzazioniByFkAutconcAutcoll.autoriznumero}</a></b>&nbsp;,
										        	 	${autConc.autorizzazioniByFkAutconcAutcoll.autorizcomune.comune }&nbsp;,
										        	 	${autConc.autorizzazioniByFkAutconcAutcoll.tipologiaregistro.trDescrizione }
										        	 	</br>
										        	 </c:if>
							        
							        			</c:forEach>
							                    
										        <b><fmt:message key="label.concessione_titolare"/></b>: &nbsp;${concessioni.titolare}									        
													<br />
											        <b><fmt:message key="mercatid.label.occupante"/></b>: ${concessioni.occupante}
									  		</td>
											<%-- Link alla chiamata ajax per recuperare la lista dei subentri collegati alla concesione attiva --%>
											<%-- id duplicato --%>
											<td align="right" style="vertical-align:top;">
												<label style="font-size: 12px;  font-weight:bold; cursor: pointer; "
													for="codiceposteggio${index_concessioni.index}${varIndex.index}"
													onclick="tabVisualizzaSubentri('dettaglioDialogDiv${concessioni.codiceconcessione}_<%=i%>_<%=j%>_${posteggi.posteggioInfoHelper.id.codice}',${concessioni.codiceconcessione})"
													title="<fmt:message key="label.storico_subentri" />" >[S]</label>

						                    </td>
										</tr>
										<tr>
											<td colspan="3"><div style="height: 1px;border-bottom: 1px solid #000;"/></td>
										</tr>
										 <%-- Div che viene popolato alla chiamata ajax quando si ricercano i subentri sulla concesione attiva --%>									 
									</c:if>
									<%i++;%>
									
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
							    <c:forEach items="${posteggi.istanzeConcessioniMercatoHelpers}" var="concessioni" varStatus="index_concessioni">								    			   
								    <c:if test="${concessioni.codiceuso eq param.codiceuso or param.codiceuso eq null}">
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
                
                	
			   		<%-- END MERCEOLOGIE [M] --%>
			   		<%-- START NOTE [N] --%>
			   		<c:if test="${posteggi.posteggioInfoHelper.note!=null}">
						<%--  class="noteColumn" (classe che mostra la S) --%>
						<label  id="notePosteggio${posteggi.posteggioInfoHelper.id.codice}"
								title="<fmt:message key="label.note" />" ><b>[N]</b>
						</label>
						<div data-dojo-type="dijit.Tooltip" data-dojo-props="connectId:'notePosteggio${posteggi.posteggioInfoHelper.id.codice}',position:['before']"  style="display: none;">
						    <pre>${posteggi.posteggioInfoHelper.note}</pre>
						</div>
					</c:if>
					<%-- END NOTE [N] --%> 		
                </td>
			</tr>
						
		</c:forEach>
		</tbody>
	</table>
	</fieldset>
	</div>
	
	<div class="vbg-form">
    <fieldset>
    <legend><fmt:message key="label.riepilogo"/></legend>
	<div class="parametriDiv">
	
	    <div class="form-group">
					<label><fmt:message key="label.totale_posteggi" /></label>
					<span class='readonly-form-control'><%= (posteggiCount) %></span>
		</div>
		
		<div class="form-group">
					<label><fmt:message key="label.totale_attivi" /></label>
					<span class='readonly-form-control'><%= (posteggiAbilitati) %></span>
		</div>
		
		<div class="form-group">
					<label><fmt:message key="label.totale_disabilitati" /></label>
					<span class='readonly-form-control'><%= (posteggiDisabilitati) %></span>
		</div>
		
		<div class="form-group">
					<label><fmt:message key="label.posteggi_occupati" /></label>
					<span class='readonly-form-control'><%= posteggiOccupati %></span>
		</div>
		
		<div class="form-group">
					<label><fmt:message key="label.posteggi_liberi" /></label>
					<span class='readonly-form-control'><%= (posteggiCount-posteggiOccupati) %></span>
		</div>
	
 
	</div>	
	</fieldset>
	</div>
	<br class="clear" />
	
	
<%-- FINE TABELLA A POSTEGGI LISTA --%>	
	

	
<vbg-modal id="modal_dettaglio_subentri" class="vbg-form">
	<div slot='body' id="modal_dettaglio_subentri-body" >

	</div>
</vbg-modal>
	
    </spring-form:form>
    
    
    </div>
    

	<script type="text/javascript">
		<%
		String displayButtonModifica = "display:none;";
		String displayButtonElimina= "display:none;";
		String displayButtonAddMerceologie="display:none;";
		String displayButtonCreaComunicazioni= "display:none;";
		String displayButtonLivelliServizio= "display:none;";
		%>
		
		let modalDettaglioSubentri = document.querySelector('#modal_dettaglio_subentri');
		let modBoby = document.querySelector('#modal_dettaglio_subentri-body');
		
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
			    //$('addMerceologie_button').appear();
			    $('comunicazioni_button').appear();
			    $('livelliservizio_button').appear();
			    
			}else
			{
				$('modifica_button').fade();
			    $('elimina_button').fade();
			    //$('addMerceologie_button').fade();
			    $('comunicazioni_button').fade();
			    $('livelliservizio_button').fade();
			}
	    }
	    
	    async function tabVisualizzaSubentri(divId, id){
	    	
	    	
	    	visualizzaSubentriModal(id);
		}
	    
	    async function visualizzaSubentriModal(id) {	
			
	    	window.vbg.nascondiModalCaricamento();
	    	
	    	let response = await fetch('${pageContext.request.contextPath}/mercatid/ajaxStoricoSubentri.htm?codiceconcessione='+ id);
	    	
			risposta = await response.text();
			
			window.vbg.nascondiModalCaricamento();
			
			if (response.status !== 200) {				
				alert(risposta);
				return;
			}
			
			modBoby.innerHTML = risposta;
			modalDettaglioSubentri.open();
			
			
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
	 	
	 	
	 jQuery(function() {
	 	
		jQuery(".merceologia-posteggio").each(function () {
			
			var elemento = jQuery(this);
			var idposteggio = jQuery(this).data('idposteggio');
					
			var jhqrPr = jQuery.ajax({
				  url: '${pageContext.request.contextPath}/mercatid/ajaxDettaglioMerceologiePosteggio.htm',
				  method: "POST",
				  context: document.body,			  
				  cache: false,					  
				  dataType: "html",
				  data: 'idposteggio='+idposteggio,
				  success: function(data, textStatus, jqXHR){
					  jQuery(elemento).html(data);
				  },
				  error: function(jqXHR, textStatus, errorThrown){
				  }
					  
			});
			
		});
		
	 });
	
      var goToUrlCreateMerceologia= "../mercatid/createMerceologia.htm?codiceposteggio=${null}&codiceposteggi=";
      goToUrlCreateMerceologia = escape(goToUrlCreateMerceologia);
      
      
  	
	
	function exportPosteggi(){		
		goToExportPentahoPanel();
	}
	
	function goToExportPentahoPanel(){
		
		var url  = URLDecode('${_urlback}');			
		ajaxHistorySet(url);			
		setTimeout("doSubmit('../mercatid/inizializzaExportModalitaPentaho.htm','',document.inviodati)",10);
	}
	
	
	function dettaglioPosteggi(){
		
		var url  = URLDecode('${_urlback}');			
		ajaxHistorySet(url);			
		setTimeout("doSubmit('../mercatid/dettaglioPosteggi.htm?codicemercato=${mercati.id.codice}','',document.inviodati)",10);
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
    
	
	function visualizzaByClasse(icona, classe, nomeparametro){
		
		let dettagliList = document.querySelectorAll(classe);
		
		if(icona.classList.contains('fa-eye-slash')){
			for (const elem of dettagliList) {
		        elem.style.display = '';
		    }
			icona.classList.remove('fa-eye-slash');
			icona.classList.add('fa-eye');
			console.log('Sto salvando il parametro ' + nomeparametro + ' col valore 1');
			saveUserPreference(nomeparametro, "1");
			console.log('Ho salvato il parametro ' + nomeparametro + ' col valore 1');
		}else{
			for (const elem of dettagliList) {
		        elem.style.display = 'none';
		    }
			icona.classList.remove('fa-eye');
			icona.classList.add('fa-eye-slash');
			console.log('Sto salvando il parametro ' + nomeparametro + ' col valore 0');
			saveUserPreference(nomeparametro, "0");
			console.log('Ho salvato il parametro ' + nomeparametro + ' col valore 0');
		}
		
		
	}
      
   </script>


<div id="functions">

<ul>
	<li><a href="javascript:doHref('create.htm?codicemercato=${mercati.id.codice}','');"><fmt:message key="button.new" /></a></li>
	<li id="livelliservizio_button" style="<%=displayButtonLivelliServizio%>"><a class="btn btn-primary" href="javascript:doSubmit('../mercatidlivelloservizio/createM.htm','',document.inviodati)"><fmt:message key="button.livelli_servizio" /></a></li>
	<%--
	<li id="modifica_button" style="<%=displayButtonModifica%>"><a href="javascript:doSubmit('dettaglioPosteggi.htm?codicemercato=${mercati.id.codice}','',document.inviodati)"><fmt:message key="mercatid.button.changeInfo" /></a></li>
	 --%>
	
	<li id="modifica_button" style="<%=displayButtonModifica%>"><a href="javascript:dettaglioPosteggi();"><fmt:message key="mercatid.button.changeInfo" /></a></li>

	<%-- 
	<li id="addMerceologie_button" style="<%=displayButtonAddMerceologie%>"><a href="javascript:doSubmit('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrlCreateMerceologia+codP,'',document.inviodati);"><fmt:message key="mercatid.button.addMerceologia" /></a></li>
	--%>
	<c:if test="${isExistComunicazione}">
		<li id="lista_comunicazioni_button" ><a href="javascript:historySet('${_urlback}','../comunicazionitmercato/list.htm?codicemercato=${mercati.id.codice}','');"><fmt:message key="button.lista_comunicazioni" /></a></li>
	</c:if>
	<li id="comunicazioni_button" style="<%=displayButtonCreaComunicazioni%>" ><a href="javascript:doSubmit('createComunicazione.htm','',document.inviodati)"><fmt:message
		key="button.crea_comunicazioni" /></a></li>
	<c:if test="${mercati.flagGestisciPosizioni}">
		<li><a href="javascript:doHref('compattaPosizioni.htm?codicemercato=${mercati.id.codice}','');"><fmt:message key="button.compatta_posizioni" /></a></li>
	</c:if>
	<li id="elimina_button" style="<%=displayButtonElimina%>" ><a href="javascript:doSubmit('deletePosteggi.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
		key="button.delete" /></a></li>
		
    <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>