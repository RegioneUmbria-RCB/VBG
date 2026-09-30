<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.commissioni_conferenze" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.commissioni_conferenze" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
  
	<div id="subcontent">
		<form name="commissioniedilizietForm" action="list.htm">
			<jmesa:springTableFacade
				id="commissioniedilizie_id" 
				items="${commissioniedilizietList}" 
				var="commissioniediliziet_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.CommissioniediliziaTFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="numeroprotocollo" titleKey="label.numero_protocollo" width="5%">
                           	<a href="view.htm?codice=${commissioniediliziet_var.id}">${commissioniediliziet_var.numeroprotocollo}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="data"  width="10%" titleKey="label.data" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataCommEdiliziaTCustomFilter"/>
						<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione"/>
						<jmesa:htmlColumn property="tipologia" titleKey="label.tipologia" filterEditor="org.jmesa.custom.CommEdiliziaTipologiaDroplist" />
						<jmesa:htmlColumn property="aperta" titleKey="label.stato" filterEditor="org.jmesa.custom.StatoDroplist" cellEditor="org.jmesa.custom.StatoApertaChiusaCellEditor" />
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${commissioniediliziet_var.id}" title="<fmt:message key="label.edit.record" />${commissioniediliziet_var.id}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="label.commissioni_conferenze" />';
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