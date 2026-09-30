<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="bachecalavorocerca.label.lista_bachecalavorocerca.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="bachecalavorocerca.label.lista_bachecalavorocerca.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<form name="bachecalavorocercaForm" action="list.htm">
			<jmesa:springTableFacade
				id="bachecalavorocerca_id" 
				items="${bachecalavorocercaList}" 
				var="bachecalavorocerca_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.BachecalavorocercaFilterMatcherMap" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${bachecalavorocerca_var.id.codice}">${bachecalavorocerca_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="label.nominativo" />
						<jmesa:htmlColumn property="annuncio" titleKey="bachecalavorocerca.label.annuncio" />
						<jmesa:htmlColumn property="qualifica" titleKey="bachecalavorocerca.label.qualifica" />
						<jmesa:htmlColumn property="titolodistudio" titleKey="bachecalavorocerca.label.titolodistudio" />
						<jmesa:htmlColumn property="scadenza" titleKey="bachecalavorocerca.label.scadenza" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.ScadenzaBachecalavorocercaCustomFilter" width="8%"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${bachecalavorocerca_var.id.codice}" title="<fmt:message key="label.edit.record" />${bachecalavorocerca_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="bachecalavorocerca.label.lista_bachecalavorocerca.title" />';
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