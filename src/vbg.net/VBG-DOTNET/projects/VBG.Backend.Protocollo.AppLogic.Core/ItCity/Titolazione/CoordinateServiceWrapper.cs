using VBG.Shared.Infrastructure.ServiceModel;
using ItCityService;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Titolazione
{
    public class CoordinateServiceWrapper
    {
        private readonly LoginWsInfo _loginInfo;
        private readonly ProtocolloLogs _logs;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public CoordinateServiceWrapper(string url, LoginWsInfo loginInfo, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory)
        {
            this._loginInfo = loginInfo;
            this._logs = logs;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
        }

        public CoordinateTitolazioneOutput GetCoordinateTitolazione(string classifica)
        {
            if (string.IsNullOrWhiteSpace(classifica))
                throw new InvalidOperationException(
                    "Non è possibile richiamare CoordinateServiceWrapper.GetCoordinateTitolazione senza passare il parametro 'classifica'.");

            var parts = classifica.Split('.');

            if (parts.Length != 3)
                throw new InvalidOperationException(
                    "Il parametro 'classifica' deve rispettare il formato 'titolo.classe.sottoclasse'.");

            var titolo = parts[0];
            var classe = parts[1];
            var sottoclasse = parts[2];

            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    var request = new CoordinateTitolazione
                    {
                        Titolo = titolo,
                        Classe = classe,
                        Sottoclasse = sottoclasse
                    };

                    var response = ws.Service.GetCoordinateTitolazione(
                        this._loginInfo.Username,
                        this._loginInfo.Password,
                        request);

                    if (response.ExitCode != 0)
                        throw new Exception(response.ExitMessage);

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
