<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.Anagrafe"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.domain.web.AnagrafeCommand" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/><title><fmt:message key="anagrafe.label.dettaglio_anagrafe.title"/></title>
</head>
<body>
<style>.bgred{background:red;}</style>
    <c:set var="personaGiuridicaval" value="<%= WebConstants.PERSONA_GIURIDICA %>" scope="request"/>
    <c:set var="personaFisicaval" value="<%= WebConstants.PERSONA_FISICA %>" scope="request"/>
    <% 
      	// SETTA LO STYLE INIZIALE 
    	// 1- Se l'anagrafe scelto è tecnico,mostra la sezione albo
    	// 2- Se non è tecnico la tiene nascosta
    	String displaySezioneAlbo="";
    	if(!request.getAttribute("isTecnico").toString().equals("-1"))
    	{
			 displaySezioneAlbo = "display:none;";
    	}
    	AnagrafeCommand ac = (AnagrafeCommand)request.getAttribute("anagrafe");
    	Anagrafe a = ac.getEntity();
    	if(StringUtils.isNotBlank(a.getNumeroelencopro()) || 
    			StringUtils.isNotBlank(a.getProvinciaelencopro()) ||
    			( a.getElenchiprofessionalibase()!=null && a.getElenchiprofessionalibase().getId()!=null )){
    		displaySezioneAlbo = "";
    	}
	%> 
	<span class="titoloPagina"><fmt:message key="anagrafe.label.dettaglio_anagrafe.title"/></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../anagrafe/view"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="anagrafe" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
		        <jsp:param name="commandName" value="anagrafe"/>
		    </jsp:include>

<jsp:include page="./form_js.jsp" />

            <%-- DATI GENERALI START --%>
			<c:set var="VERTICALIZZAZIONE_WSANAGRAFE_REQUEST"><%=request.getAttribute(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE)%></c:set>
			<c:set var="ESCLUDI_RICERCA_PER_PF_REQUEST"><%=request.getAttribute(WebConstants.ESCLUDI_RICERCA_PER_PF)%></c:set>			
			<table width="100%">
			<c:if test="${anagrafe.entity.flagDisabilitato eq 1}">
				<tr><td style="color: red;">Utente disabilitato dal <fmt:formatDate value="${anagrafe.entity.dataDisabilitato}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/> </td></tr>
			</c:if>
