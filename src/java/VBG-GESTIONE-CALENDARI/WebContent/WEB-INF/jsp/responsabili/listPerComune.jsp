<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.responsabili" /></title>
</head>
<body>
	<div class="titolo">${comune.comune }</div>
	<div class="descrizione"><fmt:message key="label.responsabili" /></div>
	<form name="responsabiliForm" action="listPerComune.htm">
		<jmesa:springTableModel id="tag" items="${responsabili}" var="bean">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn titleKey="label.nome" property="responsabile.responsabile" filterable="true" sortable="false" />
					<jmesa:htmlColumn titleKey="label.userid" property="responsabile.userid" filterable="true" sortable="false" />
					<jmesa:htmlColumn titleKey="label.gestioneFiereMostre" property="responsabile.gestioneFiereMostre" filterable="false" sortable="false" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor" />
					<jmesa:htmlColumn titleKey="label.gestioneFesteSagre" property="responsabile.gestioneFesteSagre" filterable="false" sortable="false" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor" />					
					<jmesa:htmlColumn titleKey="label.readonly" property="responsabile.readonly" filterable="false" sortable="false" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor" />
					<jmesa:htmlColumn titleKey="label.disabilitato" property="responsabile.disabilitato" filterable="false" sortable="false" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor" />					
					<%-- 
					<jmesa:htmlColumn filterable="false" sortable="false" width="20%">
						<a class="table_button" href="javascript:visualizza('${bean.responsabile.id.codice }')" title="<fmt:message key='label.visualizza' />"><fmt:message key='button.visualizza' /></a>
    					<a class="table_button" href="javascript:elimina('${bean.responsabile.id.codice }')" title="<fmt:message key='label.elimina' />"><fmt:message key='button.elimina' /></a>
					</jmesa:htmlColumn>
					--%>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableModel>
	</form>
	<br />
	<%--
	<a class="button" id="aggiungi_resp" href="javascript:aggiungi()" title="<fmt:message key='label.aggiungi' />"><fmt:message key='button.aggiungi' /></a>
	--%>
	<a class="button" id="chiudi_resp" href="#" title="<fmt:message key='label.chiudi' />"><fmt:message key='button.chiudi' /></a>
	<script type="text/javascript">
		$(".table_button").button();
		function onInvokeAction(id) {
		    createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function visualizza(codiceresp){		
			document.location.href="${pageContext.request.contextPath}/responsabili/viewPerComune.htm?codicecomune=${comune.codicecomune}&codiceresp="+codiceresp;
		}
		function aggiungi(){		
			document.location.href="${pageContext.request.contextPath}/responsabili/viewPerComune.htm?codicecomune=${comune.codicecomune}";
		}
		function elimina(codiceresp){
			if(confirm("<fmt:message key='alert.elimina' />")){
				document.location.href="${pageContext.request.contextPath}/responsabili/eliminaPerComune.htm?codicecomune=${comune.codicecomune}&codiceresp="+codiceresp;
			}
		}
		$("#chiudi_resp").click(function(){
			location.href="${pageContext.request.contextPath}/enti/list.htm";
		});
	</script>
</body>
</html>