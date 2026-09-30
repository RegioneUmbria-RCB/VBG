using VBG.Shared.Infrastructure.ServiceModel;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using log4net;
using System;
using VBG.Pagamenti.Legacy.EntraNextService;

namespace VBG.Pagamenti.Legacy.ENTRANEXT
{
    public class EntraNextPaymentService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(EntraNextPaymentService));
        public readonly PaymentSettingsEntraNext Settings;
        private readonly IBindingFactory _bindingFactory;
        private readonly IResolveUrl _resolveUrl;

        public EntraNextPaymentService(IPagamentiEntraNextSettingsReader settings, IBindingFactory bindingFactory, IResolveUrl resolveUrl)
        {
            this.Settings = settings.GetSettings();
            this._bindingFactory = bindingFactory;
            this._resolveUrl = resolveUrl;
        }

        public InserisciPosizioniInAttesaResponse IniziaPagamento(IniziaPagamentoEntraNextRequest iniziaPagamentoRequest)
        {
            try
            {
                var client = new PayServerClientWrapperEntraNext(this.Settings, this._bindingFactory);
                var adapter = new PaymentRequestAdapterEntraNext(this.Settings, iniziaPagamentoRequest, this._resolveUrl);
                var request = adapter.Adatta();
                return client.GeneraUrlRedirect(request);
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante l'inizializzazione del pagamento {ex.ToString()}");
                throw;
            }
        }

        public RiceviEsitoTransazioneResponse GetEsitoTransazione(string identificativoTransazione)
        {
            try
            {
                var client = new PayServerClientWrapperEntraNext(this.Settings, this._bindingFactory);

                var request = new RiceviEsitoTransazioneRequest { IdentificativoTransazione = identificativoTransazione };
                var response = client.GetEsitoTransazione(request);

                return response;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la richiesta dell'esito della transazione con identificativo {identificativoTransazione}, {ex.ToString()}");
                throw;
            }
        }

        public ScaricaPagamentiRTPosizioniDebitorieResponse GetRicevutaPagamento(string identificativoTransazione)
        {
            try
            {
                var client = new PayServerClientWrapperEntraNext(this.Settings, this._bindingFactory);

                var request = new ScaricaPagamentiRTPosizioniDebitorieRequest { TipoChiaveApplicativa = TipoChiaveApplicativa.IUV, ChiaveApplicativa = identificativoTransazione };
                var response = client.GetRicevutaPagamento(request);

                return response;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la richiesta per il download della ricevuta con identificativo {identificativoTransazione}, {ex.ToString()}");
                throw;
            }
        }

        public VerificaPosizioneResponse VerificaPosizione(string posizione)
        {
            try
            {
                var client = new PayServerClientWrapperEntraNext(this.Settings, this._bindingFactory);

                var request = new VerificaPosizioneRequest { TipoChiaveApplicativa = TipoChiaveApplicativa.Gestionale, ChiaveApplicativa = posizione };
                var response = client.VerificaPosizione(request);

                return response;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la verifica della posizione con numero posizione (riferimento pratica) {posizione}, {ex.ToString()}");
                throw;
            }
        }
    }
}
