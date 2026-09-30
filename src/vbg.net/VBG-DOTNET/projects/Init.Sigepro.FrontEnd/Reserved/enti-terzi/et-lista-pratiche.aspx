<%@ Page Title="Pratiche di competenza" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="et-lista-pratiche.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.enti_terzi.et_lista_pratiche" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
    <script type="text/javascript">

        $(function onLoad() {
            $("#<%=gvRisultati.ClientID%> a, #<%=cmdCerca.ClientID%>").on("click", mostraModalCaricamento);


        });

    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <asp:MultiView ActiveViewIndex="0" runat="server" ID="multiView">
        <asp:View runat="server">

            <div class="row">
                <ar:DateTextBox runat="server" ID="dtbDallaData" Label="Dalla data" BtSize="Col6" />
                <ar:DateTextBox runat="server" ID="dtbAllaData" Label="Alla data" BtSize="Col6" />
            </div>

            <div class="row">
                <ar:TextBox runat="server" ID="txtNumeroProtocollo" Label="Numero protocollo" BtSize="Col6" />
                <ar:TextBox runat="server" ID="txtNumeroPratica" Label="Numero pratica" BtSize="Col6" />
            </div>


            <div class="row">
                <ar:DropDownList runat="server" ID="ddlSoftware" Label="Modulo di competenza" BtSize="Col6" />
                <ar:DropDownList runat="server" ID="ddlElaborata" Label="Elaborata" BtSize="Col6" />
            </div>

            <div>
                <asp:Button Text="Chiudi" ID="cmdChiudi" runat="server" OnClick="cmdChiudi_Click" CssClass="btn btn-default" />
                <asp:Button Text="Cerca" ID="cmdCerca" runat="server" OnClick="cmdCerca_Click" CssClass="btn btn-primary" />
            </div>
        </asp:View>
        <asp:View runat="server">
            <style>
                .ellipsis {
                    max-width: 200px;
                    overflow-x: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                }
            </style>

            <script type="text/javascript">
                $(() => {
                    document.querySelectorAll('.ellipsis').forEach(item => {
                        item.setAttribute('title', item.innerText);
                    });

                    $('#<%=gvRisultati.ClientID%>').dataTable({
                        "order": [
                            [0, 'desc']
                        ]
                    });


                });
            </script>

            <asp:GridView runat="server" ID="gvRisultati" CssClass="table" GridLines="None" AutoGenerateColumns="false" OnRowDataBound="gvRisultati_RowDataBound" DataKeyNames="UUID" OnSelectedIndexChanged="gvRisultati_SelectedIndexChanged">

                <Columns>
                    <asp:BoundField DataField="StringaNumeroIstanza" HeaderText="Numero pratica" />
                    <asp:BoundField DataField="StringaProtocollo" HeaderText="Protocollo" />
                    <asp:BoundField DataField="Localizzazione" HeaderText="Localizzazione" />
                    <asp:BoundField DataField="Richiedente" HeaderText="Richiedente" />
                    <asp:BoundField DataField="Oggetto" HeaderText="Oggetto" ItemStyle-CssClass="ellipsis" />
                    <asp:BoundField DataField="StatoLavorazione" HeaderText="Stato" />
                    <asp:BoundField DataField="Modulo" HeaderText="Presentata a" ItemStyle-CssClass="ellipsis" />
                    <asp:ButtonField ButtonType="Link" CommandName="Select" Text="Seleziona" />
                </Columns>
                <EmptyDataTemplate>
                    <div class="alert alert-info">
                        La ricerca non ha restituito risultati
                    </div>
                </EmptyDataTemplate>
            </asp:GridView>

            <div>
                <asp:Button Text="Nuova ricerca" ID="cmdNuovaRicerca" runat="server" OnClick="cmdNuovaRicerca_Click" CssClass="btn btn-default" />
            </div>
        </asp:View>
    </asp:MultiView>

</asp:Content>
