<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:if test="${faq.id.codice==null}">
		<fmt:message key="faq.label.nuovo_faq.title" />
	</c:if> <c:if test="${faq.id.codice!=null}">
		<fmt:message key="faq.label.dettaglio_faq.title" />
	</c:if></title>
</head>
<body>
	<span class="titoloPagina"> <c:if test="${faq.id.codice==null}">
			<fmt:message key="faq.label.nuovo_faq.title" />
		</c:if> <c:if test="${faq.id.codice!=null}">
			<fmt:message key="faq.label.dettaglio_faq.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="faq" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="faq" />
			</jsp:include>
			<table>
				<tr>
					<td><fmt:message key="label.data" /></td>
					<td><spring-form:input tabindex="2" id="data_id" path="data"
							size="10" maxlength="10" onblur="isValidDate(this,true);" /> <init:calendar
							idImage="caldata" idInput="data_id" imagePath="/images/cal.gif"
							textKey="label.calendar" /> <spring-form:errors path="data"
							cssClass="error" /></td>
				</tr>
				<tr>
					<td><fmt:message key="faq.label.faqclassi" /></td>
					<td><spring-form:input id="faqclassi_id"
							path="faqclassi.faqclasse" cssClass="searchbox"
							onchange="checkValue(this,'faqclassi_hidden')"
							onkeydown="javascript:return searchAll(this,event)" size="35" />
						<init:autocompleter methodAjax="findFaqclassi.htm"
							idHidden="faqclassi_hidden" idInput="faqclassi_id"
							inputTitleKey="label.ricerca_faqclassi"></init:autocompleter> <spring-form:errors
							path="faqclassi" cssClass="error" /> <spring-form:hidden
							id="faqclassi_hidden" path="faqclassi.id.codice" /></td>
				</tr>
				<tr>
					<td><fmt:message key="faq.label.software" /></td>
					<td><spring-form:select path="software.codice">
							<spring-form:options items="${listSoftware}"
								itemLabel="descrizione" itemValue="codice" />
						</spring-form:select></td>
				</tr>

				<tr>
					<td><fmt:message key="faq.label.domanda" /></td>
					<td><spring-form:textarea id="domanda_id" path="domanda"
							cols="70" /> <spring-form:errors path="domanda" cssClass="error" />
					</td>
				</tr>
				<tr>
					<td><fmt:message key="faq.label.risposta" /></td>
					<td><spring-form:textarea id="risposta_id" path="risposta"
							cols="70" /> <spring-form:errors path="risposta" cssClass="error" />
					</td>
				</tr>

				<tr>
					<td><fmt:message key="label.ordine" /></td>
					<td><spring-form:input id="ordine_id" path="ordine" size="5"
							maxlength="3" /> <spring-form:errors path="ordine"
							cssClass="error" /></td>
				</tr>

				<tr>
					<td><fmt:message key="label.pubblica" /></td>
					<td><spring-form:checkbox id="pubblicare_id" path="pubblicare" />
						<spring-form:errors path="pubblicare" cssClass="error" /></td>
				</tr>
			</table>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${faq.id.codice==null}">
				<li><a
					href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message
							key="button.insert" /></a></li>
			</c:if>
			<c:if test="${faq.id.codice!=null}">
				<li><a
					href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message
							key="button.update" /></a></li>
				<li><a
					href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
							key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
	<script type="text/javascript">
		initTextEditors();
		/*
		tinyMCE.init({
					mode : "exact",
					elements : "domanda_id, risposta_id",
					theme : "advanced",
					theme_advanced_toolbar_location : "top",
					theme_advanced_toolbar_align : "left",
					plugins : "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
					theme_advanced_buttons1 : "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
					theme_advanced_buttons2 : "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
					theme_advanced_buttons3 : "link,unlink,anchor,image,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,media,advhr",
					forced_root_block : false,
					force_br_newlines : true,
					force_p_newlines : false
				});
		*/
	</script>
</body>
</html>