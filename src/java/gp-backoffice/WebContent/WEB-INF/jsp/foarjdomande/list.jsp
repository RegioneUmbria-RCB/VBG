<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.foarjdomande.in_compilazione" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.foarjdomande.in_compilazione" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
		</jsp:include>
		<div id="subcontent">
			<form name="foarjdomandeForm" action="list.htm">
				<jmesa:springTableFacade
					id="foarjdomande_id" 
					items="${domandes}" 
					var="foarjdomande_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="idDomanda" titleKey="label.codice" width="15%" />
							<jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="label.richiedente" />
							<jmesa:htmlColumn property="alberoproc.vwAlberoproc.scDescrizione" titleKey="label.intervento" />
							<jmesa:htmlColumn property="foArjServizi.nlaServizi.descrizione" titleKey="label.servizio" />
							<jmesa:htmlColumn property="oggetti" titleKey="label.xml_domanda">
							<jsp:include page="../includes/visualizzaOggetto.jsp">
	       						<jsp:param name="idElemento" value="div${foarjdomande_var.id.codice}" />
	       						<jsp:param name="fileId" value="${foarjdomande_var.oggetti.id.codice}" />
	       						<jsp:param name="readonly" value="true" />
	   						</jsp:include>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="dataUltimaModifica" titleKey="label.data_modifica"  sortable="false" filterable="false" cellEditor="org.jmesa.view.editor.DateWithTimeCellEditor" width="10%"/>							
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a href="javascript:doHref('invalidaDomanda.htm?codice=${foarjdomande_var.id.codice}','<fmt:message key="javascript.confirm.invalida_foarjdomande" />');"  title="<fmt:message key="label.invalida" /> ${foarjdomande_var.id.codice}">
									<fmt:message key="label.invalida" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="form.foarjdomande.in_compilazione" />';
			</script>
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('invalidaTutte.htm','<fmt:message key="javascript.confirm.invalida_foarjdomande.tutte" />');"><fmt:message key="button.invalida_tutte" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
		</body>
</html>