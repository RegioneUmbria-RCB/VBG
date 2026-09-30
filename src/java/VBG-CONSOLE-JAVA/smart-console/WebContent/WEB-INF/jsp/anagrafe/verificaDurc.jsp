<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>		
			<fmt:message key="label.verifica_esistenza_durc" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.verifica_esistenza_durc" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	    <div class="parametriDiv">
			<div class="etichetta">
   				<div>
             		<fmt:message key="label.soggetto" />:
       			</div>
        	</div>
        	<div class="parametro">
       			<div>
           	 		<c:out value="${anagrafe.descrizioneRichiedente}"/>
       			</div>
		 	</div>
    	</div>  
    <br class="clear"/>
    
	    <div style="width: 650px;min-height: 50px; border: thin dotted; padding: 5px;">
			<fmt:message key="label.verifica_esistenza_durc.help" />
		</div>
    
		<form name="inviodati" action="updateVerificaDurc.htm" method="post">
			<input type="hidden" name="codiceAnagrafe" value="${param.codiceAnagrafe}" />
			<c:choose>
			<c:when test="${not empty param.codiceIstanza}">
				<input type="hidden" name="codiceIstanza" value="${param.codiceIstanza}" />
			</c:when>
			<c:otherwise>
			<input type="hidden" name="codiceIstanza" value="" />
			</c:otherwise>
			</c:choose>
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="anagrafe" />
		    </jsp:include>
		    <%-- 
		    <c:if test="${mostraNuova eq true }">
		    	<div id="functions">	
					<ul>			
						<jsp:include page="../anagrafe/funzioniDURC.jsp">
						   <jsp:param name="uniquePageIdentifier" value="${param.codiceAnagrafe}" />
						   <jsp:param name="codiceAnagrafe" value="${param.codiceAnagrafe}" />
						   <jsp:param name="codiceIstanza" value="${param.codiceIstanza}" />
						   <jsp:param name="showAsButton" value="true" />
						   <jsp:param name="function" value="verifica"/>
						   <jsp:param name="returnToUrl" value="${_urlback}"/>
						</jsp:include>							
					</ul>
				</div>	
		    </c:if>
		    --%>
		    <%-- <jsp:param name="function" value="nuovoDURC"/>--%>
			<table>
				<tr>
					<td>
						<fmt:message key="label.data_registrazione_richiesta_durc" />
	       			</td>
	       			<td>
						<input id="dataregistrazione_id" type="text" name="dataregistrazione" size="10" maxlength="10" onblur="isValidDate(this,true);" value="<%= Utilities.getToday(false)%>"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldataregistrazione" idInput="dataregistrazione_id" textKey="label.calendar"/>					    
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="functions">
	
		<ul>			
			<li><a href="javascript:doSubmit('updateVerificaDurc.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>