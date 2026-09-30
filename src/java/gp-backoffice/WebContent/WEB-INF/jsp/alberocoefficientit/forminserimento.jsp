<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset="UTF-8"/>
		<title>			
			<c:if test="${alberoCoefficientiR.id.codice==null}">
				<fmt:message key="alberoCoefficientiR.label.nuovo_alberoCoefficientiR.title" />
			</c:if> 
			<c:if test="${alberoCoefficientiR.id.codice!=null}">
				<fmt:message key="alberoCoefficientiR.label.dettaglio_alberoCoefficientiR.title" />
			</c:if>
		</title>
		<style media="all">
			#codiceCoefficente_id {
				min-width: auto;
			}
		</style>
	</head>
	<body>
		<span class="titoloPagina">
			<c:if test="${alberoCoefficientiR.id.codice==null}">
				<fmt:message key="alberoCoefficientiR.label.nuovo_alberoCoefficientiR.title" />
			</c:if> 
			<c:if test="${alberoCoefficientiR.id.codice!=null}">
				<fmt:message key="alberoCoefficientiR.label.dettaglio_alberoCoefficientiR.title" />
			</c:if>
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<div id="subcontent">
			<spring-form:form commandName="alberoCoefficientiR" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="alberoCoefficientiR" />
		    </jsp:include>
	
			<div class="vbg-form">
		        <fieldset>
		        	<legend><fmt:message key="label.intervento"/></legend>
		        	<div class="parametriDiv">
						<div class="etichetta">
							<div><fmt:message key="label.tipo_concorso" />:</div>							
						</div>		
						<div class="parametro">       		 	
							<div> ${alberoCoefficientiT.descrizione }</div>						
						</div>
					</div>		           
		        </fieldset>
	       
	      		<fieldset>
		            <legend><fmt:message key="label.coefficiente"/></legend>
		            <div class="form-group">
		                <label>* <fmt:message key="label.codice_coefficiente"/></label>
		                <input id="codiceCoefficente_id" type="text" required="true" name="codiceCoefficente" value="${alberoCoefficientiR.codiceCoefficente}" maxlength="6" size="6">
		               	<spring-form:errors path="codiceCoefficente" cssClass="error validation-feedback"/>
		            </div>
		            <div class="form-group">
		                <label>* <fmt:message key="label.descrizione"/></label>
		                <input type="text" required="true" name="descrizione" value="${alberoCoefficientiR.descrizione}" maxlength="500" size="150">
		                <spring-form:errors path="descrizione" cssClass="error validation-feedback"/>
		            </div>
		            <div class="form-group">
		                <label>* <fmt:message key="label.valore"/></label>
		                <input type="text" required="true" name="valore" value="${alberoCoefficientiR.valore}" maxlength="50">
		                <spring-form:errors path="valore" cssClass="error validation-feedback"/>
		            </div>
		            <%-- <div class="form-group">
		                <label><fmt:message key="label.tipo"/></label>
		                <input type="text" required="true" name="tipo" value="${alberoCoefficientiR.tipo}">
		                <spring-form:errors path="tipo" cssClass="error validation-feedback"/>
		            </div> --%>
		            <div class="form-group">
		                <label><fmt:message key="label.note"/></label>
		                <textarea name="note" value="${alberoCoefficientiR.note}" maxlength="4000" >${alberoCoefficientiR.note}</textarea>
		            </div>
		            <div class="form-group">
		                <label><fmt:message key="label.visibile"/></label>
		                <input id="visibile_id" required="true" type="checkbox" name="visibile" ${alberoCoefficientiR.visibile?'checked':''} onchange="changeValue()">
		                <spring-form:errors path="visibile" cssClass="error validation-feedback"/>
		            </div>
	        	</fieldset>
	       	</div>
       		</spring-form:form>
   		</div>      	
	
		<div class="form-button">
			<c:if test="${alberoCoefficientiR.id.codice==null}">
			    <a class="btn btn-primary" href="javascript:doSubmit('insertRiga.htm','',document.inviodati)"><fmt:message key="button.insert"/></a>
		    </c:if>
		    <c:if test="${alberoCoefficientiR.id.codice!=null}">
			    <a class="btn btn-primary" href="javascript:doSubmit('updateRiga.htm','',document.inviodati)"><fmt:message key="button.update"/></a>
		    </c:if>
		    <a class="btn btn-secondary" href="javascript:doHref('view.htm?codice=${alberoCoefficientiT.id.codice }','')"><fmt:message key="button.back" /></a>
		</div>	
		
		<script type="text/javascript">
		
			function changeValue(){
			    
			    console.log("cambio valore");
			    let visibile =  document.querySelector('#visibile_id');
			    if(visibile.checked){
					visibile.value = true;
			    }else{
					visibile.value = false;
			    }
			}
		
		</script>
	</body>
</html>