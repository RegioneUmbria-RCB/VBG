<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipidocumento.label.lista_tipidocumento.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipidocumento.label.lista_tipidocumento.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../tipidocumento/list" />	
	</jsp:include>
	<div id="subcontent">
		<form name="tipidocumentoForm" action="list.htm">
			<jmesa:springTableFacade
				id="tipidocumento_id" 
				items="${tipidocumentoList}" 
				var="tipidocumento_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="javascript:historySet('${_urlback}','../tipidocumento/view.htm?codice=${tipidocumento_var.id.codice}&software=TT','');">${tipidocumento_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="documento" titleKey="tipidocumento.label.documento" />
						<jmesa:htmlColumn property="letteretipo.descrizione" titleKey="tipidocumento.label.letteretipo" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../tipidocumento/view.htm?codice=${tipidocumento_var.id.codice}&software=TT','');" title="<fmt:message key="label.edit.record" />${tipidocumento_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="tipidocumento.label.lista_tipidocumento.title" />';
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