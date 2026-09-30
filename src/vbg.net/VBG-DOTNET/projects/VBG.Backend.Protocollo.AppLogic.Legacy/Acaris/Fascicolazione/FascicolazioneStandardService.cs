using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Entity;
using VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Folder;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione
{
    public class FascicolazioneStandardService : IFascicolazioneService
    {
        private readonly ProtocolloLogs _logger;
        private readonly FolderService _folderService;
        public static readonly string Name = "FascicolazioneStandardService";
        private readonly ParametriRegoleInfo _config;

        public FascicolazioneStandardService(IProtocolloSerializer serializer, ProtocolloLogs logger, IFolderTypeResolver resolver, ParametriRegoleInfo config)
        {
            this._logger = logger;
            this._folderService = new FolderService(serializer, this._logger, resolver);
            this._config = config;
        }

        public IdFolder Fascicola(FascicolaRequest request)
        {
            this._logger.Info("Inizio fascicolazione standard");
            IdFolder idFolder;
            if (request.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if(!string.IsNullOrEmpty(request.IdProtocollo))
                {
                    idFolder = this._folderService.GetFolderDaIdProtocollo(request.Protocollo, this._config, request.IdProtocollo);
                }
                else
                {
                    if (String.IsNullOrEmpty(request.NumeroProtocollo) && !request.DataProtocollo.HasValue)
                    {
                        throw new Exception("Impossibile risalire al fascicolo da utilizzare in quanto l'istanza non è protocollata correttamente");
                    }

                    idFolder = this._folderService.GetFolderDaEstremiProtocollo(request.Protocollo, this._config, request.NumeroProtocollo, request.DataProtocollo.Value.Year);
                }
            }
            else
            {
                //creazione del folder
                var folderResponse = this._folderService.CreaFascicolo();
                idFolder = new IdFolder(folderResponse.objectId.value);
            }

            this._logger.Info("Fine fascicolazione standard");
            return idFolder;
        }
    }
}
