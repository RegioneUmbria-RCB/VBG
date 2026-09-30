<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<c:if test="${Letteretipo.id.codice==null}">
			<fmt:message key="letteretipo.label.nuova_lettera.title" />
		</c:if> 
		<c:if test="${letteretipo.id.codice!=null}">
			<fmt:message key="letteretipo.label.dettaglio_lettera.title" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${letteretipo.id.codice==null}">
	<fmt:message key="letteretipo.label.nuova_lettera.title" />
</c:if> 
<c:if test="${letteretipo.id.codice!=null}">
	<fmt:message key="letteretipo.label.dettaglio_lettera.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="letteretipo" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="letteretipo" />        
    </jsp:include>
    		<c:choose>
				<c:when test="${letteretipo.flagDisabilitato eq true}">
					<br class="clear"/>
					<div id="disabilitato_status_msg" class="alertLine">
			    		<b><fmt:message key="label.record_disabilitato"/></b>
			    	</div>
			    	<br class="clear"/>
				</c:when>
			</c:choose>			
	<table>
		<tr>
			<td><fmt:message key="letteretipo.label.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="70" />
			<spring-form:errors path="descrizione" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="letteretipo.label.file" /></td>
			<td>
				<jsp:include page="../includes/oggetti.jsp" >
	       			<jsp:param name="idElemento" value="fileIdCodice" />
	   				<jsp:param name="codiceOggetto" value="${letteretipo.file.id.codice}" />
	   				<jsp:param name="codiceOggettoId" value="file_id_codice" />
	   				<jsp:param name="nomefileId" value="file_nomefile" />
    			</jsp:include>
    			<spring-form:hidden path="file.id.codice" id="file_id_codice"/>
    			<spring-form:hidden path="file.nomefile" id="file_nomefile"/>
    			<spring-form:errors path="file" cssClass="error"/>
			</td>
		</tr>
	</table>

	<script type='text/javascript'>
		$('descrizione_id').focus();
	</script>	
	</spring-form:form>
</div>

	<fieldset><legend><fmt:message key="letteretipo.label.templates"/></legend>
		<div class="parametriDiv">
			<div class="etichetta">
				<c:if test="${not empty codiceoggettomoddoctipo}">
					<div><fmt:message key="letteretipo.label.modello.comune" />:</div>
				</c:if>
				<div><fmt:message key="letteretipo.label.modello.sistema" />:</div>				
			</div>
			<div class="parametro">		
				<c:if test="${not empty codiceoggettomoddoctipo}">
					<div>
						<a href="../file/ajaxDownload.htm?fileId=${codiceoggettomoddoctipo}" target="blank" title="<fmt:message key="letteretipo.label.modello.comune.download"/>"><fmt:message key="letteretipo.label.modello.comune.download"/></a>
					</div>				
				</c:if>
				<div>
					<a href="<%=BackofficeNETConstants.getUrlTo(request, BackofficeNETConstants.getURL_RTF_BASE_DOC(), null, null, true) %>" target="blank" title='<fmt:message key="letteretipo.label.modello.sistema.download"/>'>base.doc</a>
				</div>
			</div>
		</div>		
		<br />
	</fieldset>
	
	<br />

<div id="functions">
<ul>
	<c:if test="${letteretipo.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${letteretipo.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li>
		
			<c:choose>
				<c:when test="${letteretipo.flagDisabilitato eq true}">
					<a href="javascript:doSubmit('abilitaDisabilita.htm','<fmt:message key="javascript.confirm.abilita"><fmt:param value="Lettere tipo"/></fmt:message>',document.inviodati)"><fmt:message key="button.abilita" /></a>
				</c:when>
				<c:otherwise>
					<a href="javascript:doSubmit('abilitaDisabilita.htm','<fmt:message key="javascript.confirm.disabilita"><fmt:param value="Lettere tipo" /></fmt:message>',document.inviodati)"><fmt:message key="button.disabilita" /></a>
				</c:otherwise>
			</c:choose>

		</li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<!--  <li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>-->
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>

	

</body>
</html>
