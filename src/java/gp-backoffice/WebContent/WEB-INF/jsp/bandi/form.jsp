<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${bandi.id.codice==null}">
			<fmt:message key="form.bandi.title.create" />
		</c:if>
		<c:if test="${bandi.id.codice!=null}">
			<fmt:message key="form.bandi.title.view" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
	<c:if test="${bandi.id.codice==null}">
		<fmt:message key="form.bandi.title.create" />
	</c:if>
	<c:if test="${bandi.id.codice!=null}">
		<fmt:message key="form.bandi.title.view" />
	</c:if>
	</span>
<%int tabIndex = 0; %>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../bandi/view" />
</jsp:include>
<div id="subcontent">
<spring-form:form commandName="bandi" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="bandi" />
	</jsp:include>
	<div class="vbg-form">
		<fieldset>
		<legend id="legend_dati_generali" ><fmt:message key="label.dati_generali"/></legend>
		
		<div class="form-group">
			<label><fmt:message key="form.bandi.descrizione" /></label>
			<spring-form:input tabindex="1" id="descrizione_id" path="descrizione" size="70" />
			<spring-form:errors path="descrizione" cssClass="error" />
		</div>
		
		<div class="form-group">
			<label><fmt:message key="form.bandi.datapubblicazione" /></label>
			<spring-form:input tabindex="2" id="datapubblicazione_id" path="datapubblicazione" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
			<init:calendar imagePath="/images/cal.gif" idImage="calDataPubblicazione" idInput="datapubblicazione_id" textKey="label.calendar"/>
			<spring-form:errors	path="datapubblicazione" cssClass="error" />
			
		</div>
		
		<div class="form-group">
			<label><fmt:message key="form.bandi.datascadenza" /></label>
			
			<spring-form:input tabindex="3" id="datascadenza_id" path="datascadenza" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
			<init:calendar imagePath="/images/cal.gif" idImage="calDataScadenza" idInput="datascadenza_id" textKey="label.calendar"/>
	   		<spring-form:errors path="datascadenza" cssClass="error" />
			
		</div>
		
		<div class="form-group">
			<label><fmt:message key="label.numero_determina_approvazione" /></label>
			<spring-form:input tabindex="6" id="descrizione_id" path="numeroDetermina" size="20" />
			<spring-form:errors path="numeroDetermina" cssClass="error" />
		</div>
		<div class="form-group">
			<label><fmt:message key="label.data_determina_approvazione" /></label>
			<spring-form:input tabindex="7" id="dataDetermina_id" path="dataDetermina" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
			<init:calendar imagePath="/images/cal.gif" idImage="calDataDetermina" idInput="dataDetermina_id" textKey="label.calendar"/>
	   		<spring-form:errors path="dataDetermina" cssClass="error" />
		</div>
	</fieldset>
	<fieldset>
		<legend><fmt:message key="label.filtri"/></legend>
		<div class="form-group">
			<label><fmt:message key="form.bandi.tipibando" /></label>
			<c:if test="${tipibandoList == null }">
				<b>${ bandi.tipibando.descrizione }</b>
			</c:if>
			<c:if test="${tipibandoList != null }">
				<spring-form:select tabindex="4" path="tipibando.id.codice" >
				    <spring-form:option value="xxx"><fmt:message key="label.select.default"/></spring-form:option>
					<spring-form:options items="${tipibandoList}" itemValue="id.codice"	itemLabel="descrizione" />
				</spring-form:select> 
				<spring-form:errors path="tipibando" cssClass="error" />
			</c:if>
			
		</div>
		<div class="form-group">
			<label><fmt:message key="form.bandi.alberoproc" /></label>			
			<c:if test="${bandi.id.codice==null}">
				<script type='text/javascript'>
				function setHiddenFieldalberoproc(inputField, listItem) {
					var a = listItem.id;
					document.getElementById('alberoproc_id').value = inputField.value;
					document.getElementById('alberoproc_hidden').value = a;
					<%-- 
					Assegnazione al campo nascosto effettuata per evitare errore di validazione: siccome visualizziamo alberoproc.vwAlberoproc.scDescrizione
					e l'oggetto di dominio ha il controllo di validazione @Valid allora trovando alberoproc.scDescrizione vuoto o nullo
					da errore nella validazione (vedi it.gruppoinit.pal.gp.core.domain.Alberoproc.getVwAlberoproc())
					--%>
					document.getElementById('alberoproc_hidden_descrizione').value = inputField.value;
					$('alberoproc_id_choices').fade();
				}
				</script>
				<spring-form:textarea id="alberoproc_id"  tabindex="5" path="alberoproc.vwAlberoproc.scDescrizione" cssClass="searchbox" onchange="checkValue(this,'alberoproc_hidden')" onkeydown="javascript:return searchAll(this,event)" cols="62" rows="2" />
				<init:autocompleter methodAjax="findAlberoproc.htm" afterUpdateElement="setHiddenFieldalberoproc" idHidden="alberoproc_hidden" idInput="alberoproc_id" inputTitleKey="label.ricerca_intervento"/>
				<spring-form:errors path="alberoproc" cssClass="error"/> 
				<spring-form:hidden	id="alberoproc_hidden" path="alberoproc.id.codice"/>
				<%-- 
					Campo nascosto per evitare errore di validazione: siccome visualizziamo alberoproc.vwAlberoproc.scDescrizione
					e l'oggetto di dominio ha il controllo di validazione @Valid allora trovando alberoproc.scDescrizione vuoto o nullo
					da errore nella validazione (vedi it.gruppoinit.pal.gp.core.domain.Alberoproc.getVwAlberoproc())
				--%>
				<spring-form:hidden	id="alberoproc_hidden_descrizione" path="alberoproc.scDescrizione"/>
			</c:if>	
			<c:if test="${bandi.id.codice!=null}">
				<b>${ bandi.alberoproc.vwAlberoproc.scDescrizione }</b>
				<spring-form:hidden	id="alberoproc_id" path="alberoproc.vwAlberoproc.scDescrizione"/>
				<spring-form:hidden	id="alberoproc_hidden" path="alberoproc.id.codice"/>
				<spring-form:hidden	id="alberoproc_hidden_descrizione" path="alberoproc.scDescrizione"/>
			</c:if>
		</div>
		 <div class="form-group">
		 	<label><fmt:message key="label.data_presentazione" /></label>
			<span><fmt:message key="label.data.inizio" /></span>
			<spring-form:input tabindex="2" id="data_alla_id" path="dataDalla" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
			<init:calendar imagePath="/images/cal.gif" idImage="calDataDalla" idInput="data_alla_id" textKey="label.calendar"/>
			<spring-form:errors	path="dataDalla" cssClass="error" />
			&nbsp;&nbsp;&nbsp;
			<span><fmt:message key="label.data.fine" /></span>
			<spring-form:input tabindex="2" id="data_alla" path="dataAlla" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
			<init:calendar imagePath="/images/cal.gif" idImage="calDataAlladat" idInput="data_alla" textKey="label.calendar"/>
			<spring-form:errors	path="dataAlla" cssClass="error" />
		</div>
	              
	</fieldset>
	<%tabIndex = 8; %>
		<c:if test="${ bandi.bandiinputs != null &&  not empty bandi.bandiinputs}">
		<div class="form-group">
			<fieldset>
				<legend><fmt:message key="bandi.label.dati_aggiuntivi"/></legend>
				<c:forEach items="${bandi.bandiinputs}" var="current" varStatus="a">
						<label>${current.tipibandoinput.etichetta}</label>
						<spring:bind path="bandiinputs[${a.index}].tipibandoinput.id.codice">
							<input type="hidden" name="${status.expression}" value="${status.value}" />
						</spring:bind> 
						<spring:bind path="bandiinputs[${a.index}].valore">
							<input tabindex="<%=tabIndex++%>" type="text" name="${status.expression}" onblur="checkNumberValue(this);" value="${status.value}" />					 
						</spring:bind>
						<spring-form:errors path="bandiinputs[${a.index}].valore" cssClass="error" />
				</c:forEach>
			</c:if>	
		</fieldset>
	</div
			
	</div>
	
	
