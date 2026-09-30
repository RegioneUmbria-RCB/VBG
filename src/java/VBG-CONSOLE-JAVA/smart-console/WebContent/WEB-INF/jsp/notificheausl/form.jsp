<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${notificheAusl.id.codNotifica == null  || notificheAusl.id.codNotifica == ''}">
			<fmt:message key="form.notificheausl.title.create" />
		</c:if> 
		<c:if test="${notificheAusl.id.codNotifica!=null && notificheAusl.id.codNotifica != ''}">
			<fmt:message key="form.notificheausl.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${notificheAusl.id.codNotifica == null  || notificheAusl.id.codNotifica == ''}">
	<fmt:message key="form.notificheausl.title.create" />
</c:if> 
<c:if test="${notificheAusl.id.codNotifica!=null && notificheAusl.id.codNotifica != ''}">
	<fmt:message key="form.notificheausl.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="notificheAusl" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="notificheAusl" />
    </jsp:include>
	<table>
        <c:if test="${notificheAusl.id.codNotifica!=null && notificheAusl.id.codNotifica != ''}">
		<tr>
			<td><fmt:message key="form.notificheausl.codicenotifica" /></td>
			<td><spring-form:input id="codNotifica_id" path="id.codNotifica" size="10" readonly="true"/>
			<spring-form:errors path="id.codNotifica" cssClass="error"/></td>
		</tr>
        </c:if>
        <c:if test="${notificheAusl.id.codNotifica == null  || notificheAusl.id.codNotifica == ''}">
        <tr>
			<td><fmt:message key="form.notificheausl.codicenotifica" /></td>
			<td><spring-form:input id="codNotifica_id" path="id.codNotifica" size="10" maxlength="10"/>
			<spring-form:errors path="id.codNotifica" cssClass="error"/></td>
		</tr>
        </c:if>
        <tr>
			<td><fmt:message key="form.notificheausl.idditta" /></td>
			<td><spring-form:input id="idDitta_id" path="idDitta" size="10" maxlength="10" onchange="eliminaDecimale(this);checkNumberValue(this);" />
			<spring-form:errors path="idDitta" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.ragionesociale" /></td>
			<td><spring-form:input id="ragSoc_id" path="ragSoc" size="70" />
			<spring-form:errors path="ragSoc" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.comune" /></td>
			<td><spring-form:input id="comune_id" path="comune" size="70" />
			<spring-form:errors path="comune" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.localita" /></td>
			<td><spring-form:input id="localita_id" path="localita" size="70" />
			<spring-form:errors path="localita" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.indirizzo" /></td>
			<td><spring-form:input id="indirizzo_id" path="indirizzo" size="70" />
			<spring-form:errors path="indirizzo" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.descComparto" /></td>
			<td><spring-form:input id="descComparto_id" path="descComparto" size="70" />
			<spring-form:errors path="descComparto" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.descAtt" /></td>
			<td><spring-form:input id="descAtt_id" path="descAtt" size="70" />
			<spring-form:errors path="descAtt" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.rapLeg" /></td>
			<td><spring-form:input id="rapLeg_id" path="rapLeg" size="70" />
			<spring-form:errors path="rapLeg" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.comuneLegale" /></td>
			<td><spring-form:input id="comuneLegale_id" path="comuneLegale" size="70" />
			<spring-form:errors path="comuneLegale" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.localitaLegale" /></td>
			<td><spring-form:input id="localitaLegale_id" path="localitaLegale" size="70" />
			<spring-form:errors path="localitaLegale" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.indirizzoLegale" /></td>
			<td><spring-form:input id="indirizzoLegale_id" path="indirizzoLegale" size="70" />
			<spring-form:errors path="indirizzoLegale" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.annoReg" /></td>
			<td><spring-form:input id="annoReg_id" path="annoReg" size="4" maxlength="4"/>
			<spring-form:errors path="annoReg" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.codTipoaut" /></td>
			<td><spring-form:input id="codTipoaut_id" path="codTipoaut" size="1" maxlength="1"/>
			<spring-form:errors path="codTipoaut" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.idAut" /></td>
			<td><spring-form:input id="idAut_id" path="idAut" size="10" maxlength="10" onchange="eliminaDecimale(this);checkNumberValue(this);"/>
			<spring-form:errors path="idAut" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.tipoNotifica" /></td>
			<td><spring-form:input id="tipoNotifica_id" path="tipoNotifica" size="70" />
			<spring-form:errors path="tipoNotifica" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.dataPerv" /></td>
			<td><spring-form:input id="dataPerv_id" path="dataPerv" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
			<init:calendar imagePath="/images/cal.gif" idImage="caldataPerv_id" idInput="dataPerv_id" textKey="label.calendar"/>
			<spring-form:errors path="dataPerv" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.protSian" /></td>
			<td><spring-form:input id="protSian_id" path="protSian" size="10" maxlength="10"/>
			<spring-form:errors path="protSian" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.dataSian" /></td>
			<td><spring-form:input id="dataSian_id" path="dataSian" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
			<init:calendar imagePath="/images/cal.gif" idImage="caldataSian_id" idInput="dataSian_id" textKey="label.calendar"/>
			<spring-form:errors path="dataSian" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.mesePervenuta" /></td>
			<td><spring-form:input id="mesePervenuta_id" path="mesePervenuta" size="2" maxlength="2"/>
			<spring-form:errors path="mesePervenuta" cssClass="error"/></td>
		</tr>
        <tr>
			<td><fmt:message key="form.notificheausl.annoPervenuta" /></td>
			<td><spring-form:input id="annoPervenuta_id" path="annoPervenuta" size="4" maxlength="4"/>
			<spring-form:errors path="annoPervenuta" cssClass="error"/></td>
		</tr>
	</table>
	
	<script type="text/javascript">
	var valoreIdDitta=document.getElementById("idDitta_id").value;
	var array=valoreIdDitta.toString().split(",");
	document.getElementById("idDitta_id").value=array[0];
	var valoreidAut_id=document.getElementById("idAut_id").value;
	var arrayidAut_id=valoreidAut_id.toString().split(",");
	document.getElementById("idAut_id").value=arrayidAut_id[0];

	function eliminaDecimale(obj){
		var valore=obj.value;
		var array=valore.toString().split(",");
		obj.value=array[0];
		}
	</script>

</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${notificheAusl.id.codNotifica == null  || notificheAusl.id.codNotifica == ''}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${notificheAusl.id.codNotifica!=null && notificheAusl.id.codNotifica != ''}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm?filterIndirizzo=&filterRagSoc=','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>