<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- 
PARAMETRI:
	funzioneRichiesta
		funzioni possibili:
			funzioni: 				
				visualizza i bottoni di azione
			javascriptBlock:
				riporta il blocco javascript per effettuare le chiamate al dettaglio questa funzione va messa di solito in fondo alla pagina

I seguenti parametri sono validi nel caso che la funzioneRichiesta equivalga a 	'funzioni'
	idElemento:
		l'id della select dal quale recuperare il messaggio		
	showElabora:
		visualizza il bottone elabora
	showElaboraTutti:
		visualizza il bottone elabora					
	showCancella:
		visualizza il bottone cancella
	showCancellatutti:
		visualizza il bottone cancella tutti
 --%>
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<c:set var="showPreElabora" value="false" />
<c:if test="${not empty param.showPreElabora}">
	<c:set var="showPreElabora" value="${param.showPreElabora}" />
</c:if>
<c:set var="showElabora" value="true" />
<c:if test="${not empty param.showElabora}">
	<c:set var="showElabora" value="${param.showElabora}" />
</c:if>
<c:set var="showElaboraTutti" value="false" />
<c:if test="${not empty param.showElaboraTutti}">
	<c:set var="showElaboraTutti" value="${param.showElaboraTutti}" />
</c:if>
<c:set var="showCancella" value="true" />
<c:if test="${not empty param.showCancella}">
	<c:set var="showCancella" value="${param.showCancella}" />
</c:if>
<c:set var="showCancellaTutti" value="true" />
<c:if test="${not empty param.showCancellaTutti}">
	<c:set var="showCancellaTutti" value="${param.showCancellaTutti}" />
</c:if>

<c:set var="funzioneRichiesta" value="funzioni"/>
<c:if test="${not empty param.funzioneRichiesta}">
	<c:set var="funzioneRichiesta" value="${param.funzioneRichiesta}" />
</c:if>
<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
<%-- BEGIN SEZIONE FUNZIONI --%>
<c:if test="${funzioneRichiesta eq 'funzioni'}">
	<c:if test="${empty param.idElemento}">
		[funzioni_cart.jsp]  Attenzione !! non è stato settato il parametro idElemento.
	</c:if>
	<div id="functions">
		<ul>
			<c:if test="${showPreElabora eq true}">
				<li>
					<a href="javascript:void(0)" onclick="preelaboraMessaggio('${param.idElemento}');"><fmt:message key="stp.label.elabora" /></a>
				</li>
			</c:if>
			<c:if test="${showElabora eq true}">
				<li>
					<a href="javascript:void(0)" onclick="elaboraMessaggio('${param.idElemento}');"><fmt:message key="stp.label.elabora" /></a>
				</li>
			</c:if>
			<c:if test="${showElaboraTutti eq true}">
				<li>
					<a href="javascript:void(0)" onclick="elaboraTuttiMessaggi('${param.idElemento}');"><fmt:message key="stp.label.elabora_tutti" /></a>
				</li>
			</c:if>
			<c:if test="${showCancella eq true}">		
				<li>
					<a href="javascript:void(0)" onclick="cancellaMessaggio('${param.idElemento}');"><fmt:message key="stp.label.cancella" /></a>
				</li>
			</c:if>
			<c:if test="${showCancellaTutti eq true}">		
				<li>
					<a href="javascript:void(0)" onclick="cancellaTuttiMessaggi('${param.idElemento}');"><fmt:message key="stp.label.cancellatutti" /></a>
				</li>
			</c:if>
		</ul>
	</div>
</c:if>
<c:if test="${funzioneRichiesta eq 'javascriptBlock'}">
	<script type="text/javascript">
		var eseguiOperazione = function(divId, operazione){
			var idEgov = document.getElementById(divId).value;
			if(operazione!=''){
				if(operazione=='preelabora')
				{
					tabPreElabora('dialogDivPreElaborazione', divId);
				}
				else{
					doHref('eseguiOperazione.htm?operazione='+operazione+'&idEgov='+idEgov+"&servizio="+divId,'');
				}
			}
		};
		var preelaboraMessaggio = function(divId){			
			eseguiOperazione(divId,'preelabora');
		};
		var elaboraMessaggio = function(divId){			
			eseguiOperazione(divId,'elabora');
		};
		var elaboraTuttiMessaggi = function(divId){			
			eseguiOperazione(divId,'elaboratutti');
		};
		var cancellaMessaggio = function(divId){
			if(confirm('<fmt:message key="javascript.confirm.delete" />')){
				eseguiOperazione(divId,'cancella');
			}
		};
		var cancellaTuttiMessaggi = function(divId){
			if(confirm('<fmt:message key="javascript.confirm.delete" />')){
				eseguiOperazione(divId,'cancellaTutti');
			}
		};
		
		
		function tabPreElabora(dialogDiv, divIdEgov){
			<%-- Il DIV che viene richiamato si strova sulla cart/form.jsp  ('dialogDivPreElaborazione') --%>
			
			preElabora(divIdEgov,dialogDiv);		
		}
		
		function preElabora(divIdEgov, dialogDiv) {
				if(dialogWorking){
					dialogWorking.show();
				}
				var idEgov = document.getElementById(divIdEgov).value;
				var _ts = new Date().getTime();				
				jQuery.ajax({
					  type: "GET",
					  url: '${pageContext.request.contextPath}/cart/ajaxPreElabora.htm?idEgov='+ idEgov,
					  dataType: "html",
					  cache: false,
					  success: function(data) {
							if(dialogWorking){
							  dialogWorking.hide();
							}
							dijit.byId(dialogDiv).show();
						  	jQuery("#dialogPreElabora").html(data);	
						  }
					  ,
					  error: function(dataError){
						if(dialogWorking){
						  dialogWorking.hide();
						}
						dijit.byId(dialogDiv).show();
					  	jQuery("#dialogPreElabora").html(dataError.innerText);
						console.error(dataError.innerText);
					  }	
					});				
			}
		
		function validaAggiornaDizionario(formId){
			var myRegExpAmm = /amministrazioni[0-9]*_hidden/;
			var myRegExpTm = /tipimovimento[0-9]*_hidden/;
			var els = jQuery('#'+formId+' > * > * input[type=hidden]');
			for (var i=0;i<els.length;i++){
				var el = els[i];
				var elementid = el.id;
				if(elementid){
					if(myRegExpAmm.test(elementid) || myRegExpTm.test(elementid)){
						if(el.value==''){
							return false;
						}
					}
				}
			}
			return true;
		}
		
		function aggiornaDizionario( formObj , formId){
			if(validaAggiornaDizionario(formId)){
				doSubmit('proseguiElaborazione.htm','',formObj);
			}else{
				alert("Attenzione compilare tutti i campi relativi ad amministrazioni e tipimovimento");				
			}
		}
		
	</script>
</c:if>
<%-- END SEZIONE FUNZIONI --%>