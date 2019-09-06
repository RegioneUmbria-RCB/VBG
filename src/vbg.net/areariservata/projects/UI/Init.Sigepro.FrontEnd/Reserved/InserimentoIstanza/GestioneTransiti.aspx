<%@ Page Title="Ricerca autorizzazione" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneTransiti.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneTransiti" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">

    <div class="row">
        <ar:TextBox runat="server" ID="txtNumeroAutorizzazione" Label="Numero autorizzazione" BtSize="Col3" />
        <ar:DateTextBox runat="server" ID="txtDataAutorizzazione" Label="Data autorizzazione" BtSize="Col3" />
    </div>

    <div>
        <asp:Button Text="Ricerca" ID="cmdRicerca" CssClass="btn btn-primary" runat="server" OnClick="cmdRicerca_Click" />
    </div>

    <asp:Panel ID="pnlRisultati" runat="server" Visible="false">
        <asp:HiddenField runat="server" ID="hidCodiceIstanza" />

        <div class="transiti-autorizzazioni">

            <div class="dati-autorizzazione">
                <div class="riferimenti">
                    <h2>Autorizzazione 
                        <asp:Literal Text="" ID="ltrRiferimentiAutorizzaizone" runat="server" />
                    </h2>

                    <div class="row">
                        <ar:LabeledLabel runat="server" ID="lblDataInizioValidita" Label="Valida dal" BtSize="Col3" />
                        <ar:LabeledLabel runat="server" ID="lblDataFineValidita" Label="Valida al" BtSize="Col3" />
                    </div>
                </div>

                <% if (MostraAutorizzazioniRimanenti) {%>
                <div class="autorizzazioni-rimanenti">
                    <div>
                        <div class="numero-rimanenti">
                            <asp:Literal Text="" ID="ltrAutorizzazioniRimanenti" runat="server" />
                        </div>

                        Rimanenti
                    </div>
                </div>
                <%} %>
            </div>

            <div class="operazioni">
                <asp:GridView runat="server" ID="gvOperazioni" AutoGenerateColumns="false" GridLines="None" CssClass="table">
                    <Columns>
                        <asp:BoundField HeaderText="Tipo" DataField="Tipo" />
                        <asp:BoundField HeaderText="Pratica" DataField="Istanza" />
                        <asp:BoundField HeaderText="Protocollo" DataField="Protocollo" />
                        <asp:BoundField HeaderText="Stato" DataField="Stato" />
                        <asp:BoundField HeaderText="Data Aut.Op." DataField="DataAutorizzazione" DataFormatString="{0:dd/MM/yyyy}" />
                    </Columns>
                </asp:GridView>
            </div>

        </div>
    </asp:Panel>

</asp:Content>
