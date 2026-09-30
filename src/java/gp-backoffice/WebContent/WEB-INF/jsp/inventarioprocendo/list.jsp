<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.inventarioprocendo.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.inventarioprocendo.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	     <div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="label.endoprocedimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${inventarioprocedimentiT.procedimento}" /></div>
			</div>
	    </div>
	    <div class="clear"></div>	
		<form name="allegatiForm" action="list.htm">
			<jmesa:springTableFacade
				id="inventarioprocendo_id" 
				items="${inventarioprocendoList}" 
				var="inventarioprocendo_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${inventarioprocendo_var.id.codice}">${inventarioprocendo_var.id.codice}</a>
                        </jmesa:htmlColumn>								
                        <jmesa:htmlColumn property="inventarioprocEndoD.procedimento" titleKey="label.inventarioprocendo_d"/>
						<jmesa:htmlColumn property="flagPubblica" titleKey="label.pubblica" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist"/>
						<jmesa:htmlColumn property="flagNecessario" titleKey="label.necessario" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist"/>
						<jmesa:htmlColumn property="inventarioprocEndoD.disabilitato" titleKey="label.disabilitato" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist"/>
						<c:if test="${_COMUNIASSOCIATI_ eq true }">
							<jmesa:htmlColumn property="comune.comune" titleKey="label.comune" />						
						</c:if>
						<jmesa:htmlColumn property="ordine" titleKey="label.ordine"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${inventarioprocendo_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${inventarioprocendo_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${inventarioprocedimentiT.id.codice}" name="codiceT"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codiceT=${inventarioprocedimentiT.id.codice}&';
			var _captionTab='<fmt:message key="label.inventarioprocendo.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codiceT=${inventarioprocedimentiT.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>