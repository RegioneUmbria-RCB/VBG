
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
using Init.SIGePro.Manager.DTO.Normative;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class InventarioprocLeggiMgr
    {
		public List<NormativaDto> GetByCodiceInventario(string idComune, int codiceInventario)
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
								leggi.le_id AS codice,
								leggi.le_descrizione AS descrizione,
								leggi.le_link as link,
								leggi.codiceoggetto AS codiceoggetto,
								normative.normativa AS categoria
							FROM 
								inventarioproc_leggi join leggi ON
									leggi.idcomune = inventarioproc_leggi.idcomune AND
									leggi.le_id = inventarioproc_leggi.fkleid
								left join normative ON
									normative.idcomune = leggi.idcomune AND
									normative.codicenormativa = leggi.FK_NORMATIVE
							WHERE
								inventarioproc_leggi.idcomune = {0} AND
								inventarioproc_leggi.CODICEINVENTARIO={1}
							order by leggi.le_descrizione";

				sql = PreparaQueryParametrica(sql, "idComune", "codiceInventario");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceInventario", codiceInventario));

					using (var dr = cmd.ExecuteReader())
					{
						var rVal = new List<NormativaDto>();

						while (dr.Read())
						{
							var objCodiceOggetto = dr["codiceoggetto"];


							var el = new NormativaDto
							{
								Codice = Convert.ToInt32(dr["codice"]),
								Descrizione = dr["descrizione"].ToString(),
								CodiceOggetto = objCodiceOggetto == DBNull.Value ? (int?)null : Convert.ToInt32(objCodiceOggetto),
								Categoria = dr["categoria"].ToString(),
								Link = dr["link"].ToString(),
							};

							rVal.Add(el);
						}

						return rVal;
					}
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
				