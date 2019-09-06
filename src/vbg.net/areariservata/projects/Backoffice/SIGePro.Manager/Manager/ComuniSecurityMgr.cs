using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per TitoliMgr.\n	/// </summary>
	public class ComuniSecurityMgr: BaseManager
	{

		public ComuniSecurityMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB


		public ComuniSecurity GetById(string pCS_CODICEISTAT)
		{
			ComuniSecurity retVal = new ComuniSecurity();
			retVal.CS_CODICEISTAT = pCS_CODICEISTAT;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as ComuniSecurity;
			
			return null;
		}

		public ComuniSecurity GetByClass(ComuniSecurity pClass)
		{
			DataClassCollection comunisecurity = db.GetClassList(pClass,true,false);
			if (comunisecurity.Count!=0)
				return (comunisecurity[0]) as ComuniSecurity;

			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(ComuniSecurity p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(ComuniSecurity p_class, ComuniSecurity p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}


		#endregion
	}
}

