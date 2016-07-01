using System;
using System.Collections;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;

namespace SIGePro.Net.WebServices.WSSIGeProImport.Utils
{
	/// <summary>
	/// Descrizione di riepilogo per FunzioniSuiComuni.
	/// </summary>
	public class FunzioniSuiComuni
	{
		public static Comuni PrendiCodiceComune(DataBase db, Comuni Comune)
		{
			Comuni retVal = new Comuni();
			ComuniMgr comuniMgr = new ComuniMgr(db);

			ArrayList al = comuniMgr.GetList(Comune);

			switch (al.Count)
			{
				case 0:
					retVal = new Comuni();
					break;
				case 1:
					retVal = (al[0] as Comuni);
					break;
				default:
					throw new SystemException("Sono stati trovati " + al.Count + " comuni ");
			}
			return retVal;
		}
	}
}