<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglibs.jsp" %>

		<input type="button" id="chiudi" data-custatt="session" class="bottone-cart" onclick="document.location.href='${pageContext.request.contextPath}/home/dologout.htm'" value="Chiudi"/>

