<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ include file="../includes/taglibs.jsp" %>
<html>
	<head>
		<%@ include file="../includes/header.jsp" %>
		  <%--
		<script type="text/javascript">
			$(document).ready(function(){
				$("#nuova_istanza").click(function(){
					location.href="${pageContext.request.contextPath}/nuovaistanza/start.htm";
				});
				$("#istanze_in_sospeso").click(function(){
					location.href="${pageContext.request.contextPath}/domande/list.htm";
				});
				$("#le_mie_istanze").click(function(){
					location.href="${pageContext.request.contextPath}/istanze/search.htm";
				});
				$("#le_mie_scadenze").click(function(){
					location.href="${pageContext.request.contextPath}/scadenze/list.htm";
				});
				$("#servizi").click(function(){
					location.href="${pageContext.request.contextPath}/listaservizi/list.htm";
				});
				$("#esci").click(function(){
					location.href="${pageContext.request.contextPath}/home/logout.htm";
				});
			});
		</script>
		 --%>	
	</head>
	<body>
		<%@ include file="../includes/testata.jsp" %>
		<div class="colmask leftmenu">
			<div class="colleft">
				<div class="col1">
					<decorator:body />
				</div>
				<%--
				<div class="col2">
					<button id="servizi" class="menu_button"><fmt:message key="button.lista-servizi" /></button>	
					<button id="nuova_istanza" class="menu_button"><fmt:message key="button.nuova-istanza" /></button>
					<button id="istanze_in_sospeso" class="menu_button"><fmt:message key="button.istanze-in-sospeso" /></button>
					<button id="le_mie_istanze" class="menu_button"><fmt:message key="button.le-mie-pratiche" /></button>
					<button id="le_mie_scadenze" class="menu_button"><fmt:message key="button.le-mie-scadenze" /></button>
					<button id="esci" class="menu_button"><fmt:message key="button.esci" /></button>
				</div>
				 --%>
			</div>
		</div>
		<%@ include file="../includes/footer.jsp" %>
	</body>
</html>