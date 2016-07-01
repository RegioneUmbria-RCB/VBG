using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.Utils;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per TipiMovimentoDocTipoMgr.\n	/// </summary>
	public class TipiMovimentoDocTipoMgr: BaseManager
	{

		public TipiMovimentoDocTipoMgr( DataBase dataBase ) : base( dataBase ) {}

		private TipiMovimentoDocTipo DataIntegrations ( TipiMovimentoDocTipo p_class )
		{
			TipiMovimentoDocTipo retVal = ( TipiMovimentoDocTipo ) p_class.Clone();

			return retVal;
		}

		private void Validate(TipiMovimentoDocTipo p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			RequiredFieldValidate( p_class ,ambitoValidazione);

			//ForeignValidate( p_class );
		}

		private void ForeignValidate ( TipiMovimentoDocTipo p_class )
		{
			#region TIPIMOVIMENTODOCTIPO.CODICELETTERA
			if ( ! IsStringEmpty( p_class.CODICELETTERA ) )
			{
				if ( this.recordCount("LETTERETIPO","CODICELETTERA","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICELETTERA = " + p_class.CODICELETTERA ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTODOCTIPO.CODICELETTERA (" + p_class.CODICELETTERA + ") non trovato nella tabella TIPIMOVIMENTODOCTIPO"));
				}
			}
			#endregion

			#region TIPIMOVIMENTODOCTIPO.TIPOMOVIMENTO
			if ( ! IsStringEmpty( p_class.TIPOMOVIMENTO ) )
			{
				if ( this.recordCount("TIPIMOVIMENTO","TIPOMOVIMENTO","WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND TIPOMOVIMENTO = '" + p_class.TIPOMOVIMENTO + "'" ) == 0 )
				{
					throw( new RecordNotfoundException("TIPIMOVIMENTO.CODICELETTERA (" + p_class.CODICELETTERA + ") non trovato nella tabella LETTERETIPO"));
				}
			}
			#endregion
		}

		public TipiMovimentoDocTipo Insert( TipiMovimentoDocTipo p_class )
		{
			p_class = DataIntegrations( p_class );

			Validate(p_class, AmbitoValidazione.Insert);
			
			db.Insert( p_class );

			return p_class;
		}





		#region Metodi per l'accesso di base al DB


		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<TipiMovimentoDocTipo> GetList(TipiMovimentoDocTipo p_class)
		{
			return this.GetList(p_class,null);
		}
	
		/*public ArrayList GetList(TipiMovimentoDocTipo p_class, TipiMovimentoDocTipo p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}*/

        public List<TipiMovimentoDocTipo> GetList(TipiMovimentoDocTipo p_class, TipiMovimentoDocTipo p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<TipiMovimentoDocTipo>();
        }

		#endregion
	}
}
