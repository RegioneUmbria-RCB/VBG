<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.istanze-in-sospeso" /></title>
</head>
<body>
	<div class="titolo"><fmt:message key="label.istanze-in-sospeso" /></div>
	<div class="descrizione"></div>
	<form name="domandeForm" action="list.htm">
		<jmesa:springTableFacade id="tag" items="${domande}" var="bean">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn titleKey="label.codice-domanda" property="idDomanda" filterable="false" sortable="false" />
					<jmesa:htmlColumn titleKey="label.data-modifica" property="dataUltimaModifica" filterable="false" sortable="false" cellEditor="org.jmesa.view.editor.DateWithTimeCellEditor" width="20%" />
					<jmesa:htmlColumn filterable="false" sortable="false" width="160px">
    					<a class="table_button" href="javascript:riprendiDomanda('${bean.id.codice }', '${bean.idDomanda }')" title="<fmt:message key='label.riprendi-domanda' /> ${bean.idDomanda }"><fmt:message key="button.riprendi" /></a>
    					<a class="table_button" href="javascript:eliminaDomanda('${bean.id.codice }', '${bean.idDomanda }')" title="<fmt:message key='label.elimina-domanda' /> ${bean.idDomanda }"><fmt:message key="button.elimina" /></a>
					</jmesa:htmlColumn>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableFacade>
	</form>
	<script type="text/javascript">
		$(".table_button").button();
		function onInvokeAction(id) {
		    createHiddenInputFieldsForLimitAndSubmit(id);
		}
		function eliminaDomanda(id, idDomanda){
			if(confirm("<fmt:message key='alert.elimina-domanda' />\n"+idDomanda)){
				document.location.href="${pageContext.request.contextPath}/domande/delete.htm?id="+id;
			}
		}
		function riprendiDomanda(id, idDomanda){
			if(confirm("<fmt:message key='alert.riprendi-domanda' />\n"+idDomanda)){
				document.location.href="${pageContext.request.contextPath}/nuovaistanza/riprendi.htm?id="+id;
			}
		}
	</script>
</body>
</html>