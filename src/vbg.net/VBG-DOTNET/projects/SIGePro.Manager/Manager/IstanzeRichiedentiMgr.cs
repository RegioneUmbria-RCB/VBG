using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{
    public class IstanzeRichiedentiMgr : BaseManager
    {
        #region parametri per la configurazione di alcune procedure automatiche all'interno del manager
        private bool _insertanagrafe = false;

        /// <summary>
        /// Se True, inserisce le anagrafiche (tabella ANAGRAFE) non presenti in SIGePro
        /// </summary>
        public bool InsertAnagrafe
        {
            set { this._insertanagrafe = value; }
            get { return this._insertanagrafe; }
        }
        private bool _updateanagrafe = false;
        /// <summary>
        /// Se True, aggiorna le anagrafiche (tabella ANAGRAFE) quando già presenti in SIGePro.
        /// </summary>
        public bool UpdateAnagrafe
        {
            get { return this._updateanagrafe; }
            set { this._updateanagrafe = value; }
        }

        private bool m_escludiControlliSuAnagraficheDisabilitate = false;
        /// <summary>
        /// Se impostato a true non effettua verifiche dei dati sulle anagrafiche disabilitate nei metodi Insert e Extract
        /// </summary>
        public bool EscludiControlliSuAnagraficheDisabilitate
        {
            get { return this.m_escludiControlliSuAnagraficheDisabilitate; }
            set { this.m_escludiControlliSuAnagraficheDisabilitate = value; }
        }

        private bool _ricercasolocfpiva = false;
        public bool RicercaSoloCF_PIVA
        {
            get { return this._ricercasolocfpiva; }
            set { this._ricercasolocfpiva = value; }
        }

        private bool _escludiverificaincongruenze = false;
        public bool EscludiVerificaIncongruenze
        {
            get { return this._escludiverificaincongruenze; }
            set { this._escludiverificaincongruenze = value; }
        }

        private bool m_ForzaInserimentoAnagrafe = false;
        public bool ForzaInserimentoAnagrafe
        {
            get { return this.m_ForzaInserimentoAnagrafe; }
            set { this.m_ForzaInserimentoAnagrafe = value; }
        }
        #endregion

        public IstanzeRichiedentiMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public IstanzeRichiedenti GetById(String pCODICEISTANZA, String pCODICERICHIEDENTE, String pIDCOMUNE)
        {
            IstanzeRichiedenti retVal = new IstanzeRichiedenti();
            retVal.CODICEISTANZA = pCODICEISTANZA;
            retVal.CODICERICHIEDENTE = pCODICERICHIEDENTE;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public IstanzeRichiedenti GetByClass(IstanzeRichiedenti pClass)
        {
            var mydc = this.db.GetClassList(pClass, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public List<IstanzeRichiedenti> GetList(IstanzeRichiedenti p_class)
        {
            return this.db.GetClassList(p_class);
        }

        public void Delete(IstanzeRichiedenti p_class)
        {
            this.db.Delete(p_class);
        }

        public IstanzeRichiedenti Insert(IstanzeRichiedenti p_class)
        {

            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            p_class = this.ChildDataIntegrations(p_class);

            this.ChildInsert(p_class);

            return p_class;
        }

        public IstanzeRichiedenti Update(IstanzeRichiedenti p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }


        private IstanzeRichiedenti DataIntegrations(IstanzeRichiedenti p_class)
        {
            IstanzeRichiedenti retVal = (IstanzeRichiedenti)p_class.Clone();

            if (string.IsNullOrEmpty(retVal.CODICERICHIEDENTE) && retVal.Richiedente != null)
            {
                retVal.CODICERICHIEDENTE = this.InsertUpdateAnagrafeDataClass(retVal.Richiedente).CODICEANAGRAFE;
            }

            if (string.IsNullOrEmpty(retVal.CODICEANAGRAFECOLL) && retVal.AnagrafeCollegata != null)
            {
                retVal.CODICEANAGRAFECOLL = this.InsertUpdateAnagrafeDataClass(retVal.AnagrafeCollegata).CODICEANAGRAFE;
            }

            if (retVal.Codiceprocuratore.GetValueOrDefault(int.MinValue) == int.MinValue && retVal.Procuratore != null)
            {
                retVal.Codiceprocuratore = Convert.ToInt32(this.InsertUpdateAnagrafeDataClass(retVal.Procuratore).CODICEANAGRAFE);
            }

            return retVal;
        }
        private void Validate(IstanzeRichiedenti p_class, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(IstanzeRichiedenti p_class)
        {
            #region ISTANZERICHIEDENTI.CODICEISTANZA
            if (!String.IsNullOrEmpty(p_class.CODICEISTANZA))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEISTANZA", p_class.CODICEISTANZA),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("ISTANZE", "CODICEISTANZA", conditions) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZERICHIEDENTI.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region ISTANZERICHIEDENTI.CODICERICHIEDENTE
            if (!String.IsNullOrEmpty(p_class.CODICERICHIEDENTE))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEANAGRAFE", p_class.CODICERICHIEDENTE),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("ANAGRAFE", "CODICEANAGRAFE", conditions) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZERICHIEDENTI.CODICERICHIEDENTE non trovato nella tabella ANAGRAFE"));
                }
            }
            #endregion

            #region ISTANZERICHIEDENTI.CODICETIPOSOGGETTO
            if (!String.IsNullOrEmpty(p_class.CODICETIPOSOGGETTO))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICETIPOSOGGETTO", p_class.CODICETIPOSOGGETTO),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("TIPISOGGETTO", "CODICETIPOSOGGETTO", conditions) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZERICHIEDENTI.CODICETIPOSOGGETTO non trovato nella tabella TIPISOGGETTO"));
                }
            }
            #endregion

            #region ISTANZERICHIEDENTI.CODICEANAGRAFECOLL
            if (!String.IsNullOrEmpty(p_class.CODICEANAGRAFECOLL))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEANAGRAFE", p_class.CODICEANAGRAFECOLL),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("ANAGRAFE", "CODICEANAGRAFE", conditions) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZERICHIEDENTI.CODICEANAGRAFECOLL non trovato nella tabella ANAGRAFE"));
                }
            }
            #endregion
        }

        private IstanzeRichiedenti ChildDataIntegrations(IstanzeRichiedenti p_class)
        {
            IstanzeRichiedenti retVal = (IstanzeRichiedenti)p_class.Clone();

            for (int i = 0; i < retVal.AnagrafeDocumenti.Count; i++)
            {
                if (string.IsNullOrEmpty(retVal.AnagrafeDocumenti[i].IDCOMUNE))
                    retVal.AnagrafeDocumenti[i].IDCOMUNE = retVal.IDCOMUNE;
                else if (!string.Equals(retVal.AnagrafeDocumenti[i].IDCOMUNE, retVal.IDCOMUNE, StringComparison.OrdinalIgnoreCase))
                    throw new IncongruentDataException("ANAGRAFEDOCUMENTI.IDCOMUNE diverso da ISTANZERICHIEDENTI.IDCOMUNE");

                if (string.IsNullOrEmpty(retVal.AnagrafeDocumenti[i].CODICEISTANZA))
                    retVal.AnagrafeDocumenti[i].CODICEISTANZA = retVal.CODICEISTANZA;
                else if (!string.Equals(retVal.AnagrafeDocumenti[i].CODICEISTANZA, retVal.CODICEISTANZA, StringComparison.OrdinalIgnoreCase))
                    throw new IncongruentDataException("ANAGRAFEDOCUMENTI.CODICEISTANZA diverso da ISTANZERICHIEDENTI.CODICEISTANZA");

                retVal.AnagrafeDocumenti[i].CODICEANAGRAFE = retVal.CODICERICHIEDENTE;
            }

            return retVal;
        }

        private void ChildInsert(IstanzeRichiedenti p_class)
        {
            for (int i = 0; i < p_class.AnagrafeDocumenti.Count; i++)
            {
                AnagrafeDocumentiMgr pManager = new AnagrafeDocumentiMgr(this.db);
                pManager.Insert(p_class.AnagrafeDocumenti[i]);
            }
        }


        /// <summary>
        /// Inserisce o aggiorna una anagrafica nella tabella ANAGRAFE nel rispetto delle regole impostate
        /// nelle proprietà UpdateAnagrafe e InsertAnagrafe della classe IstanzeMGR
        /// </summary>
        /// <param name="anagrafeDataClass">E' la classe di tipo Anagrafe da inserire</param>
        /// <returns>Ritorna la classe Anagrafe inserita o aggiornata. Se la classe è vuota CODICEANAGRAFE="" o null allora l'inserimento o l'aggiornamento non è andato a buon fine.</returns>
        private Anagrafe InsertUpdateAnagrafeDataClass(Anagrafe anagrafeDataClass)
        {
            Anagrafe richiedente = null;
            AnagrafeMgr anagrafeMgr = new AnagrafeMgr(this.db);

            anagrafeMgr.EscludiControlliSuAnagraficheDisabilitate = this.EscludiControlliSuAnagraficheDisabilitate;
            anagrafeMgr.ForzaInserimentoAnagrafe = this.ForzaInserimentoAnagrafe;
            anagrafeMgr.RicercaSoloCF_PIVA = this.RicercaSoloCF_PIVA;
            anagrafeMgr.EscludiVerificaIncongruenze = this.EscludiVerificaIncongruenze;

            //cerca l'anagrafica e se la trova la aggiorna in sigepro
            try
            {
                richiedente = anagrafeMgr.Extract(anagrafeDataClass, false, this.UpdateAnagrafe);

                if (string.IsNullOrEmpty(richiedente.CODICEANAGRAFE) && this.InsertAnagrafe)
                {
                    //Se l'anagrafica non è stata trovata viene inserita.
                    richiedente = anagrafeMgr.Insert(anagrafeDataClass);
                }
            }
            catch (Init.SIGePro.Exceptions.Anagrafe.OmonimiaExceptionWarning oew)
            {
                if (this.ForzaInserimentoAnagrafe)
                {
                    Anagrafe anagDisabilitata = (anagrafeDataClass.Clone() as Anagrafe);
                    anagDisabilitata.FLAG_DISABILITATO = "1";
                    anagDisabilitata.DATA_DISABILITATO = DateTime.Now.Date;

                    try
                    {
                        richiedente = anagrafeMgr.Extract(anagrafeDataClass, false, this.UpdateAnagrafe);

                        if (string.IsNullOrEmpty(richiedente.CODICEANAGRAFE) && this.InsertAnagrafe)
                        {
                            //Se l'anagrafica non è stata trovata viene inserita.
                            richiedente = anagrafeMgr.Insert(anagDisabilitata);
                        }
                    }
                    catch (Init.SIGePro.Exceptions.Anagrafe.OmonimiaExceptionWarning oew2)
                    {
                        //esiste un'anagrafica disabilitata ma anche in questo caso alcuni dei dati
                        //non corrispondono, l'inserimento dell'anagrafica disabilitata viene forzato
                        richiedente = anagrafeMgr.Insert(anagDisabilitata, false);
                    }

                }
                else
                {
                    throw;
                }
            }

            return richiedente;
        }
        #endregion
    }
}