using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
{ 	///<summary>
	/// Descrizione di riepilogo per CittadinanzaMgr.\n	/// </summary>
	public class CatastoMgr: BaseManager
	{

		public CatastoMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public Catasto GetById(String pCODICE)
		{
			Catasto retVal = new Catasto();
			retVal.CODICE = pCODICE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as Catasto;
			
			return null;
		}

        public Catasto GetByClass(Catasto cls)
        {
            PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(cls, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as Catasto;

            return null;
        }

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(Catasto p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(Catasto p_class, Catasto p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}
		#endregion
	}
}

