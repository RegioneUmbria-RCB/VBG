using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Collection;
using Init.Utils;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per ResponsabiliMgr.\n	/// </summary>
	public class ResponsabiliRuoliMgr: BaseManager
	{

		public ResponsabiliRuoliMgr( DataBase dataBase ) : base( dataBase ) {}

		
		#region Metodi per l'accesso di base al DB
		 
		public IEnumerable<ResponsabiliRuoli> GetList(ResponsabiliRuoli p_class)
		{
			return this.GetList(p_class,null);
		}

		public IEnumerable<ResponsabiliRuoli> GetList(ResponsabiliRuoli p_class, ResponsabiliRuoli p_cmpClass)
		{
			return db.GetClassList(p_class,p_cmpClass,false,false).ToList<ResponsabiliRuoli>();
		}

		public void Delete(ResponsabiliRuoli p_class)
		{
			db.Delete( p_class) ;
		}

		public ResponsabiliRuoli Insert( ResponsabiliRuoli p_class  )
		{
			Validate(p_class, AmbitoValidazione.Insert);

			db.Insert( p_class ) ;

			return p_class;
		}



		private void Validate(ResponsabiliRuoli p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class ,ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate ( ResponsabiliRuoli p_class )
		{
			#region RESPONSABILI.CODICERESPONSABILE
			if ( ! StringChecker.IsStringEmpty( p_class.CODICERESPONSABILE ) )
			{
				if ( this.recordCount("RESPONSABILI","CODICERESPONSABILE","WHERE CODICERESPONSABILE = " + p_class.CODICERESPONSABILE + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("RESPONSABILIRUOLI.CODICERESPONSABILE (" + p_class.CODICERESPONSABILE + ") non trovato nella tabella RESPONSABILI"));
				}
			}
			#endregion

			#region RUOLI.ID
			if ( ! StringChecker.IsStringEmpty( p_class.IDRUOLO ) )
			{
				if ( this.recordCount("RUOLI","ID","WHERE ID = " + p_class.IDRUOLO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("RESPONSABILIRUOLI.IDRUOLO (" + p_class.IDRUOLO + ") non trovato nella tabella RUOLI"));
				}
			}
			#endregion

		}


		#endregion
	}
}