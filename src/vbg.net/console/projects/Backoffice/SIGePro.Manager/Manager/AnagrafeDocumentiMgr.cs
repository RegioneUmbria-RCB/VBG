using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions.AnagrafeDocumenti;
using Init.Utils;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
 { 	
	public class AnagrafeDocumentiMgr: BaseManager
{

		public AnagrafeDocumentiMgr( DataBase dataBase ) : base( dataBase ) {}


		public AnagrafeDocumenti GetById(String pID, String pIDCOMUNE)
		{
			AnagrafeDocumenti retVal = new AnagrafeDocumenti();
						retVal.ID = pID;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as AnagrafeDocumenti;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public List<AnagrafeDocumenti> GetList(AnagrafeDocumenti p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<AnagrafeDocumenti> GetList(AnagrafeDocumenti p_class, AnagrafeDocumenti p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<AnagrafeDocumenti>();
		}


		/// <summary>
		/// Effettua l'eliminazione dell'oggetto dal database
		/// </summary>
		/// <param name="p_class">L'oggetto da eliminare</param>
		public void Delete(AnagrafeDocumenti p_class)
		{	
			db.Delete(p_class);
		}

		
		public AnagrafeDocumenti Insert( AnagrafeDocumenti p_class )
		{
			Validate(p_class, AmbitoValidazione.Insert);

			db.Insert(p_class);

			p_class = ChildDataIntegrations( p_class );

			ChildInsert( p_class );

			return p_class;
		}

		public AnagrafeDocumenti Update( AnagrafeDocumenti p_class )
		{

			db.Update( p_class );

			return p_class;
		}

		
		private AnagrafeDocumenti ChildDataIntegrations( AnagrafeDocumenti p_class )
		{
			AnagrafeDocumenti retVal = ( AnagrafeDocumenti ) p_class.Clone();

			if ( StringChecker.IsStringEmpty( retVal.CODICEOGGETTO ) && retVal.Oggetto != null )
			{
				if ( StringChecker.IsStringEmpty( retVal.Oggetto.IDCOMUNE ) )
				{
					retVal.Oggetto.IDCOMUNE = retVal.IDCOMUNE;
				}
				else
				{
					if ( retVal.Oggetto.IDCOMUNE.ToUpper() != retVal.IDCOMUNE.ToUpper() )
						throw new IncongruentDataException( p_class, "ANAGRAFEDOCUMENTI.OGGETTI.IDCOMUNE diverso da ANAGRAFEDOCUMENTI.IDCOMUNE");
				}
			}

			return retVal;
		}

		private void ChildInsert(AnagrafeDocumenti p_class)
		{
			if (p_class.Oggetto!=null)
			{
				OggettiMgr oggettiMgr = new OggettiMgr(db);
				oggettiMgr.Insert(p_class.Oggetto);
			}
		}

		private void Validate(AnagrafeDocumenti p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class ,ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate ( AnagrafeDocumenti p_class )
		{
			#region ANAGRAFEDOCUMENTI.CODICEANAGRAFE
			if ( ! StringChecker.IsStringEmpty( p_class.CODICEANAGRAFE ) )
			{
				if ( this.recordCount("ANAGRAFE","CODICEANAGRAFE","WHERE CODICEANAGRAFE = " + p_class.CODICEANAGRAFE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException(p_class,"ANAGRAFEDOCUMENTI.CODICEANAGRAFE non trovato nella tabella ANAGRAFE"));
				}
			}
			#endregion

			#region ANAGRAFEDOCUMENTI.IDTIPODOCUMENTO
			if ( ! StringChecker.IsStringEmpty( p_class.IDTIPODOCUMENTO ) )
			{
				if ( this.recordCount("TIPIDOCUMENTO","IDTIPODOCUMENTO","WHERE IDTIPODOCUMENTO = " + p_class.IDTIPODOCUMENTO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException(p_class,"ANAGRAFEDOCUMENTI.IDTIPODOCUMENTO non trovato nella tabella TIPIDOCUMENTO"));
				}
			}
			#endregion

			#region ANAGRAFEDOCUMENTI.CODICEISTANZA
			if ( ! StringChecker.IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if ( this.recordCount("ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException(p_class,"ANAGRAFEDOCUMENTI.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region ANAGRAFEDOCUMENTI.CODICEOGGETTO
			if ( ! StringChecker.IsStringEmpty( p_class.CODICEOGGETTO ) )
			{
				if ( this.recordCount("OGGETTI","CODICEOGGETTO","WHERE CODICEOGGETTO = " + p_class.CODICEOGGETTO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException(p_class,"ANAGRAFEDOCUMENTI.CODICEOGGETTO non trovato nella tabella OGGETTI"));
				}
			}
			#endregion
		}
	}
}