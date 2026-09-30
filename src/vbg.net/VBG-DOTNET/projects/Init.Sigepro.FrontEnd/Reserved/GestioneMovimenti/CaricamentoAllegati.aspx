<%@ Page Title="Caricamento allegati" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="CaricamentoAllegati.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.CaricamentoAllegati" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>



<asp:Content ID="Content1" ContentPlaceHolderID="headPagina" runat="server">

    <script type="text/javascript">

        $(function () {
            var txtDescrizioneAllegato = $('#<%=txtDescrizioneAllegato.Inner.ClientID%>');

            $('#<%=cmdAggiungiAllegato.ClientID %>').click(function (e) {

                txtDescrizioneAllegato.attr('required', 'required');
                let upload = document.getElementById('<%=fuAllegato.Inner.ClientID%>');

                upload.addEventListener('change', (e) => {

                    document.getElementById('<%=cmdCaricaAllegato.ClientID%>').click();
                    
                });

                e.preventDefault();
                $('#bottoniMovimento').fadeOut(function () {
                    $('#bottoniAllegato').fadeIn();
                });
            });


            $('#<%=cmdAnnullaAggiuntaAllegato.ClientID %>').click(function (e) {

			    e.preventDefault();

			    txtDescrizioneAllegato.removeAttr('required');

			    $('#bottoniAllegato').fadeOut(function () {
			        $('#bottoniMovimento').fadeIn();
			    });
			});
            
            document.getElementById('<%=cmdCaricaAllegato.ClientID%>')
                    .addEventListener('click', (e) => {

			    var form = document.forms[0];

			    if (!form.checkValidity || form.checkValidity()) {
			        mostraModalCaricamento();
			    }
            });

            document.querySelectorAll('.elimina-allegato').forEach((item) => {
                item.addEventListener('click', () => {
                    mostraModalCaricamento();
                });
            });

            document.getElementById('<%=cmdProcedi.ClientID%>').addEventListener('click', () => {
                mostraModalCaricamento();
            });
        });
    </script>

</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <div class="inputForm">
        <div class="descrizioneStep">
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="MOVIMENTI.CARICAMENTO_ALLEGATI.DESCRIZIONE_PASSAGGIO" ValoreDefault="In questo passaggio è possibile caricare documenti che verranno trasmessi allo sportello.<br />Fare click su 'Aggiungi allegato' per aggiungere un nuovo documento." />
            
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
                                NavigateUrl='<%# UrlDownload(Eval( "IdAllegato")) %>'
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
                                CssClass="elimina-allegato"
                                OnClientClick="return confirm('Eliminare l\'allegato selezionato\?')"
                                OnClick="EliminaAllegato" />

                          <%--  <asp:LinkButton runat="server"
                                ID="lnkFirma"
                                Text="Firma"
                                Visible='<%# this.RichiedeFirmaDigitale && !(bool)Eval("FirmatoDigitalmente") %>'
                                
                                CommandName="Firma"
                                CommandArgument='<%# Eval("IdAllegato") %>' />--%>
                        </ItemTemplate>
                    </asp:TemplateField>
                </Columns>
                <EmptyDataTemplate>
                    <div>Non sono ancora stati caricati allegati</div>
                </EmptyDataTemplate>
            </asp:GridView>

        </div>

        <div id="bottoniAllegato" style='<%= MostraBottoniAllegato ? "": "display:none"%>'>
            <h3>
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="MOVIMENTI.CARICAMENTO_ALLEGATI.TITOLO_AGGIUNTA_ALLEGATO" ValoreDefault="Aggiungi allegato" />
            </h3>

            <%if (PermettiNoteAllegato) {%>
                <ar:TextBox runat="server" ID="txtDescrizioneAllegato" Label="Descrizione *" TextMode="MultiLine" Columns="60" Rows="5"/>
            <%} %>
            <ar:ArFileUpload runat="server" id="fuAllegato" Label="Allegato" HelpText='<%# this.RichiedeFirmaDigitale ? "Il file deve essere firmato digitalmente" : String.Empty %>' />
            
            <div>
                <asp:Button CssClass="btn btn-default" runat="server" ID="cmdAnnullaAggiuntaAllegato" Text="Annulla" />
                <asp:Button CssClass="btn btn-primary" runat="server" ID="cmdCaricaAllegato" Text="Carica allegato" OnClick="cmdCaricaAllegato_Click" class="uploadAllegato" />
            </div>

        </div>

        <fieldset id="bottoniMovimento" style='<%= MostraBottoniAllegato ? "display:none": ""%>'>
            <div>
                

                <asp:Button runat="server" cssClass="btn btn-default" ID="cmdTornaIndietro" Text="Torna indietro" OnClick="cmdTornaIndietro_Click" />
                <asp:Button runat="server" cssClass="btn btn-primary" ID="cmdAggiungiAllegato" Text="Aggiungi allegato" />
                <asp:Button runat="server" cssClass="btn btn-primary" ID="cmdProcedi" Text="Procedi" OnClick="cmdProcedi_Click" />
            </div>
        </fieldset>
    </div>

</asp:Content>
