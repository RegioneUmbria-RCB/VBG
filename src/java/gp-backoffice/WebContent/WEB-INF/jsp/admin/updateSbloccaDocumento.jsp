<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.pannello_di_amministrazione" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.pannello_di_amministrazione" />	
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../admin/view" />
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="administration" />
	</jsp:include>
	<div id="subcontent">
	<form name="inviodati" id="updateComuneIstanzaForm">	
	
	<table>
		<tr>
			<td>Codice oggetto</td>
			<td>
				<td><input type="text"  id="codice_id" name="oggetto.id.codice" size="20" />
			</td>
		</tr>	
	</table>
	
	</table>
	</fieldset>		
		<div id="functions">
			<ul>
				<li><a href="javascript:void 0;" onclick="sblocca();">Sblocca</a></li>
				<li><a href="javascript:doHref('../admin/view.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>	
		</form>		
	</div>	
		<script type="text/javascript">
		
		function sblocca(){
		
			doSubmit('../admin/updateSbloccaDocumentoExec.htm','',$('updateComuneIstanzaForm'));	
		}
		
		
		</script>	
</body>
</html>