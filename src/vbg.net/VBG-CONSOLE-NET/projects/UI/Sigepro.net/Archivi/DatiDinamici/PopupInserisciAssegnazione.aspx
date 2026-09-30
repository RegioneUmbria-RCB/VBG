<%@ Page Title="Inserisci formula assegnazione" Language="C#" MasterPageFile="~/SigeproNetMaster.master"
	AutoEventWireup="true" CodeBehind="PopupInserisciAssegnazione.aspx.cs" Inherits="Sigepro.net.Archivi.DatiDinamici.PopupInserisciAssegnazione" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
	<script type="text/javascript">
		function safeName(name) {
			var caratteriNonValidi

			return name.replace(/[^A-Za-z0-9_]/g,'_');
		}

		function inserisciFormula() {

			var rVal='';

			$('.checkCampi')
				.each(function(index,item) {

					if($(item).find('input').is(':checked')) {
						var nomeCampo=$(item).data('val');
						rVal=rVal.concat('var ',safeName(nomeCampo),' = TrovaCampo(\"',nomeCampo,'\");\r\n');
					}
				});

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
						<asp:CheckBox runat="server" ID="chkSeleziona" data-val='<%# Eval("CampoDinamico") %>'
							CssClass="checkCampi" />
					</td>
					<td>
						[
						<asp:Label runat="server" ID="Label1" Text='<%# Eval("Posverticale") %>' /> / 
						<asp:Label runat="server" ID="Label2" Text='<%# Eval("Posorizzontale") %>' />
						]
						<asp:Label runat="server" ID="lblCampo" Text='<%# Eval("CampoDinamico") %>' />
					</td>
				</tr>
			</ItemTemplate>
			<FooterTemplate>
				</table>
			</FooterTemplate>
		</asp:Repeater>
		<div class="bottoni">
			<asp:Button runat="server" ID="cmdInserisci" Text="Inserisci formula" OnClientClick="return inserisciFormula()" />
		</div>
	</fieldset>
</asp:Content>
