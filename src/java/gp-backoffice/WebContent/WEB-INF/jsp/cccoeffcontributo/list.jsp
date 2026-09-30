<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_cccoeffcontributo.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_cccoeffcontributo.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<form name="cccoeffcontributoForm" action="list.htm">
			<jmesa:springTableFacade
				id="cccoeffcontributo_id" 
				items="${cccoeffcontributoList}" 
				var="cccoeffcontributo_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${cccoeffcontributo_var.id.codice}">${cccoeffcontributo_var.id.codice}</a>
                        </jmesa:htmlColumn>							
						<jmesa:htmlColumn property="ccDestinazioni.destinazione" titleKey="label.destinazione" />
						<jmesa:htmlColumn property="ccTipointervento.intervento" titleKey="label.tipo_intervento" />
						<jmesa:htmlColumn property="coefficiente" titleKey="label.coefficiente" sortable="false" filterable="false">
							 <fmt:formatNumber minFractionDigits="2" value="${cccoeffcontributo_var.coefficiente}" />
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${cccoeffcontributo_var.id.codice}" title="<fmt:message key="label.edit.record" />${cccoeffcontributo_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
				<input type="hidden" value="${ccValiditacoefficienti.id.codice}" name="codiceCoefficiente"/>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codiceCoefficiente=${ccValiditacoefficienti.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_cccoeffcontributo.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codiceCoefficiente=${ccValiditacoefficienti.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>