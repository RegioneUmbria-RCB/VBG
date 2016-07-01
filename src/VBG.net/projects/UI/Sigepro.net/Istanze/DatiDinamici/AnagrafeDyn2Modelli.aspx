<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master"  MaintainScrollPositionOnPostback="true" EnableEventValidation="false" AutoEventWireup="True" Codebehind="AnagrafeDyn2Modelli.aspx.cs" Inherits="Sigepro.net.Istanze.DatiDinamici.AnagrafeDyn2Modelli" Title="Schede dell'anagrafica" %>
<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register Src="DatiDinamiciCtrl.ascx" TagName="DatiDinamiciCtrl" TagPrefix="uc1" %>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
	<uc1:DatiDinamiciCtrl id="DatiDinamiciCtrl1" runat="server"  OnGetListaModelli="GetListaModelli" 
	OnGetModelloDinamicoDaId="GetModelloDinamicoDaId" NomeChiaveCodice="CodiceAnagrafe" OnAggiungiScheda="AggiungiScheda" OnEliminaScheda="EliminaScheda"  OnGetListaIndiciScheda="GetListaIndiciScheda" 
	OnClose="Close"
	OnGetUrlPaginaStorico="GetUrlPaginaStorico"
	OnVerificaEsistenzaStorico="VerificaEsistenzaStorico">
	</uc1:DatiDinamiciCtrl>
</asp:Content>
