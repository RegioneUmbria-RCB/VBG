<%@ Page Title="Firma documento" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master"
	AutoEventWireup="true" CodeBehind="FirmaDocumento.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.FirmaDigitale.FirmaDocumento" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<script type="text/javascript">
		function getListaCodiciOggetto() {
			return '<%=CodiceOggetto %>';
		}

		function setEsitoFirma(codiceoggetto, codice, messaggio) {
			if (!codice || codice.toUpperCase() == "KO") {
				firmaFallita();
				return;
			}

			mostraPostFirma();

			document.forms[0].hidEsito.value = codice.toUpperCase();
			__doPostBack('hidEsito', '');
		}

		function mostraPostFirma() {
			$('#appletFirma').fadeOut('fast', function () {
				$('#postFirma').fadeIn('fast');
			});
		}

		function firmaFallita() {
			alert('Firma fallita');
		}
	</script>
	<fieldset>
		<div>
			<label for='<%= fileDaFirmare.ClientID %>'>
				File da firmare digitalmente:</label>
			<asp:HyperLink runat="server" ID="fileDaFirmare" Target="_blank" />
		</div>

		<asp:HiddenField runat="server" Value="" ID="hidEsito" ClientIDMode="Static" OnValueChanged="hidEsito_ValueChanged" />

		<div style="height: 55px" id="appletFirma">
			<script type="text/javascript" src="applets/deployJava.js"></script>
			<script type="text/javascript">
				var attributes = { width: '100%', height: '100%' };
				var parameters = {
					dataUrl: '<%= UrlJspFirmaDigitale %>?Token=<%=TokenApplicazione%>',
					jnlp_href: 'applets/simple_sign_app.jnlp'
				};
				var version = '1.6';
				deployJava.runApplet(attributes, parameters, version);
			</script>

			<div class="bottoni">
				<asp:Button runat="server" ID="cmdChiudi" Text="Chiudi" OnClick="cmdChiudi_Click" />
			</div>
		</div>

		<div style="display: none; width:100%; height:70px; text-align:center; padding-top: 50px;" id="postFirma">
			<i>Attendere prego, elaborazione del documento in corso...</i>
		</div>
	</fieldset>
</asp:Content>
