<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="SostituzioniDocumentaliGrid.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.SostituzioniDocumentaliGrid" %>
<%@ Register TagPrefix="uc1" Src="~/Reserved/GestioneMovimenti/FileDownload.ascx" TagName="FileDownload" %>


<asp:Repeater runat="server" ID="rptDocumentiSostituibili">

    <ItemTemplate>
        <div class="panel panel-default documento-sostituibile">
            <div class="panel-heading">
                <asp:Literal runat="server" Text='<%#Eval("Descrizione") %>' />
            </div>
            <div class="panel-body">
                <div class="file-originale">
                    <span>Nome file:</span>
                    <uc1:FileDownload runat="server" DataSource='<%# DataBinder.Eval(Container, "DataItem") %>'></uc1:FileDownload>
                </div>

                <asp:Panel runat="server" ID="pnlSostituzione" Visible='<%# DataBinder.Eval(Container.DataItem, "DocumentoSostitutivo") != null %>' Style="margin-top: 0.3em">
                    <span>Sostituito da:</span>
                    <uc1:FileDownload runat="server" DataSource='<%# DataBinder.Eval(Container.DataItem, "DocumentoSostitutivo") %>'></uc1:FileDownload>
                </asp:Panel>


                <div class="modal fade upload-documento-sostitutivo" tabindex="-1" role="dialog">
                  <div class="modal-dialog" role="document">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">
                            Selezionare un file per sostituire 
                            <b>
                                "<asp:Literal runat="server" ID="ltrNomeFileOriginale" Text='<%#Eval("Descrizione") %>' />"
                            </b>

                        </h4>
                      </div>
                      <div class="modal-body">
                        <p>
                            <%if (this.RichiedeFirmaDigitale) {%>
                                <div class="help-block">
                                    <i>Il file deve essere firmato digitalmente</i>
                                </div>
                            <%} %>

                            <div>
                                <asp:FileUpload runat="server" ID="fuFileSostitutivo" CssClass="form-control" Style="width: 100%; margin-top: 1.0em" />
                            </div>
                        </p>
                      </div>
                      <div class="modal-footer">
                        <asp:Button runat="server" ID="cmdSostituisciDocumento" 
                                    CssClass="btn btn-default" 
                                    Text="Conferma" 
                                    OnClick="cmdSostituisciDocumento_Click" 
                                    CommandArgument='<%#Eval("CommandArgument") %>' />
                      </div>
                    </div><!-- /.modal-content -->
                  </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->




<%--                <div class="upload-documento-sostitutivo" data-title="Selezionare il file da caricare">
                    <div>
                        Selezionare il file che andrà a sostituire l'allegato 
                                <b>"<asp:Literal runat="server" ID="ltrNomeFileOriginale" Text='<%#Eval("Descrizione") %>' />"
                                </b>
                    </div>

                    <%if (this.RichiedeFirmaDigitale)
                      {%>
                    <div style="margin-top: 1.0em">
                        <i>Il file deve essere firmato digitalmente</i>
                    </div>
                    <%} %>

                    <div>
                        <asp:FileUpload runat="server" ID="fuFileSostitutivo" Style="width: 100%; margin-top: 1.0em" />
                    </div>

                    <div class="bottoni">
                        <asp:Button runat="server" ID="cmdSostituisciDocumento" Text="Conferma" OnClick="cmdSostituisciDocumento_Click" CommandArgument='<%#Eval("CommandArgument") %>' />
                    </div>
                </div>--%>
            </div>

            <div class="panel-footer azioni">
                <asp:Panel runat="server" ID="Panel2" Visible='<%# DataBinder.Eval(Container.DataItem, "DocumentoSostitutivo") == null %>'>
                    <i class="ion-upload"></i>
                    <a href="#" class="cmd-sostituisci-documento">Sostituisci documento</a>
                </asp:Panel>

                <asp:Panel runat="server" ID="Panel1" Visible='<%# DataBinder.Eval(Container.DataItem, "DocumentoSostitutivo") != null %>'>
                    <i class="ion-trash-a"></i>

                    <asp:LinkButton runat="server" ID="cmdAnnullaSostituzione"
                        CommandArgument='<%#Eval("DocumentoSostitutivo.CodiceOggetto") %>'
                        Text="Annulla sostituzione"
                        OnClick="cmdAnnullaSostituzione_Click"
                        OnClientClick="return confermaCanellazione();" />
                </asp:Panel>
            </div>
        </div>
    </ItemTemplate>
</asp:Repeater>
