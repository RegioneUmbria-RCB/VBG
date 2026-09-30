<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${taskscheduler.displayMode==taskscheduler.displayConstants.NEW}">
			<fmt:message key="taskscheduler.label.nuovo_taskscheduler.title" />
		</c:if> 
		<c:if test="${taskscheduler.displayMode==taskscheduler.displayConstants.VIEW}">
			<fmt:message key="taskscheduler.label.dettaglio_taskscheduler.title" />
		</c:if>
	</title>
    <script type="text/javascript">
	    vbg.ready(() => {
	    	document.getElementById('help2_tooltip').textContent = document.querySelector('#operazione_id').querySelector('option:checked').title;
	    	
	    	document.getElementById('giorni_id').addEventListener("change", (el) =>{
	    	    el.preventDefault();
                var value=el.target.value.replace(",",".");
                if (isNaN(value) || value.indexOf(".")>0 || value<0  ){
                    alert('Devi inserire un numero intero positivo');
                    el.target.value="";
                    el.target.focus();
                    return false;
                } 
                return true;
	    	});
	    	
	    	document.getElementById('ore_id').addEventListener("change", (el) =>{
	    	    el.preventDefault();
	    	    var value=el.target.value.replace(",",".");
                if (isNaN(value) || value.indexOf(".")>0 || value<0 || value>23){
                    alert('Devi inserire un numero intero positivo compreso tra 0 e 23');
                    el.target.value="";
                    el.target.focus();
                    return false;
                } 
                
                return true;
	        });
	    	document.getElementById('minuti_id').addEventListener("change", (el) =>{
	    	    el.preventDefault();
	    	    var value=el.target.value.replace(",",".");
                if (isNaN(value) || value.indexOf(".")>0 || value<0 || value>59 ){
                    alert('Devi inserire un numero intero positivo compreso tra 0 e 59');
                    el.target.value="";
                    el.target.focus();
                    return false;
                } 
                return true;
            });
	    });
    </script>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="taskscheduler.label.lista_taskscheduler.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="taskscheduler" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		      <jsp:param name="commandName" value="taskscheduler" />
		    </jsp:include>
		    <div id="form" class="vbg-form">
			    <fieldset>
			      <legend><fmt:message key="taskscheduler.label.dati_generali" /></legend>
			      <div class="form-group">
	                  <label><fmt:message key="label.operazione" /></label>
					  <spring-form:select id="operazione_id" path="entity.taskbase.task" onchange="impostahelp2()">
	    				<c:forEach items="${taskscheduler.operazioneList}" var="operazione">
		           			<spring-form:option value="${operazione.task}" label="${operazione.task}" title="${operazione.descrizione}"></spring-form:option>                               
					   	</c:forEach>                            
					  </spring-form:select>
					  <init:help idHelp="help2"/>
	                  <spring-form:errors path="entity.taskbase" cssClass="error"/>
			      </div>
			      <div class="form-group">
	                  <label><fmt:message key="label.descrizione_operazione" /></label>
	                  <spring-form:textarea id="descrizione_id" path="entity.descrizione" rows="4" cols="70"/>
	                  <spring-form:errors path="entity.descrizione" cssClass="error"/>
			      </div>
			      <div class="form-group">
	                  <label><fmt:message key="taskscheduler.label.esegui_operazione_ogni" /></label>
	                  <spring-form:input id="giorni_id" path="giorni" size="3" maxlength="3" cssStyle="text-align:right;"/>                   
	                  <spring-form:input id="ore_id" path="ore" size="2" maxlength="2" cssStyle="text-align:right;" />
	                  <spring-form:input id="minuti_id" path="minuti" size="2" maxlength="2" cssStyle="text-align:right;"/>
	                  <spring-form:errors path="entity.intervallo" cssClass="error"/> 
			      </div>
			      <div class="form-group">
	                  <label><fmt:message key="taskscheduler.label.prossimaesecuzione" /></label>
	                  <spring-form:input  tabindex="6" id="prossimaesecuzione_id" path="entity.prossimaesecuzione" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
	                  <init:calendar idImage="caldata1" idInput="prossimaesecuzione_id" imagePath="/images/cal.gif" textKey="label.calendar"/>
	                  <spring-form:errors path="entity.prossimaesecuzione" cssClass="error" />
	                  <spring-form:input id="hhmmss_id" path="hhmmss" size="8" maxlength="8" onblur="isValidTime(this,true);"/>
	                  <init:help idHelp="help3" textKey="taskscheduler.help.prossimaesecuzione"/>
			      </div>
			      <div class="form-group">
			          <label><fmt:message key="label.attivo" /></label>
	                  <spring-form:checkbox id="attivo_id" path="entity.attivo" value="1" />
	                  <spring-form:errors path="entity.attivo" cssClass="error"/>
			      </div>
			      <div class="form-group">
			          <label><fmt:message key="label.in_esecuzione" /></label>
	                  <spring-form:checkbox id="inesecuzione_id" path="entity.inesecuzione" value="1" />
	                  <init:help idHelp="help5" textKey="taskscheduler.help.inesecuzione"/>   
	                  <spring-form:errors path="entity.inesecuzione" cssClass="error"/>
			      </div>
			    </fieldset>
            </div>
		</spring-form:form>
	</div>
	<div class="form-button">
        <c:if test="${taskscheduler.displayMode==taskscheduler.displayConstants.NEW}">
		    <a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
		</c:if>
		<c:if test="${taskscheduler.displayMode==taskscheduler.displayConstants.VIEW}">
		    <a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
			<a class="btn btn-primary" href="javascript:doSubmit('createParametri.htm?codice=${taskscheduler.entity.id.codice}','',document.inviodati)"><fmt:message key="button.parametri" /></a>
			<a class="btn btn-primary" href="javascript:doSubmit('esegui.htm?codice=${taskscheduler.entity.id.codice}','',document.inviodati)"><fmt:message key="button.esegui" /></a>
			<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
		</c:if>
		<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>