<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" Title="Nuovo calcolo" AutoEventWireup="true" CodeBehind="CCICalcoliTotInsert.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione.CCICalcoliTotInsert" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <asp:ScriptManager ID="ScriptManager1" runat="server">
    </asp:ScriptManager>
    <fieldset>
        <asp:UpdatePanel ID="UpdatePanel1" runat="server">
            <contenttemplate>
                <div>
                    <asp:Label runat="server" ID="label2" Text="*Data" AssociatedControlID="txtData"></asp:Label>
                    <init:datetextbox runat="server" id="txtData" autopostback="True" ontextchanged="txtData_TextChanged"></init:datetextbox>
                </div>
                <div>
                    <asp:Label runat="server" ID="lblFkCCVCID" Text="*Listino coefficienti" AssociatedControlID="ddlFkCCVCID"></asp:Label>
                    <asp:DropDownList runat="server" ID="ddlFkCCVCID" DataTextField="Descrizione" DataValueField="Id" />
                </div>
                <div>
                    <asp:Label runat="server" ID="Label1" Text="*Tipo intervento" AssociatedControlID="ddlFfOCCBTIID"></asp:Label>
                    <asp:DropDownList runat="server" ID="ddlFfOCCBTIID" DataTextField="Intervento" DataValueField="Id" AutoPostBack="True" OnSelectedIndexChanged="OnTipoInterventoBaseModificato" />
                </div>
                <div>
                    <asp:Label runat="server" ID="Label6" Text="Tipo intervento dettaglio" AssociatedControlID="ddlTipoInterventoDettaglio"></asp:Label>
                    <asp:DropDownList runat="server" ID="ddlTipoInterventoDettaglio" DataTextField="Descrizione" DataValueField="Id" />
                </div>
                <div>
                    <asp:Label runat="server" ID="Label3" Text="*Destinazione" AssociatedControlID="ddlFkOCCBDEID"></asp:Label>
                    <asp:DropDownList runat="server" ID="ddlFkOCCBDEID" DataTextField="Destinazione" DataValueField="Id" AutoPostBack="True" OnSelectedIndexChanged="OnTipoDestinazioneBaseModificato" />
                </div>
                <div>
                    <asp:Label runat="server" ID="Label7" Text="Destinazione dettaglio" AssociatedControlID="ddlTipoDestinazioneDettaglio"></asp:Label>
                    <asp:DropDownList runat="server" ID="ddlTipoDestinazioneDettaglio" DataTextField="Descrizione" DataValueField="Id" />
                </div>
                <div>
                    <asp:Label runat="server" ID="Label4" Text="*Tipo calcolo" AssociatedControlID="ddlFkBCCTCID"></asp:Label>
                    <asp:DropDownList runat="server" ID="ddlFkBCCTCID" DataTextField="Tipocalcolo" DataValueField="Id" />
                </div>
                <div>
                    <asp:Label runat="server" ID="Label5" Text="*Descrizione" AssociatedControlID="txtDescrizione"></asp:Label>
                    <asp:TextBox runat="server" ID="txtDescrizione" Columns="100" MaxLength="200" />
                </div>
                <div class="DescrizioneCampo">
                    Se lasciato vuoto verrà valorizzato con <b>Destinazione - Tipo Intervento</b>
                </div>
            </contenttemplate>
        </asp:UpdatePanel>
        <div class="Bottoni">
            <init:sigeprobutton runat="server" id="cmdInserisci" text="Inserisci" idrisorsa="INSERISCI" onclick="cmdInserisci_Click" />
            <init:sigeprobutton runat="server" id="cmdChiudiInserimento" text="Chiudi" idrisorsa="CHIUDI" onclick="cmdChiudiInserimento_Click" />
        </div>
    </fieldset>

</asp:Content>
