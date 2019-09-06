using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;

namespace Init.SIGePro.Protocollo.Insiel2.LeggiProtocollo
{
    public class LeggiProtoNumeroAnnoInputAdapter : ILeggiProtoInputAdapter
    {
        string _numero;
        string _anno;

        public LeggiProtoNumeroAnnoInputAdapter(string numero, string anno)
        {
            _numero = numero;
            _anno = anno;
        }

        public DettagliProtocolloRequest Adatta()
        {
            var request = new DettagliProtocolloRequest
            {
                Registrazione = new ProtocolloRequest
                {
                    Item = new ProtocolloRequestIdentificatoreProt
                    {
                        Anno = _anno,
                        Numero = _numero,
                        versoSpecified = false
                    }
                }
            };

            return request;
        }
    }
}
