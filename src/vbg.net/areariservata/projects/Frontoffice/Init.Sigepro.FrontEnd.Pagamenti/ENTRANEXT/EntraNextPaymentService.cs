using Init.Sigepro.FrontEnd.Pagamenti.EntraNextService;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT
{
    public class EntraNextPaymentService
    {
        ILog _log = LogManager.GetLogger(typeof(EntraNextPaymentService));
        public readonly PaymentSettingsEntraNext Settings;

        public EntraNextPaymentService(IPagamentiEntraNextSettingsReader settings)
        {
            this.Settings = settings.GetSettings();
        }

        public InserisciPosizioniInAttesaResponse IniziaPagamento(IniziaPagamentoEntraNextRequest iniziaPagamentoRequest)
        {
            try
            {
                var client = new PayServerClientWrapperEntraNext(this.Settings);
                var adapter = new PaymentRequestAdapterEntraNext(this.Settings, iniziaPagamentoRequest);
                var request = adapter.Adatta();
                return client.GeneraUrlRedirect(request);
            }
            catch (Exception ex)
            {
                _log.Error($"Errore durante l'inizializzazione del pagamento {ex.ToString()}");
                throw;
            }
        }

        public RiceviEsitoTransazioneResponse GetEsitoTransazione(string identificativoTransazione)
        {
            try
            {
                var client = new PayServerClientWrapperEntraNext(this.Settings);

                var request = new RiceviEsitoTransazioneRequest { IdentificativoTransazione = identificativoTransazione };
                var response = client.GetEsitoTransazione(request);

                return response;
            }
            catch (Exception ex)
            {
                _log.Error($"Errore durante la richiesta dell'esito della transazione con identificativo {identificativoTransazione}, {ex.ToString()}");
                throw;
            }
        }

        public ScaricaPagamentiRTPosizioniDebitorieResponse GetRicevutaPagamento(string identificativoTransazione)
        {
            try
            {
                var client = new PayServerClientWrapperEntraNext(this.Settings);

                var request = new ScaricaPagamentiRTPosizioniDebitorieRequest { TipoChiaveApplicativa = TipoChiaveApplicativa.IUV, ChiaveApplicativa = identificativoTransazione };
                var response = client.GetRicevutaPagamento(request);

                return response;
            }
            catch (Exception ex)
            {
                _log.Error($"Errore durante la richiesta per il download della ricevuta con identificativo {identificativoTransazione}, {ex.ToString()}");
                throw;
            }
        }

        public VerificaPosizioneResponse VerificaPosizione(string posizione)
        {
            try
            {
                var client = new PayServerClientWrapperEntraNext(this.Settings);

                var request = new VerificaPosizioneRequest { TipoChiaveApplicativa = TipoChiaveApplicativa.Gestionale, ChiaveApplicativa = posizione };
                var response = client.VerificaPosizione(request);

                return response;
            }
            catch (Exception ex)
            {
                _log.Error($"Errore durante la verifica della posizione con numero posizione (riferimento pratica) {posizione}, {ex.ToString()}");
                throw;
            }
        }
    }
}
