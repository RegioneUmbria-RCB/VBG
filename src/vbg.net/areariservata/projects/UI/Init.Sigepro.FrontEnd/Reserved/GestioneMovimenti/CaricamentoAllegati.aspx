<%@ Page Title="Caricamento allegati" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="CaricamentoAllegati.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.CaricamentoAllegati" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>



<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">

    <script type="text/javascript">

        $(function () {
            var txtDescrizioneAllegato = $('#<%=txtDescrizioneAllegato.Inner.ClientID%>');

            $('#<%=cmdAggiungiAllegato.ClientID %>').click(function (e) {

                txtDescrizioneAllegato.attr('required', 'required');

                e.preventDefault();
                $('#bottoniMovimento').fadeOut(function () {
                    $('#bottoniAllegato').fadeIn();
                });
            });

            $('#attenderePrego').css('display', 'none');

            $('#<%=cmdAnnullaAggiuntaAllegato.ClientID %>').click(function (e) {

			    e.preventDefault();

			    txtDescrizioneAllegato.removeAttr('required');

			    $('#bottoniAllegato').fadeOut(function () {
			        $('#bottoniMovimento').fadeIn();
			    });
			});

			$('.uploadAllegato').click(function (e) {

			    var form = document.forms[0];

			    if (!form.checkValidity || form.checkValidity()) {
			        $('#caricamentoFileIncorso').modal('show');
			    }

			});
        });
	</script>

</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <div class="inputForm">
        <div class="descrizioneStep">
            In questo passaggio è possibile caricare documenti che verranno trasmessi allo sportello.<br />
            Fare click su "Aggiungi allegato" per aggiungere un nuovo documento.
        </div>

        <div id="fldSetAllegati" runat="server" visible='<%# Convert.ToInt32(DataBinder.Eval( MovimentoDaEffettuare,"Allegati.Count" )) > 0 %>'>
            <h3>Allegati inseriti</h3>

            <asp:GridView ID="dgAllegatiMovimento" runat="server"
                GridLines="None"
                AutoGenerateColumns="False"
                OnRowCommand="OnRowCommand"
                DataSource='<%# DataBinder.Eval( MovimentoDaEffettuare, "Allegati" ) %>'
                CssClass="table">
                <Columns>
                    <asp:BoundField HeaderText="Descrizione" DataField="Descrizione" />
                    <asp:TemplateField HeaderText="Nome file">
                        <ItemTemplate>
                            <asp:HyperLink ID="lnkMostraAllegato" runat="server"
                                NavigateUrl='<%# Eval( "IdAllegato", "~/MostraOggetto.ashx?idComune=" + IdComune + "&CodiceOggetto={0}" ) %>'
                                Target="_blank"
                                Text='<%# Eval("Note") %>' />

                            <%if (this.RichiedeFirmaDigitale) { %>
                                <div>
                                    <asp:Label runat="server" ID="lblMessaggioFirmaDigitale" Text="Attenzione, il file non è firmato digitalmente oppure non contiene firme digitali valide"
                                        CssClass="errori"
                                        Visible='<%# !(bool)Eval("FirmatoDigitalmente")%>' />
                                </div>
                            <%} %>
                        </ItemTemplate>
                    </asp:TemplateField>
                    <asp:TemplateField HeaderText="">
                        <ItemTemplate>
                            <asp:LinkButton ID="lnkElimina" runat="server"
                                CommandArgument='<%# Eval("IdAllegato") %>'
                                Text="Elimina"
                                OnClientClick="return confirm('Eliminare l\'allegato selezionato\?')"
                                OnClick="EliminaAllegato" />

                            <asp:LinkButton runat="server"
                                ID="lnkFirma"
                                Text="Firma"
                                Visible='<%# this.RichiedeFirmaDigitale && !(bool)Eval("FirmatoDigitalmente") %>'
                                CommandName="Firma"
                                CommandArgument='<%# Eval("IdAllegato") %>' />
                        </ItemTemplate>
                    </asp:TemplateField>
                </Columns>
                <EmptyDataTemplate>
                    <div>Non sono ancora stati caricati allegati</div>
                </EmptyDataTemplate>
            </asp:GridView>

        </div>

        <div id="bottoniAllegato" style='<%= MostraBottoniAllegato ? "": "display:none"%>'>
            <h3>Aggiungi allegato</h3>

            <%if (PermettiNoteAllegato) {%>
                <ar:TextBox runat="server" ID="txtDescrizioneAllegato" Label="Descrizione *" TextMode="MultiLine" Columns="60" Rows="5"/>
            <%} %>
            <ar:ArFileUpload runat="server" id="fuAllegato" Label="Allegato" HelpText='<%# this.RichiedeFirmaDigitale ? "Il file deve essere firmato digitalmente" : String.Empty %>' />
            
            <div class="bottoni">
                <asp:Button runat="server" ID="cmdCaricaAllegato" Text="Aggiungi allegato" OnClick="cmdCaricaAllegato_Click" class="uploadAllegato" />
                <asp:Button runat="server" ID="cmdAnnullaAggiuntaAllegato" Text="Annulla" />
            </div>

        </div>

        <fieldset id="bottoniMovimento" style='<%= MostraBottoniAllegato ? "display:none": ""%>'>
            <div class="bottoni">
                <asp:Button runat="server" ID="cmdAggiungiAllegato" Text="Aggiungi allegato" />

                <asp:Button runat="server" ID="cmdTornaIndietro" Text="Torna indietro" OnClick="cmdTornaIndietro_Click" />
                <asp:Button runat="server" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
            </div>
        </fieldset>
    </div>

    <div id="attenderePrego">
        L'invio di un file di grandi dimensioni potrebbe richiedere anche alcuni minuti.<br />
        <br />
        Attendere senza effettuare alcuna operazione.
    </div>
</asp:Content>
