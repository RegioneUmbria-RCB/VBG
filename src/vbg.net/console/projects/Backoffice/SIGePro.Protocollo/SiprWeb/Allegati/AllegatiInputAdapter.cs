/*
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.SiprWeb.Allegati;
using Init.SIGePro.Protocollo.SiprWeb.Verticalizzazioni;
using System.IO;


namespace Init.SIGePro.Protocollo.SiprWeb.Allegati
{
    public class AllegatiInputAdapter
    {
        ProtocolloLogs _logs;
        string _numeroProtocollo;
        List<ProtocolloAllegati> _allegati;
        VerticalizzazioniConfiguration _vert;
        string _utente;

        public AllegatiInputAdapter(ProtocolloLogs logs, string numeroProtocollo, string utente, VerticalizzazioniConfiguration vert, List<ProtocolloAllegati> allegati)
        {
            _logs = logs;
            _numeroProtocollo = numeroProtocollo;
            _vert = vert;
            _allegati = allegati;
            _utente = utente;
        }

        public inserisciAllegatiRequest AdattaTest()
        {
            string[] descrizioneList = new string[] { "test1", "test2", "test3" };
            string[] nomeFileList = new string[] { "Allegato INV.doc", "Privacy.doc", "TestoProposta.pdf" };
            string[] titoloList = new string[] { "1", "2", "3" };

            var pathFile = _vert.FtpPathAllegati.Substring(_vert.FtpPathAllegati.LastIndexOf("/"));

            var request = new inserisciAllegatiRequest
            {
                Descrizione = descrizioneList.ToArray(),
                NomeFile = nomeFileList.ToArray(),
                Titolo = titoloList.ToArray(),
                NumeroProtocollo = _numeroProtocollo,
                PathFile = pathFile,
                TipoAllegato = TipoAllegato_Type.O,
                utente = _utente
            };

            return request;
        }

        public inserisciAllegatiRequest Adatta()
        {
            List<string> descrizioneList = new List<string>();
            List<string> nomeFileList = new List<string>();
            List<string> titoloList = new List<string>();

            foreach (var allegato in _allegati)
            {
                _logs.InfoFormat("Inserimento dell'allegato codice: {0} nome file: {1}, descrizione: {2}", allegato.CODICEOGGETTO, allegato.NOMEFILE, allegato.Descrizione);
                descrizioneList.Add(allegato.Descrizione);
                nomeFileList.Add(allegato.NOMEFILE);
                titoloList.Add(allegato.CODICEOGGETTO);

                UploadFile(allegato.OGGETTO);
            }

            var request = new inserisciAllegatiRequest
            {
                Descrizione = descrizioneList.ToArray(),
                NomeFile = nomeFileList.ToArray(),
                Titolo = titoloList.ToArray(),
                NumeroProtocollo = _numeroProtocollo,
                PathFile = _vert.FtpPathAllegati,
                TipoAllegato = TipoAllegato_Type.O,
                utente = _utente
            };

            return request;
        }

        private void UploadFile(byte[] buffer)
        {
            if (String.IsNullOrEmpty(_vert.NomeDirCondivisa))
                _logs.Warn("Parametro NOMEDIR_CONDIVISA della verticalizzazione PROTOCOLLO_SIPRWEB non valorizzato, non e' possibile inviare file al protocollo");

            File.WriteAllBytes(_vert.NomeDirCondivisa, buffer);
        }
    }
}
*/