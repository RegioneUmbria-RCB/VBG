using ItCityService;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;


namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.RicercaUO
{
    public class RicercaUOServiceWrapper
    {
        private readonly LoginWsInfo _loginInfo;
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public RicercaUOServiceWrapper(string url, LoginWsInfo loginInfo, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            this._loginInfo = loginInfo;
            this._logs = logs;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
        }

        public RicercaUOOutput CercaUOPerChiaveAletrnativa(RecapitoInterno.ChiaveAlternativa chiaveAlternativa)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    var request = new RecapitoInterno
                    {
                        ChiaveComposta = chiaveAlternativa
                    };

                    var response = ws.Service.RicercaUO(this._loginInfo.Username, this._loginInfo.Password, this._loginInfo.Identificativo, request);

                    if (response.ExitCode != 0)
                    {
                        throw new Exception(response.ExitMessage);
                    }

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"RICERCA DELLA UO FALLITA: {ex.Message}", ex);
            }
        }
    }
}
