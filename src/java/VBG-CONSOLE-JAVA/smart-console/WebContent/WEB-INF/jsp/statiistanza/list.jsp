<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="statiistanze.label.lista_statiistanza.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="statiistanze.label.lista_statiistanza.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="statiistanzaForm" action="list.htm">
				<jmesa:springTableFacade
					id="statiistanza_id" 
					items="${statiistanzaList}" 
					var="statiistanza_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.StatiistanzaFilterMatcherMap">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codicestato" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${statiistanza_var.id.codicestato}">${statiistanza_var.id.codicestato}</a>
                         	</jmesa:htmlColumn>							
							<jmesa:htmlColumn property="stato" titleKey="statiistanze.label.stato" />
							<jmesa:htmlColumn property="modificaistanza" titleKey="statiistanze.label.modificaistanza" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist"/>
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
									<a class="dettaglioColumn" href="view.htm?codice=${statiistanza_var.id.codicestato}" title="<fmt:message key="label.edit.record" /> ${statiistanza_var.id.codicestato}">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="statiistanze.label.lista_statiistanza.title" />';
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