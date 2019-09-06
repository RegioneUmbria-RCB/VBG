using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloItCityService;

namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public class CreaSottoFascicoloRequest : ISottoFascicolazioneRequest
    {
        string _classifica;
        string _oggetto;
        string _idProtocollo;

        FascicoloDiRiferimento _fascicolo;

        public CreaSottoFascicoloRequest(string classifica, string oggetto, FascicoloDiRiferimento fascicolo, string idProtocollo)
        {
            this._classifica = classifica;
            this._oggetto = oggetto;
            this._fascicolo = fascicolo;
            this._idProtocollo = idProtocollo;
        }

        public bool CompletaRegistrazione => true;

        public Fascicolo GetDatiFascicoloRequest( FascicolazioneServiceWrapper fascicolazioneService)
        {
            var creaSottoFascicoloInfo = new CreazioneSottoFascicoloRequestInfo(this._classifica, this._oggetto, this._fascicolo.IdFascicolo);
            return fascicolazioneService.CreaSottoFascicolo(this._fascicolo, creaSottoFascicoloInfo, this._idProtocollo);
        }
    }
}
