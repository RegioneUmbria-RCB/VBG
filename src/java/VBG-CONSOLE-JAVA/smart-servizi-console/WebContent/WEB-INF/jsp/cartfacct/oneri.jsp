<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.List"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.MessaggioErrore"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand"%>
<%@page import="it.gruppoinit.pal.gp.core.service.FoDomandeOneriService"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.CartModuloHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Oggetti"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.apache.commons.lang.BooleanUtils"%>
<%@page import="org.apache.commons.lang.ObjectUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.EndoFACCT"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento"%>
<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomandeOneri"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
	<%!
	String placeholder(String input){
	    StringBuilder ph = new StringBuilder();
	    if(StringUtils.isNotEmpty(input)){
			ph.append("${").append(input).append("}");
	    }
	    return ph.toString();
	}
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="cartfacct.title.oneri" /></title>
	<%
	String  vJS = "3.11_2017-08-07_13.42";
	List<FoDomandeOneri> oneri = (List<FoDomandeOneri>)request.getAttribute("oneri");
	Map<Integer, Map<String, MessaggioErrore>> errorMap = (Map<Integer, Map<String, MessaggioErrore>>) request.getAttribute("errori_oneri"); 
	String valoreStatoEffettuato = (String)request.getAttribute("stato_effettuato");
	PresentazioneDomandaCartCommand command = (PresentazioneDomandaCartCommand) request.getAttribute("presentazioneDomandaCommand");
	Set<String> endoz = new HashSet<String>();
	Set<String> endoNoCartAttivi = new HashSet<String>();
	if(null != command){
	    endoz = command.getEndoAttivi();
	    endoNoCartAttivi = command.getEndoNoCartAttivi();
	}
	%>
	<link type="text/css" href="${pageContext.request.contextPath}/css/jquery.fileupload-ui.css" rel="stylesheet" ></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/init-facct.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/init-schededinamiche.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/autoNumeric-1.7.5.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-fp.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-ui.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-jui.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload-init.js?<%=vJS %>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
	<link type="text/css" href="${pageContext.request.contextPath}/css/schededinamiche.css" rel="stylesheet">
	<style type="text/css">
	.numberField{
		width: 60px;
		text-align: right;
	}
	.dateField{
		width: 80px;
	}
	.descrizioneCausale{
		font-weight: bold;
		font-style: normal;
	}
	.descrizioneEndo{
		font-weight: normal;
		font-style: italic;
	}
	.causaleField{
		width: 100%;
		margin-bottom: 2px;
	}
	.endoField{
		width: 100%;
	}
	.tableHeader{
		text-align: center;
	    border-width: 1px;
	    border-style: solid;
	    border-color: rgb(204, 204, 204);
	    /*
	    border-bottom-width: 1px;
	    border-bottom-style: solid;
	    border-bottom-color: rgb(204, 204, 204);
	    */
	}
	.importoColumn{
		width: 70px;
	}
	TD.importoColumn{
		padding-left: 2px;
		padding-right: 2px;
	}
	.totale{
		text-align: right;
		font-weight: bold;
	}
	.corpo{
		width: 100%;
	}
	.deleteColumn{
		max-width: 72px;
		min-width: 72px;
		/*
		padding-right: 4px;
		padding-left: 4px;
		*/
	}
	.descColumn{
		max-width: 500px;
		/*
		min-width: 72px;
		padding-right: 4px;
		padding-left: 4px;
		*/
	}
	.distintaOneri{
		border-width: 0px;
		width: 100%;
	}
	.riepilogo{
	    border-width: 1px;
	    border-style: solid;
	    border-color: rgb(204, 204, 204);
		border-radius: 4px;
		margin-top: 20px;
	}
	.titolo-riepilogo{
		background-color: rgb(224, 224, 224);
		/*
		height: 30px;
		text-align: center;
		*/
		padding-right: 20px;
		padding-left: 5px;
		padding-bottom: 5px;
		padding-top: 5px;
		vertical-align: middle;
	}
	.tabella-riepilogo{
		width: 100%;
	}
	</style>
