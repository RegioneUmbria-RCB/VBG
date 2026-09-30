<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="orariaperturatestata.label.lista_orariaperturatestata.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="orariaperturatestata.label.lista_orariaperturatestata.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../orariaperturatestata/list" />
	</jsp:include>	
    <c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanza.id.codice}</c:param>
	</c:import>    
	<div id="subcontent">
		<form name="orariaperturatestataForm" action="list.htm">
			<jmesa:springTableFacade
				id="orariaperturatestata_id" 
				items="${orariaperturatestataList}" 
				var="orariaperturatestata_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${orariaperturatestata_var.id.codice}">${orariaperturatestata_var.id.codice}</a>
                        </jmesa:htmlColumn>			
                        <jmesa:htmlColumn property="tipiorario.toDescrizione" titleKey="label.descrizione" />					
						<jmesa:htmlColumn property="periododaTransient" titleKey="label.periodo_dal" sortable="false" filterable="false"/>
						<jmesa:htmlColumn property="periodoaTransient" titleKey="label.al" sortable="false" filterable="false"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${orariaperturatestata_var.id.codice}" title="<fmt:message key="label.edit.record" />${orariaperturatestata_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${istanza.id.codice}" name="codiceIstanza" />
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codiceIstanza=${istanza.id.codice}&';
			var _captionTab='<fmt:message key="orariaperturatestata.label.lista_orariaperturatestata.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codiceIstanza=${istanza.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>