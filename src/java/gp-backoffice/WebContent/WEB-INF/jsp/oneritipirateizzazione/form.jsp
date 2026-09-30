<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.math.BigDecimal"%>
<%@page import="it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.TipologiaRateizzazioneEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.dettaglio_oneritipirateizzazione.title" />		
	</title>
	<style>
		div.rateizza {
		    width: 60%;
		    border-collapse: collapse;
		}
		
		.divTable.rateizza .divTableCell, .divTable.rateizza .divTableHead {
			padding: 3px 2px;
		}
		
		.divTable.rateizza .divTableHeading {
		    border-bottom: 12px solid #FFFFFF;
		}
		
		.divTable.rateizza .divTableHeading .divTableHead {
		   font-size: 14px;
			font-weight: bold;
		}
		
		.divTable {
		    display: table;
		}
		
		.divTableRow {
		    display: table-row;
		}
		
		.divTableHeading {
		    display: table-header-group;
		    font-size: 14px;
		    text-transform: uppercase;
		    font-weight: bold;
		}
		
		.divTableCell, .divTableHead {
		    display: table-cell;
		}
		
		.divTableHeading {
		    display: table-header-group;
		}
		
		.divTableFoot {
		    display: table-footer-group;
		}
		
		.divTableBody {
		    display: table-row-group;
		}
		.divTableCell.importo {
				    color: #B61218;
				}
		.div#functions {
			margin-top: 21px;
		}
		#frequenzarate_id {
	 	   width: 236px;
		}
	</style>
