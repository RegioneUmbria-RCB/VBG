<%@ Page Title="" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="PagamentoEntraNext.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.PagamentoEntraNext" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server"></asp:Content>
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
                    Attenzione il pagamento non è andato a buone fine. Si prega di ritentare la procedura.<br />
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

                var OPERATION_TIMEOUT = 3000;
                var MAX_RETRY = 100;

                function callback(idx) {

                    return function () {
                        $.ajax({
                            type: "GET",
                            url: 'VerificaStatoRicevutaEntraNext.ashx?idcomune=<%=base.IdComune%>&software=<%=base.Software%>&idPresentazione=<%=base.IdDomanda%>&idTransaction=<%=this.IdTransaction%>&idx=' + idx,
                            dataType: 'html',
                            success: function (esito) {
                                if (esito != 'OK' && idx < MAX_RETRY) {
                                    var indice = idx + 1;
                                    setTimeout(callback(indice), OPERATION_TIMEOUT);
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
                setTimeout(callback(0), OPERATION_TIMEOUT);

            </script>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <div class="loader" style="display: inline-block;"></div>
                        In attesa di una ricevuta dal sistema di pagamento</h3>
                </div>
                <div class="panel-body">
                    <div>
                        E' necessario attendere qualche minuto affinché il sistema dei pagamenti PagoPA generi e notifichi ai nostri sistemi la ricevuta telematica di pagamento.<br />
                        Se si esce da questa pagina sarà necessario rientrare nella propria area personale, riprendere la propria pratica in sospeso e terminare correttamente l'inoltro.<br />
                        <br />
                        <u>Se nell'arco di 30 minuti la pratica non dovesse sbloccarsi, si prega di contattare l'assistenza al seguente indirizzo: </u>
                        <a href="mailto:assistenza.servizionline@comune.modena.it">assistenza.servizionline@comune.modena.it</a>
                    </div>
                    <div class="progress progress-striped active" style="margin-bottom: 0;">
                        <div class="progress-bar" style="width: 100%"></div>
                    </div>
                </div>
            </div>

        </asp:View>
    </asp:MultiView>
</asp:Content>
