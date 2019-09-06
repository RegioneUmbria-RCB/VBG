using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per DynTModelliMgr.\n	/// </summary>
	public class DynTModelliMgr: BaseManager
{

		public DynTModelliMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB


		public DynTModelli GetById(String pIDMODELLO, String pIDCOMUNE)
		{
			DynTModelli retVal = new DynTModelli();
			retVal.IDMODELLO = pIDMODELLO;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as DynTModelli;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(DynTModelli p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(DynTModelli p_class, DynTModelli p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

	#endregion
	}
}