using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TipologiaIstanzaMgr.\n	/// </summary>
	public class TipologiaIstanzaMgr: BaseManager
{

		public TipologiaIstanzaMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public TipologiaIstanza GetById(String pTI_ID, String pIDCOMUNE)
		{
			TipologiaIstanza retVal = new TipologiaIstanza();
			retVal.TI_ID = pTI_ID;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipologiaIstanza;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TipologiaIstanza p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(TipologiaIstanza p_class, TipologiaIstanza p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}
#endregion
	}
}
