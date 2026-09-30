<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="manifestazioni.label.lista_spuntisti.title" />
	</title>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-multi-upload.js?<%=vJS %>" defer></script>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="manifestazioni.label.lista_spuntisti.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../spuntistimercati/viewSpuntistiMercato" />	    	
		</jsp:include>	
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
        	<jsp:param name="commandName" value="mercatiConfigurazione" />
    	</jsp:include>
		
		<div class="vbg-form">
			<fieldset>
				<legend>Dati mercato</legend>			
				<div class="header_dato">
					<span class="header_dato_etichetta"><fmt:message key="label.mercato" />:</span>
					<span class="header_dato_valore">
					<div>${mercati.descrizione}</div>
					</span>
				</div>
				<div class="header_dato">
					<span class="header_dato_etichetta"><fmt:message key="label.giorno" />:</span>
					<span class="header_dato_valore">
					<div>${mercatiUso.descrizione}</div>
					</span>
				</div>
			</fieldset>		
		</div>
		
		<br class="clear" />
		<c:if test="${isGraduatoriaSpuntistiAttiva}">
		
		<c:set var="select_tutti" value="" ></c:set>
		<c:set var="select_attiva" value=""></c:set>
		<c:set var="select_non_attiva" value=""></c:set>
		<c:choose>
			<c:when test="${isAttivi eq true}"><c:set var="select_attiva" value="selected='selected'" ></c:set></c:when>
			<c:when test="${isAttivi eq false}"><c:set var="select_non_attiva" value="selected='selected'" ></c:set></c:when>
			<c:otherwise><c:set var="select_tutti" value="selected='selected'" ></c:set></c:otherwise>
		</c:choose>
		<select id="select_id" onchange="reload()">
			<option ${select_tutti}  value="" ><fmt:message key="label.tutti"/></option>
			<option ${select_attiva} value="true"><fmt:message key="label.attivi"/></option>
			<option ${select_non_attiva} value="false"><fmt:message key="label.non_attivi"/></option>
		</select>		
		
		<table class="vbg-table">
			<thead>
				<th><fmt:message key="label.spuntista"/></th>
				<th><fmt:message key="label.autorizzazione"/></th>
				<th><fmt:message key="label.data"/></th>
				<th><fmt:message key="label.registro"/></th>
				<th><fmt:message key="label.comune"/></th>
				<th><fmt:message key="label.data_registrazione" /></th>
				<th><fmt:message key="label.attivo" /></th>
				<th><fmt:message key="label.data_disattivazione" /></th>
				<th><fmt:message key="label.data_validita" /></th>
			</thead>
			<tbody>
				<c:if test="${not empty  listSpuntistiMercati}">
					<c:forEach items="${listSpuntistiMercati}" var="var" varStatus="b1">
					<c:set value="" var="styleDisattivati"></c:set>
				    <c:if test="${!var.flgAttivo}">
				    	<c:set value="border: 2px solid var(--error-color);" var="styleDisattivati"></c:set>
				    </c:if>
				    <tr style="${styleDisattivati}">
				    	<td>${var.autorizzazioni.anagrafe.descrizioneRichiedente}</td>
						<td>${var.autorizzazioni.autoriznumero}</td>
						<td><fmt:formatDate value="${var.autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
						<td>${var.autorizzazioni.tipologiaregistro.trDescrizione}</td>
						<td>${var.autorizzazioni.autorizcomune.descrizioneEstesa}</td>
						<td><fmt:formatDate value="${var.dataRegistrazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
						<td>
							<c:if test="${var.flgAttivo}"><fmt:message key="label.si" /></c:if> 
							<c:if test="${!var.flgAttivo}"><fmt:message key="label.no" /></c:if> 
						</td>
						<td>
							<c:if test="${var.dataDisattivazione!=null}"><fmt:formatDate value="${var.dataDisattivazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if> 
							<c:if test="${var.dataDisattivazione==null}"> - </c:if> 
						</td>
						<td><fmt:formatDate value="${var.autorizzazioni.istanza.datavalidita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
				    </tr>
					</c:forEach>
				
				</c:if>
				<c:if test="${empty  listSpuntistiMercati}">
					<tr>
				    	<td colspan="9" align="center"><fmt:message key="label.record_non_presenti"/></td>
				    </tr>
				</c:if>
			</tbody>
		
		</table>		
		
		</c:if>
		<c:if test="${!isGraduatoriaSpuntistiAttiva}">
			<b><fmt:message key="label.funzionalita_non_attiva"/></b>
		</c:if>
		
		<div id="dialog-6" class='vbg-modal' data-auto-open='false'>
			<div class='vbg-modal-body'>
				<h1>Conferma</h1>
				<div class="vbg-form">
					<div class="form-group">
						<fmt:message key="label.messaggio_disabilita_spunt_fiere_per_operatore">
							<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
						</fmt:message>
					</div>
					<div class="form-group">
						<label for="terms"><fmt:message key="label.accettazione_condizioni_disabilita_spuntisti_fiere"/></label>
	            		<input type="checkbox" id="terms">
					</div>
				</div>
				<div class="vbg-modal-footer">
					<a href="#" id="disabilita_id" data-role='disable-popup' class="btn btn-primary">
						<fmt:message key="button.disabilita" />
					</a>
					<a href="#" data-role='toggle-popup' class="btn btn-secondary">
						<fmt:message key="button.back" />
					</a>
				</div>
			</div>
		</div>


		<script type="text/javascript">

 			vbg.ready(() => {
 				
	 			let popConferma = document.querySelector('#dialog-6');
	 			const buttonDisabilita = popConferma.querySelector('#disabilita_id');	 			
	 			let checkTerms = popConferma.querySelector('#terms');
	 			
	 			buttonDisabilita.style.display = 'none';
	 
	 			checkTerms.addEventListener('click', (e) => {
	 				
	 				if(checkTerms.checked){
	 					console.log('fava');
	 					buttonDisabilita.style.display = 'inline-block';
	 				}else{
	 					buttonDisabilita.style.display = 'none';
	 				}
	 			});
	 			
	 			
	 			buttonDisabilita.addEventListener('click', (e) => {
	 				
	 				let codiceMercato = document.querySelector('.disabilita_id').dataset.codiceMercato;
	 				let codiceUso = document.querySelector('.disabilita_id').dataset.codiceUso;	 				
	 				
	 				try{
	 					disabilita(codiceMercato, codiceUso);
	 					vbg.mostraModalCaricamento();
	 				}finally{
						vbg.nascondiModalCaricamento();
					}			
	 			});
	 			
	 			
				function disabilita(mercatoId, usoId) {
					doHref('disabilitaSpuntistaFieraPerTermine.htm?codiceMercato='
							+ mercatoId + '&codiceuso=' + usoId, '');
				}			
				
			 });
 			
 			function reload(){
				
				let val = document.querySelector('#select_id').value;
				console.log(val);				
				doHref('viewSpuntistiMercato.htm?codiceMercato=${mercati.id.codice}&codiceuso=${mercatiUso.id.codice}&attivi='+val, ''); 
				vbg.mostraModalCaricamento();
			
			}
		</script>
		
	
	<% 
		pageContext.setAttribute("codice_fiera", WebConstants.MANIFESTAZIONE_FIERA);
	%>
	
	<div id="functions">
		<ul>
		    <c:if test="${isGraduatoriaSpuntistiAttiva && mostraSpuntistiDaDisabilitare}">  
				<li><a href="javascript:historySet('${_urlback}', '../spuntistimercati/viewSpuntistiMercatoDaDisabilitare.htm?codiceMercato=${mercati.id.codice}&codiceuso=${mercatiUso.id.codice}', '')""><fmt:message key="button.spuntisti_da_disabilitare" /></a></li>
			</c:if>
			<c:if test="${isGraduatoriaSpuntistiAttiva && mercati.manifestazione.codice eq codice_fiera}">  
				 <li><a href="javascript:void(0);" class="disabilita_id" data-role=open-vbg-modal data-target-modal-id="dialog-6" data-codice-mercato="${mercati.id.codice}" data-codice-uso="${mercatiUso.id.codice}"><fmt:message key="button.spuntisti_fiera_da_disabilitare_per_termine" /></a></li>
				 <%-- vbg-btn  btn-elimina <li><a href="javascript:historySet('${_urlback}', '../spuntistimercati/disabilitaSpuntistaFieraPerTermine.htm?codiceMercato=${mercati.id.codice}&codiceuso=${mercatiUso.id.codice}', '')""><fmt:message key="button.spuntisti_fiera_da_disabilitare_per_termine" /></a></li> --%>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>