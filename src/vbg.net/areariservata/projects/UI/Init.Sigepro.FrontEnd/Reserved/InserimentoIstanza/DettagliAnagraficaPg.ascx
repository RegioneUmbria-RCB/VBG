<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="DettagliAnagraficaPg.ascx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DettagliAnagraficaPg" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Common"
    Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="cc3" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<style>
    .form-hide {
        display: none;
        /*border: 1px solid #f00;*/
    }
</style>

<script type="text/javascript">

    function copiaDatiCorrispondenza() {
        var src = [$('#<%=acComuneSedeLegale.HiddenClientID%>'),
                $('#<%=acComuneSedeLegale.Inner.ClientID%>'),
                $('#<%=Indirizzo.Inner.ClientID%>'),
                $('#<%=Citta.Inner.ClientID%>'),
                $('#<%=Cap.Inner.ClientID%>')];

            var dst = [$('#<%=acComuneCorrispondenza.HiddenClientID%>'),
                $('#<%=acComuneCorrispondenza.Inner.ClientID%>'),
                $('#<%=IndirizzoCorrispondenza.Inner.ClientID%>'),
                $('#<%=CittaCorrispondenza.Inner.ClientID%>'),
                $('#<%=CapCorrispondenza.Inner.ClientID%>')];

        for (var i = 0; i < src.length; i++) {
            dst[i].val(src[i].val());
        }
    }

    $(function () {
        verificaTipoSoggetto();


        $('#copiaResidenza').on('click', function (e) {

            e.preventDefault();

            copiaDatiCorrispondenza();
        });

        $('#<%= TipoSoggetto.ClientID%>').change(function (e) {
                e.preventDefault();
                verificaTipoSoggetto();
            });

            var hidComuneSL = $('#<%= acComuneSedeLegale.HiddenClientID %>'),
                txtComuneSL = $('#<%= acComuneSedeLegale.Inner.ClientID %>'),
                urlRicercaComune = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune',
                urlRicercaProv = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaProvincia';

            RicercaComuni('<%= IdComune %>', txtComuneSL, hidComuneSL, urlRicercaComune);

            var hidComuneCorrispondenza = $('#<%= acComuneCorrispondenza.HiddenClientID%>');
            var txtComuneCorrispondenza = $('#<%= acComuneCorrispondenza.Inner.ClientID %>');

            RicercaComuni('<%= IdComune %>', txtComuneCorrispondenza, hidComuneCorrispondenza, urlRicercaComune);

            var hidComuneCciaa = $('#<%= acProvinciaCCIAA.HiddenClientID %>');
            var txtComuneCciaa = $('#<%= acProvinciaCCIAA.Inner.ClientID%>');

            RicercaComuni('<%= IdComune %>', txtComuneCciaa, hidComuneCciaa, urlRicercaComune);

            var hidComuneRegTrib = $('#<%= acComuneRegTrib.HiddenClientID %>');
            var txtComuneRegTrib = $('#<%= acComuneRegTrib.Inner.ClientID%>');

            RicercaComuni('<%= IdComune %>', txtComuneRegTrib, hidComuneRegTrib, urlRicercaComune);

            var hidProvinciaRea = $('#<%= acProvinciaREA.HiddenClientID %>');
            var txtProvinciaRea = $('#<%= acProvinciaREA.Inner.ClientID%>');

            RicercaProvincia('<%= IdComune %>', txtProvinciaRea, hidProvinciaRea, urlRicercaProv);

            var txtSedeInps = $('#<%= acSedeINPS.Inner.ClientID%>'),
                hidSedeInps = $('#<%= acSedeINPS.HiddenClientID%>'),
                txtSedeInail = $('#<%= acSedeINAIL.Inner.ClientID%>'),
                hidSedeInail = $('#<%= acSedeINAIL.HiddenClientID%>'),
                urlRicercaInpsInailBase = '<%=ResolveClientUrl("~/Reserved/InserimentoIstanza/HelperGestioneInpsInail/InpsInailjsService.asmx")%>',
                argsRicercaInpsInail = 'software=TT&idcomune=<%=Server.UrlEncode(IdComune)%>',
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

                    var txtDescrizioneEstesa = $('#<%=txtDescrizioneEstesa.ClientID %>');

                $('#descrizioneEstesa').css('display', data.d ? 'block' : 'none');
                if (!data.d)
                    txtDescrizioneEstesa.val('');

                txtDescrizioneEstesa.attr('data-validate', data.d);

                $('#aspnetForm').validator('update');
            }
        });
    }

    function preparaValidazioneCodiceFiscale() {
        var codiceFiscale = $('#<%=CodiceFiscale.Inner.ClientID %>');
            var partitaIva = $('#<%=PartitaIva.Inner.ClientID %>');

        $('.validazioneCodiceFiscale').css('display', 'none');

        codiceFiscale.change(function () { doFormValidation(); });
        partitaIva.change(function () { doFormValidation(); });
    }

    function doFormValidation() {
        var codiceFiscale = $('#<%=CodiceFiscale.Inner.ClientID %>');
            var partitaIva = $('#<%=PartitaIva.Inner.ClientID %>');

        var validazioneFallita = codiceFiscale.val().length == 0 && partitaIva.val().length == 0;

        $('.validazioneCodiceFiscale').css('display', validazioneFallita ? 'inline' : 'none');

        return !validazioneFallita;
    }

    window.doFormValidation = doFormValidation;

