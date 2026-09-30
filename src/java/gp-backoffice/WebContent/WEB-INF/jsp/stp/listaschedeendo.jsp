<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.verifica_schede_endo" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.verifica_schede_endo" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="stpCommand" />
	</jsp:include>
	
	<fieldset><legend><fmt:message key="label.schede_endo_tipo1" /></legend>
	
		<form name="domainForm" action="verificaSchedeEndo.htm">
			<jmesa:springTableFacade
				id="schedeendo1_id" 
				items="${listaSchede1}" 
				var="scheda1_var"				
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%" />
						<jmesa:htmlColumn property="inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo" titleKey="label.tipi_famiglie_endo" />
						<jmesa:htmlColumn property="inventarioprocedimenti.tipoendo.tipo" titleKey="label.tipi_endo" />
						<jmesa:htmlColumn property="inventarioprocedimenti.procedimento" titleKey="label.inventarioprocedimenti" />
						<%--
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${scheda1_var.id.codice}" title="<fmt:message key="label.edit.record" />${scheda1_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
						 --%>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='verificaSchedeEndo.htm?';
			var _captionTab='<fmt:message key="label.schede_endo_tipo1" />';
		</script>
		
		<div id="functions">
			<ul>			
				<li><a href="javascript:doHref('richiediDownloadSchede.htm?endoTipo=TIPO_1','')"><fmt:message key="stp.button.richiedi_schede_endo1" /></a></li>
			</ul>
		</div>
		
		
		</fieldset>
		
		
		<fieldset><legend><fmt:message key="label.schede_endo_tipo2" /></legend>
		
		<form name="domainForm" action="verificaSchedeEndo.htm">
			<jmesa:springTableFacade
				id="schedeendo2_id" 
				items="${listaSchede2}" 
				var="scheda2_var"
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%" />
						<jmesa:htmlColumn property="alberoproc.vwAlberoproc.scDescrizione" titleKey="label.alberoproc" />
						<%--
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${scheda2_var.id.codice}" title="<fmt:message key="label.edit.record" />${scheda2_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
						 --%>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
		var _jmesaUrl='verificaSchedeEndo.htm?';
		var _captionTab='<fmt:message key="label.schede_endo_tipo1" />';
		</script>
		
		<div id="functions">
			<ul>			
				<li><a href="javascript:doHref('richiediDownloadSchede.htm?endoTipo=TIPO_2','')"><fmt:message key="stp.button.richiedi_schede_endo2" /></a></li>
			</ul>
		</div>
		
		</fieldset>
	</div>
	<div id="functions">
		<ul>			
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>