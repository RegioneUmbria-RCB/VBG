using Export.Collection;
using Export.Data;
using Export.Mail;
using Init.SIGeProExport.Data;
using Init.SIGeProExport.Manager;
using Init.Utils;
using Parser;
using PersonalLib2.Data;
using PersonalLib2.Data.V2.Legacy;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Globalization;
using System.IO;
using System.Web;
using System.Xml;
using System.Xml.Schema;
using System.Xml.Serialization;

namespace Export
{
    public class CExport : IDisposable
    {
        private MemorySpace _memory = new MemorySpace();
        private string _sFileName = "";
        private readonly FileManager _fm = new FileManager();
        private string _sDateTime;


        public CExport()
        {
        }


        #region Gestione creazione file (.txt o .xml)

        private void RunCycleDettTrac(List<TRACCIATIDETTAGLIO> alDett)
        {
            int index = 0;
            foreach (TRACCIATIDETTAGLIO dettaglio in alDett)
            {
                index += 1;

                //3.2.1.4.1 - Se CFG_TRACCIATIDETTAGLIO.QUERY non è null
                if (!String.IsNullOrEmpty(dettaglio.QUERY))
                {
                    //3.2.1.4.1.1 - _memory.replace(CFG_TRACCIATIDETTAGLIO.QUERY)
                    string pQueryDett = this._memory.Replace(dettaglio.QUERY.Replace("\r\n", " "));
                    IDbDataAdapter adaptDett;
                    DataSet dsTracciatiDett = new DataSet();
                    try
                    {
                        if (this.Debug)
                            this.LogMessage(pQueryDett, this.FolderPath, this.IdComune);

                        IDbCommand cmdDett = this.DataBase.CreateCommand(pQueryDett);
                        DataProviderFactory dpf = new DataProviderFactory(this.DataBase.Connection);
                        adaptDett = dpf.CreateDataAdapter(cmdDett);
                        adaptDett.Fill(dsTracciatiDett);
                    }
                    catch (Exception ex)
                    {
                        throw new Exception("Errore generato durante l'esecuzione della query del dettaglio tracciato " + dettaglio.DESCRIZIONE + ". La query è: " + pQueryDett + ". Modulo: Export. Metodo: RunCycleDettTrac. Messaggio: " + ex.Message + "\r\n");
                    }

                    //Verifico se l'esportazione è stata configurata per poter aggiungere una riga null qualora la query
                    //non restituisse alcun record
                    if (this.Esportazione.INSERISCI_NULLI == "1")
                    {
                        //3.2.1.4.1.2 - se nel dataset non ci sono righe, ne viene aggiunta 1 per impostare a null 
                        //le precedenti variabili inserite nel memory
                        if (dsTracciatiDett.Tables[0].Rows.Count == 0)
                            dsTracciatiDett.Tables[0].Rows.Add(dsTracciatiDett.Tables[0].NewRow());
                    }

                    //3.2.1.4.1.3 - ogni campo letto (select) si aggiunge a _memory (_memory.add). Nei tracciati di dettaglio 
                    // la query può ritornare più righe, in quel caso viene letta solamente la prima
                    if (dsTracciatiDett.Tables[0].Rows.Count > 0)
                        this._memory.Add(dsTracciatiDett.Tables[0].Rows[0]);
                }

                string pValore = String.Empty;

                if (!String.IsNullOrEmpty(dettaglio.VALORE))
                {
                    //Si calcola il valore da esportare _memory.replace(CFG_TRACCIATIDETTAGLIO.VALORE) (potrebbe contenere variabili &)
                    pValore = this._memory.Replace(dettaglio.VALORE);
                }
                else
                {
                    if (dettaglio.OBBLIGATORIO == "1")
                    {
                        //3.2.1.4.2.1 - Se Valore è String.Empty e TRACCIATIDETTAGLIO.OBBLIGATORIO=1 allora Exception
                        throw new Exception("IdTracciato: " + dettaglio.FK_TRACCIATI_ID + ", IdDettaglio: " + dettaglio.ID + ", dato obbligatorio non presente per il campo " + dettaglio.DESCRIZIONE);
                    }
                }

                //se l'esportazione deve creare un file CSV, allora:
                //          - i separatori e l'invio finale vengono aggiunti automaticamente
                //          - se CAMPOTESTO = 1 allora viene aggiunto un prefisso " e un suffisso "
                if (this.Esportazione.FK_TIPIESPORTAZIONE_CODICE == "CSV")
                {
                    if (dettaglio.CAMPOTESTO == "1" || pValore.IndexOf(";") > -1 || pValore.IndexOf("\"") > -1)
                        pValore = "\"" + pValore.Replace("\"", "\"\"") + "\"";

                    //se è stata scelta l'opzione CAMPOTESTO allora il valore finale deve essere ="<valore>" altrimenti non viene interpretato bene
                    if (dettaglio.CAMPOTESTO == "1")
                        pValore = "=" + pValore;

                    this.AppendText(pValore, dettaglio);

                    //nell'ultimo dettaglio non va aggiunto il ; finale
                    if (index != alDett.Count)
                        this.AppendText(";", dettaglio);
                }
                else
                {
                    this.AppendText(pValore, dettaglio);
                }
            }
        }

