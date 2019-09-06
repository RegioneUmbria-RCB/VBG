using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloItCityService;

namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public class CreaFascicoloRequest : IFascicolazioneRequest
    {
        string _classifica;
        string _oggetto;
        string _idProtocollo;

        public CreaFascicoloRequest(string classifica, string oggetto, string idProtocollo)
        {
            this._classifica = classifica;
            this._oggetto = oggetto;
            this._idProtocollo = idProtocollo;
        }

        public bool CompletaRegistrazione => true;

        public Fascicolo GetDatiFascicoloRequest(FascicolazioneServiceWrapper fascicolazioneService)
        {
            var creaFascicoloInfo = new CreazioneFascicoloRequestInfo(this._classifica, this._oggetto);
            return fascicolazioneService.CreaFascicolo(creaFascicoloInfo, this._idProtocollo);
        }
    }
}
