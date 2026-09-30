<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.title.piano_rotazione" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.title.piano_rotazione" />
	</span>

<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../bandi/viewGraduatoria" />
</jsp:include>
<div id="subcontent">
<table border="1" cellpadding="6" cellspacing="0">
	 <tr><td><b>Posteggio/Giorno</b></td>
	     <c:forEach items="${giorni}" var="descGiorno">
	     	<td><b>${descGiorno}</b></td>
	     </c:forEach>
	 </tr>
	     <c:forEach items="${graduatorietPianorotaziones}" var="posteggi" varStatus="a">
	         <tr>   
	              <c:if test="${posteggi.isOccupantiPresneti}">        
	             <td><b>${posteggi.codiceposteggio}</b></td>
	            
		             <c:forEach items="${posteggi.giornosHelper}" var="giorno" varStatus="b">
		  				<td>${giorno.occupante} <br /> (${giorno.numeroIstanza})</td>
		          	 </c:forEach>
	          	 </c:if>
	         </tr>
	    </c:forEach>
</table>
</div>


<div id="functions">
<ul>
    <c:if test="${!isRilasciateConcessioni}">
    	<li><a href="javascript:historySet('${_urlback}','../bandi/createConcessioniPianorotazione.htm?codice=${graduatoriet.id.codice}','');"><fmt:message key="button.rilascia_concessioni" /></a></li>
    </c:if>
    <li><a href="javascript:historySet('${_urlback}','../bandi/deletePianorotazione.htm?codice=${graduatoriet.id.codice}','<fmt:message key="label.delete.piano_rotazione" />');"><fmt:message key="button.delete" /></a></li>
    <li><a href="javascript:doHref('../bandi/viewGraduatoria.htm?codice=${graduatoriet.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>


</body>
</html>
