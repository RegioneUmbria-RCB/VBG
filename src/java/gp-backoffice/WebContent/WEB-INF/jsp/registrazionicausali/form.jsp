<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${registrazioniCausali.id.codice==null}">
			<fmt:message key="form.registrazioniCausali.title.create" />
		</c:if> 
		<c:if test="${registrazioniCausali.id.codice!=null}">
			<fmt:message key="form.registrazioniCausali.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${registrazioniCausali.id.codice==null}">
	<fmt:message key="form.registrazioniCausali.title.create" />
</c:if> 
<c:if test="${registrazioniCausali.id.codice!=null}">
	<fmt:message key="form.registrazioniCausali.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="registrazioniCausali" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="registrazioniCausali" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="form.registrazioniCausali.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="70" />
			<spring-form:errors path="descrizione" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniCausali.ordine" /></td>
			<td><spring-form:input id="ordine_id" path="ordine" size="2" maxlength="4"/>
			<spring-form:errors path="ordine" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniCausali.note" /></td>
			<td><spring-form:textarea id="note_id" path="note" cols="70" rows="4" />
			<spring-form:errors path="note" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniCausali.richiedePosteggio"/></td>
			<td><spring-form:checkbox id="richiedePosteggio_id" path="richiedePosteggio"  onclick="javascript:validatePosteggio(this)"/>
			<spring-form:errors path="richiedePosteggio" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniCausali.richiedeEndo" /></td>
			<td><spring-form:checkbox id="richiedeEndo_id" path="richiedeEndo"  onclick="javascript:validateEndo(this)"/>
			<spring-form:errors path="richiedeEndo" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniCausali.nonPrevedeIncassi"/></td>
			<td><spring-form:checkbox id="nonPrevedeIncassi_id" path="nonPrevedeIncassi"/>
			<spring-form:errors path="nonPrevedeIncassi" cssClass="error"/>
			<init:help idHelp="help_nonPrevedeIncassi" textKey="form.registrazioniCausali.nonPrevedeIncassi.help"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniCausali.soloImportiNegativi"/></td>
			<td><spring-form:checkbox id="soloImportiNegativi_id" path="soloImportiNegativi"/>
			<spring-form:errors path="soloImportiNegativi" cssClass="error"/>
			<init:help idHelp="help_soloImportiNegativi" textKey="form.registrazioniCausali.soloImportiNegativi.help"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniCausali.abilitato" /></td>
			<td><spring-form:checkbox id="abilitato_id" path="abilitato"  />
			<spring-form:errors path="abilitato" cssClass="error"/></td>
		</tr>
	</table>
	
	<script type='text/javascript'>
		$('descrizione_id').focus();

		function validatePosteggio(posteggio){
			var endo=document.getElementById("richiedeEndo_id");
			if(endo.checked){
				posteggio.checked=false;
				alert('<fmt:message key="form.registrazioniCausali.validate.checkbox"/>');
			}else{
				if(posteggio.checked){
					posteggio.checked=true;
				}else{
					posteggio.checked=false;
				}
			}
		}
		function validateEndo(endo){
			var posteggio=document.getElementById("richiedePosteggio_id");
			if(posteggio.checked){
				endo.checked=false;
				alert('<fmt:message key="form.registrazioniCausali.validate.checkbox"/>');
			}else{
				if(endo.checked){
					endo.checked=true;
				}else{
					endo.checked=false;
				}
			}
		}
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${registrazioniCausali.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${registrazioniCausali.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
