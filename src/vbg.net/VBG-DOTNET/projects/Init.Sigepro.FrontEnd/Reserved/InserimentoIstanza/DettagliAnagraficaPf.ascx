<%@ Control Language="C#" AutoEventWireup="true" Debug="true" CodeBehind="DettagliAnagraficaPf.ascx.cs"
    Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DettagliAnagraficaPf" %>
<%@ Register TagPrefix="cc1" Namespace="Init.Sigepro.FrontEnd.WebControls.Common"
    Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<input type="hidden" runat="server" id="ANAGRAFE_PK" />

<style>
    .form-hide {
        display: none;
    }
</style>

<script type="text/javascript">

    function checkDatiAlbo(el, resetValues) {
        var campodati = $('#<%=acProvinciaAlbo.Inner.ClientID%>'),
            campoCodice = $('#<%=acProvinciaAlbo.HiddenClientID%>'),
            labelCampo = campodati.parent().parent().find('label'),
            selectedItem = el.find('option:selected'),
            regionale = selectedItem.text() === '' ? false : selectedItem.data('regionale').toString() === '1';

        // if (resetValues) {
        //     campodati.val('');
        //     campoCodice.val('');
        // }


        if (regionale) {
            labelCampo.html('Regione <sup>*</sup>');
            campodati.ricercaAlbo('ricercaRegione');
        } else {
            labelCampo.html('Provincia <sup>*</sup>');
            campodati.ricercaAlbo('ricercaProvincia');
        }
    }


    $(function () {

        var txtComuneNascita = $('#<%= acComuneNascita.Inner.ClientID %>');
        var hidComuneNascita = $('#<%= acComuneNascita.HiddenClientID%>');
        var urlRicerca = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune?idcomune=<%=Server.UrlEncode(this.IdComune)%>';
        var urlRicercaProv = '<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaProvincia?idcomune=<%=Server.UrlEncode(this.IdComune)%>';


        // Dati albo professionale
        // Dati albo professionale
        $('#<%=acProvinciaAlbo.Inner.ClientID%>').ricercaAlbo({
            hiddenElement: $('#<%=acProvinciaAlbo.HiddenClientID%>'),
            idComune: '<%= IdComune %>',
            urlRicerca: urlRicercaProv
        });

        $('#<%=ddlAlbo.Inner.ClientID%>').on('change', function () {
            checkDatiAlbo($(this), true);
        });

        checkDatiAlbo($('#<%=ddlAlbo.Inner.ClientID%>'), false);


        verificaTipoSoggetto();

        $('#<%= TipoSoggetto.ClientID%>').change(function (e) {
            e.preventDefault();
            verificaTipoSoggetto();
        });

        // Inizializzo i campi di ricerca dei comuni/provincie


        new RicercaComuni('<%= IdComune %>', txtComuneNascita, hidComuneNascita, urlRicerca);

        var txtComuneCorrispondenza = $('#<%= acComuneCorrispondenza.Inner.ClientID %>');
        var hidComuneCorrispondenza = $('#<%= acComuneCorrispondenza.HiddenClientID%>');

        new RicercaComuni('<%= IdComune %>', txtComuneCorrispondenza, hidComuneCorrispondenza, urlRicerca);

        var txtComuneResidenza = $('#<%= acComuneResidenza.Inner.ClientID %>');
        var hidComuneResidenza = $('#<%= acComuneResidenza.HiddenClientID%>');

        new RicercaComuni('<%= IdComune %>', txtComuneResidenza, hidComuneResidenza, urlRicerca);

 //           var hidProvinciaAlbo = $('#<%= acProvinciaAlbo.HiddenClientID %>');
 //           var txtProvinciaAlbo = $('#<%= acProvinciaAlbo.Inner.ClientID%>');
 //
 //           new RicercaProvincia('<%= IdComune %>', txtProvinciaAlbo, hidProvinciaAlbo, urlRicercaProv);

        var verificaCf = new VerificaCf({
            txtNome: $('#<%=NOME.Inner.ClientID %>'),
            txtCognome: $('#<%=NOMINATIVO.Inner.ClientID %>'),
            txtDataNascita: $('#<%=DataNascita.Inner.ClientID %>'),
            txtSesso: $('#<%=sesso.Inner.ClientID %>'),
            txtComuneNascita: $('#<%=acComuneNascita.HiddenClientID %>'),
            txtCodiceFiscale: $('#<%=CodiceFiscale.Inner.ClientID %>'),
            bottoneInvio: $('#<%=cmdConfirm.ClientID %>'),
            urlVerifica: '<%=ResolveClientUrl("~/Public/WebServices/ValidazioneCfService.asmx") %>/ValidaCF',
            divErrori: $('#<%=alertValidazioneCf.ClientID%>')
        });

        updateValidators();

        document.forms[0].onsubmit = function () {

            if ($('.has-error').length) {
                return false;
            }

            return verificaCf.doCheck();
        }

    });

    function verificaTipoSoggetto() {
        $.ajax({
            url: '<%= ResolveClientUrl("~/Public/WebServices/TipiSoggettoJsService.asmx")%>/GetById?idComune=<%=IdComune %>&Software=<%=Software %>',
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: "{'idSoggetto':'" + $('#<%= TipoSoggetto.ClientID%>').val() + "'}",
            success: function (data) {
                var specificaDescrizione = data.d && data.d.RichiedeSpecificaDescrizione,
                    mostraDatiAlbo = data.d && data.d.RichiedeDatiAlbo,
                    txtDescrizioneEstesa = $('#<%=txtDescrizioneEstesa.ClientID %>'),
                    ddlAlbo = $('#<%=ddlAlbo.Inner.ClientID %>'),
                    txtNumeroAlbo = $('#<%=txtNumeroAlbo.Inner.ClientID %>'),
                    acProvinciaAlbo = $('#<%=acProvinciaAlbo.Inner.ClientID %>'),
                    hidProvinciaAlbo = $('#<%=acProvinciaAlbo.HiddenClientID%>');

                $('#descrizioneEstesa').css('display', specificaDescrizione ? 'block' : 'none');

                txtDescrizioneEstesa.closest('div').removeClass('has-error');

                if (!specificaDescrizione) {
                    txtDescrizioneEstesa.val('');
                }

                txtDescrizioneEstesa.attr('data-validate', specificaDescrizione);
                


                $('#datiAlbo').css('display', mostraDatiAlbo ? 'block' : 'none');

                if (!mostraDatiAlbo) {
                    // ddlAlbo.val('');
                    // txtNumeroAlbo.val('');
                    // acProvinciaAlbo.val('');
                    // hidProvinciaAlbo.val('');
                }

                ddlAlbo.attr('data-validate', mostraDatiAlbo);
                txtNumeroAlbo.attr('data-validate', mostraDatiAlbo);
                acProvinciaAlbo.attr('data-validate', mostraDatiAlbo);

                setTimeout(() => updateValidators(), 100);
            }
        });
    }

