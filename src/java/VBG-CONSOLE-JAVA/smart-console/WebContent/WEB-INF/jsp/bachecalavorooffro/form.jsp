<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${bachecalavorooffro.id.codice==null}">
			<fmt:message key="bachecalavorooffro.label.nuovo_bachecalavorooffro.title" />
		</c:if> 
		<c:if test="${bachecalavorooffro.id.codice!=null}">
			<fmt:message key="bachecalavorooffro.label.dettaglio_bachecalavorooffro.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${bachecalavorooffro.id.codice==null}">
			<fmt:message key="bachecalavorooffro.label.nuovo_bachecalavorooffro.title" />
		</c:if> 
		<c:if test="${bachecalavorooffro.id.codice!=null}">
			<fmt:message key="bachecalavorooffro.label.dettaglio_bachecalavorooffro.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="bachecalavorooffro" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="bachecalavorooffro" />
		    </jsp:include>
			<table>
				<tr>
 					<td>
    					<fmt:message key="label.nominativo" />
 					</td> 												
					<td>
						<spring-form:input id="anagrafe_id" size="67" path="anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden')" onkeydown="javascript:return searchAll(this,event)"/> 
						<init:autocompleter methodAjax="findAnagrafe.htm" minChars="3" idHidden="anagrafe_hidden" idInput="anagrafe_id" inputTitleKey="label.ricerca_nominativo"/>
						<spring-form:errors path="anagrafe" cssClass="error" /> 
						<spring-form:hidden id="anagrafe_hidden" path="anagrafe.id.codice" />
					</td>					
				</tr>				
				<tr>
					<td>
						<fmt:message key="bachecalavorooffro.label.annuncio" />
					</td>
					<td>
						<spring-form:textarea id="annuncio_id" path="annuncio" rows="4" cols="54"/>
						<spring-form:errors path="annuncio" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="bachecalavorooffro.label.qualifica" />
					</td>
					<td>
						<spring-form:input id="qualifica_id" path="qualifica" size="70" />
						<spring-form:errors path="qualifica" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="bachecalavorooffro.label.titolodistudio" />
					</td>
					<td>
						<spring-form:input id="titolodistudio_id" path="titolodistudio" size="70" />
						<spring-form:errors path="titolodistudio" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="bachecalavorooffro.label.contattare" />
					</td>
					<td>
						<spring-form:textarea id="contattare_id" path="contattare" rows="3" cols="54" />
						<spring-form:errors path="contattare" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="bachecalavorooffro.label.tipocontratto" />
					</td>
					<td>
						<spring-form:input id="tipocontratto_id" path="tipocontratto" size="70" />
						<spring-form:errors path="tipocontratto" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.comune" />						
					</td>
					<td>					
						<spring-form:input id="comuni_id" path="comuni.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comuni_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter methodAjax="findComuni.htm" idHidden="comuni_hidden"  minChars="2" idInput="comuni_id" inputTitleKey="label.ricerca_comune"/>
						<spring-form:errors	path="comuni" cssClass="error"	/>
						<spring-form:hidden id="comuni_hidden" path="comuni.codicecomune"  />
					</td>
				</tr>				
				<tr>
					<td>
						<fmt:message key="label.indirizzo" />
					</td>
					<td>
						<spring-form:textarea id="sedelavoroindirizzo_id" path="sedelavoroindirizzo" rows="2" cols="54"/>
						<spring-form:errors path="sedelavoroindirizzo" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="bachecalavorooffro.label.scadenza" />
					</td>
					<td>
						<spring-form:input  tabindex="2" id="scadenza_id" path="scadenza" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
		   				<init:calendar idImage="caldata" idInput="scadenza_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
		   				<spring-form:errors	path="scadenza" cssClass="error" />
					</td>
				</tr>				
			</table>
			<script type='text/javascript'>
				$('annuncio_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${bachecalavorooffro.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${bachecalavorooffro.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>