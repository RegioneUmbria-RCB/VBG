<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipiprocedure.label.lista_tipiprocedure.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipiprocedure.label.lista_tipiprocedure.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../tipiprocedure/list" />	
	</jsp:include>
	<div id="subcontent">
		<form name="tipiprocedureForm" action="list.htm">
			<jmesa:springTableFacade
				id="tipiprocedure_id" 
				items="${tipiprocedureList}" 
				var="tipiprocedure_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore"
				filterMatcherMap="org.jmesa.custom.SiNoFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="javascript:historySet('${_urlback}','../tipiprocedure/view.htm?codice=${tipiprocedure_var.id.codice}&software=${tipiprocedure_var.software.codice}','');">${tipiprocedure_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="procedura" titleKey="tipiprocedure.label.procedura" />
						<jmesa:htmlColumn property="naturaendo.natura" titleKey="tipiprocedure.label.natura_endo" width="10%" />
						<jmesa:htmlColumn property="giorni" titleKey="tipiprocedure.label.giorni_previsti" width="5%"/>
						<jmesa:htmlColumn property="flagDisabilitato" 
											cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor"
                                            filterEditor="org.jmesa.custom.SiNoDroplist"
                                            titleKey="label.disabilitato" width="5%"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../tipiprocedure/view.htm?codice=${tipiprocedure_var.id.codice}&software=${tipiprocedure_var.software.codice}','');" title="<fmt:message key="label.edit.record" />&nbsp;${tipiprocedure_var.procedura}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="tipiprocedure.label.lista_tipiprocedure.title" />';
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