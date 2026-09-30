<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<c:if test="${param.result eq 'VALID'}"><img src="images/ok.gif" alt="${param.result}" /></c:if>
<c:if test="${param.result eq 'INVALID'}"><img src="images/nok.gif" alt="${param.result}" />&nbsp;NON VALIDO</c:if>
<c:if test="${param.result eq 'UNKNOWN'}"><img src="images/wa.gif" alt="${param.result}" />&nbsp;SCONOSCIUTO</c:if>
<c:if test="${param.result eq 'UNDETERMINED'}"><img src="images/wa.gif" alt="${param.result}" />&nbsp;INDETERMINATO</c:if>
<c:if test="${param.result eq 'REVOKED'}"><img src="images/nok.gif" alt="${param.result}" />&nbsp;REVOCATO</c:if>								
