<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.ProcedimentoProcediMarche"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProcediMarcheCommand"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ProcedimentoProcediMarche ppm = null;
ProcediMarcheCommand cmd = (ProcediMarcheCommand)session.getAttribute("pmCommand");
if(cmd != null){
    ppm = cmd.getProcedimento();
}

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<fmt:message key="procedimarche.label.dettaglioprocedimento.title" />
	</title>
	<style type="text/css">
		.required {
			color: #da0008;
			font-weight: bold;
		}
	</style>
	
</head>
<body>
<div style="width: 85%; height: 70px;">
	<div style="float: left;">
		<img src="../images/procedimarche/procedimarche-logo5.png" style="width: 260px;"/>
	</div>
	<div style="float: right;">
		<img src="../images/procedimarche/regione_marche.png" style="width: 128px; height: 64px;"/>
	</div>
</div>
<%
if(ppm == null ){
    

%>

<%
} else {
%>
<div>
	<span class="titoloPagina">
		${pmCommand.procedimento.datiProcedimento.nome}
	</span>
</div>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../procedimarche/view" />
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="pmCommand" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="pmCommand" />
    </jsp:include>
    <table id="procedimarche_tab">
		<tr class="titoloSezione">
			<td colspan="4"><fmt:message key="procedimarche.label.datiregionali" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.categoria" /></td>			
			<td colspan="3">
			<spring-form:hidden id="procedimento_id" path="procedimento.datiProcedimento.id" />
			<spring-form:input id="categoria_id" path="procedimento.datiProcedimento.categoria" size="70" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.settoreattivita" /></td>			
			<td colspan="3">
			<spring-form:input id="settore_id" path="procedimento.datiProcedimento.settoreAttivita" size="70" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="label.descrizione" /></td>			
			<td colspan="3">
			<spring-form:textarea id="descrizione_id" path="procedimento.datiProcedimento.descrizione" cols="72" rows="6" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.destinatari" /></td>			
			<td colspan="3">
			<input type="text" size="70" name="destinatari" disabled="disabled" value="<%= ppm.getDatiProcedimento().getCategorieDestinatarioFormatted() %>"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.tipologiaregime" /></td>			
			<td colspan="3">
			<spring-form:input id="tiporegime_id" path="procedimento.datiProcedimento.tipologiaRegime" size="70" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.giustificazioneregime" /></td>			
			<td colspan="3">
			<spring-form:input id="giusregime_id" path="procedimento.datiProcedimento.giustificazioneRegime" size="70" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.terminiconclusione" /></td>			
			<td colspan="3">
			<spring-form:input id="termconcl_id" path="procedimento.datiProcedimento.terminiConclusione" size="70" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.modalitaconclusione" /></td>			
			<td colspan="3">
			<spring-form:input id="modconcl_id" path="procedimento.datiProcedimento.modalitaConclusione" size="70" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.strumentitutela" /></td>			
			<td colspan="3">
			<spring-form:input id="tutela_id" path="procedimento.datiProcedimento.strumentiTutela" size="70" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.specifichedimensionali" /></td>			
			<td colspan="3">
			<spring-form:input id="specdim_id" path="procedimento.datiProcedimento.specificheDimensionali" size="70" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.categorieanticorruzione" /></td>			
			<td colspan="3">
			<spring-form:input id="catanticorr_id" path="procedimento.datiProcedimento.categorieAnticorruzione" size="70" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.rischiocorruzione" /></td>			
			<td colspan="3">
			<input type="text" size="20" name="rischiocorruzione" disabled="disabled" value="<%= ppm.getDatiProcedimento().getRischioCorruzioneFormatted() %>"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.missione" /></td>			
			<td colspan="3">
			<spring-form:input id="missione_id" path="procedimento.datiProcedimento.missione" size="70" disabled="true"/>
		</tr>
		<tr>
			<td><fmt:message key="procedimarche.label.pubblicato" /></td>			
			<td colspan="3">
			<input type="text" size="20" name="pubblicato" disabled="disabled" value="<%= ppm.getDatiProcedimento().getPubblicatoFormatted() %>"/>
		</tr>
		<tr class="titoloSezione">
			<td colspan="4"><fmt:message key="procedimarche.label.datilocali" /></td>
		</tr>
		<c:if test="${empty pmCommand.procedimento.datiSpecifici}">
			<tr >
				<td colspan="4"><fmt:message key="procedimarche.label.procedimentononcollegato" /></td>			
			</tr>
			<tr>
				<td colspan="4"><fmt:message key="procedimarche.label.selezioneendo" /></td>
			</tr>
			<tr>
				<td colspan="4">
					<input type="text" id="inventarioprocedimento_id" name="nomeInventarioproc" class="searchbox" 
					onchange="checkValue(this,'inventarioprocedimento_hidden')" 
					onkeydown="javascript:return searchAll(this,event)" size="67"/>
					<init:autocompleter methodAjax="findProcedimentiPrincipali.htm" idHidden="inventarioprocedimento_hidden" idInput="inventarioprocedimento_id" 
					callBack="filterinventario" afterUpdateElement="inventarioCallBack" inputTitleKey="label.ricerca_inventarioprocedimento"/>
					<spring-form:hidden id="inventarioprocedimento_hidden" path="idInventarioproc"  />
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty pmCommand.procedimento.datiSpecifici}">
			<tr>
				<td><fmt:message key="procedimarche.label.procedimentocollegato" /></td>
				<td colspan="3">
                    <a href="javascript:historySet('../procedimarche/view.htm?idProc=${pmCommand.procedimento.datiCollegamento.codiceStp}&software=${pmCommand.procedimento.datiCollegamento.inventarioprocedimenti.software.codice }','../inventarioprocedimenti/view.htm?codice=${pmCommand.procedimento.datiCollegamento.inventarioprocedimenti.id.codice}','');">
					${pmCommand.procedimento.datiCollegamento.inventarioprocedimenti.procedimento}
					</a>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.nodoalberocollegato" /></td>
				<td colspan="3">
                    <a href="javascript:historySet('../procedimarche/view.htm?idProc=${pmCommand.procedimento.datiCollegamento.codiceStp}&software=${pmCommand.procedimento.datiCollegamento.inventarioprocedimenti.software.codice }','../alberoproc/view.htm?codice=${pmCommand.procedimento.datiCollegamento.alberoproc.id.codice}','');">
					${pmCommand.procedimento.datiCollegamento.alberoproc.descrizioneCompleta}
					</a>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.nomeprocedimento" /></td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.nomeProcedimentoEnte" id="nomeproc_id" size="100"/>
					<spring-form:errors path="procedimento.datiSpecifici.nomeProcedimentoEnte" cssClass="error"/></td>
					<spring-form:hidden path="procedimento.datiSpecifici.cfEnte" />
					<spring-form:hidden path="procedimento.datiSpecifici.idProcedimentoEnte" id="inventarioprocedimenti_id"/>
					<spring-form:hidden path="procedimento.datiSpecifici.idTipoProcedimentoSpecifico" />
					<spring-form:hidden path="procedimento.datiCollegamento.id.codice" id="stp_endo_id"/>
					<spring-form:hidden path="procedimento.datiCollegamento.inventarioprocedimenti.amministrazioni.id.codice" id="amministrazione_id"/>
					<c:if test="${empty pmCommand.procedimento.datiCollegamento.alberoproc}">
						<input type="hidden" name="alberoprocid" value="" id="alberoprocid_id"/>
					</c:if>
					<c:if test="${not empty pmCommand.procedimento.datiCollegamento.alberoproc}">
						<input type="hidden" name="alberoprocid" value="${ pmCommand.procedimento.datiCollegamento.alberoproc.id.codice}" id="alberoprocid_id"/>
					</c:if>
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.seriearchivistica" /></td>			
				<td colspan="3">
					<spring-form:select path="procedimento.datiSpecifici.idSerieArchivistica" id="seriearch_id">
						<spring-form:option value=""></spring-form:option>
						<spring-form:options items="${pmCommand.serieArchivistiche}" itemLabel="descrizione" itemValue="id"/>
					</spring-form:select>
				</td>				
			</tr>
			<tr>
				<td>
					<label class="required">*</label>
					<label ><fmt:message key="procedimarche.label.tipofascicolo" /></label>
				</td>			
				<td colspan="3">
					<spring-form:select path="procedimento.datiSpecifici.idTipoFascicolo" id="tipofasc_id" >
						<spring-form:option value=""></spring-form:option>
						<spring-form:options items="${pmCommand.tipiFascicolo}" itemLabel="descrizione" itemValue="id"/>
					</spring-form:select>
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.dettagliotitolario" /></td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.dettaglioTitolario" id="detttitol_id" size="100"/>
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.anniconservazione" /></td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.anniConservazione" id="anniconserv_id" size="5" cssStyle="text-align: right;" onchange="checkNumberInt(this);"/>
				</td>				
			</tr>
			<tr>
				<td>
					<label class="required">*</label>
					<label><fmt:message key="procedimarche.label.competenzaistruttoria" /></label>
				</td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.uoCompetenzaIstruttoria" id="competistr_id" size="100"/>
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.competenzaprovvedimento" /></td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.uoCompetenzaProvvedimentoFinale" id="competprovv_id" size="100"/>
				</td>				
			</tr>
			<tr>
				<td>
					<label class="required">*</label>
					<label><fmt:message key="procedimarche.label.recapitiistruttoria" /></label>
				</td>			
				<td colspan="3">
					<spring-form:textarea path="procedimento.datiSpecifici.uoRecapitiIstruttoria" id="recapistr_id" cols="72" rows="6" />
				</td>				
			</tr>
			<tr>
				<td>
					<label class="required">*</label>
					<label><fmt:message key="procedimarche.label.responsabileistruttoria" /></label>
				</td>			
				<td>
					<label class="required">*</label><label><fmt:message key="procedimarche.label.responsabilenome" /></label><br />
					<spring-form:input path="procedimento.datiSpecifici.responsabileNome" id="respnome_id" size="45"/>
				</td>				
				<td colspan="2">
					<label class="required">*</label><label><fmt:message key="procedimarche.label.responsabilecognome" /></label><br />
					<spring-form:input path="procedimento.datiSpecifici.responsabileCognome" id="respcognome_id" size="45"/>
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.sostitutoinerzia" /></td>			
				<td colspan="3">
					<fmt:message key="procedimarche.label.sostitutonome" /><br />
					<spring-form:input path="procedimento.datiSpecifici.nomeCognomeSostituto" id="sostituto_id" size="100"/>
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.termineconclusione" /></td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.termineConclusione" id="termconcl2_id" size="100"/>
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.maxgiornitermine" /></td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.maxGiorniTermine" id="maxgiorni_id" size="5" cssStyle="text-align: right;" onchange="checkNumberInt(this);"/>
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.attotermine" /></td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.attoDefinizioneTermine" id="attotermine_id" size="100" />
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.linkmodulistica" /></td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.linkModulistica" id="linkmodul_id" size="100" />
				</td>				
			</tr>
			<tr>
				<td>
					<label class="required">*</label>
					<label><fmt:message key="procedimarche.label.linkservizio" /></label>
				</td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.linkServizio" id="linkserv_id" size="100" />
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.modalitapagamento" /></td>			
				<td colspan="3">
					<spring-form:textarea path="procedimento.datiSpecifici.modalitaPagamenti" id="modpagam_id" cols="72" rows="6" />
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.modalitarichiestainfo" /></td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.modalitaRichiestaInfo" id="richinfo_id" size="100" />
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.customersatisfation" /></td>			
				<td colspan="3">
					<spring-form:input path="procedimento.datiSpecifici.customerSatisfation" id="custsatisf_id" size="100" />
				</td>				
			</tr>
			<tr>
				<td><fmt:message key="procedimarche.label.pubblicato" /></td>
				<td colspan="3">
					<spring-form:select path="procedimento.datiSpecifici.pubblicato" id="pubblicato_spec_id" >
						<spring-form:option value=""></spring-form:option>
						<spring-form:option value="true">Si</spring-form:option>
						<spring-form:option value="false">No</spring-form:option>
					</spring-form:select>
				</td>				
			</tr>
		</c:if>
	</table>
    <br />
    
    <script type='text/javascript'>
    
    collega = function(){
    	idPm = jQuery('#inventarioprocedimento_hidden').val();
    	doHref('collegaProcedimento.htm?idInvproc=' + idPm,'');
    }
    
    scollega = function(){
    	idStp = jQuery('#stp_endo_id').val();
    	doHref('scollegaProcedimento.htm?idStpEndo=' + idStp,'');
    }
    
    salvaProcedimento = function(pubblica){
    	var postTo = 'salvaProcedimento.htm?pubblica=';
    	if(pubblica){
    		postTo += 'true';
    	}
    	else {
    		postTo += 'false';
    	}
    	doSubmit(postTo,'',document.inviodati);
    }
    
    spubblicaProcedimento = function(){
    	idStp = jQuery('#stp_endo_id').val();
    	doHref('spubblicaProcedimento.htm?idStpEndo=' + idStp,'');
    	
    }
    
    inventarioCallBack = function(inputField,listItem){
		var a = listItem.id;
		document.getElementById('inventarioprocedimento_id').value = inputField.value;
		document.getElementById('inventarioprocedimento_hidden').value = a;
		$('inventarioprocedimento_id_choices').fade();	
	}
	
    filterinventario = function(element, entry) { 
		return entry + "&codicesoftware=<%= ORMHelper.getSoftware()%>";
	}
    
    var context = "<%= request.getContextPath()%>";
    var canEdit = <%= cmd.isCanEdit()%>;
    jQuery(function(){
        if(canEdit){
	    	var lbl = "<fmt:message key="procedimarche.button.caricadavbg.help" />";
	    	var idAmm = jQuery('#amministrazione_id').val();
	    	var idAproc = jQuery('#alberoprocid_id').val();
	    	var idInvproc = jQuery('#inventarioprocedimenti_id').val();
	    	jQuery('#nomeproc_id').autofill({serviceUrl: context + '/procedimarche/findNomeProcedimento.htm', serviceParams: {codiceInventarioProc: idInvproc}, label: lbl});
	    	jQuery('#competistr_id').autofill({serviceUrl: context + '/procedimarche/findDescrizioneEstesaAmministrazione.htm', serviceParams: {codiceAmministrazione: idAmm}, label: lbl});
	    	jQuery('#respnome_id').autofill({serviceUrl: context + '/procedimarche/findResponsabile.htm', serviceParams: {codiceAlberoproc: idAproc}, label: lbl});
	    	jQuery('#respcognome_id').autofill({serviceUrl: context + '/procedimarche/findResponsabile.htm', serviceParams: {codiceAlberoproc: idAproc}, label: lbl});
	    	jQuery('#recapistr_id').autofill({serviceUrl: context + '/procedimarche/findRecapitiIstruttoriaAmministrazione.htm', serviceParams: {codiceAmministrazione: idAmm}, label: lbl});
	    	jQuery('#termconcl2_id').autofill({serviceUrl: context + '/procedimarche/findTermineConclusione.htm', serviceParams: {codiceInventarioProc: idInvproc}, label: lbl});
	    	jQuery('#maxgiorni_id').autofill({serviceUrl: context + '/procedimarche/findMaxGiorniTermine.htm', serviceParams: {codiceAlberoproc: idAproc}, label: lbl});
	    	jQuery('#linkserv_id').autofill({serviceUrl: context + '/procedimarche/findUrlProcedimentoFrontOffice.htm', serviceParams: {idproc: idAproc}, label: lbl});
        }
        else{
        	disableUI();
        }
    });
    
    disableUI = function(){
    	var inputFields = jQuery('#procedimarche_tab').find(":input");
    	inputFields.each(function(index){
    		if(this.disabled == false){
    			this.disabled = true;
    		}
    	})
    }
	</script>	
    
