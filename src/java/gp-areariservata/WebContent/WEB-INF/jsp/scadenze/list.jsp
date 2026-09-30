<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key='label.scadenze' /></title>
</head>
<body>
	<div class="titolo"><fmt:message key='label.scadenze' /></div>
	<div class="descrizione"></div>
	<c:if test="${param.success eq true }">
	<label class="success"><fmt:message key='label.scadenza-completata' /></label>
	</c:if>
	<form name="scadenzeForm" action="list.htm">
		<jmesa:springTableFacade id="tag" items="${scadenze}" var="bean">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn titleKey="label.data-scadenza" property="datascadenzastr" filterable="false" sortable="false" width="12%" />
					<jmesa:htmlColumn titleKey="label.descrizione" property="descrmovimentodafare" filterable="false" sortable="false" />
					<jmesa:htmlColumn titleKey="label.numero-istanza" property="numeroistanza" filterable="false" sortable="false" />
					<jmesa:htmlColumn titleKey="label.numero-protocollo" property="numeroprotocollo" filterable="false" sortable="false" />
					<jmesa:htmlColumn titleKey="label.data-protocollo" property="dataprotocollo" pattern="dd/MM/yyyy" cellEditor="it.gruppoinit.pal.gp.areariservata.web.util.ExtendedDateCellEditor" filterable="false" sortable="false" width="12%" />
					<jmesa:htmlColumn titleKey="label.richiedente" property="ricNominativo" filterable="false" sortable="false" />
					<jmesa:htmlColumn titleKey="label.tecnico" property="tecNominativo" filterable="false" sortable="false" />
					<jmesa:htmlColumn titleKey="label.azienda" property="azNominativo" filterable="false" sortable="false" />
					<jmesa:htmlColumn filterable="false" sortable="false">
    					<a class="table_button" href="javascript:view('${bean.id.codice }')" title="<fmt:message key='label.visualizza' />"><fmt:message key='button.visualizza' /></a>
					</jmesa:htmlColumn>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableFacade>
	</form>
	<br />
	<script type="text/javascript">
		$(".table_button").button();
		function onInvokeAction(id) {
		    createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function view(codice){		
			document.location.href="${pageContext.request.contextPath}/scadenze/view.htm?codice="+codice;
		}
	</script>
</body>
</html>