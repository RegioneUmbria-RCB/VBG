using System;
using System.Collections;
using Init.SIGePro.Verticalizzazioni;
using System.Xml.Serialization;
using Init.SIGePro.SegnaturaSigepro;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using System.Data;
using PersonalLib2.Data;
using System.Text.RegularExpressions;
using System.Collections.Generic;
using Init.SIGePro.Exceptions.Protocollo;
using Init.SIGePro.Manager.Manager;
using PersonalLib2.Exceptions;
using System.IO;
using log4net;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    /// <summary>
    /// Descrizione di riepilogo per PROTOCOLLO_SIGEPRO.
    /// </summary>
    /// 
    public class PROTOCOLLO_SIGEPRO : ProtocolloBase
    {
        #region Costruttori
        public PROTOCOLLO_SIGEPRO()
        {
        }
        #endregion

        private string _CodiceAOO = String.Empty;
        private string _DenominazioneAOO = String.Empty;
        private string _CodiceAmm = String.Empty;
        private string _DenominazioneAmm = String.Empty;
        private string _IndirizzoTelematico = String.Empty;
        private int? _idMittDestAmministrazione = (int?)null;


        public enum FormatFileProtocolloEnum { CodiceOggetto_IdComune }


        ILog _log = LogManager.GetLogger(typeof(PROTOCOLLO_SIGEPRO));

        #region Proprietà

        #endregion

        public struct FormatFileProtocolloStruct
        {
            public string CodiceOggetto { get; set; }
            public string IdComune { get; set; }
            public string Estensione { get; set; }

            const string DELIMITATORE = "-";

            public string Delimitatore { get; set; }

            public enum FormtFileProtocolloEnum { CodiceOggetto_IdComune }

            public string FormatFileProtocollo(FormatFileProtocolloEnum format)
            {
                if (String.IsNullOrEmpty(Delimitatore))
                    Delimitatore = DELIMITATORE;

                string res = String.Empty;

                if (format == FormatFileProtocolloEnum.CodiceOggetto_IdComune)
                {
                    int iPos = Estensione.LastIndexOf(".");

                    if (iPos == -1)
                        Estensione = "." + Estensione;

                    res = CodiceOggetto + Delimitatore + IdComune + Estensione;
                }

                if (String.IsNullOrEmpty(res))
                    throw new Exception("Il formato del nome file ha restituito una stringa vuota");

                return res;
            }
        }

        public override ListaTipiDocumento GetTipiDocumento()
        {
            var res = new ListaTipiDocumento();
            var list = new List<ListaTipiDocumentoDocumento>();

            var mgr = new ProtTipologiaProtocolloMgr(this.DatiProtocollo.Db);
            var listTipoProto = mgr.GetList(new ProtTipologiaProtocollo { Idcomune = DatiProtocollo.IdComune });

            foreach (var tipoProto in listTipoProto)
                list.Add(new ListaTipiDocumentoDocumento { Codice = tipoProto.Tp_Id.Value.ToString(), Descrizione = tipoProto.Tp_Descrizione });

            res.Documento = list.ToArray();

            return res;
        }


        #region Metodi per ottenere la lista dei tipi documento e delle classifiche

        public override ListaTipiClassifica GetClassifiche()
        {
            ListaTipiClassifica pListaTipiClassifica = null;

            try
            {
                DataSet ds = new DataSet();

                string sql = @"SELECT Prot_Classificazione.Cl_Id, '[' || Prot_Classificazione.cl_codice || '] - ' || Prot_Classificazione.Cl_Descrizione || ' (' || Prot_Classificazione.Cl_Id || ')' As Cl_Descrizione
                               FROM prot_classificazione
                               WHERE Prot_Classificazione.Idcomune = {0}
                               ORDER BY Prot_Classificazione.Cl_Codice";

                sql = String.Format(sql, this.DatiProtocollo.Db.Specifics.QueryParameterName("idcomune"));

                using (IDbCommand cmd = this.DatiProtocollo.Db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.DatiProtocollo.Db.CreateParameter("idcomune", this.DatiProtocollo.IdComune));

                    IDataAdapter da = this.DatiProtocollo.Db.CreateDataAdapter(cmd);
                    da.Fill(ds);
                }

                pListaTipiClassifica = CreaListaTipiClassifica(ds);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la richiesta della lista dei tipi documento con il protocollo SIPrWeb. Metodo: ListaTipiDocumento. " + ex.Message + "\r\n");
            }

            return pListaTipiClassifica;
        }

        private ListaTipiClassifica CreaListaTipiClassifica(DataSet ds)
        {
            var pListaTipiClassifica = new ListaTipiClassifica();

            pListaTipiClassifica.Classifica = new ListaTipiClassificaClassifica[((DataSet)ds).Tables[0].Rows.Count];

            int iCount = 0;
            foreach (DataRow dr in ((DataSet)ds).Tables[0].Rows)
            {
                pListaTipiClassifica.Classifica[iCount] = new ListaTipiClassificaClassifica();

                pListaTipiClassifica.Classifica[iCount].Codice = dr["CL_ID"].ToString();
                pListaTipiClassifica.Classifica[iCount].Descrizione = dr["CL_DESCRIZIONE"].ToString();
                iCount++;
            }

            return pListaTipiClassifica;
        }

        #endregion

        #region Metodi per l'annullamento di un protocollo

        public override ListaMotiviAnnullamento GetMotivoAnnullamento()
        {
            try
            {
                ListaMotiviAnnullamento list = new ListaMotiviAnnullamento();

                ProtMotiviAnnullamento protMotAnn = new ProtMotiviAnnullamento();
                protMotAnn.Idcomune = DatiProtocollo.IdComune;
                ProtMotiviAnnullamentoMgr protMotAnnMgr = new ProtMotiviAnnullamentoMgr(this.DatiProtocollo.Db);
                List<ProtMotiviAnnullamento> listProtMotAnn = protMotAnnMgr.GetList(protMotAnn);

                list.MotivoAnnullamento = new ListaMotiviAnnullamentoMotivoAnnullamento[listProtMotAnn.Count];

                int iCount = 0;
                foreach (ProtMotiviAnnullamento elem in listProtMotAnn)
                {
                    list.MotivoAnnullamento[iCount] = new ListaMotiviAnnullamentoMotivoAnnullamento();

                    list.MotivoAnnullamento[iCount].Codice = elem.MaId.ToString();
                    list.MotivoAnnullamento[iCount].Descrizione = elem.MaDescrizione;

                    iCount++;
                }


                return list;
            }
            catch (Exception ex)
            {
                if (ex.InnerException != null)
                    throw new ProtocolloException("Errore generato durante la richiesta della lista dei motivi di annullamento eseguita con il protocollo SIGePro. Metodo: AnnullaProtocollo, modulo: ProtocolloSigepro. " + ex.Message + "Inner exception: " + ex.InnerException.Message + "\r\n");
                else
                    throw new ProtocolloException("Errore generato durante la richiesta della lista dei motivi di annullamento eseguita con il protocollo SIGePro. Metodo: AnnullaProtocollo, modulo: ProtocolloSigepro. " + ex.Message + "\r\n");
            }
        }

        public override DatiProtocolloAnnullato IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                return Annullato(idProtocollo, annoProtocollo, numeroProtocollo);
            }
            catch (SingleRowException ex)
            {
                DatiProtocolloAnnullato datiProtocolloAnnullato = new DatiProtocolloAnnullato();
                datiProtocolloAnnullato.Annullato = EnumAnnullato.warning;
                datiProtocolloAnnullato.NoteAnnullamento = "Errore: sono presenti più protocolli con lo stesso numero ed anno!" + "Numero: " + numeroProtocollo + ", anno: " + annoProtocollo;

                return datiProtocolloAnnullato;
            }
            catch (ProtocolloException ex)
            {
                throw ex;
            }
            catch (Exception ex)
            {
                if (ex.InnerException != null)
                    throw new ProtocolloException("Errore generato durante la verifica della nullabilità del protocollo numero " + numeroProtocollo + " ed anno " + annoProtocollo + " eseguita con il protocollo SIGePro. Metodo: EAnnullato, modulo: ProtocolloSigepro. " + ex.Message + "Inner exception: " + ex.InnerException.Message + "\r\n");
                else
                    throw new ProtocolloException("Errore generato durante la verifica della nullabilità del protocollo numero " + numeroProtocollo + " ed anno " + annoProtocollo + " eseguita con il protocollo SIGePro. Metodo: EAnnullato, modulo: ProtocolloSigepro. " + ex.Message + "\r\n");
            }
        }

        public override void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento)
        {
            try
            {
                if (_protocolloLogs.IsDebugEnabled)
                    _protocolloLogs.Debug("Inviata richiesta di annullamento protocollo. Anno: " + annoProtocollo + ", numero: " + numeroProtocollo);

                AnnullamentoProtocollo(idProtocollo, annoProtocollo, numeroProtocollo, motivoAnnullamento, noteAnnullamento);
            }
            catch (Exception ex)
            {
                if (ex.InnerException != null)
                    throw new ProtocolloException("Errore generato durante l'annullamento di un protocollo eseguita con il protocollo SIGePro. Metodo: AnnullaProtocollo, modulo: ProtocolloSigepro. " + ex.Message + "Inner exception: " + ex.InnerException.Message + "\r\n");
                else
                    throw new ProtocolloException("Errore generato durante l'annullamento di un protocollo eseguita con il protocollo SIGePro. Metodo: AnnullaProtocollo, modulo: ProtocolloSigepro. " + ex.Message + "\r\n");
            }
        }

        #endregion

        #region Metodi per la stampa di un'etichetta

        #endregion

        #region Metodi per la lettura di un protocollo

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloLetto pProtocolloLetto = null;

            _log.Debug("Chiamata al metodo GetProtocollo");
            _log.InfoFormat("Lettura del protocollo id: {0}, numero: {1}, anno: {2}", idProtocollo, numeroProtocollo, annoProtocollo);
            pProtocolloLetto = GetProtocollo(idProtocollo, annoProtocollo, numeroProtocollo);

            return pProtocolloLetto;
        }

        #endregion

        #region Metodi per la lettura degli allegati

        /*
        private string GetIdBase(string idBase)
        {
            try
            {
                string[] arrDatiProt = idBase.Split('|');
                return arrDatiProt[3];
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante il recupero del Codice dell'oggetto, metodo GetIdBase della classe ProtocolloSigepro");
            }
        }
        */

        public override AllOut LeggiAllegato()
        {
            int codiceOggetto;
            bool isNumber = Int32.TryParse(IdAllegato, out codiceOggetto);

            if (!isNumber)
                throw new Exception("Il codice oggetto passato non è un numero, IdAllegato: " + IdAllegato);

            try
            {
                ProtOggettiMgr mgrOgg = new ProtOggettiMgr(this.DatiProtocollo.Db);
                ProtOggetti ogg = mgrOgg.GetById(codiceOggetto, this.DatiProtocollo.IdComune);

                string estensioneFile = mgrOgg.GetExtension(ogg);

                VerticalizzazioneFilesystemProtocollo fs = new VerticalizzazioneFilesystemProtocollo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software);
                bool readFromFileSystem = fs.Attiva;

                byte[] oggetto = ogg.Oggetto;

                if (oggetto == null && readFromFileSystem)
                {
                    if (String.IsNullOrEmpty(fs.DirectoryLocale))
                        throw new Exception("Il Parametro DIRECTORY_LOCALE della verticalizzazione FILESYSTEM_PROTOCOLLO deve essere valorizzata");

                    var format = new FormatFileProtocolloStruct { IdComune = DatiProtocollo.IdComune, CodiceOggetto = ogg.Codiceoggetto.Value.ToString(), Estensione = mgrOgg.GetExtension(ogg) };
                    string fileName = format.FormatFileProtocollo(FormatFileProtocolloEnum.CodiceOggetto_IdComune);

                    oggetto = GetAllegatoFromDisk(ogg.Percorso, fileName, fs.DirectoryLocale);
                }
                else
                {
                    if (oggetto == null)
                        throw new Exception("L'oggetto che fa riferimento al codice " + ogg.Codiceoggetto.Value.ToString() + " ha un valore null, controllare l'allineamento degli oggetti in base alla verticalizzazione FILESYSTEM_PROTOCOLLO.");
                }

                return new AllOut
                {
                    Image = oggetto,
                    IDBase = IdAllegato,
                    ContentType = mgrOgg.GetContentType(ogg),
                    Serial = ogg.Nomefile,
                    TipoFile = mgrOgg.GetExtension(ogg)
                };

            }
            catch (Exception)
            {
                throw;
            }
        }

        /// <summary>
        /// Ottiene il buffer di dati dell'oggetto recuperato da file system
        /// </summary>
        /// <param name="percorso">Directory del file a partire dall'applicazione.</param>
        /// <param name="nomeFile">Nome del file</param>
        /// <param name="directoryLocale">Directory Locale ottenuta dalla lettura del parametro DIRECTORY_LOCALE della verticalizzazione FILESYSTEM_PROTOCOLLO</param>
        /// <returns></returns>
        public byte[] GetAllegatoFromDisk(string percorso, string nomeFile, string directoryLocale)
        {
            string directory = Path.Combine(directoryLocale, percorso);
            string filePath = Path.Combine(directory, nomeFile);

            try
            {
                byte[] res = File.ReadAllBytes(filePath);
                return res;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore verificato durante la lettura del file da filesystem. " + ex.ToString());
            }
        }

        #endregion

        #region Metodo usato per eseguire la protocollazione
        public override DatiProtocolloRes Protocollazione(Data.DatiProtocolloIn pProt)
        {
            DatiProtocolloRes pProtocollo = null;

            try
            {
                GetParametriFromVertSigepro();
                _protocolloLogs.Debug("Chiamata al metodo InserisciProtocollo");
                pProtocollo = InserisciProtocollo(pProt);
                _protocolloLogs.Debug("Fine Chiamata al metodo InserisciProtocollo");

                if (string.IsNullOrEmpty(pProtocollo.NumeroProtocollo))
                    throw new Exception();

                return pProtocollo;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }


        }

        public DatiProtocolloRes Protocollazione(Segnatura segnatura)
        {
            DatiProtocolloRes pProtocollo = null;
            DatiProtocolloIn pProt = null;
            //Fascicolo pFasc = null;

            try
            {
                pProt = CreaProtocolloFromSegnatuta(segnatura);
                pProtocollo = Protocollazione(pProt);
            }
            catch (Exception ex)
            {
                throw ex;
            }

            return pProtocollo;
        }

        private DatiProtocolloIn CreaProtocolloFromSegnatuta(Segnatura segnatura)
        {
            //Valido il file segnatura.xml
            _protocolloSerializer.Serialize("Segnatura.xml", segnatura, ProtocolloValidation.TipiValidazione.XSD, "Segnatura_SIGePro.xsd", true);
            _protocolloLogs.Debug("Creato il file segnatura.xml");

            Data.DatiProtocolloIn pProt = new Data.DatiProtocolloIn();

            //Viene creato l'oggetto Segnatura da passare in ingresso al metodo InserisciProtocollo
            //Setto l'intestazione
            CreaProtocolloIntestazione(pProt, segnatura);
            //Setto i mittenti
            CreaProtocolloMittenti(pProt, segnatura);
            //Setto i destinatari
            CreaProtocolloDestinatari(pProt, segnatura);
            //Setto gli allegati del protocollo
            CreaProtocolloAllegati(pProt, segnatura);

            return pProt;
        }

        private void CreaProtocolloAllegati(DatiProtocolloIn pProt, Segnatura segnatura)
        {
            throw new Exception("The method or operation is not implemented.");
        }

        private void CreaProtocolloDestinatari(DatiProtocolloIn pProt, Segnatura segnatura)
        {
            throw new Exception("The method or operation is not implemented.");
        }

        private void CreaProtocolloMittenti(DatiProtocolloIn pProt, Segnatura segnatura)
        {
            pProt.Mittenti = new ListaMittDest();
            pProt.Mittenti.Amministrazione = new List<Amministrazioni>();
            pProt.Mittenti.Anagrafe = new List<ProtocolloAnagrafe>();

            switch (pProt.Flusso)
            {
                case "P":
                case "I":
                    Amministrazioni amm = new Amministrazioni();
                    amm.PROT_UO = ((UnitaOrganizzativa)((Amministrazione)segnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Denominazione.Text[0];
                    amm.IDCOMUNE = DatiProtocollo.IdComune;
                    AmministrazioniMgr ammMgr = new AmministrazioniMgr(this.DatiProtocollo.Db);
                    ArrayList list = ammMgr.GetList(amm);
                    if (list != null)
                    {
                        if (list.Count > 1)
                            throw new ProtocolloException("Ci sono più amministrazioni con l'unità organizzativa settata");
                        else
                            pProt.Mittenti.Amministrazione.Add((Amministrazioni)list[0]);
                    }
                    else
                    {
                        throw new ProtocolloException("Non ci sono amministrazioni con l'unità organizzativa settata");
                    }

                    break;
                case "A":
                    ProtocolloAnagrafe anag = new ProtocolloAnagrafe();
                    anag.NOME = ((Nome)((Persona)segnatura.Intestazione.Origine.Mittente.Items[1]).Items[0]).Text[0];
                    anag.NOMINATIVO = ((Cognome)((Persona)segnatura.Intestazione.Origine.Mittente.Items[1]).Items[1]).Text[0];
                    if (((CodiceFiscale)((Persona)segnatura.Intestazione.Origine.Mittente.Items[1]).Items[2]).Text[0].Length == 16)
                        anag.CODICEFISCALE = ((CodiceFiscale)((Persona)segnatura.Intestazione.Origine.Mittente.Items[1]).Items[2]).Text[0];
                    else
                        anag.PARTITAIVA = ((CodiceFiscale)((Persona)segnatura.Intestazione.Origine.Mittente.Items[1]).Items[2]).Text[0];

                    pProt.Mittenti.Anagrafe.Add(anag);
                    break;
                default:
                    break;
            }
        }

        private void CreaProtocolloIntestazione(DatiProtocolloIn pProt, Segnatura segnatura)
        {
            _CodiceAOO = segnatura.Intestazione.Identificatore.CodiceAOO.Text[0];
            _DenominazioneAOO = segnatura.Intestazione.Identificatore.DescrizioneAOO.Text[0];

            switch (segnatura.Intestazione.Registro.tipo)
            {
                case RegistroTipo.Arrivo:
                    pProt.Flusso = "A";
                    break;
                case RegistroTipo.Partenza:
                    pProt.Flusso = "P";
                    break;
                case RegistroTipo.Interno:
                    pProt.Flusso = "I";
                    break;
            }

            //Setto l'oggetto (campo obbligatorio)
            pProt.Oggetto = segnatura.Intestazione.Oggetto.Text[0];

            //Setto la tipologia del documento (campo obbligatorio)
            pProt.TipoDocumento = segnatura.Intestazione.Parametri[0].ValoreParametro.Text[0];


            //Setto la classifica
            pProt.Classifica = segnatura.Intestazione.Classifica[0].Livello[0].Text[0];
        }


        //private void CreaSegnatura(Data.Protocollo pProt)
        //{
        //    Segnatura pSegnatura = new Segnatura();

        //    //Viene creato l'oggetto Segnatura da passare in ingresso al metodo InserisciProtocollo
        //    //Setto l'intestazione
        //    CreaSegnaturaIntestazione(pProt, pSegnatura);
        //    //Setto i mittenti
        //    CreaSegnaturaMittenti(pProt, pSegnatura);
        //    //Setto i destinatari
        //    CreaSegnaturaDestinatari(pProt, pSegnatura);
        //    //Setto gli allegati del protocollo
        //    CreaSegnaturaAllegati(pProt, pSegnatura);


        //    //Chiamata per creare eventualmente il file segnatura.xml
        //    if (_protocolloLogs.IsDebugEnabled)
        //    {
        //        _protocolloSerializer.Serialize("Segnatura.xml", pSegnatura,TipiValidazione.XSD, "Segnatura_SIGePro.xsd", true);
        //        LogMessage("Creato il file segnatura.xml");
        //    }
        //}


        private void CreaSegnaturaAllegati(DatiProtocolloIn pProt, Segnatura pSegnatura)
        {
            //Setto gli allegati
            pSegnatura.Descrizione = new Descrizione();


            //Verifico se ci sono allegati
            if (pProt.Allegati.Count >= 1)
            {
                pSegnatura.Descrizione.Item = new Documento();

                if (pProt.Allegati.Count > 1)
                {
                    pSegnatura.Descrizione.Allegati = new Documento[pProt.Allegati.Count - 1];
                }

                for (int iCount = 0; iCount < pProt.Allegati.Count; iCount++)
                {
                    if (iCount == 0)
                    {
                        //Setto il documento principale
                        //nuova versione
                        string sDescrizione = "";
                        int iPos = pProt.Allegati[iCount].Descrizione.LastIndexOf('.');
                        if (iPos != -1)
                            sDescrizione = pProt.Allegati[iCount].Descrizione.Substring(0, iPos);
                        if (sDescrizione != string.Empty)
                            ((Documento)pSegnatura.Descrizione.Item).nome = pProt.Allegati[iCount].Descrizione;
                        else
                            ((Documento)pSegnatura.Descrizione.Item).nome = pProt.Allegati[iCount].NOMEFILE;
                        //nuova versione

                        ((Documento)pSegnatura.Descrizione.Item).tipoMIME = pProt.Allegati[iCount].MimeType;
                        ((Documento)pSegnatura.Descrizione.Item).tipoRiferimento = DocumentoTipoRiferimento.cartaceo;
                    }
                    else
                    {
                        //Setto gli allegati del documento principale
                        pSegnatura.Descrizione.Allegati[iCount - 1] = new Documento();
                        //nuova versione
                        string sDescrizione = string.Empty;
                        int iPos = pProt.Allegati[iCount].Descrizione.LastIndexOf('.');
                        if (iPos != -1)
                            sDescrizione = pProt.Allegati[iCount].Descrizione.Substring(0, iPos);
                        if (sDescrizione != string.Empty)
                            ((Documento)pSegnatura.Descrizione.Allegati[iCount - 1]).nome = pProt.Allegati[iCount].Descrizione;
                        else
                            ((Documento)pSegnatura.Descrizione.Allegati[iCount - 1]).nome = pProt.Allegati[iCount].NOMEFILE;
                        //nuova versione

                        ((Documento)pSegnatura.Descrizione.Allegati[iCount - 1]).tipoMIME = pProt.Allegati[iCount].MimeType;
                        ((Documento)pSegnatura.Descrizione.Allegati[iCount - 1]).tipoRiferimento = DocumentoTipoRiferimento.cartaceo;
                    }
                }
            }
            else
            {
                pSegnatura.Descrizione.Item = new TestoDelMessaggio();
            }
        }

        private void CreaSegnaturaDestinatari(DatiProtocolloIn pProt, Segnatura pSegnatura)
        {
            int iCount = 0;
            int iIndex = 0;

            //Verifico le amministrazioni (interne ed esterne)
            if (pProt.Destinatari.Amministrazione.Count >= 1)
            {
                if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].PROT_UO))
                {
                    pSegnatura.Intestazione.Destinazione = new Destinazione[1];
                    pSegnatura.Intestazione.Destinazione[0] = new Destinazione();

                    //Setto l'indirizzo telematico
                    pSegnatura.Intestazione.Destinazione[0].IndirizzoTelematico = new IndirizzoTelematico();
                    pSegnatura.Intestazione.Destinazione[0].IndirizzoTelematico.Text = new string[1] { "" };
                    pSegnatura.Intestazione.Destinazione[0].IndirizzoTelematico.Text[0] = _IndirizzoTelematico;

                    //Setto la denominazione dell'amministrazione
                    pSegnatura.Intestazione.Destinazione[0].Destinatario = new Destinatario[1];
                    pSegnatura.Intestazione.Destinazione[0].Destinatario[0] = new Destinatario();
                    pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items = new object[2];



                    pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0] = new Amministrazione();
                    ((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Denominazione = new Denominazione();
                    ((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Denominazione.Text = new string[1] { "" };
                    ((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Denominazione.Text[0] = _DenominazioneAmm;
                    //Setto il codice dell'amministrazione
                    ((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).CodiceAmministrazione = new CodiceAmministrazione();
                    ((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).CodiceAmministrazione.Text = new string[1] { "" };
                    ((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).CodiceAmministrazione.Text[0] = _CodiceAmm;

                    //Setto la denominazione dell'AOO
                    pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[1] = new AOO();
                    ((AOO)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[1]).Denominazione = new Denominazione();
                    ((AOO)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[1]).Denominazione.Text = new string[1] { "" };
                    ((AOO)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[1]).Denominazione.Text[0] = _DenominazioneAOO;
                    //Setto il codice dell'AOO
                    ((AOO)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[1]).CodiceAOO = new CodiceAOO();
                    ((AOO)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[1]).CodiceAOO.Text = new string[1] { "" };
                    ((AOO)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[1]).CodiceAOO.Text[0] = _CodiceAOO;

                    ((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items = new UnitaOrganizzativa[1];
                    ((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items[0] = new UnitaOrganizzativa();
                    ((UnitaOrganizzativa)(((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items[0])).Denominazione = new Denominazione();
                    ((UnitaOrganizzativa)(((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items[0])).Denominazione.Text = new string[1] { "" };
                    ((UnitaOrganizzativa)(((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items[0])).Denominazione.Text[0] = pProt.Destinatari.Amministrazione[0].PROT_UO;
                    ((UnitaOrganizzativa)(((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items[0])).Items = new IndirizzoPostale[1];
                    ((UnitaOrganizzativa)(((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items[0])).Items[0] = new IndirizzoPostale();
                    ((IndirizzoPostale)((UnitaOrganizzativa)(((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items[0])).Items[0]).Items = new Denominazione[1];
                    ((IndirizzoPostale)((UnitaOrganizzativa)(((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items[0])).Items[0]).Items[0] = new Denominazione();
                    ((Denominazione)((IndirizzoPostale)((UnitaOrganizzativa)(((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items[0])).Items[0]).Items[0]).Text = new string[1] { "" };
                    if (!string.IsNullOrEmpty(pProt.Destinatari.Amministrazione[0].INDIRIZZO))
                        ((Denominazione)((IndirizzoPostale)((UnitaOrganizzativa)(((Amministrazione)pSegnatura.Intestazione.Destinazione[0].Destinatario[0].Items[0]).Items[0])).Items[0]).Items[0]).Text[0] = pProt.Destinatari.Amministrazione[0].INDIRIZZO;
                }
                else
                {
                    iCount = pProt.Destinatari.Amministrazione.Count;

                    if (pProt.Destinatari.Anagrafe != null)
                        iCount += pProt.Destinatari.Anagrafe.Count;

                    pSegnatura.Intestazione.Destinazione = new Destinazione[iCount];


                    foreach (Amministrazioni pAmministrazione in pProt.Destinatari.Amministrazione)
                    {
                        pSegnatura.Intestazione.Destinazione[iIndex] = new Destinazione();

                        //Setto l'indirizzo telematico
                        pSegnatura.Intestazione.Destinazione[iIndex].IndirizzoTelematico = new IndirizzoTelematico();
                        pSegnatura.Intestazione.Destinazione[iIndex].IndirizzoTelematico.Text = new string[1] { "" };
                        //Setto un indirizzo fittizio se non specificato in SIGePro perchè durante la validazione è obbligatorio
                        pSegnatura.Intestazione.Destinazione[iIndex].IndirizzoTelematico.Text[0] = (string.IsNullOrEmpty(pAmministrazione.EMAIL) ? "xxxx@xxx.xx" : pAmministrazione.EMAIL);

                        pSegnatura.Intestazione.Destinazione[iIndex].Destinatario = new Destinatario[1];
                        pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0] = new Destinatario();
                        pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items = new Amministrazione[1];
                        pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0] = new Amministrazione();

                        ((Amministrazione)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Denominazione = new Denominazione();
                        ((Amministrazione)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Denominazione.Text = new string[1] { "" };
                        if (!string.IsNullOrEmpty(pAmministrazione.AMMINISTRAZIONE))
                            ((Amministrazione)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Denominazione.Text[0] = pAmministrazione.AMMINISTRAZIONE;

                        ((Amministrazione)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items = new IndirizzoPostale[1];
                        ((Amministrazione)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[0] = new IndirizzoPostale();
                        ((IndirizzoPostale)(((Amministrazione)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[0])).Items = new Denominazione[1];
                        ((IndirizzoPostale)(((Amministrazione)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[0])).Items[0] = new Denominazione();
                        ((Denominazione)((IndirizzoPostale)(((Amministrazione)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[0])).Items[0]).Text = new string[1] { "" };
                        if (!string.IsNullOrEmpty(pAmministrazione.INDIRIZZO))
                            ((Denominazione)((IndirizzoPostale)(((Amministrazione)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[0])).Items[0]).Text[0] = pAmministrazione.INDIRIZZO;


                        iIndex++;
                    }
                }
            }

            //Verifico le anagrafiche
            if (pProt.Destinatari.Anagrafe.Count >= 1)
            {
                if (iCount == 0)
                {
                    iCount = pProt.Destinatari.Anagrafe.Count;

                    pSegnatura.Intestazione.Destinazione = new Destinazione[iCount];
                }

                foreach (ProtocolloAnagrafe pAnagrafe in pProt.Destinatari.Anagrafe)
                {
                    pSegnatura.Intestazione.Destinazione[iIndex] = new Destinazione();

                    //Setto l'indirizzo telematico
                    pSegnatura.Intestazione.Destinazione[iIndex].IndirizzoTelematico = new IndirizzoTelematico();
                    pSegnatura.Intestazione.Destinazione[iIndex].IndirizzoTelematico.Text = new string[1] { "" };
                    //Setto un indirizzo fittizio se non specificato in SIGePro perchè durante la validazione è obbligatorio
                    pSegnatura.Intestazione.Destinazione[iIndex].IndirizzoTelematico.Text[0] = (string.IsNullOrEmpty(pAnagrafe.EMAIL) ? "" : pAnagrafe.EMAIL);

                    pSegnatura.Intestazione.Destinazione[iIndex].Destinatario = new Destinatario[1];
                    pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0] = new Destinatario();
                    pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items = new object[1];
                    pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0] = new Persona();


                    ((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items = new object[3];
                    ((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[0] = new Nome();
                    ((Nome)((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[0]).Text = new string[1] { "" };
                    ((Nome)((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[0]).Text[0] = pAnagrafe.NOME;
                    ((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[1] = new Cognome();
                    ((Cognome)((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[1]).Text = new string[1] { "" };
                    if (!string.IsNullOrEmpty(pAnagrafe.NOMINATIVO))
                        ((Cognome)((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[1]).Text[0] = pAnagrafe.NOMINATIVO;
                    ((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[2] = new CodiceFiscale();
                    ((CodiceFiscale)((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[2]).Text = new string[1] { "" };
                    if (!string.IsNullOrEmpty(pAnagrafe.CODICEFISCALE))
                        ((CodiceFiscale)((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[2]).Text[0] = pAnagrafe.CODICEFISCALE;
                    else
                        ((CodiceFiscale)((Persona)pSegnatura.Intestazione.Destinazione[iIndex].Destinatario[0].Items[0]).Items[2]).Text[0] = pAnagrafe.PARTITAIVA;


                    iIndex++;
                }
            }
        }

        private void CreaSegnaturaMittenti(DatiProtocolloIn pProt, Segnatura pSegnatura)
        {
            //Setto l'origine
            pSegnatura.Intestazione.Origine = new Origine();
            pSegnatura.Intestazione.Origine.Mittente = new Mittente();


            //Verifico le amministrazioni (interne ed esterne)
            if (pProt.Mittenti.Amministrazione.Count >= 1)
            {
                if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].PROT_UO))
                {

                    //Setto l'indirizzo telematico
                    pSegnatura.Intestazione.Origine.IndirizzoTelematico = new IndirizzoTelematico();
                    pSegnatura.Intestazione.Origine.IndirizzoTelematico.Text = new string[1] { "" };
                    pSegnatura.Intestazione.Origine.IndirizzoTelematico.Text[0] = _IndirizzoTelematico;

                    //Setto la denominazione dell'amministrazione
                    pSegnatura.Intestazione.Origine.Mittente.Items = new object[2];
                    pSegnatura.Intestazione.Origine.Mittente.Items[0] = new Amministrazione();
                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Denominazione = new Denominazione();
                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Denominazione.Text = new string[1] { "" };
                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Denominazione.Text[0] = _DenominazioneAmm;
                    //Setto il codice dell'amministrazione
                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).CodiceAmministrazione = new CodiceAmministrazione();
                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).CodiceAmministrazione.Text = new string[1] { "" };
                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).CodiceAmministrazione.Text[0] = _CodiceAmm;


                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items = new UnitaOrganizzativa[1];
                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0] = new UnitaOrganizzativa();
                    ((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Denominazione = new Denominazione();
                    ((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Denominazione.Text = new string[1] { "" };
                    ((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Denominazione.Text[0] = ((Amministrazioni)pProt.Mittenti.Amministrazione[0]).PROT_UO;
                    ((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Items = new IndirizzoPostale[1];
                    ((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Items[0] = new IndirizzoPostale();
                    ((IndirizzoPostale)((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Items[0]).Items = new Denominazione[1];
                    ((IndirizzoPostale)((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Items[0]).Items[0] = new Denominazione();
                    ((Denominazione)((IndirizzoPostale)((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Items[0]).Items[0]).Text = new string[1] { "" };
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].INDIRIZZO))
                        ((Denominazione)((IndirizzoPostale)((UnitaOrganizzativa)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Items[0]).Items[0]).Text[0] = pProt.Mittenti.Amministrazione[0].INDIRIZZO;

                    //Setto la denominazione dell'AOO
                    pSegnatura.Intestazione.Origine.Mittente.Items[1] = new AOO();
                    ((AOO)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Denominazione = new Denominazione();
                    ((AOO)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Denominazione.Text = new string[1] { "" };
                    ((AOO)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Denominazione.Text[0] = _DenominazioneAOO;
                    //Setto il codice dell'AOO
                    ((AOO)pSegnatura.Intestazione.Origine.Mittente.Items[1]).CodiceAOO = new CodiceAOO();
                    ((AOO)pSegnatura.Intestazione.Origine.Mittente.Items[1]).CodiceAOO.Text = new string[1] { "" };
                    ((AOO)pSegnatura.Intestazione.Origine.Mittente.Items[1]).CodiceAOO.Text[0] = _CodiceAOO;
                }
                else
                {
                    //Setto l'indirizzo telematico
                    pSegnatura.Intestazione.Origine.IndirizzoTelematico = new IndirizzoTelematico();
                    pSegnatura.Intestazione.Origine.IndirizzoTelematico.Text = new string[1] { "" };
                    //Setto un indirizzo fittizio se non specificato in SIGePro perchè durante la validazione è obbligatorio
                    pSegnatura.Intestazione.Origine.IndirizzoTelematico.Text[0] = (string.IsNullOrEmpty(((Amministrazioni)pProt.Mittenti.Amministrazione[0]).EMAIL) ? "xxxx@xxx.xx" : ((Amministrazioni)pProt.Mittenti.Amministrazione[0]).EMAIL);

                    pSegnatura.Intestazione.Origine.Mittente.Items = new Amministrazione[1];
                    pSegnatura.Intestazione.Origine.Mittente.Items[0] = new Amministrazione();
                    //Setto la denominazione dell'amministrazione esterna
                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Denominazione = new Denominazione();
                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Denominazione.Text = new string[1] { "" };
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE))
                        ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Denominazione.Text[0] = pProt.Mittenti.Amministrazione[0].AMMINISTRAZIONE;


                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items = new IndirizzoPostale[1];
                    ((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0] = new IndirizzoPostale();
                    ((IndirizzoPostale)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Items = new Denominazione[1];
                    ((IndirizzoPostale)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Items[0] = new Denominazione();
                    ((Denominazione)((IndirizzoPostale)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Items[0]).Text = new string[1] { "" };
                    if (!string.IsNullOrEmpty(pProt.Mittenti.Amministrazione[0].INDIRIZZO))
                        ((Denominazione)((IndirizzoPostale)((Amministrazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Items[0]).Items[0]).Text[0] = pProt.Mittenti.Amministrazione[0].INDIRIZZO;
                }

                //Perchè nel caso che il mittente sia un'amministrazione esterna ed una anagrafica devo prendere solo la prima dal momento che il protocollo gestisce un unico mittente
                return;
            }

            //Verifico le anagrafiche
            if (pProt.Mittenti.Anagrafe.Count >= 1)
            {
                //Setto l'indirizzo telematico
                pSegnatura.Intestazione.Origine.IndirizzoTelematico = new IndirizzoTelematico();
                pSegnatura.Intestazione.Origine.IndirizzoTelematico.Text = new string[1] { "" };
                //Setto un indirizzo fittizio se non specificato in SIGePro perchè durante la validazione è obbligatorio
                pSegnatura.Intestazione.Origine.IndirizzoTelematico.Text[0] = (string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].EMAIL) ? "" : ((ProtocolloAnagrafe)pProt.Mittenti.Anagrafe[0]).EMAIL);

                pSegnatura.Intestazione.Origine.Mittente.Items = new object[2];
                pSegnatura.Intestazione.Origine.Mittente.Items[0] = new Denominazione();
                ((Denominazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Text = new string[1] { "" };
                ((Denominazione)pSegnatura.Intestazione.Origine.Mittente.Items[0]).Text[0] = pProt.Mittenti.Anagrafe[0].NOMINATIVO + (string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].NOME) ? "" : " " + pProt.Mittenti.Anagrafe[0].NOME);


                pSegnatura.Intestazione.Origine.Mittente.Items[1] = new Persona();
                ((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items = new object[3];
                ((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items[0] = new Nome();
                ((Nome)((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items[0]).Text = new string[1] { "" };
                ((Nome)((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items[0]).Text[0] = pProt.Mittenti.Anagrafe[0].NOME;
                ((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items[1] = new Cognome();
                ((Cognome)((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items[1]).Text = new string[1] { "" };
                if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].NOMINATIVO))
                    ((Cognome)((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items[1]).Text[0] = pProt.Mittenti.Anagrafe[0].NOMINATIVO;
                ((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items[2] = new CodiceFiscale();
                ((CodiceFiscale)((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items[2]).Text = new string[1] { "" };
                if (!string.IsNullOrEmpty(pProt.Mittenti.Anagrafe[0].CODICEFISCALE))
                    ((CodiceFiscale)((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items[2]).Text[0] = pProt.Mittenti.Anagrafe[0].CODICEFISCALE;
                else
                    ((CodiceFiscale)((Persona)pSegnatura.Intestazione.Origine.Mittente.Items[1]).Items[2]).Text[0] = pProt.Mittenti.Anagrafe[0].PARTITAIVA;
            }
        }

        private void CreaSegnaturaIntestazione(Data.DatiProtocolloIn pProt, Segnatura pSegnatura)
        {
            pSegnatura.Intestazione = new Intestazione();

            //Setto l'identificatore (campo obbligatorio)
            pSegnatura.Intestazione.Identificatore = new Identificatore();

            pSegnatura.Intestazione.Identificatore.CodiceAOO = new CodiceAOO();
            pSegnatura.Intestazione.Identificatore.CodiceAOO.Text = new string[1] { "" };
            pSegnatura.Intestazione.Identificatore.CodiceAOO.Text[0] = _CodiceAOO;
            pSegnatura.Intestazione.Identificatore.DescrizioneAOO = new DescrizioneAOO();
            pSegnatura.Intestazione.Identificatore.DescrizioneAOO.Text = new string[1] { "" };
            pSegnatura.Intestazione.Identificatore.DescrizioneAOO.Text[0] = _DenominazioneAOO;

            //Setto il registro (campo obbligatorio)
            pSegnatura.Intestazione.Registro = new Registro();
            switch (pProt.Flusso)
            {
                case "A":
                    pSegnatura.Intestazione.Registro.tipo = RegistroTipo.Arrivo;
                    break;
                case "P":
                    pSegnatura.Intestazione.Registro.tipo = RegistroTipo.Partenza;
                    break;
                case "I":
                    pSegnatura.Intestazione.Registro.tipo = RegistroTipo.Interno;
                    break;
            }

            //Setto l'oggetto (campo obbligatorio)
            pSegnatura.Intestazione.Oggetto = new Oggetto();
            pSegnatura.Intestazione.Oggetto.Text = new string[1];
            pSegnatura.Intestazione.Oggetto.Text[0] = pProt.Oggetto;

            //Setto la tipologia del documento (campo obbligatorio)
            pSegnatura.Intestazione.Parametri = new Parametro[1];
            pSegnatura.Intestazione.Parametri[0] = new Parametro();
            pSegnatura.Intestazione.Parametri[0].NomeParametro = new NomeParametro();
            pSegnatura.Intestazione.Parametri[0].NomeParametro.Text = new string[1] { "" };
            pSegnatura.Intestazione.Parametri[0].NomeParametro.Text[0] = "TipoDocumento";
            pSegnatura.Intestazione.Parametri[0].ValoreParametro = new ValoreParametro();
            pSegnatura.Intestazione.Parametri[0].ValoreParametro.Text = new string[1] { "" };
            pSegnatura.Intestazione.Parametri[0].ValoreParametro.Text[0] = pProt.TipoDocumento;


            //Setto la classifica
            pSegnatura.Intestazione.Classifica = new Classifica[1];
            pSegnatura.Intestazione.Classifica[0] = new Classifica();
            pSegnatura.Intestazione.Classifica[0].Livello = new Livello[1];
            pSegnatura.Intestazione.Classifica[0].Livello[0] = new Livello();
            pSegnatura.Intestazione.Classifica[0].Livello[0].Text = new string[1] { "" };
            pSegnatura.Intestazione.Classifica[0].Livello[0].Text[0] = pProt.Classifica;
        }

        private DatiProtocolloAnnullato Annullato(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloAnnullato datiProtAnn = new DatiProtocolloAnnullato();

            ProtGeneraleMgr protGeneraleMgr = new ProtGeneraleMgr(this.DatiProtocollo.Db);
            ProtGenerale protGenerale = new ProtGenerale();
            protGenerale.Idcomune = DatiProtocollo.IdComune;
            if (!string.IsNullOrEmpty(idProtocollo))
            {
                protGenerale = protGeneraleMgr.GetById(Convert.ToInt32(idProtocollo), DatiProtocollo.IdComune);
                if (protGenerale == null)
                {
                    datiProtAnn.Annullato = EnumAnnullato.warning;
                    datiProtAnn.NoteAnnullamento = "Errore: non è presente nessun protocollo di numero " + numeroProtocollo + " ed anno " + annoProtocollo;

                    return datiProtAnn;
                }
            }
            else
            {
                string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                string sNumProtocollo = sNumProtSplit[0];

                protGenerale.Pg_Anno = Convert.ToInt32(annoProtocollo);
                protGenerale.Pg_Numero = Convert.ToInt32(sNumProtocollo);

                List<ProtGenerale> list = protGeneraleMgr.GetList(protGenerale);

                if (list.Count == 0)
                {
                    datiProtAnn.Annullato = EnumAnnullato.warning;
                    datiProtAnn.NoteAnnullamento = "Errore: non è presente nessun protocollo di numero " + numeroProtocollo + " ed anno " + annoProtocollo;

                    return datiProtAnn;
                }

                if (list.Count > 1)
                {
                    datiProtAnn.Annullato = EnumAnnullato.warning;
                    datiProtAnn.NoteAnnullamento = "Errore: sono presenti più protocolli con lo stesso numero ed anno!" + "Numero: " + numeroProtocollo + ", anno: " + annoProtocollo;

                    return datiProtAnn;
                }

                protGenerale = list[0];
            }


            if (protGenerale.Pg_Annullato != "N")
            {
                datiProtAnn.Annullato = EnumAnnullato.si;
                datiProtAnn.NoteAnnullamento = string.IsNullOrEmpty(protGenerale.Pg_Noteannullamento) ? string.Empty : protGenerale.Pg_Noteannullamento;
                ProtMotiviAnnullamentoMgr protMotAnnMgr = new ProtMotiviAnnullamentoMgr(this.DatiProtocollo.Db);
                string motivoAnnullamento = protMotAnnMgr.GetById(protGenerale.Pg_Fkidmotivoannullamento.GetValueOrDefault(int.MinValue), DatiProtocollo.IdComune).MaDescrizione;
                datiProtAnn.MotivoAnnullamento = string.IsNullOrEmpty(motivoAnnullamento) ? string.Empty : motivoAnnullamento;
            }
            else
            {
                datiProtAnn.Annullato = EnumAnnullato.no;
            }


            return datiProtAnn;
        }

        private void AnnullamentoProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivo, string note)
        {
            ProtGeneraleMgr pProtGeneraleMgr = new ProtGeneraleMgr(this.DatiProtocollo.Db);
            ProtGenerale pProtGenerale = new ProtGenerale();
            pProtGenerale.Idcomune = DatiProtocollo.IdComune;
            if (!string.IsNullOrEmpty(idProtocollo))
            {
                pProtGenerale = pProtGeneraleMgr.GetById(Convert.ToInt32(idProtocollo), DatiProtocollo.IdComune);

                if (pProtGenerale == null)
                    throw new ProtocolloException("Non è presente nessun protocollo di numero " + numeroProtocollo + " ed anno " + annoProtocollo);
            }
            else
            {

                string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                string sNumProtocollo = sNumProtSplit[0];

                pProtGenerale.Pg_Anno = Convert.ToInt32(annoProtocollo);
                pProtGenerale.Pg_Numero = Convert.ToInt32(sNumProtocollo);
                List<ProtGenerale> list = pProtGeneraleMgr.GetList(pProtGenerale);

                if (list.Count == 0)
                    throw new ProtocolloException("Non è presente nessun protocollo di numero " + numeroProtocollo + " ed anno " + annoProtocollo);

                if (list.Count > 1)
                    throw new ProtocolloException("Sono presenti più protocolli con lo stesso numero ed anno!" + "Numero: " + numeroProtocollo + ", anno: " + annoProtocollo);

                pProtGenerale = list[0];
            }

            //Protocollo annullato
            pProtGenerale.Pg_Annullato = "S";
            //Motivo annullamento
            pProtGenerale.Pg_Fkidmotivoannullamento = Convert.ToInt32(motivo);
            //Note annullamento
            pProtGenerale.Pg_Noteannullamento = note;
            //Utente annullamento
            pProtGenerale.Pg_Fkutenteannullamento = Operatore;
            //Data annullamento
            pProtGenerale.Pg_Dataannullamento = DateTime.Now.ToString("yyyyMMdd");
            //Ora annullamento
            pProtGenerale.Pg_Oraannullamento = DateTime.Now.ToString("HHmm");

            pProtGeneraleMgr.Update(pProtGenerale);

        }

        private DatiProtocolloLetto GetProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            DatiProtocolloLetto pProtocolloLetto = new DatiProtocolloLetto();

            //Anno,numero e data del protocollo
            ProtGeneraleMgr pProtGeneraleMgr = new ProtGeneraleMgr(this.DatiProtocollo.Db);
            ProtGenerale pProtGenerale = new ProtGenerale();
            pProtGenerale.Idcomune = DatiProtocollo.IdComune;
            if (!string.IsNullOrEmpty(idProtocollo) || (!string.IsNullOrEmpty(numeroProtocollo) && !string.IsNullOrEmpty(annoProtocollo)))
            {
                if (!string.IsNullOrEmpty(idProtocollo))
                {
                    pProtGenerale = pProtGeneraleMgr.GetById(Convert.ToInt32(idProtocollo), DatiProtocollo.IdComune);
                    if (pProtGenerale == null)
                    {
                        throw new Exception("NESSUN PROTOCOLLO TROVATO");
                        //pProtocolloLetto.Warning = "Errore: non è stato trovato nessun protocollo con numero " + numeroProtocollo + " ed anno " + annoProtocollo;
                        //return pProtocolloLetto;
                    }

                    if (!string.IsNullOrEmpty(annoProtocollo) && !string.IsNullOrEmpty(numeroProtocollo))
                    {
                        string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                        if (pProtGenerale.Pg_Numero.ToString() != sNumProtSplit[0])
                            throw new Exception("Il numero del protocollo riletto non coincide con quello passato!" + "Numero riletto/anno riletto: " + pProtGenerale.Pg_Numero.ToString() + "/" + pProtGenerale.Pg_Anno.ToString() + ", numero passato/anno passato: " + sNumProtSplit[0] + "/" + annoProtocollo);

                        if (pProtGenerale.Pg_Anno.ToString() != annoProtocollo)
                            throw new Exception("L'anno del protocollo riletto non coincide con quello passato!" + "Numero riletto/anno riletto: " + pProtGenerale.Pg_Numero.ToString() + "/" + pProtGenerale.Pg_Anno.ToString() + ", numero passato/anno passato: " + sNumProtSplit[0] + "/" + annoProtocollo);
                    }
                }
                else
                {
                    string[] sNumProtSplit = numeroProtocollo.Split(new Char[] { '/' });
                    string sNumProtocollo = sNumProtSplit[0];

                    pProtGenerale.Pg_Anno = Convert.ToInt32(annoProtocollo);
                    pProtGenerale.Pg_Numero = Convert.ToInt32(sNumProtocollo);
                    List<ProtGenerale> list = pProtGeneraleMgr.GetList(pProtGenerale);
                    if (list.Count == 0)
                    {
                        throw new Exception("NON E' STATO TROVATO NESSUN PROTOCOLLO");
                        //pProtocolloLetto.Warning = "Errore: non è stato trovato nessun protocollo con numero " + numeroProtocollo + " ed anno " + annoProtocollo;
                        //return pProtocolloLetto;
                    }
                    if (list.Count > 1)
                    {
                        throw new Exception("TROVATO PIU' DI UN PROTOCOLLO");
                        //pProtocolloLetto.Warning = "Errore: sono stati trovati più di un protocollo con numero " + numeroProtocollo + " ed anno " + annoProtocollo;
                        //return pProtocolloLetto;
                    }

                    pProtGenerale = list[0];
                }
            }
            else
                throw new Exception("NON È POSSIBILE RILEGGERE IL PROTOCOLLO/DOCUMENTO, DATI NON COMPLETI");

            pProtocolloLetto.IdProtocollo = pProtGenerale.Pg_Id.ToString();
            pProtocolloLetto.AnnoProtocollo = pProtGenerale.Pg_Anno.ToString();
            pProtocolloLetto.NumeroProtocollo = pProtGenerale.Pg_Numero.ToString();
            //pProtocolloLetto.NumeroPratica = pProtGenerale.Pg_Numero.ToString();
            pProtocolloLetto.DataProtocollo = pProtGenerale.Pg_Dataregistrazione.Substring(6, 2) + "/" + pProtGenerale.Pg_Dataregistrazione.Substring(4, 2) + "/" + pProtGenerale.Pg_Dataregistrazione.Substring(0, 4);
            //pProtocolloLetto.DataInserimento = pProtGenerale.Pg_Dataregistrazione.Substring(6, 2) + "/" + pProtGenerale.Pg_Dataregistrazione.Substring(4, 2) + "/" + pProtGenerale.Pg_Dataregistrazione.Substring(0, 4);

            //Nullabilità del protocollo
            DatiProtocolloAnnullato datiProtAnn = Annullato(idProtocollo, annoProtocollo, numeroProtocollo);
            pProtocolloLetto.Annullato = datiProtAnn.Annullato.ToString().ToUpper();
            pProtocolloLetto.MotivoAnnullamento = datiProtAnn.MotivoAnnullamento;
            if (!string.IsNullOrEmpty(pProtGenerale.Pg_Dataannullamento))
                pProtocolloLetto.DataAnnullamento = pProtGenerale.Pg_Dataannullamento.Substring(6, 2) + "/" + pProtGenerale.Pg_Dataannullamento.Substring(4, 2) + "/" + pProtGenerale.Pg_Dataannullamento.Substring(0, 4);

            if (!string.IsNullOrEmpty(pProtGenerale.Pg_Oggetto))
                pProtocolloLetto.Oggetto = pProtGenerale.Pg_Oggetto;

            if (!string.IsNullOrEmpty(pProtGenerale.Pg_Fkidmodalita.ToString()))
            {
                //ProtModalitaProtcolloMgr pProtModProtocolloMgr = new ProtModalitaProtcolloMgr(DataBase);
                //pProtocolloLetto.Origine = pProtModProtocolloMgr.GetById(pProtGenerale.Pg_Fkidmodalita).Mp_Descrizione;

                //Mittente e Destinatario
                AmministrazioniMgr pAmmMgr = new AmministrazioniMgr(this.DatiProtocollo.Db);
                Amministrazioni pAmmDest = null;
                Amministrazioni pAmmMitt = null;
                ProtAltriDestinatariMgr pProtAltriDestMgr = new ProtAltriDestinatariMgr(this.DatiProtocollo.Db);
                ProtAltriDestinatari pProtAltriDest = new ProtAltriDestinatari();
                List<ProtAltriDestinatari> listDest = null;
                List<MittDestOut> listMittDest = new List<MittDestOut>();
                switch (pProtGenerale.Pg_Fkidmodalita)
                {
                    case 1:
                        pProtocolloLetto.Origine = "A";

                        //Mittente
                        pProtocolloLetto.MittentiDestinatari = new MittDestOut[1];
                        pProtocolloLetto.MittentiDestinatari[0] = new MittDestOut();
                        pProtocolloLetto.MittentiDestinatari[0].IdSoggetto = pProtGenerale.Pg_Fkidmittente.ToString();
                        pProtocolloLetto.MittentiDestinatari[0].CognomeNome = pProtGenerale.Pg_Mittente + " - (MITTENTE)";

                        //Destinatario
                        //Per i protocolli inseriti dal software Protocollo non c'è il destinatatio (Pg_Fkiddestinatario = null) 
                        //per il flusso A
                        if (!pProtGenerale.Pg_Fkiddestinatario.HasValue)
                            break;

                        pAmmDest = pAmmMgr.GetByIdProtocollo(DatiProtocollo.IdComune, pProtGenerale.Pg_Fkiddestinatario.GetValueOrDefault(int.MinValue), DatiProtocollo.Software, DatiProtocollo.CodiceComune);
                        if ((pAmmDest != null) && (pAmmDest.AMMINISTRAZIONE == pProtGenerale.Pg_Destinatario))
                        {
                            pProtocolloLetto.InCaricoA = pAmmDest.PROT_UO;
                            pProtocolloLetto.InCaricoA_Descrizione = pAmmDest.AMMINISTRAZIONE;
                        }
                        break;
                    case 2:
                        pProtocolloLetto.Origine = "P";
                        if (pProtGenerale.Pg_Fkidmittente.HasValue)
                        {
                            pAmmMitt = pAmmMgr.GetByIdProtocollo(DatiProtocollo.IdComune, pProtGenerale.Pg_Fkidmittente.GetValueOrDefault(int.MinValue), DatiProtocollo.Software, DatiProtocollo.CodiceComune);
                            if ((pAmmMitt != null) && (pAmmMitt.AMMINISTRAZIONE == pProtGenerale.Pg_Mittente))
                            {
                                pProtocolloLetto.MittenteInterno = pAmmMitt.PROT_UO;
                                pProtocolloLetto.MittenteInterno_Descrizione = pAmmMitt.AMMINISTRAZIONE;
                            }
                            else
                            {
                                MittDestOut mitt = new MittDestOut();
                                mitt.IdSoggetto = pProtGenerale.Pg_Fkidmittente.ToString();
                                mitt.CognomeNome = pProtGenerale.Pg_Mittente + " - (MITTENTE)";
                                listMittDest.Add(mitt);
                            }
                        }

                        if (pProtGenerale.Pg_Fkiddestinatario.HasValue)
                        {
                            MittDestOut dest = new MittDestOut();
                            dest.IdSoggetto = pProtGenerale.Pg_Fkiddestinatario.ToString();
                            dest.CognomeNome = pProtGenerale.Pg_Destinatario + " - (DESTINATARIO)";
                            listMittDest.Add(dest);
                        }

                        pProtAltriDest.Idcomune = DatiProtocollo.IdComune;
                        pProtAltriDest.Ad_Fkidprotocollo = pProtGenerale.Pg_Id;
                        listDest = pProtAltriDestMgr.GetList(pProtAltriDest);

                        foreach (ProtAltriDestinatari elem in listDest)
                        {
                            MittDestOut altriDest = new MittDestOut();
                            altriDest.IdSoggetto = elem.Ad_Fkidanagrafe.ToString();
                            altriDest.CognomeNome = elem.Ad_Destinatario + " - (DESTINATARIO)";
                            listMittDest.Add(altriDest);
                        }

                        if (listMittDest.Count != 0)
                            pProtocolloLetto.MittentiDestinatari = listMittDest.ToArray();

                        break;
                    case 3:
                        pProtocolloLetto.Origine = "I";

                        if (pProtGenerale.Pg_Fkidmittente.HasValue)
                        {
                            pAmmMitt = pAmmMgr.GetByIdProtocollo(DatiProtocollo.IdComune, pProtGenerale.Pg_Fkidmittente.GetValueOrDefault(int.MinValue), DatiProtocollo.Software, DatiProtocollo.CodiceComune);
                            if ((pAmmMitt != null) && (pAmmMitt.AMMINISTRAZIONE == pProtGenerale.Pg_Mittente))
                            {
                                pProtocolloLetto.MittenteInterno = pAmmMitt.PROT_UO;
                                pProtocolloLetto.MittenteInterno_Descrizione = pAmmMitt.AMMINISTRAZIONE;
                            }
                            else
                            {
                                MittDestOut mitt = new MittDestOut();
                                mitt.IdSoggetto = pProtGenerale.Pg_Fkidmittente.ToString();
                                mitt.CognomeNome = pProtGenerale.Pg_Mittente + " - (MITTENTE)";
                                listMittDest.Add(mitt);
                            }
                        }


                        if (pProtGenerale.Pg_Fkiddestinatario.HasValue)
                        {
                            pAmmDest = pAmmMgr.GetByIdProtocollo(DatiProtocollo.IdComune, pProtGenerale.Pg_Fkiddestinatario.GetValueOrDefault(int.MinValue), DatiProtocollo.Software, DatiProtocollo.CodiceComune);
                            if ((pAmmDest != null) && (pAmmDest.AMMINISTRAZIONE == pProtGenerale.Pg_Destinatario))
                            {
                                pProtocolloLetto.InCaricoA = pAmmDest.PROT_UO;
                                pProtocolloLetto.InCaricoA_Descrizione = pAmmDest.AMMINISTRAZIONE;
                            }
                            else
                            {
                                MittDestOut destInterno = new MittDestOut();
                                destInterno.IdSoggetto = pProtGenerale.Pg_Fkiddestinatario.ToString();
                                destInterno.CognomeNome = pProtGenerale.Pg_Destinatario + " - (DESTINATARIO)";
                                listMittDest.Add(destInterno);
                            }
                        }

                        pProtAltriDest.Idcomune = DatiProtocollo.IdComune;
                        pProtAltriDest.Ad_Fkidprotocollo = pProtGenerale.Pg_Id;
                        listDest = pProtAltriDestMgr.GetList(pProtAltriDest);

                        foreach (ProtAltriDestinatari elem in listDest)
                        {
                            MittDestOut altriDest = new MittDestOut();
                            altriDest.IdSoggetto = elem.Ad_Fkidanagrafe.ToString();
                            altriDest.CognomeNome = elem.Ad_Destinatario + " - (DESTINATARIO)";
                            listMittDest.Add(altriDest);
                        }

                        if (listMittDest.Count != 0)
                            pProtocolloLetto.MittentiDestinatari = listMittDest.ToArray();

                        break;
                }
            }
            if (pProtGenerale.Pg_Fkidclassificazione.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                pProtocolloLetto.Classifica = pProtGenerale.Pg_Fkidclassificazione.ToString();
                ProtClassificazioneMgr pProtClassificazioneMgr = new ProtClassificazioneMgr(this.DatiProtocollo.Db);
                pProtocolloLetto.Classifica_Descrizione = pProtClassificazioneMgr.GetById(pProtGenerale.Pg_Fkidclassificazione.GetValueOrDefault(int.MinValue), DatiProtocollo.IdComune).Cl_Descrizione;
            }
            if (pProtGenerale.Pg_Fkidtipologia.GetValueOrDefault(int.MinValue) != int.MinValue)
            {
                pProtocolloLetto.TipoDocumento = pProtGenerale.Pg_Fkidtipologia.ToString();
                ProtocolloTipiDocumentoMgr pProtTipProtocolloMgr = new ProtocolloTipiDocumentoMgr(this.DatiProtocollo.Db);
                var tipiProto = pProtTipProtocolloMgr.GetById(DatiProtocollo.IdComune, pProtGenerale.Pg_Fkidtipologia.ToString(), DatiProtocollo.Software, DatiProtocollo.CodiceComune);
                if (tipiProto != null)
                    pProtocolloLetto.TipoDocumento_Descrizione = tipiProto.Descrizione;
            }

            ProtAllegatiProtocolloMgr pProtAllegatiProtMgr = new ProtAllegatiProtocolloMgr(this.DatiProtocollo.Db);
            ProtAllegatiProtocollo pProtAllegatiProtocollo = new ProtAllegatiProtocollo();
            pProtAllegatiProtocollo.Idcomune = DatiProtocollo.IdComune;
            pProtAllegatiProtocollo.Ad_Dlid = pProtGenerale.Pg_Id;
            List<ProtAllegatiProtocollo> listAllegati = pProtAllegatiProtMgr.GetList(pProtAllegatiProtocollo);
            _log.Debug("Lettura dati GetProtocollo, prima degli allegati");
            if (listAllegati.Count > 0)
            {
                pProtocolloLetto.Allegati = new AllOut[listAllegati.Count];
                int iIndex = 0;
                foreach (ProtAllegatiProtocollo pAll in listAllegati)
                {
                    if (pAll.Ad_Ogid.GetValueOrDefault(int.MinValue) != int.MinValue)
                    {
                        pProtocolloLetto.Allegati[iIndex] = new AllOut();

                        ProtOggettiMgr pProtOggettiMgr = new ProtOggettiMgr(this.DatiProtocollo.Db);

                        string sql = @"select CodiceOggetto, NomeFile, IdComune, null as Oggetto, Dimensione_File, Percorso 
                                       from prot_oggetti 
                                       where idcomune = " + this.DatiProtocollo.Db.Specifics.QueryParameterName("idcomune") + " " +
                                      "and codiceoggetto = " + this.DatiProtocollo.Db.Specifics.QueryParameterName("codiceoggetto");

                        using (IDbCommand cmd = this.DatiProtocollo.Db.CreateCommand(sql))
                        {
                            cmd.Parameters.Add(this.DatiProtocollo.Db.CreateParameter("idcomune", DatiProtocollo.IdComune));
                            cmd.Parameters.Add(this.DatiProtocollo.Db.CreateParameter("codiceoggetto", pAll.Ad_Ogid.GetValueOrDefault(int.MinValue)));

                            var pProtOggetti = this.DatiProtocollo.Db.GetClass<ProtOggetti>(cmd);

                            //ProtOggetti pProtOggetti = pProtOggettiMgr.GetById(pAll.Ad_Ogid.GetValueOrDefault(int.MinValue), IdComune);
                            pProtocolloLetto.Allegati[iIndex].ContentType = pProtOggettiMgr.GetContentType(pProtOggetti);
                            //pProtocolloLetto.Allegati[iIndex].Image = pProtOggetti.Oggetto;
                            pProtocolloLetto.Allegati[iIndex].IDBase = pProtOggetti.Codiceoggetto.Value.ToString();
                            pProtocolloLetto.Allegati[iIndex].TipoFile = pProtOggettiMgr.GetExtension(pProtOggetti);
                            int iPos = pAll.Ad_Descrizione.LastIndexOf("." + pProtocolloLetto.Allegati[iIndex].TipoFile);

                            if (iPos != -1)
                                pProtocolloLetto.Allegati[iIndex].Serial = pAll.Ad_Descrizione.Substring(0, iPos);
                            else
                                pProtocolloLetto.Allegati[iIndex].Serial = pAll.Ad_Descrizione;

                            pProtocolloLetto.Allegati[iIndex].Serial += " - (" + pProtOggetti.Nomefile + ")";
                        }
                        iIndex++;
                    }
                }
            }
            _log.Debug("Lettura dati GetProtocollo, dopo degli allegati");
            return pProtocolloLetto;
        }


        private DatiProtocolloRes InserisciProtocollo(Data.DatiProtocolloIn pProt)
        {
            DatiProtocolloRes pProtocollo = new DatiProtocolloRes();
            ProtGenerale pProtGenerale = null;
            bool inTransaction = true;

            if (!this.DatiProtocollo.Db.IsInTransaction)
            {
                this.DatiProtocollo.Db.BeginTransaction();
                inTransaction = false;
            }
            try
            {
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, pProt);
                _protocolloLogs.InfoFormat("Inserimento dei dati nel protocollo generale, file: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);
                //inserisco un record nella tabella PROT_GENERALE
                pProtGenerale = InsertProtGenerale(pProt);

                if (!inTransaction)
                {
                    this.DatiProtocollo.Db.CommitTransaction();
                    inTransaction = true;
                }

                _protocolloLogs.Info("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");

                //Setto le proprietà della classe che viene restituita
                pProtocollo.IdProtocollo = pProtGenerale.Pg_Id.ToString();
                pProtocollo.AnnoProtocollo = pProtGenerale.Pg_Anno.ToString();
                pProtocollo.NumeroProtocollo = pProtGenerale.Pg_Numero.ToString();

                if (ModificaNumero)
                    pProtocollo.NumeroProtocollo = pProtocollo.NumeroProtocollo.TrimStart(new char[] { '0' });

                if (AggiungiAnno)
                    pProtocollo.NumeroProtocollo += "/" + pProtGenerale.Pg_Anno.ToString();

                pProtocollo.DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy");

                _protocolloLogs.InfoFormat("Dati protocollo restituiti, id protocollo: {0}, numero: {1}, anno: {2}, data: {3}", pProtocollo.IdProtocollo, pProtocollo.NumeroProtocollo, pProtocollo.AnnoProtocollo, pProtocollo.DataProtocollo);

                return pProtocollo;
            }
            catch (Exception ex)
            {
                if (!inTransaction)
                    this.DatiProtocollo.Db.RollbackTransaction();

                throw new Exception("ERRORE GENERATO DURANTE LA CREAZIONE DEI DATI DI PROTOCOLLO", ex);
            }
        }

        private ProtGenerale InsertProtGenerale(Data.DatiProtocolloIn protoIn)
        {
            ProtGenerale protoGen = new ProtGenerale();
            ProtGeneraleMgr protoGenMgr = new ProtGeneraleMgr(this.DatiProtocollo.Db);

            //IdComune
            protoGen.Idcomune = DatiProtocollo.IdComune;
            //Anno del protocollo
            protoGen.Pg_Anno = DateTime.Now.Year;
            //Numero del protocollo
            protoGen.Pg_Numero = protoGenMgr.GetNextNumeroProtocollo(protoGen.Pg_Anno.GetValueOrDefault(int.MinValue), protoGen.Idcomune, Convert.ToInt32(_CodiceAOO));
            //Data registrazione
            protoGen.Pg_Dataregistrazione = DateTime.Now.ToString("yyyyMMdd");
            //Ora registrazione
            protoGen.Pg_Oraregistrazione = DateTime.Now.ToString("HHmm");
            //Oggeto del protocollo
            protoGen.Pg_Oggetto = protoIn.Oggetto;
            //Mittente e destinatario  del protocollo
            switch (protoIn.Flusso)
            {
                case "A":
                    //Setto il mittente

                    if (protoIn.Mittenti.Amministrazione.Count >= 1)
                    {
                        //pProtGenerale.Pg_Fkidmittente = Convert.ToInt32(pProt.Mittenti.Amministrazione[0].CODICEAMMINISTRAZIONE);
                        //protoGen.Pg_Fkidmittente = _idMittDestAmministrazione;
                        protoGen.Pg_Mittente = protoIn.Mittenti.Amministrazione[0].AMMINISTRAZIONE;
                        protoGen.Pg_IndirizzoMittente = String.Concat(protoIn.Mittenti.Amministrazione[0].INDIRIZZO, " ", protoIn.Mittenti.Amministrazione[0].CAP, " ", protoIn.Mittenti.Amministrazione[0].CITTA).Trim();
                    }
                    else
                    {
                        protoGen.Pg_Fkidmittente = Convert.ToInt32(protoIn.Mittenti.Anagrafe[0].CODICEANAGRAFE);
                        string mittente = String.Empty;

                        if (protoIn.Mittenti.Anagrafe[0].TIPOANAGRAFE == "F")
                            mittente = protoIn.Mittenti.Anagrafe[0].NOMINATIVO + (String.IsNullOrEmpty(protoIn.Mittenti.Anagrafe[0].NOME) ? "" : " " + protoIn.Mittenti.Anagrafe[0].NOME) + " ( " + (string.IsNullOrEmpty(protoIn.Mittenti.Anagrafe[0].CODICEFISCALE) ? "" : protoIn.Mittenti.Anagrafe[0].CODICEFISCALE) + (string.IsNullOrEmpty(protoIn.Mittenti.Anagrafe[0].PARTITAIVA) ? "" : " " + protoIn.Mittenti.Anagrafe[0].PARTITAIVA) + " ) - [PF]";

                        if (protoIn.Mittenti.Anagrafe[0].TIPOANAGRAFE == "G")
                            mittente = protoIn.Mittenti.Anagrafe[0].NOMINATIVO + " ( " + (String.IsNullOrEmpty(protoIn.Mittenti.Anagrafe[0].CODICEFISCALE) ? "" : protoIn.Mittenti.Anagrafe[0].CODICEFISCALE) + (string.IsNullOrEmpty(protoIn.Mittenti.Anagrafe[0].PARTITAIVA) ? "" : " " + protoIn.Mittenti.Anagrafe[0].PARTITAIVA) + " ) - [PG]";

                        protoGen.Pg_Mittente = mittente;
                    }

                    //Setto il destinatario

                    //protoGen.Pg_Fkiddestinatario = Convert.ToInt32(protoIn.Destinatari.Amministrazione[0].CODICEAMMINISTRAZIONE);
                    //protoGen.Pg_Fkiddestinatario = _idMittDestAmministrazione;
                    protoGen.Pg_Destinatario = protoIn.Destinatari.Amministrazione[0].AMMINISTRAZIONE;

                    //pProtGenerale.Pg_Destinatario = pProt.Destinatari.Amministrazione[0].PROT_UO; E' più corretto usare l'amministrazione
                    protoGen.Pg_Fkidmodalita = 1;
                    break;
                case "P":
                    //Setto il mittente

                    //protoGen.Pg_Fkidmittente = _idMittDestAmministrazione;
                    protoGen.Pg_Mittente = protoIn.Mittenti.Amministrazione[0].AMMINISTRAZIONE;

                    //pProtGenerale.Pg_Mittente = pProt.Mittenti.Amministrazione[0].PROT_UO; E' più corretto usare l'amministrazione

                    //Setto il destinatario
                    if (protoIn.Destinatari.Amministrazione.Count >= 1)
                    {
                        //protoGen.Pg_Fkiddestinatario = _idMittDestAmministrazione;
                        protoGen.Pg_Destinatario = protoIn.Destinatari.Amministrazione[0].AMMINISTRAZIONE;
                        protoGen.Pg_IndirizzoDestinatario = String.Concat(protoIn.Destinatari.Amministrazione[0].INDIRIZZO, " ", protoIn.Destinatari.Amministrazione[0].CAP, " ", protoIn.Destinatari.Amministrazione[0].CITTA).Trim();
                    }
                    else
                    {
                        protoGen.Pg_Fkiddestinatario = Convert.ToInt32(protoIn.Destinatari.Anagrafe[0].CODICEANAGRAFE);
                        string destinatario = String.Empty;

                        if (protoIn.Destinatari.Anagrafe[0].TIPOANAGRAFE == "F")
                            destinatario = protoIn.Destinatari.Anagrafe[0].NOMINATIVO + (string.IsNullOrEmpty(protoIn.Destinatari.Anagrafe[0].NOME) ? "" : " " + protoIn.Destinatari.Anagrafe[0].NOME) + " ( " + (string.IsNullOrEmpty(protoIn.Destinatari.Anagrafe[0].CODICEFISCALE) ? "" : protoIn.Destinatari.Anagrafe[0].CODICEFISCALE) + (string.IsNullOrEmpty(protoIn.Destinatari.Anagrafe[0].PARTITAIVA) ? "" : " " + protoIn.Destinatari.Anagrafe[0].PARTITAIVA) + " ) - [PF]";

                        if (protoIn.Destinatari.Anagrafe[0].TIPOANAGRAFE == "G")
                            destinatario = protoIn.Destinatari.Anagrafe[0].NOMINATIVO + " ( " + (string.IsNullOrEmpty(protoIn.Destinatari.Anagrafe[0].CODICEFISCALE) ? "" : protoIn.Destinatari.Anagrafe[0].CODICEFISCALE) + (string.IsNullOrEmpty(protoIn.Destinatari.Anagrafe[0].PARTITAIVA) ? "" : " " + protoIn.Destinatari.Anagrafe[0].PARTITAIVA) + " ) - [PG]";

                        protoGen.Pg_Destinatario = destinatario;
                    }

                    protoGen.Pg_Fkidmodalita = 2;
                    break;
                case "I":
                    //protoGen.Pg_Fkidmittente = _idMittDestAmministrazione;
                    protoGen.Pg_Mittente = protoIn.Mittenti.Amministrazione[0].AMMINISTRAZIONE;
                    protoGen.Pg_IndirizzoMittente = String.Concat(protoIn.Mittenti.Amministrazione[0].INDIRIZZO, " ", protoIn.Mittenti.Amministrazione[0].CAP, " ", protoIn.Mittenti.Amministrazione[0].CITTA).Trim();
                    //pProtGenerale.Pg_Mittente = pProt.Mittenti.Amministrazione[0].PROT_UO; E' più corretto usare l'amministrazione

                    //protoGen.Pg_Fkiddestinatario = _idMittDestAmministrazione;
                    protoGen.Pg_Destinatario = protoIn.Destinatari.Amministrazione[0].AMMINISTRAZIONE;
                    protoGen.Pg_IndirizzoDestinatario = String.Concat(protoIn.Destinatari.Amministrazione[0].INDIRIZZO, " ", protoIn.Destinatari.Amministrazione[0].CAP, " ", protoIn.Destinatari.Amministrazione[0].CITTA).Trim();

                    //pProtGenerale.Pg_Destinatario = pProt.Destinatari.Amministrazione[0].PROT_UO; E' più corretto usare l'amministrazione
                    protoGen.Pg_Fkidmodalita = 3;
                    break;
            }
            //Flag per stabilire se il protocollo è annullato
            protoGen.Pg_Annullato = "N";
            //Tipologia del protocollo
            protoGen.Pg_Fkidtipologia = Convert.ToInt32(protoIn.TipoDocumento);
            int classifica;
            bool isConvertToNumber = int.TryParse(protoIn.Classifica, out classifica);
            if (!isConvertToNumber)
                throw new Exception("VALORE DELLA CLASSIFICA NON CORRETTO");

            //Classifica del protocollo
            protoGen.Pg_Fkidclassificazione = Convert.ToInt32(protoIn.Classifica);
            //Utente inserimento del protocollo
            protoGen.Pg_Fkutenteinserimento = Operatore;
            //AOO
            protoGen.Pg_Fkidaoo = Convert.ToInt32(_CodiceAOO);


            //Verifico se ci sono allegati
            //ed eventualmente aggiorno le tabelle PROT_ALLEGATIPROTOCOLLO e PROT_OGGETTI
            InsertProtAllegatiProtocollo(protoIn, protoGen);
            //Verifico se ci sono più destinatari tra le amministrazioni esterne e le anagrafiche
            //ed eventualmente aggiorno la tabella PROT_ALTRIDESTINATARI
            InsertProtAltriDestinatari(protoIn, protoGen);
            //Inserisco un record nella tabella PROT_ASSEGNAZIONI nel caso in cui viene protocollato un'istanza

            if (!String.IsNullOrEmpty(DatiProtocollo.CodiceIstanza))
                InsertProtAssegnazioni(protoIn, protoGen);

            //Inserisco un record nella tabella PROT_GENERALE
            _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, protoGen);
            _protocolloLogs.InfoFormat("Inserimento protocollo generale, file: {0}", ProtocolloLogsConstants.ProtocollazioneRequestFileName);
            protoGen = protoGenMgr.Insert(protoGen);

            return protoGen;
        }

        private void InsertProtAssegnazioni(Data.DatiProtocolloIn protoIn, ProtGenerale protoGen)
        {
            ProtAssegnazioni protoAss = new ProtAssegnazioni();
            protoAss.As_Dataassegnazione = DateTime.Now.ToString("yyyyMMdd");
            protoAss.As_Oraassegnazione = DateTime.Now.ToString("HHmm");

            string codiceResponsabile = DatiProtocollo.Istanza.CODICERESPONSABILE;
            string descrizioneResponsabile = Operatore;

            if (!String.IsNullOrEmpty(protoIn.Classifica))
            {
                var assegnatario = new ProtClassificazioneMgr(this.DatiProtocollo.Db).GetResponsabileProcedimento(Convert.ToInt32(protoIn.Classifica), DatiProtocollo.IdComune);
                if (assegnatario != null)
                {
                    codiceResponsabile = assegnatario.CODICEANAGRAFE;
                    descrizioneResponsabile = String.Concat(assegnatario.NOME, " ", assegnatario.NOMINATIVO);
                }
            }

            protoAss.As_Fkidanagrafe = Convert.ToInt32(codiceResponsabile);
            protoAss.As_Utenteassegnazione = descrizioneResponsabile;

            protoAss.As_Fkidprotocollo = protoGen.Pg_Id;
            protoAss.Idcomune = DatiProtocollo.IdComune;
            protoGen.ProtAssegnazioni.Add(protoAss);
        }

        public void GestisciInserimentoAllegati(VerticalizzazioneFilesystemProtocollo fs, byte[] oggetto, ProtOggetti pProtOggetti, ProtOggettiMgr pProtOggettiMgr)
        {
            if (fs.Attiva)
            {
                string codiceOggetto = pProtOggetti.Codiceoggetto.Value.ToString();

                var format = new FormatFileProtocolloStruct { IdComune = DatiProtocollo.IdComune, CodiceOggetto = codiceOggetto, Estensione = pProtOggettiMgr.GetExtension(pProtOggetti) };
                string fileName = format.FormatFileProtocollo(FormatFileProtocolloEnum.CodiceOggetto_IdComune);

                string percorso = CreatePathOggetti(codiceOggetto);

                if (String.IsNullOrEmpty(fs.DirectoryLocale))
                    throw new Exception("Parametro DirectoryLocale della verticalizzazione FILESYSTEM_PROTOCOLLO non presente.");

                SaveAllegatoToDisk(oggetto, fs.DirectoryLocale, fileName, percorso);

                pProtOggetti.Percorso = percorso;
                pProtOggettiMgr.Update(pProtOggetti);
            }
        }

        private void InsertProtAllegatiProtocollo(Data.DatiProtocolloIn pProt, ProtGenerale pProtGenerale)
        {

            var fs = new VerticalizzazioneFilesystemProtocollo(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software);

            ProtOggettiMgr pProtOggettiMgr = new ProtOggettiMgr(this.DatiProtocollo.Db);

            for (int iCount = 0; iCount < pProt.Allegati.Count; iCount++)
            {
                //non dovrebbe essere necessario
                if (pProt.Allegati[iCount].OGGETTO == null)
                    throw new ProtocolloException("Errore generato dal web method InsertProtAllegatiProtocollo del protocollo Sigepro. Metodo: InsertProtAllegatiProtocollo, modulo: ProtocolloSigepro. C'è un allegato con il campo OGGETTO null.\r\n");

                ProtOggetti pProtOggetti = new ProtOggetti();
                pProtOggetti.Idcomune = DatiProtocollo.IdComune;

                pProtOggetti.Oggetto = fs.Attiva ? null : pProt.Allegati[iCount].OGGETTO;

                pProtOggetti.DimensioneFile = pProt.Allegati[iCount].OGGETTO.Length;
                pProtOggetti.Nomefile = pProt.Allegati[iCount].NOMEFILE;

                pProtOggetti = pProtOggettiMgr.Insert(pProtOggetti);

                GestisciInserimentoAllegati(fs, pProt.Allegati[iCount].OGGETTO, pProtOggetti, pProtOggettiMgr);

                ProtAllegatiProtocollo pProtAllegatiProtocollo = new ProtAllegatiProtocollo();
                pProtAllegatiProtocollo.Idcomune = DatiProtocollo.IdComune;
                pProtAllegatiProtocollo.Ad_Ogid = pProtOggetti.Codiceoggetto;

                int iPos = pProt.Allegati[iCount].Descrizione.LastIndexOf('.' + pProt.Allegati[iCount].Extension);
                if (iPos != -1)
                    pProtAllegatiProtocollo.Ad_Descrizione = pProt.Allegati[iCount].Descrizione.Substring(0, iPos);
                else
                    //Per evitare di avere nel campo Descrizione della tabella ProtAllegatiProtocollo l'estensione
                    pProtAllegatiProtocollo.Ad_Descrizione = pProt.Allegati[iCount].Descrizione;

                //Per evitare di avere nel campo Descrizione della tabella ProtAllegatiProtocollo l'estensione
                //if (sDescrizione != "")
                //{
                //    pProtAllegatiProtocollo.Ad_Descrizione = pProt.Allegati[iCount].Descrizione;
                //}
                //else
                //{
                //    pProtAllegatiProtocollo.Ad_Descrizione = pProt.Allegati[iCount].NOMEFILE;
                //}

                //Inserito per poter validare il file xml
                pProtAllegatiProtocollo.Ad_Descrizione = pProtAllegatiProtocollo.Ad_Descrizione.Replace("\"", "");


                //Verifico che il nome non sià più lungo del campo
                if (pProtAllegatiProtocollo.Ad_Descrizione.Length > 50)
                    pProtAllegatiProtocollo.Ad_Descrizione = pProtAllegatiProtocollo.Ad_Descrizione.Substring(0, 46) + "." + pProt.Allegati[iCount].Extension;

                pProtGenerale.ProtAllegati.Add(pProtAllegatiProtocollo);
            }
        }

        private void InsertProtAltriDestinatari(Data.DatiProtocolloIn protoIn, ProtGenerale protoGen)
        {
            for (int iCount = 1; iCount < protoIn.Destinatari.Amministrazione.Count; iCount++)
            {
                ProtAltriDestinatari protoAltriDestinatari = new ProtAltriDestinatari();
                protoAltriDestinatari.Idcomune = DatiProtocollo.IdComune;
                protoAltriDestinatari.Ad_Utenteassegnazione = Operatore;
                protoAltriDestinatari.Ad_Oraassegnazione = DateTime.Now.ToString("HHmm");
                protoAltriDestinatari.Ad_Dataassegnazione = DateTime.Now.ToString("yyyyMMdd");

                protoAltriDestinatari.Ad_Fkidanagrafe = _idMittDestAmministrazione;
                protoAltriDestinatari.Ad_Destinatario = protoIn.Destinatari.Amministrazione[iCount].AMMINISTRAZIONE;

                protoGen.ProtAltriDest.Add(protoAltriDestinatari);
            }

            int idxAna = protoIn.Destinatari.Amministrazione.Count == 0 ? 1 : 0;

            for (int iCount = idxAna; iCount < protoIn.Destinatari.Anagrafe.Count; iCount++)
            {
                ProtAltriDestinatari protoAltriDestinatari = new ProtAltriDestinatari();
                protoAltriDestinatari.Idcomune = DatiProtocollo.IdComune;
                protoAltriDestinatari.Ad_Utenteassegnazione = Operatore;
                protoAltriDestinatari.Ad_Oraassegnazione = DateTime.Now.ToString("HHmm");
                protoAltriDestinatari.Ad_Dataassegnazione = DateTime.Now.ToString("yyyyMMdd");
                protoAltriDestinatari.Ad_Fkidanagrafe = Convert.ToInt32(protoIn.Destinatari.Anagrafe[iCount].CODICEANAGRAFE);
                string destinatario = string.Empty;

                if (protoIn.Destinatari.Anagrafe[0].TIPOANAGRAFE == "F")
                    destinatario = protoIn.Destinatari.Anagrafe[iCount].NOMINATIVO + (string.IsNullOrEmpty(protoIn.Destinatari.Anagrafe[iCount].NOME) ? "" : " " + protoIn.Destinatari.Anagrafe[iCount].NOME) + " ( " + (string.IsNullOrEmpty(protoIn.Destinatari.Anagrafe[iCount].CODICEFISCALE) ? "" : protoIn.Destinatari.Anagrafe[iCount].CODICEFISCALE) + (string.IsNullOrEmpty(protoIn.Destinatari.Anagrafe[iCount].PARTITAIVA) ? "" : " " + protoIn.Destinatari.Anagrafe[iCount].PARTITAIVA) + " ) - [PF]";

                if (protoIn.Destinatari.Anagrafe[iCount].TIPOANAGRAFE == "G")
                    destinatario = protoIn.Destinatari.Anagrafe[iCount].NOMINATIVO + " ( " + (string.IsNullOrEmpty(protoIn.Destinatari.Anagrafe[iCount].CODICEFISCALE) ? "" : protoIn.Destinatari.Anagrafe[iCount].CODICEFISCALE) + (string.IsNullOrEmpty(protoIn.Destinatari.Anagrafe[iCount].PARTITAIVA) ? "" : " " + protoIn.Destinatari.Anagrafe[iCount].PARTITAIVA) + " ) - [PG]";

                protoAltriDestinatari.Ad_Destinatario = destinatario;
                protoGen.ProtAltriDest.Add(protoAltriDestinatari);
            }
        }
        #endregion

        #region Upload

        /// <summary>
        /// Esegue un controllo sul percorso passato (directoryLocale + percorso), se la directory non esiste la crea e ottiene il path della directory dove sarà inserito il file.
        /// </summary>
        /// <param name="directoryLocale"></param>
        /// <param name="percorso"></param>
        /// <returns></returns>
        public string GetDirectoryPath(string directoryLocale, string percorso)
        {
            if (String.IsNullOrEmpty(directoryLocale))
                throw new Exception("Per salvare l'oggetto su file è necessario specificare il percorso, il parametro DIRECTORY_LOCALE del modulo FILESYSTEM_PROTOCOLLO non è stato specificato");

            string folderUploadPath = Path.Combine(directoryLocale, percorso);

            bool folderExist = Directory.Exists(folderUploadPath);

            if (!folderExist)
            {
                try
                {
                    Directory.CreateDirectory(folderUploadPath);
                }
                catch (Exception ex)
                {
                    throw new Exception("Errore durante la creazione della directory: " + folderUploadPath + " - Dettaglio errore: " + ex.ToString());
                }
            }

            return folderUploadPath;
        }

        /// <summary>
        /// Salva l'oggetto su file system
        /// </summary>
        /// <param name="buffer"></param>
        /// <param name="directoryLocale"></param>
        /// <param name="fileName"></param>
        /// <param name="percorso"></param>
        public void SaveAllegatoToDisk(byte[] buffer, string directoryLocale, string fileName, string percorso)
        {
            string folderUploadPath = GetDirectoryPath(directoryLocale, percorso);
            string filePath = Path.Combine(folderUploadPath, fileName);
            bool fileExist = File.Exists(filePath);

            if (fileExist)
                File.Delete(filePath);

            try
            {
                File.WriteAllBytes(filePath, buffer);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la scrittura del file: " + filePath + " - Dettaglio errore: " + ex.ToString());
            }
        }

        /// <summary>
        /// Crea il path dove salvare gli oggetti su filesystem.
        /// </summary>
        /// <param name="codiceOggetto"></param>
        /// <returns></returns>
        public string CreatePathOggetti(string codiceOggetto)
        {
            string padding = codiceOggetto.PadLeft(10, '0');
            string directory = padding.Substring(0, 4) + "\\" + padding.Substring(4, 2) + "\\" + padding.Substring(6, 2);

            return directory;
        }


        #endregion

        #region Utility

        /// <summary>
        /// Metodo usato per leggere i parametri della verticalizzazione Protocollo Sigepro
        /// </summary>
        private void GetParametriFromVertSigepro()
        {
            try
            {
                VerticalizzazioneProtocolloSigepro protocolloSigepro;

                //string tSoftware = string.IsNullOrEmpty(Software) ? GetIstanza().SOFTWARE : Software;

                protocolloSigepro = new VerticalizzazioneProtocolloSigepro(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune);

                if (protocolloSigepro.Attiva)
                {
                    _CodiceAOO = _CodiceAOO == string.Empty ? protocolloSigepro.Codiceaoo : _CodiceAOO;
                    _DenominazioneAOO = _DenominazioneAOO == string.Empty ? protocolloSigepro.Denominazioneaoo : _DenominazioneAOO;
                    _CodiceAmm = _CodiceAmm == string.Empty ? protocolloSigepro.Codiceamministrazione : _CodiceAmm;
                    _DenominazioneAmm = _DenominazioneAmm == string.Empty ? protocolloSigepro.Denominazioneamministrazione : _DenominazioneAmm;
                    _IndirizzoTelematico = _IndirizzoTelematico == string.Empty ? protocolloSigepro.Indirizzotelematico : _IndirizzoTelematico;
                }
                else
                    throw new ProtocolloException("La verticalizzazione PROTOCOLLO_SIGEPRO non è attiva.\r\n");
            }
            catch (ProtocolloException ex)
            {
                throw ex;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la lettura della verticalizzazione PROTOCOLLO_SIGEPRO. Metodo: GetParametriFromVertSigepro, modulo: ProtocolloSigepro. " + ex.Message + "\r\n");
            }
        }

        #endregion
    }
}

namespace Init.SIGePro.SegnaturaSigepro
{
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class AggiornamentoConferma
    {

        private Identificatore identificatoreField;

        private MessaggioRicevuto messaggioRicevutoField;

        private Riferimenti riferimentiField;

        private Descrizione descrizioneField;

        private string versioneField;

        public AggiornamentoConferma()
        {
            this.versioneField = "2005-03-29";
        }

        /// <remarks/>
        public Identificatore Identificatore
        {
            get
            {
                return this.identificatoreField;
            }
            set
            {
                this.identificatoreField = value;
            }
        }

        /// <remarks/>
        public MessaggioRicevuto MessaggioRicevuto
        {
            get
            {
                return this.messaggioRicevutoField;
            }
            set
            {
                this.messaggioRicevutoField = value;
            }
        }

        /// <remarks/>
        public Riferimenti Riferimenti
        {
            get
            {
                return this.riferimentiField;
            }
            set
            {
                this.riferimentiField = value;
            }
        }

        /// <remarks/>
        public Descrizione Descrizione
        {
            get
            {
                return this.descrizioneField;
            }
            set
            {
                this.descrizioneField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string versione
        {
            get
            {
                return this.versioneField;
            }
            set
            {
                this.versioneField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Identificatore
    {

        private CodiceAmministrazione codiceAmministrazioneField;

        private DescrizioneAmministrazione descrizioneAmministrazioneField;

        private CodiceAOO codiceAOOField;

        private DescrizioneAOO descrizioneAOOField;

        private NumeroRegistrazione numeroRegistrazioneField;

        private DataRegistrazione dataRegistrazioneField;

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione
        {
            get
            {
                return this.codiceAmministrazioneField;
            }
            set
            {
                this.codiceAmministrazioneField = value;
            }
        }

        /// <remarks/>
        public DescrizioneAmministrazione DescrizioneAmministrazione
        {
            get
            {
                return this.descrizioneAmministrazioneField;
            }
            set
            {
                this.descrizioneAmministrazioneField = value;
            }
        }

        /// <remarks/>
        public CodiceAOO CodiceAOO
        {
            get
            {
                return this.codiceAOOField;
            }
            set
            {
                this.codiceAOOField = value;
            }
        }

        /// <remarks/>
        public DescrizioneAOO DescrizioneAOO
        {
            get
            {
                return this.descrizioneAOOField;
            }
            set
            {
                this.descrizioneAOOField = value;
            }
        }

        /// <remarks/>
        public NumeroRegistrazione NumeroRegistrazione
        {
            get
            {
                return this.numeroRegistrazioneField;
            }
            set
            {
                this.numeroRegistrazioneField = value;
            }
        }

        /// <remarks/>
        public DataRegistrazione DataRegistrazione
        {
            get
            {
                return this.dataRegistrazioneField;
            }
            set
            {
                this.dataRegistrazioneField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class CodiceAmministrazione
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class DescrizioneAmministrazione
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class CodiceAOO
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class DescrizioneAOO
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class NumeroRegistrazione
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class DataRegistrazione
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class MessaggioRicevuto
    {

        private object[] itemsField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("DescrizioneMessaggio", typeof(DescrizioneMessaggio))]
        [System.Xml.Serialization.XmlElementAttribute("Identificatore", typeof(Identificatore))]
        [System.Xml.Serialization.XmlElementAttribute("PrimaRegistrazione", typeof(PrimaRegistrazione))]
        public object[] Items
        {
            get
            {
                return this.itemsField;
            }
            set
            {
                this.itemsField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class DescrizioneMessaggio
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class PrimaRegistrazione
    {

        private Identificatore identificatoreField;

        private AutoreProtocollazione autoreProtocollazioneField;

        private DataDocumento dataDocumentoField;

        private DataArrivo dataArrivoField;

        private OraArrivo oraArrivoField;

        /// <remarks/>
        public Identificatore Identificatore
        {
            get
            {
                return this.identificatoreField;
            }
            set
            {
                this.identificatoreField = value;
            }
        }

        /// <remarks/>
        public AutoreProtocollazione AutoreProtocollazione
        {
            get
            {
                return this.autoreProtocollazioneField;
            }
            set
            {
                this.autoreProtocollazioneField = value;
            }
        }

        /// <remarks/>
        public DataDocumento DataDocumento
        {
            get
            {
                return this.dataDocumentoField;
            }
            set
            {
                this.dataDocumentoField = value;
            }
        }

        /// <remarks/>
        public DataArrivo DataArrivo
        {
            get
            {
                return this.dataArrivoField;
            }
            set
            {
                this.dataArrivoField = value;
            }
        }

        /// <remarks/>
        public OraArrivo OraArrivo
        {
            get
            {
                return this.oraArrivoField;
            }
            set
            {
                this.oraArrivoField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class AutoreProtocollazione
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class DataDocumento
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class DataArrivo
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class OraArrivo
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Riferimenti
    {

        private object itemField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("ContestoProcedurale", typeof(ContestoProcedurale))]
        [System.Xml.Serialization.XmlElementAttribute("Messaggio", typeof(Messaggio))]
        [System.Xml.Serialization.XmlElementAttribute("Procedimento", typeof(Procedimento))]
        public object Item
        {
            get
            {
                return this.itemField;
            }
            set
            {
                this.itemField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class ContestoProcedurale
    {

        private CodiceAmministrazione codiceAmministrazioneField;

        private CodiceAOO codiceAOOField;

        private Identificativo identificativoField;

        private TipoContestoProcedurale tipoContestoProceduraleField;

        private Oggetto oggettoField;

        private Classifica[] classificaField;

        private DataAvvio dataAvvioField;

        private Note noteField;

        private string rifeField;

        private string idField;

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione
        {
            get
            {
                return this.codiceAmministrazioneField;
            }
            set
            {
                this.codiceAmministrazioneField = value;
            }
        }

        /// <remarks/>
        public CodiceAOO CodiceAOO
        {
            get
            {
                return this.codiceAOOField;
            }
            set
            {
                this.codiceAOOField = value;
            }
        }

        /// <remarks/>
        public Identificativo Identificativo
        {
            get
            {
                return this.identificativoField;
            }
            set
            {
                this.identificativoField = value;
            }
        }

        /// <remarks/>
        public TipoContestoProcedurale TipoContestoProcedurale
        {
            get
            {
                return this.tipoContestoProceduraleField;
            }
            set
            {
                this.tipoContestoProceduraleField = value;
            }
        }

        /// <remarks/>
        public Oggetto Oggetto
        {
            get
            {
                return this.oggettoField;
            }
            set
            {
                this.oggettoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Classifica")]
        public Classifica[] Classifica
        {
            get
            {
                return this.classificaField;
            }
            set
            {
                this.classificaField = value;
            }
        }

        /// <remarks/>
        public DataAvvio DataAvvio
        {
            get
            {
                return this.dataAvvioField;
            }
            set
            {
                this.dataAvvioField = value;
            }
        }

        /// <remarks/>
        public Note Note
        {
            get
            {
                return this.noteField;
            }
            set
            {
                this.noteField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "IDREF")]
        public string rife
        {
            get
            {
                return this.rifeField;
            }
            set
            {
                this.rifeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "ID")]
        public string id
        {
            get
            {
                return this.idField;
            }
            set
            {
                this.idField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Identificativo
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class TipoContestoProcedurale
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Oggetto
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Classifica
    {

        private CodiceAmministrazione codiceAmministrazioneField;

        private CodiceAOO codiceAOOField;

        private Denominazione denominazioneField;

        private Livello[] livelloField;

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione
        {
            get
            {
                return this.codiceAmministrazioneField;
            }
            set
            {
                this.codiceAmministrazioneField = value;
            }
        }

        /// <remarks/>
        public CodiceAOO CodiceAOO
        {
            get
            {
                return this.codiceAOOField;
            }
            set
            {
                this.codiceAOOField = value;
            }
        }

        /// <remarks/>
        public Denominazione Denominazione
        {
            get
            {
                return this.denominazioneField;
            }
            set
            {
                this.denominazioneField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Livello")]
        public Livello[] Livello
        {
            get
            {
                return this.livelloField;
            }
            set
            {
                this.livelloField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Denominazione
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Livello
    {

        private string nomeField;

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string nome
        {
            get
            {
                return this.nomeField;
            }
            set
            {
                this.nomeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class DataAvvio
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Note
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Messaggio
    {

        private object itemField;

        private PrimaRegistrazione primaRegistrazioneField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("DescrizioneMessaggio", typeof(DescrizioneMessaggio))]
        [System.Xml.Serialization.XmlElementAttribute("Identificatore", typeof(Identificatore))]
        public object Item
        {
            get
            {
                return this.itemField;
            }
            set
            {
                this.itemField = value;
            }
        }

        /// <remarks/>
        public PrimaRegistrazione PrimaRegistrazione
        {
            get
            {
                return this.primaRegistrazioneField;
            }
            set
            {
                this.primaRegistrazioneField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Procedimento
    {

        private CodiceAmministrazione codiceAmministrazioneField;

        private CodiceAOO codiceAOOField;

        private Identificativo identificativoField;

        private TipoProcedimento tipoProcedimentoField;

        private Oggetto oggettoField;

        private Classifica[] classificaField;

        private Responsabile responsabileField;

        private DataAvvio dataAvvioField;

        private DataTermine dataTermineField;

        private Note noteField;

        private string rifeField;

        private string idField;

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione
        {
            get
            {
                return this.codiceAmministrazioneField;
            }
            set
            {
                this.codiceAmministrazioneField = value;
            }
        }

        /// <remarks/>
        public CodiceAOO CodiceAOO
        {
            get
            {
                return this.codiceAOOField;
            }
            set
            {
                this.codiceAOOField = value;
            }
        }

        /// <remarks/>
        public Identificativo Identificativo
        {
            get
            {
                return this.identificativoField;
            }
            set
            {
                this.identificativoField = value;
            }
        }

        /// <remarks/>
        public TipoProcedimento TipoProcedimento
        {
            get
            {
                return this.tipoProcedimentoField;
            }
            set
            {
                this.tipoProcedimentoField = value;
            }
        }

        /// <remarks/>
        public Oggetto Oggetto
        {
            get
            {
                return this.oggettoField;
            }
            set
            {
                this.oggettoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Classifica")]
        public Classifica[] Classifica
        {
            get
            {
                return this.classificaField;
            }
            set
            {
                this.classificaField = value;
            }
        }

        /// <remarks/>
        public Responsabile Responsabile
        {
            get
            {
                return this.responsabileField;
            }
            set
            {
                this.responsabileField = value;
            }
        }

        /// <remarks/>
        public DataAvvio DataAvvio
        {
            get
            {
                return this.dataAvvioField;
            }
            set
            {
                this.dataAvvioField = value;
            }
        }

        /// <remarks/>
        public DataTermine DataTermine
        {
            get
            {
                return this.dataTermineField;
            }
            set
            {
                this.dataTermineField = value;
            }
        }

        /// <remarks/>
        public Note Note
        {
            get
            {
                return this.noteField;
            }
            set
            {
                this.noteField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "IDREF")]
        public string rife
        {
            get
            {
                return this.rifeField;
            }
            set
            {
                this.rifeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "ID")]
        public string id
        {
            get
            {
                return this.idField;
            }
            set
            {
                this.idField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class TipoProcedimento
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Responsabile
    {

        private Persona personaField;

        /// <remarks/>
        public Persona Persona
        {
            get
            {
                return this.personaField;
            }
            set
            {
                this.personaField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Persona
    {

        private object[] itemsField;

        private Identificativo identificativoField;

        private string rifeField;

        private string idField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("CodiceFiscale", typeof(CodiceFiscale))]
        [System.Xml.Serialization.XmlElementAttribute("Cognome", typeof(Cognome))]
        [System.Xml.Serialization.XmlElementAttribute("Denominazione", typeof(Denominazione))]
        [System.Xml.Serialization.XmlElementAttribute("Nome", typeof(Nome))]
        [System.Xml.Serialization.XmlElementAttribute("Titolo", typeof(Titolo))]
        public object[] Items
        {
            get
            {
                return this.itemsField;
            }
            set
            {
                this.itemsField = value;
            }
        }

        /// <remarks/>
        public Identificativo Identificativo
        {
            get
            {
                return this.identificativoField;
            }
            set
            {
                this.identificativoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "IDREF")]
        public string rife
        {
            get
            {
                return this.rifeField;
            }
            set
            {
                this.rifeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "ID")]
        public string id
        {
            get
            {
                return this.idField;
            }
            set
            {
                this.idField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class CodiceFiscale
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Cognome
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Nome
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Titolo
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class DataTermine
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Descrizione
    {

        private object itemField;

        private object[] allegatiField;

        private Note noteField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Documento", typeof(Documento))]
        [System.Xml.Serialization.XmlElementAttribute("TestoDelMessaggio", typeof(TestoDelMessaggio))]
        public object Item
        {
            get
            {
                return this.itemField;
            }
            set
            {
                this.itemField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlArrayItemAttribute("Documento", typeof(Documento), IsNullable = false)]
        [System.Xml.Serialization.XmlArrayItemAttribute("Fascicolo", typeof(Fascicolo), IsNullable = false)]
        public object[] Allegati
        {
            get
            {
                return this.allegatiField;
            }
            set
            {
                this.allegatiField = value;
            }
        }

        /// <remarks/>
        public Note Note
        {
            get
            {
                return this.noteField;
            }
            set
            {
                this.noteField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Documento
    {

        private CollocazioneTelematica collocazioneTelematicaField;

        private Impronta improntaField;

        private TitoloDocumento titoloDocumentoField;

        private PrimaRegistrazione primaRegistrazioneField;

        private TipoDocumento tipoDocumentoField;

        private Oggetto oggettoField;

        private Classifica[] classificaField;

        private NumeroPagine numeroPagineField;

        private Note noteField;

        private string rifeField;

        private DocumentoTipoRiferimento tipoRiferimentoField;

        private string nomeField;

        private string tipoMIMEField;

        private string idField;

        public Documento()
        {
            this.tipoRiferimentoField = DocumentoTipoRiferimento.MIME;
        }

        /// <remarks/>
        public CollocazioneTelematica CollocazioneTelematica
        {
            get
            {
                return this.collocazioneTelematicaField;
            }
            set
            {
                this.collocazioneTelematicaField = value;
            }
        }

        /// <remarks/>
        public Impronta Impronta
        {
            get
            {
                return this.improntaField;
            }
            set
            {
                this.improntaField = value;
            }
        }

        /// <remarks/>
        public TitoloDocumento TitoloDocumento
        {
            get
            {
                return this.titoloDocumentoField;
            }
            set
            {
                this.titoloDocumentoField = value;
            }
        }

        /// <remarks/>
        public PrimaRegistrazione PrimaRegistrazione
        {
            get
            {
                return this.primaRegistrazioneField;
            }
            set
            {
                this.primaRegistrazioneField = value;
            }
        }

        /// <remarks/>
        public TipoDocumento TipoDocumento
        {
            get
            {
                return this.tipoDocumentoField;
            }
            set
            {
                this.tipoDocumentoField = value;
            }
        }

        /// <remarks/>
        public Oggetto Oggetto
        {
            get
            {
                return this.oggettoField;
            }
            set
            {
                this.oggettoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Classifica")]
        public Classifica[] Classifica
        {
            get
            {
                return this.classificaField;
            }
            set
            {
                this.classificaField = value;
            }
        }

        /// <remarks/>
        public NumeroPagine NumeroPagine
        {
            get
            {
                return this.numeroPagineField;
            }
            set
            {
                this.numeroPagineField = value;
            }
        }

        /// <remarks/>
        public Note Note
        {
            get
            {
                return this.noteField;
            }
            set
            {
                this.noteField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "IDREF")]
        public string rife
        {
            get
            {
                return this.rifeField;
            }
            set
            {
                this.rifeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DocumentoTipoRiferimento.MIME)]
        public DocumentoTipoRiferimento tipoRiferimento
        {
            get
            {
                return this.tipoRiferimentoField;
            }
            set
            {
                this.tipoRiferimentoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string nome
        {
            get
            {
                return this.nomeField;
            }
            set
            {
                this.nomeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string tipoMIME
        {
            get
            {
                return this.tipoMIMEField;
            }
            set
            {
                this.tipoMIMEField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "ID")]
        public string id
        {
            get
            {
                return this.idField;
            }
            set
            {
                this.idField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class CollocazioneTelematica
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Impronta
    {

        private string codificaField;

        private string algoritmoField;

        private string[] textField;

        public Impronta()
        {
            this.codificaField = "base64";
            this.algoritmoField = "SHA-1";
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string codifica
        {
            get
            {
                return this.codificaField;
            }
            set
            {
                this.codificaField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string algoritmo
        {
            get
            {
                return this.algoritmoField;
            }
            set
            {
                this.algoritmoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class TitoloDocumento
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class TipoDocumento
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class NumeroPagine
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    public enum DocumentoTipoRiferimento
    {

        /// <remarks/>
        MIME,

        /// <remarks/>
        telematico,

        /// <remarks/>
        cartaceo,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class TestoDelMessaggio
    {

        private string tipoRiferimentoField;

        private string tipoMIMEField;

        private string idField;

        public TestoDelMessaggio()
        {
            this.tipoRiferimentoField = "MIME";
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "NMTOKEN")]
        public string tipoRiferimento
        {
            get
            {
                return this.tipoRiferimentoField;
            }
            set
            {
                this.tipoRiferimentoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string tipoMIME
        {
            get
            {
                return this.tipoMIMEField;
            }
            set
            {
                this.tipoMIMEField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string id
        {
            get
            {
                return this.idField;
            }
            set
            {
                this.idField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Fascicolo
    {

        private CodiceAmministrazione codiceAmministrazioneField;

        private CodiceAOO codiceAOOField;

        private UnitaOrganizzativa unitaOrganizzativaField;

        private Oggetto oggettoField;

        private Identificativo identificativoField;

        private Classifica[] classificaField;

        private Procedimento[] procedimentoField;

        private Note noteField;

        private object itemField;

        private string rifeField;

        private string dataCreazioneField;

        private string autoreField;

        private string idField;

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione
        {
            get
            {
                return this.codiceAmministrazioneField;
            }
            set
            {
                this.codiceAmministrazioneField = value;
            }
        }

        /// <remarks/>
        public CodiceAOO CodiceAOO
        {
            get
            {
                return this.codiceAOOField;
            }
            set
            {
                this.codiceAOOField = value;
            }
        }

        /// <remarks/>
        public UnitaOrganizzativa UnitaOrganizzativa
        {
            get
            {
                return this.unitaOrganizzativaField;
            }
            set
            {
                this.unitaOrganizzativaField = value;
            }
        }

        /// <remarks/>
        public Oggetto Oggetto
        {
            get
            {
                return this.oggettoField;
            }
            set
            {
                this.oggettoField = value;
            }
        }

        /// <remarks/>
        public Identificativo Identificativo
        {
            get
            {
                return this.identificativoField;
            }
            set
            {
                this.identificativoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Classifica")]
        public Classifica[] Classifica
        {
            get
            {
                return this.classificaField;
            }
            set
            {
                this.classificaField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Procedimento")]
        public Procedimento[] Procedimento
        {
            get
            {
                return this.procedimentoField;
            }
            set
            {
                this.procedimentoField = value;
            }
        }

        /// <remarks/>
        public Note Note
        {
            get
            {
                return this.noteField;
            }
            set
            {
                this.noteField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Documento", typeof(Documento))]
        [System.Xml.Serialization.XmlElementAttribute("Fascicolo", typeof(Fascicolo))]
        public object Item
        {
            get
            {
                return this.itemField;
            }
            set
            {
                this.itemField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "IDREF")]
        public string rife
        {
            get
            {
                return this.rifeField;
            }
            set
            {
                this.rifeField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string dataCreazione
        {
            get
            {
                return this.dataCreazioneField;
            }
            set
            {
                this.dataCreazioneField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string autore
        {
            get
            {
                return this.autoreField;
            }
            set
            {
                this.autoreField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute(DataType = "ID")]
        public string id
        {
            get
            {
                return this.idField;
            }
            set
            {
                this.idField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class UnitaOrganizzativa
    {

        private Denominazione denominazioneField;

        private Identificativo identificativoField;

        private object[] itemsField;

        private UnitaOrganizzativaTipo tipoField;

        public UnitaOrganizzativa()
        {
            this.tipoField = UnitaOrganizzativaTipo.permanente;
        }

        /// <remarks/>
        public Denominazione Denominazione
        {
            get
            {
                return this.denominazioneField;
            }
            set
            {
                this.denominazioneField = value;
            }
        }

        /// <remarks/>
        public Identificativo Identificativo
        {
            get
            {
                return this.identificativoField;
            }
            set
            {
                this.identificativoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Fax", typeof(Fax))]
        [System.Xml.Serialization.XmlElementAttribute("IndirizzoPostale", typeof(IndirizzoPostale))]
        [System.Xml.Serialization.XmlElementAttribute("IndirizzoTelematico", typeof(IndirizzoTelematico))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("Ruolo", typeof(Ruolo))]
        [System.Xml.Serialization.XmlElementAttribute("Telefono", typeof(Telefono))]
        [System.Xml.Serialization.XmlElementAttribute("UnitaOrganizzativa", typeof(UnitaOrganizzativa))]
        public object[] Items
        {
            get
            {
                return this.itemsField;
            }
            set
            {
                this.itemsField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(UnitaOrganizzativaTipo.permanente)]
        public UnitaOrganizzativaTipo tipo
        {
            get
            {
                return this.tipoField;
            }
            set
            {
                this.tipoField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Fax
    {

        private string noteField;

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string note
        {
            get
            {
                return this.noteField;
            }
            set
            {
                this.noteField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class IndirizzoPostale
    {

        private object[] itemsField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("CAP", typeof(CAP))]
        [System.Xml.Serialization.XmlElementAttribute("Civico", typeof(Civico))]
        [System.Xml.Serialization.XmlElementAttribute("Comune", typeof(Comune))]
        [System.Xml.Serialization.XmlElementAttribute("Denominazione", typeof(Denominazione))]
        [System.Xml.Serialization.XmlElementAttribute("Nazione", typeof(Nazione))]
        [System.Xml.Serialization.XmlElementAttribute("Provincia", typeof(Provincia))]
        [System.Xml.Serialization.XmlElementAttribute("Toponimo", typeof(Toponimo))]
        public object[] Items
        {
            get
            {
                return this.itemsField;
            }
            set
            {
                this.itemsField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class CAP
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Civico
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Comune
    {

        private string codiceISTATField;

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string codiceISTAT
        {
            get
            {
                return this.codiceISTATField;
            }
            set
            {
                this.codiceISTATField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Nazione
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Provincia
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Toponimo
    {

        private string dugField;

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string dug
        {
            get
            {
                return this.dugField;
            }
            set
            {
                this.dugField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class IndirizzoTelematico
    {

        private IndirizzoTelematicoTipo tipoField;

        private string noteField;

        private string[] textField;

        public IndirizzoTelematico()
        {
            this.tipoField = IndirizzoTelematicoTipo.smtp;
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(IndirizzoTelematicoTipo.smtp)]
        public IndirizzoTelematicoTipo tipo
        {
            get
            {
                return this.tipoField;
            }
            set
            {
                this.tipoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string note
        {
            get
            {
                return this.noteField;
            }
            set
            {
                this.noteField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    public enum IndirizzoTelematicoTipo
    {

        /// <remarks/>
        smtp,

        /// <remarks/>
        uri,

        /// <remarks/>
        NMTOKEN,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Ruolo
    {

        private Denominazione denominazioneField;

        private Identificativo identificativoField;

        private Persona personaField;

        /// <remarks/>
        public Denominazione Denominazione
        {
            get
            {
                return this.denominazioneField;
            }
            set
            {
                this.denominazioneField = value;
            }
        }

        /// <remarks/>
        public Identificativo Identificativo
        {
            get
            {
                return this.identificativoField;
            }
            set
            {
                this.identificativoField = value;
            }
        }

        /// <remarks/>
        public Persona Persona
        {
            get
            {
                return this.personaField;
            }
            set
            {
                this.personaField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Telefono
    {

        private string noteField;

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string note
        {
            get
            {
                return this.noteField;
            }
            set
            {
                this.noteField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    public enum UnitaOrganizzativaTipo
    {

        /// <remarks/>
        permanente,

        /// <remarks/>
        temporanea,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Allegati
    {

        private object[] itemsField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Documento", typeof(Documento))]
        [System.Xml.Serialization.XmlElementAttribute("Fascicolo", typeof(Fascicolo))]
        public object[] Items
        {
            get
            {
                return this.itemsField;
            }
            set
            {
                this.itemsField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Amministrazione
    {

        private Denominazione denominazioneField;

        private CodiceAmministrazione codiceAmministrazioneField;

        private object[] itemsField;

        /// <remarks/>
        public Denominazione Denominazione
        {
            get
            {
                return this.denominazioneField;
            }
            set
            {
                this.denominazioneField = value;
            }
        }

        /// <remarks/>
        public CodiceAmministrazione CodiceAmministrazione
        {
            get
            {
                return this.codiceAmministrazioneField;
            }
            set
            {
                this.codiceAmministrazioneField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Fax", typeof(Fax))]
        [System.Xml.Serialization.XmlElementAttribute("IndirizzoPostale", typeof(IndirizzoPostale))]
        [System.Xml.Serialization.XmlElementAttribute("IndirizzoTelematico", typeof(IndirizzoTelematico))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        [System.Xml.Serialization.XmlElementAttribute("Ruolo", typeof(Ruolo))]
        [System.Xml.Serialization.XmlElementAttribute("Telefono", typeof(Telefono))]
        [System.Xml.Serialization.XmlElementAttribute("UnitaOrganizzativa", typeof(UnitaOrganizzativa))]
        public object[] Items
        {
            get
            {
                return this.itemsField;
            }
            set
            {
                this.itemsField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class AnnullamentoProtocollazione
    {

        private Identificatore identificatoreField;

        private Motivo motivoField;

        private Provvedimento provvedimentoField;

        private string versioneField;

        public AnnullamentoProtocollazione()
        {
            this.versioneField = "2005-03-29";
        }

        /// <remarks/>
        public Identificatore Identificatore
        {
            get
            {
                return this.identificatoreField;
            }
            set
            {
                this.identificatoreField = value;
            }
        }

        /// <remarks/>
        public Motivo Motivo
        {
            get
            {
                return this.motivoField;
            }
            set
            {
                this.motivoField = value;
            }
        }

        /// <remarks/>
        public Provvedimento Provvedimento
        {
            get
            {
                return this.provvedimentoField;
            }
            set
            {
                this.provvedimentoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string versione
        {
            get
            {
                return this.versioneField;
            }
            set
            {
                this.versioneField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Motivo
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Provvedimento
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class AOO
    {

        private Denominazione denominazioneField;

        private CodiceAOO codiceAOOField;

        /// <remarks/>
        public Denominazione Denominazione
        {
            get
            {
                return this.denominazioneField;
            }
            set
            {
                this.denominazioneField = value;
            }
        }

        /// <remarks/>
        public CodiceAOO CodiceAOO
        {
            get
            {
                return this.codiceAOOField;
            }
            set
            {
                this.codiceAOOField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Assegnazione
    {

        private UnitaOrganizzativa unitaOrganizzativaField;

        private Assegnazione[] assegnazione1Field;

        private string statoField;

        /// <remarks/>
        public UnitaOrganizzativa UnitaOrganizzativa
        {
            get
            {
                return this.unitaOrganizzativaField;
            }
            set
            {
                this.unitaOrganizzativaField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Assegnazione")]
        public Assegnazione[] Assegnazione1
        {
            get
            {
                return this.assegnazione1Field;
            }
            set
            {
                this.assegnazione1Field = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string stato
        {
            get
            {
                return this.statoField;
            }
            set
            {
                this.statoField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Assegnazioni
    {

        private Assegnazione assegnazioneField;

        /// <remarks/>
        public Assegnazione Assegnazione
        {
            get
            {
                return this.assegnazioneField;
            }
            set
            {
                this.assegnazioneField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class ConfermaRicezione
    {

        private Identificatore identificatoreField;

        private MessaggioRicevuto messaggioRicevutoField;

        private Riferimenti riferimentiField;

        private Descrizione descrizioneField;

        private string versioneField;

        public ConfermaRicezione()
        {
            this.versioneField = "2005-03-29";
        }

        /// <remarks/>
        public Identificatore Identificatore
        {
            get
            {
                return this.identificatoreField;
            }
            set
            {
                this.identificatoreField = value;
            }
        }

        /// <remarks/>
        public MessaggioRicevuto MessaggioRicevuto
        {
            get
            {
                return this.messaggioRicevutoField;
            }
            set
            {
                this.messaggioRicevutoField = value;
            }
        }

        /// <remarks/>
        public Riferimenti Riferimenti
        {
            get
            {
                return this.riferimentiField;
            }
            set
            {
                this.riferimentiField = value;
            }
        }

        /// <remarks/>
        public Descrizione Descrizione
        {
            get
            {
                return this.descrizioneField;
            }
            set
            {
                this.descrizioneField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string versione
        {
            get
            {
                return this.versioneField;
            }
            set
            {
                this.versioneField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Destinatario
    {

        private object[] itemsField;

        private IndirizzoTelematico indirizzoTelematicoField;

        private Telefono[] telefonoField;

        private Fax[] faxField;

        private IndirizzoPostale indirizzoPostaleField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("AOO", typeof(AOO))]
        [System.Xml.Serialization.XmlElementAttribute("Amministrazione", typeof(Amministrazione))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        public object[] Items
        {
            get
            {
                return this.itemsField;
            }
            set
            {
                this.itemsField = value;
            }
        }

        /// <remarks/>
        public IndirizzoTelematico IndirizzoTelematico
        {
            get
            {
                return this.indirizzoTelematicoField;
            }
            set
            {
                this.indirizzoTelematicoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Telefono")]
        public Telefono[] Telefono
        {
            get
            {
                return this.telefonoField;
            }
            set
            {
                this.telefonoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Fax")]
        public Fax[] Fax
        {
            get
            {
                return this.faxField;
            }
            set
            {
                this.faxField = value;
            }
        }

        /// <remarks/>
        public IndirizzoPostale IndirizzoPostale
        {
            get
            {
                return this.indirizzoPostaleField;
            }
            set
            {
                this.indirizzoPostaleField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Destinazione
    {

        private IndirizzoTelematico indirizzoTelematicoField;

        private Destinatario[] destinatarioField;

        private DestinazioneConfermaRicezione confermaRicezioneField;

        public Destinazione()
        {
            this.confermaRicezioneField = DestinazioneConfermaRicezione.no;
        }

        /// <remarks/>
        public IndirizzoTelematico IndirizzoTelematico
        {
            get
            {
                return this.indirizzoTelematicoField;
            }
            set
            {
                this.indirizzoTelematicoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Destinatario")]
        public Destinatario[] Destinatario
        {
            get
            {
                return this.destinatarioField;
            }
            set
            {
                this.destinatarioField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(DestinazioneConfermaRicezione.no)]
        public DestinazioneConfermaRicezione confermaRicezione
        {
            get
            {
                return this.confermaRicezioneField;
            }
            set
            {
                this.confermaRicezioneField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    public enum DestinazioneConfermaRicezione
    {

        /// <remarks/>
        si,

        /// <remarks/>
        no,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class InterventoOperatore
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Intestazione
    {

        private Identificatore identificatoreField;

        private PrimaRegistrazione primaRegistrazioneField;

        private OraRegistrazione oraRegistrazioneField;

        private Origine origineField;

        private Destinazione[] destinazioneField;

        private PerConoscenza[] perConoscenzaField;

        private Risposta rispostaField;

        private Riservato riservatoField;

        private InterventoOperatore interventoOperatoreField;

        private string riferimentoDocumentiCartaceiField;

        private string riferimentiTelematiciField;

        private Registro registroField;

        private Oggetto oggettoField;

        private Classifica[] classificaField;

        private RiferimentoPadre riferimentoPadreField;

        private RiferimentoFiglio[] riferimentoFiglioField;

        private Assegnazioni[] assegnazioniField;

        private ListaFascicoli listaFascicoliField;

        private Note noteField;

        private Parametro[] parametriField;


        /// <remarks/>
        public Identificatore Identificatore
        {
            get
            {
                return this.identificatoreField;
            }
            set
            {
                this.identificatoreField = value;
            }
        }

        /// <remarks/>
        public PrimaRegistrazione PrimaRegistrazione
        {
            get
            {
                return this.primaRegistrazioneField;
            }
            set
            {
                this.primaRegistrazioneField = value;
            }
        }

        /// <remarks/>
        public OraRegistrazione OraRegistrazione
        {
            get
            {
                return this.oraRegistrazioneField;
            }
            set
            {
                this.oraRegistrazioneField = value;
            }
        }

        /// <remarks/>
        public Origine Origine
        {
            get
            {
                return this.origineField;
            }
            set
            {
                this.origineField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Destinazione")]
        public Destinazione[] Destinazione
        {
            get
            {
                return this.destinazioneField;
            }
            set
            {
                this.destinazioneField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("PerConoscenza")]
        public PerConoscenza[] PerConoscenza
        {
            get
            {
                return this.perConoscenzaField;
            }
            set
            {
                this.perConoscenzaField = value;
            }
        }

        /// <remarks/>
        public Risposta Risposta
        {
            get
            {
                return this.rispostaField;
            }
            set
            {
                this.rispostaField = value;
            }
        }

        /// <remarks/>
        public Riservato Riservato
        {
            get
            {
                return this.riservatoField;
            }
            set
            {
                this.riservatoField = value;
            }
        }

        /// <remarks/>
        public InterventoOperatore InterventoOperatore
        {
            get
            {
                return this.interventoOperatoreField;
            }
            set
            {
                this.interventoOperatoreField = value;
            }
        }

        /// <remarks/>
        public string RiferimentoDocumentiCartacei
        {
            get
            {
                return this.riferimentoDocumentiCartaceiField;
            }
            set
            {
                this.riferimentoDocumentiCartaceiField = value;
            }
        }

        /// <remarks/>
        public string RiferimentiTelematici
        {
            get
            {
                return this.riferimentiTelematiciField;
            }
            set
            {
                this.riferimentiTelematiciField = value;
            }
        }

        /// <remarks/>
        public Registro Registro
        {
            get
            {
                return this.registroField;
            }
            set
            {
                this.registroField = value;
            }
        }

        /// <remarks/>
        public Oggetto Oggetto
        {
            get
            {
                return this.oggettoField;
            }
            set
            {
                this.oggettoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Classifica")]
        public Classifica[] Classifica
        {
            get
            {
                return this.classificaField;
            }
            set
            {
                this.classificaField = value;
            }
        }

        /// <remarks/>
        public RiferimentoPadre RiferimentoPadre
        {
            get
            {
                return this.riferimentoPadreField;
            }
            set
            {
                this.riferimentoPadreField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("RiferimentoFiglio")]
        public RiferimentoFiglio[] RiferimentoFiglio
        {
            get
            {
                return this.riferimentoFiglioField;
            }
            set
            {
                this.riferimentoFiglioField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Assegnazioni")]
        public Assegnazioni[] Assegnazioni
        {
            get
            {
                return this.assegnazioniField;
            }
            set
            {
                this.assegnazioniField = value;
            }
        }

        /// <remarks/>
        public ListaFascicoli ListaFascicoli
        {
            get
            {
                return this.listaFascicoliField;
            }
            set
            {
                this.listaFascicoliField = value;
            }
        }

        /// <remarks/>
        public Note Note
        {
            get
            {
                return this.noteField;
            }
            set
            {
                this.noteField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlArrayItemAttribute("Parametro", IsNullable = false)]
        public Parametro[] Parametri
        {
            get
            {
                return this.parametriField;
            }
            set
            {
                this.parametriField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class OraRegistrazione
    {

        private OraRegistrazioneTempo tempoField;

        private string[] textField;

        public OraRegistrazione()
        {
            this.tempoField = OraRegistrazioneTempo.locale;
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(OraRegistrazioneTempo.locale)]
        public OraRegistrazioneTempo tempo
        {
            get
            {
                return this.tempoField;
            }
            set
            {
                this.tempoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    public enum OraRegistrazioneTempo
    {

        /// <remarks/>
        locale,

        /// <remarks/>
        rupa,

        /// <remarks/>
        NMTOKEN,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Origine
    {

        private IndirizzoTelematico indirizzoTelematicoField;

        private Mittente mittenteField;

        /// <remarks/>
        public IndirizzoTelematico IndirizzoTelematico
        {
            get
            {
                return this.indirizzoTelematicoField;
            }
            set
            {
                this.indirizzoTelematicoField = value;
            }
        }

        /// <remarks/>
        public Mittente Mittente
        {
            get
            {
                return this.mittenteField;
            }
            set
            {
                this.mittenteField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Mittente
    {

        private object[] itemsField;

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("AOO", typeof(AOO))]
        [System.Xml.Serialization.XmlElementAttribute("Amministrazione", typeof(Amministrazione))]
        [System.Xml.Serialization.XmlElementAttribute("Denominazione", typeof(Denominazione))]
        [System.Xml.Serialization.XmlElementAttribute("Persona", typeof(Persona))]
        public object[] Items
        {
            get
            {
                return this.itemsField;
            }
            set
            {
                this.itemsField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class PerConoscenza
    {

        private IndirizzoTelematico indirizzoTelematicoField;

        private Destinatario[] destinatarioField;

        private PerConoscenzaConfermaRicezione confermaRicezioneField;

        public PerConoscenza()
        {
            this.confermaRicezioneField = PerConoscenzaConfermaRicezione.no;
        }

        /// <remarks/>
        public IndirizzoTelematico IndirizzoTelematico
        {
            get
            {
                return this.indirizzoTelematicoField;
            }
            set
            {
                this.indirizzoTelematicoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute("Destinatario")]
        public Destinatario[] Destinatario
        {
            get
            {
                return this.destinatarioField;
            }
            set
            {
                this.destinatarioField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(PerConoscenzaConfermaRicezione.no)]
        public PerConoscenzaConfermaRicezione confermaRicezione
        {
            get
            {
                return this.confermaRicezioneField;
            }
            set
            {
                this.confermaRicezioneField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    public enum PerConoscenzaConfermaRicezione
    {

        /// <remarks/>
        si,

        /// <remarks/>
        no,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Risposta
    {

        private IndirizzoTelematico indirizzoTelematicoField;

        /// <remarks/>
        public IndirizzoTelematico IndirizzoTelematico
        {
            get
            {
                return this.indirizzoTelematicoField;
            }
            set
            {
                this.indirizzoTelematicoField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Riservato
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Registro
    {

        private RegistroTipo tipoField;

        private string[] textField;

        public Registro()
        {
            this.tipoField = RegistroTipo.Arrivo;
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        [System.ComponentModel.DefaultValueAttribute(RegistroTipo.Arrivo)]
        public RegistroTipo tipo
        {
            get
            {
                return this.tipoField;
            }
            set
            {
                this.tipoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    public enum RegistroTipo
    {

        /// <remarks/>
        Arrivo,

        /// <remarks/>
        Partenza,

        Interno,
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class RiferimentoPadre
    {

        private Identificatore identificatoreField;

        /// <remarks/>
        public Identificatore Identificatore
        {
            get
            {
                return this.identificatoreField;
            }
            set
            {
                this.identificatoreField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class RiferimentoFiglio
    {

        private Identificatore identificatoreField;

        /// <remarks/>
        public Identificatore Identificatore
        {
            get
            {
                return this.identificatoreField;
            }
            set
            {
                this.identificatoreField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class ListaFascicoli
    {

        private Fascicolo fascicoloField;

        /// <remarks/>
        public Fascicolo Fascicolo
        {
            get
            {
                return this.fascicoloField;
            }
            set
            {
                this.fascicoloField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Parametro
    {

        private NomeParametro nomeParametroField;

        private ValoreParametro valoreParametroField;

        /// <remarks/>
        public NomeParametro NomeParametro
        {
            get
            {
                return this.nomeParametroField;
            }
            set
            {
                this.nomeParametroField = value;
            }
        }

        /// <remarks/>
        public ValoreParametro ValoreParametro
        {
            get
            {
                return this.valoreParametroField;
            }
            set
            {
                this.valoreParametroField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class NomeParametro
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class ValoreParametro
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class NoteProtocollazione
    {

        private string[] textField;

        /// <remarks/>
        [System.Xml.Serialization.XmlTextAttribute()]
        public string[] Text
        {
            get
            {
                return this.textField;
            }
            set
            {
                this.textField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class NotificaEccezione
    {

        private Identificatore identificatoreField;

        private MessaggioRicevuto messaggioRicevutoField;

        private Motivo motivoField;

        private string versioneField;

        public NotificaEccezione()
        {
            this.versioneField = "2005-03-29";
        }

        /// <remarks/>
        public Identificatore Identificatore
        {
            get
            {
                return this.identificatoreField;
            }
            set
            {
                this.identificatoreField = value;
            }
        }

        /// <remarks/>
        public MessaggioRicevuto MessaggioRicevuto
        {
            get
            {
                return this.messaggioRicevutoField;
            }
            set
            {
                this.messaggioRicevutoField = value;
            }
        }

        /// <remarks/>
        public Motivo Motivo
        {
            get
            {
                return this.motivoField;
            }
            set
            {
                this.motivoField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string versione
        {
            get
            {
                return this.versioneField;
            }
            set
            {
                this.versioneField = value;
            }
        }
    }

    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("xsd", "2.0.50727.42")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(AnonymousType = true)]
    [System.Xml.Serialization.XmlRootAttribute(Namespace = "", IsNullable = false)]
    public partial class Segnatura
    {

        private Intestazione intestazioneField;

        private Riferimenti riferimentiField;

        private Descrizione descrizioneField;

        private string versioneField;

        public Segnatura()
        {
            this.versioneField = "2005-03-29";
        }

        /// <remarks/>
        public Intestazione Intestazione
        {
            get
            {
                return this.intestazioneField;
            }
            set
            {
                this.intestazioneField = value;
            }
        }

        /// <remarks/>
        public Riferimenti Riferimenti
        {
            get
            {
                return this.riferimentiField;
            }
            set
            {
                this.riferimentiField = value;
            }
        }

        /// <remarks/>
        public Descrizione Descrizione
        {
            get
            {
                return this.descrizioneField;
            }
            set
            {
                this.descrizioneField = value;
            }
        }

        /// <remarks/>
        [System.Xml.Serialization.XmlAttributeAttribute()]
        public string versione
        {
            get
            {
                return this.versioneField;
            }
            set
            {
                this.versioneField = value;
            }
        }
    }
}