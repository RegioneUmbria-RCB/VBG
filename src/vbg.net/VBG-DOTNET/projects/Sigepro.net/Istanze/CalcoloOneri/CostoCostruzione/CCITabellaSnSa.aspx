<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" CodeBehind="CCITabellaSnSa.aspx.cs" Inherits="Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione.CCITabellaSnSa" Title="Superfici per attività turistiche commerciali e direzionali e relativi accessori" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>

<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>



<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">
    <table>
        <colgroup width="70%" />
        <colgroup width="30%" />
        <tr>
            <th>Denominazione</th>
            <th>Superficie (mq)</th>
        </tr>

        <tr>
            <td>Superficie netta non residenziale</td>
            <td>
                <init:DecimalTextBox runat="server" ID="dtbSuArt9"></init:DecimalTextBox>
            </td>
        </tr>
        <tr>
            <td>Superficie accessori</td>
            <td>
                <init:DecimalTextBox runat="server" ID="dtbSa"></init:DecimalTextBox>
            </td>
        </tr>

    </table>
    <fieldset>
        <div class="Bottoni">
            <init:SigeproButton runat="server" ID="cmdProcedi" Text="Procedi" IdRisorsa="PROCEDI" OnClick="cmdProsegui_Click" />
            <init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
        </div>
    </fieldset>
</asp:Content>
