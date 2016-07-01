using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per ChiusureIstanzaMgr.\n	/// </summary>
	public class ChiusureIstanzaMgr: BaseManager
{

		public ChiusureIstanzaMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public ChiusureIstanza GetById(String pCODICEISTANZA, String pIDCOMUNE)
		{
			ChiusureIstanza retVal = new ChiusureIstanza();
			retVal.CODICEISTANZA = pCODICEISTANZA;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as ChiusureIstanza;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<ChiusureIstanza> GetList(ChiusureIstanza p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<ChiusureIstanza> GetList(ChiusureIstanza p_class, ChiusureIstanza p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<ChiusureIstanza>();
		}

        public void Delete(ChiusureIstanza p_class)
        {
            db.Delete(p_class);
        }

#endregion
	}
}
