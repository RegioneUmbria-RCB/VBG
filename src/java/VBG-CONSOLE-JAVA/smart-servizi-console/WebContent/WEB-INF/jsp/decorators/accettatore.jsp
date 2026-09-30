<%@page import="it.gruppoinit.pal.gp.core.domain.Comuni"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="javax.jms.Session"%>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%String  vJS = "3.11_2017-10-24_15.11";%>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<title><decorator:title /></title>
		<link href="https://fonts.googleapis.com/css?family=Merriweather:400,700" rel="stylesheet" type="text/css">
		<link type="text/css" href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" />		
		<link type="text/css" href="${pageContext.request.contextPath}/css/pager.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.11.1.custom.css" rel="stylesheet" />	
		<link type="text/css" href="${pageContext.request.contextPath}/css/jquery.tooltip.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/autocompiler/init-autocompiler.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/jmesa.css" rel="stylesheet" />
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/additional-methods.min.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/localization/messages_it.min.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.11.1.custom.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-it.js?<%=vJS %>"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.blockUI.js?<%=vJS %>"></script>	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.min.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.min.js?<%=vJS %>"></script>		 
		<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tooltip.min.js?<%=vJS %>"></script> --%>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tmpl.min.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/gruppoinit.js?<%=vJS %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/autocompiler/init-autocompiler.js?<%=vJS %>"></script>
		<%-- FOGLI DI STILE AREA RISERVATA --%>		
		<link id="ctl00_ctl00_cssIncludeLink" href="${pageContext.request.contextPath}/css/less/less.include.css" type="text/css" rel="stylesheet" />		
		<link id="ctl00_ctl00_cssLink" href="${pageContext.request.contextPath}/css/stili2.css" type="text/css" rel="stylesheet" />
		<style>
			.logo-star{
				height: auto; 
		    	width: auto; 
		    	max-width: 50px; 
		    	max-height: 50px; 
				float: left;			
			}
			
			.logo-aurit{
				height: auto; 
		    	width: auto; 
		    	max-width: 200px; 
				float: left;			
			}
			
			.logo-spid-agid{
				height: auto; 
		    	width: auto; 
		    	max-width: 300px; 
		    	max-height: 300px; 
				float: right;
			}
			.titolo-app{
				padding-top: 13px;
				padding-left: 20px;
				float: left;
				font-size: 1em;
				color: #b71218;
				text-transform: uppercase;
		    	text-shadow: #bbbbbb 3px 3px 3px;
		    	font-size: large;
		    	font-weight: bold;
			}
		</style> 
		<decorator:head />
		<script type="text/javascript">
			$(document).ready(function(){	
				$.datepicker.regional['it'];
				$.datepicker.setDefaults({
					inline: true,
					dateFormat: "dd/mm/yy",
					changeYear: true,
					yearRange: "1900:+00"});
				// correggiAllineamentoImmagine();
				$('#ui-datepicker-div').hide();
				$("input:submit").button();
				$("input:file").button();
				$("input:button").button();
				$("button").button();
				$("submit").button();
			});	
		</script>		
	</head>
	<body>
		<div id="hrTop">
		</div>
		<div id="messaggioAggiornamento_id" style="width:100%; text-align:center; font-size:20px; color:#FF0000; padding:40px; display:none"></div>
<%--
		<%if ("A461,051005,F605,B984,C407,C847,M321,F452,F598,F605,E680,H153,H157,H305,H901,L019,L833,L913".indexOf(it.gruppoinit.pal.gp.core.dao.helper.ORMHelper.getIdente())>=0){ %>
					<div id="messaggioAggiornamento_id" style="width:100%; text-align:center; font-size:20px; color:#FF0000; padding:40px;">
					Attenzione!!! Oggi pomeriggio le pratiche in sospeso saranno eliminate per aggiornamenti applicativi.
					</div>
		<%} %>
