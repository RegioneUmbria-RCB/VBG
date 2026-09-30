<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="documentidafirmare.label.titolo_pagina" /></title>
	</head>
	<body>
		<br class="clear" />
		<span class="titoloPagina"><fmt:message key="documentidafirmare.label.titolo_pagina_list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
			<spring-form:form commandName="documentidafirmare" name="inviodati">
				<jmesa:springTableFacade
					id="documentidafirmare_id" 
					items="${documentidafirmareList}" 
					var="documentidafirmare_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="3%">
                                  <a href="popupViewMettiAllaFirma.htm?codice=${documentidafirmare_var.id.codice}">${documentidafirmare_var.id.codice}</a>
                            </jmesa:htmlColumn>
							<jmesa:htmlColumn property="oggetti.nomefile"  width="15%"  titleKey="documentidafirmare.label.nome_file" />
							<jmesa:htmlColumn property="movimentiallegati.descrizione"  width="25%"  titleKey="documentidafirmare.label.descrizione_file" />
							<jmesa:htmlColumn property="richiedente.responsabile"  width="15%"  titleKey="documentidafirmare.label.op_richiedente" />
							
							<jmesa:htmlColumn property="annotazioniRichiedente"  width="15%"  titleKey="documentidafirmare.label.note_op" />
							
							<jmesa:htmlColumn property="dataRichiesta" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor"  width="5%"  titleKey="documentidafirmare.label.data_richiesta" />
							<jmesa:htmlColumn property="firmatario.responsabile"  width="15%"  titleKey="documentidafirmare.label.responsabile_firmatario" />
							
							<jmesa:htmlColumn property="annotazioniFirmatario"  width="15%"  titleKey="documentidafirmare.label.note_resp" />
							
							
							<jmesa:htmlColumn property="dataFirma" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" width="5%"  titleKey="documentidafirmare.label.data_firma" />
							
							<jmesa:htmlColumn property="flagDaFirmare"  width="12%"  titleKey="documentidafirmare.label.stato_richiesta" />
							
	
							
							
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="popupViewMettiAllaFirma.htm?codice=${documentidafirmare_var.id.codice}"  title="<fmt:message key="label.edit.record" />&nbsp;${documentidafirmare_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" name="codiceMovAllegato" id="codiceMovAllegato_id" value="${codiceMovAllegato}" />		 	
			</spring-form:form>
			<script type="text/javascript">
				var _jmesaUrl='list.htm?';
				var _captionTab='<fmt:message key="tipiresponsabili.label.title.list" />';
			</script>
		</div>

		<div id="functions">
			<ul>
				<li><a href="javascript:doSubmit('popupCreate.htm','',document.inviodati)"><fmt:message key="documentidafirmare.button.nuova_richiesta" /></a></li>
				<li><a href="javascript:self.close();"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
		
	</body>
</html>