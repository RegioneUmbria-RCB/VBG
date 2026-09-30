<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<c:if test="${statiistanza.id.codicestato=='' || statiistanza.id.codicestato==null}">
				<fmt:message key="statiistanze.label.nuovo_statiistanze.title" />
			</c:if> 
			<c:if test="${statiistanza.id.codicestato!='' && statiistanza.id.codicestato!=null}">
			<c:if test="${error ne '03'}">
				<fmt:message key="statiistanze.label.dettaglio_statiistanze.title" />
				</c:if>
				<c:if test="${error eq '03'}">
				<fmt:message key="statiistanze.label.nuovo_statiistanze.title" />
				</c:if>
			</c:if>
		</title>
		<style>
			.sotto-form{
					margin-left: var(--default-padding);
					font-style: italic;
					padding-top: var(--half-padding);
			}

		</style>
		
	</head>
	<body>
		<span class="titoloPagina">
			<c:if test="${statiistanza.id.codicestato =='' || statiistanza.id.codicestato==null}">
				<fmt:message key="statiistanze.label.nuovo_statiistanze.title" />
			</c:if> 
			<c:if test="${statiistanza.id.codicestato!='' && statiistanza.id.codicestato!=null}">
				<c:if test="${error ne '03'}">
					<fmt:message key="statiistanze.label.dettaglio_statiistanze.title" />
				</c:if>
				<c:if test="${error eq '03'}">
					<fmt:message key="statiistanze.label.nuovo_statiistanze.title" />
				</c:if>
			</c:if>
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<div id="subcontent">
			<div class="vbg-form">
				<spring-form:form commandName="statiistanza" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
			        <jsp:param name="commandName" value="statiistanza" />
			    </jsp:include>
			    <c:if test="${statiistanza.id.codicestato!='' && statiistanza.id.codicestato!=null}">
			    	<c:if test="${error ne '03'}">
				    	<div class="form-group">
							<label><fmt:message key="statiistanze.label.codicestato" /></label>
							<spring-form:input id="codicestato_id" path="id.codicestato" size="2" disabled="true" maxlength="2"/>
							<spring-form:errors path="id.codicestato" cssClass="error"/>
						</div>
			    	</c:if>
			    	<c:if test="${error eq '03'}">
			    		<div class="form-group">
							<label><fmt:message key="statiistanze.label.codicestato" /></label>
							<td><spring-form:input id="codicestato_id" path="id.codicestato" size="2" maxlength="2"/>
							<spring-form:errors path="id.codicestato" cssClass="error"/></td>
						</div>
			    	</c:if>
			    </c:if>
			    <c:if test="${statiistanza.id.codicestato=='' || statiistanza.id.codicestato==null}">
			    	<div class="form-group">
						<label><fmt:message key="statiistanze.label.codicestato" /></label>
						<spring-form:input id="codicestato_id" path="id.codicestato" size="2" />
						<spring-form:errors path="id.codicestato" cssClass="error"/>
					</div>					
				</c:if>
				<div class="form-group">
					<label><fmt:message key="statiistanze.label.stato" /></label>
					<spring-form:input id="stato_id" path="stato" size="70" />
					<spring-form:errors path="stato" cssClass="error"/>
				</div>
				<div class="form-group">
					<label><fmt:message key="statiistanze.label.stato_esterno" /></label>
					<spring-form:input id="etichettaSistemaEsterno_id" path="etichettaSistemaEsterno" size="70" />
					<spring-form:errors path="etichettaSistemaEsterno" cssClass="error"/>
				</div>
				<div class="form-group">
					<label><fmt:message
							key="statiistanze.label.modificaistanza" /></label>
					<spring-form:checkbox path="modificaistanza" />
					<div class="input-help"><fmt:message key="statiistanze.label.modificaistanza.help" /></div>
					<spring-form:errors path="modificaistanza" cssClass="error" />
				</div>
				<div class="form-group">
					<label><fmt:message key="statiistanze.label.staticomportamento" /></label>
					<spring-form:select path="staticomportamento.codcomportamento"
						itemLabel="comportamento" itemValue="codcomportamento"
						items="${staticomportamenti}"></spring-form:select>
					<spring-form:errors path="staticomportamento.codcomportamento"
						cssClass="error" />
				</div>
				<div class="form-group">
					<label><fmt:message key="statiistanze.label.ordine" /></label>
					<spring-form:input id="ordine_id" path="ordine" size="2" maxlength="2" onchange="changeValue(this)" />
					<spring-form:errors path="ordine" cssClass="error" />
				</div>
				<div class="form-group">
					<label><fmt:message	key="statiistanze.label.blocca_integrazioni_online" /></label>
					<spring-form:checkbox path="flagBloccaIntegrOnline" />
					<div class="input-help"><fmt:message key="statiistanze.label.blocca_integrazioni_online.help" /></div>
					<spring-form:errors path="flagBloccaIntegrOnline" cssClass="error" />
				</div>
				<c:if test="${OSP_LIVORNO_ATTIVA eq true }">					
					<label>Stato della pratica su SIT LDP</label>
					<spring-form:input id="ospldpStatoPratica_id" path="ospldpStatoPratica" size="20" maxlength="20"/>
					<br />
					<div class="sotto-form">
						Indicare lo stato della pratica sul sistema LDP. Ad esempio:
						<ul> 
							<li><b>Prenotato</b> (fase di prentazione e durante fase istruttoria)</li>
							<li><b>Autorizzato</b> (rilascio di autorizzazione o concessione)</li>
							<li><b>Diniego</b> (richiesta rifiutata o non autorizzata  dopo l'istruttoria)</li>
						</ul>
					</div>
					<spring-form:errors path="ospldpStatoPratica" cssClass="error"/>					
				</c:if>
				<div class="form-group">
					<label><fmt:message key="statiistanze.label.flag_warning" /></label>
					<spring-form:checkbox path="flagWarning" />
					<div class="input-help"><fmt:message key="statiistanze.label.flag_warning.help" /></div>					
					<spring-form:errors path="flagWarning" cssClass="error" />
				</div>
				<div class="form-group">
					<label><fmt:message key="statiistanze.label.testo_warning" /></label>
					<spring-form:input id="testo_warning" path="testoWarning"
						size="100" />
					<spring-form:errors path="testoWarning" cssClass="error" />
				</div>
				<div class="form-group">
					<label><fmt:message key="label.colore" /></label>
					<spring-form:input id="colore_id" path="colore" size="10" />
					<spring-form:errors path="colore" cssClass="error" />
				</div>
				<div class="form-group">
					<label><fmt:message key="statiistanze.label.flag_flagFiltrabileFront" /></label>
					<spring-form:checkbox path="flagFiltrabileFront" />
					<div class="input-help"><fmt:message key="statiistanze.label.flag_flagFiltrabileFront.help" /></div>					
					<spring-form:errors path="flagWarning" cssClass="error" />
				</div>
				
 
				<script type='text/javascript'>
					function changeValue(obj){
						var importo=obj.value;
						if(isNaN(importo)){
							alert('<fmt:message key="alert.field.numeric" />');
							obj.value = '';
							return;
						}
					}
				</script>
				<c:if test="${statiistanza.id.codicestato=='' || statiistanza.id.codicestato==null}">
					<script type='text/javascript'>
						$('codicestato_id').focus();
					</script>
				</c:if>
				<c:if test="${statiistanza.id.codicestato!='' && statiistanza.id.codicestato!=null}">
					<c:if test="${error ne '03'}">
						<script type='text/javascript'>
							$('stato_id').focus();
						</script>
					</c:if>
					<c:if test="${error eq '03'}">
						<script type='text/javascript'>
							$('codicestato_id').focus();
						</script>
					</c:if>
				</c:if>
				</spring-form:form>
			</div>
		</div>
		<div class="form-button">		
			<c:if test="${statiistanza.id.codicestato=='' || statiistanza.id.codicestato==null}">
				<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
			</c:if>
			<c:if test="${statiistanza.id.codicestato!='' &&  statiistanza.id.codicestato!=null}">
			<c:if test="${error ne '03'}">
				<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
				<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
			</c:if>
			<c:if test="${error eq '03'}">
				<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
			</c:if>
			</c:if>
			<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>
		
		</div>
	</body>
</html>
