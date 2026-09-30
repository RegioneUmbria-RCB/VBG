<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<fmt:message key="responsabili.label.dettaglio_ruoli.title" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="responsabili.label.dettaglio_ruoli.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="responsabile" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="responsabile" />
    </jsp:include>
    
<div id="docer_status_id" style="margin:10px;">
						
</div>    
    
<div class="jmesa">
	<table class="table" style="width: 50%;">
		<thead>
			<tr class="header">
			<td><fmt:message key="responsabili.label.responsabiliruoli" /> <b>${responsabile.responsabile}</b></td>
			<c:if test="${isDocErAttivo}">
				<td><fmt:message key="label.cod_ruolo_docer" /></td>
				<td>Stato in DOC-ER</td>
			</c:if>
			<td><spring-form:errors path="responsabiliruolis" cssClass="error"/></td>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${ruolis}" var="ruoli_var" varStatus="a">
			<tr class="${((a.index%2)==0)?'odd':'even' }">
				<td>
				<c:if test="${!ruoli_var.ruoloResponsabileTransient}">
					<input type="checkbox" value="${ruoli_var.id.codice}" name="ruolis" />${ruoli_var.ruolo}
				</c:if>
				<c:if test="${ruoli_var.ruoloResponsabileTransient}">
					<input  type="checkbox" value="${ruoli_var.id.codice}" name="ruolis" checked="checked"/>${ruoli_var.ruolo}
				</c:if>
				</td>
				
				<c:if test="${isDocErAttivo}">
					<td>
						${ruoli_var.codDocer}
					</td>
					<td id="docer_status_id_${ ruoli_var.id.codice }">
						
					</td>
				</c:if>
				<td></td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
		
</div>
	
</spring-form:form>
</div>

<script type="text/javascript">


	function verificaRuoli(){
		var listaruoli = '';
		jQuery("input:checkbox[name='ruolis']").each(function(){
			// if($(this).checked){
				listaruoli+=$(this).value+",";
			// }
		});
		if(listaruoli!=''){
			jQuery('#docer_status_id').html( htmlVerifica);
			listaruoli = listaruoli.substring(0,(listaruoli.length)-1);			
			var jhqrPr = jQuery.ajax({
				  url: '../ajaxdocer/controllaEsistenzaRuoliUtente.htm',
				  context: document.body,
				  cache: false,
				  data: "codiceUtente=${responsabile.id.codice}&listaruoli="+listaruoli,
				  dataType: "text",
				  success: function(data) {
					  if(data){
							if(data.substring(0,4)=='#OK#'){
								jQuery('#docer_status_id').html('');
								mostraDatiOK(data);
							}else if (data.substring(0,4)=='#KO#'){
								jQuery('#docer_status_id').html('');
								mostraDatiKO(data);
							}else{
								jQuery('#docer_status_id').html('');
								mostraDatiERRORE(data);
							}
					  }
				  },
				  error: function(jqXHR, textStatus, errorThrown){
						console.error("Errore: " + jqXHR.responseText);
				}
			});	
		}
		
	}

	var htmlVerifica = "<span class=\"error_header\">Verifica esistenza delle assegnazioni dei ruoli all'utente in corso <img src=\"${pageContext.request.contextPath}/images/spinner.gif\" /></span>";
	var htmlCreazione = "Creazione utente in corso <img src=\"${pageContext.request.contextPath}/images/spinner.gif\" />";
	
	
	function  mostraDatiERRORE(errore){
		
		jQuery('#docer_status_id').html('&nbsp;<span class="error_header">'+errore+'</span>');
	}
	function  mostraDatiKO(risultato){
		
	}
	function  mostraDatiOK(risultato){
		
		var risposta = risultato.replace('#OK#','');
		var lista = risposta.split('|');
		
		for(var i=0;i<lista.length;i++){
			var elab=lista[i].split('!!!');
			
			var idRuolo=elab[0];
			if(idRuolo!=''){
				var esito = elab[1];			
				if(esito=='OK'){
					mostraOK(idRuolo);
				}else{
					mostraKO(idRuolo);
				}
			}
		}
		
	}
	
	function mostraOK(idruolo){
		var htmlRimuovi = '<a href="javascript:void(0)" onclick="rimuoviRuoloUtente(\''+idruolo+'\',${responsabile.id.codice})"><img src=\"${pageContext.request.contextPath}/images/cross.gif\" /></a>';
		jQuery('#docer_status_id_'+idruolo).html('<img title="Il gruppo è associato all\'utente" src=\"${pageContext.request.contextPath}/images/success.gif\" />&nbsp;'+htmlRimuovi);
	}
	
	function mostraKO(idruolo){
		var htmlKO = '<a href="javascript:void(0)" title="Il gruppo non è associato all\'utente" onclick="creaRuoloUtente(\''+idruolo+'\',${responsabile.id.codice})"><img src=\"${pageContext.request.contextPath}/images/warning.gif\" /></a>';		
		jQuery('#docer_status_id_'+idruolo).html(htmlKO);
	}
	
	function mostraERRORE(idruolo,errore){
		var htmlKO = '<a href="javascript:void(0)"  title="Il gruppo non è associato all\'utente o non è corretto" onclick="creaRuoloUtente(\''+idruolo+'\',${responsabile.id.codice})"><img src=\"${pageContext.request.contextPath}/images/warning.gif\" /></a>';				
		jQuery('#docer_status_id_'+idruolo).html(htmlKO+'&nbsp;<span class="error_header">'+errore+'</span>');
	}	

	jQuery(document).ready(function(){
		
		verificaRuoli();
		
	});
	
	
	function creaRuoloUtente(idruolo, codiceresponsabile){
			if(confirm('Procedere se si intende creare la relazione utente-gruppo in DOCER')){
				jQuery('#docer_status_id').html( htmlCreazione );
				var jhqrPr = jQuery.ajax({
				  url: '../ajaxdocer/creaRuoloUtente.htm',
				  context: document.body,
				  cache: false,
				  data: "codiceUtente="+codiceresponsabile+"&idRuolo="+idruolo,
				  dataType: "text",
				  success: function(data) {
					  if(data){
						  if(data=='true'){
							  verificaRuoli();
							}else if (data=='false'){
								verificaRuoli();
							}else{
								mostraERRORE(data);
							}
					  }
				  },
				  error: function(jqXHR, textStatus, errorThrown){
						console.error("Errore: " + jqXHR.responseText);
				}
			});
		}
	}
	
	function rimuoviRuoloUtente(idruolo, codiceresponsabile){
		if(confirm('Procedere se si intende rimuovere la relazione utente-gruppo in DOCER')){
			jQuery('#docer_status_id').html( htmlCreazione );
			var jhqrPr = jQuery.ajax({
			  url: '../ajaxdocer/rimuoviRuoloUtente.htm',
			  context: document.body,
			  cache: false,
			  data: "codiceUtente="+codiceresponsabile+"&idRuolo="+idruolo,
			  dataType: "text",
			  success: function(data) {
				  if(data){
					  if(data=='true'){
						  verificaRuoli();
						}else if (data=='false'){
							verificaRuoli();
						}else{
							mostraERRORE(data);
						}
				  }
			  },
			  error: function(jqXHR, textStatus, errorThrown){
					console.error("Errore: " + jqXHR.responseText);
			}
		});
		}
}

</script>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('saveRuoli.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	<li><a href="javascript:doHref('view.htm?codice=${responsabile.id.codice}','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
