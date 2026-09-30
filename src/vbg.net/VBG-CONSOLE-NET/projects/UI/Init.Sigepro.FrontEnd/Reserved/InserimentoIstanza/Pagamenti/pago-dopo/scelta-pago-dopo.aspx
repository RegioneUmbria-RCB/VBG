<%@ Page Title="Scegli come pagare" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" AutoEventWireup="true" CodeBehind="scelta-pago-dopo.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.pago_dopo.scelta_pago_dopo" %>

<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register TagPrefix="ar" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <style>
        .opzioni-pagamento {
            display: flex;
            margin-top: var(--padding-large);
        }



            .opzioni-pagamento > a:first-child {
                margin-right: var(--padding-default);
            }


            .opzioni-pagamento > a:last-child {
                margin-left: var(--padding-default);
            }

            .opzioni-pagamento > a {
                background-color: var(--well-background-color);
                padding: var(--padding-default);
                color: var(--font-color) !important;
                text-decoration: none;
                box-shadow: 0 0 0 rgba(0, 0, 0, 0.2);
                transition: box-shadow 0.3s;
                width: 50%;
            }

                .opzioni-pagamento > a:hover {
                    box-shadow: 0 6px 6px rgba(0, 0, 0, 0.5);
                }

                .opzioni-pagamento > a .icona {
                    text-align: center;
                    /*font-size: 11em;
                line-height: 0;*/
                    padding: var(--padding-small) 0 var(--padding-small) 0;
                }

            .opzioni-pagamento h3 {
                margin: 0;
                padding: 0;
                text-align: center;
                /*color: var(--font-accent-color) !important;*/
                font-size: 1.8em;
                text-transform: uppercase;
            }

                .opzioni-pagamento h3 > small {
                    font-size: var(--default-font-size);
                    text-transform: none;
                }
    </style>
    <script type="text/javascript">
        ready(() => {
            document.querySelector('#cmd-paga-dopo').addEventListener('click', (e) => {
                e.preventDefault();

                $('#<%=bmConfermaCreazionePosizioneDebitoria.ClientID%>').modal('show');
            });

            document.querySelector('#<%=bmConfermaCreazionePosizioneDebitoria.ClientID%> .modal-ok-button').addEventListener('click', (e) => {

                $('#<%=bmConfermaCreazionePosizioneDebitoria.ClientID%>').modal('hide');
            });

        });
    </script>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="stepContent" runat="server">
    <div class="descrizione-generale">
        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="step-paga-dopo.descrizione-step">
            &nbsp;
        </ar:RisorsaTestualeLabel>
    </div>
    <div class="opzioni-pagamento">
        <a href="#" id="cmd-paga-dopo">
            <div class="opzione paga-dopo">
                <h3>
                    <ar:RisorsaTestualeLabel runat="server" IdRisorsa="step-paga-dopo.paga-dopo-titolo">
                        Paga dopo
                    </ar:RisorsaTestualeLabel>
                    <small>
                        <ar:RisorsaTestualeLabel runat="server" IdRisorsa="step-paga-dopo.paga-dopo-sottotitolo">
                            Paga sul territorio
                        </ar:RisorsaTestualeLabel>
                    </small>
                </h3>
                <div class="icona" aria-hidden="true">
                    <img src="./images/creditcard.png" alt="Paga con carta di credito" />
                </div>
                <div class="descrizione-modalita">
                    <ar:RisorsaTestualeLabel runat="server" IdRisorsa="step-paga-dopo.paga-dopo-descrizione">
                        Presso banche, ricevitorie, tabaccai, bancomat o supermercati. 
                        Potrai pagare in contanti, con carte di credito o conto corrente.
                    </ar:RisorsaTestualeLabel>
                </div>
            </div>
        </a>

        <asp:LinkButton runat="server" ID="lnkPagaOnline" OnClick="lnkPagaOnline_Click" CssClass="vbg-show-spinner">
        <div class="opzione paga-online">
            <h3>
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="step-paga-dopo.paga-online-titolo">
                    Paga online
                </ar:RisorsaTestualeLabel>
                <small>
                    <ar:RisorsaTestualeLabel runat="server" IdRisorsa="step-paga-dopo.paga-online-sottotitolo">
                        Paga sul sito o tramite app
                    </ar:RisorsaTestualeLabel>
                </small>
            </h3>
            <div class="icona" aria-hidden="true">
                <img src="./images/euro.png" alt="Paga online" />
            </div>
            <div class="descrizione-modalita">
                <ar:RisorsaTestualeLabel runat="server" IdRisorsa="step-paga-dopo.paga-online-descrizione">
                    Paga ora sul sito o con le app del tuo Ente Creditore, della tua banca o degli altri canali di pagamento.
                    Potrai pagare con carte, conto corrente, CBILL.
                </ar:RisorsaTestualeLabel>
            </div>
        </div>
        </asp:LinkButton>
    </div>

    <ar:BootstrapModal runat="server" ID="bmConfermaCreazionePosizioneDebitoria" Title="Stai per aprire una nuova posizione debitoria"
        OkText="Apri posizione debitoria" KoText="Annulla" OnOkClicked="bmConfermaCreazionePosizioneDebitoria_OkClicked"
        OkCssClass="vbg-show-spinner">
        <ModalBody>
            <ar:RisorsaTestualeLabel runat="server">
                <p>Proseguendo verrà aperta una nuova posizione debitoria e verranno generati uno o più avvisi di pagamento. Per procedere al pagamento dovrai stampare l'avviso e recarti presso uno degli esercenti convenzionati come banche, ricevitorie, tabaccai, bancomat o supermercati</p>
                <p>La presentazione della domanda sarà sospesa fino a quando le posizioni non saranno saldate. Una volta effettuato il pagamento potrai riprendere la compilazione della domanda tramite la sezione <b>"Istanze in sospeso"</b>.</p>
                <p>La posizione debitoria potrà essere annullata riprendendo la compilazione tramite la sezione <b>"Istanze in sospeso"</b> tornando nello step di verifica pagamento oneri.</p>

                <div class="alert alert-info">
                    <b>
                        Gli avvisi saranno validi per trenta giorni dalla data di elaborazione, come indicato nel testo dell’avviso. 
                        Una volta scaduti senza che sia stato effettuato il pagamento bisognerà rigenerare le posizioni debitorie.
                    </b>
                </div>
            </ar:RisorsaTestualeLabel>
        </ModalBody>
    </ar:BootstrapModal>
</asp:Content>
