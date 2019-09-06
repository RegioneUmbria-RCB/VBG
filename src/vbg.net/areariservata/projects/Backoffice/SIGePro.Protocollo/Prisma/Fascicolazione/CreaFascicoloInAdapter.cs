using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Fascicolazione
{
    public class CreaFascicoloInAdapter
    {
        public CreaFascicoloInAdapter()
        {

        }

        public CreaFascicoloInXML Adatta(FascicolazioneInfo info)
        {
            return new CreaFascicoloInXML
            {
                AnnoFascicolo = info.AnnoFascicolo,
                CodiceClassifica = info.ClassificaFascicolo,
                DataApertura = info.DataFascicolo,
                Oggetto = info.OggettoFascicolo,
                Utente = info.Utente,
                UnitaCompetenza = info.Uo,
                UnitaCreazione = info.Uo
            };
        }
    }
}
