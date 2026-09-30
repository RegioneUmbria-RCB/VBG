<%@ include file="../includes/taglibs.jsp"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="java.math.BigDecimal"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="form.registrazioniFilter.title.listscadenze" /></title>
</head>
<body>
<span class="titoloPagina"> <fmt:message key="form.registrazioniFilter.title.listscadenze" /> </span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../registrazioniinout/searchScadenze" />
</jsp:include>
<script type="text/javascript">
		var arrayIdCampiDaAssegnare = new Array();
		var arrayIdCampiDaAssegnareViewDettagli=new Array();
</script>
<div id="subcontent">
	<spring-form:form commandName="registrazioniInOutCommand" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="registrazioniInOutCommand" />
	</jsp:include>
	<div class="parametriDiv">
	<fieldset><legend><fmt:message key="label.filtri" /></legend>
	<c:if test="${registrazioniInOutCommand.filter.registrazioniCausali.id.codice!=null}">
		<span class="parametri"><fmt:message key="form.registrazioniFilter.registrazioniCausali" />:<label> ${registrazioniInOutCommand.filter.registrazioniCausali.descrizione}</label> </span>
	</c:if>
	<c:if test="${registrazioniInOutCommand.filter.conti.id.codice!=null}">
		<span class="parametri"><fmt:message key="form.registrazioniFilter.conti" /> : <label>${registrazioniInOutCommand.filter.conti.descrizione}</label> </span>
	</c:if>
	<c:if test="${registrazioniInOutCommand.filter.dataInizio!=null || registrazioniInOutCommand.filter.dataFine!=null}">
		<span class="parametri"><fmt:message key="form.registrazioniFilter.scadenza" /> : <c:if test="${registrazioniInOutCommand.filter.dataInizio!=null }">
			<fmt:message key="form.registrazioniFilter.data.inizio" /> : 
			<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioniInOutCommand.filter.dataInizio}" /></label>
		</c:if> <c:if test="${registrazioniInOutCommand.filter.dataFine!=null}">
			<fmt:message key="form.registrazioniFilter.data.fine" /> : 
			<label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioniInOutCommand.filter.dataFine}" /></label>
		</c:if> </span>
	</c:if>
	<c:if test="${registrazioniInOutCommand.filter.anagrafe.id.codice!=null}">
		<span class="parametri"><fmt:message key="form.registrazioniFilter.anagrafe" /> : <label>${registrazioniInOutCommand.filter.anagrafe.descrizioneRichiedente}</label> </span>
	</c:if>
	<c:if test="${registrazioniInOutCommand.filter.mercati.id.codice!=null}">
		<span class="parametri"><fmt:message key="form.registrazioniFilter.mercati" /> : <label>${registrazioniInOutCommand.filter.mercati.descrizione}</label> </span>
	</c:if>
	<c:if test="${registrazioniInOutCommand.filter.mercatiUso.id.codice!=null}">
		<span class="parametri"><fmt:message key="form.registrazioni.mercatiUso" /> : <label>${registrazioniInOutCommand.filter.mercatiUso.descrizione}</label> </span>
	</c:if>
	<c:if test="${registrazioniInOutCommand.filter.posteggio.id.codice!=null}">
		<span class="parametri"><fmt:message key="form.registrazioni.mercati.posteggio" /> : <label>${registrazioniInOutCommand.filter.posteggio.codiceposteggio}</label> </span>
	</c:if>
	<c:if test="${registrazioniInOutCommand.filter.alberoproc.id.codice!=null}">
		<span class="parametri"><fmt:message key="form.registrazioniFilter.alberoproc" /> : <label>${registrazioniInOutCommand.filter.alberoproc.scDescrizione}</label> </span>
	</c:if>
	<c:if test="${registrazioniInOutCommand.filter.amministrazioni.id.codice!=null}">
		<span class="parametri"><fmt:message key="form.registrazioniFilter.amministrazioni" /> : <label>${registrazioniInOutCommand.filter.amministrazioni.amministrazione}</label> </span>
	</c:if>
	<c:if test="${registrazioniInOutCommand.filter.importo!=null}">
		<span class="parametri"><fmt:message key="form.registrazioniFilter.importo.maggiore" /> : <label>${registrazioniInOutCommand.filter.importo}</label> </span>
	</c:if>
	<c:if test="${registrazioniInOutCommand.filter.saldo!=null}">
		<span class="parametri"><fmt:message key="form.registrazioniFilter.daincassare.maggiore" /> : <label>${registrazioniInOutCommand.filter.saldo}</label> </span>
	</c:if>
	<c:if
		test="${registrazioniInOutCommand.filter.registrazioniCausali.id.codice==null 
                   && registrazioniInOutCommand.filter.conti.id.codice==null 
                   && registrazioniInOutCommand.filter.dataInizio==null && registrazioniInOutCommand.filter.dataFine==null 
                   && registrazioniInOutCommand.filter.anagrafe.id.codice==null 
                   && registrazioniInOutCommand.filter.mercati.id.codice==null
                   && registrazioniInOutCommand.filter.alberoproc.id.codice==null 
                   && registrazioniInOutCommand.filter.amministrazioni.id.codice==null
                   && registrazioniInOutCommand.filter.importo==null
                   && registrazioniInOutCommand.filter.posteggio.id.codice==null
                    && registrazioniInOutCommand.filter.saldo == null}"
	>
		<span class="parametri"><fmt:message key="form.registrazioniFilter.nofilter" /></span>
	</c:if>
	</fieldset>
	</div>
	<br class="clear"/>
	<input type="hidden" name="entity.tipo" value="E" />
	<fieldset><legend><fmt:message key="form.registrazioniInOut.regNuovoOSeleziona" /></legend>
	<table>
		<tr>
			<td colspan="5"><spring-form:select onchange="fillRegIOValues(this);" path="entity.id.codice">
				<spring-form:option value="">NUOVO INCASSO</spring-form:option>
				<c:forEach items="${regIODaAssegnare}" var="regIO">
					<spring-form:option value="${regIO.id.codice}">DATA INCASSO: <fmt:formatDate value="${regIO.dataIncasso}" pattern="dd/MM/yyyy" />, IMPORTO: <fmt:formatNumber minFractionDigits="2">${regIO.importo}</fmt:formatNumber>
						<fmt:message key="label.valuta" />, RIMANENZA: <fmt:formatNumber minFractionDigits="2">${regIO.rimanenza}</fmt:formatNumber>
						<fmt:message key="label.valuta" />, ANAGRAFE: ${regIO.anagrafe.descrizioneRichiedente}</spring-form:option>
				</c:forEach>
			</spring-form:select></td>
		</tr>
	</table>
	<div id="regIODiv">
	<table>
		<tr>
			<td><fmt:message key="form.registrazioniInOut.importo" /></td>
			<td><c:if test="${registrazioniInOutCommand.entity.id.codice == null}">
				<spring-form:input id="importo_id" path="entity.importo" size="10" onchange="setRimanenza(this);" />
			</c:if> <c:if test="${registrazioniInOutCommand.entity.id.codice != null}">
				<spring-form:input id="importo_id" path="entity.importo" size="10" onchange="setRimanenza(this);" readonly="true" />
			</c:if> <spring-form:errors path="entity.importo" cssClass="error" /></td>
			<td><fmt:message key="form.registrazioniInOut.rimanenza" /></td>
			<td><c:if test="${registrazioniInOutCommand.entity.id.codice!=null}">
				<c:if test="${registrazioniInOutCommand.entity.rimanenza gt 0}">
					<spring-form:input id="rimanenza_id" cssClass="inputRed" path="entity.rimanenza" size="10" readonly="true" />
				</c:if>
				<c:if test="${registrazioniInOutCommand.entity.rimanenza le 0}">
					<spring-form:input id="rimanenza_id" cssClass="inputGreen" path="entity.rimanenza" size="10" readonly="true" />
				</c:if>
				<spring-form:errors path="entity.rimanenza" cssClass="error" />
				</c:if>
					<c:if test="${registrazioniInOutCommand.entity.id.codice == null}">
					<input id="rimanenza_id" type="text" size="10" readonly="readonly" />
				</c:if> 
				<a class="vbg-btn btn-euro btn-euro-aggiungi" href="javascript:setAllValues();" title="Assegna tutti"> 
					<!-- <img src="${pageContext.request.contextPath}/images/money_add.png" alt="Assegna tutti" />  --> 
				</a>
			</td>
			<td><fmt:message key="form.registrazioniInOut.datadistinta" /></td>
			<td><spring-form:input id="dataDistinta_id" path="entity.dataDistinta" size="10" maxlength="10" onblur="isValidDate(this,true);" /> 
				<init:calendar imagePath="/images/cal.gif" idImage="calDataDistinta" idInput="dataDistinta_id" textKey="label.calendar"/> 
				<spring-form:errors path="entity.dataDistinta" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioniInOut.tipimodalitapagamento" /></td>

			<td><spring-form:select path="entity.tipimodalitapagamento.id.codice">
				<spring-form:options items="${tipimodalitapagamentoList}" itemValue="id.codice" itemLabel="mpDescrestesa" />
			</spring-form:select> <spring-form:errors path="entity.tipimodalitapagamento" cssClass="error" /></td>
			<td><fmt:message key="form.registrazioniInOut.riferimentipagamento" /></td>
			<td><spring-form:input id="riferimentiPagamento_id" path="entity.riferimentiPagamento" /> <spring-form:errors path="entity.riferimentiPagamento" cssClass="error" tabindex="0" /></td>
			<td><fmt:message key="form.registrazioniInOut.dataincasso" /></td>
			<td><spring-form:input id="dataIncasso_id" path="entity.dataIncasso" size="10" maxlength="10" onblur="isValidDate(this,true);" /> <init:calendar imagePath="/images/cal.gif" idImage="calDataIncasso" idInput="dataIncasso_id"
				textKey="label.calendar"
			/> <spring-form:errors path="entity.dataIncasso" cssClass="error" /></td>
			<td><fmt:message key="form.registrazioniInOut.note" /></td>
			<td><spring-form:textarea id="note_id" path="entity.note" cols="20" rows="3" /> <spring-form:errors path="entity.note" cssClass="error" tabindex="0" /></td>
		</tr>
	</table>
	</div>
	<table>
		<tr>
			<td><fmt:message key="form.registrazioniInOut.visualizzadettagli" /></td>
			<td><spring-form:checkbox id="visualizzaDettagliId" path="entity.visualizzaDettagliTransient" onclick="visualizzaDettagliScadenze();" /> <init:help idHelp="help1" textKey="form.registrazioniInOut.visualizzadettagli.help" /></td>
		</tr>
	</table>

	</fieldset>
	<div id="functions">
	<ul>
		<li><a href="javascript:doSubmit('updateScadenze.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	</ul>
	</div>
	<br class="clear" />
	<%
