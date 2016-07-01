using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Halley.Builders.Errors
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
            var mittente = (Persona)_segnatura.Intestazione.Mittente[0].Items[0];
            mittente.Cognome = String.Format("{0} ({1})", mittente.Cognome, mittente.CodiceFiscale);

            var segnaturaString = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, _segnatura);

            return new HalleySegnaturaBuilder.SegnaturaRequest(_segnatura, segnaturaString);
        }
    }
}