</script>
<input type="hidden" runat="server" id="ANAGRAFE_PK" name="ANAGRAFE_PK" />

<div class="form-small">

    <fieldset>
        <legend>Tipo soggetto</legend>

        <div class="row">
            <div class="form-group col-md-6 has-feedback">
                <label for='<%=TipoSoggetto.ClientID %>'>In qualità di <sup>*</sup></label>
                <cc1:ComboTipiSoggetto ID="TipoSoggetto" runat="server" TipoSoggetto="G" class="form-control" required />
                <div class="help-block with-errors"></div>
            </div>

            <div class="form-group col-md-6 has-feedback" id="descrizioneEstesa">
                <label for='<%= txtDescrizioneEstesa.ClientID%>'>Specificare <sup>*</sup></label>
                <asp:TextBox runat="server" ID="txtDescrizioneEstesa" class="form-control" MaxLength="128" Columns="80" required />
                <div class="help-block with-errors"></div>
            </div>
        </div>
    </fieldset>


    <fieldset>
        <legend>Dati del soggetto</legend>

        <div class="row">
            <ar:TextBox runat="server" ID="Nominativo" Label="Ragione sociale" MaxLength="180" Required="true" BtSize="Col8" />
            <ar:DropDownList runat="server" ID="CodiceFormaGiuridica" Required='true' Label="Forma giuridica" BtSize="Col4" />
        </div>
        <div class="row">
            <ar:TextBox runat="server" ID="CodiceFiscale" Label="Codice fiscale impresa" MaxLength="32" Required="true" BtSize="Col4" />
            <ar:TextBox runat="server" ID="PartitaIva" Label="Partita IVA" MaxLength="32" Required='<%#PartitaIvaObbligatoria %>' Visible='<%#PartitaIvaVisibile %>' BtSize="Col4" />
            <ar:DateTextBox runat="server" ID="DataNominativo" Label="Data di costituzione" Required='<%#DataCostituzioneObbligatoria %>' Visible='<%#DataCostituzioneVisibile %>' BtSize="Col4" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(SedeLegaleVisibile) %>'>
        <legend>Sede legale</legend>

        <div class="row">
            <ar:Autocomplete runat="server" ID="acComuneSedeLegale" Label="Comune" Required='<%# SedeLegaleObbligatoria %>' MaxLength="50" BtSize="Col6" />
            <ar:TextBox runat="server" ID="Indirizzo" Label="Indirizzo" MaxLength="100" Required='<%# SedeLegaleObbligatoria %>' BtSize="Col6" />
        </div>

        <div class="row">
            <ar:TextBox runat="server" ID="Citta" Label="Località" MaxLength="100" Required='<%# SedeLegaleObbligatoria %>' BtSize="Col6" />
            <ar:TextBox runat="server" ID="Cap" Label="Cap" MaxLength="5" Required='<%# SedeLegaleObbligatoria %>' BtSize="Col6" />
        </div>
    </fieldset>



    <fieldset class='<%=ClasseCampo(CciaaVisibile) %>'>
        <legend>CCIAA</legend>
        <div class="row">
            <ar:TextBox runat="server" ID="RegDitte" Label="Numero" MaxLength="16" Required='<%# CciaaObbligatoria %>' BtSize="Col4" />
            <ar:DateTextBox runat="server" ID="DataRegDitte" Label="Data" Required='<%#CciaaObbligatoria %>' BtSize="Col4" />
            <ar:Autocomplete runat="server" ID="acProvinciaCCIAA" Label="Provincia" Required='<%# CciaaObbligatoria %>' MaxLength="50" BtSize="Col4" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(RegTribVisibile) %>'>
        <legend>Reg. Trib.</legend>
        <div class="row">
            <ar:TextBox runat="server" ID="RegTrib" Label="Numero" MaxLength="16" Required='<%# RegTribObbligatorio %>' BtSize="Col4" />
            <ar:DateTextBox runat="server" ID="RegTribData" Label="Data" Required='<%#RegTribObbligatorio %>' BtSize="Col4" />
            <ar:Autocomplete runat="server" ID="acComuneRegTrib" Label="Comune" Required='<%# RegTribObbligatorio %>' MaxLength="50" BtSize="Col4" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(ReaVisibile) %>'>

        <legend>REA</legend>
        <div class="row">
            <ar:TextBox runat="server" ID="NumIscrREA" Label="Numero" MaxLength="16" Required='<%# ReaObbligatoria %>' BtSize="Col4" />
            <ar:DateTextBox runat="server" ID="DataIscrREA" Label="Data" Required='<%#ReaObbligatoria %>' BtSize="Col4" />
            <ar:Autocomplete runat="server" ID="acProvinciaREA" Label="Comune" Required='<%# ReaObbligatoria %>' MaxLength="50" BtSize="Col4" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(InpsVisibile) %>'>
        <legend>INPS</legend>
        <div class="row">
            <ar:TextBox runat="server" ID="txtNumeroInps" Label="Numero" MaxLength="16" Required='<%# InpsObbligatoria %>' BtSize="Col6" />
            <ar:Autocomplete runat="server" ID="acSedeINPS" Label="Sede" Required='<%# InpsObbligatoria %>' MaxLength="50" BtSize="Col6" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(InailVisibile) %>'>
        <legend>INAIL</legend>
        <div class="row">
            <ar:TextBox runat="server" ID="txtNumeroINAIL" Label="Numero" MaxLength="16" Required='<%# InailObbligatoria %>' BtSize="Col6" />
            <ar:Autocomplete runat="server" ID="acSedeINAIL" Label="Sede" Required='<%# InailObbligatoria %>' MaxLength="50" BtSize="Col6" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(TelefonoVisibile || CellulareVisibile || FaxVisibile || EmailVisibile || PecVisibile) %>'>
        <legend>Contatti</legend>

        <div class="row">
            <ar:TextBox runat="server" ID="Telefono" MaxLength="15" Label="Telefono" Visible='<%#TelefonoVisibile %>' Required='<%#TelefonoObbligatorio %>' BtSize="Col4" />
            <ar:TextBox runat="server" ID="TelefonoCellulare" MaxLength="15" Label="Cellulare" Visible='<%#CellulareVisibile %>' Required='<%#CellulareObbligatorio %>' BtSize="Col4" />
            <ar:TextBox runat="server" ID="Fax" MaxLength="15" Label="Fax" Visible='<%#CellulareVisibile %>' Required='<%#CellulareObbligatorio %>' BtSize="Col4" />
        </div>

        <div class="row">
            <ar:TextBox runat="server" ID="email" MaxLength="70" Label="E-Mail" Visible='<%#EmailVisibile %>' Required='<%#EmailObbligatoria %>' BtSize="Col6" />
            <ar:TextBox runat="server" ID="emailPec" MaxLength="70" Label="PEC" Visible='<%#PecVisibile %>' Required='<%#PecObbligatoria %>' BtSize="Col6" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(CorrispondenzaVisibile) %>'>
        <legend>Indirizzo per la corrispondenza </legend>

        <div class='copia-indirizzo <%=ClasseCampo(SedeLegaleVisibile) %>'>
            <i class="glyphicon glyphicon-pencil"></i>
            <a id="copiaResidenza" href="#">Copia da indirizzo di residenza</a>
        </div>
        <div class="row">
            <ar:Autocomplete runat="server" ID="acComuneCorrispondenza" Label="Comune" BtSize="Col4" Required='<%# CorrispondenzaObbligatoria %>' Visible='<%#CorrispondenzaVisibile %>' MaxLength="50" />
            <ar:TextBox runat="server" ID="IndirizzoCorrispondenza" MaxLength="100" BtSize="Col8" Label="Indirizzo" Visible='<%#CorrispondenzaVisibile %>' Required='<%#CorrispondenzaObbligatoria %>' />
        </div>
        <div class="row">
            <ar:TextBox runat="server" ID="CittaCorrispondenza" MaxLength="50" Label="Località" BtSize="Col6" Visible='<%#CorrispondenzaVisibile %>' Required='<%#CorrispondenzaObbligatoria %>' />
            <ar:TextBox runat="server" ID="CapCorrispondenza" MaxLength="5" Label="CAP" BtSize="Col6" Visible='<%#CorrispondenzaVisibile %>' Required='<%#CorrispondenzaObbligatoria %>' />
        </div>
    </fieldset>


    <asp:Button ID="cmdConfirm" runat="server" CssClass="btn btn-primary" Text="Conferma" OnClick="cmdConfirm_Click" OnClientClick="return doFormValidation();" CausesValidation="true" />
    <asp:LinkButton ID="cmdCancel" CssClass="btn btn-default" runat="server" Text="Annulla" CausesValidation="False"
        OnClick="cmdCancel_Click" />

</div>
