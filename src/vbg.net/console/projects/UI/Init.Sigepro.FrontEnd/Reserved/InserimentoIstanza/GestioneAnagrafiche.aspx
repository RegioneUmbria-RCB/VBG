<%@ Page Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" EnableEventValidation="false" AutoEventWireup="true"
    CodeBehind="GestioneAnagrafiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.DatiAnagrafici" Title="Untitled Page" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="cc2" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="uc1" TagName="DettagliAnagraficaPf" Src="~/Reserved/InserimentoIstanza/DettagliAnagraficaPf.ascx" %>
<%@ Register TagPrefix="uc2" TagName="DettagliAnagraficaPg" Src="~/Reserved/InserimentoIstanza/DettagliAnagraficaPg.ascx" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<asp:Content runat="server" ContentPlaceHolderID="head" ID="headContent">
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
                                    <asp:LinkButton ID="lnkCollega" runat="server" Text='<%# Eval("TestoLinkCollegaAzienda") %>' CommandName="Edit" Visible='<%#Eval("MostraLinkCollegaAzienda") %>' style="margin-right: 15px;" />
                                    <asp:LinkButton ID="LinkButton1" runat="server" Text="Modifica" CommandName="Select" style="margin-right: 15px;" />
                                    <asp:LinkButton ID="LinkButton2" runat="server" Text="Rimuovi" CommandName="Delete" CausesValidation="false" OnClientClick="return confirm('Proseguire con l\'eliminazione?');" />
                                </ItemTemplate>

                                <EditItemTemplate>
                                    <asp:LinkButton ID="lnkConferma" runat="server" Text="Conferma" CommandName="Update" />
                                    <asp:LinkButton ID="lnkAnnulla" runat="server" Text="Annulla" CommandName="Cancel" />
                                </EditItemTemplate>
                            </asp:TemplateField>
                        </Columns>
                    </asp:GridView>

                    <div class="bottoni">
                        <asp:Button ID="cmdNuovo" runat="server" Text="Aggiungi soggetto" OnClick="cmdNuovo_Click" />
                    </div>
                </fieldset>
            </div>
        </asp:View>
        <asp:View runat="server" ID="nuovoView">
            <script type="text/javascript">

                require(['jquery', 'jquery.ui'], function ($) {

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
                            /*
                            self._divPopUp.html(messaggio);

                            self._divPopUp.dialog({
                                resizable: false,
                                title: 'Verifica del codice fiscale / partita iva immesso',
                                width: 550,
                                modal: true,
                                buttons: {
                                    "Verifica i dati immessi": function () {
                                        $(this).dialog("close");
                                    },
                                    "Prosegui": function () {
                                        $(this).dialog("close");
                                        self._validazioneAttiva = false;
                                        self._cmdSubmit.click();
                                    }
                                }
                            });
                            */
                            self._divPopUp.find(".testoErrore").html(messaggio);
                            self._divPopUp.modal("show");
                        }
                    }

                    var verificaObserver = null;

                    $(function () {
                        var ddlTipoPersona = $('#<%= cmbTipoPersona.Inner.ClientID %>');
					    var txtCodiceFiscale = $('#<%= txtCodiceFiscale.Inner.ClientID %>');
					    var cmdSubmit = $('#<%= cmdCercaCf.ClientID %>');
					    var divPopUp = $("#<%=bmErroreValidazione.ClientID%>")//$('#popupErrori');

					    verificaObserver = new VerificaCodiceFiscalePartitaIvaObserver(ddlTipoPersona, txtCodiceFiscale, cmdSubmit, divPopUp);
                        //verificaObserver = new VerificaCodiceFiscalePartitaIvaObserver(ddlTipoPersona, txtCodiceFiscale, cmdSubmit, divPopUp);

                        
					    ddlTipoPersona.on('change', function () {
					        var lbl = $('#<%=txtCodiceFiscale.Inner.ClientID%>').parent().find('label'),
                                etichetta = ddlTipoPersona.find('option:selected').data('cf');

						    lbl.html(etichetta);
						});
					});
				});
            </script>




            <ar:DropDownList ID="cmbTipoPersona" runat="server" Label="Tipo soggetto" HasFeedback="false">
                <asp:ListItem Value="F" data-cf="Codice fiscale<sup>*</sup>" data-verifica="CF">Persona fisica</asp:ListItem>
                <asp:ListItem Value="G" data-cf="Codice fiscale impresa<sup>*</sup>" data-verifica="PI">Società</asp:ListItem>
                <asp:ListItem Value="G" data-cf="Codice fiscale titolare<sup>*</sup>" data-verifica="CF">Impresa individuale</asp:ListItem>
            </ar:DropDownList>

            <ar:TextBox runat="server" ID="txtCodiceFiscale" Columns="22" MaxLength="30" Label="Codice fiscale" Required />

            <div>
                <asp:Button ID="cmdCercaCf" CssClass="btn btn-primary" runat="server" Text="Cerca" OnClick="cmdCercaCf_Click" />
                <asp:LinkButton ID="cmdCancelNew" CssClass="btn btn-default" runat="server" Text="Annulla" OnClick="OnEndEdit" formnovalidate />
            </div>

            <div id="popupErrori" style="display: none"></div>
            <ar:BootstrapModal runat="server" ID="bmErroreValidazione" Title="Verifica del codice fiscale / partita iva immesso" KoText="Verifica i dati immessi" OkText="Prosegui" >
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
