using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.Insiel3.Services;

namespace Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo
{
    public class LeggiProtoId : ILeggiProtocollo
    {
        const string SEPARATORE_ID_PROTOCOLLO = ";";
        string _idProtocollo;
        ProtocolloService _wrapper;

        public LeggiProtoId(string idProtocollo, ProtocolloService wrapper)
        {
            _idProtocollo = idProtocollo;
            _wrapper = wrapper;
        }

        public DettagliProtocollo Leggi()
        {
            var idProtocolloAdapter = new IdProtocolloAdapter(_idProtocollo, SEPARATORE_ID_PROTOCOLLO);
            var idProtocollo = idProtocolloAdapter.Adatta();

            var request = new DettagliProtocolloRequest
            {
                registrazione = new ProtocolloRequest
                {
                    Item = new IdProtocollo
                    {
                        progDoc = idProtocollo.ProgDoc,
                        progMovi = idProtocollo.ProgMovi
                    }
                }
            };

            var response = _wrapper.LeggiProtocollo(request, true);
            return response;
        }
    }
}
