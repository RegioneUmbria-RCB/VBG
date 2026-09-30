<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" EnableEventValidation="false" Codebehind="ModificaDatiUtente.aspx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.ModificaDatiUtente" Title="Modifica dati anagrafici" %>

<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="cc3" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

	<script type="text/javascript" src='<%= ResolveClientUrl("~/Scripts/RicercaComuni.js")%>'></script>

	<script type="text/javascript">
		$(function () {
			var txtComuneNascita = $('#<%= txtComuneNascita.ClientID %>');
			var hidComuneNascita = $('#<%= hidComuneNascita.ClientID%>');
			var urlRicerca = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune';
			var urlRicercaProv = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaProvincia';

			RicercaComuni('<%= IdComune %>', txtComuneNascita, hidComuneNascita, urlRicerca);

			var txtComuneCorrispondenza = $('#<%= txtComuneCorrispondenza.ClientID %>');
			var hidComuneCorrispondenza = $('#<%= hidComuneCorrispondenza.ClientID%>');

			RicercaComuni('<%= IdComune %>', txtComuneCorrispondenza, hidComuneCorrispondenza, urlRicerca);

			var txtComuneResidenza = $('#<%= txtComuneResidenza.ClientID %>');
			var hidComuneResidenza = $('#<%= hidComuneResidenza.ClientID%>');

			RicercaComuni('<%= IdComune %>', txtComuneResidenza, hidComuneResidenza, urlRicerca);
		
		})
	
	</script>


	<asp:ScriptManager ID="ScriptManager1" runat="server">
	</asp:ScriptManager>
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="viewModifica">
			<div class="inputForm">
				<fieldset>
					<legend>Dati del soggetto</legend>
					<div>
						<asp:Label runat="server" ID="lbl1" AssociatedControlID="TITOLO" Text="Titolo" />
						<cc1:ComboTitoli ID="TITOLO" runat="server">
						</cc1:ComboTitoli>
						<asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="TITOLO" ErrorMessage="*" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label5" AssociatedControlID="NOMINATIVO" Text="Cognome" />
						<asp:TextBox ID="NOMINATIVO" runat="server" MaxLength="180" Columns="28" />
						<asp:RequiredFieldValidator ID="CognomeValidator" runat="server" ControlToValidate="NOMINATIVO" ErrorMessage="*" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label6" AssociatedControlID="NOME" Text="Nome" />
						<asp:TextBox ID="NOME" runat="server" MaxLength="30" Columns="28" /><asp:RequiredFieldValidator ID="NomeValidator" runat="server" ControlToValidate="NOME"
							ErrorMessage="*" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label7" AssociatedControlID="sesso" Text="Sesso" />
						<asp:DropDownList ID="sesso" runat="server">
							<asp:ListItem Value="">Selezionare...</asp:ListItem>
							<asp:ListItem Value="M">Maschio</asp:ListItem>
							<asp:ListItem Value="F">Femmina</asp:ListItem>
						</asp:DropDownList>
						<asp:RequiredFieldValidator ID="SessoValidator" runat="server" ControlToValidate="sesso" ErrorMessage="*" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label8" AssociatedControlID="CodiceCittadinanza" Text="Cittadinanza" />
						<cc1:ComboCittadinanza ID="CodiceCittadinanza" runat="server" />
						<asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ControlToValidate="CodiceCittadinanza" ErrorMessage="*" />
					</div>
					<legend>Residenza</legend>
					<div>
						<asp:Label runat="server" ID="lblComuneResidenza" Text="Comune" AssociatedControlID="txtComuneResidenza" />
						<asp:HiddenField runat="server" ID="hidComuneResidenza" />
						<asp:TextBox runat="server" ID="txtComuneResidenza" Columns="50" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label12" AssociatedControlID="Indirizzo" Text="Indirizzo" />
						<asp:TextBox ID="Indirizzo" runat="server" MaxLength="100" Columns="60" />
						<asp:RequiredFieldValidator ID="RequiredFieldValidator9" runat="server" ControlToValidate="Indirizzo" ErrorMessage="*" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label13" AssociatedControlID="Citta" Text="Località" />
						<asp:TextBox ID="Citta" runat="server" MaxLength="100" Columns="60" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label14" AssociatedControlID="Cap" Text="Cap" />
						<asp:TextBox ID="Cap" runat="server" MaxLength="5" Columns="15" />
						<asp:RequiredFieldValidator ID="RequiredFieldValidator11" runat="server" ControlToValidate="Cap" ErrorMessage="*" />
					</div>
					<legend>Indirizzo per la corrispondenza</legend>
					<div>
						<asp:Button ID="cmdCopiaResidenza" runat="server" Text="Copia dati residenza" CausesValidation="False" OnClick="cmdCopiaResidenza_Click" />
					</div>

					<div>
						<asp:Label runat="server" ID="lblComuneCorrispondenza" AssociatedControlID="txtComuneCorrispondenza"
							Text="Comune" />
						<asp:HiddenField runat="server" ID="hidComuneCorrispondenza" />
						<asp:TextBox runat="server" ID="txtComuneCorrispondenza" Columns="50" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label20" AssociatedControlID="IndirizzoCorrispondenza" Text="Indirizzo" />
						<asp:TextBox ID="IndirizzoCorrispondenza" runat="server" MaxLength="100" Columns="60" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label21" AssociatedControlID="CittaCorrispondenza" Text="Località" />
						<asp:TextBox ID="CittaCorrispondenza" runat="server" MaxLength="50" Columns="60" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label22" AssociatedControlID="CapCorrispondenza" Text="Cap" />
						<asp:TextBox ID="CapCorrispondenza" runat="server" MaxLength="5" Columns="15" />
					</div>
					<legend>Dati di nascita e codice fiscale</legend>
					<div>
						<asp:Label runat="server" ID="Label15" AssociatedControlID="DataNascita" Text="Data di nascita" />
						<cc3:DateTextBox ID="DataNascita" runat="server" />
						<asp:RequiredFieldValidator ID="RequiredFieldValidator12" runat="server" ErrorMessage="*" ControlToValidate="DataNascita" />
					</div>
					<div>
						<asp:Label runat="server" ID="lblComuneNascita2" AssociatedControlID="txtComuneNascita" Text="Comune" />
						<asp:HiddenField runat="server" ID="hidComuneNascita" />
						<asp:TextBox runat="server" ID="txtComuneNascita" Columns="40"></asp:TextBox>
						<asp:RequiredFieldValidator ID="valComuneNascita" runat="server" ControlToValidate="txtComuneNascita" ErrorMessage="*" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label17" AssociatedControlID="CodiceFiscale" Text="Cod.Fis." />
						<asp:TextBox ID="CodiceFiscale" runat="server" MaxLength="16" Columns="25" />
						<asp:Button ID="cmdCalcolaCF" runat="server" Text="Calcola" CausesValidation="False" Visible="False"></asp:Button>
						<asp:RequiredFieldValidator ID="CodiceFilscaleValidator" runat="server" ControlToValidate="CodiceFiscale" ErrorMessage="*" />
					</div>
					<legend>Altri dati</legend>
					<div>
						<asp:Label runat="server" ID="Label18" AssociatedControlID="Telefono" Text="Telefono" />
						<asp:TextBox ID="Telefono" runat="server" MaxLength="15" Columns="25" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label19" AssociatedControlID="TelefonoCellulare" Text="Cellulare" />
						<asp:TextBox ID="TelefonoCellulare" runat="server" MaxLength="15" Columns="25" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label4" AssociatedControlID="Fax" Text="Fax" />
						<asp:TextBox ID="Fax" runat="server" MaxLength="15" Columns="25" />
					</div>
					<div>
						<asp:Label runat="server" ID="Label23" AssociatedControlID="email" Text="E-mail" />
						<asp:TextBox ID="email" runat="server" MaxLength="70" Columns="40" />
					</div>
					<div class="bottoni">
						<asp:Button ID="cmdConfirm" runat="server" Text="Conferma" OnClick="cmdConfirm_Click" />
						<asp:Button ID="cmdCancel" runat="server" Text="Annulla" CausesValidation="False" OnClick="cmdCancel_Click" />
					</div>
				</fieldset>
			</div>
		</asp:View>
		<asp:View runat="server" ID="view1">
			<div class="inputForm">
				<fieldset>
					<div>
						<span class="SectionText">La richiesta di modifica dati è stata inviata correttamente<br /></span>
					</div>
					<%--<div style="width: 100%">
						<asp:Button ID="cmdBackToHome" runat="server" Text="Torna alla home" OnClick="cmdBackToHome_Click"></asp:Button>
					</div>--%>
				</fieldset>
			</div>
		</asp:View>
	</asp:MultiView>
</asp:Content>
