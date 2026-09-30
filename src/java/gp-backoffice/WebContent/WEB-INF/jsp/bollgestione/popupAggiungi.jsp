<%@ include file="../includes/taglibs.jsp"%>
<script type="text/javascript">
	(function ($, namespaceContainer){
		

		function PopupAggiungi(elemento) {
			var $elemento = elemento.dialog({
				'autoOpen': false,
				 'width': '600',
				 'title': 'Aggiungi riga',
				 'modal': true,
				 'close': function () {
					 self.callbackChiusura();
				 }
			});
			var deferred;
			var self = this;
			
			var campoIdBollettazione = $elemento.find('#aggiungiIdBollettazione'),
				campoIdAnagrafica = $elemento.find('#aggiungiIdAnagrafica'),
				campoDescrizione = $elemento.find('#aggiungiDescrizione'),
				campoConti = $elemento.find('#aggiungiConti'),
				campoImportoSenzaIVA = $elemento.find('#aggiungiImportoSenzaIVA'),
				campoIVA = $elemento.find('#aggiungiIVA'),
				campoImporto = $elemento.find('#aggiungiImporto'),
				campoNoteUtente = $elemento.find('#aggiungiNoteUtente');
			
			var cmdSalva = $elemento.find('#cmdAggiungi');

			campoImportoSenzaIVA.on('blur', function() {
				if (campoImportoSenzaIVA.val()!= '') {
					var importoSenzaIVA = parseFloat(stringToNumber(campoImportoSenzaIVA.val()));
					var iva = parseFloat(stringToNumber(campoIVA.val()));
					
					campoImporto.val(numberToString(importoSenzaIVA + (importoSenzaIVA * iva / 100)));

				}
			});
			
			campoIVA.on('keyup', function () {
				if (campoIVA.val()=== '') {
					campoIVA.val("0");
				}
			});
			
			campoIVA.on('blur', function() {
				if (campoImportoSenzaIVA.val()!= '') {
					var importoSenzaIVA = parseFloat(stringToNumber(campoImportoSenzaIVA.val()));
					var iva = parseFloat(stringToNumber(campoIVA.val()));
					campoImporto.val(numberToString(importoSenzaIVA + (importoSenzaIVA * iva / 100)));
				}
			});

			$elemento.find('[required=true]').each(function (idx) {
				var el = $(this),
					targetErrore = $elemento.find(el.data('targetErrore'));
				
				targetErrore.hide();
				
				el.on('focusout', function () {
					el.addClass('touched');
					
					targetErrore.hide();
					el.removeClass('has-error');
					if (el.val() === '') {
						targetErrore.show();
						el.addClass('has-error');
					}					
				});
				
			});
			
			let numberToString = function(val){
				
				if(val && val!=''){
					return (val +'').replace('.',',');
				}
				return val;
			};
			
			let stringToNumber = function(val){
				if(val && val!=''){
					return val.replace(',','.');
				}
				return val;
			};
									
			this.mostra = function (idBollettazione, idAnagrafica) {
				
				
				campoIdBollettazione.val(idBollettazione);
				campoIdAnagrafica.val(idAnagrafica);
				campoConti.find("option").first().attr('selected', 'selected');
				campoImportoSenzaIVA.val(0);
				campoIVA.val(0);
				campoImporto.val(0);
				campoDescrizione.val('');
				campoNoteUtente.val('');							
				
				$elemento.dialog('open');
				
				$elemento.find('.has-error').removeClass('has-error');
				$elemento.find('.touched').removeClass('touched');
				
				$elemento.find('.error > li').hide();
				
				deferred = $.Deferred();
				
				return deferred;
			};
			
			cmdSalva.on('click', function (e) {
				
				$elemento.find('[required=true]').not('.touched').each(function (idx) {
					$(this).trigger('focusout');
				});
				
				
				if($elemento.find('.has-error').length) {
					return;
				}
				
				
				deferred.resolve({
					idBollettazione: campoIdBollettazione.val(),
					idAnagrafica: campoIdAnagrafica.val(),
					idConto: campoConti.val(),
					descrizione: campoDescrizione.val(),
					importoSenzaIVA: campoImportoSenzaIVA.val(),
					iva: campoIVA.val(),
					importo: campoImporto.val(),
					noteUtente: campoNoteUtente.val()						
				});
				e.preventDefault();
			});
			
			this.nascondi = function () {
				$elemento.dialog('close');
			};
			
			this.callbackChiusura = function () {
				deferred.reject();
			};
		}
		
		namespaceContainer.PopupAggiungi = PopupAggiungi;
		
	})(jQuery, window);

</script>



<div id="formAggiungi" class="popup-form">

	<input type="hidden" id="aggiungiIdBollettazione"></input>
	<input type="hidden" id="aggiungiIdAnagrafica"></input>
				
				
	<ul class="error">
		<li id="aggiungiErroreDescrizione">
			<fmt:message key="dettaglioBollettazione.label.errore-descrizione" />
		</li>				
		<li id="aggiungiErroreImportoSenzaIVA">
			<fmt:message key="dettaglioBollettazione.label.errore-importo-senza-iva" />
		</li>
		<li id="aggiungiErroreImporto">
			<fmt:message key="dettaglioBollettazione.label.errore-importo" />
		</li>
	</ul>				
				
	<table>
		<tr>
			<td>
				<label for="aggiungiDescrizione">* <fmt:message
						key="dettaglioBollettazione.label.descrizione" />
				</label>
			</td>
			<td>
				<textarea id="aggiungiDescrizione" required="true" data-target-errore="#aggiungiErroreDescrizione"></textarea>
			</td>
		</tr>
		</tr>
				<tr>
			<td>
				* <fmt:message key="dettaglioBollettazione.label.importoSenzaIVA" /></td>
			<td>
				<input type="text" id="aggiungiImportoSenzaIVA" required="true" data-target-errore="#aggiungiErroreImportoSenzaIVA" class="numero"></input>
			</td>
		</tr>
				<tr>
			<td>
				* <fmt:message key="dettaglioBollettazione.label.iva" /></td>
			<td>
				<input type="text" id="aggiungiIVA" required="true" data-target-errore="#aggiungiErroreIVA" class="numero"></input>
			</td>
		</tr>
		<tr>	
			<td>
				<label for="aggiungiImporto">
				* <fmt:message key="dettaglioBollettazione.label.importo" />
				</label>
			</td>
			<td>
				<input type="text" id="aggiungiImporto" required="true" readonly="true" data-target-errore="#aggiungiErroreImporto" class="numero"></input>
			</td>
		</tr>
		<tr>	
			<td>
				<label for="aggiungiConti">
				* <fmt:message key="dettaglioBollettazione.label.conti" />
				</label>
			</td>
			<td>
				<spring-form:select path="descrizione" id="aggiungiConti"	items="${contiList}" 
				itemLabel="descrizione" itemValue="id.codice"></spring-form:select> 							
			</td>
		</tr>
		<tr>
			<td>
				<label for="aggiungiNoteUtente">
					<fmt:message key="dettaglioBollettazione.label.noteUtente" />
				</label>
			</td>
			<td>
				<textarea id="aggiungiNoteUtente" ></textarea>
			</td>
		</tr>
	</table>
	
	<div id="functions">
		<ul>
			<li>
				<a href="#" id="cmdAggiungi">
					<fmt:message key="button.update" />
				</a>
			</li>
		</ul>
	</div>

</div>