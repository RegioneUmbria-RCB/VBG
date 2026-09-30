<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ include file="../includes/taglibs.jsp" %>
<html>
	<head>
		<%@ include file="../includes/header.jsp" %>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#chiudi").click(function(){
					window.close();
				});
			});
		</script>
		<style type="text/css">
			.label {font-style: italic; background-color: #C1C1C1;}
			.value {font-weight: bold;}
			a {text-decoration: none;}
		</style>	
	</head>
	<body>
		<div>
			<decorator:body />
		</div>
	</body>
</html>