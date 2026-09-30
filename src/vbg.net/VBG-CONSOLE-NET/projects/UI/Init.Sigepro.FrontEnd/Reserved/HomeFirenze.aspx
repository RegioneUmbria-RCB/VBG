<%@ Page Title="Benvenuto nel sistema di presentazione on-line delle pratiche" Language="C#"
	MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="HomeFirenze.aspx.cs"
	Inherits="Init.Sigepro.FrontEnd.Reserved.HomeFirenze" %>




<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
	<style>
		ul > li{list-style-type: disc}
		ul {padding-left: 20px;padding-top: 10px;}
	</style>


	<ul >
		<li>Selezionare <b>Nuova pratica</b> per iniziare la presentazione di una nuova pratica on-line
		</li>
		<li>Selezionare <b>Pratiche in sospeso</b> per continuare l'inserimento di una pratica lasciata
			in sospeso precedentemente </li>
		<li>Selezionale <b>Le mie pratiche</b> per consultare le proprie pratiche trasmesse e il relativo
			stato di avanzamento </li>
	</ul>
</asp:Content>
