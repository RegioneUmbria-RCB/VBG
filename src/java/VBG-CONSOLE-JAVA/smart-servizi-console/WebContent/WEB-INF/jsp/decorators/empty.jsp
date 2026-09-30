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
		<%@ include file="../includes/testata.jsp" %>
		<div class="colmask leftmenu">
			<div class="colleft">
				<div class="col1">
					<decorator:body />
				</div>
				<div class="col2">
					<button id="chiudi" class="menu_button"><fmt:message key="button.chiudi" /></button>
				</div>
			</div>
		</div>
		<%@ include file="../includes/footer.jsp" %>
	</body>
</html>