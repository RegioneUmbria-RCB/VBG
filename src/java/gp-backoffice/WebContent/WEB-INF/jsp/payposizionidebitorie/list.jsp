<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title><fmt:message key="label.posizioni_debitorie.title" /></title>
</head>
<body>
	<span class="titoloPagina"> <init:editLabel
			key="label.posizioni_debitorie.title" role="ROLE_EDITLABEL" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../payposizionidebitorie/list" />
	</jsp:include>
	<div id="subcontent">


		<form name="inviodati" action="list.htm">

			<fieldset style="width: 90%; padding: 2px;">
				<legend>Filtri</legend>
				<div>
					<div>Associato a giornata di mercato</div>
					<div>
						<select name="presenze" id="presenze_id">
							<option value="">Tutti</option>
							<option
								<%if ("0".equalsIgnoreCase((String) request.getAttribute("presenze"))) {%>
								selected="selected" <%}%> value="0">No</option>
							<option
								<%if ("1".equalsIgnoreCase((String) request.getAttribute("presenze"))) {%>
								selected="selected" <%}%> value="1">Sì</option>
						</select>
					</div>
				</div>
				<div>
					<div>Pagamento completato</div>
					<div>
						<select name="pagato" id="pagato_id">
							<option value="">Tutti</option>
							<option
								<%if ("0".equalsIgnoreCase((String) request.getAttribute("pagato"))) {%>
								selected="selected" <%}%> value="0">No</option>
							<option
								<%if ("1".equalsIgnoreCase((String) request.getAttribute("pagato"))) {%>
								selected="selected" <%}%> value="1">Sì</option>
						</select>
					</div>
				</div>
				<div>
					<div>Descrizione</div>
					<div>
						<input type="text" name="descrizione" id="descrizione_id"
							size="60" value="<%=request.getAttribute("descrizione")%>"></input>
					</div>
				</div>
				<div>
					<div>id</div>
					<div>
						<input type="text" name="id" id="id_id" size="60"
							value="<%=request.getAttribute("id")%>"></input>
					</div>
				</div>
				<ul id="functions">
					<li><a href="javascript:applicaFiltri()">Applica filtri</a></li>
				</ul>
			</fieldset>

			${htmltable}
		</form>
		<script type="text/javascript">
			var _jmesaUrl = 'list.htm?';
			var _captionTab = '<fmt:message key="label.posizioni_debitorie.title" />';

			function applicaFiltri() {

				doSubmit('list.htm', '', document.inviodati);
			}
			jQuery('.azioni_posizioni_debitorie').click(function() {
				console.log(jQuery(this).data('id'));
				dettaglioPosizioneDebitoria(jQuery(this).data('id'));
			})

			function annullaPosizioneDebitoria(id) {
				if (confirm('Continuare con l\'operazione? Sara\'registrata nei log')) {
					disableFunctions();
					jQuery
							.ajax({
								url : '${pageContext.request.contextPath}/payposizionidebitorie/ajaxAnnullaPosizioneDebitoria.htm',
								dataType : 'html',
								type : 'POST',
								data : 'idPosizioneDebitoria=' + id,
								cache : false,
								success : function(data, textStatus, jqXHR) {

									enableFunctions();
									alert(data);

								},
								error : function(jqXHR, textStatus, errorThrown) {
									enableFunctions();
									alert("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
								}
							});
				}
			}

			function segnaPagatoPosizioneDebitoria(id) {
				if (confirm('Continuare con l\'operazione? Sara\'registrata nei log')) {
					disableFunctions();
					jQuery
							.ajax({
								url : '${pageContext.request.contextPath}/payposizionidebitorie/ajaxSegnaPosizioneDebitoriaPagatoOffline.htm',
								dataType : 'html',
								type : 'POST',
								data : 'idPosizioneDebitoria=' + id
										+ '&riferimentiPagamento=',
								cache : false,
								success : function(data, textStatus, jqXHR) {

									enableFunctions();
									alert(data);

								},
								error : function(jqXHR, textStatus, errorThrown) {
									enableFunctions();
									alert("<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
								}
							});
				}
			}
			function dettaglioPosizioneDebitoria(id) {

				disableFunctions();
				jQuery
						.ajax({
							url : '${pageContext.request.contextPath}/payposizionidebitorie/ajaxDettaglioPosizione.htm',
							dataType : 'html',
							type : 'POST',
							data : 'idPosizioneDebitoria=' + id,
							cache : false,
							success : function(data, textStatus, jqXHR) {

								enableFunctions();
								jQuery('#genericDialogContainer').dialog({
									autoOpen : false,
									modal : true,
									width : '80%',
									height : 300,
									resizable : true,
									title : 'Dettaglio'
								}).html(data);

								jQuery('#genericDialogContainer')
										.dialog("open");

							},
							error : function(jqXHR, textStatus, errorThrown) {
								enableFunctions();
								jQuery('#genericDialogContainer')
										.html(
												"<div class=\"error_header\">Si è verificato un errore nel caricamento dei dati</div>");
							}
						});
			}
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a
				href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
	<div id="genericDialogContainer"></div>
</body>
</html>