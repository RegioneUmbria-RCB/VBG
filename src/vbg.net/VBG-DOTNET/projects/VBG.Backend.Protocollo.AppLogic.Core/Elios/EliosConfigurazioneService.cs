using EliosWSConfigurazioneSoapClient;
using VBG.Shared.Infrastructure.ServiceModel;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class EliosConfigurazioneService
    {
        private readonly ProtocolloSerializer _serializer;
        private readonly ClientConfigurazioneServiceCreator _clientConfigurazioneServiceCreator;

        public EliosConfigurazioneService(string urlConfigurazione, ProtocolloSerializer protocolloSerializer, IBindingFactory bindingFactory, ProtocolloLogs logs)
        {
            this._serializer = protocolloSerializer;
            this._clientConfigurazioneServiceCreator = new ClientConfigurazioneServiceCreator(logs, bindingFactory, urlConfigurazione);
        }

        internal ConfigurazioneResponse Configurazione(string token)
        {
            try
            {
                using (var ws = this._clientConfigurazioneServiceCreator.CreateClient())
                {
                    var response = ws.Service.Configurazione(token);
                    this._serializer.LogAndValidate("ConfigurazioneResponse.xml", response);

                    return ConfigurazioneResponse.FromWSResponse(response);
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
    }
}
