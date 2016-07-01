using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloApSystemsService;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ApSystems.DataSetApSystems;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.ApSystems;
using System.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ApSystems.Services;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_APSYSTEMS : ProtocolloBase
    {
        #region Membri Privati della classe

        private string _url;
        private string _username;
        private string _password;
        private bool _escludiClassifica = false;

        #endregion

        #region Creazione WebService

        private ServiceProtocolloSoapClient CreaWebService()
        {
            _protocolloLogs.Debug("Creazione del webservice APSYSTEM");
            try
            {
                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("ApSystemsHttpBinding");

                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_APSYSTEMS NON È STATO VALORIZZATO.");

                _protocolloLogs.Debug("Fine creazione del webservice APSYSTEM");

                return new ServiceProtocolloSoapClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE", ex);
            }
        }

        #endregion

        #region Protocollazione

        public override DatiProtocolloRes Protocollazione(Data.DatiProtocolloIn pProt)
        {
            try
            {
                SetParamFromVertApSystems();
                using (var ws = CreaWebService())
                {
                    try
                    {
                        var aut = new AuthenticationDetails
                        {
                            UserName = _username,
                            Password = _password
                        };

                        var dati = new DatiProtocolloApSystems(ws, aut, Operatore, _protocolloLogs, _protocolloSerializer);

                        //var dsd = ws.GetInputStructInsertProtocolloGenerale(aut);

                        _protocolloLogs.Debug("VALORIZZAZIONE DEI PARAMETRI");
                        DataSet dsDati = dati.GetDataSet4Insert(pProt, _escludiClassifica, DatiProtocollo.Db);
                        _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, dsDati);
                        
                        _protocolloLogs.InfoFormat("Chiamata a InsertProtocolloGenerale, username: {0}, password: {1}, dati: {2}, operatore: {3}", aut.UserName, aut.Password, dsDati.GetXml(), Operatore);

                        var response = ws.InsertProtocolloGenerale(aut, dsDati, Operatore);

                        _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                        var ds = new ApSystems.DataSetApSystems.Protocolli.InsertProtocollo.protocolli();
                        ds.Merge(response);

                        if (ds.ContieneErrori())
                            throw new Exception(String.Format("RESTITUZIONE DELL'ERRORE DA PARTE DEL WEB METHOD INSERTPROTOCOLLOGENERALE DAL WEB SERVICE, DESCRIZIONE ERRORE: {0}", ds.GetDescrizioneErrore()));
                        
                        _protocolloLogs.Info("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                        var datiProto = CreaDatiProtocollo(ds);
                        
                        pProt.Allegati.ForEach(x => InserisciAllegato(x, datiProto.IdProtocollo, ws, aut));

                        return datiProto;
                    }
                    catch (Exception)
                    {
                        ws.Abort();
                        throw;
                    }
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        private DatiProtocolloRes CreaDatiProtocollo(ApSystems.DataSetApSystems.Protocolli.InsertProtocollo.protocolli ds)
        {
            try
            {
                var datiProto = new DatiProtocolloRes();

                var dt = ds.protocollo;
                var r = dt[0];
                
                datiProto.IdProtocollo = r.codice;
                datiProto.NumeroProtocollo = r.numero_protocollo;
                datiProto.DataProtocollo = r.data_protocollo;
                datiProto.AnnoProtocollo = DateTime.Parse(r.data_protocollo).ToString("yyyy");

                if (!String.IsNullOrEmpty(_protocolloLogs.Warnings.WarningMessage))
                    datiProto.Warning = _protocolloLogs.Warnings.WarningMessage;

                _protocolloLogs.InfoFormat("Dati protocollo restituiti, codice: {0}, numero: {1}, anno: {2}, data: {3}", datiProto.IdProtocollo, datiProto.NumeroProtocollo, datiProto.AnnoProtocollo, datiProto.DataProtocollo);

                return datiProto;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DEI DATI DI PROTOCOLLO DOPO LA RISPOSTA DEL WEB SERVICE", ex);
            }

        }

        #endregion

        #region Allegati

        private void InserisciAllegato(ProtocolloAllegati all, string codiceProtocollo, ServiceProtocolloSoapClient ws, AuthenticationDetails aut)
        {
            try
            {
                _protocolloLogs.InfoFormat("Chiamata a web service InsertAllegatoProtocolloGenerale, inserimento allegato, username: {0}, password: {1}, codice protocollo: {2}, codice oggetto: {3}, nome file {4}, operatore: {5}", aut.UserName, aut.Password, codiceProtocollo, all.CODICEOGGETTO, all.NOMEFILE, Operatore);
                ws.InsertAllegatoProtocolloGenerale(aut, codiceProtocollo, all.OGGETTO, all.NOMEFILE, Operatore);
                _protocolloLogs.Info("Inserimento allegati avvenuto con successo");
            }
            catch (Exception ex)
            {
                _protocolloLogs.WarnFormat("Errore restituito dal web service durante l'inserimento dell'allegato codice: {0}, nome: {1} non inserito nel protocollo, errore restituito: {2}", all.CODICEOGGETTO, all.NOMEFILE, ex.Message);
            }
        }


        public override AllOut LeggiAllegato()
        {
            try
            {
                SetParamFromVertApSystems();
                using (var ws = CreaWebService())
                {
                    var aut = new AuthenticationDetails
                    {
                        UserName = _username,
                        Password = _password
                    };

                    var proto = LeggiProtocollo(IdProtocollo, AnnoProtocollo, NumProtocollo);
                    var all = proto.Allegati.Where(x => x.IDBase == IdAllegato).FirstOrDefault();

                    _protocolloLogs.InfoFormat("Chiamata web method GetAllegato, username: {0}, password: {1}, id allegato: {2}", aut.UserName, aut.Password, IdAllegato);
                    byte[] buffer = ws.GetAllegato(aut, IdAllegato, false);
                    _protocolloLogs.Info("Chiamata a web method GetAllegato avvunuta con successo");

                    var rAllOut = new AllOut
                    {
                        IDBase = IdAllegato,
                        Image = buffer,
                        Serial = all.Serial
                    };

                    return rAllOut;
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DELL'ALLEGATO", ex);
            }

        }

        #endregion

        #region Leggi Protocollo

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {

            try
            {
                SetParamFromVertApSystems();

                using (var ws = CreaWebService())
                {
                    try
                    {
                        DatiProtocolloLetto result = null;
                        var aut = new AuthenticationDetails();
                        aut.UserName = _username;
                        aut.Password = _password;
                        _protocolloLogs.InfoFormat("Chiamata a web method GetProtocolloGenerale (leggi protocollo), username: {0}, password: {1}, id protocollo: {2}, anno protocollo: {3}, numero protocollo: {4}", aut.UserName, aut.Password, idProtocollo, annoProtocollo, numeroProtocollo);
                        var dsRes = ws.GetProtocolloGenerale(aut,
                                                idProtocollo,
                                                annoProtocollo,
                                                numeroProtocollo,
                                                numeroProtocollo,
                                                String.Empty,
                                                String.Empty,
                                                String.Empty,
                                                String.Empty,
                                                String.Empty,
                                                String.Empty,
                                                String.Empty,
                                                String.Empty,
                                                String.Empty,
                                                String.Empty);


                        var ds = new ApSystems.DataSetApSystems.Protocolli.GetProtocollo.protocolli();
                        ds.Merge(dsRes);

                        if (ds.ContieneErrori())
                            throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD GetProtocolloGenerale() DEL WEB SERVICE, ID PROTOCOLLO: {0}, NUMERO PROTOCOLLO: {1}, ANNO PROTOCOLLO: {2}, DETTAGLIO ERRORE: {3}", idProtocollo, numeroProtocollo, annoProtocollo, ds.GetDescrizioneErrore()));

                        var proto = ds.protocollo;

                        if (proto.Rows.Count == 1)
                        {
                            _protocolloLogs.Info("Chiamata a web method GetProtocolloGenerale (leggi protocollo) avvenuta con successo");
                            _protocolloLogs.DebugFormat("Dati restituiti dal web method GetProtocolloGenerale (leggi protocollo): {0}", ds.GetXml());

                            result = CreaDatiProtocolloLetto(ds);
                        }
                        else
                        {
                            if (proto.Rows.Count == 0)
                                throw new Exception(String.Format("LA RICERCA DEL PROTOCOLLO NUMERO: {0}, ANNO: {1}, ID: {2} NON HA PRODOTTO ALCUN RISULTATO", numeroProtocollo, annoProtocollo, idProtocollo));

                            if (proto.Rows.Count > 1)
                                throw new Exception(String.Format("LA RICERCA DEL PROTOCOLLO NUMERO: {0}, ANNO: {1}, ID: {2} HA PRODOTTO PIU' DI UN RISULTATO", numeroProtocollo, annoProtocollo, idProtocollo));
                        }

                        return result;
                    }
                    catch (Exception)
                    {
                        ws.Abort();
                        throw;
                    }
                }
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE DURANTE LA LETTURA DEL PROTOCOLLO.", ex);
            }
        }

        private DatiProtocolloLetto CreaDatiProtocolloLetto(ApSystems.DataSetApSystems.Protocolli.GetProtocollo.protocolli ds)
        {
            try
            {

                var result = new DatiProtocolloLetto();
                var r = ds.protocollo[0];
                
                result.IdProtocollo = r.codice;
                result.NumeroProtocollo = r.numero;
                result.DataProtocollo = DateTime.Parse(r.data).ToString("dd/MM/yyyy");
                result.AnnoProtocollo = DateTime.Parse(r.data).ToString("yyyy");
                result.Oggetto = r.oggetto;
                result.Classifica_Descrizione = r.classificazione;

                result.Origine = r.tipologia;

                var dtDest = ds.destinatario;
                var dtMitt = ds.mittente;
                var dtAll = ds.allegato;
                
                var listMittDest = new List<MittDestOut>();

                if (r.tipologia == "A")
                {
                    foreach (var rd in dtMitt.Rows)
                    {
                        listMittDest.Add(new MittDestOut
                        {
                            IdSoggetto = ((ApSystems.DataSetApSystems.Protocolli.GetProtocollo.protocolli.mittenteRow)rd).codice,
                            CognomeNome = ((ApSystems.DataSetApSystems.Protocolli.GetProtocollo.protocolli.mittenteRow)rd).descrizione
                        });
                    }

                    result.InCaricoA = dtDest[0].codice;
                    result.InCaricoA_Descrizione = dtDest[0].descrizione;

                    result.MittentiDestinatari = listMittDest.ToArray();
                }
                else if (r.tipologia == "P")
                {
                    result.MittenteInterno = dtMitt[0].codice;
                    result.MittenteInterno_Descrizione = dtMitt[0].descrizione;

                    foreach (var rd in dtDest.Rows)
                    {
                        listMittDest.Add(new MittDestOut
                        {
                            IdSoggetto = ((ApSystems.DataSetApSystems.Protocolli.GetProtocollo.protocolli.destinatarioRow)rd).codice,
                            CognomeNome = ((ApSystems.DataSetApSystems.Protocolli.GetProtocollo.protocolli.destinatarioRow)rd).descrizione
                        });
                    }

                    result.MittentiDestinatari = listMittDest.ToArray();
                }
                else if (r.tipologia == "I")
                {
                    result.InCaricoA = dtDest[0].codice;
                    result.InCaricoA_Descrizione = dtDest[0].descrizione;
                    result.MittenteInterno = dtMitt[0].codice;
                    result.MittenteInterno_Descrizione = dtMitt[0].descrizione;
                }
                else
                    throw new Exception(String.Format("IL SISTEMA NON SUPPORTA IL FLUSSO -{0}- RESTITUITO DAL SISTEMA DI PROTOCOLLAZIONE", r.tipologia));

                
                var listAll = new List<AllOut>();

                foreach (var all in dtAll)
                {
                    listAll.Add(new AllOut
                    {
                        IDBase = all.codice,
                        Serial = all.nome
                    });
                }

                result.Allegati = listAll.ToArray();

                return result;
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE GENERATO DURANTE LA MAPPATURA DEI DATI LETTI DAL WEB SERVICE DI PROTOCOLLO", ex);
            }
        }

        #endregion

        #region Fascicolazione

        //La fascicolazione al momento non può essere utilizzata in quanto il we service chiede il fascicolo durante la protocollazione mentre il nostro applicativo chiede 
        //prima di fare il protocollo e poi di fascicolare con due chiamate al web service differenti.
        
        #endregion

        #region Verticalizzazioni

        private void SetParamFromVertApSystems()
        {
            try
            {
                var vert = new VerticalizzazioneProtocolloApsystems(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                if (vert.Attiva)
                {
                    _protocolloLogs.DebugFormat("Valori parametri verticalizzazioni: url: {0}, username: {1}, password: {2}, escludi classifica: {3}",
                                                vert.Url,
                                                vert.Username,
                                                vert.Password,
                                                vert.EscludiClassifica);

                    _url = vert.Url;
                    _username = vert.Username;
                    _password = vert.Password;
                    _escludiClassifica = vert.EscludiClassifica == "1";

                    if (String.IsNullOrEmpty(_url))
                        throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_APSYSTEMS NON E' VALORIZZATO");

                    if (String.IsNullOrEmpty(_username))
                        throw new Exception("IL PARAMETRO USERNAME DELLA VERTICALIZZAZIONE PROTOCOLLO_APSYSTEMS NON E' VALORIZZATO");

                    if (String.IsNullOrEmpty(_password))
                        throw new Exception("IL PARAMETRO PASSWORD DELLA VERTICALIZZAZIONE PROTOCOLLO_APSYSTEMS NON E' VALORIZZATO");
                }
                else
                    throw new Exception("LA VERTICALIZZAZIONE PROTOCOLLO_APSYSTEMS NON È ATTIVA.");

                _protocolloLogs.Debug("Fine recupero valori da verticalizzazioni");

            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DEI DATI DALLA VERTICALIZZAZIONE PROTOCOLLO_APSYSTEMS", ex);
            }
        }

        #endregion
    }
}
