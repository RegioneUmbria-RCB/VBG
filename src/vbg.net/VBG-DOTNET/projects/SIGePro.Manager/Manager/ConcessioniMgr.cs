using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    public class ConcessioniMgr : BaseManager
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

        public ConcessioniMgr(DataBase dataBase) : base(dataBase) { }


        public Concessioni GetById(String pID, String pIDCOMUNE)
        {
            Concessioni retVal = new Concessioni();
            retVal.ID = pID;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }


        public void Delete(Concessioni p_class)
        {
            this.db.Delete(p_class);
        }

        public Concessioni Insert(Concessioni p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        public Concessioni Update(Concessioni p_class)
        {
            this.db.Update(p_class);

            return p_class;
        }

        private Concessioni DataIntegrations(Concessioni p_class)
        {
            Concessioni retVal = (Concessioni)p_class.Clone();

            if (String.IsNullOrEmpty(retVal.IDCOMUNE))
                throw new RequiredFieldException("CONCESSIONI.IDCOMUNE obbligatorio");

            if (retVal.FK_IDPOSTEGGIO.GetValueOrDefault(int.MinValue) == int.MinValue && retVal.Posteggio != null)
            {
                Mercati_D posteggio = retVal.Posteggio;

                if (String.IsNullOrEmpty(posteggio.IdComune))
                    posteggio.IdComune = retVal.IDCOMUNE;
                else if (posteggio.IdComune != retVal.IDCOMUNE)
                    throw new Exceptions.IncongruentDataException("MERCATI_D.IDCOMUNE (" + posteggio.IdComune + ") diverso da CONCESSIONI.IDCOMUNE (" + retVal.IDCOMUNE + ")");


                Mercati_DMgr merc_d = new Mercati_DMgr(this.db);
                List<Mercati_D> al = merc_d.GetList(posteggio);

                switch (al.Count)
                {
                    case 0: retVal.FK_IDPOSTEGGIO = merc_d.Insert(posteggio).IdPosteggio; break;
                    case 1: retVal.FK_IDPOSTEGGIO = al[0].IdPosteggio; break;
                    default: throw (new Init.SIGePro.Exceptions.Mercati_D.MoreThanOneRecordException(posteggio));
                }

                if (retVal.FK_CODICEMERCATO.GetValueOrDefault(int.MinValue) == int.MinValue)
                    retVal.FK_CODICEMERCATO = posteggio.FkCodiceMercato;
            }

            if (retVal.FK_CODICEMERCATO.GetValueOrDefault(int.MinValue) == int.MinValue)
                throw new RequiredFieldException("CONCESSIONI.FK_CODICEMERCATO obbligatorio");

            if (retVal.FK_IDPOSTEGGIO.GetValueOrDefault(int.MinValue) == int.MinValue && retVal.Posteggio == null)
                throw new RequiredFieldException("Non è stato associato nessun posteggio alla concessione!!!");

            if (retVal.Attiva.GetValueOrDefault(int.MinValue) == int.MinValue)
                retVal.Attiva = 1;

            if (retVal.FK_IDMERCATIUSO.GetValueOrDefault(int.MinValue) == int.MinValue && retVal.Uso != null)
            {
                Mercati_Uso uso = retVal.Uso;

                if (String.IsNullOrEmpty(uso.IdComune))
                    uso.IdComune = retVal.IDCOMUNE;
                else if (uso.IdComune != retVal.IDCOMUNE)
                    throw new Exceptions.IncongruentDataException("MERCATI_USO.IDCOMUNE (" + uso.IdComune + ") diverso da CONCESSIONI.IDCOMUNE (" + retVal.IDCOMUNE + ")");


                if (uso.FkCodiceMercato.GetValueOrDefault(int.MinValue) == int.MinValue)
                    uso.FkCodiceMercato = retVal.FK_CODICEMERCATO;
                else if (uso.FkCodiceMercato != retVal.FK_CODICEMERCATO)
                    throw new Exceptions.IncongruentDataException("MERCATI_USO.FKCODICEMERCATO (" + uso.FkCodiceMercato + ") diverso da CONCESSIONI.FK_CODICEMERCATO (" + retVal.FK_CODICEMERCATO + ")");


                Mercati_UsoMgr merc_uso = new Mercati_UsoMgr(this.db);
                List<Mercati_Uso> al = merc_uso.GetList(uso);

                switch (al.Count)
                {
                    case 0: retVal.FK_IDMERCATIUSO = merc_uso.Insert(uso).Id; break;
                    case 1: retVal.FK_IDMERCATIUSO = al[0].Id; break;
                    default: throw (new Init.SIGePro.Exceptions.Mercati_Uso.MoreThanOneRecordException(retVal.Uso));
                }
            }

            if (retVal.FK_IDMERCATIUSO.GetValueOrDefault(int.MinValue) == int.MinValue && retVal.FK_CODICEMERCATO > int.MinValue)
            {
                Mercati_Uso uso = new Mercati_Uso();
                uso.IdComune = retVal.IDCOMUNE;
                uso.FkCodiceMercato = retVal.FK_CODICEMERCATO;

                Mercati_UsoMgr merc_uso = new Mercati_UsoMgr(this.db);
                List<Mercati_Uso> al = merc_uso.GetList(uso);

                switch (al.Count)
                {
                    case 1: retVal.FK_IDMERCATIUSO = al[0].Id; break;
                    default: retVal.FK_IDMERCATIUSO = null; break;
                }


            }

            if (String.IsNullOrEmpty(retVal.CODICEANAGRAFE) && retVal.Titolare != null)
            {
                Anagrafe titolare = retVal.Titolare;

                if (String.IsNullOrEmpty(titolare.IDCOMUNE))
                    titolare.IDCOMUNE = retVal.IDCOMUNE;
                else if (titolare.IDCOMUNE != retVal.IDCOMUNE)
                    throw new Exceptions.IncongruentDataException("ANAGRAFE.IDCOMUNE (" + titolare.IDCOMUNE + ") diverso da CONCESSIONI.IDCOMUNE (" + retVal.IDCOMUNE + ")");


                retVal.CODICEANAGRAFE = this.InsertUpdateAnagrafeDataClass(titolare).CODICEANAGRAFE;
            }

            return retVal;
        }


        private void Validate(Concessioni p_class, AmbitoValidazione ambitoValidazione)
        {
            if (p_class.FK_IDPOSTEGGIO.GetValueOrDefault(int.MinValue) == int.MinValue && p_class.Posteggio != null)
            {
                Mercati_DMgr merc_d = new Mercati_DMgr(this.db);
                p_class.FK_IDPOSTEGGIO = merc_d.Insert(p_class.Posteggio).IdPosteggio;
            }

            if (p_class.FK_IDMERCATIUSO.GetValueOrDefault(int.MinValue) == int.MinValue && p_class.Uso != null)
            {
                Mercati_UsoMgr merc_uso = new Mercati_UsoMgr(this.db);
                p_class.FK_IDMERCATIUSO = merc_uso.Insert(p_class.Uso).Id;
            }

            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(Concessioni p_class)
        {
            #region CONCESSIONI.FK_CODICEMERCATO
            if (p_class.FK_CODICEMERCATO.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEMERCATO", p_class.FK_CODICEMERCATO.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("MERCATI", "CODICEMERCATO", conditions) == 0)
                {
                    throw (new RecordNotfoundException($"CONCESSIONI.FK_CODICEMERCATO ({p_class.FK_CODICEMERCATO}) non trovato nella tabella MERCATI"));
                }
            }
            #endregion

            #region CONCESSIONI.FK_IDMERCATIUSO
            if (p_class.FK_IDMERCATIUSO.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("ID", p_class.FK_IDMERCATIUSO.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("MERCATI_USO", "ID", conditions) == 0)
                {
                    throw (new RecordNotfoundException($"CONCESSIONI.FK_IDMERCATIUSO ({p_class.FK_IDMERCATIUSO}) non trovato nella tabella MERCATI_USO"));
                }
            }
            #endregion

            #region CONCESSIONI.FK_IDPOSTEGGIO 
            if (p_class.FK_IDPOSTEGGIO.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("IDPOSTEGGIO", p_class.FK_IDPOSTEGGIO.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE)
                };
                if (this.recordCount("MERCATI_D", "IDPOSTEGGIO", conditions) == 0)
                {
                    throw (new RecordNotfoundException($"CONCESSIONI.FK_IDPOSTEGGIO ({p_class.FK_IDPOSTEGGIO}) non trovato nella tabella MERCATI_D"));
                }
            }
            #endregion

            #region CONCESSIONI.FK_TIPOCONCESSIONE
            if (!String.IsNullOrEmpty(p_class.FK_TIPOCONCESSIONE))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("TIPOCONCESSIONE", p_class.FK_TIPOCONCESSIONE)
                };
                if (this.recordCount("CONCESSIONITIPI", "TIPOCONCESSIONE", conditions) == 0)
                {
                    throw (new RecordNotfoundException($"CONCESSIONI.FK_TIPOCONCESSIONE ({p_class.FK_TIPOCONCESSIONE}) non trovato nella tabella CONCESSIONITIPI"));
                }
            }
            #endregion
        }


        private Anagrafe InsertUpdateAnagrafeDataClass(Anagrafe anagrafeDataClass)
        {
            //Anagrafe richiedente = null;
            //AnagrafeMgr p_anagrafemgr = new AnagrafeMgr( this.db );

            ////cerca l'anagrafica e se la trova la aggiorna in sigepro
            //richiedente = p_anagrafemgr.Extract( anagrafeDataClass,false,UpdateAnagrafe );

            //if (IsStringEmpty(richiedente.CODICEANAGRAFE) && InsertAnagrafe)
            //{
            //    //Se l'anagrafica non è stata trovata viene inserita.
            //    richiedente = p_anagrafemgr.Insert(anagrafeDataClass);
            //}

            //return richiedente;

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

                if (String.IsNullOrEmpty(richiedente.CODICEANAGRAFE) && this.InsertAnagrafe)
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

                        if (String.IsNullOrEmpty(richiedente.CODICEANAGRAFE) && this.InsertAnagrafe)
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
                    throw oew;
                }
            }

            return richiedente;
        }
    }
}