        private void RunCycleTrac(List<TRACCIATI> aTrac)
        {
            //2.0 - Ciclo per tutti i tracciati dell'esportazione
            //                 (TracciatiMgr.GetList dove TRACCIATI.Id = IdEsportazione e TRACCIATI.IDCOMUNE = idcomune ordinato per OUT_ORDINE)
            //                 Per ogni tracciato
            foreach (TRACCIATI tracciato in aTrac)
            {
                //estrapolo i dettagli
                TRACCIATIDETTAGLIO td = new TRACCIATIDETTAGLIO
                {
                    IDCOMUNE = tracciato.IDCOMUNE,
                    FK_TRACCIATI_ID = tracciato.ID,
                    OrderBy = "OUT_ORDINE ASC"
                };

                TracciatiDettMgr tdMgr = new TracciatiDettMgr(this.DbExport);
                List<TRACCIATIDETTAGLIO> alDett = tdMgr.GetList(td);

                //3.0.1 - Se TRACCIATI.QUERY non è null
                if (!String.IsNullOrEmpty(tracciato.QUERY))
                {
                    //       3.1 - Si esegue la query TRACCIATI.QUERY dopo aver invocato il metodo _memory.replace(Query)
                    DataProviderFactory dpf = new DataProviderFactory(this.DataBase.Connection);
                    string pQuery = this._memory.Replace(tracciato.QUERY.Replace("\r\n", " "));
                    IDbDataAdapter adapter;
                    DataSet dsTracciati = new DataSet();

                    try
                    {
                        if (this.Debug)
                            this.LogMessage(pQuery, this.FolderPath, this.IdComune);

                        IDbCommand cmd = this.DataBase.CreateCommand(pQuery);
                        adapter = dpf.CreateDataAdapter(cmd);
                        adapter.Fill(dsTracciati);
                    }
                    catch (Exception ex)
                    {
                        throw new Exception("Errore generato durante l'esecuzione della query del tracciato " + tracciato.DESCRIZIONE + ". La query è: " + pQuery + ". Modulo: Export. Metodo: RunCycleTrac. Messaggio: " + ex.Message + "\r\n");
                    }


                    if (dsTracciati.Tables[0].Rows.Count > 0)
                    {
                        //       3.1.4 - ogni campo letto (select) si aggiunge a _memory (_memory.add)
                        foreach (DataRow drTestata in dsTracciati.Tables[0].Rows)
                        {
                            //Aggiorno il file xml
                            if ((this.Esportazione.FK_TIPIESPORTAZIONE_CODICE == "XML") && (tracciato.FK_TIPITRACCIATO_CODICE != "FOOTER"))
                                this.CreateAppFile(tracciato);

                            this._memory.Add(drTestata);

                            //Aumento il campo progressivo di _memory se si tratta di un tracciato "DETAIL" (variazione)
                            //if (TypeTrc == "DETAIL")
                            if (tracciato.FK_TIPITRACCIATO_CODICE == "DETAIL")
                            {
                                this._memory.ProgressivoRecord++;
                                this._memory.Add("PROGRESSIVO_RECORD", this._memory.ProgressivoRecord);
                            }

                            //i tracciati di tipo QUERY non hanno dettagli in quanto servono solamente a fare query e se ne possono creare infiniti.
                            if (tracciato.FK_TIPITRACCIATO_CODICE != "QUERY")
                            {
                                this.RunCycleDettTrac(alDett);
                            }

                            //Aggiorno il file xml
                            if ((this.Esportazione.FK_TIPIESPORTAZIONE_CODICE == "XML") && (tracciato.FK_TIPITRACCIATO_CODICE != "HEADER") && !string.IsNullOrEmpty(tracciato.OUT_XMLTAG))
                                this.CreateAppFile(tracciato.OUT_NOMEFILE);

                            //nei file CSV , qualora sia presente la query nel tracciato ciclato, il carattere di fine riga viene aggiunto solo
                            //se la query ritorna risultati
                            if (tracciato.FK_TIPITRACCIATO_CODICE != "QUERY" && this.Esportazione.FK_TIPIESPORTAZIONE_CODICE == "CSV")
                            {
                                this.AppendText("\r\n", tracciato);
                            }
                        }
                    }
                }
                else
                {
                    // se non è presente nessuna query nel tracciato e il tracciato non è configurato come QUERY
                    if (tracciato.FK_TIPITRACCIATO_CODICE != "QUERY")
                    {
                        //Aggiorno il file xml
                        if ((this.Esportazione.FK_TIPIESPORTAZIONE_CODICE == "XML") && (tracciato.FK_TIPITRACCIATO_CODICE != "FOOTER"))
                            this.CreateAppFile(tracciato);

                        this.RunCycleDettTrac(alDett);

                        //Aggiorno il file xml se l'esportazione è di tipo xml
                        if ((this.Esportazione.FK_TIPIESPORTAZIONE_CODICE == "XML") && (tracciato.FK_TIPITRACCIATO_CODICE != "HEADER") && !string.IsNullOrEmpty(tracciato.OUT_XMLTAG))
                            this.CreateAppFile(tracciato.OUT_NOMEFILE);

                        //ggiungo il carattere di fine riga se si tratta di un CSV
                        if (this.Esportazione.FK_TIPIESPORTAZIONE_CODICE == "CSV")
                        {
                            this.AppendText("\r\n", tracciato);
                        }
                    }
                }


            }
        }

