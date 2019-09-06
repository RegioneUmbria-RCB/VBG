using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TipiAperturaMgr.\n	/// </summary>
	public class TipiAperturaMgr: BaseManager
{

		public TipiAperturaMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public TipiApertura GetById(String pTA_ID, String pIDCOMUNE)
		{
			TipiApertura retVal = new TipiApertura();
			retVal.TA_ID = pTA_ID;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipiApertura;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TipiApertura p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(TipiApertura p_class, TipiApertura p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

	#endregion
	}
}