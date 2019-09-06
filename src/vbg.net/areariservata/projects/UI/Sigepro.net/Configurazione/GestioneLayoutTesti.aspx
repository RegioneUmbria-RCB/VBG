<%@ Page Language="C#" MasterPageFile="~/SigeproNetMaster.master" AutoEventWireup="true" EnableEventValidation="false" ValidateRequest="false"
CodeBehind="GestioneLayoutTesti.aspx.cs" Inherits="Sigepro.net.Configurazione.GestioneLayoutTesti" Title="Gestione Testi" %>


<%@ MasterType VirtualPath="~/SigeproNetMaster.master" %>

<%@ Register tagPrefix="init" namespace="Init.Utils.Web.UI" assembly="Init.Utils"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.UI" assembly="SIGePro.WebControls"%>
<%@ Register tagPrefix="init" namespace="SIGePro.WebControls.Ajax" assembly="SIGePro.WebControls"%>

<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <asp:ScriptManager ID="ScriptManager1" runat="server"></asp:ScriptManager>
    <asp:UpdatePanel ID="UpdatePanel1" runat="server" RenderMode="Inline" UpdateMode="Always" ChildrenAsTriggers="true">
        <ContentTemplate>
        <asp:Panel runat="server" ID="pnForm" DefaultButton="cmdCerca">
            <div>
                <div>
                    <br /><init:LabeledTextBox runat="server" ID="txtCercaTesto" Descrizione="&nbsp;&nbsp;Inserire qui il testo da cercare:&nbsp;" /><br />
                </div>
                <div class="bottoni">
                    <init:SigeproButton runat="server" ID="cmdCerca" Text="Cerca" IdRisorsa="CERCA" OnClick="cmdCerca_Click"/>
                    <init:SigeproButton runat="server" ID="cmdChiudi" Text="Chiudi" IdRisorsa="CHIUDI" OnClick="cmdChiudi_Click" /><br /><br />
                </div>
                <div>
                    <asp:GridView CssClass="" runat="server" AutoGenerateColumns="False" ID="gvLista" OnPageIndexChanging="gvLista_PageIndexChanging" OnRowEditing="gvLista_RowEditing" OnRowCancelingEdit="gvLista_RowCancelingEdit" OnRowUpdating="gvLista_RowUpdating" OnRowDataBound="gvLista_RowDataBound" OnRowDeleting="gvLista_RowDeleting" PageSize="20">
                        <AlternatingRowStyle CssClass="RigaAlternata" />
                        <PagerStyle CssClass="Pager" />
                        <RowStyle CssClass="Riga" />
                        <HeaderStyle CssClass="IntestazioneTabella" />
                        <EmptyDataRowStyle CssClass="NessunRecordTrovato"/>
                        <Columns>

                            <asp:TemplateField>
                                <HeaderTemplate>
                                    <asp:Label runat="server" ID="lblHeaderCodiceTesto" Text="Codice Testo" ToolTip="Visualizza il codice del testo" />
                                </HeaderTemplate>
                                <ItemTemplate>
                                    <asp:Label runat="server" ID="lblCodiceTesto" Text='<%# DataBinder.Eval(Container.DataItem , "Codicetesto") %>' />
                                </ItemTemplate>
                                <HeaderStyle Width="28%" />
                                <ItemStyle Width="28%" />
                            </asp:TemplateField>
                            
                            <asp:TemplateField>
                                <HeaderTemplate>
                                    <asp:Label runat="server" ID="lblHeaderTesto" Text="Testo Base" ToolTip="Visualizza il testo base" />
                                </HeaderTemplate>
                                <ItemTemplate>
                                    <asp:Label runat="server" ID="lblTestoBase" Text='<%# GetTestoBase( DataBinder.Eval(Container.DataItem , "Codicetesto"), DataBinder.Eval(Container.DataItem , "Testo") ) %>' />
                                </ItemTemplate>
                                <HeaderStyle Width="30%" />
                                <ItemStyle Width="30%" />
                            </asp:TemplateField>

                            <asp:TemplateField>
                                <HeaderTemplate>
                                    <asp:Label runat="server" ID="lblHeaderSoftware" Text="Software"  ToolTip="Visualizza il software corrente, se il testo non esiste con il software corrente allora visualizza il testo con software - TT -" />
                                </HeaderTemplate>
                                <EditItemTemplate>
                                    <asp:Label runat="server" ID="lblSoftware" Text="" />
                                </EditItemTemplate>
                                <ItemTemplate>
                                    <asp:Label runat="server" ID="lblSoftware" Text="" />
                                </ItemTemplate>
                                <HeaderStyle Width="6%" />
                                <ItemStyle Width="6%" />
                            </asp:TemplateField>
                            
                            <asp:TemplateField>
                                <HeaderTemplate>
                                    <asp:Label runat="server" ID="lblHeaderNuovoTesto" Text="Testo Visualizzato" ToolTip="Visualizza il nuovo testo in base al software passato, se non presente allora visualizza il nuovo testo con software - TT - se non presente nemmeno quest'ultimo allora non sarà visualizzato alcun nuovo testo" />
                                </HeaderTemplate>
                                <EditItemTemplate>
                                    <asp:TextBox runat="server" ID="txtNuovoTesto" MaxLength="200" TextMode="MultiLine" Rows="2" Columns="30" Text='<%# GetLayoutTestiClass( DataBinder.Eval(Container.DataItem , "Codicetesto") ).Nuovotesto %>' />
                                </EditItemTemplate>
                                <ItemTemplate>
                                    <asp:Label runat="server" ID="lblNuovoTesto" Text="" />
                                </ItemTemplate>
                                <HeaderStyle Width="30%" />
                                <ItemStyle Width="30%" />
                            </asp:TemplateField>
                            
                            <asp:TemplateField>
                                <EditItemTemplate>
                                    <asp:ImageButton runat="server" ID="imgUpdate" ImageUrl="~/Images/save.gif" CommandName="Update" AlternateText="Salva il testo visualizzato per il software corrente" />
                                    <asp:ImageButton runat="server" ID="imgCancel" ImageUrl="~/Images/cancel.gif" CommandName="Cancel" AlternateText="Annulla l'operazione" />
                                </EditItemTemplate>
                                
                                <ItemTemplate>
                                    <asp:ImageButton runat="server" ID="imgEdit" CommandName="Edit" ImageUrl="" />
                                    <asp:ImageButton runat="server" ID="imgDelete" ImageUrl="~/Images/delete.gif" CommandName="Delete" AlternateText="Elimina il testo visualizzato per il software corrente" OnClientClick="return confirm('Attenzione, il testo visualizzato per il software corrente sarà eliminato, si desidera procedere con l\'operazione? ')" />
                                </ItemTemplate>
                                <HeaderStyle Width="6%" HorizontalAlign="Right" />
                                <ItemStyle Width="6%" HorizontalAlign="Right" />                        
                            </asp:TemplateField>
                        </Columns>
                    </asp:GridView>
                </div>
            </div>
        </asp:Panel>
        </ContentTemplate>
    </asp:UpdatePanel>
    
</asp:Content>
