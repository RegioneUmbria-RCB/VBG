<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipimovimentodoctipo.id.codicelettera==null}">
			<fmt:message key="tipimovimento.label.nuovo_tipimovimentodoctipo.title" />
		</c:if> 
		<c:if test="${tipimovimentodoctipo.id.codicelettera!=null}">
			<fmt:message key="tipimovimento.label.dettaglio_tipimovimentodoctipo.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${tipimovimentodoctipo.id.codicelettera==null}">
			<fmt:message key="tipimovimento.label.nuovo_tipimovimentodoctipo.title" />
		</c:if> 
		<c:if test="${tipimovimentodoctipo.id.codicelettera!=null}">
			<fmt:message key="tipimovimento.label.dettaglio_tipimovimentodoctipo.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<c:if test="${tipimovimentodoctipo.id.codicelettera==null}">
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../tipimovimento/createTipiDocumento" />
		<jsp:param name="qs" value="codiceMovimento%3d${tipimovimento.id.tipomovimento}%26software%3d${tipimovimento.software.codice}" />	
	</jsp:include>
	</c:if>
	<c:if test="${tipimovimentodoctipo.id.codicelettera!=null}">
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../tipimovimento/createTipiDocumento" />
			<jsp:param name="qs" value="codiceMovimento%3d${tipimovimento.id.tipomovimento}%26codiceLettera%3d${tipimovimentodoctipo.id.codicelettera}%26software%3d${tipimovimento.software.codice}" />
		</jsp:include>	
	</c:if>
	<div id="subcontent">
		<spring-form:form commandName="tipimovimentodoctipo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tipimovimentodoctipo" />
		    </jsp:include>
			<div class="vbg-form">
				<fieldset>
					<legend>
						<fmt:message key="label.dettaglio" />
					</legend>
					<div class="parametriDiv">
						<div class="etichetta">
							<div><fmt:message key="tipimovimento.label.movimento" />:</div>
				    	</div>
				    	<div class="parametro">
				    	  <div><c:out value="${tipimovimento.id.tipomovimento}" /> - <c:out value="${tipimovimento.movimento}" /></div>
						</div>
					</div>
				</fieldset>
				<fieldset>
					<legend>
						<fmt:message key="tipimovimento.label.dettaglio_tipimovimentodoctipo.dati_generali" />
					</legend>
					<div class="form-group">
						<label><fmt:message key="tipimovimentodoctipo.label.letteretipo" /></label>
						<div id="letteretipo_id1" style="display:none;"><spring-form:input id="lettere_tipo_id1" path="letteretipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findLettereTipo.htm?codicesoftware=TT'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id1" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter></div>
						<div id="letteretipo_id2" style="display:inline;"><spring-form:input id="lettere_tipo_id2" path="letteretipo.descrizione" cssClass="searchbox" size="75" onchange="checkValue(this,'lettere_tipo_hidden')" onkeydown="javascript:return searchAll(this,event)"/><init:autocompleter methodAjax='findLettereTipo.htm'  idHidden="lettere_tipo_hidden"  idInput="lettere_tipo_id2" inputTitleKey="label.ricerca_lettera_tipo"></init:autocompleter></div>
						<spring-form:errors path="letteretipo" cssClass="error"/> 
						<spring-form:hidden id="lettere_tipo_hidden" path="letteretipo.id.codice"  />
					    <input type="checkbox" id="id_flag1" />
		                <init:help idHelp="help1" textKey="help.letteretipo_archivi_base"/>
					</div>
				 	<div class="form-group">
				 		<label><fmt:message key="tipimovimento.label.dettaglio_tipimovimentodoctipo.flg_generaaut" /></label>
				 		<spring-form:checkbox id="flgGeneraAut_id" path="flgGeneraAut"/>
						<label style="width:100%" for="flgGeneraAut_id"><span style="line-height: 2em; vertical-align: top;"><fmt:message key="tipimovimento.label.dettaglio_tipimovimentodoctipo.flg_generaaut.help" /></span></label>
				 	</div>
				 	<div class="form-group" id="div_faseEsecuzione">
				 		<label><fmt:message key="tipimovimento.label.dettaglio_tipimovimentodoctipo.fase_esecuzione" /></label>
						<spring-form:select id="faseEsecuzione_id" path="faseEsecuzione">
							<spring-form:option value=""><fmt:message key='label.seleziona'/></spring-form:option>
							<c:forEach items="${fasiesecuzione}" var="fase">
			    				<spring-form:option value="${fase.key}">${fase.value}</spring-form:option>
			    			</c:forEach>
						</spring-form:select>
				 	</div>
				</fieldset>
				<div class="form-button">
					<c:if test="${tipimovimentodoctipo.id.codicelettera==null}">
						<a class="btn btn-primary" href="javascript:doSubmit('insertDocumentoTipo.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
					</c:if>
					<c:if test="${tipimovimentodoctipo.id.codicelettera!=null}">
						<a class="btn btn-primary" href="javascript:doSubmit('updateDocumentoTipo.htm','',document.inviodati)"><fmt:message key="button.update" /></a>						
						<a class="btn btn-primary" href="javascript:doSubmit('deleteDocumentoTipo.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
					</c:if>
					<a class="btn btn-secondary" href="javascript:historyBack('')"><fmt:message key="button.back" /></a>
				</div>
			</div>
		</spring-form:form>
	</div>
	<script type="text/javascript">
		vbg.ready(() => {
			const chkTuttiSW = document.querySelector('#id_flag1');
			if(chkTuttiSW){
				
				const letteretipo_id1 = document.querySelector('#letteretipo_id1');
				const letteretipo_id2 = document.querySelector('#letteretipo_id2');
				
				chkTuttiSW.addEventListener('click', async (e) => {
					if(e.target.checked){			
						letteretipo_id1.style.display="inline";
						letteretipo_id2.style.display="none";
					}else{
						letteretipo_id1.style.display="none";
						letteretipo_id2.style.display="inline";
					}
				});
			}
			
			const flgGenAutomatica = document.querySelector('#flgGeneraAut_id');

			flgGenAutomatica.addEventListener('click', async (e) => {
				mostraNascondiFaseEsecuzione(e.target);
			});
			
			mostraNascondiFaseEsecuzione(flgGenAutomatica);
		});
		
		function mostraNascondiFaseEsecuzione(flgGenAutomatica){
			
			let selFaseEsecuzione = document.querySelector('#faseEsecuzione_id');
			let divFaseEsecuzione = document.querySelector('#div_faseEsecuzione');
			
			if(flgGenAutomatica.checked){			
				divFaseEsecuzione.style.display="";
			}else{
				divFaseEsecuzione.style.display="none";
				selFaseEsecuzione.value = "";
			}
		}
	</script>
</body>
</html>