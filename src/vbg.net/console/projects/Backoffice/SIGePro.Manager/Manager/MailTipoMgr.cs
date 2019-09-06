using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using System;
using System.Collections.Generic;
using System.Data;
using log4net;

namespace Init.SIGePro.Manager 
{
	/// <summary>
	/// Descrizione di riepilogo per MailTipoMgr.
	/// </summary>
	public class MailTipoMgr : BaseManager
	{
		public MailTipoMgr( DataBase dataBase ) : base( dataBase ) {}

        ILog _log = LogManager.GetLogger(typeof(MailTipoMgr));

        protected int m_idsorteggio = int.MinValue;
        public int IdSorteggio
        {
            get { return m_idsorteggio; }
            set { m_idsorteggio = value; }
        }

        protected string m_idcomune = string.Empty;
        public string IdComune
        {
            get { return m_idcomune; }
            set { m_idcomune = value; }
        }

        protected string m_codiceistanza = string.Empty;
        public string CodiceIstanza
        {
            get { return m_codiceistanza; }
            set { m_codiceistanza = value; }
        }

        protected string m_codicemovimento = string.Empty;
        public string CodiceMovimento
        {
            get { return m_codicemovimento; }
            set { m_codicemovimento = value; }
        }


		#region Metodi per l'accesso di base al DB
		public MailTipo GetById(string pCODICEMAIL, string pIDCOMUNE)
		{
			MailTipo retVal = new MailTipo();
			retVal.CODICEMAIL = pCODICEMAIL;
			retVal.IDCOMUNE = pIDCOMUNE;

			DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as MailTipo;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(MailTipo p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(MailTipo p_class, MailTipo p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
        }

        #region
        
        public string GetSubject(MailTipo mailTipo, Istanze istanza)
        {
            IdComune = istanza.IDCOMUNE;
            CodiceIstanza = istanza.CODICEISTANZA;

            return SostituisciCampi(mailTipo.OGGETTO);
        }

        public string GetSubject(MailTipo mailTipo, Movimenti movimento)
        {
            CodiceMovimento = movimento.CODICEMOVIMENTO;
            IdComune = movimento.IDCOMUNE;
            CodiceIstanza = movimento.CODICEISTANZA;

            return SostituisciCampi(mailTipo.OGGETTO);
        }

        public string GetSubject(MailTipo mailTipo)
        {
            IdComune = mailTipo.IDCOMUNE;
            return SostituisciCampi(mailTipo.OGGETTO);
        }

        public string GetBody(MailTipo mailTipo, Istanze istanza)
        {
            IdComune = istanza.IDCOMUNE;
            CodiceIstanza = istanza.CODICEISTANZA;

            return SostituisciCampi(mailTipo.CORPO);
        }

        public string GetBody(MailTipo mailTipo, Movimenti movimento)
        {
            CodiceMovimento = movimento.CODICEMOVIMENTO;
            IdComune = movimento.IDCOMUNE;
            CodiceIstanza = movimento.CODICEISTANZA;

            return SostituisciCampi(mailTipo.CORPO);
        }

        public string GetBody(MailTipo mailTipo)
        {
            IdComune = mailTipo.IDCOMUNE;
            return SostituisciCampi(mailTipo.CORPO);
        }

        private string SostituisciCampi(string testo)
        {
            testo = SostituisciCampiIstanza(testo);

            testo = SostituisciCampiMovimento(testo);

            testo = SostituisciCampiAutorizzazioni(testo);

            testo = SostituisciCampiPeople(testo);

            testo = SostituisciSorteggi(testo);
            
            return testo;
        }

        private string SostituisciCampiIstanza(string testoDaSostituire)
        {
            try
            {
                _log.Debug("CodiceIstanza: " + CodiceIstanza);
                if (!string.IsNullOrEmpty(CodiceIstanza))
                {
                    Istanze mIstanza = new IstanzeMgr(db).GetById(IdComune, Convert.ToInt32(CodiceIstanza));
                    

                    var confMgr = new ConfigurazioneMgr(db);
                    Configurazione conf = confMgr.GetById(IdComune, mIstanza.SOFTWARE);

                    #region Dati Generali

                    testoDaSostituire = testoDaSostituire.Replace("[DATISPO_DEN]", conf.DENOMINAZIONE);
                    testoDaSostituire = testoDaSostituire.Replace("[DATIGEN_COD_ACCR]", conf.CodiceAccreditamento);

                    if (String.IsNullOrEmpty(conf.CodiceAccreditamento))
                    {
                        Configurazione confTT = confMgr.GetById(IdComune, "TT");

                        testoDaSostituire = testoDaSostituire.Replace("[DATIGEN_DEN]", confTT.DENOMINAZIONE);
                        testoDaSostituire = testoDaSostituire.Replace("[DATIGEN_COD_ACCR]", confTT.CodiceAccreditamento);
                    }
                    
                    #endregion

                    var anagMgr = new AnagrafeMgr(db);

                    #region Dati del richiedente e dell'azienda
                    Anagrafe anagrafe = anagMgr.GetById(mIstanza.IDCOMUNE, Convert.ToInt32(mIstanza.CODICERICHIEDENTE));
                    testoDaSostituire = testoDaSostituire.Replace("[1]", (string.IsNullOrEmpty(anagrafe.NOME) ? anagrafe.NOMINATIVO : anagrafe.NOMINATIVO + " " + anagrafe.NOME));
                    testoDaSostituire = testoDaSostituire.Replace("[2]", anagrafe.INDIRIZZO);
                    testoDaSostituire = testoDaSostituire.Replace("[3]", anagrafe.CITTA);
                    testoDaSostituire = testoDaSostituire.Replace("[4]", anagrafe.CAP);
                    testoDaSostituire = testoDaSostituire.Replace("[5]", anagrafe.PROVINCIA);

                    _log.Debug("Sostituisci [RIC_CF]: " + anagrafe.CODICEFISCALE);

                    //testoDaSostituire = testoDaSostituire.Replace("[RIC_CF]", String.IsNullOrEmpty(anagrafe.CODICEFISCALE) ? "" : anagrafe.CODICEFISCALE);

                    #endregion

                    #region Dati del Titolare Legale

                    if (!String.IsNullOrEmpty(mIstanza.CODICETITOLARELEGALE))
                    {
                        Anagrafe anagTitLeg = anagMgr.GetById(IdComune, Convert.ToInt32(mIstanza.CODICETITOLARELEGALE));

                        if (anagTitLeg != null)
                        {
                            testoDaSostituire = testoDaSostituire.Replace("[AZRIC_CF]", anagTitLeg.CODICEFISCALE);

                            string denominazioneTitolareLegale = !String.IsNullOrEmpty(anagTitLeg.NOMINATIVO) ? anagTitLeg.NOMINATIVO : String.Empty;
                            denominazioneTitolareLegale += !String.IsNullOrEmpty(anagTitLeg.FORMAGIURIDICA) ? " " + anagTitLeg.FORMAGIURIDICA : String.Empty;

                            testoDaSostituire = testoDaSostituire.Replace("[AZRIC_DEN]", denominazioneTitolareLegale);
                        }
                        else
                        {
                            testoDaSostituire = testoDaSostituire.Replace("[AZRIC_CF]", String.Empty);
                            testoDaSostituire = testoDaSostituire.Replace("[AZRIC_DEN]", String.Empty);
                        }
                    }
                    else
                    {
                        testoDaSostituire = testoDaSostituire.Replace("[AZRIC_CF]", String.Empty);
                        testoDaSostituire = testoDaSostituire.Replace("[AZRIC_DEN]", String.Empty);
                    }
                    #endregion

                    #region Dati dell'istanza
                    testoDaSostituire = testoDaSostituire.Replace("[6]", mIstanza.DATA.Value.ToString("dd/MM/yyyy"));
                    testoDaSostituire = testoDaSostituire.Replace("[7]", mIstanza.NUMEROPROTOCOLLO);
                    testoDaSostituire = testoDaSostituire.Replace("[8]", mIstanza.DATAPROTOCOLLO.GetValueOrDefault(DateTime.MinValue).ToString("dd/MM/yyyy"));
                    testoDaSostituire = testoDaSostituire.Replace("[9]", new AlberoProcMgr(db).GetById(Convert.ToInt32(mIstanza.CODICEINTERVENTOPROC), mIstanza.IDCOMUNE).SC_DESCRIZIONE);
                    testoDaSostituire = testoDaSostituire.Replace("[10]", new TipiProcedureMgr(db).GetById(mIstanza.CODICEPROCEDURA, mIstanza.IDCOMUNE).Procedura);
                    testoDaSostituire = testoDaSostituire.Replace("[12]", mIstanza.CODICELOTTO);
                    testoDaSostituire = testoDaSostituire.Replace("[13]", mIstanza.LAVORI);
                    
                    Responsabili responsabile = new ResponsabiliMgr(db).GetById(mIstanza.CODICERESPONSABILE, mIstanza.IDCOMUNE);
                    testoDaSostituire = testoDaSostituire.Replace("[17]", responsabile.RESPONSABILE);

                    string responsabileproc = string.Empty;
                    if (!string.IsNullOrEmpty(mIstanza.CODICERESPONSABILEPROC))
                    {
                        responsabile = new ResponsabiliMgr(db).GetById(mIstanza.CODICERESPONSABILEPROC, mIstanza.IDCOMUNE);
                        responsabileproc = responsabile.RESPONSABILE;
                    }

                    testoDaSostituire = testoDaSostituire.Replace("[18]", responsabileproc);

                    testoDaSostituire = testoDaSostituire.Replace("[19]", "[19]"); //Da fare
                    testoDaSostituire = testoDaSostituire.Replace("[20]", mIstanza.NUMEROISTANZA);
                    testoDaSostituire = testoDaSostituire.Replace("[21]", "[21]"); //Da fare: Impianti per il software SU


                    #endregion

                    #region Dati di IstanzeAree
                    IstanzeAree istanzeAree = new IstanzeAree();
                    istanzeAree.IDCOMUNE = mIstanza.IDCOMUNE;
                    istanzeAree.CODICEISTANZA = mIstanza.CODICEISTANZA;
                    istanzeAree.PRIMARIO = "1";
                    List<IstanzeAree> listIstanzeAree = new IstanzeAreeMgr(db).GetList(istanzeAree);
                    if ((listIstanzeAree != null) && (listIstanzeAree.Count > 0))
                    {
                        istanzeAree = listIstanzeAree[0];
                        testoDaSostituire = testoDaSostituire.Replace("[11]", new AreeMgr(db).GetById(istanzeAree.CODICEAREA, istanzeAree.IDCOMUNE).DENOMINAZIONE);
                    }
                    #endregion

                    #region Dati di IstanzeMappali
                    IstanzeMappali istMappali = new IstanzeMappali();
                    istMappali.Idcomune = mIstanza.IDCOMUNE;
                    istMappali.Fkcodiceistanza = Convert.ToInt32(mIstanza.CODICEISTANZA);
                    istMappali.Primario = 1;
                    List<IstanzeMappali> listIstanzeMappali = new IstanzeMappaliMgr(db).GetList(istMappali);
                    if ((listIstanzeMappali != null) && (listIstanzeMappali.Count > 0))
                    {
                        istMappali = listIstanzeMappali[0];
                        testoDaSostituire = testoDaSostituire.Replace("[14]", istMappali.Foglio);
                        testoDaSostituire = testoDaSostituire.Replace("[15]", istMappali.Particella);
                        testoDaSostituire = testoDaSostituire.Replace("[16]", istMappali.Sub);
                    }
                    #endregion

                    #region Dati di IstanzeStradario
                    IstanzeStradario istStradario = new IstanzeStradario();
                    istStradario.IDCOMUNE = mIstanza.IDCOMUNE;
                    istStradario.CODICEISTANZA = mIstanza.CODICEISTANZA;
                    istStradario.PRIMARIO = "1";
                    List<IstanzeStradario> listIstanzeStradario = new IstanzeStradarioMgr(db).GetList(istStradario);
                    if ((listIstanzeStradario != null) && (listIstanzeStradario.Count > 0))
                    {
                        istStradario = listIstanzeStradario[0];
                        testoDaSostituire = testoDaSostituire.Replace("[22]", istStradario.CIVICO);
                        Stradario stradario = new StradarioMgr(db).GetById(istStradario.CODICESTRADARIO, istStradario.IDCOMUNE);
                        testoDaSostituire = testoDaSostituire.Replace("[23]", stradario.PREFISSO + " " + stradario.DESCRIZIONE);
                    }

                    testoDaSostituire = testoDaSostituire.Replace("[24]", mIstanza.PASSWORD);
                    string flagVia = string.Empty;
                    switch (mIstanza.FLAGVIA)
                    {
                        case "0":
                            flagVia = "Non Prevista";
                            break;
                        case "1":
                            flagVia = "VIA Regionale";
                            break;
                        case "2":
                            flagVia = "VIA Nazionale";
                            break;
                        case "3":
                            flagVia = "Non Prevista";
                            break;
                    }
                    testoDaSostituire = testoDaSostituire.Replace("[25]", flagVia);
                    string variantePR = string.Empty;
                    switch (mIstanza.VARIANTEPR)
                    {
                        case "1":
                            variantePR = "Si";
                            break;
                        default:
                            variantePR = "No";
                            break;
                    }
                    testoDaSostituire = testoDaSostituire.Replace("[26]", variantePR);
                    testoDaSostituire = testoDaSostituire.Replace("[27]", DateTime.Now.ToString("dd/MM/yyyy"));
                    if (!string.IsNullOrEmpty(mIstanza.CODICEPROFESSIONISTA))
                    {
                        Anagrafe tecnico = new AnagrafeMgr(db).GetById(mIstanza.IDCOMUNE, Convert.ToInt32(mIstanza.CODICEPROFESSIONISTA));
                        testoDaSostituire = testoDaSostituire.Replace("[28]", (string.IsNullOrEmpty(tecnico.NOME) ? tecnico.NOMINATIVO : tecnico.NOMINATIVO + " " + tecnico.NOME));
                    }
                    testoDaSostituire = testoDaSostituire.Replace("[29]", "[29]"); //Da fare
                    testoDaSostituire = testoDaSostituire.Replace("[30]", "[30]"); //Da fare
                    testoDaSostituire = testoDaSostituire.Replace("[31]", "[31]"); //Da fare
                    #endregion
                }
            }
            catch (Exception ex)
            {
                throw new Exception("SostituisciCampiIstanza:" + ex.Message);
            }
            return testoDaSostituire;
        }
        private string SostituisciCampiMovimento(string testoDaSostituire)
        {
            if (!string.IsNullOrEmpty(CodiceMovimento))
            {
                Movimenti mMovimento = new MovimentiMgr(db).GetById(CodiceMovimento, IdComune);

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
            int fine = 0;

            while (testoDaSostituire.IndexOf("[38(") > -1 || testoDaSostituire.IndexOf("[39(") > -1 )
            {
                if (fine > 50)
                    break;

                string num_aut = string.Empty;
                string data_aut = string.Empty;

                int posStart = testoDaSostituire.IndexOf("[38(");
                if( posStart == -1 )
                    posStart = testoDaSostituire.IndexOf("[39(");

                posStart += 4;
                int posEnd = testoDaSostituire.IndexOf(")]", posStart);

                string codRegistro = testoDaSostituire.Substring(posStart, posEnd - posStart);

                Autorizzazioni filtro = new Autorizzazioni();
                filtro.IDCOMUNE = IdComune;
                filtro.FKIDISTANZA = CodiceIstanza;
                filtro.FKIDREGISTRO = codRegistro;

                List<Autorizzazioni> lAut = new AutorizzazioniMgr(db).GetList(filtro);

                //attualmente in sigepro è possibile inserire una sola autorizzazione per registro in un'istanza.
                if (lAut.Count > 0)
                {
                    Autorizzazioni a = lAut[0];

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
                string codicepeople = string.Empty;

                string cmdText = "select " +
                                    "codicepeople " +
                                 "from " +
                                    "istanzepeoplet, istanzepeopled " +
                                "where " +
                                    "istanzepeoplet.idcomune = istanzepeopled.idcomune and " +
                                    "istanzepeoplet.id = istanzepeopled.fkid and " +
                                    "istanzepeopled.idcomune = '" + IdComune + "' and " +
                                    "istanzepeopled.codiceistanza = " + CodiceIstanza;

                using (IDbCommand cmd = db.CreateCommand(cmdText))
                {
                    using (IDataReader dr = cmd.ExecuteReader())
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
            if (IdSorteggio > int.MinValue)
            {
                if (testoDaSostituire.IndexOf("[41]") > -1 || testoDaSostituire.IndexOf("[42]") > -1)
                {
                    SorteggiTestata st = new SorteggiTestataMgr(db).GetById(IdSorteggio, IdComune);

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
