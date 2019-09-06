<%@ Page Title="Firma tramite CID/PIN" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="Firma.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.FirmaCidPin.Firma" %>
<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
    <script type="text/javascript">
        function mostraOggetto() {
			window.open('<%=GetUrlMostraOggetto()%>');

			return false;
        }

        require(['jquery', 'app/uploadAllegati'], function ($, UploadAllegati) {
        	function processaFirma() {
        		$('.bottoni').hide(function () {
        			$('#invioInCorso').show();
        		});
        	}

        	$(function () {
        		$('.bottoneFirma').on('click', processaFirma);
        	});
        });
    </script>

	<style media="all">
		#preview
		{
			width: 100%;
			height: 400px;
		}
		
		.no-close .ui-dialog-titlebar-close {
		  display: none;
		}
		
		.ion-load-b
		{
			-webkit-animation: spin 3s infinite linear;
			-moz-animation: spin 3s infinite linear;
			-o-animation: spin 3s infinite linear;
			animation: spin 3s infinite linear;
			font-size: 1.5em;
		}
		
		.ui-dialog-title>.ion-load-b
		{
			font-size: 1.2em;
		}
		
		.ion-alert-circled
		{
			font-size: 1.5em;
		}
		
		@-moz-keyframes spin {
			from { -moz-transform: rotate(0deg); }
			to { -moz-transform: rotate(360deg); }
		}
		@-webkit-keyframes spin {
			from { -webkit-transform: rotate(0deg); }
			to { -webkit-transform: rotate(360deg); }
		}
		@keyframes spin {
			from {transform:rotate(0deg);}
			to {transform:rotate(360deg);}
		}
	</style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<div class="inputForm">
		<fieldset>
		
        <asp:MultiView runat="server" ID="multiView">
            <asp:View runat="server" ID="firmaView">
                <div>
                    <label>CID</label>
                    <asp:TextBox runat="server" ID="txtCid" />
                </div>

                <div>
                    <label>PIN</label>
                    <asp:TextBox runat="server" ID="txtPin" TextMode="Password" />
                </div>
            
                <div class="bottoni">
                    <asp:Button runat="server" ID="cmdFirma" Text="Firma" class="bottoneFirma" OnClick="cmdFirma_Click" />
                    <asp:Button runat="server" ID="cmdVisualizzaFile" Text="Visualizza file" OnClientClick="return mostraOggetto();"/>
                    <asp:Button runat="server" ID="Button1" Text="Annulla" OnClick="GoBackToCallingPage"/>
                </div>

				<div class="messaggioInvio" id="invioInCorso" style="display: none;">
					Verifica del file in corso, l'operazione potrebbe richiedere anche alcuni minuti. Si prega di attendere senza interagire con il browser
				</div>
            </asp:View>

            <asp:View runat="server" ID="erroreView">
                <div class="warningEndo">
                    <%--<span class="icon-warning ion-alert-circled"></span>--%>
                    <asp:Image runat="server" ID="imgWarning" ImageUrl="~/Images/warning-icon.png" class="iconaWarning" />

                    <div class="contenutoWarning">
                        <h3>Scheda "<%= GetNomeSchedaDinamica() %>" non compilata</h3>
                        Per poter procedere alla firma tramite CID/PIN occorre compilare la scheda "<%= GetNomeSchedaDinamica() %>" presente nello step <%= (GetIndiceSchedaDinamica() + 1).ToString() %>.<br />
                        <br />
                        Premere "Torna alla scheda" per tornare allo step <%= (GetIndiceSchedaDinamica() + 1).ToString() %> per compilare la scheda. Altrimenti premete "Annulla" per selezionare un altro metodo di firma.
                    </div>


                </div>

				<div class="bottoni">
					<asp:Button runat="server" ID="cmdVaiAllaScheda" Text="Torna alla scheda" OnClick="cmdVaiAllaScheda_Click"/>
					<asp:Button runat="server" ID="cmdChiudi" Text="Annulla" OnClick="GoBackToCallingPage"/>
				</div>
            </asp:View>

        </asp:MultiView>
        </fieldset>
    </div>

</asp:Content>
