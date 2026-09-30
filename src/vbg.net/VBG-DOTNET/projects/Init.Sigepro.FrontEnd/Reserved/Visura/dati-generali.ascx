<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="dati-generali.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.dati_generali" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<div class="row">
    <ar:LabeledLabel runat="server" ID="lblComune" Label="Comune" BtSize="Col4" />
</div>

<div class="row" runat="server" visible='<%#MostraDatiProtocollo %>'>
    <ar:LabeledLabel runat="server" ID="lblProtocollo" IdRisorsa="visura.numero_protocollo" Label="Numero protocollo" BtSize="Col3" />
    <ar:LabeledLabel runat="server" ID="lblDataProtocollo" IdRisorsa="visura.data_protocollo" Label="Data protocollo" BtSize="Col3" />
</div>

<div class="row">
    <ar:LabeledLabel runat="server" ID="lblNumeroPratica" IdRisorsa="visura.codice_istanza" Label="Numero pratica" BtSize="Col3" />
    <ar:LabeledLabel runat="server" ID="lblDataPresentazione" IdRisorsa="visura.data_presentazione" Label="Data presentazione" BtSize="Col3" />
</div>
<%if( this.MostraPosizioneArchivio) {%>
<div class="row">
    <ar:LabeledLabel runat="server" ID="lblPosizioneArchivio" IdRisorsa="visura.posizione_in_archivio" Label="Posizione in archivio" BtSize="Col3" />
</div>
<%} %>
<div class="row">
    <ar:LabeledLabel runat="server" ID="lblOggetto" IdRisorsa="visura.oggetto" Label="Oggetto" BtSize="Col12" />
</div>
<div class="row">
    <ar:LabeledLabel runat="server" ID="lblIntervento" IdRisorsa="visura.tipo_intervento" Label="Intervento" BtSize="Col12" />
</div>

<%if (this.MostraStatoPratica) {%>
    <div class="row">
        <ar:LabeledLabel runat="server" ID="lblStatoPratica" IdRisorsa="visura.stato" Label="Stato" BtSize="Col3" />
    </div>
<%} %>

<%if (this.MostraRiferimenti) {%>
    <h3>Riferimenti</h3>
    <div class="row">
        <ar:LabeledLabel runat="server" ID="lblResponsabileProc" Label="Responsabile procedimento" BtSize="Col4" />
        <ar:LabeledLabel runat="server" ID="lblIstruttore" Label="Istruttore" BtSize="Col4" />
        <ar:LabeledLabel runat="server" ID="lblOperatore" Label="Operatore" BtSize="Col4" />
    </div>
<%} %>