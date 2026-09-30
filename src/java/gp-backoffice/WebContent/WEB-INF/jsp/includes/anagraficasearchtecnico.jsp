<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%-- BEGIN RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>

<c:set var="tiposoggetto" value="" />
<c:if test="${not empty param.tiposoggetto}">
	<c:set var="tiposoggetto" value="${param.tiposoggetto}" />
</c:if>
<c:set var="anagrafeInputSize" value="67" />
<c:if test="${not empty param.anagrafeInputSize}">
	<c:set var="anagrafeInputSize" value="${param.anagrafeInputSize}" />
</c:if>
<c:set var="anagrafeAutocompleterAjax" value="findAnagrafe.htm" />
<c:if test="${not empty param.anagrafeAutocompleterAjax}">
	<c:set var="anagrafeAutocompleterAjax"
		value="${param.anagrafeAutocompleterAjax}" />
</c:if>
<c:set var="anagrafeMinChars" value="2" />
<c:if test="${not empty param.anagrafeMinChars}">
	<c:set var="anagrafeMinChars" value="${param.anagrafeMinChars}" />
</c:if>
<c:set var="codAnagrafeStorico" value="" />
<c:if test="${not empty param.codAnagrafeStorico}">
	<c:set var="codAnagrafeStorico" value="${param.codAnagrafeStorico}" />
</c:if>
<c:set var="dataAnagrafeStorico" value="" />
<c:if test="${not empty param.dataAnagrafeStorico}">
	<c:set var="dataAnagrafeStorico" value="${param.dataAnagrafeStorico}" />
</c:if>
<c:set var="descrizioneAnagrafeStorico" value="" />
<c:if test="${not empty param.descrizioneAnagrafeStorico}">
	<c:set var="descrizioneAnagrafeStorico" value="${param.descrizioneAnagrafeStorico}" />
</c:if>


<c:set var="statoAnagrafe" value=""/>
<c:if test="${not empty param.statoAnagrafe}">
	<c:set var="statoAnagrafe" value="${param.statoAnagrafe}"/>
</c:if>

<c:set var="styleTableStorico" value="padding-left: 0px;"/>
<c:if test="${not empty  codAnagrafeStorico and not empty dataAnagrafeStorico}">
	<c:set var="styleTableStorico" value="border: #c0c0c0 1px dashed; padding-left: 0px;"/>

<!--   PARAMETRO OPZIONALE 
 
	  Parametro aggiunto per la funzionalità del verifica e richiesta DURC 
      Nel caso la richiesta venga fatta per un soggetto dell'istanza  sarà necessario
      tenere traccia anche del codice istanza
-->
<c:if test="${not empty param.codiceIstanza}">
	<c:set var="codiceIstanza" value="${param.codiceIstanza}" />
</c:if>
<!--   PARAMETRO OPZIONALE 
 
	  Parametro aggiunto per le funzionalità: Altre operazioni.
	  Nel caso sia presente allora l'icona per accedere alle Altre operazione, altrimenti no.
	  Necessario per verificare se è possibile impostare la history back correttamente, nel caso 
	  siamo in fase di insert non è possibile impostare la history correttamente. 
-->
<c:set var="isInUpdate" value="false" />
<c:if test="${not empty param.isInUpdate}">
	<c:set var="isInUpdate" value="true" />
</c:if>

<table style="${styleTableStorico}">
<tr>
	<td>
