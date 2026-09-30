using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders.Errors
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
