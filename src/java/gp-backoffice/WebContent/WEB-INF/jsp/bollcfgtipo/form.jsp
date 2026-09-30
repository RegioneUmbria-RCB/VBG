<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ImplementazioniEnum"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${bollcfgtipo.id.codice==null}">
			<fmt:message key="bollcfgtipo.label.nuovo_bollcfgtipo.title" />
		</c:if> 
		<c:if test="${bollcfgtipo.id.codice!=null}">
			<fmt:message key="bollcfgtipo.label.dettaglio_bollcfgtipo.title" />
		</c:if>
	</title>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
	<style media="all">
		.funzioni-dettaglio {
			margin: 0;
			padding: 0;
			list-style-type: none;
			width: 100%;
		}
		
		.funzioni-dettaglio>li {
			display: inline-block;
			margin-right: 16px;
			cursor: pointer;
			color: #b61218;
		}
		
		.btn-foot {
			text-align: right !important;
		}
		
		#dettaglioSchede {
			padding: 16px 0 16px 0;
		}
		
		#functions ul {
			padding: 16px 0 16px 0;
		}
		
		.scadenze-fisse>p {
			display: inline;
			padding-left: var(- -half-padding);
		}
		
		.listaSchede a:link, .listaSchede a:visited {
			background: #E8EBF0;
			border: 1px solid var(--form-element-border-color);
			color: var(--text-color);
			font-size: small;
			font-weight: normal;
			line-height: 14px;
			text-decoration: none;
			border-radius: 5px 5px 0px 0px;
		}
		
		.readonly {
			width: 100%;
			pointer-events: none;
    		border: none;
    		background: transparent;
		}
		
		.help-metadati {
			padding-left: 5px;
		}
	</style>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${bollcfgtipo.id.codice==null}">
			<fmt:message key="bollcfgtipo.label.nuovo_bollcfgtipo.title" />
		</c:if> 
		<c:if test="${bollcfgtipo.id.codice!=null}">
			<fmt:message key="bollcfgtipo.label.dettaglio_bollcfgtipo.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="bollcfgtipo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="bollcfgtipo" />
		    </jsp:include>
		    <div class="vbg-form">
		    	<fieldset>
					<legend>Tipo bollettazione</legend>
					<div class="form-group">
						<label>* <fmt:message key="bollcfgtipo.label.descrizione" /></label>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />
						<spring-form:errors path="descrizione" cssClass="error"/>
					</div>
					<div class="form-group">
						<label for="raggrUtenza_id"><fmt:message key="bollcfgtipo.label.flagRaggruppaUtenza" /></label>
						<spring-form:checkbox id="raggrUtenza_id" path="flagRaggruppaUtenza" />
						<div class="input-help"><fmt:message key="manifestazione.help.flagRaggruppaUtenza" /></div>					
						<spring-form:errors path="flagRaggruppaUtenza" cssClass="error" />
					</div>
					<!-- ARROTONDAMENTO -->
					<div class="form-group">
						<label><fmt:message key="bollcfgtipo.label.arrotondamento" /></label>
						<spring-form:select path="arrotondamento" id="arrotondamentoId" >
							<spring-form:option value=""><fmt:message key='label.seleziona'/>...</spring-form:option>
							<c:forEach items="${arrotondamentiCustom}" var="arrotondamento">
				    			<spring-form:option value="${arrotondamento.key}">${arrotondamento.value}</spring-form:option>
				    		</c:forEach>
							</spring-form:select> 									
						<spring-form:errors path="arrotondamento" cssClass="error" />
					</div>
					<div class="form-group">
						<label><fmt:message key="bollcfgtipo.label.implementazione" /></label>				
						<c:if test="${bollcfgtipo.id.codice==null}">
							<spring-form:select path="implementazione" id="implementazioniId" items="${implementazioni}" onchange="viewFlag()">
							</spring-form:select> 
							<spring-form:errors path="implementazione" cssClass="error" />					
						</c:if>
						<c:if test="${bollcfgtipo.id.codice!=null}">
							<spring-form:input id="implementazioniId" path="implementazione" size="30" readonly="true"/>
						</c:if>								
					</div>
					<div class="form-group">
						<label><fmt:message key="bollcfgtipo.label.tipiscadenza" /></label>
						<c:if test="${bollcfgtipo.id.codice==null}">
							<spring-form:select path="fkTipiscadenzaId.id" id="tipiscadenzaId"
									items="${tipiScadenza}" itemLabel="descrizione"
									itemValue="id" onchange="mostraScadenzePeriodicheFisse()"></spring-form:select> <spring-form:errors
									path="fkTipiscadenzaId" cssClass="error" />					
						</c:if>
						<c:if test="${bollcfgtipo.id.codice!=null}">
							<spring-form:input id="tipiscadenzaId" path="fkTipiscadenzaId.descrizione" size="30" readonly="true"/>					
						</c:if>								
					</div>				
					<div class="form-group">
						<label><fmt:message key="bollcfgtipo.label.periodo" /></label>
						<c:if test="${bollcfgtipo.id.codice==null}">
							<spring-form:select path="periodo" id="periodiId"
							items="${periodi}" onchange="mostraScadenzePeriodicheFisse()"></spring-form:select> <spring-form:errors
							path="periodo" cssClass="error" />					
						</c:if>
						<c:if test="${bollcfgtipo.id.codice!=null}">
							<spring-form:input id="periodiId" path="periodo" size="30" readonly="true"/>					
						</c:if>					
					</div>
					<c:if test="${bollcfgtipo.id.codice==null}">
						<div class="form-group scadenze-fisse">
							<label>Scadenza periodo</label>
							<spring-form:input id="scadenzePeriodi_id" path="scadenzePeriodi" size="3" onchange="validaPeriodo()"/>
							<spring-form:errors id="scadenzePeriodi_id_err" path="scadenzePeriodi" cssClass="error" />	
							<c:if test="${bollcfgtipo.id.codice!=null}">
								<spring-form:input id="scadenzePeriodi_id" path="scadenzePeriodi" size="3" readonly="true"/>
							</c:if>
						</div>
					</c:if>
					<c:if test="${bollcfgtipo.id.codice != null and bollcfgtipo.scadenzePeriodi != null}">
						<div class="form-group scadenze-fisse">
							<label>Scadenza periodo</label>						
							<spring-form:input id="scadenzePeriodi_id" path="scadenzePeriodi" size="3" readonly="true"/>						
						</div>
					</c:if>
					<!-- Flag ignora subentri -->				
					<c:if test="${bollcfgtipo.implementazione eq 'Mercati' || bollcfgtipo.id.codice==null}">	
					<div class="form-group rigamercati">
						<label><fmt:message key="bollcfgtipo.label.ignorasubentri" /></label>
						<spring-form:checkbox id="ignSub_id" path="flagIgnorasubentri" />
						<div class="input-help"><fmt:message key="bollcfgtipo.help.ignorasubentri" /></div>
						<spring-form:errors	path="flagIgnorasubentri" cssClass="error" />
					</div>
					<div class="form-group rigamercati">
						<label><fmt:message key="bollcfgtipo.label.titolarita_pagamenti" /></label>
						<c:if test="${bollcfgtipo.id.codice==null}">
							<spring-form:select path="titolaritaPagamenti" id="titolaritaPagamentiId"
							items="${titolaritaPagamenti}" itemValue="codice" itemLabel="descrizione" onchange=""></spring-form:select> <spring-form:errors
							path="titolaritaPagamenti" cssClass="error" />				
						</c:if>
						<c:if test="${bollcfgtipo.id.codice != null}">
							<c:forEach items="${titolaritaPagamenti}" var="tit">
								<c:if test="${tit.codice eq bollcfgtipo.titolaritaPagamenti }">
									${ tit.descrizione }
									<spring-form:hidden id="titolaritaPagamentiId" path="titolaritaPagamenti"/>
								</c:if>
							</c:forEach>				
						</c:if>					
					</div>	
					</c:if>	
					<!-- Flag richiesta fattura -->				
					<div class="form-group">
						<label for="flgRichFatt_id"><fmt:message key="bollcfgtipo.label.richiestafattura" /></label>
						<spring-form:checkbox id="flgRichFatt_id" path="flagRichiestaFattura" />
						<div class="input-help"><fmt:message key="bollcfgtipo.help.richiestafattura" /></div>
						<spring-form:errors	path="flagRichiestaFattura" cssClass="error" />
					</div>
					<div class="form-group">
						<label for="flagCaricamentoMassivo_id"><fmt:message key="bollcfgtipo.label.caricamento_massivo_nodo_pagamenti" /></label>
						<spring-form:checkbox id="flagCaricamentoMassivo_id" path="flagCaricamentoMassivo" />
						<div class="input-help"><fmt:message key="bollcfgtipo.label.caricamento_massivo_nodo_pagamenti.help" /></div>
						<spring-form:errors	path="flagCaricamentoMassivo" cssClass="error" />
					</div>
					<div class="form-group">
						<label><fmt:message key="stampe.codice" /></label>
						<jsp:include page="../includes/autocompletergenericoTT.jsp" >
							<jsp:param name="idElemento" value="letteraAccompagnamento" />		
							<jsp:param name="propertyPath" value="letteraAccompagnamento" />				
							<jsp:param name="pathPropertyDescription" value="letteraAccompagnamento.descrizione" />
							<jsp:param name="pathPropertyCode" value="letteraAccompagnamento.id.codice" />
							<jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />	
							<jsp:param name="titleKey" value="label.ricerca_tipo_lettera" />
							<jsp:param name="id_help" value="help_letteraTipo" />
							<jsp:param name="help" value="help.search_archivi_base" />
						</jsp:include>
						<fmt:message key="help.ricerca_per_software_TT"/>					
					</div>				
				</fieldset>
				<c:if test="${bollcfgtipo.id.codice != null}">
				<fieldset>
					<legend><fmt:message key="bollcfgtipo.label.rateizzazione"/></legend>
					<table class="vbg-table" id="table-rate">
						<thead>
							<tr>								
								<th width="40%"><fmt:message key="label.descrizione"/></th>
								<th width="20%"><fmt:message key="bollcfgtipo.label.rangebasso"/></th>
								<th width="20%"><fmt:message key="bollcfgtipo.label.rangealto"/></th>
								<th width="20%"><fmt:message key="label.azioni"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${bollCfgTipoRate}" var="rate">	
							<tr class="riga-rata_${rate.id.codice}">							
								<td data-descrizione="${rate.rangeRateizzazioni.tiporateizzazione.descrizione}">${rate.rangeRateizzazioni.tiporateizzazione.descrizione}</td>
								<td data-rangeBasso="${rate.rangeRateizzazioni.rangeBasso}">${rate.rangeRateizzazioni.rangeBasso}</td>
								<td data-rangeAlto="${rate.rangeRateizzazioni.rangeAlto}">${rate.rangeRateizzazioni.rangeAlto}</td>
								<td>
									<ul class="funzioni-dettaglio" data-id="${rate.id.codice}">
										<li class="azione cmd-elimina">				
											<i class="fa fa-trash-o"></i>
											<fmt:message key="label.elimina" />
										</li>
									</ul>
								</td>
							</tr>
							</c:forEach>						
						</tbody>
					</table>
					<div class="btn-foot">
					 	<a class="cmd-aggiungi" href="javascript:void(0)">
                           <i class="fa fa-plus-circle"></i>
                           <fmt:message key="label.aggiungi" />
                       </a>
					</div>
				</fieldset>
				</c:if>
			</div>
			<div class="form-button">
				<c:if test="${bollcfgtipo.id.codice==null}">
					<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
				</c:if>
				<c:if test="${bollcfgtipo.id.codice!=null}">
					<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
					<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
				</c:if>
				<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>
			</div>
			
			<br />
			
			<!-- SCHEDE RUOLI, MERCATI, CAUSALI ONERI -->
			<c:if test="${bollcfgtipo.id.codice!=null}">
			<div id="subcontent">	
				<div id="navigation">					
					<ul class="listaSchede">					
						<li id="schedaRuoli_id"><a href="#" title="<fmt:message key="label.ruoli" />" ><fmt:message key="label.ruoli" /></a></li>	
						<li id="schedaMercati_id"><a href="#" title="<fmt:message key="label.mercati" />"><fmt:message key="label.mercati" /></a></li>	
						<li id="schedaCO_id" ><a href="#" title="<fmt:message key="label.causalioneri" />" ><fmt:message key="label.causalioneri" /></a></li>  			            
						<li id="schedaConti_id" ><a href="#" title="<fmt:message key="button.conti" />" ><fmt:message key="button.conti" /></a></li>
						<li id="schedaAltriDati_id"><a href="#" title="<fmt:message key="label.altri_dati" />" ><fmt:message key="label.altri_dati" /></a></li>
					</ul>
				</div>				
				<div id="dettaglioSchede" >
				</div>					
			</div>	
			</c:if>			 
		</spring-form:form>
	</div>		
	<vbg-modal id="vbg-modal-gestisci-rata" >
		<div slot='body' class='vbg-modal-body'>
			<h1></h1>
			<div class="vbg-form">
				<div class="form-group">
			        <label><fmt:message key="label.descrizione" /></label>
					<select id="descrizione_rata_id">
						<c:forEach items="${rateizzazioni}" var="rate">
							<option value="${rate.id.codice }">${rate.tiporateizzazione.descrizione }</option>
						</c:forEach>
					</select>
			    </div>
			    <div class="form-group">
			        <label><fmt:message key="bollcfgtipo.label.rangebasso"/></label>			        
			       <input id="rangeBasso_id" readonly />
			    </div>
			    <div class="form-group">
			        <label><fmt:message key="bollcfgtipo.label.rangealto"/></label>
			       <input id="rangeAlto_id" readonly/>
			    </div>
			</div>
		
		</div>
		<div slot="footer" class="vbg-modal-footer">
			 <a href="javascript:void(0)" id="bottone_modifica" class='bottone-salvataggio btn btn-primary'><fmt:message key="button.update" /></a>             
             <a href="javascript:void(0)" id="bottone_inserisci" class='bottone-salvataggio btn btn-primary'><fmt:message key="button.insert" /></a>
             <a href="#" data-role='toggle-popup' class="btn btn-secondary btnChiudi" ><fmt:message key="button.back" /></a>
		</div>
	</vbg-modal> 
	
	<jsp:include page="./funzioniJS.jsp" />
	 
</body>
</html>