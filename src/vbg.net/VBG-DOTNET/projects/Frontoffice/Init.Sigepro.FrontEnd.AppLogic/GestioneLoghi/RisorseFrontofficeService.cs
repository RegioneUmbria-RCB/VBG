using Init.Sigepro.FrontEnd.AppLogic.GestioneConfigurazioneContenuti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLoghi
{
    public class RisorseFrontofficeService : IRisorseFrontofficeService
    {
        private readonly ConfigurazioneContenutiServiceCreator _areaRiservataServiceCreator;
        private readonly IOggettiService _oggettiService;

        public RisorseFrontofficeService(ConfigurazioneContenutiServiceCreator areaRiservataServiceCreator, IOggettiService oggettiService)
        {
            this._areaRiservataServiceCreator = areaRiservataServiceCreator;
            this._oggettiService = oggettiService;
        }

        public BinaryFile GetRisorsaFrontoffice(string idRisorsa)
        {
            return this._areaRiservataServiceCreator.Call(ws =>
            {
                var codiceOggetto = ws.Service.GetCodiceOggettoRisorsaFrontoffice(ws.Token, idRisorsa);

                if (codiceOggetto <= 0)
                {
                    return null;
                }

                var file = this._oggettiService.GetById(codiceOggetto);

                return file;
            });
        }
    }
}
