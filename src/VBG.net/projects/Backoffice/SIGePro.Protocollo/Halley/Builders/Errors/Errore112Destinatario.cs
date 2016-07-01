using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Halley.Builders.Errors
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
            var destinatario = (Persona)_segnatura.Intestazione.Destinatario[0].Items[0];
            destinatario.Cognome = String.Format("{0} ({1})", destinatario.Cognome, destinatario.CodiceFiscale);

            var segnaturaString = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, _segnatura);

            return new HalleySegnaturaBuilder.SegnaturaRequest(_segnatura, segnaturaString);
        }
    }
}
