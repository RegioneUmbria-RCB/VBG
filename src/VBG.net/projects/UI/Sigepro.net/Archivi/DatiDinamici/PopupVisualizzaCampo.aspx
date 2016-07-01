<%@ Page Title="Mostra/Visualizza campi" Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="PopupVisualizzaCampo.aspx.cs" Inherits="Sigepro.net.Archivi.DatiDinamici.PopupVisualizzaCampo" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">

	<script type="text/javascript">
		function safeName(name) {
			var caratteriNonValidi

			return name.replace(/[^A-Za-z0-9_]/g,'_');
		}

		function mostraCampi(mostra) {

			var rVal = 'var campi = new []{';

			$('.checkCampi')
				.each(function(index,item) {

					if($(item).find('input').is(':checked')) {
						var nomeCampo=$(item).data('val');

						<%if (!CampiStatici) {%>
							nomeCampo = "\"" + nomeCampo.toString() + "\"";
						<%} %>
						
						rVal=rVal.concat('\r\n', nomeCampo,',');
					}
				});

			<% if (CampiStatici) {%>
				var funzioneMostra = "MostraCampiTestuali";
				var funzioneNascondi = "NascondiCampiTestuali";
			<%} else { %>
				var funzioneMostra = "MostraCampiDinamici";
				var funzioneNascondi = "NascondiCampiDinamici";
			<% } %>

			rVal = rVal.concat("\r\n};\r\n\r\n" , mostra ? funzioneMostra : funzioneNascondi , '(campi, 0);' );

			window.opener.insertText(rVal);

			self.close();

			return false;
		}
	</script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<fieldset>
		<legend>Seleziona i campi</legend>
		<asp:Repeater runat="server" ID="rptListaCampi">
			<HeaderTemplate>
				<table>
			</HeaderTemplate>
			<ItemTemplate>
				<tr>
					<td style="width: 16px">
						<asp:CheckBox runat="server" ID="chkSeleziona" data-val='<%# Eval("Key") %>'
							CssClass="checkCampi" />
					</td>
					<td>
						[
						<asp:Label runat="server" ID="Label1" Text='<%# Eval("PosVerticale") %>' /> / 
						<asp:Label runat="server" ID="Label2" Text='<%# Eval("PosOrizzontale") %>' />
						]
						<asp:Label runat="server" ID="lblCampo" Text='<%# Eval("Value") %>' />
					</td>
				</tr>
			</ItemTemplate>
			<FooterTemplate>
				</table>
			</FooterTemplate>
		</asp:Repeater>
		<div class="bottoni">
			<asp:Button runat="server" ID="cmdInserisci" Text="Visualizza campi" OnClientClick="return mostraCampi(true)" />
			<asp:Button runat="server" ID="Button1" Text="Nascondi campi" OnClientClick="return mostraCampi(false)" />
		</div>
	</fieldset>
</asp:Content>
