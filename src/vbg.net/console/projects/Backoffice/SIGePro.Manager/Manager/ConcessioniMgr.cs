using System;
using System.Collections;
using Init.SIGePro.Collection;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Validator;
using Init.Utils;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	

	public class ConcessioniMgr: BaseManager
	{
		#region parametri per la configurazione di alcune procedure automatiche all'interno del manager
		//per default teniamo conto della configurazione di sigepro dal web.
		bool _insertanagrafe = false;
		bool _updateanagrafe = false;
        bool m_ForzaInserimentoAnagrafe = false;
        bool m_escludiControlliSuAnagraficheDisabilitate = false;

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

		public ConcessioniMgr( DataBase dataBase ) : base( dataBase ) {}
		

		public Concessioni GetById(String pID, String pIDCOMUNE)
		{
			Concessioni retVal = new Concessioni();
			retVal.ID = pID;
			retVal.IDCOMUNE = pIDCOMUNE;

			DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as Concessioni;
			
			return null;
		}
		

		public ArrayList GetList(Concessioni p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(Concessioni p_class, Concessioni p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}


		public void Delete(Concessioni p_class)
		{
			db.Delete( p_class) ;
		}

		public Concessioni Insert( Concessioni p_class )
		{
			p_class = DataIntegrations( p_class );

			Validate( p_class ,AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}

		public Concessioni Update( Concessioni p_class )
		{
			db.Update( p_class );

			return p_class;
		}

		private Concessioni DataIntegrations ( Concessioni p_class )
		{
			Concessioni retVal = ( Concessioni ) p_class.Clone();
			
			if ( IsStringEmpty( retVal.IDCOMUNE ) )
				throw new RequiredFieldException("CONCESSIONI.IDCOMUNE obbligatorio");

            if (retVal.FK_IDPOSTEGGIO.GetValueOrDefault(int.MinValue) == int.MinValue && retVal.Posteggio != null)
            {
                Mercati_D posteggio = retVal.Posteggio;

                if (StringChecker.IsStringEmpty(posteggio.IDCOMUNE))
                    posteggio.IDCOMUNE = retVal.IDCOMUNE;
                else if (posteggio.IDCOMUNE != retVal.IDCOMUNE)
                    throw new Exceptions.IncongruentDataException("MERCATI_D.IDCOMUNE (" + posteggio.IDCOMUNE + ") diverso da CONCESSIONI.IDCOMUNE (" + retVal.IDCOMUNE + ")");


                Mercati_DMgr merc_d = new Mercati_DMgr(db);
                List<Mercati_D> al = merc_d.GetList(posteggio);

                switch (al.Count)
                {
                    case 0: retVal.FK_IDPOSTEGGIO = merc_d.Insert(posteggio).IDPOSTEGGIO; break;
                    case 1: retVal.FK_IDPOSTEGGIO = al[0].IDPOSTEGGIO; break;
                    default: throw (new Init.SIGePro.Exceptions.Mercati_D.MoreThanOneRecordException(posteggio));
                }

                if (retVal.FK_CODICEMERCATO.GetValueOrDefault(int.MinValue) == int.MinValue)
                    retVal.FK_CODICEMERCATO = posteggio.FKCODICEMERCATO;
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

                if (StringChecker.IsStringEmpty(uso.IdComune))
                    uso.IdComune = retVal.IDCOMUNE;
                else if (uso.IdComune != retVal.IDCOMUNE)
                    throw new Exceptions.IncongruentDataException("MERCATI_USO.IDCOMUNE (" + uso.IdComune + ") diverso da CONCESSIONI.IDCOMUNE (" + retVal.IDCOMUNE + ")");


                if (uso.FkCodiceMercato.GetValueOrDefault(int.MinValue) == int.MinValue)
                    uso.FkCodiceMercato = retVal.FK_CODICEMERCATO;
                else if (uso.FkCodiceMercato != retVal.FK_CODICEMERCATO)
                    throw new Exceptions.IncongruentDataException("MERCATI_USO.FKCODICEMERCATO (" + uso.FkCodiceMercato + ") diverso da CONCESSIONI.FK_CODICEMERCATO (" + retVal.FK_CODICEMERCATO + ")");


				Mercati_UsoMgr merc_uso = new Mercati_UsoMgr( db );
				List<Mercati_Uso> al = merc_uso.GetList( uso );
				
				switch( al.Count )
				{
					case 0: retVal.FK_IDMERCATIUSO = merc_uso.Insert( uso ).Id; break;
                    case 1: retVal.FK_IDMERCATIUSO = al[0].Id; break;
					default: throw( new Init.SIGePro.Exceptions.Mercati_Uso.MoreThanOneRecordException( retVal.Uso ) ) ;
				}
			}

            if (retVal.FK_IDMERCATIUSO.GetValueOrDefault(int.MinValue) == int.MinValue && retVal.FK_CODICEMERCATO > int.MinValue)
            {
                Mercati_Uso uso = new Mercati_Uso();
                uso.IdComune = retVal.IDCOMUNE;
                uso.FkCodiceMercato = retVal.FK_CODICEMERCATO;

                Mercati_UsoMgr merc_uso = new Mercati_UsoMgr(db);
                List<Mercati_Uso> al = merc_uso.GetList(uso);

                switch (al.Count)
                {
                    case 1: retVal.FK_IDMERCATIUSO = al[0].Id; break;
                    default: retVal.FK_IDMERCATIUSO = null; break;
                }


            }

			if ( IsStringEmpty( retVal.CODICEANAGRAFE ) && retVal.Titolare != null )
			{
                Anagrafe titolare = retVal.Titolare;

                if (StringChecker.IsStringEmpty(titolare.IDCOMUNE))
                    titolare.IDCOMUNE = retVal.IDCOMUNE;
                else if (titolare.IDCOMUNE != retVal.IDCOMUNE)
                    throw new Exceptions.IncongruentDataException("ANAGRAFE.IDCOMUNE (" + titolare.IDCOMUNE + ") diverso da CONCESSIONI.IDCOMUNE (" + retVal.IDCOMUNE + ")");


				retVal.CODICEANAGRAFE = InsertUpdateAnagrafeDataClass(titolare).CODICEANAGRAFE;
			}

			return retVal;
		}


		private void Validate(Concessioni p_class, AmbitoValidazione ambitoValidazione)
		{
            if (p_class.FK_IDPOSTEGGIO.GetValueOrDefault(int.MinValue) == int.MinValue && p_class.Posteggio != null)
			{
				Mercati_DMgr merc_d = new Mercati_DMgr( db );
				p_class.FK_IDPOSTEGGIO = merc_d.Insert( p_class.Posteggio ).IDPOSTEGGIO;
			}

            if (p_class.FK_IDMERCATIUSO.GetValueOrDefault(int.MinValue) == int.MinValue && p_class.Uso != null)
			{
				Mercati_UsoMgr merc_uso = new Mercati_UsoMgr( db );
                p_class.FK_IDMERCATIUSO = merc_uso.Insert(p_class.Uso).Id;
			}
			
			RequiredFieldValidate( p_class , ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate ( Concessioni p_class )
		{
			#region CONCESSIONI.FK_CODICEMERCATO
            if (p_class.FK_CODICEMERCATO.GetValueOrDefault(int.MinValue) > int.MinValue)
			{
				if ( this.recordCount("MERCATI","CODICEMERCATO","WHERE CODICEMERCATO = " + p_class.FK_CODICEMERCATO.ToString() + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
                    throw (new RecordNotfoundException("CONCESSIONI.FK_CODICEMERCATO (" + p_class.FK_CODICEMERCATO.ToString() + ") non trovato nella tabella MERCATI"));
				}
			}
			#endregion

			#region CONCESSIONI.FK_IDMERCATIUSO
            if (p_class.FK_IDMERCATIUSO.GetValueOrDefault(int.MinValue) > int.MinValue)
			{
				if ( this.recordCount("MERCATI_USO","ID","WHERE ID = " + p_class.FK_IDMERCATIUSO.ToString() + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
                    throw (new RecordNotfoundException("CONCESSIONI.FK_IDMERCATIUSO (" + p_class.FK_IDMERCATIUSO.ToString() + ") non trovato nella tabella MERCATI_USO"));
				}
			}
			#endregion

			#region CONCESSIONI.FK_IDPOSTEGGIO 
            if (p_class.FK_IDPOSTEGGIO.GetValueOrDefault(int.MinValue) > int.MinValue)
			{
				if ( this.recordCount("MERCATI_D","IDPOSTEGGIO","WHERE IDPOSTEGGIO = " + p_class.FK_IDPOSTEGGIO.ToString() + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
                    throw (new RecordNotfoundException("CONCESSIONI.FK_IDPOSTEGGIO (" + p_class.FK_IDPOSTEGGIO.ToString() + ") non trovato nella tabella MERCATI_D"));
				}
			}
			#endregion

			#region CONCESSIONI.FK_TIPOCONCESSIONE
			if ( ! IsStringEmpty( p_class.FK_TIPOCONCESSIONE ) )
			{
				if ( this.recordCount("CONCESSIONITIPI","TIPOCONCESSIONE","WHERE TIPOCONCESSIONE = '" + p_class.FK_TIPOCONCESSIONE + "'") == 0 )
				{
					throw( new RecordNotfoundException("CONCESSIONI.FK_TIPOCONCESSIONE (" + p_class.FK_TIPOCONCESSIONE + ") non trovato nella tabella CONCESSIONITIPI"));
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
                richiedente = anagrafeMgr.Extract(anagrafeDataClass, false, UpdateAnagrafe);

                if (StringChecker.IsStringEmpty(richiedente.CODICEANAGRAFE) && InsertAnagrafe)
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

                        if (StringChecker.IsStringEmpty(richiedente.CODICEANAGRAFE) && InsertAnagrafe)
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