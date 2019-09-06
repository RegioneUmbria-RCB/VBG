using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Fascicolazione
{
    public class AbilitazioneFascicolazioneAdapter
    {
        public AbilitazioneFascicolazioneAdapter()
        {

        }

        public AbilAperturaPraticaRequest Adatta(FascicolazioneInfo info)
        {
            var classificaAdapter = new ClassificaAdapter();
            var classifica = classificaAdapter.Adatta(info.UsaLivelliClassifica, info.Classifica);

            return new AbilAperturaPraticaRequest
            {
                anno = info.AnnoFascicolo,
                codiceRegistro = classifica,
                codiceUfficio = info.CodiceUfficio,
                codiceUfficioOperante = info.CodiceUfficioOperante,
            };
        }
    }
}
