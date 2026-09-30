<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html>
	<head>
		<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
		<title><fmt:message key="label.movimenti_allegati" /></title>
		<style>
			.disabilitata-zip-logico > td{
	
				color: red;	
				text-decoration: line-through;
				background-image: linear-gradient(45deg, #f0f0f0 5.56%, #ffffff 5.56%, #ffffff 50%, #f0f0f0 50%, #f0f0f0 55.56%, #ffffff 55.56%, #ffffff 100%);
				background-size: 12.73px 12.73px; 
				min-width: 10px; 
				border: 2px solid maroon; 
				cursor: help;
			}
			.disabled-link {
			  pointer-events: none;
			  cursor: help;
			}
				
		
		</style>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.movimenti_allegati" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../movimentiallegati/list" />	    	
		</jsp:include>				
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${movimento.istanza.id.codice}</c:param>
		</c:import>
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.movimento" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					${movimento.movimento} - [${movimento.tipomovimento.id.tipomovimento}]							
				</div>
			</div>
		</div>
		<div class="parametriDiv">
			<div class="etichetta">
				<div><label for="CONF_UTENTE_VISUALIZZA_FILE_XML" title="${titleNascondiFileXML}" id="nascondiXMLFileTitleLabel">Nascondi file xml</label>:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					<div id="functions">
						<%
							String showXMLFileChecked = "";
							// gestisce la visualizzazione dei file xml nei documenti dell'istanza e degli allegati dei movimenti
							if(((String)request.getAttribute(WebConstants.CONF_UTENTE_VISUALIZZA_FILE_XML)).equals("1")) {
							    showXMLFileChecked = "checked='checked'";
							}
							pageContext.setAttribute("showXMLFileChecked", showXMLFileChecked);
						%>
						<c:set var="titleNascondiFileXML"><fmt:message key="label.documentiistanza_visualizza_file_xml_help"/></c:set>
						<c:set var="_CONF_UTENTE_VISUALIZZA_FILE_XML"><%=WebConstants.CONF_UTENTE_VISUALIZZA_FILE_XML%></c:set>
						<input title="${titleNascondiFileXML}" ${showXMLFileChecked} type="checkbox" id="CONF_UTENTE_VISUALIZZA_FILE_XML" onclick="nascondiXMLFile(this, '${_CONF_UTENTE_VISUALIZZA_FILE_XML}');"/>
					</div>							
				</div>
			</div>
		</div>
		
		<br class="clear" />	
		<jsp:include page="../includes/displayGlobalMessages.jsp" >
		       <jsp:param name="commandName" value="movimentiallegati" />
		</jsp:include>
		<script type="text/javascript">
		
			function visualizzaDocumento(obj, mostra){
			
				console.log(obj +"-->"+mostra);
				if(mostra){
					jQuery( obj ).parent().parent().show();
				}else{
					jQuery( obj).parent().parent().hide();
				}
				
			}
			

			
			function hideXMLEst() {
				var checked = jQuery("#CONF_UTENTE_VISUALIZZA_FILE_XML").prop('checked');
				console.log("(hideXMLEst) senza parametro  -->"+checked);
				jQuery(".nomeFile_cls").each(function () {
					var filename = jQuery(this).text(), labelTitle = "Nascondi file xml";
					if (filename.toLowerCase().endsWith(".xml") && checked) {
						labelTitle = "File xml nascosti";
						visualizzaDocumento(jQuery(this), false);
					} else {
						visualizzaDocumento(jQuery(this), true);
					}
					jQuery("#nascondiXMLFileTitleLabel").text(labelTitle);
				});
			}
			
			function nascondiXMLFile(obj, nomeParametro){
				var valore = obj.checked==true?'1':'0';
				hideXMLEst();
				saveUserPreference(nomeParametro, valore)
			}
			
		</script>
	
		<c:set var="xmlnascosti_var" value="Nascondi file xml" />	

		<div id="subcontent">							
			<form name="movimentiallegatiForm" action="list.htm">
				<jmesa:springTableFacade
					id="movimentiallegati_id" 
					items="${movimentiallegatiList}" 
					var="movimentiallegati_var"
					stateAttr="restore" >				
					<jmesa:htmlTable>
						<jmesa:htmlRow>							
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%" >
                                  <a href="javascript:dettaglio(${movimentiallegati_var.id.codice});">${movimentiallegati_var.id.codice}</a>
                            </jmesa:htmlColumn>						
							<jmesa:htmlColumn property="descrizione" titleKey="label.descrizione"/>
							<jmesa:htmlColumn property="oggetto.nomefile" titleKey="label.nome" >
								<p class="nomeFile_cls">${movimentiallegati_var.oggetto.nomefile}</p>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="note" titleKey="label.note"/>
							<jmesa:htmlColumn width="10%" property="dataregistrazione" titleKey="label.data_registrazione"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor"/>
							<jmesa:htmlColumn property="oggetto" titleKey="label.oggetto" sortable="false" filterable="false" width="7%" >
								<c:if test="${movimentiallegati_var.oggetto!=null}">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="mall${movimentiallegati_var.id.codice}" />
			       						<jsp:param name="fileId" value="${movimentiallegati_var.oggetto.id.codice}" />
			   						</jsp:include>
		   						</c:if>	
		   						<c:if test="${movimentiallegati_var.oggetto==null && movimentiallegati_var.stcIdallegato!=null && movimentiallegati_var.stcIddocumento!=null}">
									<jsp:include page="../includes/visualizzaOggettoSTC.jsp" >
			       						<jsp:param name="codicemovimento" value="${movimentiallegati_var.movimento.id.codice}" />
			       						<jsp:param name="codiceistanza" value="${movimentiallegati_var.movimento.istanza.id.codice}" />
			   							<jsp:param name="stcIddocumento" value="${movimentiallegati_var.stcIddocumento}" />
		   								<jsp:param name="stcIdallegato" value="${movimentiallegati_var.stcIdallegato}" />
		   								<jsp:param name="codiceRiferimento" value="${movimentiallegati_var.id.codice}" />
										<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_MOVIMENTO%>" />	
										<jsp:param name="indice" value="mov_all${movimentiallegati_var.id.codice}"/>	   							
		   						</jsp:include>						
							    </c:if>	
								<jsp:include page="../ajax/downloadEml.jsp" >
							   		<jsp:param name="codiceallegato" value="${movimentiallegati_var.id.codice}" />
							   		<jsp:param name="idElemento" value="docist${movimentiallegati_var.id.codice}" />
		       						<jsp:param name="fileId" value="${movimentiallegati_var.id.codice}" />
									<jsp:param name="mostralabel" value="true" />
							   	</jsp:include>
							</jmesa:htmlColumn>	
							<jmesa:htmlColumn width="5%"  property="flagPubblica" titleKey="label.pubblica" sortable="false" filterable="false">
								<input id="flagPubblicaId${movimentiallegati_var.id.codice}" type="checkbox" onclick="changeCheckboxValue('flagPubblicaId${movimentiallegati_var.id.codice }','${pageContext.request.contextPath}/movimentiallegati/ajaxChangeFlagPubblica.htm?codice=${movimentiallegati_var.id.codice}&checkPermessi=true')" ${movimentiallegati_var.flagPubblica?'checked':''} />
							</jmesa:htmlColumn>
							<jmesa:htmlColumn width="5%"  property="controllook" titleKey="label.valido" sortable="false" filterable="false">								
								<select id="select_valido_doc_mov${movimentiallegati_var.id.codice}" name="controllook" onchange="changeValueValidoMovAll('select_valido_doc_mov${movimentiallegati_var.id.codice}','${movimentiallegati_var.id.codice}');">
									<option  value="null" ${movimentiallegati_var.controllook==null?'selected':''}>Da verificare</option>								
									<option  value="1" ${movimentiallegati_var.controllook==1?'selected':''}>Valido</option>
									<option  value="0" ${movimentiallegati_var.controllook==0?'selected':''}>Non valido</option>
								</select>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn width="5%" property="_mettiallafirma" titleKey="label.seleziona" headerEditor="org.jmesa.custom.SelezionaAllegatiMovHeaderEditor" sortable="false" filterable="false">
																												
							
							
								<input id="sel_id${movimentiallegati_var.id.codice}" class="documenti_da_firmare_cls" onclick="addToDocDaFirmare();" value="${movimentiallegati_var.id.codice}" type="checkbox" />
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="110px;">
								<a class="vbg-btn btn-dettaglio" href="javascript:dettaglio(${movimentiallegati_var.id.codice});" title="<fmt:message key="label.edit.record" /> ${movimentiallegati_var.descrizione}">
								</a>
								
									<c:if test="${not empty movimentiallegati_var.oggetto}">
									<c:choose>
										<c:when test="${inite:endsWith(movimentiallegati_var.oggetto.nomefile, '.doc') 
													or inite:endsWith(movimentiallegati_var.oggetto.nomefile, '.rtf') 
													or inite:endsWith(movimentiallegati_var.oggetto.nomefile, '.txt')
													or inite:endsWith(movimentiallegati_var.oggetto.nomefile, '.htm')
													or inite:endsWith(movimentiallegati_var.oggetto.nomefile, '.html')
													or inite:endsWith(movimentiallegati_var.oggetto.nomefile, '.odt')}">											
											<a class="vbg-btn btn-pdf" href="javascript:trasformaInPDF(${movimentiallegati_var.id.codice});" title="<fmt:message key="label.trasforma_il_documento_in_pdf" />">
											</a>
										</c:when>
									
										<c:when test="${inite:endsWith(movimentiallegati_var.oggetto.nomefile, '.pdf')}">	
											<c:if test="${isAttivaQrCode eq true}">							
												<a class="vbg-btn btn-qrcode" href="javascript:applicaQRCODE(${movimentiallegati_var.id.codice});" title="<fmt:message key="label.applica_qrcode" />">
												</a>
											</c:if>
											<c:if test="${isAttivoLayerQRcode eq true}">							
												<a class="vbg-btn btn-qrcode" href="javascript:applicaQrcodePdf(${movimentiallegati_var.id.codice},${movimentiallegati_var.oggetto.id.codice});" title="<fmt:message key="label.applica_qrcode" />">
												</a>
											</c:if>
										</c:when>
										 
										<c:otherwise>
											<a class="vbg-btn btn-pdf vbg-btn-disabled" href="javascript:void(0);" title="<fmt:message key="label.trasforma_il_documento_in_pdf_disabilitata" />"> </a>
										</c:otherwise>
									</c:choose>	
									</c:if>								
							
								<c:if test="${inite:endsWith(movimentiallegati_var.oggetto.nomefile, '.pdf') && isAttivaLayerProt}">
 									<%-- 
 									<a style="${param.styleHref}" class="vbg-btn btn-applicalayer"  title="<fmt:message key="label.applica_layer" />" href="javascript:void 0" onclick="applicaLayerProtocolloInPDF(${movimentiallegati_var.movimento.id.codice},${movimentiallegati_var.oggetto.id.codice})">
									</a>
									--%>
									
									 <a href="javascript:void(0);" class="applica_layer_id vbg-btn btn-applicalayer" data-codice-movimento="${movimentiallegati_var.movimento.id.codice}"
									 data-codice-oggetto="${movimentiallegati_var.oggetto.id.codice}" title="Applica informazioni protocollo come layer"></a>
									
									
   								</c:if>
								<c:if test="${movimentiallegati_var.oggetto != null}">
									<jsp:include page="../includes/mettiallafirma.jsp" >
			       						<jsp:param name="idElemento" value="mall${movimentiallegati_var.id.codice }" />
			       						<jsp:param name="fileId" value="${movimentiallegati_var.oggetto.id.codice}" />
			       						<jsp:param name="codiceMovAllegato" value="${movimentiallegati_var.id.codice}" />
			   						</jsp:include>
		   						</c:if>	
					
					            <a href="javascript:void(0);" class="delete_id vbg-btn btn-elimina" data-codice-allegato-movimento="${movimentiallegati_var.id.codice}"></a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" name="codiceMovimento" id="codiceMovimento_id" value="${movimento.id.codice}" />
				<input type="hidden" name="codiceMovimentoAllegato" id="codiceMovimentoAllegato_id" value="" />
			</form>
			
			
			<form name="DOC_DA_METTERE_ALLA_FIRMA_FRM" method="post" action="${pageContext.request.contextPath}/documentidafirmare/ajaxViewOggettoMultiploList.htm">					
					<input type="hidden" name="lista_doc_da_firmare" id="lista_doc_da_firmare_id"/>
			</form>	
			
			
			<script type="text/javascript">
				var _jmesaUrl='list.htm?codiceMovimento=${movimento.id.codice}&';
				var _captionTab='<fmt:message key="label.movimenti_allegati" />';
			</script>			
			
		<script type="text/javascript">
			function dettaglio(codice){
				var urlTo="../movimentiallegati/view.htm?codice="+codice;
				historySet('${_urlback}', urlTo, '');
			}
			
			function trasformaInPDF(movimentiAllegatiId){

				doHref('insertTrasformaInPdf.htm?codice='+movimentiAllegatiId,'<fmt:message key="javascript.confirm.conferma_trasforma_il_documento_in_pdf" />');
			}
			
			
			
		</script>
		
	  <div id="dialog-6">
         	<fmt:message key="label.messaggio_cancellazione_documento_per_operatore">
					<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
			</fmt:message>
         <div>
            <label for="terms"><fmt:message key="label.accettazione_condizioni_eliminazione"/></label>
            <input type="checkbox" id="terms">
         </div>
      </div>
      
      
      <div style="display: none;" id="dialog-7">
         	<fmt:message key="label.messaggio_applica_layer_protocollo_documento">
					<fmt:param>${movimento.numeroprotocollo}</fmt:param>
					<fmt:param><fmt:formatDate pattern= "<%=WebConstants.DATE_FORMAT_PATTERN %>" value ="${movimento.dataprotocollo}" /></p>  </fmt:param>
			</fmt:message>
         <div>
            <label for="terms"><fmt:message key="label.accettazione"/></label>
            <input type="checkbox" id="terms">
         </div>
      </div>
		
							

   
								 
	<script type="text/javascript">
	
	    function selezionaDeselezionaTutti()
	    {
	    	var check = jQuery("#id_check_seleziona_tot").is(':checked');
	    	jQuery(".documenti_da_firmare_cls").each(function()
	    	{
	    		this.checked = check;
	    	});
			addToDocDaFirmare();
	    }
	
		function addToDocDaFirmare(){
			var docDaFirmare = "";
			var almenoUno = false;
			jQuery(".documenti_da_firmare_cls").each(function() {
			    if(this.checked){
			    	docDaFirmare += this.value+",";
			    	almenoUno=true;
			    }
			});
			docDaFirmare = docDaFirmare.replace(/,$/,"");
			if(almenoUno){
				jQuery('#metti_alla_firma_fun').show();
			}else{
				jQuery('#metti_alla_firma_fun').hide();
			}
			jQuery('#lista_doc_da_firmare_id').val(docDaFirmare);
		}
	
		function mettiallafirma(){
			var listaCodici=jQuery('#lista_doc_da_firmare_id').val();
            javascript:historySet('${_urlback}', '../documentidafirmare/createMettiAllaFirmaMultipli.htm?codiciMovAllegato='+listaCodici, '');
		}
	
	
		jQuery(function () {
	      	
	  		jQuery(".delete_id").eliminaConConferma({
	      		dialogSelector: '#dialog-6',
	      		dataId: 'codiceAllegatoMovimento',
	      		callback: function (id) {
	      			//alert(id);
	      			elimina(id)
	      		}
	      	});
	  		
	  	});
  		
			
			jQuery(function () {
		      	
		  		jQuery(".applica_layer_id").applicaLayerConConferma({
		      		dialogSelector: '#dialog-7',
		      		dataMovId: 'codiceMovimento',
		      		dataOggId: 'codiceOggetto',
		      		callback: function (dataMovId,dataOggId) {
		      			//alert(dataOggId);
		      			//alert(dataMovId);
		      			applicaLayerProtocolloInPDF(dataMovId,dataOggId)
		      		}
		      	});
		  		
		  	});
	
			function applicaLayerProtocolloInPDF(codiceMovimento,codiceOggetto){
				
				doHref('applicaLayerProtocolloPdf.htm?codiceMovimento='+codiceMovimento+'&codiceOggetto='+codiceOggetto,'');
			}
			
			function applicaQRCODE(codMovAllegato){
				doHref('insertQRCode.htm?codiceMovAllegati='+codMovAllegato,'Attenzione! Al documento PDF verra\' aggiunto un QRcode. Assicurarsi che il doc non sia firmato. Proseguire con l\'operazione?');
				
			}
			
			function applicaQrcodePdf(codMovAllegato, codOggetto){
				console.log(codMovAllegato, codOggetto);
				doHref('applicaQrcodePdf.htm?codiceMovimentoAll='+codMovAllegato+'&codiceOggetto='+codOggetto,'Attenzione! Al documento PDF verra\' aggiunto un QRcode con i dati dell\'istanza. Proseguire con l\'operazione?');
				
			}			
			
			function cancellaDocIstanzaConfirm(codice){
				dijit.byId('cancellaDocumentiIstanzeDialogDiv').show();
				$('codiceMovimentoAllegato_id').value = codice;
			}
			/**
			function elimina(){
				doHref('deleteMovAllegati.htm?codice='+$('codiceMovimentoAllegato_id').value+'&codiceMovimento='+$('codiceMovimento_id').value,'',document.inviodati);
			}
			**/
			function elimina(codiceAllegatoMovimento){
				doHref('deleteMovAllegati.htm?codice='+codiceAllegatoMovimento+'&codiceMovimento='+$('codiceMovimento_id').value,'',document.inviodati);
			}
			
			
			function changeValueValidoMovAll(obj, id){			
				var opzione="";
			    if(document.getElementById(obj).value!='null')
				{
			    	opzione=document.getElementById(obj).value;
				}
				
				new Ajax.Request('${pageContext.request.contextPath}/movimentiallegati/ajaxChangeValueFieldValido.htm?codice='+id+'&valido='+opzione, {
							method: 'post',	
							onSuccess: function(transport){
							  dijit.showTooltip(transport.responseText, dojo.byId(obj));
							  setTimeout(function(){dijit.hideTooltip(dojo.byId(obj))},1000);	
							},
							onFailure: function(transport){ 
							  $(id).innerHTML= transport.responseText;
							  $(id).className='error_checkbox';
							  $(id).style.display='';
							  $(id).pulsate();
							  $(id).fade();
							}						    		 
					});
				}
			
			
		</script>
		
		
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:historySet('${_urlback}', '../movimentiallegati/create.htm?codiceMovimento=${movimento.id.codice}', '');"><fmt:message key="button.new" /></a></li>
				<li id="metti_alla_firma_fun" style="display: none;" ><a href="javascript:mettiallafirma()"><fmt:message key="button.metti_alla_firma_multiplo" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
		<script type="text/javascript">
		
			document.addEventListener("DOMContentLoaded",(e)=>{
				disableFunctions();
				 jQuery(document).ajaxStop(()=>{
					 
					 function disabilitaZipLogico() {
						  const tabellaMovimentiAllegati = document.getElementById("movimentiallegati_id");
						  var righeMovimentiAllegati = tabellaMovimentiAllegati.getElementsByTagName("tbody")[1].getElementsByTagName("tr");
						  var arrRigheMovimentiAllegati = [...righeMovimentiAllegati];
						  arrRigheMovimentiAllegati.forEach(riga => {
						    let codiceMovAllegati = Number(riga.getElementsByTagName("td")[0].getElementsByTagName("a")[0].innerHTML);
						    var codiceMovimentiAllegati = [
						      <c:forEach var="dettaglio" items="${movinmentoziplogicotestata.dettaglii}">
						        <c:out value="${dettaglio.movimentiZipLogico.movimentiallegati.id.codice}" />,
						      </c:forEach>
						    ];
						    var guidCollegato = '<c:out value="${movinmentoziplogicotestata.guidCollegato}"/>';

						    for (var i = 0; i < codiceMovimentiAllegati.length; i++) {
						      if (codiceMovAllegati == codiceMovimentiAllegati[i] && guidCollegato) {

						        riga.classList.add("disabilitata-zip-logico");
						        riga.setAttribute("title","Documento utilizzato nello zip logico collegato");
						        riga.removeAttribute("onmouseover");
						        riga.removeAttribute("onmouseout");
						        let ogg =  riga.getElementsByClassName("el-mostra-oggetto")[0];						       
					        	let o = ogg.getElementsByTagName("a");
					        	let arrOgg = [...o];
					        	arrOgg = arrOgg.slice(1,arrOgg.length - 1);
						        arrOgg.forEach(logg=>{
						        	logg.classList.add("disabled-link");
						        	logg.style.color="gray";
						        });
						 
						        
						        
						      }
						    }

						  });
						}
					
			  		disabilitaZipLogico()
			  		enableFunctions();
				  });
				 
		
				 
				
			});
			jQuery(document).ready(function() {
				console.log("at ready function");
				hideXMLEst();
		

			});
		</script>
	</body>
</html>