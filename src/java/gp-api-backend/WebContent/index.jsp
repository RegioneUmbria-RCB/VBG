<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%--
	per avviare l'applicativo l'url da digitare deve essere del tipo: 
	http://<host>:<port>/<context>/?idcomunealias=<alias> 
--%>
<c:redirect url="welcome/start.htm">
	<c:if test="${not empty param.idcomunealias}">
		<c:param name="<%=WebConstants.IDCOMUNE_ALIAS %>" value="${param.idcomunealias}" />
	</c:if>
	<c:param name="<%=WebConstants.SOFTWARE %>" value="<%=WebConstants.SOFTWARE_TT %>" />
	<c:param name="_timestamp" value="<%=String.valueOf(System.currentTimeMillis()) %>" />
</c:redirect>