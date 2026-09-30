<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.rangerateizzazioni.title.list" /></title>
	</head>
	<body>
		<h2><fmt:message key="form.rangerateizzazioni.title.list" /></h2>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
		</jsp:include>
	<c:if test="${not empty rangeRateizzazioniList}">
	<%
	int i=0;
	%>
	<div class="jmesa" >
			<table border="0"  cellpadding="0"  cellspacing="0"  class="table" >
				<thead>
				<tr  class="header">
					<td><fmt:message key="form.rangerateizzazioni.rangeabasso" /></td>
					<td><fmt:message key="form.rangerateizzazioni.rangeaalto" /></td>
					<td><fmt:message key="form.rangerateizzazioni.tiporateizzazione" /></td>
				</tr>
				</thead>
				<tbody class="tbody">
				<c:forEach items="${rangeRateizzazioniList}" var="var_rateizzazione" varStatus="index">
				<tr class="<%=(i%2)==0?"odd":"even"%>"  onmouseover="this.className='highlight'"  onmouseout="this.className='<%=(i%2)==0?"odd":"even"%>'">
					<td>${var_rateizzazione.rangeBasso}</td>
                    <c:if test="${var_rateizzazione.rangeAlto==null}">
                    <td><fmt:message key="form.rangerateizzazioni.oltre" /></td>
                    </c:if>
                    <c:if test="${var_rateizzazione.rangeAlto!=null}">
					<td>${var_rateizzazione.rangeAlto}</td>
                    </c:if>
					<td>${var_rateizzazione.tiporateizzazione.descrizione}</td>
				</tr>
				<%i++;%>
				</c:forEach>
				</tbody>
		  </table>
		</div>
		</c:if>
	

<div id="functions">
			<ul>
				<li><a href="javascript:doHref('../mercaticonfigurazione/view.htm?software=${software.codice}','')"><fmt:message key="button.back" /></a></li>
                <li><a href="javascript:doHref('view.htm?software=${software.codice}','')"><fmt:message key="button.change" /></a></li>
			</ul>
		</div>
	</body>
</html>