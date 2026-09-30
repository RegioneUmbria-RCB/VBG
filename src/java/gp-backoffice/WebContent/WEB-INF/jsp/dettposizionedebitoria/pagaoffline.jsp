<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.posizioni_debitorie.pagaoffline.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.posizioni_debitorie.pagaoffline.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="pagamentoOffline" name="dettaglio">
 
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="pagamentoOffline" />
		    </jsp:include>

		   	<div slot="body">
		   		<h1 id="titolo">Posizione debitoria ${pagamentoOffline.datiPagamento.idPosizioneDebitoria}</h1>
		   	</div>
		   	<div id="form" class='vbg-form'>
		   		<div class='form-group'>
		   			<label><fmt:message key="label.posizioni_debitorie.descrizione" /></label>
		   			<spring-form:input id="causale" path="datiPagamento.descrizione"  size="100" readonly="true" />
		   		</div>
		   		<div class='form-group'>
		   			<label><fmt:message key="label.importo_pagato" />*</label>
		   			<spring-form:input id="importopagato_id" path="datiPagamento.importo" cssStyle="text-align:right;" size="10" maxlength="10" onblur="checkNumberValue(this);" readonly="true"/>
		   		</div>
				<div class='form-group'>
		   			<label><fmt:message key="label.data_pagamento" />*</label>
		   			<fmt:formatDate value="${pagamentoOffline.datiPagamento.dataPagamento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" scope="page" var="_dataPagamento" />
					<input id="dataPagamento_id" name="datiPagamento.dataPagamento" onblur="isValidDate(this,true);" type="text" value="${_dataPagamento}" size="10" maxlength="10"/>
					<a id="caldatapagamento" href="javascript:void(0)" title="Calendario"> <img src="${pageContext.request.contextPath}/images/cal.gif" alt="Calendario"/></a>
		   					   			<script type="text/javascript"> 
		   				RANGE_CAL_1 = new Calendar({
							inputField: "dataPagamento_id",
							dateFormat: "%d/%m/%Y",
							trigger: "caldatapagamento",
							bottomBar: false,
							onSelect: function() {
								var date = Calendar.intToDate(this.selection.get());
								this.hide();
							}
						})
					</script>
		   		</div>
		   		<div class='form-group'>
		   			<label><fmt:message key="label.modalita_pagamento" />*</label>
					<spring-form:select id="modalitapagamento_id" path="datiPagamento.idModalitaPagamento"> 
							<spring-form:option value="" ><fmt:message key='label.seleziona'/></spring-form:option>
							<spring-form:options items="${pagamentoOffline.modalitaPagamento}" itemLabel="mpDescrestesa" itemValue="id.codice" />
						</spring-form:select>
		   		</div>
		   		<div class='form-group'>
		   			<label><fmt:message key="label.riferimento_documento" /></label>
					<spring-form:input id="docriferimento_id" path="datiPagamento.riferimentoPagamento"  size="60" />
		   		</div>
		   	</div>
		</spring-form:form>
	</div>
	<script type="text/javascript">
	
		function valida(){
			
			let importopagato = document.getElementById('importopagato_id').value;
			let dataPagamento = document.getElementById('dataPagamento_id').value;
			let modalitapagamento = document.getElementById('modalitapagamento_id').value;
			if(importopagato=='' || dataPagamento=='' || modalitapagamento==''){
				alert('Attenzione! Compilare i dati obbligatori');
				return false;
			}
			return true;
		}
		function pagaOffline(){
			
			if(valida()){
				doSubmit('registraPagamentoOffline.htm','',document.dettaglio);
			}
		}
		
	</script>
	<div>
		<a class='btn btn-primary' href="javascript:pagaOffline()"><fmt:message key="button.registra_pagamento_offline" /></a>
		<a class='btn btn-primary' href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	</div>
</body>