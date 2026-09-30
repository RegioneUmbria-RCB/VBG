<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.lista_tipo_documento_protocollo.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.lista_tipo_documento_protocollo.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<form name="protocollotipodocForm" action="list.htm">
				<jmesa:springTableFacade
					id="protocollotipodoc_id" 
					items="${protocolloTipidocumentoList}" 
					var="protocollotipodoc_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="10%">
                                  <a href="view.htm?codice=${protocollotipodoc_var.id.codice}">${protocollotipodoc_var.codice}</a>
                         	</jmesa:htmlColumn>				
							<jmesa:htmlColumn property="comune.comune" titleKey="label.comune" >
							<c:choose>
								<c:when test="${not empty protocollotipodoc_var.comune.codicecomune }">
								${protocollotipodoc_var.comune.comune}
								</c:when>
								<c:otherwise><fmt:message key="label.tutti" /></c:otherwise>
							</c:choose>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="software.descrizione" titleKey="label.software" />
							<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="view.htm?codice=${protocollotipodoc_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${ruoli_var.ruolo}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="label.lista_tipo_documento_protocollo.title" />';
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