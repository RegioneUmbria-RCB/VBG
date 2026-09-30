 <?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${cctipointervento.id.codice==null}">
			<fmt:message key="label.nuovo_cctipointervento.title" />
		</c:if> 
		<c:if test="${cctipointervento.id.codice!=null}">
			<fmt:message key="label.dettaglio_cctipointervento.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${cctipointervento.id.codice==null}">
			<fmt:message key="label.nuovo_cctipointervento.title" />
		</c:if> 
		<c:if test="${cctipointervento.id.codice!=null}">
			<fmt:message key="label.dettaglio_cctipointervento.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="cctipointervento" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="cctipointervento" />
		    </jsp:include>
			<table>
			    <tr>
					<td>
						<fmt:message key="label.intervento_base" />
					</td>
					<td>
						<spring-form:select path="occBasetipointervento.id">
						    <spring-form:options items="${listInterventiBase}" itemValue="id" itemLabel="intervento" ></spring-form:options>
						</spring-form:select>
						<spring-form:errors path="occBasetipointervento" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.intervento" />
					</td>
					<td>
						<spring-form:textarea id="intervento_id" path="intervento" rows="5" cols="40" />
						<spring-form:errors path="intervento" cssClass="error"/>
					</td>
				</tr>
				
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${cctipointervento.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${cctipointervento.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>