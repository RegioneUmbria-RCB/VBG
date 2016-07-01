using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per ResponsabiliSoftwareMgr.\n	/// </summary>
	public class ResponsabiliSoftwareMgr: BaseManager
{

		public ResponsabiliSoftwareMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB
		public ResponsabiliSoftware GetById(string idComune, int codiceResponsabile, string software)
		{
			ResponsabiliSoftware filtro = new ResponsabiliSoftware();
			filtro.IDCOMUNE = idComune;
			filtro.CODICERESPONSABILE = codiceResponsabile.ToString();
			filtro.SOFTWARE = software;

			return (ResponsabiliSoftware)db.GetClass(filtro);
		}

		public ResponsabiliSoftware GetById(String pCODICERESPONSABILE, String pSOFTWARE, String pIDCOMUNE)
		{
			ResponsabiliSoftware retVal = new ResponsabiliSoftware();
			retVal.CODICERESPONSABILE = pCODICERESPONSABILE;
			retVal.SOFTWARE = pSOFTWARE;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as ResponsabiliSoftware;
			
			return null;
		}

 

		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(ResponsabiliSoftware p_class)
		{
			return this.GetList(p_class,null);
		}
	
		public ArrayList GetList(ResponsabiliSoftware p_class, ResponsabiliSoftware p_cmpClass )
		{
			return db.GetClassList(p_class,p_cmpClass,false,false);
		}

#endregion
	}
}