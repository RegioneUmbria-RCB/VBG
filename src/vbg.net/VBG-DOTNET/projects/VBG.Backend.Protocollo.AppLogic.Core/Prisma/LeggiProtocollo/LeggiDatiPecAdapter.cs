using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.LeggiProtocollo
{
    public class LeggiDatiPecAdapter
    {
        public LeggiDatiPecAdapter()
        {

        }

        public LeggiPecInXML Adatta(string numero, string anno, string registro, string utente)
        {
            return new LeggiPecInXML
            {
                Utente = utente,
                ProtocolloGruppo = new ProtocolloGruppoInXml
                {
                    Anno = anno,
                    Numero = numero,
                    TipoRegistro = registro
                }
            };
        }
    }
}
