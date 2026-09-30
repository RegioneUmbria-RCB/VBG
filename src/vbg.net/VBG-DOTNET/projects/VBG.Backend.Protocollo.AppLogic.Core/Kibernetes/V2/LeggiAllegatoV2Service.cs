using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using ProtocolloKibernetesV2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V2
{
    public class LeggiAllegatoV2Service : ILeggiAllegatoService
    {
        private IParametriService _parametriService;
        private ILog _logger;
        private IProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;

        public LeggiAllegatoV2Service(IParametriService parametriService, ILog logger, IProtocolloSerializer serializer, IBindingFactory bindingFactory) 
        {
            this._parametriService = parametriService;
            this._logger = logger;
            this._serializer = serializer;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logger, bindingFactory, parametriService.Url);
        }

        public LeggiAllegatoResponse LeggiAllegato(int idProtocollo, int idAllegato)
        {
            this._logger.Debug($"Inizio lettura allegato con indice {idAllegato}");

            using (var ws = this._protocolloClientServiceCreator.CreateClient())
            {
                try
                {
                    var authInfo = new AuthInfo
                    {
                        NomeUtente = this._parametriService.UserName,
                        Password = this._parametriService.Password
                    };

                    this._serializer.LogAndValidate("AuthInfoRequest.xml", authInfo);

                    var response = ws.Service.GetAllegatiProtocollo(authInfo, idProtocollo);

                    this._serializer.LogAndValidate("GetAllegatiProtocolloResponse.xml", response);

                    if (!response.Any())
                    {
                        throw new Exception("Non sono presenti allegati");
                    }

                    this._logger.Debug($"Fine lettura allegato con indice {idAllegato}");

                    return LeggiAllegatoResponse.FromExportResult(response[idAllegato]);

                }
                catch (Exception ex)
                {
                    throw new Exception($"Errore durante la chiamata a LeggiAllegato per la lettura dell'allegato con indice {idAllegato}: {ex.Message}");
                }
            }
        }

    }
}
