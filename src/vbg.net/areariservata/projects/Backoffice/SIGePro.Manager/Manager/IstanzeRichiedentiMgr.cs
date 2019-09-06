using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	
	public class IstanzeRichiedentiMgr: BaseManager
    {
        #region parametri per la configurazione di alcune procedure automatiche all'interno del manager
        bool _insertanagrafe = false;

		/// <summary>
		/// Se True, inserisce le anagrafiche (tabella ANAGRAFE) non presenti in SIGePro
		/// </summary>
		public bool InsertAnagrafe
		{
			set { _insertanagrafe = value; }
			get { return _insertanagrafe;}
		}
		bool _updateanagrafe = false;
		/// <summary>
		/// Se True, aggiorna le anagrafiche (tabella ANAGRAFE) quando già presenti in SIGePro.
		/// </summary>
		public bool UpdateAnagrafe
		{
			get { return _updateanagrafe; }
			set { _updateanagrafe = value; }
		}

        bool m_escludiControlliSuAnagraficheDisabilitate = false;
        /// <summary>
        /// Se impostato a true non effettua verifiche dei dati sulle anagrafiche disabilitate nei metodi Insert e Extract
        /// </summary>
        public bool EscludiControlliSuAnagraficheDisabilitate
        {
            get { return m_escludiControlliSuAnagraficheDisabilitate; }
            set { m_escludiControlliSuAnagraficheDisabilitate = value; }
        }

        bool _ricercasolocfpiva = false;
        public bool RicercaSoloCF_PIVA
        {
            get { return _ricercasolocfpiva; }
            set { _ricercasolocfpiva = value; }
        }

        bool _escludiverificaincongruenze = false;
        public bool EscludiVerificaIncongruenze
        {
            get { return _escludiverificaincongruenze; }
            set { _escludiverificaincongruenze = value; }
        }

        bool m_ForzaInserimentoAnagrafe = false;
        public bool ForzaInserimentoAnagrafe
        {
            get { return m_ForzaInserimentoAnagrafe; }
            set { m_ForzaInserimentoAnagrafe = value; }
        }
        #endregion

        public IstanzeRichiedentiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public IstanzeRichiedenti GetById(String pCODICEISTANZA, String pCODICERICHIEDENTE, String pIDCOMUNE)
		{
			IstanzeRichiedenti retVal = new IstanzeRichiedenti();
			retVal.CODICEISTANZA = pCODICEISTANZA;
			retVal.CODICERICHIEDENTE = pCODICERICHIEDENTE;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as IstanzeRichiedenti;
			
			return null;
		}

        public IstanzeRichiedenti GetByClass(IstanzeRichiedenti pClass)
        {
            DataClassCollection mydc = db.GetClassList(pClass, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as IstanzeRichiedenti;

            return null;
        }

        public List<IstanzeRichiedenti> GetList(IstanzeRichiedenti p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<IstanzeRichiedenti> GetList(IstanzeRichiedenti p_class, IstanzeRichiedenti p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeRichiedenti>();
		}


		public void Delete(IstanzeRichiedenti p_class)
		{
			db.Delete( p_class) ;
		}
		
		public IstanzeRichiedenti Insert( IstanzeRichiedenti p_class )
		{

			p_class = DataIntegrations( p_class );

			Validate( p_class ,AmbitoValidazione.Insert);

			db.Insert( p_class );

			p_class = ChildDataIntegrations( p_class );

			ChildInsert( p_class );

			return p_class;
		}

		public IstanzeRichiedenti Update( IstanzeRichiedenti p_class )
		{
			db.Update( p_class );
			return p_class;
		}	


		private IstanzeRichiedenti DataIntegrations( IstanzeRichiedenti p_class )
		{
			IstanzeRichiedenti retVal = ( IstanzeRichiedenti ) p_class.Clone();

            if (string.IsNullOrEmpty(retVal.CODICERICHIEDENTE) && retVal.Richiedente != null)
			{
				retVal.CODICERICHIEDENTE = InsertUpdateAnagrafeDataClass(retVal.Richiedente).CODICEANAGRAFE;
			}

            if (string.IsNullOrEmpty(retVal.CODICEANAGRAFECOLL) && retVal.AnagrafeCollegata != null)
			{
				retVal.CODICEANAGRAFECOLL = InsertUpdateAnagrafeDataClass( retVal.AnagrafeCollegata ).CODICEANAGRAFE;
			}

            if (retVal.Codiceprocuratore.GetValueOrDefault(int.MinValue) == int.MinValue && retVal.Procuratore != null)
            {
                retVal.Codiceprocuratore = Convert.ToInt32( InsertUpdateAnagrafeDataClass(retVal.Procuratore).CODICEANAGRAFE);
            }

			return retVal;
		}
		private void Validate(IstanzeRichiedenti p_class, AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class , ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate( IstanzeRichiedenti p_class )
		{
			#region ISTANZERICHIEDENTI.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if (  this.recordCount( "ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZERICHIEDENTI.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion	

			#region ISTANZERICHIEDENTI.CODICERICHIEDENTE
			if ( ! IsStringEmpty( p_class.CODICERICHIEDENTE ) )
			{
				if (  this.recordCount( "ANAGRAFE","CODICEANAGRAFE","WHERE CODICEANAGRAFE = " + p_class.CODICERICHIEDENTE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZERICHIEDENTI.CODICERICHIEDENTE non trovato nella tabella ANAGRAFE"));
				}
			}
			#endregion
			
			#region ISTANZERICHIEDENTI.CODICETIPOSOGGETTO
			if ( ! IsStringEmpty( p_class.CODICETIPOSOGGETTO ) )
			{
				if (  this.recordCount( "TIPISOGGETTO","CODICETIPOSOGGETTO","WHERE CODICETIPOSOGGETTO = " + p_class.CODICETIPOSOGGETTO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZERICHIEDENTI.CODICETIPOSOGGETTO non trovato nella tabella TIPISOGGETTO"));
				}
			}
			#endregion	

			#region ISTANZERICHIEDENTI.CODICEANAGRAFECOLL
			if ( ! IsStringEmpty( p_class.CODICEANAGRAFECOLL ) )
			{
				if (  this.recordCount( "ANAGRAFE","CODICEANAGRAFE","WHERE CODICEANAGRAFE = " + p_class.CODICEANAGRAFECOLL + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZERICHIEDENTI.CODICEANAGRAFECOLL non trovato nella tabella ANAGRAFE"));
				}
			}
			#endregion
		}

		private IstanzeRichiedenti ChildDataIntegrations( IstanzeRichiedenti p_class )
		{
			IstanzeRichiedenti retVal = ( IstanzeRichiedenti ) p_class.Clone();

			for( int i=0; i < retVal.AnagrafeDocumenti.Count; i++ )
			{
                if (string.IsNullOrEmpty(retVal.AnagrafeDocumenti[i].IDCOMUNE))
					retVal.AnagrafeDocumenti[i].IDCOMUNE		= retVal.IDCOMUNE;
				else if ( retVal.AnagrafeDocumenti[i].IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper() )
					throw new IncongruentDataException("ANAGRAFEDOCUMENTI.IDCOMUNE diverso da ISTANZERICHIEDENTI.IDCOMUNE");

                if (string.IsNullOrEmpty(retVal.AnagrafeDocumenti[i].CODICEISTANZA))
					retVal.AnagrafeDocumenti[i].CODICEISTANZA		= retVal.CODICEISTANZA;
				else if ( retVal.AnagrafeDocumenti[i].CODICEISTANZA.ToUpper() != retVal.CODICEISTANZA.ToUpper() )
					throw new IncongruentDataException("ANAGRAFEDOCUMENTI.CODICEISTANZA diverso da ISTANZERICHIEDENTI.CODICEISTANZA");

				retVal.AnagrafeDocumenti[i].CODICEANAGRAFE  = retVal.CODICERICHIEDENTE;
			}

			return retVal;
		}

		private void ChildInsert( IstanzeRichiedenti p_class )
		{
			for( int i=0; i < p_class.AnagrafeDocumenti.Count; i++ )
			{
				AnagrafeDocumentiMgr pManager = new AnagrafeDocumentiMgr( this.db );
				pManager.Insert( p_class.AnagrafeDocumenti[i] );
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
                richiedente = anagrafeMgr.Extract(anagrafeDataClass, false, UpdateAnagrafe);

                if (string.IsNullOrEmpty(richiedente.CODICEANAGRAFE) && InsertAnagrafe)
                {
                    //Se l'anagrafica non è stata trovata viene inserita.
                    richiedente = anagrafeMgr.Insert(anagrafeDataClass);
                }
            }
            catch (Init.SIGePro.Exceptions.Anagrafe.OmonimiaExceptionWarning oew)
            {
                if (ForzaInserimentoAnagrafe)
                {
                    Anagrafe anagDisabilitata = (anagrafeDataClass.Clone() as Anagrafe);
                    anagDisabilitata.FLAG_DISABILITATO = "1";
                    anagDisabilitata.DATA_DISABILITATO = DateTime.Now.Date;

                    try
                    {
                        richiedente = anagrafeMgr.Extract(anagrafeDataClass, false, UpdateAnagrafe);

                        if (string.IsNullOrEmpty(richiedente.CODICEANAGRAFE) && InsertAnagrafe)
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
		#endregion
	}
}