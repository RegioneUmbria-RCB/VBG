<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="GrigliaAllegati.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.GrigliaAllegati" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:GridView runat="server" ID="gvAllegati"
    AutoGenerateColumns="false"
    DataKeyNames="Id"
    OnRowDeleting="OnRowDeleting"
    OnRowUpdating="OnRowUpdating"
    OnRowCommand="OnRowCommand"
    GridLines="None"
    CssClass="griglia-allegati table">
    <Columns>
        <asp:TemplateField HeaderText="&nbsp;" ItemStyle-Width="48px">
            <ItemTemplate>
                <ar:AttributiAllegato runat="server" ID="attributiAllegato"
                    Obbligatorio='<%# Eval("Richiesto") %>'
                    ContieneNote='<%# Eval("HaNote") %>'
                    RichiedeFirma='<%# Eval("RichiedeFirmaDigitale") %>'
                    IdAllegato='<%# Eval("Id") %>' />
            </ItemTemplate>
        </asp:TemplateField>

        <asp:TemplateField HeaderText="Descrizione">
            <ItemTemplate>
                <div style="text-align: justify">
                    <asp:Literal runat="server" ID="ltrDescrizioneallegato" Text='<%# Eval("Descrizione") %>' />
                </div>
            </ItemTemplate>
        </asp:TemplateField>

        <asp:TemplateField HeaderText="Modello" ItemStyle-Width="96px" ItemStyle-HorizontalAlign="Center">
            <ItemTemplate>
                <div class="downloadModelli">

                    <asp:Panel runat="server" ID="pnlDownloadConPrecompilazione" class="dropdown" Visible='<%# Eval("ConsenteDownloadModello") %>'>

                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            <i class="glyphicon glyphicon-cloud-download"></i>
                            <span class="caret"></span>
                        </button>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">

                            <li>
                                <asp:HyperLink runat="server" ID="lnkDownloadModello"
                                    Target="_blank"
                                    Text="<i class='glyphicon glyphicon-cloud-download'></i> Scarica modello"
                                    Visible='<%# Eval("HaLinkDownloadSenzaPrecompilazione") %>'
                                    NavigateUrl='<%#Eval("LinkDownloadSenzaPrecompilazione") %>' />
                            </li>

                            <li>
                                <asp:HyperLink runat="server" ID="lnkMostraPdf"
                                    Target="_blank"
                                    Text="<i class='fa fa-file-pdf-o' aria-hidden='true'></i> Scarica in formato Pdf"
                                    Visible='<%#Eval("HaLinkPdf") %>'
                                    NavigateUrl='<%#Eval("LinkPdf") %>' />

                            </li>

                            <li>

                                <asp:HyperLink runat="server" ID="lnkMostraPdfCompilabile"
                                    Target="_blank"
                                    Text="<i class='fa fa-file-pdf-o' aria-hidden='true'></i> Scarica in formato Pdf compilabile"
                                    Visible='<%#Eval("HaLinkPdfCompilabile") %>'
                                    NavigateUrl='<%#Eval("LinkPdfCompilabile") %>' />
                            </li>

                            <li>
                                <asp:HyperLink runat="server" ID="lnkMostraRtf"
                                    Target="_blank"
                                    Text="<i class='fa fa-file-word-o' aria-hidden='true'></i> Scarica in formato Rtf"
                                    Visible='<%#Eval("HaLinkRtf") %>'
                                    NavigateUrl='<%#Eval("LinkRtf") %>' />
                            </li>

                            <li>
                                <asp:HyperLink runat="server" ID="lnkMostraDoc"
                                    Target="_blank"
                                    Text="<i class='fa fa-file-word-o' aria-hidden='true'></i> Scarica in formato Word"
                                    Visible='<%#Eval("HaLinkDoc") %>'
                                    NavigateUrl='<%#Eval("LinkDoc") %>' />
                            </li>

                            <li>
                                <asp:HyperLink runat="server" ID="lnkMostraOd"
                                    Target="_blank"
                                    Text="<i class='fa fa-file-text-o' aria-hidden='true'></i>Scarica in formato Open Document"
                                    Visible='<%#Eval("HaLinkOO") %>'
                                    NavigateUrl='<%#Eval("LinkOO") %>' />
                            </li>
                        </ul>
                    </asp:Panel>
                </div>
            </ItemTemplate>
            <EditItemTemplate>
                &nbsp;
            </EditItemTemplate>
        </asp:TemplateField>
        <asp:TemplateField HeaderText="Nome File" ItemStyle-Width="390px">
            <ItemTemplate>
                <div class="displayPanel" idfile='<%# Eval("ID") %>'>

                    <div class='<%# "form-group has-feedback " + ((bool)Eval("MostraAvvertimentoFirma") ? "has-error" : "has-success") %>' runat="server" visible='<%# Eval("HaFile") %>'>
                        <asp:HyperLink runat="server" ID="lnkMostraAllegato"
                            Target="_self"
                            NavigateUrl='<%#Eval("LinkDownloadFile") %>'
                            ToolTip='<%# Eval("NomeFile") %>'
                            Text='<%# "<i class=\"glyphicon glyphicon-eye-open\"></i> " + Eval("NomeFile").ToString() %>'
                            CssClass="form-control"
                            Visible='<%# Eval("HaFile") %>' />

                        <span class="glyphicon glyphicon-exclamation-sign form-control-feedback" aria-hidden="true" runat="server" visible='<%# Eval("MostraAvvertimentoFirma") %>'></span>
                       
                        <div class="help-block with-errors" runat="server" visible='<%# Eval("MostraAvvertimentoFirma") %>'>
                            Attenzione, il file deve essere firmato digitalmente
                        </div>

                        <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true" runat="server" visible='<%# !(bool)Eval("MostraAvvertimentoFirma") %>'></span>

                    </div>
                </div>

                <div class="editPanel" idfile='<%# Eval("ID") %>'>
                    <asp:FileUpload runat="server" ID="EditPostedFile" CssClass="form-control" Visible='<%#	(!(bool) Eval("HaFile")) %>' />
                </div>
            </ItemTemplate>
        </asp:TemplateField>
        <asp:TemplateField ItemStyle-HorizontalAlign="Right" ItemStyle-Width="100px">
            <ItemTemplate>
                <div class="displayPanel" idfile='<%# Eval("ID") %>' style="text-align:left;">
                    <asp:LinkButton ID="lnkEditAllegato"
                        runat="server"
                        CssClass="editLink actionButton"
                        idFile='<%# Eval("ID") %>'
                        CommandName="Edit"
                        CausesValidation="false"
                        Visible='<%# Eval("MostraBottoneAllega") %>' Style="display: block">
                        <i class="glyphicon glyphicon-cloud-upload"></i>
                        Allega
                        
                    </asp:LinkButton>


                    <% if (this.PermettiAllegatiMultipili)  {%>
					    <asp:LinkButton ID="LinkButton1" 
									    runat="server" 
									    CommandArgument='<%# Eval("ID") %>' 
									    CommandName="AllegaMultipli" 
									    CausesValidation="false"
                                        CssClass="actionButton"
									    Visible='<%# Eval("MostraBottoneAllega") %>' style="display:block;white-space:nowrap">
                            
                            <i class="fa fa-files-o" aria-hidden="true"></i>
                            Allegati multipli
                            
					    </asp:LinkButton>
                    <%} %>

                    <asp:LinkButton runat="server" ID="lnkModificaPdfCompilabile"
                        ToolTip='Compila il modello in formato Pdf'
                        CommandName="Compila"
                        Visible='<%# Eval("MostraBottoneCompila") %>'
                        CommandArgument='<%# Eval("ID") %>'
                        Style="display: block; white-space: nowrap;"
                        CssClass="actionButton">
                        
                        <i class="glyphicon glyphicon-pencil"></i>
                        Compila on-line
                         
                    </asp:LinkButton>

                    <asp:LinkButton ID="lnkFirma"
                        runat="server"
                        CommandName="Firma"
                        CommandArgument='<%# Eval("CodiceOggetto") %>'
                        CausesValidation="false"
                        Visible='<%# Eval("MostraBottoneFirma") %>'
                        Style="display: block; white-space: nowrap;"
                        CssClass="actionButton">

                        <i class="glyphicon glyphicon-pencil"></i> 
                        Firma
                        
                    </asp:LinkButton>

                    <asp:LinkButton ID="lnkEliminaAllegato"
                        runat="server"
                        CommandName="Delete"
                        CausesValidation="false"
                        OnClientClick="return confirm('Eliminare l\'allegato selezionato?');"
                        Visible='<%# Eval("MostraBottoneRimuovi") %>'
                        Style="display: block; white-space: nowrap;"
                        CssClass="actionButton text-danger">

                        <i class="glyphicon glyphicon-trash"></i>
                        Rimuovi
                         
                    </asp:LinkButton>
                </div>

                <div class="editPanel" idfile='<%# Eval("ID") %>' style="text-align:left;">
                    <asp:LinkButton ID="LinkButton3"
                        runat="server"
                        CssClass="sendLink actionButton"
                        idFile='<%# Eval("ID") %>'
                        CommandName="Update"
                        Visible='<%# (!(bool)Eval("HaFile")) %>' Style="display: block">

                        <i class="glyphicon glyphicon-ok"></i>
                        Invia
                    </asp:LinkButton>

                    <asp:LinkButton ID="LinkButton2"
                        runat="server"
                        CssClass="cancelLink actionButton"
                        idFile='<%# Eval("ID") %>'
                        CommandName="Cancel"
                        Visible='<%# (!(bool)Eval("HaFile")) %>'
                        CausesValidation="false" Style="display: block">

                        <i class="glyphicon glyphicon-remove"></i>
                        Annulla
                    </asp:LinkButton>
                </div>
            </ItemTemplate>
        </asp:TemplateField>
    </Columns>
</asp:GridView>
