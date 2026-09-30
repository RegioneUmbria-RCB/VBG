<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.import-file" /></title>
</head>
<body>
	<div class="titolo">
		<fmt:message key="label.import-file" />
	</div>
	<div class="descrizione"><a href="${pageContext.request.contextPath }/ajax/ajaxGetTracciatoXSD.htm" target="_new">scarica modello del tracciato</a></div>
	<c:choose>
	<c:when test="${param.ret eq 0 }">
		<label style="color: green; font-weight: bold;"><fmt:message key='label.file-elaborato-con-successo' /></label>			
	</c:when>
	<c:otherwise>
	<spring-form:form commandName="fileUploadCommand" action="upload.htm" method="post" name="fileUploadForm" enctype="multipart/form-data">
		<table class="sezione_table" border="0">
		<tr>	
			<td width="10%" valign="top">
				<input id="fileupload" type="file" name="file" />
			</td>
			<td width="90%" align="left" valign="top"><spring-form:errors path="file" cssClass="validation_error" htmlEscape="false" /></td>
		</tr>
	</table>
	<table class="sezione_table">
	<tr>
		<td class="sezione_table_buttons">
			<input type="button" value="<fmt:message key='button.conferma' />" id="conferma" />
			<input type="button" value="<fmt:message key='button.chiudi' />" id="annulla" />
		</td>
	</tr>
	</table>
	</spring-form:form>
	<script type="text/javascript">
		
		$("#annulla").click(function(){
			window.location.replace("../home/start.htm");
		});
		$("#conferma").click(function(){
			document.fileUploadForm.submit();
		});
	</script>
	</c:otherwise>
	</c:choose>
</body>
</html>