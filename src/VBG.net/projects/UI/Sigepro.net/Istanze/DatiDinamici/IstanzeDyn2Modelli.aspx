<%@ Page Language="C#" AutoEventWireup="True" MaintainScrollPositionOnPostback="true" EnableEventValidation="false" ValidateRequest="false" MasterPageFile="~/SigeproNetMaster.master" Codebehind="IstanzeDyn2Modelli.aspx.cs" Inherits="Sigepro.net.Istanze.DatiDinamici.IstanzeDyn2Modelli"
	Title="Schede dell'istanza" %>

<%@ Register Src="DatiDinamiciCtrl.ascx"  TagName="DatiDinamiciCtrl" TagPrefix="uc1" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%--<asp:Content ID="headContent" ContentPlaceHolderID="headPagina" runat="server">
    <script type="text/javascript" src="js/D2FocusManager.js"></script>
	<script type="text/javascript" src="js/AjaxCallManager.js"></script>
	<script type="text/javascript" src="js/D2PannelloErrori.js"></script>
	<script type="text/javascript" src="js/DatiDinamiciExtender.js"></script>
	<script type="text/javascript" src="js/DescrizioneControllo.js"></script>
	<script type="text/javascript" src="js/GetterSetterDatiDinamici.js"></script>
    <script type="text/javascript" src="js/jQuery.resetValues.js"></script>    
	<script type="text/javascript" src="js/jQuery.searchDatiDinamici.js"></script>
	<script type="text/javascript" src="js/jquery.uploadDatiDinamici.js"></script>
</asp:Content>--%>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<div class="Informazioni">
		<div>
			<asp:Label CssClass="Etichetta" ID="lblTitolo1" runat="server" Text="Codice istanza:" />
			<asp:LinkButton runat="server" ID="lnkTornaAIstanza" OnClick="lnkTornaAIstanza_Click"><asp:Label CssClass="Valore" ID="lblCodiceIstanza" runat="server" Text="" /></asp:LinkButton>
		</div>
		
		<div>
			<asp:Label CssClass="Etichetta" ID="Label1" runat="server" Text="Richiedente:" />
			<asp:Label CssClass="Valore" ID="lblRichiedente" runat="server" Text="" />
		</div>
	</div>

	<div id="datiMovimento" runat="server"  class="Informazioni">
		<div>
			<asp:Label CssClass="Etichetta" ID="Label2" runat="server" Text="Codice movimento:" />
			<asp:Label CssClass="Valore" ID="lblCodiceMovimento" runat="server" Text="" />
		</div>
	</div>

	<uc1:DatiDinamiciCtrl id="ddCtrl" runat="server"  OnGetListaModelli="GetListaModelli"
	OnGetModelloDinamicoDaId="GetModelloDinamicoDaId" 
	OnGetListaIndiciScheda="GetListaIndiciScheda" 
	NomeChiaveCodice="CodiceIstanza" 
	OnAggiungiScheda="AggiungiScheda" 
	OnEliminaScheda="EliminaScheda" 
	OnClose="Close"
	OnGetUrlPaginaStorico="GetUrlPaginaStorico"
	OnVerificaEsistenzaStorico="VerificaEsistenzaStorico">
	</uc1:DatiDinamiciCtrl>

	
</asp:Content>
