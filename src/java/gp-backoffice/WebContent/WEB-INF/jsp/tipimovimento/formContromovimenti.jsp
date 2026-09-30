<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${contromovimento.id.codice==null}">
			<fmt:message key="contromovimento.label.nuovo_contromovimento.title" />
		</c:if> 
		<c:if test="${contromovimento.id.codice!=null}">
			<fmt:message key="contromovimento.label.dettaglio_contromovimento.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${contromovimento.id.codice==null}">
			<fmt:message key="contromovimento.label.nuovo_contromovimento.title" />
		</c:if> 
		<c:if test="${contromovimento.id.codice!=null}">
			<fmt:message key="contromovimento.label.dettaglio_contromovimento.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
    		<jsp:param name="path" value="../tipimovimento/viewContromovimento" />
	</jsp:include>
	
<script type="text/javascript">
	function tuttiSw(){
		if($('id_flag').checked){
		    $('id1').style.display="inline";
		    $('id2').style.display="none";
		}else
		{
			$('id1').style.display="none";
			$('id2').style.display="inline";
		}
	}	
</script>
<%
     String  swSettato="display:none;";
     String  swTT="display:inline;";
%>
	<div id="subcontent">
	    <div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="tipimovimento.label.codice" />:</div>
				<div><fmt:message key="tipimovimento.label.movimento" />:</div>
	    	</div>
	    	<div class="parametro">
	    	  <div><c:out value="${contromovimento.tipomovimento.id.tipomovimento}" /></div>
	    	  <div><c:out value="${contromovimento.tipomovimento.movimento}" /></div>
			</div>
	     </div>
	     <br class="clear" />
		 <spring-form:form commandName="contromovimento" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="contromovimento" />
		    </jsp:include>
		<table>
			<tr>
				<td>
					<fmt:message key="contromovimento.label.datacreazione" />
				</td>
				<td>
					<spring-form:input id="datacreazione_id" path="datacreazione"  onblur="isValidDate(this,true);" size="8" />
					<init:calendar idImage="caldatacreazione" idInput="datacreazione_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
					<spring-form:errors path="datacreazione" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="tipimovimento.label.codice_tipocontromovimento" />
				</td>
				<td>
				<c:if test="${contromovimento.id.codice==null}">
				<jsp:include page="../includes/tipimovimentosearch.jsp" >
					<jsp:param name="idElemento" value="tipoMovimentoInputId" />
					<jsp:param name="pathTipomovimento" value="tipocontromovimento" />
				</jsp:include>
				</c:if>
				<c:if test="${contromovimento.id.codice!=null}">
					<spring-form:input  path="tipocontromovimento.movimento" readonly="true" size="73"/>
					<a style="float: right;" class="dettaglioColumn" href="javascript:historySet('${_urlback}','../tipimovimento/createChangeTipoContromovimento.htm?codiceControMov=${contromovimento.id.codice}')" title="<fmt:message key="label.edit.record" />&nbsp;${tipimovimento_var.id.tipomovimento}">
						<label><fmt:message key="label.edit.record.image" /></label>
					</a>
				</c:if>	
				
				</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.amministrazione_effettua_mov" />
					</td>
					<td>
						<spring-form:input id="amministrazioniTipiMovimento_id" path="amministrazioniTipiMovimento.amministrazione" size="70" cssClass="searchbox" onchange="checkValue(this,'amministrazioniTipiMovimento_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
						<init:autocompleter methodAjax="findAmministrazioni.htm?tutteLeAmministrazioni=true&codiciAmministrazioneEsclusi=-2" idHidden="amministrazioniTipiMovimento_hidden" idInput="amministrazioniTipiMovimento_id" inputTitleKey="label.ricerca_amministrazione"></init:autocompleter>
						<spring-form:errors path="amministrazioniTipiMovimento" cssClass="error"/> 
						<spring-form:hidden id="amministrazioniTipiMovimento_hidden" path="amministrazioniTipiMovimento.id.codice"  />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.procedura_valido" />
					</td>
					<td>
						<spring-form:input id="tipiprocedure_id" path="tipiprocedure.descrizioneEstesa" cssClass="searchbox" size="70" onchange="checkValue(this,'tipiprocedure_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
						<init:autocompleter methodAjax='findTipiprocedure.htm'  idHidden="tipiprocedure_hidden"  idInput="tipiprocedure_id" inputTitleKey="label.ricerca_tipiprocedure"></init:autocompleter>
						<spring-form:errors path="tipiprocedure" cssClass="error"/> 
						<spring-form:hidden id="tipiprocedure_hidden" path="tipiprocedure.id.codice"  />					    		                
		            </td>					
				</tr>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.amministrazione_effettua_contromov" />
					</td>
					<td>
						<spring-form:input id="amministrazioniTipiContromovimento_id" path="amministrazioniTipiContromovimento.amministrazione" size="70" cssClass="searchbox" onchange="checkValue(this,'amministrazioniTipiContromovimento_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
						<init:autocompleter methodAjax="findAmministrazioni.htm?tutteLeAmministrazioni=true&codiciAmministrazioneEsclusi=-1" idHidden="amministrazioniTipiContromovimento_hidden" idInput="amministrazioniTipiContromovimento_id" inputTitleKey="label.ricerca_amministrazione"></init:autocompleter>
						<spring-form:errors path="amministrazioniTipiContromovimento" cssClass="error"/> 
						<spring-form:hidden id="amministrazioniTipiContromovimento_hidden" path="amministrazioniTipiContromovimento.id.codice"  />
					</td>
				</tr>
				<tr>
					<td colspan="2" style="color: maroon;font-size: 1.1em;font-weight: bolder; text-wrap: balance; padding-left: 55px;">
					<fmt:message key="tipimovimento.label.amministrazione_effettua_contromov.help" /></td>									
				</tr>				
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.cont_mov_obbligatorio"/>
					</td>
					<td>
						<spring-form:checkbox id="flagbase_id" path="flagbase"/>
						<spring-form:errors path="flagbase" cssClass="error"/>
					</td>
				</tr>
				

				<c:if test="${contromovimento.tipomovimento.flagInterruzione == false && contromovimento.tipomovimento.flagProroga == false
				&& contromovimento.tipomovimento.flagRichiestaintegrazione == false}">
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.comportamento"/>
					</td>
					<td>
					    <spring-form:radiobutton id="soloseesitonegativo_0" path="soloseesitonegativo" value="0" />
					    <label for="soloseesitonegativo_0"><fmt:message key="label.sempre"/></label>
					    <spring-form:radiobutton id="soloseesitonegativo_1" path="soloseesitonegativo" value="1" />
					    <label for="soloseesitonegativo_1"><fmt:message key="tipimovimento.label.se_negativo" /></label>
					    <spring-form:radiobutton id="soloseesitonegativo_2" path="soloseesitonegativo" value="2" />
					    <label for="soloseesitonegativo_2"><fmt:message key="tipimovimento.label.se_positivo" /></label>
						<spring-form:errors path="soloseesitonegativo" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<c:if test="${isVerticalizzazioneSTCAttiva eq true}">
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.comportamento_stc"/>
					</td>
					<td>
						<spring-form:radiobutton path="propostostc" id="propostostc_0" value="0" />
					    <label title="<fmt:message key="tipimovimento.label.title_sempre"/>" for="propostostc_0">
					    	<fmt:message key="label.sempre"/>
					    </label>
				    	<spring-form:radiobutton path="propostostc" id="propostostc_1" value="1" />
					    <label title="<fmt:message key="tipimovimento.label.title_se_istanza_creata_stc"/>" for="propostostc_1">
					    	<fmt:message key="tipimovimento.label.se_istanza_creata_stc" />
					    </label>
				    	<spring-form:radiobutton path="propostostc" id="propostostc_2" value="2"/>
					    <label title="<fmt:message key="tipimovimento.label.title_se_istanza_non_creata_stc"/>" for="propostostc_2">
					    	<fmt:message key="tipimovimento.label.se_istanza_non_creata_stc" />
					    </label>
						<spring-form:errors path="propostostc" cssClass="error"/>
					</td>
				</tr>
				</c:if>
				<tr>
					<td>
						<fmt:message key="tipimovimento.label.data_antecedente"/>
					</td>
					<td>
						<spring-form:checkbox id="seprecedente_id" path="seprecedente"/>
						<fmt:message key="tipimovimento.label.descrizione_data_antecedente" />
						<spring-form:errors path="seprecedente" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('datacreazione_id').focus();
			</script>
			
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${contromovimento.id.codice==null}">
				<li><a href="javascript:doSubmit('insertContromovimento.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${contromovimento.id.codice!=null}">
				<li><a href="javascript:doSubmit('updateContromovimento.htm','Attenzione, cambiando amministrazione del movimento o procedura, verranno eliminati i tempi di attesa non più validi',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('deleteContromovimento.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('view.htm?codice=${contromovimento.tipomovimento.id.tipomovimento}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>