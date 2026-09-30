<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.procedura_reset_backup_allegati_stc" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.procedura_reset_backup_allegati_stc" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="istanze" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanze" />
		    </jsp:include>
			<table>
				<tr>
					<td colspan="2">
						<b><fmt:message key="label.descrizione_procedura_di_reset_backup_stc_allegati" /></b>
					</td>
				</tr>
				<tr >
					<td colspan="2"></td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.codice" />
					</td>
					<td>
						<spring-form:input id="id.codice_id" path="id.codice" size="20" />
						<spring-form:errors path="id.codice" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('id.codice_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('resetOperazioneBackupSTC.htm','',document.inviodati)"><fmt:message key="button.reset" /></a></li>
			<li><a href="javascript:doHref('../admin/view.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>