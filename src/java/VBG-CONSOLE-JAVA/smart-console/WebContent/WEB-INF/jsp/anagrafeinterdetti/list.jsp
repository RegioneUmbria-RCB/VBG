<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_anagrafe_interdetti" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_anagrafe_interdetti" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../anagrafeinterdetti/list" />
	</jsp:include>
	<div id="subcontent">
		<form name="anagarfeinteedettiForm" action="list.htm">
			<jmesa:springTableFacade
				id="anagarfeinterdetti_id" 
				items="${anagrafeInterdettiList}" 
				var="anagrafeInterdetti_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.AnagarfeinterdetteFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codiceanagrafe" titleKey="label.codice" width="2%">
                           	<a href="javascript:historySet('${_urlback }','../anagrafe/view.htm?codice=${anagrafeInterdetti_var.id.codiceanagrafe}','')" >${anagrafeInterdetti_var.id.codiceanagrafe}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="anagrafe.richiedente" titleKey="label.nominativo" />
						<jmesa:htmlColumn property="anagrafe.descrizioneResidenza" titleKey="label.residenza" />
						<jmesa:htmlColumn property="anagrafe.corrispondenza" titleKey="label.corrispondenza" />
						<jmesa:htmlColumn property="id.datascadenza" titleKey="label.data_fine_interdizione" width="8%" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataScadenzaAnagrafeinterdettiCustomFilter" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../anagrafe/view.htm?codice=${anagrafeInterdetti_var.id.codiceanagrafe}','')" title="<fmt:message key="label.edit.record" />&nbsp;${anagrafeInterdetti_var.anagrafe.richiedente}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="label.lista_anagrafe_interdetti" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createImport.htm','');"><fmt:message key="button.importexcel" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>