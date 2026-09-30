<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="DettagliAnagraficaPg.ascx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DettagliAnagraficaPg" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Common"
    Assembly="Init.Sigepro.FrontEnd.WebControls" %>

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
                urlRicercaComune = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune?idcomune=<%=this.IdComune%>',
                urlRicercaProv = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaProvincia?idcomune=<%=this.IdComune%>';

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
                argsRicercaInpsInail = 'software=<%=Server.UrlEncode(Software)%>&idcomune=<%=Server.UrlEncode(IdComune)%>',
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

                txtDescrizioneEstesa.closest('div').removeClass('has-error');
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

<div class="form-small dati-anagrafici">

    <fieldset id="tipo-soggetto">
        <legend>Tipo soggetto</legend>

        <div class="row">
            <div class="form-group col-md-12 has-feedback">
                <label for='<%=TipoSoggetto.ClientID %>'>In qualità di <sup>*</sup></label>
                <cc1:ComboTipiSoggetto ID="TipoSoggetto" runat="server" TipoSoggetto="G" class="form-control" required />
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="row" id="descrizioneEstesa">
            <div class="form-group col-md-12 has-feedback">
                <label for='<%= txtDescrizioneEstesa.ClientID%>'>Specificare <sup>*</sup></label>
                <asp:TextBox runat="server" ID="txtDescrizioneEstesa" class="form-control" MaxLength="128" Columns="80" required />
                <div class="help-block with-errors"></div>
            </div>
        </div>
    </fieldset>


    <fieldset>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pg.titolo.dati-soggetto">Dati del soggetto</ar:RisorsaTestualeLabel>
        </legend>

        <div class="row">
            <ar:TextBox runat="server" ID="Nominativo" IdRisorsa="dettagli-anagrafica.pg.ragione-sociale" Label="Ragione sociale" MaxLength="180" Required="true" BtSize="Col8" />
            <ar:DropDownList runat="server" ID="CodiceFormaGiuridica" Required="true" IdRisorsa="dettagli-anagrafica.pg.forma-giuridica" Label="Forma giuridica" BtSize="Col4" />
        </div>
        <div class="row">
            <ar:TextBox runat="server" ID="CodiceFiscale" IdRisorsa="dettagli-anagrafica.pg.cf-impresa" Label="Codice fiscale impresa" MaxLength="32" Required="true" BtSize="Col4" />
            <ar:TextBox runat="server" ID="PartitaIva" IdRisorsa="dettagli-anagrafica.pg.pi-impresa" Label="Partita IVA" MaxLength="32" Required='<%#PartitaIvaObbligatoria %>' Visible='<%#PartitaIvaVisibile %>' BtSize="Col4" />
            <ar:DateTextBox runat="server" ID="DataNominativo" IdRisorsa="dettagli-anagrafica.pg.data-costituzione" Label="Data di costituzione" Required='<%#DataCostituzioneObbligatoria %>' Visible='<%#DataCostituzioneVisibile %>' BtSize="Col4" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(SedeLegaleVisibile) %>'>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pg.titolo.sede-legale">Sede legale</ar:RisorsaTestualeLabel>
        </legend>

        <div class="row">
            <ar:Autocomplete runat="server" ID="acComuneSedeLegale" IdRisorsa="dettagli-anagrafica.pg.sede-legale-comune" Label="Comune" Required='<%# SedeLegaleObbligatoria %>' MaxLength="50" BtSize="Col6" />
            <ar:TextBox runat="server" ID="Indirizzo" Label="Indirizzo" IdRisorsa="dettagli-anagrafica.pg.sede-legale-indirizzo" MaxLength="100" Required='<%# SedeLegaleObbligatoria %>' BtSize="Col6" />
        </div>

        <div class="row">
            <ar:TextBox runat="server" ID="Citta" IdRisorsa="dettagli-anagrafica.pg.sede-legale-localita" Label="Località" MaxLength="100" BtSize="Col6" />
            <ar:TextBox runat="server" ID="Cap" Label="Cap" IdRisorsa="dettagli-anagrafica.pg.sede-legale-cap" MaxLength="15" Required='<%# SedeLegaleObbligatoria %>' BtSize="Col6" />
        </div>
    </fieldset>



    <fieldset class='<%=ClasseCampo(CciaaVisibile) %>'>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pg.titolo.cciaa">CCIAA</ar:RisorsaTestualeLabel></legend>
        <div class="row">
            <ar:TextBox runat="server" ID="RegDitte" IdRisorsa="dettagli-anagrafica.pg.cciaa-numero" Label="Numero" MaxLength="16" Required='<%# CciaaObbligatoria %>' BtSize="Col4" />
            <ar:DateTextBox runat="server" ID="DataRegDitte" IdRisorsa="dettagli-anagrafica.pg.cciaa-data" Label="Data" Required='<%#CciaaObbligatoria %>' BtSize="Col4" />
            <ar:Autocomplete runat="server" ID="acProvinciaCCIAA" IdRisorsa="dettagli-anagrafica.pg.cciaa-provincia" Label="Comune" Required='<%# CciaaObbligatoria %>' MaxLength="50" BtSize="Col4" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(RegTribVisibile) %>'>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pg.titolo.reg-trib">Reg. Trib.</ar:RisorsaTestualeLabel></legend>
        <div class="row">
            <ar:TextBox runat="server" ID="RegTrib" IdRisorsa="dettagli-anagrafica.pg.reg-trib-numero" Label="Numero" MaxLength="16" Required='<%# RegTribObbligatorio %>' BtSize="Col4" />
            <ar:DateTextBox runat="server" ID="RegTribData" IdRisorsa="dettagli-anagrafica.pg.reg-trib-data" Label="Data" Required='<%#RegTribObbligatorio %>' BtSize="Col4" />
            <ar:Autocomplete runat="server" ID="acComuneRegTrib" IdRisorsa="dettagli-anagrafica.pg.reg-trib-comune" Label="Comune" Required='<%# RegTribObbligatorio %>' MaxLength="50" BtSize="Col4" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(ReaVisibile) %>'>

        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pg.titolo.rea">REA</ar:RisorsaTestualeLabel></legend>
        <div class="row">
            <ar:TextBox runat="server" IdRisorsa="dettagli-anagrafica.pg.rea-numero" ID="NumIscrREA" Label="Numero" MaxLength="16" Required='<%# ReaObbligatoria %>' BtSize="Col4" />
            <ar:DateTextBox runat="server" ID="DataIscrREA" IdRisorsa="dettagli-anagrafica.pg.rea-data" Label="Data" Required='<%#ReaObbligatoria %>' BtSize="Col4" />
            <ar:Autocomplete runat="server" ID="acProvinciaREA" IdRisorsa="dettagli-anagrafica.pg.rea-comune" Label="Provincia" Required='<%# ReaObbligatoria %>' MaxLength="50" BtSize="Col4" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(InpsVisibile) %>'>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pg.titolo.inps">INPS</ar:RisorsaTestualeLabel></legend>
        <div class="row">
            <ar:TextBox runat="server" ID="txtNumeroInps" IdRisorsa="dettagli-anagrafica.pg.inps-numero" Label="Numero" MaxLength="16" Required='<%# InpsObbligatoria %>' BtSize="Col6" />
            <ar:Autocomplete runat="server" ID="acSedeINPS" IdRisorsa="dettagli-anagrafica.pg.inps-sede" Label="Sede" Required='<%# InpsObbligatoria %>' MaxLength="50" BtSize="Col6" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(InailVisibile) %>'>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pg.titolo.inail">INAIL</ar:RisorsaTestualeLabel></legend>
        <div class="row">
            <ar:TextBox runat="server" ID="txtNumeroINAIL" IdRisorsa="dettagli-anagrafica.pg.inail-numero" Label="Numero" MaxLength="16" Required='<%# InailObbligatoria %>' BtSize="Col6" />
            <ar:Autocomplete runat="server" ID="acSedeINAIL" IdRisorsa="dettagli-anagrafica.pg.inail-sede" Label="Sede" Required='<%# InailObbligatoria %>' MaxLength="50" BtSize="Col6" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(TelefonoVisibile || CellulareVisibile || EmailVisibile || PecVisibile) %>'>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pg.titolo.contatti">Contatti</ar:RisorsaTestualeLabel></legend>

        <div class="row">
            <ar:TextBox runat="server" ID="Telefono" Pattern="^(\+[\d]{2,3})?[\s]?([0-9\s]+)$" MaxLength="15" IdRisorsa="dettagli-anagrafica.pg.contatti-telefono" Label="Telefono" Visible='<%#TelefonoVisibile %>' Required='<%#TelefonoObbligatorio %>' BtSize="Col4" />
            <ar:TextBox runat="server" ID="TelefonoCellulare" Pattern="^(\+[\d]{2,3})?[\s]?([0-9\s]+)$" MaxLength="15" IdRisorsa="dettagli-anagrafica.pg.contatti-cellulare" Label="Cellulare" Visible='<%#CellulareVisibile %>' Required='<%#CellulareObbligatorio %>' BtSize="Col4" />
        </div>

        <div class="row">
            <ar:TextBox runat="server" ID="email" MaxLength="70" type="email" IdRisorsa="dettagli-anagrafica.pg.contatti-email" Label="E-Mail" Visible='<%#EmailVisibile %>' Required='<%#EmailObbligatoria %>' BtSize="Col6" />
            <ar:TextBox runat="server" ID="emailPec" MaxLength="70" type="email" IdRisorsa="dettagli-anagrafica.pg.contatti-pec" Label="PEC" Visible='<%#PecVisibile %>' Required='<%#PecObbligatoria %>' BtSize="Col6" />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(CorrispondenzaVisibile) %>'>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pg.titolo.corrispondenza">Indirizzo per la corrispondenza</ar:RisorsaTestualeLabel></legend>

        <div class='copia-indirizzo <%=ClasseCampo(SedeLegaleVisibile) %>'>
            <i class="glyphicon glyphicon-pencil"></i>
            <a id="copiaResidenza" href="#">Copia da indirizzo di residenza</a>
        </div>
        <div class="row">
            <ar:Autocomplete runat="server" ID="acComuneCorrispondenza" IdRisorsa="dettagli-anagrafica.pg.corrispondenza-comune" Label="Comune" BtSize="Col4" Required='<%# CorrispondenzaObbligatoria %>' Visible='<%#CorrispondenzaVisibile %>' MaxLength="50" />
            <ar:TextBox runat="server" ID="IndirizzoCorrispondenza" MaxLength="100" BtSize="Col8" IdRisorsa="dettagli-anagrafica.pg.corrispondenza-indirizzo" Label="Indirizzo" Visible='<%#CorrispondenzaVisibile %>' Required='<%#CorrispondenzaObbligatoria %>' />
        </div>
        <div class="row">
            <ar:TextBox runat="server" ID="CittaCorrispondenza" MaxLength="50" IdRisorsa="dettagli-anagrafica.pg.corrispondenza-localita" Label="Località" BtSize="Col6" Visible='<%#CorrispondenzaVisibile %>' Required='<%#CorrispondenzaObbligatoria %>' />
            <ar:TextBox runat="server" ID="CapCorrispondenza" MaxLength="15" IdRisorsa="dettagli-anagrafica.pg.corrispondenza-cap" Label="CAP" BtSize="Col6" Visible='<%#CorrispondenzaVisibile %>' Required='<%#CorrispondenzaObbligatoria %>' />
        </div>
    </fieldset>
    <div id="tipo-soggetto-bottom"></div>
    <asp:Button ID="cmdConfirm" runat="server" CssClass="btn btn-primary" Text="Conferma" OnClick="cmdConfirm_Click" OnClientClick="return doFormValidation();" CausesValidation="true" />
    <asp:LinkButton ID="cmdCancel" CssClass="btn btn-default" runat="server" Text="Annulla" CausesValidation="False"
        OnClick="cmdCancel_Click" />

</div>
