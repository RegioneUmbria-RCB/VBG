<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="lavoritipicausalioneri.label.lista_lavoritipicausalioneri.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="lavoritipicausalioneri.label.lista_lavoritipicausalioneri.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <c:if test="${not empty param.codiceLavoro}">
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="lavoritipicausalioneri.label.lavoritipi" />:</div>
			</div>
			<div class="parametro">
				<div>${lavoritipi.lavoro}</div>
			</div>
		</div>
	</c:if>
	<br />
	<div id="subcontent">
		<form name="lavoritipicausalioneriForm" action="list.htm">
			<jmesa:springTableFacade
				id="lavoritipicausalioneri_id" 
				items="${lavoritipicausalioneriList}" 
				var="lavoritipicausalioneri_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${lavoritipicausalioneri_var.id.codice}&codiceLavoro=${param.codiceLavoro}">${lavoritipicausalioneri_var.id.codice}</a>
                        </jmesa:htmlColumn>	
						<jmesa:htmlColumn property="lavoritipi.lavoro" titleKey="lavoritipicausalioneri.label.lavoritipi" />
						<jmesa:htmlColumn property="tipicausalioneri.coDescrizione" titleKey="lavoritipicausalioneri.label.tipicausalioneri" />
						<jmesa:htmlColumn property="tipiunitamisura.umDescrbreve" titleKey="lavoritipicausalioneri.label.tipiunitamisura" />
						<jmesa:htmlColumn property="costoUnitarioUm" titleKey="lavoritipicausalioneri.label.costounitarioum" />						
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${lavoritipicausalioneri_var.id.codice}&codiceLavoro=${param.codiceLavoro}" title="<fmt:message key="label.edit.record" /> ${lavoritipicausalioneri_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" name="codiceLavoro" value="${param.codiceLavoro}" />		
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codiceCategoria=${param.codiceCategoria}&codiceLavoro=${param.codiceLavoro}&';
			var _captionTab='<fmt:message key="lavoritipicausalioneri.label.lista_lavoritipicausalioneri.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codiceLavoro=${param.codiceLavoro}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>