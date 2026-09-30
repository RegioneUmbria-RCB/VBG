<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key='label.istanze-presentate' /></title>
</head>
<body>
	<div class="titolo"><fmt:message key='label.istanze-presentate' /></div>
	<div class="descrizione"></div>
	<form name="istanzeForm" action="list.htm">
		<jmesa:springTableFacade id="tag" items="${istanze}" var="bean">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn titleKey="label.numero-istanza" property="numeroPratica" filterable="false" sortable="false" />
					<jmesa:htmlColumn titleKey="label.data-istanza" property="dataPratica" pattern="dd/MM/yyyy" cellEditor="it.gruppoinit.pal.gp.areariservata.web.util.ExtendedDateCellEditor" filterable="false" sortable="false" width="12%" />
					<jmesa:htmlColumn titleKey="label.numero-protocollo" property="numeroProtocolloGenerale" filterable="false" sortable="false" />
					<jmesa:htmlColumn titleKey="label.data-protocollo" property="dataProtocolloGenerale" pattern="dd/MM/yyyy" cellEditor="it.gruppoinit.pal.gp.areariservata.web.util.ExtendedDateCellEditor" filterable="false" sortable="false" width="12%" />
					<jmesa:htmlColumn filterable="false" sortable="false" width="160px">
    					<a class="table_button" href="javascript:statoAvanzamento('${bean.idPratica }')" title="<fmt:message key='label.stato' />"><fmt:message key='label.stato' /></a>
    				</jmesa:htmlColumn>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableFacade>
	</form>
	<br />
	<a class="table_button" href="${pageContext.request.contextPath}/istanze/search.htm"><fmt:message key='button.chiudi' /></a>
	<script type="text/javascript">
		$(".table_button").button();
		function onInvokeAction(id) {
		    createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function statoAvanzamento(codiceIstanza){		
			document.location.href="${pageContext.request.contextPath}/istanze/view.htm?codiceIstanza="+codiceIstanza;
		}
	</script>
</body>
</html>