<%@ Page Title="" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="PagamentoMIP.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.PagamentoMIP" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server" ID="nuovoPagamento">
            <%if (!ErrorePagamento){%>
            <script type="text/javascript">

                require(['jquery'], function ($) {

                    $(function () {
                        var url = '<%=UrlPagamenti %>';

                        $('#paga').on('click', function (e) {
                            document.location.replace(url);

                            e.preventDefault();
                        })

                        document.location.replace(url);
                    });

                });

            </script>


            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <div class="loader" style="display: inline-block;"></div>
                        Trasferimento al sistema di pagamento</h3>
                </div>
                <div class="panel-body">
                    In pochi secondi verrai automaticamente trasferito al sistema di pagamento.<br />
                    Fai click su <a href="#" id="paga">questo link</a> se non vuoi attendere
                </div>
            </div>
            <%}%>
        </asp:View>

        <asp:View runat="server" ID="pagamentoFallito">

            <div class="panel panel-danger">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <b class="glyphicon glyphicon-exclamation-sign"></b>
                        Pagamento fallito</h3>
                </div>
                <div class="panel-body">
                    Il pagamento è fallito e nessuna somma verrà prelevata.<br />
                    Fai click su <b>"Indietro"</b> per tornare allo step precedente.
                </div>
            </div>

        </asp:View>

        <asp:View runat="server" ID="pagamentoRiuscito">

            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <b class="glyphicon glyphicon-ok"></b>
                        Pagamento completato con successo</h3>
                </div>
                <div class="panel-body">
                    A breve riceverai una mail contenente i dettagli del pagamento.<br />
                    Fai click su <b>"Avanti"</b> per proseguire con la presentazione della domanda.
                </div>
            </div>

        </asp:View>

        <asp:View runat="server" ID="pagamentoAnnullato">

            <div class="panel panel-warning">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <b class="glyphicon glyphicon-exclamation-sign"></b>
                        Pagamento annullato</h3>
                </div>
                <div class="panel-body">
                    Il pagamento è stato annullato dall'utente e nessuna somma verrà prelevata.<br />
                    Fai click su <b>"Indietro"</b> per tornare allo step precedente.
                </div>
            </div>
        </asp:View>
    </asp:MultiView>
</asp:Content>
