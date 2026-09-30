<%@ Page Title="Selezione adempimenti da attività ATECO" Language="C#" MasterPageFile="~/AreaRiservataPopupMaster.Master" AutoEventWireup="true" CodeBehind="AtecoAlbero.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.AtecoAlbero" EnableViewState="false" %>
<%@ Register TagPrefix="cc1" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.Ateco" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	
	<div id="intestazioneAteco">
		In questa sezione è possibile individuare l'attività della propria azienda secondo la classificazione ATECO.<br />
		In seguito alla selezione sarà possibile individuare le informazioni relative agli adempimenti che è possibile attivare.<br /><br />
		Le voci contrassegnate con il simbolo <img src='<%=ResolveClientUrl("~/Images/help_interventi.gif") %>' /> forniscono ulteriori dettagli.
		
		<br />
		<br />		
		
		<b>Attività ATECO</b> <a href="#" id="lnkRicerca">[Ricerca testuale]</a>
	</div>
	<cc1:AlberoAtecoJs runat="server" ID="alberoAteco" OnFogliaSelezionata="alberoAteco_FogliaSelezionata" />
	

</asp:Content>
