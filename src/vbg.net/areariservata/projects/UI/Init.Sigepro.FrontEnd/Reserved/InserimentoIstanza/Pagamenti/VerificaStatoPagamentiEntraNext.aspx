<%@ Page Title="Verifica stato dei pagamenti" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="VerificaStatoPagamentiEntraNext.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.VerificaStatoPagamentiEntraNext" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
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
                                <td><asp:Literal runat="server" text='<%#Eval("NumeroOperazione") %>' /></td>
                                <td><asp:Literal runat="server" text='<%#Eval("Stato") %>' /></td>
                            </tr>
                        </ItemTemplate>


                        <FooterTemplate>
                            </table>
                        </FooterTemplate>
                    </asp:Repeater>
                </div>

                <div class="bottoni">
                    <asp:Button runat="server" Text="Prosegui compilazione" OnClick="AnnullaPagamentiInSospeso" ID="cmdProcedi" />
                </div>
            </div>

        </fieldset>
    </div>
</asp:Content>
