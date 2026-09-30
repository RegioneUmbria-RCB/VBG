using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ProtocolloInsielService2;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.LeggiProtocollo
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
