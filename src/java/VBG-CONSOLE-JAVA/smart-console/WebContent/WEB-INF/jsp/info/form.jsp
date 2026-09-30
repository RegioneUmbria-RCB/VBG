<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${info.id.codice==null}">
			<fmt:message key="info.label.nuovo_info.title" />
		</c:if> 
		<c:if test="${info.id.codice!=null}">
			<fmt:message key="info.label.dettaglio_info.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${info.id.codice==null}">
			<fmt:message key="info.label.nuovo_info.title" />
		</c:if> 
		<c:if test="${info.id.codice!=null}">
			<fmt:message key="info.label.dettaglio_info.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="info" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="info" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="info.label.titolo" />
					</td>
					<td>
						<spring-form:textarea id="titolo_id" path="titolo" cols="70"/>
						<spring-form:errors path="titolo" cssClass="error"/>
					</td>
				</tr>				
				<tr>
					<td>
						<fmt:message key="info.label.oggetti" />
					</td>
					<td>
					<jsp:include page="../includes/oggetti.jsp" >
	       				<jsp:param name="idElemento" value="oggettoIdCodice" />
	   					<jsp:param name="codiceOggetto" value="${info.oggetti.id.codice}" />
	   					<jsp:param name="idComuneOggetto" value="${info.oggetti.id.idcomune}" />
	   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
	   					<jsp:param name="nomefileId" value="oggetto_nomefile" />
    				</jsp:include>
    				<spring-form:hidden path="oggetti.id.codice" id="oggetto_id_codice"/>
    				<spring-form:hidden path="oggetti.nomefile" id="oggetto_nomefile"/>
    				<spring-form:errors path="oggetti" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="info.label.indirizzoweb" />
					</td>
					<td>
						<spring-form:input id="indirizzoweb_id" path="indirizzoweb" size="70" />
						<spring-form:errors path="indirizzoweb" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.ordine" />
					</td>
					<td>
						<spring-form:input id="ordine_id" path="ordine" size="3" onchange="checkNumberInt(this)" maxlength="3" cssStyle="text-align:right;"/>
						<spring-form:errors path="ordine" cssClass="error"/>
					</td>
				</tr>								
			</table>
			<script type='text/javascript'>
				$('titolo_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${info.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${info.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
		<script type="text/javascript">
		initTextEditors();
		/*
		tinyMCE.init({
  			mode: "exact",   			
			elements: "titolo_id", 
  			theme: "advanced",
  			theme_advanced_toolbar_location: "top",
  			theme_advanced_toolbar_align: "left",  			
  			plugins: "safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,xhtmlxtras,template",
  		  	theme_advanced_buttons1: "newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,visualchars,|,fullscreen,|,template,|,code",
  			theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent,blockquote,|,forecolor,backcolor",
  			theme_advanced_buttons3: "link,unlink,anchor,image,cleanup,|,insertdate,inserttime,|,tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,media,advhr",
  			forced_root_block : false,
  	        force_br_newlines : true,
  	        force_p_newlines : false
		});
		*/
	</script>		
</body>
</html>