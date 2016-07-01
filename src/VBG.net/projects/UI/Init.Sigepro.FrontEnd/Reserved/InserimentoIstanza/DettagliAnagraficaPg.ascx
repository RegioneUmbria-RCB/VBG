<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="DettagliAnagraficaPg.ascx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DettagliAnagraficaPg" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Common"
	Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="cc3" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>

<style>
    .form-hide {
        display:none;
        /*border: 1px solid #f00;*/
    }

</style>

<script type="text/javascript">
    require(['jquery', 'app/ricercacomuni', 'app/ricercaprovincia', 'app/RicercaGenerica', 'jquery.ui'], function ($, RicercaComuni, RicercaProvincia, RicercaGenerica) {
		$(function () {
			verificaTipoSoggetto();

			$('#<%= TipoSoggetto.ClientID%>').change(function (e) {
				e.preventDefault();
				verificaTipoSoggetto();
			});

		    var hidComuneSL = $('#<%= hidComuneSL.ClientID %>'),
                txtComuneSL = $('#<%= txtComuneSL.ClientID %>'),
                urlRicercaComune = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune',
		        urlRicercaProv = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaProvincia';

		    RicercaComuni('<%= IdComune %>', txtComuneSL, hidComuneSL, urlRicercaComune);

			var txtComuneCorrispondenza = $('#<%= txtComuneCorrispondenza.ClientID %>');
			var hidComuneCorrispondenza = $('#<%= hidComuneCorrispondenza.ClientID%>');

		    RicercaComuni('<%= IdComune %>', txtComuneCorrispondenza, hidComuneCorrispondenza, urlRicercaComune);

			var hidComuneCciaa = $('#<%= hidProvinciaCciaa.ClientID %>');
			var txtComuneCciaa = $('#<%= txtProvinciaCciaa.ClientID%>');

		    RicercaComuni('<%= IdComune %>', txtComuneCciaa, hidComuneCciaa, urlRicercaComune);

			var hidComuneRegTrib = $('#<%= hidComuneRegTrib.ClientID %>');
			var txtComuneRegTrib = $('#<%= txtComuneRegTrib.ClientID%>');

		    RicercaComuni('<%= IdComune %>', txtComuneRegTrib, hidComuneRegTrib, urlRicercaComune);

			var hidProvinciaRea = $('#<%= hidProvinciaRea.ClientID %>');
			var txtProvinciaRea = $('#<%= txtProvinciaRea.ClientID%>');

			RicercaProvincia('<%= IdComune %>', txtProvinciaRea, hidProvinciaRea, urlRicercaProv);

		    var txtSedeInps = $('#<%= txtSedeInps.ClientID%>'),
                hidSedeInps = $('#<%= hidSedeInps.ClientID%>'),
                txtSedeInail = $('#<%= txtSedeInail.ClientID%>'),
                hidSedeInail = $('#<%= hidSedeInail.ClientID%>'),
                urlRicercaInpsInailBase = '<%=ResolveClientUrl("~/Reserved/InserimentoIstanza/HelperGestioneInpsInail/InpsInailjsService.asmx")%>',
                argsRicercaInpsInail = 'software=TT&idcomune=<%=Server.UrlEncode(IdComune)%>&token=<%=Server.UrlEncode(Token)%>'
                urlRicercaInps = urlRicercaInpsInailBase + '/ElencoSediInps?' + argsRicercaInpsInail,
		        urlRicercaInail = urlRicercaInpsInailBase + '/ElencoSediInail?' + argsRicercaInpsInail;

		    RicercaGenerica('<%= IdComune %>', txtSedeInps, hidSedeInps, urlRicercaInps);
		    RicercaGenerica('<%= IdComune %>', txtSedeInail, hidSedeInail, urlRicercaInail);

			preparaValidazioneCodiceFiscale();
		});

		function verificaTipoSoggetto() {
			$.ajax({
				url: '<%= ResolveClientUrl("~/Public/WebServices/TipiSoggettoJsService.asmx")%>/RichiedeDescrizioneEstesa?idComune=<%=IdComune %>&Software=<%= Software %>',
				type: "POST",
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				data: "{'idSoggetto':'" + $('#<%= TipoSoggetto.ClientID%>').val() + "'}",
				success: function (data) {
					$('#<%=pnlDescrizioneEstesa.ClientID%>').css('display', data.d ? 'inline' : 'none');
					if (!data.d)
						$('#<%=txtDescrizioneEstesa.ClientID %>').val('');
				}
			});
		}

		function preparaValidazioneCodiceFiscale() {
			var codiceFiscale = $('#<%=CodiceFiscale.ClientID %>');
			var partitaIva = $('#<%=PartitaIva.ClientID %>');

			$('.validazioneCodiceFiscale').css('display', 'none');

			codiceFiscale.change(function () { doFormValidation(); });
			partitaIva.change(function () { doFormValidation(); });
		}

		function doFormValidation() {
			var codiceFiscale = $('#<%=CodiceFiscale.ClientID %>');
			var partitaIva = $('#<%=PartitaIva.ClientID %>');

			var validazioneFallita = codiceFiscale.val().length == 0 && partitaIva.val().length == 0;

			$('.validazioneCodiceFiscale').css('display', validazioneFallita ? 'inline' : 'none');

			return !validazioneFallita;
		}

		window.doFormValidation = doFormValidation;
	});
