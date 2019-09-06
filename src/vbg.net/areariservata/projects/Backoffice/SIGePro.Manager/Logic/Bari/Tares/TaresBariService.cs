// -----------------------------------------------------------------------
// <copyright file="TaresBariService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.Bari.Tares
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using PersonalLib2.Data;
	using System.Data;

	public class TaresBariService
	{
		DataBase _db;

		public TaresBariService(DataBase db)
		{
			this._db = db;
		}

		public bool UtenteAppartieneACaf(string codiceFiscale)
		{
			return !String.IsNullOrEmpty(GetCodiceFiscaleCafDaCFOperatore(codiceFiscale));
		}

		public string GetCodiceFiscaleCafDaCFOperatore(string codiceFiscaleOperatore)
		{

			bool closeCnn = false;

			try
			{
				if (_db.Connection.State == ConnectionState.Closed)
				{
					_db.Connection.Open();
					closeCnn = true;
				}
				var sql = String.Format(@"SELECT codicefiscalecaf,codicefiscaleope FROM tares_caf_operatori WHERE codicefiscaleope = {0}", _db.Specifics.QueryParameterName("codicefiscaleope"));

				using (var cmd = _db.CreateCommand(sql))
				{
					cmd.Parameters.Add(_db.CreateParameter("codicefiscaleope", codiceFiscaleOperatore));

					using (var dr = cmd.ExecuteReader())
					{
						if (!dr.Read())
							return null;

						return dr[0].ToString();
					}
				}
			}
			finally
			{
				if (closeCnn)
					_db.Connection.Close();
			}

		}
	}
}
