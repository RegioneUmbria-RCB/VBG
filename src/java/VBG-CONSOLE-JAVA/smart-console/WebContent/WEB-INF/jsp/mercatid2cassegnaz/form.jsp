<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${mercatid2cassegnaz.id.codice==null}">
			<fmt:message key="label.nuovo_mercatid2cassegnaz.title" />
		</c:if> 
		<c:if test="${mercatid2cassegnaz.id.codice!=null}">
			<fmt:message key="label.dettaglio_mercatid2cassegnaz.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${mercatid2cassegnaz.id.codice==null}">
			<fmt:message key="label.nuovo_mercatid2cassegnaz.title" />
		</c:if> 
		<c:if test="${mercatid2cassegnaz.id.codice!=null}">
			<fmt:message key="label.dettaglio_mercatid2cassegnaz.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="mercatid2cassegnaz" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercatid2cassegnaz" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.modello" />
					</td>
					<td>
					<jsp:include page="../includes/autocompletergenericoTT.jsp" >
						<jsp:param name="idElemento" value="dyn2Modellit" />		
						<jsp:param name="propertyPath" value="dyn2Modellit" />				
						<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
						<jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
						<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm?codicesoftware=" />
						<jsp:param name="id_help" value="help_modello" />	
						<jsp:param name="help" value="help.modelli_archivi_base" />
					</jsp:include>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.campo" />
					</td>
					<td>
					<script type="text/javascript">
							function filter(element, entry) {
								return entry + "&codiceModello=" + document.getElementById("dyn2Modellit_hidden").value;								
							}
					</script>
					<jsp:include page="../includes/autocompletergenericoTT.jsp" >
						<jsp:param name="idElemento" value="dyn2Campi" />		
						<jsp:param name="propertyPath" value="dyn2Campi" />				
						<jsp:param name="pathPropertyDescription" value="dyn2Campi.nomecampo" />
						<jsp:param name="pathPropertyCode" value="dyn2Campi.id.codice" />
						<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?codiceSoftware=" />
						<jsp:param name="ajaxCallBack" value="filter" />
						<jsp:param name="id_help" value="help_campo" />	
					</jsp:include>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${mercatid2cassegnaz.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mercatid2cassegnaz.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceMercato=${mercatid2cassegnaz.mercati.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>