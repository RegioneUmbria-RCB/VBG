<%@ Page Title="" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="PagamentoEntraNext.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.PagamentoEntraNext" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" %>

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
                    Il pagamento è fallito verifica lo stato del pagamento.<br />
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
        <asp:View runat="server" ID="pagamentoInAttesa">
            <script type="text/javascript">

                $(function () {
                    $('#<%=bmAttesaRicevuta.ClientID%>').modal("show");
                });

                function callback(idx) {
                    return function () {
                        $.ajax({
                            type: "GET",
                            url: 'VerificaStatoRicevutaEntraNext.ashx?idcomune=<%=base.IdComune%>&software=<%=base.Software%>&idPresentazione=<%=base.IdDomanda%>&idTransaction=<%=this.IdTransaction%>&idx=' + idx,
                            dataType: 'html',
                            success: function (esito) {
                                if (esito != 'OK' && idx < 13) {
                                    var indice = idx + 1;
                                    setTimeout(callback(indice), 10000);
                                }
                                else {
                                    var esitoFinale = esito == "DIFFERITO" ? "TIMEOUT" : "OK";
                                    location.replace('PagamentoEntraNext.aspx?idcomune=<%=base.IdComune%>&software=<%=base.Software%>&idPresentazione=<%=base.IdDomanda%>&stepid=<%=Request.QueryString["stepid"]%>&idTransaction=<%=IdTransaction%>&esito=' + esitoFinale);
                                }
                            },
                            error: function (errore) {
                                location.replace('PagamentoEntraNext.aspx?idcomune=<%=base.IdComune%>&software=<%=base.Software%>&idPresentazione=<%=base.IdDomanda%>&stepid=<%=Request.QueryString["stepid"]%>&idTransaction=<%=IdTransaction%>&esito=ERROR');
                            },
                            async: true
                        });
                    }
                }

                setTimeout(callback(0), 5000);

            </script>

            <ar:BootstrapModal runat="server" ID="bmAttesaRicevuta" Title="Attesa ricevuta" ShowOkButton="false" ShowFooter="false">
                <ModalBody>
                    <div>Attendere la notifica  della ricevuta</div>
                    <div class="progress progress-striped active" style="margin-bottom: 0;">
                        <div class="progress-bar" style="width: 100%"></div>
                    </div>
                </ModalBody>
            </ar:BootstrapModal>
        </asp:View>
    </asp:MultiView>
</asp:Content>
