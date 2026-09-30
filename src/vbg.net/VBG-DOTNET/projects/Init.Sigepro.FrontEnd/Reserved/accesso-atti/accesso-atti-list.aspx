<%@ Page Title="Lista atti" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="accesso-atti-list.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.accesso_atti.accesso_atti_list" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">

    <style>
        .titolo-fascicolo {
            background-color: #f5f5f5;
        }
    </style>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <p>
        <ar:RisorsaTestualeLabel runat="server" ID="descrizioneAccessoAtti" ValoreDefault="Descrizione accesso agli atti" />
    </p>
    <asp:Repeater runat="server" ID="rptItems" OnItemDataBound="rptItems_ItemDataBound">

        <ItemTemplate>

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <%#Eval("Descrizione") %>
                    </h3>
                </div>

                <asp:Repeater runat="server" ID="rptPraticheFascicolo">

                    <HeaderTemplate>
                        <table class="table">
                            <thead>
                                <th>Protocollo</th>
                                <th>Numero pratica</th>
                                <th>Localizzazione</th>
                                <th>Richiedente</th>
                                <th>Oggetto</th>
                                <th>Stato</th>
                                <th>Presentata a</th>
                                <th>Seleziona</th>
                            </thead>
                            <tbody>
                    </HeaderTemplate>

                    <ItemTemplate>
                        <tr>
                            <td><%#Eval("StringaProtocollo") %></td>
                            <td><%#Eval("StringaNumeroIstanza") %></td>
                            <td><%#Eval("Localizzazione") %></td>
                            <td><%#Eval("Richiedente")%></td>
                            <td><%#Eval("Oggetto")%></td>
                            <td><%#Eval("StatoLavorazione")%></td>
                            <td><%#Eval("SoftwareDescrizione")%></td>
                            <td><a href='<%#Eval("Link") %>' class="vbg-show-spinner">Seleziona</a></td>
                        </tr>
                    </ItemTemplate>

                    <FooterTemplate>
                        </tbody>
                        </table>
                    </FooterTemplate>
                </asp:Repeater>

            </div>
        </ItemTemplate>

    </asp:Repeater>


    <asp:Button ID="cmdClose" runat="server" CssClass="btn btn-default" Text="Chiudi" OnClick="cmdClose_Click" />

</asp:Content>
