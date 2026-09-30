<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%
	pageContext.setAttribute("varTT",WebConstants.SOFTWARE_TT);
%>	
<spring-form:form commandName="responsabile" name="inviodati">
	<br />
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="responsabile" />
	</jsp:include>
	<div id="div_check_permessi"> 
		<a id="a_check_permessi" href="javascript:selezionaAndDeselezionaTutti()">
			<fmt:message key="label.seleziona_tutto" />
		</a>
	</div>
	<div id="menuAbilitati">
	<ul>
		<%-- _lengthOld viene inizializzata con un valore di default elevato in maniera tale che al primo passo _diffLength sia minore di -5  --%>
		<c:set var="_lengthOld" value="${9999}" />
<c:forEach var="clpermmenuList" items="${responsabile.clpermmenuList}"	varStatus="counter2"><c:set var="_disabled" value="" /><c:set var="_checked" value="" /><c:if test="${fn:length(clpermmenuList.menu.menulink)==1 and clpermmenuList.software.codice != varTT}"><c:set var="_disabled" value="disabled" /></c:if><c:set var="_length" value="${fn:length(clpermmenuList.menu.menulink)}" /><c:set var="_diffLength" value="${_length - _lengthOld}" /><c:if test="${_diffLength==0}"></li></c:if><c:if test="${_diffLength==1}"><ul></c:if><c:if test="${_diffLength==-1}"></li></ul></li>
</c:if><c:if test="${_diffLength==-2}"></li></ul></li></ul></li></c:if><c:if test="${_diffLength==-3}"></li></ul></li></ul></li></ul></li></c:if><c:if test="${_diffLength==-4}">
</li></ul></li></ul></li></ul></li></ul></li></c:if><c:if test="${_diffLength==-5}"></li></ul></li></ul></li>
</ul></li></ul></li></ul></li></c:if><li><spring:bind path="nuoviPermessi"><input type="hidden" name="_${status.expression}" value="1" />
<c:forEach var="clpermmenuListCurrent" items="${responsabile.entity.menuAbilitati}"><c:if test="${clpermmenuListCurrent.menu.id eq clpermmenuList.menu.id and clpermmenuListCurrent.software.codice eq clpermmenuList.software.codice}"><c:set var="_checked" value="checked" /></c:if><c:if test="${fn:length(clpermmenuList.menu.menulink)==1 and clpermmenuListCurrent.menu.id eq clpermmenuList.menu.id}"><c:set var="_checked" value="checked" /></c:if></c:forEach>
<input type="checkbox" id="menu_id${clpermmenuList.menu.menulink}"	onclick="checkedGenitori('${clpermmenuList.menu.menulink}','${permessisoftware}');"	name="${status.expression}" value="${clpermmenuList.menu.id}#${clpermmenuList.software.codice}" ${_checked } ${_disabled } />
<c:if test="${clpermmenuList.menu.layouttesti == true}"><label for="menu_id${clpermmenuList.menu.menulink}"><fmt:message key="label.layouttesti.${clpermmenuList.menu.descrizione}"></fmt:message></label></c:if>
<c:if test="${clpermmenuList.menu.layouttesti != true}"><label for="menu_id${clpermmenuList.menu.menulink}">${clpermmenuList.menu.descrizione}</label></c:if></spring:bind><c:set var="_lengthOld" value="${fn:length(clpermmenuList.menu.menulink)}" />
</c:forEach></ul>
	</div>
</spring-form:form>
<div id="functions">
	<ul>
		<li><a href="javascript:doSubmit('savePermessi.htm?permessisoftware=${permessisoftware}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a	href="javascript:doSubmit('createReplicapermessi.htm?codice=${responsabile.entity.id.codice}','',document.inviodati)"><fmt:message key="responsabili.button.replica_permessi" /></a></li>
		<li><a href="javascript:doHref('view.htm?codice=${responsabile.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
	
	
	