</c:if>	
		<%-- END RECUPERO PARAMETRI PER CONFIGURAZIONI OPZIONALI --%>
		<%-- BEGIN SEZIONE ANAGRAFE PRINCIPALE --%>
		<script type="text/javascript">
		
		function setHiddenField1${param.idElemento}(inputField,listItem){
			var a = listItem.id;
			document.getElementById('${param.idElemento}_id1').value = inputField.value;
			document.getElementById('${param.idElemento}_hidden').value = a;
			visualizzaFunzioni${param.idElemento}();
			$('${param.idElemento}_choices').fade();
		}
		
		
		
		</script>
		
		<%-- --%>

		<spring-form:input 
			id="${param.idElemento}_id1" 
			path="${param.pathAnagrafica}.descrizioneRichiedente" cssClass="searchbox"
			size="${anagrafeInputSize}"
			onchange="checkValue(this,'${param.idElemento}_hidden')"
			onkeydown="return searchAll(this,event)" />
		<init:autocompleter 
			methodAjax='findAnagrafe.htm?statoAnagrafe=${statoAnagrafe}'  
			idHidden="${param.idElemento}_hidden" 
			idInput="${param.idElemento}_id1" 
			inputTitleKey="${param.titleKey}" 
			callBack="${ajaxCallBack}" 
			afterUpdateElement="setHiddenField1${param.idElemento}"/>
		

	
	<spring-form:hidden path="${param.pathAnagrafica}.id.codice" id="${param.idElemento}_hidden" />
	<spring-form:errors path="${param.pathAnagrafica}" cssClass="error" />
	
	

		<%-- END SEZIONE ANAGRAFE PRINCIPALE--%>
<c:if test="${not empty  codAnagrafeStorico and not empty dataAnagrafeStorico}">		
	</td>
	<td>
