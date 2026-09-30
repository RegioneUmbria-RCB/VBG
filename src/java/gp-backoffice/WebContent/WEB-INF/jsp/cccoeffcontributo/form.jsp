<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${cccoeffcontributo.id.codice==null}">
			<fmt:message key="label.nuovo_cccoeffcontributo.title" />
		</c:if> 
		<c:if test="${cccoeffcontributo.id.codice!=null}">
			<fmt:message key="label.dettaglio_cccoeffcontributo.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${cccoeffcontributo.id.codice==null}">
			<fmt:message key="label.nuovo_cccoeffcontributo.title" />
		</c:if> 
		<c:if test="${cccoeffcontributo.id.codice!=null}">
			<fmt:message key="label.dettaglio_cccoeffcontributo.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="cccoeffcontributo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="cccoeffcontributo" />
		    </jsp:include>
			<table>
			     <tr>
					<td>
						<fmt:message key="label.destinazione" />
					</td>
					<td>
						<spring-form:select path="ccDestinazioni.id.codice">
						    <spring-form:options items="${ccDestinazionis}" itemValue="id.codice" itemLabel="destinazione" ></spring-form:options>
						</spring-form:select>
						<spring-form:errors path="ccDestinazioni" cssClass="error"/>
					</td>
				</tr>
				 <tr>
					<td>
						<fmt:message key="label.tipo_intervento" />
					</td>
					<td>
						<spring-form:select path="ccTipointervento.id.codice">
						    <spring-form:options items="${ccTipointerventos}" itemValue="id.codice" itemLabel="intervento" ></spring-form:options>
						</spring-form:select>
						<spring-form:errors path="ccTipointervento" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.coefficiente" />
					</td>
					<td>
						<spring-form:input id="coefficinte_id" path="coefficiente" cssStyle="text-align:right;" size="10" maxlength="8" onblur="checkNumberValue(this);" />
						<fmt:message key="label.simbolo_percentuale" />
						<spring-form:errors path="coefficiente" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${cccoeffcontributo.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${cccoeffcontributo.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceCoefficiente=${cccoeffcontributo.ccValiditacoefficienti.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>