using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ApSystems.DataSetApSystems;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloApSystemsService;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Manager;
using log4net;
using Init.SIGePro.Protocollo.ApSystems.DataSetApSystems.Corrispondenti.GetCorrispondente;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.ApSystems.Services
{
    internal class DatiProtocolloApSystems : BaseProtocolloApSystems
    {
        DatiProtocolloIn _dataInput;
        bool _escludiClassifica = false;
        DataSetApSystems.Protocolli.DatiProtocollo.protocolli _ds = null;
        string _operatore;
        DataBase _db;
        ProtocolloLogs _protocolloLog;
        ProtocolloSerializer _protocolloSerializer;

        public DatiProtocolloApSystems(ServiceProtocolloSoapClient ws, AuthenticationDetails authWs, string operatore, ProtocolloLogs protocolloLog, ProtocolloSerializer protocolloSerializer)
        {
            _ws = ws;
            _authWs = authWs;
            _operatore = operatore;
            _protocolloLog = protocolloLog;
            _protocolloSerializer = protocolloSerializer;
        }

        internal DataSetApSystems.Protocolli.DatiProtocollo.protocolli GetDataSet4Insert(DatiProtocolloIn dataInput, bool escludiClassifica, DataBase db)
        {
            _dataInput = dataInput;
            _escludiClassifica = escludiClassifica;
            _db = db;

            _ds = new DataSetApSystems.Protocolli.DatiProtocollo.protocolli();
            var dt = _ds.protocollo;

            InsertDatiGenerici();

            if (_dataInput.Flusso == "A")
            {
                InsertAmministrazioniMittentiArrivo();
                InsertAnagrafeMittentiArrivo();
                InsertAmministrazioniDestinatariArrivo();
            }
            else if (_dataInput.Flusso == "P")
            {
                InsertAmministrazioniDestinatariPartenza();
                InsertAmministrazioniMittentiPartenza();
                InsertAnagrafeDestinatariPartenza();
            }
            else if (_dataInput.Flusso == "I")
            {
                InsertAmministrazioneMittenteInterno();
                InsertAmministrazioneDestinatarioInterno();
            }
            else
                throw new Exception(String.Format("IL FLUSSO {0} NON ESISTE", _dataInput));

            return _ds;
        }

        private void InsertDatiGenerici()
        {
            _protocolloLog.Debug("Valorizzazione dei dati generici");

            var r = _ds.protocollo.NewprotocolloRow();

            r.tipologia = _dataInput.Flusso;
            r.oggetto = _dataInput.Oggetto;
            r.annotazione = _dataInput.TipoDocumento;

            if (!_escludiClassifica)
                r.classificazione = _dataInput.Classifica;

            _ds.protocollo.AddprotocolloRow(r);
            _protocolloLog.Debug("Fine valorizzazione dei dati generici");
        }

        #region Arrivo

        private void InsertAmministrazioniMittentiArrivo()
        {
            _protocolloLog.Debug("Valorizzazione dei dati riguardanti le amministrazioni dei mittenti in Arrivo");
            try
            {
                var list = _dataInput.Mittenti.Amministrazione;
                if (list.Count > 0)
                {
                    foreach (var a in list)
                    {
                        if (!String.IsNullOrEmpty(a.PROT_UO))
                            throw new Exception(String.Format("NON E' POSSIBILE SELEZIONARE UN'AMMINISTRAZIONE INTERNA (UO VALORIZZATA) COME MITTENTE DI UN PROTOCOLLO IN ARRIVO, AMMINISTRAZIONE: {0} CODICE: {1}, UO: {2}", a.AMMINISTRAZIONE, a.CODICEAMMINISTRAZIONE, a.PROT_UO));

                        if (String.IsNullOrEmpty(a.PARTITAIVA))
                            throw new Exception(String.Format("LA PARTITA IVA DELL'AMMINISTRAZIONE {0} NON E' STATA VALORIZZATA", a.AMMINISTRAZIONE));

                        var r = _ds.mittente.NewmittenteRow();

                        var corr = new CorrispondenteApSystemsService(_ws, _authWs, _protocolloLog, _protocolloSerializer);
                        var dt = corr.GetCorrispondenteByCodiceFiscale(a.PARTITAIVA);
                        
                        string codiceIstat = GetCodiceIstatComune(a.CITTA);
                        string codiceComuneWs = GetCodiceComuneWs(codiceIstat);

                        if (dt.Rows.Count == 0)
                        {
                            var rcInsert = corr.InsertCorrispondenteAmministrazione(a, _operatore, codiceComuneWs);
                            var rc = corr.GetCorrispondenteByCodice(rcInsert.codice);

                            r.codice = rc.codice;
                            r.descrizione = rc.descrizione;
                            r.indirizzo = rc.indirizzo;
                            r.cap = rc.cap;
                            r.codice_comune = rc.codice_comune;
                            r.descrizione_comune = rc.descrizione_comune;
                        }
                        else
                        {
                            var rc = dt[0];
                            r.codice = rc.codice;
                            r.descrizione = rc.descrizione;
                            r.indirizzo = rc.indirizzo;
                            r.cap = rc.cap;
                            r.codice_comune = rc.codice_comune;
                            r.descrizione_comune = rc.descrizione_comune;
                        }

                        _ds.mittente.AddmittenteRow(r);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA COMPILAZIONE DEI DATI DEI MITTENTI IN ARRIVO RIGUARDANTE LE AMMINISTRAZIONI", ex);
            }
            _protocolloLog.Debug("Fine valorizzazione dei dati riguardanti le amministrazioni dei mittenti in Arrivo");
        }

        private void InsertAnagrafeMittentiArrivo()
        {
            _protocolloLog.Debug("Valorizzazione dei dati riguardanti le anagrafiche dei mittenti in Arrivo");
            try
            {
                var list = _dataInput.Mittenti.Anagrafe;
                if (list.Count > 0)
                {
                    foreach (var a in list)
                    {
                        var codFiscPartIva = String.IsNullOrEmpty(a.CODICEFISCALE) ? a.PARTITAIVA : a.CODICEFISCALE;
                        
                        var corr = new CorrispondenteApSystemsService(_ws, _authWs, _protocolloLog, _protocolloSerializer);
                        var dt = corr.GetCorrispondenteByCodiceFiscale(codFiscPartIva);

                        //DataSetApSystems.Corrispondenti.GetCorrispondente.corrispondenti.corrispondenteRow rc = null;

                        var r = _ds.mittente.NewmittenteRow();

                        if (dt.Rows.Count == 0)
                        {
                            var rcInsert = corr.InsertCorrispondenteAnagrafe(a, _operatore);
                            var rc = corr.GetCorrispondenteByCodice(rcInsert.codice);

                            r.codice = rc.codice;
                            r.descrizione = rc.descrizione;
                            r.indirizzo = rc.indirizzo;
                            r.cap = rc.cap;
                            r.codice_comune = rc.codice_comune;
                            r.descrizione_comune = rc.descrizione_comune;
                            
                        }
                        else
                        {
                            var rc = dt[0];
                            r.codice = rc.codice;
                            r.descrizione = rc.descrizione;
                            r.indirizzo = rc.indirizzo;
                            r.cap = rc.cap;
                            r.codice_comune = rc.codice_comune;
                            r.descrizione_comune = rc.descrizione_comune;                            
                        }

                        _ds.mittente.AddmittenteRow(r);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("SI E' VERIFICATO UN ERRORE DURANTE LA COMPILAZIONE DEI DATI DEI MITTENTI IN ARRIVO", ex);
            }
            _protocolloLog.Debug("Fine valorizzazione dei dati riguardanti le anagrafiche dei mittenti in Arrivo");
        }

        private void InsertAmministrazioniDestinatariArrivo()
        {
            _protocolloLog.Debug("Valorizzazione dei dati riguardanti le amministrazioni dei destinatari in Arrivo");
            try
            {
                if (_dataInput.Destinatari.Amministrazione.Count == 0)
                    throw new Exception("AMMINISTRAZIONE NON VALORIZZATA");

                if (_dataInput.Destinatari.Amministrazione.Count > 1)
                    throw new Exception("SONO PRESENTI PIU' AMMINISTRAZIONI, DEVE ESSERNE PRESENTE SOLAMENTE UNA");

                var amm = _dataInput.Destinatari.Amministrazione[0];

                if (String.IsNullOrEmpty(amm.PROT_UO))
                    throw new Exception(String.Format("L'AMMINISTRAZIONE: {0}, CODICE: {1} NON HA L'UO VALORIZZATA", amm.AMMINISTRAZIONE, amm.CODICEAMMINISTRAZIONE));

                var corr = new CorrispondenteApSystemsService(_ws, _authWs, _protocolloLog, _protocolloSerializer);
                var rc = corr.GetCorrispondenteByCodiceUfficio(amm.PROT_UO);

                var r = _ds.destinatario.NewdestinatarioRow();

                r.codice = rc.codice;
                r.descrizione = rc.descrizione;
                r.indirizzo = rc.indirizzo;
                r.cap = rc.cap;
                r.codice_comune = rc.codice_comune;
                r.descrizione_comune = rc.descrizione_comune;

                _ds.destinatario.AdddestinatarioRow(r);

            }
            catch (Exception ex)
            {
                throw new Exception("SI E' VERIFICATO UN ERRORE DURANTE LA COMPILAZIONE DEI DATI DEL DESTINATARIO (AMMINISTRAZIONE) IN ARRIVO", ex);
            }
            _protocolloLog.Debug("Fine valorizzazione dei dati riguardanti le amministrazioni dei destinatari in Arrivo");
        }

        #endregion

        #region Partenza

        private void InsertAmministrazioniDestinatariPartenza()
        {
            _protocolloLog.Debug("Valorizzazione dei dati riguardanti le amministrazioni dei destinatari in Partenza");
            try
            {
                var list = _dataInput.Destinatari.Amministrazione;
                if (list.Count > 0)
                {
                    foreach (var a in list)
                    {
                        //if (!String.IsNullOrEmpty(a.PROT_UO))
                        //    throw new Exception("NON E' POSSIBILE SELEZIONARE UN'AMMINISTRAZIONE INTERNA (UO VALORIZZATA) COME DESTINATARIO DI UN PROTOCOLLO IN PARTENZA");

                        if (String.IsNullOrEmpty(a.PROT_UO) && String.IsNullOrEmpty(a.PARTITAIVA))
                            throw new Exception(String.Format("LA PARTITA IVA DELL'AMMINISTRAZIONE {0} NON E' STATA VALORIZZATA", a.AMMINISTRAZIONE));

                        var r = _ds.destinatario.NewdestinatarioRow();

                        var corr = new CorrispondenteApSystemsService(_ws, _authWs, _protocolloLog, _protocolloSerializer);
                        
                        corrispondenti.corrispondenteDataTable dt = null;

                        if (!String.IsNullOrEmpty(a.PROT_UO))
                        {
                            dt = corr.GetCorrispondenteByCodiceUfficio(a.PROT_UO).Table as corrispondenti.corrispondenteDataTable;
                            if (dt.Rows.Count == 0)
                                throw new Exception(String.Format("L'AMMINISTRAZIONE: {0}, CODICE: {1}, CON UO VALORIZZATA A: {2}, NON E' STATA TROVATA NELL'ARCHIVIO DI PROTOCOLLO, NON E' POSSIBILE INSERIRE NELL'ANAGRAFICA DEL SISTEMA DI PROTOCOLLAZIONE UN'AMMINISTRAZIONE CON UO VALORIZZATA", a.AMMINISTRAZIONE, a.CODICEAMMINISTRAZIONE, a.PROT_UO));
                        }
                        else
                            dt = corr.GetCorrispondenteByCodiceFiscale(a.PARTITAIVA);

                        string codiceIstat = GetCodiceIstatComune(a.CITTA);
                        string codiceComuneWs = GetCodiceComuneWs(codiceIstat);

                        if (dt.Rows.Count == 0)
                        {
                            var rcInsert = corr.InsertCorrispondenteAmministrazione(a, _operatore, codiceIstat);
                            var rc = corr.GetCorrispondenteByCodice(rcInsert.codice);

                            r.codice = rc.codice;
                            r.descrizione = rc.descrizione;
                            r.indirizzo = rc.indirizzo;
                            r.cap = rc.cap;
                            r.codice_comune = rc.codice_comune;
                            r.descrizione_comune = rc.descrizione_comune;
                        }
                        else
                        {
                            var rc = dt[0];
                            r.codice = rc.codice;
                            r.descrizione = rc.descrizione;
                            r.indirizzo = rc.indirizzo;
                            r.cap = rc.cap;
                            r.codice_comune = rc.codice_comune;
                            r.descrizione_comune = rc.descrizione_comune;
                        }

                        _ds.destinatario.AdddestinatarioRow(r);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("ERRORE DURANTE LA COMPILAZIONE DEI DATI DEI DESTINATARI IN PARTENZA RIGUARDANTE LE AMMINISTRAZIONI", ex);
            }
            _protocolloLog.Debug("Fine valorizzazione dei dati riguardanti le amministrazioni dei destinatari in Partenza");
        }

        private void InsertAnagrafeDestinatariPartenza()
        {
            _protocolloLog.Debug("Valorizzazione dei dati riguardanti le anagrafiche dei destinatari in Partenza");
            try
            {
                var list = _dataInput.Destinatari.Anagrafe;
                if (list.Count > 0)
                {
                    foreach (var a in list)
                    {
                        var codFiscPartIva = String.IsNullOrEmpty(a.CODICEFISCALE) ? a.PARTITAIVA : a.CODICEFISCALE;

                        var corr = new CorrispondenteApSystemsService(_ws, _authWs, _protocolloLog, _protocolloSerializer);
                        var dt = corr.GetCorrispondenteByCodiceFiscale(codFiscPartIva);

                        var r = _ds.destinatario.NewdestinatarioRow();

                        if (dt.Rows.Count == 0)
                        {
                            var rcInsert = corr.InsertCorrispondenteAnagrafe(a, _operatore);
                            var rc = corr.GetCorrispondenteByCodice(rcInsert.codice);
                            r.codice = rc.codice;
                            r.descrizione = rc.descrizione;
                            r.indirizzo = rc.indirizzo;
                            r.cap = rc.cap;
                            r.codice_comune = rc.codice_comune;
                            r.descrizione_comune = rc.descrizione_comune;
                        }
                        else
                        {
                            var rc = dt[0];
                            r.codice = rc.codice;
                            r.descrizione = rc.descrizione;
                            r.indirizzo = rc.indirizzo;
                            r.cap = rc.cap;
                            r.codice_comune = rc.codice_comune;
                            r.descrizione_comune = rc.descrizione_comune;
                        }

                        _ds.destinatario.AdddestinatarioRow(r);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("SI E' VERIFICATO UN ERRORE DURANTE LA COMPILAZIONE DEI DATI DEI DESTINATARI IN PARTENZA", ex);
            }
            _protocolloLog.Debug("Fine valorizzazione dei dati riguardanti le anagrafiche dei destinatari in Partenza");
        }

        private void InsertAmministrazioniMittentiPartenza()
        {
            _protocolloLog.Debug("Valorizzazione dei dati riguardanti le anagrafiche dei mittenti in Partenza");
            try
            {
                if (_dataInput.Mittenti.Amministrazione.Count == 0)
                    throw new Exception("AMMINISTRAZIONE NON VALORIZZATA");

                if (_dataInput.Mittenti.Amministrazione.Count > 1)
                    throw new Exception("SONO PRESENTI PIU' AMMINISTRAZIONI, DEVE ESSERNE PRESENTE SOLAMENTE UNA");

                var amm = _dataInput.Mittenti.Amministrazione[0];

                var corr = new CorrispondenteApSystemsService(_ws, _authWs, _protocolloLog, _protocolloSerializer);
                var rc = corr.GetCorrispondenteByCodiceUfficio(amm.PROT_UO);

                var r = _ds.mittente.NewmittenteRow();

                r.codice = rc.codice;
                r.descrizione = rc.descrizione;
                r.indirizzo = rc.indirizzo;
                r.cap = rc.cap;
                r.codice_comune = rc.codice_comune;
                r.descrizione_comune = rc.descrizione_comune;

                _ds.mittente.AddmittenteRow(r);

            }
            catch (Exception ex)
            {
                throw new Exception("SI E' VERIFICATO UN ERRORE DURANTE LA COMPILAZIONE DEI DATI DEL MITTENTE (AMMINISTRAZIONE) IN PARTENZA", ex);
            }
            _protocolloLog.Debug("Fine valorizzazione dei dati riguardanti le anagrafiche dei mittenti in Partenza");
        }

        #endregion

        #region Interno

        private void InsertAmministrazioneMittenteInterno()
        {
            _protocolloLog.Debug("Valorizzazione dei dati riguardanti le amministrazioni dei mittenti in Interno");
            try
            {
                if (_dataInput.Mittenti.Amministrazione.Count == 0)
                    throw new Exception("AMMINISTRAZIONE NON VALORIZZATA");

                var amm = _dataInput.Mittenti.Amministrazione[0];

                if (String.IsNullOrEmpty(amm.PROT_UO))
                    throw new Exception(String.Format("L'AMMINISTRAZIONE: {0}, CODICE: {1} NON HA L'UO VALORIZZATA", amm.AMMINISTRAZIONE, amm.CODICEAMMINISTRAZIONE));

                var corr = new CorrispondenteApSystemsService(_ws, _authWs, _protocolloLog, _protocolloSerializer);
                var rc = corr.GetCorrispondenteByCodiceUfficio(amm.PROT_UO);

                var r = _ds.mittente.NewmittenteRow();
                r.codice = rc.codice;
                r.descrizione = rc.descrizione;
                r.indirizzo = rc.indirizzo;
                r.cap = rc.cap;
                r.codice_comune = rc.codice_comune;
                r.descrizione_comune = rc.descrizione_comune;                
                _ds.mittente.AddmittenteRow(r);
            }
            catch (Exception ex)
            {
                throw new Exception("SI E' VERIFICATO UN ERRORE DURANTE LA COMPILAZIONE DEI DATI DEL MITTENTE (AMMINISTRAZIONE) INTERNO", ex);
            }
            _protocolloLog.Debug("Fine valorizzazione dei dati riguardanti le amministrazioni dei mittenti in Interno");
        }

        private void InsertAmministrazioneDestinatarioInterno()
        {
            _protocolloLog.Debug("Valorizzazione dei dati riguardanti le amministrazioni dei destintari in Interno");
            try
            {
                if (_dataInput.Destinatari.Amministrazione.Count == 0)
                    throw new Exception("AMMINISTRAZIONE NON VALORIZZATA");

                var amm = _dataInput.Destinatari.Amministrazione[0];

                if (String.IsNullOrEmpty(amm.PROT_UO))
                    throw new Exception(String.Format("L'AMMINISTRAZIONE: {0}, CODICE: {1} NON HA L'UO VALORIZZATA", amm.AMMINISTRAZIONE, amm.CODICEAMMINISTRAZIONE));


                var corr = new CorrispondenteApSystemsService(_ws, _authWs, _protocolloLog, _protocolloSerializer);
                var rc = corr.GetCorrispondenteByCodiceUfficio(amm.PROT_UO);

                var r = _ds.destinatario.NewdestinatarioRow();
                r.codice = rc.codice;
                r.descrizione = rc.descrizione;
                r.indirizzo = rc.indirizzo;
                r.cap = rc.cap;
                r.codice_comune = rc.codice_comune;
                r.descrizione_comune = rc.descrizione_comune;
                _ds.destinatario.AdddestinatarioRow(r);
            }
            catch (Exception ex)
            {
                throw new Exception("SI E' VERIFICATO UN ERRORE DURANTE LA COMPILAZIONE DEI DATI DEL DESTINATARIO (AMMINISTRAZIONE) INTERNO", ex);
            }
            _protocolloLog.Debug("Fine valorizzazione dei dati riguardanti le amministrazioni dei destintari in Interno");
        }

        #endregion

        /// <summary>
        /// Utilizzato per recuperare il codice istat dalla descrizione del comune, 
        /// utilizzato soprattutto per recuperare il dato in base al valore inserito sul parametro CITTA dell'amministrazione
        /// </summary>
        /// <param name="citta">Descrizione del comune</param>
        /// <returns>Codice Istat del comune</returns>
        private string GetCodiceIstatComune(string citta)
        {
            var descrizione = citta.Trim();
            string retVal = String.Empty;

            if (!String.IsNullOrEmpty(descrizione))
            {
                var com = new ComuniMgr(_db).GetByComune(descrizione);
                
                if (com == null)
                    _protocolloLog.Debug(String.Format("LA RICERCA PER COMUNE {0} NON HA PROODOTTO RISULTATI", descrizione));
                else
                    retVal = com.CODICEISTAT;
            }

            return retVal;
        }

        private string GetCodiceComuneWs(string codiceIstat)
        {
            string retVal = String.Empty;

            var corr = new CorrispondenteApSystemsService(_ws, _authWs, _protocolloLog, _protocolloSerializer);

            DataSetApSystems.Comuni.GetComune.comuni.comuneRow comRow = null;

            if (!String.IsNullOrEmpty(codiceIstat))
                comRow = corr.GetComuneByCodiceIstat(codiceIstat);

            if (comRow != null)
                retVal = comRow.codice;

            return retVal;
        }
    }
}
