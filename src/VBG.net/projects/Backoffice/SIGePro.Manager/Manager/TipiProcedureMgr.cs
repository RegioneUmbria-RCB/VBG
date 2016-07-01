using System;
using System.Data;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Manager
{ 	///<summary>
	/// Descrizione di riepilogo per TipiProcedureMgr.\n	/// </summary>
	public class TipiProcedureMgr : BaseManager
	{

		public TipiProcedureMgr(DataBase dataBase) : base(dataBase) { }

		#region Metodi per l'accesso di base al DB

        [Obsolete]
		public TipiProcedure GetById(String pCODICEPROCEDURA, String pIDCOMUNE)
		{
			TipiProcedure retVal = new TipiProcedure();
			retVal.Codiceprocedura = Convert.ToInt32(pCODICEPROCEDURA);
			retVal.Idcomune = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
			if (mydc.Count != 0)
				return (mydc[0]) as TipiProcedure;

			return null;
		}

        public TipiProcedure GetById(string idComune, int? codiceprocedura)
        {
            TipiProcedure c = new TipiProcedure();


            c.Codiceprocedura = codiceprocedura;
            c.Idcomune = idComune;

            return (TipiProcedure)db.GetClass(c);
        }

		public TipiProcedure GetByCodiceIstanza(string idComune , int codiceIstanza)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"SELECT 
								  tipiprocedure.*
								FROM 
								  tipiprocedure,
								  istanze
								WHERE 
								  tipiprocedure.idcomune = istanze.idcomune AND
								  tipiprocedure.codiceprocedura = istanze.codiceprocedura AND
								  istanze.idcomune = {0} AND
								  istanze.codiceistanza = {1} ";

				sql = PreparaQueryParametrica(sql, "idcomune", "codiceistanza");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceistanza", codiceIstanza));

					return db.GetClass<TipiProcedure>(cmd);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}

		
		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(TipiProcedure p_class)
		{
			return this.GetList(p_class, null);
		}

		public ArrayList GetList(TipiProcedure p_class, TipiProcedure p_cmpClass)
		{
			return db.GetClassList(p_class, p_cmpClass, false, false);
		}



		#endregion
	}
}