</c:if>		
		
		<%-- BEGIN ICONE FUNZIONALITA' --%>
		<%-- se il parametro non è specificato --%>
		<span id="${param.idElemento}_interdizioni" style="display: none;"></span>  
		<c:if test="${empty param.anagrafeHideFunctions}">
		
			<a class="vbg-btn btn-dettaglio" id="imgDettaglio${param.idElemento}" style="display: none;" 
			href="javascript:dettaglioAnagrafe${param.idElemento}('${param.idElemento}_hidden');" 
				title="<fmt:message key="label.visualizza_dettaglio_anagrafe" />">
			</a>			
			<a class="vbg-btn btn-avvisi" id="imgAvvisi${param.idElemento}" style="display: none;"
			href="javascript:dettaglioAvvisi${param.idElemento}();" title="<fmt:message key="label.visualizza_avvisi_anagrafe" />" >
			</a>						
			<a class="vbg-btn btn-modifica" id="imgModifica${param.idElemento}" style="display: none;" 
			href="javascript:modificaAnagrafe${param.idElemento}('${param.idElemento}_hidden');" title="<fmt:message key="label.modifica_anagrafe" />">
			</a>
			<a class="vbg-btn btn-aggiungi" id="imgAggiungi${param.idElemento}"  
			href="javascript:nuovaAnagrafe${param.idElemento}('${param.idElemento}_hidden');" <fmt:message key="label.inserisci_anagrafe" />>
			</a>		
			 <%-- INIZIO SEZIONE ICONA ALTRE OPERAZIONI --%>
		    <a class="vbg-btn btn-altreopzioni" id="imgAltreOperazioni${param.idElemento}" style="display: none;" 
		    href="javascript:tabAltreOperazioni${param.idElemento}('${param.idElemento}_hidden');" <fmt:message key="label.altre_operazioni" />>
			</a>
			<%-- INIZIO APERTURA PANNELLO ALTRE OPERAZIONI --%>
		    <div dojoType="dijit.Dialog" style="display:none;" id="altreOperazioniDialogDiv${param.idElemento}_hidden" title="<fmt:message key="label.altre_operazioni" />: ">
				<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 400px; height: 200px;">
				<table>
					<tr><td colspan="2"><fmt:message key="label.seleziona_operazione_help" /><td></tr>
					<tr><td colspan="2">&nbsp;<td></tr>
					<tr>
						<td><fmt:message key="label.seleziona_operazione" /></td>
						<%--
						<td>
						     <select id="list">
					            <option attributo="verificaDURC" value="verificaDURC">Verifica DURC</option>
					            <option attributo="richiediDURC" value="richiediDURC">Richiedi DURC</option>
					        </select>
			        	</td>
			        	 --%>
			        	 <td>
				        	<select id="list${param.idElemento}" name="list${param.idElemento}"></select>
			        	</td>
			        </tr>
        		</table>
        		<div id="functions" style="padding-top: 5em;">
					<ul>
						<li><a class="button${param.idElemento}" href="javascript:void 0"><fmt:message key="button.ok" /></a></li>
						<li><a href="javascript:void 0" onclick="closeTabAltreOperazioni('${param.idElemento}_hidden');"><fmt:message key="button.annulla" /></a></li>
					</ul>
				</div>	
        	</div>
		  </div>
		 <%-- END APERTURA PANNELLO ALTRE OPERAZIONI --%>
		 <script type="text/javascript">
		        var codiceAnagrafe ;
		        var myDialog;
				function tabAltreOperazioni${param.idElemento}(id){
					codiceAnagrafe=document.getElementById(id).value;
					myDialog=dijit.byId("altreOperazioniDialogDiv"+id).show();
					loadAltreOperazioniDisponibile${param.idElemento}();
				}
				
				function closeTabAltreOperazioni(id)
				{
					myDialog=dijit.byId("altreOperazioniDialogDiv"+id).hide();	
				}
				 
				jQuery(document).ready(function () {
					jQuery(".button${param.idElemento}").click(function(){
						if(jQuery("#list${param.idElemento} option:selected").val() == 'Verifica Durc')
						{
							historySet('${_urlback}','../anagrafe/verificaDurc.htm?codiceAnagrafe='+codiceAnagrafe+'&codiceIstanza=${codiceIstanza}','');
						}
						if(jQuery("#list${param.idElemento} option:selected").val() == 'Richiedi Durc')
						{
							historySet('${_urlback}','../anagrafe/richiediDurc.htm?codiceAnagrafe='+codiceAnagrafe+'&codiceIstanza=${codiceIstanza}','');
						}
						if(jQuery("#list${param.idElemento} option:selected").val() == 'Seleziona')
						{
							
						}
				    });
				});
				
				function loadAltreOperazioniDisponibile${param.idElemento}()
				{
					new Ajax.Request('${pageContext.request.contextPath}/json/getAltreOperazioniDisponibili.htm', { 
					 		method:'post',
					 		//parameters:{chiave_ricerca : code}, 
			  				onSuccess: function(transport){
			  			 	var json = transport.responseText.evalJSON();
			  			    jQuery("#list${param.idElemento} option").remove();
			  			 	for (var i = 0; i < json.item.length; i++) {
			  	                //options += '<option value="' + json.item[i] + '">' + json.item[i] + '</option>';
			  	                jQuery("#list${param.idElemento}").append( // Append an object to the inside of the select box
					    			jQuery("<option></option>") // Yes you can do this.
					                .text(json.item[i])
					                .val(json.item[i])
			  	            );
			  	            }
			  			},
			    		 onFailure: function(transport){
			  				printResult(transport, "Errore durante il recupero dell'informazioni");
			    		}
					});
				}
				
		</script>					
		 <%-- END SEZIONE ICONA ALTRE OPERAZIONI --%>			
			<%-- END ICONE FUNZIONALITA' --%>
		</c:if>
		<div id="${param.idElemento}_interdizioni" style="display: none;"></div>  
		
	
<c:if test="${not empty  codAnagrafeStorico and not empty dataAnagrafeStorico}">
</td>
</tr>
<%-- BEGIN SEZIONE ANAGRAFE STORICO --%>		
	<tr>
		<td>
			<c:if test="${not empty param.idElementoStorico}">
				<spring-form:hidden path="${param.pathAnagraficaStorica}.id.codice"
					id="${param.idElementoStorico}" />
			</c:if>
			
			<b>${descrizioneAnagrafeStorico}</b>
		</td>
		<td>
			<c:if test="${not empty codAnagrafeStorico}">	
				<a id="imgDettaglioStorico${param.idElemento}" class="vbg-btn btn-storico" style="display: none;"
				href="javascript:dettaglioAnagrafeStorico${param.idElemento}(${codAnagrafeStorico});" title="<fmt:message key="label.visualizza_dettaglio_anagrafe_storico" />">
				</a>
			</c:if>
		</td>
	</tr>
