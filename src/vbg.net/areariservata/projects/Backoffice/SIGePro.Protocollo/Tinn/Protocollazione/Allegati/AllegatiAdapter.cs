using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Tinn.Segnatura;
using Init.SIGePro.Protocollo.Tinn.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs;
using System.IO;
using Init.SIGePro.Protocollo.Tinn.Protocollazione.Allegati;
using Init.SIGePro.Protocollo.Tinn.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.Tinn.Protocollazione.Allegati
{
    public class AllegatiAdapter
    {
        ProtocolloService _wrapper;
        List<ProtocolloAllegati> _allegati;
        ProtocolloSerializer _serializer;
        ProtocolloLogs _logs;
        VerticalizzazioniConfiguration _vert;

        public AllegatiAdapter(ProtocolloService wrapper, List<ProtocolloAllegati> allegati, ProtocolloSerializer serializer, ProtocolloLogs logs, VerticalizzazioniConfiguration vert)
        {
            _wrapper = wrapper;
            _allegati = allegati;
            _serializer = serializer;
            _logs = logs;
            _vert = vert;
        }

        public void Adatta(SegnaturaInput segnatura)
        {
            if (_allegati.Count == 0 && _vert.InviaSegnatura)
            {
                segnatura.Descrizione = new Descrizione
                {
                    Documento = new Documento
                    {
                        nome = ProtocolloLogsConstants.ProfileFileName,
                        DescrizioneDocumento = new DescrizioneDocumento { Text = new string[] { ProtocolloLogsConstants.ProfileFileName } }
                    }
                };

                if(!String.IsNullOrEmpty(_vert.TipoDocumentoPrincipale))
                    segnatura.Descrizione.Documento.TipoDocumento = new TipoDocumento { Text = new string[] { _vert.TipoDocumentoPrincipale } };

                _serializer.Serialize(ProtocolloLogsConstants.ProfileFileName, segnatura);
                var profilePath = Path.Combine(_logs.Folder, ProtocolloLogsConstants.ProfileFileName);

                _allegati.Add(new ProtocolloAllegati
                {
                    NOMEFILE = ProtocolloLogsConstants.ProfileFileName,
                    Descrizione = ProtocolloLogsConstants.ProfileFileName,
                    MimeType = "text/xml",
                    OGGETTO = File.ReadAllBytes(profilePath)
                });
            }

            if (_allegati.Count == 1)
            {
                var documento = _allegati.First();
                _wrapper.InserisciAllegato(documento);

                segnatura.Descrizione = new Descrizione
                {
                    Documento = new Documento
                    {
                        id = documento.ID,
                        nome = documento.NOMEFILE,
                        DescrizioneDocumento = new DescrizioneDocumento { Text = new string[] { ProtocolloLogsConstants.ProfileFileName } }
                    }
                };

                if (!String.IsNullOrEmpty(_vert.TipoDocumentoPrincipale))
                    segnatura.Descrizione.Documento.TipoDocumento = new TipoDocumento { Text = new string[] { _vert.TipoDocumentoPrincipale } };
            }

            if (_allegati.Count > 1)
            {
                segnatura.Descrizione = new Descrizione
                {
                    Documento = _wrapper.ValorizzaeInviaDocumentoAllegato(_allegati[0], _vert.TipoDocumentoPrincipale),
                    Allegati = _allegati.Skip(1).Select(x => _wrapper.ValorizzaeInviaDocumentoAllegato(x, _vert.TipoDocumentoAllegato)).ToArray()
                };
            }
        }
    }
}
