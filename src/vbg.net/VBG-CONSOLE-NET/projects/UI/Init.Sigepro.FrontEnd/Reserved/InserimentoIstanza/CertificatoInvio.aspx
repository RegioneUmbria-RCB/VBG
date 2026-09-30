<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true"
    CodeBehind="CertificatoInvio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CertificatoInvio"
    Title="Domanda inviata con successo" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">


    <div runat="server" id="divRedirect" class="panel panel-info">
        <div class="panel-heading">
            <asp:Literal runat="server" ID="ltrRedirectTitolo" />
        </div>
        <div class="panel-body">
            <asp:Literal runat="server" ID="ltrRedirectMessaggio" />

            <div style="padding-top: 15px;">
                <asp:Button runat="server" ID="cmdRedirectProcedi" CssClass="btn btn-primary" OnClick="cmdRedirectProcedi_Click" />
            </div>
        </div>
    </div>

    <fieldset>
        <div class="descrizioneStep">
            <asp:Literal runat="server" ID="ltrDescrizione" />
        </div>
    </fieldset>
    <div class="inputForm" style="padding-top: 15px;">
        <fieldset>
            <div class="riepilogoDomandaHtml" id="riepilogoDomanda" style="overflow-y: hidden; height: 500px">

                <iframe id="iFrameDomanda" class="riepilogo-domanda-html" src='<%= UrlVisualizzazioneRiepilogo %>'></iframe>

            </div>
        </fieldset>
    </div>
    <div style="padding-top: 15px;">
        Nel caso in cui il certificato non venisse visualizzato correttamente è comunque
	    possibile scaricarlo da <a href='<%=UrlDownloadRiepilogo%>'>questo link</a>
    </div>

</asp:Content>
