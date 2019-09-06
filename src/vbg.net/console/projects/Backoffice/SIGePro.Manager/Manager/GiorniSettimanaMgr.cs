using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per GiorniSettimanaMgr.
	/// </summary>
	public class GiorniSettimanaMgr: BaseManager
	{
		public GiorniSettimanaMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public GiorniSettimana GetById(String pGS_ID )
		{
			GiorniSettimana retVal = new GiorniSettimana();
			retVal.GS_ID = pGS_ID;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as GiorniSettimana;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(GiorniSettimana p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(GiorniSettimana p_class, GiorniSettimana p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

		#endregion
	}
}
