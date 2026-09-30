<%@page import="it.gruppoinit.pal.gp.core.domain.web.IstanzeCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.AndOrRestriction"%>
<%@page import="it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum"%>
<%@page
	import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.result_validazione" /></title>

</head>
<body>
	<span class="titoloPagina"> <init:editLabel
			key="label.result_validazione" role="ROLE_EDITLABEL" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<%-- 
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanze/searchIstanze" />
	</jsp:include>
	--%>
	<div id="subcontent">

		<spring-form:form commandName="equitaliatracciatoCommand"
			name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="equitaliatracciatoCommand" />
			</jsp:include>
			<c:if
				test="${equitaliatracciatoCommand.numeroIstanzeNonValidatepacchetto>0}">
				<div>
					<b class="error_header alert alert-danger">Attenzione sono
						presenti errori di validazione per il pacchetto di pratiche preso
						in esame</b>
				</div>
				<br />
			</c:if>
			<fieldset>
				<legend>
					<b><fmt:message key="label.configurazioni" /></b>
				</legend>
				<table width="100%" border="0">
					<tr>
						<td colspan="8" class="titoloSottoSezione">Configurazioni
							equitalia</td>
					</tr>
					<%-- prima riga --%>
					<tr>
						<%-- prima colonna --%>
						<td>Codice ente creditore:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.codiceEnteCreditore" /></td>
						<%-- seconda colonna --%>
						<td>Tipo ufficio:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.tipoUfficio" /></td>
						<%-- terza colonna --%>
						<td>Codice ufficio:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.codiceUfficio" /></td>
						<%-- quarta colonna --%>
						<td>Tipo minuta:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.tipoMinuta" /></td>
					</tr>
					<%-- seconda riga --%>
					<tr>
						<%-- prima colonna --%>
						<td>Tipo cartellazione:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.tipoCartellazione" /></td>
						<%-- seconda colonna --%>
						<td>Specie del ruolo:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.specieDelRuolo" /></td>
						<%-- terza colonna --%>
						<td>Tipo iscrizione:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.tipoIscrizione" /></td>
						<%-- quarta colonna --%>
						<td>Tipo compenso:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.tipoCompenso" /></td>

					</tr>
					<%-- terza riga  --%>
					<tr>
						<%-- prima colonna --%>
						<td>Release:</td>
						<td><spring-form:input path="equitaliaTracciatiCfg.rilascio" />
						</td>
						<%-- seconda colonna --%>
						<td>Testo modalità opposizione:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.testoModalitaOpposizione" /></td>
						<%-- terza colonna --%>
						<td>Testo comunicazione contribuente:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.testoComunicazioneContrib" /></td>
						<%-- quarta colonna --%>
						<td>Invio testo singolo contribuente:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.invioTestoSingContrib" /></td>
					</tr>

					<%-- quarta riga  --%>
					<tr>
						<%-- prima colonna --%>
						<td>Tipologia ente:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.tipologiaEnte" /></td>
						<%-- seconda colonna --%>
						<td>Invio volantino:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.invioVolantino" /></td>
						<%-- terza colonna --%>
						<td>Modalita stampa volantino:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.modalitaStampaVolantino" /></td>
						<%-- quarta colonna --%>
						<td>Comunicazione contribuente:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.comunicazioneContrib" /></td>
					</tr>

					<%-- quinta riga riga --%>
					<tr>
						<%-- prima colonna --%>
						<td>Flag provenienza:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.flagProvenienza" /></td>
						<%-- seconda colonna --%>
						<td>Codifica identificativo tipologia atto</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.codificaTipologiaAtto" /></td>
						<%-- terza colonna --%>
						<td>Codice tipo atto</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.codiceTipoAtto" /></td>
						<%-- quarta colonna --%>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="8" class="titoloSottoSezione">Configurazioni
							backoffice</td>
					</tr>
					<%-- sesta riga  --%>
					<tr>
						<%-- prima colonna --%>
						<td>Movimento anno atto:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.codiceMovAnnoDebito" /></td>
						<%-- seconda colonna --%>
						<td>Movimento per data atto:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.codiceMovDataAtto" /></td>
						<%-- terza colonna --%>
						<td>Movimento per data notifica atto:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.codiceMovDataNotificaAtto" /></td>
						<%-- quarta colonna --%>
						<td>Capo dinamico numero verbale:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.dynCampiNumeroVerbale" /></td>
					</tr>
					<%-- settima riga  --%>
					<tr>
						<%-- prima colonna --%>
						<td>Capo dinamico data verbale:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.dynCampiDataVerbale" /></td>
						<%-- seconda colonna --%>
						<td>Capo dinamico testo verbale:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.dynCampiTestVerbale" /></td>
						<%-- terza colonna --%>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<%-- quarta colonna --%>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<%-- ottava riga  --%>
					<tr>
						<%-- prima colonna --%>
						<td>Movimento istanza a ruolo:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.codiceMovIstanzaARuolo" /></td>
						<%-- seconda colonna --%>
						<td>Codice tipo causale onere sanzione:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.mapEqInSanAmmComOner" />(E'
							possibile inserire più codici separati da virgole. Es 89,90)</td>
						<%-- terza colonna --%>
						<td>Codice tipo causale onere spese sanzione:</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.mapEqInSpSanAmmComOner" />(E'
							possibile inserire più codici separati da virgole. Es 100,101)</td>
						<%-- quarta colonna --%>
						<td>Codice registro autorizzazione ordinanza</td>
						<td><spring-form:input
								path="equitaliaTracciatiCfg.codiceRegAutOrdinanza"
								readonly="true" /></td>

					</tr>

					<%-- nona  riga--%>
					<tr>
						<%-- prima colonna --%>
						<td>Ambito</td>
						<c:if test="${ambito.id!=null}">
							<td><b>${ambito.ambito}</b></td>
						</c:if>
						<c:if test="${ambito.id==null}">
							<td><b>TUTTI</b></td>
						</c:if>

						<%-- seconda colonna --%>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<%-- terza colonna --%>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<%-- quarta colonna --%>
						<td>&nbsp;</td>
						<td>&nbsp;</td>

					</tr>

					<%-- decima riga  --%>
					<tr>
						<%-- prima colonna --%>
						<td>Referente nome</td>
						<td><spring-form:input path="nomeResponsabile" /> <spring-form:errors
								path="nomeResponsabile" cssClass="error" delimiter="," /></td>
						<%-- seconda colonna --%>
						<td>Referente Cognome</td>
						<td><spring-form:input path="cognomeResponsabile" /> <spring-form:errors
								path="cognomeResponsabile" cssClass="error" delimiter="," /></td>

						<%-- terza colonna --%>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<%-- quarta colonna --%>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>


				</table>
			</fieldset>


			<fieldset>
				<legend>
					<b><fmt:message key="label.riepilogo" /></b>
				</legend>
				<div class="parametriDiv">
					<div class="etichetta">
						<div>
							<fmt:message key="label.numero_istanze_pacchetto" />
							:
						</div>
						<div>
							<fmt:message key="label.numero_istanze_pacchetto_non_validate" />
							:
						</div>
					</div>
					<div class="parametro">
						<div>${equitaliatracciatoCommand.numeroIstanzepacchetto}</div>
						<div>${equitaliatracciatoCommand.numeroIstanzeNonValidatepacchetto}</div>
					</div>
				</div>
				<br />
			</fieldset>

			<div class="jmesa">
				<table border="1" cellpadding="2" cellspacing="0" class="table">
					<%
					    int i=1;
					%>
					<tr class="header">
						<td colspan="5">
							<%-- <fmt:message key="label.risultato_validazione_pacchetto_istanze" />
								<br />
								 --%> <c:if
								test="${equitaliatracciatoCommand.numeroIstanzeNonValidatepacchetto>0}">
		    						Forza creazione tracciato, escludendo le istanza con errori <input
									id="chb_id" type="checkbox" onchange="mostrabottone(this)" />
							</c:if>
						</td>
					</tr>
					<c:forEach items="${equitaliatracciatoCommand.resultValidazione}"
						var="current" varStatus="a">
						<tr>
							<td width="25%" style="vertical-align: top;"><b>${current.chiave}</b></td>
							<td width="75%" colspan="4">
								<table width="100%" border="1">
									<tr class="header">
										<td width="50%"><fmt:message key="label.errori" /></td>
										<td width="50%"><fmt:message key="label.warning" /></td>
									</tr>
									<c:forEach items="${current.valore}" var="var" varStatus="b">
										<tr class="<%=(i%2)==0?"odd":"even"%>">
											<c:if test="${var.isError}">
												<td width="25">${var.message}</td>
												<td width="25">&nbsp;</td>
											</c:if>
											<c:if test="${!var.isError}">
												<td width="25">&nbsp;</td>
												<td width="25">${var.message}</td>
											</c:if>
										</tr>
										<%
										    i++;
										%>
									</c:forEach>

								</table>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>


		</spring-form:form>





	</div>
	<div id="functions">
		<ul>
			<%--   <c:if test="${istanzeConErrori==0 && not empty equitaliatracciatoCommand.resultValidazione}">--%>
			<li id="button_create_id"><a
				href="javascript:creaTracciatoEquitalia450Istanze();"><fmt:message
						key="button.create_tracciato" /></a></li>
			<li id="button_create_id_excel"><a
				href="javascript:creaTracciatoEquitalia450IstanzeExcel();"><fmt:message
						key="button.create_tracciato_excel" /></a></li>
			<%--</c:if>--%>
			<li><a
				href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>

	<script type="text/javascript">
	
	
	
	function download(hrefFormAction, confirmMessage, objForm) {


		
    	var queryString; 
    	if (buttonSubmitted) {
    		return;
    	}
    	if (checkConfirmMessage(confirmMessage)) {
    		if (objForm) {
    			//disableFunctions();
    			objForm.action = hrefFormAction;
    			objForm.submit();
    			return;
    		} else {
    			//disableFunctions();
    			document.forms[0].action = hrefFormAction;
    			document.forms[0].submit();
    			return
    		}
    	}
    	
    	
    }
	
	jQuery(document).ready(function() {
		if(${istanzeConErrori==0 && not empty equitaliatracciatoCommand.resultValidazione})
		{
			jQuery("#button_create_id").show();
			jQuery("#button_create_id_excel").show();
		}else
		{
			jQuery("#button_create_id").hide();
			jQuery("#button_create_id_excel").hide();
		}

	});
	
	function mostrabottone(valore)
	{
		
		if(jQuery("#chb_id").attr('checked'))
		{jQuery("#button_create_id").show();
		jQuery("#button_create_id_excel").show();}
		else{jQuery("#button_create_id").hide();
		jQuery("#button_create_id_excel").hide();}
		
	}
		
		
	function creaTracciatoEquitalia450Istanze(){
		if(jQuery("#chb_id").attr('checked'))
		{setTimeout("doSubmit('creaTracciatoEquitalia450Istanze.htm?isForzaCreazione=true','',document.inviodati)",10);}
		else{setTimeout("doSubmit('creaTracciatoEquitalia450Istanze.htm','',document.inviodati)",10);}
		
		
	}
	
	function creaTracciatoEquitalia450IstanzeExcel(){
		/*
		if(jQuery("#chb_id").attr('checked'))
		{setTimeout("doSubmit('ajaxCreaTracciatoEquitalia450IstanzeExcel.htm?isForzaCreazione=true','',document.inviodati)",10);}
		else{setTimeout("doSubmit('ajaxCreaTracciatoEquitalia450IstanzeExcel.htm','',document.inviodati)",10);}
		*/
		
		if(jQuery("#chb_id").attr('checked'))
		{download('ajaxCreaTracciatoEquitalia450IstanzeExcel.htm?isForzaCreazione=true', '', document.inviodati);}
		else{download('ajaxCreaTracciatoEquitalia450IstanzeExcel.htm', '', document.inviodati);}
		
		
		
		
	}
	</script>
</body>
</html>