<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="mailtipo.label.lista_mailtipo.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="mailtipo.label.lista_mailtipo.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<form name="mailtipoForm" action="list.htm">
			<jmesa:springTableFacade
				id="mailtipo_id" 
				items="${mailtipoList}" 
				var="mailtipo_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${mailtipo_var.id.codice}">${mailtipo_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione" />
						<jmesa:htmlColumn property="ambito" titleKey="mailtipo.label.ambito" >
							<c:if test="${mailtipo_var.ambito eq 'M'}"><fmt:message key="mailtipo.label.ambito.item_mail" /></c:if>							
							<c:if test="${mailtipo_var.ambito eq 'P'}"><fmt:message key="mailtipo.label.ambito.item_protocollo" /></c:if>
							<c:if test="${mailtipo_var.ambito eq 'C'}"><fmt:message key="mailtipo.label.ambito.item_commissioni_e_conferenze" /></c:if>
							<c:if test="${mailtipo_var.ambito eq 'F'}"><fmt:message key="mailtipo.label.ambito.item_frontend" /></c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${mailtipo_var.id.codice}" title="<fmt:message key="label.edit.record" />${mailtipo_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="mailtipo.label.lista_mailtipo.title" />';
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