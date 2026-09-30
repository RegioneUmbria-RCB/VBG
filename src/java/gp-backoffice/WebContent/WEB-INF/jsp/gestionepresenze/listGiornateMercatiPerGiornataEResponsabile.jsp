<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.MercatiD"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.net.URLEncoder"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.lista_giornate_mercati_responsabile.title" /></title>
</head>
<body>
    <jsp:include page="../includes/history.jsp">
   		 <jsp:param name="path" value="../gestionepresenze/listGiornateMercatoPerResponsabileEDataOdierna" />
	</jsp:include>
    
    <span class="titoloPagina">
		<fmt:message key="label.lista_giornate_mercati_responsabile.title" />: <b>${responsabile}</b> 
	</span>


	<br /><br /><br />
	<table border="1" cellpadding="40%" cellspacing="0px"  width="100%">
		<%
		    int i = 0;
		%>
		<c:forEach items="${giornataMercatoCommands}" var="giornate" varStatus="varIndex">
		  
			<%--  <c:if test="<%=(i%2)==0%>"> --%>
			<tr>
			<%-- </c:if> --%>
			
                    <td width="70%">
	            		<span class="parametri">   
		    				<fmt:message key="label.data" />: <label> <c:out value="${giornate.dataGiornata}"></c:out></label>	
	    				</span>
	    				<span class="parametri">   
		    				<fmt:message key="label.mercato" />: <label> <c:out value="${giornate.descrizioneMercato}"></c:out> - <c:out value="${giornate.descrizioneUso}"></c:out></label>	
	    				</span>
    				</td>
    				
    				<td >
	            		<div id="functions">
						<ul>
							<li>
								<a href="javascript:historySet('${_urlback}','${giornate.linkGiornataGestionePresenze}','');"><fmt:message key="button.accedi_giornata" /></a>
							</li>
						</ul>
						</div>
            		</td>
          
            	
            	
            
			<%
			    i++;
			%>
			
			
			<%--  <c:if test="<%=(i%2)==0%>">   --%>
			</tr>
			<%
			    i = 0;
			%>
			<%--  </c:if>  --%>
		</c:forEach>
	</table>
	
</body>
</html>