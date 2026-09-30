using VBG.Backend.Protocollo.AppLogic.Core.ItCity.Classificazione;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Fascicolazione
{
    public class FascicolazioneRequestFactory
    {
        public static IFascicolazioneRequest Create(string classifica, string numero, int? anno, string oggetto, ClassificheServiceWrapper classificheService, string idProtocollo)
        {
            if (String.IsNullOrEmpty(numero))
            {
                int idClassifica = classificheService.GetClassificaByCodice(classifica).Id.Value;

                return new CreaFascicoloRequest(idClassifica, oggetto, idProtocollo);
            }

            return new CercaFascicoloRequest(classifica, anno, numero);
        }
    }
}
