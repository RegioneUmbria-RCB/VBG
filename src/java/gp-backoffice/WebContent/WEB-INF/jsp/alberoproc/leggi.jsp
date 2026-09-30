<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="alberoproc.label.dettaglio_leggi.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="alberoproc.label.dettaglio_leggi.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
       		<div class="etichetta"> 
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionepadre}
	        	</div>
	        	<br />
	        	<div>
	        		${alberoproc.vwAlberoproc.scDescrizionebreve}
	        	</div>
	        </div>
    </div>
    <div class="clear"></div>
		<spring-form:form commandName="alberoprocLeggi" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoprocLeggi" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="alberoproc.label.legge" />
					</td>
					<td>
						<spring-form:select id="legge_id" path="legge.id.codice"  >
						<c:forEach items="${leggiList}" var="leggi_var">
							<c:if test="${leggi_var.leggitipi.ltDescrizione != null && leggi_var.leggitipi.ltDescrizione != ''}">
								<spring-form:option value="${leggi_var.id.codice}" label="${leggi_var.leggitipi.ltDescrizione} - ${leggi_var.leDescrizione}"></spring-form:option>
							</c:if>
							<c:if test="${leggi_var.leggitipi.ltDescrizione == null || leggi_var.leggitipi.ltDescrizione == ''}">
								<spring-form:option value="${leggi_var.id.codice}" label="${leggi_var.leDescrizione}"></spring-form:option>
							</c:if>
						</c:forEach>
						</spring-form:select>
						<spring-form:errors path="legge" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('legge_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('insertLegge.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=' + ${alberoproc.id.codice} + '#legge_anchor','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>