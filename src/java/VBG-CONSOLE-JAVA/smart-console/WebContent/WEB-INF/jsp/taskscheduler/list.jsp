<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="taskscheduler.label.lista_taskscheduler.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="taskscheduler.label.lista_taskscheduler.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
			<jsp:param name="commandName" value="sorteggitestata" />
		</jsp:include>
		<form name="taskschedulerForm" action="list.htm">
			<jmesa:springTableFacade
				id="taskscheduler_id" 
				items="${taskschedulerList}" 
				var="taskscheduler_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.TaskschedulerFilterMatcherMap" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${taskscheduler_var.id.codice}">${taskscheduler_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione_operazione" />
						<jmesa:htmlColumn property="taskbase.task" titleKey="label.operazione" />
						<jmesa:htmlColumn property="descrizioneIntervallo" titleKey="label.intervallo" width="10%"/>
						<jmesa:htmlColumn property="prossimaesecuzione" titleKey="label.prossima_esecuzione" pattern="<%= WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateWithTimeCellEditor" filterEditor="org.jmesa.custom.ProssimaesecTaskSchCustomFilter" width="10%"/>
						<jmesa:htmlColumn property="attivo" titleKey="label.attivo" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist" width="5%"/>
						<jmesa:htmlColumn property="inesecuzione" titleKey="label.in_esecuzione" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist" width="5%"/>
						<jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="8%">
							<a class="dettaglioColumn" href="view.htm?codice=${taskscheduler_var.id.codice}" title="<fmt:message key="label.edit.record" />${taskscheduler_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
							<a class="eliminaRiga" style="float: none;" href="javascript:doHref('eliminaOperazione.htm?codiceoperazione=${taskscheduler_var.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${taskscheduler_var.id.codice}">
								<label><fmt:message key="label.elimina.image" /></label>
							</a>		
						</jmesa:htmlColumn>	
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="taskscheduler.label.lista_taskscheduler.title" />';
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