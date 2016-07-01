<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="PraticaInviataConSuccesso.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.PraticaInviataConSuccesso" Title="Pratica inviata con successo" %>
<%@ MasterType VirtualPath="~/AreaRiservataMaster.Master" %>

<%@ Register TagPrefix="uc1" TagName="VisuraCtrl" Src="~/Reserved/VisuraCtrl.ascx" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

<script type="text/Javascript">
	function StampaPagina( el )
	{
		hideDiv( "intestazionePagina");
		hideDiv( "piedePagina");
		hideDiv( "menuNavigazione");
		
		el.style.display = "none";
		
		printWindow();
		
		el.style.display = "";
		
		showDiv( "intestazionePagina");
		showDiv( "piedePagina");
		showDiv( "menuNavigazione");
		
		return false;
	}
	
	function hideDiv( name )
	{
		var el = document.getElementById( name );
		
		if (el)
			el.style.display = "none";
	}
	
	function showDiv( name )
	{
		var el = document.getElementById( name );
		
		if (el)
			el.style.display = "";
	}

	function printWindow(){
	   var bV = parseInt(navigator.appVersion)
	   if (bV >= 4) window.print();
	}
</script>

<div>
	Gentile <asp:Label runat="server" id="lblNomeUtente" />.<br /> 
	Suggeriamo di stampare il contenuto di questa pagina e di presentarlo 
	all’ufficio del 
	<asp:Label runat="server" id="lblNomeComune" />
	insieme a un documento di identità valido.<br />
	Per ulteriori informazioni e/o delucidazioni può contattare il numero
	<asp:Label runat="server" id="lblTelefonoSportello" /><br />
	<br />
	ORARI DI APERTURA:<br />
	<asp:Label runat="server" id="lblOrariSportello" /><br /><br />
	Buona giornata.
</div>

<uc1:VisuraCtrl runat="server" ID="visuraCtrl" />

<div class="inputForm">
	<fieldset>
		<div class="bottoni">
			<asp:Button runat="server" ID="cmdPrint" Text="Stampa" OnClientClick="return StampaPagina(this);" />
		</div>
	</fieldset>
</div>
</asp:Content>