</spring-form:form>
</div> 
<%} %>
<div id="functions">
<ul>
	<c:if test="${pmCommand.canEdit}">
		<c:if test="${empty pmCommand.procedimento.datiSpecifici}">
			<li><a href="javascript:collega()" title="<fmt:message key="procedimarche.button.collegaprocedimento.help"/>"><fmt:message key="procedimarche.button.collegaprocedimento"/></a></li>
		</c:if>
		<c:if test="${not empty pmCommand.procedimento.datiSpecifici}">
			<li><a href="javascript:scollega()" title="<fmt:message key="procedimarche.button.scollegaprocedimento.help"/>"><fmt:message key="procedimarche.button.scollegaprocedimento" /></a></li>
			<li><a href="javascript:salvaProcedimento(false)" title="<fmt:message key="procedimarche.button.salvaprocedimento.help" />"><fmt:message key="procedimarche.button.salvaprocedimento" /></a></li>
			<li><a href="javascript:salvaProcedimento(true)" title="<fmt:message key="procedimarche.button.pubblicaprocedimento.help" />"><fmt:message key="procedimarche.button.pubblicaprocedimento" /></a></li>
			<c:if test="${not empty pmCommand.procedimento.datiSpecifici.idTipoProcedimentoSpecifico}">
				<li><a href="javascript:spubblicaProcedimento()" title="<fmt:message key="procedimarche.button.spubblicaprocedimento.help" />"><fmt:message key="procedimarche.button.spubblicaprocedimento" /></a></li>
			</c:if>
		</c:if>
	</c:if>
	<li><a href="javascript:doHref('../history/back.htm?GoTo=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
