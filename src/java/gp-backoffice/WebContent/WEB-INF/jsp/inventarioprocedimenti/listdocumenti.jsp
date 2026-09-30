<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="inventarioprocedimenti.label.lista_documenti.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="inventarioprocedimenti.label.lista_documenti.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	     <div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="inventarioprocedimenti.label.endo_procedimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${inventarioprocedimenti.procedimento}" /></div>
			</div>
	    </div>
	    <div class="clear"></div>	
		<form name="documentiForm" action="listdocumenti.htm">
			<jmesa:springTableFacade
				id="documenti_id" 
				items="${documentiList}" 
				var="documenti_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="viewDocumento.htm?codicedocumento=${documenti_var.id.codice}">${documenti_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="documento" titleKey="inventarioprocedimenti.label.altri_documento" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="viewDocumento.htm?codicedocumento=${documenti_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${documenti_var.documento}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${inventarioprocedimenti.id.codice}" name="codiceendo"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listdocumenti.htm?codiceendo=${inventarioprocedimenti.id.codice}&';
			var _captionTab='<fmt:message key="inventarioprocedimenti.label.lista_documenti.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createDocumento.htm?codiceendo=${inventarioprocedimenti.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>