<c:if test="${anagrafe.entity.id.codice == null}">	
	<c:if test="${VERTICALIZZAZIONE_WSANAGRAFE_REQUEST eq true}">
		<%-- GESTIONE FUNZIONALITà RICERCA ANAGRAFICA DA STSIMA ESTERNO PER PERS. FISICA --%>
		<%--Per la persona fisica eiste in verticalizzazione un ulteriorte parametro che deve essere attivato
		    affinchè la funzionalità di ricerca da un sistema inteno sia attiva --%>
		<c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval && ESCLUDI_RICERCA_PER_PF_REQUEST eq 1 }">
		<tr class="titoloSezione"><td colspan="6"><fmt:message key="anagrafe.label.ricerca_dal_ws"/></td></tr>		
		<tr>
			<td width="30%"><fmt:message key="label.codice_fiscale"/></td>
			<td colspan="5">
				<spring-form:input id="cfPivaRicercaWs_id" path="cfPivaRicercaWs" size="20" maxlength="16" cssStyle="float: left;"/>
				<span id="functions">
					<ul style="margin-top: -2px;">											
						<li><a href="javascript:doSubmit('${anagrafe.prefixPopup}popolaDatiDaWs.htm','',document.inviodati)"><fmt:message key="button.richiedi_dati"/></a></li>
					</ul>
				</span>		
			</td>
		</tr>		
		</c:if>
		<%-- GESTIONE FUNZIONALITà RICERCA ANAGRAFICA DA STSIMA ESTERNO PER PERS. GIURIDICA --%>
		<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
		<tr class="titoloSezione"><td colspan="6"><fmt:message key="anagrafe.label.ricerca_dal_ws"/></td></tr>
		<tr>	
			<td width="30%"><fmt:message key="label.partita_iva"/>/<fmt:message key="label.codice_fiscale"/></td>
			<td colspan="5">
				<spring-form:input id="cfPivaRicercaWs_id" path="cfPivaRicercaWs" size="20" maxlength="16" cssStyle="float: left;"/>
				<span id="functions">
					<ul style="margin-top: -2px;">											
						<li><a href="javascript:doSubmit('${anagrafe.prefixPopup}popolaDatiDaWs.htm','',document.inviodati)"><fmt:message key="button.richiedi_dati"/></a></li>
					</ul>
				</span>		
			</td>
		</c:if>	
		</tr>
	</c:if>	
			</c:if>
			<tr class="titoloSezione"><td colspan="6"><fmt:message key="anagrafe.label.dati_generali"/></td></tr>		
				<tr>
					<td style="min-width:200px;"><fmt:message key="anagrafe.label.tipo_anagrafe"/></td>
					<c:if test="${anagrafe.entity.id.codice==null}">
					<td colspan="5" class="inline-ui-cell">
					    <c:if test="${anagrafe.popupCaller != ''}">
					    	<c:if test="${anagrafe.visualizzaTipoAnagrafe eq '' || anagrafe.visualizzaTipoAnagrafe eq personaFisicaval}">
						    	<spring-form:radiobutton id="id_fisica" path="entity.tipoanagrafe" value="<%=WebConstants.PERSONA_FISICA%>" onclick="mostraNascondiEls(id,'${anagrafe.visualizzaTipoAnagrafe}')"  />
						    	<label for="id_fisica"><fmt:message key="label.persona_fisica"/></label>
					    	</c:if>
					    	<c:if test="${anagrafe.visualizzaTipoAnagrafe eq '' || anagrafe.visualizzaTipoAnagrafe eq personaGiuridicaval}">
						    	<spring-form:radiobutton id="id_giuridica" path="entity.tipoanagrafe" value="<%=WebConstants.PERSONA_GIURIDICA%>" onclick="mostraNascondiEls(id,'${anagrafe.visualizzaTipoAnagrafe}')"  />
						    	<label for="id_giuridica"><fmt:message key="label.persona_giuridica"/></label>	 
					    	</c:if>   
							<spring-form:errors path="entity.tipoanagrafe" cssClass="error"/>
						</c:if>
						<c:if test="${anagrafe.popupCaller == ''}">
							<spring-form:radiobutton id="id_fisica" path="entity.tipoanagrafe" value="<%=WebConstants.PERSONA_FISICA%>" onclick="mostraNascondiEls(id,'')"  />
					    	<label for="id_fisica"><fmt:message key="label.persona_fisica"/></label>
					    	<spring-form:radiobutton id="id_giuridica" path="entity.tipoanagrafe" value="<%=WebConstants.PERSONA_GIURIDICA%>" onclick="mostraNascondiEls(id,'')"  />
					    	<label for="id_giuridica"><fmt:message key="label.persona_giuridica"/></label>	    
							<spring-form:errors path="entity.tipoanagrafe" cssClass="error"/>
						</c:if>
					</td>
					</c:if>
					<c:if test="${anagrafe.entity.id.codice!=null}">
					<td colspan="5" class="inline-ui-cell">
					   <c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
					     <input type="text" value="<fmt:message key="label.persona_fisica"/>" readonly="readonly" size="30"/>
					     <c:if test="${isConversioneAnagrafica eq true && anagrafe.entity.flagDisabilitato eq 0}">
					     	<label><a class="linkConversioneFisicaGiuridica" href="javascript:doHref('../anagrafe/covertTipoAnagrafe.htm?codice=${anagrafe.entity.id.codice}','')"><fmt:message key="label.persona_fisica_to_giuridica"/></a></label>
					     </c:if>
					   </c:if>
					   <c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
					     <input type="text" value="<fmt:message key="label.persona_giuridica"/>" readonly="readonly" size="30"/>
					     <c:if test="${isConversioneAnagrafica eq true }">
					     	<label ><a class="linkConversioneFisicaGiuridica" href="javascript:doHref('../anagrafe/covertTipoAnagrafe.htm?codice=${anagrafe.entity.id.codice}','')"><fmt:message key="label.persona_giuridica_to_fisica"/></a></label>
					     </c:if>
					   </c:if>
					</td>
					</c:if>
				</tr>				
				<%-- VISUALIZZATA SE SCELTO PERSONAFISICA 
				 START --%>
			    <c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval }">
				<tr>
					<td><fmt:message key="label.cognome"/></td>
					<td>
						<c:if test="${anagrafe.entity.nominativo eq anagrafe.oldAnagrafe.nominativo || anagrafe.entity.nominativo==null}">
							<spring-form:input id="nominativo_id" path="entity.nominativo" size="30"/>
						</c:if>
						<c:if test="${anagrafe.entity.nominativo ne anagrafe.oldAnagrafe.nominativo && anagrafe.entity.nominativo!=null }">
							<spring-form:input cssClass="bgred" id="nominativo_id" path="entity.nominativo" size="30" onclick="gda('nominativo_modificatoOverlay_id')"/>
							<input id="id_nuovo_nominativo" type="hidden" value="${anagrafe.oldAnagrafe.nominativo}" name="oldAnagrafe.nominativo"></input>
							<jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.nominativo}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.nominativo}"/>
							   <jsp:param value="nominativo_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="nominativo_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="nominativo_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_nominativo" name="oldfieldId"/>
							</jsp:include>	
						</c:if>
						<spring-form:errors path="entity.nominativo" cssClass="error"/>
					</td>
					<td><fmt:message key="label.nome"/></td>
					<td colspan="3">
						<c:if test="${anagrafe.entity.nome eq anagrafe.oldAnagrafe.nome || anagrafe.entity.nome==null}">
							<spring-form:input id="nome_id" path="entity.nome" size="30"/>
						</c:if>
						<c:if test="${anagrafe.entity.nome ne anagrafe.oldAnagrafe.nome && anagrafe.entity.nome!=null }">
							<spring-form:input cssClass="bgred" id="nome_id" path="entity.nome" size="30" onclick="gda('nome_modificatoOverlay_id')"/>
							<input id="id_nuovo_nome" type="hidden" value="${anagrafe.oldAnagrafe.nome}" name="oldAnagrafe.nome"></input>
							<jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.nome}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.nome}"/>
							   <jsp:param value="nome_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="nome_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="nome_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_nome" name="oldfieldId"/>
							</jsp:include>	
						</c:if>
						<spring-form:errors path="entity.nome" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<%	
				    // SERVE PER GESTIRE IL CHECKBOX,
				    // SE IL CAMPO FLAG NO PROFIT SARà UGUALE A true  DOVRà MOSTRALO SELEZIONATO
				    // SE IL CAMPO FLAG NO PROFIT SARà UGUALE A false DOVRà MOSTRALO NON SELEZIONATO
				    String showCheckedNoProfit="";
					if(((Boolean) request.getAttribute("flagNoProfit")!=null))
					{
						if ((Boolean) request.getAttribute("flagNoProfit")==true) {
						    showCheckedNoProfit = "checked='checked'";
		    			} else {
		    				showCheckedNoProfit = "";
						}
					}
				%>				
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
				<tr>
					<td><fmt:message key="label.ragione_sociale"/></td>
					<td>						
						<c:if test="${anagrafe.entity.nominativo eq anagrafe.oldAnagrafe.nominativo || anagrafe.entity.nominativo==null}">
							<spring-form:input id="ragione_sociale_id" path="entity.nominativo" size="60"/>
						</c:if>
						<c:if test="${anagrafe.entity.nominativo ne anagrafe.oldAnagrafe.nominativo && anagrafe.entity.nominativo!=null }">
							<spring-form:input cssClass="bgred" id="ragione_sociale_id" path="entity.nominativo" size="60" onclick="gda('ragione_sociale_modificatoOverlay_id')"/>
							<input id="id_nuovo_ragione_sociale" type="hidden" value="${anagrafe.oldAnagrafe.nominativo}" name="oldAnagrafe.nominativo"></input>
							<jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.nominativo}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.nominativo}"/>
							   <jsp:param value="ragione_sociale_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="ragione_sociale_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="ragione_sociale_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_ragione_sociale" name="oldfieldId"/>
							</jsp:include>	
						</c:if>
						<spring-form:errors path="entity.nominativo" cssClass="error"/>
					</td>
					<td colspan="4">
						<c:if test="${anagrafe.entity.flagNoprofit == anagrafe.oldAnagrafe.flagNoprofit}">
							<spring-form:checkbox id="flag_no_profit_id" path="entity.flagNoprofit"  />
						</c:if>
						<c:if test="${anagrafe.entity.flagNoprofit != anagrafe.oldAnagrafe.flagNoprofit}">
					    	<input type="checkbox" id="flag_no_profit_id" <%=showCheckedNoProfit%> style="outline-color:red;outline-style:solid;outline-width:thin;"  name="entity.flagNoprofit"  value="true" onmouseover="javascript:gda('flag_no_profit_dialog');"/>
					    	<input type="hidden" id="id_nuovo_flag_no_profit"  name=""  value="${anagrafe.oldAnagrafe.flagNoprofit}"/>
							<div id="flag_no_profit_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="flag_no_profit_dialogInner" class="dialog">
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato"/></td>
									</tr>
									<tr>
									    <c:if test="${anagrafe.entity.flagNoprofit==true}">
								    		<td  class="parametri"><fmt:message key="label.si"/></td>
								    	</c:if>
								    	<c:if test="${anagrafe.entity.flagNoprofit==false}">
								    		<td  class="parametri"><fmt:message key="label.no"/></td>
								    	</c:if>
									</tr>
									<tr>
								    	<td><fmt:message key="label.vecchio_valore_associato"/></td>
									</tr>
									<tr>
										<c:if test="${anagrafe.oldAnagrafe.flagNoprofit==true}">
									       <td  class="parametri"><fmt:message key="label.si"/></td>
									    </c:if>
									    <c:if test="${anagrafe.oldAnagrafe.flagNoprofit==false}">
									       <td  class="parametri"><fmt:message key="label.no"/></td>
									    </c:if>
									</tr>
									<tr>
									    <td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe"/></td>
									</tr>
					   				</table>
					   				<div id="functions">
										<ul>
											<li><a href="#" onclick="riprChkbox('flag_no_profit_id','id_nuovo_flag_no_profit','flag_no_profit_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accChkbox('flag_no_profit_id','flag_no_profit_dialog')"><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
						</c:if>	
						<spring-form:errors path="entity.flagNoprofit" cssClass="error"/>
						<fmt:message key="label.flag_no_profit"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.forma_giuridica"/></td>
					<td colspan="5">
					<c:if test="${anagrafe.entity.formagiuridica.id.codice == anagrafe.oldAnagrafe.formagiuridica.id.codice || anagrafe.entity.formagiuridica.id.codice==null }">
						<spring-form:input id="formagiuridica_id" path="entity.formagiuridica.formagiuridica" cssClass="searchbox" onchange="checkValue(this,'formagiuridica_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
						<init:autocompleter methodAjax="findFormegiuridiche.htm" idHidden="formagiuridica_hidden" idInput="formagiuridica_id" inputTitleKey="label.ricerca_forma_giuriche"></init:autocompleter>
						<spring-form:errors path="entity.formagiuridica" cssClass="error"/> 
						<spring-form:hidden id="formagiuridica_hidden" path="entity.formagiuridica.id.codice"  />
					</c:if>
					<c:if test="${anagrafe.entity.formagiuridica.id.codice != anagrafe.oldAnagrafe.formagiuridica.id.codice && anagrafe.entity.formagiuridica.id.codice!=null }">
						<spring-form:input id="formagiuridica_id" path="entity.formagiuridica.formagiuridica" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'formagiuridica_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gda('formagiuridica_modificatoOverlay_id')"  size="40"/>
						<init:autocompleter methodAjax="findFormegiuridiche.htm" idHidden="formagiuridica_hidden" idInput="titolo_id" inputTitleKey="label.ricerca_forma_giuriche"></init:autocompleter>
						<spring-form:errors path="entity.formagiuridica" cssClass="error"/> 
						<spring-form:hidden id="formagiuridica_hidden" path="entity.formagiuridica.id.codice"  />
						<input type="hidden" id="id_nuovo_formagiuridica" name="oldAnagrafe.formagiuridica.id.codice"  value="${anagrafe.oldAnagrafe.formagiuridica.id.codice}"  />
						<jsp:include page="../anagrafe/formModificaCampi.jsp">
								<jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.formagiuridica.formagiuridica}"/>
								<jsp:param name="nuovocampo" value="${anagrafe.entity.formagiuridica.formagiuridica}"/>
								<jsp:param value="formagiuridica_modificatoOverlay_id" name="id_div_overlay"/>
								<jsp:param value="formagiuridica_modificatoInner_id" name="id_div_inner"/>
								<jsp:param value="formagiuridica_id" name="fieldId"/>
								<jsp:param value="id_nuovo_formagiuridica" name="oldfieldId"/>
						</jsp:include>	
					</c:if>
					</td>
				</tr>
				</c:if>
				<%-- END
				VISUALIZZATA SE SCELTO PERSONAGIURIDICA --%>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
				<tr>
					<td><fmt:message key="label.titolo"/></td>
					<td colspan="5">
					<c:if test="${anagrafe.entity.titolo.id.codice == anagrafe.oldAnagrafe.titolo.id.codice || anagrafe.entity.titolo.id.codice==null }">
						<spring-form:input id="titolo_id" path="entity.titolo.titolo" cssClass="searchbox" onchange="checkValue(this,'titolo_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
						<init:autocompleter methodAjax="findTitoli.htm" idHidden="titolo_hidden" idInput="titolo_id" inputTitleKey="label.ricerca_titoli"></init:autocompleter>
						<spring-form:errors path="entity.titolo" cssClass="error"/> 
						<spring-form:hidden id="titolo_hidden" path="entity.titolo.id.codice"  />
					</c:if>
					<c:if test="${anagrafe.entity.titolo.id.codice != anagrafe.oldAnagrafe.titolo.id.codice && anagrafe.entity.titolo.id.codice!=null }">
						<spring-form:input id="titolo_id" path="entity.titolo.titolo" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'titolo_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gda('titolo_modificatoOverlay_id')"  size="40"/>
						<init:autocompleter methodAjax="findTitoli.htm" idHidden="titolo_hidden" idInput="titolo_id" inputTitleKey="label.ricerca_titoli"></init:autocompleter>
						<spring-form:errors path="entity.titolo" cssClass="error"/> 
						<spring-form:hidden id="titolo_hidden" path="entity.titolo.id.codice"  />
						<input type="hidden" id="id_nuovo_titolo" name="oldAnagrafe.titolo.id.codice"  value="${anagrafe.oldAnagrafe.titolo.id.codice}"  />
						<jsp:include page="../anagrafe/formModificaCampi.jsp">
								<jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.titolo.titolo}"/>
								<jsp:param name="nuovocampo" value="${anagrafe.entity.titolo.titolo}"/>
								<jsp:param value="titolo_modificatoOverlay_id" name="id_div_overlay"/>
								<jsp:param value="titolo_modificatoInner_id" name="id_div_inner"/>
								<jsp:param value="titolo_id" name="fieldId"/>
								<jsp:param value="id_nuovo_titolo" name="oldfieldId"/>
						</jsp:include>	
					</c:if>	
					</td>
				</tr>
				</c:if>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval }">
				<tr>
					<td><fmt:message key="label.sesso"/></td>
					<td colspan="5">					
						<c:if test="${anagrafe.entity.sesso eq anagrafe.oldAnagrafe.sesso || anagrafe.entity.sesso==null}">
							<spring-form:select id="sesso_id" path="entity.sesso">
								<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
								<spring-form:option value="<%=WebConstants.MASCHIO%>"><fmt:message key="label.maschio"/></spring-form:option>
								<spring-form:option value="<%=WebConstants.FEMMINA%>"><fmt:message key="label.femmina"/></spring-form:option>
							</spring-form:select>							
						</c:if>
						<c:if test="${anagrafe.entity.sesso ne anagrafe.oldAnagrafe.sesso && anagrafe.entity.sesso!=null }">							
							<spring-form:select id="sesso_id" path="entity.sesso" cssClass="bgred" onclick="gda('sesso_modificatoOverlay_id')">
								<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
								<spring-form:option value="<%=WebConstants.MASCHIO%>"><fmt:message key="label.maschio"/></spring-form:option>
								<spring-form:option value="<%=WebConstants.FEMMINA%>"><fmt:message key="label.femmina"/></spring-form:option>
							</spring-form:select>																				
							<input id="id_nuovo_sesso" type="hidden" value="${anagrafe.oldAnagrafe.sesso}" name="oldAnagrafe.sesso"></input>
							<jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.sesso}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.sesso}"/>
							   <jsp:param value="sesso_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="sesso_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="sesso_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_sesso" name="oldfieldId"/>
							</jsp:include>	
						</c:if>
						<spring-form:errors path="entity.sesso" cssClass="error"/>
					</td>
				</tr>
			    </c:if>
			    <c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
			    <tr>
					<td><fmt:message key="label.cittadinanza"/></td>
					<td colspan="5">
					<c:if test="${anagrafe.entity.cittadinanza.codice == anagrafe.oldAnagrafe.cittadinanza.codice || anagrafe.entity.cittadinanza.codice==null }">
						<spring-form:input id="cittadinanza_id" path="entity.cittadinanza.cittadinanza" cssClass="searchbox" onchange="checkValue(this,'cittadinanza_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="22"/>
						<init:autocompleter methodAjax="findcittadinanze.htm" idHidden="cittadinanza_hidden" idInput="cittadinanza_id" inputTitleKey="label.ricerca_cittadinanza"></init:autocompleter>
						<spring-form:errors path="entity.cittadinanza" cssClass="error"/> 
						<spring-form:hidden id="cittadinanza_hidden" path="entity.cittadinanza.codice"  />
					</c:if>
					<c:if test="${anagrafe.entity.cittadinanza.codice != anagrafe.oldAnagrafe.cittadinanza.codice && anagrafe.entity.cittadinanza.codice!=null }">
						<spring-form:input id="cittadinanza_id" path="entity.cittadinanza.cittadinanza" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'cittadinanza_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gda('cittadinanza_modificatoOverlay_id')"  size="22"/>
							<init:autocompleter methodAjax="findcittadinanze.htm" idHidden="cittadinanza_hidden" idInput="cittadinanza_id" inputTitleKey="label.ricerca_cittadinanza"></init:autocompleter>
							<spring-form:errors path="entity.cittadinanza" cssClass="error"/> 
							<spring-form:hidden id="cittadinanza_hidden" path="entity.cittadinanza.codice"/>
							<input type="hidden" id="id_nuovo_cittadinanza" name="oldAnagrafe.cittadinanza.cittadinanza"  value="${anagrafe.oldAnagrafe.cittadinanza.cittadinanza}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.cittadinanza.cittadinanza}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.cittadinanza.cittadinanza}"/>
							   <jsp:param value="cittadinanza_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="cittadinanza_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="cittadinanza_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_cittadinanza" name="oldfieldId"/>
							</jsp:include>	
					</c:if>
					</td>
				</tr>
				</c:if>
				<%	
				    // SERVE PER GESTIRE IL CHECKBOX,
				    // SE IL CAMPO TIPOLOGIA SARà UGUALE A -1 DOVRà MOSTRALO SELEZIONATO
				    // SE IL CAMPO TIPOLOGIA SARà UGUALE A 0 DOVRà MOSTRALO NON SELSZIONATO
				    String showChecked1="";
					if(((Integer) request.getAttribute("tipologia")!=null))
					{
						if ((Integer) request.getAttribute("tipologia")==-1) {
		   					showChecked1 = "checked='checked'";
		    			} else {
							showChecked1 = "";
						}
					}
				%>
				<tr>
					<td><fmt:message key="label.tipo_soggetto"/></td>
					<td colspan="5">
					<c:if test="${anagrafe.entity.tipologia == anagrafe.oldAnagrafe.tipologia}">
						<spring-form:checkbox id="tipologia_id" path="entity.tipologia" value="-1" onclick="mostraNascondiSezioneAlbo()"/>
					</c:if>	
					<c:if test="${anagrafe.entity.tipologia != anagrafe.oldAnagrafe.tipologia}">
					    <input type="checkbox" id="tipologia_id" <%=showChecked1%> style="outline-color:red;outline-style:solid;outline-width:thin;"  name="entity.tipologia"  value="-1" onmouseover="javascript:gda('tipologia_dialog');" onclick="mostraNascondiSezioneAlbo()"/>
					    <input type="hidden" id="id_nuovo_tipologia"  name=""  value="${anagrafe.oldAnagrafe.tipologia}"/> 
						<div id="tipologia_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="tipologia_dialogInner" class="dialog">
									<table width="100%">
									<tr>
								    	<td><fmt:message key="label.nuovo_valore_associato"/></td>
									</tr>
									<tr>
									    <c:if test="${anagrafe.entity.tipologia==-1}">
								    		<td  class="parametri"><fmt:message key="label.tecnico"/></td>
								    	</c:if>
								    	<c:if test="${anagrafe.entity.tipologia==0}">
								    		<td  class="parametri"><fmt:message key="label.non_tecnico"/></td>
								    	</c:if>
									</tr>
									<tr><td><fmt:message key="label.vecchio_valore_associato"/></td></tr>
									<tr>
										<c:if test="${anagrafe.oldAnagrafe.tipologia==-1}">
									       <td  class="parametri"><fmt:message key="label.tecnico"/></td>
									    </c:if>
									    <c:if test="${anagrafe.oldAnagrafe.tipologia==0}">
									       <td  class="parametri"><fmt:message key="label.non_tecnico"/></td>
									    </c:if>
									</tr>
									<tr><td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe"/></td></tr>
					   				</table>
					   				<div id="functions"><ul>
											<li><a href="#" onclick="ripristinaCheckboxTipologia('tipologia_id','id_nuovo_tipologia','tipologia_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accettaCheckboxTipologia('tipologia_id','tipologia_dialog')"><fmt:message key="button.accetta"/></a></li>
										</ul></div>
								</div>	
							</div>
					</c:if>	
						<spring-form:errors path="entity.tipologia" cssClass="error"/>
						<fmt:message key="tipimovimento.label.descrizione_tipo_soggetto"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.password"/></td>
					<td colspan="6">
					<spring-form:input id="password_clear_id" path="entity.passwordClear" size="8" cssStyle="float: left;"/>
					<spring-form:errors path="entity.passwordClear" cssClass="error"/>
						<div id="functions" style="display: inline;">
							<ul style="margin-top: -2px;">
							    <li><a href="javascript:nuovaPassword();"><fmt:message key="button.genera_password"/></a></li>
							    
								    <c:if test="${anagrafe.entity.id.codice!=null}">
								    <%-- STAMPA RICEVUTA --%>
									<%pageContext.setAttribute("URL_STAMPA",BackofficeNETConstants.getURL_STAMPA_RICHIEDENTI_DOCUMENTI_TIPO());%>
									<%-- la password è settata dalla funzione stampaRicevuta a runtime recuperandola dal form quindi utilizzo un segnaposto per la sostituzione--%>
									<c:set var="_URL_STAMPA_RICEVUTA" value="${URL_STAMPA}?CodiceAnagrafe=${anagrafe.entity.id.codice}&Doc_base=SCHEDAANAGRAFE.RTF&Password=SEGNAPOSTO"/>
									<c:set var="_URL_STAMPA_RICEVUTA" value="${inite:geturlto(pageContext.request, _URL_STAMPA_RICEVUTA, _urlback, null, true)}"/>
								    <%
								    try{
								    	// devo eseguire l'encoding perchè quando passo l'url alla funzione js stampaRicevuta questa esegue il decode!
										String encS = java.net.URLEncoder.encode((String)pageContext.getAttribute("_URL_STAMPA_RICEVUTA"),"UTF-8");
								    	pageContext.setAttribute("_URL_STAMPA_RICEVUTA", encS);
								    }catch(Exception e){}
								    %>
								    <li><a href="javascript:stampaRicevuta('${_URL_STAMPA_RICEVUTA}');"><fmt:message key="button.stampa"/></a></li>	  
								    </c:if>
							      <c:if test="${isPasswordSet=='1'}">
							    	<c:if test="${CAN_UPDATE eq true }">
							    		<li><a href="javascript:doHref('resetPassword.htm?codice=${anagrafe.entity.id.codice}','',document.inviodati)"><fmt:message key="button.reset_password"/></a></li>
							     	</c:if>
								</c:if>
							</ul>
					    </div>
					    <fmt:message key="label.descrizione_password_anagrafica"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.password_md5"/></td>
					<td>
						<spring-form:input id="password_id" path="entity.password" size="50" disabled="true"/>
					</td>					
				</tr>
				
				
				
				<jsp:include page="datiLocalizzativi.jsp" />
				
				
				
				<%-- GESTIONE DELL'INDIRIZZO DI DATINASCITA/SEDE LEGALE START --%>	
				 <%
					String displayDatinascita_azienda= "display:none;";
					String styleDatinascita_azienda = "";
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ANAGRAFE_DATI_NASCITA_O_DATI_AZIENDA)).equals("1")) {
					    displayDatinascita_azienda = "";
					    styleDatinascita_azienda="sezioneDatiMeno";
					} else {
					    displayDatinascita_azienda = "display:none;";
					    styleDatinascita_azienda="sezioneDatiPiu";
					}
				%>				
				<%--  DESCRIZIONE TITOLO --%>
			    <c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
			    <tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleDatinascita_azienda%>" id="id_link_dati_nascita_azienda" href="javascript:showHidePanel('id_nascita_az_tab', 'id_link_dati_nascita_azienda', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_DATI_NASCITA_O_DATI_AZIENDA %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione"/> <fmt:message key="label.dati_nascita_e_codice_fiscale"/>">
							<label for="id_link_dati_nascita_azienda"> <fmt:message key="label.dati_nascita_e_codice_fiscale"/></label>
						</a>
					</td>
				</tr>
				</c:if>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
				<tr class="titoloSezione">
					<td colspan="6">
						<a class="<%=styleDatinascita_azienda%>" id="id_link_dati_nascita_azienda" href="javascript:showHidePanel('id_nascita_az_tab', 'id_link_dati_nascita_azienda', '<%= WebConstants.CONF_UTENTE_ANAGRAFE_DATI_NASCITA_O_DATI_AZIENDA %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione"/> <fmt:message key="label.dati_azienda"/>">
							<label for="id_link_dati_nascita_azienda"><fmt:message key="label.dati_azienda"/></label>
						</a>
					</td>
				</tr>
				</c:if>
				<%-- CAMPI DATI AZIENDA (PERSONA GIURIDICA) --%>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
				<tr id="id_nascita_az_tab" style="<%=displayDatinascita_azienda%>;">
			    	<td><fmt:message key="label.data_costituzione"/></td>
					<td>
					<fmt:formatDate value="${anagrafe.entity.datanominativo}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_nominativo"/>
					<fmt:formatDate value="${anagrafe.oldAnagrafe.datanominativo}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_nominativo_old"/>
					<c:if test="${(data_nominativo == data_nominativo_old) || data_nominativo==null}">
						<spring-form:input id="data_costituzione_id" path="entity.datanominativo" size="8"  onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatacostituzione" idInput="data_costituzione_id" textKey="label.calendar"/>
					</c:if>
					<c:if test="${(data_nominativo != data_nominativo_old) && data_nominativo!=null}">
						<spring-form:input id="data_costituzione_id" path="entity.datanominativo" cssClass="bgred" size="8" onclick="gda('data_costituzione_modificato_dialog')"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatacostituzione" idInput="data_costituzione_id" textKey="label.calendar"/>
					    <spring-form:hidden path="oldAnagrafe.datanominativo" id="id_nuovo_datacostituzione"/>
							<div id="data_costituzione_modificato_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="data_costituzione_modificato_dialogInner" class="dialog">
									<table width="100%">
									<tr><td><fmt:message key="label.nuovo_valore_associato"/></td></tr>
									<tr><td  class="parametri"> <fmt:formatDate value="${anagrafe.entity.datanominativo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr><td><fmt:message key="label.vecchio_valore_associato"/></td></tr>
									<tr><td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.datanominativo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr><td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe"/></td></tr>
					   				</table>
					   				<div id="functions">
										<ul>
										    <li><a href="#" onclick="riprChkbox('data_costituzione_id','id_nuovo_datacostituzione','data_costituzione_modificato_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accetta('data_costituzione_id','data_costituzione_modificato_dialog')"><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
					</c:if>
						<spring-form:errors path="entity.datanominativo" cssClass="error"/>
					</td>
					<td><fmt:message key="label.data_inizio_attivita"/></td>
<td colspan="3">
<fmt:formatDate value="${anagrafe.entity.dataInizioAttivita}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_inizio_attivita"/>
<fmt:formatDate value="${anagrafe.oldAnagrafe.dataInizioAttivita}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_inizio_attivita_old"/>
<c:if test="${(data_inizio_attivita == data_inizio_attivita_old) || data_inizio_attivita==null}">
	<spring-form:input id="data_inizio_attivita_id" path="entity.dataInizioAttivita" size="8"  onblur="isValidDate(this,true);"/>
	<init:calendar imagePath="/images/cal.gif" idImage="cal_data_inizio_attivita" idInput="data_inizio_attivita_id" textKey="label.calendar"/>
</c:if>
<c:if test="${(data_inizio_attivita != data_inizio_attivita_old) && data_inizio_attivita!=null}">
<spring-form:input id="data_inizio_attivita_id" path="entity.dataInizioAttivita"  cssClass="bgred" size="8" onclick="gda('data_inizio_attivita_modificato_dialog')"/>
<init:calendar imagePath="/images/cal.gif" idImage="cal_data_inizio_attivita" idInput="data_inizio_attivita_id" textKey="label.calendar"/>
   <spring-form:hidden path="oldAnagrafe.dataInizioAttivita" id="id_nuovo_data_inizio_attivita"/>
	<div id="data_inizio_attivita_modificato_dialog" style="display: none;" dojoType="dijit.Dialog">
		<div id="data_inizio_attivita_modificato_dialogInner" class="dialog">
			<table width="100%">
			<tr><td><fmt:message key="label.nuovo_valore_associato"/></td></tr>
			<tr><td  class="parametri"> <fmt:formatDate value="${anagrafe.entity.dataInizioAttivita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
			</tr>
			<tr><td><fmt:message key="label.vecchio_valore_associato"/></td></tr>
			<tr><td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.dataInizioAttivita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
			</tr>
			<tr><td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe"/></td></tr>
  				</table>
  				<div id="functions">
				<ul>
				    <li><a href="#" onclick="riprChkbox('data_inizio_attivita_id','id_nuovo_data_inizio_attivita','data_inizio_attivita_modificato_dialog')"><fmt:message key="button.rifiuta"/></a></li>
					<li><a href="#" onclick="accetta('data_inizio_attivita_id','data_inizio_attivita_modificato_dialog')"><fmt:message key="button.accetta"/></a></li>
				</ul>
			</div>
		</div>	
	</div>
</c:if>						
<spring-form:errors path="entity.dataInizioAttivita" cssClass="error"/>
</td>
				</tr>
				</c:if>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaFisicaval}">
				<tr id="id_nascita_az_tab" style="<%=displayDatinascita_azienda%>;">
					<td><fmt:message key="label.comune_nascita"/></td>
					<td > 
					<c:if test="${(anagrafe.entity.comuneNascita.codicecomune eq anagrafe.oldAnagrafe.comuneNascita.codicecomune) || anagrafe.entity.comuneNascita.codicecomune==null }">
						<spring-form:input id="comune_nascita_id" path="entity.comuneNascita.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comune_nascita_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="28"/>
						<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_nascita_hidden" idInput="comune_nascita_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
						<spring-form:errors path="entity.comuneNascita" cssClass="error"/> 
						<spring-form:hidden id="comune_nascita_hidden" path="entity.comuneNascita.codicecomune"  />
					</c:if>
					<c:if test="${(anagrafe.entity.comuneNascita.codicecomune ne anagrafe.oldAnagrafe.comuneNascita.codicecomune) && anagrafe.entity.comuneNascita.codicecomune!=null }">
							<spring-form:input id="comune_nascita_id" path="entity.comuneNascita.descrizioneEstesa" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'comune_nascita_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gda('comunenascita_modificatoOverlay_id')"  size="28"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_nascita_hidden" idInput="comune_nascita_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comuneNascita" cssClass="error"/> 
							<spring-form:hidden id="comune_nascita_hidden" path="entity.comuneNascita.codicecomune"  />
							<input type="hidden" id="id_nuovo_comunenascita" name="oldAnagrafe.comuneNascita.comune"  value="${anagrafe.oldAnagrafe.comuneNascita.comune}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.comuneNascita.comune}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.comuneNascita.comune}"/>
							   <jsp:param value="comunenascita_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="comunenascita_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="comune_nascita_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_comunenascita" name="oldfieldId"/>
							</jsp:include>
					</c:if>				
					</td>
					<td><fmt:message key="label.data_nascita"/></td>
					<td colspan="3">
					<fmt:formatDate value="${anagrafe.entity.datanascita}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_nascita"/>
					<fmt:formatDate value="${anagrafe.oldAnagrafe.datanascita}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_nascita_old"/>
					<c:if test="${(data_nascita eq data_nascita_old)  || data_nascita==null}">
						<spring-form:input id="data_nascita_id" path="entity.datanascita" size="8" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatanascita" idInput="data_nascita_id" textKey="label.calendar"/>
					</c:if>
					<c:if test="${(data_nascita ne data_nascita_old) && data_nascita ne null}">
						<spring-form:input  id="data_nascita_id" path="entity.datanascita" cssClass="bgred" onclick="javascript:gda('data_nascita_modificato_dialog');" onblur="isValidDate(this,true);" size="8"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatanascita" idInput="data_nascita_id" textKey="label.calendar"/>
						<spring-form:hidden path="oldAnagrafe.datanascita" id="id_nuovo_datanascita"/>
							<div id="data_nascita_modificato_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="data_nascita_modificato_dialogInner" class="dialog">
									<div style="width: 100%" align="right">
										<a title="<fmt:message key="button.back"/>" href="javascript:gda('data_nascita_modificato_dialog');"><img src="${pageContext.request.contextPath }/images/cross.gif" border="0"/></a>
									</div>
									<table width="100%">
									<tr><td><fmt:message key="label.nuovo_valore_associato"/></td></tr>
									<tr><td  class="parametri"> <fmt:formatDate value="${anagrafe.entity.datanascita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr><td><fmt:message key="label.vecchio_valore_associato"/></td></tr>
									<tr><td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.datanascita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr><td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe"/></td></tr>
					   				</table>
					   				<div id="functions">
										<ul>
										    <li><a href="#" onclick="ripristina('data_nascita_id','id_nuovo_datanascita','data_nascita_modificato_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accetta('data_nascita_id','data_nascita_modificato_dialog')"><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
					</c:if>
						<spring-form:errors path="entity.datanascita" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<tr id="id_dati_cf_table" style="<%=displayDatinascita_azienda%>;">
					<td>
						<c:choose>
							<c:when test="${anagrafe.entity.tipoanagrafe=='F'}">
								<fmt:message key="label.codice_fiscale"/>
							</c:when>
							<c:otherwise>
								<fmt:message key="label.codice_fiscale_impresa"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td class="inline-ui-cell" style="min-width:200px;">
					    <c:set  scope="page" value="${anagrafe.entity.codicefiscale != null}" var="isCFPresente"></c:set>
						<c:choose>
							<c:when test="${anagrafe.entity.codicefiscale eq anagrafe.oldAnagrafe.codicefiscale || (empty anagrafe.entity.codicefiscale and empty  anagrafe.oldAnagrafe.codicefiscale)}">
								<spring-form:input id="codice_fiscale_id" path="entity.codicefiscale" size="24" readonly="${isCFPresente}"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssClass="bgred" id="codice_fiscale_id" path="entity.codicefiscale" size="24" onclick="gda('codicefiscale_modificatoOverlay_id')" readonly="${isCFPresente}"/>
								<input id="id_nuovo_codicefiscale" type="hidden" value="${anagrafe.oldAnagrafe.codicefiscale}" name="oldAnagrafe.codicefiscale"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.codicefiscale}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.codicefiscale}"/>
								   <jsp:param value="codicefiscale_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="codicefiscale_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="codice_fiscale_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_codicefiscale" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.codicefiscale" cssClass="error"/> 
						<c:if test="${isCFPresente }">
							 <c:if test="${CAN_UPDATE eq true }">
								<a class="vbg-btn btn-modifica" id="link_mod_cf_id" href="javascript:attivaMoficacampoCF();" title="<fmt:message key="label.modifica.record"/> ${albocategorie_var.id.codice}">
								</a>
							    <a class="vbg-btn btn-salva"  id="link_save_cf_id" style="display: none;"  href="javascript:updateCF();"
	                               title="<fmt:message key="label.salva"/> ">
							    </a>
						   </c:if>
						</c:if>
						<script type="text/javascript">
                            function attivaMoficacampoCF()
                            {  jQuery("#codice_fiscale_id").attr("readonly", false);
                               jQuery("#link_mod_cf_id").hide();
                               jQuery("#link_save_cf_id").show();
                            }
                            function updateCF(){
                            	var _cf = jQuery('#codice_fiscale_id').val();
                    			var jhqrPr = jQuery.ajax({
                    				  url: 'ajaxUpdateCF.htm',
                    				  context: document.body,
                    				  data: "codiceAnagrafe=${anagrafe.entity.id.codice}&cf="+_cf,
                    				  cache: false,					  
                    				  dataType: "html",
                    				  success: function(data){
                    					 dijit.showTooltip(data, dojo.byId('codice_fiscale_id'));
  										 setTimeout(function(){dijit.hideTooltip(dojo.byId('codice_fiscale_id'))},1000);
  									    },
	  									failure: function(data){
	  									  console.error(data);
	  									  alert(data);
	  									}
                    			});
                    			jQuery("#link_mod_cf_id").show();
                                jQuery("#link_save_cf_id").hide();
                                jQuery("#codice_fiscale_id").attr("readonly", true);
                    		}
						</script>
					</td>					
					<td id="functions" class="inline-ui-cell" colspan="4" style="min-width:200px;">
						<ul>
							<c:if test="${anagrafe.entity.tipoanagrafe=='F'}">
							<li><a href="javascript:calcolaCodicefiscale();"><fmt:message key="button.genera_codice_fiscale"/></a></li>
							</c:if>
								<c:if test="${VERTICALIZZAZIONE_WSANAGRAFE_REQUEST eq true}">
									<c:if test="${anagrafe.entity.id.codice!=null}">
										<c:if test="${ anagrafe.entity.tipoanagrafe eq 'G' or (anagrafe.entity.tipoanagrafe eq personaFisicaval && ESCLUDI_RICERCA_PER_PF_REQUEST eq 1 )}">
												<li><a href="javascript:controlloAggiornamentiAnagrafeByCF();"><fmt:message key="button.controlla_aggiornamenti"/></a></li>
	<script type="text/javascript">
	function controlloAggiornamentiAnagrafeByCF()
	{
		if(document.getElementById('codice_fiscale_id').value!='')
		{
			javascript:doSubmit('${anagrafe.prefixPopup}controlloAggiornamentiAnagrafeByCF.htm','',document.inviodati);
		}else
		{
			alert('Codice Fiscale non presente')
		}
	}
	</script>
										</c:if>		
								    </c:if>
							    </c:if>
						</ul>
					</td>
				</tr>
				<c:if test="${anagrafe.entity.tipoanagrafe=='F'}">
					<tr  id="id_dati_nascita_cf_table"" style="<%=displayDatinascita_azienda%>;">
						<td class="titoloSottoSezione" colspan="6">
							<fmt:message key="label.impresa_individuale"/>
							(<label style="font-size:13px;"><fmt:message key="label.descrizione_impresa_individuale"/></label>
							 <init:help idHelp="impresa_individuale_help_id" textKey="anagrafe.help.impresa_individuale"/>)
						</td>
					</tr>
				</c:if>
				<tr id="id_dati_pi_table" style="<%=displayDatinascita_azienda%>;">		
					<td><fmt:message key="label.partita_iva"/></td>
					<td class="inline-ui-cell">
						<c:set  scope="page" value="${anagrafe.entity.partitaiva !=null}" var="isPIPresente"></c:set>
						<c:choose> 
							<c:when test="${anagrafe.entity.partitaiva eq anagrafe.oldAnagrafe.partitaiva || (empty anagrafe.entity.partitaiva and empty  anagrafe.oldAnagrafe.partitaiva)}">
								<spring-form:input id="partita_iva_id" path="entity.partitaiva" size="24" readonly="${isPIPresente}"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssClass="bgred" id="partita_iva_id" path="entity.partitaiva" size="24" onclick="gda('partitaiva_modificatoOverlay_id')" readonly="${isPIPresente}"/>
								<input id="id_nuovo_partitaiva" type="hidden" value="${anagrafe.oldAnagrafe.partitaiva}" name="oldAnagrafe.partitaiva"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.partitaiva}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.partitaiva}"/>
								   <jsp:param value="partitaiva_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="partitaiva_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="partita_iva_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_partitaiva" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.partitaiva" cssClass="error"/> 
						<c:if test="${isPIPresente }">
						   <c:if test="${CAN_UPDATE eq true }">
								<a class="vbg-btn btn-modifica" id="lk_md_id" href="javascript:attivaMoficacampo();" title="<fmt:message key="label.modifica.record"/> ${albocategorie_var.id.codice}">
								</a>
							<a class="vbg-btn btn-salva" id="lk_sv_id" style="display: none;"  href="javascript:updatePI();"
	                               title="<fmt:message key="label.salva"/> ">
							   </a>
						   </c:if>
						</c:if>
