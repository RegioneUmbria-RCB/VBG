<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true"
    Codebehind="ErroriSistema.aspx.cs" Inherits="Sigepro.net.ErroriSistema" Title="Errore di sistema" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="cc1" %>
<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <div class="ErroriSistema">
        <div class="ErroreTitolo">
            ERRORE INATTESO</div>
        <div class="ErroreHeader">
            La funzionalità a cui stai cercando di accedere ha generato un errore inatteso.<br />
            La invitiamo a contattare l'assistenza tecnica chiamando il numero verde
            <asp:Label runat="Server" ID="lblNumeroVerde"></asp:Label>
            oppure a inviare un messaggio di posta elettronica al seguente indirizzo
            <asp:Label runat="server" ID="lblMailSupport"></asp:Label></div>
        
            <div class="ErroriSistemaTabella">
        
                <table>
                    <tr>
                        <td colspan="2" align="center">
                            DETTAGLIO DELL'ERRORE (da comunicare al servizio assistenza insieme ad una breve descrizione
                        delle operazioni svolte)
                        </td>
                    </tr>
                    <tr>
                        <td>Descrizione</td>
                        <td><asp:Label runat="server" ID="lblDescrizione"></asp:Label></td>
                    </tr>
                    <tr>
                        <td>Sorgente</td>
                        <td><asp:Label runat="server" ID="lblSorgente"></asp:Label></td>
                    </tr>
                    <tr>
                        <td>Pagina</td>
                        <td><asp:Label runat="server" ID="lblPagina"></asp:Label></td>
                    </tr>
                    <tr>
                        <td>Target</td>
                        <td><asp:Label runat="server" ID="lblTarget"></asp:Label></td>
                    </tr>
                    <tr>
                        <td>Stack</td>
                        <td><asp:Label runat="server" ID="lblStack"></asp:Label></td>
                    </tr>
                </table>
            
            </div>
        
        <div class="ErroreBottoni">
            <init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
        </div> 
    </div>
</asp:Content>