        //Deve essere utilizzato solamente per le esportazioni di tipo XML
        private void RunSection()
        {
            this.RunSection(null);
        }

        private void RunSection(string sTypeTrc)
        {
            //TypeTrc = sTypeTrc;
            TRACCIATI trac = new TRACCIATI();
            try
            {
                trac.IDCOMUNE = this.IdComune;
                trac.FK_ESP_ID = this.IdEsportazione.ToString();

                if (!string.IsNullOrEmpty(sTypeTrc))
                {
                    if (sTypeTrc == "DETAIL" || sTypeTrc == "QUERY")
                        trac.OthersWhereClause.Add("FK_TIPITRACCIATO_CODICE IN ('DETAIL','QUERY')");
                    else
                        trac.FK_TIPITRACCIATO_CODICE = sTypeTrc;
                }


                trac.OrderBy = "OUT_ORDINE ASC";

                TracciatiMgr trMgr = new TracciatiMgr(this.DbExport);
                List<TRACCIATI> al = trMgr.GetList(trac);

                this.RunCycleTrac(al);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante l'elaborazione di un tracciato di tipo: " + sTypeTrc + " .Modulo: Export. Metodo: RunSection. Messaggio " + ex.ToString() + "\r\n", ex);
            }
        }

        public byte[] Run(string xmlText)
        {
            byte[] arByte;
            try
            {
                this._sDateTime = DateTime.Now.Ticks.ToString();
                this.FolderPath = this.FolderPath + this.IdComune + "_" + this._sDateTime + "\\";
                this._fm.FolderPath = this.FolderPath;

                //Salvataggio del file di input
                if (!string.IsNullOrEmpty(xmlText))
                {
                    this.CreateDirectory();
                    FileStream fileInput = new FileStream(this.FolderPath + "Input.log", FileMode.Create, FileAccess.ReadWrite);
                    StreamUtils.BulkTransfer(StreamUtils.StringToStream(xmlText), fileInput);
                    fileInput.Close();
                }

                //1.0 - Ciclo per gli elementi del file XML
                //      (si utilizza il dataset tornato da XmlParser.Parse)
                XMLParser xmlP = new XMLParser
                {
                    XmlSchema = this.Esportazione.INPUT_XSD,
                    XmlText = xmlText
                };

                DataSet ds = xmlP.Parse();

                this.CreateDirectory();
                this.LogMessage("Creazione file zip e relativo byte array iniziata!", this.FolderPath, this.IdComune);

                //Vengono caricati nell'oggetto memory gli eventuali parametri dell'esportazione
                //e si aggiunge il primo elemento del dataset a _memory
                this.MemoryInitialize(ds);

                if (this.Debug)
                    this.LogMessage("Sono stati passati " + ds.Tables[0].Rows.Count.ToString() + " elementi da analizzare", this.FolderPath, this.IdComune);

                //Oggetto usato per conservare i dati derivanti dall'intestazione
                if (this.Esportazione.FK_TIPIESPORTAZIONE_CODICE != "XML")
                {
                    this.RunSection("HEADER");
                }


                //1.2 - Viene creato un file per ogni nome file presente in ESPORTAZIONI.OUT_NOMEFILE e TRACCIATI.OUT_NOMEFILE (Select OUT_NOMEFILE From ESPORTAZIONI Where ID=x UNION Select OUT_NOMEFILE From TRACCIATI Where FK_ESP_ID=x Group By OUT_NOMEFILE)
                foreach (DataRow dr in ds.Tables[0].Rows)
                {
                    if (this.Debug)
                        this.LogMessage("Codice: " + dr["CODICE"].ToString(), this.FolderPath, this.IdComune);

                    //Verifico se l'esportazione è stata configurata per poter annullare tutti i dati (tranne quelli di input)
                    //al termine di ogni pratica
                    if (this.Esportazione.ANNULLA_DATI == "1")
                        this.MemoryInitialize(ds);

                    //1.2.1 - ogni elemento del dataset si aggiunge a _memory
                    this._memory.Add(dr);

                    //Nuove modifiche per creare file XML
                    switch (this.Esportazione.FK_TIPIESPORTAZIONE_CODICE)
                    {
                        case "XML":
                            {
                                //nel caso di xml, tutti i tracciati vengono ciclati per ogni record passato anche quelli "HEADER" e "FOOTER"
                                this.RunSection();
                                break;
                            }
                        default:
                            {
                                this.RunSection("DETAIL");
                                break;
                            }
                    }

                }

                //Verifico se l'esportazione è stata configurata per poter annullare tutti i dati (tranne quelli di input)
                //al termine di ogni pratica
                if (this.Esportazione.ANNULLA_DATI == "1")
                    this.MemoryInitialize(ds);

                //Numero totale delle pratiche da elaborare
                //_memory.RecordCount = ds.Tables[0].Rows.Count;
                //_memory.Add("RECORD_COUNT",_memory.RecordCount);
                if (this.Esportazione.FK_TIPIESPORTAZIONE_CODICE != "XML")
                    this.RunSection("FOOTER");

                this.CloseOpenFile();

                //Verifico se occorre zippare
                if (this.Zip)
                    arByte = this.CreateZip(this._fm);
                else
                    arByte = this._fm.CreateByteArray(this.Esportazione.OUT_NOMEFILE);

                if (!string.IsNullOrEmpty(this.MailDestinatario))
                {
                    if (this.Debug)
                        this.LogMessage("Preparazione e invio della mail contenente l'esportazione", this.FolderPath, this.IdComune);
                    //Invio per mail il risultato dell'esportazione
                    this.SendMail(arByte);
                }

                this.LogMessage("Creazione file zip e relativo byte array terminata con successo!", this.FolderPath, this.IdComune);
            }
            catch (Exception ex)
            {
                this.LogMessage("Creazione file zip e relativo byte array terminata con errore! " + ex.Message, this.FolderPath, this.IdComune);
                throw;
            }

            return arByte;
        }

