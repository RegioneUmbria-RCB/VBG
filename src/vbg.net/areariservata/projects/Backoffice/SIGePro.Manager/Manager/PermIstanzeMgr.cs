using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
{
	public class PermIstanzeMgr: BaseManager
	{
		public PermIstanzeMgr( DataBase dataBase ) : base( dataBase ) {}

		
		#region Metodi per l'accesso di base al DB

		public PermIstanze GetById(String pID, String pIDCOMUNE)
		{
			PermIstanze retVal = new PermIstanze();
			retVal.ID = pID;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as PermIstanze;
			
			return null;
		}

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<PermIstanze> GetList(PermIstanze p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public List<PermIstanze> GetList(PermIstanze p_class, PermIstanze p_cmpClass )
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<PermIstanze>();
		}


		public void Delete(PermIstanze p_class)
		{	
			db.Delete( p_class) ;
		}

		public PermIstanze Insert( PermIstanze p_class )
		{

			Validate(p_class, AmbitoValidazione.Insert);

			db.Insert( p_class );

			return p_class;
		}

		public PermIstanze Update( PermIstanze p_class )
		{
			
			db.Update( p_class );
			
			return p_class;
		}

		private void Validate(PermIstanze p_class, Init.SIGePro.Validator.AmbitoValidazione ambitoValidazione)
		{
			p_class.TABELLA = "1";

			RequiredFieldValidate( p_class ,ambitoValidazione);

			ForeignValidate( p_class );
		}

		private void ForeignValidate ( PermIstanze p_class )
		{
			#region PERMISTANZE.CODICEISTANZA
			if ( ! IsStringEmpty( p_class.CODICEISTANZA ) )
			{
				if ( this.recordCount("ISTANZE","CODICEISTANZA","WHERE CODICEISTANZA = '" + p_class.CODICEISTANZA + "' AND IDCOMUNE = '" + p_class.IDCOMUNE + "'") == 0 )
				{
					throw( new RecordNotfoundException("PERMISTANZE.CODICEISTANZA non trovato nella tabella ISTANZE"));
				}
			}
			#endregion
		}

		#endregion

		public bool VerificaPermessiUtente(string idComune, int codiceResponsabile, int codiceIstanza, int tabella)
		{
			PermIstanze filtro = new PermIstanze();
			filtro.IDCOMUNE = idComune;
			filtro.CODICERESPONSABILE = codiceResponsabile.ToString();
			filtro.CODICEISTANZA = codiceIstanza.ToString();
			filtro.TABELLA = tabella.ToString();

			List<PermIstanze> l = db.GetClassList(filtro).ToList<PermIstanze>();

			if (l == null || l.Count == 0)
				return false;

			return true;
		}
	}
}
