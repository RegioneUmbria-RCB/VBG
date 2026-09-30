<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_validita_coefficienti.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_validita_coefficienti.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<form name="ccvaliditacoefficientiForm" action="list.htm">
			<jmesa:springTableFacade
				id="ccvaliditacoefficienti_id" 
				items="${ccvaliditacoefficientiList}" 
				var="ccvaliditacoefficienti_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.DateCoefficientiValiditaFilterMatcherMap" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${ccvaliditacoefficienti_var.id.codice}">${ccvaliditacoefficienti_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione" />
						<jmesa:htmlColumn property="datainiziovalidita" titleKey="label.data_inizio_validita" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>"  cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataInizioCoefficienteCustomFilter" width="8%" />
						<jmesa:htmlColumn property="costomq" titleKey="label.costo_mq" style="text-align:right;" headerStyle="text-align:right;" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${ccvaliditacoefficienti_var.id.codice}" title="<fmt:message key="label.edit.record" />${ccvaliditacoefficienti_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="label.lista_validita_coefficienti.title" />';
		</script>	
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>