</head>
<body>
	<span class="titoloPagina"> 
		<fmt:message key="label.dettaglio_oneritipirateizzazione.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>	
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../oneritipirateizzazione/form" />
	</jsp:include>
	
	<div id="subcontent">
		<spring-form:form commandName="otr" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="otr" />
		</jsp:include>
		<input type="hidden" name="codiceRaggruppamento" value="${codiceRaggruppamento }" />
		<input type="hidden" name="idOnere" value="${idOnere}" />		
		
		<c:if test="${flagRaggruppamento}">	
			<c:set var="importo" value="${ioHelper.totaleOneriCausaleRaggruppamento}" />
		</c:if>
		<c:if test="${not flagRaggruppamento}">	
			<c:set var="importo" value="${ioHelper.totaleOneriCausale}" />
		</c:if>
		<div class="divTable rateizza">
			<div class="divTableHeading">
			<c:if test="${flagRaggruppamento}">			
				<div class="divTableRow head">
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.raggruppamento" />
					</div>
					<div class="divTableCell importo">						
						${rco.rcoDescr}
					</div>
				</div>			
				<div class="divTableRow head">
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.importo" />
					</div>
					<div class="divTableCell importo">						
						<fmt:formatNumber maxFractionDigits="2" value="${importo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN %>"/>						
					</div>
				</div>
			</c:if>			
			<c:if test="${not flagRaggruppamento}">
				<div class="divTableRow head">
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.importo" />
					</div>
					<div class="divTableCell importo">						
						<fmt:formatNumber maxFractionDigits="2" value="${importo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN %>"/>	
						&nbsp;
						${istanzaoneri.tipicausalioneri.coDescrizione }		
					</div>					
				</div>
			</c:if>	
			</div>	
			
			<div class="divTableBody">				
				<div class="divTableRow">
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.tipirateizzazioni" />
					</div>
					<div class="divTableCell">						
						<spring-form:hidden path="id.codice" id="id_hidden" />
						<spring-form:select path="descrizione" id="descrizione_id" itemValue="id.codice" items="${oneritipirateizzazione}" itemLabel="descrizione" />
						<spring-form:errors path="descrizione" cssClass="error" /> 										
					</div>
				</div>
				<div class="divTableRow">
					<div class="divTableCell">
						<label for="numerorate_id">
							<fmt:message key="label.oneritipirateizzazione.numerorate" />
						</label>
					</div>
					<div class="divTableCell">
						<spring-form:input id="numerorate_id" path="nrorate" size="8" disabled="true" />
					</div>
				</div>
				<div class="divTableRow">
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.ripartizionerate" />
					</div>
					<div class="divTableCell">
						<spring-form:input id="ripartizionerate_id" path="ripartizionerate"  disabled="true" />
						<init:help idHelp="help_ripartizionerata" textKey="help.oneritipirateizzazione.ripartizionerate" />
					</div>
				</div>
				<div class="divTableRow">
					<div class="divTableCell">
						* <fmt:message key="label.oneritipirateizzazione.datainiziorateizzazione" />
					</div>
					<div class="divTableCell">						
						<spring-form:input id="datainiziorateizzazione_id" path="determinadatainizioratetransient" size="10"  onblur="isValidDate(this,true);" /> 
                        <init:calendar imagePath="/images/cal.gif" idImage="calDatainiziorateizzazione" idInput="datainiziorateizzazione_id" textKey="label.calendar" /> 
                        <spring-form:errors path="determinadatainizioratetransient" cssClass="error" />
						
					</div>
				</div>				
				<div class="divTableRow" id="tipologiaratagg" style="display:none">
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.frequenzarategg" />
					</div>
					<div class="divTableCell">
						<spring-form:input id="frequenzarategg_id" path="frequenzarate" size="35" disabled="true" />
					</div>
				</div>				
				<div class="divTableRow" id="tipologiarata" style="display:none">
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.frequenzarate" />
					</div>
					<div class="divTableCell">						
						<spring-form:select path="periodicitaEnum" id="frequenzarate_id" items="${periodicitaValue}"  disabled="true"/>						
					</div>
				</div>
				<div class="divTableRow">
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.scadenzarate" />
					</div>
					<div class="divTableCell">	
						<spring-form:hidden path="scadenzarate.id" id="hidden_scadenzarate"/>				
						<spring-form:input id="scadenzarate_id" path="scadenzarate.descrizione" size="35" disabled="true" />
						<init:help idHelp="help_scadenzarate" textKey="help.oneritipirateizzazione.scadenzarate" />
					</div>
				</div>
				<div class="divTableRow" id="interessi" >
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.interessi" />
					</div>
					<div class="divTableCell">
						<spring-form:input id="interessi_id" path="interessirate" size="8" disabled="true" />
					</div>
				</div>
				<div class="divTableRow">
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.speseRateizzazioni" />
					</div>
					<div class="divTableCell">
						<spring-form:input path="speseRateizzazione" id="speseRateizzazioni_id" size="8" disabled="true"/>						
						<init:help idHelp="help_speseRateizzazioni" textKey="help.oneritipirateizzazione.speseRateizzazioni" />
					</div>
				</div>
				<div class="divTableRow" id="flagInteressi" style="display:none">
					<div class="divTableCell">
						<fmt:message key="label.oneritipirateizzazione.interessilegali" />
					</div>
					<div class="divTableCell">												
						<input type="checkbox" id="interessilegali_id" value="${flagInteressiLegali}" disabled='disabled' />
						&nbsp;					
						<spring-form:input path="tipoAnatocismo" id="anatocismo_id" disabled="true" />
					</div>
				</div>
				<div class="divTableRow" id="datainiziointeressi" style="display: none">
					<div class="divTableCell">
						* <fmt:message key="label.oneritipirateizzazione.datadiinizio" />
					</div>
					<div class="divTableCell">
						<spring-form:input id="datadiinizio_id" path="dataInizioTransient" size="10"  onblur="isValidDate(this,true);" /> 
                        <init:calendar imagePath="/images/cal.gif" idImage="caldatadiinizio" idInput="datadiinizio_id" textKey="label.calendar" /> 
                        <spring-form:errors path="dataInizioTransient" cssClass="error" />
					</div>
				</div>
			</div>
		</div>
	</spring-form:form>
	</div> 
	<div id="functions">
		<ul>			
			<li><a id="btnRateizza" href="javascript:doSubmit('inserisciRateizzazione.htm?codiceIstanza=${istanza.id.codice}','',document.inviodati)" ><fmt:message key="button.rateizza" /> </a></li>		
			<li><a href="javascript:doHref('../istanzeoneri/list.htm?codiceIstanza=${istanza.id.codice}&&software=${istanza.software.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
			
	<script type='text/javascript'>	
		(function ($) {
		    var tipolrata = '${otr.tipologiaRateizzazione}';
		    var frequenzarate = '${otr.frequenzarate}';
		    var checkInteressi = '${otr.flagInteressiLegali}';
		    if (tipolrata == 'DEFAULT') {
		        $('#tipologiaratagg').show();
		        $('#flagInteressi').show();
		        if (checkInteressi == 'true') {
		            $('#datainiziointeressi').show();
		            $('#anatocismo_id').show();
		        } else {
		            $('#datainiziointeressi').hide();
		            $('#anatocismo_id').hide();
		        }
		    } else {
		        $('#tipologiarata').show();
		        $('#flagInteressi').hide();
		        $('#datainiziointeressi').hide();
		    }
		    $('#frequenzarate_id').val(frequenzarate);
	
		    $('#descrizione_id').change(function () {
		        console.log($(this).val());
	
		        var idTiporateizzazione = $(this).val();
		        // elimino l'eventuale messaggio di errore se presente
		        //$('#messaggioErrore').hide();
		        if (idTiporateizzazione != '') {
		            disableFunctions();
		            var jhqrPr = $.ajax({
		                url: "${pageContext.request.contextPath}/oneritipirateizzazione/ajaxSettaTipoRateizzazione.htm?codiceIstanza=${istanza.id.codice}",
		                data: { idTiporateizzazione: idTiporateizzazione },
		                context: document.body,
		                cache: false,
		                dataType: "html",
		                success: function (data, textStatus, jqXHR) {
		                    console.log(data);
		                    var object = JSON.parse(data);
		                    /* Imposto la visualizzazione in base alla tipologia di rateizzazione */
		                    if (object.tipologiaRateizzazione == 'DEFAULT') {
		                        $('#tipologiarata').hide();
		                        $('#tipologiaratagg').show();
		                        $('#flagInteressi').show();
		                        $('#datainiziointeressi').show();
		                        if (object.flginteressilegali == 'true') {
		                            $('#interessilegali_id').prop('checked', true);
		                            $('#interessilegali_id').checked = true;
		                            $('#interessi').hide();
		                            $('#anatocismo_id').show();
		                            $('#datainiziointeressi').show();
		                        } else {
		                            $('#interessi').show();
		                            $('#interessilegali_id').prop('checked', false);
		                            $('#interessilegali_id').checked = false;
		                            $('#anatocismo_id').hide();
		                            $('#datainiziointeressi').hide();
		                        }
		                    }
		                    else {
		                        $('#tipologiaratagg').hide();
		                        $('#tipologiarata').show();
		                        $('#flagInteressi').hide();
		                        $('#datainiziointeressi').hide();
		                        $('')
		                    }
	
		                    /* setto tutti i campi  */
	
		                    $('#id_hidden').val(object.id);
		                    $('#numerorate_id').val(object.numeroRate);
		                    $('#ripartizionerate_id').val(object.ripartizionerate);
		                    /* $('#tipologiarata').val(object.tipologiaRateizzazione );	 */
		                    /* Parse per la frequenza rate quando non è espressa in giorni*/
		                    var freq = object.frequenzarate;
		                    switch (freq) {
		                        case '30':
		                            $('#frequenzarate_id').val(12);
		                            break;
		                        case '60':
		                            $('#frequenzarate_id').val(6);
		                            break;
		                        case '90':
		                            $('#frequenzarate_id').val(4);
		                            break;
		                        case '120':
		                            $('#frequenzarate_id').val(3);
		                            break;
		                        case '180':
		                            $('#frequenzarate_id').val(2);
		                            break;
		                        case '365':
		                            $('#frequenzarate_id').val(1);
		                            break;
		                        default:
		                            $('#frequenzarate_id').val(12);
		                            break;
		                    }
		                    $('#scadenzarate_id').val(object.scadenzarate.descrizione);
		                    $('#hidden_scadenzarate').val(object.scadenzarate.id);
		                    $('#interessi_id').val(object.interessirate);
		                    $('#speseRateizzazioni_id').val(object.speseRateizzazione);
		                    $('#interessilegali_id').val(object.flginteressilegali);
		                    $('#anatocismo_id').val(object.tipoAnatocismo);
		                    $('#datainiziorateizzazione_id').val(object.deterinizioratetrans);
		                    $('#datadiinizio_id').val(object.deterinizioratetrans);
		                    enableFunctions();
		                }, error: function (xhr, ajaxOptions, thrownError) {
		                    enableFunctions()
		                    alert(xhr.status);
		                    alert(thrownError);
		                }
		            });
		        }
		    });
	
		    $('#datadiinizio_id').on('keyup', function () {
		        console.log($(this).val());
		        var datainizioInteressi = $(this).val();
		        var dataInizioRate = $('#datainiziorateizzazione_id').val();
		        console.log(dataInizioRate);
		        if (dataInizioRate < datainizioInteressi) {
		            alert("Attenzione, non è possibile effettuare la rateizzazione per il seguente motivo: La data iniziale per gli Interessi legali é successiva alla data finale.");
		            $(this).css('border-color', 'red');
		            $(this).css('border-width', 'medium');
		        } else {
		            $(this).css('border-color', 'inherit');
		            $(this).css('border-width', 'initial');
		            console.log("Ok");
		        }
		    });
		})(jQuery);		
	</script>
</body>
</html>