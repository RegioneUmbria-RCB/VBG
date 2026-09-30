<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.istanze_discusse_commissione" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.istanze_discusse_commissione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../commissioniediliziet/listCommissioniedilizieR" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="commissioniediliziet" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commissioniediliziet" />
		    </jsp:include>
			<div class="parametriDiv">
				<div class="etichetta">
					<div><fmt:message key="label.numero_commissione" />:</div>
					<div><fmt:message key="label.descrizione" />:</div>
					<div><fmt:message key="label.data_commissione" />:</div>
				</div>		
				<div class="parametro">       		 	
					<div>${commissioniediliziet.entity.numprotocollo}</div>
					<div>${commissioniediliziet.entity.descrizione}</div>
					<div><fmt:formatDate value="${commissioniediliziet.entity.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></div>
				</div>
			</div>
			<br class="clear" />
			<table width="100%">
			<tr>
				<td width="12%"> 
					<fmt:message key="label.ora_inizio" />
				</td>
				<td> 
					<spring-form:input id="orainizio_id" path="entity.orainizio"  size="8" maxlength="5" onblur="isValidOra(this,true);" />
					<spring-form:errors path="entity.orainizio" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.ora_fine" />
				</td>
				<td>
					<spring-form:input id="orafine_id" path="entity.orafine" size="8" maxlength="5" onblur="isValidOra(this,true);" />
					<spring-form:errors path="entity.orafine" cssClass="error"/>
				</td>
			</tr>
			</table>
			<br class="clear"/>
			<table width="100%">
				
				<tr class="titoloSezione">
					<td colspan="2">
						<fmt:message key="label.istanze_in_discussione" />
					</td>
				</tr>
				<!--  TABELLA JMESA CHE CONTIENE LE COMMISSIONI EDILIZIE R IN DISCUSSIONE -->
				<tr>
					<td colspan="2">
						<form name="commissioniedilizietForm" action="listCommissioniedilizieR.htm">
							<jmesa:springTableFacade
								id="commissioniedilizier_id" 
								items="${commissioniedilizierList}" 
								var="commissioniedilizier_var"
								exportTypes="pdfp,excel,csv" 
								stateAttr="restore" filterMatcherMap="org.jmesa.custom.CommissioniediliziaRFilterMatcherMap" maxRows="100" maxRowsIncrements="100,1000">
								<jmesa:htmlTable>
									<jmesa:htmlRow>
										<jmesa:htmlColumn property="ordine" titleKey="label.ordine">
										    <input style="text-align: right;" type="text" value="${commissioniedilizier_var.ordine}" name="ordine" size="4"></input>
											<input type="hidden" value="${commissioniedilizier_var.id.codice}" name="codiciCommissioniRScelte" ></input>
										</jmesa:htmlColumn>
										<jmesa:htmlColumn property="movimento.istanza.numeroistanza" titleKey="label.numeroistanza" width="5%">
				                           	<a href="javascript:historySet('${_urlback }','../istanze/view.htm?codice=${commissioniedilizier_var.movimento.istanza.id.codice}','') ">${commissioniedilizier_var.movimento.istanza.numeroistanza}</a>
				                        </jmesa:htmlColumn>								
										<jmesa:htmlColumn property="movimento.istanza.data" titleKey="label.data_presentazione" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataIstanzaCommissioneRCustomFilter" width="10%"/>	
										<jmesa:htmlColumn property="movimento.istanza.numeroprotocollo" titleKey="label.numero_protocollo"/>
										<jmesa:htmlColumn property="movimento.istanza.dataprotocollo" titleKey="label.data_protocollo" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataProtocolloCommissioneRCustomFilter" width="10%"/>
										<jmesa:htmlColumn property="movimento.tipomovimento.descrizioneEstesa" titleKey="label.movimento"></jmesa:htmlColumn>
										<jmesa:htmlColumn property="movimento.istanza.richiedente.richiedente" titleKey="label.richiedente"/>
										<jmesa:htmlColumn property="movimento.istanza.lavori" titleKey="label.lavori"/>
										<jmesa:htmlColumn property="movimento.data" titleKey="label.data_richiesta" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataMovimentoCommissioneRCustomFilter" width="10%"/>
										<jmesa:htmlColumn property="movimento.istanza.alberoproc.vwAlberoproc.scDescrizione" titleKey="label.intervento"/>									
										<jmesa:htmlColumn property="tipologiaparere" titleKey="label.tipologia_parere" sortable="false" filterable="false" width="5%">
											<c:if test="${commissioniedilizier_var.movimentoRientro.id.codice==null}">
						                        <a href="javascript:doHref('createEsitoCommissioniedilizieR.htm?codiceCommissioneR=${commissioniedilizier_var.id.codice}&ordine=${commissioniedilizier_var.ordine}&codiceCommissioneT=${commissioniediliziet.entity.id.codice}','')"><fmt:message key="label.esito" /></a>
											</c:if>
											<c:if test="${commissioniedilizier_var.movimentoRientro.id.codice!=null}">
												<a style="color: green;" href="javascript:doHref('createEsitoCommissioniedilizieR.htm?codiceCommissioneR=${commissioniedilizier_var.id.codice}','')">${commissioniedilizier_var.commedilizieTipopareri.descrizione}</a>
											</c:if>					                            
					                    </jmesa:htmlColumn>
					                        <jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="5%">
												<c:if test="${commissioniedilizier_var.movimentoRientro.id.codice==null}">					                        
						                        	 <a class="eliminaRiga" href="javascript:doHref('deleteCommissioniedilizieR.htm?codiceCommissioneR=${commissioniedilizier_var.id.codice}','<fmt:message key="javascript.confirm.delete" />')"  title="<fmt:message key="label.elimina" />${commissioniedilizier_var.id.codice}">
															<label><fmt:message key="label.azioni" /></label>
													 </a>
												 </c:if>
											</jmesa:htmlColumn>	
										</jmesa:htmlRow>
								</jmesa:htmlTable>
							 </jmesa:springTableFacade>
							 
						</form>
						
					</td>
				</tr>
				<tr>
					<td colspan="2">
	 			    	<div id="filtro" dojoType="dijit.Dialog" title="<fmt:message key='label.filro_istanze_discutere'/>" style="display: none;">
	    					<div style="width: 500px; height: 200px;" id="filtroContent"></div>
						</div>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('orainizio_id').focus();
				
				var _jmesaUrl='listCommissioniedilizieR.htm?codiceCommissione=${commissioniediliziet.entity.id.codice}&';
				var _captionTab='<fmt:message key="label.istanze_in_discussione"/>';	
				
				
				function tabshowFiltro(){
					dijit.byId('filtro').show();
					showFiltro();		
				}
				
				function showFiltro() {
					var ts=(new Date()).getTime();
					new Ajax.Request(
							'${pageContext.request.contextPath}/commissioniediliziet/ajaxShowFiltro.htm?ts_='+ts+'&codiceCommissioneT='+${commissioniediliziet.entity.id.codice},
							{
								method : 'post',
								onSuccess : function(transport) {							
									var response = transport.responseText;							
									$('filtroContent').innerHTML = parseAjaxResponse(response,true,false);
														
								},
								onFailure : function(transport) {
									var response = transport.responseText;
									alert(response);
								}
							});
				}
				
				// Javascrip per mostrare il calendario (Viene richiamto all'interno della jsp ajax/listInventarioprocedimenti.jsp)
				
				function setupCal(inputId, imageId){
					RANGE_CAL_1 = new Calendar({
							inputField: inputId,
							dateFormat: "%d/%m/%Y",
							trigger: imageId,
							bottomBar: false,
							onSelect: function() {
						var date = Calendar.intToDate(this.selection.get());
						this.hide();
					}
					})
				
				}
				
				function isDataPresent()
				{
					if(document.getElementById('data_id').value=='')
					{
						alert('Data Obbligatoria');
						return false;
					}
					return true;
				}
				
				function seachIstazeDaDiscure()
				{
					//if(isDataPresent())
					//{
						doSubmit('searchIstanzeInCommissione.htm','',document.innnerForm);
					//}
				}
			</script>	
		</spring-form:form>
	</div>
	 
	<div id="functions">
		<ul>
			<li><a href="javascript:doSubmit('updateCommissioniedilizieTAndChild.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href="javascript:tabshowFiltro();"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doSubmit('riordinaCommissioniedilizieR.htm','',document.inviodati)"><fmt:message key="button.riordina" /></a></li>
			<li><a href="javascript:doHref('view.htm?codice=${commissioniediliziet.entity.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
</body>
</html>