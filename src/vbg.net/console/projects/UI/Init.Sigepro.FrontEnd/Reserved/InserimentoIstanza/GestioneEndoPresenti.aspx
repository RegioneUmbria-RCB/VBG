<%@ Page Title="Gestione endo presenti" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
    AutoEventWireup="true" CodeBehind="GestioneEndoPresenti.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoPresenti" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content2" ContentPlaceHolderID="head" runat="server">
    <style media="all">
        .infoEstremiAtto {
            font-style: italic;
        }

        .estremoObbligatorio {
            background: url('<%=ResolveClientUrl("~/images/asterisco.png")%>') no-repeat left center;
        }

        .estremoAtto {
            padding-left: 10px;
        }

        .chk-presente> input[type=checkbox]
        {
            margin-right: 12px;
        }
    </style>

    <script type="text/javascript">
        require(['jquery', 'app/gestione-endo-presenti'], function ($) {
            $(function () {

                $('.dati-endo-presente').gestioneEndoPresenti({
                    webServiceUrl: '<%= ResolveClientUrl("~/reserved/inserimentoIstanza/GestioneEndoPresenti.Scripts.asmx") %>/GetStringaCampiRichiesti?<%=Request.QueryString %>',
                    token: '<%=UserAuthenticationResult.Token %>'
                });
            });
        });
    </script>

</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <div class="form-small">
        <asp:Repeater runat="server" ID="rptEndo">
            <ItemTemplate>
                <fieldset class="blocco fisso dati-endo-presente">
                    <legend>
                        <asp:Literal ID="ltrNomeEndo" runat="server" Text='<%# Eval("Descrizione") %>' />
                    </legend>
                    <asp:HiddenField runat="server" ID="hidIdEndo" Value='<%# Eval("CodiceInventario") %>' />
                    <asp:CheckBox runat="server" ID="chkPresente" Checked='<%# Bind("Presente") %>' Enabled='<%# Bind("TipoTitoloNonObbligatorio") %>' CssClass="chk-presente"
                        Text=" Sono in possesso dell'autorizzazione/titolo abilitativo" TextAlign="Right" />
                    
                    <div class="estremi-atto">
                        <div class="infoEstremiAtto info-estremi-atto">
                        </div>

                        <ar:DropDownList runat="server"
                            ID="ddlTipiTitolo"
                            Label="Tipo titolo"
                            CssClass="ddl-tipo-titolo"
                            DataValueField="Codice"
                            DataTextField="Descrizione"
                            DataSource='<%# Bind( "TitoliSupportati")%>'
                            SelectedValue='<%# Bind("IdTipoTitoloSelezionato") %>'
                            Required="true" />
                        <div class="row">
                            <ar:TextBox runat="server"
                                ID="txtNumeroAtto"
                                Text='<%# Eval("NumeroAtto") %>'
                                MaxLength="15"
                                BtSize="Col3"
                                Label="Numero"
                                CssClass="txt-numero" />

                            <ar:DateTextBox ID="txtDataAtto"
                                runat="server"
                                Label="Del"
                                Text='<%# Eval("DataAtto") %>'
                                CssClass="txt-data"
                                BtSize="Col3" />

                            <ar:TextBox runat="server"
                                ID="txtRilasciatoDa"
                                Text='<%# Eval("RilasciatoDa") %>'
                                BtSize="Col6"
                                Label="Rilasciato da"
                                MaxLength="100"
                                CssClass="txt-rilasciato-da" />
                        </div>

                        <ar:TextBox runat="server"
                            ID="txtNote"
                            Text='<%# Eval("Note") %>'
                            Label="Note"
                            MaxLength="100"
                            TextMode="MultiLine"
                            CssClass="txt-note" />
                    </div>
                </fieldset>
            </ItemTemplate>
        </asp:Repeater>
    </div>
</asp:Content>
