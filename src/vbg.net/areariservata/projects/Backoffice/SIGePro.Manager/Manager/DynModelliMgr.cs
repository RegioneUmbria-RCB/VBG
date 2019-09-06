using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per DynModelliMgr.\n	/// </summary>
	public class DynModelliMgr: BaseManager
{

		public DynModelliMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB


		public DynModelli GetById(String pCODICE, String pIDCOMUNE)
		{
			DynModelli retVal = new DynModelli();
			retVal.CODICE = pCODICE;
			retVal.IDCOMUNE = pIDCOMUNE;
	
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as DynModelli;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(DynModelli p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(DynModelli p_class, DynModelli p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

	#endregion
	}
}