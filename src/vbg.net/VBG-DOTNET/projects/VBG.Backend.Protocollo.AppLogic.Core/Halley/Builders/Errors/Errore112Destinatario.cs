using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders.Errors
{
    public class Errore112Destinatario : IErrori
    {
        HalleySegnaturaInput _segnatura;
        ProtocolloSerializer _serializer;

        public Errore112Destinatario(HalleySegnaturaInput segnatura, ProtocolloSerializer serializer)
        {
            _segnatura = segnatura;
            _serializer = serializer;
        }

        public HalleySegnaturaBuilder.SegnaturaRequest GetSegnatura()
        {
            foreach (var destinatario in _segnatura.Intestazione.Destinatario)
            {
                var persona = (Persona)destinatario.Items[0];
                persona.Cognome = String.Format("{0} ({1})", persona.Cognome, persona.CodiceFiscale);
            }

            var segnaturaString = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, _segnatura);
            return new HalleySegnaturaBuilder.SegnaturaRequest(_segnatura, segnaturaString);
        }
    }
}
