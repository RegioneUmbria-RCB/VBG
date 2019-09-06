using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.Smistamento
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
