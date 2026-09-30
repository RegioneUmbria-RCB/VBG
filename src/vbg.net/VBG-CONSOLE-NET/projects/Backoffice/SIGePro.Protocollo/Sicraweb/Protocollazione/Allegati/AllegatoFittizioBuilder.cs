using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using System.IO;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Segnatura;
using Init.SIGePro.Protocollo.Sicraweb.Protocollazione;

namespace Init.SIGePro.Protocollo.Sicraweb.Protocollazione.Allegati
{
    public class AllegatoFittizioBuilder
    {
        Segnatura.Segnatura _segnatura;
        ProtocolloSerializer _serializer;
        ProtocolloLogs _logs;

        public AllegatoFittizioBuilder(Segnatura.Segnatura segnatura, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            _segnatura = segnatura;
            _serializer = serializer;
            _logs = logs;
        }

        public ProtocolloAllegati Build()
        {
            _segnatura.Descrizione = new Descrizione
            {
                Documento = new Documento { nome = ProtocolloLogsConstants.ProfileFileName }
            };

            _serializer.Serialize(ProtocolloLogsConstants.ProfileFileName, _segnatura);

            return new ProtocolloAllegati
            {
                NOMEFILE = ProtocolloLogsConstants.ProfileFileName,
                MimeType = ProtocolloConstants.MIMETYPE_XML,
                Descrizione = ProtocolloLogsConstants.ProfileFileName,
                OGGETTO = File.ReadAllBytes(Path.Combine(_logs.Folder, ProtocolloLogsConstants.ProfileFileName))
            };
        }
    }
}
