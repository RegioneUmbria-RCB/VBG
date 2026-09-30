<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_storico_attivita.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_storico_attivita.title" /></span>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../iattivita/listIattivitaSnapshot" />
	</jsp:include>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
		<div class="etichetta">
				<div><fmt:message key="label.iattivita" />:</div>
				<div><fmt:message key="label.ultima_istanza" />:</div>		
			</div>		
			<div class="parametro">       		 	
				<div>${iAttivita.denominazione}</div>
				<div>${iAttivita.istanza.numeroistanza}</div>
			</div>
		</div>
		<form name="iattivitaSnapshotForm" action="listIattivitaSnapshot.htm">
			<jmesa:springTableFacade
				id="iattivitaSnapshot_id" 
				items="${iattivitaSnapshotList}" 
				var="iattivitaSnapshot_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" 
				filterMatcherMap="org.jmesa.custom.IattivitaSnapShotFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="javascript:historySet('${_urlback}','../iattivita/viewIattivitaSnapshot.htm?codice=${iattivitaSnapshot_var.id.codice}','')">${iattivitaSnapshot_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="denominazione" titleKey="label.denominazione" />
						<jmesa:htmlColumn property="istanza.numeroistanza" titleKey="label.numero_istanza" />
						<jmesa:htmlColumn property="attiva" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist" titleKey="label.attiva" width="5%"/>
                 		<jmesa:htmlColumn property="operante" cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist" titleKey="label.operante" width="5%"/>
						<jmesa:htmlColumn property="data" titleKey="label.data_snapshot" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataIattivitaSnapshotCustomFilter" headerStyle="width: 10%"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../iattivita/viewIattivitaSnapshot.htm?codice=${iattivitaSnapshot_var.id.codice}','')" title="<fmt:message key="label.edit.record" />${iattivitaSnapshot_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>			
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${iAttivita.id.codice}" name="codiceattivita"/>
		</form>
		
		<script type="text/javascript">
			var _jmesaUrl='listIattivitaSnapshot.htm?codiceattivita=${iAttivita.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_storico_attivita.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>