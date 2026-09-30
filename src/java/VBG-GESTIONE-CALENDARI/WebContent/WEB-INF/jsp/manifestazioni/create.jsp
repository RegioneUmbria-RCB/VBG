<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.tipo-manifestazione" /></title>
</head>
<body>
	<div class="titolo">
		<fmt:message key="label.tipo-manifestazione" />
	</div>
	<div class="descrizione"></div>
	<table class="sezione_table">
		<tr>
			<td class="sezione_table_buttons">
				<input type="button" value="<fmt:message key='button.fiere-mostre' />" id="fiere-mostre" />
				<input type="button" value="<fmt:message key='button.feste-sagre' />" id="feste-sagre" />
				<input type="button" value="<fmt:message key='button.chiudi' />" id="chiudi" />
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		$("#fiere-mostre").click(function(){
			window.location.replace("${pageContext.request.contextPath}/manifestazioni/view.htm?tipo=FM");
		});
		$("#feste-sagre").click(function(){
			window.location.replace("${pageContext.request.contextPath}/manifestazioni/view.htm?tipo=FS");
		});
		$("#chiudi").click(function(){
			window.location.replace("${pageContext.request.contextPath}/home/start.htm");
		});
	</script>
</body>
</html>