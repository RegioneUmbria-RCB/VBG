<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipicausalioneri.label.lista_tipicausalioneri.title" /></title>
</head>
<body>
    <c:set scope="page" var="flgTipicausaliinteressiVar"  value="0"></c:set>
    <c:if test="${isMora}">
    	<c:set scope="page" var="flgTipicausaliinteressiVar"  value="1"></c:set>
    </c:if>
	<span class="titoloPagina"><fmt:message key="tipicausalioneri.label.lista_tipicausalioneri.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../tipicausalioneri/list" />
	</jsp:include>
	<div id="subcontent">
		<form name="tipicausalioneriForm" action="list.htm">
			<jmesa:springTableFacade
				id="tipicausalioneri_id" 
				items="${tipicausalioneriList}" 
				var="tipicausalioneri_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.TipicausalioneriFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="8%">
                           	<a href="javascript:historySet('${_urlback }','../tipicausalioneri/view.htm?codice=${tipicausalioneri_var.id.codice}');">${tipicausalioneri_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="coDescrizione" titleKey="tipicausalioneri.label.coDescrizione" />
						<jmesa:htmlColumn property="coDisabilitato" titleKey="tipicausalioneri.label.coDisabilitato" cellEditor="org.jmesa.custom.SiNoCellEditor"  filterEditor="org.jmesa.custom.SiNoDroplist" width="8%" headerStyle="text-align:center;" style="text-align:center;"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="8%">
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../tipicausalioneri/view.htm?codice=${tipicausalioneri_var.id.codice}');" title="<fmt:message key="label.edit.record" />${tipicausalioneri_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
		   </jmesa:springTableFacade>
		<input type="hidden" name="flgTipicausaliinteressi" value="${isMora}" />			
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?flgTipicausaliinteressi=${isMora}&';
			var _captionTab='<fmt:message key="tipicausalioneri.label.lista_tipicausalioneri.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
		<%if(ORMHelper.isConsoleRegionale()){ %>		
			<li><a href="javascript:historySet('${_urlback }','../tipicausalioneri/create.htm?isMora=${isMora}');"><fmt:message key="button.new" /></a></li>
		<%} %>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>