<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipologiaregistri.label.dettaglio_protocollo_registri.title" /></title>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="tipologiaregistri.label.dettaglio_protocollo_registri.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    
     <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../tipologiaregistri/listProtocolloRegistri" />
	</jsp:include>
	<div id="subcontent">
		<div class="vbg-form">
			<fieldset>
				<legend><fmt:message key="tipologiaregistri.label.tipologiaregistro"/></legend>
				<div class="parametriDiv">
		       		<div class="parametro"> 
			        	<div>${registro.trDescrizione}</div>
			        </div>
				</div>
			</fieldset>	
			
			<table class="vbg-table">
				<thead>
					<th><fmt:message key="label.comune" /></th>
					<th><fmt:message key="tipologiaregistri.label.tipodocumento" /></th>
					<th><fmt:message key="label.tipimovimento" /></th>
					<th><fmt:message key="label.mailtipo" /></th>
					<th><fmt:message key="tipologiaregistri.label.classifica" /></th>
					<th><fmt:message key="tipologiaregistri.label.protocollo_flusso" /></th>
					<th><fmt:message key="tipologiaregistri.label.amministrazione_mittente" /></th>
					<th><fmt:message key="tipologiaregistri.label.amministrazione_destinataria" /></th>
					<th><fmt:message key="label.azioni" /></th>	
				</thead>
				<tbody>
				<%int j=1;%>
				<c:forEach items="${configs}" var="config">
					<tr class="<%=(j%2)==0?"odd":"even"%>">
					    <td>
					    	<c:if test="${config.comune == null}">	
								<fmt:message key="label.tutti" />
							</c:if>
							<c:if test="${config.comune != null}">
					    		${config.comune.comune}
					    	</c:if>			    	
					    </td>
						<td>${config.idtipodocumento}</td>
						<td>${config.tipimovimento.descrizioneEstesa}</td>
						<td>${config.mailtipo.descrizione}</td>
						<td>${config.classifica}</td>
						<td>${config.protocolloFlusso.codice}</td>
						<td>${config.mittente.descrizioneEstesa}</td>
						<td>${config.destinatario.descrizioneEstesa}</td>
						<td>					
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../tipologiaregistri/viewProtocolloRegistri.htm?codice=${config.id.codice}','')" title="<fmt:message key="label.azioni" /> ">
								<label><fmt:message key="label.azioni" /></label>
							</a> 					
						</td>
					</tr>
					<%j++; %>
				</c:forEach>		
				</tbody>
			</table>
		</div>

		<c:if test="${isComuniAssociati eq true}">
	
			<vbg-modal class="" id="message_dialog_confirm" title="<fmt:message key="label.messaggio_conferma"/>">
			 	<div slot='body'>
					Per proseguire è necessario scegliere un comune 
					<select name="codiceComune" id="codiceComune_id">
						<option value=""><fmt:message key="label.tutti" /></option>
						<c:forEach items="${responsabilicomunis}" var="rc">
							<option value="${rc.comune.codicecomune}">${rc.comune.comune}</option>
						</c:forEach>
					</select>	
				</div>
							
				<div slot='footer' class="form-button"">
					<a class="btn btn-primary" href="javascript:nuovoParametro();"><fmt:message key="button.new" /></a>
					<a class="btn btn-secondary" href="javascript:hideNuovoParametro();"><fmt:message key="button.annulla" /></a>
				</div>				
			</vbg-modal>
	
			<script type="text/javascript">
			
				// 
				function showNuovoParametro(){
					document.querySelector('#message_dialog_confirm').open();					
				}
				
				function hideNuovoParametro(){
					document.querySelector('#message_dialog_confirm').close();	
				}
				function nuovoParametro(){
					var codiceComune = document.querySelector('#codiceComune_id').value;	
					historySet('${_urlback}','../tipologiaregistri/createProtocolloRegistri.htm?codiceRegistro=${registro.id.codice}&codicecomune='+codiceComune,'');
				}
			</script>
	
		</c:if>
		<c:if test="${isComuniAssociati eq false}">
			<script type="text/javascript"> 
				function showNuovoParametro(){
					historySet('${_urlback}','../tipologiaregistri/createProtocolloRegistri.htm?codiceRegistro=${registro.id.codice}&codiceComune=','');			
				}		
			</script>
		</c:if>
	<div class="vbg-form">
		<div class="form-button">
			<a class="btn btn-primary" href="javascript:showNuovoParametro();"><fmt:message key="button.new" /></a>
			<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
		</div>
	</div>
</div>
</body>
</html>



