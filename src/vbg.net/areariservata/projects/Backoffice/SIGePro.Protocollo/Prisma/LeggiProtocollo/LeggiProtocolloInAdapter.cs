using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    public class LeggiProtocolloInAdapter
    {
        public LeggiProtocolloInAdapter()
        {

        }

        public LeggiProtocolloInXML Adatta(string numero, string anno, string registro, string utente)
        {
            return new LeggiProtocolloInXML
            {
                ProtocolloGruppo = new ProtocolloGruppoInXml
                {
                    Anno = anno,
                    Numero = numero,
                    TipoRegistro = registro
                },
                Utente = utente
            };
        }
    }
}
