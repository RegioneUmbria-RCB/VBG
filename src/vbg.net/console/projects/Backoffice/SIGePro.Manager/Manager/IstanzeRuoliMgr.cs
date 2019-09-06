using Init.SIGePro.Collection;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per IstanzeOneriMgr.\n	/// </summary>
	public class IstanzeRuoliMgr: BaseManager
	{
		public IstanzeRuoliMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB
		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<IstanzeRuoli> GetList(IstanzeRuoli p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<IstanzeRuoli> GetList(IstanzeRuoli p_class, IstanzeRuoli p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeRuoli>();
		}


		public void Delete( IstanzeRuoli p_class )
		{		
			db.Delete( p_class) ;
		}

		public IstanzeRuoli Insert( IstanzeRuoli p_class )
		{
			
			Validate( p_class ,AmbitoValidazione.Insert );

			db.Insert( p_class );
			
			return p_class;
		}


		private void Validate(IstanzeRuoli p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class , ambitoValidazione);
			
			ForeignValidate( p_class );
		}

		private void ForeignValidate ( IstanzeRuoli p_class )
		{
			#region ISTANZERUOLI.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if (  this.recordCount( "ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = " + p_class.CODICEISTANZA + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZERUOLI.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion

			#region ISTANZERUOLI.IDRUOLO
			if ( ! IsStringEmpty( p_class.IDRUOLO ) )
			{
				if (  this.recordCount( "RUOLI","ID","WHERE ID = " + p_class.IDRUOLO + " AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("ISTANZERUOLI.IDRUOLO non trovato nella tabella RUOLI"));
				}
			}
			#endregion
		}
		#endregion
	}
}

