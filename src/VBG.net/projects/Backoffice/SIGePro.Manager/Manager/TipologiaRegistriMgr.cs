using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TipologiaRegistriMgr.\n	/// </summary>
	public class TipologiaRegistriMgr: BaseManager
{

		public TipologiaRegistriMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB


		public TipologiaRegistri GetById(String pTR_ID, String pIDCOMUNE)
		{
			TipologiaRegistri retVal = new TipologiaRegistri();
			retVal.TR_ID = pTR_ID;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipologiaRegistri;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public List<TipologiaRegistri> GetList(TipologiaRegistri p_class)
		{
			return this.GetList(p_class,null);
		}

		public List<TipologiaRegistri> GetList(TipologiaRegistri p_class, TipologiaRegistri p_cmpClass)
		{
			return db.GetClassList(p_class, p_cmpClass, false, false).ToList < TipologiaRegistri>();
		}
#endregion
	}
}