</spring-form:form>
</div>
<script type='text/javascript'>
	$('descrizione_id').focus();
</script>
<%
	pageContext.setAttribute("URL_STAMPA_LETTERE_TIPO",BackofficeNETConstants.getURL_STAMPA_LETTERE_TIPO());
%>
	<c:set var="_URL_STAMPA_LETTERE_TIPO" value="${URL_STAMPA_LETTERE_TIPO}?idbando=${bandi.id.codice}"/>	
	<c:set var="_URL_STAMPA_LETTERE_TIPO" value="${inite:geturlto(pageContext.request, _URL_STAMPA_LETTERE_TIPO, _urlback, null, true)}" />
<div id="functions">
<ul>
	<c:if test="${bandi.id.codice==null && tipibandoList != null}">
		<li><a tabindex="<%=tabIndex++%>" href="javascript:doSubmit('nextPhase.htm','',document.inviodati)"><fmt:message key="button.forward" /></a></li>
	</c:if>
	<c:if test="${bandi.id.codice==null && bandi.bandiinputs!=null && tipibandoList == null}">
		<li><a tabindex="<%=tabIndex++%>" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${bandi.id.codice!=null}">
		<li><a tabindex="<%=tabIndex++%>" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a tabindex="<%=tabIndex++%>" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		<li><a tabindex="<%=tabIndex++%>" href="../bandiallegati/list.htm?codice=${bandi.id.codice}"><fmt:message key="button.bandi.allegati" /></a></li>
		<c:if test="${bandi.alberoproc.mercato.id.codice != null}">
			<li><a tabindex="<%=tabIndex++%>" href="javascript:historySet('${_urlback}','../autorizzazioni/createCessaConcessioniManifestazione.htm?codiceMercato=${bandi.alberoproc.mercato.id.codice}&codiceUso=${bandi.alberoproc.mercatoUso.id.codice}');"><fmt:message key="button.cessa_concessioni" /></a></li>
		</c:if>
		<li><a href="javascript:void 0"	onclick="window.open('${_URL_STAMPA_LETTERE_TIPO}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message	key="button.print" /></a></li>
	</c:if>
	<c:if test="${bandi.id.codice != null && bandi.tipibando.flagMultiintervento}">
	    <li><a tabindex="<%=tabIndex++%>" href="javascript:historySet('${_urlback}','../bandialberoproc/list.htm?codiceBandi=${bandi.id.codice}');"><fmt:message key="button.configura_albero_proc_tipi_bando" /></a></li>
	</c:if>
	
	<li><a  tabindex="<%=tabIndex++%>" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
