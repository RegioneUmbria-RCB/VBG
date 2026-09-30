<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="form.gestionepresenze.sistema_posizioni_debitorie_concessionario" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.gestionepresenze.sistema_posizioni_debitorie_concessionario" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>
<div id="subcontent">

	<h1 class="error_header">
	<fmt:message key="form.gestionepresenze.sistema_posizioni_debitorie_concessionario.help" />
	</h1>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatipresenzeT" />
    </jsp:include>
    <table>
		<tr>
			<td><fmt:message key="form.gestionepresenze.sistema_posizioni_debitorie_concessionario.data" /></td>
			<td class="inline-ui-cell" valign="middle" style="vertical-align: middle;">
			
			<input type="text" id="data_id" name="data" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
			<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="data_id" textKey="label.calendar"/>
			
	
			</td>
		</tr>

	</table>


	<h2 id="output_id" class="warning_header"></h2>
	
	
</div>
<script type="text/javascript">
	function ok(){
		
		
		var data = jQuery('#data_id').val();
		
		if(data != ''){
			if(confirm('Attenzione! La procedura creerà le posizioni debitorie per i concessionari che non hanno ottenuto la posizione debitoria. \nContinuare?')){
			disableFunctions();
				var jhqrPr = jQuery.ajax({
					  url: '../gestionepresenze/ajaxSistemaPosizioniDebitorieConcessionariDelGiorno.htm',
					  data: "data="+data,
					  context: document.body,
					  cache: false,					  
					  dataType: "html",
					  success: function(result){
						  enableFunctions();
						  alert(result);
						  jQuery('#output_id').html(result);
					  },
				      error: function (xhr, ajaxOptions, thrownError) {
				    	enableFunctions();
				        alert(xhr.status);
				        alert(thrownError);
				      }
				});
			}
		
		}else{
			alert("La data è obbligatoria");
		}
	}

</script>
<div id="functions">
<ul>
	<li><a href="javascript:ok()"><fmt:message key="button.ok" /></a></li>
	
	
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
