<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeBehind="DettagliAnagraficaPf.ascx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DettagliAnagraficaPf" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Common"
	Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="cc3" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<input type="hidden" runat="server" id="ANAGRAFE_PK" />
<style>
    .form-hide {
        display:none;
    }

</style>
<script type="text/javascript">
	require(['jquery', 'app/ricercaComuni', 'app/ricercaProvincia', 'app/verificaCf', 'jquery.ui'], function ($, RicercaComuni, RicercaProvincia, VerificaCf) {

		$(function () {
			verificaTipoSoggetto();

			$('#<%= TipoSoggetto.ClientID%>').change(function (e) {
				e.preventDefault();
				verificaTipoSoggetto();
			});

			// Inizializzo i campi di ricerca dei comuni/provincie
			var txtComuneNascita = $('#<%= txtComuneNascita.ClientID %>');
			var hidComuneNascita = $('#<%= hidComuneNascita.ClientID%>');
			var urlRicerca		= '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune';
			var urlRicercaProv	= '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaProvincia';

			new RicercaComuni('<%= IdComune %>', txtComuneNascita, hidComuneNascita, urlRicerca);

			var txtComuneCorrispondenza = $('#<%= txtComuneCorrispondenza.ClientID %>');
			var hidComuneCorrispondenza = $('#<%= hidComuneCorrispondenza.ClientID%>');

			new RicercaComuni('<%= IdComune %>', txtComuneCorrispondenza, hidComuneCorrispondenza, urlRicerca);

			var txtComuneResidenza = $('#<%= txtComuneResidenza.ClientID %>');
			var hidComuneResidenza = $('#<%= hidComuneResidenza.ClientID%>');

			new RicercaComuni('<%= IdComune %>', txtComuneResidenza, hidComuneResidenza, urlRicerca);

			var hidProvinciaAlbo = $('#<%= hidProvinciaAlbo.ClientID %>');
			var txtProvinciaAlbo = $('#<%= txtProvinciaAlbo.ClientID%>');

			new RicercaProvincia('<%= IdComune %>', txtProvinciaAlbo, hidProvinciaAlbo, urlRicercaProv);

			new VerificaCf({
				txtNome: $('#<%=NOME.ClientID %>'),
				txtCognome: $('#<%=NOMINATIVO.ClientID %>'),
				txtDataNascita: $('#<%=DataNascita.ClientID %>'),
				txtSesso: $('#<%=sesso.ClientID %>'),
				txtComuneNascita: $('#<%=hidComuneNascita.ClientID %>'),
				txtCodiceFiscale: $('#<%=CodiceFiscale.ClientID %>'),
				bottoneInvio: $('#<%=cmdConfirm.ClientID %>'),
				urlVerifica: '<%=ResolveClientUrl("~/Public/WebServices/ValidazioneCfService.asmx") %>/ValidaCF',
				divErrori: $('#alertValidazioneCf')
			});
		});

		function verificaTipoSoggetto() {
			$.ajax({
				url: '<%= ResolveClientUrl("~/Public/WebServices/TipiSoggettoJsService.asmx")%>/GetById?idComune=<%=IdComune %>&Software=<%=Software %>',
				type: "POST",
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				data: "{'idSoggetto':'" + $('#<%= TipoSoggetto.ClientID%>').val() + "'}",
				success: function (data) {
					var specificaDescrizione = data.d && data.d.RichiedeDescrizioneEstesa;
					var mostraDatiAlbo = data.d && data.d.RichiedeDatiAlbo;

					$('#descrizioneEstesa').css('display', specificaDescrizione ? 'inline' : 'none');
					if (!specificaDescrizione)
						$('#<%=txtDescrizioneEstesa.ClientID %>').val('');


					$('#datiAlbo').css('display', mostraDatiAlbo ? 'inline' : 'none');
					if (!mostraDatiAlbo) {
						$('#<%=ddlAlbo.ClientID %>').val('');
						$('#<%=txtNumeroAlbo.ClientID %>').val('');
						$('#<%=hidProvinciaAlbo.ClientID %>').val('');
						$('#<%=txtProvinciaAlbo.ClientID %>').val('');
					}
				}
			});
		}
	});
