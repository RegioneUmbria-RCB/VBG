<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><c:out value="${CURRENT_STEP.titolo }"></c:out></title>
</head>
<body>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#button_aggiungi_soggetto_pf").click(function(){
			$("#tipo").val("PF");
		});
		$("#button_aggiungi_soggetto_pg").click(function(){
			$("#tipo").val("PG");
		});	
	});
	function modificaSoggetto(id,tipo){
		if(confirm("<fmt:message key='alert.modifica-soggetto' />")){
			window.location.replace("modificaSoggetto.htm?idSogg="+id+"&tipoSogg="+tipo);
		}
	}
	function eliminaSoggetto(id){
		if(confirm("<fmt:message key='alert.elimina-soggetto' />")){
			window.location.replace("eliminaSoggetto.htm?idSogg="+id);
		}
	}
	function collegaSoggetto(id){
		var n = $("[for=PG]").length;
		if(n == 0){
			alert("<fmt:message key='alert.non-ci-sono-persone-giuridiche-da-collegare' />");
		}else{
			var titolo = "<fmt:message key='label.scegli-soggetto-da-collegare' />";
			var soggDialog = $("<div class='dialog' title='" + titolo + "'></div>");
			$("[for=PG]").each(function(){
				var idSoggColl = $(this).attr('id');
				var nomeSoggColl = $(this).html();
				var htmlSoggColl = $("<p class='soggDaColl'>"+nomeSoggColl+"<br /></p>");
				var actionSoggColl = $("<p><fmt:message key='label.collega-soggetto' /></p>")
				.click(function(){
					window.location.replace("collegaSoggetto.htm?idSogg="+id+"&idSoggColl="+idSoggColl);
				}).button();
				htmlSoggColl.append(actionSoggColl);
				soggDialog.append(htmlSoggColl);
			});
			createDialog(soggDialog);
		}
	}
	function eliminaSoggettoCollegato(id){
		if(confirm("<fmt:message key='alert.elimina-collegamento' />")){
			window.location.replace("eliminaSoggettoCollegato.htm?idSogg="+id);
		}
	}
	
	function createDialog(text) {
	    return text
	    .dialog({
	        resizable: false,
	        modal: true,
	        buttons: {
	            "<fmt:message key='button.chiudi' />": function() {
	                $( this ).dialog( "close" );
	            }
	        }
	    });
	}
	
	</script>
	<div class="titolo"><c:out value="${CURRENT_STEP.titolo }"></c:out></div>
	<div class="descrizione"><c:out value="${CURRENT_STEP.descrizione }"></c:out></div>
	
	<%@ include file="../includes/alert.jsp" %>
	
	<spring-form:form action="save.htm" method="post" commandName="nuovaIstanzaCommand" id="soggettiPOST">
	
	<input type="hidden" name="pager_step" />
	<input type="hidden" name="pager_azione" />
	
	<div class="titolo_sezione"><fmt:message key='label.soggetti-registrati' /></div>
	<div class="sezione">
	<c:if test="${not empty nuovaIstanzaCommand.altriSoggettiHelper }">
	<table class="sezione_table" border="0" cellpadding="5">
	<thead><tr class="sezione_table_h"><th><fmt:message key='label.soggetto' /></th><th><fmt:message key='label.ruolo' /></th><th><fmt:message key='label.soggetto-collegato' /></th><th>&nbsp;</th></tr></thead>	
	<c:forEach items="${nuovaIstanzaCommand.altriSoggettiHelper }" var="currentAS" varStatus="count">	
		<c:set var="row" value="" />
		<c:choose>
			<c:when test="${count.index % 2 == 0 }"><c:set var="row" value="odd" /></c:when>
			<c:otherwise><c:set var="row" value="" /></c:otherwise>
		</c:choose>
		<tr class="sezione_table_row_${row }">
		<c:if test="${not empty currentAS.soggetto.soggetto.personaFisica.codiceFiscale}">
		<td class="sezione_table_label2">${currentAS.soggetto.soggetto.personaFisica.cognome} ${currentAS.soggetto.soggetto.personaFisica.nome}<br />
		<fmt:message key='label.cf' /> (${currentAS.soggetto.soggetto.personaFisica.codiceFiscale})</td>
		<td>${currentAS.soggetto.tipoRapporto.ruolo}</td>
		<td class="sezione_table_label2">
			<c:if test="${currentAS.tipoSoggetto.richiedianagrafecoll eq true }">
			<c:choose>
			<c:when test="${empty currentAS.soggetto.anagraficaCollegata.personaGiuridica.ragioneSociale}">
			<input type="button" value="<fmt:message key='button.collega-soggetto' />" onclick="collegaSoggetto('${count.index}')" />
			</c:when>
			<c:otherwise>
			${currentAS.soggetto.anagraficaCollegata.personaGiuridica.ragioneSociale}
			<input type="button" value="X" onclick="eliminaSoggettoCollegato('${count.index}')" title="<fmt:message key='label.elimina-collegamento' />"/>
			</c:otherwise>
			</c:choose>
			</c:if>&nbsp;
		</td>
		<td class="sezione_table_buttons">
			<input type="button" value="<fmt:message key='button.modifica' />" onclick="modificaSoggetto('${count.index}','PF')" />
			<input type="button" value="<fmt:message key='button.elimina' />" onclick="eliminaSoggetto('${count.index}')" />
		</td>		
		</c:if>
		<c:if test="${not empty currentAS.soggetto.soggetto.personaGiuridica.ragioneSociale}">
		<td class="sezione_table_label2"><label for="PG" id="${count.index}">${currentAS.soggetto.soggetto.personaGiuridica.ragioneSociale}
		<br />
		P.Iva (${currentAS.soggetto.soggetto.personaGiuridica.partitaIva}) C.F. (${currentAS.soggetto.soggetto.personaGiuridica.codiceFiscale})</label></td>
		<td>${currentAS.soggetto.tipoRapporto.ruolo}</td>
		<td>&nbsp;</td>
		<td class="sezione_table_buttons">
			<input type="button" value="<fmt:message key='button.modifica' />" onclick="modificaSoggetto('${count.index}','PG')" />
			<input type="button" value="<fmt:message key='button.elimina' />" onclick="eliminaSoggetto('${count.index}')" />
		</td>
		</c:if>
		</tr>
	</c:forEach>
	</table>
	</c:if>
	<c:if test="${empty nuovaIstanzaCommand.altriSoggettiHelper }">
		<div><fmt:message key='label.nessun-soggetto' /></div>
		<div class="titolo_sottosezione"></div>
	</c:if>
	</div>
	</spring-form:form>
	<spring-form:form action="sceltaRuolo.htm" method="post" commandName="nuovaIstanzaCommand" id="sceltaRuolo">
	<input type="hidden" name="tipo" id="tipo" />
	<div id="cerca_altri_soggetti_buttons" class="sezione">
		<table class="sezione_table">
		<tr>
		<td class="sezione_table_buttons">
			<input type="submit" value="<fmt:message key='button.aggiungi-persona-fisica' />" id="button_aggiungi_soggetto_pf" />
			<input type="submit" value="<fmt:message key='button.aggiungi-persona-giuridica' />" id="button_aggiungi_soggetto_pg" />
		</td>
		</tr>
		</table>	
	</div>
	
	</spring-form:form>

	<%@ include file="../includes/pager.jsp" %>
</body>
</html>