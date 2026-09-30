<%@ Page Title="Pratiche in sospeso (CONSOLE)" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="vbg-istanze-in-sospeso.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.vbg_istanze_in_sospeso" %>

<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
    
    <style>
        .trasferimento {
            margin-top: 10%;
        }

        .trasferimento .progress {
            margin-bottom: 12px;
        }

        .trasferimento .panel {
            max-width: 60%;
            margin: 0 auto;
        }

        .trasferimento .panel-heading > h3 {
            font-weight: bold;
        }

        .trasferimento .panel-body {
            line-height: 2em;
        }
    </style>

</asp:Content>


<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View ID="listaView" runat="server">

            <h1>
                <ar:RisorsaTestualeLabel runat="server" ID="titoloPagina" IdRisorsa="vbg_istanze_in_sospeso.titolo_pagina">Pratiche in sospeso: selezione comune</ar:RisorsaTestualeLabel>
            </h1>
            <p>
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="vbg_istanze_in_sospeso.descrizione_pagina">In questa sezione è possibile selezionare il comune, tra quelli che hanno attivato il servizio, presso cui riprendere una domanda in sospeso</ar:RisorsaTestualeLabel>
            </p>

            <asp:Repeater runat="server" ID="rptListaUrl">
                <HeaderTemplate>
                    <ul>
                </HeaderTemplate>

                <ItemTemplate>
                    <li>
                        <asp:LinkButton
                            runat="server"
                            OnClick="onComuneSelezionato"
                            CommandArgument='<%#Eval("CodiceComune") %>'
                            Text='<%#Eval("Comune") %>'
                            Visible='<%#Eval("ServizioAttivo") %>' />

                        <asp:Label
                            runat="server"
                            Text='<%#Eval("Comune", "{0} (servizio non attivo)") %>'
                            Visible='<%#!(bool)Eval("ServizioAttivo") %>' />
                    </li>
                </ItemTemplate>

                <FooterTemplate>
                    </ul>
                </FooterTemplate>
            </asp:Repeater>
        </asp:View>


        <asp:View ID="redictView" runat="server">

            <script type="text/javascript">
                ready(() => {
                    const el = document.getElementsByClassName('redir-link');
                    if (el && el.length) el[0].click();
                });
            </script>

            <div class="trasferimento">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Trasferimento in corso</h3>
                    </div>
                    <div class="panel-body">

                        <div class="progress progress-striped active">
                            <div class="progress-bar" style="width: 100%"></div>
                        </div>

                        In pochi secondi verrai automaticamente trasferito alla pagina richiesta.<br />
                        Fai click su
                        <asp:HyperLink runat="server" CssClass="redir-link" ID="hlRedirUrl">questo link</asp:HyperLink>
                        se non vuoi attendere
                    </div>
                </div>

            </div>
        </asp:View>
    </asp:MultiView>
</asp:Content>
