using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using Init.Utils;
using System.Data;
using PersonalLib2.Sql.Collections;

namespace Init.SIGePro.Manager 
{ 	
	public class IstanzeConcessioniMgr: BaseManager
	{
		#region parametri per la configurazione di alcune procedure automatiche all'interno del manager
		//per default teniamo conto della configurazione di sigepro dal web.
		bool _insertanagrafe = false;
		bool _updateanagrafe = false;
        bool m_ForzaInserimentoAnagrafe = false;
        bool m_escludiControlliSuAnagraficheDisabilitate = false;

        public bool ForzaInserimentoAnagrafe
        {
            get { return m_ForzaInserimentoAnagrafe; }
            set { m_ForzaInserimentoAnagrafe = value; }
        }

        /// <summary>
        /// Se impostato a true non effettua verifiche dei dati sulle anagrafiche disabilitate nei metodi Insert e Extract
        /// </summary>
        public bool EscludiControlliSuAnagraficheDisabilitate
        {
            get { return m_escludiControlliSuAnagraficheDisabilitate; }
            set { m_escludiControlliSuAnagraficheDisabilitate = value; }
        }

		/// <summary>
		/// Se True, inserisce le anagrafiche (tabella ANAGRAFE) non presenti in SIGePro
		/// </summary>
		public bool InsertAnagrafe
		{
			get { return _insertanagrafe; }
			set { _insertanagrafe = value; }
		}

		/// <summary>
		/// Se True, aggiorna le anagrafiche (tabella ANAGRAFE) quando già presenti in SIGePro.
		/// </summary>
		public bool UpdateAnagrafe
		{
			get { return _updateanagrafe; }
			set { _updateanagrafe = value; }
		}

        /// <summary>
        /// Se True, effettua la ricerca delle anagrafiche solamente per codice fiscale/partita iva. Per default è False
        /// </summary>
        bool _ricercasolocfpiva = false;
        public bool RicercaSoloCF_PIVA
        {
            get { return _ricercasolocfpiva; }
            set { _ricercasolocfpiva = value; }
        }

        /// <summary>
        /// Se True, esclude la verifica dei dati una volta trova un'anagrafica. Per default è false.
        /// </summary>
        bool _escludiverificaincongruenze = false;
        public bool EscludiVerificaIncongruenze
        {
            get { return _escludiverificaincongruenze; }
            set { _escludiverificaincongruenze = value; }
        }
		#endregion

		public IstanzeConcessioniMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public IstanzeConcessioni GetById(String pID, String pIDCOMUNE)
		{
			IstanzeConcessioni retVal = new IstanzeConcessioni();
			retVal.ID = pID;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as IstanzeConcessioni;
			
			return null;
		}

