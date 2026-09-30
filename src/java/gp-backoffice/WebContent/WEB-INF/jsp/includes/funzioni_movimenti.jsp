<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI --%>
<c:set var="funzioneRichiesta" value=""/>
<c:set var="mostraAbilitato" value="${param.mostraAbilitato}"/>
<c:set var="codiceMovimentoId" value="${param.codiceMovimentoId}"/>
<%-- 
PARAMETRI:
	funzioneRichiesta
		funzioni possibili:
			visualizzamovimentiPadre: 
				visualizza 
					se il movimento è stato creato da altri movimenti 
					una freccia verde e lancia la visualizzazione della lista dei movimenti che l'hanno generato
					se il movimento non ha predecessori una freccia  grigia
			visualizzamovimentiFiglio:
				 visualizza 
					se il movimento ha generato altri movimenti 
					una freccia verde e lancia la visualizzazione della lista dei movimenti che ha generato
					se il movimento non ha generato altri movimenti una freccia  grigia
			javascriptBlock:
				riporta il blocco javascript per effettuare le chiamate al dettaglio questa funzione va messa di solito in fondo alla pagina		
	mostraAbilitato:
		la lunghezza del set dei movimenti				
	codiceMovimentoId:
		Il codice del movimento			
--%>
<c:if test="${not empty param.funzioneRichiesta}">
	<c:set var="funzioneRichiesta" value="${param.funzioneRichiesta}"/>
</c:if>
<%-- END RECUPERO PARAMETRI--%>
<%-- BEGIN SEZIONE FUNZIONI --%>
	<c:set var="mostra" value="true"></c:set>
	<c:if test="${mostraAbilitato eq '0'}">
		<c:set var="mostra" value="false"></c:set>
	</c:if>
