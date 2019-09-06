using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;

namespace Init.SIGePro.Protocollo.Insiel2.LeggiProtocollo
{
    public class LeggiProtoIdInputAdapter : ILeggiProtoInputAdapter
    {
        IdProtocolloAdapter.IdProtocollo _idProtocollo;

        public LeggiProtoIdInputAdapter(IdProtocolloAdapter.IdProtocollo idProtocollo)
        {
            _idProtocollo = idProtocollo;
        }

        public DettagliProtocolloRequest Adatta()
        {
            return new DettagliProtocolloRequest
            {
                Registrazione = new ProtocolloRequest
                {
                    Item = new IdProtocollo
                    {
                        ProgDoc = _idProtocollo.ProgDoc,
                        ProgMovi = _idProtocollo.ProgMovi
                    }
                }
            };
        }
    }
}
