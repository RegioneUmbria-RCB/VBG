<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_documenti_commissione" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_documenti_commissione"/></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.numero_commissione" />:</div>
			<div><fmt:message key="label.descrizione" />:</div>
		</div>		
		<div class="parametro">       		 	
			<div>${commedilizieconvocazioni.commissioniedilizieT.numprotocollo}</div>
			<div>${commedilizieconvocazioni.commissioniedilizieT.descrizione}</div>
		</div>
	</div>
		<form name="commedilizieallegatiForm" action="list.htm">
			<jmesa:springTableFacade
				id="commedilizieallegati_id" 
				items="${commedilizieallegatiList}" 
				var="commedilizieallegati_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.AllegatoCommisFilterMatcherMap">
				<jmesa:htmlTable>
					<jmesa:htmlRow>
						<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                           	<a href="view.htm?codice=${commedilizieallegati_var.id.codice}">${commedilizieallegati_var.id.codice}</a>
                        </jmesa:htmlColumn>								
						<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione" width="27%" />
						<jmesa:htmlColumn property="note" titleKey="label.note" width="60%" />
						<jmesa:htmlColumn property="dataregistrazione" titleKey="label.data_registrazione" width="4%" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataregistrazioneAllegatoCommisFilter"/>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${commedilizieallegati_var.id.codice}" title="<fmt:message key="label.edit.record" />${commedilizieallegati_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${commissioniedilizieT.id.codice}" name="codiceCommissione"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codiceCommissione=${commissioniedilizieT.id.codice}&';
			var _captionTab='<fmt:message key="label.lista_documenti_commissione" />';
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codiceCommissione=${commissioniedilizieT.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../commissioniediliziet/view.htm?codice=${commissioniedilizieT.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>