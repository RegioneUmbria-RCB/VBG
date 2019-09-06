<%@ Page Title="Untitled" Async="true" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="GestioneAllegatiDatiDinamici.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneAllegatiDatiDinamici" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<%@ Register TagPrefix="cc2" Namespace="Init.Sigepro.FrontEnd.WebControls.Common" Assembly="Init.Sigepro.FrontEnd.WebControls" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content runat="server" ContentPlaceHolderID="head">

    <style>
        .azioni {
            display: inline-block;
        }

            .azioni a {
                display: block;
                text-align: left;
            }
    </style>
</asp:Content>


<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">
    <script type="text/javascript">
        var preloadedImg = new Image();
        preloadedImg.src = '<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>';

        $(function () {
            $(".bottoneInvio").click(function (e) {
                $('#caricamentoFileIncorso').modal('show');
            });

            $('.upload-allegato').on('change', function (e) {
                var el = $(this),
                    bottoneInvia = el.closest('tr').find('.bottone-invio');

                if (bottoneInvia.length > 0) {
                    bottoneInvia[0].click();
                }
            });
        });

        function triggerOpenFile(fileUploadId) {
            var el = document.getElementById(fileUploadId);

            //el.show();
            el.focus();

            setTimeout(function () {

                el.click();
            }, 1000);

        }
    </script>



    <ar:AttributiAllegato runat="server" ID="ltrLegendaAttributi" NascontiNoteCompilazioneInLegenda="true" Legenda="true" />

    <asp:GridView runat="server" ID="gvRiepiloghiDatiDinamici"
        GridLines="None"
        AutoGenerateColumns="false"
        DataKeyNames="IdModello,IndiceMolteplicita"
        OnRowDeleting="gvRiepiloghiDatiDinamici_RowDeleting"
        OnRowEditing="gvRiepiloghiDatiDinamici_RowEditing"
        OnRowCancelingEdit="gvRiepiloghiDatiDinamici_RowCancelingEdit"
        OnRowUpdating="gvRiepiloghiDatiDinamici_RowUpdating"
        OnRowCommand="gvRiepiloghiDatiDinamici_RowCommand"
        CssClass="griglia-allegati table">
        <Columns>
            <asp:TemplateField HeaderText="&nbsp;" ItemStyle-Width="32px">
                <ItemTemplate>
                    <ar:AttributiAllegato runat="server" ID="attributiAllegato" Obbligatorio='<%# Eval("Richiesto") %>' RichiedeFirma='<%# Eval("RichiedeFirmaDigitale") %>' />
                </ItemTemplate>
            </asp:TemplateField>
            <asp:TemplateField>
                <HeaderTemplate>
                    Scheda
                </HeaderTemplate>
                <ItemTemplate>
                    <asp:Literal runat="server" ID="ltrNomeFile" Text='<%# Eval("NomeScheda")%>' />
                </ItemTemplate>
            </asp:TemplateField>
            <asp:TemplateField ItemStyle-HorizontalAlign="Center">
                <HeaderTemplate>
                    Modello
                </HeaderTemplate>
                <ItemTemplate>
                    <%# Eval("LinkDownloadModello")%>
                    <asp:LinkButton runat="server" ID="lnkDownloadModello" Text="<i class='glyphicon glyphicon-cloud-download'></i>" CommandArgument='<%#Eval("CommandArgument")%>' OnClick="MostraModelloDinamico" />
                </ItemTemplate>
            </asp:TemplateField>
            <asp:TemplateField>
                <HeaderTemplate>Documento</HeaderTemplate>
                <ItemTemplate>

                    <div class='<%# "form-group has-feedback " + ((bool)Eval("MostraBottoneFirma") ? "has-error" : "has-success") %>' runat="server" visible='<%# ((int?)Eval("CodiceOggetto")).HasValue %>'>

                        <asp:HyperLink runat="server" ID="lnkDownloadOggetto"
                            NavigateUrl='<%# Eval( "CodiceOggetto" , "~/Reserved/MostraOggettoFo.ashx?IdComune=" + IdComune + "&Software=" + Software + "&CodiceOggetto={0}&IdPresentazione=" + IdDomanda)%>'
                            Text='<%# Eval("NomeFile") %>'
                            CssClass="form-control"
                            Visible='<%# ((int?)Eval("CodiceOggetto")).HasValue %>' />

                        <span class="glyphicon glyphicon-exclamation-sign form-control-feedback" aria-hidden="true" runat="server" visible='<%# Eval("MostraBottoneFirma") %>'></span>

                        <div class="help-block with-errors" runat="server" visible='<%# Eval("MostraBottoneFirma") %>'>
                            Attenzione, il file deve essere firmato digitalmente
                        </div>

                        <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true" runat="server" visible='<%# !(bool)Eval("MostraBottoneFirma") %>'></span>

                    </div>

                </ItemTemplate>

                <EditItemTemplate>
                    <asp:FileUpload runat="server" ID="fuAllegato" CssClass="upload-allegato form-control" />

                </EditItemTemplate>
            </asp:TemplateField>

            <asp:TemplateField ItemStyle-HorizontalAlign="Right">
                <HeaderTemplate>&nbsp;</HeaderTemplate>
                <ItemTemplate>
                    <div class="azioni">
                        <asp:LinkButton runat="server"
                            ID="lnkEdit"
                            CommandName="Edit"
                            Visible='<%# !((int?)Eval("CodiceOggetto")).HasValue %>'>
                        <i class="glyphicon glyphicon-cloud-upload"></i>
                        Allega un nuovo file
                        </asp:LinkButton>

                        <asp:LinkButton ID="lnkFirma"
                            runat="server"
                            CommandName="Firma"
                            CommandArgument='<%# Eval("CodiceOggetto") %>'
                            CausesValidation="false"
                            Style="white-space: nowrap"
                            Visible='<%# Eval("MostraBottoneFirma") %>'>

                        <i class="glyphicon glyphicon-pencil"></i>
                        Firma on-line
                        </asp:LinkButton>

                        <asp:LinkButton runat="server" ID="lnkElimina"
                            CommandName="Delete"
                            OnClientClick="return confirm('L\'allegato corrente verrà eliminato. Per passare allo step successivo sarà necessario allegare un file firmato digitalmente. Proseguire\?')"
                            Visible='<%# ((int?)Eval("CodiceOggetto")).HasValue && ((bool)Eval("Richiesto"))%>'>
                                               
                        <i class="glyphicon glyphicon-cloud-upload"></i>
                        Allega un nuovo file
                        </asp:LinkButton>

                    </div>
                </ItemTemplate>

                <EditItemTemplate>
                    <div class="azioni">
                        <asp:LinkButton runat="server" ID="lnkUpdate" CommandName="Update" CssClass="bottoneInvio bottone-invio">
                            <i class="glyphicon glyphicon-ok"></i>
                            Invia file
                        </asp:LinkButton>
                        <asp:LinkButton runat="server" ID="lnkCancel" CommandName="Cancel" CssClass="errori">
                            <i class="glyphicon glyphicon-remove"></i>
                            Annulla
                        </asp:LinkButton>
                    </div>
                </EditItemTemplate>
            </asp:TemplateField>

        </Columns>
    </asp:GridView>
</asp:Content>
