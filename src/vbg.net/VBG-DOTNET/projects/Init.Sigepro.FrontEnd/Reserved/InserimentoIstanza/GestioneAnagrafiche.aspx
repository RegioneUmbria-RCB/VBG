<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" EnableEventValidation="false" AutoEventWireup="true"
    CodeBehind="GestioneAnagrafiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DatiAnagrafici" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="cc2" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="uc1" TagName="DettagliAnagraficaPf" Src="~/Reserved/InserimentoIstanza/DettagliAnagraficaPf.ascx" %>
<%@ Register TagPrefix="uc2" TagName="DettagliAnagraficaPg" Src="~/Reserved/InserimentoIstanza/DettagliAnagraficaPg.ascx" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<asp:Content runat="server" ContentPlaceHolderID="head" ID="headContent">

    <%=LoadScripts(new[] {
        "~/js/app/ricercaComuni.js",
        "~/js/app/ricercaProvincia.js",
        "~/js/app/RicercaGenerica.js",
        "~/js/app/verificaCf.js",
        "~/js/app/ricerca-albi-professionali/ricerca-albo.js",
}) %>

    <script type="text/javascript">
        $(function () {

            $.fn.validator.Constructor.INPUT_SELECTOR = ':input:not([type="hidden"], [type="submit"], [type="reset"], button, [formnovalidate])';
            $('#aspnetForm').validator('update');

        });
    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <asp:ScriptManager runat="server" ID="scriptManager1">
    </asp:ScriptManager>
    <asp:MultiView runat="server" ID="multiview" ActiveViewIndex="0" OnActiveViewChanged="multiview_ActiveViewChanged">
        <asp:View runat="server" ID="listaView">
            <div class="inputForm">
                <fieldset>
                    <asp:GridView runat="server" ID="dgRichiedenti"
                        AutoGenerateColumns="False"
                        DataKeyNames="Id"
                        OnRowDeleting="dgRichiedenti_DeleteCommand"
                        OnSelectedIndexChanged="dgRichiedenti_SelectedIndexChanged"
                        OnRowEditing="dgRichiedenti_EditCommand"
                        OnRowUpdating="dgRichiedenti_UpdateCommand"
                        OnRowCancelingEdit="dgRichiedenti_CancelCommand"
                        GridLines="None"
                        CssClass="table">
                        <Columns>
                            <asp:TemplateField HeaderText="Nominativo">
                                <ItemTemplate>
                                    <asp:Label ID="lblNominativo" runat="server" Text='<%# Eval("Nominativo") %>' />
                                </ItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField HeaderText="In qualità di">
                                <ItemTemplate>
                                    <asp:Label ID="lblInQualitaDi" runat="server" Text='<%# Eval("InQualitaDi") %>' />
                                </ItemTemplate>
                            </asp:TemplateField>

                            <asp:TemplateField HeaderText="Azienda collegata">
                                <ItemTemplate>
                                    <asp:Label ID="lblAziendaCollegata" runat="server" Text='<%# Eval("AziendaCollegata") %>'>
                                    </asp:Label>
                                </ItemTemplate>

                                <EditItemTemplate>
                                    <asp:DropDownList CssClass="form-control" runat="server" ID="ddlAziendeCollegabili" DataSource='<%# Eval("AziendeCollegabili") %>' DataTextField="Value" DataValueField="Key" />
                                </EditItemTemplate>
                            </asp:TemplateField>
                            <asp:TemplateField ItemStyle-HorizontalAlign="Right">
                                <ItemTemplate>
                                    <asp:LinkButton ID="lnkCollega" runat="server" Text='<%# Eval("TestoLinkCollegaAzienda") %>' CommandName="Edit" Visible='<%#Eval("MostraLinkCollegaAzienda") %>' />
                                    <asp:LinkButton ID="LinkButton1" runat="server" Text="Modifica" CommandName="Select" />
                                    <asp:LinkButton ID="LinkButton2" runat="server" Text="Rimuovi" CommandName="Delete" CausesValidation="false" OnClientClick="return confirm('Proseguire con l\'eliminazione?');" />
                                </ItemTemplate>

                                <EditItemTemplate>
                                    <asp:LinkButton ID="lnkConferma" runat="server" Text="Conferma" CommandName="Update" />
                                    <asp:LinkButton ID="lnkAnnulla" runat="server" Text="Annulla" CommandName="Cancel" />
                                </EditItemTemplate>
                            </asp:TemplateField>
                        </Columns>
                    </asp:GridView>

                    <asp:Button ID="cmdNuovo" CssClass="btn btn-primary" runat="server" Text="Aggiungi soggetto" OnClick="cmdNuovo_Click" />
                </fieldset>
            </div>
        </asp:View>

        <asp:View runat="server" ID="nuovoView">
            <style>
                .hidden {
                    display: none !important;
                }
            </style>

            <script type="text/javascript">


                function VerificaCodiceFiscalePartitaIvaObserver(ddltipoPersona, txtCodiceFiscale, cmdSubmit, divPopUp) {
                    this.messaggioErroreCodiceFiscale = "Il codice fiscale immesso non sembra essere valido per un cittadino italiano. Procedere?";
                    this.messaggioErrorePartitaIva = "La prtita iva immessa non sembra essere valida per un'azienda italiana. Procedere?"
                    this.idVerificaCodiceFiscale = "CF";
                    this.idVerificaPartitaIva = "PI";

                    this.verificaCodiceFiscale = verificaCodiceFiscale;
                    this.verificaPartitaIva = verificaPartitaIva;
                    this.mostraPopUpValidazione = mostraPopUpValidazione;

                    this._ddlTipoPersona = ddltipoPersona;
                    this._txtCodiceFiscale = txtCodiceFiscale;
                    this._cmdSubmit = cmdSubmit;
                    this._divPopUp = divPopUp;
                    this._validazioneAttiva = true;

                    var self = this;

                    this._divPopUp.find(".btn-default").on("click", function onCancelClick(e) {
                        //e.preventDefault();
                    });

                    this._divPopUp.find(".modal-ok-button").on("click", function onContinueClick(e) {

                        self._divPopUp.modal("hide");

                        self._validazioneAttiva = false;
                        self._cmdSubmit.click();

                        e.preventDefault();
                    });

                    this._cmdSubmit.click(function (e) {

                        if (!self._validazioneAttiva)
                            return;

                        var tipoVerifica = self._ddlTipoPersona.find("option:selected").data('verifica');

                        if (tipoVerifica === self.idVerificaCodiceFiscale && !self.verificaCodiceFiscale()) {
                            e.preventDefault();
                            self.mostraPopUpValidazione(self.messaggioErroreCodiceFiscale);
                            return;
                        }

                        if (tipoVerifica === self.idVerificaPartitaIva && !self.verificaPartitaIva()) {
                            e.preventDefault();
                            self.mostraPopUpValidazione(self.messaggioErrorePartitaIva);
                            return;
                        }

                    });

                    function verificaCodiceFiscale() {
                        var val = self._txtCodiceFiscale.val();
                        var pattern = /^[a-zA-Z]{6}\d\d[a-zA-Z]\d\d[a-zA-Z]\d\d\d[a-zA-Z]/;

                        if (val.length !== 16)
                            return false;

                        if (!pattern.test(val))
                            return false;

                        return true;
                    }


                    function verificaPartitaIva() {
                        var val = self._txtCodiceFiscale.val();
                        var pattern = /^[0-9]{11}$/;

                        if (val.length === 11 && pattern.test(val))
                            return true;

                        if (!verificaCodiceFiscale())
                            return false;

                        return true;
                    }

                    function mostraPopUpValidazione(messaggio) {

                        self._divPopUp.find(".testoErrore").html(messaggio);
                        self._divPopUp.modal("show");
                    }
                }

                var verificaObserver = null;

                $(function () {
                    // vecchio codice che utilizza ancora jquery
                    verificaObserver = new VerificaCodiceFiscalePartitaIvaObserver(
                        $('#<%= cmbTipoPersona.Inner.ClientID %>'),
                        $('#<%= txtCodiceFiscale.ClientID %>'),
                        $('#<%= cmdCercaCf.ClientID %>'),
                        $("#<%= bmErroreValidazione.ClientID %>")
                    );

                    const mappaTestiPerCF = new Array();
                    const etichette = document.querySelectorAll('.etichettaCFPI');
                    const ddl = document.querySelector('#<%= cmbTipoPersona.Inner.ClientID %>');

                    etichette.forEach(item => {
                        mappaTestiPerCF[item.dataset.verifica] = item;
                    });

                    ddl.addEventListener('change', () => {
                        const selected = ddl.options[ddl.selectedIndex];
                        const verifica = selected.dataset.verifica;
                        const tipoPersona = selected.value;

                        etichette.forEach(item => item.classList.add('hidden'));

                        mappaTestiPerCF[verifica + tipoPersona].classList.remove('hidden');
                    });

                    ddl.dispatchEvent(new Event('change'))
                });
            </script>


            <ar:DropDownList ID="cmbTipoPersona" runat="server" Label="Tipo soggetto" HasFeedback="false">
                <asp:ListItem Value="F" data-verifica="CF">Persona fisica</asp:ListItem>
                <asp:ListItem Value="G" data-verifica="PI">Società</asp:ListItem>
                <asp:ListItem Value="G" data-verifica="CF">Impresa individuale</asp:ListItem>
            </ar:DropDownList>


            <div class="form-group has-feedback">
                <label>
                    <ar:RisorsaTestualeLabel runat="server" ExtraCssclass="etichettaCFPI" data-verifica="CFF" IdRisorsa="gestione-anagrafiche.ricerca.codice-fiscale" Style="display: inline-block">Codice fiscale</ar:RisorsaTestualeLabel>
                    <ar:RisorsaTestualeLabel runat="server" ExtraCssclass="etichettaCFPI" data-verifica="PIG" IdRisorsa="gestione-anagrafiche.ricerca.codice-fiscale-impresa" Style="display: inline-block">Codice fiscale impresa</ar:RisorsaTestualeLabel>
                    <ar:RisorsaTestualeLabel runat="server" ExtraCssclass="etichettaCFPI" data-verifica="CFG" IdRisorsa="gestione-anagrafiche.ricerca.codice-fiscale-titolare" Style="display: inline-block">Codice fiscale titolare</ar:RisorsaTestualeLabel>

                    <sup>*</sup>
                </label>
                <asp:TextBox runat="server" ID="txtCodiceFiscale" CssClass="form-control" required="true" Columns="22" MaxLength="30">
                </asp:TextBox>

                <div class="help-block with-errors"></div>
            </div>


            <div class="bottoni">
                <asp:Button ID="cmdCercaCf" runat="server" Text="Cerca" OnClick="cmdCercaCf_Click" />
                <asp:LinkButton ID="cmdCancelNew" CssClass="btn btn-default" runat="server" Text="Annulla" OnClick="OnEndEdit" formnovalidate />
            </div>

            <div id="popupErrori" style="display: none"></div>
            <ar:BootstrapModal runat="server" ID="bmErroreValidazione" Title="Verifica del codice fiscale / partita iva immesso" KoText="Verifica i dati immessi" OkText="Prosegui">
                <ModalBody>
                    <div class="testoErrore"></div>
                </ModalBody>
            </ar:BootstrapModal>
        </asp:View>
        <asp:View runat="server" ID="personaFisicaView">
            <script type="text/javascript">
                $(function () {
                    $('#aspnetForm').validator('update');
                });
            </script>
            <uc1:DettagliAnagraficaPf runat="server" ID="DettagliPf"></uc1:DettagliAnagraficaPf>
        </asp:View>
        <asp:View runat="server" ID="personaGiuridicaView">
            <uc2:DettagliAnagraficaPg runat="server" ID="DettagliPg"></uc2:DettagliAnagraficaPg>
        </asp:View>
    </asp:MultiView>
</asp:Content>
