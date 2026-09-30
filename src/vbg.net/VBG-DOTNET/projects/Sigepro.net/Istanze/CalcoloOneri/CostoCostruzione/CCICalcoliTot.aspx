<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="True" Inherits="Istanze_CalcoloOneri_CostoCostruzione_CCICalcoliTot"
    Title="Calcolo del contributo relativo al costo di costruzione" CodeBehind="CCICalcoliTot.aspx.cs" %>

<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>


<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>

<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<%@ Register Src="~/Istanze/CalcoloOneri/CostoCostruzione/CCModificaRiduzioniCtrl.ascx" TagPrefix="uc1" TagName="CCModificaRiduzioniCtrl" %>






<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="Server">

    <asp:GridView runat="server" AutoGenerateColumns="false" DataKeyNames="Id" ID="gvLista"
        OnSelectedIndexChanged="gvLista_SelectedIndexChanged"
        OnRowDataBound="gvLista_RowDataBound"
        OnRowDeleting="gvLista_RowDeleting">
        <AlternatingRowStyle CssClass="RigaAlternata" />
        <RowStyle CssClass="Riga" />
        <HeaderStyle CssClass="IntestazioneTabella" />
        <EmptyDataRowStyle CssClass="NessunRecordTrovato" />
        <Columns>
            <asp:ButtonField CommandName="Select" DataTextField="Id" HeaderText="Codice" Text="Button" />
            <asp:BoundField DataField="Descrizione" HeaderText="Descrizione" />
            <asp:TemplateField>
                <ItemTemplate>
                    <asp:ImageButton ID="cmdElimina" runat="server" CommandName="Delete" ImageUrl="~/images/Delete.gif" />
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
        <EmptyDataTemplate>
            <asp:Label ID="Label6" runat="server"></asp:Label>
        </EmptyDataTemplate>
    </asp:GridView>
    <fieldset>
        <div class="Bottoni">
            <init:SigeproButton runat="server" ID="cmdNuovo" Text="Nuovo" IdRisorsa="NUOVO" OnClick="cmdNuovo_Click" />
            <init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" />
        </div>
    </fieldset>

</asp:Content>
