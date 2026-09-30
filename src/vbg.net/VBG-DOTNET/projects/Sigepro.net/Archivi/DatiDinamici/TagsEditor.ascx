<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="TagsEditor.ascx.cs" Inherits="Sigepro.net.Archivi.DatiDinamici.TagsEditor" %>
<%@ Register TagPrefix="init" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils.Web" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.UI" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="init" Namespace="SIGePro.WebControls.Ajax" Assembly="SIGePro.WebControls" %>
<%@ Register TagPrefix="bs" Namespace="SIGePro.WebControls.Bootstrap" Assembly="SIGePro.WebControls" %>

<div class="tags-editor">
    <div>
        <div class="tags-summary">

            <div class="label">Tags:</div>

            <asp:Repeater runat="server" ID="rptTagsList">
                <ItemTemplate>
                    <div class="tags-summary_item" data-tag="<%# Container.DataItem %>">
                        #<%# Container.DataItem %>
                    </div>
                </ItemTemplate>
            </asp:Repeater>

            <asp:LinkButton runat="server" ID="btnAddTag" Text="Modifica..." OnClick="btnEditTags_Click" />
        </div>
    </div>

    <%if (ShowModal)
        { %>

    <script>
        vbg.ready(() => {
            document.getElementById('<%=this.txtNuovoTag.Item.ClientID%>').focus();
        });
    </script>

    <bs:BootstrapModal runat="server" ID="bmEdit" Title="Modifica tags campo" NoValidate="true" AlwaysVisible="true"
        OkText="Salva e chiudi"
        KoText="Annulla"
        OnOkClicked="bmEdit_OkClicked"
        OnKoClicked="bmEdit_KoClicked">
        <ModalBody runat="server">

            <div class="popup-tags-list">
                <asp:Repeater runat="server" ID="rptTagsEditor" OnItemCommand="rptTags_ItemCommand">
                    <ItemTemplate>
                        <div class="popup-tags-list_item">
                            <div class="me-2"><%# Container.DataItem %></div>
                            <div>
                                <asp:LinkButton runat="server" ID="btnDeleteTag" Text="Elimina" CommandName="Delete" CommandArgument='<%# Container.DataItem %>' CssClass="btn btn-danger btn-sm" />
                            </div>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>
            </div>


            <fieldset>
                <legend>Nuovo tag</legend>
                <init:LabeledTextBox runat="server" ID="txtNuovoTag" Descrizione="Nuovo tag" Required="true" Pattern="[A-Za-z][A-Za-z0-9_]+"/>
                <asp:Button runat="server" ID="cmdAggiungi" OnClick="cmdAggiungi_Click" Text="Aggiungi" />
            </fieldset>
        </ModalBody>
    </bs:BootstrapModal>
    <%} %>
</div>
