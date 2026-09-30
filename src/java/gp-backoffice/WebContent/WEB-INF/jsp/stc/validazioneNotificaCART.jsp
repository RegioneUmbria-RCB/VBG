<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="form.stc.praticacollegata.title" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="cart.allegatinotifica.validazionemodulo.title" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="stcNotificaCommand" />
    </jsp:include>
    <c:if test="${not empty erroriValidazione }">
    	<ol>
    		<c:forEach items="${erroriValidazione}" var="error">
    			<c:forEach items="${error.errori}" var="errorMessage">
    			<li>
    				${errorMessage.message}
    			</li>
    			</c:forEach>
    		</c:forEach>
    	</ol>

    </c:if>
	<div id="functions">
		<ul>
        	<li><a href="javascript:historySet('../cart/generaAllegatiNotifica.htm?codiceIstanza=${stcNotificaCommand.codiceIstanza}&codiceMovimento=${stcNotificaCommand.codiceMovimento}','../cart/caricaModulistica.htm?codiceIstanza=${stcNotificaCommand.codiceIstanza}&codiceMovimento=${stcNotificaCommand.codiceMovimento}');">Carica i dati nella modulistica CART</a></li>				 
		</ul>
		
		<ul>
        	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				 
		</ul>
	</div>
</div>
</body>
</html>