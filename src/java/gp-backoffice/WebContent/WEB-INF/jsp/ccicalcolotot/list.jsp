<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_ccicalcolotot.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_ccicalcolotot.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanze.id.codice}</c:param>
	</c:import>
	<div id="subcontent">
		<form name="ccicalcolototForm" action="list.htm">
			<jmesa:springTableFacade
				id="ccicalcolotot_id" 
				items="${ccicalcolototList}" 
				var="ccicalcolotot_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.DateCcIcalcolototFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${ccicalcolotot_var.id.codice}">${ccicalcolotot_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione"/>
						<jmesa:htmlColumn property="data" titleKey="label.data" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>"  cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataCcIcalcolototCustomFilter" width="10%"/>
						<jmesa:htmlColumn property="ccValiditacoefficienti.descrizione" titleKey="label.listino_coefficienti"/>
						<jmesa:htmlColumn property="quotacontribTotale" titleKey="label.totali_contributo" width="6%" style="text-align:right;" headerStyle="text-align:right;">
							<fmt:formatNumber minFractionDigits="2">${ccicalcolotot_var.quotacontribTotale}</fmt:formatNumber> &euro;
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${ccicalcolotot_var.id.codice}" title="<fmt:message key="label.edit.record" />${ccicalcolotot_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" name="codiceIstanza" value="${istanze.id.codice}"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codiceIstanza=${istanze.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_ccicalcolotot.title"/>';
		</script>
		
		
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?CodiceIstanza=${istanze.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>