        private void MemoryInitialize(DataSet ds)
        {
            this._memory = new MemorySpace();

            //Vengono caricati nell'oggetto memory gli eventuali parametri dell'esportazione
            if (this.ParametriCollection != null)
            {
                foreach (Parametro elem in this.ParametriCollection)
                {
                    this._memory.Add(elem.NOME, elem.VALORE);
                }
            }

            //1.1 - si aggiunge il primo elemento del dataset a _memory
            //TODO: ds.Tables[0].Rows potrebbe non avere righe
            if (ds != null && ds.Tables[0].Rows.Count > 0)
                this._memory.Add(ds.Tables[0].Rows[0]);
        }

        private void SendMail(byte[] arByte)
        {
            #region 1 Messaggio

            SIGeProMailMessage message = new SIGeProMailMessage
            {
                CorpoMail = "In allegato trova il file .zip dell'esportazione",
                Destinatari = this.MailDestinatario,
                Oggetto = "Esportazione: " + this.Esportazione.DESCRIZIONE
            };

            #endregion

            #region 2. Attachments

            BinaryObject obj = new BinaryObject
            {
                FileContent = arByte,
                FileName = this.IdComune + "_" + this._sDateTime + ".zip"
            };
            message.Attachments = new BinaryObject[1];
            message.Attachments[0] = obj;

            #endregion

            #region 3. Invio

            string webServiceUrl = this.WebServiceMail;
            SmtpMailSender SMTPms = new SmtpMailSender
            {
                Url = webServiceUrl
            };
            SMTPms.Send(this.Token, "TT", message);

            #endregion
        }

