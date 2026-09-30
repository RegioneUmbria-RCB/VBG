<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${quesiti.entity.id.codice==null}">
			<fmt:message key="quesiti.label.nuovo_quesiti.title" />
		</c:if> 
		<c:if test="${quesiti.entity.id.codice!=null}">
			<fmt:message key="quesiti.label.dettaglio_quesiti.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${quesiti.entity.id.codice==null}">
			<fmt:message key="quesiti.label.nuovo_quesiti.title" />
		</c:if> 
		<c:if test="${quesiti.entity.id.codice!=null}">
			<fmt:message key="quesiti.label.dettaglio_quesiti.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="quesiti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="quesiti" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="quesiti.label.nominativo" />
					</td>
					<td >
						<spring-form:input id="nominativo_id" path="entity.nominativo" size="70" disabled="true" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.indirizzo" />
					</td>
					<td>
						<spring-form:input id="indirizzo_id" path="entity.indirizzo" size="70" disabled="true" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.cap" />
					</td>
					<td>
						<spring-form:input id="cap_id" path="entity.cap" size="5" disabled="true" />&nbsp;&nbsp;

						<fmt:message key="label.citta" />&nbsp;&nbsp;
						<spring-form:input id="citta_id" path="entity.citta" size="32" disabled="true" />&nbsp;&nbsp;
					
						<fmt:message key="label.provincia" />&nbsp;
						<spring-form:input id="provincia_id" path="entity.provincia" size="2" disabled="true" />
					</td>
				</tr>
				<tr>					
					<td>
						<fmt:message key="label.telefono1" />
					</td>
					<td>
						<spring-form:input id="telefono_id" path="entity.telefono" size="29" disabled="true" />&nbsp;&nbsp;					
						<fmt:message key="label.fax" />&nbsp;&nbsp;					
						<spring-form:input id="fax_id" path="entity.fax" size="28" disabled="true" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.email" />
					</td>
					<td>
						<spring-form:input id="email_id" path="entity.email" size="70" readonly="true" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="quesiti.label.quesito" />
					</td>
					<td>
						<spring-form:textarea id="quesito_id" path="entity.quesito" cols="54" disabled="true" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="quesiti.label.risposta" />
					</td>
					<td>
						<spring-form:textarea id="risposta_id" path="entity.risposta" cols="70"/>
						<spring-form:errors path="entity.risposta" cssClass="error"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="label.categoria" />
					</td>
					<td>
						<spring-form:input id="faqclassi_id" path="entity.faqclassi.faqclasse" cssClass="searchbox" onchange="checkValue(this,'faqclassi_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
						<init:autocompleter methodAjax="findFaqclassi.htm" idHidden="faqclassi_hidden" idInput="faqclassi_id" inputTitleKey="label.ricerca_faqclassi"></init:autocompleter>
						<spring-form:errors path="entity.faqclassi" cssClass="error"/>
						<spring-form:hidden id="faqclassi_hidden" path="entity.faqclassi.id.codice"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="quesiti.label.letto" />
					</td>
					<td>
						<spring-form:checkbox id="letto_id" path="entity.letto"/>
						<spring-form:errors path="entity.letto" cssClass="error"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="label.tipo" />
					</td>
					<td>
						<spring-form:select path="entity.software.codice">
						<spring-form:options items="${quesiti.softwares}" itemLabel="descrizione" itemValue="codice"/>
						</spring-form:select>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('risposta_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>			
			<c:if test="${quesiti.entity.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('creaFaq.htm','',document.inviodati)"><fmt:message key="button.creafaq" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<script type="text/javascript">
		tinyMCE.init({
  			mode: "exact",   			
			elements: "risposta_id", 
  			theme: "advanced",
  			theme_advanced_toolbar_location: "top",
  			theme_advanced_toolbar_align: "left",  			
  			plugins: "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
  		  	theme_advanced_buttons1: "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
  			theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
  			theme_advanced_buttons3: "link,unlink,anchor,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,advhr",
  			forced_root_block : false,
  	        force_br_newlines : true,
  	        force_p_newlines : false
		});
	</script>		
</body>
</html>