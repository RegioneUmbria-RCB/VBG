<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.lista_anagrafe_documenti" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message
	key="label.lista_anagrafe_documenti" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
   	<jsp:param name="path" value="../anagrafe/listdocumenti" />
</jsp:include>
<div id="subcontent">
<div class="parametriDiv">
<div class="etichetta">
<div><fmt:message key="label.soggetto" />:</div>
</div>
<div class="parametro">
<div><c:out value="${anagrafe.descrizioneRichiedente}" /></div>
</div>
</div>
<br class="clear" />
<form name="anagrafedocumentiForm" action="listdocumenti.htm"><jmesa:springTableFacade
	id="anagrafedocumenti_id" items="${anagrafedocumentiList}"
	var="anagrafedocumenti_var" exportTypes="pdfp,excel,csv"
	stateAttr="restore" filterMatcherMap="org.jmesa.custom.DocumentiAnagrafeFilterMatcherMap">
	<jmesa:htmlTable>
		<jmesa:htmlRow>
			<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
				<a href="javascript:historySet('${_urlback}','../anagrafe/viewDocumenti.htm?codice=${anagrafedocumenti_var.id.codice}','');">${anagrafedocumenti_var.id.codice}</a>
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="rifdocumento" titleKey="label.riferimento_documento" />
			<jmesa:htmlColumn property="tipidocumento.documento" titleKey="label.tipo_documento" />
			<jmesa:htmlColumn property="istanza.numeroistanza" titleKey="label.codice_istanza" />
			<jmesa:htmlColumn property="datainiziovalidita" titleKey="label.data_inizio_validita" width="8%" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataInizioValiditaDocAnagrafeCustomFilter" />
			<jmesa:htmlColumn property="datafinevalidita" titleKey="label.data_fine_validita" width="8%" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataFineValiditaDocAnagarfeCustomFilter" />
			<jmesa:htmlColumn property="oggetto" titleKey="label.azioni" sortable="false" filterable="false" width="5%">
				<c:if test="${anagrafedocumenti_var.oggetto.id.codice != null}">
				<a class="visualizzaDocColumn" href="../file/ajaxDownload.htm?fileId=${anagrafedocumenti_var.oggetto.id.codice}" title="<fmt:message key="label.download" />">
				<label><fmt:message key="label.edit.record.image" /></label></a>
				</c:if>
				
					<c:choose>
						<c:when test="${anagrafedocumenti_var.flagXmlvisuraparix eq true}">
							<a class="visualizzaDocPDFColumn" href="../istanzerichiedenti/ajaxStampaPdfParix.htm?codice=${anagrafedocumenti_var.oggetto.id.codice}" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
					            <label><fmt:message key="label.visualizza.image"/></label></a>
						</c:when>
						
					</c:choose>	
														
			</jmesa:htmlColumn>
			<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
				<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../anagrafe/viewDocumenti.htm?codice=${anagrafedocumenti_var.id.codice}','');" title="<fmt:message key="label.edit.record" />&nbsp;${anagrafedocumenti_var.tipidocumento.documento}">
				<label><fmt:message key="label.edit.record.image" /></label></a>
			</jmesa:htmlColumn>
		</jmesa:htmlRow>
	</jmesa:htmlTable>
</jmesa:springTableFacade> <input type="hidden" value="${anagrafe.id.codice}"
	name="codiceanagrafe" /></form>
<script type="text/javascript">
	var _jmesaUrl='listdocumenti.htm?codiceanagrafe=${anagrafe.id.codice}&';
	var _captionTab='<fmt:message key="label.lista_anagrafe_documenti" />';	
</script>
</div>
<div id="functions">
<ul>
	<c:if test="${anagrafe.tipoanagrafe eq 'G'}">
		<c:if test="${isWSDURC eq true }">
			<li>
				<jsp:include page="../anagrafe/funzioniDURC.jsp">
				   <jsp:param name="uniquePageIdentifier" value="${anagrafe.id.codice}" />
				   <jsp:param name="codiceAnagrafe" value="${anagrafe.id.codice}" />
				   
				   <jsp:param name="showAsButton" value="true" />
				   <jsp:param name="function" value="verifica"/>
				   <jsp:param name="returnToUrl" value="${_urlback}"/>
				</jsp:include>		
			</li>
			<li>
				<jsp:include page="../anagrafe/funzioniDURC.jsp">
				   <jsp:param name="uniquePageIdentifier" value="${anagrafe.id.codice}" />
				   <jsp:param name="codiceAnagrafe" value="${anagrafe.id.codice}" />
				   
				   <jsp:param name="showAsButton" value="true" />
				   <jsp:param name="function" value="nuovoDURC"/>
				   <jsp:param name="returnToUrl" value="${_urlback}"/>
				</jsp:include>		
			</li>
			
		</c:if>
		<c:if test="${isParixGate eq true }">												
					<li><a href="javascript:historySet('${_urlback}', '../istanzerichiedenti/insertVisuraParix.htm?codiceAnagrafe=${anagrafe.id.codice}&codiceIstanza=${istanza.id.codice}', '');"><fmt:message key="button.visura_infocamere" /></a></li>																			
		</c:if>	
	</c:if>				
	<li><a
		href="javascript:historySet('${_urlback}','../anagrafe/createDocumenti.htm?codiceanagrafe=${anagrafe.id.codice}','');"><fmt:message
		key="button.new" /></a></li>
	<li><a
		href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message
		key="button.back" /></a></li>
</ul>
</div>
</body>
</html>