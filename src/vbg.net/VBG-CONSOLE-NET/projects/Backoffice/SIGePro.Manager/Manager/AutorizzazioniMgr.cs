using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.Autorizzazioni;
using Init.SIGePro.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    public class AutorizzazioniMgr : BaseManager
    {
        #region parametri per la configurazione di alcune procedure automatiche all'interno del manager
        //per default teniamo conto della configurazione di sigepro dal web.
        private bool _insertanagrafe = false;
        private bool _updateanagrafe = false;
        private bool m_ForzaInserimentoAnagrafe = false;
        private bool m_escludiControlliSuAnagraficheDisabilitate = false;

        /// <summary>
        /// Se True, inserisce le anagrafiche (tabella ANAGRAFE) non presenti in SIGePro
        /// </summary>
        public bool InsertAnagrafe
        {
            get { return this._insertanagrafe; }
            set { this._insertanagrafe = value; }
        }

        /// <summary>
        /// Se True, aggiorna le anagrafiche (tabella ANAGRAFE) quando già presenti in SIGePro.
        /// </summary>
        public bool UpdateAnagrafe
        {
            get { return this._updateanagrafe; }
            set { this._updateanagrafe = value; }
        }

        public bool ForzaInserimentoAnagrafe
        {
            get { return this.m_ForzaInserimentoAnagrafe; }
            set { this.m_ForzaInserimentoAnagrafe = value; }
        }

        /// <summary>
        /// Se impostato a true non effettua verifiche dei dati sulle anagrafiche disabilitate nei metodi Insert e Extract
        /// </summary>
        public bool EscludiControlliSuAnagraficheDisabilitate
        {
            get { return this.m_escludiControlliSuAnagraficheDisabilitate; }
            set { this.m_escludiControlliSuAnagraficheDisabilitate = value; }
        }

        /// <summary>
        /// Se True, effettua la ricerca delle anagrafiche solamente per codice fiscale/partita iva. Per default è False
        /// </summary>
        private bool _ricercasolocfpiva = false;
        public bool RicercaSoloCF_PIVA
        {
            get { return this._ricercasolocfpiva; }
            set { this._ricercasolocfpiva = value; }
        }

        /// <summary>
        /// Se True, esclude la verifica dei dati una volta trova un'anagrafica. Per default è false.
        /// </summary>
        private bool _escludiverificaincongruenze = false;
        public bool EscludiVerificaIncongruenze
        {
            get { return this._escludiverificaincongruenze; }
            set { this._escludiverificaincongruenze = value; }
        }
        #endregion

        public AutorizzazioniMgr(DataBase dataBase) : base(dataBase) { }


        public Autorizzazioni GetById(String pID, String pIDCOMUNE)
        {
            Autorizzazioni retVal = new Autorizzazioni();

            retVal.ID = pID;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<Autorizzazioni> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Autorizzazioni;

            return null;
        }

        public Autorizzazioni GetByClass(Autorizzazioni pClass)
        {
            List<Autorizzazioni> mydc = this.db.GetClassList(pClass, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Autorizzazioni;

            return null;
        }

        public List<Autorizzazioni> GetList(Autorizzazioni p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<Autorizzazioni> GetList(Autorizzazioni p_class, Autorizzazioni p_cmpClass)
        {
            return this.db.GetClassList(p_class, p_cmpClass, false).ToList<Autorizzazioni>();
        }


        public void Delete(Autorizzazioni p_class)
        {
            this.VerificaRecordCollegati(p_class);

            this.EffettuaCancellazioneACascata(p_class);

            this.db.Delete(p_class);
        }

        private void EffettuaCancellazioneACascata(Autorizzazioni p_class)
        {
            #region AUTORIZZAZIONI_CONCESSIONI
            AutorizzazioniConcessioni aut_conc = new AutorizzazioniConcessioni();
            aut_conc.Idcomune = p_class.IDCOMUNE;
            aut_conc.FkIdAutAttuale = Convert.ToInt32(p_class.ID);

            List<AutorizzazioniConcessioni> lAutConcessioni = new AutorizzazioniConcessioniMgr(this.db).GetList(aut_conc);
            foreach (AutorizzazioniConcessioni autorizzazioneConc in lAutConcessioni)
            {
                AutorizzazioniConcessioniMgr mgr = new AutorizzazioniConcessioniMgr(this.db);
                mgr.Delete(autorizzazioneConc);
            }
            #endregion
        }

        private void VerificaRecordCollegati(Autorizzazioni p_class)
        {

        }

        public Autorizzazioni Insert(Autorizzazioni p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            p_class = this.ChildDataIntegrations(p_class);

            return p_class;
        }

        public Autorizzazioni Update(Autorizzazioni p_class)
        {

            this.db.Update(p_class);

            return p_class;
        }

        #region BeforeInsert
        private Autorizzazioni DataIntegrations(Autorizzazioni p_class)
        {
            Autorizzazioni retVal = (Autorizzazioni)p_class.Clone();

            if (retVal.FlagAttiva.GetValueOrDefault(int.MinValue) == int.MinValue)
                retVal.FlagAttiva = 1;


            if (retVal.FKCodiceAnagrafe.GetValueOrDefault(int.MinValue) == int.MinValue && retVal.Anagrafe != null)
            {
                Anagrafe titolare = retVal.Anagrafe;

                if (string.IsNullOrEmpty(titolare.IDCOMUNE))
                    titolare.IDCOMUNE = retVal.IDCOMUNE;
                else if (titolare.IDCOMUNE != retVal.IDCOMUNE)
                    throw new Exceptions.IncongruentDataException("ANAGRAFE.IDCOMUNE (" + titolare.IDCOMUNE + ") diverso da AUTORIZZAZIONI.IDCOMUNE (" + retVal.IDCOMUNE + ")");


                retVal.FKCodiceAnagrafe = Convert.ToInt32(this.InsertUpdateAnagrafeDataClass(titolare).CODICEANAGRAFE);
            }

            return retVal;
        }
        private void Validate(Autorizzazioni p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
        {
            if (p_class.FlagAttiva.GetValueOrDefault(int.MinValue) != 0 && p_class.FlagAttiva.GetValueOrDefault(int.MinValue) != 1)
                throw new Init.SIGePro.Exceptions.IncongruentDataException("Il campo AUTORIZZAZIONI.FLAG_ATTIVA contiene il valore " + p_class.FlagAttiva.Value.ToString() + ". I valori ammessi sono 1(abilitata) e 0(disabilitata)");

            if (p_class.FlagAttiva.GetValueOrDefault(int.MinValue) == 0 && p_class.DataStorico.GetValueOrDefault(DateTime.MinValue) == DateTime.MinValue)
                throw new Init.SIGePro.Exceptions.IncongruentDataException("L'autorizzazione risulta cessata ma non è stata specificata la data della cessazione. Valorizzare il campo DataStorico o rendere attiva la concessione.");

            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(Autorizzazioni p_class)
        {
            #region AUTORIZZAZIONI.FKIDREGISTRO
            if (!this.IsStringEmpty(p_class.FKIDREGISTRO))
            {
                if (this.recordCount("TIPOLOGIAREGISTRI", "TR_ID", "WHERE TR_ID = '" + p_class.FKIDREGISTRO + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException(p_class, "AUTORIZZAZIONI.FKIDREGISTRO non trovato nella tabella TIPOLOGIAREGISTRO"));
                }
            }
            #endregion

            #region AUTORIZZAZIONI.FKIDISTANZA
            if (!this.IsStringEmpty(p_class.FKIDISTANZA))
            {
                if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE CODICEISTANZA = " + p_class.FKIDISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0)
                {
                    throw (new RecordNotfoundException(p_class, "AUTORIZZAZIONI.FKIDISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion

            #region AUTORIZZAZIONI.FK_CODICEANAGRAFE
            if (p_class.FKCodiceAnagrafe.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                if (this.recordCount("ANAGRAFE", "CODICEANAGRAFE", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEANAGRAFE = " + p_class.FKCodiceAnagrafe.ToString()) == 0)
                {
                    throw (new RecordNotfoundException(p_class, "AUTORIZZAZIONI.FK_CODICEANAGRAFE (" + p_class.FKCodiceAnagrafe.ToString() + ") non trovato nella tabella ANAGRAFE"));
                }
            }
            #endregion
        }

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

                if (Init.Utils.StringChecker.IsStringEmpty(richiedente.CODICEANAGRAFE) && this.InsertAnagrafe)
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

                        if (Init.Utils.StringChecker.IsStringEmpty(richiedente.CODICEANAGRAFE) && this.InsertAnagrafe)
                        {
                            //Se l'anagrafica non è stata trovata viene inserita.
                            richiedente = anagrafeMgr.Insert(anagDisabilitata);
                        }
                    }
                    catch (Init.SIGePro.Exceptions.Anagrafe.OmonimiaExceptionWarning)
                    {
                        //esiste un'anagrafica disabilitata ma anche in questo caso alcuni dei dati
                        //non corrispondono, l'inserimento dell'anagrafica disabilitata viene forzato
                        richiedente = anagrafeMgr.Insert(anagDisabilitata, false);
                    }

                }
                else
                {
                    throw oew;
                }
            }

            return richiedente;
        }

        #endregion

        #region AfterInsert

        private Autorizzazioni ChildDataIntegrations(Autorizzazioni p_class)
        {
            Autorizzazioni retVal = (Autorizzazioni)p_class.Clone();

            return retVal;
        }

        #endregion
    }
}