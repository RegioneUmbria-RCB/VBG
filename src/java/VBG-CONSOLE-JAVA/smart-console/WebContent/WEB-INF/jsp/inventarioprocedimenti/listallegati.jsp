<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="inventarioprocedimenti.label.lista_allegati.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="inventarioprocedimenti.label.lista_allegati.title" /></span>
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
		<form name="allegatiForm" action="listallegati.htm">
			<jmesa:springTableFacade
				id="allegati_id" 
				items="${allegatiList}" 
				var="allegati_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="viewAllegati.htm?codiceallegato=${allegati_var.id.codice}&codicecomune=${allegati_var.id.idcomune}">${allegati_var.id.codice}</a>
                        </jmesa:htmlColumn>
						<jmesa:htmlColumn property="allegato" titleKey="inventarioprocedimenti.label.allegato" />
						<jmesa:htmlColumn property="pubblica" titleKey="inventarioprocedimenti.label.flag_pubblica">
							<c:if test="${allegati_var.pubblica eq 1}">
								Sì
							</c:if>
							<c:if test="${allegati_var.pubblica ne 1}">
								No
							</c:if>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="richiesto" titleKey="inventarioprocedimenti.label.flag_richiesto" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist"/>
						<jmesa:htmlColumn property="foRichiedefirma" titleKey="inventarioprocedimenti.label.flag_foRichiedefirma" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist"/>
						<c:if test="${_COMUNIASSOCIATI_ eq true }">
							<jmesa:htmlColumn property="comune.comune" titleKey="label.comune" />						
						</c:if>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="viewAllegati.htm?codiceallegato=${allegati_var.id.codice}&codicecomune=${allegati_var.id.idcomune}" title="<fmt:message key="label.edit.record" />&nbsp;${allegati_var.allegato}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${inventarioprocedimenti.id.codice}" name="codiceendo"/>
			 <input type="hidden" value="${inventarioprocedimenti.id.idcomune}" name="codicecomune"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listallegati.htm?codiceendo=${inventarioprocedimenti.id.codice}&codicecomune=${inventarioprocedimenti.id.idcomune}';
			var _captionTab='<fmt:message key="inventarioprocedimenti.label.lista_allegati.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
		<%if(ORMHelper.isConsoleLocale()){ %>
			<%--
			<c:if test="${isEndoTipo2 eq false and isEndoTipo1 eq false}">
			 --%>
				<li><a href="javascript:doHref('createAllegati.htm?codiceendo=${inventarioprocedimenti.id.codice}&codicecomune=${inventarioprocedimenti.id.idcomune}','');"><fmt:message key="button.new" /></a></li>
			<%--	
			</c:if>
			 --%>
		<%}else{ %>
		<%--
			<c:if test="${isEndoTipo2 eq true or isEndoTipo1 eq true }">
		 --%>
			
				<li><a href="javascript:doHref('createAllegati.htm?codiceendo=${inventarioprocedimenti.id.codice}&codicecomune=${inventarioprocedimenti.id.idcomune}','');"><fmt:message key="button.new" /></a></li>
		<%--
			</c:if>
			--%>
		<%} %>
			
			
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>