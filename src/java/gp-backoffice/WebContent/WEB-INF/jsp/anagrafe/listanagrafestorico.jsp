<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.lista_anagrafe_storico"/></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.lista_anagrafe_storico"/></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<div id="subcontent">
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
			<jsp:param name="commandName" value="anagrafe" />
		</jsp:include>    
			<form name="anagrafeForm" action="listanagrafestorico.htm">			
				<jmesa:springTableFacade
					id="anagrafestorico_id" 
					items="${anagrafestoricoList}" 
					var="anagrafestorico_var" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.AnagrafeFilterMatcherMap">
						<jmesa:htmlTable>
							<jmesa:htmlRow>
								<jmesa:htmlColumn property="descrizioneRichiedente" titleKey="label.nominativo_ragione_sociale" filterable="false" sortable="false"  width="30%"/>
								<jmesa:htmlColumn property="descrizioneResidenza" titleKey="label.residenza_sede_legale" filterable="false" sortable="false" width="15%"/>
							<%--<jmesa:htmlColumn property="descrizioneResidenza" titleKey="label.sede_legale" filterable="false" sortable="false"  width="15%"/> --%>
								<jmesa:htmlColumn property="corrispondenza" titleKey="label.corrispondenza" filterable="false" sortable="false"  width="15%"/>
								<jmesa:htmlColumn property="codicefiscale" titleKey="label.codicefiscale" filterable="false" sortable="false" width="15%"/>
								<jmesa:htmlColumn property="partitaiva" titleKey="label.partitaiva" filterable="false" sortable="false"  width="15%"/>
								<jmesa:htmlColumn property="comuneNascita.transientDescrizioneComune" titleKey="label.comune_nascita" filterable="false" sortable="false"/>  
								<jmesa:htmlColumn property="datanascita" titleKey="label.data_nascita" filterable="false" sortable="false" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor"/>
							   <%--  <jmesa:htmlColumn property="comunecomregditte.transientDescrizioneComune" titleKey="label.comune_reg_ditte" filterable="false" sortable="false"/>  --%>
								 <%--<jmesa:htmlColumn property="datanominativo" titleKey="label.data_costituzione" filterable="false" sortable="false" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor"/> --%>
								<jmesa:htmlColumn property="pec" titleKey="label.pec" filterable="false" sortable="false" width="15%"/>
								<jmesa:htmlColumn property="tipoanagrafe" titleKey="anagrafe.label.tipo_anagrafe" filterable="false" sortable="false">
								   <c:if test="${anagrafestorico_var.tipoanagrafe eq 'G'}">
								   		<fmt:message key="label.persona_giuridica"/>
								   </c:if>
								   <c:if test="${anagrafestorico_var.tipoanagrafe eq 'F'}">
								   	<fmt:message key="label.persona_fisica"/>
								   </c:if>
								</jmesa:htmlColumn>
								<jmesa:htmlColumn property="datainiziovalidita" titleKey="label.data_inizio_validita" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterable="false" sortable="false" width="8%"/>
								<jmesa:htmlColumn property="datafinevalidita" titleKey="label.data_fine_validita" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterable="false" sortable="false" width="8%"/>
								<%-- <jmesa:htmlColumn property="responsabili.responsabile" titleKey="label.responsabile"  filterable="false" sortable="false" width="5%"/>  --%>
							    <jmesa:htmlColumn property="" titleKey="label.edit.record.image" sortable="false" filterable="false" width="5%">
									<jsp:include page="../includes/visualizzaInfoGenerico.jsp" >
										<jsp:param name="label_dialog" value="label.dettaglio_anagrafe_storico" />
	       								<jsp:param name="methodAjax" value="ajaxDettaglioStoricoAnagrafe.htm?codice=${anagrafestorico_var.id.codice}"/>
	   									<jsp:param name="descrizione" value="${anagrafestorico_var.descrizioneRichiedente}" />
	   									<jsp:param name="descrizioneResponsabile" value="${anagrafestorico_var.responsabili.responsabile}" />
	   									<jsp:param name="indice" value="${anagrafestorico_var.id.codice}" />
	   									<jsp:param name="sizeDialog" value="700"/>
	   								</jsp:include>						
								</jmesa:htmlColumn>
								<jmesa:htmlColumn property="." titleKey="label.elimina" sortable="false" filterable="false" width="25%">
									<c:if test="${anagrafestorico_var.isFirst!=true && anagrafestorico_var.isLast!=true }">
										<a  class="eliminaRiga" href="javascript:doHref('deleteAnagrafeStorico.htm?codice=${anagrafestorico_var.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina" />  " >
											<label><fmt:message key="label.elimina" /></label>
										</a>
									</c:if>
								</jmesa:htmlColumn>
							</jmesa:htmlRow>
						</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" name="codiceanagrafe" value="${anagrafe.entity.id.codice}"/>
			</form>
			
			
			<script type="text/javascript">
				
			
			
				var _jmesaUrl='listanagrafestorico.htm?codiceanagrafe=${anagrafe.entity.id.codice}&';
				var _captionTab='<fmt:message key="label.lista_anagrafe_storico" />';
			
			</script>
			
		</div>
		<div id="functions">
			<ul>
			    <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>