String dettaglioParziale="display:none;";
String dettaglioTotale="display:none;";
%>
	<fieldset><legend><fmt:message key="form.registrazioniInOut.scadenze" /></legend> <%-- VISUALIZZA LA TABELLA TOTALE --%>
	<div class="jmesa" id="dettaglioCompleto">
	<table class="table" width="100%">
		<thead>
			<tr class="header">
				<td><fmt:message key="form.registrazioniFilter.scadenza" /></td>
				<td width="8%"><fmt:message key="form.registrazioniFilter.progressivo" /></td>
				<td id="causaleForDettagli" style="<%=dettaglioParziale%>"><fmt:message key="form.registrazioniFilter.registrazioniCausali" /></td>
				<td id="mercatiForDettagli" style="<%=dettaglioParziale %>"><fmt:message key="form.registrazioniFilter.mercati" /></td>

				<td id="usoForDettagli" style="<%=dettaglioParziale %>"><fmt:message key="form.registrazioni.mercatiUso" /></td>
				<td id="posteggioForDettagli" style="<%=dettaglioParziale %>"><fmt:message key="form.registrazioni.mercati.posteggio" /></td>
				<td width="5%"><fmt:message key="form.registrazioniFilter.nrRata" /></td>
				<td width="10%"><fmt:message key="form.registrazioniFilter.conto" /></td>
				<td width="5%" style="text-align: right;"><fmt:message key="form.registrazioniFilter.importo" /></td>
				<td width="5%" style="text-align: right;"><fmt:message key="form.registrazioniFilter.daincassare" /></td>
				<td width="5%"><fmt:message key="form.registrazioniFilter.ripartisci" /></td>
				<td width="10%"><fmt:message key="form.registrazioniFilter.assegna" /></td>
				<td width="5%" style="text-align: right;"><fmt:message key="form.registrazioniFilter.incassato" /></td>
				<td><fmt:message key="form.registrazioniFilter.dettagliIncassi" /></td>

			</tr>
		</thead>
		<tbody class="tbody">
			<%int anchor=0; %>
			<%int counter=0; %>
			<%int j=1;%>
			<c:set var="totRimanenza" value="0.00"></c:set>
			<c:set var="totImporto" value="0.00"></c:set>
			<c:set var="totIncassato" value="0.00"></c:set>
			<c:forEach items="${registrazioniInOutCommand.scadenzeList}" var="scadenzaImporto" varStatus="currentImporto">
				<c:if test="${(scadenzaImporto.rimanenza) > 0}">
					<c:set var="totRimanenza" value="${totRimanenza + scadenzaImporto.rimanenza}"></c:set>
					<c:set var="totImporto" value="${totImporto + scadenzaImporto.importo}"></c:set>
					<c:set var="totIncassato" value="${totIncassato + scadenzaImporto.incassato}"></c:set>
					<tr class="<%=(j%2)==0?"odd":"even"%>">
						<td><fmt:formatDate value="${scadenzaImporto.scadenza }" pattern="dd/MM/yyyy" /></td>
						<td id="registrazione<%=anchor %>"><a href="#registrazione<%=anchor %>" onclick="javascript:$('a_popup_registrazione${currentImporto.index}').appear();" title="Visualizza dettagli">${scadenzaImporto.registrazioni.progressivo}</a>
						<div id="a_popup_registrazione${currentImporto.index}" style="display: none">
						<table>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.progressivo" /></td>
								<td>${scadenzaImporto.registrazioni.progressivo} <a href="#registrazione<%=anchor %>" onclick="javascript:$('a_popup_registrazione${currentImporto.index}').fade();">(chiudi)</a></td>
							</tr>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.anagrafe" /></td>
								<td>${scadenzaImporto.registrazioni.anagrafe.descrizioneRichiedente}</td>
							</tr>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.registrazioniCausali" /></td>
								<td>${scadenzaImporto.registrazioni.registrazioniCausali.descrizione}</td>
							</tr>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.importo" /></td>
								<td><fmt:formatNumber minFractionDigits="2">${scadenzaImporto.registrazioni.importo}</fmt:formatNumber></td>
							</tr>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.incassato" /></td>
								<td><fmt:formatNumber minFractionDigits="2">${scadenzaImporto.registrazioni.incassato}</fmt:formatNumber></td>
							</tr>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.daincassare" /></td>
								<td><fmt:formatNumber minFractionDigits="2">${scadenzaImporto.registrazioni.importo - scadenzaImporto.registrazioni.incassato }</fmt:formatNumber></td>
							</tr>
						</table>
						</div>
						</td>

						<td id="causale<%=j%>" style="<%= dettaglioParziale%>">${scadenzaImporto.registrazioni.registrazioniCausali.descrizione}</td>
						<td id="mercato<%=j%>" style="<%= dettaglioParziale%>">${scadenzaImporto.registrazioni.mercatiD.mercati.descrizione}</td>
						<td id="uso<%=j%>" style="<%=dettaglioParziale %>">${scadenzaImporto.registrazioni.mercatiUso.descrizione}</td>
						<td id="posteggio<%=j%>" style="<%=dettaglioParziale %>">${scadenzaImporto.registrazioni.mercatiD.codiceposteggio}</td>

						<td>${scadenzaImporto.nrRata}</td>
						<td>${scadenzaImporto.conti.descrizione}</td>
						<td align="right"><fmt:formatNumber minFractionDigits="2">${scadenzaImporto.importo}</fmt:formatNumber></td>
						<td align="right"><fmt:formatNumber minFractionDigits="2">${scadenzaImporto.rimanenza}</fmt:formatNumber></td>
						<td align="right">
							<input type="checkbox" id="rip_${currentImporto.index}" onclick="gestImporto(this,'assegna_${currentImporto.index}',${scadenzaImporto.rimanenza},${currentImporto.index});" />
							<input type="hidden" id="da_assegnare_${currentImporto.index}" value="${scadenzaImporto.rimanenza}" />
						
						</td>
						<td>
							<spring-form:input id="assegna_${currentImporto.index}" path="scadenzeList[${currentImporto.index}].transientSommaDaAssegnare" maxlength="8" size="8" readonly="true" /> 
							<script type="text/javascript">
								arrayIdCampiDaAssegnare.push('assegna_${currentImporto.index}#${scadenzaImporto.importo - scadenzaImporto.incassato}');
     						</script> 
     						<a class="vbg-btn btn-euro btn-euro-aggiungi" href="javascript:setValue('assegna_${currentImporto.index}','${scadenzaImporto.importo - scadenzaImporto.incassato}');" title="<fmt:message key="label.assegna" />"> 
     							<!-- <img src="${pageContext.request.contextPath}/images/money_add.png" alt="<fmt:message key="label.assegna" />"/> -->
							</a> 
							<a class="vbg-btn btn-elimina" href="javascript:setValue('assegna_${currentImporto.index}','0');" title="<fmt:message key="label.annulla" />"> 
								<!-- <img src="${pageContext.request.contextPath}/images/cross.gif" alt="<fmt:message key="label.annulla" />" />  -->
							</a>
						</td>
						<td align="right"><fmt:formatNumber minFractionDigits="2">${scadenzaImporto.incassato}</fmt:formatNumber></td>
						<td><c:if test="${not empty scadenzaImporto.regIoAssegnazionis}">
							<table class="table" width="100%">
								<tbody class="tbody">									
									<%int k=1;%>
									<c:forEach items="${scadenzaImporto.regIoAssegnazionis}" var="varAssegnazione" varStatus="currentAssegnazione">
										<tr class="<%=(k%2)==0?"odd":"even"%>">
											<td align="right">
												<fmt:formatDate value="${varAssegnazione.registrazioniInOut.dataIncasso}" pattern="dd/MM/yyyy" /> 
												(<fmt:formatNumber minFractionDigits="2">${varAssegnazione.importo}</fmt:formatNumber>) 
												<a class="vbg-btn btn-elimina" href="javascript:doHref('deleteAssegnazione.htm?codice=${varAssegnazione.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.annulla" />"> 
													<!-- <img src="${pageContext.request.contextPath}/images/cross.gif" alt="<fmt:message key="label.annulla" />" />  -->
												</a>
											</td>
										</tr>
										<%k++;%>
									</c:forEach>
								</tbody>
							</table>
						</c:if></td>
					</tr>
					<script type="text/javascript">
					arrayIdCampiDaAssegnareViewDettagli.push('causale<%=j%>#mercato<%=j%>#uso<%=j%>#posteggio<%=j%>');
     	</script>
					<%anchor++; %>
					<%counter++; %>
					<%j++; %>
				</c:if>
			</c:forEach>
			<tr id="totViewTotale" style="<%=dettaglioParziale %>" class="header">
				<td colspan="8" align="right">TOTALI</td>
				<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${totImporto}</fmt:formatNumber></td>
				<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${totRimanenza}</fmt:formatNumber></td>
				
				<td></td>
				<td></td>
				<td style="text-align: right;" colspan="2"><fmt:formatNumber minFractionDigits="2">${totIncassato}</fmt:formatNumber></td>				
				
			</tr>

			<tr id="totViewParziale" style="<%=dettaglioTotale %>" class="header">
				<td colspan="4" align="right">TOTALI</td>
				<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${totImporto}</fmt:formatNumber></td>
				<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${totRimanenza}</fmt:formatNumber></td>
				<td></td>
				<td></td>
				<td style="text-align: right;"  colspan="2"><fmt:formatNumber minFractionDigits="2">${totIncassato}</fmt:formatNumber></td>

			</tr>

		</tbody>
	</table>
	</div>

	</fieldset>
	<fieldset><legend><fmt:message key="form.registrazioniInOut.incassicompleti" /></legend> <%int k=1;%>
	<div class="jmesa">
	<table class="table" width="100%">
		<thead>
			<tr class="header">
				<td><fmt:message key="form.registrazioniFilter.progressivo" /></td>
				<td><fmt:message key="form.registrazioniFilter.registrazioniCausali" /></td>
				<td><fmt:message key="form.registrazioniFilter.mercati" /></td>

				<td><fmt:message key="form.registrazioni.mercatiUso" /></td>
				<td><fmt:message key="form.registrazioni.mercati.posteggio" /></td>
				<td><fmt:message key="form.registrazioniFilter.nrRata" /></td>
				<td><fmt:message key="form.registrazioniFilter.conto" /></td>
				<td style="text-align: right;"><fmt:message key="form.registrazioniFilter.importo" /></td>
				<td><fmt:message key="form.registrazioniFilter.dettagliIncassi" /></td>
			</tr>
		</thead>
		<tbody>
			<c:set var="totImportoIncasso" value="0.00"></c:set>
			<c:forEach items="${incassiCompleti}" var="incassatoImporto" varStatus="currentIncassato">
				<%anchor++; %>
				<c:if test="${not (incassatoImporto.rimanenza > 0)}">
					<c:set var="totImportoIncasso" value="${totImportoIncasso + incassatoImporto.importo}"></c:set>
					<tr class="<%=(k%2)==0?"odd":"even"%>">
						<td id="registrazione<%=anchor %>"><a href="#registrazione<%=anchor %>" onclick="javascript:$('popup_registrazione${currentIncassato.index}').appear();" title="Visualizza dettagli">${incassatoImporto.registrazioni.progressivo}</a>
						<div id="popup_registrazione${currentIncassato.index}" style="display: none">
						<table>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.progressivo" /></td>
								<td>${incassatoImporto.registrazioni.progressivo} <a href="#registrazione<%=anchor %>" onclick="javascript:$('popup_registrazione${currentIncassato.index}').fade();">(chiudi)</a></td>
							</tr>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.anagrafe" /></td>
								<td>${incassatoImporto.registrazioni.anagrafe.descrizioneRichiedente}</td>
							</tr>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.registrazioniCausali" /></td>
								<td>${incassatoImporto.registrazioni.registrazioniCausali.descrizione}</td>
							</tr>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.importo" /></td>
								<td><fmt:formatNumber minFractionDigits="2">${incassatoImporto.registrazioni.importo}</fmt:formatNumber></td>
							</tr>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.incassato" /></td>
								<td><fmt:formatNumber minFractionDigits="2">${incassatoImporto.registrazioni.incassato}</fmt:formatNumber></td>
							</tr>
							<tr>
								<td class="header"><fmt:message key="form.registrazioniFilter.daincassare" /></td>
								<td><fmt:formatNumber minFractionDigits="2">${incassatoImporto.registrazioni.importo - incassatoImporto.registrazioni.incassato }</fmt:formatNumber></td>
							</tr>
						</table>
						</div>
						</td>
						<td>${incassatoImporto.registrazioni.registrazioniCausali.descrizione}</td>
						<td>${incassatoImporto.registrazioni.mercatiD.mercati.descrizione}</td>
						<td>${incassatoImporto.registrazioni.mercatiUso.descrizione}</td>
						<td>${incassatoImporto.registrazioni.mercatiD.codiceposteggio}</td>
						<td>${incassatoImporto.nrRata}</td>
						<td>${incassatoImporto.conti.descrizioneConto}</td>
						<td align="right"><fmt:formatNumber minFractionDigits="2">${incassatoImporto.importo}</fmt:formatNumber></td>
						<td><c:if test="${not empty incassatoImporto.regIoAssegnazionis}">
							<table class="table" width="100%">
								<tbody class="tbody">
									<%int x=1;%>
									<c:forEach items="${incassatoImporto.regIoAssegnazionis}" var="varAssegnazione1" varStatus="currentAssegnazione1">
										<tr class="<%=(x%2)==0?"odd":"even"%>">
											<td><fmt:message key="form.registrazioniFilter.incassato" /></td>
											<td align="right"><fmt:formatNumber minFractionDigits="2">${varAssegnazione1.importo}</fmt:formatNumber></td>
											<td><fmt:message key="form.registrazioniFilter.dataIncasso" /></td>
											<td align="right"><fmt:formatDate value="${varAssegnazione1.registrazioniInOut.dataIncasso}" pattern="dd/MM/yyyy" /> 
												<a class="vbg-btn btn-elimina" href="javascript:doHref('deleteAssegnazione.htm?codice=${varAssegnazione1.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.annulla" />"> 
													<!-- <img src="${pageContext.request.contextPath}/images/cross.gif" alt="<fmt:message key="label.annulla" />" />  -->
												</a>
											</td>
										</tr>
										<%x++;%>
									</c:forEach>
								</tbody>
							</table>
						</c:if></td>
					</tr>
					<%k++;%>
				</c:if>
			</c:forEach>
			<tr class="header">
				<td colspan="7" align="right">TOTALI</td>
				<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${totImportoIncasso}</fmt:formatNumber></td>
				<td></td>
			</tr>
		</tbody>
	</table>
	</div>
	</fieldset>
	<fieldset><legend><fmt:message key="form.registrazioniInOut.totali" /></legend>
	<div class="jmesa">
	<table class="table">
		<thead class="header">
			<tr>
				<td style="text-align: right;"><fmt:message key="form.registrazioniFilter.importo" /></td>
				<td style="text-align: right;"><fmt:message key="form.registrazioniFilter.daincassare" /></td>
			</tr>
		</thead>
		<tbody>
			<tr class="odd">
				<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${totImporto+totImportoIncasso}</fmt:formatNumber></td>
				<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${totRimanenza}</fmt:formatNumber></td>
			</tr>
		</tbody>
	</table>
	</div>
	</fieldset>
	<script type="text/javascript">
		$('importo_id').focus();

		visualizzaDettagliScadenze();
		
		function visualizzaDettagliScadenze(){
			var dettagliDisplay=document.getElementById("visualizzaDettagliId");
			var id=dettagliDisplay.checked;
			var i=0;
			if(dettagliDisplay.checked){

				$('causaleForDettagli').appear();
				$('mercatiForDettagli').appear();
				$('usoForDettagli').appear();
				$('posteggioForDettagli').appear();
				for(var k=0;k<arrayIdCampiDaAssegnareViewDettagli.length;k++){
					var array=arrayIdCampiDaAssegnareViewDettagli[k].split("#");
					for(var y=0;y<array.length;y++){
						$(array[y]).appear();
					}
				}
				$('totViewParziale').fade();
				$('totViewTotale').appear();
			}else{
				$('causaleForDettagli').fade();
				$('mercatiForDettagli').fade();
				$('usoForDettagli').fade();
				$('posteggioForDettagli').fade();
				for(var k2=0;k2<arrayIdCampiDaAssegnareViewDettagli.length;k2++){
					var array2=arrayIdCampiDaAssegnareViewDettagli[k2].split("#");
					for(var y2=0;y2<array2.length;y2++){

						$(array2[y2]).fade();
					}
				}
				$('totViewTotale').fade();
				$('totViewParziale').appear();
			}
			saveUserPreference('<%= WebConstants.CONF_UTENTE_REG_IO_VISUALIZZA_DETTAGLI %>',id);			
		}
		/*
		function saveUserPreference(nomeparametro, valore){
			new Ajax.Request('../registrazioniinout/salvaPreferenza.htm', {
				  method: 'post',
				  parameters: {nomeparametro: nomeparametro,valore: valore},
				  onSuccess: function(transport){ },
				  onFailure: function(transport){ 
					var response = transport.responseText;
				    alert(response); }						    		 
				  });			
		}
		*/
		function resetAllValues(){
			var i=0;
			var id;
			var stop=0;
			while(i < arrayIdCampiDaAssegnare.length){
				stop = arrayIdCampiDaAssegnare[i].indexOf("#");
				id = arrayIdCampiDaAssegnare[i].substring(0,stop);
				$(id).value = '';
				i++;
			}
		}
		
		function setAllValues(){
			if(checkImporto()){
				var i=0;
				var value;
				var id;
				var start=0;
				var stop=0;
				while(i < arrayIdCampiDaAssegnare.length){
					stop = arrayIdCampiDaAssegnare[i].indexOf("#");
					id = arrayIdCampiDaAssegnare[i].substring(0,stop);
					value = arrayIdCampiDaAssegnare[i].substring(stop+1);
					if(!updateRimanenza(id,value)){
						break;
					}
					i++;
				}
			}
		}
		
		function setValue(id,value){
			resetCheckBoxes();
			updateRimanenza(id,value);
		}
		function resetCheckBoxes(){
			var form = document.forms[0];
			for (var i = 0; i < form.elements.length; i++ ) {
				if(form.elements[i].id){
					if (form.elements[i].type == 'checkbox') {
				        var chk = form.elements[i].id.substring(0,4);		        
			        	if(chk=='rip_'){
			        		form.elements[i].checked = false;
			        	}
			        }
				}
		  }
		}
		
		function checkImporto(){
			var _importo = $('importo_id').value;
			if(_importo.indexOf(",",0)>0){
					_importo = _importo.replace(",",".");
			}
			if(isNaN(_importo) || _importo==""){
				alert("Attenzione! Il campo importo non è numerico.");
				$('importo_id').focus();
				return false;
			}else{
				importo = parseFloat(_importo);
				if(importo <=0){
					alert("Attenzione! Il valore del campo importo deve essere maggiore di zero.");
					$('importo_id').focus();
					return false;
				}
			}
			return true;
		}
		
		function updateRimanenza(id,_value){
				//assegno alla variabile old_value il valore corrente del campo da aggiornare
				var _old_value = $(id).value;
				_old_value = _old_value.replace(",",".");
				var old_value;
				if(isNaN(_old_value)){
					old_value = _old_value;
				}else{
					old_value = parseFloat(_old_value);
				}						
				//assegno alla variabile rimanenza il valore corrente del campo rimanenza
				var _rimanenza = $('rimanenza_id').value;
				if(_rimanenza.indexOf(",",0)>0){
						_rimanenza = _rimanenza.replace(",",".");
				}
				if(isNaN(_rimanenza)){
					rimanenza = _rimanenza;
				}else{					
					rimanenza = parseFloat(_rimanenza);
				}
				//assegno alla variabile importo il valore corrente del campo importo
				var _importo = $('importo_id').value;
				if(_importo.indexOf(",",0)>0){
					_importo = _importo.replace(",",".");
				}
				if(isNaN(_importo)){
					importo = _importo;
				}else{					
					importo = parseFloat(_importo);
				}
				//assegno alla variabile new_value il nuovo valore da assegnare
				var value;
				var _value2 = _value;
				if(_value2.indexOf(",",0)>0){	
					_value2 = _value2.replace(",",".");
				}
				if(isNaN(_value2)){
					value = _value2;
				}else{	
					value = parseFloat(_value2);
				}
				var new_value = parseFloat(value);
				
				//aggiornamento dei valori dei campi
				if(importo > 0){
					if(value == 0){
						if(!isNaN(old_value)){
							rimanenza += old_value;
							var r = rimanenza.toFixed(2);			
							$('rimanenza_id').value = (""+r).replace(".",",");
							$(id).value = '';
						}else{
							$(id).value = '';
						}
						return false;
					}else{
						if(isNaN(old_value)){
							if(rimanenza < new_value){
								if(rimanenza > 0){
									var c = rimanenza.toFixed(2);									
									$(id).value = (""+c).replace(".",",");
								}else{
									alert("Il valore da assegnare è pari a "+rimanenza);
									return false;
								}
								rimanenza = 0;
								var r1 = rimanenza.toFixed(2);
								$('rimanenza_id').value = (""+r1).replace(".",",");
								return false;
							}else{
								rimanenza -= new_value;
								var c1 = new_value.toFixed(2);
								$(id).value = (""+c1).replace(".",",");							
							}
						}else{
							alert("Il valore è già stato assegnato.");
							return false;
						}
					}
					var r2 = rimanenza.toFixed(2);
					$('rimanenza_id').value = (""+r2).replace(".",",");

					return true;					
				}else{
					alert("Campo importo vuoto!");
					$('importo_id').focus();
					return false;
				}
		}

		function fillRegIOValues(obj){
			resetAllValues();
			new Ajax.Request('<%=request.getContextPath()%>/registrazioniinout/ajaxDettaglioRegistrazioniIO.htm', {
						  method: 'post',
						  parameters: {codiceRegIO: obj.options[obj.selectedIndex].value},
						  onSuccess: function(transport){
							  var response = transport.responseText;		
							  $("regIODiv").innerHTML = response;
							  applyStyle();					  
						  },
						  onFailure: function(transport){ 
							var response = transport.responseText;
						  }						    		 
			});
		}
		
		function setRimanenza(obj){
			resetAllValues();
			if(checkValue(obj)){
				if(!$('importo_hidden')){
						$('rimanenza_id').value = obj.value;		
				}else{
					if(!$('importo_hidden').value){
						$('rimanenza_id').value = obj.value;
					}
				}
			}
		}
		
		function checkValue(obj){
			if(isNaN(obj.value.replace(",","."))){
				alert('<fmt:message key="alert.field.numeric" />');
				obj.value = "";
				return false;
			}
			if(obj.value.indexOf(".",0)>0){
				obj.value = obj.value.replace(".",",");
			}
			return true;
		}

		function dettaglioRegistrazione(codice){
			var goToUrl = "../registrazioni/view.htm?codice="+codice;
			goToUrl = escape(goToUrl);
			doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'');
			
		}


		function gestImporto(obj, id, _value, idx){

			var checked = obj.checked;
			if(checked==true){
				obj.checked=true;
			}else{
				obj.checked=false;
			}
			//assegno alla variabile old_value il valore corrente del campo da aggiornare
			var _old_value = $(id).value;
			_old_value = _old_value.replace(",",".");
			var old_value;
			if(isNaN(_old_value)){
				old_value = _old_value;
			}else{
				old_value = parseFloat(_old_value);
			}						
			//assegno alla variabile rimanenza il valore corrente del campo rimanenza
			var _rimanenza = $('rimanenza_id').value;
			if(_rimanenza.indexOf(",",0)>0){
					_rimanenza = _rimanenza.replace(",",".");
			}
			if(isNaN(_rimanenza)){
				rimanenza = _rimanenza;
			}else{					
				rimanenza = parseFloat(_rimanenza);
			}
			//assegno alla variabile importo il valore corrente del campo importo
			var _importo = $('importo_id').value;
			if(_importo.indexOf(",",0)>0){
				_importo = _importo.replace(",",".");
			}
			if(isNaN(_importo)){
				importo = _importo;
			}else{					
				importo = parseFloat(_importo);
			}



			var value;
			var _value2 = _value;

			if(isNaN(_value2)){
				value = _value2;
			}else{	
				value = parseFloat(_value2);
			}

			var new_value = parseFloat(value);
			
			//aggiornamento dei valori dei campi
			if(importo <= 0){				
				alert("Campo importo vuoto!");
				obj.checked=false;
				$('importo_id').focus();
				return false;
			}

			var form = document.forms[0];
			
      		var importoTotale=0.0;

			for (var i = 0; i < form.elements.length; i++ ) {
					if(form.elements[i].id){
						var _idx = form.elements[i].id.substring(8);
						if(form.elements[i].id=='assegna_'+idx){
							$('assegna_'+idx).value='';
						}	
						if (form.elements[i].type == 'checkbox') {
					        var chk = form.elements[i].id.substring(0,4);		        
				        	if(chk=='rip_'){
					        	var _idx = form.elements[i].id.substring(4);				        	
					            if (form.elements[i].checked == true) {
					            	var importoRata = $('da_assegnare_'+_idx).value;				            	
					            	importoTotale = parseFloat(importoTotale)+parseFloat(importoRata); 
					            }
				        	}
				        }
					}
			  }

			var rimanenza = importo;
			for (var i = 0; i < form.elements.length; i++ ) {
		        if (form.elements[i].type == 'checkbox') {
			        var chk = form.elements[i].id.substring(0,4);		        
		        	if(chk=='rip_'){
			        	var _idx = form.elements[i].id.substring(4);				        	
			            if (form.elements[i].checked == true) {
			            	var importoRata = $('da_assegnare_'+_idx).value;				            	
							var percentuale = (importoRata*100) / importoTotale;
							var importoFinaleRata = (importo/100) * percentuale;
							if(importoFinaleRata>importoRata){
								$('assegna_'+_idx).value=(""+importoRata).replace(".",",");
								rimanenza -= importoRata; 
							}else{
								var r1 = importoFinaleRata.toFixed(2);								
								$('assegna_'+_idx).value=(""+r1).replace(".",",");
								rimanenza -= r1;
			            	}					
			            }
		        	}
		        }
		  }
			var rimanenzaArrotondata = rimanenza.toFixed(2);
			$('rimanenza_id').value = (""+rimanenzaArrotondata).replace(".",",");

		}
		
</script>

</spring-form:form></div>

<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('updateScadenze.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>



</body>
</html>