<%@ Page Title="Domicilio elettronico" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneDomicilioElettronico.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneDomicilioElettronico" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <script type="text/javascript">
        var ddlDomicilioElettronico;
        var txtAltroIndirizzo;
        var divAltroIndirizzo;

        $(function () {
            ddlDomicilioElettronico = $('#<%=ddlDomicilioElettronico.Inner.ClientID %>');
                txtAltroIndirizzo = $('#<%=txtAltroIndirizzo.Inner.ClientID %>');
            divAltroIndirizzo = $('#altroIndirizzo');

            ddlDomicilioElettronico.change(onDropDownChanged);

            ddlDomicilioElettronico.change();
        });

        function onDropDownChanged() {
            var usaEmailEsistente = ddlDomicilioElettronico.val() !== '-';

            var style = usaEmailEsistente ? 'none' : '';

            if (usaEmailEsistente)
                txtAltroIndirizzo.val('');

            divAltroIndirizzo.css('display', style);
        };
    </script>

    <div class="row">
        <ar:DropDownList runat="server" ID="ddlDomicilioElettronico" Label="Domicilio elettronico" DataValueField="Email" DataTextField="Nominativo" BtSize="Col8" />

        <div id="altroIndirizzo">
            <ar:TextBox runat="server" ID="txtAltroIndirizzo" MaxLength="60" Label="Specificare l'indirizzo da utilizzare" BtSize="Col4" />
        </div>
    </div>
</asp:Content>
