<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI --%>
<c:set var="_link" value="${param.link}"/>
<c:set var="_title" value="${param.title}"/>
<c:set var="_view" value="${param.view }"/>
<c:if test="${_view eq 'true'}">
<a class="vbg-btn btn-gis" href="${_link}" title="${_title }"> 
</a>
</c:if>
<%-- END SEZIONE FUNZIONI --%>
