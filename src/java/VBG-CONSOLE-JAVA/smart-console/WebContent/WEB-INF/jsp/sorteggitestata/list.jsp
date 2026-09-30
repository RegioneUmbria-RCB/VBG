<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="sorteggitestata.label.lista_sorteggitestata.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="sorteggitestata.label.lista_sorteggitestata.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
			<jsp:param name="commandName" value="sorteggitestata" />
		</jsp:include>
		<form name="sorteggitestataForm" action="list.htm">
			<jmesa:springTableFacade
				id="sorteggitestata_id" 
				items="${sorteggitestataList}" 
				var="sorteggitestata_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.SorteggitestataFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${sorteggitestata_var.id.codice}&sorteggidettaglio_id_f_sorteggiata=Si">${sorteggitestata_var.id.codice}</a>
                        </jmesa:htmlColumn>		
                        <jmesa:htmlColumn property="stDatasorteggio" titleKey="label.data" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataSorteggioSorteggiTCustomFilter" width="10%"/>						
						<jmesa:htmlColumn property="stDescrizione" titleKey="label.descrizione" />
						<jmesa:htmlColumn property="categoria.descrizione" titleKey="label.categoria" />
						<jmesa:htmlColumn property="oggetto" titleKey="label.oggetto" sortable="false" filterable="false" width="5%" >
							<c:if test="${sorteggitestata_var.oggetto!=null}">
							<jsp:include page="../includes/visualizzaOggetto.jsp" >
	       						<jsp:param name="idElemento" value="${sorteggitestata_var.id.codice}" />
	       						<jsp:param name="fileId" value="${sorteggitestata_var.oggetto.id.codice}" />
	   						</jsp:include>
	   						</c:if>													
						</jmesa:htmlColumn>		
						<jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="8%">
							<a class="dettaglioColumn" href="view.htm?codice=${sorteggitestata_var.id.codice}&sorteggidettaglio_id_f_sorteggiata=Si" title="<fmt:message key="label.edit.record" /> ${sorteggitestata_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>													
					        <a class="eliminaRiga" style="float: none;" href="javascript:doHref('eliminaSorteggitestata.htm?codice=${sorteggitestata_var.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" /> ${sorteggitestata_var.id.codice}">
							     <label><fmt:message key="label.elimina.image" /></label>
							</a>				
						</jmesa:htmlColumn>						
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="sorteggitestata.label.lista_sorteggitestata.title" />';
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