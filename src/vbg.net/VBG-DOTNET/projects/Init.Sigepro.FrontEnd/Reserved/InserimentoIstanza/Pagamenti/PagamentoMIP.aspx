<%@ Page Title="" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="PagamentoMIP.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.PagamentoMIP" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <asp:MultiView runat="server" ID="multiView" ActiveViewIndex="0">
        <asp:View runat="server" ID="nuovoPagamento">
            <%if (!ErrorePagamento)
                {%>
            <script type="text/javascript">

                $(function () {
                    var url = '<%=UrlPagamenti %>';

                    $('#paga').on('click', function (e) {
                        document.location.replace(url);

                        e.preventDefault();
                    })

                    document.location.replace(url);
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
                    <asp:Literal runat="server" ID="ltrTestoPagamentoFallito" />
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
                    <%=TestoPagamentoRiuscito %>
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
                    <%=TestoPagamentoAnnullato %>
                </div>
            </div>
        </asp:View>

        <asp:View runat="server" ID="pagamentoInCorso">

            <script type="text/javascript">

                $(function () {
                    setTimeout(() => {
                        document.location.replace('<%=UrlReload%>');
                    }, 10000)
                });
            </script>


            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <div class="loader" style="display: inline-block;"></div>
                        Verifica in corso
                    </h3>
                </div>
                <div class="panel-body">
                    Verifica dello stato del pagamento in corso. Si prega di attendere senza effettuare ulteriori operazioni...<br />
                </div>
            </div>
        </asp:View>

    </asp:MultiView>
</asp:Content>
