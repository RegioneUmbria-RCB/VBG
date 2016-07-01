<%@ Page Title="Compila allegato" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="Edit.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.EditOggetti.Edit" %>
<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
	<script type="text/javascript">
		function returnToPage(){
			$('.bottoni').hide();
			location.replace('<%=GetReturnToUrl()%>');
		}	
	</script>

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
<fieldset>
	<div class="istruzioni-upload">
		<ol>
			<li>
				<div class="main">Il documento verrà aperto con l'editor di sistema predefinito in base al tipo di file</div>
				<div class="second">ad esempio: .doc -> Microsoft Office, .odt -> Open Office</div>
			</li>

			<li>
				<div class="main">Salvare il documento utilizzando l'icona Salva o dal menu File/Salva a seconda dell'editor utilizzato</div>
				<div class="second">NON modificare il percorso ed il nome del file, NON utilizzare "Salva con nome..."</div>
			</li>

			<li>
				<div class="main">Completare l'operazione ricaricando sul server il documento modificato attraverso il bottone "Carica file modificato"</div>
			</li>
		</ol>
	</div>
 

	<applet 
				name="Edit Docs Applet"
				code="it.gruppoinit.pal.gp.backoffice.applets.EditDocsApplet" 
				codebase="Applet/" 
				archive="init-editdocs-applet.jar"
				height="100" 
				width="800" 
				align="middle" style="padding: 2px;"><p>Questo browser non supporta le Java Applet.</p>
				<param name="debug" value="true" />
				<param name="callJsCloseEditDocs" value="true" />
				<param name="callJsCloseEditDocsFunc" value="returnToPage" />
				<param name="labelBtnInviaModifiche" value='Carica file modificato' />
				<param name="labelDownloadCompletato" value='Download completato' />
				<param name="urlOggetti" value="<%=GetUrlDownload() %>" />
				<param name="urlUploadOggetti" value="<%=GetUrlUpload() %>" />
	</applet>

	<div class="bottoni">
		<asp:Button runat="server" ID="cmdChiudi" Text="Annulla" OnClientClick="returnToPage();return false;" />
	</div>
</fieldset>

</asp:Content>
