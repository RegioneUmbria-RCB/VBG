<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="javax.xml.datatype.XMLGregorianCalendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="it.init.sigepro.rte.types.DettaglioPraticaVisuraType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.visura-istanza" /></title>
</head>
<body>
	<div class="titolo"><fmt:message key="label.visura-istanza" /></div>
	<div class="descrizione"></div>
	<fieldset><legend><fmt:message key="label.dati-istanza" /></legend>
	<fmt:message key="label.numero-istanza" />: <b>${istanza.dettaglioPratica.numeroPratica } </b>
	<%
	Date dprat = ((DettaglioPraticaVisuraType)request.getAttribute("istanza")).getDettaglioPratica().getDataPratica().toGregorianCalendar().getTime(); 
	pageContext.setAttribute("_dataPratica", dprat);
	XMLGregorianCalendar dProt = ((DettaglioPraticaVisuraType)request.getAttribute("istanza")).getDettaglioPratica().getDataProtocolloGenerale();
	if(dProt!=null){
	    Date _dProt = dProt.toGregorianCalendar().getTime();
	    pageContext.setAttribute("_dataProtocollo", _dProt);
	}
	
	%>
	<fmt:formatDate value="${_dataPratica}" var="dataPratica" pattern="dd/MM/yyyy" />
	<fmt:formatDate value="${_dataProtocollo}" var="dataProtocollo" pattern="dd/MM/yyyy" />
	<fmt:message key="label.data-istanza" />: <b><c:out value="${dataPratica}" /></b><br />
	<fmt:message key="label.numero-protocollo" />: <b><c:out value="${istanza.dettaglioPratica.numeroProtocolloGenerale }" default="-" /> </b>
	<fmt:message key="label.data-protocollo" />: <b><c:out value="${dataProtocollo}" default="-" /></b><br />	
	</fieldset>					
	<br />
	<fieldset><legend><fmt:message key="label.lista-attivita" /></legend>
	<form name="istanzaForm" action="view.htm">
		<jmesa:tableFacade id="tag" items="${istanza.listaAttivita}" var="bean">
			<jmesa:htmlTable width="100%">
				<jmesa:htmlRow>
					<jmesa:htmlColumn title="Descrizione" property="tipoAttivita.descrizione" filterable="false" sortable="false" />
					<jmesa:htmlColumn title="Data" property="dataAttivita" pattern="dd/MM/yyyy" cellEditor="it.gruppoinit.pal.gp.areariservata.web.util.ExtendedDateCellEditor" filterable="false" sortable="false" />
					<jmesa:htmlColumn title="Esito" filterable="false" sortable="false">
						<c:if test="${bean.esito eq true }"><fmt:message key="label.esito-positivo" /></c:if>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn title="Parere" property="parere" filterable="false" sortable="false" />
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:tableFacade>
	</form>
	</fieldset>
	<br />
	<a class="table_button" href="${pageContext.request.contextPath}/istanze/list.htm"><fmt:message key="button.chiudi" /></a>
	<script type="text/javascript">
		$(".table_button").button();
		function onInvokeAction(id) {
		    createHiddenInputFieldsForLimitAndSubmit(id);
		}
	</script>
</body>
</html>