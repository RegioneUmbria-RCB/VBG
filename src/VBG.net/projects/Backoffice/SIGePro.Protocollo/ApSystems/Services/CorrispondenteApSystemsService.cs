using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloApSystemsService;
using log4net;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.ApSystems.DataSetApSystems;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.ApSystems.Services
{
    public class CorrispondenteApSystemsService : BaseProtocolloApSystems
    {
        //ILog _log = LogManager.GetLogger(typeof(CorrispondenteApSystemsService));
        ProtocolloLogs _protocolloLog;
        ProtocolloSerializer _protocolloSerializer;

        public CorrispondenteApSystemsService(ServiceProtocolloSoapClient ws, AuthenticationDetails authWs, ProtocolloLogs protocolloLogs, ProtocolloSerializer protocolloSerializer)
        {
            _ws = ws;
            _authWs = authWs;
            _protocolloLog = protocolloLogs;
            _protocolloSerializer = protocolloSerializer;
        }

        #region ICorrispondenteApSystemsService Members

        public DataSetApSystems.Corrispondenti.GetCorrispondente.corrispondenti.corrispondenteRow GetCorrispondenteByCodice(string codiceCorrispondente)
        {
            try
            {
                if (String.IsNullOrEmpty(codiceCorrispondente))
                    throw new Exception("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI, CODICE CORRISPONDENTE NON VALORIZZATO");

                var response = _ws.GetCorrispondente(_authWs, codiceCorrispondente, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty);
                var ds = new DataSetApSystems.Corrispondenti.GetCorrispondente.corrispondenti();
                ds.Merge(response);

                if (ds.ContieneErrori())
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD GetCorrispondente() DEL WEB SERVICE DURANTE LA RICHIESTA PER CODICE CORRISPONDENTE {0}, DETTAGLIO ERRORE: {1}", codiceCorrispondente, ds.GetDescrizioneErrore()));

                if (ds.corrispondente.Rows.Count > 1)
                    throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI, TROVATO PIU' DI UN RISULTATO, CODICE CORRISPONDENTE: {0}", codiceCorrispondente));

                if (ds.corrispondente.Rows.Count == 0)
                    throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI, NESSUN CORRISPONDENTE TROVATO, CODICE CORRISPONDENTE: {0}", codiceCorrispondente));


                return ds.corrispondente[0];

            }
            catch (Exception)
            {
                throw;
            }
        }
        
        
        public DataSetApSystems.Corrispondenti.GetCorrispondente.corrispondenti.corrispondenteRow GetCorrispondenteByCodiceUfficio(string codiceUfficio)
        {
            try
            {
                
                if (String.IsNullOrEmpty(codiceUfficio))
                    throw new Exception("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI, CODICE CORRISPONDENTE NON VALORIZZATO");

                var response = _ws.GetCorrispondente(_authWs, codiceUfficio, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty);
                var ds = new DataSetApSystems.Corrispondenti.GetCorrispondente.corrispondenti();
                ds.Merge(response);

                if (ds.ContieneErrori())
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD GetCorrispondente() DEL WEB SERVICE DURANTE LA RICHIESTA PER CODICE UFFICIO {0}, DETTAGLIO ERRORE: {1}", codiceUfficio, ds.GetDescrizioneErrore()));

                if (ds.corrispondente.Rows.Count > 1)
                    throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI, TROVATO PIU' DI UN RISULTATO, CODICE UFFICIO: {0}", codiceUfficio));

                if (ds.corrispondente.Rows.Count == 0)
                    throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI, NESSUN CORRISPONDENTE TROVATO, CODICE UFFICIO: {0}", codiceUfficio));


                return ds.corrispondente[0];

            }
            catch (Exception)
            {
                throw;
            }
        }

        public DataSetApSystems.Corrispondenti.GetCorrispondente.corrispondenti.corrispondenteDataTable GetCorrispondenteByCodiceFiscale(string codiceFiscale)
        {
            try
            {
                if (String.IsNullOrEmpty(codiceFiscale))
                    throw new Exception("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI, CODICE FISCALE (O PARTITA IVA) DEL CORRISPONDENTE NON VALORIZZATO");


                var response = _ws.GetCorrispondente(_authWs, String.Empty, codiceFiscale, String.Empty, String.Empty, String.Empty, String.Empty, String.Empty);
                var ds = new DataSetApSystems.Corrispondenti.GetCorrispondente.corrispondenti();
                ds.Merge(response);

                if (ds.ContieneErrori())
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD GetCorrispondente() DEL WEB SERVICE DURANTE LA RICHIESTA PER CODICE FISCALE {0}, DETTAGLIO ERRORE: {1}", codiceFiscale, ds.GetDescrizioneErrore()));

                if (ds.corrispondente.Rows.Count > 1)
                    throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI, TROVATO PIU' DI UN RISULTATO, CODICE FISCALE / PARTITA IVA: {0}", codiceFiscale));

                return ds.corrispondente;
            }
            catch (Exception)
            {
                throw;
            }
        }

        public DataSetApSystems.Comuni.GetComune.comuni.comuneRow GetComuneByCodiceIstat(string codiceIstat)
        {
            try
            {
                if (String.IsNullOrEmpty(codiceIstat))
                {
                    _protocolloLog.Warn("Warning generato durante il recupero delle informazioni sul comune, codice istat non valorizzato, le funzionalita' andranno comunque avanti senza questo dato");
                    return null;
                }
                    
                //throw new Exception("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI SUL COMUNE, CODICE ISTAT NON VALORIZZATO");

                string codiceIstatNumerico = Convert.ToInt32(codiceIstat).ToString();

                var response = _ws.GetComune(_authWs, String.Empty, codiceIstatNumerico, String.Empty, String.Empty, String.Empty);
                var ds = new DataSetApSystems.Comuni.GetComune.comuni();
                ds.Merge(response);

                if (ds.ContieneErrori())
                {
                    _protocolloLog.WarnFormat(String.Format("Warning restituito dal web method getcomune() del web service durante la richiesta per codice istat {0}, dettaglio errore: {1}, le funzionalità andranno comunque avanti senza questo dato", codiceIstat, ds.GetDescrizioneErrore()));
                    return null;
                }

                 //throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD GetComune() DEL WEB SERVICE DURANTE LA RICHIESTA PER CODICE ISTAT {0}, DETTAGLIO ERRORE: {1}", codiceIstat, ds.GetDescrizioneErrore()));

                if (ds.comune.Rows.Count > 1)
                {
                    _protocolloLog.WarnFormat(String.Format("Warning generato durante il recupero delle informazioni sul comune, codice istat: {0}, trovato piu' di un risultato, le funzionalità andranno comunque avanti senza questo dato", codiceIstat));
                    return null;
                }
                    
                    //throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI SUL COMUNE, CODICE ISTAT: {0}, TROVATO PIU' DI UN RISULTATO", codiceIstat));

                if (ds.comune.Rows.Count == 0)
                {
                    _protocolloLog.WarnFormat(String.Format("Warning generato durante il recupero delle informazioni sul comune, codice istat: {0}, comune non trovato, le funzionalità andranno comunque avanti senza questo dato", codiceIstat));
                    return null;
                }
                    
                    //throw new Exception(String.Format("ERRORE GENERATO DURANTE IL RECUPERO DELLE INFORMAZIONI SUL COMUNE, CODICE ISTAT: {0}, COMUNE NON TROVATO", codiceIstat));
                return ds.comune[0];
            }
            catch (Exception)
            {
                throw;
            }
        }

        public DataSetApSystems.Corrispondenti.InsertCorrispondente.corrispondenti.corrispondenteRow InsertCorrispondenteAnagrafe(SIGePro.Data.ProtocolloAnagrafe anag, string userName)
        {
            try
            {
                var codFiscPartIva = String.IsNullOrEmpty(anag.CODICEFISCALE) ? anag.PARTITAIVA : anag.CODICEFISCALE;
                var nominativo = !String.IsNullOrEmpty(anag.NOME) ? String.Concat(anag.NOMINATIVO, " ", anag.NOME) : anag.NOMINATIVO;

                if (String.IsNullOrEmpty(codFiscPartIva))
                    throw new Exception(String.Format("IL CODICE FISCALE O LA PARTITA IVA NON SONO STATI VALORIZZATI, CODICE ANAGRAFE: {0}, NOMINATIVO: {1}", anag.CODICEANAGRAFE, anag.NOMINATIVO));


                string codiceComune = String.Empty;
                if(!String.IsNullOrEmpty(anag.CodiceIstatComRes))
                {
                    var com = GetComuneByCodiceIstat(anag.CodiceIstatComRes);
                    
                    if(com != null)
                        codiceComune = com.codice;
                }
                _protocolloLog.DebugFormat("Chiamata al web method InsertCorrispondente del web service, inserimento anagrafica codice: {0}, nominativo: {1}, codice fiscale / partita iva: {2}", anag.CODICEANAGRAFE, nominativo, codFiscPartIva);
                var response = _ws.InsertCorrispondente(_authWs, codFiscPartIva, nominativo, anag.INDIRIZZO, anag.CAP, codiceComune, anag.EMAIL, anag.TELEFONO, anag.FAX, userName);
                _protocolloLog.DebugFormat("Risposta dal web method InsertCorrispondente del web service, inserita anagrafica codice (VBG): {0}, nominativo: {1}, codice fiscale / partita iva: {2}", anag.CODICEANAGRAFE, nominativo, codFiscPartIva);

                var ds = new DataSetApSystems.Corrispondenti.InsertCorrispondente.corrispondenti();

                ds.Merge(response);

                if (ds.ContieneErroreCorrispondente())
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD InsertCorrispondente() DEL WEB SERVICE DURANTE L'INSERIMENTO DELL'ANAGRAFICA CODICE: {0}, DESCRIZIONE: {1}, DETTAGLIO ERRORE: {2}", anag.CODICEANAGRAFE, nominativo, ds.GetDescrizioneErroreCorrispondente()));

                return ds.corrispondente[0];
            }
            catch (Exception)
            {
                throw;
            }

        }

        public DataSetApSystems.Corrispondenti.InsertCorrispondente.corrispondenti.corrispondenteRow InsertCorrispondenteAmministrazione(SIGePro.Data.Amministrazioni amm, string userName, string codiceComune)
        {
            try
            {
                if (!String.IsNullOrEmpty(amm.PROT_UO))
                    throw new Exception(String.Format("NON E' POSSIBILE INSERIRE UN'AMMINISTRAZIONE CHE PRESENTA L'UO VALORIZZATA, CODICE AMMINISTRAZIONE: {0}, AMMINISTRAZIONE: {1}, UO: {2}", amm.CODICEAMMINISTRAZIONE, amm.AMMINISTRAZIONE, amm.PROT_UO));

                var response = _ws.InsertCorrispondente(_authWs, amm.PARTITAIVA, amm.AMMINISTRAZIONE, amm.INDIRIZZO, amm.CAP, codiceComune, amm.EMAIL, amm.TELEFONO1, amm.TELEFONO2, userName);

                _protocolloLog.DebugFormat("Chiamata al web method InsertCorrispondente del web service, inserimento amministrazione codice: {0}, amministrazione: {1}, partita iva: {2}", amm.CODICEAMMINISTRAZIONE, amm.AMMINISTRAZIONE, amm.PARTITAIVA);
                var ds = new DataSetApSystems.Corrispondenti.InsertCorrispondente.corrispondenti();
                _protocolloLog.DebugFormat("Risposta dal web method InsertCorrispondente del web service, inserimento amministrazione codice (VBG): {0}, amministrazione: {1}, partita iva: {2}", amm.CODICEAMMINISTRAZIONE, amm.AMMINISTRAZIONE, amm.PARTITAIVA);

                ds.Merge(response);

                if (ds.ContieneErrori())
                    throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB METHOD InsertCorrispondente() DEL WEB SERVICE DURANTE L'INSERIMENTO DELL'AMMINISTRAZIONE CODICE: {0}, DESCRIZIONE: {1} DETTAGLIO ERRORE: {2}", amm.CODICEAMMINISTRAZIONE, amm.AMMINISTRAZIONE ,ds.GetDescrizioneErrore()));

                return ds.corrispondente[0];
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE L'INSERIMENTO DELL'AMMINISTRAZIONE NEI CORRISPONDENTI, CODICE AMMINISTRAZIONE: {0}, AMMINISTRAZIONE: {1}", amm.CODICEAMMINISTRAZIONE, amm.AMMINISTRAZIONE), ex);
            }
        }

        #endregion
    }
}
