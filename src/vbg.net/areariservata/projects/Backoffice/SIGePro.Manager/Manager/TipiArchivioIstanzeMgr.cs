using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TipiArchivioIstanzeMgr.\n	/// </summary>
	public class TipiArchivioIstanzeMgr: BaseManager
{

		public TipiArchivioIstanzeMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public TipiArchivioIstanze GetById(String pCODICEARCHIVIO, String pIDCOMUNE)
		{
			TipiArchivioIstanze retVal = new TipiArchivioIstanze();
						retVal.CODICEARCHIVIO = pCODICEARCHIVIO;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipiArchivioIstanze;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TipiArchivioIstanze p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(TipiArchivioIstanze p_class, TipiArchivioIstanze p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}
#endregion
}
}

