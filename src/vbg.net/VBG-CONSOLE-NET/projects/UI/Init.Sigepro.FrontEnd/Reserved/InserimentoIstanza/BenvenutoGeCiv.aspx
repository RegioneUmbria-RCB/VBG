<%@ Page Title="" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="BenvenutoGeCiv.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.BenvenutoGeCiv" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server">


            <div class="descrizioneStep">
                <asp:Literal runat="server" ID="ltrTestoListaStep"></asp:Literal>
            </div>

            <asp:Panel runat="server" ID="pnlSelezioneComune">
                <h2 style="width: 100%; text-align: center">
                    <asp:Literal runat="server" ID="lblComuniAssociati">Selezionare il comune per cui si vuole presentare l'istanza</asp:Literal>
                </h2>

                <div class="row">
                    <div class="col-md-4"></div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <asp:DropDownList ID="cmbComuni" runat="server" CssClass="form-control" DataTextField="Value" DataValueField="Key" required />
                            <div class="help-block with-errors"></div>
                        </div>
                    </div>
                    <div class="col-md-4"></div>
                </div>
            </asp:Panel>

        </asp:View>

        <asp:View runat="server">
            <div class="alert alert-danger" role="alert">
                <strong>ATTENZIONE!</strong> Non si è abilitati alla presentazione telematiche di istanze da questo portale.<br />
                L’utilizzo del portale è riservato, in questa fase sperimentale, ai professionisti segnalati dai rispettivi ordini professionali.<br />
                Se si è tra i “tecnici sperimentatori” l’abilitazione avverrà entro poche ore.
            </div>
        </asp:View>

    </asp:MultiView>



</asp:Content>
