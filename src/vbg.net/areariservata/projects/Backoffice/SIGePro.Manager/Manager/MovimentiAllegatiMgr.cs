using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{
	
	public class MovimentiAllegatiMgr: BaseManager
	{
		public MovimentiAllegatiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public MovimentiAllegati GetById(String pIDCOMUNE, String pIDALLEGATO, String pCODICEMOVIMENTO)
		{
			MovimentiAllegati retVal = new MovimentiAllegati();
			retVal.IDCOMUNE = pIDCOMUNE;
			retVal.IDALLEGATO = pIDALLEGATO;
			retVal.CODICEMOVIMENTO = pCODICEMOVIMENTO;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as MovimentiAllegati;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public List<MovimentiAllegati> GetList(MovimentiAllegati p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public List<MovimentiAllegati> GetList(MovimentiAllegati p_class, MovimentiAllegati p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false).ToList<MovimentiAllegati>();
		}

		public void Delete(MovimentiAllegati cls)
		{
			VerificaRecordCollegati(cls);

			EffettuaCancellazioneACascata(cls);

			db.Delete(cls);

			EliminaOggetto(cls);
		}

		private void EliminaOggetto(MovimentiAllegati cls)
		{
			if (!String.IsNullOrEmpty(cls.CODICEOGGETTO))
				new OggettiMgr(db).EliminaOggetto(cls.IDCOMUNE, Convert.ToInt32(cls.CODICEOGGETTO));
		}

		private void EffettuaCancellazioneACascata(MovimentiAllegati cls)
		{
		}

        private void VerificaRecordCollegati(MovimentiAllegati cls)
        {

        }

		public MovimentiAllegati Insert( MovimentiAllegati p_class )
		{	
			Validate( p_class ,AmbitoValidazione.Insert);

			db.Insert( p_class );

			p_class = ChildDataIntegrations( p_class );

//			ChildInsert( p_class );
			
			return p_class;
		}	

		public MovimentiAllegati Update( MovimentiAllegati p_class )
		{
			db.Update( p_class );
			
			return p_class;
		}	
		private MovimentiAllegati ChildDataIntegrations( MovimentiAllegati p_class )
		{
			MovimentiAllegati retVal = ( MovimentiAllegati ) p_class.Clone();

            if (IsStringEmpty(retVal.CODICEOGGETTO) && retVal.Oggetto != null)
			{
				if ( IsStringEmpty( retVal.Oggetto.IDCOMUNE ) )
				{
					retVal.Oggetto.IDCOMUNE = retVal.IDCOMUNE;
				}
				else
				{
					if ( retVal.Oggetto.IDCOMUNE != retVal.IDCOMUNE )
						throw( new IncongruentDataException("MOVIMENTIALLEGATI.IDCOMUNE è diverso da MOVIMENTIALLEGATI.OGGETTI.IDCOMUNE" ));
				}
			}

			return retVal;
		}


		private void Validate(MovimentiAllegati p_class, AmbitoValidazione ambitoValidazione)
		{
			throw new NotImplementedException();
			/*
			 * Gestito da JAVA
			 * 
			if ( IsStringEmpty( p_class.CODICEMOVIMENTO ) )
				throw ( new RequiredFieldException("MOVIMENTIALLEGATI.CODICEMOVIMENTO non presente") );

			if ( IsStringEmpty( p_class.CODICEOGGETTO ) && p_class.Oggetto != null )
			{
				OggettiMgr oggettiMgr = new OggettiMgr(db);
				p_class.CODICEOGGETTO = oggettiMgr.Insert(p_class.Oggetto).CODICEOGGETTO;
			}

			RequiredFieldValidate( p_class , ambitoValidazione);

			ForeignValidate( p_class );
			*/
		}

		private void ForeignValidate ( MovimentiAllegati p_class )
		{
			#region MOVIMENTIALLEGATI.CODICEMOVIMENTO
			if ( ! IsStringEmpty( p_class.CODICEMOVIMENTO ) )
			{
				if (  this.recordCount( "MOVIMENTI","CODICEMOVIMENTO","WHERE CODICEMOVIMENTO = " + p_class.CODICEMOVIMENTO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("MOVIMENTIALLEGATI.CODICEMOVIMENTO non trovato nella tabella MOVIMENTI"));
				}
			}
			#endregion

			#region MOVIMENTIALLEGATI.CODICEOGGETTO
			if ( ! IsStringEmpty( p_class.CODICEOGGETTO ) )
			{
				if (  this.recordCount( "OGGETTI","CODICEOGGETTO","WHERE CODICEOGGETTO = " + p_class.CODICEOGGETTO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("MOVIMENTIALLEGATI.CODICEOGGETTO non trovato nella tabella OGGETTI"));
				}
			}
			#endregion
		}

		#endregion
	}
}