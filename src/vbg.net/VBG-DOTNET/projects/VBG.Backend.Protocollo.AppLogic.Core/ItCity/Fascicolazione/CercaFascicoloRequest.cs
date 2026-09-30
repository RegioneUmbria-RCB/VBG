using ItCityService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Fascicolazione
{
    public class CercaFascicoloRequest : IFascicolazioneRequest
    {
        string _classifica;
        int _anno;
        string _numeroFascicolo;

        public bool CompletaRegistrazione => false;

        public CercaFascicoloRequest(string classifica, int? anno, string numeroFascicolo)
        {
            this._classifica = classifica;
            this._anno = anno.GetValueOrDefault(DateTime.Now.Year);
            this._numeroFascicolo = numeroFascicolo;
        }

        public Fascicolo GetDatiFascicoloRequest(FascicolazioneServiceWrapper fascicolazioneService, int idUnitaOperativa)
        {
            var infoCercaFascicolo = new CercaFascicoloInfo(this._classifica, this._anno, this._numeroFascicolo);
            return fascicolazioneService.CercaFascicolo(infoCercaFascicolo);
        }
    }
}
