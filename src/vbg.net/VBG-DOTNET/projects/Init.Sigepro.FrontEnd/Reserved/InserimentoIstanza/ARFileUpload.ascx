<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="ARFileUpload.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.ARFileUpload" %>
<asp:MultiView runat="server" ID="multiView">

	<asp:View runat="server" ID="uploadView" >
		<asp:FileUpload runat="server" ID="fuCaricaFile" />
		<asp:LinkButton runat="server" ID="lnkCaricaFile" Text="Allega file" OnClick="lnkCaricaFile_Click" />
	</asp:View>

	<asp:View runat="server" ID="dettagliView" >
		<asp:HyperLink runat="server" ID="hlDownloadFile" />
		<asp:Label runat="server" CssClass="errore" ID="lblErroreFirma" Visible="false" Text="Attenzione, il file non è stato firmato digitalmente" />
		<asp:LinkButton runat="server" ID="lnkFirma" Text="Firma" OnClick="lnkFirma_Click" />
		<asp:LinkButton runat="server" ID="lnkRimuovi" Text="Rimuovi file" OnClick="lnkRimuovi_Click" OnClientClick="return confirm('Rimuovere l\'allegato selezionato?')" />
	</asp:View>

</asp:MultiView>


