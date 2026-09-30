using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.Smistamento
{
    public class AddSmistamentoInAdapter
    {
        public AddSmistamentoInAdapter()
        {

        }

        public AddSmistamentoInXML Adatta(string idDocumento, string tipoSmistamento, string uo, string utente)
        {
            return new AddSmistamentoInXML
            {
                IdDocumento = idDocumento,
                TipoSmistamento = tipoSmistamento,
                UnitaSmistamento = uo,
                Utente = utente
            };
        }
    }
}
