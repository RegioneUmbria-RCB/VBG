using ItCityService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Fascicolazione
{
    public class CreaFascicoloRequest : IFascicolazioneRequest
    {
        int _classifica;
        string _oggetto;
        string _idProtocollo;

        public CreaFascicoloRequest(int classifica, string oggetto, string idProtocollo)
        {
            this._classifica = classifica;
            this._oggetto = oggetto;
            this._idProtocollo = idProtocollo;
        }

        public bool CompletaRegistrazione => true;

        public Fascicolo GetDatiFascicoloRequest(FascicolazioneServiceWrapper fascicolazioneService, int idUnitaOperativa)
        {
            var creaFascicoloInfo = new CreazioneFascicoloRequestInfo(this._classifica, this._oggetto);
            return fascicolazioneService.CreaFascicolo(creaFascicoloInfo, idUnitaOperativa);
        }
    }
}
