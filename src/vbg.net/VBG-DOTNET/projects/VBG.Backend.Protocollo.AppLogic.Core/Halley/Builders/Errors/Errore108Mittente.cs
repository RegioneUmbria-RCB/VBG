using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders.Errors
{
    public class Errore108Mittente : IErrori
    {
        HalleySegnaturaInput _segnatura;
        ProtocolloSerializer _serializer;

        public Errore108Mittente(HalleySegnaturaInput segnatura, ProtocolloSerializer serializer)
        {
            _segnatura = segnatura;
            _serializer = serializer;
        }

        public HalleySegnaturaBuilder.SegnaturaRequest GetSegnatura()
        {
            foreach (var mittente in _segnatura.Intestazione.Mittente)
            {
                var persona = (Persona)mittente.Items[0];
                persona.Cognome = String.Format("{0} ({1})", persona.Cognome, persona.CodiceFiscale);
            }

            var segnaturaString = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, _segnatura);

            return new HalleySegnaturaBuilder.SegnaturaRequest(_segnatura, segnaturaString);
        }
    }
}
