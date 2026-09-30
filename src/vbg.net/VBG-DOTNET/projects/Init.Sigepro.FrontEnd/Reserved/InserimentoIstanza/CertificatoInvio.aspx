<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true"
    CodeBehind="CertificatoInvio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CertificatoInvio"
    Title="Domanda inviata con successo" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

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

    <div runat="server" id="divQuestionarioGradimento" class="alert alert-info">

        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="certificato-invio.questionario.label">
            <h2>Indagine per rilevare periodicamente il grado di soddisfazione delle iniziative e dei servizi offerti alla cittadinanza. </h2>
            Le chiediamo di compilare il seguente questionario che ci sarà utile per conoscere l’opinione dei cittadini sui servizi utilizzati 
            al fine di migliorarne la qualità e il livello di soddisfazione del servizio.<br />
            La compilazione richiede circa un minuto e al termine potrà tornare alla pratica appena presentata.
        </ar:RisorsaTestualeLabel>

        <asp:Button runat="server" ID="cmdCompilaQuestionario" CssClass="btn btn-primary vbg-show-spinner" OnClick="cmdCompilaQuestionario_Click" Text="Compila il questionario" style="margin-top: 24px" />
    </div>

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
