using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per InterventiMgr.\n	/// </summary>
	public class InterventiMgr: BaseManager
{

		public InterventiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public Interventi GetById(int pCODICEINTERVENTO, String pIDCOMUNE)
		{
			Interventi retVal = new Interventi();
			retVal.CodiceIntervento = pCODICEINTERVENTO;
			retVal.Idcomune = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as Interventi;
			
			return null;
		}

		public Interventi GetByClass( Interventi p_class )
		{
			return (db.GetClassList(p_class,true,false)[0]) as Interventi;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(Interventi p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(Interventi p_class, Interventi p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

#endregion
	}
}