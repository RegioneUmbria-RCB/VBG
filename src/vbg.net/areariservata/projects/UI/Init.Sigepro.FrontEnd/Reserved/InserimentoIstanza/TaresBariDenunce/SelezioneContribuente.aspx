<%@ Page Title="Selezione contribuente" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="SelezioneContribuente.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce.SelezioneContribuente" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="uc1" TagName="UtenzaSelezionabileItem" Src="~/Reserved/InserimentoIstanza/TaresBariDenunce/UtenzaSelezionabileItem.ascx" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">

    <script type="text/javascript">
        $(function () {

            if ($('#<%=txtIdContribuente.ClientID %>').val() == '') {
                    $('.ricerca-utenza').hide();
                } else {
                    $('.tipo-utenza').hide();
                }

                $('#<%=cmdRicercaUtenza.ClientID %>').on('click', function (e) {

                    $('.tipo-utenza').hide();
                    $('.ricerca-utenza').show();

                    e.preventDefault();
                });

                $('#<%=cmdAnnullaRicerca.ClientID %>').on('click', function (e) {

                $('.tipo-utenza').show();
                $('.ricerca-utenza').hide();

                e.preventDefault();
            });
        });
    </script>



</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <div class="inputForm">
        <fieldset>

            <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">

                <asp:View runat="server" ID="viewRicerca">

                    <div class="tipo-utenza">
                        Specificare se il contribuente per cui si intende effettuare la domanda è un nuovo contribuente oppure un contribuente esistente (un contribuente esistente possiede un identificativo contribuente).
                    </div>

                    <div class="bottoni tipo-utenza">
                        <asp:Button runat="server" ID="cmdRicercaUtenza" Text="Sono un contribuente esistente" />
                        <asp:Button runat="server" ID="cmdNuovaUtenza" Text="Sono un nuovo contribuente"
                            OnClick="cmdNuovaUtenza_Click" />
                    </div>

                    <div class="ricerca-utenza">
                        <legend>Ricerca contribuente</legend>

                        <div>
                            <label>Identificativo contribuente (*)</label>
                            <asp:TextBox runat="server" ID="txtIdContribuente" Columns="20" MaxLength="6" />
                        </div>
                        <div>
                            <label>Codice fiscale o partita iva</label>
                            <asp:TextBox runat="server" ID="txtPartitaIvaCf" Columns="20" MaxLength="16" />
                        </div>
                        <div class="bottoni">
                            <asp:Button runat="server" ID="cmdCerca" Text="Cerca"
                                OnClick="cmdCerca_Click" />
                            <asp:Button runat="server" ID="cmdAnnullaRicerca" Text="Annulla" />
                        </div>
                    </div>

                </asp:View>

                <asp:View runat="server" ID="viewDettaglio">
                    <uc1:UtenzaSelezionabileItem runat="server" ID="utenzaSelezionabileItem" OnUtenzaSelezionata="UtenzaSelezionata" />

                    <div class="bottoni">
                        <asp:Button runat="server" ID="cmdSelezionacontribuente" Text="Seleziona un altro contribuente" OnClick="cmdSelezionacontribuente_Click" />
                    </div>
                </asp:View>

            </asp:MultiView>

        </fieldset>



    </div>


</asp:Content>
