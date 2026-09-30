<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${infosuap.id.codice==null}">
			<fmt:message key="infosuap.label.nuovo_infosuap.title" />
		</c:if> 
		<c:if test="${infosuap.id.codice!=null}">
			<fmt:message key="infosuap.label.dettaglio_infosuap.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${infosuap.id.codice==null}">
			<fmt:message key="infosuap.label.nuovo_infosuap.title" />
		</c:if> 
		<c:if test="${infosuap.id.codice!=null}">
			<fmt:message key="infosuap.label.dettaglio_infosuap.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="infosuap" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="infosuap" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="infosuap.label.titolo" />
					</td>
					<td>
						<spring-form:textarea id="titolo_id" path="titolo" cols="70" />
						<spring-form:errors path="titolo" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td> 
						<spring-form:textarea id="descrizione_id" path="descrizione" cols="70"/>
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.ordine" />
					</td>
					<td>
						<spring-form:input id="ordine_id" path="ordine" size="2" onchange="checkNumberInt(this)" maxlength="2" cssStyle="text-align:right;"/>
						<spring-form:errors path="ordine" cssClass="error"/>
					</td>
				</tr>				
			</table>			
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${infosuap.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${infosuap.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<script type="text/javascript">
		tinyMCE.init({
  			mode: "exact",   			
			elements: "descrizione_id, titolo_id", 
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