</script>
<div class="inputForm">

    <fieldset class="note" style="font-style:italic">
        I campi contrassegnati con <sup>*</sup> sono obbligatori
    </fieldset>

	<fieldset>
		<legend>Tipo soggetto</legend>
		<div>
			<label>In qualità di <sup>*</sup></label>
			<cc1:ComboTipiSoggetto ID="TipoSoggetto" runat="server" TipoSoggetto="F" />
			<asp:RequiredFieldValidator ID="TipoSoggettoValidator" runat="server" ControlToValidate="TipoSoggetto"
				ErrorMessage="Campo obbligatorio" Display="Dynamic" /><br />
		</div>

		<div id="descrizioneEstesa">
			<label>Specificare <sup>*</sup></label>
			<asp:TextBox runat="server" ID="txtDescrizioneEstesa" MaxLength="128" Columns="80" />
		</div>
	</fieldset>

	<fieldset>
		<legend>Dati del soggetto</legend>
		
        <div>
			<asp:Label runat="server" ID="lbl1" AssociatedControlID="TITOLO" Text="Titolo" />
			<cc1:ComboTitoli ID="TITOLO" runat="server">
			</cc1:ComboTitoli>
		</div>

		<div>
			<label>Cognome <sup>*</sup></label>
			<asp:TextBox ID="NOMINATIVO" runat="server" MaxLength="180" Columns="28" />
			<asp:RequiredFieldValidator ID="CognomeValidator" runat="server" ControlToValidate="NOMINATIVO"
				ErrorMessage="Campo obbligatorio" />
		</div>

		<div>
			<label>Nome <sup>*</sup></label>
			<asp:TextBox ID="NOME" runat="server" MaxLength="30" Columns="28" />
            <asp:RequiredFieldValidator ID="NomeValidator" runat="server" ControlToValidate="NOME" 
                ErrorMessage="Campo obbligatorio" />
		</div>

		<div>
			<label>Sesso <sup>*</sup></label>
			<asp:DropDownList ID="sesso" runat="server">
				<asp:ListItem Value="">Selezionare...</asp:ListItem>
				<asp:ListItem Value="M">Maschio</asp:ListItem>
				<asp:ListItem Value="F">Femmina</asp:ListItem>
			</asp:DropDownList>
			<asp:RequiredFieldValidator ID="SessoValidator" runat="server" ControlToValidate="sesso"
				ErrorMessage="Campo obbligatorio" />
		</div>

		<div class='<%=ClasseCampo(CittadinanzaVisible)%>'>
			<%=Label("Cittadinanza", CittadinanzaObbligatoria) %>
			<cc1:ComboCittadinanza ID="CodiceCittadinanza" runat="server" />
			<asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ControlToValidate="CodiceCittadinanza"
				ErrorMessage="Campo obbligatorio" Enabled='<%#CittadinanzaObbligatoria %>' />
		</div>
	</fieldset>

    <fieldset>
		<legend>Dati di nascita e codice fiscale</legend>
		<div>
			<label>Data di nascita <sup>*</sup></label>
			<cc3:DateTextBox ID="DataNascita" runat="server" />
			<asp:RequiredFieldValidator ID="RequiredFieldValidator12" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="DataNascita" />
		</div>
		<div>
			<label>Comune <sup>*</sup></label>
			<asp:HiddenField runat="server" ID="hidComuneNascita" />
			<asp:TextBox runat="server" ID="txtComuneNascita" Columns="40"></asp:TextBox>
			<span style="font-style: italic">Per i nati all'estero indicare il nome dello stato
				di nascita</span>
			<asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ControlToValidate="txtComuneNascita"
				ErrorMessage="Campo obbligatorio" />
		</div>
		<div>
			<label>Codice fiscale <sup>*</sup></label>
			<asp:TextBox ID="CodiceFiscale" runat="server" MaxLength="32" Columns="25" ReadOnly="true" />
			<asp:Button ID="cmdCalcolaCF" runat="server" Text="Calcola" CausesValidation="False"
				Visible="False"></asp:Button>
			<asp:RequiredFieldValidator ID="CodiceFilscaleValidator" runat="server" ControlToValidate="CodiceFiscale"
				ErrorMessage="Campo obbligatorio" />
		</div>
	</fieldset>

	<fieldset class='<%=ClasseCampo(ResidenzaVisible)%>'>
		<legend>Residenza</legend>
		<div>
			<%=Label("Comune", ResidenzaObbligatoria) %>
			<asp:HiddenField runat="server" ID="hidComuneResidenza" />
			<asp:TextBox runat="server" ID="txtComuneResidenza" Columns="50" />
			<asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="txtComuneResidenza"
				ErrorMessage="Campo obbligatorio" Visible='<%#ResidenzaObbligatoria %>' />
		</div>
		<div>
			<%=Label("Indirizzo", ResidenzaObbligatoria) %>
			<asp:TextBox ID="Indirizzo" runat="server" MaxLength="100" Columns="60" />
			<asp:RequiredFieldValidator ID="RequiredFieldValidator9" runat="server" ControlToValidate="Indirizzo"
				ErrorMessage="Campo obbligatorio" Enabled='<%#ResidenzaObbligatoria %>' />
		</div>
		<div>
			<label>Località/Città</label>
			<asp:TextBox ID="Citta" runat="server" MaxLength="100" Columns="60" />
		</div>
		<div>
			<%=Label("Cap", ResidenzaObbligatoria) %>
			<asp:TextBox ID="Cap" runat="server" MaxLength="5" Columns="15" />
			<asp:RequiredFieldValidator ID="RequiredFieldValidator11" runat="server" ControlToValidate="Cap"
				ErrorMessage="Campo obbligatorio" Enabled='<%#ResidenzaObbligatoria %>' />
		</div>
	</fieldset>


	

	<div id="datiAlbo">
		<fieldset>
			<legend>Dati elenco professionale</legend>
			<div>
				<label>Elenco professionale <sup>*</sup></label>
				<cc1:ComboElenchiProfessionali runat="server" ID="ddlAlbo" />
			</div>
			<div>
				<label>Numero iscrizione <sup>*</sup></label>
				<asp:TextBox runat="server" ID="txtNumeroAlbo"></asp:TextBox>
			</div>
			<div>
				<label>Provincia <sup>*</sup></label>
				<asp:HiddenField runat="server" ID="hidProvinciaAlbo" />
				<asp:TextBox runat="server" ID="txtProvinciaAlbo" Columns="40" />
			</div>
		</fieldset>
	</div>


	<fieldset class='<%=ClasseCampo(TelefonoVisible || CellulareVisible || FaxVisible || EmailVisible || PecVisible ) %>'>
		<legend>Altri dati</legend>
		<div class='<%=ClasseCampo(TelefonoVisible) %>'>
			<%=Label("Telefono", TelefonoObbligatorio) %>
			<asp:TextBox ID="Telefono" runat="server" MaxLength="15" Columns="25" />
            <asp:RequiredFieldValidator runat="server" ControlToValidate="Telefono" Enabled='<%#TelefonoObbligatorio %>' ErrorMessage="Campo obbligatorio"/>
		</div>
		
        <div class='<%=ClasseCampo(CellulareVisible) %>'>
			<%=Label("Cellulare", CellulareObbligatorio) %>
			<asp:TextBox ID="TelefonoCellulare" runat="server" MaxLength="15" Columns="25" />
            <asp:RequiredFieldValidator runat="server" ControlToValidate="TelefonoCellulare" Enabled='<%#CellulareObbligatorio %>' ErrorMessage="Campo obbligatorio"/>
		</div>
		
        <div class='<%=ClasseCampo(FaxVisible) %>'>
			<%=Label("Fax", FaxObbligatorio) %>
			<asp:TextBox ID="Fax" runat="server" MaxLength="15" Columns="25" />
            <asp:RequiredFieldValidator runat="server" ControlToValidate="Fax" Enabled='<%#FaxObbligatorio %>' ErrorMessage="Campo obbligatorio"/>
		</div>

		<div class='<%=ClasseCampo(EmailVisible) %>'>
			<%=Label("E-Mail", EmailObbligatoria) %>
			<asp:TextBox ID="email" runat="server" MaxLength="70" Columns="40" />
            <asp:RequiredFieldValidator runat="server" ControlToValidate="email" Enabled='<%#EmailObbligatoria %>' ErrorMessage="Campo obbligatorio"/>
		</div>

		<div class='<%=ClasseCampo(PecVisible) %>'>
			<%=Label("Pec", PecObbligatoria) %>
			<asp:TextBox ID="emailPec" runat="server" MaxLength="70" Columns="40" />
            <asp:RequiredFieldValidator runat="server" ControlToValidate="emailPec" Enabled='<%#PecObbligatoria %>' ErrorMessage="Campo obbligatorio"/>
		</div>
	</fieldset>
	
	<fieldset>
		<legend>Indirizzo per la corrispondenza</legend>
		<div class='bottoni <%=ClasseCampo(ResidenzaVisible) %>'>
			<asp:Button ID="cmdCopiaResidenza" runat="server" Text="Copia da indirizzo di residenza"
				CausesValidation="False" OnClick="cmdCopiaResidenza_Click" />
		</div>

		<div>
			<asp:Label runat="server" ID="lblComuneCorrispondenza" AssociatedControlID="txtComuneCorrispondenza"
				Text="Comune" />
			<asp:HiddenField runat="server" ID="hidComuneCorrispondenza" />
			<asp:TextBox runat="server" ID="txtComuneCorrispondenza" Columns="50" />
		</div>

		<div>
			<asp:Label runat="server" ID="Label20" AssociatedControlID="IndirizzoCorrispondenza"
				Text="Indirizzo" />
			<asp:TextBox ID="IndirizzoCorrispondenza" runat="server" MaxLength="100" Columns="60" />
		</div>

		<div>
			<asp:Label runat="server" ID="Label21" AssociatedControlID="CittaCorrispondenza"
				Text="Località" />
			<asp:TextBox ID="CittaCorrispondenza" runat="server" MaxLength="50" Columns="60" />
		</div>

		<div>
			<asp:Label runat="server" ID="Label22" AssociatedControlID="CapCorrispondenza" Text="Cap" />
			<asp:TextBox ID="CapCorrispondenza" runat="server" MaxLength="5" Columns="15" />
		</div>

		<div class="bottoni">
			<asp:Button ID="cmdConfirm" runat="server" Text="Conferma" OnClick="cmdConfirm_Click" CausesValidation="true" />
			<asp:Button ID="cmdCancel" runat="server" Text="Annulla" CausesValidation="False"
				OnClick="cmdCancel_Click" />
		</div>
	</fieldset>
	<div id='alertValidazioneCf' >&nbsp;</div>
</div>
