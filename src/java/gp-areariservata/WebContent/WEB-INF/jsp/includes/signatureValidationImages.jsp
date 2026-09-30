<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<c:if test="${param.result eq 'VALID'}"><img src="${pageContext.request.contextPath}/images/signature/ok.gif" alt="${param.result}" />&nbsp;</c:if>
<c:if test="${param.result eq 'INVALID'}"><img src="${pageContext.request.contextPath}/images/signature/nok.gif" alt="${param.result}" />&nbsp;NON VALIDO</c:if>
<c:if test="${param.result eq 'UNKNOWN'}"><img src="${pageContext.request.contextPath}/images/signature/wa.gif" alt="${param.result}" />&nbsp;SCONOSCIUTO</c:if>
<c:if test="${param.result eq 'UNDETERMINED'}"><img src="${pageContext.request.contextPath}/images/signature/wa.gif" alt="${param.result}" />&nbsp;INDETERMINATO</c:if>
<c:if test="${param.result eq 'REVOKED'}"><img src="${pageContext.request.contextPath}/images/signature/nok.gif" alt="${param.result}" />&nbsp;REVOCATO</c:if>								
