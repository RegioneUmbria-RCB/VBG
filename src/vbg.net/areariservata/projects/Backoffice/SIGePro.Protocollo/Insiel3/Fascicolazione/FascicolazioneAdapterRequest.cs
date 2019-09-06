using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Fascicolazione
{
    public class FascicolazioneAdapterRequest
    {
        public FascicolazioneAdapterRequest()
        {

        }

        public AperturaPraticaRequest Adatta(FascicolazioneInfo info)
        {
            var classificaAdapter = new ClassificaAdapter();
            var classifica = classificaAdapter.Adatta(info.UsaLivelliClassifica, info.Classifica);

            var retVal = new AperturaPraticaRequest
            {
                anno = info.AnnoFascicolo,
                codiceUfficio = info.CodiceUfficio,
                codiceUfficioOperante = info.CodiceUfficioOperante,
                oggetto = info.Oggetto,
                codiceRegistro = classifica
            };

            if (String.IsNullOrEmpty(info.NumeroFascicolo))
            {
                retVal.numerazioneManuale = false;
            }
            else
            {
                retVal.numerazioneManuale = true;
                retVal.numero = info.NumeroFascicolo;
            }

            return retVal;

        }
    }
}
