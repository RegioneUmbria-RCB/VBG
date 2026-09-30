<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${endoCausali.id.codice==null}">
			<fmt:message key="form.endoCausali.title.create" />
		</c:if> 
		<c:if test="${endoCausali.id.codice!=null}">
			<fmt:message key="form.endoCausali.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${endoCausali.id.codice==null}">
	<fmt:message key="form.endoCausali.title.create" />
</c:if> 
<c:if test="${endoCausali.id.codice!=null}">
	<fmt:message key="form.endoCausali.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="endoCausali" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="endoCausali" />
    </jsp:include>
<table width="100%">
	<tr>
		<td>	
			<table>
				<tr>
					<td>
						<fmt:message key="form.registrazioniCausali.descrizione" />
					</td>
					<td>
						<spring-form:input id="registrazioniCausali_id" path="registrazioniCausali.descrizione" cssClass="searchbox" onkeydown="javascript:return searchAll(this,event)" onchange="checkValue(this,'registrazioniCausali_hidden')" size="60"/>
						<init:autocompleter methodAjax="findRegistrazioniCausali.htm" idHidden="registrazioniCausali_hidden" idInput="registrazioniCausali_id" inputTitleKey="label.ricerca_causale"/>
						<spring-form:errors path="registrazioniCausali" cssClass="error"/> 
						<spring-form:hidden id="registrazioniCausali_hidden" path="registrazioniCausali.id.codice"  />
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('registrazioniCausali_id').focus();
			</script>	
			
		
			<div id="functions">
			<ul>
				<c:if test="${endoCausali.id.codice==null}">
					<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
				</c:if>
				<c:if test="${endoCausali.id.codice!=null}">
					<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
					<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				</c:if>
				<li><a href="javascript:doHref('list.htm?inventarioprocedimenti.id.codice=${endoCausali.inventarioprocedimenti.id.codice}','')"><fmt:message key="button.back" /></a></li>
			</ul>
			</div>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
			<c:if test="${endoCausali.id.codice!=null}">
			<div class="titoloTabella"><fmt:message key="form.endoConti.title" /></div>
			<form name="endoContiForm" action="view.htm">
			<jmesa:springTableFacade
				id="endoConti_id"
				items="${endoCausali.endoContis}"
				var="endoConti_var" maxRows="10" exportTypes=""
				stateAttr="restore">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
		                      <a href="../endoconti/view.htm?codice=${endoConti_var.id.codice}">${endoConti_var.id.codice}</a>
		                </jmesa:htmlColumn>
						<jmesa:htmlColumn property="importo" titleKey="form.endoConti.importo" cellEditor="org.jmesa.view.editor.NumberCellEditor" pattern="###.##" />
						<jmesa:htmlColumn property="conti.descrizione"	titleKey="form.conti.descrizione" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record"	sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn"	href="../endoconti/view.htm?codice=${endoConti_var.id.codice}"	title="<fmt:message key="label.edit.record" /> ${endoConti_var.conti.descrizione}">
							<label><fmt:message key="label.edit.record.image" /></label> </a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			</jmesa:springTableFacade> <input type="hidden" value="${endoCausali.id.codice}"
				name="codice" /></form>
			<c:if test="${endoCausali.id.codice!=null}">
				<div id="functions">
				<ul>
					<li><a
						href="javascript:doSubmit('../endoconti/create.htm?codiceendoCausali=${endoCausali.id.codice}','',document.inviodati)"><fmt:message
						key="form.endoCausali.endoconti" /></a></li>
				</ul>
				</div>
			</c:if>
			</c:if>
		</td>
	</tr>
</table>
</spring-form:form>
</div>
</body>
</html>