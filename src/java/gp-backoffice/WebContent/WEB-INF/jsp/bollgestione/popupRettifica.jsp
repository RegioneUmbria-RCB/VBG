<%@ include file="../includes/taglibs.jsp"%>

<script type="text/javascript">

(function ($, namespaceContainer){

	function PopupRettifica(elemento) {
		var $elemento = elemento.dialog({
			 'autoOpen': false,
			 'width': '600',
			 'title': 'Rettifica riga',
			 'modal': true,
			 'close': function () {
				 self.callbackChiusura();
			 }
		 });
		
		var deferred;
		var self = this;
		
		var campoIdBollettazione = $elemento.find('#rettificaIdBollettazione'),
			campoIdAnagrafica = $elemento.find('#rettificaIdAnagrafica'),
			campoIdRiga = $elemento.find('#rettificaIdRiga'),
			campoImportoSenzaIVA = $elemento.find('#rettificaImportoSenzaIVA'),
			campoIVA  = $elemento.find('#rettificaIVA'),
			campoImporto = $elemento.find('#rettificaImporto');
		
		var erroreImportoSenzaIVA = $elemento.find('#erroreImportoSenzaIVA');
		var erroreImporto = $elemento.find('#erroreImporto');
		var cmdSalva = $elemento.find('#cmdSalva');							
		
		this.mostraErroreImportoSenzaIVA = function () {
			erroreImportoSenzaIVA.show();
		};
		
		this.mostraErroreImporto = function () {
			erroreImporto.show();
		};
								
		this.mostra = function (idBollettazione, idAnagrafica, idRiga, descrizione, importoSenzaIVA, iva, importo) {
			
			erroreImporto.hide();
			erroreImportoSenzaIVA.hide();
			
			campoIdBollettazione.val(idBollettazione);
			campoIdAnagrafica.val(idAnagrafica);
			campoIdRiga.val(idRiga);
			campoImportoSenzaIVA.val(importoSenzaIVA);
			campoIVA.val(iva);
			campoImporto.val(importo);
			
			$elemento.dialog('option', 'title', descrizione);
			$elemento.dialog('open');
			
			deferred = $.Deferred();
			
			return deferred;
		};
		
		campoImportoSenzaIVA.on('keyup', function () {

			if (campoImportoSenzaIVA.val()=== '') {
				self.mostraErroreImportoSenzaIVA();
				campoImportoSenzaIVA.focus();
				
				return;
			}
		});
		
		function ricalcolaTotale(){
			
			var importoSenzaIVA = parseFloat(campoImportoSenzaIVA.val().replace(',','.'));
			var iva = parseFloat(campoIVA.val().replace(',','.'));			
			
			var totale = (importoSenzaIVA + (importoSenzaIVA * iva / 100))
							.toFixed(2)
							.toString()
							.replace('.',',');
			
			campoImporto.val(totale);
			console.log(importoSenzaIVA,iva,totale);
			
		}
		
		campoImportoSenzaIVA.on('blur', function() {
			if (campoImportoSenzaIVA.val()!= '') {
				ricalcolaTotale();

			}
		});
	
		campoIVA.on('keyup', function () {

			if (campoIVA.val()=== '') {
				campoIVA.val("0");
			}
		});
		
		campoIVA.on('blur', function() {
			if (campoImportoSenzaIVA.val()!= '') {
				ricalcolaTotale();
				
			}
		});

		campoImporto.on('keyup', function () {
			if (campoImporto.val()=== '') {
				self.mostraErroreImporto();
				campoImporto.focus();
				
				return;
			}
		});
		
		cmdSalva.on('click', function (e) {

			if (campoImportoSenzaIVA.val()=== '') {
				self.mostraErroreImportoSenzaIVA();
				campoImportoSenzaIVA.focus();
				
				return;
			}
			
			if (campoImporto.val()=== '') {
				self.mostraErroreImporto();
				campoImporto.focus();
				
				return;
			}
			
			if (campoIVA.val()=== '') {
				campoIVA.val("0");
			}
			
			ricalcolaTotale();
			
			deferred.resolve({
				idBollettazione: campoIdBollettazione.val(),
				idAnagrafica: campoIdAnagrafica.val(),
				idRiga: campoIdRiga.val(),
				importoSenzaIVA: campoImportoSenzaIVA.val(),
				iva: campoIVA.val(),
				importo: campoImporto.val()								
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
	
	namespaceContainer.PopupRettifica = PopupRettifica;
	
})(jQuery, window);

</script>



<div id="formRettifica"  class="popup-form">
				<input type="hidden" id="rettificaIdBollettazione"></input>
				<input type="hidden" id="rettificaIdAnagrafica"></input>
				<input type="hidden" id="rettificaIdRiga"></input>
				
				<div class="error" id="erroreImportoSenzaIVA">
					<fmt:message key="dettaglioBollettazione.label.errore-importo-senza-iva" />
				</div>
				<div class="error" id="erroreImporto">
					<fmt:message key="dettaglioBollettazione.label.errore-importo" />
				</div>
				
				<table>
					<tr>
						<td>
							<label for="">
								* <fmt:message
										key="dettaglioBollettazione.label.rettifica.importoSenzaIVA" />
							</label>
						</td>
						<td>
							<input type="text" id="rettificaImportoSenzaIVA" required="true" class="numero"> </input>
						</td>
					</tr>
					<tr>
						<td>
							<label for="">
								* <fmt:message
										key="dettaglioBollettazione.label.rettifica.iva" />
							</label>
						</td>
						<td>
							<input type="text" id="rettificaIVA" required="true" class="numero" ></input>
						</td>
					</tr>
					<tr>
						<td>
							<label for="">
								* <fmt:message
										key="dettaglioBollettazione.label.rettifica.importo" />
							</label>
						</td>
						<td>
							<input type="text" id="rettificaImporto" required="true" readonly="true" class="numero" ></input>
						</td>
					</tr>
				</table>
				
				<div id="functions">
					<ul>
						<li>
							<a href="#" id="cmdSalva">
								<fmt:message key="button.update" />
							</a>
						</li>
					</ul>
				</div>
			</div>