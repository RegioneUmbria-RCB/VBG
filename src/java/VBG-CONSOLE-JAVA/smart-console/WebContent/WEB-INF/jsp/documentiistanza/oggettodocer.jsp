<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%--

<param name="view" value="list" />	
<param name="docnum" value="${documentiistanza_var.idDocer}" />
<param name="identificativo" value="${documentiistanza_var.id.codice}_${documentiistanza_var.idDocer}" />

--%>
<c:if test="${empty param.view}">
	[oggettodocer.jsp]  Attenzione !! non è stato settato il parametro idElemento.
</c:if>



<c:if test="${empty param.identificativo}">
	[oggettodocer.jsp]  Attenzione !! non è stato settato il parametro identificativo.
</c:if>
<c:if test="${not empty param.docnum}">
	<c:choose>
	<c:when test="${param.view == 'list'}">
	<a title="<fmt:message key="label.docer_visualizza_allegato_archiviato" />" href="javascript:visualizza${param.identificativo}()"><img border="0" 
			src="${pageContext.request.contextPath}/images/find.gif" /></a>
	</c:when>
	<c:otherwise>
	<fieldset>
		<fmt:message key="label.docer_visualizza_allegato_archiviato" />
		<ul id="functions">
			<li><a href="javascript:visualizza${param.identificativo}()"><fmt:message key="label.visualizza" /></a></li>
		</ul>
	</fieldset>	
	</c:otherwise>
	</c:choose>
	<script type="text/javascript">
	<!--
	function visualizza${param.identificativo}(){
		window.location.href='${pageContext.request.contextPath}/ajaxdocer/ajaxDownload.htm?docnum=${param.docnum}&nomefile=';
	}	
	//-->
	</script>
</c:if>