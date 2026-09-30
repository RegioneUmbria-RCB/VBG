<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${rangerateizzazioni.id.codice==null}">
			<fmt:message key="form.rangerateizzazioni.title.create" />
		</c:if> 
		<c:if test="${rangerateizzazioni.id.codice!=null}">
			<fmt:message key="form.rangerateizzazioni.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${rateizzazioni.rangerateizzazioniList[0].id.codice==null}">
	<fmt:message key="form.rangerateizzazioni.title.create" />
</c:if> 
<c:if test="${rateizzazioni.rangerateizzazioniList[0].id.codice!=null}">
	<fmt:message key="form.rangerateizzazioni.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="rateizzazioni" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="rateizzazioni" />
    </jsp:include>
    <table>
		<c:forEach items="${rateizzazioni.rangerateizzazioniList}"  varStatus="index">
                 <tr>
					<td>
                    <c:if test="${index.index==0}">
                    <fmt:message key="label.from" />
                    <spring-form:input id="rangeBasso_id" path="rangerateizzazioniList[${index.index}].rangeBasso" size="5" readonly="true"/>                     
                    </c:if>
                    <c:if test="${index.index>0}">
                    <fmt:message key="label.from" />
                    <spring-form:input id="rangeBasso_id" path="rangerateizzazioniList[${index.index}].rangeBasso" size="5"/>                      
                    </c:if>
                    <fmt:message key="label.to" />
                    <spring-form:input path="rangerateizzazioniList[${index.index}].rangeAlto" size="5" />
					<spring-form:select  path="rangerateizzazioniList[${index.index}].tiporateizzazione.id.codice"  >
				    <spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${listtipirateizzazioni}" itemValue="id.codice"	itemLabel="descrizione" />
				    </spring-form:select>
                    <spring-form:errors path="rangerateizzazioniList[${index.index}].rangeAlto" cssClass="error"></spring-form:errors>
                    </td>
				</tr>
	 	</c:forEach>
  </table>
	<script type='text/javascript'>
		$('rangeBasso_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${rateizzazioni.rangerateizzazioniList[0].id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${rateizzazioni.rangerateizzazioniList[0].id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('../mercaticonfigurazione/view.htm?software=${software.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
