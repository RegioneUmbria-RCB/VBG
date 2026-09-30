<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="tipiarchivioistanze.label.lista_archivio_istanze.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="tipiarchivioistanze.label.lista_archivio_istanze.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="tipiarchivioistanzeForm" action="list.htm">
				<jmesa:springTableFacade
					id="tipiarchivioistanze_id" 
					items="${tipiarchivioistanzeList}" 
					var="tipiarchivioistanze_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>							
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="5%">
                               <a href="view.htm?codice=${tipiarchivioistanze_var.id.codice}">${tipiarchivioistanze_var.id.codice}</a>
                            </jmesa:htmlColumn>
                            <jmesa:htmlColumn property="archivio" titleKey="tipiarchivioistanze.label.codice"/>
							<jmesa:htmlColumn property="id" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="view.htm?codice=${tipiarchivioistanze_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${tipiarchivioistanze_var.archivio}">
								<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="tipiarchivioistanze.label.lista_archivio_istanze.title" />';
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