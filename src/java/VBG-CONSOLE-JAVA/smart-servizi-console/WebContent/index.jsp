<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
	per avviare l'applicativo l'url da digitare deve essere del tipo: 
	http://localhost:8080/areariservata2/?idcomunealias=E256&software=CO 
--%>
<c:redirect url="home/start.htm">
	<c:if test="${not empty param.idcomunealias}">
	<c:param name="<%=WebConstants.IDCOMUNE_ALIAS %>" value="${param.idcomunealias}" />
	</c:if>
	<c:if test="${not empty param.Token}">
	<c:param name="<%=WebConstants.TOKEN %>" value="${param.Token}" />
	</c:if>
	<c:if test="${not empty param.software}">
	<c:param name="<%=WebConstants.SOFTWARE %>" value="${param.software}" />
	</c:if>
	<c:param name="ts" value="<%=String.valueOf(System.currentTimeMillis()) %>" />
</c:redirect>
<%--
<c:redirect url="home/start.htm">
	<c:param name="idcomunealias" value="E256" />
	<c:param name="software" value="CO" />
</c:redirect>
--%>

