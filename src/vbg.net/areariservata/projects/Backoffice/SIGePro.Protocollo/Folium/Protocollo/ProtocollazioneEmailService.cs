using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloFoliumEmailService;
using Init.SIGePro.Protocollo.Folium.ServiceWrapper;
using Init.SIGePro.Protocollo.Constants;
using System.IO;

namespace Init.SIGePro.Protocollo.Folium.Protocollo
{
    public class ProtocollazioneEmailService : IProtocollazione
    {
        /// <summary>
        /// E' la request del ws di ProtocollazioneEmail
        /// </summary>
        RequestInfo _info;
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        ProtocollazioneEmailServiceWrapper _wrapper;

        public ProtocollazioneEmailService(RequestInfo info, ProtocolloLogs logs, ProtocolloSerializer serializer)
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

            _wrapper = new ProtocollazioneEmailServiceWrapper(_info.Regole.UrlWsMail, _info.Regole.Binding, _logs, _serializer, credenzialiAutenticazione);

        }

        /// <summary>
        /// Apparentemente questo metodo è uguale a quello esposto nella classe ProtocollazioneService, in realtà i tipi MittenteDestinatario e DocumentoProtocollato 
        /// sono diversi, fanno riferimento infatti a web services differenti anche se apparentemente uguali.
        /// </summary>
        /// <returns></returns>
        public DatiProtocolloRes Protocolla()
        {
            try
            {
                var disabilitaMail = _info.Regole.DisabilitaMailArrivo;

                if (_info.Flusso == "U")
                {
                    disabilitaMail = false;
                }

                _logs.Info("COMPILAZIONE DEI MITTENTI / DESTINATARI");

                var mittentiDestinatari = _info.Anagrafiche.Select(x => new MittenteDestinatario
                {
                    citta = String.IsNullOrEmpty(x.Localita) ? x.Comune : x.Localita,
                    codiceMezzoSpedizione = x.MezzoInvio,
                    cognome = x.Cognome,
                    denominazione = _info.Flusso == "D" ? _info.UoDestinatarioInterno : x.Denominazione,
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
                    note = _info.Note,
                    timbro = true
                };

                _logs.Info("FINE COMPILAZIONE DELLA REQUEST");

                if (_info.Regole.IsContenuto)
                {
                    var allegati = _info.Allegati;

                    _logs.InfoFormat("NUMERO ALLEGATI, {0}", allegati.Count());

                    if (allegati.Count() > 0)
                    {
                        var allegatoPrincipale = allegati.First();

                        _logs.InfoFormat("NOME FILE PRINCIPALE: {0}", allegatoPrincipale.NOMEFILE);

                        if (_info.Regole.EstensioniAccettateFileContenuto != null)
                        {
                            _logs.Info("VALIDAZIONE DEL FILE PRINCIPALE CON LE ESTENSIONI");

                            var extPrinc = Path.GetExtension(allegatoPrincipale.NOMEFILE.ToLower());
                            var isContenutoOk = _info.Regole.EstensioniAccettateFileContenuto.Contains(extPrinc);
                            if (!isContenutoOk)
                            {
                                if (_info.Flusso == "U")
                                {
                                    throw new Exception($"L'ESTENSIONE {extPrinc} RELATIVA AL FILE {allegatoPrincipale.NOMEFILE} NON E' ACCETTATA COME DOCUMENTO PRINCIPALE");
                                }

                                _logs.Warn($"L'ESTENSIONE {extPrinc} RELATIVA AL FILE {allegatoPrincipale.NOMEFILE} NON E' ACCETTATA COME DOCUMENTO PRINCIPALE CHE SARA' SPOSTATO TRA GLI ALLEGATI AL PROTOCOLLO");
                                _info.Regole.IsContenuto = false;
                                request.isContenuto = false;
                            }
                        }

                        if (_info.Regole.IsContenuto)
                        {
                            _logs.Info("COMPILAZIONE DEL CONTENUTO");
                            var allegatoPrimario = allegati.First();
                            request.isContenuto = true;
                            request.contenuto = allegatoPrimario.OGGETTO;
                            request.nomeFileContenuto = allegatoPrimario.NOMEFILE;
                            _logs.Info("FINE COMPILAZIONE DEL CONTENUTO");
                        }
                    }
                }

                var response = _wrapper.Protocolla(request);

                _logs.Info("COMPILAZIONE DELLA CLASSE DatiProtocolloRes DOPO LA CHIAMATA A PROTOCOLLAMAIL");
                var retVal = new DatiProtocolloRes
                {
                    AnnoProtocollo = response.dataProtocollo.Value.ToString("yyyy"),
                    DataProtocollo = response.dataProtocollo.Value.ToString("dd/MM/yyyy"),
                    IdProtocollo = response.id.HasValue ? response.id.Value.ToString() : "",
                    NumeroProtocollo = response.numeroProtocollo,
                    Warning = _logs.Warnings.WarningMessage
                };

                _logs.Info("COMPILAZIONE DELLA CLASSE DatiProtocolloRes DOPO LA CHIAMATA A PROTOCOLLAMAIL AVVENUTA CORRETTAMENTE");
                return retVal;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public void Assegna(long idProtocollo)
        {
            var srv = new ProtocollazioneService(_info, _logs, _serializer);
            srv.Assegna(idProtocollo);
        }

        public void InserisciAllegati(long idProtocollo)
        {
            var srv = new ProtocollazioneService(_info, _logs, _serializer);
            srv.InserisciAllegati(idProtocollo);
        }

        public void InviaMail(long idProtocollo)
        {
            _logs.InfoFormat("INVIA MAIL PROTOCOLLO ID: {0}, FLUSSO: {1}", idProtocollo, _info.Flusso);
            if (_info.Flusso == ProtocolloConstants.COD_USCITA)
            {
                _logs.Info("CHIAMATA AL METODO InviaMail del wrapper ProtocollazioneEmailService");
                _wrapper.InviaEmail(idProtocollo);
                _logs.Info("FINE CHIAMATA AL METODO InviaMail del wrapper ProtocollazioneEmailService");
            }

            _logs.InfoFormat("FINE INVIA MAIL PROTOCOLLO ID: {0}, FLUSSO: {1}", idProtocollo, _info.Flusso);
        }
    }
}