<script type="text/javascript">
  function attivaMoficacampo()
  {
     jQuery("#partita_iva_id").attr("readonly", false);
     jQuery("#lk_md_id").hide();
     jQuery("#lk_sv_id").show();
  }
             function updatePI(){
             	var pIva = jQuery('#partita_iva_id').val();
     			var jhqrPr = jQuery.ajax({
     				  url: 'ajaxUpdatePI.htm',
     				  context: document.body,
     				  data: "codiceAnagrafe=${anagrafe.entity.id.codice}&pi="+pIva,
     				  cache: false,					  
     				  dataType: "html",
     				  success: function(data){
      					 dijit.showTooltip(data, dojo.byId('partita_iva_id'));
					 setTimeout(function(){dijit.hideTooltip(dojo.byId('partita_iva_id'))},1000);
					 },
					failure: function(transport){
					  console.error(data);
					  alert(data);
					}
     			});
       			jQuery("#lk_md_id").show();
                   jQuery("#lk_sv_id").hide();
                   jQuery("#partita_iva_id").attr("readonly", true);
       		}
</script>
						
					</td>
					<c:if test="${anagrafe.entity.tipoanagrafe=='G'}">
					<td id="functions" class="inline-ui-cell"  colspan="4">
						<c:if test="${anagrafe.entity.id.codice!=null}">
							<c:if test="${VERTICALIZZAZIONE_WSANAGRAFE_REQUEST eq true}">
								<ul>
									<li><a href="javascript:controlloAggiornamentiAnagrafeByPI()"><fmt:message key="button.controlla_aggiornamenti"/></a></li>
								</ul>
