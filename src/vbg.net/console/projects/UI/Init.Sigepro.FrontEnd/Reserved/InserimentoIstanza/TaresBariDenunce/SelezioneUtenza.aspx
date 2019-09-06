<%@ Page Title="Titolo" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="SelezioneUtenza.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.TaresBariDenunce.SelezioneUtenza" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="uc1" TagName="DettagliUtenza" Src="~/Reserved/InserimentoIstanza/TaresBariDenunce/DettagliUtenza.ascx" %>
<%@ Register TagPrefix="uc2" TagName="SelezioneUtenzeMultiple" Src="~/Reserved/InserimentoIstanza/TaresBariDenunce/SelezioneUtenzeMultiple.ascx" %>


<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
	
	<script type="text/javascript">
		
	</script>

</asp:Content>


<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
	<div class="inputForm">
		
        

		<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		
			<asp:View runat="server" ID="viewRicerca">
		
                <asp:Literal runat="server" ID="ltrTestoRicerca" />
        		<br />
                <br />
                <fieldset>

					<legend>Ricerca contribuente</legend>

					<div>
						<label>Identificativo contribuente (*)</label>
						<asp:TextBox runat="server" ID="txtIdContribuente" Columns="20" MaxLength="6" />
					</div>
					<div>
						<label>Codice fiscale o partita iva</label>
						<asp:TextBox runat="server" ID="txtPartitaIvaCf" Columns="20" MaxLength="16" />
					</div>
					<div class="bottoni">
						<asp:Button runat="server" ID="cmdCerca" Text="Cerca" onclick="cmdCerca_Click" />
					</div>

                </fieldset>

                <uc2:SelezioneUtenzeMultiple runat="server" id="selezioneUtenzeMultiple" OnErrore="selezioneUtenzeMultiple_Errore" OnUtenzeSelezionate="selezioneUtenzeMultiple_UtenzeSelezionate" />
		
			</asp:View>

			<asp:View runat="server" ID="viewDettaglio">

                <asp:Literal runat="server" ID="ltrTestoDettaglio" />
                <br />
                <br />
				<uc1:DettagliUtenza runat="server" ID="utenzaSelezionabileItem" OnUtenzaSelezionata="UtenzaSelezionata"/>

				<div class="bottoni">
					<asp:Button runat="server" ID="cmdSelezionacontribuente" Text="Seleziona un altro contribuente" OnClick="cmdSelezionacontribuente_Click" />
				</div>
			</asp:View>

		</asp:MultiView>
	</div>


</asp:Content>
