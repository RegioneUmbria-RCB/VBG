<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commedilizieallegati.id.codice==null}">
			<fmt:message key="label.nuovo_documento_commissione" />
		</c:if> 
		<c:if test="${commedilizieallegati.id.codice!=null}">
			<fmt:message key="label.modifica_documento_commissione" />
		</c:if>
	</title>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>	
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commedilizieallegati.id.codice==null}">
			<fmt:message key="label.nuovo_documento_commissione" />
		</c:if> 
		<c:if test="${commedilizieallegati.id.codice!=null}">
			<fmt:message key="label.modifica_documento_commissione" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	
	<div class="vbg-form">
		<fieldset>
   			<legend><fmt:message key="label.dettaglio_commissione_edilizia" /></legend>
				<div class="parametriDiv">
					<div class="etichetta">
						<div><fmt:message key="label.numero_commissione" />:</div>
						<div><fmt:message key="label.descrizione" />:</div>
					</div>		
					<div class="parametro">       		 	
						<div>${commedilizieallegati.commissioniedilizieT.numprotocollo}</div>
						<div>${commedilizieallegati.commissioniedilizieT.descrizione}</div>
					</div>
				</div>
			</fieldset>
		</div>
		<br class="clear" />
		<spring-form:form commandName="commedilizieallegati" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizieallegati" />
		    </jsp:include>
		    
		    
		<div id="form" class="vbg-form">
			<fieldset>
				<legend>Documento commissione</legend>
			
			 	<div class="form-group">
			 		<label><fmt:message key="label.descrizione" /></label>
			 		<spring-form:input id="descrizione_id" path="descrizione" size="70" cssClass="required" />
			 		<spring-form:errors path="descrizione" cssClass="error validation-feedback"/>
			 			 	</div>		    
		    	<div class="form-group">
			 		<label><fmt:message key="label.note" /></label>
			 		<spring-form:textarea id="note_id" path="note" cols="40" readonly="4" />
					<spring-form:errors path="note" cssClass="error validation-feedback"/>
			 	</div>
		    	<div class="form-group">
			 		<label><fmt:message key="label.pubblica" /></label>
			 		<spring-form:checkbox path="flagPubblica" />
			 	</div>				 	
				<div class="form-group">
			 		<label><fmt:message key="label.doc_atto" />
			 		<c:if test="${commedilizieallegati.id.codice!=null}">
			 			
			 			<div class="firme_allegati_presenti" data-id-allegato="${commedilizieallegati.id.codice}"><i class="fa fa-spinner fa-spin"></i></div>
						<jsp:include page="snippetfirmaallegati.jsp" >
							<jsp:param name="idallegato" value="${commedilizieallegati.id.codice}" />
						</jsp:include>
					</c:if>	
			 		</label>                                        
					<jsp:include page="../includes/oggetti.jsp">
					<jsp:param name="idElemento" value="oggettoIdCodice" />
					<jsp:param name="codiceOggetto" value="${commedilizieallegati.oggetti.id.codice}" />
					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
					<jsp:param name="nomefileId" value="oggetto_nomefile" />					
					</jsp:include>
					<spring-form:hidden path="oggetti.id.codice" id="oggetto_id_codice" />
					<spring-form:hidden path="oggetti.nomefile" id="oggetto_nomefile" />
					<spring-form:errors path="oggetti" cssClass="error validation-feedback"/>
			 	</div>
			 					 			
		 	</fieldset>		
		 </div> 
			 	
		<c:if test="${commedilizieallegati.id.codice!=null}">
			
		</c:if>		
		</spring-form:form>
	</div>
	<div class="vbg-form">
		<div class="form-button">			
			<c:if test="${commedilizieallegati.id.codice==null}">
				<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
			</c:if>
			<c:if test="${commedilizieallegati.id.codice!=null}">
				<a class="btn btn-primary" id="bottone-salva" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
				<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('list.htm?codiceCommissione=${commedilizieallegati.commissioniedilizieT.id.codice }','')"><fmt:message key="button.back" /></a>			
		</div>
	</div>
    
    <c:if test="${commedilizieallegati.id.codice!=null}">
        <script type="text/javascript">
        
        vbg.ready(() => {
        	
        	document.querySelector('#bottone-salva').addEventListener('click', (e) => {
        	    const contieneFirme = document.querySelector('#allegato-contiene-firme').value === '1';
        	    const messaggioWarning = "Attenzione! Si sta per eliminare un allegato con una o più firme associate.\r\n"+ 
        	                             "Proseguendo con l'operazione saranno eliminati entrambe.\r\n" +
        	                             "Proseguire?";
        	    const vecchioCodiceOggetto = "${commedilizieallegati.oggetti.id.codice}";
        	    const nuovoCodiceOggetto = document.querySelector('#oggetto_id_codice').value;
        	    
    	        if (contieneFirme && (vecchioCodiceOggetto !== nuovoCodiceOggetto)){
    	            if(!confirm(messaggioWarning)) {
    	            	document.location = document.location;
    	            	e.preventDefault();
    	            	return false;
    	            }
    	        }
        		
    	        return true;
        	});
        });
        </script>
    </c:if>
</body>
</html>