using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per DynCampiMgr.\n	/// </summary>
	public class DynCampiMgr: BaseManager
{

		public DynCampiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB


		public DynCampi GetById(String pCODICE, String pIDCOMUNE)
		{
			DynCampi retVal = new DynCampi();
						retVal.CODICE = pCODICE;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as DynCampi;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(DynCampi p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(DynCampi p_class, DynCampi p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

#endregion
 }
}