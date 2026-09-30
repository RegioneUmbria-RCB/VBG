using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Configurazione
{
    public class ConfigurazioneNodoPagamentiRepository : IConfigurazioneNodoPagamentiRepository
    {
        private readonly IConfigurazione<ParametriSigeproSecurity> _config;
        private readonly ITokenApplicazioneService _tokenApplicazioneService;
        private readonly ILog _log = LogManager.GetLogger(typeof(ConfigurazioneNodoPagamentiRepository));

        public ConfigurazioneNodoPagamentiRepository(IConfigurazione<ParametriSigeproSecurity> config, ITokenApplicazioneService tokenApplicazioneService)
        {
            this._config = config;
            this._tokenApplicazioneService = tokenApplicazioneService;
        }

        public ConfigurazioneNodoPagamenti GetConfigurazione(string software, string codiceComune)
        {
            return CallService(ws => {

                this._log.Debug($"Chiamata a GetConfigurazione del nodo pagamenti con software={software} e codiceComune={codiceComune}");

                var result = ws.Service.GetConfigurazione(ws.Token, software, codiceComune);

                if (this._log.IsDebugEnabled)
                {
                    this._log.Debug($"Esito della chiamata {result.ToXmlString()}");
                }

                return new ConfigurazioneNodoPagamenti(result.NodoPagamentiAttivo, result.PagoDopoAttivo, result.UrlWs, result.CodiceFiscaleEnteCreditore, 
                    result.UrlRitorno, result.IdModalitaPagamento, result.SoggettoPendenza,
                    result.PagoDopoGGScadenza);
            });
        }

        private T CallService<T>(Func<ServiceInstance<WsNodoPagamenti.WsNodoPagamentiServiceClient>, T> callback)
        {
            using (var ws = CreateClient())
            {
                try
                {
                    return callback(ws);
                }
                catch (Exception ex)
                {
                    this._log.Error($"Errore durante la chiamata al servizio del nodo di pagamenti: {ex}");

                    ws.Service.Abort();

                    throw;
                }
            }
        }

        private ServiceInstance<WsNodoPagamenti.WsNodoPagamentiServiceClient> CreateClient()
        {
            var serviceUrl = this._config.Parametri.UrlWsNodoPagamenti;
            var bindingName = "defaultServiceBinding";
            var token = this._tokenApplicazioneService.GetToken();

            this._log.Debug($"Inizializzazione del web service di gestione comuni all'endpoint {serviceUrl} utilizzando il binding {bindingName}");

            var endPoint = new EndpointAddress(serviceUrl);
            var binding = new BasicHttpBinding(bindingName);
            var ws = new WsNodoPagamenti.WsNodoPagamentiServiceClient(binding, endPoint);

            return new ServiceInstance<WsNodoPagamenti.WsNodoPagamentiServiceClient>(ws, token);
        }
    }
}
