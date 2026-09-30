<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ include file="../includes/taglibs.jsp" %>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<title>Area Riservata - <decorator:title /></title>	
		
		<link type="text/css" href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet" />		
		<link type="text/css" href="${pageContext.request.contextPath}/css/pager.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.11.1.custom.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/jmesa.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/jquery.tooltip.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/autocompiler/init-autocompiler.css" rel="stylesheet" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.11.1.custom.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-it.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tooltip.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tmpl.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/gruppoinit.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/autocompiler/init-autocompiler.js"></script>
		<%-- FOGLI DI STILE AREA RISERVATA MS --%>
		<link id="ctl00_ctl00_cssIncludeLink" href="<%=request.getSession().getAttribute("baseAreaRiservataMsUrl")%>/css/less/less.include.css" type="text/css" rel="stylesheet" />
		<link id="ctl00_ctl00_cssLink" href="<%=request.getSession().getAttribute("baseAreaRiservataMsUrl")%>/css/stili2.css" type="text/css" rel="stylesheet" />
		<decorator:head />
		<script type="text/javascript">
		
		function correggiAllineamentoImmagine() {

            var image = $('#logo > a > img');

            if (!image[0].complete) {
                   setTimeout(correggiAllineamentoImmagine, 100);
            }
            else {
                   var altezzaLogo = $('#logo').height();
                   var altezzaIntestazione = $('#nomeComuneV2').height();

                   var nuovoTop = (altezzaLogo - altezzaIntestazione) / 2;
                   $('#nomeComuneV2').css('padding-top', nuovoTop + 'px');
            }
	     }

			$(document).ready(function(){	
				
				$.datepicker.regional['it'];
				$.datepicker.setDefaults( {
					inline: true,
					dateFormat: "dd/mm/yy",
					changeYear: true,
					yearRange: "1900:+00"}); 
				
					correggiAllineamentoImmagine();
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
		
			<div id="intestazionePagina">
				<div id="hrTop">
				</div>
				<div id="logo">
					<a id="ctl00_ctl00_lnkTornaAllaHome" title="Torna alla pagina iniziale dell'area riservata" 
					href="<%=request.getSession().getAttribute(WebConstants.RETURNTO)%>"><img id="ctl00_ctl00_imgLogo" src="<%=request.getSession().getAttribute("baseAreaRiservataMsUrl")%>/MostraRisorsa.ashx?IdComune=<%=ORMHelper.getIdcomuneAlias()%>&amp;IdRisorsa=logo_suap" style="border-width:0px;" /></a>
				</div>

				<div id="nomeComuneV2" style="padding-top: 11.5px;">
					<h1>Area riservata</h1>
					<h2><span id="ctl00_ctl00_lblNomeComune2" style="font-size: 12px;"><c:import url="/ajax/findCurrentSoftware.htm" /></span></h2>
				</div>

				<div id="datiUtenteV2">
					Utente connesso:
					<span id="ctl00_ctl00_lblNomeUtente2"><spring-security:authentication property="principal.anagrafe" /></span>
				</div>

				<div class="clear"></div>
				<div id="datiUtente">
					<span id="preNomeUtente">
						Utente Connesso:
					</span>
					<span id="ctl00_ctl00_lblNomeUtente"><spring-security:authentication property="principal.anagrafe" /></span>
				</div>
				<div id="spacer">
				</div>
			</div>
			<div  style="margin: 10px;">
				<decorator:body />
			</div>
		
	</body>
</html>