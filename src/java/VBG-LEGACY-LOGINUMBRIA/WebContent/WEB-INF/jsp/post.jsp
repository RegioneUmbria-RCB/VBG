<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="it.gruppoinit.auth.util.AuthCostants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<%
	String returnTo = (String)request.getAttribute("return_to");
	
	boolean trovato = false;
	
	if(returnTo!=null && returnTo.indexOf("AUTH_TYPE")>=0){
	    trovato = true;
	}
	%>
	<form action="${return_to }" name="postToCaller" method="post">
		
		<c:forEach items="${returnToAttrs }" var="reqAttr">
			<%if (trovato){ %>		
				<c:if test="${ reqAttr.key ne 'AUTH_TYPE'}">
					<input type="hidden" name="${reqAttr.key }" value="${reqAttr.value }"/>		
				</c:if>				
			<%}else{ %>
				<input type="hidden" name="${reqAttr.key }" value="${reqAttr.value }"/>		
			<%} %>
						
		</c:forEach>
	</form>
	<script type="text/javascript">
	<!--
 		document.postToCaller.submit();
	//-->
	</script>
</body>
</html>