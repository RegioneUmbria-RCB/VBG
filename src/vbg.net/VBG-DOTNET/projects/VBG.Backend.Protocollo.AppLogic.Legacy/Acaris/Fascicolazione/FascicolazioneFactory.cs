using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione
{
    public class FascicolazioneFactory
    {
        private readonly Dictionary<string, IFascicolazioneService> _fasicolazioneServices;
        private readonly string _default = FascicolazioneStandardService.Name;

        public FascicolazioneFactory(IProtocolloSerializer serializer, ProtocolloLogs logger, IFolderTypeResolver resolver, ParametriRegoleInfo config)
        {
            this._fasicolazioneServices = new Dictionary<string, IFascicolazioneService>
            {
                { FascicolazioneSuDossierService.Name, new FascicolazioneSuDossierService(serializer, logger, resolver) },
                { FascicolazioneStandardService.Name, new FascicolazioneStandardService(serializer, logger, resolver, config) }
            };
        }

        public IdFolder Fascicola(FascicolaRequest request)
        {
            string serviceName = string.IsNullOrEmpty(request.Configurazione.TipoFascicolazione) ? this._default : request.Configurazione.TipoFascicolazione;
            IFascicolazioneService service = this._fasicolazioneServices[serviceName];
            return service.Fascicola(request);
        }
    }
}