</script>
<input type="hidden" runat="server" id="ANAGRAFE_PK" name="ANAGRAFE_PK" />
<div class="inputForm">
	<fieldset>
		<legend>Tipo soggetto</legend>
		<div>
			<asp:Label runat="server" ID="label5" AssociatedControlID="TipoSoggetto" Text="In qualità di" />
			<cc1:ComboTipiSoggetto runat="server" ID="TipoSoggetto" TipoSoggetto="G" AutoPostBack="false">
			</cc1:ComboTipiSoggetto>
			<asp:RequiredFieldValidator ID="RequiredFieldValidator3" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="TipoSoggetto" />
		</div>
		<asp:Panel runat="server" ID="pnlDescrizioneEstesa">
			<asp:Label runat="server" ID="lblDescrizioneEstesa" AssociatedControlID="txtDescrizioneEstesa"
				Text="Specificare"></asp:Label>
			<asp:TextBox runat="server" ID="txtDescrizioneEstesa" MaxLength="128" Columns="80" />
		</asp:Panel>
	</fieldset>

	<fieldset>
		<legend>Dati del soggetto</legend>
		<div>
            <%=Label("Ragione sociale", true) %>
			<asp:TextBox runat="server" ID="Nominativo" Columns="60" MaxLength="180" />
			<asp:RequiredFieldValidator runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="Nominativo" />
		</div>
		<div>
            <%=Label("Forma giuridica", true) %>
			<cc1:ComboFormeGiuridiche runat="Server" ID="CodiceFormaGiuridica">
			</cc1:ComboFormeGiuridiche>
			<asp:RequiredFieldValidator runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="CodiceFormaGiuridica" />
		</div>

		<div>
            <%=Label("Codice fiscale impresa", true) %>
			<asp:TextBox ID="CodiceFiscale" runat="server" Columns="25" MaxLength="32" />
			<asp:RequiredFieldValidator ID="RequiredFieldValidator2" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="CodiceFiscale"/>
		</div>

		<div class='<%=ClasseCampo(PartitaIvaVisibile) %>'>
			<%=Label("Partita IVA", PartitaIvaObbligatoria) %>
			<asp:TextBox ID="PartitaIva" runat="server" Columns="25" MaxLength="32" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator25" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="PartitaIva" Enabled='<%#PartitaIvaObbligatoria %>'/>
		</div>

		<div class='<%=ClasseCampo(DataCostituzioneVisibile) %>'>
			<%=Label("Data di costituzione", DataCostituzioneObbligatoria)%>
			<cc3:DateTextBox ID="DataNominativo" runat="server" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator4" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="DataNominativo" Enabled='<%#DataCostituzioneObbligatoria %>'/>
		</div>
	</fieldset>

	<fieldset class='<%=ClasseCampo(SedeLegaleVisibile) %>'>
		<legend>Sede legale</legend>

		<div>
			<%=Label("Comune", SedeLegaleObbligatoria) %>
			<asp:HiddenField runat="server" ID="hidComuneSL" />
			<asp:TextBox runat="server" ID="txtComuneSL" Columns="50" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="txtComuneSL" Enabled='<%#SedeLegaleObbligatoria%>' />
		</div>

		<div>
			<%=Label("Indirizzo", SedeLegaleObbligatoria) %>
			<asp:TextBox ID="Indirizzo" runat="server" Columns="60" MaxLength="100" />
			<asp:RequiredFieldValidator ID="RequiredFieldValidator6" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="Indirizzo" Enabled='<%#SedeLegaleObbligatoria%>'  />
		</div>
		<div>
			<%=Label("Località") %>
			<asp:TextBox ID="Citta" runat="server" Columns="60" MaxLength="100" />
		</div>
		<div>
			<%=Label("Cap", SedeLegaleObbligatoria) %>
			<asp:TextBox ID="Cap" runat="server" Columns="15" MaxLength="5" />
			<asp:RequiredFieldValidator ID="RequiredFieldValidator8" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="Cap"  Enabled='<%#SedeLegaleObbligatoria%>' />
		</div>
	</fieldset>



    <fieldset class='<%=ClasseCampo(CciaaVisibile) %>'>
        <legend>CCIAA</legend>
		<div>
			<%=Label("Numero", CciaaObbligatoria)%>
			<asp:TextBox ID="RegDitte" runat="server" Columns="25" MaxLength="16" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator12" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="RegDitte" Enabled='<%#CciaaObbligatoria %>'/>
		</div>

		<div>
			<%=Label("Data", CciaaObbligatoria)%>
			<cc3:DateTextBox ID="DataRegDitte" runat="server" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator13" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="DataRegDitte" Enabled='<%#CciaaObbligatoria %>'/>
		</div>
		<div>
			<%=Label("Provincia", CciaaObbligatoria)%>
			<asp:HiddenField runat="server" ID="hidProvinciaCciaa" />
			<asp:TextBox runat="server" ID="txtProvinciaCciaa" Columns="50" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator14" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="txtProvinciaCciaa" Enabled='<%#CciaaObbligatoria %>'/>
		</div>
    </fieldset>

    <fieldset  class='<%=ClasseCampo(RegTribVisibile) %>'>
        <legend>Reg. Trib.</legend>
		<div>
			<%=Label("Numero", RegTribObbligatorio)%>
			<asp:TextBox runat="server" ID="RegTrib" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator15" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="RegTrib" Enabled='<%#RegTribObbligatorio %>'/>
		</div>
		<div>
			<%=Label("Data", RegTribObbligatorio)%>
			<cc3:DateTextBox ID="RegTribData" runat="Server" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator16" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="RegTribData" Enabled='<%#RegTribObbligatorio %>'/>
		</div>
		<div>
			<%=Label("Comune", RegTribObbligatorio)%>
			<asp:HiddenField runat="server" ID="hidComuneRegTrib" />
			<asp:TextBox runat="server" ID="txtComuneRegTrib" Columns="50" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator17" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="txtComuneRegTrib" Enabled='<%#RegTribObbligatorio %>'/>
		</div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(ReaVisibile) %>'>

        <legend>REA</legend>

		<div>
            <%=Label("Numero", ReaObbligatoria)%>
			<asp:TextBox runat="server" ID="NumIscrREA" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator19" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="NumIscrREA" Enabled='<%#ReaObbligatoria %>'/>
		</div>

		<div>
			<%=Label("Data", ReaObbligatoria)%>
			<cc3:DateTextBox ID="DataIscrREA" runat="Server"></cc3:DateTextBox>
            <asp:RequiredFieldValidator ID="RequiredFieldValidator20" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="DataIscrREA" Enabled='<%#ReaObbligatoria %>'/>
		</div>

		<div>
			<%=Label("Provincia", ReaObbligatoria)%>
			<asp:HiddenField runat="server" ID="hidProvinciaRea" />
			<asp:TextBox runat="server" ID="txtProvinciaRea" Columns="40" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator18" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="txtProvinciaRea" Enabled='<%#ReaObbligatoria %>'/>
		</div>
	</fieldset>

    <fieldset class='<%=ClasseCampo(InpsVisibile) %>'>
        <legend>INPS</legend>

		<div>
            <%=Label("Numero", InpsObbligatoria)%>
			<asp:TextBox runat="server" ID="txtNumeroInps" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator22" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="txtNumeroInps" Enabled='<%#InpsObbligatoria %>'/>
		</div>

        <div>
            <%=Label("Sede", InpsObbligatoria)%>
            <asp:HiddenField runat="server" ID="hidSedeInps" />
            <asp:TextBox runat="server" ID="txtSedeInps" Columns="40" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator21" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="txtSedeInps" Enabled='<%#InpsObbligatoria %>'/>
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(InailVisibile) %>'>
        <legend>INAIL</legend>

		<div>
            <%=Label("Numero", InailObbligatoria)%>
			<asp:TextBox runat="server" ID="txtNumeroInail" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator23" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="txtNumeroInail" Enabled='<%#InailObbligatoria %>'/>
		</div>

        <div>
            <%=Label("Sede", InailObbligatoria)%>
            <asp:HiddenField runat="server" ID="hidSedeInail" />
            <asp:TextBox runat="server" ID="txtSedeInail" Columns="40" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator24" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="txtSedeInail" Enabled='<%#InailObbligatoria %>'/>
        </div>
    </fieldset>

	<fieldset class='<%=ClasseCampo(TelefonoVisibile || CellulareVisibile || FaxVisibile || EmailVisibile || PecVisibile) %>'>
		<legend>Contatti</legend>

		<div class='<%=ClasseCampo(TelefonoVisibile) %>'>
			<%=Label("Telefono", TelefonoObbligatorio)%>
			<asp:TextBox ID="Telefono" runat="server" Columns="25" MaxLength="15" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator5" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="Telefono" Enabled='<%#TelefonoObbligatorio %>'/>
		</div>

		<div class='<%=ClasseCampo(CellulareVisibile) %>'>
			<%=Label("Cellulare", CellulareObbligatorio)%>
			<asp:TextBox ID="TelefonoCellulare" runat="server" Columns="25" MaxLength="15" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator7" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="TelefonoCellulare" Enabled='<%#CellulareObbligatorio %>'/>
		</div>

		<div class='<%=ClasseCampo(FaxVisibile) %>'>
			<%=Label("Fax", FaxObbligatorio)%>
			<asp:TextBox ID="Fax" runat="server" Columns="25" MaxLength="15" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator9" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="Fax" Enabled='<%#FaxObbligatorio %>'/>
		</div>

		<div class='<%=ClasseCampo(EmailVisibile) %>'>
			<%=Label("E-mail", EmailObbligatoria)%>
			<asp:TextBox ID="email" runat="server" Columns="40" MaxLength="70" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator10" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="email" Enabled='<%#EmailObbligatoria %>'/>
		</div>

		<div class='<%=ClasseCampo(PecVisibile) %>'>
			<%=Label("E-mail PEC", PecObbligatoria)%>
			<asp:TextBox ID="emailPec" runat="server" MaxLength="70" Columns="40" />
            <asp:RequiredFieldValidator ID="RequiredFieldValidator11" runat="server" ErrorMessage="Campo obbligatorio"
				ControlToValidate="emailPec" Enabled='<%#PecObbligatoria %>'/>
		</div>
    </fieldset>

	<fieldset>
		<legend>Indirizzo per la corrispondenza</legend>
		<div class="bottoni <%=ClasseCampo(SedeLegaleVisibile) %>">
			<asp:Button ID="cmdCopiaResidenza" Text="Copia da indirizzo sede legale"
				runat="server" CausesValidation="False" OnClick="cmdCopiaResidenza_Click" />
		</div>
		<div>
			<asp:Label runat="server" ID="lblComuneCorrispondenza" Text="Comune" AssociatedControlID="txtComuneCorrispondenza" />
			<asp:HiddenField runat="server" ID="hidComuneCorrispondenza" />
			<asp:TextBox runat="server" ID="txtComuneCorrispondenza" Columns="50" />
		</div>
		<div>
			<asp:Label runat="server" ID="label14" AssociatedControlID="IndirizzoCorrispondenza"
				Text="Indirizzo" />
			<asp:TextBox ID="IndirizzoCorrispondenza" runat="server" Columns="60" MaxLength="100" />
		</div>
		<div>
			<asp:Label runat="server" ID="label15" AssociatedControlID="CittaCorrispondenza"
				Text="Località" />
			<asp:TextBox ID="CittaCorrispondenza" runat="server" Columns="60" MaxLength="50" />
		</div>
		<div>
			<asp:Label runat="server" ID="label16" AssociatedControlID="CapCorrispondenza" Text="Cap" />
			<asp:TextBox ID="CapCorrispondenza" runat="server" Columns="15" MaxLength="5" />
		</div>

		<div class="bottoni">
			<asp:Button ID="cmdConfirm" runat="server" Text="Conferma" OnClick="cmdConfirm_Click" OnClientClick="return doFormValidation();" CausesValidation="true" />
			<asp:Button ID="cmdCancel" runat="server" Text="Annulla" CausesValidation="False"
				OnClick="cmdCancel_Click" />
		</div>
	</fieldset>
</div>