<%-- END SEZIONE ANAGRAFE STORICO --%>
</table>
</c:if>
		<%-- BEGIN JAVASCRIPT FUNZIONALITA' AGGIUNTIVE --%>
		<script type="text/javascript">
		
				function dettaglioAnagrafe${param.idElemento}(objId){		
					var codiceAnagrafe = document.getElementById(objId).value;
					if(codiceAnagrafe!=''){				
							var secondDlg${param.idElemento} = new dijit.Dialog({
					            title: "<fmt:message key="label.visualizza_dettaglio_anagrafe" />" ,
					            style: "overflow:auto; width: 600px;"
					        });
							new Ajax.Request('<%=request.getContextPath()%>/ajax/dettaglioAnagrafe.htm', {
								  method: 'post',
								  parameters: {codiceAnagrafe: codiceAnagrafe},
								  onSuccess: function(transport){
									  var response = transport.responseText;		
									  result = parseAjaxResponse(response, true, false);
									  secondDlg${param.idElemento}.attr("content", result);
								      secondDlg${param.idElemento}.show();							  
								    },
								  onFailure: function(transport){ 
									var response = transport.responseText;
									secondDlg${param.idElemento}.attr("content", response);
								    secondDlg${param.idElemento}.show();	
								  }
							});
						}
				}
				<c:if test="${not empty codAnagrafeStorico}">	
					function dettaglioAnagrafeStorico${param.idElemento}(codiceAnagrafeStorico){		
						if(codiceAnagrafeStorico!=''){				
								var secondDlg${param.idElemento} = new dijit.Dialog({
						            title: "<fmt:message key="label.visualizza_dettaglio_anagrafe_storico" />" ,
						            style: "overflow:auto;"
						        });
								new Ajax.Request('<%=request.getContextPath()%>/ajax/ajaxDettaglioStoricoAnagrafe.htm', {
									  method: 'post',
									  parameters: {codice: codiceAnagrafeStorico},
									  onSuccess: function(transport){
										  var response = transport.responseText;		
										  result = parseAjaxResponse(response, true, false);
										  secondDlg${param.idElemento}.attr("content", result);
									      secondDlg${param.idElemento}.show();							  
									    },
									  onFailure: function(transport){ 
										var response = transport.responseText;
										secondDlg${param.idElemento}.attr("content", response);
									    secondDlg${param.idElemento}.show();	
									  }
								});					
							}
					}
				</c:if>
				function visualizzaFunzioni${param.idElemento}(){
					<c:if test="${empty param.anagrafeHideFunctions}"> 
					if(document.getElementById('${param.idElemento}_hidden').value!=''){
						showDiv('imgDettaglio${param.idElemento}');
						<c:if test="${not empty codAnagrafeStorico}">
							showDiv('imgDettaglioStorico${param.idElemento}');
						</c:if>
						showDiv('imgModifica${param.idElemento}');
						showDiv('imgAltreOperazioni${param.idElemento}');
						checkForAvvisi${param.idElemento}();
					}else{
						hideDiv('imgDettaglio${param.idElemento}');
						<c:if test="${not empty codAnagrafeStorico}">
							hideDiv('imgDettaglioStorico${param.idElemento}');
						</c:if>
						hideDiv('imgModifica${param.idElemento}');
						hideDiv('imgAltreOperazioni${param.idElemento}');
					}
					</c:if>
				}
				function checkForAvvisi${param.idElemento}(){
					var codiceAnagrafe = document.getElementById('${param.idElemento}_hidden').value;
					
					
					if(codiceAnagrafe!=''){	
						var _ts  = new Date().getTime();
						
						
						new Ajax.Request('../json/findAnagrafeAvvisi.htm?ts_='+_ts, {
							  method: 'post',
							  parameters: {codiceAnagrafe: codiceAnagrafe},
							  onSuccess: function(transport){ 
								var response = transport.responseText;
								//displayErrorMessage(response);
								var json = response.evalJSON();
								if(json.anagrafeAvvisi){
									if(json.anagrafeAvvisi.avvisi == 'true'){
										showDiv('imgAvvisi${param.idElemento}');
									}else{
										hideDiv('imgAvvisi${param.idElemento}');
									}
									if(json.anagrafeAvvisi.interdizione){
										if(json.anagrafeAvvisi.interdizione!=''){
											$('${param.idElemento}_id1').style.color = "#FF0000";
											$('${param.idElemento}_id2').style.color = "#FF0000";
											$('${param.idElemento}_interdizioni').innerHTML=json.anagrafeAvvisi.interdizione;
											$('${param.idElemento}_interdizioni').style.color="#FF0000";
											$('${param.idElemento}_interdizioni').style.display='';
										}else{
											
											$('${param.idElemento}_id1').style.color = "#000000";
											$('${param.idElemento}_id2').style.color = "#000000";
											$('${param.idElemento}').style.backgroundColor = "#FFFFFF";
											$('${param.idElemento}_interdizioni').style.display='none';
										}
									}
								}
							  },
							  onFailure: function(transport){ 
								var response = transport.responseText; 
							    displayErrorMessage("Errore nella ricerca del procedimento!");
							  }						    		 
						} );
					
					}	
				}
				function dettaglioAvvisi${param.idElemento}(){		
					var codiceAnagrafe = document.getElementById('${param.idElemento}_hidden').value;
					if(codiceAnagrafe!=''){
						var avvisiDlg${param.idElemento} = new dijit.Dialog({
				            title: "<fmt:message key="label.visualizza_avvisi_anagrafe" />" ,
				            style: "overflow:auto; width: 400px; height: 200px;"
				        });
						new Ajax.Request('<%=request.getContextPath()%>/ajax/dettaglioAnagrafeAvvisi.htm', {
							  method: 'post',
							  parameters: {codiceAnagrafe: codiceAnagrafe},
							  onSuccess: function(transport){
								  var response = transport.responseText;		
								  result = parseAjaxResponse(response, true, false);
								  avvisiDlg${param.idElemento}.attr("content", result);
								  avvisiDlg${param.idElemento}.show();						  
							    },
							  onFailure: function(transport){ 
								var response = transport.responseText;
								avvisiDlg${param.idElemento}.attr("content", response);
								avvisiDlg${param.idElemento}.show();
								 }						    		 
						});
											
					}
				}
				<%-- VENGONO ESEGUITE AL CARICAMENTO--%>
				jQuery(document).ready(function(){
					if(document.getElementById('${param.idElemento}_hidden').value != ''){
						showDiv('imgDettaglio${param.idElemento}');
						<c:if test="${not empty codAnagrafeStorico}">
							showDiv('imgDettaglioStorico${param.idElemento}');
						</c:if>
						showDiv('imgModifica${param.idElemento}');
						showDiv('imgAltreOperazioni${param.idElemento}');
						checkForAvvisi${param.idElemento}();
					};
				});
				
				
				function modificaAnagrafe${param.idElemento}(objId){		
					var codiceAnagrafe = document.getElementById(objId).value;
					if(codiceAnagrafe!=''){
						var caller = '${param.idElemento}';
						var modificaAnagrafe${param.idElemento}Win = window.open("<%=request.getContextPath()%>/anagrafe/popupview.htm?codice="+codiceAnagrafe+"&popupCaller="+caller,69,"status=1,menubar=0,scrollbars=1,width=800, height=600");
					}
				}				
				function nuovaAnagrafe${param.idElemento}(objId){
					var caller = '${param.idElemento}';	
						if('${param.tiposoggetto}'!= '')
						{
							var tipo='${param.tiposoggetto}';
							var nuovaAnagrafe${param.idElemento}Win = window.open("<%=request.getContextPath()%>/anagrafe/popupcreate.htm?visualizzaTipoAnagrafe="+tipo+"&popupCaller="+caller,69,"status=1,menubar=0,scrollbars=1,width=800, height=600");			
						}else
						{
							var nuovaAnagrafe${param.idElemento}Win = window.open("<%=request.getContextPath()%>/anagrafe/popupcreate.htm?tiposoggetto=F&popupCaller="+caller,69,"status=1,menubar=0,scrollbars=1,width=800, height=600");			
						}
				}
			</script>			