<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%--
	per avviare l'applicativo l'url da digitare deve essere del tipo: 
	http://<host>:<port>/<context>/?idcomunealias=<alias> 
--%>
<%
long times = System.currentTimeMillis();
%>
<script type="text/javascript">
function goTo(){
	location.href='${pageContext.request.contextPath}/welcome/start.htm?idcomunealias=${idcomunealias}&software=TT&_timestamp=<%= times%>';	
}
</script>
<input type="button" value="clicca qui se non sei rediretto" onclick="goTo();"/>
