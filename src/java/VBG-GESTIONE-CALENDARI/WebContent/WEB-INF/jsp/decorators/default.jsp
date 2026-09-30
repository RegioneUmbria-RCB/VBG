<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ include file="../includes/taglibs.jsp" %>
<html>
	<head>
		<%@ include file="../includes/header.jsp" %>
		<script type="text/javascript">
			$(document).ready(function(){
				/*
				$("#accordion").accordion({
					  active: 2,
					  collapsible: true
				});
				*/
				$("#enti").click(function(){
					location.href="${pageContext.request.contextPath}/enti/list.htm";
				});
				$("#responsabili").click(function(){
					location.href="${pageContext.request.contextPath}/responsabili/list.htm";
				});				
				$("#cerca-feste-sagre").click(function(){
					location.href="${pageContext.request.contextPath}/manifestazioni/startSearch.htm?tipo=FS";
				});
				$("#cerca-fiere-mostre").click(function(){
					location.href="${pageContext.request.contextPath}/manifestazioni/startSearch.htm?tipo=FM";
				});
				$("#cerca-manifestazioni-areepubbliche").click(function(){
					location.href="${pageContext.request.contextPath}/manifareepubbliche/startSearch.htm";
				});
				$("#cerca-imprese-aree-pubbliche").click(function(){
					location.href="${pageContext.request.contextPath}/anagrafeimpresa/startSearch.htm";
				});	
				
				$("#nuovo-feste-sagre").click(function(){
					location.href="${pageContext.request.contextPath}/manifestazioni/view.htm?tipo=FS";
				});
				$("#nuovo-fiere-mostre").click(function(){
					location.href="${pageContext.request.contextPath}/manifestazioni/view.htm?tipo=FM";
				});
				$("#nuovo-manifestazioni-areepubbliche").click(function(){
					location.href="${pageContext.request.contextPath}/manifareepubbliche/view.htm";
				});
				$("#nuovo-imprese-aree-pubbliche").click(function(){
					location.href="${pageContext.request.contextPath}/anagrafeimpresa/view.htm";
				});	
				$("#import").click(function(){
					location.href="${pageContext.request.contextPath}/importdafile/start.htm";
				});
				$("#configurazione-email").click(function(){
					location.href="${pageContext.request.contextPath}/configurazioneemail/createOrview.htm";
				});
				$("#esci").click(function(){
					location.href="${pageContext.request.contextPath}/home/logout.htm";
				});
			});
		</script>
	</head>
	<body>
		<%@ include file="../includes/testata.jsp" %>
		<div class="colmask leftmenu">
			<div class="colleft">
				<div class="col1">
					<c:if test="${error != null}">
						<label style="color: red; font-weight: bold; padding-bottom: 5px">${error }</label>					
					</c:if>
					<decorator:body />
				</div>
				<div class="col2">
					<div id='cssmenu'>
						<ul>
						   <spring-security:authorize ifAnyGranted="ROLE_ADMINISTRATOR">
						   <li><a href='#' id="enti"><span><fmt:message key="button.enti" /></span></a></li>
						   <li><a href='#' id="responsabili"><span><fmt:message key="button.responsabili" /></span></a></li>
						   </spring-security:authorize>
						   <li class='has-sub'><a href='#'><span><fmt:message key="button.cerca-manifestazione" /></span></a>
						      <ul>
						         <spring-security:authorize ifAnyGranted="ROLE_GESTIONE_FESTE_SAGRE">
						         <li><a href='#' id="cerca-feste-sagre"><span><fmt:message key="button.cerca-feste-sagre" /></span></a></li>
						         </spring-security:authorize>
								 <spring-security:authorize ifAnyGranted="ROLE_GESTIONE_FIERE_MOSTRE">
						         <li><a href='#' id="cerca-fiere-mostre"><span><fmt:message key="button.cerca-fiere-mostre" /></span></a></li>
						         </spring-security:authorize>
						          <%-- COMMENTATO PERCHè NON FACEVA PARTE ANCORA DELL'ORDINE --%>
						         <%--
						         <spring-security:authorize ifAnyGranted="ROLE_GESTIONE_MANIFESTAZIONI_AREE_PUBBLICHE">
						         <li class='last'><a href='#' id="cerca-manifestazioni-areepubbliche"><span><fmt:message key="button.cerca-manifestazioni-areepubbliche" /></span></a></li>
						         </spring-security:authorize>
						         --%>
						      </ul>
						   </li>
						   <spring-security:authorize ifNotGranted="ROLE_READONLY">
						   <li class='has-sub'><a href='#'><span><fmt:message key="button.inserisci-manifestazione" /></span></a>
						      <ul>
						         <spring-security:authorize ifAnyGranted="ROLE_GESTIONE_FESTE_SAGRE">
						         <li><a href='#' id="nuovo-feste-sagre"><span><fmt:message key="button.nuovo-feste-sagre" /></span></a></li>
						         </spring-security:authorize>
								 <spring-security:authorize ifAnyGranted="ROLE_GESTIONE_FIERE_MOSTRE">
						         <li><a href='#' id="nuovo-fiere-mostre"><span><fmt:message key="button.nuovo-fiere-mostre" /></span></a></li>
						         </spring-security:authorize>
						         <%-- COMMENTATO PERCHè NON FACEVA PARTE ANCORA DELL'ORDINE --%>
						         <%--
						         <spring-security:authorize ifAnyGranted="ROLE_GESTIONE_MANIFESTAZIONI_AREE_PUBBLICHE">
						         <li class='last'><a href='#' id="nuovo-manifestazioni-areepubbliche"><span><fmt:message key="button.nuovo-manifestazioni-areepubbliche" /></span></a></li>
						         </spring-security:authorize>
						         --%>
						      </ul>
						   </li>
						   <%-- COMMENTATO PERCHè NON FACEVA PARTE ANCORA DELL'ORDINE --%>
						   <%--
						   <li class='has-sub'><a href='#'><span><fmt:message key="button.aree-pubbliche" /></span></a>
					         <ul>
					            <li><a  href='#' id="cerca-imprese-aree-pubbliche"><span><fmt:message key="button.cerca-imprese-aree-pubbliche" /></span></a>
					         	<spring-security:authorize ifAnyGranted="ROLE_GESTIONE_INSERIMENTI_ANAGRAFICHE">
					         		<li><a class='last' href='#' id="nuovo-imprese-aree-pubbliche"><span><fmt:message key="button.inserisci-imprese-aree-pubbliche" /></span></a></li>
					         	</spring-security:authorize>
					         </ul>
						   </li>
						   --%>
						   </spring-security:authorize>
						   <spring-security:authorize ifNotGranted="ROLE_READONLY">
						   <li><a href='#' id="import"><span><fmt:message key="button.carica-tracciato-xml" /></span></a></li>
						   </spring-security:authorize>
						   
						   <li class='has-sub'><a href='#'><span><fmt:message key="button.configurazioni" /></span></a>
						      <ul>
						         <li><a href='#' id="configurazione-email"><span><fmt:message key="button.configurazione-email" /></span></a></li>
						      </ul>
						   </li>
						   
						   <li class='last'><a href='#' id="esci"><span><fmt:message key="button.esci" /></span></a></li>
						</ul>
					</div>	
				</div>
			</div>
		</div>
		<%@ include file="../includes/footer.jsp" %>
	</body>
</html>