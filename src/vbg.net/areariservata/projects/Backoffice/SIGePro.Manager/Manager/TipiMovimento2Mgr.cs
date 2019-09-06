using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TipiMovimento2Mgr.\n	/// </summary>
	public class TipiMovimento2Mgr: BaseManager
{

		public TipiMovimento2Mgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public TipiMovimento2 GetById(String pCODICETIPO, String pIDCOMUNE)
		{
			TipiMovimento2 retVal = new TipiMovimento2();
			retVal.CODICETIPO = pCODICETIPO;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipiMovimento2;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TipiMovimento2 p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(TipiMovimento2 p_class, TipiMovimento2 p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}
	#endregion
	}
}
