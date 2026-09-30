<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="form.gestionepresenze.spuntistigiornoprima.title" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="form.gestionepresenze.spuntistigiornoprima.title" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="giornoPrecedente" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="giornoPrecedente" />
    </jsp:include>
    
	<span class="parametri"><fmt:message key="form.gestionepresenze.mercato" />: <label>${giornoCorrente.mercato.descrizione}</label></span>
	<span class="parametri"><fmt:message key="form.gestionepresenze.mercatiUso" />: <label>${giornoCorrente.mercatoUso.descrizione}</label></span>
	<span class="parametri"><fmt:message key="form.gestionepresenze.data" />: <label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${giornoCorrente.dataRegistrazione}" /></label></span><br />
	<div class="titoloSezione"><fmt:message key="form.gestionepresenze.spuntistigiornoprima.title.list" /></div><br />	
	<%int i=0;%>
	<c:set var="enable_insert" value="0"></c:set>
	<c:if test="${not empty giornoPrecedente.listaPresenze}">
	<div class="jmesa" >
			<table border="0"  cellpadding="0"  cellspacing="0"  class="table" >
				<thead>
				<tr  class="header">
					<td><fmt:message key="gestionepresenze.spuntistigiornoprima.nome" /></td>
					<td><fmt:message key="gestionepresenze.spuntistigiornoprima.assegnaposteggio" /></td>
					<td><fmt:message key="gestionepresenze.spuntistigiornoprima.presente" /></td>
				</tr>
				</thead>
				<tbody class="tbody">
				<c:forEach items="${giornoPrecedente.listaPresenze}" var="var_presenza" varStatus="index">
				<c:if test="${var_presenza.spuntista eq true}">
				<c:set var="enable_insert" value="1"></c:set>
				<tr class="<%=(i%2)==0?"odd":"even"%>"  onmouseover="this.className='highlight'"  onmouseout="this.className='<%=(i%2)==0?"odd":"even"%>'">
					<td>${var_presenza.occupante.descrizioneRichiedente}</td>
					<td>					
					<spring-form:select path="listaPresenze[${index.index}].posteggio.id.codice" >
					<spring-form:option value="">Nessun posteggio</spring-form:option>
					<c:forEach items="${giornoCorrente.listaPresenze}" var="var_presenza2">
					<c:if test="${not empty var_presenza2.posteggio and (empty var_presenza2.occupante)}">
						<spring-form:option value="${var_presenza2.posteggio.id.codice}" >${var_presenza2.posteggio.codiceposteggio}</spring-form:option>
					</c:if>
					</c:forEach>
					</spring-form:select>					
					</td>
					<td><spring-form:checkbox path="listaPresenze[${index.index}].presente" /></td>
				</tr>
				<%i++;%>
				</c:if>
				</c:forEach>
				</tbody>
		  </table>
		</div>
		</c:if>
	</spring-form:form>
</div>
<br />
<div id="functions">
<ul>
	<c:if test="${enable_insert eq 1}">
	<li><a href="javascript:doSubmit('insertSpuntistiGiornoPrima.htm?codiceMercato=${giornoCorrente.mercato.id.codice}&usoMercato=${giornoCorrente.mercatoUso.id.codice}&giornoMercato=<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${giornoCorrente.dataRegistrazione}" />','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?codiceMercato=${giornoCorrente.mercato.id.codice}&usoMercato=${giornoCorrente.mercatoUso.id.codice}&giornoMercato=<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${giornoCorrente.dataRegistrazione}" />','')"><fmt:message key="button.back" /></a></li>	
</ul>
</div>
</body>
</html>