        public IstanzeConcessioni GetByClass(IstanzeConcessioni pClass)
        {
            DataClassCollection mydc = db.GetClassList(pClass, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as IstanzeConcessioni;

            return null;
        }

 		public List<IstanzeConcessioni> GetList(IstanzeConcessioni p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public List<IstanzeConcessioni> GetList(IstanzeConcessioni p_class, IstanzeConcessioni p_cmpClass )
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeConcessioni>();
		}

        public void Delete(IstanzeConcessioni cls)
        {
			VerificaRecordCollegati(cls);
			EffettuaCancellazioneACascata(cls);
			db.Delete(cls);
        }

		private void VerificaRecordCollegati(IstanzeConcessioni cls)
		{
		}


		private void EffettuaCancellazioneACascata(IstanzeConcessioni cls)
		{
			/*
			 * FIXVB6 000005: metodo da reimplementare
			 * 
			 * Chiamava Concessioni.CancellaStepStorico
			 * Reimplementare la logica
			 */
			throw new NotImplementedException("Metodo non implementato vedi 000005");
		}

		public IstanzeConcessioni Insert( IstanzeConcessioni p_class )
		{

			p_class = DataIntegrations( p_class );

			Validate(p_class, AmbitoValidazione.Insert);

			db.Insert( p_class );

			return p_class;
		}

		public IstanzeConcessioni Update( IstanzeConcessioni p_class )
		{
			db.Update( p_class );
			return p_class;
		}	

		private IstanzeConcessioni DataIntegrations( IstanzeConcessioni p_class )
		{
			IstanzeConcessioni retVal = ( IstanzeConcessioni ) p_class.Clone();

			if ( string.IsNullOrEmpty( retVal.FKIDCONCESSIONE ) && retVal.Concessione != null )
			{
                Concessioni conc = retVal.Concessione;

                if (StringChecker.IsStringEmpty(conc.IDCOMUNE))
                    conc.IDCOMUNE = retVal.IDCOMUNE;
                else if (conc.IDCOMUNE != retVal.IDCOMUNE)
                    throw new Exceptions.IncongruentDataException("CONCESSIONI.IDCOMUNE (" + conc.IDCOMUNE + ") diverso da ISTANZECONCESSIONI.IDCOMUNE (" + retVal.IDCOMUNE + ")");


				ConcessioniMgr mgr = new ConcessioniMgr( db );
                mgr.InsertAnagrafe = this.InsertAnagrafe;
                mgr.UpdateAnagrafe = this.UpdateAnagrafe;
                mgr.ForzaInserimentoAnagrafe = this.ForzaInserimentoAnagrafe;
                mgr.EscludiControlliSuAnagraficheDisabilitate = this.EscludiControlliSuAnagraficheDisabilitate;
                mgr.RicercaSoloCF_PIVA = this.RicercaSoloCF_PIVA;
                mgr.EscludiVerificaIncongruenze = this.EscludiVerificaIncongruenze;

                retVal.FKIDCONCESSIONE = mgr.Insert(retVal.Concessione).ID;
			}

            if (string.IsNullOrEmpty(retVal.FK_IDAUTORIZZAZIONE) && retVal.Autorizzazione != null)
			{
                Autorizzazioni autorizzazione = retVal.Autorizzazione;

                if (StringChecker.IsStringEmpty(autorizzazione.IDCOMUNE))
                    autorizzazione.IDCOMUNE = retVal.IDCOMUNE;
                else if (autorizzazione.IDCOMUNE != retVal.IDCOMUNE)
                    throw new Exceptions.IncongruentDataException("AUTORIZZAZIONI.IDCOMUNE (" + autorizzazione.IDCOMUNE + ") diverso da ISTANZECONCESSIONI.IDCOMUNE (" + retVal.IDCOMUNE + ")");


                if (StringChecker.IsStringEmpty(autorizzazione.FKIDISTANZA))
                    autorizzazione.FKIDISTANZA = retVal.FKCODICEISTANZA;
                else if (autorizzazione.FKIDISTANZA != retVal.FKCODICEISTANZA)
                    throw new Exceptions.IncongruentDataException("AUTORIZZAZIONI.FKIDISTANZA (" + autorizzazione.FKIDISTANZA + ") diverso da ISTANZECONCESSIONI.FKCODICEISTANZA (" + retVal.FKCODICEISTANZA + ")");


				AutorizzazioniMgr auto = new AutorizzazioniMgr( db );
				retVal.FK_IDAUTORIZZAZIONE = auto.Insert( retVal.Autorizzazione ).ID;
			}

            if (string.IsNullOrEmpty(retVal.IDPRECEDENTE))
			{
				retVal.IDPRECEDENTE = "0";
			}
			

			return retVal;
		}

        private void Validate(IstanzeConcessioni p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class , ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate( IstanzeConcessioni p_class )
		{
			#region ISTANZECONCESSIONI.FKCODICECAUSALE
			if ( ! IsStringEmpty( p_class.FKCODICECAUSALE ) )
			{
				if (  this.recordCount( "CONCESSIONICAUSALI","CODICECAUSALE","WHERE CODICECAUSALE = " + p_class.FKCODICECAUSALE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZECONCESSIONI.FKCODICECAUSALE non trovato nella tabella CONCESSIONICAUSALI"));
				}
			}
			#endregion	

			#region ISTANZECONCESSIONI.FKCODICECAUSALESTORICO
			if ( ! IsStringEmpty( p_class.FKCODICECAUSALESTORICO ) )
			{
				if (  this.recordCount( "CONCESSIONICAUSALI","CODICECAUSALE","WHERE CODICECAUSALE = " + p_class.FKCODICECAUSALESTORICO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZECONCESSIONI.FKCODICECAUSALESTORICO non trovato nella tabella CONCESSIONICAUSALI"));
				}
			}
			#endregion	
			
			#region ISTANZECONCESSIONI.FKCODICEISTANZA
			if ( ! IsStringEmpty( p_class.FKCODICEISTANZA ) )
			{
				if (  this.recordCount( "ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.FKCODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZECONCESSIONI.FKCODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion	

			#region ISTANZECONCESSIONI.FKIDCONCESSIONE
			if ( ! IsStringEmpty( p_class.FKIDCONCESSIONE ) )
			{
				if (  this.recordCount( "CONCESSIONI","ID","WHERE ID = " + p_class.FKIDCONCESSIONE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZECONCESSIONI.FKIDCONCESSIONE non trovato nella tabella CONCESSIONI"));
				}
			}
			#endregion	

		}

		#endregion

	}
}