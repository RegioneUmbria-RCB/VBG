<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocEndoLoc"%>
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
<title>Definizione dell'intervento</title>
<style>

	.altriendoselezionati{
		cursor: url(../images/clickon.png), auto;
	}

	.altriEndoAttivati{
		background: #e0e0e0;
		font-style: italic;
		border: 1px dotted #000000;
	}
	.interventoScelto{	
		padding: 10px;
		color: maroon;
		font-size: 1.1em;
	}
</style>		
</head>
<%
List<AlberoprocEndoLoc> alocs = (List<AlberoprocEndoLoc>)request.getAttribute("interventiLocalis");
String errorMessage = (String)request.getAttribute("error");
String urlSchedaEndo = "../dizionario/popupSchedaSpiegazioneEndo1.htm?id=__ID__&idcomuneinventario=__IDCOMUNE__&idComuneEndoLoc=__IDCOMUNEENDOLOC__&codiceEndoLoc=__CODICEENDOLOC__";
// "../dizionario/popupSchedaSpiegazioneEndo1.htm?id=__ID__&idcomuneinventario=__IDCOMUNE__";//request.getSession().getAttribute("baseAreaRiservataMsUrl") + "/Public/mostraDettagliEndo.aspx?IdComune="+ ORMHelper.getIdcomuneAlias()+"&fromAreaRiservata=True&print=False&Id=";
%>
<body>

	<jsp:include page="../includes/messaggio_aggiornamento.jsp" >
			<jsp:param name="settimeout" value="false"></jsp:param>
	</jsp:include>	 
	<jsp:include page="../includes/alert.jsp" >
			<jsp:param name="commandName" value="presentazioneDomandaCommand"></jsp:param>
	</jsp:include>
	<div>
	    <form action="<%=request.getContextPath()%>/cart/initPratica.htm" id="target" name="presentazioneDomandaCommand" method="post" >
		
	    <div class="alberoEndo" id="elencoEndoNecessari">
   			<div class="elenco-endo-title">Definizione dell'intervento</div>
   			<div style="padding-bottom: 10px;">
			E' stata selezionata la seguente attività ed intervento:
				<div class="interventoScelto">
					${descrizioneAttivita} - ${ stp2.stpTipologieEndo2.descrizione }
				</div>
				Selezionare l'azione/intervento specifico che si applica alla tipologia di domanda che si intende produrre.
			</div>
			<ul>
	    	<li class="famigliaEndo">
	    		<ul>
	    			<li class="tipoEndo" style="padding-bottom: 10px;padding-top: 10px;">
	    			Lista degli interventi previsti per l'attività
	    			<p />
				    	<ul>
			<%
			
			String checked_intervento = " checked='checked' ";
			%>
				    	
				    	<c:if test="${fn:toUpperCase(stp2.stpTipologieEndo2.descrizione) eq 'VARIAZIONE'}">


			<%
			
			checked_intervento  = " ";
			%>				    	
				    	
				    	
					    	<li class="endo" style="padding-top: 10px;">
								<dl>
									<dd>
										<input id="endo_zero" 
											type="radio" 
											name="interventiLocali" value="" />
										<label for="endo_zero" style="text-transform: capitalize">${ stp2.stpTipologieEndo2.descrizione }</label>																		
										<a class="link-scheda-endo-zero" href=""><img src="<%=request.getContextPath()%>/images/help.gif"/></a>			
										
										<div style="display: none; padding: 10px;" id="endo_zero_help">
											
											Questa azione/intervento è di tipo generico e deve essere utilizzata nel caso in cui non sono applicabili 
											le altre azioni/interventi specifici. <br />
											Questa tipologia prevede che la modulistica necessaria venga reperita dal richiedente attraverso il portale dell’ente. <br /> 
											 Tale modulistica dovrà essere allegata alla domanda nell’apposita sezione.
										</div>
																
									</dd>
								</dl>
							</li>
						</c:if>
			    <%		int count=1;
				        for(int i = 0; i < alocs.size(); i++){
				            
				            
				            if(i>0){				        	 
				        		checked_intervento="";
				            }
				            
				            AlberoprocEndoLoc endo = alocs.get(i);
				    		String value = "ALOCS--"+endo.getId().getIdcomune()+"|"+endo.getId().getCodice(); // endo.getInventarioprocedimenti().getId().getIdcomune()+"|"+endo.getInventarioprocedimenti().getId().getCodice();
				    		String id = "interventi_locali_"+endo.getInventarioprocedimenti().getId().getIdcomune()+"_"+endo.getInventarioprocedimenti().getId().getCodice();
				    		boolean necessario = false;
				    		if(endo.getFlagNecessario()!=null){
				    			if(endo.getFlagNecessario().booleanValue()){
				    				necessario = true;
				    		
				    			}
				    		}
				    		
				    		String descrizione = endo.getDescrizione();
				    		if(StringUtils.isBlank(descrizione)){
				    		    descrizione = endo.getInventarioprocedimenti().getProcedimento();
				    		}
			    %>
			    
						<li class="endo">
							<dl>
								<dd>
									<input id="<%= id %>_<%=i %>" 
										type="radio" 
										name="interventiLocali" value="<%= value%>" <%=checked_intervento %> />
									<label for="<%=id%>_<%=i %>"><%=descrizione%></label>
																		<%
										String urlInfoEndo =  urlSchedaEndo.replaceAll("__ID__", String.valueOf(endo.getInventarioprocedimenti().getId().getCodice())).replaceAll("__IDCOMUNE__", endo.getInventarioprocedimenti().getId().getIdcomune());
										
										    urlInfoEndo = urlInfoEndo.replaceAll("__IDCOMUNEENDOLOC__", StringUtils.defaultString(endo.getId().getIdcomune()));
										    urlInfoEndo = urlInfoEndo.replaceAll("__CODICEENDOLOC__", String.valueOf(endo.getId().getCodice()));
										
										%>
																									
									<a class="link-scheda-endo" href="<%= urlInfoEndo %>"><img src="<%=request.getContextPath()%>/images/help.gif"/></a>									
								</dd>
							</dl>
						</li>
				<%
					    }
			    %>
			    		</ul>
			    	</li>
			    </ul>
			    
		    </li>
		    </ul>
		    
		     
			    
		</div>

		    <input type="hidden" name="codicecatastalecomune" value="${param.codicecatastalecomune }"/>
		    <input type="hidden" name="id" value="${param.id}"/>
		    <p >&nbsp;</p>
		    
		    <div>
		    	<input type="button" id="procedi_id_2" class="bottone-cart"  value="Avanti" />
		    	<input type="button" id="chiudi"  class="bottone-cart" onclick="document.location.href='<%= request.getSession().getAttribute(WebConstants.RETURNTO)%>'" value="Chiudi"/>
		    </div>
	    </form>
	    <script type="text/javascript">
	    
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
	    	var checked = $('input[name=interventiLocali]:checked', '#target').val();
			if(checked == undefined){
				alert('Attenzione!\n La scelta dell\'intervento obbligatoria');
				return;
			}
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
	    	
	    	$('.link-scheda-endo-zero').click(function (e) {
	    		e.preventDefault();
	    		
	    		
	    		$('#endo_zero_help').dialog({
	    		      height: 200,
	    		      width: 550,
	    		      title: "Dettagli dell\'endoprocedimento",
	    		      modal: true
	    		});
	    		
	    		
	    		
	    	});
	    		
	    	
	    	
	    	
	    }

		
	    </script>
	</div>

    <div id="descrizioneEndo" />
</body>
</html>