<c:choose>	
	<c:when test="${funzioneRichiesta eq 'visualizzamovimentiPadre'}">
		<c:if test="${mostra eq true}">
			<a href="javascript:visualizzaMovimentiPadre(${codiceMovimentoId})" title="<fmt:message key="label.visualizza_movimenti_padre" />"><img alt="<fmt:message key="label.visualizza_movimenti_padre" />" src="${pageContext.request.contextPath}/images/table/prevPage.gif"  /></a>
		</c:if>
		<c:if test="${mostra eq false}">
			<img title="<fmt:message key="label.non_visualizza_movimenti_padre" />" alt="<fmt:message key="label.non_visualizza_movimenti_padre" />" src="${pageContext.request.contextPath}/images/table/prevPageDisabled.gif"  />
		</c:if>
	</c:when>
	<c:when test="${funzioneRichiesta eq 'visualizzamovimentiFiglio'}">
		<c:if test="${mostra eq true}">
			<a href="javascript:visualizzaMovimentiFiglio(${codiceMovimentoId})" title="<fmt:message key="label.visualizza_movimenti_figlio" />"><img alt="<fmt:message key="label.visualizza_movimenti_figlio" />" src="${pageContext.request.contextPath}/images/table/nextPage.gif"  /></a>		
		</c:if>
		<c:if test="${mostra eq false}">
			<img title="<fmt:message key="label.non_visualizza_movimenti_figlio" />" alt="<fmt:message key="label.non_visualizza_movimenti_padre" />" src="${pageContext.request.contextPath}/images/table/nextPageDisabled.gif"  />		
		</c:if>
	</c:when>
	<c:when test="${funzioneRichiesta eq 'javascriptBlock'}">
		
		<div dojoType="dijit.Dialog" id="fxMovimentiFiglioDlg"	title="<fmt:message key="label.movimenti_istanza" />" >
		</div>
		
		<script type="text/javascript">
			
			function visualizzaMovimentiFiglio(codice){
				
				dijit.byId('fxMovimentiFiglioDlg').show();
				var ts=new Date().getTime();
				new Ajax.Request('<%=request.getContextPath()%>/movimenti/ajaxListaMovimentiContromovimenti.htm?ts='+ts, {
					  method: 'post',
					  parameters: {codiceMovimento: codice, tipo:'figlio'},
					  onSuccess: function(transport){
						  var response = transport.responseText;		
						  result = parseAjaxResponse(response, true, false);
						  dijit.byId('fxMovimentiFiglioDlg').attr("style", "overflow:auto; width:800px");
						  dijit.byId('fxMovimentiFiglioDlg').attr("content", result);
						  // dijit.byId('fxMovimentiFiglioDlg').show();							  
					    },
					  onFailure: function(transport){ 
						var response = transport.responseText;
						dijit.byId('fxMovimentiFiglioDlg').attr("style", "overflow:auto; width:800px");
						dijit.byId('fxMovimentiFiglioDlg').attr("content", response);
						dijit.byId('fxMovimentiFiglioDlg').show();	
					  }
				});		
			}
			function visualizzaMovimentiPadre(codice){
				
				dijit.byId('fxMovimentiFiglioDlg').show();
				var ts=new Date().getTime();
				new Ajax.Request('<%=request.getContextPath()%>/movimenti/ajaxListaMovimentiContromovimenti.htm?ts='+ts, {
					  method: 'post',
					  parameters: {codiceMovimento: codice, tipo:'padre'},
					  onSuccess: function(transport){
						  var response = transport.responseText;		
						  result = parseAjaxResponse(response, true, false);
						  dijit.byId('fxMovimentiFiglioDlg').attr("style", "overflow:auto;  width:800px");
						  dijit.byId('fxMovimentiFiglioDlg').attr("content", result);
						  // dijit.byId('fxMovimentiFiglioDlg').show();							  
					    },
					  onFailure: function(transport){ 
						var response = transport.responseText;
						dijit.byId('fxMovimentiFiglioDlg').attr("style", "overflow:auto;  width:800px");
						dijit.byId('fxMovimentiFiglioDlg').attr("content", response);
						dijit.byId('fxMovimentiFiglioDlg').show();	
					  }
				});	
			}
			
			
			
			
			var visualizzaSezioneAttivitaMovimento = function (){
				
				document.querySelectorAll('.movimenti_info_attivita_div').forEach(async (item) => {
					
					let codicemovimento = item.getAttribute('data-codicemovimento');

					const data = new URLSearchParams();
					data.append('codicemovimento',codicemovimento);
					
					const response = await fetch("${pageContext.request.contextPath}/movimenti/ajaxSezioneInfoAttivitaTipoMovimento.htm", {
		                method: "POST",
		                cache: "no-cache",
		                body: data                
					});		
					
					let messaggio = await response.text();
										
					
					if (response.status === 200) {
						impostaInnerHTMLConScript(item, messaggio);
					}else{					
						item.innerHTML = messaggio;
					}
										
				});
			}
			
			
			function apriModalInfoAttivitaMovimento(htmlContentDivId){
				
				let divModalId = 'info-attivita-movimento-div';
				let divModalIdBody = divModalId+'-body';
				let vbgModalInfoMovimenti  =  document.getElementById(divModalId);
				
				if(!vbgModalInfoMovimenti){	
					let modal = document.createElement('div');
					
					modal.innerHTML = `<vbg-modal id='\${divModalId}'> 
										<div slot='body' id='\${divModalIdBody}'></div>
										</vbg-modal>`;
									
					
					document.body.appendChild(modal);
				}
			
				vbgModalInfoMovimenti  =  document.getElementById(divModalId);
				
				document.getElementById(divModalIdBody).innerHTML = document.getElementById(htmlContentDivId).innerHTML;
				
		     	
		     	vbgModalInfoMovimenti.open();
			}
			
			
			
			var visualizzaSezioneAllegatiMovimento = function (){
				
				document.querySelectorAll('.movimenti_allegati_div').forEach(async (item) => {
					
					let codicemovimento = item.getAttribute('data-codicemovimento');

					const data = new URLSearchParams();
					data.append('codicemovimento',codicemovimento);
					
					const response = await fetch("${pageContext.request.contextPath}/movimenti/ajaxSezioneAllegati.htm", {
		                method: "POST",
		                cache: "no-cache",
		                body: data                
					});		
					
					let messaggio = await response.text();
										
					
					if (response.status === 200) {
						impostaInnerHTMLConScript(item, messaggio);
					}else{					
						item.innerHTML = messaggio;
					}
										
				});
			}
			
			vbg.ready(() => {	

							
				visualizzaSezioneAllegatiMovimento();	
				visualizzaSezioneAttivitaMovimento();
				
			}); 
			
		</script>
		 
	</c:when>
</c:choose>
<%-- END SEZIONE FUNZIONI --%>
