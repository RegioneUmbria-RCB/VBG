using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per TipiOrarioDettaglioMgr.
	/// </summary>
	public class TipiOrarioDettaglioMgr: BaseManager
	{
		public TipiOrarioDettaglioMgr( DataBase dataBase ) : base( dataBase ) {}

		
		#region Metodi per l'accesso di base al DB
		public TipiOrarioDettaglio GetById(String pOA_ID, String pIDCOMUNE)
		{
			TipiOrarioDettaglio retVal = new TipiOrarioDettaglio();
			retVal.OA_ID = pOA_ID;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipiOrarioDettaglio;
			
			return null;
		}

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TipiOrarioDettaglio p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(TipiOrarioDettaglio p_class, TipiOrarioDettaglio p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

		#endregion
	}
}
