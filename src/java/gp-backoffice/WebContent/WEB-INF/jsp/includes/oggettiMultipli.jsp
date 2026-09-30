	<fieldset> 
		<div id="functions">
		<!-- Il div viene popolato dinamicamente con i campi di input file -->	
		<ul id="contenitoreUpload">
			
		</ul>
		</div>
		<br />	
		<a class="vbg-btn btn-aggiungi" href="javascript: void 0" id="aggiungiFile" title="Aggiungi file">
		</a>
	</fieldset>
	
		<style>
			input[type=file] {
				border: 1px solid;
				display: none;
			}
			

		</style>
		
		<div id="template-carica-file" style="display:none">
			<li class="template btn btn-primary">
				<input type="file" />
				<a href="#" class="carica-file">Carica file</a>
				<span class="bottone-file"></span>
				<a href="#" class="rimuovi-file vbg-btn btn-elimina" style="display:none">
					<!-- <img src="../images/cross.gif">  -->
					<label>&nbsp;</label>
				</a>
			</li>
		</div>
							
		<script type="text/javascript">
		        // Rimuove un file selezionato
				function rimuoviFile(contatore)
				{
					jQuery("#li_id"+contatore).remove();
				}
		
				jQuery(function () {
					
					var contatore = 0;
					// Funzione che permette di creare l'input di un nuovo file
					function aggiungiInput() {
					
						var template = jQuery('#template-carica-file>.template').clone(),
							parsed = template, //jQuery(template),
							a = parsed.find('.carica-file'),
							input = parsed.find('input'),
							span = parsed.find('span'),
							rimuovi = parsed.find('.rimuovi-file');
						
						contatore++
						
						input.attr('name', 'nomeFile' + contatore);
						input.attr('id', 'file' + contatore);
							
						jQuery('#contenitoreUpload').append(parsed);
						
						a.on('click', function (e) {

							input.click();
						
							e.preventDefault();
						});
						
						rimuovi.on('click', function (e) {
							parsed.remove();
							
							e.preventDefault();
						});
						
	
						input.on('change', function () {
							var fullpath = jQuery(this).val(),	
								file = fullpath.slice(fullpath.lastIndexOf('\\') + 1 );
								
							span.text(file);
							//span.append("<a style:\"none\" href=\"javascript: void 0\" id=\"rimuoviFile\" onclick='javascript: rimuoviFile("+ (contatore) + ")' title=\"Rimuovi file\"><img src=\"../images/cross.gif\"> </a>");
							//span.append("<span id='rimuovi' ><img src=\"../images/cross.gif\"><span>");
							//jQuery('#contenitoreUpload').append(parsed);
							
							a.hide();
							rimuovi.show();
							//jQuery("#li_id"+contatore).remove(".functions");
							
							
						});
						applyStyle();
						
					}
					
					// Chiamata che viene fatto al caricamento della pagina per impostare di default un campo di 
					// inserimento file
					aggiungiInput();
					
					// Funzione che scatena l'evento di aggiungere un nuvo bottone seleziona file
					jQuery('#aggiungiFile').on("click", function (e) {
						aggiungiInput();
						
						e.preventDefault();
					})
					
				

				});
		</script>
							