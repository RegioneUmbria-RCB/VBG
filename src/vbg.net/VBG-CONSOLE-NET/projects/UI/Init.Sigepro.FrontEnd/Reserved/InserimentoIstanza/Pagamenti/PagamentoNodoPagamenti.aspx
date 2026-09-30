<%@ Page Title="" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
    AutoEventWireup="true" CodeBehind="PagamentoNodoPagamenti.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.PagamentoNodoPagamenti" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" %>

<%@ Register TagPrefix="cc1" Namespace="Init.Utils.Web.UI" Assembly="Init.Utils" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <style>
        .badge {
            min-width: 26px;
        }

        .badge.badge-danger {
            background-color: #a94442 !important;
        }

        .badge.badge-success {
            background-color:#007958 !important;
        }

        .badge.badge-in-progress {
            background-color:#31708f !important;
        }
    </style>
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
                    <ar:RisorsaTestualeLabel runat="server" IdRisorsa="PagamentoNodoPagamenti_MessaggioPagamentoFallito">
                        Il pagamento è fallito oppure è stato annullato dall'utente verifica lo stato del pagamento.<br />
                        Fai click su <b>"Ritenta il pagamento"</b> per ripetere la procedura di pagamento.
                    </ar:RisorsaTestualeLabel>

                </div>
            </div>


            <asp:Button runat="server" ID="cmdTornaIndietro" Text="Torna indietro" OnClick="cmdTornaIndietro_Click" CssClass="btn btn-default" />
            <asp:Button runat="server" ID="cmdRitentaPagamento" Text="Ritenta il pagamento" OnClick="cmdRitentaPagamento_Click" CssClass="btn btn-primary" />

        </asp:View>

        <asp:View runat="server" ID="pagamentoRiuscito">

            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <b class="glyphicon glyphicon-ok"></b>
                        Pagamento completato con successo</h3>
                </div>
                <div class="panel-body">
                    <ar:RisorsaTestualeLabel runat="server" IdRisorsa="PagamentoNodoPagamenti_MessaggioPagamentoRiuscito">
                        A breve riceverai una mail contenente i dettagli del pagamento.<br />
                        L’attestazione di pagamento sarà acquisita dal sistema e non sarà necessario inviare ulteriori informazioni sul pagamento effettuato.<br />
                        Fai click su <b>"Avanti"</b> per proseguire la presentazione della domanda.
                    </ar:RisorsaTestualeLabel>
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

                        const maxRetries = 20;   // Numero di retries per verificare l'esito del pagamento
                        const retryRate = 3000; // Tempo tra un retry e il successivo

                        $.ajax({
                            type: "GET",
                            url: 'VerificaStatoRicevutaNodoPagamenti.ashx?idcomune=<%=base.IdComune%>&software=<%=base.Software%>&idPresentazione=<%=base.IdDomanda%>&idTransaction=<%=this.IdTransaction%>&idx=' + idx,
                            dataType: 'html',
                            success: (stato) => {
                                if (stato == "ANNULLATO" || stato == "PAGATO") {
                                    document.location.reload();
                                    return;
                                }

                                if (idx < maxRetries) {
                                    setTimeout(callback(++idx), retryRate);
                                    return;
                                }

                                const el = document.getElementById("testoAttenderePrego");
                                el.innerHTML = document.getElementById('messaggio-timeout').innerHTML;

                                const contenitoreBottoni = document.createElement('div');
                                contenitoreBottoni.style.textAlign = 'right';
                                el.appendChild(contenitoreBottoni);

                                const btnContinua = document.createElement('div');
                                btnContinua.className = 'btn btn-primary';
                                btnContinua.innerText = 'Continua ad attendere';
                                btnContinua.style.marginTop = '12px';
                                btnContinua.addEventListener('click', () => {
                                    document.location.reload();
                                });

                                const btnChiudi = document.createElement('div');
                                btnChiudi.className = 'btn btn-default';
                                btnChiudi.innerText = 'Torna alla pagina principale';
                                btnChiudi.style.marginTop = '12px';
                                btnChiudi.style.marginRight = '6px';
                                btnChiudi.addEventListener('click', () => {
                                    document.location.replace('<%=UrlPaginaIniziale%>');
                                });


                                contenitoreBottoni.appendChild(btnChiudi);
                                contenitoreBottoni.appendChild(btnContinua);
                                
                                

                                document.getElementById("spinnerAttenderePrego").style.display = 'none';
                                // var esitoFinale = "NON_CONFERMATO";
                                // location.replace('PagamentoNodoPagamenti.aspx?idcomune=<%=base.IdComune%>&software=<%=base.Software%>&idPresentazione=<%=base.IdDomanda%>&stepid=<%=Request.QueryString["stepid"]%>&idTransaction=<%=IdTransaction%>&esito=' + esitoFinale);

                            },
                            error: function (errore) {
                                document.location.reload();
                            }
                        });
                    }
                }

                setTimeout(callback(0), 5000);

            </script>

            <template id="messaggio-timeout">
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="pagamento-nodo-pagamenti.modal.timeout-risposta">
                    <p>
                    Siamo in attesa di ricevere le informazioni di avvenuto pagamento dal sistema PagoPA. 
                    Possono passare diversi minuti prima che la ricevuta sia resa disponibile al nostro sistema.
                    </p>
                    <p>
                        Facendo click su <b>"Continua ad attendere"</b> riproveremo a contattare PagoPA per richiedere la ricevuta
                    </p>

                    <p>
                        Facendo click su <b>"Torna alla pagina principale"</b> potrai tornare alla pagina iniziale per usare ulteriori servizi.
                        Sarà comunque possibile riprendere la domanda in un secondo momento per verificare lo stato del pagamento 
                        e, in caso di esito positivo, portare a termine la compilazione.
                    </p>

                </ar:RisorsaTestualeLabel>
            </template>

            <ar:BootstrapModal runat="server" ID="bmAttesaRicevuta" Title="Attesa ricevuta" ShowOkButton="false" ShowFooter="false">
                <ModalBody>
                    <div id="testoAttenderePrego">
                        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="pagamento-nodo-pagamenti.modal.messaggio-attendere">
                            <p>
                                Siamo in attesa di un esito dal sistema di pagamenti, l'operazione potrebbe richiedere anche alcuni minuti.
                            </p>
                            <p>
                                Attendere senza effettuare altre operazioni.
                            </p>
                        </ar:RisorsaTestualeLabel>
                    </div>
                    <div id="spinnerAttenderePrego" class="progress progress-striped active" style="margin-bottom: 0;">
                        <div class="progress-bar" style="width: 100%"></div>
                    </div>
                </ModalBody>
            </ar:BootstrapModal>
        </asp:View>

    </asp:MultiView>

    <asp:Panel runat="server" ID="pnlDettaglioPagamenti">
        <h2>Operazioni di pagamento</h2>
        <asp:Repeater ID="rptDettagliPosizioni" runat="server">
            <HeaderTemplate>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Causale</th>
                            <th>IUV</th>
                            <th>Importo (€)</th>
                        </tr>
                    </thead>
                    <tbody>
            </HeaderTemplate>

            <ItemTemplate>
                <tr title='<%#Eval("StatoNativo") %> (<%#Eval("Stato") %>)'>
                    <td>
                        <div class="badge badge-danger" style='display: <%# Eval("Stato").ToString() == "PagamentoFallito" ? "inline-block" : "none" %>'>
                            !
                        </div>

                        <div class="badge badge-success" style='display: <%# Eval("Stato").ToString() == "PagamentoRiuscito" ? "inline-block" : "none" %>'>
                            <i class="glyphicon glyphicon-ok"></i>
                        </div>

                        <div class="badge badge-in-progress" style='display: <%# Eval("Stato").ToString() == "PagamentoIniziato" ? "inline-block" : "none" %>'>
                            <i class="glyphicon glyphicon-time"></i>
                        </div>
                        
                        <%#Eval("IdPosizioneNodoPagamenti") %> - <%#Eval("Causale") %></td>
                    <td>
                        <%#Eval("IUV") %>
                    </td>

                    <td style="text-align: right"><%#Eval("Importo") %></td>
                </tr>
            </ItemTemplate>


            <FooterTemplate>
                </tbody>
                            </table>
            </FooterTemplate>
        </asp:Repeater>
    </asp:Panel>



</asp:Content>
