<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.FACCTConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.cart.AllegatoDaFirmare"%>
<%@page import="java.util.List"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ajaxupload.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/css/facct.css" rel="stylesheet"></link>
<title><spring:message code="cartfacct.title.firmadigitale.mda"></spring:message></title>
</head>
<%
Set<AllegatoDaFirmare> modelli =(HashSet<AllegatoDaFirmare>) request.getAttribute("moduli");
AllegatoDaFirmare modelloRiepilogo = (AllegatoDaFirmare)request.getAttribute("modello_riepilogo");
PresentazioneDomandaCartCommand command = (PresentazioneDomandaCartCommand) request.getAttribute("presentazioneDomandaCommand");
Set<String> endos = new HashSet<String>();
Set<String> endoNoCartAttivi = new HashSet<String>();
if(null != command){
    endos = command.getEndoAttivi();
    endoNoCartAttivi = command.getEndoNoCartAttivi();
}
List<String> errors = (List<String>)request.getAttribute("errors");
%>
<body>

	<jsp:include page="../includes/messaggio_aggiornamento.jsp" >
			<jsp:param name="settimeout" value="false"></jsp:param>
	</jsp:include>

	<script type="text/javascript">
		var objIds = [];
		var uploadCtrls = [];
		var statusIcons = [];
		var pendingUploads = 0;
	</script>
	<div class="dialog" id="dialog" title="" style="display: none;">
			<p id="dialog_content">Messaggio:</p>
	</div>
	<div>
	    <form action="<%=request.getContextPath() %>/cart/presentaDomanda.htm?disableMR=1" id="target" name="presentazioneDomandaCommand" method="post" enctype="multipart/form-data">
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
			
			<%for(String cod : endos){ %>
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
		    </c:choose>
			
		    <div id="elencoallegati" style="padding: 5px; width: 95%;">
		    	<p>
		    		<span style="font-size: x-large; font-weight: bold;"><spring:message code="cartfacct.title.firmadigitale.mda"></spring:message></span>
		    	</p>
		    	<br />
		    	<p>
		    		<span style="font-size: small;"><spring:message code="cartfacct.label.firmadigitale.steps"></spring:message></span>
		    	</p>
		    	<p>
		    		<ol class="elenconumerato">
		    			<li class="elenconumerato"><span style="font-size: small;"><spring:message code="cartfacct.label.firmadigitale.step1"></spring:message></span></li>
		    			<li class="elenconumerato">
		    				<span style="font-size: small;"><spring:message code="cartfacct.label.firmadigitale.step2"></spring:message></span>
		    				<ul class="elencopuntato">		    					
		    					<li class="elencopuntato">
		    						<span style="left: 0px;">
			    						<spring:message code="cartfacct.label.firmadigitale.firmaesterna"></spring:message><br />
			    						<spring:message code="cartfacct.label.firmadigitale.firmaesterna.help"></spring:message>
		    						</span>
		    					</li>
								<li class="elencopuntato">
		    						<span style="left: 0px;">
			    						<spring:message code="cartfacct.label.firmadigitale.firmaintegrata"></spring:message><br />
			    						<spring:message code="cartfacct.label.firmadigitale.firmaintegrata.help"></spring:message>
		    						</span>
		    					</li>		    					
		    				</ul>
		    			</li>
		    			<li class="elenconumerato"><span style="font-size: small;"><spring:message code="cartfacct.label.firmadigitale.step3"></spring:message></span></li>
		    		</ol>
		    	</p>
		    	<br />
		    	<div style="width: 100%; position: relative; left: 50px;">
		    		<div style="width: 40%; display: inline-block;">
		    		<input type="radio" name="tipofirma" id="rdb_firmaesterna"  checked="checked" value="esterna"/>
		    		<label for="rdb_firmaesterna" id="lbl_firmaesterna"><spring:message code="cartfacct.label.firmadigitale.firmaesterna"></spring:message></label>
		    		</div>
		    		<div style="width: 40%; display: inline-block;">
		    		<input type="radio" name="tipofirma" id="rdb_firmaintegrata" value="integrata"/>
		    		<label for="rdb_firmaintegrata" id="lbl_firmaintegrata"><spring:message code="cartfacct.label.firmadigitale.firmaintegrata"></spring:message></label>
		    		</div>		    		
		    	</div>
		    	<div>
			    	<fieldset class="fieldset-firmadigitale">
			    		<legend id="legend_allegati" ><spring:message code="cartfacct.label.firmadigitale.firmaintegrata"></spring:message></legend>
			    		
			    		
<div style="color: maroon;padding: 10px;" id="helpFirmaIntegrata">

ATTENZIONE! E' necessario cliccare con il tasto destro del mouse e selezionare:

<ul style="padding-left: 20px;">
	<li>- con Chrome: "Salva link con nome..."</li>
	<li>- con Firefox:  "Salva destinazione con nome..."</li>
	<li>- con Internet Explorer: "Salva oggetto con nome..."</li>
	<li>- con Safari: "Scarica documento collegato"</li>
</ul>

e NON SALVARE il file con programmi esterni (aprendolo ad esempio con acrobat reader e usando la funzionalita' "salva con nome")
</div>			    		
			    		
					    <div style="padding-left:15px;" id="firma_digitale_wrapper">
					    <jsp:include page="../includes/firmadigitale.jsp" >
					    	<jsp:param value="firma_digitale_wrapper" name="appletElementId"/>					    
					    </jsp:include>
					    </div>
				    	<ul>
				    	<%if(modelloRiepilogo != null){ %>
				    		<li style="padding:10px;">
					    		<div>
					    			<div class="sign-attachment" style="width: 80%;">
					    				<div class="sign-attachment">
								    		<span>${modello_riepilogo.descrizione }</span><br />
							    			<a id="allegato_${modello_riepilogo.riferimento}_download_link" style="float: left; clear: left;" href="<%=request.getContextPath() %>/ajax/downloadOggetto.htm?idOggetto=${modello_riepilogo.riferimento}&fileRename=${modello_riepilogo.fileName }">${modello_riepilogo.fileName }</a>
						    			</div>
						    			<br />
							    		<div class="sign-attachment, signed-upload-ctrl">
							    			<input type="text" name="signedupload-${modello_riepilogo.riferimento}-name" id="signedupload-${modello_riepilogo.riferimento}-name" readonly="readonly" size="50"/>
							    			<input type="button" name="signedupload-${modello_riepilogo.riferimento}" id="signedupload-${modello_riepilogo.riferimento}" value="Carica il file firmato" class="bottone-cart-small"/>
							    		</div>
						    		</div>
						    		<div class="sign-attachment" style="width: 15%;">
							    		<img id="esito_firma_${modello_riepilogo.riferimento}_ico" src="<%=request.getContextPath() %>/images/sign_qm.jpg" alt="" />
							    		<span id="esito_firma_${modello_riepilogo.riferimento}_msg"></span>
							    		<input type="hidden" name="allegato_${modello_riepilogo.riferimento}_esito" value=""/>
						    		</div>
							    </div>
				    		</li>
				    		<script type="text/javascript">
				    			objIds[objIds.length] = <%=modelloRiepilogo.getRiferimento() %>;
				    		</script>
				    	<%
				    	}
				    	AllegatoDaFirmare allegato = null;
				    	if(modelli != null){
				    	    Iterator<AllegatoDaFirmare> allIter = modelli.iterator();
					    	while(allIter.hasNext()){
					    	    allegato = allIter.next();
					    	%>
					    		<li style="padding:10px;">
						    		<div>
						    			<div class="sign-attachment" style="width: 80%;">
						    				<div class="sign-attachment">
									    		<span><%=allegato.getDescrizione() %></span><br />
								    			<a id="allegato_<%=allegato.getRiferimento() %>_download_link" style="float: left; clear: left;" href="<%=request.getContextPath() %>/ajax/downloadOggetto.htm?idOggetto=<%=allegato.getRiferimento() %>&fileRename=<%=allegato.getFileName() %>"><%=allegato.getFileName() %></a>
							    			</div>
							    			<br />
								    		<div class="sign-attachment, signed-upload-ctrl">
								    			<input type="text" name="signedupload-<%= allegato.getRiferimento()%>-name" id="signedupload-<%= allegato.getRiferimento()%>-name" readonly="readonly" size="50"/>
								    			<input type="button" name="signedupload-<%= allegato.getRiferimento()%>" id="signedupload-<%= allegato.getRiferimento()%>" value="Carica il file firmato" class="bottone-cart-small"/>
								    		</div>
							    		</div>
							    		<div class="sign-attachment" style="width: 15%;">
								    		<img id="esito_firma_<%=allegato.getRiferimento() %>_ico" src="<%=request.getContextPath() %>/images/sign_qm.jpg" alt="" />
								    		<span id="esito_firma_<%=allegato.getRiferimento() %>_msg"></span>
								    		<input type="hidden" name="allegato_<%=allegato.getRiferimento() %>_esito" value=""/>
							    		</div>
								    </div>
					    		</li>
					    		<script type="text/javascript">
					    			objIds[objIds.length] = <%=allegato.getRiferimento() %>;
					    			uploadCtrls[uploadCtrls.length] = undefined;
					    		</script>
					    	<%
					    	}
				    	}
				    	%>
				    	</ul>
				    </fieldset>
				</div>
		    </div>
		    <br />
		    <div align="left" style="display: block; padding-left:15px;">
		    	<input type="button" value="<spring:message code="cartfacct.button.presentadomanda"></spring:message>"  id="procedi_id" class="bottone-cart"/>
		    	<input type="button" value="<spring:message code="cartfacct.button.tornaamodulistica"></spring:message>" id="indietro" class="bottone-cart"/>		    	
		    	<input type="button" class="bottone-cart" onclick="document.location.href='<%= request.getSession().getAttribute(WebConstants.RETURNTO)%>'" value="<spring:message code="label.chiudi"></spring:message>"/>
		    </div>
	    </form>
	    <script type="text/javascript">
		    $(document).ready(inizializzaForm);
		    
		    var errMsg = "";
		    <%
		    	if(errors != null && errors.size() > 0){
		    %>
		    errMsg += "<ul>";
		    <%
		    	    for(String error : errors){
		    %>
		    errMsg += "<li><%=error %></li>";
		    <%
		    	    }
		    %>
		    errMsg += "</ul>";
		    <%
		    	}
	    	%>

		    function bloccaUIAndApplet(){
		    	hideDivApplet();
		    	$.blockUI();		    	
		    }
		    
		    function sbloccaUIAndApplet(){
		    	showDivApplet();
		    	$.unblockUI();		    	
		    }

		    var appletWidth = 0;
		    var appletHeight = 0;
		    var appletVisible = true;
		    hideDivApplet = function(force){
		    	if(appletWidth > 1){
			    	appletWidth=jQuery("#firma-digitale-applet>applet").width();
			    	appletHeight=jQuery("#firma-digitale-applet>applet").height();
			    	jQuery("#firma-digitale-applet>applet").height(1).width(1);
		    	}
		    	jQuery("#firma-digitale-applet").hide();
			};

			showDivApplet = function(force){
		    	if(appletVisible){
					jQuery("#firma-digitale-applet>applet").height(appletHeight).width(appletWidth);
					jQuery("#firma-digitale-applet").show();
		    	}
			};

			function inizializzaForm(){
		    
		    	$(document).ajaxStart(bloccaUIAndApplet).ajaxStop(sbloccaUIAndApplet);
		    	
			    $("#procedi_id").click(presentaDomandaClickHandler);
			    
		    	$("#indietro").click(function(){
		    		hideDivApplet();
					$.blockUI();
					$('#target').attr('action','<%=request.getContextPath() %>/cart/caricaModulistica.htm');
					$('#target').submit();
				});
	    
		    	for(var i = 0; i < objIds.length; i++){
		    		extraData = {
		    			codiceoggetto: objIds[i]
		    		};
		    		uploadCtrls[i] = new AjaxUpload('#signedupload-' + objIds[i], {
						action: "<%=request.getContextPath() %>/cart/ajaxUploadMdaFirmato.htm?disableMR=1",
						name: 'signedupload-' + objIds[i],
						responseType: 'json',
						autoSubmit: true,
						submitIfEmpty: false,
						data: extraData,
						onSubmit: displayFile,
						onComplete: updateClientStatus 
					});		    		
		    		uploadCtrls[i].objId = objIds[i];
		    	}
		    	if(errMsg){
		    		showDialog(errMsg);
		    	}
		    	if(appletWidth == 0){
		    		appletWidth=jQuery("#firma-digitale-applet>applet").width();
		    	}
		    	if(appletHeight == 0){
		    		appletHeight=jQuery("#firma-digitale-applet>applet").height();
		    	}
		    	impostaModalitaFirma();
		    	$('input[name="tipofirma"]').click(impostaModalitaFirma);
		    }
		    
		    function presentaDomandaClickHandler(evt){
		    	if(pendingUploads > 0){
		    		var msg = '<spring:message code="cartfacct.label.firmadigitale.pendinguploads"></spring:message>';
		    		showDialog(msg, "Attenzione");
		    	}
		    	else{
		    		hideDivApplet();
					$.blockUI();
					$('#target').submit();
		    	}
		    }
	    
			function impostaModalitaFirma(){
				var tipoFirma = $('input[name="tipofirma"]:checked').val();
				appletVisible = tipoFirma == 'integrata';
				//l'applet è reso visibile solo per la modalità firma integrata
				var fieldsToHide = $('.signed-upload-ctrl');
				if(appletVisible){
					if(!globalAppletInizializzata){
						runFirmaApplet();
					}
					showDivApplet();
					fieldsToHide.hide();
					$('#legend_allegati').empty();
					$('#legend_allegati').append('<spring:message code="cartfacct.label.firmadigitale.firmaintegrata"></spring:message>');
					$('#helpFirmaIntegrata').hide();
					
				}
				else{
					hideDivApplet();
					fieldsToHide.show();
					$('#legend_allegati').empty();
					$('#legend_allegati').append('<spring:message code="cartfacct.label.firmadigitale.firmaesterna.ext"></spring:message>');
					$('#helpFirmaIntegrata').show();
				}
			}
			
		    function displayFile(file, extension){
		    	pendingUploads += 1;
		    	var uploaderCtrl = this;
		    	statusIcons[uploaderCtrl.objId] = $("#esito_firma_" + uploaderCtrl.objId + "_ico").attr('src');
		    	$("#esito_firma_" + uploaderCtrl.objId + "_ico").attr('src','<%=request.getContextPath() %>/images/spinner.gif');
		    	var txtField = $('[name="signedupload-' + uploaderCtrl.objId + '-name"]');
		    	txtField.val(file);
		    	//hideDivApplet();
		    	//$.blockUI();
		    }
		    
			function updateClientStatus(file, response){
				pendingUploads -= 1;
				showDivApplet();
				$.unblockUI();
				if(response){
					if(!response.error){
						setEsitoFirma(response.codiceoggetto, response.esito, response.messaggio, response.filename);
					}
					else{
						showDialog(response.error, "Errore");
						var oldIcon = statusIcons[response.codiceoggetto];
						if(oldIcon){
							$("#esito_firma_" + response.codiceoggetto + "_ico").attr('src',oldIcon);
						}
						delete statusIcons[response.codiceoggetto];
					}
				}
				else{
					showDialog("Upload del file " + file + "fallito.", "Errore");
				}
			}

			function showDialog(message,title, width, height){
				
				hideDivApplet();
				if(!title)title = "";
				var dialogDiv = $('#dialog');
				dialogDiv.attr('title',title);
				var dialogP = $('#dialog_content');
				//dialogP.empty();
				dialogP.html(message);
				var option = {
						minWidth: 300,
						minHeight: 250,
						beforeClose: function( event, ui ) {
							showDivApplet(); 						  
						}
				};
				if(width){
					option.width = width;
				}
				if(height){
					option.height = height;
				}
				dialogDiv.dialog(option);
			};
		    
			function getListaCodiciOggetto(){
				var codiceoggetto;
				var retstr = null;
				if(objIds && objIds.length > 0){
					for(i = 0; i < objIds.length; i++){
						codiceoggetto = objIds[i];
						$("#esito_firma_" + codiceoggetto + "_msg").text("");
						$("#esito_firma_" + codiceoggetto + "_ico").attr('src','<%=request.getContextPath() %>/images/sign_qm.jpg');
						retstr = objIds.join(",");
					}
				}
				return retstr;
			}
		    
			function setEsitoFirma(codiceoggetto, codice, messaggio, newFileName){
				
				var icon = "sign_ok.jpg";
				var isOk = false;
				if(!codice || codice.toUpperCase() == "KO"){
					icon = "sign_ko.jpg";
				}
				else{
					//messaggio = "";
					isOk = true;
				}
				var removeIndex = $.inArray(codiceoggetto, objIds);
				if(isOk){
					//elimino il file firmato dall'array che memorizza i codici degli allegati da firmare
					if(removeIndex > -1){
						objIds.splice(removeIndex, 1);
					}
					else{
						//il codice oggetto può non essere fra gli allegati da firmare perchè l'utente ha già uploadato un file con firma digitale valida
					}
				}else{
					//se non è già presente si aggiunge l'id dell'oggetto con firma non valida all'array che memorizza i codici dei files da firmare
					if(removeIndex == -1){
						objIds.push(codiceoggetto);
					}
					else{
						//il file è già presente nell'array dei codici dei file da firmare perciò non lo aggiungo un'altra volta
					}
				}
				$("#esito_firma_" + codiceoggetto + "_msg").html(messaggio);
				$("#esito_firma_" + codiceoggetto + "_ico").attr('src','<%=request.getContextPath() %>/images/' + icon);
				if(isOk){
					$("[name=allegato_" + codiceoggetto + "_esito]").val(codice.toUpperCase());
					var oldHref = $("#allegato_" + codiceoggetto + "_download_link").attr('href');
					var oldText = $("#allegato_" + codiceoggetto + "_download_link").text();
					var newHref = oldHref + ".p7m";
					var newText = oldText + ".p7m";
					if(newFileName){
						var searchFor = "fileRename=";
						var replaceIndex = oldHref.lastIndexOf(searchFor);
						newHref = oldHref.substring(0,replaceIndex + searchFor.length) + newFileName;
						newText = newFileName;
					}
					$("#allegato_" + codiceoggetto + "_download_link").attr('href', newHref);
					$("#allegato_" + codiceoggetto + "_download_link").text(newText);
					//$("#signedupload-" + codiceoggetto + "-name").val(newText);
				}
			}
	    </script>
	
</body>
</html>