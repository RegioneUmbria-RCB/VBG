<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="visura-documenti.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.Visura.visura_documenti" %>
<div class="visura-documenti">
    <asp:GridView GridLines="None" runat="server" ID="gvAllegati" CssClass="table" AutoGenerateColumns="false"
        OnRowDataBound="gvAllegati_RowDataBound"
        EmptyDataText="<div class='alert alert-info'>La pratica non contiene documenti</div>">
        <Columns>
            <asp:TemplateField HeaderText="Documento">
                <ItemTemplate>
                    <asp:Literal runat="server" ID="ltrDescrizione" Text='<%# Eval("Descrizione") %>' />
                    <div class="md5-allegato">
                        <small>
                            <asp:Literal runat="server" ID="ltrNomeFile" Text='<%# Eval("NomeFile")%>' />

                            <asp:Literal runat="server" ID="ltrMd5" Text='<%# Eval( "Md5", " (MD5: {0})" )%>' Visible='<%# Eval("HasMd5")%>' />
                        </small> 
                        
                    </div>
                </ItemTemplate>
            </asp:TemplateField>
            <asp:BoundField HeaderText="Data caricamento" DataField="Data" DataFormatString="{0:dd/MM/yyyy}" HtmlEncode="false" />
            <asp:TemplateField ItemStyle-Width="16px">
                <ItemTemplate>
                    <asp:HyperLink runat="server" ID="hlDownloadAllegato" Target="_blank">
							Scarica
                    </asp:HyperLink>
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
    </asp:GridView>

</div>
