<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipimovimento.label.lista_movimento.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipimovimento.label.lista_movimento.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../tipimovimento/list" />
    </jsp:include>
   
	<div id="subcontent">
		<form name="tipimovimentoForm" action="list.htm">
			<jmesa:springTableFacade
				id="tipimovimento_id" 
				items="${tipimovimentoList}" 
				var="tipimovimento_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.SiNoFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.tipomovimento" titleKey="label.codice" width="2%">
                           	<a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../tipimovimento/view.htm?codice=${tipimovimento_var.id.tipomovimento}','')">${tipimovimento_var.id.tipomovimento}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="movimento" titleKey="tipimovimento.label.movimento" />
						<jmesa:htmlColumn property="flagRichiestaintegrazione" titleKey="tipimovimento.label.flag_richiesta_integrazione"
											cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor"
                                            filterEditor="org.jmesa.custom.SiNoDroplist" width="5%"/>												
						<jmesa:htmlColumn property="flagInterruzione" titleKey="tipimovimento.label.flag_interruzione" 
											cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor"
                                            filterEditor="org.jmesa.custom.SiNoDroplist" width="5%"/>
                                            <jmesa:htmlColumn property="flagStc" titleKey="tipimovimento.label.flag_stc"
                                            cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor"
                                            filterEditor="org.jmesa.custom.SiNoDroplist" width="5%" /> 
						<jmesa:htmlColumn property="flagDisabilitato" 
											cellEditor="org.jmesa.celleditor.SiNoBooleanCellEditor"
                                            filterEditor="org.jmesa.custom.SiNoDroplist"
                                            titleKey="label.disabilitato" width="5%"/>						
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../tipimovimento/view.htm?codice=${tipimovimento_var.id.tipomovimento}','')" title="<fmt:message key="label.edit.record" />&nbsp;${tipimovimento_var.id.tipomovimento}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="tipimovimento.label.lista_tipimovimento.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../tipimovimento/create.htm','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>