--%>		
		<div style="margin: 8px; position: relative;">
				<%if(request.getSession().getAttribute(WebConstants.ACCESSO_SERVIZIO_COMUNICA) != null){ %>
					<img src="${pageContext.request.contextPath}/images/comunica.png" style="width: 120px; float:left;" alt="Logo Comunica" />	
					<img src="${pageContext.request.contextPath}/images/logo_rete_regionale_suap.png" alt="Logo " style="float:right; width: 68px; margin-right: 10px;" />					
					<div style="padding-left:20px; font-weight:bold;font-size: 25px; color: #CCCCCC;float:left;">
						<span style="color:#FFCC00">Scia</span> in <span style="color:#FFCC00">ComUnica</span>
						<div style="clear: both; color:#666666; font-weight: normal; font-size: 16px; "><%=request.getSession().getAttribute(WebConstants.ENTE_IN_SESSION_VARIABLE_NAME) %></div>
					</div>
				<%}else{%>
					<%if(request.getSession().getAttribute(WebConstants.PRODOTTO).equals(WebConstants.PRODOTTO_AURIT)){ %>
						<img src="${pageContext.request.contextPath}/images/logo_rete_regionale_aurit.png" alt="Logo accettatore" class="logo-aurit" />
					<%}else{ %>
						<img src="${pageContext.request.contextPath}/images/logo_rete_regionale_suap.png" alt="Logo " class="logo-star" />			
					<%} %>
					<img src="${pageContext.request.contextPath}/images/spid-agid-logo-lb.png" alt="Logo SPID AGID" class="logo-spid-agid" />
					<div class="titolo-app">
					
					<%
					String deployContext = (String)request.getSession().getAttribute(WebConstants.CONFIG_APP_DEPLOY_CONTEXT);
					String separator = "<br/>";
					if(request.getSession().getAttribute(WebConstants.PRODOTTO).equals(WebConstants.PRODOTTO_AURIT)){ %>
						Accettatore Unico Regionale Istanze Telematiche
						<% if(StringUtils.isNotBlank(deployContext)){ %>	
						<%= separator + deployContext %>	
					<%	}
					}else{ %>
						<fmt:message key="label.nome-applicazione" />	
						<% if(StringUtils.isNotBlank(deployContext)){ %>	
						<%= separator + deployContext %>	
					<%	}		
					} %>
					
						
						
					</div>			
			
					<div style="clear: both;"></div>
					<div style="float: right;">
						<%
						Comuni c = (Comuni) request.getSession().getAttribute(WebConstants.COMUNE_SELEZIONATO_DOMANDA_ATTIVA_SESSION_VARIABLE_NAME); 
						if(c == null){
						    c = new Comuni();
						}
						%>
						<div>Ente: <span style="font-weight: bold;"><%=request.getSession().getAttribute(WebConstants.ENTE_IN_SESSION_VARIABLE_NAME) %></span></div>
						<div>Comune: <span style="font-weight: bold;"><%= StringUtils.defaultString(c.getComune())%></span></div>
								<span id="utenteconnesso_container_id"></span>
								<span id="utenteconnesso_container_id_hidden" style="display: none;"><spring-security:authentication property="principal.anagrafe" /></span>
								<script type="text/javascript">
		
								$(function(){ 	
									
									$.ajax({
								        type: "GET",
								        url: "${pageContext.request.contextPath}/ajax/ajaxGetUtente.htm",
								        data: "",
								        dataType: "text",
										success: function(data) {
											if(data!=''){
												$('#utenteconnesso_container_id').html(data);
											}
									  },
									  error: function(jqXHR, textStatus, errorThrown){								  
										  		$('#utenteconnesso_container_id').html($('#utenteconnesso_container_id_hidden').html());
											}
									});
									
								});
								
								</script>
					</div>

				<%if(request.getSession().getAttribute(WebConstants.PRODOTTO).equals(WebConstants.PRODOTTO_AURIT)){ %>
				
					<%}else{ %>
	<div id="manuale_div_id" style="clear: both; width: 410px;">
		<a href="#" onclick="jQuery('#manuale_star').toggle();" style="color:#b71218; font-weight: bold;">Clicca QUI per scaricare il Manuale di STAR e sapere come ...</a>
		<div id="manuale_star" style="display: none; padding: 8px; background-color: rgb(235, 235, 235); width: 400px; border: 1px solid rgb(192, 192, 192);">
 			     <div style="width:100%;"><a href="#" onclick="jQuery('#manuale_star').toggle();" style="border: 0"><img style="float: right;" src="${pageContext.request.contextPath}/images/cross.gif"></a></div>	
		 <ul style="padding-left: 10px; line-height:90%">
		  <li style="list-style: circle">Compilare una pratica</li>
		  <li style="list-style: circle">Riprendere una pratica compilata solo in parte</li>
		  <li style="list-style: circle">Firmare una pratica</li>
		  <li style="list-style: circle">Trasmettere la pratica</li>
		  <li style="list-style: circle">Cosa fare in caso di problemi o dubbi</li>
		</ul>					  
		  Scarica il <a href="#" class="linkEsterno" style="color:#b71218; font-weight: bold; " title="scarica il manuale di " onclick="NuovaFinestra=window.open('http://www.suap.toscana.it/suap-portal/manuale-star','NuovaFinestra','menubar=no, toolbar=no, status=no, scrollbars=yes,resizable,width=900,height=550'); return false;">Manuale di STAR</a>
				<%if(request.getSession().getAttribute(WebConstants.ACCESSO_SERVIZIO_COMUNICA) != null){ %>
				  <br/>Scarica il <a href="#" class="linkEsterno" style="color:#b71218; font-weight: bold; " title="scarica il manuale di COMUNICA" onclick="NuovaFinestra=window.open('https://servizi2.suap.toscana.it/manuali/Scia in Comunica - Manuale operativo.pdf','NuovaFinestra','menubar=no, toolbar=no, status=no, scrollbars=yes,resizable,width=900,height=550'); return false;">Manuale di COMUNICA</a>
				<%} %>
		</div>
	</div>
				<%} %>
				
					
						
			<%} %>		
		</div>	
		<div id="spacer" style="clear: both;"><% if(request.getSession().getAttribute("_WHERE_IS_FRED_")!=null){out.print(request.getSession().getAttribute("_WHERE_IS_FRED_"));} %></div>
		<div style="margin: 8px; margin-bottom: 45px;">
			<decorator:body />
		</div>
		<%if(request.getSession().getAttribute(WebConstants.ACCESSO_SERVIZIO_COMUNICA) != null){ %>
			<div style="background-color:#fff; height:40px;width:100%; position:fixed; bottom: 0;left: 0;">
				<span style="float:left; padding-left:20px; padding-top:10px;">
				<a target="_blank" href="http://www.regione.toscana.it/privacy" class="linkEsterno" style="font-weight: bold; font-size: 10px;">privacy</a>
				</span>
				<img src="${pageContext.request.contextPath}/images/logoRT.png" style="height: 40px; padding-top: 2px; position: absolute; right: 162px;  top: 0" />
				<img src="${pageContext.request.contextPath}/images/unionCamereToscana.png" style="height: 35px;padding-top:5px; position: absolute;  right: 0;"/>
			</div>
	   <%} %>	
	</body>
</html>