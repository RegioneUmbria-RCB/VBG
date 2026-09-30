<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti"%>
<%@page import="java.util.Set"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.AlberoEndo"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.ElenchiEndoFACCT"%>
<%@page import="it.eng.suap.xengine.model.service.xcommon.AzioneType"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.EndoFACCT"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" href="${pageContext.request.contextPath}/css/facct.css" rel="stylesheet"></link>
<link type="text/css" href="${pageContext.request.contextPath}/css/dettagliIntervento.css" rel="stylesheet"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<title>Selezione Endoprocedimenti</title>
<style>

	.altriendoselezionati{
		cursor: url(../images/clickon.png), auto;
	}

	.altriEndoAttivati{
		background: #e0e0e0;
		font-style: italic;
		border: 1px dotted #000000;
	}


</style>		
</head>
<%
//String tipoAz = (String)request.getAttribute("tipoAzione");
ElenchiEndoFACCT elenchiEndo =(ElenchiEndoFACCT) request.getAttribute("elenchiEndo");
Set<String> endoAttivi =(Set<String>) request.getAttribute("endoAttivi");
Set<String> endoNoCartAttivi =(Set<String>) request.getAttribute("endoNoCartAttivi");
String errorMessage = (String)request.getAttribute("error");
String urlSchedaEndo = "../dizionario/popupSchedaSpiegazioneEndo1.htm?id=__ID__&idcomuneinventario=__IDCOMUNE__&idComuneEndoLoc=__IDCOMUNEENDOLOC__&codiceEndoLoc=__CODICEENDOLOC__"; 
//"../dizionario/popupSchedaSpiegazioneEndo1.htm?id=";//request.getSession().getAttribute("baseAreaRiservataMsUrl") + "/Public/mostraDettagliEndo.aspx?IdComune="+ ORMHelper.getIdcomuneAlias()+"&fromAreaRiservata=True&print=False&Id=";
%>
<body>
	    <%
	        if(StringUtils.isEmpty(errorMessage)){
	            //AlberoEndo endoCART = elenchiEndo.getEndoCart();
	            AlberoEndo endoNecessari = elenchiEndo.getEndoNecessari();
	            AlberoEndo endoRicorrenti = elenchiEndo.getEndoRicorrenti();
	            AlberoEndo altriEndo = elenchiEndo.getAltriEndo();
	            int count = 0;
	    %>
		<%--
	<div class="descrizione">
			Selezionare l'azione e gli endoprocedimenti che si desidera attivare:
	</div>
	 --%>
	<jsp:include page="../includes/messaggio_aggiornamento.jsp" >
			<jsp:param name="settimeout" value="false"></jsp:param>
	</jsp:include>	 
	<jsp:include page="../includes/alert.jsp" >
			<jsp:param name="commandName" value="presentazioneDomandaCommand"></jsp:param>
	</jsp:include>
	<div>
	    <form action="<%=request.getContextPath()%>/cart/caricaModulistica.htm" id="target" name="presentazioneDomandaCommand" method="post" >
	    
	    <c:if test="${not empty erroreSciaCondizionata }">
	    	
	    	<div class="ui-state-error ui-corner-all" style="padding: 10px; text-align: center; font-size:18px;"> 
			
				
   	 Attenzione stai per predisporre una SCIA CONDIZIONATA (SCIA o SCIA unica + richiesta di autorizzazione):
				<br />Non sara' possibile iniziare l'attività fino al rilascio dei relativi atti di assenso, che verra' comunicato dallo Sportello Unico.				
				<br />
				Dichiaro di aver preso visione
    <br />
				<label for="procedi_id_3">PRESA VISIONE</label>&nbsp;<input type="checkbox" id="procedi_id_3" name="controlloEndoAccettato" class="bottone-cart" value="presaVisione">
	    	</div>
	   	    
	    </c:if>
	    
	    
	    	<%--
	    	<c:choose>
		    	<c:when test="${valoreAzioneAvvio eq tipoAzione }">
			    	<div style="padding: 5px;">
			    		<span style="font-size: large;">Sono necessari lavori sui fabbricati?</span>
			    		<ul>
			    			<li>	
			    				<input id="azioneAvvioId" type="radio" <c:if test="${presentazioneDomandaCommand.lavoriSuFabbricati eq false}">checked="checked" </c:if>onclick="checkEndo()" name="tipoAzione" value="<%=AzioneType.AVVIO%>"/>&nbsp;<label for="azioneAvvioId">No</label>
			    			</li>
			    			<li>
			    				<input id="azioneAvvioSt0Id" type="radio" <c:if test="${presentazioneDomandaCommand.lavoriSuFabbricati eq true}">checked="checked" </c:if>name="tipoAzione" onclick="checkEndo()" value="<%=AzioneType.AVVIO_ST_0%>"/>&nbsp;<label for="azioneAvvioSt0Id">Sì</label>
			    			</li>
			    		</ul>	
			    	</div>
				</c:when>
				
				<c:otherwise>
					<input type="hidden" name="tipoAzione" value="${tipoAzione}" />
				</c:otherwise>
				
			</c:choose>
	    	 --%>
	    	 <input type="hidden" name="tipoAzione" value="${tipoAzione}" />
	    	 
	    	 
	    	 <%	
	    	EndoFACCT ipComunica = (EndoFACCT)request.getAttribute("ipComunica");
	    	 	if(ipComunica!=null){
	    	 		String v2 = ipComunica.isEndoCART() ? ipComunica.getCodiceBDR() : ipComunica.getIdcomune() + "|" + String.valueOf(ipComunica.getCodiceInventario()) + "";%>
	    	 	 	<input type="hidden" name="endoAttivi" value="<%=v2 %>" />
	    	 <% } %>
	    	 
		<%
		    if(endoNecessari != null && endoNecessari.getEndosPerFamigliaCategoria().size() > 0){
		%>
	    <div class="alberoEndo" id="elencoEndoNecessari">
	    
	    
	    
	    			<%if(request.getSession().getAttribute(WebConstants.PRODOTTO).equals(WebConstants.PRODOTTO_AURIT)){ %>
						<div class="elenco-endo-title">Procedimenti Necessari</div>
					<%}else{ %>
						<div class="elenco-endo-title">Endoprocedimenti Necessari</div>
					<%} %>
   			
   			
   			
   			
		<% 
				Iterator<String> famiglieIter = endoNecessari.getFamiglieKeySet().iterator();
				while(famiglieIter.hasNext()){
				    String famiglia = famiglieIter.next();
		%>
			<ul>
	    	<li class="famigliaEndo">
	    		<%= famiglia %>
	    		<%
	    			Iterator<String> categorieIter = endoNecessari.getCategorieKeySetPerFamiglia(famiglia).iterator();
	    			while(categorieIter.hasNext()){
	    			    String categoria = categorieIter.next();
	    			    List<EndoFACCT> elencoEndo = endoNecessari.getElencoEndoPerFamigliaCategoria(famiglia, categoria);
	    		%>
	    		<ul>
	    			<li class="tipoEndo">
	    			<%= categoria %>
				    	<ul>
			    <%
				        for(int i = 0; i < elencoEndo.size(); i++){
				            count+=1;
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = "checked=\"checked\"";
				    		String readOnly = "readonly='readonly'";
				    		StringBuilder idsb = new StringBuilder("endo_");
				    		if(!endo.isEndoCART()){
				    		    idsb.append("nocart_");
				    		}
				    		idsb.append(count);
				    		String name = endo.isEndoCART() ? "endoAttivi" : "endoNoCartAttivi";
				    		String value = endo.isEndoCART() ? endo.getCodiceBDR() : endo.getIdcomune() + "|" + String.valueOf(endo.getCodiceInventario()) + "";
				    		
				    		
			    %>
						<li class="endo">
							<dl>
								<dd>
									<input id="<%=idsb.toString()%>" type="checkbox" name="<%=name%>" value="<%= value%>" <%=checked %> <%=readOnly %>/>
									<label for="<%=idsb.toString()%>"><%=endo.getDescrizione()%></label>	
									<%
										String urlInfoEndo =  urlSchedaEndo.replaceAll("__ID__", String.valueOf(endo.getCodiceInventario())).replaceAll("__IDCOMUNE__", endo.getIdcomune());
										if(endo.getIdEndoLoc() != null){
										    urlInfoEndo = urlInfoEndo.replaceAll("__IDCOMUNEENDOLOC__", StringUtils.defaultString(endo.getIdEndoLoc().getIdcomune()));
										    urlInfoEndo = urlInfoEndo.replaceAll("__CODICEENDOLOC__", (endo.getIdEndoLoc().getCodice()==null?"":String.valueOf(endo.getIdEndoLoc().getCodice())));
										}
										%>								
									<a class="link-scheda-endo" href="<%= urlInfoEndo%>"><img src="<%=request.getContextPath()%>/images/info_endo.gif"/></a>
									<% if(StringUtils.isNotBlank(endo.getNote())){ %>
									<a class="help-endo" id="help-endo-<%=idsb.toString()%>" href="#"><img src="<%=request.getContextPath()%>/images/help.gif"/></a>
									<script type="text/javascript">
									$(function(){
										$('#help-endo-<%=idsb.toString()%>').tooltip({
											content: "<%=endo.getNote()%>",
											items: 'a'
										});
									});
									</script>
									<%
										}
										if(endo.getSubEndos().size()>0){ 
									%>
										<ul>
									<%   for(int j = 0; j < endo.getSubEndos().size(); j++){
									    	count+=1;
									    	
								    		EndoFACCT endo2 = endo.getSubEndos().get(j);
								    		boolean endo2Obbbligatorio = endo2.isObbligatorio();
								    		String checked2 = endo2.isObbligatorio()? "checked=\"checked\"" : "";
								    		String readOnly2 = endo2.isObbligatorio()? "readonly=\"readonly\"" : "";;
								    		
								    		StringBuilder idsb2 = new StringBuilder("endo_");
								    		if(!endo2.isEndoCART()){
								    			idsb2.append("nocart_");
								    		}
								    		idsb2.append(count);
								    		String name2 = endo2.isEndoCART() ? "endoAttivi" : "endoNoCartAttivi";
								    		String value2 = endo2.isEndoCART() ? endo2.getCodiceBDR() : endo2.getIdcomune() + "|" + String.valueOf(endo2.getCodiceInventario()) + "";	
										%>
										
											<li class="sub-endo" style="padding-left: 20px;">
												<dl>
													<dd>
														<input id="<%=idsb2.toString()%>" type="checkbox" name="<%=name2%>" value="<%= value2%>" <%=checked2 %> <%=readOnly2 %>/>
														<label for="<%=idsb2.toString()%>"><%=endo2.getDescrizione()%></label>
															<%
															String urlInfoEndo2 =  urlSchedaEndo.replaceAll("__ID__", String.valueOf(endo2.getCodiceInventario())).replaceAll("__IDCOMUNE__", endo2.getIdcomune());
															if(endo2.getIdEndoLoc() != null){
																urlInfoEndo2 = urlInfoEndo2.replaceAll("__IDCOMUNEENDOLOC__", StringUtils.defaultString(endo2.getIdEndoLoc().getIdcomune()));
																urlInfoEndo2 = urlInfoEndo2.replaceAll("__CODICEENDOLOC__", (endo2.getIdEndoLoc().getCodice()==null?"":String.valueOf(endo.getIdEndoLoc().getCodice())));
															}
															%>																								
														<a class="link-scheda-endo" href="<%= urlInfoEndo2%>"><img src="<%=request.getContextPath()%>/images/info_endo.gif"/></a>
														<% if(StringUtils.isNotBlank(endo.getNote())){ %>
														<a class="help-endo" id="help-endo-<%=idsb.toString()%>" href="#"><img src="<%=request.getContextPath()%>/images/help.gif"/></a>
														<script type="text/javascript">
														$(function(){
															$('#help-endo-<%=idsb.toString()%>').tooltip({
																content: "<%=endo.getNote()%>",
																items: 'a'
															});
														});
														</script>
													<%
														}
														if(endo2Obbbligatorio){ 
													%>
													<script type="text/javascript">
														$(document).ready(function() {
															$("#<%=idsb2.toString()%>").click(keepChecked);
														});
													</script>
													<%} %>
													</dd>
												</dl>	
											</li>
									
									
									<%	}%>
										</ul>
									<%  }%>
									<script type="text/javascript">
										$(document).ready(function() {
											$("#<%=idsb.toString()%>").click(keepChecked);
										});
									</script>
								</dd>
							</dl>
						</li>
				<%
					    }
			    %>
			    		</ul>
			    	</li>
			    </ul>
			    <%
	    			}
			    %>
		    </li>
		    </ul>
			    <%
	    		}
			    %>
		</div>
	    <%
			}
		    if(endoRicorrenti != null && endoRicorrenti.getEndosPerFamigliaCategoria().size() > 0){
		%>
	    <div class="alberoEndo" id="elencoEndoRicorrenti">
   			<div class="elenco-endo-title">Endoprocedimenti Ricorrenti</div>
		<% 
				Iterator<String> famiglieIter = endoRicorrenti.getFamiglieKeySet().iterator();
				while(famiglieIter.hasNext()){
				    String famiglia = famiglieIter.next();
		%>
			<ul>
	    	<li class="famigliaEndo">
	    		<%= famiglia %>
	    		<%
	    			Iterator<String> categorieIter = endoRicorrenti.getCategorieKeySetPerFamiglia(famiglia).iterator();
	    			while(categorieIter.hasNext()){
	    			    String categoria = categorieIter.next();
	    			    List<EndoFACCT> elencoEndo = endoRicorrenti.getElencoEndoPerFamigliaCategoria(famiglia, categoria);
	    		%>
	    		<ul>
	    		<li class="tipoEndo">
	    			<%= categoria %>
		    		<ul>
			    <%
				        for(int i = 0; i < elencoEndo.size(); i++){
				            count+=1;
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = endoNoCartAttivi.contains(endo.getIdcomune() + "|" + String.valueOf(endo.getCodiceInventario())) || (endo.isEndoCART() && endoAttivi.contains(endo.getCodiceBDR())) ? "checked=\"checked\"" : "";
				    		String readOnly = "";
				    		StringBuilder idsb = new StringBuilder("endo_");
				    		if(!endo.isEndoCART()){
				    		    idsb.append("nocart_");
				    		}
				    		idsb.append(count);
				    		String name = endo.isEndoCART() ? "endoAttivi" : "endoNoCartAttivi";
				    		String value = endo.isEndoCART() ? endo.getCodiceBDR() : endo.getIdcomune() + "|" + String.valueOf(endo.getCodiceInventario());
			    %>
						<li class="endo">
							<dl>
								<dd>
									<input id="<%=idsb.toString()%>" type="checkbox" name="<%=name %>" value="<%= value%>" <%=checked %> />
									<label for="<%=idsb.toString()%>"><%=endo.getDescrizione()%></label>
									<%
										String urlInfoEndo =  urlSchedaEndo.replaceAll("__ID__", String.valueOf(endo.getCodiceInventario())).replaceAll("__IDCOMUNE__", endo.getIdcomune());
										if(endo.getIdEndoLoc() != null){
											urlInfoEndo = urlInfoEndo.replaceAll("__IDCOMUNEENDOLOC__", StringUtils.defaultString(endo.getIdEndoLoc().getIdcomune()));
											urlInfoEndo = urlInfoEndo.replaceAll("__CODICEENDOLOC__", (endo.getIdEndoLoc().getCodice()==null?"":String.valueOf(endo.getIdEndoLoc().getCodice())));
										}
										%>								
									<a class="link-scheda-endo" href="<%= urlInfoEndo%>"><img src="<%=request.getContextPath()%>/images/info_endo.gif"/></a>
									<% if(StringUtils.isNotBlank(endo.getNote())){ %>						
									<a class="help-endo" id="help-endo-<%=idsb.toString()%>" href="#"><img src="<%=request.getContextPath()%>/images/help.gif"/></a>
									<script type="text/javascript">
									$(function(){
										$('#help-endo-<%=idsb.toString()%>').tooltip({
											content: "<%=endo.getNote()%>",
											items: 'a'
										});
									});
									</script>

									<%
										}
										if(endo.getSubEndos().size()>0){ 
									%>
										<ul>
									<%   for(int j = 0; j < endo.getSubEndos().size(); j++){
									    	count+=1;
									    	
								    		EndoFACCT endo2 = endo.getSubEndos().get(j);
								    		boolean endo2Obbbligatorio = endo2.isObbligatorio();
								    		String checked2 = endo2.isObbligatorio()? "checked=\"checked\"" : "";
								    		String readOnly2 = endo2.isObbligatorio()? "readonly=\"readonly\"" : "";;
								    		
								    		StringBuilder idsb2 = new StringBuilder("endo_");
								    		if(!endo2.isEndoCART()){
								    			idsb2.append("nocart_");
								    		}
								    		idsb2.append(count);
								    		String name2 = endo2.isEndoCART() ? "endoAttivi" : "endoNoCartAttivi";
								    		String value2 = endo2.isEndoCART() ? endo2.getCodiceBDR() : endo2.getIdcomune() + "|" + String.valueOf(endo2.getCodiceInventario()) + "";	
										%>
										
											<li class="sub-endo" style="padding-left: 20px;">
												<dl>
													<dd>
														<input id="<%=idsb2.toString()%>" type="checkbox" name="<%=name2%>" value="<%= value2%>" <%=checked2 %> <%=readOnly2 %>/>
														<label for="<%=idsb2.toString()%>"><%=endo2.getDescrizione()%></label>									
														

												<%
													String urlInfoEndo2 =  urlSchedaEndo.replaceAll("__ID__", String.valueOf(endo2.getCodiceInventario())).replaceAll("__IDCOMUNE__", endo2.getIdcomune());
													if(endo2.getIdEndoLoc() != null){
														urlInfoEndo2 = urlInfoEndo2.replaceAll("__IDCOMUNEENDOLOC__", StringUtils.defaultString(endo2.getIdEndoLoc().getIdcomune()));
														urlInfoEndo2 = urlInfoEndo2.replaceAll("__CODICEENDOLOC__", (endo2.getIdEndoLoc().getCodice()==null?"":String.valueOf(endo.getIdEndoLoc().getCodice())));
													}
													%>																								
												<a class="link-scheda-endo" href="<%= urlInfoEndo2%>"><img src="<%=request.getContextPath()%>/images/info_endo.gif"/></a>
												<% if(StringUtils.isNotBlank(endo.getNote())){ %>
												<a class="help-endo" id="help-endo-<%=idsb.toString()%>" href="#"><img src="<%=request.getContextPath()%>/images/help.gif"/></a>
												<script type="text/javascript">
												$(function(){
													$('#help-endo-<%=idsb.toString()%>').tooltip({
														content: "<%=endo.getNote()%>",
														items: 'a'
													});
												});
												</script>

													<%
														}
														if(endo2Obbbligatorio){ 
													%>
													<script type="text/javascript">
														$(document).ready(function() {
															$("#<%=idsb2.toString()%>").click(keepChecked);
														});
													</script>
													<%} %>
													</dd>
												</dl>	
											</li>
									
									
									<%	}%>
										</ul>
									<%  }%>
									
									
									
								</dd>
							</dl>
						</li>
				<%
					    }
			    %>
		    		</ul>
			    </li>
			    </ul>
			    <%
	    			}
			    %>
		    </li>
		    </ul>
			    <%
	    		}
			    %>
		</div>
	    <%
			} 
		    /*
			else {
			    */
			%>
				
				<%-- <div><span>Nessun endoprocedimento necessario attivabile per l'attività selezionata.</span></div> --%>
			<%
			//}
		%>
		
		
		
		<!-- BEGIN SELEZIONE ENDO -->		
		<script id="template-checked-endo" type="text/x-jquery-tmpl">
			<li id="li_altri_endo_{{= index}}" class="endoselezionaticlass endo" data-value="{{= codiceEndo}}">
				<dl><dd>
					<input id="check_altri_endo_{{= index}}" type="checkbox" name="{{= paramName}}" value="{{= codiceEndo}}" checked="checked"></input>
					<label for="check_altri_endo_{{= index}}">{{= descrizioneEndo}}</label>
						<ul>
							{{tmpl($data) "#template-sub-endo"}}							
						</ul>

				</dd></dl>
			</li>
		</script>
		
		<script id="template-sub-endo" type="text/x-jquery-tmpl">
						
							{{each(i,endo) subEndoList}} 

											<li class="sub-endo" style="padding-left: 20px;">
												<dl>
													<dd>
														{{if $(endo).data('checked') !='' }}
															<input id="__{{= $(endo).data('id') }}" type="checkbox" name="__{{= $(endo).data('tipo') }}" 
																	value="{{= $(endo).data('value') }}" 
																		{{= $(endo).data('checked') }}
																		disabled="disabled"
																	/>
																<input type="hidden" name="{{= $(endo).data('tipo') }}" value="{{= $(endo).data('value') }}" />
															<label for="{{= $(endo).data('id') }}"> {{= $(endo).data("descrizione") }}</label>									
														{{else}}
																<input id="{{= $(endo).data('id') }}" type="checkbox" name="{{= $(endo).data('tipo') }}" 
																	value="{{= $(endo).data('value') }}" 
																		{{= $(endo).data('checked') }}																		
																	/>
																<label for="{{= $(endo).data('id') }}"> {{= $(endo).data("descrizione") }}</label>					
														{{/if}}
													
													</dd>
												</dl>	
											</li>										

							{{/each}}

		</script>
		
<script type="text/javascript">

	function appendNewElementSelected(id) {
		addToSelezionati($('.altriendoselezionati[data-value=\'' + id + '\']'));
	}


	function addToSelezionati(el){
	
		if (el.data('selezionato'))
		{
			return;	
		}
		
		aggiungiElementoSelezionato(el);
	}
	
	function aggiungiElementoSelezionato(el) {
		var valore = el.data('value');
		if(valore){
			var count = el.data('count');
			var	descrizione = el.text();
			var template = $("#template-checked-endo");
			var isCart = el.data('iscart');
			var fieldName = "endoNoCartAttivi";
			
			var subendo = $('.subEndoTpl' + valore.replace('|','\\|'));
			
			
			if(isCart + "" == "true"){
				fieldName = "endoAttivi";
			}
			var params = {codiceEndo: valore, descrizioneEndo: descrizione, paramName: fieldName, index: count, subEndoList: subendo};
			if(template && template.length > 0){
				template.tmpl(params).appendTo($("#endoAttivati_id"));
			}
			el.addClass('altriEndoAttivati');
			el.data('selezionato', true);
		
			var container = $('#li_altri_endo_' + count);
			container.data('elementoCollegato', el);
			var checkbox = $('#check_altri_endo_' + count);
			checkbox.on('click', function (e) {
				removeEndo(container);
			});
			
			$("#endoSelezionati").show();
		}
	}
	
	function removeEndo(element) {
		var elementoCollegato = element.data('elementoCollegato');
		
		elementoCollegato.data('selezionato', false);
		elementoCollegato.removeClass("altriEndoAttivati");
		element.remove();
		
		if($("#endoAttivati_id").children().length === 0) {
			$('#endoSelezionati').hide();
		}
		
	}
	
	function nascondiTutti(){		
		$('#elencoAltriEndo .famigliaEndo').hide();
		$('#elencoAltriEndo .tipoEndo').hide();
		$('#elencoAltriEndo .endo').hide();		
	}
	function mostraTutti(){
		
		$('#elencoAltriEndo .famigliaEndo').show();
		$('#elencoAltriEndo .tipoEndo').show();
		$('#elencoAltriEndo .endo').show();
	}
	
	function cercaEndo(obj){
		
		var text = $('#endoFilter_id').val();
		nascondiTutti();
		if(text != ''){
			$('.altriendoselezionati').each(function( index, el ) {
				
				if($( this ).text().toLowerCase().indexOf(text.toLowerCase())>=0){
					$(this).closest('#elencoAltriEndo .famigliaEndo').show();
					$(this).closest('#elencoAltriEndo .tipoEndo').show();
					$(this).closest('#elencoAltriEndo .endo').show();
					
				}
			});
		}else{
			mostraTutti();
		}
	}
	
	$(function () {
		$('.altriendoselezionati').on('click', function (e) {
			addToSelezionati($(this));			
		});		
		
		<c:forEach items="${endoNoCartAttivi}" var="codice_endo">
				appendNewElementSelected('${codice_endo}');
		</c:forEach>
		
		
	});
	
	</script>
		
	
		
				
			<div class="alberoEndo" id="endoSelezionati" style="position:relative;width: 90%px !important; border:1px solid #EEE; border-right:0 solid; padding-top: 10px; float: none; margin: 0px; display:none" >
			  <p style="padding-bottom: 10px;"><b>Altri Endoprocedimenti attivati</b></p>
				  <ul>
				  	<li class="famigliaEndo">
					  <ul id="endoAttivati_id" style="list-style-type: none;">
					  </ul>
				  	</li>
			  	</ul>
			</div>
			
			
	
<!-- END   -->		
		
		
		
		<%
		    if(altriEndo != null && altriEndo.getEndosPerFamigliaCategoria().size() > 0){
		%>
		
		
			<div style="padding-bottom: 20px;padding-top: 10px;">
		    	<input type="button" id="procedi_id" class="bottone-cart"  value="Avanti" />
		    	<input type="button" class="bottone-cart" onclick="document.location.href='<%= request.getSession().getAttribute(WebConstants.RETURNTO)%>'" value="Chiudi"/>
		    </div>	
		
		    <div class="alberoEndo" id="elencoAltriEndo">
	   			<div class="elenco-endo-title">Altri Endoprocedimenti attivabili</div>
	   			<div style="padding-left: 20px; padding-bottom: 20px">
   				<input size="45" placeholder="procedimento da ricercare...." type="text" id="endoFilter_id" name="endoFilter" value="" onblur="cercaEndo(this)" onkeyup="cercaEndo(this)"/>
   				</div>
		<% 
				Iterator<String> famiglieIter = altriEndo.getFamiglieKeySet().iterator();
				while(famiglieIter.hasNext()){
				    String famiglia = famiglieIter.next();
		%>
			<ul>
	    	<li class="famigliaEndo">
	    		<%= famiglia %>
	    		<%
	    			Iterator<String> categorieIter = altriEndo.getCategorieKeySetPerFamiglia(famiglia).iterator();
	    			while(categorieIter.hasNext()){
	    			    String categoria = categorieIter.next();
	    			    List<EndoFACCT> elencoEndo = altriEndo.getElencoEndoPerFamigliaCategoria(famiglia, categoria);
	    		%>
	    		<ul>
	    			<li class="tipoEndo">
	    			<%= categoria %>
				    	<ul>
			    <%
				        for(int i = 0; i < elencoEndo.size(); i++, count++){
				            count++;
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = endoNoCartAttivi.contains(String.valueOf(endo.getCodiceInventario()) + "") || (endo.isEndoCART() && endoAttivi.contains(endo.getCodiceBDR())) ? "checked=\"checked\"" : "";
				    		String value = endo.isEndoCART() ? endo.getCodiceBDR() : endo.getIdcomune() + "|" + String.valueOf(endo.getCodiceInventario()) + "";
				    		StringBuilder idsb = new StringBuilder("endo_");
				    		if(!endo.isEndoCART()){
				    		    idsb.append("nocart_");
				    		}
				    		idsb.append(count);
			    %>
						<li class="endo">
							<dl>
								<dd>
									<label class="altriendoselezionati" data-count="<%= count%>" data-value="<%= value%>" data-iscart="<%= endo.isEndoCART()%>" title="clicca per aggiungere"><%=endo.getDescrizione()%></label>
									
									
											<%
											String urlInfoEndo =  urlSchedaEndo.replaceAll("__ID__", String.valueOf(endo.getCodiceInventario())).replaceAll("__IDCOMUNE__", endo.getIdcomune());
											if(endo.getIdEndoLoc() != null){
												urlInfoEndo = urlInfoEndo.replaceAll("__IDCOMUNEENDOLOC__", StringUtils.defaultString(endo.getIdEndoLoc().getIdcomune()));
												urlInfoEndo = urlInfoEndo.replaceAll("__CODICEENDOLOC__", (endo.getIdEndoLoc().getCodice()==null?"":String.valueOf(endo.getIdEndoLoc().getCodice())));
											}
											%>								
										<a class="link-scheda-endo" href="<%= urlInfoEndo%>"><img src="<%=request.getContextPath()%>/images/info_endo.gif"/></a>
										<% if(StringUtils.isNotBlank(endo.getNote())){ %>
										<a class="help-endo" id="help-endo-<%=idsb.toString()%>" href="#"><img src="<%=request.getContextPath()%>/images/help.gif"/></a>
										<script type="text/javascript">
										$(function(){
											$('#help-endo-<%=idsb.toString()%>').tooltip({
												content: "<%=endo.getNote()%>",
												items: 'a'
											});
										});
										</script>
																
									<%
										}
										if(!checked.equals("")){ 
									%>
									<script type="text/javascript">
									$(function () {
											appendNewElementSelected('<%= value%>');
									});
									</script>									
									<%} %>
									
									
									
									<%if(endo.getSubEndos().size()>0){ %>
										
									<%   for(int j = 0; j < endo.getSubEndos().size(); j++){
									    	count+=1;
									    	
								    		EndoFACCT endo2 = endo.getSubEndos().get(j);
								    		boolean endo2Obbbligatorio = endo2.isObbligatorio();
								    		String checked2 = endo2.isObbligatorio()? " checked=checked " : "";
								    		String keepChecked =  endo2.isObbligatorio()? " onclick=keepChecked() " : "";
								    		String readOnly2 = endo2.isObbligatorio()? "readonly=\"readonly\"" : "";;
								    		
								    		StringBuilder idsb2 = new StringBuilder("endo_");
								    		if(!endo2.isEndoCART()){
								    			idsb2.append("nocart_");
								    		}
								    		idsb2.append(count);
								    		String name2 = endo2.isEndoCART() ? "endoAttivi" : "endoNoCartAttivi";
								    		String value2 = endo2.isEndoCART() ? endo2.getCodiceBDR() : endo2.getIdcomune() + "|" + String.valueOf(endo2.getCodiceInventario()) + "";	
										%>
											<input class="subEndoTpl<%=value %>" 
												id="<%=idsb2.toString()%>TPL" type="hidden" 
												name="<%=name2%>TPL" value="<%= value2%>" 
												data-value="<%= value2%>" 
												data-id="<%=idsb2.toString()%>" 
												data-tipo="<%=name2 %>" 
												data-checked="<%=checked2%>"
												data-descrizione="<%=endo2.getDescrizione() %>"
												data-keep="<%=keepChecked %>" 
												/>
									
									<%	}%>
										
									<%  }%>
									
								</dd>
							</dl>
						</li>
				<%
					    }
			    %>
			    		</ul>
			    	</li>
			    </ul>
			    <%
	    			}
			    %>
		    </li>
		    </ul>
			    <%
	    		}
			    %>
		</div>
	    <%
			}
		%>
		<% 	
			if(!request.getAttribute("valoreAzioneAvvio").toString().equals(request.getAttribute("tipoAzione")) && count == 0){
		%>
			<div class="descrizione">
				Tra qualche secondo sarete rediretti alla funzionalità di compilazione della domanda.
			</div>
				<%-- <input type="hidden" name="tipoAzione" value="${tipoAzione}" /> --%>
				<script type="text/javascript">
				$(document).ready(function() {
					$.blockUI();
					$('#target').submit();					
				});
				</script>
		<%
			}
		%>
		    <input type="hidden" name="codiceAttivitaBdr" value="${presentazioneDomandaCommand.codiceAttivitaBdr }"/>
		    <input type="hidden" name="nomeAttivitaBdr" value="${presentazioneDomandaCommand.nomeAttivitaBdr }"/>
		    <input type="hidden" name="idAlberoProc" value="${presentazioneDomandaCommand.idAlberoProc }"/>
		    <input type="hidden" name="returnTo" value="${presentazioneDomandaCommand.returnTo }"/>
		    <input type="hidden" name="idDomandaFo" value="${presentazioneDomandaCommand.idDomandaFo }"/>
		    <input type="hidden" name="token" value="${presentazioneDomandaCommand.token }"/>
		    <input type="hidden" name="codicecomune" value="${presentazioneDomandaCommand.codicecomune }"/>
		    <input type="hidden" name="endoCount" value="<%= count%>"/>
		    <input type="hidden" name="lavoriSuFabbricati" value="${presentazioneDomandaCommand.lavoriSuFabbricati }"/>
		    <c:choose>
		    	<c:when test="${not empty presentazioneDomandaCommand.interventiLocali}">		    		
		    		<c:forEach items="${presentazioneDomandaCommand.interventiLocali}" var="intLoc">
		    			<input type="hidden" name="interventiLocali" value="${intLoc}" />
		    		</c:forEach>		    		
		    	</c:when>
		    </c:choose>
		    
		    
		    
		    <div>
		    	<input type="button" id="procedi_id_2" class="bottone-cart"  value="Avanti" />
		    	<input type="button" id="chiudi"  class="bottone-cart" onclick="document.location.href='<%= request.getSession().getAttribute(WebConstants.RETURNTO)%>'" value="Chiudi"/>
		    </div>
	    </form>
	    <script type="text/javascript">
	    
	    function checkEndo(){
	    	var valoreSelezionato = $('input[name="tipoAzione"]:checked').val();
	    	if(valoreSelezionato == '<%=AzioneType.AVVIO%>'){		    	
	    		$('input[name="lavoriSuFabbricati"]').val('false');
	    	}else{
	    		$('input[name="lavoriSuFabbricati"]').val('true');
	    	}
	    	$('#target').attr('action','<%=request.getContextPath()%>/cart/elencoEndo.htm');
			$.blockUI();
			$('#target').submit();						    	
	    }
	    
	    var dialogEndo = null;
	    $(document).ready(function() {
	    	
	    	$("#procedi_id").click(submitFormData);
	    	
	    	$("#procedi_id_2").click(submitFormData);
	    	
	    	dialogEndo = $('#descrizioneEndo').dialog({
				width: 800,
				height: 600,
				title: "Dettagli dell\'endoprocedimento",
				modal: true,
				autoOpen: false,
				open: function () {
					$(this).find('#accordion').accordion({ header: "h3", autoHeight: false });
					$(this).find('tr:nth-child(2n+1)').addClass('rigaAlternata');
				}
			});
	  		collegaClickHandlerADettagliProcedimenti();
	    });
	    
	    keepChecked = function(event){
	    	$(event.target).attr('checked','checked');
	    };
	    
	    function submitFormData(event){
			$.blockUI();
			
			$('#target').submit();
	    	event.preventDefault();
			return false;
	    }
	    
	    function collegaClickHandlerADettagliProcedimenti() {

	    	$('.link-scheda-endo').click(function (e) {
	    		e.preventDefault();
	    		var url = $(this).attr('href');
	    		$.blockUI();
	    		try{
	    		dialogEndo.load(url, function () { 
	    			
	    			dialogEndo.dialog('open');
	    			$.unblockUI();
	    		});
	    		}catch(err){	    			
	    			$.unblockUI();	    			
	    		}
	    	});
	    }

		
	    </script>
	</div>
	<%
		}
		else{
	%>
	    <div><span class="error"><%=errorMessage %></span></div>
	<%
	}
	%>
    <div id="descrizioneEndo" />
</body>
</html>