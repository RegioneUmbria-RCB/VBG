using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Sue
{
    public class AllegatoGenericoAdapter
    {
        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;
        IEnumerable<ProtocolloAllegati> _allegati;
        string[] _formatiAmmessi { get { return new string[] { ".pdf", ".pdf.p7m", ".xml", ".dwf", ".dwf.p7m", ".svg", ".svg.p7m", ".jpg", ".jpg.p7m" }; } }

        public AllegatoGenericoAdapter(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs, IEnumerable<ProtocolloAllegati> allegati)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
            _allegati = allegati;
        }

        public AllegatoGenerico[] Adatta()
        {
            var res = new List<AllegatoGenerico>();

            var codicePraticaTelematica = _datiProtocollazioneService.Istanza.CODICEPRATICATEL;
            if (String.IsNullOrEmpty(codicePraticaTelematica))
                CreaCodicePraticaTelematica();

            foreach (var al in _allegati)
            {
                string nomeFile = String.Format("{0}.{1}{2}", codicePraticaTelematica, al.CODICEOGGETTO, al.Extension);

                if (this._formatiAmmessi.Contains(al.Extension.ToLower()))
                    res.Add(new AllegatoGenerico
                    {
                        nomefile = nomeFile,
                        descrizione = al.Descrizione,
                        mime = al.MimeType,
                        nomefileoriginale = al.NOMEFILE
                    });
                else
                    _logs.WarnFormat("IL FORMATO {0} DELL'ALLEGATO {1}, CODICE {2} NON E' AMMESSO, SONO AMMESSI SOLAMENTE I VALORI: {3}", al.Extension, al.NOMEFILE, al.CODICEOGGETTO, String.Join(",", _formatiAmmessi));
            }

            return res.ToArray();
        }

        private string CreaCodicePraticaTelematica()
        {
            var cfPiva = String.IsNullOrEmpty(_datiProtocollazioneService.Istanza.AziendaRichiedente.CODICEFISCALE) ? _datiProtocollazioneService.Istanza.AziendaRichiedente.PARTITAIVA : _datiProtocollazioneService.Istanza.AziendaRichiedente.CODICEFISCALE;
            if (String.IsNullOrEmpty(cfPiva))
                throw new Exception("L'AZIENDA NON HA VALORIZZATO NE' IL CODICE FISCALE NE' LA PARTITA IVA");

            return String.Format("{0}-{1}", cfPiva, _datiProtocollazioneService.Istanza.DATA.Value.ToString("yyyyMMdd-HHmi"));
        }
    }
}
