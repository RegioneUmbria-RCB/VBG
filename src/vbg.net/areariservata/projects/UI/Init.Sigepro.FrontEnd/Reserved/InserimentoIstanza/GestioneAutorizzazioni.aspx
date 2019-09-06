<%@ Page Title="Senza titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
    AutoEventWireup="true" CodeBehind="GestioneAutorizzazioni.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneAutorizzazioni" %>

<%@ Register TagPrefix="cc1" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <%=LoadScripts(new[] {
        "~/js/app/stepAutorizzazioni.js",
        "~/js/app/autocompleteHelper.js"
    }) %>

    <script type="text/javascript">
        var settings = {
            autorizzazioniServiceUrl: '<%= ResolveClientUrl("~/Reserved/InserimentoIstanza/GestioneAutorizzazioni.ScriptService.asmx") %>',
            idComune: '<%=IdComune %>',
            software: '<%=Software %>',
            token: '<%=UserAuthenticationResult.Token %>'
        };

        var controlli = {
            ddlAutorizzazioni: $('#<%= ddlAutorizzazioni.ClientID %>'),
            divNumeroPresenzeConAutorizzazione: $('#numeroPresenzeAutorizzazioneTrovata'),
            txtNumeroPresenzeConAutorizzazione: $('#<%=txtNumeroPresenzeDichiarateAutTrovata.ClientID %>'),
            fldstDettagliAutorizzazione: $('#fldstDettagliAutorizzazione'),
            chkEnteNonTrovato: $('#<%=chkEnteNonTrovato.ClientID%>'),
            divNomeEstesoEnte: $('#<%=txtEnteCustom.ClientID%>'),
            hidIdEnte: $('#<%=hidIdEnte.ClientID%>'),
            txtEnte: $('#<%=txtEnte.ClientID%>')
        };

        new GestioneAutorizzazioni(controlli, settings);
    </script>
    <div class="inputForm">
        <fieldset>
            <div>
                <label>
                    Autorizzazioni presenti</label>
                <asp:DropDownList runat="server" ID="ddlAutorizzazioni" DataTextField="Text" DataValueField="Value" />
            </div>
            <div id="numeroPresenzeAutorizzazioneTrovata">
                <label>
                    Numero presenze dichiarate</label>
                <cc1:IntTextBox runat="server" ID="txtNumeroPresenzeDichiarateAutTrovata" Columns="3"
                    MaxLength="3" />
            </div>
        </fieldset>
        <fieldset id="fldstDettagliAutorizzazione">
            <legend>Dettagli autorizzazione</legend>
            <div style="font-style: italic">I campi contrassegnati con * sono obbligatori</div>
            <cc1:LabeledTextBox runat="server" ID="txtNumeroAutorizzazione" Descrizione="Numero" />
            <cc1:LabeledDateTextBox runat="server" ID="txtDataAutorizzazione" Descrizione="Data" />
            <asp:HiddenField runat="server" ID="hidIdEnte" />
            <cc1:LabeledTextBox runat="server" ID="txtEnte" Descrizione="Ente*" Item-Columns="75" />
            <div>
                <label>
                    &nbsp;</label>
                <asp:CheckBox runat="server" ID="chkEnteNonTrovato" />
                <span>L'ente non è presente nella lista</span>
            </div>
            <cc1:LabeledTextBox runat="server" ID="txtEnteCustom" Descrizione="Specificare il nome dell'ente*" Item-Columns="75" />
            <cc1:LabeledIntTextBox runat="server" ID="txtNumeroPresenteAutNonTrovata" Descrizione="Numero presenze dichiarate*"
                Item-Columns="3" Item-MaxLength="3" />
        </fieldset>
    </div>
</asp:Content>
