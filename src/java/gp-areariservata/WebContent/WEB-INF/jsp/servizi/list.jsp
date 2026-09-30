<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key='label.lista-servizi' /></title>
</head>
<body>
	<div class="titolo"><fmt:message key='label.lista-servizi' /></div>
	<div class="descrizione"></div>
	<c:set var="req" value="${pageContext.request}" />
	<c:set var="baseURL" value="${fn:replace(req.requestURL, fn:substring(req.requestURI, 0, fn:length(req.requestURI)), req.contextPath)}" />
	<form name="serviziForm" action="list.htm">
		<jmesa:springTableFacade id="tag" items="${servizi}" var="bean">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn titleKey="label.url-servizio" property="a" filterable="false" sortable="false">
						<a href="#" onclick="vaiAServizio('${bean.urlServizio}')">${baseURL}/servizi/${bean.urlServizio}</a>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn titleKey="label.url-ripresa-domande-per-servizio" property="b" filterable="false" sortable="false">
						<a href="#" onclick="vaiADomande('${bean.urlServizio}')">${baseURL}/compilazione/${bean.urlServizio}</a>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn titleKey="label.servizio" property="c" filterable="false" sortable="false">
						${bean.alberoproc.vwAlberoproc.scDescrizione}
					</jmesa:htmlColumn>
					<jmesa:htmlColumn titleKey="label.anonimo" property="anonimo" cellEditor="org.jmesa.custom.SiNoCellEditor" filterable="false" sortable="false" />
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableFacade>
	</form>
	<script type="text/javascript">
		function onInvokeAction(id) {
		    createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function vaiAServizio(url) {
		    document.location.replace("../servizi/"+url);
		}
		function vaiADomande(url) {
		    document.location.replace("../compilazione/"+url);
		}
	</script>
</body>
</html>