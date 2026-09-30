using Init.SIGePro.Data;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    /// <summary>
    /// Descrizione di riepilogo per MailTipoMgr.
    /// </summary>
    public class MailTipoMgr : BaseManager
    {
        public MailTipoMgr(DataBase dataBase) : base(dataBase) { }

        private readonly ILog _log = LogManager.GetLogger(typeof(MailTipoMgr));

        protected int m_idsorteggio = int.MinValue;
        public int IdSorteggio
        {
            get { return this.m_idsorteggio; }
            set { this.m_idsorteggio = value; }
        }

        protected string m_idcomune = string.Empty;
        public string IdComune
        {
            get { return this.m_idcomune; }
            set { this.m_idcomune = value; }
        }

        protected string m_codiceistanza = string.Empty;
        public string CodiceIstanza
        {
            get { return this.m_codiceistanza; }
            set { this.m_codiceistanza = value; }
        }

        protected string m_codicemovimento = string.Empty;
        public string CodiceMovimento
        {
            get { return this.m_codicemovimento; }
            set { this.m_codicemovimento = value; }
        }


        #region Metodi per l'accesso di base al DB
        public MailTipo GetById(string pCODICEMAIL, string pIDCOMUNE)
        {
            var retVal = new MailTipo();
            retVal.CODICEMAIL = pCODICEMAIL;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }
        #region

        public string GetSubject(MailTipo mailTipo, Istanze istanza)
        {
            this.IdComune = istanza.IDCOMUNE;
            this.CodiceIstanza = istanza.CODICEISTANZA;

            return this.SostituisciCampi(mailTipo.OGGETTO);
        }

        public string GetSubject(MailTipo mailTipo, Movimenti movimento)
        {
            this.CodiceMovimento = movimento.CODICEMOVIMENTO;
            this.IdComune = movimento.IDCOMUNE;
            this.CodiceIstanza = movimento.CODICEISTANZA;

            return this.SostituisciCampi(mailTipo.OGGETTO);
        }

        public string GetSubject(MailTipo mailTipo)
        {
            this.IdComune = mailTipo.IDCOMUNE;
            return this.SostituisciCampi(mailTipo.OGGETTO);
        }

        public string GetBody(MailTipo mailTipo, Istanze istanza)
        {
            this.IdComune = istanza.IDCOMUNE;
            this.CodiceIstanza = istanza.CODICEISTANZA;

            return this.SostituisciCampi(mailTipo.CORPO);
        }

        public string GetBody(MailTipo mailTipo, Movimenti movimento)
        {
            this.CodiceMovimento = movimento.CODICEMOVIMENTO;
            this.IdComune = movimento.IDCOMUNE;
            this.CodiceIstanza = movimento.CODICEISTANZA;

            return this.SostituisciCampi(mailTipo.CORPO);
        }

        public string GetBody(MailTipo mailTipo)
        {
            this.IdComune = mailTipo.IDCOMUNE;
            return this.SostituisciCampi(mailTipo.CORPO);
        }

        private string SostituisciCampi(string testo)
        {
            testo = this.SostituisciCampiIstanza(testo);

            testo = this.SostituisciCampiMovimento(testo);

            testo = this.SostituisciCampiAutorizzazioni(testo);

            testo = this.SostituisciCampiPeople(testo);

            testo = this.SostituisciSorteggi(testo);

            return testo;
        }

        private string SostituisciCampiIstanza(string testoDaSostituire)
        {
            try
            {
                this._log.Debug("CodiceIstanza: " + this.CodiceIstanza);
                if (string.IsNullOrEmpty(this.CodiceIstanza))
                {
                    return testoDaSostituire;
                }

                var mIstanza = new IstanzeMgr(this.db).GetById(this.IdComune, Convert.ToInt32(this.CodiceIstanza));
                var placeholders = new Dictionary<string, string>();

                // Populate placeholders from different domain areas
                this.AddDatiGeneraliPlaceholders(placeholders, mIstanza);
                this.AddRichiedentePlaceholders(placeholders, mIstanza);
                this.AddTitolareLegalePlaceholders(placeholders, mIstanza);
                this.AddIstanzaPlaceholders(placeholders, mIstanza);
                this.AddIstanzeAreaPlaceholders(placeholders, mIstanza);
                this.AddIstanzeMappaliPlaceholders(placeholders, mIstanza);
                this.AddIstanzeStradarioPlaceholders(placeholders, mIstanza);
                this.AddMiscPlaceholders(placeholders, mIstanza);

                // Replace all placeholders
                return this.ReplacePlaceholders(testoDaSostituire, placeholders);
            }
            catch (Exception ex)
            {
                throw new Exception("SostituisciCampiIstanza:" + ex.Message);
            }
        }

        private string ReplacePlaceholders(string text, Dictionary<string, string> placeholders)
        {
            foreach (var placeholder in placeholders)
            {
                text = text.Replace(placeholder.Key, placeholder.Value ?? string.Empty);
            }
            return text;
        }

        private void AddDatiGeneraliPlaceholders(Dictionary<string, string> placeholders, Istanze mIstanza)
        {
            var confMgr = new ConfigurazioneMgr(this.db);
            var conf = confMgr.GetById(this.IdComune, mIstanza.SOFTWARE);

            placeholders["[DATISPO_DEN]"] = conf.DENOMINAZIONE;
            placeholders["[DATIGEN_COD_ACCR]"] = conf.CodiceAccreditamento;

            if (string.IsNullOrEmpty(conf.CodiceAccreditamento))
            {
                var confTT = confMgr.GetById(this.IdComune, "TT");
                placeholders["[DATIGEN_DEN]"] = confTT.DENOMINAZIONE;
                placeholders["[DATIGEN_COD_ACCR]"] = confTT.CodiceAccreditamento;
            }
        }

        private void AddRichiedentePlaceholders(Dictionary<string, string> placeholders, Istanze mIstanza)
        {
            var anagMgr = new AnagrafeMgr(this.db);
            var anagrafe = anagMgr.GetById(mIstanza.IDCOMUNE, Convert.ToInt32(mIstanza.CODICERICHIEDENTE));

            var nomeCompleto = string.IsNullOrEmpty(anagrafe.NOME)
                ? anagrafe.NOMINATIVO
                : anagrafe.NOMINATIVO + " " + anagrafe.NOME;

            placeholders["[1]"] = nomeCompleto;
            placeholders["[2]"] = anagrafe.INDIRIZZO;
            placeholders["[3]"] = anagrafe.CITTA;
            placeholders["[4]"] = anagrafe.CAP;
            placeholders["[5]"] = anagrafe.PROVINCIA;

            this._log.Debug("Sostituisci [RIC_CF]: " + anagrafe.CODICEFISCALE);
        }

        private void AddTitolareLegalePlaceholders(Dictionary<string, string> placeholders, Istanze mIstanza)
        {
            if (string.IsNullOrEmpty(mIstanza.CODICETITOLARELEGALE))
            {
                placeholders["[AZRIC_CF]"] = string.Empty;
                placeholders["[AZRIC_DEN]"] = string.Empty;
                return;
            }

            var anagMgr = new AnagrafeMgr(this.db);
            var anagTitLeg = anagMgr.GetById(this.IdComune, Convert.ToInt32(mIstanza.CODICETITOLARELEGALE));

            if (anagTitLeg != null)
            {
                placeholders["[AZRIC_CF]"] = anagTitLeg.CODICEFISCALE;

                var denominazioneTitolareLegale = !string.IsNullOrEmpty(anagTitLeg.NOMINATIVO)
                    ? anagTitLeg.NOMINATIVO
                    : string.Empty;

                if (!string.IsNullOrEmpty(anagTitLeg.FORMAGIURIDICA))
                {
                    denominazioneTitolareLegale += " " + anagTitLeg.FORMAGIURIDICA;
                }

                placeholders["[AZRIC_DEN]"] = denominazioneTitolareLegale;
            }
            else
            {
                placeholders["[AZRIC_CF]"] = string.Empty;
                placeholders["[AZRIC_DEN]"] = string.Empty;
            }
        }

        private void AddIstanzaPlaceholders(Dictionary<string, string> placeholders, Istanze mIstanza)
        {
            placeholders["[6]"] = mIstanza.DATA.Value.ToString("dd/MM/yyyy");
            placeholders["[7]"] = mIstanza.NUMEROPROTOCOLLO;
            placeholders["[8]"] = mIstanza.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).ToString("dd/MM/yyyy");
            placeholders["[9]"] = new AlberoProcMgr(this.db).GetById(Convert.ToInt32(mIstanza.CODICEINTERVENTOPROC), mIstanza.IDCOMUNE).SC_DESCRIZIONE;
            placeholders["[10]"] = new TipiProcedureMgr(this.db).GetById(mIstanza.IDCOMUNE, Convert.ToInt32(mIstanza.CODICEPROCEDURA))?.Procedura;
            placeholders["[12]"] = mIstanza.CODICELOTTO;
            placeholders["[13]"] = mIstanza.LAVORI;

            var responsabile = new ResponsabiliMgr(this.db).GetById(mIstanza.IDCOMUNE, Convert.ToInt32(mIstanza.CODICERESPONSABILE));
            placeholders["[17]"] = responsabile.RESPONSABILE;

            var responsabileproc = string.Empty;
            if (!string.IsNullOrEmpty(mIstanza.CODICERESPONSABILEPROC))
            {
                responsabile = new ResponsabiliMgr(this.db).GetById(mIstanza.IDCOMUNE, Convert.ToInt32(mIstanza.CODICERESPONSABILEPROC));
                responsabileproc = responsabile.RESPONSABILE;
            }
            placeholders["[18]"] = responsabileproc;

            placeholders["[19]"] = "[19]"; //Da fare
            placeholders["[20]"] = mIstanza.NUMEROISTANZA;
            placeholders["[21]"] = "[21]"; //Da fare: Impianti per il software SU
        }

        private void AddIstanzeAreaPlaceholders(Dictionary<string, string> placeholders, Istanze mIstanza)
        {
            var istanzeAree = new IstanzeAree
            {
                IDCOMUNE = mIstanza.IDCOMUNE,
                CODICEISTANZA = mIstanza.CODICEISTANZA,
                PRIMARIO = "1"
            };

            var listIstanzeAree = new IstanzeAreeMgr(this.db).GetList(istanzeAree);
            if (listIstanzeAree != null && listIstanzeAree.Count > 0)
            {
                istanzeAree = listIstanzeAree[0];
                var area = new AreeMgr(this.db).GetById(istanzeAree.CODICEAREA, istanzeAree.IDCOMUNE);
                placeholders["[11]"] = area.DENOMINAZIONE;
            }
        }

        private void AddIstanzeMappaliPlaceholders(Dictionary<string, string> placeholders, Istanze mIstanza)
        {
            var istMappali = new IstanzeMappali
            {
                Idcomune = mIstanza.IDCOMUNE,
                Fkcodiceistanza = Convert.ToInt32(mIstanza.CODICEISTANZA),
                Primario = 1
            };

            var listIstanzeMappali = new IstanzeMappaliMgr(this.db).GetList(istMappali);
            if (listIstanzeMappali != null && listIstanzeMappali.Count > 0)
            {
                istMappali = listIstanzeMappali[0];
                placeholders["[14]"] = istMappali.Foglio;
                placeholders["[15]"] = istMappali.Particella;
                placeholders["[16]"] = istMappali.Sub;
            }
        }

        private void AddIstanzeStradarioPlaceholders(Dictionary<string, string> placeholders, Istanze mIstanza)
        {
            var istStradario = new IstanzeStradario
            {
                IDCOMUNE = mIstanza.IDCOMUNE,
                CODICEISTANZA = mIstanza.CODICEISTANZA,
                PRIMARIO = "1"
            };

            var listIstanzeStradario = new IstanzeStradarioMgr(this.db).GetList(istStradario);
            if (listIstanzeStradario != null && listIstanzeStradario.Count > 0)
            {
                istStradario = listIstanzeStradario[0];
                placeholders["[22]"] = istStradario.CIVICO;

                var stradario = new StradarioMgr(this.db).GetById(istStradario.IDCOMUNE, Convert.ToInt32(istStradario.CODICESTRADARIO));
                placeholders["[23]"] = stradario.PREFISSO + " " + stradario.DESCRIZIONE;
            }
        }

        private void AddMiscPlaceholders(Dictionary<string, string> placeholders, Istanze mIstanza)
        {
            placeholders["[24]"] = mIstanza.PASSWORD;
            placeholders["[25]"] = this.GetFlagViaDescription(mIstanza.FLAGVIA);
            placeholders["[26]"] = this.GetVariantePRDescription(mIstanza.VARIANTEPR);
            placeholders["[27]"] = DateTime.Now.ToString("dd/MM/yyyy");

            if (!string.IsNullOrEmpty(mIstanza.CODICEPROFESSIONISTA))
            {
                var tecnico = new AnagrafeMgr(this.db).GetById(mIstanza.IDCOMUNE, Convert.ToInt32(mIstanza.CODICEPROFESSIONISTA));
                var nomeTecnico = string.IsNullOrEmpty(tecnico.NOME)
                    ? tecnico.NOMINATIVO
                    : tecnico.NOMINATIVO + " " + tecnico.NOME;
                placeholders["[28]"] = nomeTecnico;
            }

            placeholders["[29]"] = "[29]"; //Da fare
            placeholders["[30]"] = "[30]"; //Da fare
            placeholders["[31]"] = "[31]"; //Da fare
        }

        private string GetFlagViaDescription(string flagVia)
        {
            switch (flagVia)
            {
                case "0":
                    return "Non Prevista";
                case "1":
                    return "VIA Regionale";
                case "2":
                    return "VIA Nazionale";
                case "3":
                    return "Non Prevista";
                default:
                    return string.Empty;
            }
        }

        private string GetVariantePRDescription(string variantePR)
        {
            return variantePR == "1" ? "Si" : "No";
        }

        private string SostituisciCampiMovimento(string testoDaSostituire)
        {
            if (!string.IsNullOrEmpty(this.CodiceMovimento))
            {
                var mMovimento = new MovimentiMgr(this.db).GetById(this.IdComune, Convert.ToInt32(this.CodiceMovimento));

                if (mMovimento != null)
                {
                    testoDaSostituire = testoDaSostituire.Replace("[32]", mMovimento.TIPOMOVIMENTO);
                    testoDaSostituire = testoDaSostituire.Replace("[33]", ""); //Da fare
                    testoDaSostituire = testoDaSostituire.Replace("[34]", mMovimento.NUMEROPROTOCOLLO + " " + mMovimento.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).ToString("dd/MM/yyyy"));
                    testoDaSostituire = testoDaSostituire.Replace("[35]", mMovimento.ESITO);
                    testoDaSostituire = testoDaSostituire.Replace("[36]", mMovimento.PARERE);
                    testoDaSostituire = testoDaSostituire.Replace("[37]", mMovimento.DATA.Value.ToString("dd/MM/yyyy"));
                }
            }

            return testoDaSostituire;
        }
        private string SostituisciCampiAutorizzazioni(string testoDaSostituire)
        {
            var fine = 0;

            while (testoDaSostituire.IndexOf("[38(") > -1 || testoDaSostituire.IndexOf("[39(") > -1)
            {
                if (fine > 50)
                    break;

                var num_aut = string.Empty;
                var data_aut = string.Empty;

                var posStart = testoDaSostituire.IndexOf("[38(");
                if (posStart == -1)
                    posStart = testoDaSostituire.IndexOf("[39(");

                posStart += 4;
                var posEnd = testoDaSostituire.IndexOf(")]", posStart);

                var codRegistro = testoDaSostituire.Substring(posStart, posEnd - posStart);

                var filtro = new Autorizzazioni();
                filtro.IDCOMUNE = this.IdComune;
                filtro.FKIDISTANZA = this.CodiceIstanza;
                filtro.FKIDREGISTRO = codRegistro;

                var lAut = new AutorizzazioniMgr(this.db).GetList(filtro);

                //attualmente in sigepro è possibile inserire una sola autorizzazione per registro in un'istanza.
                if (lAut.Count > 0)
                {
                    var a = lAut[0];

                    num_aut = a.AUTORIZNUMERO;
                    data_aut = a.AUTORIZDATA.GetValueOrDefault(DateTime.MinValue).ToString("dd/MM/yyyy");
                }

                testoDaSostituire = testoDaSostituire.Replace("38[(" + codRegistro + ")]", num_aut);
                testoDaSostituire = testoDaSostituire.Replace("39[(" + codRegistro + ")]", data_aut);

                fine += 1;
            }

            return testoDaSostituire;
        }
        private string SostituisciCampiPeople(string testoDaSostituire)
        {
            if (testoDaSostituire.IndexOf("[40]") > -1)
            {
                var codicepeople = string.Empty;

                var cmdText = "select " +
                                    "codicepeople " +
                                 "from " +
                                    "istanzepeoplet, istanzepeopled " +
                                "where " +
                                    "istanzepeoplet.idcomune = istanzepeopled.idcomune and " +
                                    "istanzepeoplet.id = istanzepeopled.fkid and " +
                                    "istanzepeopled.idcomune = '" + this.IdComune + "' and " +
                                    "istanzepeopled.codiceistanza = " + this.CodiceIstanza;

                using (var cmd = this.db.CreateCommand(cmdText))
                {
                    using (var dr = cmd.ExecuteReader())
                    {
                        if (dr.Read())
                        {
                            codicepeople = dr["codicepeople"].ToString();
                        }
                    }
                }

                testoDaSostituire = testoDaSostituire.Replace("[40]", codicepeople);
            }
            return testoDaSostituire;
        }
        private string SostituisciSorteggi(string testoDaSostituire)
        {
            if (this.IdSorteggio > int.MinValue)
            {
                if (testoDaSostituire.IndexOf("[41]") > -1 || testoDaSostituire.IndexOf("[42]") > -1)
                {
                    var st = new SorteggiTestataMgr(this.db).GetById(this.IdSorteggio, this.IdComune);

                    testoDaSostituire = testoDaSostituire.Replace("[41]", st.StDatasorteggio.GetValueOrDefault(DateTime.MinValue).ToString("dd/MM/yyyy"));
                    testoDaSostituire = testoDaSostituire.Replace("[42]", st.StDescrizione);
                }
            }
            return testoDaSostituire;
        }

        #endregion

        #endregion
    }
}
