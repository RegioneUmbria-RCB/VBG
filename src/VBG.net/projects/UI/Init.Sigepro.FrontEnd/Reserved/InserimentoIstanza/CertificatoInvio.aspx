<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true"
	CodeBehind="CertificatoInvio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.CertificatoInvio"
	Title="Domanda inviata con successo" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <fieldset>
			<div class="descrizioneStep">
                <asp:Literal runat="server" ID="ltrDescrizione" />
            </div>
    </fieldset>

	<div class="riepilogoDomandaHtml" id="riepilogoDomanda" style="overflow-y: hidden;height:500px">
		<object data='<%=UrlDownloadRiepilogo%>' type="application/pdf" width="100%" height="100%">
			<div style="width: 100%; text-align: center">
				<p>
					Sembra che nel browser in uso non sia installato un plugin per la visualizzazione
					dei files in formato pdf</p>
			</div>
		</object>
	</div>
	<br />
	Nel caso in cui il certificato non venisse visualizzato correttamente è comunque
	possibile scaricarlo da <a href='<%=UrlDownloadRiepilogo%>'>questo link</a>
</asp:Content>
