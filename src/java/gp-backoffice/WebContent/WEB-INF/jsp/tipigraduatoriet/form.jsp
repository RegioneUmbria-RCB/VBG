
<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipigraduatoriet.id.codice==null}">
			<fmt:message key="form.tipigraduatoriet.title.create" />
		</c:if> 
		<c:if test="${tipigraduatoriet.id.codice!=null}">
			<fmt:message key="form.tipigraduatoriet.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${tipigraduatoriet.id.codice==null}">
	<fmt:message key="form.tipigraduatoriet.title.create" />
</c:if> 
<c:if test="${tipigraduatoriet.id.codice!=null}">
	<fmt:message key="form.tipigraduatoriet.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../tipigraduatoriet/view" />
</jsp:include>
<div id="subcontent">
    <span class="parametri"><fmt:message key="form.tipibando.title.prefix"/><label> ${tipibando.descrizione}</label></span>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipigraduatoriet" />
	</jsp:include>
	<spring-form:form commandName="tipigraduatoriet" name="inviodati">
	<table>
		<tr>
			<td><fmt:message key="form.tipigraduatoriet.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="70" />
			<spring-form:errors path="descrizione" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipigraduatoriet.flagPianorotazione" /></td>
			<td><spring-form:checkbox path="flagPianorotazione" value="1" />
			<init:help idHelp="help_flagPianorotazione_id" textKey="label.flagPianorotazione.help" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipigraduatoriet.flagEsprArtTemp" /></td>
			<td><spring-form:checkbox path="flagEsprArtTemp" value="1" />
			<init:help idHelp="help_flagEsprArtTemp_id" textKey="label.flagEsprArtTemp.help" />
			</td>
		</tr>
	</table>
    <c:if test="${ tipigraduatoriet.tipigraduatorieds != null &&  not empty tipigraduatoriet.tipigraduatorieds}">
    <div class="titoloSottoSezione"><fmt:message key="form.tipigraduatoried.dyn2campi.title" /></div>
    <table>
			<c:forEach items="${tipigraduatoriet.tipigraduatorieds}" var="current" varStatus="a">
			<tr>
				<td><fmt:message key="form.tipigraduatoried.dyn2campi" /></td>
				<td>
					<spring:bind path="tipigraduatorieds[${a.index}].dyn2Campi.id.codice">
						<spring-form:select path="${status.expression}" >
							<spring-form:option value=""><fmt:message key="label.select.default" /></spring-form:option>								
							<spring-form:options items="${dyn2CampiList}" itemValue="id.codice" itemLabel="nomecampo"/>
						</spring-form:select>
					</spring:bind> 
				</td>
				<td><fmt:message key="form.tipigraduatoried.valore" /></td>
				<td>
				 	<spring:bind path="tipigraduatorieds[${a.index}].valore">
						<input type="text" name="${status.expression}" value="${status.value}" />						 
					</spring:bind> 
				</td>
				<td>
					<c:if test="${!(current.id.codice==null || current.id.codice eq '')}">
						<a title="<fmt:message key="button.delete" /> ${(a.index)+1}" href="javascript:doSubmit('deleteGraduatoriad.htm?codice=${current.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)">
							<img src="${pageContext.request.contextPath}/images/cross.gif" alt="<fmt:message key="button.delete" /> ${(a.index)+1}" />
						</a>
					</c:if>
				    <spring-form:errors path="tipigraduatorieds[${a.index}].valore" cssClass="error" />
                </td>
			</tr>
			</c:forEach>
	</table>
	</c:if>
	<script type='text/javascript'>
		$('descrizione_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${tipigraduatoriet.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipigraduatoriet.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
        <li><a href="javascript:doSubmit('../tipibandocampigraduat/list.htm?tipigraduatoriet.id.codice=${tipigraduatoriet.id.codice}','',document.inviodati)"><fmt:message key="button.tipibando.criteriordinamento" /></a></li>
	    <c:if test="${tipigraduatoriet.flagPianorotazione}">
	    	<li><a href="javascript:historySet('${_urlback}','../tipigradtcfgrotazione/list.htm?codice=${tipigraduatoriet.id.codice}')"><fmt:message key="button.configura_campi_piano_rotazione" /></a></li>
	    </c:if>
	    
	    
	     <c:if test="${tipigraduatoriet.flagEsprArtTemp}">
	    	<li><a href="javascript:historySet('${_urlback}','../tipigraduatorietesprart/list.htm?codice=${tipigraduatoriet.id.codice}')"><fmt:message key="button.configura_campi_bandi_art_tem" /></a></li>
	    </c:if>
	    <c:if test="${not empty tipigraduatoriet.tipibando.tipibandoinputs}">
            <li><a href="javascript:doSubmit('../tipibandooutput/list.htm?tipigraduatoriet.id.codice=${tipigraduatoriet.id.codice}','',document.inviodati)"><fmt:message key="button.tipibando.campioutput" /></a></li>
        </c:if>
	</c:if>
	<li><a href="javascript:doHref('list.htm?tipibando.id.codice=${tipigraduatoriet.tipibando.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
