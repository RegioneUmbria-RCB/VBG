using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Classificazione
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
                Utente = username,
                Valida = "Y"
            };
        }
    }
}
