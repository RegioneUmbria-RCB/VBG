using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Fascicolazione
{
    public class LeggiFascicoloInAdapter
    {
        public LeggiFascicoloInAdapter()
        {

        }

        public LeggiFascicoliInXML Adatta(string annoFascicolo, string numeroFascicolo, string utente, string classifica)
        {
            if (String.IsNullOrEmpty(numeroFascicolo) || String .IsNullOrEmpty(annoFascicolo))
            {
                return null;
            }

            return new LeggiFascicoliInXML
            {
                AnnoFascicolo = annoFascicolo,
                NumeroFascicolo = numeroFascicolo,
                Utente = utente,
                Classifica = classifica
            };
        }
    }
}
