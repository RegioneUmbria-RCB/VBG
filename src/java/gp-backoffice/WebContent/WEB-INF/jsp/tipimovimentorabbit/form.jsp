<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${isInsert}">
			<fmt:message key="tipimovimentorabbit.label.tipimovimentorabbit.title.nuovo" />
		</c:if>
		<c:if test="${isView}">
			<fmt:message key="tipimovimentorabbit.label.tipimovimentorabbit.title" />
		</c:if>
	</title>
	<style>
		#txtTesto {
			margin-left: 154px;
			margin-top: var(--default-padding);
		}
		
		.errore {
			display: contents;
			color: var(--error-color);
		}
	</style>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${isInsert}">
			<fmt:message key="tipimovimentorabbit.label.tipimovimentorabbit.title.nuovo" />
		</c:if>
		<c:if test="${isView}">
			<fmt:message key="tipimovimentorabbit.label.tipimovimentorabbit.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<div class="vbg-form">
			<spring-form:form commandName="tipimovimentoRabbit" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
			        <jsp:param name="commandName" value="tipimovimentoRabbit" />		        
			    </jsp:include>
			    <input type="hidden" value="${tipomovimento}"/>
			    	<fieldset>
			    	<legend><fmt:message key="label.configurazioni"/> </legend>
				    	<div class="form-group">
							<label><fmt:message key="label.messaggio"/></label>
							<input name="tipomovimento" type="text" value="${tipomovimento }" readonly/>							
						</div>	
						<div class="form-group">
							<label><fmt:message key="label.topic"/></label>
							<c:if test="${isInsert}">
								<spring-form:select id="topic_id" path="id.topic" onchange="mostraNascondi();">
									<spring-form:option value="" label="-- Selezionare un'opzione --"></spring-form:option>
									<spring-form:options items="${topicList}" itemLabel="topic" itemValue="topic"/>
								</spring-form:select>
							</c:if>
							<c:if test="${isView}">
								<input name="id.topic" id="topic_id" type="text" value="${tipimovimentoRabbit.id.topic}" readonly size="50"/>
							</c:if>					
							<div class="errore"></div>
						</div>
						<div class="form-group" id="sezione_categoria">
							<label><fmt:message key="label.categoria"/></label>
							<spring-form:select id="categoria_id" path="categoria" onchange="">
								<spring-form:option value="" label="-- Selezionare una categoria --"></spring-form:option>
								<spring-form:options items="${categoriaList}" itemValue="value" itemLabel="descrizione"/>
							</spring-form:select>	
							<div class="errore"></div>							
						</div>		
						<div class="form-group" id="testo_tipo_id">
							<label><fmt:message key="label.messaggio"/></label>							
							<jsp:include page="../includes/autocompletergenericoTT.jsp">
								<jsp:param name="idElemento" value="mailtipo" />
								<jsp:param name="propertyPath" value="mailtipo" />
								<jsp:param name="pathPropertyDescription" value="mailtipo.descrizione" />
								<jsp:param name="pathPropertyCode" value="mailtipo.id.codice" />
								<jsp:param name="autocompleterAjax" value="findMailtipo.htm?ambito=R&software=" />
								<jsp:param name="titleKey" value="label.ricerca_mailtipo" />
								<jsp:param name="id_help" value="help_mailtipo" />
							</jsp:include>													
						</div>
					</fieldset>
			</spring-form:form>
		</div> 
	</div>
	
	
	<script type="text/javascript">			
	const topicsEnum = [
		{
		    valore : 'valore_non_inizializzato',
		    mostraMailtipo : false
		}
		<c:forEach items="${RABBIT_TOPIC_ENUM }" var="rte">	
			,{
			    valore : '${rte.value}',
			    mostraMailtipo : ${rte.visualizzaMailTipo}
			}
		</c:forEach>	
		];
	
	function mostraNascondi(){
		
		let valoreScelto = document.getElementById("topic_id").value;
		let mostraONascondi = '';
		if(valoreScelto!=''){
			let topic = topicsEnum.find(topic => topic.valore === valoreScelto);
			if(topic){
				mostraONascondi = !topic.mostraMailtipo?'none':'';
			}			
		}
		document.getElementById("testo_tipo_id").style.display=mostraONascondi;
		mostraCategoria();
	}
	
	function mostraCategoria(){
		
		let cat = document.getElementById('sezione_categoria');
		cat.style.display='none';
		let topic = document.getElementById('topic_id').value;
		if (topic === 'backend.comunicazioni-utente.nuova') {
			cat.style.display=''
		}
	}
	
	
	
	vbg.ready(() => {
		mostraNascondi();
		mostraCategoria();
		
	});
	
		
	</script>
	<div class="form-button">	
		<c:if test="${isInsert}">
			<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>	
		</c:if>		
		<c:if test="${isView}">
			<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
		</c:if>				
		<a class="btn btn-secondary" href="javascript:doHref('list.htm?tipomovimento=${tipomovimento}','')"><fmt:message key="button.back" /></a>		
	</div>
</body>
</html>