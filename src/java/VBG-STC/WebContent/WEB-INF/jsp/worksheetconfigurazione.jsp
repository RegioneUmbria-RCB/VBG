<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jmesa.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
		<title><fmt:message key="label.stc" /></title>
	</head>
	<body>
<!-- §§§BEGIN§§§ -->
		<h1><fmt:message key="label.stc.desc" /></h1>	
		<h3 style="display: inline;"><fmt:message key="label.benvenuto_utente" />: <spring-security:authentication property="principal.username" /> </h3>
		<button name="Logout" value="Logout" onclick="logout()"><fmt:message key="label.logout" /></button>
		<div style="height: 50px;">&nbsp;</div>
		<div class="jmesa" style="float: left;">
			<table class="table">
				<caption>Versione STC</caption>
				<tbody class="tbody">
				<tr class="even">
					<td>webapp ${webapp_version}</td>	
					<td>stc.xsd ${stc_xsd_version}</td>
					<td>nla.xsd ${nla_xsd_version}</td>
					<td>types.xsd ${types_xsd_version}</td>
				</tr>
				</tbody>
			</table>
		</div>
		<div class="jmesa" style="float: left; margin-left: 10px;">
			<table class="table">
				<caption>WSDL</caption>
				<tbody class="tbody">
				<tr class="even">
					<td><a href="${pageContext.request.contextPath}/services/stc?wsdl" title="Visualizza il wsdl">STC</a></td>
					<td><a href="${pageContext.request.contextPath}/services/nla?wsdl" title="Visualizza il wsdl">NLA</a></td>	
				</tr>
				</tbody>
			</table>
		</div>
		<br style="clear: left;"/>
		<br />
		<form 
			id="formConfigurazione" 
			name="formConfigurazione" 
			action="${pageContext.request.contextPath}/worksheetconfigurazione/list.htm" 
			style="text-align: left;">
			${configurazioni}
		</form>
		<br />
		<button name="ListaPratiche" value="LitaPratiche" onclick="listaPratiche()"><fmt:message key="label.lista.pratiche" /></button>
		<button name="ListaMessaggiPratiche" value="ListaMessaggiPratiche" onclick="listaMessaggiPratiche()">Messaggi pratiche</button>
		<button name="ListaMessaggiAttivita" value="ListaMessaggiAttivita" onclick="listaMessaggiAttivita()">Messaggi attivita</button>
		<button name="TestNodi" value="TestNodi" onclick="testNodi()"><fmt:message key="label.test.nodi" /></button>
		<p>${saveResults}</p>
		<c:if test="${testnodi != null }">
			<div class="jmesa">
				<table class="table">
				<caption>Test nodi NLA</caption>
				<tbody class="tbody">
				<c:forEach items="${testnodi }" var="testnodo" varStatus="idx">
					<c:if test="${idx.index%2==0}"><c:set var="rc" value="even"/></c:if><c:if test="${idx.index%2!=0}"><c:set var="rc" value="odd"/></c:if>
					<tr class="${rc }">
						<td>${testnodo.idnodo }</td>
						<td>${testnodo.descrizione }</td>
						<td>
							<c:if test="${ empty testnodo.transientTestNLAErrorMessage}">
								<label style="color:green">ATTIVO</label>
							</c:if>
							<c:if test="${ not empty testnodo.transientTestNLAErrorMessage}">
								<label style="color:red">${testnodo.transientTestNLAErrorMessage}</label>
							</c:if>
						</td>
						<td>
							<label style="color:green">${testnodo.transientTestNLASuccessMessage}</label>
						</td>
					</tr>
				</c:forEach>
				</tbody>
				</table>
			</div>
		</c:if>
		<script type="text/javascript">
			function onInvokeAction(id) {     
				createHiddenInputFieldsForLimitAndSubmit(id); 
			}
			function logout(){
				document.location.href = "${pageContext.request.contextPath}/j_spring_security_logout";
			}
			function testNodi(){
				document.location.href = "${pageContext.request.contextPath}/worksheetconfigurazione/list.htm?test=true";
			}
			function listaPratiche(){
				document.location.href = "${pageContext.request.contextPath}/pratiche/list.htm";
			}
			function listaMessaggiPratiche(){
				document.location.href = "${pageContext.request.contextPath}/listaattivita/listPratiche.htm";
			}
			function listaMessaggiAttivita(){
				document.location.href = "${pageContext.request.contextPath}/listaattivita/listAttivita.htm";				
			}
		</script>
<!-- §§§END§§§ -->
	</body>
</html>