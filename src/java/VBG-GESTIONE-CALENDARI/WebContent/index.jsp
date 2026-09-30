<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:redirect url="home/start.htm">
	<c:param name="idcomunealias" value="GESTCAL" />
	<c:if test="${not empty param.Token}">
	<c:param name="<%=WebConstants.TOKEN %>" value="${param.Token}" />
	</c:if>
</c:redirect>

