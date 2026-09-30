<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${dyn2modellit.id.codice==null}">
			<fmt:message key="label.nuovo_dyn2modellit.title" />
		</c:if> 
		<c:if test="${dyn2modellit.id.codice!=null}">
			<fmt:message key="label.dettaglio_dyn2modellit.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${dyn2modellit.id.codice==null}">
			<fmt:message key="label.nuovo_dyn2modellit.title" />
		</c:if> 
		<c:if test="${dyn2modellit.id.codice!=null}">
			<fmt:message key="label.dettaglio_dyn2modellit.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../dyn2modellit/view" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="dyn2modellit" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="dyn2modellit" />
		    </jsp:include>
			<table width="100%">
				<tr>
					<td>
						<fmt:message key="label.codice" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:input id="codicescheda_id" path="codiceScheda" size="70" />
						<spring-form:errors path="codiceScheda" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
				<input type="hidden" name="basecontesti.id" value="IS" id="contesti_id"/>
				<%-- 
				<tr>
					<td><fmt:message key="label.contesti" /></td>
					<td>
						<spring-form:select id="contetsi_id" path="basecontesti.id"> 
						<spring-form:options items="${basecontestis}" itemLabel="contesto" itemValue="id" />
						</spring-form:select>
						<spring-form:errors path="basecontesti" cssClass="error"/>
					</td>
				</tr>
				 --%>
				<!-- §§§BEGIN§§§ -->
				<%-- TODO LA GESTIONE STANDARD NON PREVEDE LA GESTIONE DI QUESTE INFORMAZIONI
			
					<tr>
						<td><fmt:message key="label.modello_multiplo" /></td>
						<td><spring-form:checkbox id="modellomultiplo_id" path="modellomultiplo"/>
						<spring-form:errors path="modellomultiplo" cssClass="error"/></td>
			       </tr>
			       <tr>
						<td><fmt:message key="label.flg_storicizza" /></td>
						<td><spring-form:checkbox id="flgStoricizza_id" path="flgStoricizza"/>
						<spring-form:errors path="flgStoricizza" cssClass="error"/></td>
			       </tr>
		       
		       --%>       
		       <!-- §§§END§§§ -->
		       <tr>
					<td><fmt:message key="label.solo_lettura" /></td>
					<td class="inline-ui-cell">
						<spring-form:checkbox id="flgReadonlyWeb_id" path="flgReadonlyWeb"/>
						<init:help idHelp="help_flgReadonlyWeb" textKey="dyn2modellit.help.flg_readonly_web"/>
						<spring-form:errors path="flgReadonlyWeb" cssClass="error"/>
					</td>
		       </tr>
		       
			</table>
			<script type='text/javascript'>
				$('codicescheda_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${dyn2modellit.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${dyn2modellit.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>				
				<li><a href="javascript:historySet('${_urlback}','../dyn2modellit/viewFormule.htm?codice=${dyn2modellit.id.codice}','',document.inviodati)"><fmt:message key="button.formule" /></a></li>
				<li><a href="javascript:historySet('${_urlback }','../dyn2modellid/list.htm?codiceModelloT=${dyn2modellit.id.codice}','')"><fmt:message key="button.gestioni_campi" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>