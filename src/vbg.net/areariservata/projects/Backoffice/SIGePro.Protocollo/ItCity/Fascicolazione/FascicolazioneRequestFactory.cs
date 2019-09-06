using Init.SIGePro.Protocollo.ItCity.Classificazione;
using Init.SIGePro.Protocollo.ProtocolloItCityService;
using System;


namespace Init.SIGePro.Protocollo.ItCity.Fascicolazione
{
    public class FascicolazioneRequestFactory
    {
        public static IFascicolazioneRequest Create(string classifica, string numero, int? anno, string oggetto, int? idfascicolo, ClassificheServiceWrapper classificheService, string idProtocollo)
        {
            if (String.IsNullOrEmpty(numero))
            {
                return new CreaFascicoloRequest(classifica, oggetto, idProtocollo);
            }

            return new CercaFascicoloRequest(classifica, anno, numero, classificheService);
        }
    }
}
