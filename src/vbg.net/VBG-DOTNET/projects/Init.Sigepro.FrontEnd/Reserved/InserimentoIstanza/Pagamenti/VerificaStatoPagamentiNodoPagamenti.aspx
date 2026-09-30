<%@ Page Title="Verifica stato dei pagamenti" Language="C#" MasterPageFile="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master"
    AutoEventWireup="true" CodeBehind="VerificaStatoPagamentiNodoPagamenti.aspx.cs" Inherits="Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti.VerificaStatoPagamentiNodoPagamenti" %>
<%@ MasterType VirtualPath="~/Reserved/InserimentoIstanza/InserimentoIstanzaMaster.Master" %>
<%@ Register Src="~/Reserved/InserimentoIstanza/Pagamenti/stato-posizioni-ctrl.ascx" TagPrefix="uc1" TagName="statoposizionictrl" %>
<%@ Register TagPrefix="ar" Assembly="Init.Sigepro.FrontEnd.WebControls" Namespace="Init.Sigepro.FrontEnd.WebControls.FormControls" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
    <script type="text/javascript">
        ready(() => {
            const cmdProcedi = document.querySelector('#<%=cmdProcedi.ClientID%>');

            if (!cmdProcedi) {
                return;
            }

            cmdProcedi.addEventListener('click', (e) => {
                e.preventDefault();

                $('#<%=bmConfermaAnnullamentoPagamento.ClientID%>').modal('show');
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
                    <uc1:statoposizionictrl runat="server" ID="statoPosizioniCtrl" />
                </div>

                <div>
                    <asp:Button runat="server" CssClass="btn btn-primary" Text="Verifica lo stato dei pagamenti" OnClientClick="javascript:document.location.reload(); return false;" ID="cmdVerificaPagamento" />
                    <asp:Button runat="server" CssClass="btn btn-danger" Text="Prosegui compilazione" ID="cmdProcedi" />
                </div>
            </div>



        </fieldset>
    </div>

    <ar:BootstrapModal runat="server" ID="bmConfermaAnnullamentoPagamento" OkCssClass="vbg-show-spinner" OnOkClicked="bmConfermaAnnullamentoPagamento_OkClicked" Title="Conferma annullamento posizioni debitorie">
        <ModalBody>
            <ar:RisorsaTestualeLabel runat="server" IdRisorsa="verifica-pagamento.conferma-eliminazione">
                Proseguendo tutte le operazioni di pagamento in sospeso verranno annullate e sarà necessario effettuare un nuovo pagamento per proseguire con la compilazione e l'invio della domanda.<br />
                <br />
                Se si è già provveduto al pagamento si consiglia di non proseguire e di attendere la ricezione dell'esito del pagamento a meno che l'ente non abbia fornito indicazioni diverse.<br />
                <br />
                L'operazione non potrà essere annullata.
            </ar:RisorsaTestualeLabel>
        </ModalBody>
    </ar:BootstrapModal>
</asp:Content>
