<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="inventarioprocedimenti.label.lista_oneri.title" /></title>
</head>
<body>
	<style type="text/css">
	.onere-disattivo{
		text-decoration: line-through;
	}
	</style>
	<span class="titoloPagina"><fmt:message key="inventarioprocedimenti.label.lista_oneri.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../oneri/list" />
		<jsp:param name="qs" value="codiceendo=${oneriCommand.endo.id.codice}&idcomendo=${oneriCommand.endo.id.idcomune}"/>
	</jsp:include>
	<div id="subcontent">
	 	<div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${oneriCommand.endo.procedimento}" /></div>
			</div>
	    </div>
	    <div class="clear"></div>	
		<form name="oneriForm" action="list.htm">
		<%-- 
			<jmesa:springTableFacade 
				id="oneri_id" 
				items="${oneriCommand.oneri}" 
				var="oneri_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.SiNoFilterMatcherMap">
				<jmesa:htmlTable >
					<jmesa:htmlRow>
						<jmesa:htmlColumns htmlColumnsGenerator="org.jmesa.customColumn.OneriHtmlColumnGenerator"></jmesa:htmlColumns>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		 --%>
		 	${ oneriTable }
			  <input type="hidden" value="${oneriCommand.endo.id.codice}" name="codiceendo"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='../oneri/list.htm?codiceendo=${oneriCommand.endo.id.codice}&idcomendo=${oneriCommand.endo.id.idcomune}';
			var _captionTab='<fmt:message key="inventarioprocedimenti.label.lista_oneri.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:historySet(URLEncode(_jmesaUrl),'../oneri/addCausale.htm?codiceendo=${oneriCommand.endo.id.codice}&idcomendo=${oneriCommand.endo.id.idcomune}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>