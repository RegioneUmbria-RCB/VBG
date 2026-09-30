<%@ Page Title="Untitled" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneDelegaATrasmettere.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneDelegaATrasmettere" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register Src="~/Reserved/InserimentoIstanza/GestioneDelegaATrasmettere-file-view.ascx" TagPrefix="uc1" TagName="GestioneDelegaATrasmetterefileview" %>


<asp:Content runat="server" ContentPlaceHolderID="head">
    <style>
        .spacer {
            margin-top: 18px;
        }
    </style>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <div class="panel panel-primary" id="bloccoCaricamentoBollettino">

        <div class="panel-heading">
            <h3 class="panel-title">
                <asp:Literal runat="server" ID="ltrTestoDelega" />
            </h3>
        </div>

        <div class="panel-body">
            <div>
                <asp:Literal runat="server" ID="ltrLinkDownload" />
            </div>

            <div class="spacer">
            </div>

            <uc1:GestioneDelegaATrasmetterefileview runat="server" id="delegaATrasmettereView" 
                OnEliminaDocumento="delegaATrasmettereView_EliminaDocumento"
                OnFirmaDocumento="delegaATrasmettereView_FirmaDocumento"
                OnFileCaricato="delegaATrasmettereView_CaricaDocumento"/>
        </div>
    </div>

    <%if (this.RichiedeDocumentoIdentita)
        { %>
    <div class="panel panel-primary">

        <div class="panel-heading">
            <h3 class="panel-title">
                <asp:Literal runat="server" ID="ltrTitoloDocumentoIdentita" />
            </h3>
        </div>

        <div class="panel-body">
            <div>
                <asp:Literal runat="server" ID="ltrTestoDocumentoIdentita" />
            </div>

            <div class="spacer">
            </div>

            <uc1:GestioneDelegaATrasmetterefileview runat="server" id="documentoIdentitaView" 
                OnEliminaDocumento="documentoIdentitaView_EliminaDocumento"
                OnFirmaDocumento="documentoIdentitaView_FirmaDocumento"
                OnFileCaricato="documentoIdentitaView_FileCaricato"/>
        </div>
    </div>
    <%} %>


</asp:Content>
