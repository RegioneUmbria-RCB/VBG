<%@ Page Title="s" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
    AutoEventWireup="true" CodeBehind="GestioneEndoPresenti_Allegati.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.GestioneEndoPresenti_Allegati" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>

<asp:Content ID="headerContent" ContentPlaceHolderID="head" runat="server">

    <%=LoadScript("~/js/app/uploadAllegati.js") %>

    <script type="text/javascript">
        $(function () {
            var loaderUrl = '<%=ResolveClientUrl("~/Images/ajax-loader.gif")%>';
                    var urlBaseNote = '<%=ResolveClientUrl("~/Reserved/InserimentoIstanza/GestioneAllegati_Note.ashx")%>';
                    var idComune = '<%=IdComune %>';
                    var token = '<%=UserAuthenticationResult.Token %>';
                    var software = '<%=Software %>';
                    var idDomanda = '<%=IdDomanda %>';
            var provenienza = 'E';

            new UploadAllegati(loaderUrl, urlBaseNote, idComune, token, software, idDomanda, provenienza);
        });

    </script>
</asp:Content>

<asp:Content ID="Content1" ContentPlaceHolderID="stepContent" runat="server">

    <ar:AttributiAllegato runat="server" ID="ltrLegendaAttributi" Legenda="true" NascontiNoteCompilazioneInLegenda="true" StaccoDoppio="false" />

    <asp:GridView runat="server" ID="gvAllegati" AutoGenerateColumns="false" DataKeyNames="Id"
        OnRowDeleting="OnRowDeleting" OnRowUpdating="OnRowUpdating" OnRowCommand="OnRowCommand"
        GridLines="None" CssClass="table">
        <Columns>
            <asp:TemplateField>
                <ItemTemplate>
                    <ar:AttributiAllegato runat="server" ID="attributiAllegato"
                        Obbligatorio='<%# Eval("Obbligatorio") %>'
                        RichiedeFirma='<%# Eval("RichiedeFirmaDigitale") %>'
                        IdAllegato='<%# Eval("Id") %>' />
                </ItemTemplate>
            </asp:TemplateField>

            <asp:TemplateField HeaderText="Allegato">
                <ItemTemplate>
                    <div style="text-align: justify">
                        <asp:Literal runat="server" ID="ltrDescrizioneallegato" Text='<%# Eval("Descrizione") %>' /><br />
                        <i>
                            <asp:Literal runat="server" ID="ltrRiferimentiDocumento" Text='<%# Eval("RiferimentiDocumento") %>' /></i>
                    </div>
                </ItemTemplate>
            </asp:TemplateField>

            <asp:TemplateField HeaderText="Nome File" ItemStyle-Width="350px">
                <ItemTemplate>
                    <div class="displayPanel" idfile='<%# Eval("ID") %>'>
                        <asp:HyperLink runat="server" ID="lnkMostraAllegato" Target="_self" NavigateUrl='<%#Eval("LinkDownloadFile") %>'
                            ToolTip='<%# Eval("NomeFile") %>' Text='<%# Eval("NomeFile") %>' Visible='<%# Eval("HaFile") %>' />
                        <div>
                            <asp:Label runat="server" ID="lblErroreProcura" CssClass="errori" Visible='<%#Eval("MostraBottoneFirma") %>'
                                Text="Attenzione, il file non è stato firmato digitalmente" />
                        </div>
                    </div>
                    <div class="editPanel" idfile='<%# Eval("ID") %>'>
                        <asp:FileUpload runat="server" ID="EditPostedFile" Visible='<%#	(!(bool) Eval("HaFile")) %>' />
                    </div>
                    <div class="sendPanel" idfile='<%# Eval("ID") %>'>
                    </div>
                </ItemTemplate>
            </asp:TemplateField>

            <asp:TemplateField ItemStyle-HorizontalAlign="Right" ItemStyle-Width="80px">
                <ItemTemplate>
                    <div class="displayPanel" idfile='<%# Eval("ID") %>'>
                        <asp:LinkButton ID="lnkEditAllegato" runat="server" Text="Allega" CssClass="editLink"
                            idFile='<%# Eval("ID") %>' CommandName="Edit" CausesValidation="false" Visible='<%# (!(bool)Eval("HaFile")) %>' />
                        <asp:LinkButton ID="lnkFirma" runat="server" Text="Firma" CommandName="Firma" CommandArgument='<%# Eval("CodiceOggetto") %>'
                            CausesValidation="false" Visible='<%# Eval("MostraBottoneFirma") %>' />
                        <asp:LinkButton ID="lnkEliminaAllegato" runat="server" Text="Rimuovi" CommandName="Delete"
                            CausesValidation="false" OnClientClick="return confirm('Eliminare l\'allegato selezionato?');"
                            Visible='<%# Eval("HaFile") %>' />
                    </div>
                    <div class="editPanel" idfile='<%# Eval("ID") %>'>
                        <asp:LinkButton ID="LinkButton3" runat="server" Text="Invia" CssClass="sendLink"
                            idFile='<%# Eval("ID") %>' CommandName="Update" Visible='<%# (!(bool)Eval("HaFile")) %>' />
                        <asp:LinkButton ID="LinkButton2" runat="server" Text="Annulla" CssClass="cancelLink"
                            idFile='<%# Eval("ID") %>' CommandName="Cancel" Visible='<%# (!(bool)Eval("HaFile")) %>'
                            CausesValidation="false" />
                    </div>
                </ItemTemplate>
            </asp:TemplateField>
        </Columns>
    </asp:GridView>
</asp:Content>
