<%@ Control Language="C#" AutoEventWireup="true" CodeBehind="stato-posizioni-ctrl.ascx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.stato_posizioni_ctrl" %>
<%@ Register TagPrefix="ar" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" %>

<style>
    .progress-panel {
        margin-top: var(--padding-default);
    }

        .progress-panel > small {
            display: block;
            margin-top: var(--padding-default);
            text-align: right;
        }

    .piede-pagina {
        margin-bottom: var(--padding-large);
    }
</style>
<script type="text/javascript">

    ready(() => {

        const indicatoreTentativi = document.querySelector('#indicatoreTentativi');
        const maxTentativi = 10;
        const intervalloTentativi = 3000;
        const modalAvviso = $('#<%=bmDownloadAvviso.ClientID%>');
        const modalDownloadNonRiuscito = $('#<%=bmGenerazioneNonRiuscita.ClientID%>');
        const urlAvvisoPagamento = '<%= UrlBaseAvvisoPagamento %>';

        function mostraModalDownloadNonRiuscito() {
            modalAvviso.modal('hide');
            setTimeout(() => { modalDownloadNonRiuscito.modal('show') }, 0);
        }

        async function downloadDocumento(uidOnere, conteggioTentativi) {

            const urlCompleto = `${urlAvvisoPagamento}&onere=${encodeURIComponent(uidOnere)}`;

            indicatoreTentativi.innerText = (conteggioTentativi + 1).toString() + ' di ' + maxTentativi;

            try {

                let response = await fetch(urlCompleto);

                if (response.headers.get("x-stato-avviso-pagamento") === "DISPONIBILE") {

                    let blob = await response.blob();
                    var fileUrl = window.URL.createObjectURL(blob);

                    const a = document.createElement("a");
                    a.style = "display:none";
                    a.target = "_blank";
                    a.href = fileUrl;
                    a.download = `avviso-di-pagamento-${uidOnere}.pdf`;

                    // document.body.append(a);
                    a.click();
                    // document.body.remove(a);
                    window.URL.revokeObjectURL(fileUrl);                    

                    modalAvviso.modal('hide');
                    return;
                }

            } catch (exception) {
                console.log(exception);

                mostraModalDownloadNonRiuscito();
                return;
            }

            if (conteggioTentativi < (maxTentativi - 1)) {
                setTimeout(() => downloadDocumento(uidOnere, ++conteggioTentativi), intervalloTentativi);
                return;
            }

            mostraModalDownloadNonRiuscito();
        }

        document.querySelectorAll('.download-avviso').forEach(lnkDownload => {
            lnkDownload.addEventListener('click', (e) => {
                e.preventDefault();

                const uidOnere = lnkDownload.dataset.uidOnere;

                downloadDocumento(uidOnere, 0);

                modalAvviso.modal('show');
            });
        });
    });

</script>



<asp:Repeater ID="rptDettagliPosizioni" runat="server">
    <HeaderTemplate>
        <table class="table">
            <thead>
                <tr>
                    <%--<th>C.F. ente creditore</th>--%>
                    <th>Causale</th>
                    <th>Stato</th>
                    <th>IUV</th>
                    <th>C.F. soggetto debitore</th>
                    <th>Codice avviso</th>
                    <th>Importo (€)</th>
                    <th>Scadenza</th>
                    <th>Avviso di pagamento</th>
                </tr>
            </thead>
            <tbody>
    </HeaderTemplate>

    <ItemTemplate>
        <tr>
            <%--                <td>
                    <%#Eval("CfEnteCreditore") %>
                </td>--%>
            <td>
                <%#Eval("Causale") %>
            </td>
            <td>
                <%#Eval("Stato") %>
            </td>
            <td>
                <%#Eval("IUV") %>
            </td>
            <td>
                <%#Eval("CfSoggettoDebitore") %>
            </td>
            <td>
                <%#Eval("CodiceAvviso") %>
            </td>
            <td style="text-align: right"><%#Eval("Importo", "{0:N2}") %></td>
            <td>
                <%#Eval("Scadenza") %>
            </td>
            <td style="text-align: right">
                <a href="#" class="download-avviso" data-uid-onere='<%#Eval("UniqueId") %>' runat="server" visible='<%#Eval("PermetteDownloadAvviso") %>'>Scarica</a>
                <span runat="server" visible='<%#Eval("OTF") %>'>Non disp.</span>
            </td>
        </tr>
    </ItemTemplate>


    <FooterTemplate>
        </tbody>
                            </table>
    </FooterTemplate>
</asp:Repeater>

<ar:BootstrapModal runat="server" ID="bmDownloadAvviso" Title="Generazione dell'avviso in corso" Fade="false" ShowFooter="false">
    <ModalBody>
        L'operazione potrebbe richiedere anche alcuni minuti, si prega di attendere senza effettuare altre operazioni...<br />

        <div class="progress-panel">
            <div class="progress progress-striped active" style="margin-bottom: 0;">
                <div class="progress-bar" style="width: 100%"></div>
            </div>
            <small>Tentativo <span id="indicatoreTentativi"></span></small>
        </div>
    </ModalBody>
</ar:BootstrapModal>

<ar:BootstrapModal runat="server" ID="bmGenerazioneNonRiuscita" Title="Generazione dell'avviso in corso" Fade="false" KoText="Chiudi" ShowOkButton="false">
    <ModalBody>
        Non è stato possibile recuperare l'avviso di pagamento nel tempo stabilito, si prega di riprovare in un secondo momento
    </ModalBody>
</ar:BootstrapModal>
