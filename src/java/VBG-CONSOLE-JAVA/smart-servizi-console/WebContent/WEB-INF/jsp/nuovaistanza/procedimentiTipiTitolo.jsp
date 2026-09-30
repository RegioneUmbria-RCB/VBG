<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<div class="titolo"><fmt:message key='label.title.tipo-titolo' /></div>
	<div class="descrizione" ><fmt:message key='label.descrizione-tipo-titolo' /></div>
	<spring:hasBindErrors name="nuovaIstanzaCommand">
	<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;"> 
		<span class="ui-icon ui-icon-alert" style="float: left;margin-top: 2px;margin-right: .3em;"></span> 
		<strong><fmt:message key="alert.controllare-i-dati-inseriti" /></strong>
		<%--
		<ul>
		<c:forEach items="${errors.allErrors }" var="err">       	
			<li>
				<spring:message code="${err.code }" arguments="${err.arguments }" text="${err.defaultMessage}" htmlEscape="false" />				
			</li>       
		</c:forEach>
		</ul>
		--%>	
	</div>
	</spring:hasBindErrors>
	<spring-form:form action="saveTipiTitolo.htm" method="post" commandName="nuovaIstanzaCommand" name="formProcedimentiTipiTitolo">
	<input type="hidden" name="pager_step" />
	<input type="hidden" name="pager_azione" />
	<c:forEach items="${nuovaIstanzaCommand.procedimentiSelezionati}" var="ph" varStatus="idx">
	<c:if test="${ph.tipiTitoloPresent eq true }">
		<div class="titolo_sezione">${ph.procedimento.descrizione }</div>
		<div class="sezione">
		<table class="sezione_table">
			<tr>
				<td class="sezione_table_label" colspan="2">
					<spring:bind path="procedimentiSelezionati[${idx.index }].tipiTitoloSonoInPossesso">
						<input type="hidden" name="_<c:out value="${status.expression}"/>" value="false" />					
						<c:choose>
						<c:when test="${status.value eq true}">
							<input type="checkbox" name="${status.expression}" value="${status.value}" checked="checked" id="checkbox${idx.index }" />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="${status.expression}" id="checkbox${idx.index }" />
						</c:otherwise>
						</c:choose>
					</spring:bind>
					
					<fmt:message key='label.sono-in-possesso-del-titolo' />
					<script type="text/javascript">
					$(document).ready(function(){
						
						if($('#tipo_titolo_${idx.index }').val() == ''){
							showHideFields${idx.index }('false','false','false','false','false','false');
						}else{
							$.ajax({
				                type: "GET",
				                url: "${pageContext.request.contextPath}/ajax/getProcedimentoTipiTitolo.htm",
				                data: "id=${ph.procedimento.codice}",
				                dataType: "json",
				                success: function( data, textStatus, jqXHR) {
									$.each(data, function(){
										var tt = $('#tipo_titolo_${idx.index }').val();
										if(this.value == tt){
											showHideFields${idx.index }(this.mostra_numero,this.mostra_data,this.mostra_da,'true',this.richiede_allegato,this.verifica_firma);
										}
													
									});
				                }
							});
						}
						if($('#checkbox${idx.index }').val() == 'true'){
							$('#dettaglio_tipo_titolo${idx.index }').show();
						}else{
							$('#dettaglio_tipo_titolo${idx.index }').hide();
						}
						$('#checkbox${idx.index }').click(function() {					
							if($(this).is(":checked")) {
								$('#dettaglio_tipo_titolo${idx.index }').show();
					        }else{
					        	$('#dettaglio_tipo_titolo${idx.index }').hide();
					        }
						});
					});
					</script>
				</td>
			</tr>
		</table>
		<div id="dettaglio_tipo_titolo${idx.index }">
		<table class="sezione_table">
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.tipo-titolo' /></td>
				<td>
				<spring:bind path="procedimentiSelezionati[${idx.index }].procedimento.estremiAtto.tipoAtto">
						<input type="text" name="${status.expression}" value="${status.value}" id="tipo_titolo_${idx.index }" size="60" />
				</spring:bind>
				<spring-form:errors path="procedimentiSelezionati[${idx.index }].procedimento.estremiAtto.tipoAtto" cssClass="validation_error" />
				<script type="text/javascript">
					$(document).ready(function(){
						$( "#tipo_titolo_${idx.index }" ).autocomplete({
							source: "${pageContext.request.contextPath}/ajax/getProcedimentoTipiTitolo.htm?id=${ph.procedimento.codice}",
							minLength: 1,
							select: function( event, ui ) {
								if(ui.item){
									$("#tipo_titolo_${idx.index }").val(ui.item.value);
									showHideFields${idx.index }(ui.item.mostra_numero,ui.item.mostra_data,ui.item.mostra_da,'true',ui.item.richiede_allegato,ui.item.verifica_firma);
								}else{
									$("#tipo_titolo_${idx.index }").val('');
									showHideFields${idx.index }('false','false','false','false','false','false');
								};
							},
							change: function(event, ui) {					
								if(ui.item){
									$("#tipo_titolo_${idx.index }").val(ui.item.value);
									showHideFields${idx.index }(ui.item.mostra_numero,ui.item.mostra_data,ui.item.mostra_da,'true',ui.item.richiede_allegato,ui.item.verifica_firma);
								}else{
									$("#tipo_titolo_${idx.index }").val('');
									showHideFields${idx.index }('false','false','false','false','false','false');
								};
							}
						});
					});
					function showHideFields${idx.index }(mostra_numero,mostra_data,mostra_da,mostra_note,richiede_allegato,verifica_firma){
						if(mostra_numero == 'false'){
							$('#tipo_titolo_num_${idx.index }').val('');
							$('#ttnum${idx.index  }').hide();
						}else{
							$('#ttnum${idx.index  }').show();
						}
						if(mostra_data == 'false'){
							$('#tipo_titolo_data_${idx.index }').val('');
							$('#ttdata${idx.index  }').hide();
						}else{
							$('#ttdata${idx.index  }').show();
						}
						if(mostra_da == 'false'){
							$('#tipo_titolo_da_${idx.index }').val('');
							$('#ttda${idx.index  }').hide();
						}else{
							$('#ttda${idx.index  }').show();
						}
						if(mostra_note == 'false'){
							$('#ttnote${idx.index  }').val('');
							$('#ttnote${idx.index  }').hide();
						}else{
							$('#ttnote${idx.index  }').show();
						}
						if(richiede_allegato == 'false'){
							$('#ttall${idx.index  }').val('');
							$('#ttall${idx.index  }').hide();
						}else{
							$('#ttall${idx.index  }').show();					
							$('#fileupload${idx.index }').fileupload(
								'option',
								'url',
								'${pageContext.request.contextPath}/file/ajaxUpload.htm?verificaFirma='+verifica_firma
							);
						}
					}
				</script>
				</td>
			</tr>
		</table>
		<spring:bind path="procedimentiSelezionati[${idx.index }].idx">
			<input type="hidden" name="${status.expression}" value="${idx.index }" />
		</spring:bind>
		<table class="sezione_table">
			<tr id="ttnum${idx.index  }">
				<td class="sezione_table_label"><fmt:message key='label.numero' /></td>
				<td colspan="2">
				<spring:bind path="procedimentiSelezionati[${idx.index }].procedimento.estremiAtto.riferimento">
						<input type="text" name="${status.expression}" value="${status.value}" id="tipo_titolo_num_${idx.index }" size="10" />
				</spring:bind>			
				<spring-form:errors path="procedimentiSelezionati[${idx.index }].procedimento.estremiAtto.riferimento" cssClass="validation_error" />
				</td>
			</tr>
			<tr id="ttdata${idx.index  }">
				<td class="sezione_table_label"><fmt:message key='label.data-atto' /></td>
				<td colspan="2">
				<spring:bind path="procedimentiSelezionati[${idx.index }].procedimento.estremiAtto.data">
						<input type="text" name="${status.expression}" value="${status.value}" id="tipo_titolo_data_${idx.index }" size="10" maxlength="10" />
				</spring:bind>
				<spring-form:errors path="procedimentiSelezionati[${idx.index }].procedimento.estremiAtto.data" cssClass="validation_error" />
				<script type="text/javascript">
					$("#tipo_titolo_data_${idx.index }").datepicker();
				</script>
				</td>
			</tr>
			<tr id="ttda${idx.index  }">
				<td class="sezione_table_label"><fmt:message key='label.rilasciato-da' /></td>
				<td colspan="2">
				<spring:bind path="procedimentiSelezionati[${idx.index }].procedimento.estremiAtto.rilasciatoDa">
						<input type="text" name="${status.expression}" value="${status.value}" id="tipo_titolo_da_${idx.index }" size="65" />
				</spring:bind>
				<spring-form:errors path="procedimentiSelezionati[${idx.index }].procedimento.estremiAtto.rilasciatoDa" cssClass="validation_error" />
				</td>
			</tr>
			<tr id="ttnote${idx.index  }">
				<td class="sezione_table_label" valign="top"><fmt:message key='label.note' /></td>
				<td colspan="2">
				<spring:bind path="procedimentiSelezionati[${idx.index }].procedimento.estremiAtto.note">
						<textarea name="${status.expression}" id="tipo_titolo_note_${idx.index }" rows="3" cols="50">${status.value}</textarea>
				</spring:bind>
				</td>
			</tr>
			<tr id="ttall${idx.index  }">
				<td class="sezione_table_label" valign="top"><fmt:message key='label.allegato' /></td>
				<spring:bind path="procedimentiSelezionati[${idx.index }].allegatoTipoTitolo">
				<td valign="top" width="35%">
					<input type="hidden" name="${status.expression }.id" value="${status.value.id}" id="codiceOggetto${idx.index}" />
					<input type="hidden" name="${status.expression }.allegato" value="${status.value.allegato}" id="nomeFile${idx.index}" />									
					<label id="fileUploadResult${idx.index }">
					${status.value.allegato} 
					<spring-form:errors path="procedimentiSelezionati[${idx.index }].allegatoTipoTitolo" cssClass="validation_error" />
					</label>
				</td>	
				<td>
				    <span class="fileinput-button button">					      
				        <span id="fileuploadbutton${idx.index }"><fmt:message key='button.upload' /></span> 
				        <input id="fileupload${idx.index }" type="file" name="file${idx.index  }" />
				    </span>	
				    <span class="button" onclick="rimuoviAllegatoProcedimento(${idx.index})" id="filedeletebutton${idx.index }">
				    	<span><fmt:message key='button.elimina' /></span> 
				    </span>						    
				    <div id="progress${idx.index }" class="progress">
				        <div class="progress-bar"></div>
				    </div>	
				    <script type="text/javascript">
					$(function () {
					    'use strict';
					    var url = '${pageContext.request.contextPath}/file/ajaxUpload.htm';
					    $('#fileupload${idx.index }').fileupload({
					        url: url,
					        dataType: 'json',
					        start: function(e){
					        	$('#fileupload${idx.index }').prop('disabled', true);
					        	$('#fileUploadResult${idx.index }').text('');
					        	$('#progress${idx.index } .progress-bar').removeClass('progress-bar-error');
					        	$('#progress${idx.index } .progress-bar').removeClass('progress-bar-success');
					        	$('#fileUploadResult${idx.index }').removeClass('validation_error');
					        },
					        done: function (e, data) { 
					        	if(data.result.Errori != null){
					        		$('#progress${idx.index } .progress-bar').addClass('progress-bar-error');
					        		$('#fileUploadResult${idx.index }').text(data.result.Errori).addClass('validation_error');
					        	}else{
					        		$('#progress${idx.index } .progress-bar').addClass('progress-bar-success');
					        		$('#fileUploadResult${idx.index }').text(data.result.fileName + " ("+data.result.length_hr+")");
					        		$('#codiceOggetto${idx.index}').val(data.result.codiceOggetto);
					        		$('#nomeFile${idx.index}').val(data.result.fileName);	
					        	}
					        	$('#progress${idx.index } .progress-bar').text('100%');
					        	$('#fileupload${idx.index }').prop('disabled', false);
					        },
					        progress: function (e, data) {
					            var progress = parseInt(data.loaded / data.total * 100, 10);
					            $('#progress${idx.index } .progress-bar').css('width',progress + '%');
					            $('#progress${idx.index } .progress-bar').text(progress - 1 + '%');
					        },
					        fail: function (e, data) {
					        	$('#progress${idx.index } .progress-bar').addClass('progress-bar-error');
					        	$('#progress${idx.index } .progress-bar').text('100%');
					        	$('#fileUploadResult${idx.index }').text('Errore durante il caricamento del file');
					        	$('#fileupload${idx.index }').prop('disabled', false);
					        }
					    }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
					});
				    </script>					    	    
				</td>
				</spring:bind>							
			</tr>
		</table>
		</div>
		</div>
	</c:if>
	</c:forEach>
	<div class="sezione_table_buttons">
		<input type="button" value="<fmt:message key='button.indietro' />" onclick="indietro()" />
		<input type="button" value="<fmt:message key='button.salva' />" onclick="submitForm()" />
	</div>
	</spring-form:form>
	<script type="text/javascript">
	function submitForm(){
		$.blockUI();
		document.formProcedimentiTipiTitolo.pager_step.value='';
		document.formProcedimentiTipiTitolo.pager_azione.value='A';
		document.formProcedimentiTipiTitolo.submit();
	}
	function indietro(){
		$.blockUI();
		window.location.replace("view.htm");
	}
	function rimuoviAllegatoProcedimento(idx){
		$('#codiceOggetto'+idx).val('');
		$('#nomeFile'+idx).val('');
		$('#fileUploadResult'+idx).text('');
		$('#progress'+idx+' .progress-bar').css('width','0%');
        $('#progress'+idx+' .progress-bar').text('');
	}
	</script>
</body>
</html>