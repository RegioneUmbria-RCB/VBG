using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.NlaPecService;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.NlaPec
{
    public class NlaPecServiceWrapper
    {
        private static class Constants
        {
            public const string NlaPecBindingName = "NlaPecBinding";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(NlaPecServiceWrapper));

        private NlaGestioneMailClient CreaWebService()
        {
            this._log.DebugFormat("Creazione del web service NLAPec, binding {0}, url {1}", Constants.NlaPecBindingName, ParametriConfigurazione.Get.WsHostUrlNlaPec);
            var bindingFactory = StaticKernelContainer.GetService<IBindingFactory>();

            var binding = bindingFactory.CreateAndConfigure(Constants.NlaPecBindingName);
            var endpoint = new EndpointAddress(ParametriConfigurazione.Get.WsHostUrlNlaPec);

            var ws = new NlaGestioneMailClient(binding, endpoint);

            this._log.Debug("Creazione del web service NLAPec, avvenuto correttamente");

            return ws;
        }

        public IEnumerable<MessaggioType> FindPec(string token, string software, FiltroType[] filtriType)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var request = new ListaMessaggiRequest
                    {
                        token = token,
                        software = software,
                        listaFiltri = filtriType
                    };

                    var filtri = "";
                    if (request.listaFiltri.Length > 0)
                        filtri = String.Join(",", request.listaFiltri.Select(x => String.Format("{0}={1}", x.tipo, x.valore)).ToArray());

                    this._log.InfoFormat("CHIAMATA A LISTAMESSAGGI WS NLAPEC, filtri: {0}", filtri);

                    var response = ws.ListaMessaggi(request);
                    this._log.InfoFormat("CHIAMATA A A LISTAMESSAGGI WS NLAPEC AVVENUTA CON SUCCESSO, NR PEC TROVATE: {0}", response.Length);

                    return response;
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("ERRORE GENERATO DURANTE LA RICHIESTA A ListaMessaggi DEL WS NLAPEC, ERRORE: {0}", ex.Message);
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA RICHIESTA A ListaMessaggi DEL WS NLAPEC, ERRORE: {0}", ex.Message), ex);
            }
        }

        public ScaricaMessaggioBinarioResponse DownloadBinaryEml(string token, string software, string identificativo)
        {
            try
            {
                using (var ws = this.CreaWebService())
                {
                    var request = new ScaricaMessaggioBinarioRequest
                    {
                        token = token,
                        software = software,
                        identificativoMessaggio = identificativo
                    };

                    this._log.InfoFormat("CHIAMATA A SCARICAMESSAGGIOBINARIO WS NLAPEC, IDENTIFICATIVO: {0}", identificativo);
                    var response = ws.ScaricaMessaggioBinario(request);
                    this._log.InfoFormat("CHIAMATA A SCARICAMESSAGGIOBINARIO WS NLAPEC, IDENTIFICATIVO: {0} AVVENUTA CON SUCCESSO, NOME FILE: {1}", identificativo, response.nomeFile);

                    return response;
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("ERRORE GENERATO DURANTE LA RICHIESTA A ScaricaMessaggioBinario DEL WS NLAPEC, ERRORE: {0}", ex.Message);
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA RICHIESTA A ScaricaMessaggioBinario DEL WS NLAPEC, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
