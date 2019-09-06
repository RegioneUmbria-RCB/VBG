using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using PersonalLib2.Sql.Collections;

namespace Init.SIGePro.Manager
{
	/// <summary>
	/// Descrizione di riepilogo per AmministrazioniReferentiMgr.
	/// </summary>
	public class AmministrazioniReferentiMgr: BaseManager
	{
		public AmministrazioniReferentiMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public AmministrazioniReferenti GetById(string pIDCOMUNE, string pID)
		{
			AmministrazioniReferenti retVal = new AmministrazioniReferenti();
			retVal.IDCOMUNE = pIDCOMUNE;
			retVal.ID = pID;
		
			DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as AmministrazioniReferenti;
			
			return null;
		}

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(AmministrazioniReferenti p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(AmministrazioniReferenti p_class, AmministrazioniReferenti p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

		#endregion
	}
}
