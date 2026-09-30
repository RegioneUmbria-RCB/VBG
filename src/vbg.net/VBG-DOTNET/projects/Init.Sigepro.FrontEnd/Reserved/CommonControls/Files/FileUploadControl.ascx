<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="FileUploadControl.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.CommonControls.Files.FileUploadControl" %>

<div data-file-id='<%=this.CodiceOggetto%>' class='<%=this.CssClass %>'>

    <div class='<%= "form-group has-feedback " + (!this.IsValid ? "has-error has-danger" : "") %>'>

        <label class="control-label" for='<%=EditPostedFile.ClientID %>'>
            <%=EtichettaControllo + (Required ? "<sup>*</sup>" : "") %>
        </label>

        <div runat="server" visible='<%#this.ModalitaVisualizzazione%>' class="input-group">

            <asp:HyperLink runat="server" ID="lnkMostraAllegato"
                Target="_blank"
                ToolTip='<%# this.NomeFile %>'
                Text='<%# this.NomeFile%>'
                CssClass="form-control" />

            <span class="glyphicon glyphicon-exclamation-sign form-control-feedback" aria-hidden="true" runat="server" visible='<%# !this.IsValid %>'></span>

            <div class="help-block with-errors" runat="server" visible='<%# !this.IsValid %>'>
                <%=this.ErrorMessage %>
            </div>
            <div class="input-group-addon btn btn-primary">
                <asp:LinkButton runat="server" ID="cmdSostituisci" CssClass="vbg-show-spinner" OnClick="cmdSostituisci_Click">Sostituisci</asp:LinkButton>
            </div>

        </div>

        <div runat="server" visible='<%#this.ModalitaInserimento%>'>
            <div class='<%= this.CodiceOggetto.HasValue? "input-group" : ""%>'>
                <asp:FileUpload runat="server" ID="EditPostedFile" CssClass="form-control"/>

                <div class="input-group-addon btn btn-primary" runat="server" visible='<%#this.CodiceOggetto.HasValue %>'>
                    <asp:LinkButton runat="server" ID="cmdAnnullaModifiche" CssClass="vbg-show-spinner" OnClick="cmdAnnullaModifiche_Click">Annulla</asp:LinkButton>
                </div>
                
            </div>
            <div class="help-block with-errors"></div>
            <div class="help-block"><%=this.ErrorMessage %></div>
        </div>
    </div>
</div>
