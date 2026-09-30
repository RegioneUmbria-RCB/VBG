<%@ include file="taglibs.jsp" %>
<%@page import="java.net.URLDecoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="codiceIstanza" value="" scope="page"/>
<c:if test="${not empty param.codiceIstanza}">
	<c:set var="codiceIstanza" value="${param.codiceIstanza}" />
</c:if>
<c:set var="software" value="<%=ORMHelper.getSoftware() %>" scope="page"/>
<c:if test="${not empty param.codiceIstanza}">
	<c:set var="codiceIstanza" value="${param.codiceIstanza}" />
</c:if>
<c:set var="isIstanzePage" value="false" />
<c:set var="cssClassPrefix" value="bullet" />
<c:set var="displayAltreFunzioni" value="false" />
<c:if test="${not empty param.isIstanzePage}">
	<c:set var="isIstanzePage" value="true" />
	<c:set var="cssClassPrefix" value="" />
	<c:set var="displayAltreFunzioni" value="true" />
</c:if>
	<c:set var="_URL_BACK" scope="page">/</c:set>
	<c:if test="${not empty _urlback}">
		<c:set var="_URL_BACK"><%= URLDecoder.decode((String)request.getAttribute("_urlback"),"UTF-8" )%></c:set>
		<c:set var="_URL_BACK"><%= URLDecoder.decode((String)pageContext.getAttribute("_URL_BACK"),"UTF-8" )%></c:set>		
	</c:if>

