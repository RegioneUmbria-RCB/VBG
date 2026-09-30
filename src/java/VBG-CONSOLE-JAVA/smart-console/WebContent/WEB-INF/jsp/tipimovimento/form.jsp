<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.net.URLEncoder"%>

<%@page import="it.gruppoinit.pal.gp.core.domain.Tipimovimento"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.TipimovimentoCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.NEW}">
			<fmt:message key="tipimovimento.label.nuovo_tipimovimento.title" />
		</c:if> 
		<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
			<fmt:message key="tipimovimento.label.dettaglio_tipimovimento.title" />
		</c:if>
	</title>
</head>
<body>
<%
String  GGproroga="display:none;";
String  invioMaailAndMostraEsito="display:none;";
String  tipologiaregistri="display:none;";
%>

				


	<span class="titoloPagina">
		<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.NEW}">
			<fmt:message key="tipimovimento.label.nuovo_tipimovimento.title" />
		</c:if> 
		<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
			<fmt:message key="tipimovimento.label.dettaglio_tipimovimento.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../tipimovimento/view" />
    </jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="tipimovimento" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipimovimento" />
		    </jsp:include>
			<c:choose>
				<c:when test="${tipimovimento.entity.flagDisabilitato eq true}">
					<br class="clear" />					
					<div id="disabilitato_status_msg" class="alertLine">
			    		<b><fmt:message key="label.record_disabilitato"/></b>
			    	</div>
			    	<br class="clear" />
				</c:when>
			</c:choose>
			<table>
			   <c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.NEW}">
			    <tr>
					<td>
						<fmt:message key="tipimovimento.label.codice" />
					</td>
					<td>
					    <input type="text" name="_entity.id.tipomovimento" value="${software}" readonly="readonly" size="3"/>
						<spring-form:input id="tipomovimento_id" path="entity.id.tipomovimento" size="8" maxlength="6" />
						<spring-form:errors path="entity.id.tipomovimento" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.codice" />
					</td>
					<td>
						<spring-form:input id="tipomovimento_id" path="entity.id.tipomovimento" size="10" readonly="true" />
					</td>
				</tr>
				</c:if>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.movimento"/>
					</td>
					<td>
						<spring-form:input id="movimento_id" path="entity.movimento" size="70"/>
						<spring-form:errors path="entity.movimento" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_richiesta_integrazione"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_richiesta_integrazione" />">
						<spring-form:checkbox id="flagRichiestaintegrazione_id" path="entity.flagRichiestaintegrazione" onclick="Verificaintegrazione();"/>
						<fmt:message key="tipimovimento.label.descrizione_flag_richiesta_integrazione" />
						<spring-form:errors path="entity.flagRichiestaintegrazione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_interruzione"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_interruzione" />">
						<spring-form:checkbox id="flagInterruzione_id" path="entity.flagInterruzione" onclick="Verificainterruzione();"/>
						<fmt:message key="tipimovimento.label.descrizione_flag_interruzione" />
						<spring-form:errors path="entity.flagInterruzione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_proroga"/>
					</td>
					<td >
						<spring-form:checkbox id="flagProroga_id" path="entity.flagProroga"  onclick="Verificaproroga();viewHideGGproroga(this);"/>
						<fmt:message key="tipimovimento.label.descrizione_flag_proroga"/>
						<spring-form:errors path="entity.flagProroga" cssClass="error"/>
					</td>
					<td id="gg_proroga_id" style="<%=GGproroga%>">
					    <spring-form:input id="ggproroga_id" path="entity.ggproroga" size="5" onchange="checkNumberValue(this);javascript:isPositiveNumber(this)"/>
					    <fmt:message key="tipimovimento.label.descrizione_gg_proroga"/>
					    <spring-form:errors path="entity.ggproroga" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_fine_sospensione_interruzione"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_fine_sospensione_interruzione" />">
						<spring-form:checkbox id="flagFinesospinterr_id" path="entity.flagFinesospinterr" onclick="Verificachiusuraevento();"/>
						<fmt:message key="tipimovimento.label.descrizione_flag_fine_sospensione_interruzione" />
						<spring-form:errors path="entity.flagFinesospinterr" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.statoistanza"/>
					</td>
					<td>
						<spring-form:select id="statoistanza_id"  path="entity.statoistanza.id.codicestato">
                        <spring-form:option value="" ><fmt:message key="label.select.default" /></spring-form:option>
	                    <spring-form:options items="${statiistanzaList}" itemLabel="stato" itemValue="id.codicestato" />
		                </spring-form:select>
		                <init:help idHelp="help_statoistanza_id" textKey="tipimovimento.label.statoistanza.help" />
						<spring-form:errors path="entity.statoistanza" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_operante"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_operante" />">
						<spring-form:checkbox id="flagOperante_id" path="entity.flagOperante" onclick="Verificaoperante();"/>
						<fmt:message key="tipimovimento.label.descrizione_flag_operante" />
						<spring-form:errors path="entity.flagOperante" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_nonoperante"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_nonoperante" />">
						<spring-form:checkbox id="flagNonoperante_id" path="entity.flagNonoperante" onclick="Verificaoperante();"/>
						<fmt:message key="tipimovimento.label.descrizione_flag_nonoperante" />
						<spring-form:errors path="entity.flagNonoperante" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_conferanza_servizi"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_conferenza_servizi" />">
						<spring-form:checkbox id="flagCds_id" path="entity.flagCds"/>
						<fmt:message key="tipimovimento.label.descrizione_flag_conferanza_servizi" />
						<spring-form:errors path="entity.flagCds" cssClass="error"/>
					</td>
				</tr>
				<tr>
			           <td><fmt:message key="tipimovimento.label.tipologiaesito" /></td>
			           <td ><spring-form:select  id="tipologiaesito_id"  path="entity.tipologiaesito" onchange="viewHideEmailAndEsito(this);"  >
			               <spring-form:option value="0" ><fmt:message key="label.non_previsto" /></spring-form:option>
			               <spring-form:option value="2" ><fmt:message key="label.positivo" /></spring-form:option>
			               <spring-form:option value="1"><fmt:message key="label.negativo" /></spring-form:option>
			            </spring-form:select>				
					<spring-form:errors path="entity.tipologiaesito" cssClass="error"/></td>
        		</tr>
        		<!-- Visibili solo se la tipologia di esito è settata come positiva o negativa -->
        		<!-- STAR -->
        		
    		    <tr id="campo_invio_mail_id" style="<%=invioMaailAndMostraEsito%>">
					<td>
						<fmt:message key="tipimovimento.label.flag_invio_mail"/>
					</td>
					<td>
						<spring-form:checkbox id="flagEnmail_id" path="entity.flagEnmail" />
						<fmt:message key="tipimovimento.label.descrizione_flag_invio_mail" />
						<spring-form:errors path="entity.flagEnmail" cssClass="error"/>
					</td>
				</tr>
				<tr id="campo_tipologia_esito_id" style="<%=invioMaailAndMostraEsito%>">
					<td>
						<fmt:message key="tipimovimento.label.flag_mostra_esito"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_mostra_esito" />">
						<spring-form:checkbox id="flagEnmostra_id" path="entity.flagEnmostra" />
						<fmt:message key="tipimovimento.label.descrizione_flag_mostra_esito" />
						<spring-form:errors path="entity.flagEnmostra" cssClass="error"/>
					</td>
				</tr>
				<!-- END -->
				<c:if test="${entity.tipimovimento.software.codice !='TT'}">
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_registro"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_registro" />">
						<spring-form:checkbox id="flagRegistro_id" path="entity.flagRegistro" onclick="viewHideTipologiaregistri(this);" />
						<fmt:message key="tipimovimento.label.descrizione_flag_registro" />
						<spring-form:errors path="entity.flagRegistro" cssClass="error"/>
					</td>
				</tr>
				<tr  id="campo_tipologia_registro_id" style="<%=tipologiaregistri%>">
		           <td></td>
		           <td colspan="2" ><spring-form:select  id="tipologiaregistri_id"  path="entity.tipologiaregistri.id.codice"   >
                        <spring-form:option value="" ><fmt:message key="label.select.default" /></spring-form:option>
	                    <spring-form:options items="${tipologiaregistriList}" itemLabel="trDescrizione" itemValue="id.codice"></spring-form:options>
		                </spring-form:select>	
		                <fmt:message key="tipimovimento.label.descrizione_tipologiaregistri" />			
					<spring-form:errors path="entity.tipologiaregistri" cssClass="error"/></td>
        		</tr>
				</c:if>
				<c:if test="${isAmministrazioniInterneEsistono eq true}">
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_no_amm_interna"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_no_ammi_interna" />">
						<spring-form:checkbox id="flagNoamminterna_id" path="entity.flagNoamminterna"/>
						<fmt:message key="tipimovimento.label.descrizione_flag_no_amm_interna" />
						<spring-form:errors path="entity.flagNoamminterna" cssClass="error"/>
					</td>
				</tr>
				</c:if>				
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_usa_dal_protocollo"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_usa_dal_protocollo" />">
						<spring-form:checkbox id="flagUsadalprotocollo_id" path="entity.flagUsadalprotocollo"/>
						<fmt:message key="tipimovimento.label.descrizione_flag_usa_dal_protocollo" />
						<spring-form:errors path="entity.flagUsadalprotocollo" cssClass="error"/>
					</td>
				</tr>				
				<c:if test="${isVerticalizzazioneSTCAttiva eq true}">
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_stc"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_stc" />">
						<spring-form:checkbox id="flagStc_id" path="entity.flagStc"/>
						<fmt:message key="tipimovimento.label.descrizione_flag_stc" />
						<spring-form:errors path="entity.flagStc" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_disdavisionare"/>
					</td>
					<td>
						<spring-form:checkbox id="flagDisdavisionare_id" path="entity.flagDisdavisionare"/>
						<fmt:message key="tipimovimento.label.flag_disdavisionare.help" />
						<spring-form:errors path="entity.flagDisdavisionare" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<!-- §§§BEGIN§§§ -->
				<c:if test="${inite:isEnterprise()}">
					<c:if test="${isVerticalizzazioneINFOCAMERAAttiva eq true}">
					<tr>
						<td>
							<fmt:message key="tipimovimento.label.flag_camcom"/>
						</td>
						<td valign="top" title="<fmt:message key="tipimovimento.label.spiegazione_flag_camcom" />">
							<spring-form:checkbox id="flagCamcom_id" path="entity.flagCamcom"/>
							<fmt:message key="tipimovimento.label.descrizione_flag_camcom" />
							<spring-form:errors path="entity.flagCamcom" cssClass="error"/>
						</td>
					</tr>
					</c:if>
				</c:if>
				<!-- §§§END§§§ -->
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_ric_tel"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_ric_tel" />">
						<spring-form:select id="flagRicTel_id" path="entity.mailtipoByFkTipimovricTelMailtipo.id.codice" onchange="disableEl(this,'flagComTel_id')">
							<spring-form:option value="">...</spring-form:option>
							<spring-form:options items="${listaMailtipo }" itemLabel="descrizione" itemValue="id.codice" />
						</spring-form:select>
						<fmt:message key="tipimovimento.label.descrizione_flag_ric_tel" />
						<spring-form:errors path="entity.mailtipoByFkTipimovricTelMailtipo.id.codice" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_com_tel"/>
					</td>
					<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_com_tel" />">
						<spring-form:select id="flagComTel_id" path="entity.mailtipoByFkTipimovcomTelMailtipo.id.codice" onchange="disableEl(this,'flagRicTel_id')">
						<spring-form:option value="">...</spring-form:option>
							<spring-form:options items="${listaMailtipo }" itemLabel="descrizione" itemValue="id.codice" />
						</spring-form:select>
						<fmt:message key="tipimovimento.label.descrizione_flag_com_tel" />
						<spring-form:errors path="entity.mailtipoByFkTipimovcomTelMailtipo.id.codice" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.lettera_per_com_tel_o_ric_tel"/>
					</td>
					<td>
						<%
					      String  swSettato1="display:none;";
					      String  swTT1="display:inline;";
					    %>
					    <script type="text/javascript">
					      
					       function tuttiSw(){
						       if($('id_flag1').checked){			
								    $('letteretipo_id1').style.display="inline";
								    $('letteretipo_id2').style.display="none";
								}else{
									$('letteretipo_id1').style.display="none";
									$('letteretipo_id2').style.display="inline";
								}
					       }
					     </script>
						<div id="letteretipo_id1" style="<%=swSettato1%>"><spring-form:input id="lettere_tipo_id1" path="entity.letteretipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findLettereTipo.htm?codicesoftware=TT'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id1" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter></div>
						<div id="letteretipo_id2" style="<%=swTT1%>"><spring-form:input id="lettere_tipo_id2" path="entity.letteretipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findLettereTipo.htm'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id2" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter></div>
						<spring-form:errors path="entity.letteretipo" cssClass="error"/> 
						<spring-form:hidden id="lettere_tipo_hidden" path="entity.letteretipo.id.codice"/>
					    <input type="checkbox" id="id_flag1" onclick="tuttiSw();"/>
		                <init:help idHelp="help1" textKey="help.letteretipo_archivi_base"/>	
		                <fmt:message key="tipimovimento.label.descrizione_lettera_per_com_tel_o_ric_tel" />					
					</td>		
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_allegati_link"/>
					</td>
					<td>
						<spring-form:checkbox id="flgInvialinkallmail_id" path="entity.flgInvialinkallmail" />
						<fmt:message key="help.flag_allegati_link"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.flag_allegati_link_protocollo"/>
					</td>
					<td>
						<spring-form:checkbox id="flgProtocollalinkall_id" path="entity.flgProtocollalinkall" />
						<fmt:message key="help.flag_allegati_link_protocollo"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.letteratipo_linkdoc" />
					</td>
					<td>
					<jsp:include page="../includes/autocompletergenericoTT.jsp" >
						<jsp:param name="idElemento" value="letteraTipoAllegati" />		
						<jsp:param name="propertyPath" value="entity.letteraTipoAllegati" />				
						<jsp:param name="pathPropertyDescription" value="entity.letteraTipoAllegati.descrizione" />
						<jsp:param name="pathPropertyCode" value="entity.letteraTipoAllegati.id.codice" />
						<jsp:param name="autocompleterAjax" value="findLettereTipo.htm?codicesoftware=" />	
						<jsp:param name="titleKey" value="label.ricerca_tipo_lettera" />
						<jsp:param name="id_help" value="help_letteraTipoAllegati" />
						<jsp:param name="help" value="help.search_archivi_base" />
					</jsp:include>
					<fmt:message key="help.letteratipo_linkdoc"/>
					</td>
				</tr>
			</table>
			<div class="titoloSezione">
			<fmt:message key="label.dati_frontoffice" />
			</div>
			
			<!-- TABELLA DATI PER IL FRONT OFFICE -->
			<table>
			<tr>
				<td>
					<fmt:message key="tipimovimento.label.flag_pubblica_movimento"/>
				</td>
				<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_pubblica_movimento" />">
					<spring-form:checkbox id="flagPubblicamovimento_id" path="entity.flagPubblicamovimento"/>
					<fmt:message key="tipimovimento.label.descrizione_flag_pubblica_movimento" />
					<spring-form:errors path="entity.flagPubblicamovimento" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="tipimovimento.label.flag_pubblica_parere"/>
				</td>
				<td title="<fmt:message key="tipimovimento.label.spiegazione_flag_pubblica_parere" />">
					<spring-form:checkbox id="flagPubblicaparere_id" path="entity.flagPubblicaparere"/>
					<fmt:message key="tipimovimento.label.descrizione_flag_pubblica_parere" />
					<spring-form:errors path="entity.flagPubblicaparere" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="tipimovimento.label.flag_pubblica_allegati"/>
				</td>
				<td>
					<spring-form:checkbox id="flagPubblicaallegati_id" path="entity.flagPubblicaallegati"/>
					<fmt:message key="tipimovimento.label.descrizione_flag_pubblica_allegati" />
					<spring-form:errors path="entity.flagPubblicaallegati" cssClass="error"/>
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="tipimovimento.label.fo_soggettiesterni" /></td>
         			<td title="<fmt:message key="tipimovimento.label.spiegazione_fo_soggettiesterni" />" >
         			<spring-form:select  id="tipologiaregistri_id"  path="entity.foSoggettiesterni.codice"  >
         			<spring-form:option value="" ><fmt:message key="label.select.default" /></spring-form:option>
         			<spring-form:options items="${soggettiesterniList}" itemLabel="descrizione" itemValue="codice"></spring-form:options>
         			</spring-form:select> 
				<fmt:message key="tipimovimento.label.descrizione_fo_soggettiesterni" />
				<spring-form:errors path="entity.foSoggettiesterni" cssClass="error" /></td>
			</tr>	
		</table>
		<br class="clear" />			
		<!-- TABELLA DEI CONTROMOVIMENTI  -->
		<!-- START -->
		<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
		<div class="titoloSezione">
		     <fmt:message key="tipimovimento.label.contro_movimenti_associati.tilte" />
		</div>
		
		<div class="jmesa">
		<table border="0" cellpadding="2" cellspacing="0" class="table">
			<thead>
			<tr class="header">
				<td ><fmt:message key="tipimovimento.label.codice" /> </td>
				<td width="3%"><fmt:message key="label.obbligatorio" /></td>
               	<td ><fmt:message key="label.comportamento" /></td>
              	 <td ><fmt:message key="label.procedura" /></td>
              	 <td ><fmt:message key="tipimovimento.label.amministrazione_effettua_mov" /></td>
               	<td ><fmt:message key="tipimovimento.label.amministrazione_effettua_contromov" /></td>
               	<td ><fmt:message key="label.tempi_attesa" /></td>
           </tr>
		   </thead>
		   <tbody class="tbody">
				<%int j=1;%>
				
		  <%--  <c:forEach items="${tipimovimento.entity.tipicontromovimentos}" var="contro_mov_var">--%>
		  <c:forEach items="${listaTipiContromovimenti}" var="contro_mov_var">
			<tr class="<%=(j%2)==0?"odd":"even"%>">
				<td><a href="javascript:doHref('viewContromovimento.htm?codicecontromovimento=${contro_mov_var.id.codice}','')">${contro_mov_var.tipocontromovimento.id.tipomovimento}</a>-
				    <a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../tipimovimento/view.htm?codice=${contro_mov_var.tipocontromovimento.id.tipomovimento}','')">${contro_mov_var.tipocontromovimento.movimento}</a>
				</td>
				<c:if test="${contro_mov_var.flagbase==true}">
				<td><fmt:message key="label.si" /></td>
				</c:if>
				<c:if test="${contro_mov_var.flagbase==false}">
				<td><fmt:message key="label.no" /></td>
				</c:if>
				<c:if test="${contro_mov_var.soloseesitonegativo==0 || contro_mov_var.soloseesitonegativo==null}">
              	<td><fmt:message key="label.effettua" />:<br /><fmt:message key="label.sempre" /><br /></td>
	            </c:if>
	            <c:if test="${contro_mov_var.soloseesitonegativo==2}">
	            <td><fmt:message key="label.effettua" />:<br /><fmt:message key="tipimovimento.label.se_positivo" /></td>
	            </c:if>
	            <c:if test="${contro_mov_var.soloseesitonegativo==1}">
	            <td><fmt:message key="label.effettua" />:<br /><fmt:message key="tipimovimento.label.se_negativo" /></td>
	            </c:if>
	            <c:if test="${contro_mov_var.tipiprocedure.id.codice != null}">
	            <td>${contro_mov_var.tipiprocedure.procedura}</td>
	            </c:if>
	            <c:if test="${contro_mov_var.tipiprocedure.id.codice == null}">
	            <td><fmt:message key="label.tutte_procedure" /></td>
	            </c:if>
	            <td>${contro_mov_var.amministrazioniTipiMovimento.amministrazione}</td>
	            <td>${contro_mov_var.amministrazioniTipiContromovimento.amministrazione}</td>
	            <td>
			        <div id="functions">
				   	<ul>
			        	<li><a title="<fmt:message key="label.visualizza"/>" onclick="javascript:creaTempiDiRisposta(${contro_mov_var.id.codice})" href="javascript:void(0)"><fmt:message key="button.visualizza" /></a></li>
			        </ul>
			        </div>	
	           </td>
			</tr>
				<%j++; %>
		   </c:forEach>
		</tbody>
		</table>
		</div>
		
		<jsp:include page="../includes/pannelloSceltaSoftware.jsp"/>
		<script type='text/javascript'>
		
		<%
		 String software=ORMHelper.getSoftware();
		 pageContext.setAttribute("software", software);
		%>
		
		function creaTempiDiRisposta(codice)
		{
			  var url = "../tipimovimento/createTempirispostaContromovimento.htm?codicecontromovimento=" + codice;
			  if('${software}'=='<%=WebConstants.SOFTWARE_TT%>')
			  {
				  pannelloSceltaSoftware(url,'doHref',true,'');
			  }else
			  {
			     doHref('createTempirispostaContromovimento.htm?codicecontromovimento='+ codice,'');
			  }
		}
		</script>
		<!-- END -->
		
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('createContromovimento.htm?codicemovimento=${tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.new" /></a></li>
			</ul>
		</div>
		<br class="clear" /><br class="clear" />
		</c:if>
		
		<!-- TABELLA DEI MOVIMETI DI CUI E' CONTROMOVIMENTO  -->
		<!-- START -->
		<%--
		<c:if test="${fn:length(tipimovimento.entity.tipimovimentos)>0}">
		--%>
		<c:if test="${fn:length(listaTipiContromovimenti1)>0}">
		
		<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
		<div class="titoloSezione">
		     <fmt:message key="tipimovimento.label.movimenti_contro_movimenti.tilte" />
		</div>
		
		<div class="jmesa">
		<table border="0" cellpadding="2" cellspacing="0" class="table">
			<thead>
			<tr class="header">
				<td ><fmt:message key="tipimovimento.label.movimento" /> </td>
				<td><fmt:message key="label.procedura" /></td>
            </tr>
		    </thead>
			<tbody class="tbody">
			<%int j=1;%>
			<%--
			<c:forEach items="${tipimovimento.entity.tipimovimentos}" var="mov_var">
			--%>
			<c:forEach items="${listaTipiContromovimenti1}" var="mov_var">
			<tr class="<%=(j%2)==0?"odd":"even"%>">
				<td><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=../tipimovimento/view.htm?codice=${mov_var.tipomovimento.id.tipomovimento}','')">${mov_var.tipomovimento.id.tipomovimento} </a>- ${mov_var.tipomovimento.movimento}</td>
			
				<c:if test="${mov_var.tipiprocedure.id.codice != null}">
               <td>${mov_var.tipiprocedure.procedura}</td>
               </c:if>
               <c:if test="${mov_var.tipiprocedure.id.codice == null}">
               <td><fmt:message key="label.tutte_procedure" /></td>
               </c:if>
               
			</tr>
			<%j++; %>
			</c:forEach>
			</tbody>
		</table>
		</div>
		</c:if>
		</c:if>
		<!-- END -->
			
	<script type='text/javascript'>
		$('movimento_id').focus();
		
	    // gestisce la visualizzazione iniziale dei campi che possono essere
	    // nascosti
	    // flag ggproroga
        if($('flagProroga_id').checked)
        {
        	$('gg_proroga_id').appear();
        }
        // flag tipologia esito
        if($('tipologiaesito_id').value==1 || $('tipologiaesito_id').value==2)
        {
        	$('campo_invio_mail_id').appear();
         	$('campo_tipologia_esito_id').appear();
        }
        // flag tipologia registro
        if($('flagRegistro_id').checked)
        {
        	$('campo_tipologia_registro_id').appear();
        }
		// Gestisce la visualizzazione del campo ggProroga
		function viewHideGGproroga(id){
			if(id.checked){
				$('gg_proroga_id').appear();
			}else{
				$('gg_proroga_id').fade();
				$("ggproroga_id").value=0;
			}		
		}
		// gestisce la visualizzazione dei campi invio mail ed esito proroga
		function viewHideEmailAndEsito(id){
			if(id.value==1 || id.value==2 ){
				$('campo_invio_mail_id').appear();
				$('campo_tipologia_esito_id').appear();
			}else{
				$('campo_invio_mail_id').fade();
				$('campo_tipologia_esito_id').fade();
				$('flagEnmail_id').checked=false;
				$('flagEnmostra_id').checked=false;
			}
		}
		// Gestisce la visualizzazione del campo ggProroga
		function viewHideTipologiaregistri(id){
			if(id.checked){
				$('campo_tipologia_registro_id').appear();
			}else{
				$('campo_tipologia_registro_id').fade();
				$('tipologiaregistri_id').options[0].selected = true;
			}	
		}
		// verifica che se un movimento è di sospenzione
		// non potra essere di interruzione o proroga
		function Verificaintegrazione()
		{
			if($('flagProroga_id').checked || $('flagInterruzione_id').checked || $('flagFinesospinterr_id').checked){
            	alert('<fmt:message key="tipimovimento.alert.no_sospenzione" />');
                $('flagRichiestaintegrazione_id').checked=false;
            }
		}
		// verifica che se un movimento interruzione
		// non potra essere di sospenzione o proroga
		function Verificainterruzione()
		{
			if($('flagProroga_id').checked || $('flagRichiestaintegrazione_id').checked  || $('flagFinesospinterr_id').checked){
				alert('<fmt:message key="tipimovimento.alert.no_interruzione" />');
                $('flagInterruzione_id').checked=false;
            }
		}
		// verifica che se un movimento è di proroga
		// non potra essere di sospenzione o interruzione
		function Verificaproroga()
		{
			if($('flagInterruzione_id').checked || $('flagRichiestaintegrazione_id').checked  || $('flagFinesospinterr_id').checked){
				alert('<fmt:message key="tipimovimento.alert.no_proroga" />');
                $('flagProroga_id').checked=false;
            }
		}
		// verifica che il tipo movimento non possa anche essere di interruzione/proroga/sospensione
		function Verificachiusuraevento()
		{
			if($('flagProroga_id').checked || $('flagInterruzione_id').checked || $('flagRichiestaintegrazione_id').checked){
            	alert('<fmt:message key="tipimovimento.alert.no_flagFinesospinterr" />');
                $('flagFinesospinterr_id').checked=false;
            }
		}
		// verifica che se un movimento è operante non potra essere
		// non operante
		function Verificaoperante()
		{
			if($('flagOperante_id').checked && $('flagNonoperante_id').checked ){
				alert('<fmt:message key="tipimovimento.alert.operante" />');
				$('flagOperante_id').checked=false;
				$('flagNonoperante_id').checked=false;
			}
        }
		function disableEl(elem,id){
			var valSel=elem[elem.selectedIndex].value;
			if(valSel){
				$(id).value = '';
			}
		}
	</script>	
			
	</spring-form:form>
	</div>
	<br />

	<%	
	String codiceTipoMovimento = "";
	TipimovimentoCommand command = (TipimovimentoCommand)request.getAttribute("tipimovimento");
	if(null!=command){
		Tipimovimento entity = command.getEntity();
		codiceTipoMovimento = entity.getId().getTipomovimento();
	}
	String uriBack = "../tipimovimento/view.htm?codice=" + codiceTipoMovimento;
	String uriModelli = BackofficeNETConstants.getURL_DYN2_TIPIMOV_MODELLI() + "?tipomovimento=" +codiceTipoMovimento;
    String urlModelli = BackofficeNETConstants.getUrlTo(request,uriModelli,uriBack,(String)session.getAttribute(WebConstants.SOFTWARE),false);
	pageContext.setAttribute("dyn_url_modelli",urlModelli);
	%>
	<div id="functions">
		<ul>
			<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.NEW}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${tipimovimento.displayMode==tipimovimento.displayConstants.VIEW}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			    <li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Ftipimovimento%2Flistdocumentitipo.htm?tipimovimento.codice=${tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.documenti_tipo" /></a></li>
			    <li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Ftipimovimento%2Flistoneri.htm?tipimovimento.codice=${tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.gestioni_oneri" /></a></li>
			    <!-- Lasciare in dot net -->
			    <li><a href="javascript:historySet('${_urlback}','..%2Ftipimovimento/listmodelli.htm?codicemovimento=${tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.modelli" /></a></li>
			    <c:if  test="${isVerticalizzazioneSTCAttiva eq true && tipimovimento.entity.flagStc eq true }">
				    <li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fparametristc/list.htm?tipimovimento.idtipomovimento=${tipimovimento.entity.id.tipomovimento}','')"><fmt:message key="button.configura_notifiche_stc" /></a></li>
			    </c:if>
			    <li>
					<c:choose>
						<c:when test="${tipimovimento.entity.flagDisabilitato eq true}">
							<a href="javascript:doSubmit('abilitaDisabilita.htm','<fmt:message key="javascript.confirm.abilita"><fmt:param value="Tipi movimento"/></fmt:message>',document.inviodati)"><fmt:message key="button.abilita" /></a>
						</c:when>
						<c:otherwise>
							<a href="javascript:doSubmit('abilitaDisabilita.htm','<fmt:message key="javascript.confirm.disabilita"><fmt:param value="Tipi movimento" /></fmt:message>',document.inviodati)"><fmt:message key="button.disabilita" /></a>
						</c:otherwise>
					</c:choose>
				</li>
			</c:if>
			<%-- 
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
			--%>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>