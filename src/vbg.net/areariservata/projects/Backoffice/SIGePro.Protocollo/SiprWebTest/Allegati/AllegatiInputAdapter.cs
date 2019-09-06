using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.SiprWebTest.Allegati;
using Init.SIGePro.Protocollo.SiprWebTest.Verticalizzazioni;
using System.IO;
using System.Net;


namespace Init.SIGePro.Protocollo.SiprWebTest.Allegati
{
    public class AllegatiInputAdapter
    {
        ProtocolloLogs _logs;
        string _numeroProtocollo;
        List<ProtocolloAllegati> _allegati;
        VerticalizzazioniConfiguration _vert;
        string _utente;
        public bool isAdapterError { get; private set; }

        public AllegatiInputAdapter(ProtocolloLogs logs, string numeroProtocollo, string utente, VerticalizzazioniConfiguration vert, List<ProtocolloAllegati> allegati)
        {
            isAdapterError = false;
            _logs = logs;
            _numeroProtocollo = numeroProtocollo;
            _vert = vert;
            _allegati = allegati;
            _utente = utente;
        }

        public inserisciAllegatiRequest Adatta()
        {


            inserisciAllegatiRequest request = null;

            try
            {
                if (_allegati.Count == 0)
                    throw new Exception("NON SONO PRESENTI ALLEGATI");

                List<string> descrizioneList = new List<string>();
                List<string> nomeFileList = new List<string>();
                List<string> titoloList = new List<string>();
                List<TipoAllegato_Type> tipoAllegatoList = new List<TipoAllegato_Type>();

                if(!CreaCartella())
                    return null;

                foreach (var allegato in _allegati)
                {
                    _logs.InfoFormat("Inserimento dell'allegato codice: {0} nome file: {1}, descrizione: {2}", allegato.CODICEOGGETTO, allegato.NOMEFILE, allegato.Descrizione);
                    descrizioneList.Add(allegato.Descrizione);
                    nomeFileList.Add(allegato.NOMEFILE);
                    titoloList.Add(allegato.NOMEFILE);
                    tipoAllegatoList.Add(TipoAllegato_Type.A);
                    UploadFile(allegato.OGGETTO, allegato.NOMEFILE);
                }

                tipoAllegatoList[0] = TipoAllegato_Type.O;

                request = new inserisciAllegatiRequest
                {
                    Descrizione = descrizioneList.ToArray(),
                    NomeFile = nomeFileList.ToArray(),
                    Titolo = titoloList.ToArray(),
                    NumeroProtocollo = _numeroProtocollo,
                    PathFile = Path.Combine(_vert.NomeDirCondivisa, _numeroProtocollo),
                    TipoAllegato = tipoAllegatoList.ToArray(),
                    utente = _utente
                };
            }
            catch (Exception ex)
            {
                isAdapterError = true;
                _logs.WarnFormat("Errore generato durante la creazione della request per l'inserimento degli allegati, dettaglio errore: {0}", ex.Message);
            }

            return request;
        }

        private bool CreaCartella()
        {
            bool rVal = true;
            try
            {
                var NCredentials = new NetworkCredential(_vert.Username, _vert.Password, _vert.Dominio);

                using (new NetworkConnection(_vert.FtpPathAllegati, NCredentials))
                {
                    if (!Directory.Exists(Path.Combine(_vert.FtpPathAllegati, _numeroProtocollo)))
                    {
                        _logs.DebugFormat("Tentativo di creazione della cartella {0}", Path.Combine(_vert.FtpPathAllegati, _numeroProtocollo));
                        Directory.CreateDirectory(Path.Combine(_vert.FtpPathAllegati, _numeroProtocollo));
                        _logs.InfoFormat("Cartella {0} creata correttamente", Path.Combine(_vert.FtpPathAllegati, _numeroProtocollo));
                    }
                    else
                        _logs.DebugFormat("Cartella {0} già esistente", Path.Combine(_vert.FtpPathAllegati, _numeroProtocollo));
                }
            }
            catch (Exception ex)
            {
                rVal = false;
                isAdapterError = true;
                _logs.WarnFormat("Errore generato durante la creazione della cartella {0}, dettaglio errore: {1}", Path.Combine(_vert.FtpPathAllegati, _numeroProtocollo), ex.Message);
            }

            return rVal;
            
        }

        private void UploadFile(byte[] buffer, string nomeFile)
        {
            try
            {
                if (String.IsNullOrEmpty(_vert.FtpPathAllegati))
                    throw new Exception("Parametro NOMEDIR_CONDIVISA della verticalizzazione PROTOCOLLO_SIPRWEBTEST non valorizzato, non e' possibile inviare file al protocollo");

                var NCredentials = new NetworkCredential(_vert.Username, _vert.Password, _vert.Dominio);

                using (new NetworkConnection(Path.Combine(_vert.FtpPathAllegati, _numeroProtocollo), NCredentials))
                    File.WriteAllBytes(Path.Combine(_vert.FtpPathAllegati, _numeroProtocollo, nomeFile), buffer);

                _logs.InfoFormat("Scrittura del file {0} avvenuta con successo", nomeFile);
            }
            catch (Exception ex)
            {
                isAdapterError = true;
                _logs.WarnFormat("Errore generato durante la scrittura del file, dettaglio errore: {0}", ex.Message);
            }
        }
    }
}
