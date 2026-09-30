<%@ include file="../includes/taglibs.jsp"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<script>

	(function($,namespaceContainer){
		
		
		
		function PopupInviaNodoPagamenti(elemento) {
			var deferred;
			var self = this;
			
			var $elemento = elemento.dialog({
				 'autoOpen': false,
				 'width': '800',
				 'title': 'Invia al nodo dei pagamenti',
				 'modal': true,
				 'close': function () {
					 self.callbackChiusura();
					 
				 }
			 });
			
			
			
		
		this.mostra = function(codiceIstanza,codiceTipiCausaliOnere,isOnereRateizzato){
			
			$elemento.dialog('option', 'title', "Creazione posizione debitoria da oneri");
			$elemento.css("maxHeight",800);
			$elemento.dialog('open');
			
			deferred = $.Deferred();
			deferred.resolve({});			
			
			return deferred;
		};
		
		this.nascondi = function () {
			$elemento.dialog('close');
		};
		this.callbackChiusura = function () {
			
			deferred.reject();
			disableFunctions();
			location.reload();
		};
	}
		namespaceContainer.PopupInviaNodoPagamenti = PopupInviaNodoPagamenti;
		})(jQuery,window);
	
	function inserisciPosizionidebitorieDaIstanzeOneri(){
			let x = document.getElementsByClassName("soggetti_collegati");
			var codiceSoggettoCollegato_values = new Array();
			
			for (var i = 0; i < x.length; i++) {
				if (x.item(i).checked) {
					codiceSoggettoCollegato_values.push(x.item(i).value);		
				}
				
				
			}
			callAsync({
				url : "inserisciPosizionidebitorieDaIstanzeOneri.htm",
				data : {
					codiceIstanza:${istanzeoneri.istanza.id.codice},
					codiceTipiCausali: ${istanzeoneri.tipicausalioneri.id.codice},
					isRateizzato : ${istanzeoneri.flagOnereRateizzato},
					codiceSoggettoCollegato : codiceSoggettoCollegato_values
					},
				success : function(data) {
					if(data != "OK"){
						jQuery('#errori-validazione').html(data);
						jQuery('#errori-validazione').show();
						jQuery('#messaggio-conferma').hide();
						jQuery('#cmd-invia').hide();
						
					}else{
						console.log("success",data);
						jQuery('#messaggio-conferma').css("color","green");
						jQuery('#cmd-invia').hide();
						location.reload();
						
						
					}
						
					}	
			});
		
		
	}

	function callAsync(options) {
		
		disableFunctions();
		
		return jQuery.ajax({	
			url : options.url,
			data : options.data,
			method : 'POST',
			type : 'POST', // For jQuery < 1.9
			context : document.body,
			cache : false,
			dataType : options.dataType || "html",
			success : options.success,
			error : options.error || gestisciErrore
		}).always(enableFunctions);
	}
	function gestisciErrore(){
		
	}
	
	
	

	

</script>
<div id="form-invia-nodopagamento" style="display: none">
	<div id="errori-validazione" style="color: red;font-weight: bold;display: none"></div>
	<div id="messaggio-conferma"><init:editLabel key="label.istanzeoneri.messaggio_invia_sistemapagamenti" role="ROLE_EDITLABEL" /></div>
	<c:if test="${creaPerSoggettiCollegati && !empty istanzerichiedenti}">
		<div id="messaggio-richiedente"><init:editLabel key="label.istanzeoneri.messaggio_creazione_posizione" role="ROLE_EDITLABEL" /> <b>${istanza.richiedente.descrizioneRichiedente }.</b></div>
		<div>
			<init:editLabel key="label.istanzeoneri.messaggio_soggetti_collegati" role="ROLE_EDITLABEL" />
			<select id="scelta_obbligato_in_solido">
				<option value="No">No</option>
				<option value="Si">Si</option>				
			</select>
		</div>
		<div id="lista_obbligati_in_in_solido" style="display:none">
			
			<ul style="list-style-type: none;">
				<li><init:editLabel key="label.istanzeoneri.messaggio_soggetti_collegati.creazione_posizione" role="ROLE_EDITLABEL" /></li>
				<c:forEach items="${ istanzerichiedenti}" var="soggetticollegati" varStatus="loop">
				
					<li>
						<input type="checkbox" class="soggetti_collegati" id="soggetto_collegato_${loop.index}" value=${ soggetticollegati.richiedente.id.codice}>
						<label for="soggetto_collegato_${loop.index}"><b>${soggetticollegati.transientRichiedenteQualitaAzienda}</b> </label>
					</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>	
	<div style="max-height: 500px; overflow: auto">
		<table class="vbg-table">
					<thead>
					<tr>
						<th width="8%"><fmt:message key="label.codice"/></th>
						<th><fmt:message key="label.raggruppamento"/></th>
						<th><fmt:message key="label.causale"/></th>
						<th width="2%"><fmt:message key="label.numero_rata"/></th>
						<th width="5%"><fmt:message key="label.entrate_causale"/></th>
						<th width="5%" style="${param.dispayImportoIstruttoria}"><fmt:message key="label.entrate_istruttoria"/></th>
						<th width="8%"><fmt:message key="label.data_scadenza"/></th>
					</tr>
					</thead>
			       
					<tbody>	
					<c:forEach items="${listaIstanzeOneri}" var="istonere" varStatus="loop">
						 <tr>
						 	 <td class="codiciistanzeoneri">${istonere.id.codice}</td>
						 	 <td>${istonere.tipicausalioneri.raggruppamentocausalioneri.rcoDescr }</td>
						 	 <td>${istonere.tipicausalioneri.coDescrizione}</td>
						 	 <td style="text-align: right;">${istonere.numerorata}</td>
						 	 <td style="text-align: right;color: red;"><fmt:formatNumber  value="${istonere.prezzo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
						 	 <td style="text-align: right;color: red;padding-right:5px; ${param.dispayImportoIstruttoria}">[${param.dispayImportoIstruttoria}]<fmt:formatNumber  value="${istonere.prezzoistruttoria}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>"></fmt:formatNumber></td>
						 	 <td><fmt:formatDate value="${istonere.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" />  </td>
						 </tr>
					</c:forEach>
				</tbody>
		</table>
	
	</div>
	<div id="functions">
    <ul id="cmd-invia">
    	<li><a href="javascript:inserisciPosizionidebitorieDaIstanzeOneri();"><fmt:message key="button.inviaSistemaPagamenti"/></a></li>
    </ul>
</div>
</div>

