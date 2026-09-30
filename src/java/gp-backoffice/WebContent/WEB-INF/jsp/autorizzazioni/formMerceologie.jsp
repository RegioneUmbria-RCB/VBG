<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.dettaglio_informazione" />
</title>
</head>
<body>
	<span class="titoloPagina"> <fmt:message
			key="label.dettaglio_informazione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent">

		<div class="parametriDiv">
			<div class="etichetta">
				<c:choose>
					<c:when test="${empty conc }">
						<div>
							<fmt:message key="label.dettaglio_autorizzazione" />
							:
						</div>
					</c:when>
					<c:otherwise>
						<div>
							<fmt:message key="label.dettaglio_concessione" />
							:
						</div>
					</c:otherwise>
				</c:choose>

			</div>
			<div class="parametro">
				<c:choose>
					<c:when test="${empty conc }">
						<div>${aut.transientEstremiAut}</div>
					</c:when>
					<c:otherwise>
						<div>${conc.transientEstremiConcessione}</div>
					</c:otherwise>
				</c:choose>


			</div>
		</div>
		

		<script type='text/javascript'>
			function setHiddenFieldAttivita(inputField, listItem) {
				var a = listItem.id;
				nuovaAttivita(a);
			}

			function filtertiposettore(element, entry) {
				return entry + "&codicesettore="
						+ document.getElementById("settori_hidden").value;
			}
		</script>
<p>&nbsp;</p>

		<table  >
			<tr>
				<td><fmt:message key="label.tipo_informazione" /></td>
				<td><input id="settori_id" name="settori" class="searchbox"
					size="40" onkeydown="return searchAll(this,event)" /> <init:autocompleter
						methodAjax="findSettori.htm?flagDisabilitato=false"						
						idHidden="settori_hidden" idInput="settori_id" inputTitleKey=""
						minChars="1" /> <input type="hidden" id="settori_hidden"
					name="settori_hidden" /></td>
			</tr>
			<tr>
				<td><fmt:message key="label.dettaglio_informazione" /></td> 
				<td><input
					id="attivita_id" name="attivita" class="searchbox" size="40"
					onkeydown="return searchAll(this,event)" /> <init:autocompleter
						methodAjax="findAttivita.htm?flagDisabilitato=false"
						afterUpdateElement="setHiddenFieldAttivita"
						idHidden="attivita_id_hidden" idInput="attivita_id"
						callBack="filtertiposettore" inputTitleKey="" minChars="1" /></td>
			</tr>
		</table>
		
		<div id="messaggioErrore" class="error_header" style="display: none;"></div>

		<div id="dettaglioGruppi">
			&nbsp;<img
				src='${pageContext.request.contextPath}/images/spinner.gif' />
		</div>

		
	</div>



	<div id="functions">
		<ul>
			<li><a href="javascript:historyBack()"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>

	<script type="text/javascript">
		var visualizzaDettaglioInfo = function(codiceAttivita) {

			var jhqrPr = jQuery
					.ajax({
						url : '${pageContext.request.contextPath}/autorizzazioni/ajaxDettaglioMerceologia.htm?idautorizzazione=${param.idautorizzazione}&codiceAttivitaInserito='
								+ codiceAttivita,
						context : document.body,
						cache : false,
						dataType : "html",
						success : function(data, textStatus, jqXHR) {
							if (data) {
								jQuery('#dettaglioGruppi').html(data);
								jQuery('#dettaglioGruppi').show();
							}
						}
					});

		}

		function nuovaAttivita(codice) {

			jQuery('#messaggioErrore').hide();

			if (codice!= '') {
				var jhqrPr = jQuery
						.ajax({
							url : "${pageContext.request.contextPath}/autorizzazioni/ajaxAssegnaAttivita.htm?idautorizzazione=${param.idautorizzazione}&codiceAttivita="
									+ codice,
							context : document.body,
							cache : false,
							dataType : "html",
							success : function(data, textStatus, jqXHR) {
								verificaErroreoSuccesso(data, codice);
								azzeraAutoCompleter();
							}
						});
			}

		}
		function azzeraAutoCompleter() {
			jQuery('#attivita_id').val('');
			jQuery('#attivita_id_hidden').val('');
		}

		function eliminaRiga(idRiga) {
			if (confirm('<fmt:message key="javascript.confirm.delete" />')) {
				// elimino l'eventuale messaggio di errore se presente
				jQuery('#messaggioErrore').hide();
				var jhqrPr = jQuery
						.ajax({
							url : '${pageContext.request.contextPath}/autorizzazioni/ajaxEliminaAttivita.htm?idRiga='
									+ idRiga,
							context : document.body,
							cache : false,
							dataType : "html",
							success : function(data, textStatus, jqXHR) {
								verificaErroreoSuccesso(data, '');
							}
						});

			}
		}

		function cancellaErrore() {
			jQuery('#messaggioErrore').hide();
			jQuery('#messaggioErrore').html('');
		}

		function verificaErroreoSuccesso(data, codice) {
			// verifica errori o altro e aggiorna
			if (data == 'OK') {
				visualizzaDettaglioInfo(codice);
			} else {
				jQuery('#messaggioErrore').html(data);
				jQuery('#messaggioErrore').show();
			}
		}

		jQuery(document).ready(function() {
			visualizzaDettaglioInfo('');
		});
	</script>

</body>
</html>
