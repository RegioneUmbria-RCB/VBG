using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Segnatura;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.Allegati
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

                _serializer.LogAndValidate(ProtocolloLogsConstants.ProfileFileName, segnatura);
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
