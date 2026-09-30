<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.importexportsivbg.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.importexportsivbg.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">


			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="importexportsivbgcommand" />
		    </jsp:include>

		    <ul class="listaSchede">
		    	<li><a id="tabexport_all" class="Scheda" href="#" onclick="viewTab(this)"><fmt:message key="importexport.label.title.export_all" /></a></li>
			    <li><a id="tabexport_endo" class="Scheda" href="#" onclick="viewTab(this)"><fmt:message key="importexport.label.title.export_endo" /></a></li>
		    </ul>
		    
		    
		    
		    
		    <div id="divexport_all" style="display: none;">
		    
			    <spring-form:form commandName="importexportsivbgcommand" action="createExport.htm" name="exportcompleto">
					
			    	<table width="100%">
						<tr class="titoloSezione">
							<td colspan="2"><fmt:message key="importexport.label.title.export_all"/></td>
						</tr>
						<tr>
							<td>
								<fmt:message key="importexport.label.servizio_export" />
							</td>
							
							<td>
								<spring-form:input id="descrizioneServizioExport_id" path="descrizioneServizioExport" size="30" readonly="true" />
							</td>
							
						</tr>
						
						<tr>
							<td>
								<fmt:message key="importexport.label.data_export" />
							</td>
							<td>
								<spring-form:input id="dataExport_id" path="dataExport" size="10" onblur="isValidDate(this,true);" />
								<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dataExport_id" textKey="label.calendar"/>
								<spring-form:errors path="dataExport" cssClass="error"/>
							</td>
						</tr>
		
						<tr>
							<td>
								<fmt:message key="importexport.label.note_export" />
							</td>
							<td>
								<spring-form:textarea id="noteVersioneExport_id" path="noteVersioneExport" rows="4" cols="50" />
								<spring-form:errors path="noteVersioneExport" cssClass="error"/>
							</td>
						</tr>				
						
					</table>
				</spring-form:form>
				
				

				<div id="functions">
					<ul>
						<li><a href="javascript:doSubmit('exportSIVBG.htm','',document.exportcompleto)"><fmt:message key="button.export" /></a></li>
						<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
					</ul>
				</div>
				<input type="hidden" name="tab" value="divCompleto"/>
		    </div>
		    <div id="divexport_endo" style="display: none;">
				<spring-form:form commandName="importexportsivbgcommand" action="createExportEndo.htm" name="exportsingolo">
				
									
				    <table width="100%">
						<tr class="titoloSezione">
							<td colspan="2"><fmt:message key="importexport.label.title.export_endo"/></td>
						</tr>
			
						<tr>
							<td>
								<fmt:message key="importexport.label.servizio_export" />
							</td>
							<td>
								<spring-form:input id="descrizioneServizioExport_id" path="descrizioneServizioExport" size="30" readonly="true" />
							</td>
						</tr>
						
						<tr>
							<td>
								<fmt:message key="importexport.label.data_export" />
							</td>
							<td>
								<spring-form:input id="dataExport_id" path="dataExport" size="10" onblur="isValidDate(this,true);" />
								<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dataExport_id" textKey="label.calendar"/>
								<spring-form:errors path="dataExport" cssClass="error"/>
							</td>
						</tr>
		 
						<tr>
							<td>
								<fmt:message key="importexport.label.note_export" />
							</td>
							<td>
								<spring-form:textarea id="noteVersioneExport_id_endo" path="noteVersioneExport" rows="4" cols="50" />
								<spring-form:errors path="noteVersioneExport" cssClass="error"/>
							</td>
						</tr>
						
						<tr>
							<td>
								<fmt:message key="importexport.label.export.scelta_endo" />
							</td>
						</tr>
						<tr id="id_progetto_table">
							<td>
								<fmt:message key="alberoproc.label.alberoprocEndo_famigliendo" />
							</td>
							<td >
								<jsp:include page="../includes/autocompletergenerico.jsp">
								<jsp:param name="idElemento" value="famiglia_id" />		
								<jsp:param name="propertyPath" value="istanzeFilter.inventarioprocedimenti.tipoendo.tipifamiglieendo" />					
								<jsp:param name="pathPropertyDescription" value="istanzeFilter.inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo" />
								<jsp:param name="pathPropertyCode" value="istanzeFilter.inventarioprocedimenti.tipoendo.tipifamiglieendo.id.codice" />
								<jsp:param name="autocompleterAjax" value="findTipifamiglieendoSWeTT.htm" />
								<jsp:param name="titleKey" value="label.ricerca_tipo_famiglia_endo" />
								</jsp:include>
							</td>
						</tr>
						<tr id="id_progetto_table">
							<td>
								<fmt:message key="alberoproc.label.alberoprocEndo_tipiendo" />
							</td>
							<td >
								<script type="text/javascript">
									function filtertipiendo(element, entry) {
										if(document.getElementById("famiglia_id_hidden")){
											return entry + "&codiceFamiglia=" + document.getElementById("famiglia_id_hidden").value;
										}else{
											return entry ;
										}
									}
								</script>
								<jsp:include page="../includes/autocompletergenerico.jsp">
									<jsp:param name="idElemento" value="tipiendo_id" />						
									<jsp:param name="propertyPath" value="istanzeFilter.inventarioprocedimenti.tipoendo" />
									<jsp:param name="pathPropertyDescription" value="istanzeFilter.inventarioprocedimenti.tipoendo.tipo" />
									<jsp:param name="pathPropertyCode" value="istanzeFilter.inventarioprocedimenti.tipoendo.id.codice" />
									<jsp:param name="autocompleterAjax" value="findTipiendoSWeTT.htm" />
									<jsp:param name="titleKey" value="label.ricerca_tipiendo" />
									<jsp:param name="ajaxCallBack" value="filtertipiendo" />
								</jsp:include>						
							</td>
						</tr>
						<tr id="id_progetto_table">
							<td>
								<fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" />
							</td>
							<td >
								<script type="text/javascript">
									function inventarioCallBack(inputField,listItem){
										var a = listItem.id;
										document.getElementById('inventarioprocedimento_id').value = inputField.value;
										document.getElementById('inventarioprocedimento_hidden').value = a;
										$('inventarioprocedimento_id_choices').fade();	
									}
									function filterinventario(element, entry) { 
										if(document.getElementById("famiglia_id_hidden")){
											return entry + "&escludiDisabilitati=true&codiceFamiglia=" + document.getElementById("famiglia_id_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_id_hidden").value;
										}else{
											return entry + "&escludiDisabilitati=true&codiceTipologia=" + document.getElementById("tipiendo_id_hidden").value;
										}
									}
								</script>
								<jsp:include page="../includes/autocompletergenerico.jsp">
									<jsp:param name="idElemento" value="inventarioprocedimento" />					
									<jsp:param name="propertyPath" value="istanzeFilter.inventarioprocedimenti" />	
									<jsp:param name="pathPropertyDescription" value="istanzeFilter.inventarioprocedimenti.procedimento" />
									<jsp:param name="pathPropertyCode" value="istanzeFilter.inventarioprocedimenti.id.codice" />
									<jsp:param name="autocompleterAjax" value="findInventarioprocedimento.htm" />
									<jsp:param name="titleKey" value="label.ricerca_inventarioprocedimento" />
									<jsp:param name="ajaxCallBack" value="filterinventario" />
									<jsp:param name="afterUpdateElement" value="inventarioCallBack" />
								</jsp:include>
								<spring-form:errors path="istanzeFilter.inventarioprocedimenti.id.codice" cssClass="error"/>
							</td>
						</tr>	
		
					</table>
				</spring-form:form>
				<div id="functions">
					<ul>
					<li><a href="javascript:doSubmit('exportSIVBGendo.htm','',document.exportsingolo)"><fmt:message key="button.export" /></a></li>
					<li><a href="javascript:historyBack()"><fmt:message key="button.back" /></a></li>
					</ul>
				</div>
				<input type="hidden" name="tab" value="divCompleto"/>		    
		    </div>
		    
		    
		    
		    <script type="text/javascript">
				var schedeSuffix = new Array("export_all","export_endo");		
				function viewTab(obj){
					for (i=0;i<schedeSuffix.length;i++){
						$('tab'+schedeSuffix[i]).className='Scheda';
						$('div'+schedeSuffix[i]).style.display='none';
					}
					obj.className='SchedaAttiva';
					var sn = obj.id.substring(3);
					$('div'+sn).style.display='block';
				}
				var tab = '${param.tab}';
				if(tab == '')tab = '${requestScope.tab}';
				if(tab != ''){
					viewTab($(tab));
				}
			</script>
	</div>

</body>
</html>