<c:choose>
	<c:when test="${empty codiceIstanza}">
		[istanze_funzioni]  Attenzione !! non è stato settato il parametro codiceistanza.
	</c:when>
	<c:otherwise>
			<%							
				pageContext.setAttribute("URL_ISTANZEDYN2_MODELLI", BackofficeNETConstants.getURL_ISTANZE_DYN2_MODELLI());
			%>
			<c:set var="_URL_ISTANZEDYN2_MODELLI" value="${URL_ISTANZEDYN2_MODELLI}?CodiceIstanza=${codiceIstanza}"/>			 	
			<c:set var="_URL_ISTANZEDYN2_MODELLI" value="${inite:linkschede(pageContext.request, _URL_ISTANZEDYN2_MODELLI, _URL_BACK, software, false, codiceIstanza)}" />			
			<%							
				pageContext.setAttribute("URL_ISTANZEONERILISTA", BackofficeNETConstants.getURL_ISTANZEONERILISTA());
			%>			
			
			<c:set var="_URL_ISTANZEONERILISTA" value="${URL_ISTANZEONERILISTA}?Codice=${codiceIstanza}&Codice2=&DaIstanze=S"/>	
			<c:set var="_URL_ISTANZEONERILISTA" value="${inite:linkIstanzeoneri(pageContext.request, _URL_ISTANZEONERILISTA, _URL_BACK, software, false, codiceIstanza)}" />
			 
			<%--
			<c:set var="_URL_ISTANZEONERILISTA" value="${URL_ISTANZEONERILISTA}?Codice=${codiceIstanza}&Codice2=&DaIstanze=S"/>	
			<c:set var="_URL_ISTANZEONERILISTA" value="${inite:geturlto(pageContext.request, _URL_ISTANZEONERILISTA, _URL_BACK, software, false)}" />
			--%>
			<%							
				pageContext.setAttribute("URL_ISTANZESTAMPE", BackofficeNETConstants.getURL_ISTANZESTAMPE());
			%>
			<%-- 
			<c:set var="_URL_ISTANZESTAMPE" value="${URL_ISTANZESTAMPE}?Codice=${codiceIstanza}"/>	
			<c:set var="_URL_ISTANZESTAMPE" value="${inite:geturlto(pageContext.request, _URL_ISTANZESTAMPE, _URL_BACK, software, false)}" />		
			--%>
			
			<c:set var="_URL_ISTANZESTAMPE" value="${URL_ISTANZESTAMPE}?Codice=${codiceIstanza}"/>			 	
			<c:set var="_URL_ISTANZESTAMPE" value="${inite:linkStampeIstanza(pageContext.request, _URL_ISTANZESTAMPE, _URL_BACK, software, false, codiceIstanza)}" />			
			
		<ul class="${cssClassPrefix}listaSchede">
				<c:if test="${isSorteggiata eq true or empty isSorteggiata}">
					<c:set var="hrefIstanze" value="javascript:historySet('${_URL_BACK }','../istanze/view.htm?codice=${codiceIstanza}','');"></c:set>
					<c:set var="classIstanze" value="Scheda"></c:set>
					<c:if test="${isIstanzePage eq 'true' }">
						<c:set var="hrefIstanze" value="#"></c:set>
						<c:set var="classIstanze" value="SchedaAttiva"></c:set>
					</c:if>
					<li><a id="raggruppatoScheda" class="${classIstanze}" href="${hrefIstanze}"><fmt:message key="label.tab_istanza"/></a></li>
					<c:if test="${displayAltreFunzioni eq 'false' }">
						<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnElaborazione')}">						
							<li><a href="javascript:historySet('${_urlback}','../movimenti/listElaborazione.htm?codiceIstanza=${istanzeCommand.entity.id.codice}','')"><fmt:message key="button.elaborazione" /></a></li>						
						</c:if>
					</c:if>
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnEndoprocedimenti')}">
					 	<li><a id="dettaglioScheda" class="Scheda" href="javascript:historySet('${_urlback }','../istanzeprocedimenti/list.htm?codiceIstanza=${codiceIstanza}','');"><fmt:message key="label.tab_endoprocedimenti"/></a></li>
					</c:if>
				</c:if>	
				<c:if test="${isMostraSchede eq true or empty isMostraSchede}">
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnSchede')}">
						<li><a id="dettaglioScheda" class="Scheda" href="${_URL_ISTANZEDYN2_MODELLI}"><fmt:message key="label.tab_schede"/></a></li>
					</c:if>
				</c:if>	
				<c:if test="${isSorteggiata eq true or empty isSorteggiata}">	
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnSoggetticollegati')}">
					<c:set var="styleIstanzeRichiedentiPresenti"></c:set>	
						<c:if test="${isIstanzaRichiedentiCollegati eq true }">
							<c:set var="styleIstanzeRichiedentiPresenti"> style="background-color: #9FF781;" </c:set>
						</c:if>	
							<li><a id="dettaglioScheda" ${styleIstanzeRichiedentiPresenti}  class="Scheda" href="javascript:historySet('${_urlback }','../istanzerichiedenti/list.htm?codiceIstanza=${codiceIstanza}','');"><fmt:message key="label.tab_soggetti_collegati"/></a></li>
					</c:if>	
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnDocumenti')}">
						<li><a id="dettaglioScheda" class="Scheda" href="javascript:historySet('${_urlback }','../documentiistanza/list.htm?codiceIstanza=${codiceIstanza}','');"><fmt:message key="label.tab_documenti"/></a></li>
					</c:if>
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnGestioneOneri')}">
						<li><a href="${_URL_ISTANZEONERILISTA}"><fmt:message key="label.tab_gestione_oneri"/></a></li>
					</c:if>
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnAutorizzazioniConcessioni')}">
							<li><a id="dettaglioScheda" class="Scheda" href="javascript:historySet('${_urlback}','../autorizzazioni/create.htm?codiceIstanza=${codiceIstanza}','');"><fmt:message key="label.tab_autorizzazioni_concessioni"/></a></li>
					</c:if>	
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnStampe')}">
						<li><a id="dettaglioScheda" class="Scheda" href="${_URL_ISTANZESTAMPE}"><fmt:message key="button.stampe"/></a></li>
					</c:if>
					<!-- §§§BEGIN§§§ -->
					<c:if test="${inite:isEnterprise()}">	
						<c:set var="styleIstanzePresenti"></c:set>	
						<c:if test="${isIstanzeCollegate eq true }">
							<c:set var="styleIstanzePresenti"> style="background-color: #9FF781;" </c:set>
						</c:if>			
						<li><a id="dettaglioScheda" ${ styleIstanzePresenti } class="scheda" href="javascript:historySet('${_urlback }','../istanzecollegate/list.htm?codiceIstanza=${codiceIstanza}&software=${software}','');"><fmt:message key="label.tab_istanze_collegate"/></a></li>
					</c:if>
					<!-- §§§END§§§ -->
					<c:if test="${displayAltreFunzioni eq 'true' }">
						
						<li><a id="dettaglioScheda" class="Scheda" href="javascript:gestDialog('altreFunzioni');"><fmt:message key="label.tab_altre_funzioni"/></a></li>
					</c:if>
					
				</c:if>	
		
		<c:set var="diplayContent" value="" />
		<c:if test="${displayAltreFunzioni eq 'true'}">
		</ul>
		<c:set var="diplayContent" value="display: none;" />			
		<div id="altreFunzioni" style="${diplayContent}" class="overlay">
			<div id="altreFunzioni" class="dialog">
				<div style="width: 100%" align="right">
				<a title="<fmt:message key="button.back" />" href="javascript:gestDialog('altreFunzioni');"><img src="${pageContext.request.contextPath }/images/cross.gif" border="0"/></a>
				</div>
				<ul id="${cssClassPrefix}functions">	
				</c:if>			
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnInfo')}">
						<li><a href="javascript:historySet('${_urlback}','../istanze/infoView.htm?codice=${codiceIstanza}','')"><fmt:message key="button.info"/></a></li>
					</c:if>					
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnCds')}">
						<li><a href="javascript:historySet('${_urlback}','../cds/view.htm?codiceIstanza=${codiceIstanza}','')"><fmt:message key="button.cds"/></a></li>
					</c:if>					
					<c:if test="${isTipiOrarioVisibile eq true }">
						<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnOrariDiApertura')}">
							<li><a href="javascript:historySet('${_urlback}','../orariaperturatestata/list.htm?codiceIstanza=${codiceIstanza}','')"><fmt:message key="button.orari_di_apertura"/></a></li>
						</c:if>
					</c:if>
					<c:if test="${isMovimentiMailVisibile eq true }">
						<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnArchivioEmail')}">
							<li><a href="javascript:historySet('${_urlback}','../movimentimail/list.htm?codicemovimento=&codiceistanza=${codiceIstanza}','')"><fmt:message key="button.archivio_email"/></a></li>
						</c:if>
					</c:if>
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnPermessiIstanza')}">
							<li><a href="javascript:historySet('${_urlback}','../permistanze/list.htm?codiceIstanza=${codiceIstanza}','')"><fmt:message key="button.permessi_istanza"/></a></li>
					</c:if>
					<%if((Boolean)request.getAttribute(WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC)){ %>
					<li><a href="javascript:doSubmit('deleteArchiviazioniIstanze.htm','',document.inviodati)"><fmt:message key="button.delete_archiviazioni" /></a></li>
					<%} %>
					
					<!-- §§§BEGIN§§§ -->
					<c:if test="${inite:isEnterprise()}">					
						<c:if test="${isLavoritipiVisibile eq true }">
							<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnLavori')}">
								<li><a href="javascript:historySet('${_urlback}','../istanzelavorit/list.htm?codiceIstanza=${codiceIstanza}','')"><fmt:message key="button.lavori"/></a></li>
							</c:if>
						</c:if>
					</c:if>
					<!-- §§§END§§§ -->
					<!-- §§§BEGIN§§§ -->
					<c:if test="${inite:isEnterprise()}">
						<c:set var="VERTICALIZZAZIONE_REPLICAISTANZE_IN_REQUEST"><%= request.getAttribute(WebConstants.VERTICALIZZAZIONE_REPLICAISTANZE)%></c:set>
						<c:if test="${VERTICALIZZAZIONE_REPLICAISTANZE_IN_REQUEST eq true}">
							<c:if test="${isBottoneVisualizzaReplicheVisibile eq true }">
								<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnRepliche')}">
									<li><a href="javascript:historySet('${_urlback}','../istanze/visualizzaRepliche.htm?codiceIstanza=${codiceIstanza}','')"><fmt:message key="button.repliche"/></a></li>
								</c:if>
							</c:if>	
							<c:if test="${isBottoneCreaReplicheVisibile eq true }">
								<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnCreaRepliche')}">
									<li><a href="javascript:historySet('${_urlback}','../istanze/creaReplicheView.htm?codiceIstanza=${codiceIstanza}','')"><fmt:message key="button.crea_repliche"/></a></li>
								</c:if>
							</c:if>
						</c:if>
					</c:if>
					<!-- §§§END§§§ -->
					
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnChiusureFasi')}">
						<c:if test="${isAbilitaBloccaOneri eq true}">
							<c:set var="labelBloccaOneri"><fmt:message key="button.blocca_oneri"/></c:set>
							<c:choose>
								<c:when test="${isOneriBloccati eq true}">
									<c:set var="labelBloccaOneri"><fmt:message key="button.sblocca_oneri"/></c:set>
								</c:when>
							</c:choose>
							<li><a id="dettaglioScheda" class="Scheda" href="javascript:void(0);" onclick="bloccaOneri(${codiceIstanza})"><label id="bloccaOneriIstanzaLbl">${labelBloccaOneri}</label></a></li>
							<script type="text/javascript">
								var bloccaOneri = function(codiceIstanza){
									var jhqrPr = jQuery.ajax({
										  url: '../istanze/ajaxBloccaSbloccaOneri.htm',
										  context: document.body,
										  cache: false,
										  data: "codiceIstanza=${istanzeCommand.entity.id.codice}",
										  dataType: "html",
										  success: function(data) {
												  if(data=='1'){
													$('bloccaOneriIstanzaLbl').innerHTML='<fmt:message key="button.sblocca_oneri"/>';
													dijit.showTooltip('<fmt:message key="02"/>', dojo.byId('bloccaOneriIstanzaLbl'));
												  }else if(data=='0'){
													$('bloccaOneriIstanzaLbl').innerHTML='<fmt:message key="button.blocca_oneri"/>';													
													dijit.showTooltip('<fmt:message key="02"/>', dojo.byId('bloccaOneriIstanzaLbl'));
												  }	else{
													dijit.showTooltip(data, dojo.byId('bloccaOneriIstanzaLbl'));
												  }
												  setTimeout(function(){dijit.hideTooltip(dojo.byId('bloccaOneriIstanzaLbl'))},2000);
											},
											error: function(jqXHR, textStatus, errorThrown){
												dijit.showTooltip(jqXHR.responseText, dojo.byId('bloccaOneriIstanzaLbl'));	
												setTimeout(function(){dijit.hideTooltip(dojo.byId('bloccaOneriIstanzaLbl'))},2000);
											}
										});	
								};
							</script>
						</c:if>
					</c:if>						
					<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnEventi')}">
						<li><a href="javascript:historySet('${_urlback}','../istanzeeventi/list.htm?codiceIstanza=${codiceIstanza}')"><fmt:message key="button.eventi" /></a></li>						
					</c:if>
			<c:if test="${displayAltreFunzioni eq 'true'}">		
				</ul>
		
			</div>
		</div>
		</c:if>
	</c:otherwise>
</c:choose>	