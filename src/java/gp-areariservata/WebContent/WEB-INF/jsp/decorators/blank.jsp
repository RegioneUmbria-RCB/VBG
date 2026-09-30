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
	</head>
	<body>
		

					<decorator:body />

	</body>
</html>