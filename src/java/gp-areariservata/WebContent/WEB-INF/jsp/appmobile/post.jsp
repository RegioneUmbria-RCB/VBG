<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" deferredSyntaxAllowedAsLiteral="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link type="text/css" href="<%=request.getContextPath() %>/css/layout.css" rel="stylesheet"></link>
		<link type="text/css" href="<%=request.getContextPath() %>/css/default.css" rel="stylesheet"></link>
		<link type="text/css" href="<%=request.getContextPath() %>/css/pager.css" rel="stylesheet"></link>
		<link type="text/css" href="<%=request.getContextPath() %>/css/smoothness/jquery-ui-1.9.2.custom.css" rel="stylesheet"></link>
		<link type="text/css" href="<%=request.getContextPath() %>/css/autocompiler/init-autocompiler.css" rel="stylesheet"></link>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-ui-1.9.2.custom.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/autocompiler/init-autocompiler.js"></script>

<title>Autopost page</title>
</head>
<body>
	
	<%--  
		${ post_action }
	--%>
	
	<form action="${ post_action }" id="autopostFRM" method="post">
		<c:forEach var="m" items="${ mappaAttributiPost }">
		    <input type="text" name="${m.key}" value="${m.value}" /> <br  />
		</c:forEach>		
	</form>


		<script type="text/javascript">
			$(document).ready(function(){
				$('#autopostFRM').submit();
			});
		</script>


</body>
</html>