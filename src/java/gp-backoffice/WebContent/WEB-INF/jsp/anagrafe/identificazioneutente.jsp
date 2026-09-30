<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Calendar"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Anagrafe"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.web.AnagrafeCommand" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="button.identificazione_utente" />
	</title>
</head>
<body>
 
	<span class="titoloPagina">
			<fmt:message key="button.identificazione_utente" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../anagrafe/view" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="anagrafe" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="anagrafe" />
		    </jsp:include>
		    <fieldset>
			<table width="100%" style="padding: 10px;">
			<c:if test="${anagrafe.entity.flagDisabilitato eq 1}">
				<tr><td style="color: red;font-weight: bold;font-size: 1.1em;">Utente disabilitato dal <fmt:formatDate value="${anagrafe.entity.dataDisabilitato}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /> </td></tr>
			</c:if>
			<tr>
					<td style="line-height: 40px;">
						In data <b><fmt:formatDate value="<%=Calendar.getInstance().getTime() %>" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>"/></b>
						<br/> l'operatore <b>${ responsabile.responsabile }</b> contrassegna come <br/>
						</b><b>"IDENTIFICATO"</b> per l'accesso al portale 
						<br /><b>${anagrafe.entity.descrizioneRichiedente }</b>  
					</td>
			</tr>						
			</table>	
			</fieldset>
		</spring-form:form>
	</div>	
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('${anagrafe.prefixPopup}updateidentificato.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<c:if test="${anagrafe.popup eq false or empty anagrafe.popup}">
				<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
			</c:if>
			<c:if test="${anagrafe.popup eq true}">
				<li><a href="javascript:self.close()"><fmt:message key="button.back" /></a></li>
			</c:if>
		</ul>
	</div>
	<c:if test="${anagrafe.popup eq true}">
		<%if(StringUtils.defaultString(request.getParameter("done"),"false").equalsIgnoreCase("true")){ 		
		String descrizioneRichiedente = ((AnagrafeCommand)request.getAttribute("anagrafe")).getEntity().getDescrizioneRichiedente().replace("'","\\'"); 		
		%>			
		<script type="text/javascript">
		jQuery(document).ready(function(){
			opener.jQuery('#${anagrafe.popupCaller}').val('<%= descrizioneRichiedente%>');
			opener.jQuery('#${anagrafe.popupCaller}_hidden').val('${anagrafe.entity.id.codice}');
			opener.jQuery('#${anagrafe.popupCaller}').change();
		});
		</script>				
		<%} %>
	</c:if>	
</body>
</html>