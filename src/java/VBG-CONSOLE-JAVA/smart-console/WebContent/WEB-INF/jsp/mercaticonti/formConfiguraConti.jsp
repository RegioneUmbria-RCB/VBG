<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message
	key="form.configurazioneContiMercato.title.list" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message
	key="form.configurazioneContiMercato.title.list" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<script type="text/javascript">
function changeValue(obj){
	
	var importo=obj.value;
	if(isNaN(importo.replace(",","."))){
		alert('<fmt:message key="alert.field.numeric" />');
		return;
	}
	if(importo.indexOf(".",0)>0){
		importo = importo.replace(".",",");
	}		
	obj.value=importo;	
}
</script>
<div id="subcontent"><spring-form:form
	commandName="configurazioneContiMercato" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="configurazioneContiMercato" />
	</jsp:include>


	<span class="parametri"> <fmt:message
		key="form.mercatiConti.mercato" />:<label>${configurazioneContiMercato.mercati.descrizione}</label>
	<br />
	<fmt:message key="form.mercatiConti.anno" />:<label>${configurazioneContiMercato.anno}</label>
	</span>
	<br />
	<fmt:message key="form.configurazioneContiMercato.message" />
	<br />
	<spring-form:hidden path="mercati.id.codice" />
	<spring-form:hidden path="anno" />
	<div class="jmesa">
	<c:if test="${fn:length(configurazioneContiMercato.contiHelperList)>0}">
	<table cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td><fmt:message key="form.configurazioneContiMercato.contoOld" /></td>
				<td><fmt:message key="form.configurazioneContiMercato.anno" /></td>
				<td><fmt:message key="form.mercatiConti.contesto" /></td>
				<td><fmt:message key="form.configurazioneContiMercato.contoNew" /></td>
				<td colspan="2"><fmt:message
					key="form.configurazioneContiMercato.moltiplicatore" /></td>
			</tr>
		</thead>
		<tbody class="tbody">
			<c:forEach var="contihelper_var"
				items="${configurazioneContiMercato.contiHelperList}"
				varStatus="contihelperStatus">
				<tr>
					<td>${contihelper_var.contoOld.descrizioneConto}
						<spring-form:hidden path="contiHelperList[${contihelperStatus.index}].contoOld.id.codice"/>					
					</td>
					<td>${contihelper_var.mercatiConti.anno}</td>
					<td>${contihelper_var.mercatiConti.contesto}</td>
					<td><spring-form:select
						path="contiHelperList[${contihelperStatus.index}].contoNew.id.codice">
						<spring-form:options items="${contis}" itemLabel="descrizioneConto"
							itemValue="id.codice" />
					</spring-form:select></td>
					<td><spring-form:input
						path="contiHelperList[${contihelperStatus.index}].moltiplicatore"
						size="5" onchange="changeValue(this);"/></td>
					<td><spring-form:checkbox
						path="contiHelperList[${contihelperStatus.index}].usa" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	</c:if>
	<c:if test="${fn:length(configurazioneContiMercato.contiHelperList)==0}">
		<fmt:message key="form.configurazioneContiMercato.message.nessuna_configurazione_trovata" />
	</c:if>
	</div>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${fn:length(configurazioneContiMercato.contiHelperList)>0}">
		<li><a href="javascript:doSubmit('configuraContiInsert.htm','');"><fmt:message
			key="button.update" /></a></li>
		</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','');"><fmt:message
		key="button.back" /></a></li>
</ul>
</div>
</body>
</html>