<c:if test="${(bandi.id.codice != null)}">
<br/><br/><br/>


<span class="titoloTabella"><fmt:message key="form.bandi.graduatoriet.title" /></span>
		<form name="graduatorieForm" action="view.htm">
				<jmesa:springTableFacade
					id="graduatorie_id" 
					items="${bandi.graduatoriets}" 
					var="graduatorie_var"
					exportTypes="" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="viewGraduatoria.htm?codice=${graduatorie_var.id.codice}">${graduatorie_var.id.codice}</a>
                            </jmesa:htmlColumn>							
							<jmesa:htmlColumn property="descrizione" titleKey="form.graduatoriet.descrizione" width="88%"/>
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="10%">								
								<a class="dettaglioColumn" href="viewGraduatoria.htm?codice=${graduatorie_var.id.codice}" 
									title="<fmt:message key="label.edit.record" /> ${graduatorie_var.descrizione}">
								<label><fmt:message key="label.edit.record.image" /></label></a>
								
								<c:set var="_URL_STAMPA_LETTERE_TIPO_G" value="${URL_STAMPA_LETTERE_TIPO}?idgraduatoria=${graduatorie_var.id.codice}"/>	
								<c:set var="_URL_STAMPA_LETTERE_TIPO_G" value="${inite:geturlto(pageContext.request, _URL_STAMPA_LETTERE_TIPO_G, _urlback, null, true)}" />
								<a class="stampaColumn" href="javascript:void 0" onclick="window.open('${_URL_STAMPA_LETTERE_TIPO_G}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"
									title="<fmt:message key="button.print" /> ${graduatorie_var.descrizione}">
								<label><fmt:message key="label.edit.record.image" /></label></a>
								
								<a class="emailColumn" href="javascript:historySet('${_urlback}','../graduatorietcom/list.htm?codiceGraduatoria=${graduatorie_var.id.codice}','')" 
									title="<fmt:message key="button.gestioni_comunicazioni" /> ${graduatorie_var.descrizione}">
								<label><fmt:message key="label.edit.record.image" /></label></a>								
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${bandi.id.codice}" name="codice"/>
			</form>
<div id="functions">
<ul>			
	<li><a tabindex="<%=tabIndex++%>" href="javascript:doSubmit('createGraduatoria.htm?bandoid=${bandi.id.codice}','',document.inviodati)"><fmt:message key="button.bandi.graduatoria.create" /></a></li>		
</ul>
</div>
</c:if>
</body>
</html>
