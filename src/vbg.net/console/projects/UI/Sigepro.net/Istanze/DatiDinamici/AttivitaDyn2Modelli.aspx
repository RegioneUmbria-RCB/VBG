<%@ Page Title="Schede dell'attività" Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="AttivitaDyn2Modelli.aspx.cs" Inherits="Sigepro.net.Istanze.DatiDinamici.AttivitaDyn2Modelli" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register Src="DatiDinamiciCtrl.ascx" TagName="DatiDinamiciCtrl" TagPrefix="uc1" %>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">

	<div class="Informazioni">
		<div>
			<asp:Label CssClass="Etichetta" ID="lblTitolo1" runat="server" Text="Codice attività:" />
			<asp:Label CssClass="Valore" ID="lblCodiceAttivita" runat="server" Text="" />
		</div>
		
		<div>
			<asp:Label CssClass="Etichetta" ID="Label1" runat="server" Text="Denominazione:" />
			<asp:Label CssClass="Valore" ID="lblDenominazione" runat="server" Text="" />
		</div>
	    
	</div>


	<uc1:DatiDinamiciCtrl id="DatiDinamiciCtrl1" runat="server"  OnGetListaModelli="GetListaModelli" 
							OnGetModelloDinamicoDaId="GetModelloDinamicoDaId" 
							NomeChiaveCodice="CodiceAttivita" 
							OnAggiungiScheda="AggiungiScheda" 
							OnEliminaScheda="EliminaScheda"  
							OnGetListaIndiciScheda="GetListaIndiciScheda" 
							OnClose="Close"
							OnGetUrlPaginaStorico="GetUrlPaginaStorico"
							OnVerificaEsistenzaStorico="VerificaEsistenzaStorico"
							UsaFormAggiungiNuovaSchedaAttivita="true"
							OnGetListaSoftwareAttivita="GetListaSoftwareAttivita"
							OnGetMascheraSolaLettura="GetMascheraSolaLettura" />
</asp:Content>

