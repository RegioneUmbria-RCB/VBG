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
        string _codiceRegistro;
        string _codiceUfficio;

        public LeggiProtoNumeroAnnoInputAdapter(string numero, string anno, string codiceRegistro, string codiceUfficio)
        {
            this._numero = numero;
            this._anno = anno;
            this._codiceRegistro = codiceRegistro;
            this._codiceUfficio = codiceUfficio;
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
                        versoSpecified = false,
                        CodiceRegistro = this._codiceRegistro,
                        CodiceUfficio = this._codiceUfficio
                    }
                }
            };
            return request;
        }
    }
}
