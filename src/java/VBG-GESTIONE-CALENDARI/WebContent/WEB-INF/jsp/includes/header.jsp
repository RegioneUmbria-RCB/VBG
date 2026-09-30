<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!-- head -->	
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" /> 
		<title><fmt:message key="label.appname" /> - <decorator:title /></title>
		<!-- css -->
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/<spring:theme code="layout"/>" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/<spring:theme code="style"/>" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/<spring:theme code="jquery"/>" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/<spring:theme code="jmesa"/>" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/<spring:theme code="jquery.tooltip"/>" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/<spring:theme code="jquery.fileupload"/>" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/<spring:theme code="menu"/>" />
		<!-- scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-it.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jstree.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tooltip.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/gruppoinit.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/autoNumeric-1.7.5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tmpl.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/menu.js"></script>
		<decorator:head />
		<script type="text/javascript">
			
		$(document).ready(function(){
				
				$("input:submit").button();
				$("input:file").button();
				$("input:button").button();
				$("button").button();
				$(".button").button();
				$(".help_image").tooltip();		
				$(".error_image").tooltip();	
				
				$.datepicker.regional['it'];
				$.datepicker.setDefaults( {
					inline: true,
					showOn: "both", 
					buttonImageOnly: true, 
					buttonImage: "images/cal.gif",
					dateFormat: "dd/mm/yy",
					changeYear: true,
					yearRange: "1900:+00"});
				
				$(".titolo_sezione").each(function () {
					if ($(this).hasClass('toggle')){
						var titolo = $(this).html();
						$(this).html('[-] '+titolo).css("cursor", "pointer");
						$(this).click(function() {
						  if ($(this).text().slice(0,3) === '[-]') {
							  $(this).next().hide();
							  $(this).html('[+] '+titolo);
			              }else{
			            	  $(this).html('[-] '+titolo);
			            	  $(this).next().show();
			              }
						});
					}
				});
			});
		</script>
<!-- head end -->