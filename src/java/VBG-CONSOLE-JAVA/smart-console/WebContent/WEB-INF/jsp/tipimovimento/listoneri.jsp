<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipimovimento.label.lista_tipimovimentooneri.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipimovimento.label.lista_tipimovimentooneri.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="tipimovimento.label.codice" />:</div>
				<div><fmt:message key="tipimovimento.label.movimento" />:</div>
	    	</div>
	    	<div class="parametro">
	    	  <div><c:out value="${tipomovimentoinfo.id.tipomovimento}" /></div>
	    	  <div><c:out value="${tipomovimentoinfo.movimento}" /></div>
			</div>
	</div>
	<div id="subcontent">
		<form name="tipimovimentooneriForm" action="listoneri.htm">
			<jmesa:springTableFacade
				id="tipimovimentooneri_id" 
				items="${tipimovimentooneriList}" 
				var="tipimovimentooneri_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>				
						<jmesa:htmlColumn property="tipicausalioneri.coDescrizione" titleKey="tipimovimento.label.causale_onere">
						<a href="viewOneri.htm?codiceMovimento=${tipimovimentooneri_var.id.tipomovimento}&codiceComportamento=${tipimovimentooneri_var.id.codicecomportamento}&codiceOnere=${tipimovimentooneri_var.id.fkCoid}">
					            ${tipimovimentooneri_var.tipicausalioneri.coDescrizione}</a>
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="onericomportamento.comportamento" titleKey="tipimovimento.label.comportamento" />
						<jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="5%">
						<a class="eliminaRiga" href="javascript:doHref('deleteOneriFromList.htm?codiceMovimento=${tipimovimentooneri_var.id.tipomovimento}&codiceComportamento=${tipimovimentooneri_var.id.codicecomportamento}&codiceOnere=${tipimovimentooneri_var.id.fkCoid}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina"/>&nbsp;${tipimovimentooneri_var.tipicausalioneri.coDescrizione}">
							<label><fmt:message key="label.elimina" /></label>
					    </a>
					    </jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${tipomovimentoinfo.id.tipomovimento}" name="tipimovimento.codice" />
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listoneri.htm?tipimovimento.codice=${tipomovimentoinfo.id.tipomovimento}&';
			var _captionTab='<fmt:message key="tipimovimento.label.lista_tipimovimentooneri.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('createOneri.htm?codicetipomovimento=${tipomovimentoinfo.id.tipomovimento}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>