<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.web.IstanzeAccessoAttiTCommand" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ page import="java.util.Date" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_accesso_atti_log.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_accesso_atti_log.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzeaccessoattit/listLogs"/>
		
	</jsp:include>
	<div id="subcontent">
		<div class="header_dati">
			<div class="header_dato">
				<span class="header_dato_etichetta"><fmt:message key="label.fascicolo_istanza" />:</span>
				<span class="header_dato_valore"><a>${istanzeaccessoattit.istanze.numeroistanza}</a></span>
			</div>	
			<div class="header_dato">
				<span class="header_dato_etichetta"><fmt:message key="label.data_inizio_accesso" />:</span>
				<span class="header_dato_valore"><fmt:formatDate value="${istanzeaccessoattit.datainizio}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></span>
			</div>	
			<div class="header_dato">
				<span class="header_dato_etichetta"><fmt:message key="label.data_fine_accesso" />:</span>
				<span class="header_dato_valore"><fmt:formatDate value="${istanzeaccessoattit.datafine}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></span>
			</div>				
		</div>	
		<form name="istanzeaccessoattilogForm" action="listLogs.htm">
		
			<jmesa:springTableFacade
				id="istanzeaccessoattilog_id" 
				items="${accessoAttiLogs}" 
				var="accessoattilog_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="label.anagrafica" width="30%" filterable="false" />
						<jmesa:htmlColumn property="istanze.numeroistanza" titleKey="label.numeroistanza" width="15%" filterable="false" />
                        <jmesa:htmlColumn property="dataora"  titleKey="label.data_accesso" pattern="<%= WebConstants.DATE_WITH_TIME_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateWithTimeCellEditor" filterable="false" />						
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" name="codice" value="${istanzeaccessoattit.id.codice}"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listLogs.htm?codice=${istanzeaccessoattit.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_accesso_atti_log.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>