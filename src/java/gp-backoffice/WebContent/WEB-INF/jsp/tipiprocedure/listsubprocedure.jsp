<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipiprocedure.label.lista_subprocedure.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipiprocedure.label.lista_subprocedure.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	    <span class="parametri"><fmt:message key="tipiprocedure.label.codice_procedura"/><label> ${tipiprocedure.id.codice}</label></span>
	    <span class="parametri"><fmt:message key="label.procedura"/><label> ${tipiprocedure.procedura}</label></span>
	    <br class="clear"/>		
	    <form name="subprocedureForm" action="listsubprocedure.htm">
			<jmesa:springTableFacade
				id="subprocedure_id" 
				items="${subprocedureList}" 
				var="subprocedure_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="viewSubprocedura.htm?codicesubprocedura=${subprocedure_var.id.codice}">${subprocedure_var.id.codice}</a>
                        </jmesa:htmlColumn>
                        <jmesa:htmlColumn property="numerosubprocedura" titleKey="label.numero_fase"  width="2%"/>								
						<jmesa:htmlColumn property="titolosubprocedura" titleKey="tipiprocedure.label.titolosubprocedura" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="viewSubprocedura.htm?codicesubprocedura=${subprocedure_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${subprocedure_var.titolosubprocedura}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${tipiprocedure.id.codice}" name="codicetipoprocedura"></input>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listsubprocedure.htm?codicetipoprocedura=${tipiprocedure.id.codice}&';
			var _captionTab='<fmt:message key="tipiprocedure.label.lista_subprocedure.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createSubprocedura.htm?codicetipoprocedura=${tipiprocedure.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=${tipiprocedure.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>