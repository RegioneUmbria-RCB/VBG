using VBG.AppLogic.SSU.GestioneCorrezioni;
using VBG.AppLogic.SSU.GestioneIstanzaRifiutata;

namespace VBG.AppLogic.SSU.GestioneMessaggiNla
{
    public class SsuNlaService
    {
        private readonly IGestioneIstanzaRifiutataSsuService _istanzeRifiutateService;
        private readonly IGestioneCorrezioniSsuService _gestioneCorrezioniSsuService;

        public SsuNlaService(IGestioneIstanzaRifiutataSsuService istanzeRifiutateService, IGestioneCorrezioniSsuService gestioneCorrezioniSsuService)
        {
            this._istanzeRifiutateService = istanzeRifiutateService;
            this._gestioneCorrezioniSsuService = gestioneCorrezioniSsuService;
        }
    }
}
