<?xml version="1.0" encoding="UTF-8" ?>
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
<title>Selezione Endoprocedimenti</title>
</head>
<%
//String tipoAz = (String)request.getAttribute("tipoAzione");
ElenchiEndoFACCT elenchiEndo =(ElenchiEndoFACCT) request.getAttribute("elenchiEndo");
List<String> endoAttivi =(List<String>) request.getAttribute("endoAttivi");
List<String> endoNoCartAttivi =(List<String>) request.getAttribute("endoNoCartAttivi");
String errorMessage = (String)request.getAttribute("error");
String urlSchedaEndo = request.getSession().getAttribute("baseAreaRiservataMsUrl") + "/Public/mostraDettagliEndo.aspx?IdComune="+ ORMHelper.getIdcomuneAlias()+"&fromAreaRiservata=True&print=False&Id=";
%>
<body>
	    <%
	        if(StringUtils.isEmpty(errorMessage)){
	            AlberoEndo endoCART = elenchiEndo.getEndoCart();
	            AlberoEndo endoNecessari = elenchiEndo.getEndoNecessari();
	            AlberoEndo endoRicorrenti = elenchiEndo.getEndoRicorrenti();
	            AlberoEndo altriEndo = elenchiEndo.getAltriEndo();
	    %>
		<%--
	<div class="descrizione">
			Selezionare l'azione e gli endoprocedimenti che si desidera attivare:
	</div>
	 --%>
	<jsp:include page="../includes/alert.jsp" >
			<jsp:param name="commandName" value="presentazioneDomandaCommand"></jsp:param>
	</jsp:include>
	<div>
	    <form action="<%=request.getContextPath()%>/cart/caricaModulistica.htm" id="target" name="presentazioneDomandaCommand" method="post">
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
	    	
	    	
		<%
			int count = 0;
		    if(endoCART != null && endoCART.getEndosPerFamigliaCategoria().size() > 0){
		%>
	    <div class="alberoEndo" id="elencoEndoId" style="">
   			<div class="elenco-endo-title">Endoprocedimenti Attivabili</div>
		<% 
				Iterator<String> famiglieIter = endoCART.getFamiglieKeySet().iterator();
				while(famiglieIter.hasNext()){
				    String famiglia = famiglieIter.next();
		%>
	    	<!-- <div class="famiglia-endo">  -->
	    	<ul>
	    		<li class="famigliaEndo">
	    		<!-- <span class="famiglia-endo-title"> -->
	    		<%= famiglia %>
	    		<!-- </span> -->
	    		<%
	    			Iterator<String> categorieIter = endoCART.getCategorieKeySetPerFamiglia(famiglia).iterator();
	    			while(categorieIter.hasNext()){
	    			    String categoria = categorieIter.next();
	    			    List<EndoFACCT> elencoEndo = endoCART.getElencoEndoPerFamigliaCategoria(famiglia, categoria);
	    		%>
	    		<!-- <div class="categoria-endo"> -->
	    			<ul>
	    			<!-- <span class="categoria-endo-title"><%= categoria %></span> -->
	    			<li class="tipoEndo"><%= categoria %>
	    			<!-- <div class="categoria-endo">  -->
			    		<ul>
			    <%
				        for(int i = 0; i < elencoEndo.size(); i++){
				            count++;
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = endoAttivi.contains(endo.getCodiceBDR()) || endoNoCartAttivi.contains(endo.getCodiceInventario() + "") || endo.isObbligatorio() ? "checked=\"checked\"" : "";
				    		String readOnly = endo.isObbligatorio() ? "readonly='readonly'" : "";
			    %>
						<li class="endo">
							<dl>
								<dd>
									<input id="endo_<%=i%>" type="checkbox" name="endoAttivi" value="<%= endo.getCodiceBDR()%>" <%=checked %> <%=readOnly %>/>
									<label for="endo_<%=i%>"><%=endo.getDescrizione()%></label>
									<a class="link-scheda-endo" href="<%=urlSchedaEndo + endo.getCodiceInventario()%>"><img src="<%=request.getContextPath()%>/images/help.gif"/></a>
					<% if(StringUtils.isNotEmpty(readOnly)){ %>
									<script type="text/javascript">
										$(document).ready(function() {
											$("#endo_<%=i%>").click(keepChecked);
										});
									</script>
					<%} %>
								</dd>
							</dl>
						</li>
				<%
					    }
			    %>
				    	</ul>
				    </li>
			    	<!-- </div> -->
			    	</ul>
			    <!-- </div> -->
			    <%
	    			}
			    %>
			    </li>
			</ul>
		    <!-- </div>  -->
	    <%
				}
			} 
			else {
			%>
 				<!-- <div><span>Nessun endoprocedimento CART attivabile per l'attività selezionata.</span></div> -->
			<%
			}
		%>
		</div>
		<%
		    if(endoNecessari != null && endoNecessari.getEndosPerFamigliaCategoria().size() > 0){
		%>
	    <div class="alberoEndo" id="elencoEndoNecessari">
   			<div class="elenco-endo-title">Endoprocedimenti Necessari</div>
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
				        for(int i = 0; i < elencoEndo.size(); i++, count++){
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = "checked=\"checked\"";
				    		String readOnly = "readonly='readonly'";
			    %>
						<li class="endo">
							<dl>
								<dd>
									<input id="endo_nocart_<%=count%>" type="checkbox" name="endoNoCartAttivi" value="<%= endo.getCodiceInventario()%>" <%=checked %> <%=readOnly %>/>
									<label for="endo_nocart_<%=count%>"><%=endo.getDescrizione()%></label>
									<a class="link-scheda-endo" href="<%=urlSchedaEndo + endo.getCodiceInventario()%>"><img src="<%=request.getContextPath()%>/images/help.gif"/></a>
									<script type="text/javascript">
										$(document).ready(function() {
											$("#endo_nocart_<%=count%>").click(keepChecked);
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
		    /*
			else {
			    */
			%>
				<%-- <div><span>Nessun endoprocedimento necessario attivabile per l'attività selezionata.</span></div> --%>
			<%
			//}
		%>
		
		<%
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
				        for(int i = 0; i < elencoEndo.size(); i++, count++){
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = endoNoCartAttivi.contains(endo.getCodiceInventario() + "") || (endo.isEndoCART() && endoAttivi.contains(endo.getCodiceBDR())) ? "checked=\"checked\"" : "";
				    		String readOnly = "";
			    %>
						<li class="endo">
							<dl>
								<dd>
									<input id="endo_nocart_<%=count%>" type="checkbox" name="endoNoCartAttivi" value="<%= endo.getCodiceInventario()%>" <%=checked %> />
									<label for="endo_nocart_<%=count%>"><%=endo.getDescrizione()%></label>
									<a class="link-scheda-endo" href="<%=urlSchedaEndo + endo.getCodiceInventario()%>"><img src="<%=request.getContextPath()%>/images/help.gif"/></a>
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
		<%
		    if(altriEndo != null && altriEndo.getEndosPerFamigliaCategoria().size() > 0){
		%>
	    <div class="alberoEndo" id="elencoAltriEndo">
   			<div class="elenco-endo-title">Altri Endoprocedimenti</div>
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
				    		EndoFACCT endo = elencoEndo.get(i);
				    		String checked = endoNoCartAttivi.contains(endo.getCodiceInventario() + "") || (endo.isEndoCART() && endoAttivi.contains(endo.getCodiceBDR())) ? "checked=\"checked\"" : "";
				    		String readOnly = "";
			    %>
						<li class="endo">
							<dl>
								<dd>
									<input id="endo_nocart_<%=count%>" type="checkbox" name="endoNoCartAttivi" value="<%= endo.getCodiceInventario()%>" <%=checked %> <%=readOnly %>/>
									<label for="endo_nocart_<%=count%>"><%=endo.getDescrizione()%></label>
									<a class="link-scheda-endo" href="<%=urlSchedaEndo + endo.getCodiceInventario()%>"><img src="<%=request.getContextPath()%>/images/help.gif"/></a>
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
		    <div>
		    	<input type="button" id="procedi_id" value="Avanti" class="bottone-cart"/>
		    	<input type="button" class="bottone-cart" onclick="document.location.href='<%= request.getSession().getAttribute(WebConstants.RETURNTO)%>'" value="Chiudi"/>
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
	    
	    $(document).ready(function() {
	    	$("#procedi_id").click(function(){
				$.blockUI();
				$('#target').submit();
			});
	    	//checkEndo();
	  	  	$('#descrizioneEndo').dialog({
				width: 600,
				height: 500,
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
	    
	    function collegaClickHandlerADettagliProcedimenti() {

	    	$('.link-scheda-endo').click(function (e) {
	    		e.preventDefault();
	    		var url = $(this).attr('href');

	    		$('#descrizioneEndo').load(url, function () { $(this).dialog('open'); });
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
    <div id="descrizioneEndo">
</body>
</html>