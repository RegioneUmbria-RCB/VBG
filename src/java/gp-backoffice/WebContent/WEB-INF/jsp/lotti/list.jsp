<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="lotti.label.lista_lotti.title" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="lotti.label.lista_lotti.title" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />	
</jsp:include>
<div id="subcontent">
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.area" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${area.denominazione}" /></div>
		</div>
	</div>
<form name="lottiForm" action="list.htm"><jmesa:springTableFacade id="lotti_id" items="${lottiList}" var="lotti_var" exportTypes="pdfp,excel,csv" stateAttr="restore" filterMatcherMap="org.jmesa.custom.LottiFilterMatcherMap">
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="id.codicelotto" titleKey="label.codice" width="2%">
				<a href="view.htm?codiceArea=${param.codiceArea}&codice=${lotti_var.id.codicelotto}">${lotti_var.id.codicelotto}</a>
			</jmesa:htmlColumn>			
			<jmesa:htmlColumn property="assegnato" titleKey="lotti.label.assegnato" width="2%" cellEditor="org.jmesa.custom.SiNoCellEditor" filterEditor="org.jmesa.custom.SiNoDroplist" />
			<jmesa:htmlColumn property="note" titleKey="label.note" width="20%" />
			<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
				<a class="dettaglioColumn" href="view.htm?codiceArea=${param.codiceArea}&codice=${lotti_var.id.codicelotto}" title="<fmt:message key="label.edit.record" />${lotti_var.id.codicelotto}"> <label><fmt:message key="label.edit.record.image" /></label> </a>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade>
<input type="hidden" name="codiceArea" value="${param.codiceArea}" />
</form>
<script type="text/javascript">
	var _jmesaUrl = 'list.htm?codiceArea=${param.codiceArea}&';
	var _captionTab = "<fmt:message key="lotti.label.lista_lotti.title" />";
</script></div>
<div id="functions">
	<ul>
		<li><a href="javascript:doHref('create.htm?codiceArea=${param.codiceArea}','');"><fmt:message key="button.new" /></a></li>
		<li><a href="javascript:doHref('../aree/view.htm?codice=${param.codiceArea}','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>