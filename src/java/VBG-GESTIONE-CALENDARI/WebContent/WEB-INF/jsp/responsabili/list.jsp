<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.responsabili" /></title>
</head>
<body>
	<div class="titolo"><fmt:message key="label.responsabili" /></div>
	<div class="descrizione"></div>
	<form name="responsabiliForm" action="list.htm">
		<jmesa:springTableModel id="tag" items="${resps}" var="bean">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn titleKey="label.nome" property="responsabile" filterable="true" sortable="false"/>
					<jmesa:htmlColumn titleKey="label.userid" property="userid" filterable="true" sortable="false" />
					<jmesa:htmlColumn titleKey="label.gestioneFiereMostre" property="gestioneFiereMostre" filterable="false" sortable="false" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor" />
					<jmesa:htmlColumn titleKey="label.gestioneFesteSagre" property="gestioneFesteSagre" filterable="false" sortable="false" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor" />
					<jmesa:htmlColumn titleKey="label.readonly" property="readonly" filterable="false" sortable="false" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor" />
					<jmesa:htmlColumn titleKey="label.disabilitato" property="disabilitato" filterable="false" sortable="false" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor" />					
					<jmesa:htmlColumn filterable="false" sortable="false" width="20%">
						<a class="table_button" href="javascript:visualizza('${bean.id.codice }')" title="<fmt:message key='label.visualizza' />"><fmt:message key='button.visualizza' /></a>
    					<c:if test="${bean.amministratore eq false }">
    					<a class="table_button" href="javascript:elimina('${bean.id.codice }')" title="<fmt:message key='label.elimina' />"><fmt:message key='button.elimina' /></a>
						</c:if>
					</jmesa:htmlColumn>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableModel>
	</form>
	<br />
	<a class="button" id="aggiungi_resp" href="#" title="<fmt:message key='label.aggiungi' />"><fmt:message key='button.aggiungi' /></a>
	<a class="button" id="chiudi" href="#" title="<fmt:message key='label.chiudi' />"><fmt:message key='button.chiudi' /></a>
	<script type="text/javascript">
		$(".table_button").button();
		function onInvokeAction(id) {
		    createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function visualizza(codiceresp){		
			location.href="${pageContext.request.contextPath}/responsabili/view.htm?codiceresp="+codiceresp;
		}
		function elimina(codiceresp){		
			if(confirm("<fmt:message key='alert.elimina' />")){
				location.href="${pageContext.request.contextPath}/responsabili/elimina.htm?codiceresp="+codiceresp;
			}
		}
		$("#aggiungi_resp").click(function(){
			location.href="${pageContext.request.contextPath}/responsabili/view.htm";
		});
		$("#chiudi").click(function(){
			location.href="${pageContext.request.contextPath}/home/start.htm";
		});
	</script>
</body>
</html>