</head>
<body>
	<script type="text/javascript">
		var oneriCount = <%=oneri.size()%>;
		var ricevute = [];
		var allowedExtensions = "<%= CartModuloHelper.buildFileTypeRestrictionsFromDynUpload("") %>";
	
		$(function(){
			$('.dateField').datepicker();
			$('.dateField').change(function(){
				isValidDate(this,true);
			});
			var activeFields = $('.numberField');
			activeFields = activeFields.not('[readonly]');
	    	$("#indietro").click(function(){
				$('#oneriform').attr('action','<%=request.getContextPath() %>/cart/caricaModulistica.htm');
				$('#oneriform').submit();
			});
			initializeNumericFieldFacct(activeFields,2,0);
			$('error_image').tooltip();
			riepilogo();
		});

		salvaOneri = function(){
			$('#oneriform').submit();
		};

		aggiungiOnere = function(){
			var template = $("#riga_distinta");
			if(template && template.length > 0){
				//$.tmpl(template,{index: maxProg}).appendTo(tabella.find('tbody'));
				var newIndex = 0;
				var tabella = $('#distinta_oneri');
				var param = {indice: oneriCount};
				var newRow = template.tmpl(param);
				newRow.appendTo(tabella.children('tbody'));
				$('#delbutton_' + oneriCount).button();
				$("#" + oneriCount + "\\.dataPagamento").datepicker();
				$("#" + oneriCount + "\\.dataPagamento").change(function(){
					isValidDate(this,true);
				});
				initializeNumericFieldFacct($("#" + oneriCount + "\\.importo"),2,0);
				$("#" + oneriCount + "\\.statoPagamento").change(function(event){
					attivaCampiPagamento(event.target);
					return riepilogo();
				});
				$("#" + oneriCount + "\\.statoPagamento").change();
				oneriCount++;
			}
		};

		eliminaOnere = function(index){
			var rowToDelete = $("#onere_" + index);
			if(rowToDelete.length > 0){
				rowToDelete.remove();
			}
		};

		attivaCampiPagamento = function(statusField){
			var srcName = $(statusField).attr('name');
			var srcVal = $(statusField).val();
			if(srcName != undefined && srcName.length > 1){
				var nameParts = srcName.split(/\./);
				if(nameParts.length > 0){
					var indexPart = nameParts[0];
					nameParts = nameParts.splice(0,1);
					nameParts = nameParts.join('\\.');
					var enable = srcVal == '<%= valoreStatoEffettuato %>';
					var trgt = $('#' + indexPart + '\\.tipoPagamento').add('#' + indexPart + '\\.dataPagamento').add('#' + indexPart + '\\.rifPagamento');
					displayElements(trgt,enable,renameDynFieldOnDisable);
					if(!enable){
						$('#' + indexPart + '\\.tipoPagamento\\.errmsg').hide();
						$('#' + indexPart + '\\.dataPagamento\\.errmsg').hide();
						$('#' + indexPart + '\\.rifPagamento\\.errmsg').hide();
					}
				}
			}
		};
		
		riepilogo = function(){
			//recupero i dati dalle righe del riepilogo
			var righe = $('#distinta_oneri').children('tbody').children('tr');
			//svuoto le tabelle dei pagamenti online e delle ricevute
			$('#riepilogo_online').children('tbody').empty();
			$('#riepilogo_ricevute').children('tbody').empty();
			var totOneri = 0.0;
			var totOnline = 0.0;
			var totPagato = 0.0;
			righe.each(function(i,element){
				var campi = $(element).find('input');
				campi = campi.add($(element).find('select'));
				var record = {};
				campi.each(function(ii,campo){
					var name = $(campo).attr('name');
					if(name != undefined){
						var dotPos = name.indexOf('.');
						if(dotPos > -1){
							var fieldName = name.substring(dotPos +1);
							var pathParts = fieldName.split('.');
							var obj = record;
							for(var pathIdx = 0; pathIdx < pathParts.length; pathParts++){
								if(pathIdx == pathParts.length-1){
									obj[pathParts[pathIdx]] = $(campo).val();
								}
								else{
									var propVal = obj[pathParts[pathIdx]];
									if(propVal == undefined){
										propVal = {};
									}
									obj[pathParts[pathIdx]] = propVal;
								}
							}
							record[fieldName] = $(campo).val();
						}
					}
				});
				record.index = i;
				var statoPag = record.statoPagamento;
				var importo = record.importo;
				if(importo.length > 0){
					try{
						importo = importo.replace(/\./,"");
						importo = Number(importo.replace( ',','.'));
					}catch(error){
						console.error("importo non numerico: " + importo);
					}
				}
				else{
					importo = 0.0;
				}
				totOneri += importo;
				//aggiungo al riepilogo pagamenti effettuati / ricevute
				if(statoPag == 'Effettuato'){
					totPagato += importo;
					var template = $("#riga_ricevuta");
					var newRow = template.tmpl(record);
					newRow.appendTo($('#riepilogo_ricevute').children('tbody'));
					//inizializzazione campo file e suoi contenuti
					var loadRicevute = ricevute[record.index];
					if(loadRicevute == undefined){
						loadRicevute = [];
					}
					var loadFiles = {
						files: loadRicevute
					};
					var postData = {
							digitalSignatureRequired: false,
							idonere: record.id.codice
					};
					initializeUploadRicevuta(i, i + ".oggettoRicevuta.id.codice", loadFiles, postData);
				}
				//aggiungo al riepilogo pagamenti da effettuare online
				else if(statoPag == 'Online'){
					totOnline += importo;
					var template = $("#riga_online");
					var newRow = template.tmpl(record);
					newRow.appendTo($('#riepilogo_online').children('tbody'));
				}
			});
			//aggiorno i totali
			$('#totale_oneri').text(totOneri.toFixed(2));
			$('#totale_online').text(totOnline.toFixed(2));
			$('#totale_pagato').text(totPagato.toFixed(2));
			if($('#riepilogo_online').children('tbody').children('tr').length > 0){
				//$('#paga_oneri').show();
				$('#div_online').show();
			}
			else{
				$('#div_online').hide();
			}
			if($('#riepilogo_ricevute').children('tbody').children('tr').length > 0){
				//$('#info_ricevute').show();
				$('#div_ricevute').show();
			}
			else{
				$('#div_ricevute').hide();
			}
			return true;
		};

		syncCampo = function(evt){
			var evtSource = $(evt.target);
			if(evtSource.length){
				var srcName = evtSource.attr('name');
				var firstDot = srcName.indexOf('.');
				if(firstDot > -1){
					var index = srcName.substring(0,firstDot);
					var fieldName = srcName.substring(firstDot +1);
					$('#' + index + '\\.' + fieldName + '\\.label').text(evtSource.val());
				}
			}
		};

		initializeUploadRicevuta = function(index, inputElementId, dbFilesJson, uploadParamsJson){
			var selector = escapeStringForCssSelector(inputElementId);
			var multiFileOptions = {
					acceptFileTypes: allowedExtensions,
					maxUpload: 1,
					url: '<%= request.getContextPath()%>/cart/ajaxUploadRicevutaOnere.htm?disableMR=1',
					dataType: 'json',
					type: 'POST',
					formData: uploadParamsJson,
					uploadTemplateId: 'cart-template-upload',
					downloadTemplateId: 'cart-template-download',
					autoUpload: true,
					uploadTemplate: function(o){
						var uplTpl = $('#' + o.options.uploadTemplateId);
						var rendered = uplTpl.tmpl(o);
						return rendered;
					},
					downloadTemplate: function(o){
						var dwnlTpl = $('#' + o.options.downloadTemplateId);
						var rendered = dwnlTpl.tmpl(o);
						return rendered;
					},
					inputName: inputElementId,
					sent: function (e, data) {
						var emptyValueHiddenField = $("#fileupload-" + selector).find('#empty-' + selector);
						if(emptyValueHiddenField.length > 0){
							$(this).data('blueimpFileupload').emptyValueField = emptyValueHiddenField.detach();
						}
					},
					destroyed: function (e, data) {
						var theControl = $(this).data('blueimpFileupload');
						var theRows = theControl.options.filesContainer.find('tr');
						if(theRows.length == 0 && theControl.emptyValueField){
							theControl.emptyValueField.appendTo("#fileupload-" + selector);
						}
					}
				};
			//$("#" + inputElementId).button();
			$("#fileupload-" + selector).fileupload();
			$("#fileupload-" + selector).fileupload('option',multiFileOptions);
			$("#fileupload-" + selector).bind('fileuploadsent', function (e, data) {
				var emptyValueHiddenField = $("#fileupload-" + selector).find('#empty-' + selector);
				if(emptyValueHiddenField.length > 0){
					$(this).data('blueimpFileupload').emptyValueField = emptyValueHiddenField.detach();
				}
			});
			$("#fileupload-" + selector).bind('fileuploaddestroy', function (e, data) {
				ricevute[index] = undefined;
				var theControl = $(this).data('blueimpFileupload');
				var theRows = theControl.options.filesContainer.find('tr');
				if(theRows.length = 0 && theControl.emptyValueField){
					theControl.emptyValueField.appendTo("#fileupload-" + selector);
				}
			});
			if(dbFilesJson){
				$("#fileupload-" + selector).fileupload('option', 'done').call($("#fileupload-" + selector), $.Event('done'), {result: dbFilesJson});
				$("#fileupload-" + selector).fileupload('option', 'sent').call($("#fileupload-" + selector), $.Event('sent'), {result: dbFilesJson});
			}
			$("#fileupload-" + selector).data('blueimpFileupload').enableDsValidation(uploadParamsJson.dsValidation);
			$("#fileupload-" + selector).bind('fileuploaddone', function (e, data) {
				ricevute[index] = data.files;
				if(data.idOnere){
					var fName = escapeStringForCssSelector(index + ".id.codice");
					$('#' + fName).val(data.idOnere);
				}
			});
		};
		
	</script>

	<jsp:include page="../includes/messaggio_aggiornamento.jsp">
			<jsp:param name="settimeout" value="false"></jsp:param>
	</jsp:include>
	<div class="titolo" align="center"><fmt:message key="cartfacct.title.oneri" /></div>
	<div class="descrizione"></div>
	<div class="corpo">
	<form action="salvaOneri.htm" method="post" enctype="application/x-www-form-urlencoded" id="oneriform">
		<input type="hidden" name="returnTo" value="${presentazioneDomandaCommand.returnTo }"/>
	    <input type="hidden" name="idDomandaFo" value="${presentazioneDomandaCommand.idDomandaFo }"/>
	    <input type="hidden" name="token" value="${presentazioneDomandaCommand.token }"/>
	    <input type="hidden" name="idDomandaCart" value="${presentazioneDomandaCommand.idDomandaCart }"/>
	    <input type="hidden" name="codiceAttivitaBdr" value="${presentazioneDomandaCommand.codiceAttivitaBdr }"/>
	    <input type="hidden" name="nomeAttivitaBdr" value="${presentazioneDomandaCommand.nomeAttivitaBdr }"/>
	    <input type="hidden" name="idAlberoProc" value="${presentazioneDomandaCommand.idAlberoProc }"/>
	    <input type="hidden" name="codicecomune" value="${presentazioneDomandaCommand.codicecomune }"/>
	    <input type="hidden" name="tipoAzione" value="${presentazioneDomandaCommand.tipoAzione }"/>
	    <input type="hidden" name="endoCount" value="${presentazioneDomandaCommand.endoCount}"/>
	    <input type="hidden" name="firmaAllegatiUtente" value="true"/>
		<%for(String cod : endoz){ %>
		<input type="hidden" name="endoAttivi" value="<%=cod %>" />
		<% }%>
		<%for(String cod : endoNoCartAttivi ){ %>
		<input type="hidden" name="endoNoCartAttivi" value="<%=cod %>" />
		<% }%>
		<c:choose>
	    	<c:when test="${not empty presentazioneDomandaCommand.interventiLocali}">		    		
	    		<c:forEach items="${presentazioneDomandaCommand.interventiLocali}" var="intLoc">
	    			<input type="hidden" name="interventiLocali" value="${intLoc}" />
	    		</c:forEach>		    		
	    	</c:when>
	    	<c:otherwise>
	    		<input type="hidden" name="interventiLocali" value="" />
	    	</c:otherwise>
	    </c:choose>
		
		<table id="distinta_oneri" class="distintaOneri">
			<thead>
				<tr>
	                <th class="tableHeader" colspan="2"><fmt:message key="cartfacct.label.oneri.causale" />
	                    <div class="descrizioneEndo">[<fmt:message key="cartfacct.label.oneri.endoprocediemento" />]</div>
	                </th>
	                <th class="tableHeader"><fmt:message key="cartfacct.label.oneri.pagamento" /></th>
	                <th class="tableHeader"><fmt:message key="cartfacct.label.oneri.tipopagamento" /></th>
	                <th class="tableHeader"><fmt:message key="cartfacct.label.oneri.datapagamento" /></th>
	                <th class="tableHeader"><fmt:message key="cartfacct.label.oneri.rifpagamento" /></th>
	                <th class="tableHeader importoColumn"><fmt:message key="cartfacct.label.oneri.importo" /></th>
				</tr>
			</thead>
			<tbody>
				<% 
				BigDecimal totOneri = new BigDecimal(0);
				totOneri = totOneri.setScale(2);
				Map<String,MessaggioErrore> erroriGlobali = errorMap.get(new Integer(-1));
				int errorCount = erroriGlobali.size();
				for(int i = 0; i < oneri.size(); i++){
				    FoDomandeOneri onere = oneri.get(i);
				    MessaggioErrore msg = null;
					if(onere.getImporto() != null){
					    totOneri = totOneri.add(onere.getImporto());
					}
				    Map<String,MessaggioErrore> erroriOnere = errorMap.get(new Integer(i));
				    errorCount += erroriOnere.size();
				%>
				<tr id="onere_<%=i%>">
						<% if(BooleanUtils.isTrue(onere.getFlagDaFront())){ %>
						<td class="deleteColumn">
							<input class="bottone-cart" type="button" onclick="eliminaOnere(<%= i %>);" id="delbutton_<%= i %>" value="<fmt:message key='label.elimina' />"/>
						</td>
						<td class="descColumn">
                        <% }else{ %>
						<td class="descColumn" colspan="2">
						<% 
                        }
						if(onere.getOggettoRicevuta() != null && onere.getOggettoRicevuta().getId().getCodice() != null){
						    Oggetti o = onere.getOggettoRicevuta();
						    StringBuilder sb = new StringBuilder(request.getContextPath());
						    String url = sb.append("/ajax/downloadOggetto.htm?idOggetto=").append(o.getId().getCodice()).append("&fileRename=").append(o.getNomefile()).toString();
						    sb = new StringBuilder(request.getContextPath());
						    String deleteUrl = sb.append("/cart/ajaxDeleteRicevutaOnere.htm?codiceOggetto=").append(o.getId().getCodice()).toString();
						%>
						<script type="text/javascript">
							ricevute[<%=i%>] = [{
								name: '<%= o.getNomefile()%>',
								size: <%= o.getDimensioneFile()%>,
								deleteType: 'POST',
								codiceOggetto: <%= o.getId().getCodice()%>,
								url: '<%= url %>',
								deleteUrl: '<%= deleteUrl %>'
							}];
						</script>
						<%}%>
						<input type="hidden" name="<%=i%>.id.codice" id="<%=i%>.id.codice" value="<%=ObjectUtils.toString(onere.getId().getCodice()) %>" />
						<input type="hidden" name="<%=i%>.id.idcomune" value="<%=onere.getId().getIdcomune() %>"/>
						<input type="hidden" name="<%=i%>.onere.id.codice" value="<%=ObjectUtils.toString(onere.getOnere().getId().getCodice()) %>" />
						<input type="hidden" name="<%=i%>.onere.id.idcomune" value="<%=onere.getOnere().getId().getIdcomune() %>"/>
						<input type="hidden" name="<%=i%>.flagDaFront" value="<%= BooleanUtils.isTrue(onere.getFlagDaFront()) %>"/>
						<% if(BooleanUtils.isTrue(onere.getFlagDaFront())){ %>
		                    <%-- se è un onere aggiunto dall'utente ripresentare i campi editabili per la descrizione del procedimento e della causale --%>
							<div class="descrizioneCausale">
								<input type="text" value="<%= StringUtils.defaultString(onere.getDescCausale()) %>" class="mandatory causaleField" title="<fmt:message key='cartfacct.label.inserirecausale' />" 
									name="<%= i %>.descCausale" value="" onChange="javascript:syncCampo(event);" placeholder="<fmt:message key='cartfacct.label.inserirecausale' />"
									style="width:94%; max-width: 490px;"/>
								<% 
								msg = erroriOnere.get("descCausale");
								if(msg != null){
								%>
								<span class="error_image" id="<%=i%>.descCausale.errmsg" title="<%= msg.getMessage()%>">
									<label >(!)</label>
						        </span>
						        <% } %>
							</div>
							<div class="descrizioneEndo">
								<select name="<%= i %>.descProcedimento" onChange="javascript:syncCampo(event);" class="mandatory" style="width: 96%; max-width: 494px;" >
									<option value=""></option>
									<%
									List<EndoFACCT> endos = (List<EndoFACCT>)request.getAttribute("endoprocedimenti");
									for(int ii = 0; ii < endos.size(); ii++) {
									    EndoFACCT endo = endos.get(ii);
									    String selected = "";
									    if(endo.getDescrizione().equals(onere.getDescProcedimento())){
											selected = "selected=\"selected\"";
								    	}
									%>
									<option value="<%= endo.getDescrizione()%>" <%= selected %>><%= endo.getDescrizione()%></option>
									<%}%>
								</select>
								<% 
								msg = erroriOnere.get("descProcedimento");
								if(msg != null){
								%>
								<span class="error_image" id="<%=i%>.descProcedimento.errmsg" title="<%= msg.getMessage()%>">
									<label >(!)</label>
						        </span>
						        <% } %>
							</div>
                        <% }else{ %>
							<input type="hidden" name="<%=i%>.descCausale" value="<%= StringUtils.defaultString(onere.getDescCausale()) %>"/>
							<input type="hidden" name="<%=i%>.descProcedimento" value="<%=StringUtils.defaultString(onere.getDescProcedimento()) %>"/>
	                        <span class="descrizioneCausale"><%=onere.getDescCausale() %> (euro <%=onere.getImportoFormattato() %>)</span>
	                        <%if(onere.getOnere() != null && StringUtils.isNotBlank(onere.getOnere().getNote())){%>
							<span class="help_image" id="<%=onere.getId().getCodice() %>_<%=onere.getId().getIdcomune() %>_onere_help" 
							title="<%= Utilities.stripHtmlTags(onere.getOnere().getNote())%>">
								<label >(?)</label>
		                    </span>
		                    <% } %>
	                        <div class="descrizioneEndo">[<%=onere.getDescProcedimento() %>]</div>
	                    <% } %>
					</td>
					<td>
						<select id="<%=i%>.statoPagamento" name="<%=i%>.statoPagamento" class="mandatory">
							<option value=""></option>
							<%
							List<String> statiPagamento = (List<String>)request.getAttribute("statiPagamento");
							for(int ii = 0; ii < statiPagamento.size(); ii++) {
							    String stato = statiPagamento.get(ii);
							    String selected = "";
							    if(stato.equals(onere.getStatoPagamento())){
									selected = "selected=\"selected\"";
							    }
							%>
								<option value="<%= stato%>" <%=selected %>><%= stato%></option>
							<%}%>
						</select>
						<% 
						msg = erroriOnere.get("statoPagamento");
						if(msg != null){
						%>
						<span class="error_image" id="<%=i%>.statoPagamento.errmsg" title="<%= msg.getMessage()%>">
							<label >(!)</label>
				        </span>
				        <% } %>
						<script type="text/javascript">
							$(function(){
								$("#<%=i%>\\.statoPagamento").change(function(event){
									attivaCampiPagamento(event.target);
									return riepilogo();
								});
								attivaCampiPagamento($("#<%=i%>\\.statoPagamento"));
							});
						</script>
					</td>
					<td>
						<select id="<%=i%>.tipoPagamento" name="<%=i%>.tipoPagamento" class="mandatory">
							<option value=""></option>
							<%
							List<Tipimodalitapagamento> tipiPagamento = (List<Tipimodalitapagamento>)request.getAttribute("tipiPagamento");
							for(int ii = 0; ii < tipiPagamento.size(); ii++) {
							    Tipimodalitapagamento pag = tipiPagamento.get(ii);
							    String selected = "";
							    if(pag.getMpDescrestesa().equals(onere.getTipoPagamento())){
									selected = "selected=\"selected\"";
							    }
							%>
								<option value="<%= pag.getMpDescrestesa()%>" <%=selected %>><%= pag.getMpDescrestesa()%></option>
							<%}%>
						</select>
						<% 
					    msg = erroriOnere.get("tipoPagamento");
						if(msg != null){
						%>
						<span class="error_image" id="<%=i%>.tipoPagamento.errmsg" title="<%= msg.getMessage()%>">
							<label >(!)</label>
				        </span>
				        <% } %>
					</td>
					<td>
						<input type="text" id="<%=i%>.dataPagamento" name="<%=i%>.dataPagamento" value="<%= Utilities.formatDate(onere.getDataPagamento(),false) %>" size="5" class="mandatory dateField"/>
						<% 
					    msg = erroriOnere.get("dataPagamento");
						if(msg != null){
						%>
						<span class="error_image" id="<%=i%>.dataPagamento.errmsg" title="<%= msg.getMessage()%>">
							<label >(!)</label>
				        </span>
				        <% } %>
					</td>
					<td>
						<input type="text" class="mandatory" id="<%=i%>.rifPagamento" name="<%=i%>.rifPagamento" value="<%=StringUtils.defaultString(onere.getRifPagamento()) %>" size="15"/>
						<% 
					    msg = erroriOnere.get("rifPagamento");
						if(msg != null){
						%>
						<span class="error_image" id="<%=i%>.rifPagamento.errmsg" title="<%= msg.getMessage()%>">
							<label >(!)</label>
				        </span>
				        <% } %>
					</td>
					<td class="importoColumn">
						<%
						String readonly = "readonly=\"readonly\"";
						String mandatory = "";
						if(onere.isImportoModificabile()){
						    readonly = "";
						    mandatory = "mandatory";
						}
						%>
						&euro; <input type="text" id="<%=i%>.importo" name="<%=i%>.importo" onchange="javascript:return riepilogo();" value="<%=onere.getImportoFormattato() %>" class="<%=mandatory%> numberField" <%=readonly%>/>
						<% 
					    msg = erroriOnere.get("importo");
						if(msg != null){
						%>
						<span class="error_image" id="<%=i%>.importo.errmsg" title="<%= msg.getMessage()%>">
							<label >(!)</label>
				        </span>
				        <% } %>
					</td>
				</tr>
				<%}%>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5">
						<input type="button" class="bottone-cart" onclick="aggiungiOnere();" value="<fmt:message key='cartfacct.button.oneri.aggiungionere' />" />
					</td>
					<td class="totale" ><fmt:message key="cartfacct.label.oneri.totaleoneri" />:</td>
					<td class="totale" >
						&euro; <span id="totale_oneri"><%= Utilities.formatImporto(totOneri, 2, 2, false) %></span>
					</td>
				</tr>
			</tfoot>
		</table>
		<div class="riepilogo" id="div_online">
			<div class="titolo titolo-riepilogo">
				<fmt:message key="cartfacct.label.oneri.totaleonline" />
			</div>
			<table class="tabella-riepilogo" id="riepilogo_online">
				<thead>
					<tr>
						<th class="tableHeader" colspan="2"><fmt:message key="cartfacct.label.oneri.causale" /></th>
						<th class="tableHeader importoColumn"><fmt:message key="cartfacct.label.oneri.importo" /></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<tr>
						<td>
							<input type="button" class="bottone-cart" id="paga_oneri" onclick="pagaOneri();" value="<fmt:message key='cartfacct.button.oneri.pagaonline' />" />
						</td>
						<td class="totale" ><fmt:message key="cartfacct.label.oneri.totaleonline" />:</td>
						<td class="totale" >
							&euro; <span id="totale_online">0,00</span>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<div class="riepilogo" id="div_ricevute">
			<div class="titolo titolo-riepilogo">
				<fmt:message key="cartfacct.label.oneri.ricevute" />
			</div>
			<table class="tabella-riepilogo" id="riepilogo_ricevute">
				<thead>
					<tr>
						<th class="tableHeader"><fmt:message key="cartfacct.label.oneri.causale" /></th>
						<th class="tableHeader">
							<fmt:message key="cartfacct.label.oneri.ricevuta" />
						<% 
						if(erroriGlobali != null){
							MessaggioErrore msg = erroriGlobali.get("oggettoRicevuta");
							if(msg != null){
						%>
						<span class="error_image" id="oggettoRicevuta.errmsg" title="<%= msg.getMessage()%>">
							<label >(!)</label>
				        </span>
				        <% }
						}
				        %>
						</th>
						<th class="tableHeader importoColumn"><fmt:message key="cartfacct.label.oneri.importo" /></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<tr>
						<td class="totale" colspan="2"><fmt:message key="cartfacct.label.oneri.totalepagato" />:</td>
						<td class="totale" >
							&euro; <span id="totale_pagato">0,00</span>
						</td>
					</tr>
					<tr id="info_ricevute">
						<td colspan="3" class="info"><fmt:message key="cartfacct.label.oneri.inforicevute" /></td>
					</tr>
				</tfoot>
			</table>
		</div>
		<div style="padding-top: 20px;">
			<input type="button" class="bottone-cart" onclick="salvaOneri();" value="<fmt:message key='button.avanti' />" />
	    	<input type="button" class="bottone-cart" value="<spring:message code="cartfacct.button.tornaamodulistica"></spring:message>" id="indietro" />		    	
	    	<input type="button" class="bottone-cart" onclick="document.location.href='<%= request.getSession().getAttribute(WebConstants.RETURNTO)%>'" value="<spring:message code="label.chiudi"></spring:message>"/>
			
		</div>
	</form>	
	</div>
	<div class="dialog" id="dialog" title="" style="display: none;">
		<p id="dialog_content">Messaggio:</p>
	</div>
	<% String indexPh = "${indice}"; %>
	<%-- <fmt:message key='label.elimina' /> --%>
	<script id="riga_distinta" type="text/x-jquery-tmpl">
				<tr id="onere_<%= indexPh %>">
					<td class="deleteColumn">
						<input class="bottone-cart" type="button" onclick="eliminaOnere(<%= indexPh %>);" id="delbutton_<%= indexPh %>" value="<fmt:message key='label.elimina' />"/>
					</td>
					<td class="descColumn">
						<input type="hidden" name="<%= indexPh %>.id.codice" value="" />
						<input type="hidden" name="<%= indexPh %>.id.idcomune" value="<%= ORMHelper.getIdcomune()%>"/>
						<span class="descrizioneCausale">
							<input type="text" class="mandatory causaleField" placeholder="<fmt:message key='cartfacct.label.inserirecausale' />" title="<fmt:message key='cartfacct.label.inserirecausale' />" 
								name="<%= indexPh %>.descCausale" value="" onChange="javascript:syncCampo(event);"/>
						</span>
						<div class="descrizioneEndo">
							<!-- <input type="text" class="endoField" placeholder="<fmt:message key='cartfacct.label.selezionareendo' />" title="<fmt:message key='cartfacct.label.inserirecausale' />" name="<%= indexPh %>.descProcedimento" value=""/> -->
							<select name="<%= indexPh %>.descProcedimento" style="max-width: 508px;" onChange="javascript:syncCampo(event);" class="mandatory">
								<option value=""></option>
								<%
								List<EndoFACCT> endos = (List<EndoFACCT>)request.getAttribute("endoprocedimenti");
								for(int ii = 0; ii < endos.size(); ii++) {
								    EndoFACCT endo = endos.get(ii);
								%>
								<option value="<%= endo.getDescrizione()%>" ><%= endo.getDescrizione()%></option>
								<%}%>
							</select>
						</div>
					</td>
					<td>
						<select name="<%= indexPh %>.statoPagamento" onchange="javascript:return riepilogo();" class="mandatory">
							<option value=""></option>
							<option value="Effettuato">Effettuato</option>
							<option value="Online">Online</option>
						</select>
					</td>
					<td>
						<select name="<%= indexPh %>.tipoPagamento" class="mandatory">
							<option value=""></option>
							<%
								List<Tipimodalitapagamento> tipiPagamento = (List<Tipimodalitapagamento>)request.getAttribute("tipiPagamento");
								for(int ii = 0; ii < tipiPagamento.size(); ii++) {
								    Tipimodalitapagamento pag = tipiPagamento.get(ii);
								%>
								<option value="<%= pag.getMpDescrestesa()%>"><%= pag.getMpDescrestesa()%></option>
							<%}%>
						</select>
					</td>
					<td>
						<input type="text" name="<%= indexPh %>.dataPagamento" id="<%= indexPh %>.dataPagamento" value="" size="5" class="mandatory dateField"/>
					</td>
					<td>
						<input type="text" name="<%= indexPh %>.rifPagamento" value="" size="15" class="mandatory"/>
					</td>
					<td class="importoColumn">
						&euro; <input type="text" name="<%= indexPh %>.importo" onchange="javascript:return riepilogo();" id="<%= indexPh %>.importo" value="" class="mandatory numberField"/>
					</td>
				</tr>
	</script>
	<script id="riga_online" type="text/x-jquery-tmpl">
				<tr id="online_<%= placeholder("index") %>">
					<td class="descColumn" colspan="2">
                        <span class="descrizioneCausale" id="<%= placeholder("index") %>.descCausale.label"><%=placeholder("descCausale") %></span> <span class="descrizioneCausale">(euro <%=placeholder("importo") %>)</span>
                        <div class="descrizioneEndo">[<span class="descrizioneEndo" id="<%= placeholder("index") %>.descProcedimento.label"><%=placeholder("descProcedimento") %></span>]</div>
					</td>
					<td class="importoColumn">
						&euro; <input type="text" readonly="readonly" name="<%= placeholder("index") %>.importo.online" id="<%= placeholder("index") %>.importo" value="<%= placeholder("importo") %>" class="numberField"/>
					</td>
				</tr>
	</script>
	<script id="riga_ricevuta" type="text/x-jquery-tmpl">
				<tr id="ricevuta_<%= placeholder("index") %>">
					<td class="descColumn">
                        <span class="descrizioneCausale" id="<%= placeholder("index") %>.descCausale.label"><%=placeholder("descCausale") %></span> <span class="descrizioneCausale">(euro <%=placeholder("importo") %>)</span>
                        <div class="descrizioneEndo">[<span class="descrizioneEndo" id="<%= placeholder("index") %>.descProcedimento.label"><%=placeholder("descProcedimento") %></span>]</div>
					</td>
					<td>
						<div id="fileupload-<%= placeholder("index") %>.oggettoRicevuta.id.codice" class="campo-file-cart">
						<label for="fileupload-<%= placeholder("index") %>" class="mandatory-star mandatory-file-star" style="display:none;">*</label>
        				<div class="row fileupload-buttonbar">
            				<div class="span7">
                				<!-- The fileinput-button span is used to style the file input field as button -->
                				<span class="btn btn-success fileinput-button">
                    				<i class="icon-plus icon-white"></i>
                    				<span>Carica files...</span>
                    				<input type="file" name="files[]" multiple>
                				</span>
            				</div>
				            <div>
	 							<span class="icon-firmadigitale" title="I files caricati devono essere firmati digitalmente">
									<label >(!)</label>
		        				</span>
		    				</div>
            				<!-- The global progress information -->
            				<div class="span5 fileupload-progress fade">
				                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                				    <div class="bar" style="width:0%;"></div>
                				</div>
				                <!-- The extended global progress information -->
                				<div class="progress-extended">&nbsp;</div>
            				</div>
        				</div>
        				<br>
        				<!-- The table listing the files available for upload/download -->
        				<table role="presentation" class="table table-striped file-list">
        					<tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></tbody>
        				</table>
        				<!-- Display error and warning icons -->
						<div class="campo-cart-icons">
							<span class="error_image" style="display:none;" title="">
								<label >(!)</label>
					        </span>
							<span class="warning_image" style="display:none;" title="">
								<label >(!)</label>
					        </span>
					    </div>
        				<input type="hidden" name="<%= placeholder("index") %>.oggettoRicevuta.id.codice" id="empty-<%= placeholder("index") %>.oggettoRicevuta" value="" />
    					</div>
					</td>
					<td class="importoColumn">
						&euro; <input type="text" readonly="readonly" name="<%= placeholder("index") %>.importo.ricevuta" id="<%= placeholder("index") %>.importo" value="<%= placeholder("importo") %>" class="numberField"/>
					</td>
				</tr>
	</script>
	<jsp:include page="../includes/fileupload_templates.jsp" />
	<% if(errorCount > 0){ %>
		<script type="text/javascript">
			$(function(){
				var errMessage = "<fmt:message key="cartfacct.oneri.error" />";
				showDialog(errMessage,"Attenzione",400,200);
			});
		</script>
	<%} %>
</body>
</html>
