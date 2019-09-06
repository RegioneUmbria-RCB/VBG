using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TipiOrarioMgr.\n	/// </summary>
	public class TipiOrarioMgr: BaseManager
{

		public TipiOrarioMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public TipiOrario GetById(String pTO_ID, String pIDCOMUNE)
		{
			TipiOrario retVal = new TipiOrario();
						retVal.TO_ID = pTO_ID;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipiOrario;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TipiOrario p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(TipiOrario p_class, TipiOrario p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

#endregion
	}
}