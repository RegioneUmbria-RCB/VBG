<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="anagrafe.label.lista_procedimenti.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="anagrafe.label.lista_procedimenti.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../anagrafe/lististanzerichiedenti" />
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
	<div class="jmesa">
	<c:forEach items="${listIstanzerichiedentiHelper}" var="istanzerichiedentiHelper_var">
	<c:if test="${not empty istanzerichiedentiHelper_var.istanzesoggetticollegatis}">
		<br />
		<fieldset><legend class="intestazione">${istanzerichiedentiHelper_var.software.descrizione}</legend>
				<table border="0" cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td width="2%"><fmt:message key="label.numero_istanza" /></td>
							<td width="5%"><fmt:message key="label.data_presentazione" /></td>
							<td width="5%"><fmt:message key="label.numero_protocollo" /></td>
							<td width="5%"><fmt:message key="label.data_protocollo" /></td>
							<td width="20%"><fmt:message key="label.richiedente" /></td>
							<td width="20%"><fmt:message key="label.intervento" /></td>
							<td width="20%"><fmt:message key="label.localizzazione" /></td>
							<td width="6%"><fmt:message key="label.stato" /></td>
			            </tr>
					</thead>
					<tbody class="tbody">
					<%int j=1;%>
					<c:forEach items="${istanzerichiedentiHelper_var.istanzesoggetticollegatis}" var="istanzesoggetticollegati_var">
						<tr class="<%=(j%2)==0?"odd":"even"%>">
							<td align="right">
								<a href="javascript:historySet('${_urlback}','..%2Fistanze/view.htm?codice=${istanzesoggetticollegati_var.id.codice}&software=${istanzesoggetticollegati_var.software.codice}','')">${istanzesoggetticollegati_var.numeroistanza}</a>
							</td>
							<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" value="${istanzesoggetticollegati_var.data}"/></td>							
							<td>${istanzesoggetticollegati_var.numeroprotocollo}</td>
							<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" value="${istanzesoggetticollegati_var.dataprotocollo}"/></td>
							<td>${istanzesoggetticollegati_var.transientRichiedenteQualitaAzienda}</td>
							<td>${istanzesoggetticollegati_var.alberoproc.vwAlberoproc.scDescrizione}</td>
							<td>${istanzesoggetticollegati_var.localizzazioneTransient}</td>
							<td>${istanzesoggetticollegati_var.chiusura.stato}</td>
						</tr>
						<%j++; %>
					</c:forEach>
					</tbody>
					</table>
			</fieldset>
			</c:if>
			</c:forEach>
		
	</div>	
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>