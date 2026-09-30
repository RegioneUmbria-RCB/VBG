<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="dati-generali.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.dati_generali" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<div class="row" runat="server" visible='<%#DaArchivio %>'>
    <ar:LabeledLabel runat="server" ID="lblProtocollo" Label="Numero protocollo" BtSize="Col3" />
    <ar:LabeledLabel runat="server" ID="lblDataProtocollo" Label="Data protocollo" BtSize="Col3" />
</div>

<div class="row">
    <ar:LabeledLabel runat="server" ID="lblNumeroPratica" Label="Numero pratica" BtSize="Col3" />
    <ar:LabeledLabel runat="server" ID="lblDataPresentazione" Label="Data presentazione" BtSize="Col3" />
</div>

<ar:LabeledLabel runat="server" ID="lblOggetto" Label="Oggetto" />
<ar:LabeledLabel runat="server" ID="lblIntervento" Label="Intervento" />

<div class="row">
    <ar:LabeledLabel runat="server" ID="lblStatoPratica" Label="Stato" BtSize="Col3" />
</div>

<div runat="server" visible='<%#DaArchivio %>'>
    <h3>Riferimenti</h3>
    <div class="row">
        <ar:LabeledLabel runat="server" ID="lblResponsabileProc" Label="Responsabile procedimento" BtSize="Col4" />
        <ar:LabeledLabel runat="server" ID="lblIstruttore" Label="Istruttore" BtSize="Col4" />
        <ar:LabeledLabel runat="server" ID="lblOperatore" Label="Operatore" BtSize="Col4" />
    </div>
</div>
