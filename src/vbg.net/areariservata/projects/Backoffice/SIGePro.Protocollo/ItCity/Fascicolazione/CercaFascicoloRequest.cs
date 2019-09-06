using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ItCity.Classificazione;
using Init.SIGePro.Protocollo.ProtocolloItCityService;

namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public class CercaFascicoloRequest : IFascicolazioneRequest
    {
        string _classifica;
        int _anno;
        string _numeroFascicolo;
        private readonly ClassificheServiceWrapper _classificheService;

        public bool CompletaRegistrazione => false;

        public CercaFascicoloRequest(string classifica, int? anno, string numeroFascicolo, ClassificheServiceWrapper classificheService)
        {
            this._classifica = classifica;
            this._anno = anno.GetValueOrDefault(DateTime.Now.Year);
            this._numeroFascicolo = numeroFascicolo;
            this._classificheService = classificheService;
        }

        public Fascicolo GetDatiFascicoloRequest(FascicolazioneServiceWrapper fascicolazioneService)
        {
            var infoCercaFascicolo = new CercaFascicoloInfo(this._classifica, this._anno, this._numeroFascicolo, this._classificheService);
            return fascicolazioneService.CercaFascicolo(infoCercaFascicolo);
        }
    }
}
