using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager 
 { 	///<summary>
	/// Descrizione di riepilogo per TipiModalitaPagamentoMgr.\n	/// </summary>
	public class TipiModalitaPagamentoMgr: BaseManager
{

		public TipiModalitaPagamentoMgr( DataBase dataBase ) : base( dataBase ) {}

		#region Metodi per l'accesso di base al DB

		public TipiModalitaPagamento GetById(String pMP_ID, String pIDCOMUNE)
		{
			TipiModalitaPagamento retVal = new TipiModalitaPagamento();
			retVal.MP_ID = pMP_ID;
			retVal.IDCOMUNE = pIDCOMUNE;
		
			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal,true,false);
			if (mydc.Count!=0)
				return (mydc[0]) as TipiModalitaPagamento;
			
			return null;
		}

 

		public IEnumerable<TipiModalitaPagamento> GetList(string idComune)
		{
			return db.GetClassList(new TipiModalitaPagamento { IDCOMUNE = idComune }).ToList<TipiModalitaPagamento>();
		}
	

#endregion
	}
}
