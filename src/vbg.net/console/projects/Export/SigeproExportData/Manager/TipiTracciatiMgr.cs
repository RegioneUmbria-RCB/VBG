using System;
using System.Collections;
using System.Data;
using Init.SIGeProExport.Data;
using PersonalLib2.Data;
using Init.Utils;
using System.Collections.Generic;

namespace Init.SIGeProExport.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per EsportazioniMgr.
	/// </summary>
	public class TipiTracciatiMgr : BaseManager
	{
		public TipiTracciatiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public TIPITRACCIATI GetById(String pID)
		{
			TIPITRACCIATI retVal = new TIPITRACCIATI();
			retVal.CODICETIPO = pID;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TIPITRACCIATI;
			
			return null;
		}

		public TIPITRACCIATI GetByClass( TIPITRACCIATI p_class )
		{
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(p_class,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TIPITRACCIATI;

			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public List<TIPITRACCIATI> GetList(TIPITRACCIATI p_class)
		{
			return this.GetList(p_class,null);
		}

        public List<TIPITRACCIATI> GetList(TIPITRACCIATI p_class, TIPITRACCIATI p_cmpClass)
		{
			return db.GetClassList(p_class,p_cmpClass,false,false).ToList<TIPITRACCIATI>();
		}
		#endregion
		
		//
		public TIPITRACCIATI Update( TIPITRACCIATI p_class )
		{		
			db.Update( p_class );

			return p_class;
		}
	}
}

