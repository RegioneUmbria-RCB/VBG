<%@ Page Title="Compila allegato" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="Edit.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.EditOggetti.Edit" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
    <script type="text/javascript">
        function returnToPage() {
            $('.bottoni').hide();
            location.replace('<%=GetReturnToUrl()%>');
        }
    </script>
    <style>
        .second {font-style: italic}

        .istruzioni-upload li {
            margin-bottom: 1.0em;
        }
    </style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <fieldset>
        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="istruzioni-compilazione-online">
	        <div class="istruzioni-upload" >
                <h3>Istruzioni per la compilazione on-line</h3>

		        <ol>
                    <li>
                        <div class="main">
                            Scaricare <a href='<%=GetUrlDownloadJnlp() %>'>l'applet per la modifica file</a>.
                            <div class="second">Per poter eseguire correttamente l'applet è necessario avere intallato Java nella versione 1.6 o successiva. E'possibile scaricare Java all'indirizzo <a href="https://java.com/it/download/">https://java.com/it/download/</a>.</div>
                        </div>
                    </li>

                    <li>
                        <div class="main">Eseguire il file scaricato per avviare il processo di compilazione</div>
                    </li>

			        <li>
				        <div class="main">Il documento verrà aperto con l'editor di sistema predefinito in base al tipo di file</div>
				        <div class="second">ad esempio: .doc -> Microsoft Office, .odt -> Open Office</div>
			        </li>

			        <li>
				        <div class="main">Salvare il documento utilizzando l'icona Salva o dal menu File/Salva a seconda dell'editor utilizzato</div>
				        <div class="second">NON modificare il percorso ed il nome del file, NON utilizzare "Salva con nome..."</div>
			        </li>

			        <li>
				        <div class="main">Completare l'operazione ricaricando sul server il documento modificato attraverso il bottone "Invia modifiche"</div>
			        </li>
                    <li>
				        <div class="main">Al termine dell'operazione fare click sul bottone "Ho teminato" per tornare alla compialzione della domanda</div>
			        </li>
		        </ol>
	        </div>
        </ar:RisorsaTestualeLabel>

        <div>
            <a class="btn btn-primary" href='<%=GetUrlDownloadJnlp() %>'>Scarica applet per la modifica file</a>
            <asp:Button runat="server" CssClass="btn btn-primary" ID="cmdChiudi" Text="Ho terminato" OnClientClick="returnToPage();return false;" />
        </div>
    </fieldset>

</asp:Content>
