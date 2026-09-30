<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="form.areedettagli.title.list" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="form.areedettagli.title.list" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list"/>
</jsp:include>	
<div id="subcontent">
<c:if test="${not empty param.codiceArea }">
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.area" />:</div>
		</div>
		<div class="parametro">
			<div>${area.denominazione}</div>
		</div>
	</div>
</c:if>
<c:if test="${not empty param.codiceStradario }">
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.stradario" />:</div>
		</div>
		<div class="parametro">
			<div>${stradario.prefisso} ${stradario.descrizione}</div>
		</div>
	</div>
</c:if>
<form name="areedettagliForm" action="list.htm">
<jmesa:springTableFacade
	id="areedettagli_id" items="${areedettagliList}" var="areedettagli_var"
	exportTypes="pdfp,excel,csv" stateAttr="restore" filterMatcherMap="org.jmesa.custom.AreedettagliFilterMatcherMap">
	<jmesa:htmlTable >
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                 <a href="view.htm?codiceArea=${param.codiceArea}&codiceStradario=${param.codiceStradario}&codice=${areedettagli_var.id.codice}">${areedettagli_var.id.codice}</a>
            </jmesa:htmlColumn>
			<jmesa:htmlColumn property="aree.denominazione"	titleKey="form.areedettagli.aree" />
			<jmesa:htmlColumn property="stradario.descrizioneCompleta" titleKey="label.stradario" />
			<jmesa:htmlColumn property="paridispari"titleKey="form.areedettagli.paridispari" cellEditor="org.jmesa.custom.PariDispariCellEditor" filterEditor="org.jmesa.custom.PariDispariDroplist" />
			<jmesa:htmlColumn property="civicoDa" titleKey="form.areedettagli.civico_da" />
			<jmesa:htmlColumn property="civicoA" titleKey="form.areedettagli.civico_a" />
			<jmesa:htmlColumn property="" titleKey="label.edit.record"	sortable="false" filterable="false" width="5%">
				<a class="dettaglioColumn" href="view.htm?codiceArea=${param.codiceArea}&codiceStradario=${param.codiceStradario}&codice=${areedettagli_var.id.codice}" title="<fmt:message key="label.edit.record" /> ${areedettagli_var.id.codice}">
					<label><fmt:message key="label.edit.record.image" /></label>
				</a>
			</jmesa:htmlColumn>		
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade>
	<input type="hidden" name="codiceArea" value="${param.codiceArea}"/>
	<input type="hidden" name="codiceStradario" value="${param.codiceStradario}"/>
</form>

<script type="text/javascript">
	var _jmesaUrl = 'list.htm?codiceArea=${param.codiceArea}&codiceStradario=${param.codiceStradario}&';
	var _captionTab = '<fmt:message key="form.areedettagli.title.list" />';
</script></div>
<div id="functions">
<ul>
	<li><a href="javascript:doHref('create.htm?codiceArea=${param.codiceArea}&codiceStradario=${param.codiceStradario}','');"><fmt:message key="button.new" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>