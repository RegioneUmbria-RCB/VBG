using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per AreeMgr.\n	/// </summary>
	public class AreeMgr: BaseManager
	{
		public AreeMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public Aree GetById(String pCODICEAREA, String pIDCOMUNE)
		{
			Aree retVal = new Aree();
			retVal.CODICEAREA = pCODICEAREA;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as Aree;
			
			return null;
		}

		public Aree GetByClass( Aree pClass )
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(pClass,true,true);
			if (mydc.Count!=0)
				return (mydc[0]) as Aree;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(Aree p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(Aree p_class, Aree p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}
		#endregion
	}
}
