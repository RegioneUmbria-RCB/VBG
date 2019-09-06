<%@ Page Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="IstanzeInSospeso.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.ListaIstanzePresentate"
    Title="Istanze in sospeso" %>

<%@ Register Src="~/CommonControls/torna-a-scrivania-virtuale.ascx" TagPrefix="uc1" TagName="tornaascrivaniavirtuale" %>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <asp:GridView ID="dgIstanzePresentate"
        runat="server"
        DataKeyNames="Id"
        AutoGenerateColumns="False"
        OnSelectedIndexChanged="dgIstanzePresentate_SelectedIndexChanged"
        OnRowDataBound="dgIstanzePresentate_ItemDataBound"
        GridLines="None"
        CssClass="table">
        <Columns>
            <asp:TemplateField HeaderText="<i class='glyphicon glyphicon-ok'></i>" ItemStyle-Width="1%">
                <ItemTemplate>
                    <asp:HiddenField runat="server" ID="hidBookmark" />
                    <asp:CheckBox runat="server" ID="chkChecked" Checked="false" />
                </ItemTemplate>
            </asp:TemplateField>

            <asp:TemplateField HeaderText="Identificativo domanda">
                <ItemTemplate>
                    <asp:Label runat="server" ID="lblIdDomanda" Text='<%#Eval("Identificativodomanda") %>' /><br />
                    <i>Ultima modifica:
                        <asp:Label runat="server" ID="lblUltimaModifica" Text='<%#Eval("DataUltimaModifica", "{0:dd/MM/yyyy HH:mm}") %>' /></i>
                </ItemTemplate>
            </asp:TemplateField>

            <asp:TemplateField HeaderText="Richiedente" ItemStyle-Width="15%">
                <ItemTemplate>
                    <asp:Label runat="server" ID="lblRichiedente"></asp:Label>
                </ItemTemplate>
            </asp:TemplateField>



            <asp:TemplateField HeaderText="Tipo intervento" ItemStyle-Width="45%">
                <ItemTemplate>
                    <asp:Label runat="server" ID="lblTipoIntervento"></asp:Label>
                </ItemTemplate>
            </asp:TemplateField>

            <asp:TemplateField HeaderText="Oggetto" ItemStyle-Width="15%">
                <ItemTemplate>
                    <asp:Label runat="server" ID="lblOggetto"></asp:Label>
                </ItemTemplate>
            </asp:TemplateField>

            <asp:TemplateField ItemStyle-HorizontalAlign="Right" ItemStyle-Width="10%">
                <ItemTemplate>
                    <asp:LinkButton runat="server" Text="Riprendi" CommandName="Select" CausesValidation="false" ID="Linkbutton1"></asp:LinkButton>
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
    </asp:GridView>
    
    <asp:Button runat="server" ID="cmdDeleteRows" CssClass="btn btn-default" Text="Elimina le istanze selezionate" OnClick="cmdDeleteRows_Click" OnClientClick="return confirm('Eliminare le domande selezionate\?')" />

</asp:Content>
