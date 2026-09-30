<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.lista_documenti_messi_alla_firma.title" />
	</title>
</head>
	<body>
		<span class="titoloPagina">
			<fmt:message key="label.lista_documenti_messi_alla_firma.title" />
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		    <fieldset>
		    <input type="checkbox" id="CONF_UTENTE_VIS_MESSI_ALLA_FIRMA_DA_ME_id" 
						    onclick="filtraPerUtente('<%=WebConstants.CONF_UTENTE_VIS_MESSI_ALLA_FIRMA_DA_ME%>',this)" ${CONF_UTENTE_VIS_MESSI_ALLA_FIRMA_DA_ME_CHECKED}/>
		    <fmt:message key="label.solo_miei" />
		    </fieldset>
			<div id="subcontent">
				<form name="documentiDaFirmare" action="${formAction}">					
						${documentiMessiAllaFirmaHtmlTable}					
						
				</form>
				<script type="text/javascript">
				
					var _jmesaUrl='documentiMessiAllaFirmaList.htm?';
					var _captionTab='<fmt:message key="label.lista_documenti_messi_alla_firma.title" />';
				</script>	
				
				
				<script type="text/javascript">			
					
				    function filtraPerUtente(nomeparametro, objchk)
				    {
				    	salvaPreferenza(nomeparametro, objchk);
				    	javascript:doHref('documentiMessiAllaFirmaList.htm','');
				    	
				    }
					function salvaPreferenza(nomeparametro, objchk){
						var valore = "0";	
						if(objchk.checked==true){
							valore="1";	
						}
						saveUserPreference(nomeparametro, valore);
					}
				</script>	 
				
							
		</div>
		<div id="metti_alla_firma_fun" style="display: none;">
			<div id="functions">
				<ul>
					<li><a href="javascript:firma();"><fmt:message key="label.firma_i_documenti" /></a></li>
				</ul>
			</div>
		</div>
		<br class="clear" />
		<div id="functions">
			<ul>
				<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>		
	</body>
</html>
	