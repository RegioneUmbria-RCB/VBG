using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Fascicolazione
{
    public class FascicolazioneAdapterResponse
    {
        public FascicolazioneAdapterResponse()
        {

        }

        public DatiFascicoloResponseType Adatta(string anno, string dataApertura, string numero)
        {
            return new DatiFascicoloResponseType
            {
                AnnoFascicolo = anno,
                DataFascicolo = dataApertura,
                NumeroFascicolo = numero
            };
        }
    }
}
