<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.inventarioprocqrt" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.inventarioprocqrt" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="inventarioprocqrtForm" action="list.htm">
				<jmesa:springTableFacade
					id="inventarioprocqrt_id" 
					items="${list}" 
					var="inventarioprocqrt_var"
					exportTypes="pdfp,csv,excel" 
					stateAttr="restore">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="codice" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${inventarioprocqrt_var.id.codice}">${inventarioprocqrt_var.codice}</a>
                            </jmesa:htmlColumn>								
							<jmesa:htmlColumn property="titolo" titleKey="label.descrizione" />
							<jmesa:htmlColumn property="inventarioprocedimento.procedimento" titleKey="label.inventarioprocedimento"/>
							<jmesa:htmlColumn property="ordine" titleKey="label.ordine" style="text-align:right;" headerStyle="text-align:right;" width="8%"/>
							<jmesa:htmlColumn property="flagPubblica" titleKey="label.pubblica" width="8%" />				 			
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="8%">
								<a href="view.htm?codice=${inventarioprocqrt_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${inventarioprocqrt_var.id.codice}">
									<img src="${pageContext.request.contextPath}/images/edit.gif" alt="<fmt:message key="label.edit.record" /> ${inventarioprocqrt_var.id.codice}"/>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
					<input type="hidden" name="inventarioproc.id.codice" value="${inventarioprocedimenti.id.codice}"/>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?inventarioproc.id.codice=${inventarioprocedimenti.id.codice}';
				var _captionTab='<fmt:message key="label.inventarioprocqrt.list" />';
			</script>
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm?inventarioproc.id.codice=${inventarioprocedimenti.id.codice}','');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>