        private void AppendText(string pValore, TRACCIATI tracciato)
        {
            this._fm.AppendTxt(this.GetFileName(tracciato), pValore);
        }

        private void AppendText(string pValore, TRACCIATIDETTAGLIO dettaglio)
        {
            if (dettaglio != null)
                pValore = this.PadValue(pValore, Convert.ToInt32(dettaglio.LUNGHEZZA));

            try
            {
                switch (this.Esportazione.FK_TIPIESPORTAZIONE_CODICE)
                {
                    case "XML":
                        {
                            this._fm.AppendXml(this.GetFileName(dettaglio.FK_TRACCIATI_ID_001), string.IsNullOrEmpty(dettaglio.OUT_XMLTAG) ? dettaglio.OUT_XMLTAG : this.ReplaceSpace(dettaglio.OUT_XMLTAG), pValore);
                            break;
                        }
                    default:
                        {
                            //3.2.1.4.3.2 - Si apre in append il file TRACCIATI.OUT_NOMEFILE (o ESPORTAZIONI.OUTNOMEFILE) e si scrive il valore calcolato dal passo 3.2.1.4.2
                            //fm.Append(GetFileName(tracciato),pValore);
                            this._fm.AppendTxt(this.GetFileName(dettaglio.FK_TRACCIATI_ID_001), pValore);
                            break;
                        }
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la scrittura del file di tipo " + this.Esportazione.FK_TIPIESPORTAZIONE_CODICE + ". Modulo: Export. Metodo: CreateAppFile. Messaggio: " + ex.Message + "\r\n");
            }
        }

        private void CreateAppFile(TRACCIATI tracciato)
        {
            try
            {
                this._fm.CreateAppendXml(this.GetFileName(tracciato), string.IsNullOrEmpty(this.Esportazione.OUT_XMLTAG) ? this.Esportazione.OUT_XMLTAG : this.ReplaceSpace(this.Esportazione.OUT_XMLTAG.Trim()), string.IsNullOrEmpty(tracciato.OUT_XMLTAG) ? tracciato.OUT_XMLTAG : this.ReplaceSpace(tracciato.OUT_XMLTAG.Trim()));
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la scrittura del file xml. Modulo: Export. Metodo: CreateAppFile. Messaggio: " + ex.Message + "\r\n");
            }
        }

        private void CreateAppFile(string sFileName)
        {
            try
            {
                this._fm.AppendXml(sFileName);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la scrittura del file xml. Modulo: Export. Metodo: CreateAppFile. Messaggio: " + ex.Message + "\r\n");
            }
        }

        private string GetFileName(TRACCIATI tracciato)
        {
            this._sFileName = this.Esportazione.OUT_NOMEFILE;
            if ((tracciato.OUT_NOMEFILE != null) && (tracciato.OUT_NOMEFILE != ""))
                this._sFileName = tracciato.OUT_NOMEFILE;

            return this._sFileName;
        }

        private string ReplaceSpace(string sValue)
        {
            return sValue.Replace(" ", "_");
        }

        public void CloseOpenFile()
        {
            switch (this.Esportazione.FK_TIPIESPORTAZIONE_CODICE)
            {

                //3.2.1.4.4 - Se ESPORTAZIONI.FK_TIPIESPORTAZIONE_CODICE="XML" 
                case "XML":
                    {
                        this._fm.CloseXml();
                        break;
                    }
                ////3.2.1.4.3 - Se ESPORTAZIONI.FK_TIPIESPORTAZIONE_CODICE="TXT"
                default:
                    {
                        this._fm.CloseTxt();
                        break;
                    }
            }
        }

        private void CreateDirectory()
        {
            if (!Directory.Exists(this.FolderPath))
                Directory.CreateDirectory(this.FolderPath);
        }

        private byte[] CreateZip(FileManager fm)
        {
            ArrayList pLstFile = fm.GetFileList("*." + this.Esportazione.FK_TIPIESPORTAZIONE_CODICE);

            return fm.Zip(this.IdComune + "_" + DateTime.Now.Ticks.ToString(), 9, pLstFile);
        }

        #endregion


        public CListaEsportazione GetListExp()
        {
            return this.GetListExp(null);
        }

        public CListaEsportazione GetListExp(string sContext)
        {
            CListaEsportazione pListEsportazione = new CListaEsportazione();
            EsportazioniCollection pList = new EsportazioniCollection();
            try
            {
                string cmdText = "SELECT " +
                                    "ESPORTAZIONI.ID, ESPORTAZIONI.IDCOMUNE " +
                                 "FROM " +
                                    "ESPORTAZIONI " +
                                 "WHERE " +
                                    "ESPORTAZIONI.IDCOMUNE IN ('" + this.IdComune + "','DEFB') AND " +
                                    "ESPORTAZIONI.FLG_ABILITATA = 1 ";

                if (!string.IsNullOrEmpty(sContext))
                {
                    cmdText += "AND ESPORTAZIONI.FK_TIPICONTESTOESP_CODICE = '" + sContext + "' ";
                }

                cmdText += "ORDER BY " +
                                    "ESPORTAZIONI.DESCRIZIONE ASC";

                using (IDbCommand cmd = this.DbExport.CreateCommand(cmdText))
                {
                    IDataAdapter adapter = this.DbExport.CreateDataAdapter(cmd);

                    DataSet ds = new DataSet();
                    adapter.Fill(ds);

                    foreach (DataRow dr in ds.Tables[0].Rows)
                    {
                        CEsportazione pEsportazione = this.GetExpDetail(dr["IDCOMUNE"].ToString(), dr["ID"].ToString());
                        pList.Add(pEsportazione);
                    }
                }

                pListEsportazione.LISTAESPORTAZIONI = pList;

                string sFileName = "ListExp_" + DateTime.Now.Ticks.ToString() + ".xml";
                this.Serializza(pListEsportazione, sFileName);
            }
            catch (Exception ex)
            {
                throw ex;
            }

            return pListEsportazione;
        }

        public CEsportazione GetExpDetail(string idcomune, string idesportazione)
        {
            CEsportazione pEsportazione = new CEsportazione();

            try
            {

                ESPORTAZIONI esp = new EsportazioniMgr(this.DbExport).GetById(idcomune, idesportazione);

                pEsportazione.ID = esp.ID;
                pEsportazione.IDCOMUNE = esp.IDCOMUNE;
                pEsportazione.DESCRIZIONE = esp.DESCRIZIONE;
                pEsportazione.CONTESTO = esp.FK_TIPICONTESTOESP_CODICE;

                PARAMETRIESPORTAZIONE pParEsp = new PARAMETRIESPORTAZIONE
                {
                    IDCOMUNE = pEsportazione.IDCOMUNE,
                    FK_ESP_ID = pEsportazione.ID
                };
                ParametriEsportazioneMgr pParEspMgr = new ParametriEsportazioneMgr(this.DbExport);
                List<PARAMETRIESPORTAZIONE> pListParEsp = pParEspMgr.GetList(pParEsp);

                if (pListParEsp.Count != 0)
                    pEsportazione.LISTAPARAMETRI = new ParametriCollection();

                foreach (PARAMETRIESPORTAZIONE elem in pListParEsp)
                {
                    Parametro pParametro = new Parametro
                    {
                        NOME = elem.NOME,
                        DESCRIZIONE = elem.DESCRIZIONE
                    };
                    pEsportazione.LISTAPARAMETRI.Add(pParametro);
                }


                string sFileName = "ExpDetail_" + DateTime.Now.Ticks.ToString() + ".xml";
                this.Serializza(pEsportazione, sFileName);
            }
            catch (Exception ex)
            {
                throw ex;
            }

            return pEsportazione;
        }

        public CEsportazione GetExpDetail()
        {
            return this.GetExpDetail(this.Esportazione.IDCOMUNE, this.Esportazione.ID);
        }

        private string PadValue(string pValue, int pLength)
        {
            string retVal = pValue;

            if (pLength > 0)
            {
                if (retVal.Length > pLength)
                    retVal = retVal.Substring(0, pLength);
            }

            retVal = retVal.PadRight(pLength, Convert.ToChar(" "));

            return retVal;
        }

        private void LogMessage(string sMessageLog, string sFolder, string sIdComune)
        {
            DateTime pDt = DateTime.Now;
            string sDay = DateTime.Now.Day.ToString();
            string sMonth = DateTime.Now.Month.ToString();
            string sYear = DateTime.Now.Year.ToString();
            string sDate = pDt.ToString("G", DateTimeFormatInfo.InvariantInfo);
            //Creo una nuova istanza di un FileStream
            FileStream pFs = new FileStream(sFolder + "Log_" + sDay + "_" + sMonth + "_" + sYear + ".log", FileMode.Append, FileAccess.Write);
            //Creo una nuova istanza di un StreamWriter
            StreamWriter pSw = new StreamWriter(pFs);
            string sMessage = "[" + sDate + "]: " + " \tComune: " + sIdComune + " \tMessaggio:  " + sMessageLog;
            pSw.WriteLine(sMessage);
            //Chiudo l'istanza dello StreamWriter
            pSw.Flush();
            pSw.Close();
        }

        private void Serializza(object pExport, string sFileName)
        {
            FileStream pFs = null;
            
            try
            {
                XmlSerializer pXmlSerializer = new XmlSerializer(pExport.GetType());

                if (!Directory.Exists(this.FolderPath))
                    Directory.CreateDirectory(this.FolderPath);
                pFs = new FileStream(this.FolderPath + sFileName, FileMode.Create, FileAccess.ReadWrite);
                var sw = new StreamWriter(pFs);
                pXmlSerializer.Serialize(sw, pExport);
                pFs.Seek(0, SeekOrigin.Begin);

                //Verifico la validità del file xml
                switch (pExport.GetType().Name)
                {
                    case "CListaEsportazione":
                        this.ValidateXml(pFs, "ListExp.xsd");
                        break;
                    case "CEsportazione":
                        this.ValidateXml(pFs, "GetEsportazione.xsd");
                        break;
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la serializzazione della classe " + pExport.GetType().Name + ". Metodo: Serializza, modulo: Export. " + ex.Message + "\r\n");
            }
            finally
            {
                //Già chiuso in seguito alla validazione
                pFs?.Close();
            }
        }

        //Codice per effettuare la validazione
        private bool _b_success;
        private string _sMessage = "";
        /// <summary>
        /// Metodo usato per validare il file segnatura.xml in base al file xsd
        /// </summary>
        /// <param name="pStream">File xml da validare</param>
        private void ValidateXml(FileStream pStream, string sFileName)
        {
            XmlValidatingReader vreader = null;
            try
            {
                this._b_success = true;
                pStream.Seek(0, SeekOrigin.Begin);
                XmlTextReader reader = new XmlTextReader(pStream);

                //Creo un validating reader.
                vreader = new XmlValidatingReader(reader);

                XmlSchemaCollection xsc = new XmlSchemaCollection();

                xsc.Add(null, this.SchemaPath + sFileName);
                //Valido usando lo schema conservato nello schema collection.
                vreader.Schemas.Add(xsc);

                vreader.ValidationEventHandler += new ValidationEventHandler(this.ValidationCallBack);
                //Leggo e valido il file xml.
                while (vreader.Read()) { }
                if (!this._b_success)
                    throw new Exception(this._sMessage);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la validazione. " + ex.Message + "\r\n");
            }
            finally
            {
                //Chiudo il reader.
                vreader?.Close();
            }
        }

        private void ValidationCallBack(object sender, ValidationEventArgs args)
        {
            this._b_success = false;
            this._sMessage = "Errore di validazione: " + args.Message + "\r\n";
        }

        public bool Zip { get; set; } = true;

        public string Ente { get; set; } = null;

        public int IdEsportazione { get; set; } = -1;

        public string MailDestinatario { get; set; }

        public int CodiceResponsabile { get; set; } = -1;

        private DataBase _dbExport = null;
        public DataBase DbExport
        {
            get
            {
                if (this._dbExport == null)
                {
                    ProviderType initialProviderType = (ProviderType)Enum.Parse(typeof(ProviderType), this.ProviderType, true);
                    this._dbExport = new DataBase(this.ConnStringExport, initialProviderType);
                }

                return this._dbExport;
            }
        }

        public DataBase DataBase { get; set; }

        private ESPORTAZIONI _esportazione = new ESPORTAZIONI();
        public ESPORTAZIONI Esportazione
        {
            get
            {
                if (this.IdEsportazione > -1)
                {
                    if (this._esportazione.ID != this.IdEsportazione.ToString())
                    {
                        EsportazioniMgr expMgr = new EsportazioniMgr(this.DbExport);
                        this._esportazione = expMgr.GetById(this.IdComune, this.IdEsportazione.ToString());
                        if (this._esportazione == null)
                            throw new Exception("L'esportazione selezionata non esiste");
                    }
                    return this._esportazione;
                }
                else
                {
                    throw new Exception("Attenzione, Impossibile utilizzare la proprietà \"Esportazione\" se non si imposta prima \"IdEsportazione\"");
                }
            }
        }

        private string _webServiceMail = String.Empty;
        public string WebServiceMail
        {
            get
            {
                if (string.IsNullOrEmpty(this._webServiceMail))
                    this._webServiceMail = ConfigurationManager.AppSettings["WSMAIL"];

                if (string.IsNullOrEmpty(this._webServiceMail))
                    throw new Exception("Parametro WSMAIL non presente oppure uguale ad una stringa vuota nel config!!");

                return this._webServiceMail;
            }
            set { this._webServiceMail = value; }
        }

        public string Token { get; set; } = String.Empty;

        private string _connStringExport = String.Empty;
        public string ConnStringExport
        {
            get
            {
                if (string.IsNullOrEmpty(this._connStringExport))
                    this._connStringExport = ConfigurationManager.AppSettings["CONNECTIONSTRING_CONFIG"];

                if (string.IsNullOrEmpty(this._connStringExport))
                    throw new Exception("Parametro CONNECTIONSTRING_CONFIG non presente oppure uguale ad una stringa vuota nel config!!");

                return this._connStringExport;
            }
            set { this._connStringExport = value; }
        }

        private string _providerType = String.Empty;
        public string ProviderType
        {
            get
            {
                if (string.IsNullOrEmpty(this._providerType))
                    this._providerType = ConfigurationManager.AppSettings["PROVIDERTYPE_CONFIG"];

                if (string.IsNullOrEmpty(this._providerType))
                    throw new Exception("Parametro PROVIDERTYPE_CONFIG non presente oppure uguale ad una stringa vuota nel config!!");

                return this._providerType;
            }
            set { this._providerType = value; }
        }

        public string IdComune { get; set; } = String.Empty;

        private string _sFolderPath = String.Empty;
        public string FolderPath
        {
            get
            {
                if (string.IsNullOrEmpty(this._sFolderPath))
                    this._sFolderPath = HttpContext.Current.Server.MapPath(ConfigurationManager.AppSettings["FILE_PATH"]);

                if (string.IsNullOrEmpty(this._sFolderPath))
                    throw new Exception("Parametro FILE_PATH non presente oppure uguale ad una stringa vuota nel config!!");
                else
                {
                    if (!this._sFolderPath.EndsWith(@"\"))
                        this._sFolderPath += @"\";
                }


                return this._sFolderPath;
            }
            set { this._sFolderPath = value; }
        }

        private string _sSchemaPath = String.Empty;
        public string SchemaPath
        {
            get
            {
                if (string.IsNullOrEmpty(this._sSchemaPath))
                    this._sSchemaPath = HttpContext.Current.Server.MapPath(ConfigurationManager.AppSettings["SCHEMA_PATH"]);

                if (string.IsNullOrEmpty(this._sSchemaPath))
                    throw new Exception("Parametro SCHEMA_PATH non presente oppure uguale ad una stringa vuota nel config!!");
                else
                {
                    if (!this._sSchemaPath.EndsWith(@"\"))
                        this._sSchemaPath += @"\";
                }


                return this._sSchemaPath;
            }
            set { this._sSchemaPath = value; }
        }

        public ParametriCollection ParametriCollection { get; set; } = null;

        public bool Debug { get; set; } = false;

        public void Dispose()
        {
            this.DataBase?.Dispose();
            if (this._dbExport != null)
                this.DbExport.Dispose();
            if (this._memory.Count != 0)
                this._memory.Clear();
            if ((this._fm.TXT._pLstFile.Count != 0) || (this._fm.XML._pLstFile.Count != 0))
                this.CloseOpenFile();
        }

        public override bool Equals(object obj)
        {
            return obj is CExport export &&
                   this._sSchemaPath == export._sSchemaPath;
        }

        public override int GetHashCode()
        {
            return -165879401 + EqualityComparer<string>.Default.GetHashCode(this._sSchemaPath);
        }
    }
}
