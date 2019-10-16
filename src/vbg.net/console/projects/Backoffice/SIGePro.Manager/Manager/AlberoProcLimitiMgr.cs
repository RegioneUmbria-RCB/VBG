using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per AlberoProcLimitiMgr.\n	/// </summary>
	public class AlberoProcLimitiMgr: BaseManager
	{
		public AlberoProcLimitiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public AlberoProcLimiti GetById(String pFKIDALBERO, String pFKIDZONA, String pIDCOMUNE)
		{
			AlberoProcLimiti retVal = new AlberoProcLimiti();
			
			retVal.FKIDALBERO = pFKIDALBERO;
			retVal.FKIDZONA = pFKIDZONA;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as AlberoProcLimiti;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(AlberoProcLimiti p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(AlberoProcLimiti p_class, AlberoProcLimiti p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}
	#endregion
	}
}