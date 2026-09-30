using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{   ///<summary>
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

            List<TipiProcedure> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as TipiProcedure;

            return null;
        }

        public TipiProcedure GetById(string idComune, int? codiceprocedura)
        {
            TipiProcedure c = new TipiProcedure();


            c.Codiceprocedura = codiceprocedura;
            c.Idcomune = idComune;

            return (TipiProcedure)this.db.GetClass(c);
        }

        public TipiProcedure GetByCodiceIstanza(string idComune, int codiceIstanza)
        {

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
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

                sql = this.PreparaQueryParametrica(sql, "idcomune", "codiceistanza");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idcomune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceistanza", codiceIstanza));

                    return this.db.GetClass<TipiProcedure>(cmd);
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }

        #endregion
    }
}
