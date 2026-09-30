<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="inventarioprocedimenti.label.list_mapping_stp.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="inventarioprocedimenti.label.list_mapping_stp.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../inventarioprocedimenti/view" />
		<jsp:param name="qs" value="codice%3D${inventarioprocedimenti.entity.id.codice}" />	
	</jsp:include>
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
		</div>		
		<div class="parametro">       		 	
			<div>${inventarioprocedimenti.entity.procedimento}</div>
		</div>
	</div>
	<br class="clear" />
	<div id="subcontent">
		<form name="mappingSTPForm" action="listMappingSTP.htm">
				<jmesa:springTableFacade
					id="mappingSTP_id" 
					items="${inventarioprocedimenti.entity.inventarioprocedimentipeoples}" 
					var="mappingSTP_var" 
					stateAttr="restore">
						<jmesa:htmlTable>
							<jmesa:htmlRow>					
								<jmesa:htmlColumn property="codProcPeople" titleKey="label.codiceProcSTP">
									<a href="viewMappingSTP.htm?codice=${mappingSTP_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${mappingSTP_var.id.codice}">
										${mappingSTP_var.codProcPeople}
									</a>
								</jmesa:htmlColumn>
								<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
									<a class="dettaglioColumn" href="viewMappingSTP.htm?codice=${mappingSTP_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${mappingSTP_var.id.codice}">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
								</jmesa:htmlColumn>
							</jmesa:htmlRow>
						</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" name="codiceInventario" value="${inventarioprocedimenti.entity.id.codice}" />
			</form>
			<script type="text/javascript">
				var _jmesaUrl='listMappingSTP.htm?';
				var _captionTab='<fmt:message key="inventarioprocedimenti.label.list_mapping_stp.title" />';
			</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createMappingSTP.htm?codiceInventario=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:historyBack('')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>