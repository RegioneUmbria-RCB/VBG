<%@ Page Title="Lista atti" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="accesso-atti-list.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.accesso_atti.accesso_atti_list" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">

    <style>
        .titolo-fascicolo {
            background-color:#f5f5f5;
        }
    </style>
    <script>

        $(function onLoad() {
            $("#<%=gvRisultati.ClientID%>").removeClass("table-striped");

            var currItem = "";

            $("#<%=gvRisultati.ClientID%> thead>tr>th:first-child").remove();

            $("#<%=gvRisultati.ClientID%> .dati-fascicolo").each(function (item, index) {

                var $el = $(this);

                if ($el.text() != currItem) {
                    var oldRow = $el.closest("tr");
                    var newRow = $("<tr class='titolo-fascicolo'/>");

                    currItem = $el.text();

                    $el.parent().attr("colspan", '<%=gvRisultati.Columns.Count-1%>');

                    newRow.append($el.parent());

                    newRow.insertBefore(oldRow);


                } else {
                    $el.parent().remove();
                }

                console.log(item, index);
            });
        });
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <ar:RisorsaTestualeLabel runat="server" ID="descrizioneAccessoAtti" ValoreDefault="Descrizione accesso agli atti" />

    <asp:GridView runat="server" ID="gvRisultati" CssClass="table" GridLines="None" AutoGenerateColumns="false" DataKeyNames="UUID, IdAccessoAtti, CodiceIstanza, MostraDocumentiNonValidi" OnSelectedIndexChanged="gvRisultati_SelectedIndexChanged">

        <Columns>
            <asp:TemplateField HeaderText="Fascicolo">
                <ItemTemplate>
                    <div class="dati-fascicolo">
                    <%# Eval("CodiceIstanzaAccessoAtti") %> del <%# Eval("DataIstanzaAccessoAtti", "{0:dd/MM/yyyy}") %> - <%# Eval("DescrizioneAccessoAtti") %>  
                    </div>
                </ItemTemplate>
            </asp:TemplateField>
            <asp:BoundField DataField="StringaProtocollo" HeaderText="Protocollo" />
            <asp:BoundField DataField="StringaNumeroIstanza" HeaderText="Numero pratica" />
            <asp:BoundField DataField="Localizzazione" HeaderText="Localizzazione" />
            <asp:BoundField DataField="Richiedente" HeaderText="Richiedente" />
            <asp:BoundField DataField="Oggetto" HeaderText="Oggetto" />
            <asp:BoundField DataField="StatoLavorazione" HeaderText="Stato" />
            <asp:BoundField DataField="SoftwareDescrizione" HeaderText="Presentata a" />
            <asp:ButtonField ButtonType="Link" CommandName="Select" Text="Seleziona" />
        </Columns>
        <EmptyDataTemplate>
            <div class="alert alert-info">
                La ricerca non ha restituito risultati
            </div>
        </EmptyDataTemplate>
    </asp:GridView>

        <asp:Button ID="cmdClose" runat="server" CssClass="btn btn-default" Text="Chiudi" OnClick="cmdClose_Click" />

</asp:Content>
