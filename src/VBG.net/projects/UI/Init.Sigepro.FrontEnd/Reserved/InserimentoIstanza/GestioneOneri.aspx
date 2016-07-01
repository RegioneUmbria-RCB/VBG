<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
	AutoEventWireup="true" CodeBehind="GestioneOneri.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneOneri" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="uc1" TagName="GrigliaOneri" Src="~/Reserved/InserimentoIstanza/GestioneOneri_GrigliaOneri.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
	<style>
		#tabellaEndo .descrizioneEndo, #tabellaEndo .descrizioneIntervento
		{
			font-size: 0.8em;
			display: block;
			margin-top: 5px;
			margin-left: 10px;
		}
		
		#tabellaEndo .rigaTotaleImporti > td
		{
			text-align: right;
			font-weight: bold;
		}
		
		#tabellaEndo .helpNoteOnereRow
		{
			text-align: center;
		}
	</style>
	<script type="text/javascript">

		require(['jquery','jquery.ui'],function($) {

			var chkAssenzaOneri;
			var bloccoCaricamentoBollettino;


			function nascondiEstremiPagamento(chkElement) {
				var row=chkElement.parent().parent();

				row.find('.estremiPagamento').hide();
				row.find('.ddlTipoPagamento').val('');
				row.find('.txtDataPagamento').val('');
				row.find('.txtNumeroOperazione').val('');
			}

			function mostraEstremiPagamento(chkElement) {
				chkElement.parent().parent().find('.estremiPagamento').show();
			}

			function roundNumber(number,digits) {
				var multiple=Math.pow(10,digits);
				var rndedNum=Math.round(number*multiple)/multiple;
				return rndedNum;
			}

			function ricalcolaTotali() {
				var totale=0.0;

				$('.chkNonPagato>input:not(:checked)').each(function(idx,element) {
					var txtImportoPagato = $(element)
												.parent()
												.parent()
												.parent()
												.find('.importoPagato');

					var daPagare = parseFloat(txtImportoPagato.val().replace(',','.'));

					totale += daPagare;
				});

				$('#totaleDaPagare').html(roundNumber(totale,2).toFixed(2).replace('.',','));

				mostraONascondiCheckNoOneri();
			}

			function mostraONascondiCheckNoOneri() {
				var totaleDaPagare=parseFloat($('#totaleDaPagare').html());

				if(totaleDaPagare>0) {
					//	uncheck della checkbox e
					//	mostra il div di caricamento riepilogo oneri
					if(chkAssenzaOneri.is(':checked'))
						chkAssenzaOneri.click();

					//	nascondi checkbox
					$('#dichiarazioneAssenzaOneri').fadeOut();
				} else {
					$('#dichiarazioneAssenzaOneri').fadeIn();
				}
			}

			function mostraONascondiBloccoCaricamentoBollettino(mostra) {
				if(mostra) {
					bloccoCaricamentoBollettino.fadeOut();
				} else {
					bloccoCaricamentoBollettino.fadeIn();
				}
			}

			$(function() {
				chkAssenzaOneri=$('#<%=chkAssenzaOneri.ClientID %>');
				bloccoCaricamentoBollettino=$('#bloccoCaricamentoBollettino');

				if(chkAssenzaOneri.is(':checked'))
					bloccoCaricamentoBollettino.css('display','none');

				chkAssenzaOneri.click(function(e) {
					mostraONascondiBloccoCaricamentoBollettino($(this).is(':checked'));
				});

				$('.importoPagato').on('change', function (){
					
					var valore = $(this).val().replace(',','.');

					if(valore === '' || isNaN(valore))
						$(this).val('0,0');

					ricalcolaTotali();
				});


				$('.chkNonPagato').click(function() {
					var checkBox=$(this).find('input[type=checkbox]');

					if(!checkBox.is(':checked'))
						mostraEstremiPagamento($(this));
					else
						nascondiEstremiPagamento($(this));

					ricalcolaTotali();
				});

				$('.chkNonPagato').click();

				$('#testoNote').dialog({
					autoOpen: false,
					title: 'Note dell\'onere',
					modal: true
				});
				$('.noteOnere').each(function(idx,e) {
					var element=$(e);

					element.hide();

					if(element.html()==='')
						element.parent().find('a').hide();
				});

				$('.helpNoteOnere').click(function(e) {
					$('#testoNote').html($(this).parent().find('.noteOnere').html());
					$('#testoNote').dialog('open');
					e.preventDefault();
				});
			});
		});
	</script>
	<div class="inputForm">
		<fieldset>
			<table id="tabellaEndo">
				<uc1:GrigliaOneri runat="Server" ID="grigliaOneriIntervento" />
				<uc1:GrigliaOneri runat="Server" ID="grigliaOneriEndo" />
				<tr class="rigaTotaleImporti">
					<td colspan="6">
						<b>TOTALE DA PAGARE</b>
					</td>
					<td>
						€ <span id="totaleDaPagare"></span>
					</td>
				</tr>
			</table>
		</fieldset>
		<div id="testoNote">
		</div>
		
		<fieldset id="dichiarazioneAssenzaOneri">
			<div>
				<asp:CheckBox runat="server" ID="chkAssenzaOneri" OnCheckedChanged="chkAssenzaOneri_CheckedChanged" />
				<span style="font-weight: bold">
					<%= TestoDichiarazioneAssenzaOneri%></span>
			</div>
		</fieldset>
		
		<fieldset class="blocco aperto" id="bloccoCaricamentoBollettino">
			<legend>
				<%=TitoloCaricamentoBollettino %></legend>
			<asp:MultiView runat="server" ID="mvCaricamentoBollettino" ActiveViewIndex="0">
				<asp:View runat="server" ID="uploadView">
					<div class="descrizioneStep">
						<%=DescrizioneCaricamentoBollettino %>
					</div>
					<div>
						<asp:Label runat="server" ID="lblUploadFile" Text="Seleziona il file da allegare:"
							AssociatedControlID="fuCaricaFile" />
						<asp:FileUpload runat="server" ID="fuCaricaFile" />
					</div>
					<div class="bottoni">
						<asp:Button runat="server" ID="cmdUpload" Text="Allega file" OnClick="cmdUpload_Click" />
					</div>
				</asp:View>
				<asp:View runat="server" ID="dettagliouploadView">
					<div class="descrizioneStep">
						<%=DescrizioneCaricamentoBollettinoEffettuato %>
					</div>
					<div>
						<asp:Label runat="server" ID="lblFileCaricato" Text="File allegato:" AssociatedControlID="hlFileCaricato" />
						<asp:HyperLink runat="server" ID="hlFileCaricato" />
						<asp:Label runat="server" ID="lblErroreFirma" CssClass="errori">
							&nbsp;Attenzione, il file non è stato firmato digitalmente
						</asp:Label>
					</div>
					<div class="bottoni">
						<asp:Button runat="server" ID="cmdFirma" Text="Firma" OnClick="cmdFirma_Click" />
						<asp:Button runat="server" ID="cmdRimuovi" Text="Rimuovi file" OnClick="cmdRimuovi_Click"
							OnClientClick="return confirm('Rimuovere il file selezionato\?')" />
					</div>
				</asp:View>
			</asp:MultiView>
		</fieldset>
	</div>
</asp:Content>
