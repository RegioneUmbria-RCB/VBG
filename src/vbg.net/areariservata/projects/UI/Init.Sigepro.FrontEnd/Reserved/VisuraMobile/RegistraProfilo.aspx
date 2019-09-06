<%@ Page Title="Associa ad applicazione mobile" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master"
	AutoEventWireup="true" CodeBehind="RegistraProfilo.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.VisuraMobile.RegistraProfilo" %>

<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<div class="inputForm">
		<fieldset>
			<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
				<asp:View runat="server" ID="vwInserimentoPin">
					<div>
						Attraverso questa funzionalità è possibile collegare la propria utenza all'app di
						Visura Mobile. Questo permetterà di visualizzare lo stato di avanzamento delle proprie
						pratiche in tempo reale tramite il proprio dispositivo mobile.
					</div>
					<div>
						L'app è disponibile su Google Play™ e su App Store™<br />
						<img src="../../Images/google_play.png" class="appStoreIcon" />
						<img src="../../Images/app_store.png" class="appStoreIcon" />
						<br />
						<br />
						Per collegare il proprio profilo all'app di Visura Mobile inserire il codice a sei
						cifre che viene mostrato nella schermata "Collega un nuovo profilo" del menu "Impostazioni".
						<b>Attenzione</b>, il codice fa distinzione tra lettere maiuscole e minuscole
						<br />
					</div>
					<div>
						<asp:TextBox runat="server" ID="txtCodiceProfilo" />
					</div>
					<div class="bottoni">
						<asp:Button runat="server" ID="cmdCollega" Text="Associa" OnClick="cmdCollega_Click" />
					</div>
				</asp:View>
				<asp:View runat="server" ID="vwInserimentoEffettuato">
					Associazione effettuata con successo. Ora è possibile visualizzare lo stato di avanzamento delle proprie
						pratiche tramite il proprio dispositivo mobile
				</asp:View>
			</asp:MultiView>
		</fieldset>
	</div>
</asp:Content>
