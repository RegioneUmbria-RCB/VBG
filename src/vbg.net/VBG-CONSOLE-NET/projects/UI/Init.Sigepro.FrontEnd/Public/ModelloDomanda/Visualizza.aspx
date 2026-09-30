<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Visualizza.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Public.ModelloDomanda.Visualizza" %>

<%@ Register TagPrefix="cc1" TagName="GrigliaEndo" Src="~/Public/ModelloDomanda/GrigliaEndo.ascx" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
	<title>Modello di domanda</title>
	<link href="~/css/contenuti/modelloDomanda.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<form id="form1" runat="server">
	<asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
		<asp:View runat="server" ID="selezioneEndoView">
			<div id="selezioneEndo">
				<div class="descrizione">
					Prima di completare la visualizzazione del modello è possibile selezionare ulteriori procedimenti che si ritengono necessari per l'intervento da attivare.
				</div>
				<div id="listaEndoprocedimentiSelezionabili">
					<div>
						<cc1:GrigliaEndo runat="server" ID="geProcedimentiNecessari" DescrizioneGriglia="Procedimenti ricorrenti" />
						<cc1:GrigliaEndo runat="server" ID="geProcedimentiEventuali" DescrizioneGriglia="Procedimenti eventuali" />
					</div>
				</div>
				<div class="bottoni">
					<asp:LinkButton runat="server" ID="lnkGeneraModello" Text="Visualizza modello" OnClick="lnkGeneraModello_Click" />
				</div>
			</div>
		</asp:View>
		<asp:View runat="server" ID="visualizzazioneView">
			<div class="descrizione">
				Il seguente modello va considerato come un fac-simile e non può essere utilizzato per la presentazione della domanda.
				Nel caso si voglia presentare la domanda con i servizi on-line utilizzare il pulsante "Accedi ai servizi on-line".
			</div>
			<div>
				<iframe id="listaEndoprocedimentiSelezionabili" src='<%= GeneraUrlModello() %>'>
				</iframe>
			</div>
			<div class="bottoni">
				<asp:LinkButton runat="server" ID="lnkSelezionaEndo" Text="Seleziona altri procedimenti"
					OnClick="lnkSelezionaEndo_Click" />

				<asp:LinkButton runat="server" ID="lnkStampaPdf" Text="Visualizza in PDF"
					OnClick="lnkStampaPdf_Click" />

				<asp:HyperLink runat="server" ID="lnkAccedi" Text="Accedi ai servizi on-line" Target="_top"/>
				
			</div>
		</asp:View>
	</asp:MultiView>
	</form>
</body>
</html>
