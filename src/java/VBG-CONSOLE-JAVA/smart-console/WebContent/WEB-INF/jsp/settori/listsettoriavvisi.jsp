<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="settori.label.lista_settoriavvisi.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="settori.label.lista_settoriavvisi.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
		<div class="parametriDiv">
			<div class="etichetta">
			<div><fmt:message key="settori.label.settore" />:</div>
			</div>
		<div class="parametro">
			<div><c:out value="${settore.settore}" /></div>
		</div>
		</div>
        <div class="jmesa" >
        <table border="0"  cellpadding="0"  cellspacing="0"  class="table">
				<thead>
				<tr  class="header">
					<td width="20%"><fmt:message key="label.codice"/></td>
					<td width="20%"><fmt:message key="label.immagine"/></td>
					<td style="text-align: right;"><fmt:message key="label.da"/></td>
					<td style="text-align: right;"><fmt:message key="label.a"/></td>
					<td><fmt:message key="label.edit.record"/></td>
				</tr>
				</thead>
                <%
			    int i=0;
			    %>
				<tbody class="tbody">
				<c:forEach items="${settoriavvisiList}" var="var_settoreavviso" varStatus="index">
				
					<tr class="<%=(i%2)==0?"odd":"even"%>">
						<td>
							<a href="javascript:doHref('viewSettoriavvisi.htm?codiceavviso=${var_settoreavviso.id.codice}&codicesettore=${settore.id.codicesettore}','')">${var_settoreavviso.id.codice}</a>
                        </td>
						<td>
						<img src="../file/ajaxDownload.htm?fileId=${var_settoreavviso.oggetto.id.codice}" alt="" />
                        </td>		
						<td style="text-align: right;">
							<fmt:formatNumber value="${var_settoreavviso.rangeda}" />
						</td>
                        <td style="text-align: right;">
							<fmt:formatNumber value="${var_settoreavviso.rangea}" />
						</td>
                        <td width="5%">
							<a class="dettaglioColumn" href="javascript:doHref('viewSettoriavvisi.htm?codiceavviso=${var_settoreavviso.id.codice}&codicesettore=${settore.id.codicesettore}','')" title="<fmt:message key="label.edit.record" /> ${var_settoreavviso.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a> 
						</td>
					</tr>
				<%i++;%>
				
				</c:forEach>
				</tbody>
			</table>
	    </div>
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('createSettoriavvisi.htm?codicesettore=${settore.id.codicesettore}','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('view.htm?codice=${settore.id.codicesettore}','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>