<script type="text/javascript">
function controlloAggiornamentiAnagrafeByPI()
{
	if(document.getElementById('partita_iva_id').value!='')
	{
		javascript:doSubmit('${anagrafe.prefixPopup}controlloAggiornamentiAnagrafeByPI.htm','',document.inviodati);
	}else
	{
		alert('Partita IVA non presente')
	}
}
</script>
							</c:if>		
						</c:if>	
					</td>
					</c:if>
				</tr>
				<tr id="id_dati_regditte_table" style="<%=displayDatinascita_azienda%>;">
					<td><fmt:message key="anagrafe.label.reg_ditte"/></td>
					<td class="inline-ui-cell" style="min-width: 300px;"> 
						<c:choose>
							<c:when test="${anagrafe.entity.regditte eq anagrafe.oldAnagrafe.regditte || (empty anagrafe.entity.regditte and empty  anagrafe.oldAnagrafe.regditte)}">
								<spring-form:input id="reg_ditte_id" path="entity.regditte" size="24"/>
					        </c:when>
					        <c:otherwise>
								<spring-form:input id="reg_ditte_id" cssClass="bgred" path="entity.regditte" onclick="gda('regditte_modificatoOverlay_id')"  size="24"/>
								<input id="id_nuovo_regditte" type="hidden" value="${anagrafe.oldAnagrafe.regditte}" name="oldAnagrafe.regditte"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.regditte}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.regditte}"/>
								   <jsp:param value="regditte_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="regditte_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="reg_ditte_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_regditte" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>	
						<spring-form:errors path="entity.regditte" cssClass="error"/></td>
					<td class="inline-ui-cell" style="max-width: 150px;"><fmt:message key="label.data"/></td>
					<td class="inline-ui-cell" colspan="3">
					    <fmt:formatDate value="${anagrafe.entity.dataregditte}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_regditte"/>
					    <fmt:formatDate value="${anagrafe.oldAnagrafe.dataregditte}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_regditte_old"/>
						<c:if test="${(data_regditte == data_regditte_old) || data_regditte==null}">
							<spring-form:input id="data_reg_ditte_id" path="entity.dataregditte" size="8" onblur="isValidDate(this,true);"/>
							<init:calendar imagePath="/images/cal.gif" idImage="caldatarefgditte" idInput="data_reg_ditte_id" textKey="label.calendar"/>
						</c:if>
						<c:if test="${(data_regditte != data_regditte_old) && data_regditte!=null}">
						<spring-form:input id="data_reg_ditte_id" path="entity.dataregditte" cssClass="bgred" size="8" onclick="gda('dataregditte_modificatoOverlay_id')" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatarefgditte" idInput="data_reg_ditte_id" textKey="label.calendar"/>
					    <spring-form:hidden path="oldAnagrafe.dataregditte" id="id_nuovo_dataregditte"/>
						   <div id="dataregditte_modificatoOverlay_id" style="display: none;" dojoType="dijit.Dialog">
								<div id="dataregditte_modificatoInner_id" class="dialog">
									<table width="100%">
									<tr><td><fmt:message key="label.nuovo_valore_associato"/></td></tr>
									<tr><td  class="parametri"><fmt:formatDate value="${anagrafe.entity.dataregditte}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr><td><fmt:message key="label.vecchio_valore_associato"/></td></tr>
									<tr><td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.dataregditte}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr><td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe"/></td></tr>
					   				</table>
					   				<div id="functions">
										<ul>
										    <li><a href="#" onclick="ripristina('data_reg_ditte_id','id_nuovo_dataregditte','dataregditte_modificatoOverlay_id')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accetta('data_reg_ditte_id','dataregditte_modificatoOverlay_id')"><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
						</c:if>
						<spring-form:errors path="entity.dataregditte" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_dati_comune_regditte_table" style="<%=displayDatinascita_azienda%>;">
					<td><fmt:message key="label.comune_reg_ditte"/></td>
					<td colspan="5">
					    <c:if test="${(anagrafe.entity.comunecomregditte.codicecomune eq anagrafe.oldAnagrafe.comunecomregditte.codicecomune) || anagrafe.entity.comunecomregditte.codicecomune==null }">
							<spring-form:input id="comune_reg_ditte_id" path="entity.comunecomregditte.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comune_reg_ditte_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="28"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_reg_ditte_hidden" idInput="comune_reg_ditte_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comunecomregditte" cssClass="error"/> 
							<spring-form:hidden id="comune_reg_ditte_hidden" path="entity.comunecomregditte.codicecomune"  />
						</c:if>
						<c:if test="${(anagrafe.entity.comunecomregditte.codicecomune ne anagrafe.oldAnagrafe.comunecomregditte.codicecomune) && anagrafe.entity.comunecomregditte.codicecomune!=null }">
							<spring-form:input id="comune_reg_ditte_id" path="entity.comunecomregditte.descrizioneEstesa" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'comunecorrispondenza_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gda('comunecomregditte_modificatoOverlay_id')"  size="28"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_reg_ditte_hidden" idInput="comune_reg_ditte_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comunecomregditte" cssClass="error"/> 
							<spring-form:hidden id="comune_reg_ditte_hidden" path="entity.comunecomregditte.codicecomune"  />
							<input type="hidden" id="id_nuovo_comunecomregditte" name="oldAnagrafe.comunecomregditte.comune"  value="${anagrafe.oldAnagrafe.comunecomregditte.comune}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.comunecomregditte.comune}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.comunecomregditte.comune}"/>
							   <jsp:param value="comunecomregditte_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="comunecomregditte_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="comune_reg_ditte_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_comunecomregditte" name="oldfieldId"/>
							</jsp:include>
						</c:if>			
					</td>
				</tr>
			    <%-- VISUALIZZATA SE SCELTO PERSONAGIURIDICA
				START --%>
				<c:if test="${anagrafe.entity.tipoanagrafe eq personaGiuridicaval}">
				<tr id="id_dati_reg_trib_table" style="<%=displayDatinascita_azienda%>;">
					<td><fmt:message key="label.reg_trib"/></td>
					<td class="inline-ui-cell" style="min-width: 300px;">
					   <c:choose>
							<c:when test="${anagrafe.entity.regtrib eq anagrafe.oldAnagrafe.regtrib || (empty anagrafe.entity.regtrib and empty  anagrafe.oldAnagrafe.regtrib)}">
								<spring-form:input id="reg_trib_id" path="entity.regtrib" size="18"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input id="reg_trib_id" cssClass="bgred" path="entity.regtrib" onclick="gda('regtrib_modificatoOverlay_id')"  size="18"/>
								<input id="id_nuovo_reg_trib" type="hidden" value="${anagrafe.oldAnagrafe.regtrib}" name="oldAnagrafe.numeroelencopro"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.regtrib}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.regtrib}"/>
								   <jsp:param value="regtrib_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="regtrib_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="reg_trib_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_reg_trib" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
					   </c:choose>    
					   <spring-form:errors path="entity.regtrib" cssClass="error"/>
					</td>
					<td class="inline-ui-cell" style="max-width: 150px;"><fmt:message key="label.data"/></td>
					<td class="inline-ui-cell" colspan="3">
					    <fmt:formatDate value="${anagrafe.entity.dataregtrib}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_regtrib"/>
					    <fmt:formatDate value="${anagrafe.oldAnagrafe.dataregtrib}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_regtrib_old"/>
						<c:if test="${(data_regtrib == data_regtrib_old) || data_regtrib==null}">
							<spring-form:input id="data_reg_trib_id" path="entity.dataregtrib" size="8"/>
							<init:calendar imagePath="/images/cal.gif" idImage="caldataregtrib" idInput="data_reg_trib_id" textKey="label.calendar"/>
						</c:if>
						<c:if test="${(data_regtrib != data_regtrib_old) && data_regtrib!=null}">
							<spring-form:input id="data_reg_trib_id" path="entity.dataregtrib" cssClass="bgred" size="8" onclick="gda('dataregtrib_dialog')"/>
							<init:calendar imagePath="/images/cal.gif" idImage="caldataregtrib" idInput="data_reg_trib_id" textKey="label.calendar"/>
					    	<spring-form:hidden path="oldAnagrafe.dataregtrib" id="id_nuovo_dataregtrib"/>
							<div id="dataregtrib_dialog" style="display: none;" dojoType="dijit.Dialog">
								<div id="dataregtrib_dialogInner" class="dialog">
									<table width="100%">
									<tr><td><fmt:message key="label.nuovo_valore_associato"/></td></tr>
									<tr><td  class="parametri"> <fmt:formatDate value="${anagrafe.entity.dataregtrib}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr><td><fmt:message key="label.vecchio_valore_associato"/></td></tr>
									<tr><td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.dataregtrib}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr><td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe"/></td></tr>
					   				</table>
					   				<div id="functions">
										<ul>
										    <li><a href="#" onclick="ripristina('data_reg_trib_id','id_nuovo_dataregtrib','dataregtrib_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accetta('data_reg_trib_id','dataregtrib_dialog')"><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
						</c:if>
						<spring-form:errors path="entity.dataregtrib" cssClass="error"/>
					</td>
				</tr>
				<tr id="id_dati_comune_reg_trib_table" style="<%=displayDatinascita_azienda%>;">
					<td><fmt:message key="label.comune_reg_trib"/></td>
					<td colspan="5">
						<c:if test="${(anagrafe.entity.comuneregtrib.codicecomune eq anagrafe.oldAnagrafe.comuneregtrib.codicecomune) || anagrafe.entity.comuneregtrib.codicecomune==null }">
						<spring-form:input id="comune_reg_trib_id" path="entity.comuneregtrib.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'comune_reg_trib_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="28"/>
						<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_reg_trib_hidden" idInput="comune_reg_trib_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
						<spring-form:errors path="entity.comuneregtrib" cssClass="error"/> 
						<spring-form:hidden id="comune_reg_trib_hidden" path="entity.comuneregtrib.codicecomune"/>
						</c:if>
						<c:if test="${(anagrafe.entity.comuneregtrib.codicecomune ne anagrafe.oldAnagrafe.comuneregtrib.codicecomune) && anagrafe.entity.comuneregtrib.codicecomune!=null }">
							<spring-form:input id="comune_reg_trib_id" path="entity.comuneregtrib.descrizioneEstesa" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'comune_reg_trib_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gda('comuneregtrib_modificatoOverlay_id')"  size="28"/>
							<init:autocompleter methodAjax="findComuni.htm" idHidden="comune_reg_trib_hidden" idInput="comune_reg_trib_id" inputTitleKey="label.ricerca_comune"></init:autocompleter>
							<spring-form:errors path="entity.comuneregtrib" cssClass="error"/> 
							<spring-form:hidden id="comune_reg_trib_id" path="entity.comuneregtrib.comune"  />
							<input type="hidden" id="id_nuovo_comuneregtrib" name="oldAnagrafe.comuneregtrib.comune"  value="${anagrafe.oldAnagrafe.comuneregtrib.comune}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.comuneregtrib.comune}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.comuneregtrib.comune}"/>
							   <jsp:param value="comuneregtrib_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="comuneregtrib_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="comune_reg_trib_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_comuneregtrib" name="oldfieldId"/>
							</jsp:include>
						</c:if>			
					</td>
				</tr>
				<tr id="id_dati_provincia_table" style="<%=displayDatinascita_azienda%>;">
					<td><fmt:message key="label.provincia_area"/></td>
					<td colspan="5">					
						 <c:choose>
							<c:when test="${anagrafe.entity.provinciarea eq anagrafe.oldAnagrafe.provinciarea || (empty anagrafe.entity.provinciarea and empty  anagrafe.oldAnagrafe.provinciarea)}">
								<spring-form:input id="provincia_provinciarea_id" path="entity.provinciarea" size="2"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssClass="bgred" id="provincia_provinciarea_id" path="entity.provinciarea" size="2" onclick="gda('provinciarea_modificatoOverlay_id')"/>
								<input id="id_nuovo_provinciarea" type="hidden" value="${anagrafe.oldAnagrafe.provinciarea}" name="oldAnagrafe.provinciarea"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.provinciarea}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.provinciarea}"/>
								   <jsp:param value="provinciarea_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="provinciarea_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="provincia_provinciarea_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_provinciarea" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>
						<spring-form:errors path="entity.provinciarea" cssClass="error"/>					
					</td>
				</tr>	
				<tr id="id_dati_numrea_table" style="<%=displayDatinascita_azienda%>;">
					<td><fmt:message key="label.numero_iscrizione_rea"/></td>
					<td class="inline-ui-cell" style="min-width: 300px;" colspan="1">
						 <c:choose>
							<c:when test="${anagrafe.entity.numiscrrea eq anagrafe.oldAnagrafe.numiscrrea || (empty anagrafe.entity.numiscrrea and empty  anagrafe.oldAnagrafe.numiscrrea)}">
								<spring-form:input id="numiscrrea_id" path="entity.numiscrrea" size="15"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input id="numiscrrea_id" cssClass="bgred" path="entity.numiscrrea" onclick="gda('numrea_modificatoOverlay_id')"  size="15"/>
								<input id="id_nuovo_num_rea" type="hidden" value="${anagrafe.oldAnagrafe.numiscrrea}" name="oldAnagrafe.numiscrrea"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.numiscrrea}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.numiscrrea}"/>
								   <jsp:param value="numrea_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="numrea_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="numiscrrea_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_num_rea" name="oldfieldId"/>
								</jsp:include>	
							</c:otherwise>
						</c:choose>	
						<spring-form:errors path="entity.numiscrrea" cssClass="error"/> 
					</td>
					<td class="inline-ui-cell"><fmt:message key="label.data"/></td>
					<td colspan="3" class="inline-ui-cell">
					    <fmt:formatDate value="${anagrafe.entity.dataiscrrea}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_iscrrea"/>
					    <fmt:formatDate value="${anagrafe.oldAnagrafe.dataiscrrea}" pattern="<%=WebConstants.DATE_WITH_TIME_FORMAT_PATTERN %>" var="data_iscrrea_old"/>
						<c:if test="${(data_iscrrea == data_iscrrea_old) || data_iscrrea==null}">
								<spring-form:input id="dataiscrrea_id" path="entity.dataiscrrea" size="8"/>
								<init:calendar imagePath="/images/cal.gif" idImage="caldataiscrrea" idInput="dataiscrrea_id" textKey="label.calendar"/>
						</c:if>
						<c:if test="${(data_iscrrea != data_iscrrea_old) && data_iscrrea!=null}">
							<spring-form:input id="dataiscrrea_id" path="entity.dataiscrrea" cssClass="bgred" size="8" onclick="gda('dataiscrrea_dialog')"/>
							<init:calendar imagePath="/images/cal.gif" idImage="caldataiscrrea" idInput="dataiscrrea_id" textKey="label.calendar"/>
					    	<spring-form:hidden path="oldAnagrafe.dataiscrrea" id="id_nuovo_dataiscrrea"/>
							<div id="dataiscrrea_dialog" style="display: none;"  dojoType="dijit.Dialog">
								<div id="dataiscrrea_dialogInner" class="dialog">
									<table width="100%">
									<tr><td><fmt:message key="label.nuovo_valore_associato"/></td></tr>
									<tr><td  class="parametri"> <fmt:formatDate value="${anagrafe.entity.dataiscrrea}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr><td><fmt:message key="label.vecchio_valore_associato"/></td></tr>
									<tr><td class="parametri"><fmt:formatDate value="${anagrafe.oldAnagrafe.dataiscrrea}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
									<tr><td><fmt:message key="label.descrizione_ripristina_accetta_modifiche_anagarfe"/></td></tr>
					   				</table>
					   				<div id="functions">
										<ul>
										    <li><a href="#" onclick="ripristina('dataiscrrea_id','id_nuovo_dataiscrrea','dataiscrrea_dialog')"><fmt:message key="button.rifiuta"/></a></li>
											<li><a href="#" onclick="accetta('dataiscrrea_id','dataiscrrea_dialog')"><fmt:message key="button.accetta"/></a></li>
										</ul>
									</div>
								</div>	
							</div>
							</c:if>
						<spring-form:errors path="entity.dataiscrrea" cssClass="error"/> 
					</td>
				</tr>
				<tr>	
					<td><fmt:message key="label.numero_matricola_inail"/></td>
					<td colspan="1" class="inline-ui-cell" style="min-width: 300px;">
						<spring-form:input id="inailMatricola_id" path="entity.inailMatricola" size="15"/>
						<spring-form:errors path="entity.inailMatricola" cssClass="error"/> 
					</td>
					<td class="inline-ui-cell"><fmt:message key="label.sede_iscrizione_inail"/></td>
					<td colspan="3">
						<spring-form:input id="sedeInail_id" path="entity.sedeInail.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'sedeInail_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
						<init:autocompleter methodAjax="findSediINAIL.htm" idHidden="sedeInail_hidden" idInput="sedeInail_id" inputTitleKey="label.ricerca_forma_giuriche"></init:autocompleter>
						<spring-form:errors path="entity.sedeInail" cssClass="error"/> 
						<spring-form:hidden id="sedeInail_hidden" path="entity.sedeInail.codice"  />
					</td>
				</tr>
				<tr>	
				    <td><fmt:message key="label.numero_matricola_inps"/></td>
					<td colspan="1" class="inline-ui-cell" style="min-width: 300px;">
						<spring-form:input id="inpsMatricola_id" path="entity.inpsMatricola" size="15"/>
		                <spring-form:errors path="entity.inpsMatricola" cssClass="error"/> 
					</td>
					<td class="inline-ui-cell"><fmt:message key="label.sede_iscrizione_inps"/></td>
					<td colspan="3">
						<spring-form:input id="sedeInps_id" path="entity.sedeInps.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'sedeInps_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
						<init:autocompleter methodAjax="findSediINPS.htm" idHidden="sedeInps_hidden" idInput="sedeInps_id" inputTitleKey="label.ricerca_forma_giuriche"></init:autocompleter>
						<spring-form:errors path="entity.sedeInps" cssClass="error"/> 
						<spring-form:hidden id="sedeInps_hidden" path="entity.sedeInps.codice"  />
					</td>
				</tr>				
			    <tr>
			        <td><fmt:message key="label.numero_matricola_cassaedile"/></td>
					<td colspan="1"  class="inline-ui-cell" style="min-width: 300px;">
						<spring-form:input id="inpsMatricola_id" path="entity.cassaedileMatricola" size="15"/>
						<spring-form:errors path="entity.cassaedileMatricola" cssClass="error"/> 
					</td>
					<td class="inline-ui-cell">
						<fmt:message key="label.sede_iscrizione_cassaedile"/>
					</td>
					<td colspan="3">
						<spring-form:input id="sedeCassaedile_id" path="entity.sedeCassaedile.descrizioneEstesa" cssClass="searchbox" onchange="checkValue(this,'sedeCassaedile_hidden')" onkeydown="javascript:return searchAll(this,event)" size="40"/>
						<init:autocompleter methodAjax="findSediCassaedile.htm" idHidden="sedeCassaedile_hidden" idInput="sedeCassaedile_id" inputTitleKey="label.ricerca_sede_cassa_edile"></init:autocompleter>
						<spring-form:errors path="entity.sedeCassaedile" cssClass="error"/> 
						<spring-form:hidden id="sedeCassaedile_hidden" path="entity.sedeCassaedile.codice"  />
					</td>
			   </tr>
				</c:if>	
				<%-- END  VISUALIZZATA SE SCELTO PERSONAGIURIDICA GESTIONE INFORMAZIONI ALBO START --%>
				<tr id="sezione_albo_id" class="titoloSezione" style="<%=displaySezioneAlbo%>;">
					<td colspan="6"><fmt:message key="label.albo"/></td>
				</tr>
				<tr id="sezione_albo_id" style="<%=displaySezioneAlbo%>;">
					<td><fmt:message key="label.albo"/></td>	
					<td>
					    <c:if test="${(anagrafe.entity.elenchiprofessionalibase.id eq anagrafe.entity.elenchiprofessionalibase.id) || anagrafe.entity.elenchiprofessionalibase.id==null }">
							<spring-form:input id="albo_id" path="entity.elenchiprofessionalibase.epDescrizione" cssClass="searchbox" onchange="checkValue(this,'albo_hidden')" onkeydown="javascript:return searchAll(this,event)"  size="24"/>
							<init:autocompleter methodAjax="findElenchiprofessionalibase.htm" idHidden="albo_hidden" idInput="albo_id" inputTitleKey="label.ricerca_albo_professioni"></init:autocompleter>
							<spring-form:errors path="entity.elenchiprofessionalibase" cssClass="error"/> 
							<spring-form:hidden id="albo_hidden" path="entity.elenchiprofessionalibase.id"  />
						</c:if>
					    <c:if test="${(anagrafe.entity.elenchiprofessionalibase.id ne anagrafe.entity.elenchiprofessionalibase.id) && anagrafe.entity.elenchiprofessionalibase.id!=null }">
							<spring-form:input id="albo_id" path="entity.elenchiprofessionalibase.epDescrizione" cssClass="searchboxBackgroudRed" onchange="checkValue(this,'albo_hidden')" onkeydown="javascript:return searchAll(this,event)" onclick="gda('albo_modificatoOverlay_id')"  size="24"/>
							<init:autocompleter methodAjax="findElenchiprofessionalibase.htm" idHidden="albo_hidden" idInput="albo_id"  inputTitleKey="label.ricerca_albo_professioni"></init:autocompleter>
							<spring-form:errors path="entity.elenchiprofessionalibase" cssClass="error"/> 
							<spring-form:hidden id="albo_hidden" path="entity.elenchiprofessionalibase.id"  />
							<input type="hidden" id="id_nuovo_albo" name="oldAnagrafe.elenchiprofessionalibase.epDescrizione"  value="${anagrafe.oldAnagrafe.elenchiprofessionalibase.epDescrizione}"  />
						    <jsp:include page="../anagrafe/formModificaCampi.jsp">
							   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.elenchiprofessionalibase.epDescrizione}"/>
							   <jsp:param name="nuovocampo" value="${anagrafe.entity.elenchiprofessionalibase.epDescrizione}"/>
							   <jsp:param value="albo_modificatoOverlay_id" name="id_div_overlay"/>
							   <jsp:param value="albo_modificatoInner_id" name="id_div_inner"/>
							   <jsp:param value="albo_id" name="fieldId"/>
							   <jsp:param value="id_nuovo_albo" name="oldfieldId"/>
							</jsp:include>
						</c:if>
					</td>
					<td class="inline-ui-cell"><fmt:message key="label.provincia"/></td>
					<td colspan="3">
						 <c:choose>
							<c:when test="${anagrafe.entity.provinciaelencopro eq anagrafe.oldAnagrafe.provinciaelencopro || (empty anagrafe.entity.provinciaelencopro and empty  anagrafe.oldAnagrafe.provinciaelencopro)}">
								<spring-form:input id="provincia_provinciaelencopro_id" path="entity.provinciaelencopro" size="20"/>
						    </c:when>
						    <c:otherwise>
								<spring-form:input cssClass="bgred" id="provincia_provinciaelencopro_id" path="entity.provinciaelencopro" size="20" onclick="gda('provinciaelencopro_modificatoOverlay_id')"/>
								<input id="id_nuovo_provinciaelencopro" type="hidden" value="${anagrafe.oldAnagrafe.provinciaelencopro}" name="oldAnagrafe.provinciaelencopro"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.provinciaelencopro}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.provinciaelencopro}"/>
								   <jsp:param value="provinciaelencopro_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="provinciaelencopro_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="provincia_provinciaelencopro_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_provinciaelencopro" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.provinciaelencopro" cssClass="error"/>
					</td>
				</tr>
				<tr id="sezione_albo_id" style="<%=displaySezioneAlbo%>;">
					<td><fmt:message key="label.numero"/></td>
					<td colspan="5">
						<c:choose>
							<c:when test="${anagrafe.entity.numeroelencopro eq anagrafe.oldAnagrafe.numeroelencopro || (empty anagrafe.entity.numeroelencopro and empty  anagrafe.oldAnagrafe.numeroelencopro)}">
								<spring-form:input id="numero_id" path="entity.numeroelencopro" size="27"/>
						    </c:when>
						    <c:otherwise>
							<spring-form:input id="numero_id" cssClass="bgred" path="entity.numeroelencopro" onclick="gda('numeroelencopro_modificatoOverlay_id')"  size="27"/>
								<input id="id_nuovo_numeroelencopro" type="hidden" value="${anagrafe.oldAnagrafe.numeroelencopro}" name="oldAnagrafe.numeroelencopro"></input>
								<jsp:include page="../anagrafe/formModificaCampi.jsp">
								   <jsp:param name="vecchiocampo" value="${anagrafe.oldAnagrafe.numeroelencopro}"/>
								   <jsp:param name="nuovocampo" value="${anagrafe.entity.numeroelencopro}"/>
								   <jsp:param value="numeroelencopro_modificatoOverlay_id" name="id_div_overlay"/>
								   <jsp:param value="numeroelencopro_modificatoInner_id" name="id_div_inner"/>
								   <jsp:param value="numero_id" name="fieldId"/>
								   <jsp:param value="id_nuovo_numeroelencopro" name="oldfieldId"/>
								</jsp:include>	
						    </c:otherwise>
						</c:choose>    
						<spring-form:errors path="entity.numeroelencopro" cssClass="error"/>
					</td>
				</tr>
			    <%-- SEZIONE INFORMAZIONE ALBO  END --%>
			  
			    
			    <jsp:include page="altriDatiForm.jsp" />
			    
			    

			</table>	
		</spring-form:form>
	</div>
	<%
		AnagrafeCommand command = (AnagrafeCommand)request.getAttribute("anagrafe");
		Boolean isPopoup=false;
		String uriBack = "/anagrafe/view.htm?codice=" + request.getParameter("codice");
		if(command.getPopup()!=null && command.getPopup().equals(new Boolean(true)))
		{
		 isPopoup=true;
		 uriBack="/anagrafe/"+command.getPrefixPopup()+"view.htm?codice=" + request.getParameter("codice")+"&popupCaller=richiedenteIdCodice";
		}
	    String urlschedeanagrafe = BackofficeNETConstants.getURL_SCHEDE_ANAGRAFE()+"?Software=TT&CodiceAnagrafe="+command.getEntity().getId().getCodice();
	    if(isPopoup){
	    	urlschedeanagrafe = BackofficeNETConstants.getUrlToPopupdecorator(request,urlschedeanagrafe,uriBack, null);
	    }else{
			urlschedeanagrafe = BackofficeNETConstants.getUrlTo(request,urlschedeanagrafe,uriBack, null , false);
	    }
	    pageContext.setAttribute("urlschedeanagrafe", urlschedeanagrafe);
	%>
	<div id="functions">
		<ul>
			<c:if test="${anagrafe.entity.id.codice==null}">
			   <li><a href="javascript:doSubmit('${anagrafe.prefixPopup}insert.htm','',document.inviodati)"><fmt:message key="button.insert"/></a></li> 
			</c:if>
			<c:if test="${anagrafe.entity.id.codice!=null && anagrafe.entity.flagDisabilitato eq 0}">
			    <c:if test="${CAN_UPDATE eq true }">
					<li><a href="javascript:doSubmit('${anagrafe.prefixPopup}update.htm','',document.inviodati)"><fmt:message key="button.update"/></a></li>
				</c:if>    
				    	<li><a href="${urlschedeanagrafe}"><fmt:message key="button.schede"/></a></li>
						<c:if test="${anagrafe.popup eq false or empty anagrafe.popup}">
						    <c:if test="${CAN_UPDATE eq true }">						
								<li><a href="javascript:doSubmit('${anagrafe.prefixPopup}delete.htm','<fmt:message key="javascript.confirm.delete"/>',document.inviodati)"><fmt:message key="button.delete"/></a></li>
							</c:if>			
						    <li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/listscadenze.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.notifiche"/></a></li>
							<%pageContext.setAttribute("URL_STAMPA",BackofficeNETConstants.getURL_STAMPA_RICHIEDENTI_DOCUMENTI_TIPO());%>
							<c:set var="_URL_STAMPA" value="${URL_STAMPA}?CodiceAnagrafe=${anagrafe.entity.id.codice}&Doc_base=SCHEDAANAGRAFE.RTF"/>
							<c:set var="_URL_STAMPA" value="${inite:geturlto(pageContext.request, _URL_STAMPA, _urlback, null, true)}"/>
							<li><a href="javascript:void 0"	onclick="window.open('${_URL_STAMPA}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message key="button.stampa"/></a></li>
						    <li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/listemail.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.email"/></a></li>				    
						    <li><a href="javascript:historySet('${_urlback}','..%2Fautorizzazioni/listDaAnagrafe.htm?codiceAnagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="label.autorizzazioni"/></a></li>
						    <li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/listdocumenti.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.documenti"/></a></li>
						    <li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/listanagrafestorico.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.anagrafe_storico"/></a></li>
					    </c:if>
			</c:if>
			<c:if test="${anagrafe.entity.id.codice!=null}">
			 <li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/lististanzerichiedenti.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.procedimenti"/></a></li>
			</c:if>
			<c:set scope="page" value="button.disabilita" var="labelAbilita_Disabilita"></c:set>
			<c:if test="${anagrafe.entity.id.codice!=null && anagrafe.entity.flagDisabilitato eq 1}">			
			  	<c:set scope="page" value="button.abilita" var="labelAbilita_Disabilita"></c:set>
			</c:if>
			<c:if test="${CAN_UPDATE eq true }">			
				<li><a href="javascript:historySet('${_urlback}','..%2Fanagrafe/abilitaOrDisabilita.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="${labelAbilita_Disabilita}"/></a></li>
			</c:if>	
			<!-- Bottone per gestire il borsellino -->
			<c:if test="${ABBONAMENTO_ATTIVO eq true && anagrafe.entity.tipoanagrafe ne personaGiuridicaval}">	
				<li><a href="javascript:historySet('${_urlback}','..%2Fabbonamentoposteggi/view.htm?codiceanagrafe=${anagrafe.entity.id.codice}','')"><fmt:message key="button.borsellino"/></a></li>
			</c:if>
			<c:if test="${anagrafe.popup eq false or empty anagrafe.popup}">
				<li><a href="javascript:historyBack()"><fmt:message key="button.back"/></a></li>
			</c:if>
			<c:if test="${anagrafe.popup eq true}">
				<li><a href="javascript:self.close()"><fmt:message key="button.back"/></a></li>
			</c:if>
		</ul>
	</div>
	<c:if test="${anagrafe.popup eq true}">
		<%if(StringUtils.defaultString(request.getParameter("done"),"false").equalsIgnoreCase("true")){%>			
<script type="text/javascript">
jQuery(document).ready(function(){
	opener.jQuery('#${anagrafe.popupCaller}').val('<%= ((AnagrafeCommand)request.getAttribute("anagrafe")).getEntity().getDescrizioneRichiedente().replace("'","\\'")%>');
	opener.jQuery('#${anagrafe.popupCaller}_hidden').val('${anagrafe.entity.id.codice}');
	opener.jQuery('#${anagrafe.popupCaller}').change();
});
</script>				
		<%} %>
	</c:if>	
</body>
</html>