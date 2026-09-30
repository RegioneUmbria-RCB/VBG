
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class StpEndoTipo2Mgr
    {
		public IEnumerable<StpEndoTipo2> GetByCodiceIntervento(string idComune, int codiceIntervento)
		{

			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				var sql =  PreparaQueryParametrica("select * from STP_ENDO_TIPO2 where idcomune={0} and FK_SC_ID = {1}","IdComune","CodIntervento");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("CodIntervento", codiceIntervento));

					return db.GetClassList<StpEndoTipo2>(cmd);
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}
	}
}
				