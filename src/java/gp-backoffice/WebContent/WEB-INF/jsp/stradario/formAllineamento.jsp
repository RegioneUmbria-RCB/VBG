<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<fmt:message key="form.stradario.title.allinea" />
</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.stradario.title.allinea" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="stradario" />
	</jsp:include>
    <br />
		<div><span><fmt:message key="label.stradario.alert_allinea_stradario" /></span></div>
	<br />
	<fieldset><legend><b><fmt:message key="label.lista_comuni" /></b></legend>
			<div class="jmesa">
				<table border="0"  cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td width="5%"><fmt:message key="label.comune" /></td>
							<td width="5%"><fmt:message key="label.inseriti" /></td>
							<td width="5%"><fmt:message key="label.aggiornati" /></td>
			            </tr>
					</thead>
					<tbody class="tbody">
						<%int i=1;%>
						<c:forEach items="${allineamentoStradarioHelpers}" var="allineamentoStradarioHelper">
							<tr class="<%=(i%2)==0?"odd":"even"%>">			
								<td>${allineamentoStradarioHelper.comune}
								<c:if test="${allineamentoStradarioHelper.isErrore}">
									<img src="${pageContext.request.contextPath}/images/warning.gif" alt="" title="Attenzione, si sono verificati durante l'aggiornamento."/>
								</c:if>
								</td>
								<td>${allineamentoStradarioHelper.numAggiunti}</td>
								<td>${allineamentoStradarioHelper.numAggiornati}</td>
							</tr>
							<%i++; %>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<br />		
		</fieldset>	
		<c:if test="${not empty errori}">
		<div class="jmesa">
				<table border="0"  cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td><fmt:message key="label.errori" /></td>
			            </tr>
					</thead>
					<tbody class="tbody">
						<%int j=1;%>
						<c:forEach items="${errori}" var="errore">
							<tr class="<%=(j%2)==0?"odd":"even"%>">			
								<td>${errore}</td>
							</tr>
							<%j++; %>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>	
	</div>	
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doHref('../stradario/allineaStradario.htm?software=TT','');"><fmt:message key="button.allinea_stradario" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>