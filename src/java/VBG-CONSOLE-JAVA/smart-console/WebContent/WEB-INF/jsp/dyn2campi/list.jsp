<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.dyn2campi.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.dyn2campi.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../dyn2campi/list" />
	</jsp:include>
	<div id="subcontent">
		<form name="dyn2campiForm" action="list.htm">
			<jmesa:springTableFacade
				id="dyn2campi_id" 
				items="${dyn2campiList}" 
				var="dyn2campi_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="javascript:doHref('../dyn2campi/view.htm?codice=${dyn2campi_var.id.codice}','');">${dyn2campi_var.id.codice}</a>
                        </jmesa:htmlColumn>
                        <jmesa:htmlColumn property="nomecampo" titleKey="label.nome_campo" />								
						<jmesa:htmlColumn property="etichetta" titleKey="label.etichetta" />
						<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione" />
						<%--
						<jmesa:htmlColumn property="contestoTransiet" titleKey="label.contesto" />
						 --%>
						<jmesa:htmlColumn property="tipodato" titleKey="label.tipo_dato">
						    
						    <c:if test="${dyn2campi_var.tipodato == 'Testo'}">
						    	<fmt:message key="label.testo" />
						    </c:if>
						    <c:if test="${dyn2campi_var.tipodato eq 'NumericoIntero' }">
						    	<fmt:message key="label.numero_intero" />
						    </c:if>
						    <c:if test="${dyn2campi_var.tipodato eq 'NumericoDouble' }">
						    	<fmt:message key="label.numero_decimale" />
						    </c:if>
						    <c:if test="${dyn2campi_var.tipodato == 'Checkbox' }">
						    	<fmt:message key="label.casella_spunta" />
						    </c:if>
						    <c:if test="${dyn2campi_var.tipodato eq 'Lista' }">
						    	<fmt:message key="label.lista_valori" />
						    </c:if>
						    <c:if test="${dyn2campi_var.tipodato eq 'ListaSIGePro' }">
						    	<fmt:message key="label.lista_valori_sigepro" />
						    </c:if>
						     <c:if test="${dyn2campi_var.tipodato eq 'Data' }">
						    	<fmt:message key="label.data" />
						    </c:if>
						     <c:if test="${dyn2campi_var.tipodato eq 'Ricerca' }">
						    	<fmt:message key="label.ricerca_sigepro" />
						    </c:if>
						    <c:if test="${dyn2campi_var.tipodato eq 'MultiLista' }">
						    	<fmt:message key="label.multi_lista_valori" />
						    </c:if>
						    
						
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="javascript:doHref('../dyn2campi/view.htm?codice=${dyn2campi_var.id.codice}','');" title="<fmt:message key="label.edit.record" />${dyn2campi_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="label.lista_dyn2campi.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../dyn2campi/create.htm','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>