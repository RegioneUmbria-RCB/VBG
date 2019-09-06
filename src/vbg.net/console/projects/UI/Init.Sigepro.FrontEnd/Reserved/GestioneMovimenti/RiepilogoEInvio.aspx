<%@ Page Title="Riepilogo dei dati immessi" Language="C#" MasterPageFile="~/AreaRiservataMaster.Master" AutoEventWireup="true" CodeBehind="RiepilogoEInvio.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.RiepilogoEInvio" %>
<%@ Register Src="~/Reserved/GestioneMovimenti/FileDownload.ascx" TagPrefix="uc1" TagName="FileDownload" %>
<%@ Register TagPrefix="ar" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" Assembly="Init.Sigepro.FrontEnd.WebControls" %>


<asp:Content ID="Content1" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <style media="all">
        .inputForm li {
            padding-bottom: 10px;
            list-style-type: disc;
        }

        .inputForm ul {
            margin-left: 20px;
        }
    </style>
    <script type="text/javascript">
        function confermaInvio() {
            var value = confirm("Proseguendo con l'invio non sarà più possibile apportare modifiche ai dati immessi.\r\nContinuare?");

            if (value) {
                $('.modal-invio-dati-in-corso').modal('show');
            }
        }

        $(function () {
            var divComandi = $('#divComandi'),
                divSalvataggioNote = $('#divSalvataggioNote');

            divSalvataggioNote.css('display', 'none');

            $('#<%=txtNote.Inner.ClientID %>').change(function () {
		        divSalvataggioNote.fadeIn(function () {
		            $('#<%=cmdSalvaNote.ClientID%>').focus();
			    });
			    divComandi.fadeOut();
			});

		    $('#<%=txtNote.Inner.ClientID %>').keydown(function () {
		        divSalvataggioNote.fadeIn();
		        divComandi.fadeOut();
		    });

		});
    </script>

    <div class="inputForm">

        <%if (PermettiModificaNote) {%>
        <div>
            <ar:TextBox runat="server" ID="txtNote" 
                TextMode="MultiLine" 
                Columns="60" 
                Rows="5" 
                Text='<%# DataBinder.Eval(MovimentoDaEffettuare, "Note") %>' 
                Label="Note per lo sportello" />

            <div class="bottoni" id="divSalvataggioNote">
                <asp:Button runat="server" ID="cmdSalvaNote" Text="Aggiorna note" OnClick="cmdSalvaNote_Clinck" />
            </div>
        </div>
        <%} %>

        <%if (MovimentoDaEffettuare.RiepiloghiSchedeDinamiche != null && MovimentoDaEffettuare.RiepiloghiSchedeDinamiche.Count > 0) {%>
            <h3>Schede compilate</h3>
            <ul>
                <asp:Repeater runat="server" ID="rptSchedeCompilate">
                    <ItemTemplate>
                        <li>
                            <div>
                                <asp:Literal runat="server" ID="ltrNomeScheda" Text='<%# Eval("NomeScheda") %>' /></div>
                            <i>
                                <uc1:FileDownload runat="server" DataSource='<%#DataBinder.Eval(Container, "DataItem") %>' />
                            </i>
                        </li>
                    </ItemTemplate>
                </asp:Repeater>
            </ul>
        <%}%>

        <%if (SostituzioniDocumentaliPresenti){%>
        
        <h3>Sostituzioni documentali</h3>              
        <asp:Repeater runat="server" ID="rptSostituzioniDocumentali">
            <HeaderTemplate>
                <ul>
            </HeaderTemplate>

            <ItemTemplate>
                <li>
                    <div>
                        <asp:Literal runat="server" Text='<%# Eval("Descrizione") %>' />
                    </div>
                    <div>
                        <uc1:FileDownload runat="server" DataSource='<%#DataBinder.Eval(Container.DataItem, "FileSostitutivo") %>' />
                    </div>
                </li>
            </ItemTemplate>

            <FooterTemplate>
                </ul>
            </FooterTemplate>

        </asp:Repeater>       
                
        <%}%>

        <%if (MovimentoDaEffettuare.Allegati.Count() > 0) {%>

            <h3>Allegati inseriti</h3>

            <ul>
                <%foreach (var allegato in MovimentoDaEffettuare.Allegati)
                  {%>
                <li>
                    <%=allegato.Descrizione %><br />

                    <a href="<%= ResolveClientUrl(String.Format("~/MostraOggetto.ashx?IdComune={0}&Software={1}&codiceOggetto={2}", IdComune, Software, allegato.IdAllegato)) %>" target="_blank">
                        <i><%=allegato.Note%></i>
                    </a>
                </li>
                <%} %>
            </ul>
        <%} %>

        <div class="bottoni" id="divComandi">
            <asp:Button runat="server" ID="cmdTornaIndietro" Text="Torna indietro" OnClick="cmdTornaIndietro_Click" />
            <asp:Button runat="server" ID="cmdConferma" OnClick="cmdConferma_Click" Text="Trasmetti" OnClientClick="return confermaInvio();" />
        </div>

    </div>

</asp:Content>
