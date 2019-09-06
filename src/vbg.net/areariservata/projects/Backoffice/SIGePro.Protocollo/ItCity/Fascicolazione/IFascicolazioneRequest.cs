using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ItCity.Classificazione;
using Init.SIGePro.Protocollo.ProtocolloItCityService;

namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public interface IFascicolazioneRequest
    {
        Fascicolo GetDatiFascicoloRequest(FascicolazioneServiceWrapper fascicolazioneService);
        bool CompletaRegistrazione { get; }
    }
}
