using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.Halley.Builders.Errors
{
    public class ErroriFactory
    {
        public static IErrori Create(string codiceErrore, HalleySegnaturaInput segnatura, ProtocolloSerializer serializer)
        {
            if (codiceErrore == "-108")
                return new Errore108Mittente(segnatura, serializer);
            else if (codiceErrore == "-112")
                return new Errore112Destinatario(segnatura, serializer);
            else
                return null;
        }
    }
}
