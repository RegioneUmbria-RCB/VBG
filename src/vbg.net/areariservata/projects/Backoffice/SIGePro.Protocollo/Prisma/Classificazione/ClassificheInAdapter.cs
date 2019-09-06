using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Classificazione
{
    public class ClassificheInAdapter
    {
        public ClassificheInAdapter()
        {

        }

        public ClassificheInXML Adatta(string codiceAmministrazione, string codiceAoo, string username)
        {
            return new ClassificheInXML
            {
                CodiceAmministrazione = codiceAmministrazione,
                CodiceAoo = codiceAoo,
                CodiceClassifica = "%",
                Utente = username
            };
        }
    }
}
