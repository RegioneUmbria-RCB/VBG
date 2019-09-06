using System;
using System.Collections;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGeProExport.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per ParametriEsportazioneMgr.
	/// </summary>
	public class ParametriEsportazioneMgr : BaseManager
	{
		public ParametriEsportazioneMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public PARAMETRIESPORTAZIONE GetById(String pIDCOMUNE, String pID)
		{
			PARAMETRIESPORTAZIONE retVal = new PARAMETRIESPORTAZIONE();
            retVal.IDCOMUNE = pIDCOMUNE;
			retVal.ID = pID;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as PARAMETRIESPORTAZIONE;
			
			return null;
		}

		public PARAMETRIESPORTAZIONE GetByClass( PARAMETRIESPORTAZIONE p_class )
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(p_class,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as PARAMETRIESPORTAZIONE;

			return null;
		}

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<PARAMETRIESPORTAZIONE> GetList(PARAMETRIESPORTAZIONE p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<PARAMETRIESPORTAZIONE> GetList(PARAMETRIESPORTAZIONE p_class, PARAMETRIESPORTAZIONE p_cmpClass)
		{
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<PARAMETRIESPORTAZIONE>();
		}
		
		
		public PARAMETRIESPORTAZIONE Insert( PARAMETRIESPORTAZIONE p_class )
		{
            if (string.IsNullOrEmpty(p_class.ID))
                p_class.ID = (findMax("PARAMETRIESPORTAZIONE", "ID", "IDCOMUNE = '" + p_class.IDCOMUNE + "'") + 1).ToString();

			db.Insert( p_class );
			return p_class;
		}

		public PARAMETRIESPORTAZIONE Delete( PARAMETRIESPORTAZIONE p_class )
		{		
			db.Delete( p_class );
			return p_class;
		}

		public PARAMETRIESPORTAZIONE Update( PARAMETRIESPORTAZIONE p_class )
		{		
			db.Update( p_class );
			return p_class;
		}

		#endregion
	}
}
