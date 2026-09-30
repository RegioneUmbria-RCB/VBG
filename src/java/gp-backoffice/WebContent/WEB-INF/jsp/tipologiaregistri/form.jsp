<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<c:if test="${tipologiaregistri.id.codice==null}">
			<fmt:message key="tipologiaregistri.label.nuovo_tipologiaregistri.title" />
		</c:if> 
		<c:if test="${tipologiaregistri.id.codice!=null}">
			<fmt:message key="tipologiaregistri.label.dettaglio_tipologiaregistri.title" />
		</c:if>
	</title>
	<style type="text/css">
		select#comune {
		    margin-left: 94px;
		}
	</style>
</head>
	<body>
		<span class="titoloPagina">
			<c:if test="${tipologiaregistri.id.codice==null}">
				<fmt:message key="tipologiaregistri.label.nuovo_tipologiaregistri.title" />
			</c:if> 
			<c:if test="${tipologiaregistri.id.codice!=null}">
				<fmt:message key="tipologiaregistri.label.dettaglio_tipologiaregistri.title" />
			</c:if>
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
				<jsp:param name="path" value="../tipologiaregistri/view" />	
		</jsp:include>
		<div id="subcontent">
			<spring-form:form commandName="tipologiaregistri" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipologiaregistri" />
		    </jsp:include>
		    
		    <div class="vbg-form">
		    <fieldset>
		    	<legend><fmt:message key="tipologiaregistri.label.tipologiaregistro"/></legend>		    
		    	<div class="form-group">		    		
		    		<jsp:include page="../includes/comboComuni.jsp">
							<jsp:param name="mostraTutti" value="true" />
							<jsp:param name="emptyLabelTutti" value="false" />						
							<jsp:param name="readOnly" value="${isReadOnly}" />
							<jsp:param name="commandPropertyPath" value="comune" />
							<jsp:param name="colspan" value="6" />
							<jsp:param name="comune" value="${tipologiaregistri.comune.codicecomune}" />
							<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
						</jsp:include>
		    	</div>
		    	<div class="form-group">
		    		<label><fmt:message key="tipologiaregistri.label.trdescrizione" /></label>
		    		<spring-form:input id="trDescrizione_id" path="trDescrizione" size="70" />
		    	</div>
		    	<div class="form-group">
		    		<label><fmt:message key="tipologiaregistri.label.trFlagprotocollo" /></label>		    		
					<spring-form:checkbox id="trFlagprotocollo_id" path="trFlagprotocollo"/>
					<div class="input-help"><fmt:message key="tipologiaregistri.help.trFlagprotocollo" /></div>					
					<spring-form:errors path="trFlagprotocollo" cssClass="error"/>
		    	</div>
		    	<div class="form-group">
		    		<label><fmt:message key="tipologiaregistri.label.flag_usa_progr_conf" /></label>
			    	<spring-form:checkbox id="flagUsaProgrConf_id" path="flagUsaProgrConf"/>
			    	<div class="input-help"><fmt:message key="tipologiaregistri.label.flag_usa_progr_conf.help" /></div>					
					<spring-form:errors path="flagUsaProgrConf" cssClass="error"/>
		    	</div>
		    	<div class="form-group">
		    		<label><fmt:message key="tipologiaregistri.label.flag_manifestazioni" /></label>
			    	<spring-form:checkbox id="flagManifestazioni_id" path="flagManifestazioni"/>
			    	<div class="input-help"><fmt:message key="tipologiaregistri.label.flag_manifestazioni.help" /></div>					
					<spring-form:errors path="flagManifestazioni" cssClass="error"/>
		    	</div>
		    	<c:if test="${tipologiaregistri.trFlagprotocollo==true}">
		    	<div class="form-group">
		    		<label><fmt:message key="tipologiaregistri.label.trProgressivo" /></label>
		    		<spring-form:input id="trProgressivo_id" path="trProgressivo" size="15" readonly="true" />
					 <spring-form:errors path="trProgressivo" cssClass="error"/>
		    	</div>			       
	        	</c:if>
	        	<c:if test="${tipologiaregistri.trFlagprotocollo==false  || tipologiaregistri.trFlagprotocollo==null}">
	        	<div class="form-group">
	        		<label><fmt:message key="tipologiaregistri.label.trProgressivo" /></label>
	        		<spring-form:input id="trProgressivo_id" path="trProgressivo" size="15"  />
					<spring-form:errors path="trProgressivo" cssClass="error"/>
		    	</div>	        	
		        </c:if>
		        
				<div class="form-group">
		    		<label><fmt:message key="tipologiaregistri.label.numerazioneCustom" /></label>
		    		<spring-form:select id="numerazioneCustom_id" path="numerazioneCustom">
		    			<spring-form:option value=""><fmt:message key='label.seleziona'/></spring-form:option>
			    		<c:forEach items="${elencoImplementazioniCustom}" var="implementazione">
			    			<spring-form:option value="${implementazione.key}">${implementazione.value}</spring-form:option>
			    		</c:forEach>
		    		</spring-form:select>
		    		<spring-form:errors path="numerazioneCustom" cssClass="error"/>
		    	</div>
		        
		        
		        
		    	<div class="form-group">
		    		<label><fmt:message key="tipologiaregistri.label.trFlagdataauto" /></label>
					<spring-form:checkbox id="trFlagdataauto_id" path="trFlagdataauto"/>
					<div class="input-help"><fmt:message key="tipologiaregistri.help.trFlagdataauto" /></div>
					<spring-form:errors path="trFlagdataauto" cssClass="error"/>
		    	</div>		    	
		    	<c:if test="${isDocEr eq true}">
		    	<div class="form-group">
		    		<label><fmt:message key="label.cod_docer" /></label>
		    		<spring-form:input id="codRegistroDocer_id" path="codRegistroDocer" size="50" />
					<spring-form:errors path="codRegistroDocer" cssClass="error"/>
		    	</div>					
				</c:if>
		    	<div class="form-group">
			    	<label>Gestisci data anzianità</label>
			    	<spring-form:checkbox id="flagGestanzianita_id" path="flagGestanzianita"/>
					<div class="input-help"><fmt:message key="tipologiaregistri.help.flagGestanzianita" /></div>
					<spring-form:errors path="flagGestanzianita" cssClass="error"/>
		    	</div>
		    	<div class="form-group">
		    	</div>
	    	</fieldset>
	    	
	    		 <c:if test="${isVerticalizzazioneAUTORIZACCESSIAttiva}">
	    		<fieldset>
	    			<legend><init:editLabel key="label.configurazione_gestione_accessi" role="ROLE_EDITLABEL"/></legend>	    		
			    	<div class="form-group">
				    	<label><fmt:message key="tipologiaregistri.label.numero_proroghe"/></label>
				    	<spring-form:input id="numeroProroghe_id" path="numeroProroghe" size="4" />
			    	</div>
			    	<div class="form-group">
			    		<label><fmt:message key="tipologiaregistri.label.numero_rinnovi"/></label>
			    		<spring-form:input id="numeroRinnovi_id" path="numeroRinnovi" size="4" />
			    	</div>
			    	<div class="form-group">
			    		<label><fmt:message key="tipologiaregistri.label.numero_preavvisi"/></label>
			    		<spring-form:input id="numeroPreavvisi_id" path="numeroPreavvisi" size="4" />
			    	</div>
			    	<div class="form-group">
			    		<label><fmt:message key="tipologiaregistri.label.durata_autorizzazione"/></label>
			    		<spring-form:input id="durataAutorizzazione_id" path="durataAutorizzazione" size="20"/>
						<div class="input-help"><fmt:message key="tipologiaregistri.label.durata_autorizzazione.help"/></div>
			    	</div>			    	
	    		</fieldset>
	    		</c:if>
		    </div>
		<script type='text/javascript'>
			$('trDescrizione_id').focus();
		</script>	
	</spring-form:form>
	</div>
	<div class="form-button">	
		<c:if test="${tipologiaregistri.id.codice==null}">
			<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
		</c:if>
		<c:if test="${tipologiaregistri.id.codice!=null}">
			<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
			<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
		    <c:if test="${tipologiaregistri.trFlagprotocollo}">
		         <a class="btn btn-primary" href="javascript:historySet('${_urlback }','../tipologiaregistri/listProtocolloRegistri.htm?codiceRegistro=${tipologiaregistri.id.codice}','')"><fmt:message key="tipologiaregistri.button.parametri_protocollo" /></a>
		    </c:if>
		</c:if>
		<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>	
	</div>
	</body>
</html>
