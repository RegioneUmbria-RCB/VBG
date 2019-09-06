using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.Logs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo
{
    public class LeggiProtocolloFactory
    {
        public static ILeggiProtocollo Create(string idProtocollo, string numero, string anno, string codiceUfficio, string codiceRegistro, ProtocolloService wrapper, ProtocolloLogs logs)
        {
            if (String.IsNullOrEmpty(idProtocollo))
            {
                return new LeggiProtoNumeroAnno(numero, anno, codiceRegistro, codiceUfficio, wrapper, logs);
            }
            else
            {
                return new LeggiProtoId(idProtocollo, wrapper);
            }
        }
    }
}
