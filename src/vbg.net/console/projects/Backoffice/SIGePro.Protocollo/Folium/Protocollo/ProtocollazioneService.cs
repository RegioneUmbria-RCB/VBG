using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Folium.ServiceWrapper;
using Init.SIGePro.Protocollo.Folium.Allegati;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Folium.Protocollo
{
    public class ProtocollazioneService : IProtocollazione
    {
        /// <summary>
        /// E' la request del ws di Protocollazione
        /// </summary>
        RequestInfo _info;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        ProtocollazioneServiceWrapper _wrapper;

        public ProtocollazioneService(RequestInfo info, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            this._info = info;
            this._logs = logs;
            this._serializer = serializer;

            var credenzialiAutenticazione = new WSAuthentication
            {
                aoo = _info.Regole.Aoo,
                applicazione = _info.Regole.Applicazione,
                ente = _info.Regole.CodiceEnte,
                password = _info.Regole.Password,
                username = _info.Regole.Username
            };

            _wrapper = new ProtocollazioneServiceWrapper(_info.Regole.Url, _info.Regole.Binding, credenzialiAutenticazione, _logs, _serializer);
        }

        public DatiProtocolloRes Protocolla()
        {
            try
            {
                var disabilitaMail = _info.Regole.DisabilitaMailArrivo;

                if (_info.Flusso == ProtocolloConstants.COD_USCITA)
                {
                    disabilitaMail = false;
                }

                _logs.Info("COMPILAZIONE DEI MITTENTI / DESTINATARI");

                var mittentiDestinatari = _info.Anagrafiche.Select(x => new MittenteDestinatario
                {
                    citta = String.IsNullOrEmpty(x.Localita) ? x.Comune : x.Localita,
                    codiceMezzoSpedizione = x.MezzoInvio,
                    cognome = x.Cognome,
                    denominazione = x.Denominazione,
                    email = disabilitaMail ? "" : String.IsNullOrEmpty(x.Pec) ? x.Email : x.Pec,
                    indirizzo = x.Indirizzo,
                    invioPC = (_info.Regole.CodiceCC == x.ModalitaTrasmissione),
                    nome = x.Nome,
                    tipo = x.Tipo
                });

                _logs.Info("FINE COMPILAZIONE DEI MITTENTI / DESTINATARI");

                if (mittentiDestinatari.Count() == 0)
                {
                    throw new Exception("MITTENTE / DESTINATARIO NON PRESENTE");
                }

                _logs.Info("COMPILAZIONE DELLA REQUEST");

                var request = new DocumentoProtocollato
                {
                    mittentiDestinatari = mittentiDestinatari.ToArray(),
                    oggetto = _info.Oggetto,
                    registro = _info.Registro,
                    tipoProtocollo = _info.Flusso,
                    ufficioCompetente = _info.UfficioCompetente,
                    vociTitolario = _info.VociTitolario,
                    note = _info.Note
                };

                _logs.Info("FINE COMPILAZIONE DELLA REQUEST");

                if (_info.Regole.IsContenuto)
                {
                    var allegati = _info.Allegati;

                    _logs.InfoFormat("NUMERO ALLEGATI, {0}", allegati.Count());

                    if (allegati.Count() > 0)
                    {
                        _logs.Info("COMPILAZIONE DEL CONTENUTO");

                        var allegatoPrimario = allegati.First();
                        request.contenuto = allegatoPrimario.OGGETTO;
                        request.nomeFileContenuto = allegatoPrimario.NOMEFILE;

                        _logs.Info("FINE COMPILAZIONE DEL CONTENUTO");
                    }
                }

                var response = _wrapper.Protocolla(request);

                _logs.Info("COMPILAZIONE DELLA CLASSE DatiProtocolloRes DOPO LA CHIAMATA A PROTOCOLLA");
                var retVal = new DatiProtocolloRes
                {
                    AnnoProtocollo = response.dataProtocollo.Value.ToString("yyyy"),
                    DataProtocollo = response.dataProtocollo.Value.ToString("dd/MM/yyyy"),
                    IdProtocollo = response.id.HasValue ? response.id.Value.ToString() : "",
                    NumeroProtocollo = response.numeroProtocollo,
                    Warning = _logs.Warnings.WarningMessage
                };

                _logs.Info("COMPILAZIONE DELLA CLASSE DatiProtocolloRes DOPO LA CHIAMATA A PROTOCOLLA AVVENUTA CORRETTAMENTE");
                return retVal;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public void InserisciAllegati(long idProtocollo)
        {
            _logs.InfoFormat("INSERIMENTO ALLEGATI, ID PROTOCOLLO {0}", idProtocollo);
            if (_info.Allegati.Count() > 0)
            {
                var allegati = _info.Allegati.ToList();

                if (_info.Regole.IsContenuto && _info.Allegati.Count() > 1)
                {
                    allegati = _info.Allegati.Skip(1).ToList();
                }

                if (!_info.Regole.IsContenuto || _info.Allegati.Count() > 1)
                {
                    var allegatiAdapter = new AllegatiInputAdapter(allegati, idProtocollo);
                    var listaAllegati = allegatiAdapter.Adatta();
                    _wrapper.InsertAllegati(listaAllegati);
                }
            }
            _logs.InfoFormat("FINE INSERIMENTO ALLEGATI, ID PROTOCOLLO {0}", idProtocollo);
        }

        public void Assegna(long idProtocollo)
        {
            _logs.InfoFormat("INSERIMENTO ASSEGNAZIONE, ID PROTOCOLLO {0}", idProtocollo);

            if (!String.IsNullOrEmpty(_info.Smistamento) && _info.Flusso != ProtocolloConstants.COD_PARTENZA)
            {
                var request = new Assegnazione
                {
                    codiceAssegnazione = _info.Smistamento,
                    idProtocollo = idProtocollo,
                    ufficioAssegnatario = _info.Ruolo,
                    utenteAssegnatario = _info.Operatore
                };

                _wrapper.Assegna(request);
            }
            _logs.InfoFormat("FINE INSERIMENTO ASSEGNAZIONE, ID PROTOCOLLO {0}", idProtocollo);
        }

        public void InviaMail(long idProtocollo)
        {
            _logs.InfoFormat("INVIO MAIL, FLUSSO {0}, ID PROTOCOLLO {1}", _info.Flusso, idProtocollo);

            if (_info.Flusso == ProtocolloConstants.COD_USCITA)
            {
                _logs.Info("LA MAIL E' GESTITA INTERNAMENTE AL WEB SERVICE NELLA CHIAMATA PROTOCOLLA");
            }

            _logs.InfoFormat("FINE INVIO MAIL, FLUSSO {0}, ID PROTOCOLLO {1}", _info.Flusso, idProtocollo);
        }
    }
}
