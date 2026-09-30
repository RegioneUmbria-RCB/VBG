<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" Title="Dettaglio calcoli" CodeBehind="CCICalcoliTotDettaglio.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione.CCICalcoliTotDettaglio" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<%@ Register Src="~/Istanze/CalcoloOneri/CostoCostruzione/CCModificaRiduzioniCtrl.ascx" TagPrefix="uc1" TagName="CCModificaRiduzioniCtrl" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <fieldset>
        <div>
            <asp:Label runat="server" ID="label13" Text="Codice" AssociatedControlID="lblId"></asp:Label>
            <asp:Label runat="server" ID="lblId" Text=""></asp:Label>
        </div>
        <div>
            <asp:Label runat="server" ID="label7" Text="Data" AssociatedControlID="lblData"></asp:Label>
            <asp:Label runat="server" ID="lblData" Text=""></asp:Label>
        </div>
        <div>
            <asp:Label runat="server" ID="Label8" Text="Listino coefficienti" AssociatedControlID="lblListino"></asp:Label>
            <asp:Label runat="server" ID="lblListino" Text=""></asp:Label>
        </div>
        <div>
            <asp:Label runat="server" ID="Label9" Text="Tipo intervento" AssociatedControlID="lblTipoIntervento"></asp:Label>
            <asp:Label runat="server" ID="lblTipoIntervento" Text=""></asp:Label>
        </div>
        <div>
            <asp:Label runat="server" ID="Label6" Text="Tipo intervento dettaglio" AssociatedControlID="lblTipoInterventoDettaglio"></asp:Label>
            <asp:Label runat="server" ID="lblTipoInterventoDettaglio" Text=""></asp:Label>
        </div>
        <div>
            <asp:Label runat="server" ID="Label10" Text="Destinazione" AssociatedControlID="lblDestinazione"></asp:Label>
            <asp:Label runat="server" ID="lblDestinazione" Text=""></asp:Label>
        </div>
        <div>
            <asp:Label runat="server" ID="Label1" Text="Destinazione dettaglio" AssociatedControlID="lblDestinazioneDettaglio"></asp:Label>
            <asp:Label runat="server" ID="lblDestinazioneDettaglio" Text=""></asp:Label>
        </div>
        <div>
            <asp:Label runat="server" ID="Label11" Text="Tipo calcolo" AssociatedControlID="lblTipoCalcolo"></asp:Label>
            <asp:Label runat="server" ID="lblTipoCalcolo" Text=""></asp:Label>
        </div>
        <div>
            <asp:Label runat="server" ID="Label12" Text="*Descrizione" AssociatedControlID="txtEditDescrizione"></asp:Label>
            <asp:TextBox runat="server" ID="txtEditDescrizione" Columns="100" MaxLength="200" />
        </div>
        <div class="Bottoni">
            <init:SigeproButton runat="server" ID="cmdAggiornaDescrizione" Text="Aggiorna descrizione" IdRisorsa="AGGIORNADESCRIZIONE" OnClick="cmdAggiornaDescrizione_Click" />
            <init:SigeproButton runat="server" ID="cmdEliminaCalcolo" Text="Elimina calcolo" IdRisorsa="ELIMINACALCOLO" OnClick="cmdEliminaCalcolo_Click" />
        </div>
    </fieldset>
    <% if (this.MostraTabellaContributo)
        { %>
    <uc1:CCModificaRiduzioniCtrl runat="server" ID="ModificaRiduzioniCtrl"
        OnSalvataggioRiuscito="OnModificaRiduzioniRiuscita"
        OnErroreSalvataggio="OnErroreSalvataggioRiduzioni" />

    <table>
        <colgroup width="20%" />
        <colgroup width="15%" align="center" />
        <colgroup width="20%" align="center" />
        <colgroup width="10%" align="right" />
        <colgroup width="15%" align="center" />
        <colgroup width="20%" align="right" />
        <tr>
            <th>&nbsp;</th>
            <th>Costo di costruzione</th>
            <th>Percentuale contributo</th>
            <th>Quota Contributo</th>
            <th>Variazione</th>
            <th>Quota Contributo</th>
        </tr>
        <% if (MostraRigaContributoProgetto)
            { %>
        <tr>
            <th>Stato di progetto</th>
            <td>€&nbsp;
			<init:DecimalTextBox runat="server" ID="txtCostoEdificioProgetto" Columns="10" />
                <asp:ImageButton runat="server" ID="cmdDettagliCostoEdificioProgetto" ImageUrl="~/images/detail.gif" AlternateText="Visualizza il calcolo del costo di costruzione"
                    OnClick="ApriDettagli" />
            </td>
            <td>
                <init:DecimalTextBox runat="server" ID="txtCoefficenteProgetto" Columns="4" />%
			<asp:ImageButton runat="server" ID="cmdDettagliContributoProgetto" ImageUrl="~/images/detail.gif" AlternateText="Visualizza il calcolo della percentuale del contributo"
                OnClick="ApriDettagliContributo" />
            </td>
            <td>€&nbsp;<asp:Label runat="server" ID="lblQuotaProgetto">asd</asp:Label>
            </td>
            <td>€&nbsp;
			<init:DecimalTextBox runat="server" ID="txtVariazioneProgetto" Columns="6" MaxLength="7" />
                <asp:ImageButton runat="server" ID="lnkEditVariazioneProgetto" ImageUrl="~/Images/edit.gif" OnClick="EditContributoProgetto"></asp:ImageButton>
                <init:HelpIcon runat="server" ID="hlpiVariazioneProgetto" HelpControl="hlpVariazioneProgetto"></init:HelpIcon>
                <init:HelpDiv runat="server" ID="hlpVariazioneProgetto">test</init:HelpDiv>
            </td>
            <td>€&nbsp;
			<init:DecimalTextBox runat="server" ID="txtQuotaContributoProgetto" ReadOnly="True" Columns="10" />
            </td>
        </tr>
        <% }%>
        <% if (MostraRigaContributoAttuale)
            { %>
        <tr>
            <th>Stato attuale</th>
            <td>€&nbsp;
			<init:DecimalTextBox runat="server" ID="txtCostoEdificioAttuale" Columns="10" />
                <asp:ImageButton runat="server" ID="cmdDettagliCostoEdificioAttuale" ImageUrl="~/images/detail.gif" AlternateText="Visualizza il calcolo del costo di costruzione"
                    OnClick="ApriDettagli" />
            </td>
            <td>
                <init:DecimalTextBox runat="server" ID="txtCoefficenteAttuale" Columns="4" />%
			<asp:ImageButton runat="server" ID="cmdDettagliContributoAttuale" ImageUrl="~/images/detail.gif" AlternateText="Visualizza il calcolo della percentuale del contributo"
                OnClick="ApriDettagliContributo" />
            </td>
            <td>€&nbsp;<asp:Label runat="server" ID="lblQuotaAttuale">asd</asp:Label>
            </td>
            <td>€&nbsp;
			<init:DecimalTextBox runat="server" ID="txtVariazioneAttuale" Columns="6" MaxLength="7" />
                <asp:ImageButton runat="server" ID="lnkEditVariazioneAttuale" ImageUrl="~/Images/edit.gif" OnClick="EditContributoAttuale"></asp:ImageButton>
                <init:HelpIcon runat="server" ID="hlpiVariazioneAttuale" HelpControl="hlpVariazioneAttuale"></init:HelpIcon>
                <init:HelpDiv runat="server" ID="hlpVariazioneAttuale">test</init:HelpDiv>
            </td>
            <td>€&nbsp;
			<init:DecimalTextBox runat="server" ID="txtQuotaContributoAttuale" ReadOnly="True" Columns="10" />
            </td>
        </tr>
        <% } //if (MostraRigaContributoAttuale)%>
        <tr>
            <td colspan="5" style="text-align: right; font-weight: bold">Totale contributo</td>
            <td>€&nbsp;
			<init:DecimalTextBox runat="server" ID="txtQuotaContributoTotale" ReadOnly="True" Columns="10" />
            </td>
        </tr>
        <tr>
            <td colspan="5">&nbsp;
            </td>
            <td>
                <div class="Bottoni">
                    <init:SigeproButton runat="server" ID="cmdCopiaOneri" Text="Riporta il totale nella gestione oneri dell'istanza" IdRisorsa="COPIAONERI" OnClick="cmdRiportaValore_Click" />
                </div>
            </td>
        </tr>
    </table>
    <fieldset>
        <!--<div>-->
        <!--</div>-->
        <div class="Bottoni">
            <init:SigeproButton runat="server" ID="cmdSalvaContributo" Text="Salva contributo" IdRisorsa="SALVACONTRIBUTO" OnClick="cmdSalvaContributo_Click" />
            <init:SigeproButton runat="server" ID="cmdChiudiDettaglio" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudiDettaglio_Click" />
        </div>
    </fieldset>
    <% } //if (MostraTabellaContributo) %>
</asp:Content>