</script>

<small class="text-muted">I campi contrassegnati con <sup>*</sup> sono obbligatori
</small>
<div class="form-small dati-anagrafici">
    <fieldset id="tipo-soggetto">
        <legend>Tipo soggetto</legend>

        <div class="row">
            <div class="form-group has-feedback col-md-12">
                <label for='<%=TipoSoggetto.ClientID %>'>In qualità di <sup>*</sup></label>
                <cc1:ComboTipiSoggetto ID="TipoSoggetto" runat="server" TipoSoggetto="F" class="form-control" required />
                <div class="help-block with-errors"></div>
            </div>
        </div>
        <div class="row" id="descrizioneEstesa">
            <div class="form-group has-feedback col-md-12">
                <label for='<%=txtDescrizioneEstesa.ClientID %>'>Specificare <sup>*</sup></label>
                <asp:TextBox runat="server" ID="txtDescrizioneEstesa" class="form-control" MaxLength="128" Columns="80" required />
                <div class="help-block with-errors"></div>
            </div>
        </div>
    </fieldset>



    <fieldset>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pf.titolo.dati-soggetto">Dati del soggetto</ar:RisorsaTestualeLabel>
        </legend>

        <ar:DropDownList runat="server" ID="TITOLO" Required='<%# TitoloObbligatorio %>' Visible='<%#TitoloVisibile%>' Label="Titolo" />

        <div class="row">
            <ar:TextBox runat="server" ID="NOMINATIVO" Label="Cognome" MaxLength="180" Required="true" BtSize="Col6" />
            <ar:TextBox runat="server" ID="NOME" Label="Nome" MaxLength="30" Required="true" BtSize="Col6" />
        </div>

        <div class="row">
            <ar:DropDownList runat="server" ID="sesso" Label="Sesso" Required="true" BtSize="Col6">
                <asp:ListItem Value="">Selezionare...</asp:ListItem>
                <asp:ListItem Value="M">Maschio</asp:ListItem>
                <asp:ListItem Value="F">Femmina</asp:ListItem>
            </ar:DropDownList>

            <ar:DropDownList runat="server" ID="CodiceCittadinanza" Required='<%# CittadinanzaObbligatoria %>' Label="Cittadinanza" Visible='<%# CittadinanzaVisible%>' BtSize="Col6" />
        </div>
    </fieldset>

    <fieldset>
        <legend>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pf.titolo.dati-nascita">Dati di nascita e codice fiscale</ar:RisorsaTestualeLabel>
        </legend>

        <div class="row">
            <ar:DateTextBox runat="server" ID="DataNascita" IdRisorsa="dettagli-anagrafica.pf.data-nascita" Required="true" Label="Data di nascita" BtSize="Col6" />

            <ar:Autocomplete runat="server" ID="acComuneNascita" IdRisorsa="dettagli-anagrafica.pf.comune-nascita" MaxLength="40" Label="Comune" BtSize="Col6" Required="true" HelpText="Per i nati all'estero indicare il nome dello stato di nascita" />
        </div>

        <ar:TextBox runat="server" ID="CodiceFiscale" MaxLength="32" Label="Codice fiscale" Required="true" ReadOnly="true" />
        <asp:Button ID="cmdCalcolaCF" runat="server" Text="Calcola" CausesValidation="False" Visible="False"></asp:Button>

    </fieldset>

    <fieldset class='<%=ClasseCampo(ResidenzaVisible)%>'>
        <legend><ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pf.titolo.residenza">Residenza</ar:RisorsaTestualeLabel></legend>

        <div class="row">
            <ar:Autocomplete runat="server" ID="acComuneResidenza" IdRisorsa="dettagli-anagrafica.pf.comune-residenza" MaxLength="40" Label="Comune" BtSize="Col4" Required='<%# ResidenzaObbligatoria %>' />
            <ar:TextBox runat="server" ID="Indirizzo" MaxLength="100" IdRisorsa="dettagli-anagrafica.pf.indirizzo-residenza" Label="Indirizzo" BtSize="Col8" Required='<%# ResidenzaObbligatoria %>' />
        </div>

        <div class="row">
            <ar:TextBox runat="server" ID="Citta" MaxLength="100" IdRisorsa="dettagli-anagrafica.pf.localita-residenza" Label="Località/Frazione" BtSize="Col6" Required='<%# ResidenzaObbligatoria %>' />
            <ar:TextBox runat="server" ID="Cap" MaxLength="15" IdRisorsa="dettagli-anagrafica.pf.cap-residenza" Label="Cap" BtSize="Col6" Required='<%# ResidenzaObbligatoria %>' />
        </div>
    </fieldset>




    <fieldset id="datiAlbo">
        <legend><ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pf.titolo.elenco-professionale">Dati elenco professionale</ar:RisorsaTestualeLabel></legend>

        <div class="row">
            <ar:DropDownList runat="server" ID="ddlAlbo" IdRisorsa="dettagli-anagrafica.pf.albo-elenco" MaxLength="40" Label="Elenco professionale" Required="true" BtSize="Col6" />
            <ar:TextBox runat="server" ID="txtNumeroAlbo" MaxLength="10" IdRisorsa="dettagli-anagrafica.pf.albo-numero" Label="Numero" Required="true" BtSize="Col2" />
            <ar:Autocomplete runat="server" ID="acProvinciaAlbo" MaxLength="50" IdRisorsa="dettagli-anagrafica.pf.albo-provincia" Label="Provincia" Required="true" BtSize="Col4" />
        </div>
    </fieldset>


    <fieldset class='<%=ClasseCampo(TelefonoVisible || CellulareVisible || EmailVisible || PecVisible ) %>'>
        <legend><ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pf.titolo.contatti">Contatti</ar:RisorsaTestualeLabel></legend>
        <div class="row">
            <ar:TextBox runat="server" ID="Telefono" Pattern="^(\+[\d]{2,3})?[\s]?([0-9\s]+)$" MaxLength="15" IdRisorsa="dettagli-anagrafica.pf.contatti-telefono" Label="Telefono" BtSize="Col4" Visible='<%#TelefonoVisible %>' Required='<%#TelefonoObbligatorio %>' />
            <ar:TextBox runat="server" ID="TelefonoCellulare" Pattern="^(\+[\d]{2,3})?[\s]?([0-9\s]+)$" MaxLength="15" BtSize="Col4" IdRisorsa="dettagli-anagrafica.pf.contatti-cellulare" Label="Cellulare" Visible='<%#CellulareVisible %>' Required='<%#CellulareObbligatorio %>' />
            
        </div>
        <div class="row">
            <ar:TextBox runat="server" ID="email"  type="email" MaxLength="70" IdRisorsa="dettagli-anagrafica.pf.contatti-email" Label="E-Mail" BtSize="Col6" Visible='<%#EmailVisible %>' Required='<%#EmailObbligatoria %>' />
            <ar:TextBox runat="server" ID="emailPec"  type="email"  MaxLength="70" IdRisorsa="dettagli-anagrafica.pf.contatti-pec" Label="PEC" BtSize="Col6" Visible='<%#PecVisible %>' Required='<%#PecObbligatoria %>' />
        </div>
    </fieldset>

    <fieldset class='<%=ClasseCampo(CorrispondenzaVisibile) %>'>
        <legend><ar:RisorsaTestualeLabel runat="server" IdRisorsa="dettagli-anagrafica.pf.titolo.corrispondenza">Indirizzo per la corrispondenza</ar:RisorsaTestualeLabel></legend>
        <div class="row">
            <ar:Autocomplete runat="server" ID="acComuneCorrispondenza" MaxLength="50" BtSize="Col4" IdRisorsa="dettagli-anagrafica.pf.corrispondenza-comune" Label="Comune" Visible='<%#CorrispondenzaVisibile %>' Required='<%#CorrispondenzaObbligatoria %>' />
            <ar:TextBox runat="server" ID="IndirizzoCorrispondenza" MaxLength="100" BtSize="Col8" IdRisorsa="dettagli-anagrafica.pf.corrispondenza-indirizzo" Label="Indirizzo" Visible='<%#CorrispondenzaVisibile %>' Required='<%#CorrispondenzaObbligatoria %>' />
        </div>
        <div class="row">
            <ar:TextBox runat="server" ID="CittaCorrispondenza" MaxLength="50" IdRisorsa="dettagli-anagrafica.pf.corrispondenza-localita" Label="Località/Frazione" BtSize="Col6" Visible='<%#CorrispondenzaVisibile %>' Required='<%#CorrispondenzaObbligatoria %>' />
            <ar:TextBox runat="server" ID="CapCorrispondenza" MaxLength="15" IdRisorsa="dettagli-anagrafica.pf.corrispondenza-cap" Label="CAP" BtSize="Col6" Visible='<%#CorrispondenzaVisibile %>' Required='<%#CorrispondenzaObbligatoria %>' />
        </div>
    </fieldset>
    <div id="tipo-soggetto-bottom"></div>
    <asp:Button ID="cmdConfirm" runat="server" CssClass="btn btn-primary" Text="Conferma" OnClick="cmdConfirm_Click" CausesValidation="true" />
    <asp:LinkButton ID="cmdCancel" CssClass="btn btn-default" runat="server" Text="Annulla" CausesValidation="False"
        OnClick="cmdCancel_Click" />
</div>

<ar:BootstrapModal runat="server" ID="alertValidazioneCf" Title="Verifica del codice fiscale immesso" OkText="Verifica i dati immessi" KoText="Prosegui">
    <ModalBody></ModalBody>
</ar:BootstrapModal>

