<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
		<meta http-equiv="pragma" content="no-cache"></meta>
		<meta http-equiv="Cache-Control" content="no-cache" />
		<title><fmt:message key="label.nome-applicazione" /><decorator:title /></title>	
		<link type="text/css" href="${pageContext.request.contextPath}/css/layout.css" rel="stylesheet"></link>
		<link type="text/css" href="${pageContext.request.contextPath}/css/default.css" rel="stylesheet"></link>
		<link type="text/css" href="${pageContext.request.contextPath}/css/pager.css" rel="stylesheet"></link>
		<link type="text/css" href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.9.2.custom.css" rel="stylesheet"></link>
		<link type="text/css" href="${pageContext.request.contextPath}/css/jquery.tooltip.css" rel="stylesheet" />
		<link type="text/css" href="${pageContext.request.contextPath}/css/autocompiler/init-autocompiler.css" rel="stylesheet" />
		
		<link type="text/css" href="${pageContext.request.contextPath}/css/jmesa.css" rel="stylesheet"></link>
		
		<%-- 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
		 --%>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-it.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tmpl.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/gruppoinit.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tooltip.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/autocompiler/init-autocompiler.js"></script>
		 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js"></script>
		<%--
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.min.js"></script>
		--%>
		<decorator:head />
		<script type="text/javascript">
			$(document).ready(function(){
				$("input:submit").button();
				$("input:file").button();
				$("input:button").button();
				$("button").button();

				$.datepicker.regional['it'];
				$.datepicker.setDefaults( {
					inline: true,
					dateFormat: "dd/mm/yy",
					changeYear: true,
					yearRange: "1900:+00"}); 
			});
		</script>
	</head>
	<body>
		<div id="header">
			<label class="testata"><fmt:message key="label.nome-applicazione" /></label><br/>
			<%-- 
				<label class="testata2"><fmt:message key="label.ente-sde-proxy" /></label>
			--%>
		</div>
		<div id="header_user">
			<label><spring-security:authentication property="principal.anagrafe" /></label>
		</div>
		<div class="" style="padding: 10px;">
			<div class="">
				<div class="">
					<decorator:body />
				</div>				
			</div>
		</div>
	</body>
</html>