using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ItCity.Protocollazione
{
    public static class ProtocollazioneRequestFactory
    {
        public static IProtocollazioneRequest Create(IDatiProtocollo datiProtocollo, IEnumerable<IAnagraficaAmministrazione> anagrafiche, int idFascicolo, int? numeroSottoFascicolo)
        {
            if (datiProtocollo.Flusso == ProtocolloConstants.COD_ARRIVO)
            {
                return new ProtocollazioneRequestArrivo(datiProtocollo, anagrafiche, idFascicolo, numeroSottoFascicolo);
            }
            else if (datiProtocollo.Flusso == ProtocolloConstants.COD_PARTENZA)
            {
                return new ProtocollazioneRequestPartenza(datiProtocollo, anagrafiche, idFascicolo, numeroSottoFascicolo);
            }
            else if (datiProtocollo.Flusso == ProtocolloConstants.COD_INTERNO)
            {
                return new ProtocollazioneRequestInterna(datiProtocollo, idFascicolo, numeroSottoFascicolo);
            }
            else
            {
                throw new Exception($"FLUSSO {datiProtocollo.Flusso} NON GESTITO");
            }
        }
    }
}
