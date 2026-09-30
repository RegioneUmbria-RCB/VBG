<%@ Page Title="Procure" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
    AutoEventWireup="true" CodeBehind="GestioneProcure.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneProcure" %>

<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <script type="text/javascript">

        $(function () {
            $(".bottoneInvio").click(function (e) {

                $('.modal-caricamento-file-in-corso').modal('show');
                $('.bottoneAzioni').hide();

                $(":file").fadeOut('slow');


            });


            $(".bottone-allega-documento").on("click", function onAllegaDocumento(e) {
                var el = $(this),
                    codiceProcuratore = el.data("codiceProcuratore"),
                    codiceAnagrafe = el.data("codiceAnagrafe"),
                    tipoDocumento = el.data("tipoDocumento"),
                    hfCodiceProcuratore = $("#<%= hfCodiceProcuratore.ClientID%>"),
                    hfCodiceAnagrafe = $("#<%= hfCodiceAnagrafe.ClientID%>"),
                    hfTipoDocumento = $("#<%= hfTipoDocumento.ClientID%>"),
                    modal = $("#<%=bmAllegaDocumento.ClientID%>"),
                    modalTitle = modal.find(".modal-header>h3"),
                    bottoneOk = modal.find('.modal-ok-button'),
                    fileUpload = modal.find('input[type=file]');

                hfCodiceAnagrafe.val(codiceAnagrafe);
                hfCodiceProcuratore.val(codiceProcuratore);
                hfTipoDocumento.val(tipoDocumento);

                modalTitle.text(tipoDocumento === "procura" ? "Carica procura" : "Carica documento d'identità");

                modal.modal("show");

                fileUpload.on('change', function () {
                    if (bottoneOk.length) {
                        bottoneOk[0].click();
                        modal.modal("hide");
                        $('.modal-caricamento-file-in-corso').modal('show');
                    }
                });

                e.preventDefault();
            });
        });

    </script>

    <asp:GridView runat="server" ID="gvProcure" GridLines="None" AutoGenerateColumns="false"
        CssClass="table"
        DataKeyNames="CodiceProcuratore,CodiceAnagrafe"
        OnRowCommand="gvProcure_RowCommand">
        <Columns>
            <asp:TemplateField>
                <HeaderTemplate>
                    Rappresentato
                </HeaderTemplate>
                <ItemTemplate>
                    <%# Eval("NomeAnagrafe")%>
                </ItemTemplate>
            </asp:TemplateField>
            <asp:TemplateField>
                <HeaderTemplate>
                    Procuratore
                </HeaderTemplate>
                <ItemTemplate>
                    <%# Eval("NomeProcuratore")%>
                </ItemTemplate>
            </asp:TemplateField>
            <asp:TemplateField>
                <HeaderTemplate>Procura</HeaderTemplate>
                <ItemTemplate>


                    <div class='<%# "form-group has-feedback " + ((bool)Eval("AllegatoNecessitaFirma") ? "has-error" : "has-success") %>' runat="server" visible='<%# Eval("AllegatoPresente") %>'>
                        <asp:HyperLink runat="server"
                            ID="lnkDownloadOggetto"
                            Target="_blank"
                            NavigateUrl='<%# Eval( "PathDownload")%>'
                            Text='<%# Eval("NomeFile") %>' CssClass="form-control" />

                        <span class="glyphicon glyphicon-exclamation-sign form-control-feedback" aria-hidden="true" runat="server" visible='<%# Eval("AllegatoNecessitaFirma") %>'></span>
                       
                        <div class="help-block with-errors" runat="server" visible='<%# Eval("AllegatoNecessitaFirma") %>'>
                            Attenzione, il file deve essere firmato digitalmente
                        </div>

                        <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true" runat="server" visible='<%# !(bool)Eval("AllegatoNecessitaFirma") %>'></span>
                    </div>


                    <asp:LinkButton runat="server" ID="lnkEdit"
                        CommandName="EditAllegato"
                        CommandArgument="EditAllegato"
                        Visible='<%# !(bool)Eval("AllegatoPresente") %>'
                        data-codice-procuratore='<%# Eval("CodiceProcuratore") %>'
                        data-codice-anagrafe='<%# Eval("CodiceAnagrafe") %>'
                        data-tipo-documento='procura'
                        CssClass="bottone-allega-documento">
                        <i class="glyphicon glyphicon-cloud-upload"></i>
                        Allega procura
                    </asp:LinkButton>

                    <asp:LinkButton runat="server" ID="LinkButton1"
                        Text="Firma"
                        CommandName="Firma"
                        CommandArgument='<%# Eval("CodiceOggetto") %>'
                        Visible='<%# (bool)Eval("AllegatoNecessitaFirma")%>'
                        CssClass="bottoneAzioni">
                        <i class="glyphicon glyphicon-pencil"></i>
                        Firma
                    </asp:LinkButton>


                    <asp:LinkButton runat="server" ID="lnkElimina"
                        Text="Rimuovi"
                        OnClientClick="return confirm('Eliminare il file corrente\?')"
                        OnClick="lnkElimina_Click"
                        Visible='<%# Eval("AllegatoPresente") %>'
                        CssClass="bottoneAzioni text-danger">
                        <i class="glyphicon glyphicon-trash"></i>
                        Rimuovi
                    </asp:LinkButton>

                </ItemTemplate>

            </asp:TemplateField>

            <asp:TemplateField Visible="false">
                <HeaderTemplate>Documento d'identità</HeaderTemplate>
                <ItemTemplate>


                    <div class="form-group has-feedback has-success" runat="server" visible='<%# Eval("DocIdentitaPresente") %>'>
                        <asp:HyperLink runat="server"
                            ID="lnkDownloadDocumentoIdentita"
                            Target="_blank"
                            NavigateUrl='<%# Eval( "DocIdentitaPathDownload")%>'
                            Text='<%# Eval("DocIdentitaNomeFile") %>' CssClass="form-control" />
                        <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
                    </div>


                    <asp:LinkButton runat="server" ID="lnkEditDocIdentita"
                        Visible='<%# !(bool)Eval("DocIdentitaPresente") %>'
                        data-codice-procuratore='<%# Eval("CodiceProcuratore") %>'
                        data-codice-anagrafe='<%# Eval("CodiceAnagrafe") %>'
                        data-tipo-documento='documentoIdentita'
                        CssClass="bottone-allega-documento">
                        <i class="glyphicon glyphicon-cloud-upload"></i>
                        Allega documento identità
                    </asp:LinkButton>


                    <asp:LinkButton runat="server" ID="lnkEliminaDocumentoIdentita"
                        Text="Rimuovi"
                        OnClientClick="return confirm('Eliminare il file corrente\?')"
                        OnClick="lnkEliminaDocumentoIdentita_Click"
                        Visible='<%# Eval("DocIdentitaPresente") %>'
                        CssClass="bottoneAzioni text-danger">
                        <i class="glyphicon glyphicon-trash"></i>
                        Rimuovi
                    </asp:LinkButton>

                </ItemTemplate>

            </asp:TemplateField>

        </Columns>
    </asp:GridView>



    <ar:BootstrapModal runat="server" ID="bmAllegaDocumento" OnOkClicked="bmAllegaDocumento_OkClicked">
        <ModalBody>
            <asp:HiddenField runat="server" ID="hfCodiceProcuratore" />
            <asp:HiddenField runat="server" ID="hfCodiceAnagrafe" />
            <asp:HiddenField runat="server" ID="hfTipoDocumento" />

            <ar:ArFileUpload runat="server" ID="fuDocumento" Label="Seleziona il file da caricare" />
        </ModalBody>
    </ar:BootstrapModal>
</asp:Content>
