<%@ Page Title="Verifica stato dei pagamenti" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="VerificaStatoPagamentiEntraNext.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.VerificaStatoPagamentiEntraNext" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">

        $(function () {
            $('#<%=cmdProcedi.ClientID%>').on('click', e => {

                var message = "Attenzione! Proseguendo l'operazione eventuali pagamenti avviati ma di cui non è stato possibile verificare l'esito verranno perduti.\n" +
                    "Proseguire solamente se si è sicuri che i pagamenti collegati alla domanda hanno avuto un esito negativo,\n" +
                    "In caso contrario contattare l'ente prima di effettuare qualunque operazione\n\n" + 
                    "Vuoi continuare?";

                if (!confirm(message)) {
                    e.preventDefault();
                }
            });
        });
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <div class="inputForm">
        <fieldset>
            <div class="messaggio verifica-pagamenti">
                <div class="alert alert-warning ">
                    <div class="titolo">
                        Sono presenti operazioni in sospeso
                    </div>
                    <div class="corpo">
                        <%=MessaggioErrore %>
                    </div>
                </div>
                <div class="titolo">
                    Dettaglio delle operazioni in sospeso:
                </div>
                <div class="corpo">                    
                    
                    <asp:Repeater runat="server" ID="rptOperazioni">
                        <HeaderTemplate>
                            <table class="table">
                                <thead>
	                                <tr>
		                                <th>Causale</th>
		                                <th>Importo</th>
		                                <th>Numero Operazione</th>
		                                <th>Stato pagamento</th>
	                                </tr>
                                </thead>
                        </HeaderTemplate>
                        
                        <ItemTemplate>
                            <tr>
                                <td><asp:Literal runat="server" text='<%#Eval("Causale") %>' /></td>
                                <td>€ <asp:Literal runat="server" text='<%#Eval("Importo") %>' /></td>
                                <td><asp:Literal runat="server" text='<%#Eval("IdPosizioneNodoPagamenti") %>' /></td>
                                <td><asp:Literal runat="server" text='<%#Eval("StatoNativo") %>' /></td>
                            </tr>
                        </ItemTemplate>


                        <FooterTemplate>
                            </table>
                        </FooterTemplate>
                    </asp:Repeater>
                </div>

                <asp:Button CssClass="btn btn-danger" Visible="true" runat="server" Text="Annulla pagamenti in sospeso e prosegui compilazione" OnClick="AnnullaPagamentiInSospeso" ID="cmdProcedi" />
            </div>

        </fieldset>
    </div>
</asp:Content>
