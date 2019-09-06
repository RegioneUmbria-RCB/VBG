using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Fascicolazione
{
    public class FascicolazioneAdapterResponse
    {
        public FascicolazioneAdapterResponse()
        {

        }

        public DatiFascicolo Adatta(string anno, string dataApertura, string numero)
        {
            return new DatiFascicolo
            {
                AnnoFascicolo = anno,
                DataFascicolo = dataApertura,
                NumeroFascicolo = numero
            };
        }
    }
}
