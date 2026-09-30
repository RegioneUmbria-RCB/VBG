<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipibandoinput.id.codice==null}">
			<fmt:message key="form.tipibandoinput.title.create" />
		</c:if> 
		<c:if test="${tipibandoinput.id.codice!=null}">
			<fmt:message key="form.tipibandoinput.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
	<%
	String etichettaStyle = "";
	String queryStyle = "";
	%>
<c:if test="${tipibandoinput.id.codice==null}">
	<%
	etichettaStyle = "display:none";
	queryStyle = "display:none";
	%>
	<fmt:message key="form.tipibandoinput.title.create" />
</c:if> 
<c:if test="${tipibandoinput.id.codice!=null}">
	<c:set var="BANDI_TIPOCALCOLO_VALORE" value="<%=WebConstants.BANDI_TIPOINPUT_VALORE %>"></c:set>
	<c:set var="BANDI_TIPOCALCOLO_QUERY" value="<%=WebConstants.BANDI_TIPOINPUT_QUERY %>"></c:set>
	<c:set var="BANDI_TIPOCALCOLO_MERCATI" value="<%=WebConstants.BANDI_TIPOINPUT_MERCATI %>"></c:set>
	<c:if test="${tipibandoinput.tipoinput eq  BANDI_TIPOCALCOLO_VALORE || tipibandoinput.tipoinput eq BANDI_TIPOCALCOLO_MERCATI}">
		<%
		etichettaStyle = "";
		queryStyle = "display:none";
		%>
	</c:if>
	<c:if test="${tipibandoinput.tipoinput eq BANDI_TIPOCALCOLO_QUERY}">
		<%
		etichettaStyle = "";
		queryStyle = "";
		%>
	</c:if>
	<fmt:message key="form.tipibandoinput.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
<span class="parametri"><fmt:message key="form.tipibando.title.prefix"/> <label>${tipibando.descrizione}</label></span>
<spring-form:form commandName="tipibandoinput" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipibandoinput" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="form.tipibandoinput.tipoinput" /></td>
			<td><spring-form:select path="tipoinput" onchange="mostra(this);return false;" id="tipoinput_id">
					<spring-form:option value="<%=WebConstants.BANDI_TIPOINPUT_VALORE %>"><fmt:message key="form.tipibandoinput.valore" /></spring-form:option>
					<%-- 
					<spring-form:option value=" "><fmt:message key="label.select.default" /></spring-form:option>
					<spring-form:option value="<%=WebConstants.BANDI_TIPOINPUT_QUERY %>"><fmt:message key="form.tipibandoinput.query" /></spring-form:option>
					<spring-form:option value="<%=WebConstants.BANDI_TIPOINPUT_MERCATI %>"><fmt:message key="form.tipibandoinput.mercati" /></spring-form:option>					
					 --%>
				</spring-form:select>
				<spring-form:errors path="tipoinput" cssClass="error"/>
			</td>
		</tr>		
		<tr id="query" style="<%=queryStyle %>">
			<td><fmt:message key="form.tipibandoinput.query" /></td>
			<td><spring-form:textarea path="query" id="query_id" cols="60" rows="8"/>
			<spring-form:errors path="query" cssClass="error"/></td>
		</tr>
		<tr id="etichetta" style="<%=etichettaStyle %>">
			<td><fmt:message key="form.tipibandoinput.etichetta" /></td>
			<td><spring-form:input id="etichetta_id" path="etichetta" size="70" />
			<spring-form:errors path="etichetta" cssClass="error"/></td>
		</tr>				
	</table>
	<script type='text/javascript'>
		$('tipoinput_id').focus();
		function mostra(obj){
			var pos=obj.selectedIndex;
			var valore = '';
			if (pos>-1) {
				valore=obj.options[pos].value;
			}
			if (valore=='<%=WebConstants.BANDI_TIPOINPUT_VALORE %>'){
				$('query').fade();
				$('etichetta').appear();	 
			}
			if (valore=='<%=WebConstants.BANDI_TIPOINPUT_QUERY %>'){
				$('query').appear();
				$('etichetta').appear();	 
			}
			if (valore=='<%=WebConstants.BANDI_TIPOINPUT_MERCATI %>'){
				$('query').fade();
				$('etichetta').appear();	 
			}  
			if (valore==' '){
				$('query').fade();
				$('etichetta').appear();	 
			}  
			return false;
		}
		mostra($('tipoinput_id'));	
	</script>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${tipibandoinput.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipibandoinput.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?tipibando.id.codice=${tipibandoinput.tipibando.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
