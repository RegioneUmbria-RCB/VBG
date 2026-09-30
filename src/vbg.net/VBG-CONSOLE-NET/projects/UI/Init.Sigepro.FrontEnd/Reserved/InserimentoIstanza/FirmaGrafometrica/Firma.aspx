<%@ Page Title="Firma grafometrica" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="Firma.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.FirmaGrafometrica.Firma" %>
<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
    
	<script type="text/javascript" src="http://127.0.0.1:7777/files/fcsign.js"></script>
	<script type="text/javascript">

		require(['jquery', 'app/uploadAllegati'], function ($, UploadAllegati) {
			var urlOggetto = '<%=GetUrlMostraOggetto()%>';
			var urlUploadOggetto = '<%=GetUrlUploadOggetto()%>';

			window.mostraOggetto = function () {
				window.open(urlOggetto);
				return false;
			}

			function getReaders() {
				var deferred = $.Deferred();

				console.log(fcsign);

				fcsign.callback = function (response) {
					if (response.success) {
						deferred.resolve(response.readers);
					} else {
						deferred.reject(response.errorMessage || 'Si è verificato un errore durante la lettura dei lettori attivi');
					}
				};

				fcsign.readers(false, true);

				return deferred;
			}

			function getDispositivi() {
				var deferred = $.Deferred();

				console.log(fcsign);

				fcsign.callback = function (response) {
					if (response.success) {
						deferred.resolve(response.tablets);
					} else {
						deferred.reject(response.errorMessage || 'Si è verificato un errore durante la lettura dei dispositivi di firma collegati');
					}
				};

				fcsign.getSupportedTablets(true); // True serve a leggere solo i dispositivi connessi

				return deferred;
			}

			function popolaListaLettori(readers) {
				var select = $('#cmbListaLettori'),
					loader = $('#loaderLettori'),
					i = 0,
					reader,
					readerType;

				console.log(readers);

				select
					.find('option')
					.remove();

				for (i = 0; i < readers.length; i++) {
					reader = readers[i];
					readerType = reader.remote ? 'r' : (reader.smartCard ? 's' : 'n');

					$('<option>' + reader.name + '</option>', { 'value': readerType })
						.appendTo(select);
				}

				select.show(function () { loader.hide(); });
			}

			function popolaListaDispositivi(tablets) {
				var select = $('#cmbListaDispositivi'),
					loader = $('#loaderDispositivi'),
					i = 0,
					tablet;

				console.log('tablets=', tablets);

				select
					.find('option')
					.remove();

				for (i = 0; i < tablets.length; i++) {
					tablet = tablets[i];

					$('<option>' + tablet.name + '</option>', { 'value': tablet.modelAsString })
						.data('dati', tablet)
						.appendTo(select);
				}

				select.show(function () { loader.hide(); });
			}

			function firma() {
				var urlDownload = urlOggetto + "&inline=1",
					nomeFile = "riepilogo_domanda.pdf",
					uploadUrl = urlUploadOggetto,
					nomeFileFirmato = "riepilogo_domanda_firmato.pdf",
					deferred = $.Deferred(),
					extraParams = 'PdfViewer.ButtonRetryVisible=1;' +
								  'SignedFile.SaveInSameFolder=0;' +
								  'AdvancedSignature.FromTabletPC=0;' +
								  'AdvancedSignature.TabletModel=' + $('#cmbListaDispositivi>option:selected').data('dati').modelAsString +
								  ';Pin=';

				$('.bottoni').hide(function () { $('#firmaInCorso').show(); });

				console.log($('#cmbListaDispositivi>option:selected').data('dati').modelAsString);
				console.log("extraParams", extraParams);

				fcsign.extraParams = extraParams;
				fcsign.callback = function (response) {

					$('#firmaInCorso').hide(function () { $('.bottoni').show(); });

					if (response.success) {
						deferred.resolve(response.successMessage || "Firma completata con successo");
					} else {
						deferred.reject(response.errorMessage || 'Si è verificato un errore durante la firma del file');
					}
				};

				fcsign.sign(urlDownload, nomeFile, uploadUrl, nomeFileFirmato);

				return deferred;
			}

			function mostraMessaggioVerifica() {
				$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
					_title: function (title) {
						if (!this.options.title) {
							title.html("&#160;");
						} else {
							title.html(this.options.title);
						}
					}
				}));

				$('.messaggioInvio').dialog({
					dialogClass: 'no-close',
					width: 700,
					height: 100,
					title: '<span class="ion-load-b" id="Span2"></span>&nbsp;Verifica del file in corso',
					modal: true,
					closeOnEscape: false
				});
			}

			function effettuaPostBack(resultMessage) {
				console.log(resultMessage);
				$("#hidEsito").val('<%=this.SignatureId %>');
				__doPostBack('hidEsito', '');
			}


			$(function () {
				getReaders()
					.then(popolaListaLettori)
					.then(getDispositivi)
					.done(popolaListaDispositivi)
					.fail(function (errMsg) {
						alert(errMsg);
					});

				$('#preview').attr('src', urlOggetto + '&inline=1#toolbar=0');

				$('.cmdFirma').on('click', function (e) {

					firma()
					.done(function (successMsg) {

						mostraMessaggioVerifica();

						effettuaPostBack();
					})
					.fail(function (errMsg) {
						alert(errMsg);
					});

					e.preventDefault();
				});
			});
		});
    </script>

	<style media="all">
		#preview
		{
			width: 100%;
			height: 400px;
		}
		
		.no-close .ui-dialog-titlebar-close {
		  display: none;
		}
		
		.ion-load-b
		{
			-webkit-animation: spin 3s infinite linear;
			-moz-animation: spin 3s infinite linear;
			-o-animation: spin 3s infinite linear;
			animation: spin 3s infinite linear;
			font-size: 1.5em;
		}
		
		.ui-dialog-title>.ion-load-b
		{
			font-size: 1.2em;
		}
		
		.ion-alert-circled
		{
			font-size: 1.5em;
		}
		
		@-moz-keyframes spin {
			from { -moz-transform: rotate(0deg); }
			to { -moz-transform: rotate(360deg); }
		}
		@-webkit-keyframes spin {
			from { -webkit-transform: rotate(0deg); }
			to { -webkit-transform: rotate(360deg); }
		}
		@keyframes spin {
			from {transform:rotate(0deg);}
			to {transform:rotate(360deg);}
		}
	</style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<div class="inputForm">
		<fieldset>
		
        <asp:MultiView runat="server" ID="multiView">
            <asp:View runat="server" ID="firmaView">
                <span class="ion-alert-circled"></span>
                Attenzione! Questo tipo di firma può essere eseguito solo presentandosi al comune negli uffici predisposti alla presentazione.
                <br />
                <br />
                <div>
					<label>Lettore</label>
					<select id="cmbListaLettori" style="display:none"></select>
					<span class="ion-load-b" id="loaderLettori"></span>
				</div>

				<div>
					<label>Dispositivo</label>
					<select id="cmbListaDispositivi" style="display:none"></select>
					<span class="ion-load-b" id="loaderDispositivi"></span>
				</div>

				<div>
					<iframe id="preview" src=""></iframe>
				</div>

     
				<asp:HiddenField runat="server" Value="" ID="hidEsito" ClientIDMode="Static" OnValueChanged="hidEsito_ValueChanged" />

                <div class="bottoni">
                    <asp:Button runat="server" ID="cmdFirma" Text="Firma" CssClass="cmdFirma" OnClientClick="return false;"/>
                    <asp:Button runat="server" ID="cmdVisualizzaFile" Text="Scarica file" OnClientClick="return mostraOggetto();"/>
                    <asp:Button runat="server" ID="Button1" Text="Annulla" OnClick="GoBackToCallingPage"/>
                </div>

				<div id="firmaInCorso" style="display: none;">
					<span class="ion-load-b" id="Span1"></span> Firma del documento in corso...
				</div>

				<div class="messaggioInvio" id="invioInCorso" style="display: none;">
					Verifica del file in corso, l'operazione potrebbe richiedere anche alcuni minuti. Si prega di attendere senza interagire con il browser
				</div>

            </asp:View>

            <asp:View runat="server" ID="erroreView">
                <div class="warningEndo">
                    <%--<span class="icon-warning ion-alert-circled"></span>--%>
                    <asp:Image runat="server" ID="imgWarning" ImageUrl="~/Images/warning-icon.png" class="iconaWarning" />

                    <div class="contenutoWarning">
                        <h3>Scheda "<%= GetNomeSchedaDinamica() %>" non compilata</h3>
                        Per poter procedere alla firma tramite CID/PIN occorre compilare la scheda "<%= GetNomeSchedaDinamica() %>" presente nello step <%= (GetIndiceSchedaDinamica() + 1).ToString() %>.<br />
                        <br />
                        Premere "Torna alla scheda" per tornare allo step <%= (GetIndiceSchedaDinamica() + 1).ToString() %> per compilare la scheda. Altrimenti premete "Annulla" per selezionare un altro metodo di firma.
                    </div>


                </div>

                                    <div class="bottoni">
                        <asp:Button runat="server" ID="cmdVaiAllaScheda" Text="Torna alla scheda" OnClick="cmdVaiAllaScheda_Click"/>
                        <asp:Button runat="server" ID="cmdChiudi" Text="Annulla" OnClick="GoBackToCallingPage"/>
                    </div>
            </asp:View>

        </asp:MultiView>
        </fieldset>
    </div>

</asp:Content>
